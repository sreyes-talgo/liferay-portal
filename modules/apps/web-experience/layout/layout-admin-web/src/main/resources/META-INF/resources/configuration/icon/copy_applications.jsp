<%--
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
--%>

<%@ include file="/init.jsp" %>

<liferay-ui:icon
	id="copyApplications"
	message="copy-applications"
	url="javascript:;"
/>

<%
Group selGroup = (Group)request.getAttribute(WebKeys.GROUP);

PortletURL redirectURL = layoutsAdminDisplayContext.getRedirectURL();
%>

<portlet:actionURL name="copyApplications" var="copyApplicationsURL">
	<portlet:param name="mvcPath" value="/view.jsp" />
</portlet:actionURL>

<aui:form action='<%= HttpUtil.addParameter(copyApplicationsURL, "refererPlid", plid) %>' name="copyApplicationsFm">
	<aui:input name="redirect" type="hidden" value='<%= HttpUtil.addParameter(redirectURL.toString(), liferayPortletResponse.getNamespace() + "selPlid", layoutsAdminDisplayContext.getSelPlid()) %>' />
	<aui:input name="groupId" type="hidden" value="<%= selGroup.getGroupId() %>" />
	<aui:input name="selPlid" type="hidden" value="<%= layoutsAdminDisplayContext.getSelPlid() %>" />
	<aui:input name="privateLayout" type="hidden" value="<%= layoutsAdminDisplayContext.isPrivateLayout() %>" />
	<aui:input name="layoutId" type="hidden" value="<%= layoutsAdminDisplayContext.getLayoutId() %>" />
</aui:form>

<%
Layout selLayout = layoutsAdminDisplayContext.getSelLayout();
%>

<div class="hide" id="<portlet:namespace />copyPortletsFromPage">
	<p>
		<c:if test="<%= selLayout != null %>">
			<liferay-ui:message arguments="<%= HtmlUtil.escape(selLayout.getName(locale)) %>" key="the-applications-in-page-x-will-be-replaced-with-the-ones-in-the-page-you-select-below" translateArguments="<%= false %>" />
		</c:if>
	</p>

	<aui:select label="copy-from-page" name="copyLayoutId">

		<%
		List<LayoutDescription> layoutDescriptions = (List<LayoutDescription>)request.getAttribute(WebKeys.LAYOUT_DESCRIPTIONS);

		for (LayoutDescription layoutDescription : layoutDescriptions) {
			Layout layoutDescriptionLayout = LayoutLocalServiceUtil.fetchLayout(layoutDescription.getPlid());

			if (layoutDescriptionLayout != null) {
		%>

				<aui:option disabled="<%= (selLayout != null) && (selLayout.getPlid() == layoutDescriptionLayout.getPlid()) %>" label="<%= layoutDescription.getDisplayName() %>" value="<%= layoutDescriptionLayout.getLayoutId() %>" />

		<%
			}
		}
		%>

	</aui:select>

	<aui:button-row>
		<aui:button name="copySubmitButton" value="copy" />
	</aui:button-row>
</div>

<aui:script use="liferay-util-window">
	A.one('#<portlet:namespace />copyApplications').on(
		'click',
		function() {
			var content = A.one('#<portlet:namespace />copyPortletsFromPage');

			var popUp = Liferay.Util.Window.getWindow(
				{
					dialog: {
						bodyContent: content.show()
					},
					title: '<%= UnicodeLanguageUtil.get(request, "copy-applications") %>'
				}
			);

			popUp.show();

			var submitButton = popUp.get('contentBox').one('#<portlet:namespace />copySubmitButton');

			if (submitButton) {
				submitButton.on(
					'click',
					function(event) {
						popUp.hide();

						var form = A.one('#<portlet:namespace />copyApplicationsFm');

						if (form) {
							form.append(content);

							submitForm(form);
						}
					}
				);
			}
		}
	);
</aui:script>