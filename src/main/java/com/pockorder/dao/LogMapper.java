package com.pockorder.dao;

import org.springframework.stereotype.Repository;

import com.pockorder.domain.Log;

@Repository
public interface LogMapper {

	/**
	 * Select blacklist by customer tel
	 * @param customerTel
	 * @return
	 */
	public int insert(Log log);
}
