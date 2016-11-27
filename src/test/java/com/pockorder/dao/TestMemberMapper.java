package com.pockorder.dao;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.pockorder.BaseTest;
import com.pockorder.domain.Member;


public class TestMemberMapper extends BaseTest {

	@Autowired
	private MemberMapper mapper;
	
	@Test
	public void testSelectByTel() {
		Member m = mapper.selectByTel("13570919190");
		Assert.assertEquals(Float.valueOf(124), m.getBonusPoint());
	}
	
	@Test
	public void testInsert() {
		Member m = new Member();
		m.setTel("18922260939");
		m.setType(Member.TYPE_COMMON);
		mapper.insert(m);
		Assert.assertEquals("18922260939", mapper.selectByTel("18922260939").getTel());
	}

	@Test
	public void testUpdateBonusPoint() {
		mapper.updateBonusPoint("13570919190", -100f);
		Assert.assertEquals(Float.valueOf(24), mapper.selectByTel("13570919190").getBonusPoint());
	}

}
