package com.food.service;

import java.util.List;

import com.food.exception.AddressException;
import com.food.model.Restaurant;

public interface AddressService {
	
	public List<Restaurant> getAllRestaurantsByAddressId(Integer addressId) throws AddressException;

}
