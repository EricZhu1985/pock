package com.pockorder.controller.rest;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mybatis.pagination.Pager;
import com.pockorder.annotation.Logged;
import com.pockorder.domain.Inventory;
import com.pockorder.domain.InventoryItem;
import com.pockorder.service.InventoryService;
import com.pockorder.view.MsgResult;
import com.pockorder.view.PagedResult;

@RestController
@RequestMapping("/inventory")
public class InventoryRestController {

	@Autowired
	private InventoryService inventoryService;

	@Autowired  
	private HttpSession session;  

	/******************************** inventory operation ***********************************/
    @RequestMapping("/inventorylist")
    public PagedResult<Inventory> inventoryList(HttpSession session,
    		@RequestParam(value="itemName", required=false) String itemName,
    		@RequestParam(value="position", required=false) String position,
    		@RequestParam(value="quantity", required=false) String quantity,
    		@RequestParam(value="orderBy", required=false) String orderBy,
    		@RequestParam(value="page", required=false, defaultValue="1") Integer page,
    		@RequestParam(value="rows", required=false, defaultValue="10") Integer rows) {

    	Pager pager = new Pager(page, rows);
    	int qua = -1;
    	try {
    		qua = Integer.parseInt(quantity);
    	} catch(NumberFormatException e) {
    		
    	}
    	List<Inventory> list = inventoryService.getInventoryList(itemName, position, qua, orderBy, pager);
    	return new PagedResult<Inventory>(list, pager);
    }

    @RequestMapping("/addinventory")
    @Logged
    public MsgResult addInventory(HttpSession session,
    		@RequestParam(value="inventoryItemId", required=true) String inventoryItemId,
    		@RequestParam(value="position", required=true) String position,
    		@RequestParam(value="quantity", required=false, defaultValue="") String quantity) {

    	MsgResult msg = new MsgResult();
    	try {
    		int pos = Integer.parseInt(position);
    		int qua = Integer.parseInt(quantity);
    		
        	if(inventoryService.addInventory(inventoryItemId, pos, qua) >= 1) {
        		msg.setMsg("增加成功！");
        	} else {
        		msg.setErrMsg("增加失败！");
        	}
    	} catch(NumberFormatException e) {
    		msg.setErrMsg("输入参数有误");
    	}
    	
    	return msg;
    }
/*
    @RequestMapping("/updateinventory")
    @Logged
    public MsgResult updateInventory(HttpSession session,
    		@RequestParam(value="inventoryId", required=true) String inventoryId,
    		@RequestParam(value="name", required=true) String name,
    		@RequestParam(value="unit", required=true) String unit,
    		@RequestParam(value="memo", required=false, defaultValue="") String memo) {

    	MsgResult msg = new MsgResult();
    	if(inventoryService.updateInventoryItem(inventoryId, name, unit, memo) >= 1) {
    		msg.setMsg("修改成功！");
    	} else {
    		msg.setErrMsg("修改失败！");
    	}
    	
    	return msg;
    }*/

    @RequestMapping("/deleteinventory")
    @Logged
    public MsgResult deleteInventory(HttpSession session,
    		@RequestParam(value="inventoryId", required=true) String inventoryId) {

    	MsgResult msg = new MsgResult();
    	if(inventoryService.deleteInventory(inventoryId) >= 0) {
    		msg.setMsg("删除成功！");
    	} else {
    		msg.setErrMsg("删除失败！");
    	}
    	
    	return msg;
    }

    @RequestMapping("/inventorybyid")
    @Logged
    public Inventory inventoryById(HttpSession session,
    		@RequestParam(value="inventoryId", required=true) String inventoryId) {
    	return inventoryService.getInventoryById(inventoryId);
    }
    
	/******************************** inventory item operation ***********************************/
    @RequestMapping("/inventoryitemlist")
    public PagedResult<InventoryItem> inventoryItemList(HttpSession session,
    		@RequestParam(value="name", required=false) String name,
    		@RequestParam(value="memo", required=false) String memo,
    		@RequestParam(value="orderBy", required=false) String orderBy,
    		@RequestParam(value="page", required=false, defaultValue="1") Integer page,
    		@RequestParam(value="rows", required=false, defaultValue="10") Integer rows) {

    	Pager pager = new Pager(page, rows);
    	List<InventoryItem> list = inventoryService.getInventoryItemList(name, memo, orderBy, pager);
    	return new PagedResult<InventoryItem>(list, pager);
    }

    @RequestMapping("/addinventoryitem")
    @Logged
    public MsgResult addInventoryItem(HttpSession session,
    		@RequestParam(value="name", required=true) String name,
    		@RequestParam(value="unit", required=true) String unit,
    		@RequestParam(value="memo", required=false, defaultValue="") String memo,
    	    @RequestParam(value="quantity", required=true, defaultValue="0") String quantity) {

    	MsgResult msg = new MsgResult();
    	int qua = 0;
    	try {
    		qua = Integer.parseInt(quantity);
    	} catch(NumberFormatException e) {
    		qua = 0;
    	}
    	if(inventoryService.addInventoryItem(name, unit, memo, qua) >= 1) {
    		msg.setMsg("增加成功！");
    	} else {
    		msg.setErrMsg("增加失败！");
    	}
    	
    	return msg;
    }

    @RequestMapping("/updateinventoryitem")
    @Logged
    public MsgResult updateInventoryItem(HttpSession session,
    		@RequestParam(value="inventoryItemId", required=true) String inventoryItemId,
    		@RequestParam(value="name", required=true) String name,
    		@RequestParam(value="unit", required=true) String unit,
    		@RequestParam(value="memo", required=false, defaultValue="") String memo,
    		@RequestParam(value="quantity", required=true, defaultValue="0") String quantity) {

    	MsgResult msg = new MsgResult();
    	int qua = 0;
    	try {
    		qua = Integer.parseInt(quantity);
    	} catch(NumberFormatException e) {
    		qua = 0;
    	}

    	if(inventoryService.updateInventoryItem(inventoryItemId, name, unit, memo, qua) >= 1) {
    		msg.setMsg("修改成功！");
    	} else {
    		msg.setErrMsg("修改失败！");
    	}
    	
    	return msg;
    }
    

    @RequestMapping("/deleteinventoryitem")
    @Logged
    public MsgResult deleteInventoryItem(HttpSession session,
    		@RequestParam(value="inventoryItemId", required=true) String inventoryItemId) {

    	MsgResult msg = new MsgResult();
    	if(inventoryService.deleteInventoryItem(inventoryItemId) >= 0) {
    		msg.setMsg("删除成功！");
    	} else {
    		msg.setErrMsg("删除失败！");
    	}
    	
    	return msg;
    }

    @RequestMapping("/inventoryitembyid")
    @Logged
    public InventoryItem inventoryItemById(HttpSession session,
    		@RequestParam(value="inventoryItemId", required=true) String inventoryItemId) {
    	return inventoryService.getInventoryItemById(inventoryItemId);
    }

}
