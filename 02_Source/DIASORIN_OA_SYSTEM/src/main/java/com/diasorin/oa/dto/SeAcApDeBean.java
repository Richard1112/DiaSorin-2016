package com.diasorin.oa.dto;

import java.util.ArrayList;
import java.util.List;

public class SeAcApDeBean {

	private String claimDateFrom;
	private String claimDateTo;
	// 出差地类型
	private String travelLocalType;
	// 出差原因
	private String travelReason;
	// 成本中心
	private String costCenter;
	// 成本中心
	private String costCenterName;
	// 申请No
	private String expenseAppNo;
	// 申请状态
	private String appStatus;
	// 申请人
	private String employee;
	// 申请总数
	private String totalAmount;
	// 迁移MODE
	private String mode;
	// 申请时间
	private String applicationDate;
	// 驳回原因
	private String rejectReason;
	// 申请人信息
	private String authorityName;
	// approve Flag(显示是APPROVE 或者 FINISH 或者 UNDOFINISH)
	private String approveFlg;
	
	// 申请信息
	List<SeEcEpDeBean> purposeList = new ArrayList<SeEcEpDeBean>();
	
	// 出差发生明细
	private List<SeEcEpDeListBean> detailList = new ArrayList<SeEcEpDeListBean>();
	
	// 被标记有误的明细记录
	private String errorDetailNo;

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
	 * @return the expenseAppNo
	 */
	public String getExpenseAppNo() {
		return expenseAppNo;
	}

	/**
	 * @param expenseAppNo the expenseAppNo to set
	 */
	public void setExpenseAppNo(String expenseAppNo) {
		this.expenseAppNo = expenseAppNo;
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
	 * @return the purposeList
	 */
	public List<SeEcEpDeBean> getPurposeList() {
		return purposeList;
	}

	/**
	 * @param purposeList the purposeList to set
	 */
	public void setPurposeList(List<SeEcEpDeBean> purposeList) {
		this.purposeList = purposeList;
	}

	/**
	 * @return the detailList
	 */
	public List<SeEcEpDeListBean> getDetailList() {
		return detailList;
	}

	/**
	 * @param detailList the detailList to set
	 */
	public void setDetailList(List<SeEcEpDeListBean> detailList) {
		this.detailList = detailList;
	}

	/**
	 * @return the employee
	 */
	public String getEmployee() {
		return employee;
	}

	/**
	 * @param employee the employee to set
	 */
	public void setEmployee(String employee) {
		this.employee = employee;
	}

	/**
	 * @return the totalAmount
	 */
	public String getTotalAmount() {
		return totalAmount;
	}

	/**
	 * @param totalAmount the totalAmount to set
	 */
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	/**
	 * @return the mode
	 */
	public String getMode() {
		return mode;
	}

	/**
	 * @param mode the mode to set
	 */
	public void setMode(String mode) {
		this.mode = mode;
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
	 * @return the applicationDate
	 */
	public String getApplicationDate() {
		return applicationDate;
	}

	/**
	 * @param applicationDate the applicationDate to set
	 */
	public void setApplicationDate(String applicationDate) {
		this.applicationDate = applicationDate;
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

	/**
	 * @return the costCenterName
	 */
	public String getCostCenterName() {
		return costCenterName;
	}

	/**
	 * @param costCenterName the costCenterName to set
	 */
	public void setCostCenterName(String costCenterName) {
		this.costCenterName = costCenterName;
	}

	/**
	 * @return the authorityName
	 */
	public String getAuthorityName() {
		return authorityName;
	}

	/**
	 * @param authorityName the authorityName to set
	 */
	public void setAuthorityName(String authorityName) {
		this.authorityName = authorityName;
	}

	/**
	 * @return the errorDetailNo
	 */
	public String getErrorDetailNo() {
		return errorDetailNo;
	}

	/**
	 * @param errorDetailNo the errorDetailNo to set
	 */
	public void setErrorDetailNo(String errorDetailNo) {
		this.errorDetailNo = errorDetailNo;
	}

	/**
	 * @return the approveFlg
	 */
	public String getApproveFlg() {
		return approveFlg;
	}

	/**
	 * @param approveFlg the approveFlg to set
	 */
	public void setApproveFlg(String approveFlg) {
		this.approveFlg = approveFlg;
	}
	
}
