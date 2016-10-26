package com.pockorder.domain;

public class PaymentStatement {
	
	public String getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public PaymentAccount getAccount() {
		return account;
	}
	public void setAccount(PaymentAccount account) {
		this.account = account;
	}
	
	private String paymentDate;
	private String amount;
	private PaymentAccount account;

}
