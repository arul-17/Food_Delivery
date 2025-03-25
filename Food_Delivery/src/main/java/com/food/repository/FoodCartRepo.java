package com.food.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.food.model.Customer;
import com.food.model.FoodCart;

@Repository
public interface FoodCartRepo extends JpaRepository<FoodCart, Integer>{

	FoodCart findByCustomer(Customer customer);
	@Query("SELECT fc FROM FoodCart fc WHERE fc.customer.customerId = :customerId")
	FoodCart findByCustomerCustomerId(Integer customerId);


}
