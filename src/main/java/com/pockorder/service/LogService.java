package com.pockorder.service;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pockorder.constant.SessionConst;
import com.pockorder.dao.LogMapper;
import com.pockorder.domain.Log;
import com.pockorder.domain.User;

@Service
public class LogService {

	@Autowired
	private LogMapper logMapper;

	public int log(String content, String operator, String url, String ip, String sessionID) {
		Log log = new Log();
		log.setContent(content);
		log.setOperator(operator);
		log.setUrl(url);
		log.setIp(ip);
		log.setSessionID(sessionID);
		return logMapper.insert(log);
	}
	
	public int log(HttpServletRequest request, String content) {
		String sessionID = "";
		String userName = "";

		if(request.getSession() == null) {
			userName = "无Session";
		} else {
			HttpSession session = request.getSession();
			sessionID = session.getId();
			if(session.getAttribute(SessionConst.USER) == null) {
				userName = "Session[" + SessionConst.USER + "]不存在";
			} else {
				User user = (User) session.getAttribute(SessionConst.USER);
				userName = user.getName() + "(" + user.getID() + ")";
			}
		}
		
		return this.log(content, userName, request.getServletPath(), request.getRemoteAddr(), sessionID);
	}
	
}
