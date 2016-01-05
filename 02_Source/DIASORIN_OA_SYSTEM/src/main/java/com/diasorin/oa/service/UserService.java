package com.diasorin.oa.service;

import com.diasorin.oa.model.EmployeeLogin;
import com.diasorin.oa.model.EmployeeLoginHistroy;

/**
 * 用户登录或者登出时调用的服务
 * @author linliuan
 *
 */
public interface UserService {

	// 检测用户，密码是否正确
	public EmployeeLogin userLogin(String loginId,String password) throws Exception;
	
	// 插入登录历史记录
	public boolean insertLoginHisAndUpdateStatus(EmployeeLoginHistroy employeeLoginHistroy) throws Exception;
	
	// 用户登出时更新登出记录
	public boolean loginOut(String userId) throws Exception;
	
}
