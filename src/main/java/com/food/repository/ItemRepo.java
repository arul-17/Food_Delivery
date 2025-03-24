package com.food.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.food.exception.ItemException;
import com.food.model.Item;

@Repository
public interface ItemRepo extends JpaRepository<Item, Integer>{
	
	    List<Item> findByItemNameContainingIgnoreCase(String itemName) throws ItemException;

		Item findByItemName(String itemName) throws ItemException;
		
	
}
