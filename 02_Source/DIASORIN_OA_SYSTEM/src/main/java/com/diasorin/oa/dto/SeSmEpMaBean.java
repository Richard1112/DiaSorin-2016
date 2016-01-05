package com.diasorin.oa.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * 报销设定
 * @author liuan
 *
 */
public class SeSmEpMaBean {

	// 项目
	private String expenseCode;
	
	private List<SeSmEpMaListBean> beanList = new ArrayList<SeSmEpMaListBean>();


	/**
	 * @return the beanList
	 */
	public List<SeSmEpMaListBean> getBeanList() {
		return beanList;
	}

	/**
	 * @param beanList the beanList to set
	 */
	public void setBeanList(List<SeSmEpMaListBean> beanList) {
		this.beanList = beanList;
	}

	public String getExpenseCode() {
		return expenseCode;
	}

	public void setExpenseCode(String expenseCode) {
		this.expenseCode = expenseCode;
	}
	
}
