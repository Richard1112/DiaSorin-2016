package com.diasorin.oa.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the user database table.
 * 
 */
@Entity
@Table(name="t_expenses_application")
public class ExpensesApplication implements Serializable {
	private static final long serialVersionUID = 1L;

	// 序列号
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long no;
	
	// 报销申请编号
	private String expensesAppNo;
	// 申请者
	private String employeeNo;
	// 申请日期
	private String applicationDate;
	// 出差地类型
	private String travelLocalType;
	// 所属成本中心
	private String costCenterCode;
	// 出差原因
	private String travelReason;
	// 出差时间Start
	private String travelDateStart;
	// 出差时间End
	private String travelDateEnd;
	// 对应出差申请编号
	private String travelAppNo;
	// 报销合计
	private BigDecimal expenseSum;
	// 状态
	private String status;
	// 删除区分
	private String deleteFlg;
	// 登录时间
	private Timestamp addTimestamp;
	// 登录者
	private String addUserKey;
	// 更新时间
	private Timestamp updTimestamp;
	// 更新者
	private String updUserKey;
	// 更新PGMID
	private String updPgmId;
	/**
	 * @return the no
	 */
	public Long getNo() {
		return no;
	}
	/**
	 * @param no the no to set
	 */
	public void setNo(Long no) {
		this.no = no;
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
	 * @return the employeeNo
	 */
	public String getEmployeeNo() {
		return employeeNo;
	}
	/**
	 * @param employeeNo the employeeNo to set
	 */
	public void setEmployeeNo(String employeeNo) {
		this.employeeNo = employeeNo;
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
	 * @return the costCenterCode
	 */
	public String getCostCenterCode() {
		return costCenterCode;
	}
	/**
	 * @param costCenterCode the costCenterCode to set
	 */
	public void setCostCenterCode(String costCenterCode) {
		this.costCenterCode = costCenterCode;
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
	 * @return the travelAppNo
	 */
	public String getTravelAppNo() {
		return travelAppNo;
	}
	/**
	 * @param travelAppNo the travelAppNo to set
	 */
	public void setTravelAppNo(String travelAppNo) {
		this.travelAppNo = travelAppNo;
	}
	/**
	 * @return the expenseSum
	 */
	public BigDecimal getExpenseSum() {
		return expenseSum;
	}
	/**
	 * @param expenseSum the expenseSum to set
	 */
	public void setExpenseSum(BigDecimal expenseSum) {
		this.expenseSum = expenseSum;
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
	 * @return the deleteFlg
	 */
	public String getDeleteFlg() {
		return deleteFlg;
	}
	/**
	 * @param deleteFlg the deleteFlg to set
	 */
	public void setDeleteFlg(String deleteFlg) {
		this.deleteFlg = deleteFlg;
	}
	/**
	 * @return the addTimestamp
	 */
	public Timestamp getAddTimestamp() {
		return addTimestamp;
	}
	/**
	 * @param addTimestamp the addTimestamp to set
	 */
	public void setAddTimestamp(Timestamp addTimestamp) {
		this.addTimestamp = addTimestamp;
	}
	/**
	 * @return the addUserKey
	 */
	public String getAddUserKey() {
		return addUserKey;
	}
	/**
	 * @param addUserKey the addUserKey to set
	 */
	public void setAddUserKey(String addUserKey) {
		this.addUserKey = addUserKey;
	}
	/**
	 * @return the updTimestamp
	 */
	public Timestamp getUpdTimestamp() {
		return updTimestamp;
	}
	/**
	 * @param updTimestamp the updTimestamp to set
	 */
	public void setUpdTimestamp(Timestamp updTimestamp) {
		this.updTimestamp = updTimestamp;
	}
	/**
	 * @return the updUserKey
	 */
	public String getUpdUserKey() {
		return updUserKey;
	}
	/**
	 * @param updUserKey the updUserKey to set
	 */
	public void setUpdUserKey(String updUserKey) {
		this.updUserKey = updUserKey;
	}
	/**
	 * @return the updPgmId
	 */
	public String getUpdPgmId() {
		return updPgmId;
	}
	/**
	 * @param updPgmId the updPgmId to set
	 */
	public void setUpdPgmId(String updPgmId) {
		this.updPgmId = updPgmId;
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
	public String getTravelDateStart() {
		return travelDateStart;
	}
	public void setTravelDateStart(String travelDateStart) {
		this.travelDateStart = travelDateStart;
	}
	public String getTravelDateEnd() {
		return travelDateEnd;
	}
	public void setTravelDateEnd(String travelDateEnd) {
		this.travelDateEnd = travelDateEnd;
	}
}