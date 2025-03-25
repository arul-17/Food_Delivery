package com.food.service;

import java.util.List;

import com.food.exception.AddressException;
import com.food.exception.ItemException;
import com.food.exception.LoginException;
import com.food.exception.RestaurantException;
import com.food.model.Restaurant;

public interface RestaurantService {

	public Restaurant addRestaurant(String key, Restaurant restaurant) throws RestaurantException, LoginException;

	public Restaurant updateRestaurant(String key, Restaurant restaurant) throws RestaurantException, LoginException;

	public Restaurant deleteRestaurant(String key, Integer restaurantId) throws RestaurantException, LoginException;

	public Restaurant viewRestaurantById(String key, Integer restaurantId) throws RestaurantException, LoginException;

	public List<Restaurant> getAllRestaurants(String key) throws RestaurantException, LoginException;

	Restaurant deleteRestaurantById(String key, Integer restaurantId) throws RestaurantException, LoginException;

//	public List<Restaurant> viewNearByRestaurant(String key, String city)
//			throws RestaurantException, AddressException, LoginException;
//
//	public List<Restaurant> viewRestaurantByItemName(String key, String itemName)
//			throws RestaurantException, ItemException, LoginException;

}
