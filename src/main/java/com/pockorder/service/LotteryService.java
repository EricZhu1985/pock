package com.pockorder.service;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mybatis.pagination.Pager;
import com.pockorder.dao.LotteryMapper;
import com.pockorder.domain.Lottery;
import com.pockorder.domain.LotteryTerm;
import com.pockorder.exception.BusiException;
import com.pockorder.exception.DataNotFoundException;

@Service
@Transactional
public class LotteryService {

	@Autowired
	private LotteryMapper lotteryMapper;
	
	public Lottery insert(String lotteryNo, String customerWx, String customerTel, Boolean isValid) {
		Lottery lottery = new Lottery();
		lottery.setLotteryNo(lotteryNo);
		lottery.setCustomerWx(customerWx);
		lottery.setCustomerTel(customerTel);
		lottery.setIsValid(isValid);
		lotteryMapper.insert(lottery);
		return lottery;
	}

	public Lottery insert(String customerWx, String customerTel, Boolean isValid) {
		return insert(lotteryMapper.getNewLotteryNo(), customerWx, customerTel, isValid);
	}
	
	public List<Lottery> getCurrentLotteryList(Pager pager) {
		return lotteryMapper.getCurrentLotteryList(pager);
	}
	
	public List<LotteryTerm> getLotteryTermList(Pager pager) {
		return lotteryMapper.selectLotteryTermWithPrize(pager);
	}

	/**
	 * 删除Lottery
	 * @param lotteryID
	 * @return
	 */
	public void delete(String lotteryID) throws DataNotFoundException {
		if(lotteryMapper.delete(lotteryID) == 0) {
			throw new DataNotFoundException();
		}
	}
	/**
	 * 修改Lottery
	 * @param lotteryID
	 * @param customerWx
	 * @param customerTel
	 * @return
	 */
	public void updateLottery(String lotteryID, String customerWx, String customerTel) throws DataNotFoundException {
		if(lotteryMapper.updateLottery(lotteryID, customerWx, customerTel) == 0) {
			throw new DataNotFoundException();
		}
	}
    /**
     * 按ID查Lottery
     * @param lotteryID
     * @return
     */
	public Lottery selectByID(String lotteryID) {
		return lotteryMapper.selectByID(lotteryID);
	}
	/**
	 * 更新finish字段
	 * @param lotteryID
	 * @return
	 */
	public void finishLottery(String lotteryID) throws DataNotFoundException, BusiException {
		Lottery lottery = lotteryMapper.selectByID(lotteryID);
		if(lottery == null) {
			throw new DataNotFoundException();
		}
		
		if(lottery.getIsFinish()) {
			throw new BusiException("该奖券已经领奖！");
		}
		lotteryMapper.finishLottery(lotteryID);
	}
	/**
	 * 新建LotteryTerm
	 * @return 中奖号
	 */
	public String lottery() throws BusiException {
		LotteryTerm term = lotteryMapper.selectCurrentLotteryTerm();
		String lotteryTermNo = term.getLotteryTermNo();
		
		List<Lottery> lotteryList = lotteryMapper.selectValidLotteryByTermNo(lotteryTermNo);
		if(lotteryList.size() == 0) {
			throw new BusiException("还未有人参与抽奖！");
		}
		Random ran = new Random(new Date().getTime());
		int prizeIndex = ran.nextInt(lotteryList.size());
		
		Lottery prizeLottery = lotteryList.get(prizeIndex);
		lotteryMapper.updateGetLottery(prizeLottery.getLotteryID());
		lotteryMapper.endLotteryTerm(lotteryTermNo);
		lotteryMapper.newLotteryTerm();
		
		return prizeLottery.getLotteryNo();
	}
}
