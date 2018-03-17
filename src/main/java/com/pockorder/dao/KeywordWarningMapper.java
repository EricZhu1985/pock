package com.pockorder.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.mybatis.pagination.Pager;
import com.pockorder.domain.KeywordWarning;

@Repository
public interface KeywordWarningMapper {

	/**
	 * Select keywordwarning by keyword and memo
	 * @return
	 */
	//public KeywordWarning selectByTel(@Param("customerTel")String customerTel);
	
	public List<KeywordWarning> select(@Param("keyword")String keyword, @Param("memo")String memo);

	public List<KeywordWarning> select(@Param("keyword")String keyword, @Param("memo")String memo, Pager pager);
	
	public List<KeywordWarning> selectByKeywordReverse(@Param("keyword")String keyword);
	
	public int insert(KeywordWarning keywordWarning);
	
	public int delete(@Param("keywordWarningID")String keywordWarningID);
	
	public int update(@Param("keywordWarningID")String keywordWarningID, @Param("keyword")String keyword,
			@Param("amount")Integer amount, @Param("memo")String memo);
	
	public KeywordWarning selectByID(@Param("keywordWarningID")String keywordWarningID);
	
	public int updateAmount(@Param("keywordWarningID")String keywordWarningID, @Param("amount")Integer amount);
}
