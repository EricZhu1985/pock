package com.pockorder.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.pockorder.BaseTest;
import com.pockorder.domain.Inventory;
import com.pockorder.domain.InventoryItem;

public class TestInventoryMapper extends BaseTest {

	@Autowired
	private InventoryMapper mapper;
	
	@Test
	public void testSelectInventory() {
		List<Inventory> list = mapper.selectInventory("", "", "", "");
		Assert.assertEquals(1, list.size());
		Assert.assertEquals(5, list.get(0).getQuantity());
		Assert.assertEquals("测试", list.get(0).getInventoryItem().getName());
		
	}
	
	@Test
	public void testSelectInventoryItem() {
		List<InventoryItem> list = mapper.selectInventoryItem("试", "es", "");
		Assert.assertEquals(1, list.size());
		Assert.assertEquals("测试", list.get(0).getName());
		
	}
	
	@Test
	public void testInsertInventoryItem() {
		InventoryItem ii = new InventoryItem();
		ii.setName("总统无盐黄油");
		ii.setUnit("箱(200克*40块)");
		mapper.insertInventoryItem(ii);
		Assert.assertNotNull(ii.getInventoryItemId());
		InventoryItem ii2 = mapper.selectInventoryItemById(ii.getInventoryItemId());
		Assert.assertEquals("总统无盐黄油", ii2.getName());
	}

	@Test
	public void testUpdateInventoryItem() {
		InventoryItem ii = new InventoryItem();
		ii.setInventoryItemId("1");
		ii.setName("测试2");
		ii.setUnit("盒");
		mapper.updateInventoryItem(ii);
		InventoryItem ii2 = mapper.selectInventoryItemById("1");
		Assert.assertEquals("测试2", ii2.getName());
	}

	@Test
	public void testDeleteInventoryItem() {
		InventoryItem ii = new InventoryItem();
		ii.setName("总统无盐黄油");
		ii.setUnit("箱(200克*40块)");
		mapper.insertInventoryItem(ii);
		List<InventoryItem> list = mapper.selectInventoryItem("", "", "");
		Assert.assertEquals(2, list.size());
		mapper.deleteInventoryItem(ii.getInventoryItemId());
		list = mapper.selectInventoryItem("", "", "");
		Assert.assertEquals(1, list.size());
	}
	
	@Test
	public void testInsertInventory() {
		InventoryItem item = mapper.selectInventoryItemById("1");
		
		Inventory i = new Inventory();
		i.setInventoryItem(item);
		i.setQuantity(10);
		i.setPosition(1);
		mapper.insertInventory(i);

		List<Inventory> list = mapper.selectInventory("", "", "", "");
		Assert.assertEquals(2, list.size());
	}
	
	@Test
	public void testUpdateInventory() {
		Inventory inventory = mapper.selectInventoryById("1");
		inventory.setQuantity(99);
		mapper.updateInventory(inventory);
		Inventory i2 = mapper.selectInventoryById("1");
		Assert.assertEquals(99, i2.getQuantity());
	}
	
	@Test
	public void testDeleteInventory() {
		mapper.deleteInventory("1");
		List<Inventory> list = mapper.selectInventory("", "", "", "");
		Assert.assertEquals(0, list.size());
	}
}
