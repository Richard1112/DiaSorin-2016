package com.diasorin.oa.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class SeEcEpDeBean {

	// No
	private String purposeNo;
	// 出差目的
	private String purpose;
	// 当前目的所有的金额
	private BigDecimal amount;
	// 申请状态
	private String appStatus;
	// 申请No(已经申请过的)
	private String expensesAppNo;
	// purposeNo(已经申请过的)
	private String hasPurposeNo;
	// 出差发生明细
	private List<SeEcEpDeListBean> beanList = new ArrayList<SeEcEpDeListBean>();
	// 画面是否被改变FLAG
	private String hasChanged;
	

	/**
	 * @return the beanList
	 */
	public List<SeEcEpDeListBean> getBeanList() {
		return beanList;
	}
	/**
	 * @param beanList the beanList to set
	 */
	public void setBeanList(List<SeEcEpDeListBean> beanList) {
		this.beanList = beanList;
	}
	/**
	 * @return the purpose
	 */
	public String getPurpose() {
		return purpose;
	}
	/**
	 * @param purpose the purpose to set
	 */
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	/**
	 * @return the purposeNo
	 */
	public String getPurposeNo() {
		return purposeNo;
	}
	/**
	 * @param purposeNo the purposeNo to set
	 */
	public void setPurposeNo(String purposeNo) {
		this.purposeNo = purposeNo;
	}
	/**
	 * @return the amount
	 */
	public BigDecimal getAmount() {
		return amount;
	}
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	/**
	 * @return the appStatus
	 */
	public String getAppStatus() {
		return appStatus;
	}
	/**
	 * @param appStatus the appStatus to set
	 */
	public void setAppStatus(String appStatus) {
		this.appStatus = appStatus;
	}
	/**
	 * @return the expensesAppNo
	 */
	public String getExpensesAppNo() {
		return expensesAppNo;
	}
	/**
	 * @param expensesAppNo the expensesAppNo to set
	 */
	public void setExpensesAppNo(String expensesAppNo) {
		this.expensesAppNo = expensesAppNo;
	}
	/**
	 * @return the hasPurposeNo
	 */
	public String getHasPurposeNo() {
		return hasPurposeNo;
	}
	/**
	 * @param hasPurposeNo the hasPurposeNo to set
	 */
	public void setHasPurposeNo(String hasPurposeNo) {
		this.hasPurposeNo = hasPurposeNo;
	}
	/**
	 * @return the hasChanged
	 */
	public String getHasChanged() {
		return hasChanged;
	}
	/**
	 * @param hasChanged the hasChanged to set
	 */
	public void setHasChanged(String hasChanged) {
		this.hasChanged = hasChanged;
	}
}
