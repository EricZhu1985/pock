package com.pockorder.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.pockorder.domain.LotteryTerm;
import com.pockorder.service.LotteryService;

@Controller 
public class LotteryController {

	@Autowired
	private LotteryService lotteryService;
	
    @RequestMapping("/lotteryresult.do")
    public ModelAndView lotteryResult(HttpServletRequest request){
    	LotteryTerm lt = lotteryService.getLatestPrizedLottery();
    	request.setAttribute("lotteryTerm", lt);
        return new ModelAndView("lotteryresult");  // 采用重定向方式跳转页面
    }
}
