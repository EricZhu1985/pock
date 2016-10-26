package com.pockorder.domain;

public class LotteryTerm {

	private String lotteryTermNo;
	private String lotteryStart;
	private String lotteryEnd;
	private Boolean isEnd;
	private Lottery prizedLottery;
	
	public String getLotteryTermNo() {
		return lotteryTermNo;
	}
	public void setLotteryTermNo(String lotteryTermNo) {
		this.lotteryTermNo = lotteryTermNo;
	}
	public String getLotteryStart() {
		if(lotteryStart != null && lotteryStart.length() > 19) {
			lotteryStart = lotteryStart.substring(0, 19);
		}
		return lotteryStart;
	}
	public void setLotteryStart(String lotteryStart) {
		this.lotteryStart = lotteryStart;
	}
	public String getLotteryEnd() {
		if(lotteryEnd != null && lotteryEnd.length() > 19) {
			lotteryEnd = lotteryEnd.substring(0, 19);
		}
		return lotteryEnd;
	}
	public void setLotteryEnd(String lotteryEnd) {
		this.lotteryEnd = lotteryEnd;
	}
	public Boolean getIsEnd() {
		return isEnd;
	}
	public void setIsEnd(Boolean isEnd) {
		this.isEnd = isEnd;
	}
	public Lottery getPrizedLottery() {
		return prizedLottery;
	}
	public void setPrizedLottery(Lottery prizedLottery) {
		this.prizedLottery = prizedLottery;
	}
}
