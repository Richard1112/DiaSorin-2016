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
@Table(name="t_sys_travel_local")
public class SysTravelLocal implements Serializable {
	private static final long serialVersionUID = 1L;

	// 序列号
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long no;
	// 出差地类型
	private String travelLocalType;
	// 出差地Code
	private String travelCode;
	// 出差地Name
	private String travelName;
	
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
		
}