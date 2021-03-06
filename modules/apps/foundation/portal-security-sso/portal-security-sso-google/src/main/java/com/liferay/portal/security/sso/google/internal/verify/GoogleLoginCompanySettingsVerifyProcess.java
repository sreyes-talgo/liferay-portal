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

package com.liferay.portal.security.sso.google.internal.verify;

import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.util.PrefsProps;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.security.sso.google.constants.GoogleAuthorizationConfigurationKeys;
import com.liferay.portal.security.sso.google.constants.GoogleConstants;
import com.liferay.portal.security.sso.google.constants.LegacyGoogleLoginPropsKeys;
import com.liferay.portal.verify.BaseCompanySettingsVerifyProcess;
import com.liferay.portal.verify.VerifyProcess;

import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Stian Sigvartsen
 */
@Component(
	immediate = true,
	property = "verify.process.name=com.liferay.portal.security.sso.google",
	service = VerifyProcess.class
)
public class GoogleLoginCompanySettingsVerifyProcess
	extends BaseCompanySettingsVerifyProcess {

	@Override
	protected CompanyLocalService getCompanyLocalService() {
		return _companyLocalService;
	}

	@Override
	protected Set<String> getLegacyPropertyKeys() {
		return SetUtil.fromArray(LegacyGoogleLoginPropsKeys.GOOGLE_LOGIN_KEYS);
	}

	@Override
	protected String[][] getRenamePropertyKeysArray() {
		return new String[][] {
			new String[] {
				LegacyGoogleLoginPropsKeys.AUTH_ENABLED,
				GoogleAuthorizationConfigurationKeys.AUTH_ENABLED
			},
			new String[] {
				LegacyGoogleLoginPropsKeys.CLIENT_ID,
				GoogleAuthorizationConfigurationKeys.CLIENT_ID
			},
			new String[] {
				LegacyGoogleLoginPropsKeys.CLIENT_SECRET,
				GoogleAuthorizationConfigurationKeys.CLIENT_SECRET
			}
		};
	}

	@Override
	protected SettingsFactory getSettingsFactory() {
		return _settingsFactory;
	}

	@Override
	protected String getSettingsId() {
		return GoogleConstants.SERVICE_NAME;
	}

	@Reference
	private CompanyLocalService _companyLocalService;

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	@Reference
	private PrefsProps _prefsProps;

	@Reference
	private SettingsFactory _settingsFactory;

}