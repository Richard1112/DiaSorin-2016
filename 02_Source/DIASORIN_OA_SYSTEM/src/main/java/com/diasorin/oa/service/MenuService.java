package com.diasorin.oa.service;

import java.util.List;

import com.diasorin.oa.model.EmployeeInfo;
import com.diasorin.oa.model.SysModuleInfo;

/**
 * 菜单画面所调用的服务
 * @author linliuan
 *
 */
public interface MenuService {

	// 通过用户ID获取有权限进行操作的模块
	public List<SysModuleInfo> getModuleInfo(String userId) throws Exception;
	
	// 菜单主画面所调用，个人设置初期化
	public EmployeeInfo getEmployeeInfo(String userId) throws Exception;
	
	// 个人设置的更新操作
	public boolean updateEmployeeInfo(EmployeeInfo EmployeeInfo) throws Exception;
	
	
}
