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
@Table(name="t_sys_cost_center_his")
public class SysCostCenterHis implements Serializable {
	private static final long serialVersionUID = 1L;

	// 序列号
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long no;
	// 成本中心Code
	private String costCenterCode;
	// 成本中心Name
	private String costCenterName;
	// 成本中心显示名称
	private String costCenterDisplayName;
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
	/**
	 * @return the costCenterDisplayName
	 */
	public String getCostCenterDisplayName() {
		return costCenterDisplayName;
	}
	/**
	 * @param costCenterDisplayName the costCenterDisplayName to set
	 */
	public void setCostCenterDisplayName(String costCenterDisplayName) {
		this.costCenterDisplayName = costCenterDisplayName;
	}

	
}