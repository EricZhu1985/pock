package com.pockorder.exception;

public class DataNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DataNotFoundException() {
		super("数据不存在！");
	}
}
