package com.diasorin.oa.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

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
@Table(name="t_expenses_purpose_sum")
public class ExpensePurposeSum implements Serializable {
	private static final long serialVersionUID = 1L;

	// 序列号
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long no;
	// 报销目的合计编号
	private String expensesDetailsNo;
	// 所属报销申请
	private String belongExpensesAppNo;
	// 报销目的
	private String expensesPurpose;
	// 目的合计费用
	private BigDecimal expensesAmount;
	// 删除区分
	private String deleteFlg;
	// 登录时间
	private Timestamp addTimestamp;
	// 登录者
	private String addUserKey;
	// 更新时间
	private Timestamp updTimestamp;
	// 更新着
	private String updUserKey;
	// 更新PGMID
	private String updPgmId;
	
	
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
	 * @return the expensesDetailsNo
	 */
	public String getExpensesDetailsNo() {
		return expensesDetailsNo;
	}
	/**
	 * @param expensesDetailsNo the expensesDetailsNo to set
	 */
	public void setExpensesDetailsNo(String expensesDetailsNo) {
		this.expensesDetailsNo = expensesDetailsNo;
	}
	/**
	 * @return the belongExpensesAppNo
	 */
	public String getBelongExpensesAppNo() {
		return belongExpensesAppNo;
	}
	/**
	 * @param belongExpensesAppNo the belongExpensesAppNo to set
	 */
	public void setBelongExpensesAppNo(String belongExpensesAppNo) {
		this.belongExpensesAppNo = belongExpensesAppNo;
	}
	/**
	 * @return the expensesPurpose
	 */
	public String getExpensesPurpose() {
		return expensesPurpose;
	}
	/**
	 * @param expensesPurpose the expensesPurpose to set
	 */
	public void setExpensesPurpose(String expensesPurpose) {
		this.expensesPurpose = expensesPurpose;
	}
	/**
	 * @return the expensesAmount
	 */
	public BigDecimal getExpensesAmount() {
		return expensesAmount;
	}
	/**
	 * @param expensesAmount the expensesAmount to set
	 */
	public void setExpensesAmount(BigDecimal expensesAmount) {
		this.expensesAmount = expensesAmount;
	}
	/**
	 * @return the deleteFlg
	 */
	public String getDeleteFlg() {
		return deleteFlg;
	}
	/**
	 * @param deleteFlg the deleteFlg to set
	 */
	public void setDeleteFlg(String deleteFlg) {
		this.deleteFlg = deleteFlg;
	}
	/**
	 * @return the addTimestamp
	 */
	public Timestamp getAddTimestamp() {
		return addTimestamp;
	}
	/**
	 * @param addTimestamp the addTimestamp to set
	 */
	public void setAddTimestamp(Timestamp addTimestamp) {
		this.addTimestamp = addTimestamp;
	}
	/**
	 * @return the addUserKey
	 */
	public String getAddUserKey() {
		return addUserKey;
	}
	/**
	 * @param addUserKey the addUserKey to set
	 */
	public void setAddUserKey(String addUserKey) {
		this.addUserKey = addUserKey;
	}
	/**
	 * @return the updTimestamp
	 */
	public Timestamp getUpdTimestamp() {
		return updTimestamp;
	}
	/**
	 * @param updTimestamp the updTimestamp to set
	 */
	public void setUpdTimestamp(Timestamp updTimestamp) {
		this.updTimestamp = updTimestamp;
	}
	/**
	 * @return the updUserKey
	 */
	public String getUpdUserKey() {
		return updUserKey;
	}
	/**
	 * @param updUserKey the updUserKey to set
	 */
	public void setUpdUserKey(String updUserKey) {
		this.updUserKey = updUserKey;
	}
	/**
	 * @return the updPgmId
	 */
	public String getUpdPgmId() {
		return updPgmId;
	}
	/**
	 * @param updPgmId the updPgmId to set
	 */
	public void setUpdPgmId(String updPgmId) {
		this.updPgmId = updPgmId;
	}
}