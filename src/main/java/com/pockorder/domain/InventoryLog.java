package com.pockorder.domain;

public class InventoryLog {

	private String inventoryLogId;
	private int change;
	private InventoryItem inventoryItem;
	private String memo;
	
	public String getInventoryLogId() {
		return inventoryLogId;
	}
	public void setInventoryLogId(String inventoryLogId) {
		this.inventoryLogId = inventoryLogId;
	}
	public int getChange() {
		return change;
	}
	public void setChange(int change) {
		this.change = change;
	}
	public InventoryItem getInventoryItem() {
		return inventoryItem;
	}
	public void setInventoryItem(InventoryItem inventoryItem) {
		this.inventoryItem = inventoryItem;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	
}
