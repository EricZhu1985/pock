package com.pockorder.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.mybatis.pagination.Pager;
import com.pockorder.BaseTest;
import com.pockorder.dao.LotteryMapper;
import com.pockorder.domain.Lottery;
import com.pockorder.domain.LotteryTerm;
import com.pockorder.exception.BusiException;
import com.pockorder.exception.DataNotFoundException;

public class TestLotteryService extends BaseTest {

	@Autowired
	private LotteryService service;

	@Autowired
	private LotteryMapper mapper;
	
	@Test
	public void testInsert() {
		String newNo = mapper.getNewLotteryNo();
		Lottery lottery = service.insert("测试插入", "66666", false);
		Assert.assertEquals(newNo, lottery.getLotteryNo());
	}

	@Test
	public void testGetCurrentLotteryList() {
		List<Lottery> list = service.getCurrentLotteryList(new Pager());
		Assert.assertEquals(2, list.size());
		Lottery lottery = list.get(0);
		Assert.assertEquals("222", lottery.getCustomerTel());
		Assert.assertEquals("目前抽奖2", lottery.getCustomerWx());
	}

	@Test
	public void testGetLotteryTermList() {
		List<LotteryTerm> list = service.getLotteryTermList(new Pager());
		Assert.assertEquals(1, list.size());
		
	}

	@Test(expected = DataNotFoundException.class)
	public void testDelete() throws DataNotFoundException {
		service.delete("4");
		Assert.assertEquals(1, service.getCurrentLotteryList(new Pager()).size());
		service.delete("100");
		
	}

	@Test(expected = DataNotFoundException.class)
	public void testUpdateLottery() throws DataNotFoundException {
		service.updateLottery("4", "测试用户后", "6666");
		Lottery lottery = service.selectByID("4");
		Assert.assertEquals("测试用户后", lottery.getCustomerWx());
		Assert.assertEquals("6666", lottery.getCustomerTel());
		
		service.updateLottery("100", "", "");
	}

	@Test
	public void testSelectByID() {
		Lottery lottery = service.selectByID("4");
		Assert.assertEquals("目前抽奖1", lottery.getCustomerWx());
		Assert.assertEquals("111", lottery.getCustomerTel());
	}

	@Test(expected = DataNotFoundException.class)
	public void testFinishLottery() throws DataNotFoundException, BusiException {
		service.finishLottery("4");
		Lottery lottery = service.selectByID("4");
		Assert.assertTrue(lottery.getIsFinish());
		
		service.finishLottery("100");
	}

	@Test
	public void testLottery() throws BusiException {
		service.lottery();
		Lottery lottery = service.selectByID("4");
		Assert.assertTrue(lottery.getGetLottery());
		Assert.assertEquals(2, service.getLotteryTermList(new Pager()).size());
	}

}
