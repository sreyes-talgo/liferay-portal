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

<%@ include file="/facets/init.jsp" %>

<%
String fieldParamSelection = ParamUtil.getString(request, facet.getFieldId() + "selection", "0");

String fieldParamFrom = ParamUtil.getString(request, facet.getFieldId() + "from");
String fieldParamTo = ParamUtil.getString(request, facet.getFieldId() + "to");

Date fromDate = null;
Date toDate = null;

if (Validator.isNotNull(fieldParamFrom) || Validator.isNotNull(fieldParamTo)) {
	String delimiter = StringPool.FORWARD_SLASH;

	int dayPosition = 1;
	int monthPosition = 0;
	int yearPosition = 2;

	if (BrowserSnifferUtil.isMobile(request)) {
		delimiter = StringPool.DASH;

		dayPosition = 2;
		monthPosition = 1;
		yearPosition = 0;
	}

	String[] from = StringUtil.split(fieldParamFrom, delimiter);

	if (ArrayUtil.isNotEmpty(from) && (from.length == 3)) {
		int fromDay = GetterUtil.getInteger(from[dayPosition]);
		int fromMonth = GetterUtil.getInteger(from[monthPosition]) - 1;
		int fromYear = GetterUtil.getInteger(from[yearPosition]);

		fromDate = PortalUtil.getDate(fromMonth, fromDay, fromYear);
	}

	String[] to = StringUtil.split(fieldParamTo, delimiter);

	if (ArrayUtil.isNotEmpty(to) && (to.length == 3)) {
		int toDay = GetterUtil.getInteger(to[dayPosition]);
		int toMonth = GetterUtil.getInteger(to[monthPosition]) - 1;
		int toYear = GetterUtil.getInteger(to[yearPosition]);

		toDate = PortalUtil.getDate(toMonth, toDay, toYear);
	}
}

JSONArray rangesJSONArray = dataJSONObject.getJSONArray("ranges");

int index = 0;
%>

<div class="panel panel-default">
	<div class="panel-heading">
		<div class="panel-title">
			<liferay-ui:message key="time" />
		</div>
	</div>

	<div class="panel-body">
		<div class="<%= cssClass %>" data-facetFieldName="<%= HtmlUtil.escapeAttribute(facet.getFieldId()) %>" id="<%= randomNamespace %>facet">
			<aui:input autocomplete="off" name="<%= HtmlUtil.escapeAttribute(facet.getFieldId()) %>" type="hidden" value="<%= fieldParam %>" />
			<aui:input autocomplete="off" name='<%= HtmlUtil.escapeAttribute(facet.getFieldId()) + "selection" %>' type="hidden" value="<%= fieldParamSelection %>" />

			<aui:field-wrapper cssClass='<%= randomNamespace + "calendar calendar_" %>' label="" name="<%= HtmlUtil.escapeAttribute(facet.getFieldId()) %>">
				<ul class="list-unstyled modified">
					<li class="default facet-value">

						<%
						Map<String, Object> data = new HashMap<>();

						data.put("selection", 0);
						data.put("value", StringPool.BLANK);
						%>

						<aui:a cssClass='<%= Validator.isNull(fieldParam) ? "text-primary" : "text-default" %>' href="javascript:;">
							<liferay-ui:message key="<%= HtmlUtil.escape(facetConfiguration.getLabel()) %>" />
						</aui:a>
					</li>

					<%
					for (int i = 0; i < rangesJSONArray.length(); i++) {
						JSONObject rangesJSONObject = rangesJSONArray.getJSONObject(i);

						String label = HtmlUtil.escape(rangesJSONObject.getString("label"));
						String range = rangesJSONObject.getString("range");

						index = (i + 1);
					%>

						<li class="facet-value">

							<%
							String rangeCssClass = "text-default";

							if (fieldParamSelection.equals(String.valueOf(index))) {
								rangeCssClass = "text-primary";
							}

							data = new HashMap<>();

							data.put("selection", index);
							data.put("value", HtmlUtil.escape(range));
							%>

							<aui:a cssClass="<%= rangeCssClass %>" data="<%= data %>" href="javascript:;">
								<liferay-ui:message key="<%= label %>" />

								<%
								TermCollector termCollector = facetCollector.getTermCollector(range);
								%>

								<c:if test="<%= termCollector != null %>">
									<span class="frequency">(<%= termCollector.getFrequency() %>)</span>
								</c:if>
							</aui:a>
						</li>

					<%
					}
					%>

					<li class="facet-value">

						<%
						String customRangeCssClass = randomNamespace + "custom-range-toggle";

						if (fieldParamSelection.equals(String.valueOf(index + 1))) {
							customRangeCssClass += " text-primary";
						}
						else {
							customRangeCssClass += " text-default";
						}

						TermCollector termCollector = null;

						if (fieldParamSelection.equals(String.valueOf(index + 1))) {
							termCollector = facetCollector.getTermCollector(fieldParam);
						}
						%>

						<aui:a cssClass="<%= customRangeCssClass %>" href="javascript:;">
							<liferay-ui:message key="custom-range" />&hellip;

							<c:if test="<%= termCollector != null %>">
								<span class="frequency">(<%= termCollector.getFrequency() %>)</span>
							</c:if>
						</aui:a>
					</li>

					<%
					Calendar fromCalendar = CalendarFactoryUtil.getCalendar(timeZone, locale);

					if (Validator.isNotNull(fromDate)) {
						fromCalendar.setTime(fromDate);
					}
					else {
						fromCalendar.add(Calendar.DATE, -1);
					}

					Calendar toCalendar = CalendarFactoryUtil.getCalendar(timeZone, locale);

					if (Validator.isNotNull(toDate)) {
						toCalendar.setTime(toDate);
					}
					%>

					<div class="<%= !fieldParamSelection.equals(String.valueOf(index + 1)) ? "hide" : StringPool.BLANK %> modified-custom-range" id="<%= randomNamespace %>customRange">
						<div class="col-md-6" id="<%= randomNamespace %>customRangeFrom">
							<aui:field-wrapper label="from">
								<liferay-ui:input-date
									dayParam='<%= HtmlUtil.escapeJS(facet.getFieldId()) + "dayFrom" %>'
									dayValue="<%= fromCalendar.get(Calendar.DATE) %>"
									disabled="<%= false %>"
									firstDayOfWeek="<%= fromCalendar.getFirstDayOfWeek() - 1 %>"
									monthParam='<%= HtmlUtil.escapeJS(facet.getFieldId()) + "monthFrom" %>'
									monthValue="<%= fromCalendar.get(Calendar.MONTH) %>"
									name='<%= HtmlUtil.escapeJS(facet.getFieldId()) + "from" %>'
									yearParam='<%= HtmlUtil.escapeJS(facet.getFieldId()) + "yearFrom" %>'
									yearValue="<%= fromCalendar.get(Calendar.YEAR) %>"
								/>
							</aui:field-wrapper>
						</div>

						<div class="col-md-6" id="<%= randomNamespace %>customRangeTo">
							<aui:field-wrapper label="to">
								<liferay-ui:input-date
									dayParam='<%= HtmlUtil.escapeJS(facet.getFieldId()) + "dayTo" %>'
									dayValue="<%= toCalendar.get(Calendar.DATE) %>"
									disabled="<%= false %>"
									firstDayOfWeek="<%= toCalendar.getFirstDayOfWeek() - 1 %>"
									monthParam='<%= HtmlUtil.escapeJS(facet.getFieldId()) + "monthTo" %>'
									monthValue="<%= toCalendar.get(Calendar.MONTH) %>"
									name='<%= HtmlUtil.escapeJS(facet.getFieldId()) + "to" %>'
									yearParam='<%= HtmlUtil.escapeJS(facet.getFieldId()) + "yearTo" %>'
									yearValue="<%= toCalendar.get(Calendar.YEAR) %>"
								/>
							</aui:field-wrapper>
						</div>

						<%
						String taglibSearchCustomRange = "window['" + renderResponse.getNamespace() + HtmlUtil.escapeJS(facet.getFieldId()) + "searchCustomRange'](" + (index + 1) + ");";
						%>

						<aui:button disabled="<%= toCalendar.getTimeInMillis() < fromCalendar.getTimeInMillis() %>" name="searchCustomRangeButton" onClick="<%= taglibSearchCustomRange %>" value="search" />
					</div>
				</ul>
			</aui:field-wrapper>
		</div>
	</div>
</div>

<aui:script>
	var form = AUI.$(document.<portlet:namespace />fm);

	form.fm('<%= HtmlUtil.escapeJS(facet.getFieldId()) %>dayFrom').prop('disabled', true);
	form.fm('<%= HtmlUtil.escapeJS(facet.getFieldId()) %>monthFrom').prop('disabled', true);
	form.fm('<%= HtmlUtil.escapeJS(facet.getFieldId()) %>yearFrom').prop('disabled', true);

	form.fm('<%= HtmlUtil.escapeJS(facet.getFieldId()) %>dayTo').prop('disabled', true);
	form.fm('<%= HtmlUtil.escapeJS(facet.getFieldId()) %>monthTo').prop('disabled', true);
	form.fm('<%= HtmlUtil.escapeJS(facet.getFieldId()) %>yearTo').prop('disabled', true);

	function <portlet:namespace /><%= HtmlUtil.escapeJS(facet.getFieldId()) %>searchCustomRange(selection) {
		var A = AUI();
		var Lang = A.Lang;
		var LString = Lang.String;

		var dayFrom = form.fm('<%= HtmlUtil.escapeJS(facet.getFieldId()) %>dayFrom').val();
		var monthFrom = Lang.toInt(form.fm('<%= HtmlUtil.escapeJS(facet.getFieldId()) %>monthFrom').val()) + 1;
		var yearFrom = form.fm('<%= HtmlUtil.escapeJS(facet.getFieldId()) %>yearFrom').val();

		var dayTo = form.fm('<%= HtmlUtil.escapeJS(facet.getFieldId()) %>dayTo').val();
		var monthTo = Lang.toInt(form.fm('<%= HtmlUtil.escapeJS(facet.getFieldId()) %>monthTo').val()) + 1;
		var yearTo = form.fm('<%= HtmlUtil.escapeJS(facet.getFieldId()) %>yearTo').val();

		var range = '[' + yearFrom + LString.padNumber(monthFrom, 2) + LString.padNumber(dayFrom, 2) + '000000 TO ' + yearTo + LString.padNumber(monthTo, 2) + LString.padNumber(dayTo, 2) + '235959]';

		form.fm('<%= HtmlUtil.escapeJS(facet.getFieldId()) %>').val(range);
		form.fm('<%= HtmlUtil.escapeJS(facet.getFieldId()) %>selection').val(selection);

		submitForm(form);
	}
</aui:script>

<aui:script use="aui-form-validator">
	var Util = Liferay.Util;

	var customRangeFrom = Liferay.component('<%= renderResponse.getNamespace() %>modifiedfromDatePicker');
	var customRangeTo = Liferay.component('<%= renderResponse.getNamespace() %>modifiedtoDatePicker');
	var searchButton = A.one('#<portlet:namespace />searchCustomRangeButton');

	var preventKeyboardDateChange = function(event) {
		if (!event.isKey('TAB')) {
			event.preventDefault();
		}
	};

	A.one('#<portlet:namespace /><%= HtmlUtil.escapeJS(facet.getFieldId()) %>from').on('keydown', preventKeyboardDateChange);
	A.one('#<portlet:namespace /><%= HtmlUtil.escapeJS(facet.getFieldId()) %>to').on('keydown', preventKeyboardDateChange);

	var DEFAULTS_FORM_VALIDATOR = A.config.FormValidator;

	A.mix(
		DEFAULTS_FORM_VALIDATOR.STRINGS,
		{
			<portlet:namespace />dateRange: '<%= UnicodeLanguageUtil.get(request, "search-custom-range-invalid-date-range") %>'
		},
		true
	);

	A.mix(
		DEFAULTS_FORM_VALIDATOR.RULES,
		{
			<portlet:namespace />dateRange: function(val, fieldNode, ruleValue) {
				return A.Date.isGreaterOrEqual(customRangeTo.getDate(), customRangeFrom.getDate());
			}
		},
		true
	);

	var customRangeValidator = new A.FormValidator(
		{
			boundingBox: document.<portlet:namespace />fm,
			fieldContainer: 'div',
			on: {
				errorField: function(event) {
					Util.toggleDisabled(searchButton, true);
				},
				validField: function(event) {
					Util.toggleDisabled(searchButton, false);
				}
			},
			rules: {
				'<portlet:namespace /><%= HtmlUtil.escapeJS(facet.getFieldId()) %>from': {
					<portlet:namespace />dateRange: true
				},
				'<portlet:namespace /><%= HtmlUtil.escapeJS(facet.getFieldId()) %>to': {
					<portlet:namespace />dateRange: true
				}
			}
		}
	);

	var onRangeSelectionChange = function(event) {
		customRangeValidator.validate();
	};

	customRangeFrom.on('selectionChange', onRangeSelectionChange);
	customRangeTo.on('selectionChange', onRangeSelectionChange);

	A.one('.<%= randomNamespace %>custom-range-toggle').on(
		'click',
		function(event) {
			event.halt();

			A.one('#<%= randomNamespace + "customRange" %>').toggle();
		}
	);
</aui:script>