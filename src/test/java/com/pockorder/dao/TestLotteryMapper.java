package com.pockorder.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.pockorder.BaseTest;
import com.pockorder.domain.Lottery;
import com.pockorder.domain.LotteryTerm;

public class TestLotteryMapper extends BaseTest {

	@Autowired
	private LotteryMapper mapper;
	
	private Lottery createLottery(String lotteryNo, String customerWx, String customerTel, Boolean isValid) {
		Lottery lottery = new Lottery();
		lottery.setCustomerTel(customerTel);
		lottery.setCustomerWx(customerWx);
		lottery.setLotteryNo(lotteryNo);
		lottery.setIsValid(isValid);
		return lottery;
	}
	
	@Test
	public void testGetNewLotteryNo() {
		Assert.assertEquals("6", mapper.getNewLotteryNo());
	}
	
	@Test
	public void testInsert() {
		
		int sizeBefore = mapper.getCurrentLotteryList().size();
		
		Lottery validLottery = createLottery("101", "测试1", "18922260939", true);
		Assert.assertEquals(mapper.insert(validLottery), 1);
		Assert.assertNotNull(validLottery.getLotteryID());

		Lottery invalidLottery = createLottery("102", "测试2", null, false);
		Assert.assertEquals(mapper.insert(invalidLottery), 1);
		Assert.assertNotNull(invalidLottery.getLotteryID());

		int sizeAfter = mapper.getCurrentLotteryList().size();
		Assert.assertEquals(sizeBefore + 2, sizeAfter);
	}

	@Test
	public void testGetCurrentLotteryList() {
		List<Lottery> list = mapper.getCurrentLotteryList();
		Assert.assertEquals(2, list.size());
	}

	@Test
	public void testSelectLotteryTermWithPrize() {
		List<LotteryTerm> list = mapper.selectLotteryTermWithPrize();
		Assert.assertEquals(1, list.size());
	}

	@Test
	public void testUpdateLottery() {
		String nameAfter = "修改测试后";
		String telAfter = "222";
		Assert.assertEquals(1, mapper.updateLottery("2", nameAfter, telAfter));

		Lottery lottery = mapper.selectByID("2");
		Assert.assertEquals(nameAfter, lottery.getCustomerWx());
		Assert.assertEquals(telAfter, lottery.getCustomerTel());
	}

	@Test
	public void testSelectByID() {
		Lottery lottery = mapper.selectByID("1");
		Assert.assertEquals("init", lottery.getCustomerWx());
		Assert.assertEquals("18922260939", lottery.getCustomerTel());
	}

	@Test
	public void testFinishLottery() {
		mapper.finishLottery("1");
		Lottery lottery = mapper.selectByID("1");
		Assert.assertTrue(lottery.getIsFinish());
	}

	@Test
	public void testSelectValidLotteryByTermNo() {
		Assert.assertEquals(1, mapper.selectValidLotteryByTermNo("2").size());
	}

	@Test
	public void testUpdateGetLottery() {
		Assert.assertEquals(1, mapper.updateGetLottery("4"));
		Lottery lottery = mapper.selectByID("4");
		Assert.assertTrue(lottery.getGetLottery());
	}

	@Test
	public void testEndLotteryTerm() {
		Assert.assertEquals(1, mapper.endLotteryTerm("2"));
		//Assert.assertEquals(2, mapper.selectLotteryTermWithPrize().size());
	}

	@Test
	public void testNewLotteryTerm() {
		Assert.assertEquals(1,  mapper.newLotteryTerm());
	}

	@Test
	public void testSelectCurrentLotteryTerm() {
		LotteryTerm term = mapper.selectCurrentLotteryTerm();
		Assert.assertEquals("2", term.getLotteryTermNo());
		Assert.assertEquals("2016-01-01", term.getLotteryStart());
	}

	@Test
	public void testDelete() {

		Assert.assertEquals(mapper.delete("4"), 1);

		int sizeAfter = mapper.getCurrentLotteryList().size();
		Assert.assertEquals(1, sizeAfter);
	}
}
