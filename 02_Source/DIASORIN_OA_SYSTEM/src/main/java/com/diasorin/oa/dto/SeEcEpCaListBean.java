package com.diasorin.oa.dto;

public class SeEcEpCaListBean {

	// No
	private String no;
	// 目的
	private String purpose;
	// 出差原因
	private String total;
	// 申请明细项目NO	
	private String detailNo;
	// 申请No(已经申请过的)
	private String expensesAppNo;
	// purposeNo(已经申请过的)
	private String purposeNo;
	// rejectErrorFlg
	private String rejectErrorFlg;
	
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
	 * @return the purpose
	 */
	public String getPurpose() {
		return purpose;
	}
	/**
	 * @param purpose the purpose to set
	 */
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	/**
	 * @return the total
	 */
	public String getTotal() {
		return total;
	}
	/**
	 * @param total the total to set
	 */
	public void setTotal(String total) {
		this.total = total;
	}
	/**
	 * @return the detailNo
	 */
	public String getDetailNo() {
		return detailNo;
	}
	/**
	 * @param detailNo the detailNo to set
	 */
	public void setDetailNo(String detailNo) {
		this.detailNo = detailNo;
	}
	/**
	 * @return the expensesAppNo
	 */
	public String getExpensesAppNo() {
		return expensesAppNo;
	}
	/**
	 * @param expensesAppNo the expensesAppNo to set
	 */
	public void setExpensesAppNo(String expensesAppNo) {
		this.expensesAppNo = expensesAppNo;
	}
	/**
	 * @return the purposeNo
	 */
	public String getPurposeNo() {
		return purposeNo;
	}
	/**
	 * @param purposeNo the purposeNo to set
	 */
	public void setPurposeNo(String purposeNo) {
		this.purposeNo = purposeNo;
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
	
}
