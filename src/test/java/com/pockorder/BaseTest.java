package com.pockorder;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations={"classpath:spring-mybatis-test.xml", "classpath:spring-beans.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class BaseTest extends AbstractTransactionalJUnit4SpringContextTests {

	@Test
	public void blank() {
		
	}
}
