package com.pockorder.controller.rest;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pockorder.annotation.Logged;
import com.pockorder.constant.SessionConst;
import com.pockorder.domain.Payment;
import com.pockorder.domain.PaymentAccount;
import com.pockorder.domain.PaymentStatement;
import com.pockorder.domain.User;
import com.pockorder.service.PaymentService;
import com.pockorder.view.MsgResult;

@RestController
@RequestMapping("/payment")
public class PaymentRestController {

	@Autowired
	private PaymentService paymentService;
	
	@Autowired
	private HttpSession session;
	
	private User getSessionUser() {
		return (User) session.getAttribute(SessionConst.USER);
	}

    @RequestMapping("/getuseraccount")
    public List<PaymentAccount> getUserAccounts() {
	
    	User user = getSessionUser();
		return paymentService.getPaymentAccountByBranchID(user.getBranch().getBranchID());
    }

    @RequestMapping("/getbranchaccount")
    public List<PaymentAccount> getBranchAccounts(@RequestParam(value="branchID", required=false) String branchID,
    		@RequestParam(value="all") String all) {
    	boolean b = false;
    	if(all != null && all.equals("true")) {
    		b = true;
    	}
		return paymentService.getPaymentAccountByBranchID(branchID, b);
    }
    
    @RequestMapping("/getorderpayment")
    public List<Payment> getPaymentByOrderID(@RequestParam(value="orderID", required=true) String orderID) {
    	return paymentService.getPaymentByOrderID(orderID);
    }

    @RequestMapping("/statement")
    public List<PaymentStatement> statement(@RequestParam(value="start", required=false) String start,
    		@RequestParam(value="end", required=false) String end,
    		@RequestParam(value="accountID", required=false) String accountID,
    		@RequestParam(value="branchID", required=false) String branchID) {
    	return paymentService.getPaymentStatement(start, end, accountID, branchID);
    }

    @RequestMapping("/payother")
    @Logged
    public MsgResult payother(@RequestParam(value="paid", required=true) Integer paid,
    		@RequestParam(value="paymentAccountID", required=true) String paymentAccountID,
    		@RequestParam(value="memo", required=false) String memo,
    		@RequestParam(value="tel", required=false) String tel,
    		@RequestParam(value="bonusPoint", required=false) Float bonusPoint) {

    	MsgResult msg = new MsgResult();
		if(paymentService.payOther(paid, paymentAccountID, memo, tel, bonusPoint) > 0) {
    		msg.setMsg("支付成功！");
    	} else {
    		msg.setErrMsg("支付失败！");
    	}
    	return msg;
    }
    

}
