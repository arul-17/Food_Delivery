package com.food.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.food.exception.AddressException;
import com.food.exception.CustomerException;
import com.food.exception.ItemException;
import com.food.exception.LoginException;
import com.food.exception.RestaurantException;
import com.food.model.Address;
import com.food.model.CurrentUserSession;
import com.food.model.Customer;
import com.food.model.Item;
import com.food.model.Restaurant;
import com.food.repository.CurrentUserSessionRepo;
import com.food.repository.RestaurantRepo;
import com.food.service.AddressService;
import com.food.service.RestaurantService;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

	@Autowired
	private RestaurantService resService;
	@Autowired
	private CurrentUserSessionRepo sessionRepo;
	@Autowired
	private RestaurantRepo restaurantRepo;

	@PostMapping("/add")
	public ResponseEntity<String> addRestaurant(@RequestBody Restaurant restaurant, @RequestHeader("Authorization") String authorizationHeader) throws LoginException {
	    String key = authorizationHeader.replace("Bearer ", "");
	    CurrentUserSession session = sessionRepo.findByPrivateKey(key);

	    if (session == null) {
	        throw new LoginException("Invalid session key");
	    }

	    if (!session.getRole().equals("admin")) {
	        throw new LoginException("Admin login required");
	    }

	    for (Item item : restaurant.getItemList()) {
	        item.setRestaurants(List.of(restaurant));  
	    }

	    restaurantRepo.save(restaurant); 
	    return ResponseEntity.ok("Restaurant added successfully!");
	}

	@PutMapping("/update")
	public ResponseEntity<Restaurant> updateRestaurant(@RequestHeader("Authorization") String authorizationHeader, 
	        @RequestBody Restaurant res) throws RestaurantException, LoginException {
	    String key = authorizationHeader.replace("Bearer ", "");
	    CurrentUserSession session = sessionRepo.findByPrivateKey(key);

	    if (session == null) {
	        throw new LoginException("Login required");
	    }

	    if (!session.getRole().equals("admin")) {
	        throw new LoginException("Admin login required");
	    }

	    Restaurant restaurant = resService.updateRestaurant(key, res);
	    return new ResponseEntity<>(restaurant, HttpStatus.OK);
	}


	@DeleteMapping("/delete")
	public ResponseEntity<String> deleteRestaurant(@RequestHeader("Authorization") String authorizationHeader, 
	        @RequestParam Integer restaurantId) throws RestaurantException, LoginException {
	    String key = authorizationHeader.replace("Bearer ", "");
	    CurrentUserSession session = sessionRepo.findByPrivateKey(key);

	    if (session == null) {
	        throw new LoginException("Login required");
	    }

	    if (!session.getRole().equals("admin")) {
	        throw new LoginException("Admin login required");
	    }

	    resService.deleteRestaurant(key, restaurantId);
	    return new ResponseEntity<>("Restaurant deleted successfully", HttpStatus.OK);
	}
	@DeleteMapping("/delete/{restaurantId}")
	public ResponseEntity<String> deleteRestaurantById(
	        @RequestHeader("Authorization") String authorizationHeader,
	        @PathVariable Integer restaurantId) throws RestaurantException, LoginException {
	    
	    String key = authorizationHeader.replace("Bearer ", "");
	    CurrentUserSession session = sessionRepo.findByPrivateKey(key);

	    if (session == null) {
	        throw new LoginException("Login required");
	    }

	    if (!session.getRole().equals("admin")) {
	        throw new LoginException("Admin login required");
	    }

	    resService.deleteRestaurantById(key, restaurantId);
	    return new ResponseEntity<>("Restaurant with ID " + restaurantId + " deleted successfully", HttpStatus.OK);
	}

	@GetMapping("/view/{id}")
	public ResponseEntity<Restaurant> viewRestaurant(@RequestHeader("Authorization") String authorizationHeader, 
	        @PathVariable("id") Integer restaurantId) throws RestaurantException, LoginException {
	   
	    String key = authorizationHeader.replace("Bearer ", "");
	    CurrentUserSession session = sessionRepo.findByPrivateKey(key);

	    if (session == null) {
	        throw new LoginException("Login required");
	    }

	    Restaurant restaurant = resService.viewRestaurantById(key, restaurantId);
	    return new ResponseEntity<>(restaurant, HttpStatus.OK);
	}


	@GetMapping("/view")
	public ResponseEntity<List<Restaurant>> viewRestaurants(@RequestHeader("Authorization") String authorizationHeader) throws LoginException {
	    String key = authorizationHeader.replace("Bearer ", "");
	    CurrentUserSession session = sessionRepo.findByPrivateKey(key);
	    
	    if (session == null) {
	        throw new LoginException("Login required");
	    }

	    List<Restaurant> restaurants = restaurantRepo.findAll();

	    for (Restaurant res : restaurants) {
	        if (res.getAddress() == null) {
	            res.setAddress(new Address());
	        }
	    }

	    return ResponseEntity.ok(restaurants);
	}


}
