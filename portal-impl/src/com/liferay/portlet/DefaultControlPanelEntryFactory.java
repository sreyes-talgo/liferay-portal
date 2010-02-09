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

package com.liferay.portlet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.util.PropsValues;

/**
 * <a href="DefaultControlPanelEntryFactory.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 */
public class DefaultControlPanelEntryFactory {

	public static ControlPanelEntry getInstance() {
		if (_controlPanelEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Instantiate " +
						PropsValues.CONTROL_PANEL_DEFAULT_ENTRY_CLASS);
			}

			ClassLoader classLoader = PortalClassLoaderUtil.getClassLoader();

			try {
				_controlPanelEntry = (ControlPanelEntry)classLoader.loadClass(
					PropsValues.CONTROL_PANEL_DEFAULT_ENTRY_CLASS).
						newInstance();
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Return " + _controlPanelEntry.getClass().getName());
		}

		return _controlPanelEntry;
	}

	public static void setInstance(ControlPanelEntry controlPanelEntryFactory) {
		if (_log.isDebugEnabled()) {
			_log.debug("Set " + controlPanelEntryFactory.getClass().getName());
		}

		_controlPanelEntry = controlPanelEntryFactory;
	}

	private static Log _log = LogFactoryUtil.getLog(
		DefaultControlPanelEntryFactory.class);

	private static ControlPanelEntry _controlPanelEntry = null;

}