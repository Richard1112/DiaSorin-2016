package com.diasorin.oa.model;

import java.io.Serializable;
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
@Table(name="t_employee_login_history")
public class EmployeeLoginHistroy implements Serializable {
	private static final long serialVersionUID = 1L;

	// 序列号
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long no;
	// 用户工号
	private String employeeNo;
	// 登陆时间
	private Timestamp loginTimestamp;
	// 退出时间
	private Timestamp logoutTimestamp;
	
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
	 * @return the employeeNo
	 */
	public String getEmployeeNo() {
		return employeeNo;
	}
	/**
	 * @param employeeNo the employeeNo to set
	 */
	public void setEmployeeNo(String employeeNo) {
		this.employeeNo = employeeNo;
	}
	/**
	 * @return the loginTimestamp
	 */
	public Timestamp getLoginTimestamp() {
		return loginTimestamp;
	}
	/**
	 * @param loginTimestamp the loginTimestamp to set
	 */
	public void setLoginTimestamp(Timestamp loginTimestamp) {
		this.loginTimestamp = loginTimestamp;
	}
	/**
	 * @return the logoutTimestamp
	 */
	public Timestamp getLogoutTimestamp() {
		return logoutTimestamp;
	}
	/**
	 * @param logoutTimestamp the logoutTimestamp to set
	 */
	public void setLogoutTimestamp(Timestamp logoutTimestamp) {
		this.logoutTimestamp = logoutTimestamp;
	}
	

}