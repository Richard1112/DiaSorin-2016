package com.diasorin.oa.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Reports For SAP
 * @author linliuan
 *
 */
public class SeRcReMoSapBean {
	
	private String status;
	
	private String dateFrom;
	
	private String dateTo;
	
	// 是否已经全选
	private String isAllChecked;
	
	// 所有选中的明细项目ID
	private String detailId;
	
	// 所有选中的明细项目ID(隐藏项目)
	private String hiddenDetailId;
	
	// 所有选中的明细项目总金额
	private String totalAmount;
	
	// 所有选中的明细项目总金额
	private String selectAmount;
	
	// 明细项目的条数
	private String totalCount;
	
	// 总得所有记录
	private String selectCount;
	
	private List<SeRcReMoBean> beanList = new ArrayList<SeRcReMoBean>();

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the dateFrom
	 */
	public String getDateFrom() {
		return dateFrom;
	}

	/**
	 * @param dateFrom the dateFrom to set
	 */
	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}

	/**
	 * @return the dateTo
	 */
	public String getDateTo() {
		return dateTo;
	}

	/**
	 * @param dateTo the dateTo to set
	 */
	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}

	/**
	 * @return the beanList
	 */
	public List<SeRcReMoBean> getBeanList() {
		return beanList;
	}

	/**
	 * @param beanList the beanList to set
	 */
	public void setBeanList(List<SeRcReMoBean> beanList) {
		this.beanList = beanList;
	}

	/**
	 * @return the isAllChecked
	 */
	public String getIsAllChecked() {
		return isAllChecked;
	}

	/**
	 * @param isAllChecked the isAllChecked to set
	 */
	public void setIsAllChecked(String isAllChecked) {
		this.isAllChecked = isAllChecked;
	}

	/**
	 * @return the detailId
	 */
	public String getDetailId() {
		return detailId;
	}

	/**
	 * @param detailId the detailId to set
	 */
	public void setDetailId(String detailId) {
		this.detailId = detailId;
	}

	/**
	 * @return the totalAmount
	 */
	public String getTotalAmount() {
		return totalAmount;
	}

	/**
	 * @param totalAmount the totalAmount to set
	 */
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	/**
	 * @return the totalCount
	 */
	public String getTotalCount() {
		return totalCount;
	}

	/**
	 * @param totalCount the totalCount to set
	 */
	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * @return the selectCount
	 */
	public String getSelectCount() {
		return selectCount;
	}

	/**
	 * @param selectCount the selectCount to set
	 */
	public void setSelectCount(String selectCount) {
		this.selectCount = selectCount;
	}

	/**
	 * @return the selectAmount
	 */
	public String getSelectAmount() {
		return selectAmount;
	}

	/**
	 * @param selectAmount the selectAmount to set
	 */
	public void setSelectAmount(String selectAmount) {
		this.selectAmount = selectAmount;
	}

	/**
	 * @return the hiddenDetailId
	 */
	public String getHiddenDetailId() {
		return hiddenDetailId;
	}

	/**
	 * @param hiddenDetailId the hiddenDetailId to set
	 */
	public void setHiddenDetailId(String hiddenDetailId) {
		this.hiddenDetailId = hiddenDetailId;
	}


}
