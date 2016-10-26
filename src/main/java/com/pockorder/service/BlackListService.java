package com.pockorder.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mybatis.pagination.Pager;
import com.pockorder.dao.BlackListMapper;
import com.pockorder.domain.BlackList;

@Service
public class BlackListService {

	@Autowired
	private BlackListMapper blackListMapper;
	/**
	 * @param name
	 * @param password
	 * @return
	 */
	public BlackList getBlackListByTel(String customerTel) {
		return blackListMapper.selectByTel(customerTel);
	}
	
	public boolean isBlackList(String tel) {
		return !(blackListMapper.selectByTel(tel) == null);
	}

	public List<BlackList> getBlackList(String customerTel, Pager pager) {
		return blackListMapper.select(customerTel, pager);
	}
	
	public BlackList insert(String eventDate, String content, String customerName, String customerTel) {
		BlackList bl = new BlackList();
		bl.setContent(content);
		bl.setCustomerName(customerName);
		bl.setCustomerTel(customerTel);
		bl.setEventDate(eventDate);
		blackListMapper.insert(bl);
		return bl;
	}
	
	public int delete(String blackListID) {
		return blackListMapper.delete(blackListID);
	}
}
