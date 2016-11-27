package com.pockorder.domain;

public class Member {

	public static final int TYPE_COMMON = 1;
	public static final int TYPE_VIP = 2;
	
	private String memberID;
	private String tel;
	private int type;
	private Float bonusPoint;
	
	public String getMemberID() {
		return memberID;
	}
	public void setMemberID(String memberID) {
		this.memberID = memberID;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Float getBonusPoint() {
		return bonusPoint;
	}
	public void setBonusPoint(Float bonusPoint) {
		this.bonusPoint = bonusPoint;
	}
	
}
