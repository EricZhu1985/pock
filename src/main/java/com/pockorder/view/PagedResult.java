package com.pockorder.view;

import java.util.List;

import com.mybatis.pagination.Pager;

public class PagedResult<E> {

	private List<E> rows;
	private int total;
	
	public PagedResult( List<E> result, Pager pager) {
		this.rows = result;
		this.total = pager.getTotal();
	}

	public List<E> getRows() {
		return rows;
	}

	public void setRows(List<E> rows) {
		this.rows = rows;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
	
}
