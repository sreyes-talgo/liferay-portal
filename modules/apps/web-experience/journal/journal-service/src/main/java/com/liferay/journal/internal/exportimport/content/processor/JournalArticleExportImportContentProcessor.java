/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.journal.internal.exportimport.content.processor;

import com.liferay.document.library.kernel.exception.NoSuchFileEntryException;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.exportimport.content.processor.ExportImportContentProcessor;
import com.liferay.exportimport.content.processor.base.BaseTextExportImportContentProcessor;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerControl;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.exception.NoSuchArticleException;
import com.liferay.journal.exportimport.data.handler.JournalPortletDataHandler;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.portal.kernel.exception.BulkException;
import com.liferay.portal.kernel.exception.NoSuchLayoutException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Attribute;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.xml.XPath;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gergely Mathe
 */
@Component(
	property = "model.class.name=com.liferay.journal.model.JournalArticle",
	service = {
		ExportImportContentProcessor.class,
		JournalArticleExportImportContentProcessor.class
	}
)
public class JournalArticleExportImportContentProcessor
	extends BaseTextExportImportContentProcessor {

	@Override
	public String replaceExportContentReferences(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			String content, boolean exportReferencedContent,
			boolean escapeContent)
		throws Exception {

		content = replaceExportJournalArticleReferences(
			portletDataContext, stagedModel, content, exportReferencedContent);

		content = super.replaceExportContentReferences(
			portletDataContext, stagedModel, content, exportReferencedContent,
			escapeContent);

		return content;
	}

	@Override
	public String replaceImportContentReferences(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			String content)
		throws Exception {

		content = replaceImportJournalArticleReferences(
			portletDataContext, stagedModel, content);

		content = super.replaceImportContentReferences(
			portletDataContext, stagedModel, content);

		content = replaceImportImageFileEntryIds(portletDataContext, content);

		return content;
	}

	@Override
	public void validateContentReferences(long groupId, String content)
		throws PortalException {

		validateJournalArticleReferences(groupId, content);

		try {
			super.validateContentReferences(groupId, content);
		}
		catch (NoSuchFileEntryException | NoSuchLayoutException e) {
			if (ExportImportThreadLocal.isImportInProcess()) {
				if (_log.isDebugEnabled()) {
					StringBundler sb = new StringBundler(8);

					sb.append("An invalid ");

					String type = "page";

					if (e instanceof NoSuchFileEntryException) {
						type = "file entry";
					}

					sb.append(type);

					sb.append(" was detected during import when validating ");
					sb.append("the content below. This is not an error; it ");
					sb.append("typically means the ");
					sb.append(type);
					sb.append(" was deleted.\n");
					sb.append(content);

					_log.debug(sb.toString());
				}

				return;
			}

			throw e;
		}
	}

	@Override
	protected String replaceExportDLReferences(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			String content, boolean exportReferencedContent)
		throws Exception {

		Group group = _groupLocalService.getGroup(
			portletDataContext.getGroupId());

		if (group.isStagingGroup()) {
			group = group.getLiveGroup();
		}

		if (group.isStaged() && !group.isStagedRemotely() &&
			!group.isStagedPortlet(PortletKeys.DOCUMENT_LIBRARY) &&
			ExportImportThreadLocal.isStagingInProcess()) {

			return content;
		}

		StringBuilder sb = new StringBuilder(content);

		String contextPath = _portal.getPathContext();

		String[] patterns = {
			contextPath.concat("/c/document_library/get_file?"),
			contextPath.concat("/documents/"),
			contextPath.concat("/image/image_gallery?")
		};

		int beginPos = -1;
		int endPos = content.length();

		while (true) {
			beginPos = StringUtil.lastIndexOfAny(content, patterns, endPos);

			if (beginPos == -1) {
				break;
			}

			Map<String, String[]> dlReferenceParameters =
				getDLReferenceParameters(
					portletDataContext.getScopeGroupId(), content,
					beginPos + contextPath.length(), endPos);

			FileEntry fileEntry = getFileEntry(dlReferenceParameters);

			if (fileEntry == null) {
				endPos = beginPos - 1;

				continue;
			}

			endPos = MapUtil.getInteger(dlReferenceParameters, "endPos");

			try {
				if (_isAlwaysIncludeReference(portletDataContext, fileEntry) &&
					exportReferencedContent && !fileEntry.isInTrash()) {

					StagedModelDataHandlerUtil.exportReferenceStagedModel(
						portletDataContext, stagedModel, fileEntry,
						PortletDataContext.REFERENCE_TYPE_DEPENDENCY);
				}
				else {
					Element entityElement =
						portletDataContext.getExportDataElement(stagedModel);

					String referenceType =
						PortletDataContext.REFERENCE_TYPE_DEPENDENCY;

					if (fileEntry.isInTrash()) {
						referenceType =
							PortletDataContext.
								REFERENCE_TYPE_DEPENDENCY_DISPOSABLE;
					}

					portletDataContext.addReferenceElement(
						stagedModel, entityElement, fileEntry, referenceType,
						true);
				}

				String path = ExportImportPathUtil.getModelPath(fileEntry);

				StringBundler exportedReferenceSB = new StringBundler(6);

				exportedReferenceSB.append("[$dl-reference=");
				exportedReferenceSB.append(path);
				exportedReferenceSB.append("$]");

				if (fileEntry.isInTrash()) {
					String originalReference = sb.substring(beginPos, endPos);

					exportedReferenceSB.append("[#dl-reference=");
					exportedReferenceSB.append(originalReference);
					exportedReferenceSB.append("#]");
				}

				sb.replace(beginPos, endPos, exportedReferenceSB.toString());

				int deleteTimestampParametersOffset = beginPos;

				if (fileEntry.isInTrash()) {
					deleteTimestampParametersOffset = sb.indexOf(
						"[#dl-reference=", beginPos);
				}

				deleteTimestampParameters(sb, deleteTimestampParametersOffset);
			}
			catch (Exception e) {
				if (_log.isDebugEnabled()) {
					_log.debug(e, e);
				}
				else if (_log.isWarnEnabled()) {
					StringBundler exceptionSB = new StringBundler(6);

					exceptionSB.append("Unable to process file entry ");
					exceptionSB.append(fileEntry.getFileEntryId());
					exceptionSB.append(" for staged model ");
					exceptionSB.append(stagedModel.getModelClassName());
					exceptionSB.append(" with primary key ");
					exceptionSB.append(stagedModel.getPrimaryKeyObj());

					_log.warn(exceptionSB.toString());
				}
			}

			endPos = beginPos - 1;
		}

		return sb.toString();
	}

	protected String replaceExportJournalArticleReferences(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			String content, boolean exportReferencedContent)
		throws Exception {

		Group group = _groupLocalService.fetchGroup(
			portletDataContext.getGroupId());

		if (group.isStagingGroup()) {
			group = group.getLiveGroup();
		}

		if (group.isStaged() && !group.isStagedRemotely() &&
			!group.isStagedPortlet(JournalPortletKeys.JOURNAL)) {

			return content;
		}

		Document document = null;

		try {
			document = SAXReaderUtil.read(content);
		}
		catch (DocumentException de) {
			if (_log.isDebugEnabled()) {
				_log.debug("Invalid content:\n" + content);
			}

			return content;
		}

		XPath xPath = SAXReaderUtil.createXPath(
			"//dynamic-element[@type='ddm-journal-article']");

		List<Node> ddmJournalArticleNodes = xPath.selectNodes(document);

		for (Node ddmJournalArticleNode : ddmJournalArticleNodes) {
			Element ddmJournalArticleElement = (Element)ddmJournalArticleNode;

			List<Element> dynamicContentElements =
				ddmJournalArticleElement.elements("dynamic-content");

			for (Element dynamicContentElement : dynamicContentElements) {
				String jsonData = dynamicContentElement.getStringValue();

				if (jsonData.equals(StringPool.BLANK)) {
					continue;
				}

				JSONObject jsonObject = _jsonFactory.createJSONObject(jsonData);

				long classPK = GetterUtil.getLong(jsonObject.get("classPK"));

				JournalArticle journalArticle =
					_journalArticleLocalService.fetchLatestArticle(classPK);

				if (journalArticle == null) {
					if (_log.isInfoEnabled()) {
						StringBundler messageSB = new StringBundler(7);

						messageSB.append("Staged model with class name ");
						messageSB.append(stagedModel.getModelClassName());
						messageSB.append(" and primary key ");
						messageSB.append(stagedModel.getPrimaryKeyObj());
						messageSB.append(" references missing journal ");
						messageSB.append("article with class primary key ");
						messageSB.append(classPK);

						_log.info(messageSB.toString());
					}

					continue;
				}

				String journalArticleReference =
					"[$journal-article-reference=" +
						journalArticle.getPrimaryKey() + "$]";

				if (_log.isDebugEnabled()) {
					_log.debug(
						StringBundler.concat(
							"Replacing ", jsonData, " with ",
							journalArticleReference));
				}

				dynamicContentElement.clearContent();

				dynamicContentElement.addCDATA(journalArticleReference);

				if (exportReferencedContent) {
					try {
						StagedModelDataHandlerUtil.exportReferenceStagedModel(
							portletDataContext, stagedModel, journalArticle,
							PortletDataContext.REFERENCE_TYPE_DEPENDENCY);
					}
					catch (Exception e) {
						if (_log.isDebugEnabled()) {
							StringBundler messageSB = new StringBundler(10);

							messageSB.append("Staged model with class name ");
							messageSB.append(stagedModel.getModelClassName());
							messageSB.append(" and primary key ");
							messageSB.append(stagedModel.getPrimaryKeyObj());
							messageSB.append(" references journal article ");
							messageSB.append("with class primary key ");
							messageSB.append(classPK);
							messageSB.append(" that could not be exported ");
							messageSB.append("due to ");
							messageSB.append(e);

							String errorMessage = messageSB.toString();

							if (Validator.isNotNull(e.getMessage())) {
								errorMessage = StringBundler.concat(
									errorMessage, ": ", e.getMessage());
							}

							_log.debug(errorMessage, e);
						}
					}
				}
				else {
					Element entityElement =
						portletDataContext.getExportDataElement(stagedModel);

					portletDataContext.addReferenceElement(
						stagedModel, entityElement, journalArticle,
						PortletDataContext.REFERENCE_TYPE_DEPENDENCY, true);
				}
			}
		}

		return document.asXML();
	}

	protected String replaceImportImageFileEntryIds(
			PortletDataContext portletDataContext, String content)
		throws Exception {

		Map<Long, Long> dlFileEntryIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				DLFileEntry.class);

		if (MapUtil.isNotEmpty(dlFileEntryIds)) {
			Document contentDocument = SAXReaderUtil.read(content);

			contentDocument = contentDocument.clone();

			for (Map.Entry entry : dlFileEntryIds.entrySet()) {
				StringBuffer sb = new StringBuffer(4);

				sb.append("//dynamic-element[@type='image']");
				sb.append("/dynamic-content[@fileEntryId='");
				sb.append(entry.getKey());
				sb.append("']");

				XPath xPath = SAXReaderUtil.createXPath(sb.toString());

				List<Node> imageNodes = xPath.selectNodes(contentDocument);

				for (Node imageNode : imageNodes) {
					Element imageElement = (Element)imageNode;

					List<Attribute> attributes = imageElement.attributes();

					for (Attribute attribute : attributes) {
						if (StringUtil.equals(
								attribute.getName(), "fileEntryId")) {

							attribute.setValue(
								String.valueOf(entry.getValue()));
						}
					}
				}
			}

			content = contentDocument.formattedString();
		}

		return content;
	}

	protected String replaceImportJournalArticleReferences(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			String content)
		throws Exception {

		List<Element> referenceElements =
			portletDataContext.getReferenceElements(
				stagedModel, JournalArticle.class);

		for (Element referenceElement : referenceElements) {
			long classPK = GetterUtil.getLong(
				referenceElement.attributeValue("class-pk"));

			Map<Long, Long> articlePrimaryKeys =
				(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
					JournalArticle.class + ".primaryKey");

			long articlePrimaryKey = MapUtil.getLong(
				articlePrimaryKeys, classPK, classPK);

			JournalArticle journalArticle =
				_journalArticleLocalService.fetchJournalArticle(
					articlePrimaryKey);

			if (journalArticle == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to get journal article with primary key " +
							articlePrimaryKey);
				}

				throw new NoSuchArticleException(
					"No JournalArticle exists with the key {id= " +
						articlePrimaryKey + "}");
			}
			else {
				String journalArticleReference =
					"[$journal-article-reference=" + classPK + "$]";

				JSONObject jsonObject = _jsonFactory.createJSONObject();

				jsonObject.put("className", JournalArticle.class.getName());
				jsonObject.put("classPK", journalArticle.getResourcePrimKey());

				content = StringUtil.replace(
					content, journalArticleReference, jsonObject.toString());
			}
		}

		return content;
	}

	protected void validateJournalArticleReferences(
			long groupId, String content)
		throws PortalException {

		List<Throwable> throwables = new ArrayList<>();

		try {
			Document document = SAXReaderUtil.read(content);

			XPath xPath = SAXReaderUtil.createXPath(
				"//dynamic-element[@type='ddm-journal-article']");

			List<Node> ddmJournalArticleNodes = xPath.selectNodes(document);

			for (Node ddmJournalArticleNode : ddmJournalArticleNodes) {
				Element ddmJournalArticleElement =
					(Element)ddmJournalArticleNode;

				List<Element> dynamicContentElements =
					ddmJournalArticleElement.elements("dynamic-content");

				for (Element dynamicContentElement : dynamicContentElements) {
					String json = dynamicContentElement.getStringValue();

					if (Validator.isNull(json)) {
						if (_log.isDebugEnabled()) {
							_log.debug(
								"No journal article reference is specified");
						}

						continue;
					}

					JSONObject jsonObject = null;

					try {
						jsonObject = _jsonFactory.createJSONObject(json);
					}
					catch (JSONException jsone) {
						_log.debug(jsone, jsone);

						continue;
					}

					if (!jsonObject.has("classPK")) {
						continue;
					}

					long classPK = GetterUtil.getLong(
						jsonObject.get("classPK"));

					JournalArticle journalArticle =
						_journalArticleLocalService.fetchLatestArticle(classPK);

					if (journalArticle == null) {
						if (ExportImportThreadLocal.isImportInProcess()) {
							if (_log.isDebugEnabled()) {
								StringBundler sb = new StringBundler(7);

								sb.append("An invalid web content article ");
								sb.append("was detected during import when ");
								sb.append("validating the content below. ");
								sb.append("This is not an error; it ");
								sb.append("typically means the web content ");
								sb.append("article was deleted.\n");
								sb.append(content);

								_log.debug(sb.toString());
							}

							return;
						}

						Throwable throwable = new NoSuchArticleException(
							StringBundler.concat(
								"No JournalArticle exists with the key ",
								"{resourcePrimKey=", String.valueOf(classPK),
								"}"));

						throwables.add(throwable);
					}
				}
			}
		}
		catch (DocumentException de) {
			if (_log.isDebugEnabled()) {
				_log.debug("Invalid content:\n" + content);
			}
		}

		if (!throwables.isEmpty()) {
			throw new PortalException(
				new BulkException(
					"Unable to validate journal article references",
					throwables));
		}
	}

	private boolean _isAlwaysIncludeReference(
		PortletDataContext portletDataContext,
		StagedModel referenceStagedModel) {

		Map<String, String[]> parameterMap =
			portletDataContext.getParameterMap();

		String[] referencedContentBehaviorArray = parameterMap.get(
			PortletDataHandlerControl.getNamespacedControlName(
				JournalPortletDataHandler.NAMESPACE,
				"referenced-content-behavior"));

		String referencedContentBehavior = "include-if-modified";

		if (!ArrayUtil.isEmpty(referencedContentBehaviorArray)) {
			referencedContentBehavior = referencedContentBehaviorArray[0];
		}

		if (referencedContentBehavior.equals("include-always") ||
			(referencedContentBehavior.equals("include-if-modified") &&
			 portletDataContext.isWithinDateRange(
				 referenceStagedModel.getModifiedDate()))) {

			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JournalArticleExportImportContentProcessor.class);

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private JournalArticleLocalService _journalArticleLocalService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Portal _portal;

}