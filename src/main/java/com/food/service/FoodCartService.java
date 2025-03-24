package com.food.service;

import com.food.exception.CustomerException;
import com.food.exception.FoodCartException;
import com.food.exception.ItemException;
import com.food.exception.LoginException;
import com.food.model.CustomerDTO;
import com.food.model.FoodCart;
import com.food.model.ItemDTO;

public interface FoodCartService {
	
	public FoodCart addItemToCart(String key, Integer customerId, Integer itemId) throws ItemException, CustomerException, LoginException;
		
	public FoodCart removeItem(String key, Integer cartId, Integer itemId) throws FoodCartException, ItemException, LoginException;
	
	public FoodCart removeCart(String key, Integer cartId) throws FoodCartException, LoginException;

	public FoodCart updateItemQuantity(String key, Integer cartId, Integer itemId, Integer quantity) throws FoodCartException, ItemException,LoginException ;
}
