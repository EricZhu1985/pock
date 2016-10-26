package com.pockorder.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.pockorder.domain.Payment;
import com.pockorder.domain.PaymentAccount;
import com.pockorder.domain.PaymentStatement;

@Repository
public interface PaymentMapper {

	public List<PaymentAccount> getPaymentAccountByBranchID(@Param("branchID")String branchID);
	
	public int insert(Payment payment);
	
	public int insertPaymentOrder(@Param("paymentID")String paymentID, @Param("orderID")String orderID);
	
	public List<Payment> getPaymentByOrderID(@Param("orderID")String orderID);
	
	public Integer getOrderPaid(@Param("orderID")String orderID);
	
	public List<PaymentStatement> getPaymentStatement(@Param("start")String start, @Param("end")String end, @Param("accountID")String accountID, @Param("branchID")String branchID);
}
