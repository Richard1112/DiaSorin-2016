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
@Table(name="t_sys_expenses")
public class SysExpenses implements Serializable {
	private static final long serialVersionUID = 1L;

	// 序列号
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long no;
	// 报销项目Code
	private String expenseCode;
	// 报销项目Name
	private String expenseName;
	// 所属报销项目Code
	private String fatherExpenseCode;
	// 计费方式
	private String timeMethod; 
	// 财务科目号
	private String financeNo;
	// 排序
	private String showOrderNo;
	
	private String computeMethod;
	private String extendsFieldNm1;
	private String extendsFieldCo1;
	private String extendsFieldNm2;
	private String extendsFieldCo2;
	private String extendsFieldNm3;
	private String extendsFieldCo3;

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
	 * @return the timeMethod
	 */
	public String getTimeMethod() {
		return timeMethod;
	}
	/**
	 * @param timeMethod the timeMethod to set
	 */
	public void setTimeMethod(String timeMethod) {
		this.timeMethod = timeMethod;
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
	public String getComputeMethod() {
		return computeMethod;
	}
	public void setComputeMethod(String computeMethod) {
		this.computeMethod = computeMethod;
	}
	public String getExtendsFieldNm1() {
		return extendsFieldNm1;
	}
	public void setExtendsFieldNm1(String extendsFieldNm1) {
		this.extendsFieldNm1 = extendsFieldNm1;
	}
	public String getExtendsFieldCo1() {
		return extendsFieldCo1;
	}
	public void setExtendsFieldCo1(String extendsFieldCo1) {
		this.extendsFieldCo1 = extendsFieldCo1;
	}
	public String getExtendsFieldNm2() {
		return extendsFieldNm2;
	}
	public void setExtendsFieldNm2(String extendsFieldNm2) {
		this.extendsFieldNm2 = extendsFieldNm2;
	}
	public String getExtendsFieldCo2() {
		return extendsFieldCo2;
	}
	public void setExtendsFieldCo2(String extendsFieldCo2) {
		this.extendsFieldCo2 = extendsFieldCo2;
	}
	public String getExtendsFieldNm3() {
		return extendsFieldNm3;
	}
	public void setExtendsFieldNm3(String extendsFieldNm3) {
		this.extendsFieldNm3 = extendsFieldNm3;
	}
	public String getExtendsFieldCo3() {
		return extendsFieldCo3;
	}
	public void setExtendsFieldCo3(String extendsFieldCo3) {
		this.extendsFieldCo3 = extendsFieldCo3;
	}
	
}