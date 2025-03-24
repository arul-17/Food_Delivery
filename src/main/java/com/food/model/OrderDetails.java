package com.food.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "orderHistories")  // Prevent infinite recursion in toString
@Entity
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;
    private LocalDateTime orderDate;

    @OneToOne(targetEntity = FoodCart.class)
    private FoodCart foodCart;
    private String orderStatus;
    private Double taxAmount;
    private Double deliveryFee;
    private Double totalAmount;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinColumn(name = "bill_id", referencedColumnName = "billId")
    private Bill bill; // Link back to the Bill
  

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<OrderHistory> orderHistories;

    

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public LocalDateTime getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDateTime orderDate) {
		this.orderDate = orderDate;
	}

	public FoodCart getFoodCart() {
		return foodCart;
	}

	public void setFoodCart(FoodCart foodCart) {
		this.foodCart = foodCart;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Double getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(Double taxAmount) {
		this.taxAmount = taxAmount;
	}

	public Double getDeliveryFee() {
		return deliveryFee;
	}

	public void setDeliveryFee(Double deliveryFee) {
		this.deliveryFee = deliveryFee;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public List<OrderHistory> getOrderHistories() {
		return orderHistories;
	}

	public void setOrderHistories(List<OrderHistory> orderHistories) {
		this.orderHistories = orderHistories;
	}

	public Bill getBill() {
		return bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}
	
    
    // Getters and Setters (omitted for brevity)
}
