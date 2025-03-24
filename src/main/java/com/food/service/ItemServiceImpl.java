package com.food.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.food.exception.CategoryException;
import com.food.exception.ItemException;
import com.food.exception.LoginException;
import com.food.model.Category;
import com.food.model.CategoryDTO;
import com.food.model.CurrentUserSession;
import com.food.model.Item;
import com.food.model.ItemDTO;
import com.food.repository.CategoryRepo;
import com.food.repository.CurrentUserSessionRepo;
import com.food.repository.ItemRepo;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepo itemRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private CurrentUserSessionRepo currSession;

    @Override
    public Item addItem(String key, Item item, Integer categoryId) throws ItemException, CategoryException, LoginException {
   
        CurrentUserSession currSess = currSession.findByPrivateKey(key);
        if (currSess == null || !currSess.getRole().equalsIgnoreCase("admin")) {
            throw new LoginException("Admin login required");
        }

        System.out.println("Category ID received: " + categoryId);

     
        Optional<Category> categoryOpt = categoryRepo.findById(categoryId);
        if (categoryOpt.isEmpty()) {
            throw new CategoryException("Category not found with ID: " + categoryId);
        }

        Category category = categoryOpt.get();
        System.out.println("Found category: " + category.getCategoryName());

        item.setCategory(category);

     
        if (item.getQuantity() != null && item.getCost() != null) {
            item.setItemTotal(item.getQuantity() * item.getCost());
        }

      
        System.out.println("Saving item: " + item.getItemName() + 
                          ", Category: " + (item.getCategory() != null ? item.getCategory().getCategoryName() : "NULL") + 
                          ", Total: " + item.getItemTotal());

        return itemRepo.save(item);
    }





    @Override
    public Item updateItem(String key, ItemDTO itemDTO) throws ItemException, CategoryException, LoginException {
        CurrentUserSession currSess = currSession.findByPrivateKey(key);
        if (currSess == null || !currSess.getRole().equalsIgnoreCase("admin")) {
            throw new LoginException("Admin login required");
        }

        Optional<Item> optItem = itemRepo.findById(itemDTO.getItemId());
        if (optItem.isEmpty()) {
            throw new ItemException("Item not found");
        }

        Item item = optItem.get();
        
        if (itemDTO.getCategoryId() != null) {
            Optional<Category> catOpt = categoryRepo.findById(itemDTO.getCategoryId());
            if (catOpt.isEmpty()) {
                throw new CategoryException("Category not found");
            }
            item.setCategory(catOpt.get());
        }

        if (itemDTO.getItemName() != null) {
            item.setItemName(itemDTO.getItemName());
        }
        if (itemDTO.getQuantity() != null) {
            item.setQuantity(itemDTO.getQuantity());
        }
        if (itemDTO.getCost() != null) {
            item.setCost(itemDTO.getCost());
        }

        // ✅ Calculate itemTotal on update
        if (item.getQuantity() != null && item.getCost() != null) {
            item.setItemTotal(item.getQuantity() * item.getCost());
        }

        return itemRepo.save(item);
    }


    @Override
    public Item removeItemById(String key, Integer itemId) throws ItemException, LoginException {
        CurrentUserSession currSess = currSession.findByPrivateKey(key);
        if (currSess == null || !currSess.getRole().equalsIgnoreCase("admin")) {
            throw new LoginException("Admin login required");
        }

        Optional<Item> opt = itemRepo.findById(itemId);
        if (opt.isEmpty()) {
            throw new ItemException("Item not found");
        }

        Item deletedItem = opt.get();
        itemRepo.delete(deletedItem);
        return deletedItem;
    }

    @Override
    public List<Item> getAllItem(String key) throws ItemException, LoginException {
        CurrentUserSession currSess = currSession.findByPrivateKey(key);
        if (currSess == null) {
            throw new LoginException("Login required");
        }

        List<Item> items = itemRepo.findAll();
        if (items.isEmpty()) {
            throw new ItemException("No items found");
        }

        return items;
    }
    public Item getItemById(String key, Integer id) throws ItemException, LoginException {
        
        // Validate Admin Session
        CurrentUserSession currSess = currSession.findByPrivateKey(key);
        if (currSess == null || !currSess.getRole().equalsIgnoreCase("admin")) {
            throw new LoginException("Admin login required");
        }

        // Fetch Item by ID from the Database
        return itemRepo.findById(id)
                .orElseThrow(() -> new ItemException("Item with ID " + id + " not found"));
    }



    @Override
    public List<Item> getAllItemByCategoryName(String key, String categoryName)
            throws ItemException, CategoryException, LoginException {
        CurrentUserSession currSess = currSession.findByPrivateKey(key);
        if (currSess == null) {
            throw new LoginException("Login required");
        }

        Category category = categoryRepo.findByCategoryName(categoryName);
        if (category == null) {
            throw new CategoryException("Category does not exist");
        }

        List<Item> items = category.getItemList();
        if (items.isEmpty()) {
            throw new ItemException("No items found in this category");
        }

        return items;
    }
    @Override
    public List<Item> searchItemsByName(String key, String name) throws ItemException, LoginException {
        // Validate user session
        validateSession(key);

        List<Item> items = itemRepo.findByItemNameContainingIgnoreCase(name);
        
        if (items.isEmpty()) {
            throw new ItemException("No items found with name: " + name);
        }
        return items;
    }





	@Override
	public void validateSession(String key) throws LoginException {
		// TODO Auto-generated method stub
		
	}





	





	

}
