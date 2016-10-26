package com.pockorder.service;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pockorder.dao.UserMapper;
import com.pockorder.domain.User;
import com.pockorder.util.MD5Util;

@Service
public class UserService {

	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private SqlSessionFactoryBean sqlSession;
	/**
	 * Get user by name and password, password will be encode by MD5 algorithm
	 * @param name
	 * @param password
	 * @return
	 */
	
	public User getUserByPwd(String name, String password) {
		String md5pwd = MD5Util.getMd5(password);
		test();
		return userMapper.getUserByPwd(name, md5pwd);
	}
	
	public void test() {
		try {
		System.out.println("sqlSession:" + ((SqlSessionFactory) sqlSession.getObject()).openSession().selectOne("com.pockorder.dao.OrderMapper.selectMaxWeijujuNo"));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	  
}
