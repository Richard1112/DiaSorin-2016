package com.diasorin.oa.dto;

/**
 * 报销设定
 * @author liuan
 *
 */
public class SeSmEpMaListBean {

	// 级别CODE
	private String levelCode;
	
	// 级别名称
	private String levelName;
	
	// 出差地类型Code
	private String travelLocalTypeCode;
	
	// 出差地类型
	private String travelLocalType;
	
	// 允许报销上限
	private String allowExpensesUp;

	/**
	 * @return the levelCode
	 */
	public String getLevelCode() {
		return levelCode;
	}

	/**
	 * @param levelCode the levelCode to set
	 */
	public void setLevelCode(String levelCode) {
		this.levelCode = levelCode;
	}

	/**
	 * @return the levelName
	 */
	public String getLevelName() {
		return levelName;
	}

	/**
	 * @param levelName the levelName to set
	 */
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	/**
	 * @return the travelLocalType
	 */
	public String getTravelLocalType() {
		return travelLocalType;
	}

	/**
	 * @param travelLocalType the travelLocalType to set
	 */
	public void setTravelLocalType(String travelLocalType) {
		this.travelLocalType = travelLocalType;
	}

	/**
	 * @return the allowExpensesUp
	 */
	public String getAllowExpensesUp() {
		return allowExpensesUp;
	}

	/**
	 * @param allowExpensesUp the allowExpensesUp to set
	 */
	public void setAllowExpensesUp(String allowExpensesUp) {
		this.allowExpensesUp = allowExpensesUp;
	}

	/**
	 * @return the travelLocalTypeCode
	 */
	public String getTravelLocalTypeCode() {
		return travelLocalTypeCode;
	}

	/**
	 * @param travelLocalTypeCode the travelLocalTypeCode to set
	 */
	public void setTravelLocalTypeCode(String travelLocalTypeCode) {
		this.travelLocalTypeCode = travelLocalTypeCode;
	}

	
}
