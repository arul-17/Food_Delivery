package com.food.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.food.exception.CustomerException;
import com.food.exception.LoginException;
import com.food.model.Customer;
import com.food.service.CustomerService;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/add")
    public ResponseEntity<String> addCustomer(@RequestBody Customer customer) throws CustomerException {
        Customer returnCustomer = customerService.addCustomer(customer);
        return new ResponseEntity<>("Customer added successfully! ID: " + returnCustomer.getCustomerId(), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Customer> updateCustomer(@RequestHeader("Authorization") String authorizationHeader,
                                                   @RequestBody Customer customer)
            throws CustomerException, LoginException {
        String key = authorizationHeader.replace("Bearer ", "");
        Customer returnCustomer = customerService.updateCustomer(key, customer);
        return ResponseEntity.ok(returnCustomer);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCustomerById(@RequestHeader("Authorization") String authorizationHeader,
                                                     @PathVariable("id") Integer customerId)
            throws CustomerException, LoginException {
        String key = authorizationHeader.replace("Bearer ", "");
        Customer deletedCustomer = customerService.removeCustomerById(key, customerId);
        return ResponseEntity.ok("Customer deleted successfully! ID: " + deletedCustomer.getCustomerId());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteCustomer(@RequestHeader("Authorization") String authorizationHeader,
                                                 @RequestBody Customer customer)
            throws CustomerException, LoginException {
        String key = authorizationHeader.replace("Bearer ", "");
        Customer deletedCustomer = customerService.removeCustomer(key, customer);
        return ResponseEntity.ok("Customer deleted successfully! ID: " + deletedCustomer.getCustomerId());
    }
    

    @GetMapping("/all/{id}")
    public ResponseEntity<Customer> viewCustomer(@RequestHeader("Authorization") String authorizationHeader,
                                                 @PathVariable("id") Integer id)
            throws CustomerException, LoginException {
        String key = authorizationHeader.replace("Bearer ", "");
        Customer customer = customerService.viewCustomer(key, id);
        return ResponseEntity.ok(customer);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Customer>> viewAllCustomers(@RequestHeader("Authorization") String authorizationHeader)
            throws CustomerException, LoginException {
        String key = authorizationHeader.replace("Bearer ", "");
        List<Customer> customers = customerService.viewAllCustomers(key);
        return ResponseEntity.ok(customers);
    }
}
