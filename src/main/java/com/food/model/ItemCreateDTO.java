package com.food.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ItemCreateDTO {
	private String itemName;
	private String catergoryName;
	private Double cost;
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getCatergoryName() {
		return catergoryName;
	}
	public void setCatergoryName(String catergoryName) {
		this.catergoryName = catergoryName;
	}
	public Double getCost() {
		return cost;
	}
	public void setCost(Double cost) {
		this.cost = cost;
	}
	
	
}
