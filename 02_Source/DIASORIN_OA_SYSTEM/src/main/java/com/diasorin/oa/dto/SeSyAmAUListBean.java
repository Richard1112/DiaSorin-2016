package com.diasorin.oa.dto;

import java.io.Serializable;

public class SeSyAmAUListBean implements Serializable {

	private static final long serialVersionUID = 1L;
	// 部门
	private String deptId;
	private String deptName;
	// 角色
	private String roleId;
	private String roleName;
	// 控件ID
	private String controlId;
	private String controlName;
	// 权限
	private String authority;
	
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getControlId() {
		return controlId;
	}
	public void setControlId(String controlId) {
		this.controlId = controlId;
	}
	public String getAuthority() {
		return authority;
	}
	public void setAuthority(String authority) {
		this.authority = authority;
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
}
