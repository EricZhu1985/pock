package com.pockorder.domain;

public class KeywordWarning {

	private String keywordWarningID;
	private String keyword;
	private Integer amount;
	private String memo;
	
	public String getKeywordWarningID() {
		return keywordWarningID;
	}
	public void setKeywordWarningID(String keywordWarningID) {
		this.keywordWarningID = keywordWarningID;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
}
