package com.diasorin.oa.dto;

public abstract class BaseBean {

	protected int nowPage;
	
	protected int preRows;

	/**
	 * @return the nowPage
	 */
	public int getNowPage() {
		return nowPage;
	}

	/**
	 * @param nowPage the nowPage to set
	 */
	public void setNowPage(int nowPage) {
		this.nowPage = nowPage;
	}

	/**
	 * @return the preRows
	 */
	public int getPreRows() {
		return preRows;
	}

	/**
	 * @param preRows the preRows to set
	 */
	public void setPreRows(int preRows) {
		this.preRows = preRows;
	}
	
	
}
