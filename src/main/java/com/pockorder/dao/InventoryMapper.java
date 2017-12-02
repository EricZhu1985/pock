package com.pockorder.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.mybatis.pagination.Pager;
import com.pockorder.domain.Inventory;
import com.pockorder.domain.InventoryItem;

@Repository
public interface InventoryMapper {
	/**
	 * 增加物品
	 * @param inventoryItem
	 * @return
	 */
	public int insertInventoryItem(InventoryItem inventoryItem);
	/**
	 * 修改物品
	 * @param inventoryItem
	 * @return
	 */
	public int updateInventoryItem(InventoryItem inventoryItem);
	/**
	 * 删除物品
	 * @param inventoryItemId
	 * @return
	 */
	public int deleteInventoryItem(@Param("inventoryItemId")String inventoryItemId);
	/**
	 * 主键查找物品
	 * @param inventoryItemId
	 * @return
	 */
	public InventoryItem selectInventoryItemById(@Param("inventoryItemId")String inventoryItemId);
	/**
	 * 查找物品
	 * @param itemName
	 * @param position
	 * @param quantity
	 * @return
	 */
	public List<InventoryItem> selectInventoryItem(@Param("name")String name, @Param("memo")String memo, @Param("orderBy")String orderBy);
	
	public List<InventoryItem> selectInventoryItem(@Param("name")String name, @Param("memo")String memo, @Param("orderBy")String orderBy, Pager pager);
	/**
	 * 增加仓库物品
	 * @param inventoryItemId
	 * @return
	 */
	public int insertInventory(Inventory inventory);
	/**
	 * 修改仓库物品
	 * @param inventory
	 * @return
	 */
	public int updateInventory(Inventory inventory);
	/**
	 * 删除仓库物品
	 * @param inventory
	 * @return
	 */
	public int deleteInventory(@Param("inventoryId")String inventoryId);
	/**
	 * 主键查找仓库物品
	 * @param inventory
	 * @return
	 */
	public Inventory selectInventoryById(@Param("inventoryId")String inventoryId);
	/**
	 * 查找仓库物品
	 * @param itemName
	 * @param position
	 * @param quantity
	 * @return
	 */
	public List<Inventory> selectInventory(@Param("itemName")String itemName,
			@Param("position")String position, @Param("quantity")String quantity, @Param("orderBy")String orderBy);

	public List<Inventory> selectInventory(@Param("itemName")String itemName,
			@Param("position")String position, @Param("quantity")String quantity, @Param("orderBy")String orderBy, Pager pager);
}
