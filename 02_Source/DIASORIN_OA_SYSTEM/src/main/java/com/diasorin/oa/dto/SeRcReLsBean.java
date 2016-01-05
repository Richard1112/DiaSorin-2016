package com.diasorin.oa.dto;

import java.util.ArrayList;
import java.util.List;

public class SeRcReLsBean {

	// 报销发生日期开始
	private String occurDateFrom;
	// 报销发生日期结束
	private String occurDateTo;
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
	// 是否检索自己部门
	private String other;
	
	private List<SeRcReLsHeadListBean> headList = new ArrayList<SeRcReLsHeadListBean>();
	
	private List<SeRcReLsBodyListBean> bodyList = new ArrayList<SeRcReLsBodyListBean>();
	

	/**
	 * @return the occurDateFrom
	 */
	public String getOccurDateFrom() {
		return occurDateFrom;
	}

	/**
	 * @param occurDateFrom the occurDateFrom to set
	 */
	public void setOccurDateFrom(String occurDateFrom) {
		this.occurDateFrom = occurDateFrom;
	}

	/**
	 * @return the occurDateTo
	 */
	public String getOccurDateTo() {
		return occurDateTo;
	}

	/**
	 * @param occurDateTo the occurDateTo to set
	 */
	public void setOccurDateTo(String occurDateTo) {
		this.occurDateTo = occurDateTo;
	}

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
	 * @return the headList
	 */
	public List<SeRcReLsHeadListBean> getHeadList() {
		return headList;
	}

	/**
	 * @param headList the headList to set
	 */
	public void setHeadList(List<SeRcReLsHeadListBean> headList) {
		this.headList = headList;
	}

	/**
	 * @return the bodyList
	 */
	public List<SeRcReLsBodyListBean> getBodyList() {
		return bodyList;
	}

	/**
	 * @param bodyList the bodyList to set
	 */
	public void setBodyList(List<SeRcReLsBodyListBean> bodyList) {
		this.bodyList = bodyList;
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
	 * @return the other
	 */
	public String getOther() {
		return other;
	}

	/**
	 * @param other the other to set
	 */
	public void setOther(String other) {
		this.other = other;
	}
	
}
