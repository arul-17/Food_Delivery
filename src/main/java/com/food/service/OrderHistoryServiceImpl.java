package com.food.service;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.food.exception.CustomerException;
import com.food.exception.LoginException;
import com.food.exception.OrderHistoryException;
import com.food.model.Bill;
import com.food.model.CurrentUserSession;
import com.food.model.Customer;
import com.food.model.OrderDetails;
import com.food.model.OrderHistory;
import com.food.repository.BillRepo;
import com.food.repository.CurrentUserSessionRepo;
import com.food.repository.CustomerRepo;
import com.food.repository.OrderHistoryRepo;
@Service
public class OrderHistoryServiceImpl implements OrderHistoryService {

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private OrderHistoryRepo orderHistoryRepo;
    @Autowired
    private BillRepo billRepo;
    @Autowired
    private CurrentUserSessionRepo currSession;

    public OrderHistory getOrderHistoryById(String key, Integer orderHisId) throws OrderHistoryException, LoginException {
        // Validate the session and user
        CurrentUserSession currentSession = currSession.findByPrivateKey(key);
        if (currentSession == null) {
            throw new LoginException("Login required or session expired!");
        }

        // Get customer ID from the session
        Integer sessionCustomerId = currentSession.getCustomerId();
        if (sessionCustomerId == null) {
            throw new LoginException("Customer ID is not available in the session.");
        }

      
        Optional<OrderHistory> orderHistoryOpt = orderHistoryRepo.findById(orderHisId);
        if (orderHistoryOpt.isEmpty()) {
            throw new OrderHistoryException("Order history not found for order ID: " + orderHisId);
        }

        OrderHistory orderHistory = orderHistoryOpt.get();

       
        if (!orderHistory.getCustomerId().equals(sessionCustomerId)) {
            throw new OrderHistoryException("You are not authorized to view this order history");
        }

        return orderHistory;
    }

    @Override
    public List<OrderHistory> getOrderHistoryByCustomerId(String key, Integer customerId)
            throws OrderHistoryException, LoginException, CustomerException {
        System.out.println("Received token: " + key);
        CurrentUserSession currSess = currSession.findByPrivateKey(key);

        if (currSess == null) {
            System.out.println("No session found for token: " + key);
            throw new LoginException("Login required");
        }

        System.out.println("Session found: " + currSess);

        Integer sessionCustomerId = currSess.getCustomerId();
        if (sessionCustomerId == null) {
            System.out.println("No customer ID in session for token: " + key);
            throw new LoginException("Customer ID is not available in the session.");
        }

        if (!sessionCustomerId.equals(customerId)) {
            throw new OrderHistoryException("You are not authorized to view this customer's order history");
        }

        List<OrderHistory> orderHistoryList = orderHistoryRepo.findByCustomerId(customerId);
        if (orderHistoryList.isEmpty()) {
            throw new OrderHistoryException("Order history not found for customer ID: " + customerId);
        }

        return orderHistoryList;
    }
    


    @Override
    public List<OrderHistory> getAllOrderHistory(String key) throws OrderHistoryException, LoginException {
        CurrentUserSession currSess = currSession.findByPrivateKey(key);
        if (currSess != null && currSess.getRole().equalsIgnoreCase("admin")) {
            List<OrderHistory> orderHistoryList = orderHistoryRepo.findAll();
            if (orderHistoryList.isEmpty())
                throw new OrderHistoryException("Order history list is empty!");
            return orderHistoryList;
        } else {
            throw new LoginException("Admin login required");
        }
    }

	
}
