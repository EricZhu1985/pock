package com.pockorder.controller.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mybatis.pagination.Pager;
import com.pockorder.annotation.Logged;
import com.pockorder.domain.KeywordWarning;
import com.pockorder.exception.DataNotFoundException;
import com.pockorder.service.KeywordWarningService;
import com.pockorder.view.MsgResult;
import com.pockorder.view.PagedResult;

@RestController
@RequestMapping("/keywordwarning")
public class KeywordWarningRestController {

	@Autowired
	private KeywordWarningService keywordWarningService;


    @RequestMapping("/query")
    public PagedResult<KeywordWarning> query(@RequestParam(value="keyword", required=false, defaultValue="") String keyword,
    		@RequestParam(value="memo", required=false, defaultValue="") String memo,
    		@RequestParam(value="page", required=false, defaultValue="1") Integer page,
    		@RequestParam(value="rows", required=false, defaultValue="10") Integer rows) {
    	Pager pager = new Pager(page, rows);
        List<KeywordWarning> result = keywordWarningService.getKeywordWarningList(keyword, memo, pager);
        return new PagedResult<KeywordWarning>(result, pager);
    }
    
    @RequestMapping("/detail")
    public KeywordWarning detail(@RequestParam(value="keywordWarningID", required=true) String keywordWarningID) {
		return keywordWarningService.selectByID(keywordWarningID);
    }

    @RequestMapping("/insert")
    @Logged
    public MsgResult insert(@RequestParam(value="keyword") String keyword,
    		@RequestParam(value="amount") Integer amount,
    		@RequestParam(value="memo") String memo) {
    	MsgResult msg = new MsgResult();
    	if(keywordWarningService.insert(keyword, amount, memo).getKeywordWarningID() != null) {
    		msg.setMsg("增加成功！");
    	} else {
    		msg.setErrMsg("增加失败！");
    	}
    	return msg;
    }
    
    @RequestMapping("/update")
    @Logged
    public MsgResult update(@RequestParam(value="keywordWarningID", required=true) String keywordWarningID,
    		@RequestParam(value="keyword", required=true) String keyword,
    		@RequestParam(value="amount", required=true) Integer amount,
    		@RequestParam(value="memo", required=false, defaultValue="") String memo) {
    	MsgResult msg = new MsgResult();
    	if(keywordWarningService.update(keywordWarningID, keyword, amount, memo) > 0) {
    		msg.setMsg("修改成功！");
    	} else {
    		msg.setErrMsg("修改失败！");
    	}
    	return msg;
    }

    @RequestMapping("/addamount")
    @Logged
    public MsgResult addAmount(@RequestParam(value="keywordWarningID", required=true) String keywordWarningID,
    		@RequestParam(value="addAmount", required=true) Integer addAmount) {
    	MsgResult msg = new MsgResult();
    	if(keywordWarningService.addAmount(keywordWarningID, addAmount) > 0) {
    		msg.setMsg("修改成功！");
    	} else {
    		msg.setErrMsg("修改失败！");
    	}
    	return msg;
    }
    
    @RequestMapping("/delete")
    @Logged
    public MsgResult delete(@RequestParam(value="keywordWarningID") String keywordWarningID) {
    	MsgResult msg = new MsgResult();
    	if(keywordWarningService.delete(keywordWarningID) >= 1) {
    		msg.setMsg("删除成功！");
    	} else {
    		msg.setErrMsg("删除失败！");
    	}
    	return msg;
    }
    
}
