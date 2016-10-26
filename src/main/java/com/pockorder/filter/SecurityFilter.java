package com.pockorder.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.pockorder.constant.SessionConst;
import com.pockorder.domain.User;

public class SecurityFilter implements Filter {
	
	List<String> noSecurityPath;

	public void init(FilterConfig filterConfig) throws ServletException {
		
		noSecurityPath = new ArrayList<String>();
		noSecurityPath.add("/login.htm");
		noSecurityPath.add("/login.jsp");
		noSecurityPath.add("/login");
		noSecurityPath.add("/error.htm");
		noSecurityPath.add("/js/jquery.min.js");
		
	}


	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		this.doFilter((HttpServletRequest) request, (HttpServletResponse) response, chain);
	}
	
	public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		String path = request.getServletPath();
		if(!noSecurityPath.contains(path)) {
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute(SessionConst.USER);
			if(user == null) {
				request.setAttribute("errorMsg", "Access denied.");
				request.getRequestDispatcher("/login.jsp").forward(request,response);
				return;
			}
			session.setMaxInactiveInterval(1800);
		}
		
		chain.doFilter(request, response);
		
	}

	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
