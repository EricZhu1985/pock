package com.pockorder.dao;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.pockorder.BaseTest;
import com.pockorder.domain.Log;


public class TestLogMapper extends BaseTest {

	@Autowired
	private LogMapper mapper;
	
	@Test
	public void testInsert() {
		Log log = new Log();
		log.setContent("测试日志");
		log.setIp("127.0.0.1");
		log.setLogID("333");
		log.setOperator("测试用户");
		log.setSessionID("s1111");
		log.setUrl("测试url");
		
		Assert.assertEquals(1, mapper.insert(log));
		
		
	}

}
