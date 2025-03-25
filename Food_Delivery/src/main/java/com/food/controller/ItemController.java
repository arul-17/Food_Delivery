package com.food.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.food.exception.CategoryException;
import com.food.exception.ItemException;
import com.food.exception.LoginException;
import com.food.model.Item;
import com.food.model.ItemDTO;
import com.food.service.ItemService;

@RestController
@RequestMapping("/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping("/all")
    public ResponseEntity<List<Item>> getAllItems(@RequestHeader("Authorization") String authorizationHeader)
            throws ItemException, LoginException {
        String key = authorizationHeader.replace("Bearer ", "");
        return ResponseEntity.ok(itemService.getAllItem(key));
    }
    @GetMapping("/all/{id}")
    public ResponseEntity<Item> getItemById(@RequestHeader("Authorization") String authorizationHeader,
                                            @PathVariable("id") Integer id)
            throws ItemException, LoginException {
        String key = authorizationHeader.replace("Bearer ", "");
        return ResponseEntity.ok(itemService.getItemById(key, id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Item>> searchItemsByName(@RequestHeader("Authorization") String authorizationHeader,
                                                        @RequestParam String name)
            throws ItemException, LoginException {
        String key = authorizationHeader.replace("Bearer ", "");
        return ResponseEntity.ok(itemService.searchItemsByName(key, name));
    }


    @GetMapping("/category/{categoryName}")
    public ResponseEntity<List<Item>> getItemsByCategoryName(@RequestHeader("Authorization") String authorizationHeader,
                                                             @PathVariable String categoryName)
            throws ItemException, CategoryException, LoginException {
        String key = authorizationHeader.replace("Bearer ", "");
        return ResponseEntity.ok(itemService.getAllItemByCategoryName(key, categoryName));
    }

    @PostMapping("/add")
    public ResponseEntity<Item> addItem(@RequestHeader("Authorization") String authorizationHeader,
                                        @RequestBody ItemDTO itemDTO)
            throws ItemException, CategoryException, LoginException {
        String key = authorizationHeader.replace("Bearer ", "");

        Item item = new Item();
        item.setItemName(itemDTO.getItemName());
        item.setQuantity(itemDTO.getQuantity());
        item.setCost(itemDTO.getCost());

        Item savedItem = itemService.addItem(key, item, itemDTO.getCategoryId());
        return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Item> updateItem(@RequestHeader("Authorization") String authorizationHeader,
                                           @RequestBody ItemDTO itemDTO)
            throws ItemException, CategoryException, LoginException {
        String key = authorizationHeader.replace("Bearer ", "");
        return ResponseEntity.ok(itemService.updateItem(key, itemDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Item> removeItemById(@RequestHeader("Authorization") String authorizationHeader,
                                               @PathVariable("id") Integer id)
            throws ItemException, LoginException {
        String key = authorizationHeader.replace("Bearer ", "");
        return ResponseEntity.ok(itemService.removeItemById(key, id));
    }
}
