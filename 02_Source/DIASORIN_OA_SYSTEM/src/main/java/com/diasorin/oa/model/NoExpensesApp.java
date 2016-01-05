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
@Table(name="t_no_expenses_app")
public class NoExpensesApp implements Serializable {
	private static final long serialVersionUID = 1L;

	// 序列号
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long no;
	// 当前
	private String currentYear;
	// 最大申请编号
	private String maxAppNumber;
	
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
	 * @return the maxAppNumber
	 */
	public String getMaxAppNumber() {
		return maxAppNumber;
	}
	/**
	 * @param maxAppNumber the maxAppNumber to set
	 */
	public void setMaxAppNumber(String maxAppNumber) {
		this.maxAppNumber = maxAppNumber;
	}
	/**
	 * @return the currentYear
	 */
	public String getCurrentYear() {
		return currentYear;
	}
	/**
	 * @param currentYear the currentYear to set
	 */
	public void setCurrentYear(String currentYear) {
		this.currentYear = currentYear;
	}

}