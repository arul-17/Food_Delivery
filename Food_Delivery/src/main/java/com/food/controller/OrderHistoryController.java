package com.food.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.food.exception.CustomerException;
import com.food.exception.LoginException;
import com.food.exception.OrderHistoryException;
import com.food.model.CurrentUserSession;
import com.food.model.OrderDetails;
import com.food.model.OrderHistory;
import com.food.service.OrderHistoryService;
@RestController
@RequestMapping("/order")
public class OrderHistoryController {

    @Autowired
    private OrderHistoryService orderHisService;

    @GetMapping("/view/{orderHisId}")
    public ResponseEntity<OrderHistory> getOrderHistoryById(@RequestHeader("Authorization") String authorizationHeader,
            @PathVariable("orderHisId") Integer orderHisId)
            throws OrderHistoryException, LoginException {
        String key = authorizationHeader.replace("Bearer ", "");
        OrderHistory orderHistory = orderHisService.getOrderHistoryById(key, orderHisId);
        return new ResponseEntity<>(orderHistory, HttpStatus.OK);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<OrderHistory>> getOrderHistoryByCustomerId(@RequestHeader("Authorization") String authorizationHeader,
            @PathVariable("customerId") Integer customerId)
            throws OrderHistoryException, LoginException, CustomerException {
        String key = authorizationHeader.replace("Bearer ", "");
        List<OrderHistory> orderHistoryList = orderHisService.getOrderHistoryByCustomerId(key, customerId);
        return new ResponseEntity<>(orderHistoryList, HttpStatus.OK);
    }


    @GetMapping("/all")
    public ResponseEntity<List<OrderHistory>> getAllOrderHistory(@RequestHeader("Authorization") String authorizationHeader)
            throws OrderHistoryException, LoginException {
        String key = authorizationHeader.replace("Bearer ", "");
        List<OrderHistory> orderHistoryList = orderHisService.getAllOrderHistory(key);
        return new ResponseEntity<>(orderHistoryList, HttpStatus.OK);  
        }
}
