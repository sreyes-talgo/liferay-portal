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

package com.liferay.adaptive.media.blogs.web.internal.counter.test;

import com.liferay.adaptive.media.image.counter.AMImageCounter;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.blogs.kernel.model.BlogsEntry;
import com.liferay.blogs.kernel.service.BlogsEntryLocalServiceUtil;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.CompanyTestUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;

import java.util.Collection;
import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Sergio González
 */
@RunWith(Arquillian.class)
@Sync
public class BlogsAMImageCounterTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_amImageCounter = _getService(
			AMImageCounter.class, "(adaptive.media.key=blogs)");

		_company1 = CompanyTestUtil.addCompany();

		_user1 = UserTestUtil.getAdminUser(_company1.getCompanyId());

		_group1 = GroupTestUtil.addGroup(
			_company1.getCompanyId(), _user1.getUserId(),
			GroupConstants.DEFAULT_PARENT_GROUP_ID);

		_company2 = CompanyTestUtil.addCompany();

		_user2 = UserTestUtil.getAdminUser(_company2.getCompanyId());

		_group2 = GroupTestUtil.addGroup(
			_company2.getCompanyId(), _user2.getUserId(),
			GroupConstants.DEFAULT_PARENT_GROUP_ID);
	}

	@Test
	public void testBlogsAMImageCounterOnlyCountsBlogsImages()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group1, _user1.getUserId());

		DLAppLocalServiceUtil.addFileEntry(
			_user1.getUserId(), _group1.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString() + ".jpg", ContentTypes.IMAGE_JPEG,
			_getImageBytes(), serviceContext);

		BlogsEntry blogsEntry = BlogsEntryLocalServiceUtil.addEntry(
			_user1.getUserId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), new Date(), serviceContext);

		Assert.assertEquals(
			0,
			_amImageCounter.countExpectedAMImageEntries(
				_company1.getCompanyId()));

		ImageSelector imageSelector = new ImageSelector(
			_getImageBytes(), RandomTestUtil.randomString() + ".jpg",
			ContentTypes.IMAGE_JPEG, IMAGE_CROP_REGION);

		BlogsEntryLocalServiceUtil.addCoverImage(
			blogsEntry.getEntryId(), imageSelector);

		Assert.assertEquals(
			1,
			_amImageCounter.countExpectedAMImageEntries(
				_company1.getCompanyId()));
	}

	@Test
	public void testBlogsAMImageCounterOnlyCountsBlogsImagesPerCompany()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group1, _user1.getUserId());

		DLAppLocalServiceUtil.addFileEntry(
			_user1.getUserId(), _group1.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString() + ".jpg", ContentTypes.IMAGE_JPEG,
			_getImageBytes(), serviceContext);

		BlogsEntry blogsEntry = BlogsEntryLocalServiceUtil.addEntry(
			_user1.getUserId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), new Date(), serviceContext);

		Assert.assertEquals(
			0,
			_amImageCounter.countExpectedAMImageEntries(
				_company1.getCompanyId()));
		Assert.assertEquals(
			0,
			_amImageCounter.countExpectedAMImageEntries(
				_company2.getCompanyId()));

		ImageSelector imageSelector = new ImageSelector(
			_getImageBytes(), RandomTestUtil.randomString() + ".jpg",
			ContentTypes.IMAGE_JPEG, IMAGE_CROP_REGION);

		BlogsEntryLocalServiceUtil.addCoverImage(
			blogsEntry.getEntryId(), imageSelector);

		Assert.assertEquals(
			1,
			_amImageCounter.countExpectedAMImageEntries(
				_company1.getCompanyId()));
		Assert.assertEquals(
			0,
			_amImageCounter.countExpectedAMImageEntries(
				_company2.getCompanyId()));
	}

	protected static final String IMAGE_CROP_REGION =
		"{\"height\": 0, \"width\": 00, \"x\": 0, \"y\": 0}";

	private byte[] _getImageBytes() throws Exception {
		return FileUtil.getBytes(
			BlogsAMImageCounterTest.class,
			"/com/liferay/adaptive/media/blogs/web/internal/counter/test" +
				"/dependencies/image.jpg");
	}

	private <T> T _getService(Class<T> clazz, String filter) {
		try {
			Registry registry = RegistryUtil.getRegistry();

			Collection<T> services = registry.getServices(clazz, filter);

			return services.iterator().next();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private AMImageCounter _amImageCounter;

	@DeleteAfterTestRun
	private Company _company1;

	@DeleteAfterTestRun
	private Company _company2;

	private Group _group1;
	private Group _group2;
	private User _user1;
	private User _user2;

}