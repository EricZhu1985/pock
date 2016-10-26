package com.pockorder.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.mybatis.pagination.Pager;
import com.pockorder.domain.Lottery;
import com.pockorder.domain.LotteryTerm;

@Repository
public interface LotteryMapper {
	/**
	 * 新建Lottery
	 * @param lottery
	 * @return
	 */
	public int insert(Lottery lottery);
	/**
	 * 获取新建Lottery编号
	 * @return
	 */
	public String getNewLotteryNo();
	/**
	 * 获取当前抽奖参与人
	 * @return
	 */
	public List<Lottery> getCurrentLotteryList(Pager pager);

	public List<Lottery> getCurrentLotteryList();
	/**
	 * 查询LotteryTerm列表，并带上中奖信息
	 * @return
	 */
	public List<LotteryTerm> selectLotteryTermWithPrize(Pager pager);

	public List<LotteryTerm> selectLotteryTermWithPrize();
	/**
	 * 删除Lottery
	 * @param lotteryID
	 * @return
	 */
	public int delete(String lotteryID);
	/**
	 * 修改Lottery
	 * @param lotteryID
	 * @param customerWx
	 * @param customerTel
	 * @return
	 */
	public int updateLottery(@Param("lotteryID") String lotteryID,
			@Param("customerWx") String customerWx,
			@Param("customerTel") String customerTel);
    /**
     * 按ID查Lottery
     * @param lotteryID
     * @return
     */
	public Lottery selectByID(String lotteryID);
	/**
	 * 更新finish字段
	 * @param lotteryID
	 * @return
	 */
	public int finishLottery(String lotteryID);
	/**
	 * 查询指定Term的Lottery
	 * @param lotteryTermNo
	 * @return
	 */
	public List<Lottery> selectValidLotteryByTermNo(String lotteryTermNo);
	/**
	 * 更新getLottery字段
	 * @param lotteryID
	 * @return
	 */
	public int updateGetLottery(String lotteryID);
	/**
	 * 更新end字段
	 * @param lotteryTermNo
	 * @return
	 */
	public int endLotteryTerm(String lotteryTermNo);
	/**
	 * 新建LotteryTerm
	 * @return
	 */
	public int newLotteryTerm();
	
	public LotteryTerm selectCurrentLotteryTerm();
}
