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
@Table(name="t_workflow_category")
public class WorkflowCategory implements Serializable {
	private static final long serialVersionUID = 1L;

	// 序列号
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long no;
	// 分类ID
	private String categoryId;
	// 分类名称
	private String categoryName;
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
	 * @return the categoryId
	 */
	public String getCategoryId() {
		return categoryId;
	}
	/**
	 * @param categoryId the categoryId to set
	 */
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	/**
	 * @return the categoryName
	 */
	public String getCategoryName() {
		return categoryName;
	}
	/**
	 * @param categoryName the categoryName to set
	 */
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
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