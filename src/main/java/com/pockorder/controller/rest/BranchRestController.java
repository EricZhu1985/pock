package com.pockorder.controller.rest;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pockorder.constant.SessionConst;
import com.pockorder.domain.Branch;
import com.pockorder.domain.User;
import com.pockorder.service.BranchService;

@RestController
@RequestMapping("/branch")
public class BranchRestController {

	@Autowired
	private BranchService branchService;
	
	private User getSessionUser(HttpSession session) {
		return (User) session.getAttribute(SessionConst.USER);
	}

    @RequestMapping("/searchcomboboxdata")
    public List<Branch> getSearchComboboxData(HttpSession session) {
	
    	List<Branch> retVal;
    	User user = getSessionUser(session);
    	if(user.getRights() == 0) {
    		retVal = branchService.getAllBranch();
    		Branch all = new Branch();
    		all.setBranchID("-1");
    		all.setBranchName("全部");
    		retVal.add(0, all);
    	} else {
    		Branch b = branchService.getBranchByID(user.getBranch().getBranchID());
    		retVal = new ArrayList<Branch>();
    		retVal.add(b);
    	}
		return retVal;
    }

    @RequestMapping("/comboboxdata")
    public List<Branch> getComboboxData(HttpSession session) {
    	List<Branch> retVal;
    	User user = getSessionUser(session);
    	if(user.getRights() == 0) {
    		retVal = branchService.getAllBranch();

    		Branch nullValue = new Branch();
    		nullValue.setBranchID("");
    		nullValue.setBranchName("无");
    		retVal.add(0, nullValue);
    	} else {
    		Branch b = branchService.getBranchByID(user.getBranch().getBranchID());
    		retVal = new ArrayList<Branch>();
    		retVal.add(b);
    	}
		return retVal;
    }
}
