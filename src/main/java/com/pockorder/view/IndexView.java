package com.pockorder.view;

import java.util.List;

import com.pockorder.domain.Order;

public class IndexView {
	
	private String weijujuNo;
	private int totalPrice;
	private List<Order> rows;
	
	public String getWeijujuNo() {
		return weijujuNo;
	}
	public void setWeijujuNo(String weijujuNo) {
		this.weijujuNo = weijujuNo;
	}
	public int getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}
	public List<Order> getRows() {
		return rows;
	}
	public void setRows(List<Order> rows) {
		this.rows = rows;
	}
}
