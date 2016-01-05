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
@Table(name="t_sys_employee_authority")
public class SysEmployeeAuthority implements Serializable {
	private static final long serialVersionUID = 1L;

	// 序列号
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long no;
	// 部门Code
	private String deptCode;
	// 角色Code
	private String roleCode;
	// 控件ID
	private String controlId;
	// 权限
	private String authority;
	
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
	 * @return the deptCode
	 */
	public String getDeptCode() {
		return deptCode;
	}
	/**
	 * @param deptCode the deptCode to set
	 */
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	/**
	 * @return the roleCode
	 */
	public String getRoleCode() {
		return roleCode;
	}
	/**
	 * @param roleCode the roleCode to set
	 */
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
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
	 * @return the authority
	 */
	public String getAuthority() {
		return authority;
	}
	/**
	 * @param authority the authority to set
	 */
	public void setAuthority(String authority) {
		this.authority = authority;
	}
	
}