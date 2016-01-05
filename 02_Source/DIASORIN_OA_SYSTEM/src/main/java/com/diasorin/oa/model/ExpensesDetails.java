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
@Table(name="t_expenses_details")
public class ExpensesDetails implements Serializable {
	private static final long serialVersionUID = 1L;

	// 序列号
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long no;
	// 报销明细编号
	private String expensesDetailsNo;
	// 所属报销申请
	private String belongExpensesAppNo;
	// 报销对象日期模式
	private String expensesDateType;
	// 报销对象日期
	private String expensesDate;
	// 报销对象日期End
	private String expensesDateEnd;
	// 出差地
	private String travelLocation;
	// 报销项目
	private String expensesItem;
	// 里程数
	private BigDecimal kilometers;
	// 报销费用
	private BigDecimal expensesAmount;
	// 报销备注
	private String expensesComments;
	// Reject错误标识
	private String rejectErrorFlg;
	// 删除区分
	private String deleteFlg;
	// 登录时间
	private Timestamp addTimestamp;
	// 登录者
	private String addUserKey;
	// 更新时间
	private Timestamp updTimestamp;
	// 更新者
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
	 * @return the expensesDateType
	 */
	public String getExpensesDateType() {
		return expensesDateType;
	}
	/**
	 * @param expensesDateType the expensesDateType to set
	 */
	public void setExpensesDateType(String expensesDateType) {
		this.expensesDateType = expensesDateType;
	}
	/**
	 * @return the expensesDate
	 */
	public String getExpensesDate() {
		return expensesDate;
	}
	/**
	 * @param expensesDate the expensesDate to set
	 */
	public void setExpensesDate(String expensesDate) {
		this.expensesDate = expensesDate;
	}
	/**
	 * @return the expensesDateEnd
	 */
	public String getExpensesDateEnd() {
		return expensesDateEnd;
	}
	/**
	 * @param expensesDateEnd the expensesDateEnd to set
	 */
	public void setExpensesDateEnd(String expensesDateEnd) {
		this.expensesDateEnd = expensesDateEnd;
	}
	/**
	 * @return the travelLocation
	 */
	public String getTravelLocation() {
		return travelLocation;
	}
	/**
	 * @param travelLocation the travelLocation to set
	 */
	public void setTravelLocation(String travelLocation) {
		this.travelLocation = travelLocation;
	}
	/**
	 * @return the expensesItem
	 */
	public String getExpensesItem() {
		return expensesItem;
	}
	/**
	 * @param expensesItem the expensesItem to set
	 */
	public void setExpensesItem(String expensesItem) {
		this.expensesItem = expensesItem;
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
	 * @return the expensesComments
	 */
	public String getExpensesComments() {
		return expensesComments;
	}
	/**
	 * @param expensesComments the expensesComments to set
	 */
	public void setExpensesComments(String expensesComments) {
		this.expensesComments = expensesComments;
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
	/**
	 * @return the rejectErrorFlg
	 */
	public String getRejectErrorFlg() {
		return rejectErrorFlg;
	}
	/**
	 * @param rejectErrorFlg the rejectErrorFlg to set
	 */
	public void setRejectErrorFlg(String rejectErrorFlg) {
		this.rejectErrorFlg = rejectErrorFlg;
	}
	public BigDecimal getKilometers() {
		return kilometers;
	}
	public void setKilometers(BigDecimal kilometers) {
		this.kilometers = kilometers;
	}
	
}