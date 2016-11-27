package com.pockorder.domain;

public class Order {
	
	public static Integer HASBONUSPOINT_NO = 0;
	public static Integer HASBONUSPOINT_YES = 1;
	
	private String orderID;
	private String orderNo;
	private String orderDate;
	private String orderTime;
	private Integer price;
	private String content;
	private Integer paid;
	private Customer customer;
	private String recorder;
	private String deliverAddress;
	private String recordTime;
	private Integer finished;
	private Integer complete;
	private Integer deleteFlag;
	private String memo;
	private String weijujuNo;
	private Branch branch;
	private String remark;
	private Integer deliverFlag;
	private Integer hasBonusPoint;
	
	public String getOrderID() {
		return orderID;
	}
	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public String getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getPaid() {
		return paid;
	}
	public void setPaid(Integer paid) {
		this.paid = paid;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public String getRecorder() {
		return recorder;
	}
	public void setRecorder(String recorder) {
		this.recorder = recorder;
	}
	public String getDeliverAddress() {
		return deliverAddress;
	}
	public void setDeliverAddress(String deliverAddress) {
		this.deliverAddress = deliverAddress;
	}
	public String getRecordTime() {
		return recordTime;
	}
	public void setRecordTime(String recordTime) {
		this.recordTime = recordTime;
	}
	public Integer getFinished() {
		return finished;
	}
	public void setFinished(Integer finished) {
		this.finished = finished;
	}
	public Integer getComplete() {
		return complete;
	}
	public void setComplete(Integer complete) {
		this.complete = complete;
	}
	public Integer getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getWeijujuNo() {
		return weijujuNo;
	}
	public void setWeijujuNo(String weijujuNo) {
		this.weijujuNo = weijujuNo;
	}
	public Branch getBranch() {
		return branch;
	}
	public void setBranch(Branch branch) {
		this.branch = branch;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getDeliverFlag() {
		return deliverFlag;
	}
	public void setDeliverFlag(Integer deliverFlag) {
		this.deliverFlag = deliverFlag;
	}
	public void setCustomerName(String customerName) {
		if(customer == null) {
			customer = new Customer();
		}
		customer.setName(customerName);
	}
	public void setCustomerTel(String customerTel) {
		if(customer == null) {
			customer = new Customer();
		}
		customer.setTel(customerTel);
	}
	public void setCustomerWx(String customerWx) {
		if(customer == null) {
			customer = new Customer();
		}
		customer.setWx(customerWx);
	}
	public void setBranchID(String branchID) {
		if(branch == null) {
			branch = new Branch();
		}
		branch.setBranchID(branchID);
	}
	public void setBranchName(String branchName) {
		if(branch == null) {
			branch = new Branch();
		}
		branch.setBranchName(branchName);
	}
	
	public String getCustomerWx() {
		if(customer == null) {
			return null;
		}
		return customer.getWx();
	}

	public String getCustomerName() {
		if(customer == null) {
			return null;
		}
		return customer.getName();
	}
	public String getCustomerTel() {
		if(customer == null) {
			return null;
		}
		return customer.getTel();
	}
	public Integer getHasBonusPoint() {
		return hasBonusPoint;
	}
	public void setHasBonusPoint(Integer hasBonusPoint) {
		this.hasBonusPoint = hasBonusPoint;
	}
}
