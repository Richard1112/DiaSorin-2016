package com.diasorin.oa.dto;

import java.util.ArrayList;
import java.util.List;

public class SeEcEpLsBean {

	// 出差地类型
	private String travelLocationType;
	// 申请时间FROM
	private String chaimDateFrom;
	// 申请时间TO
	private String chaimDateTo;
	// 报销状态
	private String status;
	// 检索明细bean
	private List<SeEcEpLsListBean> beanList = new ArrayList<SeEcEpLsListBean>();
	
	/**
	 * @return the travelLocationType
	 */
	public String getTravelLocationType() {
		return travelLocationType;
	}
	/**
	 * @param travelLocationType the travelLocationType to set
	 */
	public void setTravelLocationType(String travelLocationType) {
		this.travelLocationType = travelLocationType;
	}
	/**
	 * @return the chaimDateFrom
	 */
	public String getChaimDateFrom() {
		return chaimDateFrom;
	}
	/**
	 * @param chaimDateFrom the chaimDateFrom to set
	 */
	public void setChaimDateFrom(String chaimDateFrom) {
		this.chaimDateFrom = chaimDateFrom;
	}
	/**
	 * @return the chaimDateTo
	 */
	public String getChaimDateTo() {
		return chaimDateTo;
	}
	/**
	 * @param chaimDateTo the chaimDateTo to set
	 */
	public void setChaimDateTo(String chaimDateTo) {
		this.chaimDateTo = chaimDateTo;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the beanList
	 */
	public List<SeEcEpLsListBean> getBeanList() {
		return beanList;
	}
	/**
	 * @param beanList the beanList to set
	 */
	public void setBeanList(List<SeEcEpLsListBean> beanList) {
		this.beanList = beanList;
	}
}
