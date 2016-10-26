package com.mybatis.pagination;

import org.apache.ibatis.session.RowBounds;

public class Pager extends RowBounds {

	private int total;
	private int page;
	
	public Pager() {
		super();
		this.page = 1;
	}
	
	public Pager(int page, int pageSize) {
		super((page - 1) * pageSize, pageSize);
		this.page = page;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getPage() {
		return page;
	}
	
	
}
