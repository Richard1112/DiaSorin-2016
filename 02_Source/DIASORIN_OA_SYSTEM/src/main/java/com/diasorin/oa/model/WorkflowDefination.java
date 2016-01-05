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
@Table(name="t_workflow_defination")
public class WorkflowDefination implements Serializable {
	private static final long serialVersionUID = 1L;

	// 序列号
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long no;
	// 流程ID
	private String workflowId;
	// 流程名称
	private String workflowName;
	// 所属分类
	private String workflowCategory;
	// 流程类型
	private String workflowType;
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
	 * @return the workflowId
	 */
	public String getWorkflowId() {
		return workflowId;
	}
	/**
	 * @param workflowId the workflowId to set
	 */
	public void setWorkflowId(String workflowId) {
		this.workflowId = workflowId;
	}
	/**
	 * @return the workflowName
	 */
	public String getWorkflowName() {
		return workflowName;
	}
	/**
	 * @param workflowName the workflowName to set
	 */
	public void setWorkflowName(String workflowName) {
		this.workflowName = workflowName;
	}
	/**
	 * @return the workflowCategory
	 */
	public String getWorkflowCategory() {
		return workflowCategory;
	}
	/**
	 * @param workflowCategory the workflowCategory to set
	 */
	public void setWorkflowCategory(String workflowCategory) {
		this.workflowCategory = workflowCategory;
	}
	/**
	 * @return the workflowType
	 */
	public String getWorkflowType() {
		return workflowType;
	}
	/**
	 * @param workflowType the workflowType to set
	 */
	public void setWorkflowType(String workflowType) {
		this.workflowType = workflowType;
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

}