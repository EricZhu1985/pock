package com.pockorder.service;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pockorder.constant.PaymentConst;
import com.pockorder.dao.PaymentMapper;
import com.pockorder.domain.Branch;
import com.pockorder.domain.Payment;
import com.pockorder.domain.PaymentAccount;
import com.pockorder.domain.PaymentStatement;

@Service
@Transactional
public class PaymentService {

	@Autowired
	private PaymentMapper paymentMapper;
	@Autowired
	private MemberService memberService;
	

	public List<PaymentAccount> getPaymentAccountByBranchID(String branchID) {
		return paymentMapper.getPaymentAccountByBranchID(branchID);
	}

	public List<PaymentAccount> getPaymentAccountByBranchID(String branchID, boolean all) {
		List<PaymentAccount> list = branchID == null ? new ArrayList<PaymentAccount>() : this.getPaymentAccountByBranchID(branchID);
		if(all) {
			PaymentAccount nullPayment = new PaymentAccount();
			nullPayment.setPaymentAccountName("全部");
			nullPayment.setPaymentAccountID("");
			Branch b = new Branch();
			b.setBranchID("");
			nullPayment.setBranch(b);
			list.add(0, nullPayment);
		}
		return list;
	}

	public List<Payment> getPaymentByOrderID(String orderID) {
		return paymentMapper.getPaymentByOrderID(orderID);
	}
	
	public Payment payOrder(String orderID, Integer paid, String paymentAccountID) {
		Payment payment = new Payment();
		payment.setMemo("");
		payment.setPaid(paid);
		payment.setAccountID(paymentAccountID);
		payment.setPaymentTypeID(PaymentConst.PAYMENT_TYPE_ID_OTHER);
		
		paymentMapper.insertPaymentOrder(payment.getPaymentID(), orderID);
		paymentMapper.insert(payment);
		return payment;
	}
	
	public List<PaymentStatement> getPaymentStatement(String start, String end, String accountID, String branchID) {
		return paymentMapper.getPaymentStatement(start, end, accountID, branchID);
	}
	
	public int payOther(Integer paid, String paymentAccountID, String memo,
			String tel, Float bonusPoint) {
		Payment payment = new Payment();
		payment.setMemo(memo);
		payment.setPaid(paid);
		payment.setAccountID(paymentAccountID);
		payment.setPaymentTypeID(PaymentConst.PAYMENT_TYPE_ID_OTHER);
		
		if(tel != null && !"".equals(tel)) {
			memberService.updateBonusPointByTel(tel, bonusPoint / 10, memo);
		}
		
		return paymentMapper.insert(payment);
	}
}
