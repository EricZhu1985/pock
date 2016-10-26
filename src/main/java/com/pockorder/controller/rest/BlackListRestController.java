package com.pockorder.controller.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mybatis.pagination.Pager;
import com.pockorder.annotation.Logged;
import com.pockorder.domain.BlackList;
import com.pockorder.service.BlackListService;
import com.pockorder.view.MsgResult;
import com.pockorder.view.PagedResult;

@RestController
@RequestMapping("/blacklist")
public class BlackListRestController {

	@Autowired
	private BlackListService blackListService;

    @RequestMapping("/isblacklist")
    public boolean isBlackList(@RequestParam(value="tel") String customerTel) {
        return blackListService.isBlackList(customerTel);
    }
    

    @RequestMapping("/query")
    public PagedResult<BlackList> query(@RequestParam(value="customerTel", required=false) String customerTel,
    		@RequestParam(value="page", required=false, defaultValue="1") Integer page,
    		@RequestParam(value="rows", required=false, defaultValue="10") Integer rows) {
    	Pager pager = new Pager(page, rows);
        List<BlackList> result = blackListService.getBlackList(customerTel, pager);
        return new PagedResult<BlackList>(result, pager);
    }

    @RequestMapping("/insert")
    @Logged
    public MsgResult insert(@RequestParam(value="eventDate") String eventDate,
    		@RequestParam(value="content") String content,
    		@RequestParam(value="customerName") String customerName,
    		@RequestParam(value="customerTel") String customerTel) {
    	MsgResult msg = new MsgResult();
    	if(blackListService.insert(eventDate, content, customerName, customerTel).getBlackListID() != null) {
    		msg.setMsg("增加成功！");
    	} else {
    		msg.setErrMsg("增加失败！");
    	}
    	return msg;
    }

    @RequestMapping("/delete")
    @Logged
    public MsgResult delete(@RequestParam(value="blackListID") String blackListID) {
    	MsgResult msg = new MsgResult();
    	if(blackListService.delete(blackListID) >= 1) {
    		msg.setMsg("删除成功！");
    	} else {
    		msg.setErrMsg("删除失败！");
    	}
    	return msg;
    }
    
}
