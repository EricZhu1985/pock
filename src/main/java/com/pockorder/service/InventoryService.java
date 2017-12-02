package com.pockorder.service;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mybatis.pagination.Pager;
import com.pockorder.dao.InventoryMapper;
import com.pockorder.domain.Inventory;
import com.pockorder.domain.InventoryItem;

@Service
@Transactional 
public class InventoryService {

	@Autowired
	private InventoryMapper inventoryMapper;
	/**
	 * Get user by name and password, password will be encode by MD5 algorithm
	 * @param name
	 * @param password
	 * @return
	 */
	public List<InventoryItem> getInventoryItemList(String name, String memo,String orderBy, Pager pager) {

		return inventoryMapper.selectInventoryItem(name, memo, orderBy, pager);
	}
	
	public int addInventoryItem(String name, String unit, String memo, int quantity) {
		InventoryItem inventoryItem = new InventoryItem();
		inventoryItem.setName(name);
		inventoryItem.setUnit(unit);
		inventoryItem.setMemo(memo);
		inventoryItem.setQuantity(quantity);
		
		return inventoryMapper.insertInventoryItem(inventoryItem);
	}
	
	public int updateInventoryItem(String inventoryItemId, String name, String unit, String memo, int quantity) {
		InventoryItem inventoryItem = inventoryMapper.selectInventoryItemById(inventoryItemId);
		inventoryItem.setName(name);
		inventoryItem.setUnit(unit);
		inventoryItem.setMemo(memo);
		inventoryItem.setQuantity(quantity);
		
		return inventoryMapper.updateInventoryItem(inventoryItem);
	}

	public int updateInventoryItemQuantity(String inventoryItemId, int quantity) {
		InventoryItem inventoryItem = inventoryMapper.selectInventoryItemById(inventoryItemId);
		inventoryItem.setQuantity(quantity);
		
		return inventoryMapper.updateInventoryItem(inventoryItem);
	}
	
	
	public int deleteInventoryItem(String inventoryItemId) {
		return inventoryMapper.deleteInventoryItem(inventoryItemId);
	}

	public List<Inventory> getInventoryList(String itemName, String position, int quantity, String orderBy) {
		return inventoryMapper.selectInventory(itemName, position, Integer.valueOf(quantity).toString(), orderBy);
	}
	
	public List<Inventory> getInventoryList(String itemName, String position, int quantity, String orderBy, Pager pager) {
		return inventoryMapper.selectInventory(itemName, position, Integer.valueOf(quantity).toString(), orderBy, pager);
	}
	
	public InventoryItem getInventoryItemById(String inventoryItemId) {
		return inventoryMapper.selectInventoryItemById(inventoryItemId);
	}
	
	public int addInventory(String inventoryItemId, int position, int quantity) {
		Inventory inventory = new Inventory();
		InventoryItem inventoryItem = inventoryMapper.selectInventoryItemById(inventoryItemId);
		inventory.setInventoryItem(inventoryItem);
		inventory.setPosition(position);
		inventory.setQuantity(quantity);
		return inventoryMapper.insertInventory(inventory);
	}
	
	public int updateInventory(String inventoryId, int quantity, int position) {
		Inventory inventory = inventoryMapper.selectInventoryById(inventoryId);
		inventory.setPosition(position);
		inventory.setQuantity(quantity);
		return inventoryMapper.updateInventory(inventory);
	}
	
	public int deleteInventory(String inventoryId) {
		return inventoryMapper.deleteInventory(inventoryId);
	}
	
	public Inventory getInventoryById(String inventoryId) {
		return inventoryMapper.selectInventoryById(inventoryId);
	}
}
