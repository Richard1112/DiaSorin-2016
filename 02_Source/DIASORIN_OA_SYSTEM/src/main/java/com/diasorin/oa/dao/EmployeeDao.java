package com.diasorin.oa.dao;

import java.util.List;

import com.diasorin.oa.common.QueryResult;
import com.diasorin.oa.dto.SeEmPeLsBean;
import com.diasorin.oa.model.EmployeeInfo;
import com.diasorin.oa.model.EmployeeLogin;

public interface EmployeeDao {
	
	// 取得指定用户信息
	public EmployeeInfo getEmployeeInfo(String employeeNo) throws Exception;
	
	// 取得用户信息一览
	public QueryResult<EmployeeInfo> getEmployeeInfoList(int start, int end, String orderBy, SeEmPeLsBean seEmPeLsBean) throws Exception;

	// 取得用户登录信息
	public EmployeeLogin getEmployeeByNo(String employeeNo) throws Exception;
	
	// 取得用户信息--指定部门
	public List<EmployeeInfo> employeeListQueryByCostCenter(String costCenter) throws Exception;
}
