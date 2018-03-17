package com.pockorder.view;

import java.util.List;

import com.pockorder.domain.KeywordWarning;

public class AddOrderResult extends OrderResult {

	private boolean isBlackList;
	private boolean isRepeat;
	private List<KeywordWarning> keywordWarningList;
	
	public boolean isBlackList() {
		return isBlackList;
	}
	public void setBlackList(boolean isBlackList) {
		this.isBlackList = isBlackList;
	}
	public boolean isRepeat() {
		return isRepeat;
	}
	public void setRepeat(boolean isRepeat) {
		this.isRepeat = isRepeat;
	}
	public List<KeywordWarning> getKeywordWarningList() {
		return keywordWarningList;
	}
	public void setKeywordWarningList(List<KeywordWarning> keywordWarningList) {
		this.keywordWarningList = keywordWarningList;
	}
	
}
