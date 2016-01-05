package com.diasorin.oa.dto;

import java.util.ArrayList;
import java.util.List;

public class SeAcApLsBean {

	// Cost Center
	private String costCenter;
	// 申请人
	private String claimBy;
	// 申请日开始
	private String claimDateFrom;
	// 申请日结束
	private String claimDateTo;
	// 状态
	private String status;
	// 驳回理由
	private String rejectReason;
	// 明细项目
	private List<SeAcApLsListBean> beanList = new ArrayList<SeAcApLsListBean>();
	
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
	 * @return the claimBy
	 */
	public String getClaimBy() {
		return claimBy;
	}
	/**
	 * @param claimBy the claimBy to set
	 */
	public void setClaimBy(String claimBy) {
		this.claimBy = claimBy;
	}
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
	public List<SeAcApLsListBean> getBeanList() {
		return beanList;
	}
	/**
	 * @param beanList the beanList to set
	 */
	public void setBeanList(List<SeAcApLsListBean> beanList) {
		this.beanList = beanList;
	}
	/**
	 * @return the rejectReason
	 */
	public String getRejectReason() {
		return rejectReason;
	}
	/**
	 * @param rejectReason the rejectReason to set
	 */
	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}
}
