package com.pockorder.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.pockorder.domain.Order;
import com.pockorder.service.OrderService;

@Controller 
public class OrderController {

	@Autowired
	private OrderService orderService;
	
    @RequestMapping("/detail.do")
    public ModelAndView testLogin2(@RequestParam(value="orderID")String orderID, HttpServletRequest request){
    	Order order = orderService.getOrder(orderID);
    	request.setAttribute("order", order);
        return new ModelAndView("detail");  // 采用重定向方式跳转页面
    }
}
