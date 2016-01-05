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
@Table(name="t_sys_employee_level_his")
public class SysEmployeeLevelHis implements Serializable {
	private static final long serialVersionUID = 1L;

	// 序列号
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long no;
	// 级别Code
	private String levelCode;
	// 级别Name
	private String levelName;
	// 区分
	private String operateFlg;
	// 操作者
	private String operater;
	// 操作时间
	private Timestamp operateTimestamp;
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
	 * @return the levelCode
	 */
	public String getLevelCode() {
		return levelCode;
	}
	/**
	 * @param levelCode the levelCode to set
	 */
	public void setLevelCode(String levelCode) {
		this.levelCode = levelCode;
	}
	/**
	 * @return the levelName
	 */
	public String getLevelName() {
		return levelName;
	}
	/**
	 * @param levelName the levelName to set
	 */
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	/**
	 * @return the operateFlg
	 */
	public String getOperateFlg() {
		return operateFlg;
	}
	/**
	 * @param operateFlg the operateFlg to set
	 */
	public void setOperateFlg(String operateFlg) {
		this.operateFlg = operateFlg;
	}
	/**
	 * @return the operater
	 */
	public String getOperater() {
		return operater;
	}
	/**
	 * @param operater the operater to set
	 */
	public void setOperater(String operater) {
		this.operater = operater;
	}
	/**
	 * @return the operateTimestamp
	 */
	public Timestamp getOperateTimestamp() {
		return operateTimestamp;
	}
	/**
	 * @param operateTimestamp the operateTimestamp to set
	 */
	public void setOperateTimestamp(Timestamp operateTimestamp) {
		this.operateTimestamp = operateTimestamp;
	}
	
	
	
}