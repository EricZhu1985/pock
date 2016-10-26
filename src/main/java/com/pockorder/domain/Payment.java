package com.pockorder.domain;

public class Payment {

	private String paymentID;
	private String paymentDate;
	private String paymentTime;
	private Integer paid;
	private PaymentAccount account;
	private String memo;
	private String paymentTypeID;
	
	public String getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}
	
	public String getPaymentID() {
		return paymentID;
	}
	public void setPaymentID(String paymentID) {
		this.paymentID = paymentID;
	}
	public String getPaymentTime() {
		return paymentTime;
	}
	public void setPaymentTime(String paymentTime) {
		this.paymentTime = paymentTime;
	}
	public Integer getPaid() {
		return paid;
	}
	public void setPaid(Integer paid) {
		this.paid = paid;
	}
	public PaymentAccount getAccount() {
		return account;
	}
	public void setAccount(PaymentAccount account) {
		this.account = account;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public void setAccountID(String accountID) {
		if(account == null) {
			account = new PaymentAccount();
		}
		account.setPaymentAccountID(accountID);
	}
	public String getPaymentTypeID() {
		return paymentTypeID;
	}
	public void setPaymentTypeID(String paymentTypeID) {
		this.paymentTypeID = paymentTypeID;
	}
}
