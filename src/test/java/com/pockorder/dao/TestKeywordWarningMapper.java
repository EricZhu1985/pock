package com.pockorder.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.pockorder.BaseTest;
import com.pockorder.domain.KeywordWarning;



public class TestKeywordWarningMapper extends BaseTest {

	@Autowired
	private KeywordWarningMapper mapper;
	
	@Test
	public void testSelect() {
		KeywordWarning kw = new KeywordWarning();
		kw.setKeyword("abcd");
		kw.setAmount(3);
		kw.setMemo("aaa");
		mapper.insert(kw);
		List<KeywordWarning> l = mapper.select("bc", "a");
		Assert.assertEquals(1, l.size());

		List<KeywordWarning> l2 = mapper.select("bb", "a");
		Assert.assertEquals(0, l2.size());
	}


}
