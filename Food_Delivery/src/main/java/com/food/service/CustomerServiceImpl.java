package com.food.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.food.exception.CustomerException;
import com.food.exception.LoginException;
import com.food.model.CurrentUserSession;
import com.food.model.Customer;
import com.food.model.FoodCart;
import com.food.repository.CurrentUserSessionRepo;
import com.food.repository.CustomerRepo;
import com.food.repository.FoodCartRepo;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepo customerRepo;

	@Autowired
	private FoodCartRepo foodCartRepo;

	@Autowired
	private CurrentUserSessionRepo currSession;
	@Autowired
    private PasswordEncoder passwordEncoder;


	@Override
	public Customer addCustomer(Customer customer) throws CustomerException {

	  
	    Customer existsCustomer = customerRepo.findByEmail(customer.getEmail());
	    if (existsCustomer != null) {
	        throw new CustomerException("Customer email already exists!");
	    }
	    String encodedPassword = passwordEncoder.encode(customer.getPassword());
	    customer.setPassword(encodedPassword); 
	    Customer savedCustomer = customerRepo.save(customer);

	    FoodCart foodCart = new FoodCart();
	    foodCart.setCustomer(savedCustomer);
	    foodCart.setItemList(new ArrayList<>()); 
	    FoodCart savedCart = foodCartRepo.save(foodCart);
	    savedCustomer.setFoodCart(savedCart);

	    return customerRepo.save(savedCustomer); 
	}


	@Override
	public Customer updateCustomer(String key, Customer c) throws CustomerException, LoginException {

		CurrentUserSession currSess = currSession.findByPrivateKey(key);
		if (currSess == null)
			throw new LoginException("Login required");

		if (c == null) {
			throw new CustomerException("Null value is not allowed");
		}

		Optional<Customer> optional = customerRepo.findById(c.getCustomerId());

		if (optional.isEmpty()) {
			throw new CustomerException("No customer exist with given customer id :" + c.getCustomerId());
		}
	
		return customerRepo.save(c);
	}

	@Override
	public Customer removeCustomerById(String key, Integer customerId) throws CustomerException, LoginException {

		CurrentUserSession currSess = currSession.findByPrivateKey(key);
		if (currSess == null)
			throw new LoginException("Login required");

		Optional<Customer> optional = customerRepo.findById(customerId);

		if (optional.isEmpty()) {
			throw new CustomerException("No customer exist with given customer id :" + customerId);
		}

		Customer deletedCustomer = optional.get();
		customerRepo.delete(deletedCustomer);
		return deletedCustomer;
	}

	@Override
	public Customer removeCustomer(String key, Customer c) throws CustomerException, LoginException {

		CurrentUserSession currSess = currSession.findByPrivateKey(key);
		if (currSess == null)
			throw new LoginException("Login required");

		if (c == null) {
			throw new CustomerException("Null value is not allowed");
		}

		Optional<Customer> optional = customerRepo.findById(c.getCustomerId());

		if (optional.isEmpty()) {
			throw new CustomerException("No customer exist with given customer id :" + c.getCustomerId());
		}

		Customer deletedCustomer = optional.get();
		customerRepo.delete(deletedCustomer);
		return deletedCustomer;
	}

	@Override
	public Customer viewCustomer(String key, Integer cid) throws CustomerException, LoginException {

		CurrentUserSession currSess = currSession.findByPrivateKey(key);
		if (currSess == null)
			throw new LoginException("Login required");

		Optional<Customer> optional = customerRepo.findById(cid);

		if (optional.isEmpty()) {
			throw new CustomerException("No customer exist with given customer id :" + cid);
		}

		return optional.get();
	}

	@Override
	public List<Customer> viewAllCustomers(String key) throws CustomerException, LoginException {

		CurrentUserSession currSess = currSession.findByPrivateKey(key);
		if (currSess == null)
			throw new LoginException("Login required");

		List<Customer> customers = customerRepo.findAll();

		if (customers.size() == 0) {
			throw new CustomerException("No customers available in list");
		}

		return customers;
	}

}
