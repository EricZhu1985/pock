package com.pockorder.domain;

public class PaymentAccount {
	
	private String paymentAccountID;
	private String paymentAccountName;
	private Branch branch;
	
	public Branch getBranch() {
		return branch;
	}
	public void setBranch(Branch branch) {
		this.branch = branch;
	}
	public String getPaymentAccountID() {
		return paymentAccountID;
	}
	public void setPaymentAccountID(String paymentAccountID) {
		this.paymentAccountID = paymentAccountID;
	}
	public String getPaymentAccountName() {
		return paymentAccountName;
	}
	public void setPaymentAccountName(String paymentAccountName) {
		this.paymentAccountName = paymentAccountName;
	}
	
}
