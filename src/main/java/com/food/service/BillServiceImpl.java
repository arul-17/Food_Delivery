package com.food.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.food.exception.BillException;
import com.food.exception.CustomerException;
import com.food.exception.LoginException;
import com.food.exception.OrderDetailsException;
import com.food.model.Bill;
import com.food.model.CurrentUserSession;
import com.food.model.Customer;
import com.food.model.FoodCart;
import com.food.model.Item;
import com.food.model.OrderDetails;
import com.food.model.OrderHistory;
import com.food.repository.BillRepo;
import com.food.repository.CurrentUserSessionRepo;
import com.food.repository.CustomerRepo;
import com.food.repository.FoodCartRepo;
import com.food.repository.ItemRepo;
import com.food.repository.OrderDetailsRepo;
import com.food.repository.OrderHistoryRepo;

@Service
public class BillServiceImpl implements BillService {

	@Autowired
	private ItemRepo itemRepo;

	@Autowired
	private BillRepo billRepo;

	@Autowired
	private OrderDetailsRepo orderDetailRepo;

	@Autowired
	private CustomerRepo cusDAO;

	@Autowired
	private FoodCartRepo foodCartRepo;

	@Autowired
	private CurrentUserSessionRepo currSession;

	@Autowired
	private OrderHistoryRepo orderHistoryRepo;

	@Override
	public Bill generateBill(String key, Integer customerId, Integer orderDetailId)
	        throws CustomerException, OrderDetailsException, LoginException, BillException {

	    // Validate user session
	    CurrentUserSession currSess = currSession.findByPrivateKey(key);
	    if (currSess == null)
	        throw new LoginException("Login required");

	    // Validate order
	    Optional<OrderDetails> optOrderDetails = orderDetailRepo.findById(orderDetailId);
	    if (optOrderDetails.isEmpty())
	        throw new OrderDetailsException("Order details not found...");

	    OrderDetails orderDetails = optOrderDetails.get();
	    if (orderDetails.getOrderStatus().equalsIgnoreCase("completed"))
	        throw new BillException("Bill already generated for this order ID");

	    // Validate customer
	    Optional<Customer> customerOpt = cusDAO.findById(customerId);
	    if (customerOpt.isEmpty())
	        throw new CustomerException("Customer does not exist");

	    FoodCart foodCart = orderDetails.getFoodCart();

	    // Calculate totals
	    double totalCost = 0.0;
	    int totalItems = 0;
	    for (Item item : foodCart.getItemList()) {
	        totalCost += (item.getQuantity() * item.getCost());
	        totalItems += item.getQuantity();
	    }
	    double taxAmount = totalCost * 0.10; // 10% tax
	    double deliveryFee = 20.0;          // Delivery fee
	    double totalAmount = totalCost + taxAmount + deliveryFee;

	    // Create Bill
	    Bill bill = new Bill();
	    bill.setTotalCost(totalCost);
	    bill.setTotalItem(totalItems);
	    bill.setBillDate(LocalDateTime.now());
	    bill.setTaxAmount(taxAmount);
	    bill.setDeliveryFee(deliveryFee);
	    bill.setTotalAmount(totalAmount);

	    // Link Bill to OrderDetails
	    bill.setOrder(orderDetails);
	    orderDetails.setBill(bill);

	   
	    System.out.println("Bill linked to OrderDetails: " + bill.getOrder());
	    System.out.println("OrderDetails linked to Bill: " + orderDetails.getBill());

	    // Save Bill
	    billRepo.save(bill);

	    // Save OrderDetails
	    orderDetails.setOrderStatus("completed");
	    orderDetailRepo.save(orderDetails);

	    // Create and save OrderHistory
	    OrderHistory orderHistory = new OrderHistory();
	    orderHistory.setBill(bill);
	    orderHistory.setOrder(orderDetails);
	    orderHistory.setCustomerId(customerId);
	    orderHistoryRepo.save(orderHistory);

	    return bill;
	}



	@Override
	public Bill updateBill(String key, Bill bill) throws BillException, LoginException {

		CurrentUserSession currSess = currSession.findByPrivateKey(key);
		if (currSess == null)
			throw new LoginException("Login required");

		Optional<Bill> opt = billRepo.findById(bill.getBillId());
		if (opt.isPresent()) {
			return billRepo.save(bill);
		} else {
			throw new BillException("Bill doesn't exists..");
		}
	}

	@Override
	public Bill removeBill(String key, Integer billId) throws BillException, LoginException {

		CurrentUserSession currSess = currSession.findByPrivateKey(key);
		if (currSess == null)
			throw new LoginException("Login required");

		Optional<Bill> opt = billRepo.findById(billId);
		if (opt.isPresent()) {
			Bill bill = opt.get();
			billRepo.delete(bill);
			return bill;
		} else {
			throw new BillException("Bill not found with ID: " + billId);
		}

	}

	@Override
	public Bill viewBill(String key, Integer billId) throws BillException, LoginException {

		CurrentUserSession currSess = currSession.findByPrivateKey(key);
		if (currSess == null)
			throw new LoginException("Login required");

		Optional<Bill> opt = billRepo.findById(billId);
		if (opt.isPresent()) {
			return opt.get();
		} else {
			throw new BillException("Bill not found with ID: " + billId);
		}
	}

}
