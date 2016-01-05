package com.diasorin.oa.dto;

import java.util.ArrayList;
import java.util.List;

public class SeRcReInBean {

	// FINANCE
	private String finance;
	// FinanceDateFrom
	private String financeDateFrom;
	// FinanceDateTo
	private String financeDateTo;
	// 员工
	private String employee;
	// 部门
	private String costCenter;
	// 报销类型
	private String expenseType;
	// travelLocationFlg
	private String travelFlg;
	// costCenterFlg
	private String costCenterFlg;
	// Employee
	private String employeeFlg;
	// expenses type
	private String expensesTypeFlg;
	
	private List<SeRcReInHeadListBean> headList = new ArrayList<SeRcReInHeadListBean>();
	
	private List<SeRcReInBodyListBean> bodyList = new ArrayList<SeRcReInBodyListBean>();

	/**
	 * @return the finance
	 */
	public String getFinance() {
		return finance;
	}

	/**
	 * @param finance the finance to set
	 */
	public void setFinance(String finance) {
		this.finance = finance;
	}

	/**
	 * @return the financeDateFrom
	 */
	public String getFinanceDateFrom() {
		return financeDateFrom;
	}

	/**
	 * @param financeDateFrom the financeDateFrom to set
	 */
	public void setFinanceDateFrom(String financeDateFrom) {
		this.financeDateFrom = financeDateFrom;
	}

	/**
	 * @return the financeDateTo
	 */
	public String getFinanceDateTo() {
		return financeDateTo;
	}

	/**
	 * @param financeDateTo the financeDateTo to set
	 */
	public void setFinanceDateTo(String financeDateTo) {
		this.financeDateTo = financeDateTo;
	}

	/**
	 * @return the employee
	 */
	public String getEmployee() {
		return employee;
	}

	/**
	 * @param employee the employee to set
	 */
	public void setEmployee(String employee) {
		this.employee = employee;
	}

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
	 * @return the expenseType
	 */
	public String getExpenseType() {
		return expenseType;
	}

	/**
	 * @param expenseType the expenseType to set
	 */
	public void setExpenseType(String expenseType) {
		this.expenseType = expenseType;
	}

	/**
	 * @return the travelFlg
	 */
	public String getTravelFlg() {
		return travelFlg;
	}

	/**
	 * @param travelFlg the travelFlg to set
	 */
	public void setTravelFlg(String travelFlg) {
		this.travelFlg = travelFlg;
	}

	/**
	 * @return the costCenterFlg
	 */
	public String getCostCenterFlg() {
		return costCenterFlg;
	}

	/**
	 * @param costCenterFlg the costCenterFlg to set
	 */
	public void setCostCenterFlg(String costCenterFlg) {
		this.costCenterFlg = costCenterFlg;
	}

	/**
	 * @return the employeeFlg
	 */
	public String getEmployeeFlg() {
		return employeeFlg;
	}

	/**
	 * @param employeeFlg the employeeFlg to set
	 */
	public void setEmployeeFlg(String employeeFlg) {
		this.employeeFlg = employeeFlg;
	}

	/**
	 * @return the expensesTypeFlg
	 */
	public String getExpensesTypeFlg() {
		return expensesTypeFlg;
	}

	/**
	 * @param expensesTypeFlg the expensesTypeFlg to set
	 */
	public void setExpensesTypeFlg(String expensesTypeFlg) {
		this.expensesTypeFlg = expensesTypeFlg;
	}

	/**
	 * @return the headList
	 */
	public List<SeRcReInHeadListBean> getHeadList() {
		return headList;
	}

	/**
	 * @param headList the headList to set
	 */
	public void setHeadList(List<SeRcReInHeadListBean> headList) {
		this.headList = headList;
	}

	/**
	 * @return the bodyList
	 */
	public List<SeRcReInBodyListBean> getBodyList() {
		return bodyList;
	}

	/**
	 * @param bodyList the bodyList to set
	 */
	public void setBodyList(List<SeRcReInBodyListBean> bodyList) {
		this.bodyList = bodyList;
	}
	
	

	
}
