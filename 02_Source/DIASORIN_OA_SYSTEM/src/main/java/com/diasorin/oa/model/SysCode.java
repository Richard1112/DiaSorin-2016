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
@Table(name="t_sys_code")
public class SysCode implements Serializable {
	private static final long serialVersionUID = 1L;

	// 序列号
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long no;
	
	private String codeId;
	private String codeDetailId;
	private String codeName;
	private String codeDetailName;
	
	
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
	 * @return the codeId
	 */
	public String getCodeId() {
		return codeId;
	}
	/**
	 * @param codeId the codeId to set
	 */
	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}
	/**
	 * @return the codeDetailId
	 */
	public String getCodeDetailId() {
		return codeDetailId;
	}
	/**
	 * @param codeDetailId the codeDetailId to set
	 */
	public void setCodeDetailId(String codeDetailId) {
		this.codeDetailId = codeDetailId;
	}
	/**
	 * @return the codeName
	 */
	public String getCodeName() {
		return codeName;
	}
	/**
	 * @param codeName the codeName to set
	 */
	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}
	/**
	 * @return the codeDetailName
	 */
	public String getCodeDetailName() {
		return codeDetailName;
	}
	/**
	 * @param codeDetailName the codeDetailName to set
	 */
	public void setCodeDetailName(String codeDetailName) {
		this.codeDetailName = codeDetailName;
	}
}