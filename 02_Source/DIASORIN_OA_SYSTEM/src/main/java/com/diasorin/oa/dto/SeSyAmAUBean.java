package com.diasorin.oa.dto;

import java.io.Serializable;
import java.util.List;

public class SeSyAmAUBean implements Serializable {

	private static final long serialVersionUID = 1L;
	// 部门
	private String deptId;
	private String deptName;
	// 角色
	private String roleId;
	private String roleName;
	// 权限一览
	private List<SeSyAmAUListBean> beanList;
	
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
	/**
	 * @return the beanList
	 */
	public List<SeSyAmAUListBean> getBeanList() {
		return beanList;
	}
	/**
	 * @param beanList the beanList to set
	 */
	public void setBeanList(List<SeSyAmAUListBean> beanList) {
		this.beanList = beanList;
	}
}
