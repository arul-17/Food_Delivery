package com.food.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.food.exception.AddressException;
import com.food.exception.RestaurantException;
import com.food.model.Address;
import com.food.model.Restaurant;
import com.food.repository.AddressRepo;

@Service
public class AddressServiceImpl implements AddressService{

	@Override
	public List<Restaurant> getAllRestaurantsByAddressId(Integer addressId) throws AddressException {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
