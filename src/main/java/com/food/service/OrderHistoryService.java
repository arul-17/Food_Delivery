package com.food.service;

import java.util.List;

import com.food.exception.CustomerException;
import com.food.exception.LoginException;
import com.food.exception.OrderHistoryException;
import com.food.model.OrderDetails;
import com.food.model.OrderHistory;

public interface OrderHistoryService {

	public OrderHistory getOrderHistoryById(String key, Integer orderHisId)
			throws OrderHistoryException, LoginException;

	public List<OrderHistory> getOrderHistoryByCustomerId(String key, Integer customerId)
			throws OrderHistoryException, LoginException, CustomerException;

	public List<OrderHistory> getAllOrderHistory(String key) throws OrderHistoryException, LoginException;

	
	

}
