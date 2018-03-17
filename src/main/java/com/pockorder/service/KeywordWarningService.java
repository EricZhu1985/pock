package com.pockorder.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mybatis.pagination.Pager;
import com.pockorder.dao.KeywordWarningMapper;
import com.pockorder.dao.OrderMapper;
import com.pockorder.domain.KeywordWarning;
import com.pockorder.domain.Order;

@Service
public class KeywordWarningService {

	@Autowired
	private KeywordWarningMapper keywordWarningMapper;
	

	public List<KeywordWarning> getKeywordWarningList(String keyword, String memo, Pager pager) {
		return keywordWarningMapper.select(keyword, memo, pager);
	}
	
	public KeywordWarning insert(String keyword, Integer amount, String memo) {
		KeywordWarning kw = new KeywordWarning();
		kw.setKeyword(keyword);
		kw.setAmount(amount);
		kw.setMemo(memo);
		
		keywordWarningMapper.insert(kw);
		return kw;
	}
	
	public int delete(String keywordWarningID) {
		return keywordWarningMapper.delete(keywordWarningID);
	}
	
	public int update(String keywordWarningID, String keyword, Integer amount, String memo) {
		return keywordWarningMapper.update(keywordWarningID, keyword, amount, memo);
	}
	
	public int addAmount(String keywordWarningID, Integer addAmount) {
		KeywordWarning kw = keywordWarningMapper.selectByID(keywordWarningID);
		return keywordWarningMapper.update(keywordWarningID, kw.getKeyword(), kw.getAmount() + addAmount, kw.getMemo());
	}
	
	public KeywordWarning selectByID(String keywordWarningID) {
		return keywordWarningMapper.selectByID(keywordWarningID);
	}
	
	public int updateAmount(String keywordWarningID, Integer amount) {
		return keywordWarningMapper.updateAmount(keywordWarningID, amount);
	}
	
	public List<KeywordWarning> updateKeywordAmount(Order order, Integer amount) {
		List<KeywordWarning> list = keywordWarningMapper.selectByKeywordReverse(order.getContent());
		for(KeywordWarning kw : list) {
			kw.setAmount(kw.getAmount() + amount);
			keywordWarningMapper.updateAmount(kw.getKeywordWarningID(), kw.getAmount());
		}
		return list;
	}
	
	public List<KeywordWarning> getKeywordWarningList(Order order) {
		return keywordWarningMapper.selectByKeywordReverse(order.getContent());
	}
}
