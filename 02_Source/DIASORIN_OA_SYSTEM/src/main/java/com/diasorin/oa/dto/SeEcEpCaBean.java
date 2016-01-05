package com.diasorin.oa.dto;

import java.util.ArrayList;
import java.util.List;

public class SeEcEpCaBean {
	// 申请当前天或者区间
	private String claimDateFrom;
	private String claimDateTo;
	// 出差地类型
	private String travelLocalType;
	// 出差原因
	private String travelReason;
	// 成本中心
	private String costCenter;
	// 申请No
	private String expensesAppNo;
	// 申请状态
	private String appStatus;
	// 申请报销明细项目
	private List<SeEcEpCaListBean> beanList = new ArrayList<SeEcEpCaListBean>();
	// 画面是否被改变FLAG
	private String hasChanged;
	
	
	/**
	 * @return the claimDateFrom
	 */
	public String getClaimDateFrom() {
		return claimDateFrom;
	}
	/**
	 * @param claimDateFrom the claimDateFrom to set
	 */
	public void setClaimDateFrom(String claimDateFrom) {
		this.claimDateFrom = claimDateFrom;
	}
	/**
	 * @return the claimDateTo
	 */
	public String getClaimDateTo() {
		return claimDateTo;
	}
	/**
	 * @param claimDateTo the claimDateTo to set
	 */
	public void setClaimDateTo(String claimDateTo) {
		this.claimDateTo = claimDateTo;
	}
	/**
	 * @return the travelLocalType
	 */
	public String getTravelLocalType() {
		return travelLocalType;
	}
	/**
	 * @param travelLocalType the travelLocalType to set
	 */
	public void setTravelLocalType(String travelLocalType) {
		this.travelLocalType = travelLocalType;
	}
	/**
	 * @return the travelReason
	 */
	public String getTravelReason() {
		return travelReason;
	}
	/**
	 * @param travelReason the travelReason to set
	 */
	public void setTravelReason(String travelReason) {
		this.travelReason = travelReason;
	}
	/**
	 * @return the beanList
	 */
	public List<SeEcEpCaListBean> getBeanList() {
		return beanList;
	}
	/**
	 * @param beanList the beanList to set
	 */
	public void setBeanList(List<SeEcEpCaListBean> beanList) {
		this.beanList = beanList;
	}
	/**
	 * @return the costCenter
	 */
	public String getCostCenter() {
		return costCenter;
	}
	/**
	 * @param costCenter the costCenter to set
	 */
	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
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
