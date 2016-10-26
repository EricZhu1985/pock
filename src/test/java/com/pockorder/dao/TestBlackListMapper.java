package com.pockorder.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.mybatis.pagination.Pager;
import com.pockorder.BaseTest;
import com.pockorder.domain.BlackList;


public class TestBlackListMapper extends BaseTest {

	@Autowired
	private BlackListMapper mapper;
	
	@Test
	public void testSelectByTel() {
		BlackList bl = mapper.selectByTel("111");
		Assert.assertEquals("pp文", bl.getCustomerName());
		
	}

	@Test
	public void testSelect() {
		List<BlackList> list = mapper.select("111");
		Assert.assertEquals(2, list.size());
		list = mapper.select(null);
		Assert.assertEquals(4, list.size());
		Pager pager = new Pager(2, 1);
		list = mapper.select(null, pager);
		Assert.assertEquals(1, list.size());
		Assert.assertEquals(4, pager.getTotal());
	}

	@Test
	public void testInsert() {
		BlackList blackList = new BlackList();
		blackList.setContent("不吃早餐");
		blackList.setCustomerName("shw");
		blackList.setCustomerTel("555");
		Assert.assertEquals(1, mapper.insert(blackList));
		
		BlackList bl = mapper.selectByTel("555");
		Assert.assertEquals("shw", bl.getCustomerName());
		Assert.assertEquals("不吃早餐", bl.getContent());
	}

	@Test
	public void testDelete() {
		Assert.assertEquals(1, mapper.delete("1"));
	}

}
