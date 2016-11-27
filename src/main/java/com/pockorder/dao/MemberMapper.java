package com.pockorder.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.pockorder.domain.CHistory;
import com.pockorder.domain.Member;

@Repository
public interface MemberMapper {

	/**
	 * Select member by tel
	 * @param tel
	 * @return
	 */
	public Member selectByTel(@Param("tel")String tel);
	/**
	 * Insert table `MEMBER`
	 * @param member
	 * @return
	 */
	public int insert(Member member);
	/**
	 * Update `BONUSPOINT` column of table `MEMBER`
	 * @param memberID
	 * @param bonusPoint
	 * @return
	 */
	public int updateBonusPoint(@Param("memberID")String memberID, @Param("bonusPoint")Float bonusPoint);
	/**
	 * Insert table `C_HISTORY`
	 * @param history
	 * @return
	 */
	public int insertHistory(CHistory history);
}
