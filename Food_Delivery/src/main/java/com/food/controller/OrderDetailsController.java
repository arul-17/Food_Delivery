package com.food.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.food.exception.CustomerException;
import com.food.exception.FoodCartException;
import com.food.exception.LoginException;
import com.food.exception.OrderDetailsException;
import com.food.model.OrderDetails;
import com.food.model.OrderStatusDTO;
import com.food.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderDetailsController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/add/{customerId}")
    public ResponseEntity<OrderDetails> saveOrderDetails(@RequestHeader("Authorization") String authorizationHeader,
                                                          @PathVariable("customerId") Integer customerId)
            throws CustomerException, FoodCartException, LoginException {
        String key = authorizationHeader.replace("Bearer ", "");
        OrderDetails savedOrder = orderService.addOrder(key, customerId);
        return new ResponseEntity<>(savedOrder, HttpStatus.ACCEPTED);
    }

    @PutMapping("/updateStatus/{orderId}")
    public ResponseEntity<OrderDetails> updateOrderStatus(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable("orderId") Integer orderId,
            @RequestBody OrderStatusDTO orderStatusDTO) throws OrderDetailsException, LoginException {

        String key = authorizationHeader.replace("Bearer ", "");

      
        OrderDetails updatedOrder = orderService.updateOrderStatus(key, orderId, orderStatusDTO.getOrderStatus());

        return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
    }

    @GetMapping("/bill/{orderId}")
    public ResponseEntity<OrderDetails> confirmBill(@RequestHeader("Authorization") String authorizationHeader,
                                                    @PathVariable("orderId") Integer orderId)
            throws OrderDetailsException, LoginException {
        String key = authorizationHeader.replace("Bearer ", "");
        OrderDetails orderDetails = orderService.viewOrder(key, orderId);
        if (orderDetails == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body(null);  
        }

        return ResponseEntity.ok(orderDetails);
    }

}