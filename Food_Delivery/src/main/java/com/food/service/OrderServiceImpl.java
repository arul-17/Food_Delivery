package com.food.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.food.exception.CustomerException;
import com.food.exception.FoodCartException;
import com.food.exception.ItemException;
import com.food.exception.LoginException;
import com.food.exception.OrderDetailsException;
import com.food.model.Bill;
import com.food.model.CurrentUserSession;
import com.food.model.Customer;
import com.food.model.FoodCart;
import com.food.model.Item;
import com.food.model.OrderDetails;
import com.food.model.OrderHistory;
import com.food.model.Restaurant;
import com.food.repository.BillRepo;
import com.food.repository.CurrentUserSessionRepo;
import com.food.repository.CustomerRepo;
import com.food.repository.OrderDetailsRepo;
import com.food.repository.OrderHistoryRepo;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderDetailsRepo orderRepo;
	@Autowired
	private OrderHistoryRepo orderHistoryRepo;

	@Autowired
	private CustomerRepo customerRepo;
	@Autowired
	private BillRepo billRepo;

	@Autowired
	private CurrentUserSessionRepo currSession;

	@Override
	public OrderDetails addOrder(String key, Integer customerId)
	        throws CustomerException, FoodCartException, LoginException {

	    CurrentUserSession currSess = currSession.findByPrivateKey(key);
	    if (currSess == null)
	        throw new LoginException("Login required");

	  
	    Optional<Customer> optCustomer = customerRepo.findById(customerId);
	    if (optCustomer.isEmpty())
	        throw new CustomerException("Customer does not exist!");
	    Customer customer = optCustomer.get();

	    FoodCart foodCart = customer.getFoodCart();
	    if (foodCart == null || foodCart.getItemList().isEmpty())
	        throw new FoodCartException("Cart is empty!");

	    double itemTotal = 0.0;
	    int totalItems = 0;
	    for (Item item : foodCart.getItemList()) {
	        item.setItemTotal(item.getCost() * item.getQuantity()); // Calculate item total
	        itemTotal += item.getItemTotal();
	        totalItems += item.getQuantity();
	    }

	    double taxAmount = itemTotal * 0.10; // 10% tax
	    double deliveryFee = 20.0; 
	    double totalAmount = itemTotal + taxAmount + deliveryFee;

	    Bill bill = new Bill();
	    bill.setTotalCost(itemTotal);
	    bill.setTotalItem(totalItems);
	    bill.setBillDate(LocalDateTime.now());
	    bill.setTaxAmount(taxAmount);
	    bill.setDeliveryFee(deliveryFee);
	    bill.setTotalAmount(totalAmount);

	    billRepo.save(bill);

	    OrderDetails orderDetails = new OrderDetails();
	    orderDetails.setFoodCart(foodCart);
	    orderDetails.setOrderDate(LocalDateTime.now());
	    orderDetails.setOrderStatus("Pending");
	    orderDetails.setTaxAmount(taxAmount);
	    orderDetails.setDeliveryFee(deliveryFee);
	    orderDetails.setTotalAmount(totalAmount);
	    orderDetails.setBill(bill);
	    bill.setOrder(orderDetails);

	
	    orderRepo.save(orderDetails);
	    
	    OrderHistory orderHistory = new OrderHistory();
	    orderHistory.setOrder(orderDetails);
	    orderHistory.setCustomerId(customerId);
	    orderHistory.setBill(bill);

	    orderHistoryRepo.save(orderHistory);
	    
	    return orderDetails;
	}


	@Override
	public OrderDetails updateOrder(String key, Integer orderId, Integer customerId)
			throws OrderDetailsException, CustomerException, FoodCartException, LoginException {
		
		CurrentUserSession currSess = currSession.findByPrivateKey(key);
		if (currSess == null)
			throw new LoginException("Login required");
		
		Optional<OrderDetails> opt1 = orderRepo.findById(orderId);
		if (opt1.isPresent()) {
			Optional<Customer> opt = customerRepo.findById(customerId);
			if (opt.isEmpty())
				throw new CustomerException("customer does not exist..!");
			Customer customer = opt.get();
			FoodCart foodCart = customer.getFoodCart();
			List<Item> itemList = foodCart.getItemList();
			if (itemList.isEmpty())
				throw new FoodCartException("cart is empty..!");
			List<OrderDetails> orderDetailsList = orderRepo.findAll();
			boolean flag = true;
			OrderDetails orderDetails = null;
			for (int i = 0; i < orderDetailsList.size(); i++) {
				OrderDetails exOrderDetails = orderDetailsList.get(i);
				if (exOrderDetails.getFoodCart().getCartId() == foodCart.getCartId()) {
					exOrderDetails.setFoodCart(foodCart);
					orderDetails = exOrderDetails;
					flag = false;
				}
			}

			if (flag) {
				orderDetails = new OrderDetails();
				orderDetails.setFoodCart(foodCart);
				orderDetails.setOrderDate(LocalDateTime.now());
				orderDetails.setOrderStatus("pending..!");
			}
			orderRepo.save(orderDetails);
			return orderDetails;
		} else {
			throw new OrderDetailsException("order does not exist...!");
		}
	}

	@Override
	public OrderDetails removeOrder(String key, Integer orderId) throws OrderDetailsException, LoginException {
		
		CurrentUserSession currSess = currSession.findByPrivateKey(key);
		if (currSess == null)
			throw new LoginException("Login required");
		
		Optional<OrderDetails> opt = orderRepo.findById(orderId);
		if (opt.isPresent()) {
			OrderDetails deletedOrder = opt.get();
			orderRepo.delete(deletedOrder);
			return deletedOrder;
		} else {
			throw new OrderDetailsException("order does not exist...!");
		}
	}

	public OrderDetails viewOrder(String key, Integer orderId) throws OrderDetailsException, LoginException {
	    
	    OrderDetails orderDetails = orderRepo.findByOrderId(orderId);
	    
	    if (orderDetails == null) {
	        throw new OrderDetailsException("Order not found.");
	    }
	    
	
	    FoodCart foodCart = orderDetails.getFoodCart();
	    
	    double itemTotal = 0.0;
	    for (Item item : foodCart.getItemList()) {
	        item.setItemTotal(item.getCost() * item.getQuantity()); 
	        itemTotal += item.getItemTotal(); 
	    }

	    
	    double taxAmount = itemTotal * 0.10; 
	    double deliveryFee = 20.0; 
	    double totalAmount = itemTotal + taxAmount + deliveryFee;

	    orderDetails.setTaxAmount(taxAmount);
	    orderDetails.setDeliveryFee(deliveryFee);
	    orderDetails.setTotalAmount(totalAmount);


	    
	    return orderDetails;
	}
	@Override
	public OrderDetails updateOrderStatus(String key, Integer orderId, String orderStatus) throws OrderDetailsException, LoginException {

	    CurrentUserSession currSess = currSession.findByPrivateKey(key);
	    if (currSess == null)
	        throw new LoginException("Login required");

	    Optional<OrderDetails> optOrder = orderRepo.findById(orderId);
	    if (optOrder.isEmpty()) {
	        throw new OrderDetailsException("Order not found with id: " + orderId);
	    }

	    OrderDetails order = optOrder.get();
	    order.setOrderStatus(orderStatus);

	    return orderRepo.save(order);
	}


}
