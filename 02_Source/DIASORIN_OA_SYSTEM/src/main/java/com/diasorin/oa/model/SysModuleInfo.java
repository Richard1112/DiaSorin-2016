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
@Table(name="t_sys_module_info")
public class SysModuleInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	// 序列号
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long no;
	// 控件ID
	private String controlId;
	// 父控件ID
	private String fatherControlId;
	// 控件区分
	private String controlDivision;
	// 控件名称
	private String controlName;
	// 方法ID
	private String methodId;
	
	
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
	 * @return the controlId
	 */
	public String getControlId() {
		return controlId;
	}
	/**
	 * @param controlId the controlId to set
	 */
	public void setControlId(String controlId) {
		this.controlId = controlId;
	}
	/**
	 * @return the fatherControlId
	 */
	public String getFatherControlId() {
		return fatherControlId;
	}
	/**
	 * @param fatherControlId the fatherControlId to set
	 */
	public void setFatherControlId(String fatherControlId) {
		this.fatherControlId = fatherControlId;
	}
	/**
	 * @return the controlDivision
	 */
	public String getControlDivision() {
		return controlDivision;
	}
	/**
	 * @param controlDivision the controlDivision to set
	 */
	public void setControlDivision(String controlDivision) {
		this.controlDivision = controlDivision;
	}
	/**
	 * @return the controlName
	 */
	public String getControlName() {
		return controlName;
	}
	/**
	 * @param controlName the controlName to set
	 */
	public void setControlName(String controlName) {
		this.controlName = controlName;
	}
	/**
	 * @return the methodId
	 */
	public String getMethodId() {
		return methodId;
	}
	/**
	 * @param methodId the methodId to set
	 */
	public void setMethodId(String methodId) {
		this.methodId = methodId;
	}
	
	
	
}