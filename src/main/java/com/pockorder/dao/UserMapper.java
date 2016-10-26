package com.pockorder.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.pockorder.domain.User;

@Repository
public interface UserMapper {

	public User getUserByName(@Param("name")String name);

	public User getUserByPwd(@Param("name")String name, @Param("password")String password);
}
