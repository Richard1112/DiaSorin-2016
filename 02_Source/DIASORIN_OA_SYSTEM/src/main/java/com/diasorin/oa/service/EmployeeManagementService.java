package com.diasorin.oa.service;

import java.util.List;

import com.diasorin.oa.common.QueryResult;
import com.diasorin.oa.dto.SeEmPeLsBean;
import com.diasorin.oa.model.EmployeeInfo;
import com.diasorin.oa.model.EmployeeLogin;


/**
 * 用户管理模块用
 * @author linliuan
 *
 */
public interface EmployeeManagementService {

	// 取得用户信息一览 -- 可以分页用
	public QueryResult<EmployeeInfo> employeeListQuery(int start, int end, String orderBy, SeEmPeLsBean seEmPeLsBean) throws Exception;
	
	// 取得用户信息 -- 根据对个成本中心
	public List<EmployeeInfo> employeeListQueryByCostCenter(String costCenter) throws Exception;
		
	// 保存用户
	public boolean employeeAdd(EmployeeInfo employeeInfo) throws Exception;
	
	// 更新用户
	public boolean employeeInfoUpdate(EmployeeInfo employeeInfo) throws Exception;
	
	// 删除用户
	public boolean employeeInfoDelete(EmployeeInfo employeeInfo) throws Exception;
	
	// 取得指定用户信息
	public EmployeeInfo employeeInfoView(String employeeNo) throws Exception;
	
	// 员工密码修改
	public boolean employeePasswordChange(EmployeeLogin employeeLogin) throws Exception;
	
	// 根据员工号取得员工登录信息
	public EmployeeLogin getEmployeeByNo(String employeeNo) throws Exception;
	
}
