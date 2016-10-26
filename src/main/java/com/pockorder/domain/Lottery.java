package com.pockorder.domain;

public class Lottery {

	private String lotteryID;
	private String lotteryNo;
	private String customerWx;
	private String customerTel;
	private String recordDate;
	//private LotteryTerm term;
	private Boolean getLottery;
	private Boolean isValid;
	private Boolean isFinish;
	private String finishTime;
	public String getLotteryID() {
		return lotteryID;
	}
	public void setLotteryID(String lotteryID) {
		this.lotteryID = lotteryID;
	}
	public String getLotteryNo() {
		return lotteryNo;
	}
	public void setLotteryNo(String lotteryNo) {
		this.lotteryNo = lotteryNo;
	}
	public String getCustomerWx() {
		return customerWx;
	}
	public void setCustomerWx(String customerWx) {
		this.customerWx = customerWx;
	}
	public String getCustomerTel() {
		return customerTel;
	}
	public void setCustomerTel(String customerTel) {
		this.customerTel = customerTel;
	}
	public String getRecordDate() {
		return recordDate;
	}
	public void setRecordDate(String recordDate) {
		this.recordDate = recordDate;
	}
	/*
	public LotteryTerm getTerm() {
		return term;
	}
	public void setTerm(LotteryTerm term) {
		this.term = term;
	}
	*/
	public Boolean getGetLottery() {
		return getLottery;
	}
	public void setGetLottery(Boolean getLottery) {
		this.getLottery = getLottery;
	}
	public Boolean getIsValid() {
		return isValid;
	}
	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}
	public Boolean getIsFinish() {
		return isFinish;
	}
	public void setIsFinish(Boolean isFinish) {
		this.isFinish = isFinish;
	}
	public String getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}
	/*
	public String getLotteryStart() {
		if(term != null) {
			return term.getLotteryStart();
		}
		return null;
	}
	public String getLotteryEnd() {
		if(term != null) {
			return term.getLotteryEnd();
		}
		return null;
	}
	*/
}
