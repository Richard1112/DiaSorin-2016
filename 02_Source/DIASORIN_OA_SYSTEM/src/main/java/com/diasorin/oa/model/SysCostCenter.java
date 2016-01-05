package com.diasorin.oa.model;

import java.io.Serializable;

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
@Table(name="t_sys_cost_center")
public class SysCostCenter implements Serializable {
	private static final long serialVersionUID = 1L;

	// 序列号
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long no;
	// 成本中心Code
	private String costCenterCode;
	// 成本中心Name
	private String costCenterName;
	// 成本中心Display Name
	private String costCenterDisplayName;
	
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