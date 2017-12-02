package com.pockorder.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.pockorder.domain.InventoryItem;
import com.pockorder.service.InventoryService;

@Controller 
public class InventoryController {

	@Autowired
	private InventoryService inventoryService;
	
    @RequestMapping("/updateinventoryitemquantitypage.do")
    public ModelAndView updateInventoryItemQuantityPage(HttpServletRequest request){
    	String inventoryItemId = request.getParameter("inventoryItemId");
    	InventoryItem ii = inventoryService.getInventoryItemById(inventoryItemId);
    	request.setAttribute("inventoryItem", ii);
        return new ModelAndView("updateinventoryitemquantity");  // 采用重定向方式跳转页面
    }

    @RequestMapping("/updateinventoryitemquantity.do")
    public ModelAndView updateInventoryItemQuantity(HttpServletRequest request){
    	String inventoryItemId = request.getParameter("inventoryItemId");
    	int quantity = 0;
    	try {
    		quantity = Integer.parseInt(request.getParameter("quantity"));
        	int result = inventoryService.updateInventoryItemQuantity(inventoryItemId, quantity);
        	if(result <= 0) {
        		request.setAttribute("resultMsg", "修改失败，未知错误");
        	}
    	} catch(NumberFormatException nfe) {
    		request.setAttribute("resultMsg", "修改失败，输入数字格式错误");
    	}
    	InventoryItem ii = inventoryService.getInventoryItemById(inventoryItemId);
    	request.setAttribute("inventoryItem", ii);
        return new ModelAndView("updateinventoryitemquantity");  // 采用重定向方式跳转页面
    }
}
