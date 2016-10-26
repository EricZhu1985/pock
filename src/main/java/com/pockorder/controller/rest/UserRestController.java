package com.pockorder.controller.rest;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pockorder.annotation.Logged;
import com.pockorder.constant.SessionConst;
import com.pockorder.domain.User;
import com.pockorder.service.UserService;

@RestController
public class UserRestController {

	@Autowired  
	private HttpSession session;  
	
	@Autowired
	private UserService userService;
	

    @RequestMapping("/login")
    @Logged
    public boolean login(
    		@RequestParam(value="name") String name,
    		@RequestParam(value="pwd") String pwd) {
    	//logService.log(request, "");
        User user = userService.getUserByPwd(name, pwd);
        if(user != null) {
        	session.setAttribute(SessionConst.USER, user);
        	return true;
        }
        return false;
    }
    
    
}
