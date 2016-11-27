package com.pockorder.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pockorder.dao.MemberMapper;
import com.pockorder.domain.CHistory;
import com.pockorder.domain.Member;
import com.pockorder.exception.DataNotFoundException;

@Service
public class MemberService {

	@Autowired
	private MemberMapper memberMapper;
	/**
	 * @param name
	 * @param password
	 * @return
	 */
	public Member getMemberByTel(String tel) throws DataNotFoundException {
		Member member = memberMapper.selectByTel(tel);
		if(member == null) {
			throw new DataNotFoundException();
		}
		return member;
	}
	
	public int updateBonusPoint(String memberID, Float bonusPoint, String content) {
		//insert c history
		CHistory history = new CHistory();
		history.setBonusPoint(bonusPoint);
		history.setMemberID(memberID);
		history.setContent(content);
		
		memberMapper.insertHistory(history);
		
		return memberMapper.updateBonusPoint(memberID, bonusPoint);
	}

	public int updateBonusPointByTel(String tel, Float bonusPoint, String content) {
		Member member = memberMapper.selectByTel(tel);
		if(member == null) {
			return 0;
		}
		return this.updateBonusPoint(member.getMemberID(), bonusPoint, content);
	}
	public Member insert(String tel) {
		Member member = new Member();
		member.setTel(tel);
		member.setType(Member.TYPE_COMMON);
		memberMapper.insert(member);
		return member;
	}
}
