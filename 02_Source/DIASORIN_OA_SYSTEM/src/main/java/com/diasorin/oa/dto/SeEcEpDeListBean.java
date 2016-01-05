package com.diasorin.oa.dto;

public class SeEcEpDeListBean {

	// No
	private String no;
	// 出差发生开始日
	private String dayFrom;
	// 出差发生结束日
	private String dayTo;
	// 项目类型
	private String expenseType;
	// 计费方式
	private String timeMethod;
	// 项目出差金额
	private String expenseAmount;
	// 项目报销备注
	private String comments;
	// 目的地
	private String location;
	// rejectErrorFlg
	private String rejectErrorFlg;
	
	/**
	 * @return the no
	 */
	public String getNo() {
		return no;
	}
	/**
	 * @param no the no to set
	 */
	public void setNo(String no) {
		this.no = no;
	}
	/**
	 * @return the dayFrom
	 */
	public String getDayFrom() {
		return dayFrom;
	}
	/**
	 * @param dayFrom the dayFrom to set
	 */
	public void setDayFrom(String dayFrom) {
		this.dayFrom = dayFrom;
	}
	/**
	 * @return the dayTo
	 */
	public String getDayTo() {
		return dayTo;
	}
	/**
	 * @param dayTo the dayTo to set
	 */
	public void setDayTo(String dayTo) {
		this.dayTo = dayTo;
	}
	/**
	 * @return the expenseType
	 */
	public String getExpenseType() {
		return expenseType;
	}
	/**
	 * @param expenseType the expenseType to set
	 */
	public void setExpenseType(String expenseType) {
		this.expenseType = expenseType;
	}
	/**
	 * @return the expenseAmount
	 */
	public String getExpenseAmount() {
		return expenseAmount;
	}
	/**
	 * @param expenseAmount the expenseAmount to set
	 */
	public void setExpenseAmount(String expenseAmount) {
		this.expenseAmount = expenseAmount;
	}
	/**
	 * @return the comments
	 */
	public String getComments() {
		return comments;
	}
	/**
	 * @param comments the comments to set
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}
	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}
	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	/**
	 * @return the timeMethod
	 */
	public String getTimeMethod() {
		return timeMethod;
	}
	/**
	 * @param timeMethod the timeMethod to set
	 */
	public void setTimeMethod(String timeMethod) {
		this.timeMethod = timeMethod;
	}
	/**
	 * @return the rejectErrorFlg
	 */
	public String getRejectErrorFlg() {
		return rejectErrorFlg;
	}
	/**
	 * @param rejectErrorFlg the rejectErrorFlg to set
	 */
	public void setRejectErrorFlg(String rejectErrorFlg) {
		this.rejectErrorFlg = rejectErrorFlg;
	}
}
