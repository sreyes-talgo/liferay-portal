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

package com.liferay.dynamic.data.mapping.type.options.internal;

import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * @author Marcellus Tavares
 */
public class OptionsDDMFormFieldContextHelper {

	public OptionsDDMFormFieldContextHelper(
		JSONFactory jsonFactory, DDMFormFieldOptions ddmFormFieldOptions,
		String value, LocalizedValue predefinedValue, Locale locale) {

		_jsonFactory = jsonFactory;
		_ddmFormFieldOptions = ddmFormFieldOptions;
		_locale = locale;

		_value = toString(value);
		_predefinedValue = toString(predefinedValue.getString(locale));
	}

	public List<Object> getOptions() {
		List<Object> options = new ArrayList<>();

		for (String optionValue : _ddmFormFieldOptions.getOptionsValues()) {
			Map<String, String> optionMap = new HashMap<>();

			LocalizedValue optionLabel = _ddmFormFieldOptions.getOptionLabels(
				optionValue);

			optionMap.put("label", optionLabel.getString(_locale));

			optionMap.put(
				"status",
				isChecked(optionValue) ? "checked" : StringPool.BLANK);
			optionMap.put("value", optionValue);

			options.add(optionMap);
		}

		return options;
	}

	protected boolean isChecked(String optionValue) {
		if (Validator.isNull(_value)) {
			return Objects.equals(_predefinedValue, optionValue);
		}

		return Objects.equals(_value, optionValue);
	}

	protected String toString(String value) {
		if (Validator.isNull(value)) {
			return StringPool.BLANK;
		}

		try {
			JSONArray jsonArray = _jsonFactory.createJSONArray(value);

			return jsonArray.getString(0);
		}
		catch (JSONException jsone) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unable to parse JSON array", jsone);
			}

			return StringPool.BLANK;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OptionsDDMFormFieldContextHelper.class);

	private final DDMFormFieldOptions _ddmFormFieldOptions;
	private final JSONFactory _jsonFactory;
	private final Locale _locale;
	private final String _predefinedValue;
	private final String _value;

}