package com.pockorder.controller.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mybatis.pagination.Pager;
import com.pockorder.annotation.Logged;
import com.pockorder.domain.Lottery;
import com.pockorder.domain.LotteryTerm;
import com.pockorder.exception.BusiException;
import com.pockorder.exception.DataNotFoundException;
import com.pockorder.service.LotteryService;
import com.pockorder.view.MsgResult;
import com.pockorder.view.PagedResult;

@RestController
@RequestMapping("/lottery")
public class LotteryRestController {

	@Autowired
	private LotteryService lotteryService;
	
    @RequestMapping("/insert")
    @Logged
    public MsgResult insert(@RequestParam(value="customerWx", required=true) String customerWx,
    		@RequestParam(value="customerTel", required=false) String customerTel,
    		@RequestParam(value="isValid", required=false, defaultValue="true") Boolean isValid) {

    	MsgResult msg = new MsgResult();
    	if(lotteryService.insert(customerWx, customerTel, isValid) != null) {
    		msg.setMsg("增加成功！");
    	} else {
    		msg.setMsg("增加失败！");
    	}
    	return msg;
    }
    /**
     * 抽奖人列表
     * @return
     */
    @RequestMapping("/currentlist")
	public PagedResult<Lottery> getCurrentLotteryList(@RequestParam(value="page", required=false, defaultValue="1") Integer page,
    		@RequestParam(value="rows", required=false, defaultValue="10") Integer rows) {

    	Pager pager = new Pager(page, rows);
        List<Lottery> result = lotteryService.getCurrentLotteryList(pager);
        return new PagedResult<Lottery>(result, pager);
	}
    /**
     * 历史记录
     * @return
     */
    @RequestMapping("/lotterytermlist")
	public PagedResult<LotteryTerm> getLotteryTermList(@RequestParam(value="page", required=false, defaultValue="1") Integer page,
    		@RequestParam(value="rows", required=false, defaultValue="10") Integer rows) {

    	Pager pager = new Pager(page, rows);
        List<LotteryTerm> result = lotteryService.getLotteryTermList(pager);
        return new PagedResult<LotteryTerm>(result, pager);
	}
    

	/**
	 * 删除Lottery
	 * @param lotteryID
	 * @return
	 */
    @RequestMapping("/delete")
	public MsgResult delete(@RequestParam(value="lotteryID", required=true) String lotteryID) {
    	MsgResult msg = new MsgResult();
    	try {
			lotteryService.delete(lotteryID);
			msg.setMsg("删除成功！");
		} catch (DataNotFoundException e) {
			msg.setErrMsg(e.getMessage());
		}
    	return msg;
	}
	/**
	 * 修改Lottery
	 * @param lotteryID
	 * @param customerWx
	 * @param customerTel
	 * @return
	 */
    @RequestMapping("/update")
	public MsgResult updateLottery(@RequestParam(value="lotteryID", required=true) String lotteryID,
			@RequestParam(value="customerWx", required=true) String customerWx,
			@RequestParam(value="customerTel", required=true) String customerTel) throws DataNotFoundException {
    	MsgResult msg = new MsgResult();
    	try {
    		lotteryService.updateLottery(lotteryID, customerWx, customerTel);
			msg.setMsg("修改成功！");
		} catch (DataNotFoundException e) {
			msg.setErrMsg(e.getMessage());
		}
    	return msg;
	}
    /**
     * 按ID查Lottery
     * @param lotteryID
     * @return
     */
    @RequestMapping("/detail")
	public Lottery selectByID(@RequestParam(value="lotteryID", required=true) String lotteryID) {
		return lotteryService.selectByID(lotteryID);
	}
	/**
	 * 更新finish字段
	 * @param lotteryID
	 * @return
	 */
    @RequestMapping("/finish")
    @Logged
	public MsgResult finishLottery(@RequestParam(value="lotteryID", required=true) String lotteryID) throws DataNotFoundException {
    	MsgResult msg = new MsgResult();
    	try {
    		lotteryService.finishLottery(lotteryID);
			msg.setMsg("修改成功！");
		} catch (DataNotFoundException e) {
			msg.setErrMsg(e.getMessage());
		} catch (BusiException e) {
			msg.setErrMsg(e.getMessage());
		}
    	return msg;
	}
	/**
	 * 抽奖
	 * @return
	 */
    @RequestMapping("/run")
    @Logged
	public MsgResult run(@RequestParam(value="pwd", required=true) String pwd) {
		MsgResult msg = new MsgResult();
		if("pockcj".equals(pwd)) {
	    	try {
				String prizeNo = lotteryService.lottery();
				msg.setMsg("抽奖成功！中奖号为：" + prizeNo);
			} catch (BusiException e) {
				msg.setErrMsg(e.getMessage());
			}
		} else {
			msg.setErrMsg("密码错误!");
		}
    	return msg;
	}
}
