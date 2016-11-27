package com.pockorder.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.mybatis.pagination.Pager;
import com.pockorder.domain.BlackList;

@Repository
public interface BlackListMapper {

	/**
	 * Select blacklist by customer tel
	 * @param customerTel
	 * @return
	 */
	public BlackList selectByTel(@Param("customerTel")String customerTel);
	
	public List<BlackList> select(@Param("customerTel")String customerTel);

	public List<BlackList> select(@Param("customerTel")String customerTel, Pager pager);
	
	public int insert(BlackList blackList);
	
	public int delete(@Param("blackListID")String blackListID);
}
