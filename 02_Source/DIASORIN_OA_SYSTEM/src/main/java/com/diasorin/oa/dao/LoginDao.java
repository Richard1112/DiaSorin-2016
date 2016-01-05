package com.diasorin.oa.dao;

import java.util.List;

import com.diasorin.oa.model.EmployeeLogin;
import com.diasorin.oa.model.EmployeeLoginHistroy;

public interface LoginDao {

	// 检测用户，密码是否正确
	public EmployeeLogin getLoginInfo(String loginId,String password) throws Exception;
	
	// 登出操作
	public List<EmployeeLoginHistroy> getLogoutInfo(String userId) throws Exception;
	
}
