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

package com.liferay.portal.security.audit.wiring.internal.servlet.filter;

import com.liferay.portal.kernel.audit.AuditRequestThreadLocal;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.BaseFilter;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.TryFilter;
import com.liferay.portal.kernel.util.WebKeys;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"after-filter=Session Max Allowed Filter", "servlet-context-name=",
		"servlet-filter-name=Audit Filter", "url-pattern=/*",
		"url-regex-ignore-pattern=^/html/.+\\.(css|gif|html|ico|jpg|js|png)(\\?.*)?$"
	},
	service = Filter.class
)
public class AuditFilter extends BaseFilter implements TryFilter {

	@Override
	public Object doFilterTry(
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		AuditRequestThreadLocal auditRequestThreadLocal =
			AuditRequestThreadLocal.getAuditThreadLocal();

		auditRequestThreadLocal.setClientHost(request.getRemoteHost());
		auditRequestThreadLocal.setClientIP(getRemoteAddr(request));
		auditRequestThreadLocal.setQueryString(request.getQueryString());

		HttpSession session = request.getSession();

		Long userId = (Long)session.getAttribute(WebKeys.USER_ID);

		if (userId != null) {
			auditRequestThreadLocal.setRealUserId(userId.longValue());
		}

		StringBuffer sb = request.getRequestURL();

		auditRequestThreadLocal.setRequestURL(sb.toString());

		auditRequestThreadLocal.setServerName(request.getServerName());
		auditRequestThreadLocal.setServerPort(request.getServerPort());
		auditRequestThreadLocal.setSessionID(session.getId());

		return null;
	}

	@Override
	protected Log getLog() {
		return _log;
	}

	protected String getRemoteAddr(HttpServletRequest request) {
		String remoteAddr = request.getHeader(HttpHeaders.X_FORWARDED_FOR);

		if (remoteAddr != null) {
			return remoteAddr;
		}

		return request.getRemoteAddr();
	}

	private static final Log _log = LogFactoryUtil.getLog(AuditFilter.class);

}