package com.food.service;

import java.util.List;

import com.food.exception.CustomerException;
import com.food.exception.LoginException;
import com.food.model.Customer;

public interface CustomerService {

	Customer addCustomer(Customer customer) throws CustomerException;

	Customer updateCustomer(String key, Customer customer) throws CustomerException, LoginException;

	Customer removeCustomerById(String key, Integer customerId) throws CustomerException, LoginException;

	Customer removeCustomer(String key, Customer customer) throws CustomerException, LoginException;

	Customer viewCustomer(String key, Integer customerId) throws CustomerException, LoginException;

	List<Customer> viewAllCustomers(String key) throws CustomerException, LoginException;

}
