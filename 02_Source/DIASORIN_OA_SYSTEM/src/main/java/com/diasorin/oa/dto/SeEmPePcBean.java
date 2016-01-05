package com.diasorin.oa.dto;

/**
 * 密码变更用
 * @author liuan
 *
 */
public class SeEmPePcBean {

	private String nowPassword;
	
	private String newPassword;
	
	private String confirmPassword;

	public String getNowPassword() {
		return nowPassword;
	}

	public void setNowPassword(String nowPassword) {
		this.nowPassword = nowPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
}
