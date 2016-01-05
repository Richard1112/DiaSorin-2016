package com.diasorin.oa.dto;

import java.util.ArrayList;
import java.util.List;

public class SeRcReInBodyListBean {
	
	private String employee;
	
	private String travelLocation;
	
	private String employeeNo;
	
	private List<String> amountList = new ArrayList<String>();

	/**
	 * @return the employee
	 */
	public String getEmployee() {
		return employee;
	}

	/**
	 * @param employee the employee to set
	 */
	public void setEmployee(String employee) {
		this.employee = employee;
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
	 * @return the amountList
	 */
	public List<String> getAmountList() {
		return amountList;
	}

	/**
	 * @param amountList the amountList to set
	 */
	public void setAmountList(List<String> amountList) {
		this.amountList = amountList;
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
}
