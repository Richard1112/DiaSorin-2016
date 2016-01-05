package com.diasorin.oa.model;

import java.io.Serializable;
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
@Table(name="t_expenses_approve_rules")
public class ExpensesApproveRules implements Serializable {
	private static final long serialVersionUID = 1L;

	// 序列号
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long no;
	// 流程审批规则ID
	private String ruleId;
	// 流程节点ID
	private String nodeId;
	// 审批者角色Code
	private String approveRoleCode;
	// 审批条件
	private String approveCondition;
	// 审批费用类型
	private String approveExpenseType;
	// 审批金额
	private String approveAmount;
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
	 * @return the ruleId
	 */
	public String getRuleId() {
		return ruleId;
	}
	/**
	 * @param ruleId the ruleId to set
	 */
	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}
	/**
	 * @return the nodeId
	 */
	public String getNodeId() {
		return nodeId;
	}
	/**
	 * @param nodeId the nodeId to set
	 */
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	/**
	 * @return the approveRoleCode
	 */
	public String getApproveRoleCode() {
		return approveRoleCode;
	}
	/**
	 * @param approveRoleCode the approveRoleCode to set
	 */
	public void setApproveRoleCode(String approveRoleCode) {
		this.approveRoleCode = approveRoleCode;
	}
	/**
	 * @return the approveCondition
	 */
	public String getApproveCondition() {
		return approveCondition;
	}
	/**
	 * @param approveCondition the approveCondition to set
	 */
	public void setApproveCondition(String approveCondition) {
		this.approveCondition = approveCondition;
	}
	/**
	 * @return the approveAmount
	 */
	public String getApproveAmount() {
		return approveAmount;
	}
	/**
	 * @param approveAmount the approveAmount to set
	 */
	public void setApproveAmount(String approveAmount) {
		this.approveAmount = approveAmount;
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
	 * @return the approveExpenseType
	 */
	public String getApproveExpenseType() {
		return approveExpenseType;
	}
	/**
	 * @param approveExpenseType the approveExpenseType to set
	 */
	public void setApproveExpenseType(String approveExpenseType) {
		this.approveExpenseType = approveExpenseType;
	}
	
}