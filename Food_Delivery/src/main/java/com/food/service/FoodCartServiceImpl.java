package com.food.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.food.exception.CustomerException;
import com.food.exception.FoodCartException;
import com.food.exception.ItemException;
import com.food.exception.LoginException;
import com.food.model.CurrentUserSession;
import com.food.model.Customer;
import com.food.model.CustomerDTO;
import com.food.model.FoodCart;
import com.food.model.Item;
import com.food.model.ItemDTO;
import com.food.repository.CurrentUserSessionRepo;
import com.food.repository.CustomerRepo;
import com.food.repository.FoodCartRepo;
import com.food.repository.ItemRepo;

@Service
public class FoodCartServiceImpl implements FoodCartService {

	@Autowired
	private FoodCartRepo foodcartRepo;

	@Autowired
	private CustomerRepo customerRepo;

	@Autowired
	private ItemRepo itemRepo;

	@Autowired
	private CurrentUserSessionRepo currSessionRepo;
	
	public FoodCart addItemToCart(String key, Integer customerId, Integer itemId) 
	        throws ItemException, CustomerException, LoginException {

	    CurrentUserSession currentSession = currSessionRepo.findByPrivateKey(key);
	    if (currentSession == null) {
	        throw new LoginException("Login required. Please log in first.");
	    }

	    Customer customer = customerRepo.findById(customerId)
	            .orElseThrow(() -> new CustomerException("No customer found with ID: " + customerId));

	 
	    FoodCart foodCart = foodcartRepo.findByCustomerCustomerId(customerId);
	    if (foodCart == null) {
	        foodCart = new FoodCart();
	        foodCart.setCustomer(customer);
	        foodCart.setItemList(new ArrayList<>()); 
	    }

	    Item item = itemRepo.findById(itemId)
	            .orElseThrow(() -> new ItemException("Item not found"));

	    boolean itemExists = false;
	    for (Item cartItem : foodCart.getItemList()) {
	        if (cartItem.getItemId().equals(itemId)) {
	            cartItem.setQuantity(cartItem.getQuantity() + 1);
	            cartItem.setItemTotal(cartItem.getQuantity() * cartItem.getCost());
	            itemExists = true;
	            break;
	        }
	    }
	    if (!itemExists) {
	        item.setQuantity(1);
	        item.setItemTotal(item.getCost()); 
	        foodCart.getItemList().add(item);
	    }

	    return foodcartRepo.save(foodCart);
	}

	public FoodCart updateItemQuantity(String key, Integer cartId, Integer itemId, Integer quantity) throws FoodCartException, LoginException, ItemException  {

	    CurrentUserSession currentSession = currSessionRepo.findByPrivateKey(key);
	    if (currentSession == null) {
	        throw new LoginException("Login required");
	    }
	    FoodCart foodCart = foodcartRepo.findById(cartId)
	            .orElseThrow(() -> new FoodCartException("Cart not found"));

	    boolean itemUpdated = false;
	    for (Item item : foodCart.getItemList()) {
	        if (item.getItemId().equals(itemId)) {
	            item.setQuantity(quantity);
	            item.setItemTotal(item.getCost() * quantity);
	            itemUpdated = true;
	            break;
	        }
	    }

	    if (!itemUpdated) {
	        throw new ItemException("Item not found in cart");
	    }

	    return foodcartRepo.save(foodCart);
	}

	
	@Override
	public FoodCart removeItem(String key, Integer cartId, Integer itemId) throws FoodCartException, ItemException, LoginException {
		// TODO Auto-generated method stub
		
		CurrentUserSession currSess = currSessionRepo.findByPrivateKey(key);
		if (currSess == null)
			throw new LoginException("Login required");

		Optional<FoodCart> opt = foodcartRepo.findById(cartId);

		if (opt.isPresent()) {
			FoodCart foodCart = opt.get();

			List<Item> itemList = foodCart.getItemList();

			boolean flag = true;
			Item getItem = null;

			for (int i = 0; i < itemList.size(); i++) {
				Item ele = itemList.get(i);
				if (ele.getItemId() == itemId) {
					flag = false;
					getItem = ele;
				}
			}
			if (flag)
				throw new ItemException("Item not found!");
			else {
				itemList.remove(getItem);
				foodCart.setItemList(itemList);
				foodcartRepo.save(foodCart);
				return foodCart;
			}
		} else {
			throw new FoodCartException("Food Cart not found!");
		}
	}

	@Override
	public FoodCart removeCart(String key, Integer cartId) throws FoodCartException, LoginException {
		// TODO Auto-generated method stub
		
		CurrentUserSession currSess = currSessionRepo.findByPrivateKey(key);
		if (currSess == null)
			throw new LoginException("Login required");

		Optional<FoodCart> opt = foodcartRepo.findById(cartId);

		if (opt.isPresent()) {
			FoodCart foodCart = opt.get();
			foodCart.getItemList().clear();
			return foodcartRepo.save(foodCart);
			
		} else {
			throw new FoodCartException("Food Cart not found!");
		}
	}

	
}
