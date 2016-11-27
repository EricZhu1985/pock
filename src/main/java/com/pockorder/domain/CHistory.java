package com.pockorder.domain;

public class CHistory {
	private String cHistoryID;
	private String memberID;
	private Float bonusPoint;
	private String content;
	
	public String getcHistoryID() {
		return cHistoryID;
	}
	public void setcHistoryID(String cHistoryID) {
		this.cHistoryID = cHistoryID;
	}
	public String getMemberID() {
		return memberID;
	}
	public void setMemberID(String memberID) {
		this.memberID = memberID;
	}
	public Float getBonusPoint() {
		return bonusPoint;
	}
	public void setBonusPoint(Float bonusPoint) {
		this.bonusPoint = bonusPoint;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
}
