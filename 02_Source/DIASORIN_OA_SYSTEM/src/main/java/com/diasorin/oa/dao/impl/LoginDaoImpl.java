package com.diasorin.oa.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.diasorin.oa.common.QueryResult;
import com.diasorin.oa.dao.LoginDao;
import com.diasorin.oa.model.EmployeeLogin;
import com.diasorin.oa.model.EmployeeLoginHistroy;

@Component
public class LoginDaoImpl extends BaseDaoImpl implements LoginDao {

	@Override
	public EmployeeLogin getLoginInfo(String loginId, String password)
			throws Exception {
		try {
			String sql = " ";
			List<String> param = new ArrayList<String>();
			sql += " and o.employeeNo = ? ";
			param.add(loginId);
		
			sql += " and o.employeePassword = ? ";
			param.add(password);
			sql += " and o.deleteFlg = '0' ";
			QueryResult<EmployeeLogin> qs = getScrollData(EmployeeLogin.class, sql, param.toArray());
			if (qs.getResultlist().size() > 0) {
				return qs.getResultlist().get(0);
			}
			return null;
		} catch(Exception e) {
			throw e;
		}
	}

	@Override
	public List<EmployeeLoginHistroy> getLogoutInfo(String userId)
			throws Exception {	
		try {
			String sql = " ";
			List<String> param = new ArrayList<String>();
			
			sql += " and o.employeeNo = ? ";
			param.add(userId);
			
			sql += " and o.logoutTimestamp is null ";
			
			QueryResult<EmployeeLoginHistroy> qs = getScrollData(EmployeeLoginHistroy.class, sql, param.toArray());
			if (qs.getResultlist().size() > 0) {
				return qs.getResultlist();
			}
			return null;
		} catch(Exception e) {
			throw e;
		}
	}

}
