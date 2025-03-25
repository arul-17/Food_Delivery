package com.food.service;

import com.food.exception.CustomerException;
import com.food.exception.FoodCartException;
import com.food.exception.LoginException;
import com.food.exception.OrderDetailsException;
import com.food.model.OrderDetails;

public interface OrderService {

	public OrderDetails addOrder(String key, Integer customerId)
			throws CustomerException, FoodCartException, LoginException;

	public OrderDetails updateOrder(String key, Integer orderId, Integer customerId)
			throws OrderDetailsException, CustomerException, FoodCartException, LoginException;

	public OrderDetails removeOrder(String key, Integer orderId) throws OrderDetailsException, LoginException;

	public OrderDetails viewOrder(String key, Integer orderId) throws OrderDetailsException, LoginException;

	OrderDetails updateOrderStatus(String key, Integer orderId, String orderStatus)
			throws OrderDetailsException, LoginException;

}
