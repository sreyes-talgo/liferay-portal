/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portal.security.permission;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.util.PropsValues;

/**
 * <a href="PermissionsListFilterFactory.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 */
public class PermissionsListFilterFactory {

	public static PermissionsListFilter getInstance() {
		if (_permissionsListFilter == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Instantiate " + PropsValues.PERMISSIONS_LIST_FILTER);
			}

			ClassLoader classLoader = PortalClassLoaderUtil.getClassLoader();

			try {
				_permissionsListFilter =
					(PermissionsListFilter)classLoader.loadClass(
						PropsValues.PERMISSIONS_LIST_FILTER).newInstance();
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Return " + _permissionsListFilter.getClass().getName());
		}

		return _permissionsListFilter;
	}

	public static void setInstance(
		PermissionsListFilter permissionsListFilter) {

		if (_log.isDebugEnabled()) {
			_log.debug("Set " + permissionsListFilter.getClass().getName());
		}

		_permissionsListFilter = permissionsListFilter;
	}

	private static Log _log = LogFactoryUtil.getLog(
		PermissionsListFilterFactory.class);

	private static PermissionsListFilter _permissionsListFilter;

}