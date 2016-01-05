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
@Table(name="t_employee_info")
public class EmployeeInfo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	//序列号
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long no;
	//用户工号
	private String employeeNo;
	//用户中文姓名
	private String employeeNameCn;
	//用户英文姓名
	private String employeeNameEn;
	//头像
	private String headPic;
	//性别
	private String sex;
	//级别Code
	private String levelCode;
	//角色Code
	private String roleCode;
	//部门Code
	private String deptCode;
	// 可服务Cost Center
	private String serviceCostCenter;
	//手机号码
	private String mobilePhone;
	//电子邮件
	private String email;
	//联系地址
	private String contactAddress;
	//常驻地
	private String liveLocation;
	//删除区分
	private String deleteFlg;
	//登录时间
	private Timestamp addTimestamp;
	//登录者
	private String addUserKey;
	//更新时间
	private Timestamp updTimestamp;
	//更新者
	private String updUserKey;
	//更新PGMID
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
	 * @return the employeeNameCn
	 */
	public String getEmployeeNameCn() {
		return employeeNameCn;
	}
	/**
	 * @param employeeNameCn the employeeNameCn to set
	 */
	public void setEmployeeNameCn(String employeeNameCn) {
		this.employeeNameCn = employeeNameCn;
	}
	/**
	 * @return the employeeNameEn
	 */
	public String getEmployeeNameEn() {
		return employeeNameEn;
	}
	/**
	 * @param employeeNameEn the employeeNameEn to set
	 */
	public void setEmployeeNameEn(String employeeNameEn) {
		this.employeeNameEn = employeeNameEn;
	}
	/**
	 * @return the sex
	 */
	public String getSex() {
		return sex;
	}
	/**
	 * @param sex the sex to set
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}
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
	 * @return the roleCode
	 */
	public String getRoleCode() {
		return roleCode;
	}
	/**
	 * @param roleCode the roleCode to set
	 */
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	/**
	 * @return the deptCode
	 */
	public String getDeptCode() {
		return deptCode;
	}
	/**
	 * @param deptCode the deptCode to set
	 */
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	/**
	 * @return the mobilePhone
	 */
	public String getMobilePhone() {
		return mobilePhone;
	}
	/**
	 * @param mobilePhone the mobilePhone to set
	 */
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the contactAddress
	 */
	public String getContactAddress() {
		return contactAddress;
	}
	/**
	 * @param contactAddress the contactAddress to set
	 */
	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}
	/**
	 * @return the liveLocation
	 */
	public String getLiveLocation() {
		return liveLocation;
	}
	/**
	 * @param liveLocation the liveLocation to set
	 */
	public void setLiveLocation(String liveLocation) {
		this.liveLocation = liveLocation;
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
	 * @return the serviceCostCenter
	 */
	public String getServiceCostCenter() {
		return serviceCostCenter;
	}
	/**
	 * @param serviceCostCenter the serviceCostCenter to set
	 */
	public void setServiceCostCenter(String serviceCostCenter) {
		this.serviceCostCenter = serviceCostCenter;
	}
	/**
	 * @return the headPic
	 */
	public String getHeadPic() {
		return headPic;
	}
	/**
	 * @param headPic the headPic to set
	 */
	public void setHeadPic(String headPic) {
		this.headPic = headPic;
	}

	
}
