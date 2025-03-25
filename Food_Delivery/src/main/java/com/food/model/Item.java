package com.food.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Item {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer itemId;
	
	@NotNull(message = "Name is require")
	private String itemName;
	
    
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    @NotNull(message = "Category is required")
    private Category category;


	private Integer quantity;
	private Double cost;
	private Double itemTotal;

	@JsonIgnore
	@ManyToMany(targetEntity = Restaurant.class)
	private List<Restaurant> restaurants = new ArrayList<>();

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
	    System.out.println("Setting category: " + (category != null ? category.getCategoryName() : "NULL"));
	    this.category = category;
	}


	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public List<Restaurant> getRestaurants() {
		return restaurants;
	}

	public void setRestaurants(List<Restaurant> restaurants) {
		this.restaurants = restaurants;
	}

	public Double getItemTotal() {
		return itemTotal;
	}

	public void setItemTotal(Double itemTotal) {
		this.itemTotal = itemTotal;
	}



	

	
	

}
