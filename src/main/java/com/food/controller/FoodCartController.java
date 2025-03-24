package com.food.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.food.exception.CustomerException;
import com.food.exception.FoodCartException;
import com.food.exception.ItemException;
import com.food.exception.LoginException;
import com.food.model.FoodCart;
import com.food.model.UpdateQuantityDTO;
import com.food.service.FoodCartService;

@RestController
@RequestMapping("/foodcart")
public class FoodCartController {

    @Autowired
    private FoodCartService foodCartService;

    @PostMapping("/addtocart/{customerId}")
    public ResponseEntity<FoodCart> addItemToCartHandler(@RequestHeader("Authorization") String authorizationHeader,
                                                         @PathVariable("customerId") Integer customerId,
                                                         @RequestParam Integer itemId)
            throws ItemException, CustomerException, LoginException {
        String key = authorizationHeader.replace("Bearer ", "");
        FoodCart foodCart = foodCartService.addItemToCart(key, customerId, itemId);
        return new ResponseEntity<>(foodCart, HttpStatus.OK);
    }


    @PutMapping("/updateQuantity")
    public ResponseEntity<FoodCart> updateItemQuantityHandler(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody UpdateQuantityDTO updateRequest) 
            throws ItemException, CustomerException, FoodCartException, LoginException {

        String key = authorizationHeader.replace("Bearer ", "");
        FoodCart updatedCart = foodCartService.updateItemQuantity(
            key, updateRequest.getCartId(), updateRequest.getItemId(), updateRequest.getQuantity()
        );

        return ResponseEntity.ok(updatedCart);
    }

   

    @DeleteMapping("/item")
    public ResponseEntity<FoodCart> removeItemHandler(@RequestHeader("Authorization") String authorizationHeader,
                                                      @RequestParam Integer cartId,
                                                      @RequestParam Integer itemId)
            throws ItemException, CustomerException, FoodCartException, LoginException {
        String key = authorizationHeader.replace("Bearer ", "");
        FoodCart foodCart = foodCartService.removeItem(key, cartId, itemId);
        return new ResponseEntity<>(foodCart, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<FoodCart> clearCartHandler(@RequestHeader("Authorization") String authorizationHeader,
                                                     @RequestParam Integer cartId)
            throws ItemException, CustomerException, FoodCartException, LoginException {
        String key = authorizationHeader.replace("Bearer ", "");
        FoodCart foodCart = foodCartService.removeCart(key, cartId);
        return new ResponseEntity<>(foodCart, HttpStatus.OK);
    }
}
