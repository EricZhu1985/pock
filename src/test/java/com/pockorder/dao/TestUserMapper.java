package com.pockorder.dao;


import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.pockorder.BaseTest;
import com.pockorder.util.MD5Util;

public class TestUserMapper extends BaseTest {

	@Autowired
	private UserMapper mapper;
	
	@Test
	public void testGetUserByName() {
		Assert.assertNotNull(mapper.getUserByName("pock"));
	}

	@Test
	public void testGetUserByPwd() {
		Assert.assertNotNull(mapper.getUserByPwd("pock", MD5Util.getMd5("pock")));
		Assert.assertNull(mapper.getUserByPwd("pock", MD5Util.getMd5("111")));
	}

}
