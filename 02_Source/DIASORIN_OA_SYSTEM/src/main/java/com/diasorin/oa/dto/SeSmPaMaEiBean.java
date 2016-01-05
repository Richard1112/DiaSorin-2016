package com.diasorin.oa.dto;

public class SeSmPaMaEiBean {

	// 序列号
	private String no;
	// 报销项目Code
	private String expenseCode;
	// 报销项目Name
	private String expenseName;
	// 所属报销项目Code
	private String fatherExpenseCode;
	// 财务科目号
	private String financeNo;
	// 排序
	private String showOrderNo;
	/**
	 * @return the expenseCode
	 */
	public String getExpenseCode() {
		return expenseCode;
	}
	/**
	 * @param expenseCode the expenseCode to set
	 */
	public void setExpenseCode(String expenseCode) {
		this.expenseCode = expenseCode;
	}
	/**
	 * @return the expenseName
	 */
	public String getExpenseName() {
		return expenseName;
	}
	/**
	 * @param expenseName the expenseName to set
	 */
	public void setExpenseName(String expenseName) {
		this.expenseName = expenseName;
	}
	/**
	 * @return the fatherExpenseCode
	 */
	public String getFatherExpenseCode() {
		return fatherExpenseCode;
	}
	/**
	 * @param fatherExpenseCode the fatherExpenseCode to set
	 */
	public void setFatherExpenseCode(String fatherExpenseCode) {
		this.fatherExpenseCode = fatherExpenseCode;
	}
	/**
	 * @return the no
	 */
	public String getNo() {
		return no;
	}
	/**
	 * @param no the no to set
	 */
	public void setNo(String no) {
		this.no = no;
	}
	/**
	 * @return the financeNo
	 */
	public String getFinanceNo() {
		return financeNo;
	}
	/**
	 * @param financeNo the financeNo to set
	 */
	public void setFinanceNo(String financeNo) {
		this.financeNo = financeNo;
	}
	/**
	 * @return the showOrderNo
	 */
	public String getShowOrderNo() {
		return showOrderNo;
	}
	/**
	 * @param showOrderNo the showOrderNo to set
	 */
	public void setShowOrderNo(String showOrderNo) {
		this.showOrderNo = showOrderNo;
	}
	
	
}
