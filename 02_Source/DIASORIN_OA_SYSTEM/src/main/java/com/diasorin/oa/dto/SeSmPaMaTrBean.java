package com.diasorin.oa.dto;

public class SeSmPaMaTrBean {

	// 序列号
	private Long no;
	// 出差地类型
	private String travelLocalType;
	// 出差地Code
	private String travelCode;
	// 出差地Name
	private String travelName;
	
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
	 * @return the travelCode
	 */
	public String getTravelCode() {
		return travelCode;
	}
	/**
	 * @param travelCode the travelCode to set
	 */
	public void setTravelCode(String travelCode) {
		this.travelCode = travelCode;
	}
	/**
	 * @return the travelName
	 */
	public String getTravelName() {
		return travelName;
	}
	/**
	 * @param travelName the travelName to set
	 */
	public void setTravelName(String travelName) {
		this.travelName = travelName;
	}
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
}
