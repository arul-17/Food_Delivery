package com.food.service;

import com.food.exception.BillException;
import com.food.exception.CustomerException;
import com.food.exception.ItemException;
import com.food.exception.LoginException;
import com.food.exception.OrderDetailsException;
import com.food.model.Bill;

public interface BillService {

	public Bill generateBill(String key, Integer customerId, Integer orderDetailId)
			throws BillException, CustomerException, OrderDetailsException, LoginException;

	public Bill updateBill(String key, Bill bill) throws BillException, LoginException;

	public Bill removeBill(String key, Integer billId) throws BillException, LoginException;

	public Bill viewBill(String key, Integer billId) throws BillException, LoginException;

}
