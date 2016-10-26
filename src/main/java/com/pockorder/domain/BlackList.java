package com.pockorder.domain;

public class BlackList {

	private String blackListID;
	private String eventDate;
	private String content;
	private String customerTel;
	private String customerName;
	
	public String getBlackListID() {
		return blackListID;
	}
	public void setBlackListID(String blackListID) {
		this.blackListID = blackListID;
	}
	public String getEventDate() {
		return eventDate;
	}
	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCustomerTel() {
		return customerTel;
	}
	public void setCustomerTel(String customerTel) {
		this.customerTel = customerTel;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
}
