package com.diasorin.oa.dto;

/**
 * 用于显示审批记录
 * @author linliuan
 *
 */
public class AuthorityByListBean {
	
	private String authorityBy;
	
	private String authorityDate;
	
	private String comment;
	
	private String statusView;

	/**
	 * @return the authorityBy
	 */
	public String getAuthorityBy() {
		return authorityBy;
	}

	/**
	 * @param authorityBy the authorityBy to set
	 */
	public void setAuthorityBy(String authorityBy) {
		this.authorityBy = authorityBy;
	}

	/**
	 * @return the authorityDate
	 */
	public String getAuthorityDate() {
		return authorityDate;
	}

	/**
	 * @param authorityDate the authorityDate to set
	 */
	public void setAuthorityDate(String authorityDate) {
		this.authorityDate = authorityDate;
	}

	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * @return the statusView
	 */
	public String getStatusView() {
		return statusView;
	}

	/**
	 * @param statusView the statusView to set
	 */
	public void setStatusView(String statusView) {
		this.statusView = statusView;
	}
	
}	
