package com.food.service;

import java.util.List;

import com.food.exception.CategoryException;
import com.food.exception.ItemException;
import com.food.exception.LoginException;
import com.food.model.CategoryDTO;
import com.food.model.Item;
import com.food.model.ItemDTO;

public interface ItemService {

	public Item addItem(String key, Item item, Integer categoryId) throws ItemException, CategoryException, LoginException;

	public Item updateItem(String key, ItemDTO itemDTO) throws ItemException, CategoryException, LoginException;

	public Item removeItemById(String key, Integer itemId) throws ItemException, LoginException;

	public List<Item> getAllItem(String key) throws ItemException, LoginException;

	public List<Item> getAllItemByCategoryName(String key, String categoryName)
			throws ItemException, CategoryException, LoginException;

	public List<Item> searchItemsByName(String key, String name) throws ItemException, LoginException ;
	public void validateSession(String key) throws LoginException;

	public Item getItemById(String key, Integer id) throws ItemException, LoginException;



	

	
}
