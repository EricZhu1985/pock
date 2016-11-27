package com.pockorder.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pockorder.annotation.Logged;
import com.pockorder.domain.Member;
import com.pockorder.exception.DataNotFoundException;
import com.pockorder.service.MemberService;
import com.pockorder.view.MsgResult;

@RestController
@RequestMapping("/member")
public class MemberRestController {

	@Autowired
	private MemberService memberService;
	
	/**
	 * 主页订单列表
	 * @param session
	 * @param startDate
	 * @param endDate
	 * @param content
	 * @param customerTel
	 * @param customerWx
	 * @param branchID
	 * @param finished
	 * @param orderBy
	 * @return
	 */
    @RequestMapping("/memberInfo")
    public Member memberInfo(@RequestParam(value="tel", required=true) String tel) {
    	try {
			return memberService.getMemberByTel(tel);
		} catch (DataNotFoundException e) {
			return null;
		}
    }

    @RequestMapping("/bonusPoint")
    @Logged
    public MsgResult bonusPoint(@RequestParam(value="tel", required=true) String tel,
    		@RequestParam(value="bonusPoint", required=true) String bonusPoint,
    		@RequestParam(value="content", required=true) String content) {
    	MsgResult result = new MsgResult();
		if(memberService.updateBonusPointByTel(tel, Float.valueOf(bonusPoint), content) > 0) {
			result.setMsg("修改成功！");
		} else {
			result.setMsg("修改失败！");
		}
		return result;
    }
}
