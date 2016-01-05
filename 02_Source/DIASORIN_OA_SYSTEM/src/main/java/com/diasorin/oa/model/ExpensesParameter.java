package com.diasorin.oa.model;

import java.io.Serializable;
import java.math.BigDecimal;

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
@Table(name="t_expenses_parameter")
public class ExpensesParameter implements Serializable {
	private static final long serialVersionUID = 1L;

	// 序列号
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long no;
	// 出差地类型
	private String travelLocal;
	// 报销项目Code
	private String expenseCode;
	// 人员级别Code
	private String employeeLevelCode;
	// 报销费用上限
	private BigDecimal allowExpensesUp;
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
	 * @return the travelLocal
	 */
	public String getTravelLocal() {
		return travelLocal;
	}
	/**
	 * @param travelLocal the travelLocal to set
	 */
	public void setTravelLocal(String travelLocal) {
		this.travelLocal = travelLocal;
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
	 * @return the employeeLevelCode
	 */
	public String getEmployeeLevelCode() {
		return employeeLevelCode;
	}
	/**
	 * @param employeeLevelCode the employeeLevelCode to set
	 */
	public void setEmployeeLevelCode(String employeeLevelCode) {
		this.employeeLevelCode = employeeLevelCode;
	}
	/**
	 * @return the allowExpensesUp
	 */
	public BigDecimal getAllowExpensesUp() {
		return allowExpensesUp;
	}
	/**
	 * @param allowExpensesUp the allowExpensesUp to set
	 */
	public void setAllowExpensesUp(BigDecimal allowExpensesUp) {
		this.allowExpensesUp = allowExpensesUp;
	}
	
	
}