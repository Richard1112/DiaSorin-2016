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
@Table(name="t_workflow_node_defination")
public class WorkflowNodeDefination implements Serializable {
	private static final long serialVersionUID = 1L;

	// 序列号
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long no;
	// 流程节点ID
	private String nodeId;
	// 流程节点名称
	private String nodeName;
	// 所属流程
	private String nodeWorkflow;
	// 上级节点ID
	private String upNodeId;
	// 是否需全部通过
	private String ifAllApprove;
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
	 * @return the nodeName
	 */
	public String getNodeName() {
		return nodeName;
	}
	/**
	 * @param nodeName the nodeName to set
	 */
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	/**
	 * @return the nodeWorkflow
	 */
	public String getNodeWorkflow() {
		return nodeWorkflow;
	}
	/**
	 * @param nodeWorkflow the nodeWorkflow to set
	 */
	public void setNodeWorkflow(String nodeWorkflow) {
		this.nodeWorkflow = nodeWorkflow;
	}
	/**
	 * @return the upNodeId
	 */
	public String getUpNodeId() {
		return upNodeId;
	}
	/**
	 * @param upNodeId the upNodeId to set
	 */
	public void setUpNodeId(String upNodeId) {
		this.upNodeId = upNodeId;
	}
	/**
	 * @return the ifAllApprove
	 */
	public String getIfAllApprove() {
		return ifAllApprove;
	}
	/**
	 * @param ifAllApprove the ifAllApprove to set
	 */
	public void setIfAllApprove(String ifAllApprove) {
		this.ifAllApprove = ifAllApprove;
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