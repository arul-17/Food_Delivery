package com.food.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.food.exception.BillException;
import com.food.exception.CustomerException;
import com.food.exception.LoginException;
import com.food.exception.OrderDetailsException;
import com.food.model.Bill;
import com.food.service.BillService;

@RestController
@RequestMapping("/bill")
public class BillController {

    @Autowired
    private BillService billService;

    @PostMapping("/add")
    public ResponseEntity<Bill> generateBill(@RequestHeader("Authorization") String authorizationHeader,
                                             @RequestParam Integer customerId,
                                             @RequestParam Integer orderId)
            throws BillException, CustomerException, OrderDetailsException, LoginException {
  
        String key = authorizationHeader.replace("Bearer ", "");
        
        // Generate the bill
        Bill myBill = billService.generateBill(key, customerId, orderId);
        return new ResponseEntity<>(myBill, HttpStatus.CREATED);
    }


    @GetMapping("/view/{billId}")
    public ResponseEntity<Bill> viewBill(@RequestHeader("Authorization") String authorizationHeader,
                                         @PathVariable Integer billId)
            throws BillException, LoginException {
        String key = authorizationHeader.replace("Bearer ", "");

        // View the bill
        Bill bill = billService.viewBill(key, billId);
        return new ResponseEntity<>(bill, HttpStatus.ACCEPTED);
    }
}
