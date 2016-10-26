package com.pockorder.view;

import com.pockorder.domain.Order;

public class OrderResult extends MsgResult {

	private Order order;

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
	
	
}
