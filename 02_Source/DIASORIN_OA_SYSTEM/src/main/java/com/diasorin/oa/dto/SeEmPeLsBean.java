package com.diasorin.oa.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * 员工一览用
 * @author linliuan
 *
 */
public class SeEmPeLsBean extends BaseBean{
	
	// 角色Code
	private String roleCode;
	// 部门Code
	private String deptCode;
	// 部门Code
	private String name;
	// 员工一览
	private List<SeEmPeLsListBean> beanList = new ArrayList<SeEmPeLsListBean>();
	
	
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
	 * @return the beanList
	 */
	public List<SeEmPeLsListBean> getBeanList() {
		return beanList;
	}
	/**
	 * @param beanList the beanList to set
	 */
	public void setBeanList(List<SeEmPeLsListBean> beanList) {
		this.beanList = beanList;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
}
