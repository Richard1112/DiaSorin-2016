package com.diasorin.oa.dto;

import java.util.ArrayList;
import java.util.List;

import com.diasorin.oa.model.ExpensesApplication;

public class SeLgMnIfBean {

	// 部门
	private String costCenter;
	
	// 部门
	private String costCenterCode;
	
	// 角色
	private String roleName;
	
	// 姓名
	private String name;
	
	// 员工号
	private String employeeNo;
	
	// 级别
	private String levelName;
	
	// 个人图片
	private String headPic;
	
	// 有记录已经保存但没有申请
	private List<ExpensesApplication> applicationBeanList = new ArrayList<ExpensesApplication>();
	
	// 有记录被打回
	private List<ExpensesApplication> rejectBeanList = new ArrayList<ExpensesApplication>();
	
	// 有记录等待审批
	private List<SeAcApLsListBean> approveBeanList = new ArrayList<SeAcApLsListBean>();

	/**
	 * @return the costCenter
	 */
	public String getCostCenter() {
		return costCenter;
	}

	/**
	 * @param costCenter the costCenter to set
	 */
	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}

	/**
	 * @return the roleName
	 */
	public String getRoleName() {
		return roleName;
	}

	/**
	 * @param roleName the roleName to set
	 */
	public void setRoleName(String roleName) {
		this.roleName = roleName;
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

	/**
	 * @return the applicationBeanList
	 */
	public List<ExpensesApplication> getApplicationBeanList() {
		return applicationBeanList;
	}

	/**
	 * @param applicationBeanList the applicationBeanList to set
	 */
	public void setApplicationBeanList(List<ExpensesApplication> applicationBeanList) {
		this.applicationBeanList = applicationBeanList;
	}

	/**
	 * @return the approveBeanList
	 */
	public List<SeAcApLsListBean> getApproveBeanList() {
		return approveBeanList;
	}

	/**
	 * @param approveBeanList the approveBeanList to set
	 */
	public void setApproveBeanList(List<SeAcApLsListBean> approveBeanList) {
		this.approveBeanList = approveBeanList;
	}

	/**
	 * @return the rejectBeanList
	 */
	public List<ExpensesApplication> getRejectBeanList() {
		return rejectBeanList;
	}

	/**
	 * @param rejectBeanList the rejectBeanList to set
	 */
	public void setRejectBeanList(List<ExpensesApplication> rejectBeanList) {
		this.rejectBeanList = rejectBeanList;
	}

	/**
	 * @return the employeeNo
	 */
	public String getEmployeeNo() {
		return employeeNo;
	}

	/**
	 * @param employeeNo the employeeNo to set
	 */
	public void setEmployeeNo(String employeeNo) {
		this.employeeNo = employeeNo;
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
	 * @return the headPic
	 */
	public String getHeadPic() {
		return headPic;
	}

	/**
	 * @param headPic the headPic to set
	 */
	public void setHeadPic(String headPic) {
		this.headPic = headPic;
	}
}
