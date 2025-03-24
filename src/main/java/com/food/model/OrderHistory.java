package com.food.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
public class OrderHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderHistoryId;

    // Many-to-one relationship with OrderDetails (each OrderHistory is associated with one OrderDetails)
    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "orderId") 
    @JsonIgnore
    private OrderDetails order;

    @NotNull(message = "Customer id required")
    private Integer customerId;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bill_bill_id", referencedColumnName = "billId")
    private Bill bill;


	public Integer getOrderHistoryId() {
		return orderHistoryId;
	}

	public void setOrderHistoryId(Integer orderHistoryId) {
		this.orderHistoryId = orderHistoryId;
	}

	public OrderDetails getOrder() {
		return order;
	}

	public void setOrder(OrderDetails order) {
		this.order = order;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public Bill getBill() {
		return bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}
    
    
}
