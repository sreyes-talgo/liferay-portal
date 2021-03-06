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

package com.liferay.shopping.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link ShoppingOrderLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see ShoppingOrderLocalService
 * @generated
 */
@ProviderType
public class ShoppingOrderLocalServiceWrapper
	implements ShoppingOrderLocalService,
		ServiceWrapper<ShoppingOrderLocalService> {
	public ShoppingOrderLocalServiceWrapper(
		ShoppingOrderLocalService shoppingOrderLocalService) {
		_shoppingOrderLocalService = shoppingOrderLocalService;
	}

	@Override
	public com.liferay.shopping.model.ShoppingOrder addLatestOrder(
		long userId, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _shoppingOrderLocalService.addLatestOrder(userId, groupId);
	}

	/**
	* Adds the shopping order to the database. Also notifies the appropriate model listeners.
	*
	* @param shoppingOrder the shopping order
	* @return the shopping order that was added
	*/
	@Override
	public com.liferay.shopping.model.ShoppingOrder addShoppingOrder(
		com.liferay.shopping.model.ShoppingOrder shoppingOrder) {
		return _shoppingOrderLocalService.addShoppingOrder(shoppingOrder);
	}

	@Override
	public void completeOrder(String number, String ppTxnId,
		String ppPaymentStatus, double ppPaymentGross, String ppReceiverEmail,
		String ppPayerEmail, boolean updateInventory,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		_shoppingOrderLocalService.completeOrder(number, ppTxnId,
			ppPaymentStatus, ppPaymentGross, ppReceiverEmail, ppPayerEmail,
			updateInventory, serviceContext);
	}

	/**
	* Creates a new shopping order with the primary key. Does not add the shopping order to the database.
	*
	* @param orderId the primary key for the new shopping order
	* @return the new shopping order
	*/
	@Override
	public com.liferay.shopping.model.ShoppingOrder createShoppingOrder(
		long orderId) {
		return _shoppingOrderLocalService.createShoppingOrder(orderId);
	}

	@Override
	public void deleteOrder(long orderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_shoppingOrderLocalService.deleteOrder(orderId);
	}

	@Override
	public void deleteOrder(com.liferay.shopping.model.ShoppingOrder order)
		throws com.liferay.portal.kernel.exception.PortalException {
		_shoppingOrderLocalService.deleteOrder(order);
	}

	@Override
	public void deleteOrders(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_shoppingOrderLocalService.deleteOrders(groupId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _shoppingOrderLocalService.deletePersistedModel(persistedModel);
	}

	/**
	* Deletes the shopping order with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param orderId the primary key of the shopping order
	* @return the shopping order that was removed
	* @throws PortalException if a shopping order with the primary key could not be found
	*/
	@Override
	public com.liferay.shopping.model.ShoppingOrder deleteShoppingOrder(
		long orderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _shoppingOrderLocalService.deleteShoppingOrder(orderId);
	}

	/**
	* Deletes the shopping order from the database. Also notifies the appropriate model listeners.
	*
	* @param shoppingOrder the shopping order
	* @return the shopping order that was removed
	*/
	@Override
	public com.liferay.shopping.model.ShoppingOrder deleteShoppingOrder(
		com.liferay.shopping.model.ShoppingOrder shoppingOrder) {
		return _shoppingOrderLocalService.deleteShoppingOrder(shoppingOrder);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _shoppingOrderLocalService.dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return _shoppingOrderLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.shopping.model.impl.ShoppingOrderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
		return _shoppingOrderLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.shopping.model.impl.ShoppingOrderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {
		return _shoppingOrderLocalService.dynamicQuery(dynamicQuery, start,
			end, orderByComparator);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return _shoppingOrderLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {
		return _shoppingOrderLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.shopping.model.ShoppingOrder fetchShoppingOrder(
		long orderId) {
		return _shoppingOrderLocalService.fetchShoppingOrder(orderId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _shoppingOrderLocalService.getActionableDynamicQuery();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _shoppingOrderLocalService.getIndexableActionableDynamicQuery();
	}

	@Override
	public com.liferay.shopping.model.ShoppingOrder getLatestOrder(
		long userId, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _shoppingOrderLocalService.getLatestOrder(userId, groupId);
	}

	@Override
	public com.liferay.shopping.model.ShoppingOrder getOrder(long orderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _shoppingOrderLocalService.getOrder(orderId);
	}

	@Override
	public com.liferay.shopping.model.ShoppingOrder getOrder(String number)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _shoppingOrderLocalService.getOrder(number);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _shoppingOrderLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.shopping.model.ShoppingOrder getPayPalTxnIdOrder(
		String ppTxnId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _shoppingOrderLocalService.getPayPalTxnIdOrder(ppTxnId);
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _shoppingOrderLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the shopping order with the primary key.
	*
	* @param orderId the primary key of the shopping order
	* @return the shopping order
	* @throws PortalException if a shopping order with the primary key could not be found
	*/
	@Override
	public com.liferay.shopping.model.ShoppingOrder getShoppingOrder(
		long orderId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _shoppingOrderLocalService.getShoppingOrder(orderId);
	}

	/**
	* Returns a range of all the shopping orders.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.shopping.model.impl.ShoppingOrderModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of shopping orders
	* @param end the upper bound of the range of shopping orders (not inclusive)
	* @return the range of shopping orders
	*/
	@Override
	public java.util.List<com.liferay.shopping.model.ShoppingOrder> getShoppingOrders(
		int start, int end) {
		return _shoppingOrderLocalService.getShoppingOrders(start, end);
	}

	/**
	* Returns the number of shopping orders.
	*
	* @return the number of shopping orders
	*/
	@Override
	public int getShoppingOrdersCount() {
		return _shoppingOrderLocalService.getShoppingOrdersCount();
	}

	@Override
	public com.liferay.shopping.model.ShoppingOrder saveLatestOrder(
		com.liferay.shopping.model.ShoppingCart cart)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _shoppingOrderLocalService.saveLatestOrder(cart);
	}

	@Override
	public java.util.List<com.liferay.shopping.model.ShoppingOrder> search(
		long groupId, long companyId, long userId, String number,
		String billingFirstName, String billingLastName,
		String billingEmailAddress, String shippingFirstName,
		String shippingLastName, String shippingEmailAddress,
		String ppPaymentStatus, boolean andOperator, int start, int end) {
		return _shoppingOrderLocalService.search(groupId, companyId, userId,
			number, billingFirstName, billingLastName, billingEmailAddress,
			shippingFirstName, shippingLastName, shippingEmailAddress,
			ppPaymentStatus, andOperator, start, end);
	}

	@Override
	public int searchCount(long groupId, long companyId, long userId,
		String number, String billingFirstName, String billingLastName,
		String billingEmailAddress, String shippingFirstName,
		String shippingLastName, String shippingEmailAddress,
		String ppPaymentStatus, boolean andOperator) {
		return _shoppingOrderLocalService.searchCount(groupId, companyId,
			userId, number, billingFirstName, billingLastName,
			billingEmailAddress, shippingFirstName, shippingLastName,
			shippingEmailAddress, ppPaymentStatus, andOperator);
	}

	@Override
	public void sendEmail(long orderId, String emailType,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		_shoppingOrderLocalService.sendEmail(orderId, emailType, serviceContext);
	}

	@Override
	public void sendEmail(com.liferay.shopping.model.ShoppingOrder order,
		String emailType,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		_shoppingOrderLocalService.sendEmail(order, emailType, serviceContext);
	}

	@Override
	public com.liferay.shopping.model.ShoppingOrder updateLatestOrder(
		long userId, long groupId, String billingFirstName,
		String billingLastName, String billingEmailAddress,
		String billingCompany, String billingStreet, String billingCity,
		String billingState, String billingZip, String billingCountry,
		String billingPhone, boolean shipToBilling, String shippingFirstName,
		String shippingLastName, String shippingEmailAddress,
		String shippingCompany, String shippingStreet, String shippingCity,
		String shippingState, String shippingZip, String shippingCountry,
		String shippingPhone, String ccName, String ccType, String ccNumber,
		int ccExpMonth, int ccExpYear, String ccVerNumber, String comments)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _shoppingOrderLocalService.updateLatestOrder(userId, groupId,
			billingFirstName, billingLastName, billingEmailAddress,
			billingCompany, billingStreet, billingCity, billingState,
			billingZip, billingCountry, billingPhone, shipToBilling,
			shippingFirstName, shippingLastName, shippingEmailAddress,
			shippingCompany, shippingStreet, shippingCity, shippingState,
			shippingZip, shippingCountry, shippingPhone, ccName, ccType,
			ccNumber, ccExpMonth, ccExpYear, ccVerNumber, comments);
	}

	@Override
	public com.liferay.shopping.model.ShoppingOrder updateOrder(long orderId,
		String ppTxnId, String ppPaymentStatus, double ppPaymentGross,
		String ppReceiverEmail, String ppPayerEmail)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _shoppingOrderLocalService.updateOrder(orderId, ppTxnId,
			ppPaymentStatus, ppPaymentGross, ppReceiverEmail, ppPayerEmail);
	}

	@Override
	public com.liferay.shopping.model.ShoppingOrder updateOrder(long orderId,
		String billingFirstName, String billingLastName,
		String billingEmailAddress, String billingCompany,
		String billingStreet, String billingCity, String billingState,
		String billingZip, String billingCountry, String billingPhone,
		boolean shipToBilling, String shippingFirstName,
		String shippingLastName, String shippingEmailAddress,
		String shippingCompany, String shippingStreet, String shippingCity,
		String shippingState, String shippingZip, String shippingCountry,
		String shippingPhone, String ccName, String ccType, String ccNumber,
		int ccExpMonth, int ccExpYear, String ccVerNumber, String comments)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _shoppingOrderLocalService.updateOrder(orderId,
			billingFirstName, billingLastName, billingEmailAddress,
			billingCompany, billingStreet, billingCity, billingState,
			billingZip, billingCountry, billingPhone, shipToBilling,
			shippingFirstName, shippingLastName, shippingEmailAddress,
			shippingCompany, shippingStreet, shippingCity, shippingState,
			shippingZip, shippingCountry, shippingPhone, ccName, ccType,
			ccNumber, ccExpMonth, ccExpYear, ccVerNumber, comments);
	}

	/**
	* Updates the shopping order in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param shoppingOrder the shopping order
	* @return the shopping order that was updated
	*/
	@Override
	public com.liferay.shopping.model.ShoppingOrder updateShoppingOrder(
		com.liferay.shopping.model.ShoppingOrder shoppingOrder) {
		return _shoppingOrderLocalService.updateShoppingOrder(shoppingOrder);
	}

	@Override
	public ShoppingOrderLocalService getWrappedService() {
		return _shoppingOrderLocalService;
	}

	@Override
	public void setWrappedService(
		ShoppingOrderLocalService shoppingOrderLocalService) {
		_shoppingOrderLocalService = shoppingOrderLocalService;
	}

	private ShoppingOrderLocalService _shoppingOrderLocalService;
}