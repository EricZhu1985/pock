package com.pockorder.domain;

public class Inventory {

	private String inventoryId;
	private InventoryItem inventoryItem;
	private int quantity;
	private int position;
	
	public String getInventoryId() {
		return inventoryId;
	}
	public void setInventoryId(String inventoryId) {
		this.inventoryId = inventoryId;
	}
	public InventoryItem getInventoryItem() {
		return inventoryItem;
	}
	public void setInventoryItem(InventoryItem inventoryItem) {
		this.inventoryItem = inventoryItem;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
}
