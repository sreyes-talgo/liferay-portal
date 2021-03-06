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

package com.liferay.wsrp.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import com.liferay.wsrp.model.WSRPConsumerPortlet;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the wsrp consumer portlet service. This utility wraps {@link com.liferay.wsrp.service.persistence.impl.WSRPConsumerPortletPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see WSRPConsumerPortletPersistence
 * @see com.liferay.wsrp.service.persistence.impl.WSRPConsumerPortletPersistenceImpl
 * @generated
 */
@ProviderType
public class WSRPConsumerPortletUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(WSRPConsumerPortlet wsrpConsumerPortlet) {
		getPersistence().clearCache(wsrpConsumerPortlet);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<WSRPConsumerPortlet> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<WSRPConsumerPortlet> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<WSRPConsumerPortlet> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<WSRPConsumerPortlet> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static WSRPConsumerPortlet update(
		WSRPConsumerPortlet wsrpConsumerPortlet) {
		return getPersistence().update(wsrpConsumerPortlet);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static WSRPConsumerPortlet update(
		WSRPConsumerPortlet wsrpConsumerPortlet, ServiceContext serviceContext) {
		return getPersistence().update(wsrpConsumerPortlet, serviceContext);
	}

	/**
	* Returns all the wsrp consumer portlets where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching wsrp consumer portlets
	*/
	public static List<WSRPConsumerPortlet> findByUuid(String uuid) {
		return getPersistence().findByUuid(uuid);
	}

	/**
	* Returns a range of all the wsrp consumer portlets where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WSRPConsumerPortletModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of wsrp consumer portlets
	* @param end the upper bound of the range of wsrp consumer portlets (not inclusive)
	* @return the range of matching wsrp consumer portlets
	*/
	public static List<WSRPConsumerPortlet> findByUuid(String uuid, int start,
		int end) {
		return getPersistence().findByUuid(uuid, start, end);
	}

	/**
	* Returns an ordered range of all the wsrp consumer portlets where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WSRPConsumerPortletModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of wsrp consumer portlets
	* @param end the upper bound of the range of wsrp consumer portlets (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching wsrp consumer portlets
	*/
	public static List<WSRPConsumerPortlet> findByUuid(String uuid, int start,
		int end, OrderByComparator<WSRPConsumerPortlet> orderByComparator) {
		return getPersistence().findByUuid(uuid, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the wsrp consumer portlets where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WSRPConsumerPortletModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of wsrp consumer portlets
	* @param end the upper bound of the range of wsrp consumer portlets (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching wsrp consumer portlets
	*/
	public static List<WSRPConsumerPortlet> findByUuid(String uuid, int start,
		int end, OrderByComparator<WSRPConsumerPortlet> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid(uuid, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first wsrp consumer portlet in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching wsrp consumer portlet
	* @throws NoSuchConsumerPortletException if a matching wsrp consumer portlet could not be found
	*/
	public static WSRPConsumerPortlet findByUuid_First(String uuid,
		OrderByComparator<WSRPConsumerPortlet> orderByComparator)
		throws com.liferay.wsrp.exception.NoSuchConsumerPortletException {
		return getPersistence().findByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the first wsrp consumer portlet in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching wsrp consumer portlet, or <code>null</code> if a matching wsrp consumer portlet could not be found
	*/
	public static WSRPConsumerPortlet fetchByUuid_First(String uuid,
		OrderByComparator<WSRPConsumerPortlet> orderByComparator) {
		return getPersistence().fetchByUuid_First(uuid, orderByComparator);
	}

	/**
	* Returns the last wsrp consumer portlet in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching wsrp consumer portlet
	* @throws NoSuchConsumerPortletException if a matching wsrp consumer portlet could not be found
	*/
	public static WSRPConsumerPortlet findByUuid_Last(String uuid,
		OrderByComparator<WSRPConsumerPortlet> orderByComparator)
		throws com.liferay.wsrp.exception.NoSuchConsumerPortletException {
		return getPersistence().findByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the last wsrp consumer portlet in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching wsrp consumer portlet, or <code>null</code> if a matching wsrp consumer portlet could not be found
	*/
	public static WSRPConsumerPortlet fetchByUuid_Last(String uuid,
		OrderByComparator<WSRPConsumerPortlet> orderByComparator) {
		return getPersistence().fetchByUuid_Last(uuid, orderByComparator);
	}

	/**
	* Returns the wsrp consumer portlets before and after the current wsrp consumer portlet in the ordered set where uuid = &#63;.
	*
	* @param wsrpConsumerPortletId the primary key of the current wsrp consumer portlet
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next wsrp consumer portlet
	* @throws NoSuchConsumerPortletException if a wsrp consumer portlet with the primary key could not be found
	*/
	public static WSRPConsumerPortlet[] findByUuid_PrevAndNext(
		long wsrpConsumerPortletId, String uuid,
		OrderByComparator<WSRPConsumerPortlet> orderByComparator)
		throws com.liferay.wsrp.exception.NoSuchConsumerPortletException {
		return getPersistence()
				   .findByUuid_PrevAndNext(wsrpConsumerPortletId, uuid,
			orderByComparator);
	}

	/**
	* Removes all the wsrp consumer portlets where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public static void removeByUuid(String uuid) {
		getPersistence().removeByUuid(uuid);
	}

	/**
	* Returns the number of wsrp consumer portlets where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching wsrp consumer portlets
	*/
	public static int countByUuid(String uuid) {
		return getPersistence().countByUuid(uuid);
	}

	/**
	* Returns all the wsrp consumer portlets where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching wsrp consumer portlets
	*/
	public static List<WSRPConsumerPortlet> findByUuid_C(String uuid,
		long companyId) {
		return getPersistence().findByUuid_C(uuid, companyId);
	}

	/**
	* Returns a range of all the wsrp consumer portlets where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WSRPConsumerPortletModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of wsrp consumer portlets
	* @param end the upper bound of the range of wsrp consumer portlets (not inclusive)
	* @return the range of matching wsrp consumer portlets
	*/
	public static List<WSRPConsumerPortlet> findByUuid_C(String uuid,
		long companyId, int start, int end) {
		return getPersistence().findByUuid_C(uuid, companyId, start, end);
	}

	/**
	* Returns an ordered range of all the wsrp consumer portlets where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WSRPConsumerPortletModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of wsrp consumer portlets
	* @param end the upper bound of the range of wsrp consumer portlets (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching wsrp consumer portlets
	*/
	public static List<WSRPConsumerPortlet> findByUuid_C(String uuid,
		long companyId, int start, int end,
		OrderByComparator<WSRPConsumerPortlet> orderByComparator) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the wsrp consumer portlets where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WSRPConsumerPortletModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of wsrp consumer portlets
	* @param end the upper bound of the range of wsrp consumer portlets (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching wsrp consumer portlets
	*/
	public static List<WSRPConsumerPortlet> findByUuid_C(String uuid,
		long companyId, int start, int end,
		OrderByComparator<WSRPConsumerPortlet> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByUuid_C(uuid, companyId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first wsrp consumer portlet in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching wsrp consumer portlet
	* @throws NoSuchConsumerPortletException if a matching wsrp consumer portlet could not be found
	*/
	public static WSRPConsumerPortlet findByUuid_C_First(String uuid,
		long companyId, OrderByComparator<WSRPConsumerPortlet> orderByComparator)
		throws com.liferay.wsrp.exception.NoSuchConsumerPortletException {
		return getPersistence()
				   .findByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the first wsrp consumer portlet in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching wsrp consumer portlet, or <code>null</code> if a matching wsrp consumer portlet could not be found
	*/
	public static WSRPConsumerPortlet fetchByUuid_C_First(String uuid,
		long companyId, OrderByComparator<WSRPConsumerPortlet> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_First(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last wsrp consumer portlet in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching wsrp consumer portlet
	* @throws NoSuchConsumerPortletException if a matching wsrp consumer portlet could not be found
	*/
	public static WSRPConsumerPortlet findByUuid_C_Last(String uuid,
		long companyId, OrderByComparator<WSRPConsumerPortlet> orderByComparator)
		throws com.liferay.wsrp.exception.NoSuchConsumerPortletException {
		return getPersistence()
				   .findByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the last wsrp consumer portlet in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching wsrp consumer portlet, or <code>null</code> if a matching wsrp consumer portlet could not be found
	*/
	public static WSRPConsumerPortlet fetchByUuid_C_Last(String uuid,
		long companyId, OrderByComparator<WSRPConsumerPortlet> orderByComparator) {
		return getPersistence()
				   .fetchByUuid_C_Last(uuid, companyId, orderByComparator);
	}

	/**
	* Returns the wsrp consumer portlets before and after the current wsrp consumer portlet in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param wsrpConsumerPortletId the primary key of the current wsrp consumer portlet
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next wsrp consumer portlet
	* @throws NoSuchConsumerPortletException if a wsrp consumer portlet with the primary key could not be found
	*/
	public static WSRPConsumerPortlet[] findByUuid_C_PrevAndNext(
		long wsrpConsumerPortletId, String uuid, long companyId,
		OrderByComparator<WSRPConsumerPortlet> orderByComparator)
		throws com.liferay.wsrp.exception.NoSuchConsumerPortletException {
		return getPersistence()
				   .findByUuid_C_PrevAndNext(wsrpConsumerPortletId, uuid,
			companyId, orderByComparator);
	}

	/**
	* Removes all the wsrp consumer portlets where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public static void removeByUuid_C(String uuid, long companyId) {
		getPersistence().removeByUuid_C(uuid, companyId);
	}

	/**
	* Returns the number of wsrp consumer portlets where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching wsrp consumer portlets
	*/
	public static int countByUuid_C(String uuid, long companyId) {
		return getPersistence().countByUuid_C(uuid, companyId);
	}

	/**
	* Returns all the wsrp consumer portlets where wsrpConsumerId = &#63;.
	*
	* @param wsrpConsumerId the wsrp consumer ID
	* @return the matching wsrp consumer portlets
	*/
	public static List<WSRPConsumerPortlet> findByWsrpConsumerId(
		long wsrpConsumerId) {
		return getPersistence().findByWsrpConsumerId(wsrpConsumerId);
	}

	/**
	* Returns a range of all the wsrp consumer portlets where wsrpConsumerId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WSRPConsumerPortletModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param wsrpConsumerId the wsrp consumer ID
	* @param start the lower bound of the range of wsrp consumer portlets
	* @param end the upper bound of the range of wsrp consumer portlets (not inclusive)
	* @return the range of matching wsrp consumer portlets
	*/
	public static List<WSRPConsumerPortlet> findByWsrpConsumerId(
		long wsrpConsumerId, int start, int end) {
		return getPersistence().findByWsrpConsumerId(wsrpConsumerId, start, end);
	}

	/**
	* Returns an ordered range of all the wsrp consumer portlets where wsrpConsumerId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WSRPConsumerPortletModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param wsrpConsumerId the wsrp consumer ID
	* @param start the lower bound of the range of wsrp consumer portlets
	* @param end the upper bound of the range of wsrp consumer portlets (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching wsrp consumer portlets
	*/
	public static List<WSRPConsumerPortlet> findByWsrpConsumerId(
		long wsrpConsumerId, int start, int end,
		OrderByComparator<WSRPConsumerPortlet> orderByComparator) {
		return getPersistence()
				   .findByWsrpConsumerId(wsrpConsumerId, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the wsrp consumer portlets where wsrpConsumerId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WSRPConsumerPortletModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param wsrpConsumerId the wsrp consumer ID
	* @param start the lower bound of the range of wsrp consumer portlets
	* @param end the upper bound of the range of wsrp consumer portlets (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching wsrp consumer portlets
	*/
	public static List<WSRPConsumerPortlet> findByWsrpConsumerId(
		long wsrpConsumerId, int start, int end,
		OrderByComparator<WSRPConsumerPortlet> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByWsrpConsumerId(wsrpConsumerId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first wsrp consumer portlet in the ordered set where wsrpConsumerId = &#63;.
	*
	* @param wsrpConsumerId the wsrp consumer ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching wsrp consumer portlet
	* @throws NoSuchConsumerPortletException if a matching wsrp consumer portlet could not be found
	*/
	public static WSRPConsumerPortlet findByWsrpConsumerId_First(
		long wsrpConsumerId,
		OrderByComparator<WSRPConsumerPortlet> orderByComparator)
		throws com.liferay.wsrp.exception.NoSuchConsumerPortletException {
		return getPersistence()
				   .findByWsrpConsumerId_First(wsrpConsumerId, orderByComparator);
	}

	/**
	* Returns the first wsrp consumer portlet in the ordered set where wsrpConsumerId = &#63;.
	*
	* @param wsrpConsumerId the wsrp consumer ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching wsrp consumer portlet, or <code>null</code> if a matching wsrp consumer portlet could not be found
	*/
	public static WSRPConsumerPortlet fetchByWsrpConsumerId_First(
		long wsrpConsumerId,
		OrderByComparator<WSRPConsumerPortlet> orderByComparator) {
		return getPersistence()
				   .fetchByWsrpConsumerId_First(wsrpConsumerId,
			orderByComparator);
	}

	/**
	* Returns the last wsrp consumer portlet in the ordered set where wsrpConsumerId = &#63;.
	*
	* @param wsrpConsumerId the wsrp consumer ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching wsrp consumer portlet
	* @throws NoSuchConsumerPortletException if a matching wsrp consumer portlet could not be found
	*/
	public static WSRPConsumerPortlet findByWsrpConsumerId_Last(
		long wsrpConsumerId,
		OrderByComparator<WSRPConsumerPortlet> orderByComparator)
		throws com.liferay.wsrp.exception.NoSuchConsumerPortletException {
		return getPersistence()
				   .findByWsrpConsumerId_Last(wsrpConsumerId, orderByComparator);
	}

	/**
	* Returns the last wsrp consumer portlet in the ordered set where wsrpConsumerId = &#63;.
	*
	* @param wsrpConsumerId the wsrp consumer ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching wsrp consumer portlet, or <code>null</code> if a matching wsrp consumer portlet could not be found
	*/
	public static WSRPConsumerPortlet fetchByWsrpConsumerId_Last(
		long wsrpConsumerId,
		OrderByComparator<WSRPConsumerPortlet> orderByComparator) {
		return getPersistence()
				   .fetchByWsrpConsumerId_Last(wsrpConsumerId, orderByComparator);
	}

	/**
	* Returns the wsrp consumer portlets before and after the current wsrp consumer portlet in the ordered set where wsrpConsumerId = &#63;.
	*
	* @param wsrpConsumerPortletId the primary key of the current wsrp consumer portlet
	* @param wsrpConsumerId the wsrp consumer ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next wsrp consumer portlet
	* @throws NoSuchConsumerPortletException if a wsrp consumer portlet with the primary key could not be found
	*/
	public static WSRPConsumerPortlet[] findByWsrpConsumerId_PrevAndNext(
		long wsrpConsumerPortletId, long wsrpConsumerId,
		OrderByComparator<WSRPConsumerPortlet> orderByComparator)
		throws com.liferay.wsrp.exception.NoSuchConsumerPortletException {
		return getPersistence()
				   .findByWsrpConsumerId_PrevAndNext(wsrpConsumerPortletId,
			wsrpConsumerId, orderByComparator);
	}

	/**
	* Removes all the wsrp consumer portlets where wsrpConsumerId = &#63; from the database.
	*
	* @param wsrpConsumerId the wsrp consumer ID
	*/
	public static void removeByWsrpConsumerId(long wsrpConsumerId) {
		getPersistence().removeByWsrpConsumerId(wsrpConsumerId);
	}

	/**
	* Returns the number of wsrp consumer portlets where wsrpConsumerId = &#63;.
	*
	* @param wsrpConsumerId the wsrp consumer ID
	* @return the number of matching wsrp consumer portlets
	*/
	public static int countByWsrpConsumerId(long wsrpConsumerId) {
		return getPersistence().countByWsrpConsumerId(wsrpConsumerId);
	}

	/**
	* Returns the wsrp consumer portlet where wsrpConsumerId = &#63; and portletHandle = &#63; or throws a {@link NoSuchConsumerPortletException} if it could not be found.
	*
	* @param wsrpConsumerId the wsrp consumer ID
	* @param portletHandle the portlet handle
	* @return the matching wsrp consumer portlet
	* @throws NoSuchConsumerPortletException if a matching wsrp consumer portlet could not be found
	*/
	public static WSRPConsumerPortlet findByW_P(long wsrpConsumerId,
		String portletHandle)
		throws com.liferay.wsrp.exception.NoSuchConsumerPortletException {
		return getPersistence().findByW_P(wsrpConsumerId, portletHandle);
	}

	/**
	* Returns the wsrp consumer portlet where wsrpConsumerId = &#63; and portletHandle = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param wsrpConsumerId the wsrp consumer ID
	* @param portletHandle the portlet handle
	* @return the matching wsrp consumer portlet, or <code>null</code> if a matching wsrp consumer portlet could not be found
	*/
	public static WSRPConsumerPortlet fetchByW_P(long wsrpConsumerId,
		String portletHandle) {
		return getPersistence().fetchByW_P(wsrpConsumerId, portletHandle);
	}

	/**
	* Returns the wsrp consumer portlet where wsrpConsumerId = &#63; and portletHandle = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param wsrpConsumerId the wsrp consumer ID
	* @param portletHandle the portlet handle
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching wsrp consumer portlet, or <code>null</code> if a matching wsrp consumer portlet could not be found
	*/
	public static WSRPConsumerPortlet fetchByW_P(long wsrpConsumerId,
		String portletHandle, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByW_P(wsrpConsumerId, portletHandle, retrieveFromCache);
	}

	/**
	* Removes the wsrp consumer portlet where wsrpConsumerId = &#63; and portletHandle = &#63; from the database.
	*
	* @param wsrpConsumerId the wsrp consumer ID
	* @param portletHandle the portlet handle
	* @return the wsrp consumer portlet that was removed
	*/
	public static WSRPConsumerPortlet removeByW_P(long wsrpConsumerId,
		String portletHandle)
		throws com.liferay.wsrp.exception.NoSuchConsumerPortletException {
		return getPersistence().removeByW_P(wsrpConsumerId, portletHandle);
	}

	/**
	* Returns the number of wsrp consumer portlets where wsrpConsumerId = &#63; and portletHandle = &#63;.
	*
	* @param wsrpConsumerId the wsrp consumer ID
	* @param portletHandle the portlet handle
	* @return the number of matching wsrp consumer portlets
	*/
	public static int countByW_P(long wsrpConsumerId, String portletHandle) {
		return getPersistence().countByW_P(wsrpConsumerId, portletHandle);
	}

	/**
	* Caches the wsrp consumer portlet in the entity cache if it is enabled.
	*
	* @param wsrpConsumerPortlet the wsrp consumer portlet
	*/
	public static void cacheResult(WSRPConsumerPortlet wsrpConsumerPortlet) {
		getPersistence().cacheResult(wsrpConsumerPortlet);
	}

	/**
	* Caches the wsrp consumer portlets in the entity cache if it is enabled.
	*
	* @param wsrpConsumerPortlets the wsrp consumer portlets
	*/
	public static void cacheResult(
		List<WSRPConsumerPortlet> wsrpConsumerPortlets) {
		getPersistence().cacheResult(wsrpConsumerPortlets);
	}

	/**
	* Creates a new wsrp consumer portlet with the primary key. Does not add the wsrp consumer portlet to the database.
	*
	* @param wsrpConsumerPortletId the primary key for the new wsrp consumer portlet
	* @return the new wsrp consumer portlet
	*/
	public static WSRPConsumerPortlet create(long wsrpConsumerPortletId) {
		return getPersistence().create(wsrpConsumerPortletId);
	}

	/**
	* Removes the wsrp consumer portlet with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param wsrpConsumerPortletId the primary key of the wsrp consumer portlet
	* @return the wsrp consumer portlet that was removed
	* @throws NoSuchConsumerPortletException if a wsrp consumer portlet with the primary key could not be found
	*/
	public static WSRPConsumerPortlet remove(long wsrpConsumerPortletId)
		throws com.liferay.wsrp.exception.NoSuchConsumerPortletException {
		return getPersistence().remove(wsrpConsumerPortletId);
	}

	public static WSRPConsumerPortlet updateImpl(
		WSRPConsumerPortlet wsrpConsumerPortlet) {
		return getPersistence().updateImpl(wsrpConsumerPortlet);
	}

	/**
	* Returns the wsrp consumer portlet with the primary key or throws a {@link NoSuchConsumerPortletException} if it could not be found.
	*
	* @param wsrpConsumerPortletId the primary key of the wsrp consumer portlet
	* @return the wsrp consumer portlet
	* @throws NoSuchConsumerPortletException if a wsrp consumer portlet with the primary key could not be found
	*/
	public static WSRPConsumerPortlet findByPrimaryKey(
		long wsrpConsumerPortletId)
		throws com.liferay.wsrp.exception.NoSuchConsumerPortletException {
		return getPersistence().findByPrimaryKey(wsrpConsumerPortletId);
	}

	/**
	* Returns the wsrp consumer portlet with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param wsrpConsumerPortletId the primary key of the wsrp consumer portlet
	* @return the wsrp consumer portlet, or <code>null</code> if a wsrp consumer portlet with the primary key could not be found
	*/
	public static WSRPConsumerPortlet fetchByPrimaryKey(
		long wsrpConsumerPortletId) {
		return getPersistence().fetchByPrimaryKey(wsrpConsumerPortletId);
	}

	public static java.util.Map<java.io.Serializable, WSRPConsumerPortlet> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the wsrp consumer portlets.
	*
	* @return the wsrp consumer portlets
	*/
	public static List<WSRPConsumerPortlet> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the wsrp consumer portlets.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WSRPConsumerPortletModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of wsrp consumer portlets
	* @param end the upper bound of the range of wsrp consumer portlets (not inclusive)
	* @return the range of wsrp consumer portlets
	*/
	public static List<WSRPConsumerPortlet> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the wsrp consumer portlets.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WSRPConsumerPortletModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of wsrp consumer portlets
	* @param end the upper bound of the range of wsrp consumer portlets (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of wsrp consumer portlets
	*/
	public static List<WSRPConsumerPortlet> findAll(int start, int end,
		OrderByComparator<WSRPConsumerPortlet> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the wsrp consumer portlets.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link WSRPConsumerPortletModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of wsrp consumer portlets
	* @param end the upper bound of the range of wsrp consumer portlets (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of wsrp consumer portlets
	*/
	public static List<WSRPConsumerPortlet> findAll(int start, int end,
		OrderByComparator<WSRPConsumerPortlet> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the wsrp consumer portlets from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of wsrp consumer portlets.
	*
	* @return the number of wsrp consumer portlets
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static java.util.Set<String> getBadColumnNames() {
		return getPersistence().getBadColumnNames();
	}

	public static WSRPConsumerPortletPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<WSRPConsumerPortletPersistence, WSRPConsumerPortletPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(WSRPConsumerPortletPersistence.class);

		ServiceTracker<WSRPConsumerPortletPersistence, WSRPConsumerPortletPersistence> serviceTracker =
			new ServiceTracker<WSRPConsumerPortletPersistence, WSRPConsumerPortletPersistence>(bundle.getBundleContext(),
				WSRPConsumerPortletPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}