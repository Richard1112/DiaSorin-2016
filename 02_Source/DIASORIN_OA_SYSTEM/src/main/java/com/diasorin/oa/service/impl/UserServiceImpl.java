package com.diasorin.oa.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.diasorin.oa.common.CodeCommon;
import com.diasorin.oa.common.ErrCommon;
import com.diasorin.oa.dao.BaseDao;
import com.diasorin.oa.dao.EmployeeDao;
import com.diasorin.oa.dao.LoginDao;
import com.diasorin.oa.model.EmployeeLogin;
import com.diasorin.oa.model.EmployeeLoginHistroy;
import com.diasorin.oa.service.UserService;

@Service("userService")
public class UserServiceImpl extends BaseServiceImpl implements UserService {

	@Resource
	BaseDao baseDaoImpl;
	
	@Resource
	LoginDao loginDaoImpl;
	
	@Resource
	EmployeeDao employeeDaoImpl;
	
	@Override
	public EmployeeLogin userLogin(String loginId, String password)
			throws Exception {
		try {
			return loginDaoImpl.getLoginInfo(loginId, password);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return null;
		}
	}

	@Override
	public boolean insertLoginHisAndUpdateStatus(
			EmployeeLoginHistroy employeeLoginHistroy) throws Exception {
		try {
			// 插入员工登录历史记录
			employeeLoginHistroy.setLoginTimestamp(getCurrTimeStamp());
			baseDaoImpl.save(employeeLoginHistroy);
			// 更新员工登录信息
			EmployeeLogin employeeLogin = (EmployeeLogin) baseDaoImpl.getByIndex(
					EmployeeLogin.class, "employeeNo",
					employeeLoginHistroy.getEmployeeNo());
			employeeLogin.setLoginStatus(CodeCommon.HAS_LOGINED_STATUS);
			employeeLogin.setUpdTimestamp(getCurrTimeStamp());
			baseDaoImpl.update(employeeLogin);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return false;
		}
	}

	@Override
	public boolean loginOut(String userId) throws Exception {
		try {
			List<EmployeeLoginHistroy> hisList = loginDaoImpl
					.getLogoutInfo(userId);
			if (hisList != null && hisList.size() > 0) {
				for (EmployeeLoginHistroy his : hisList) {
					his.setLogoutTimestamp(getCurrTimeStamp());
					baseDaoImpl.update(his);
				}
			}
			// 更新完历史记录后更新员工基本信息
			// 更新员工登录信息
			EmployeeLogin employeeLogin = (EmployeeLogin) baseDaoImpl
					.getByIndex(EmployeeLogin.class, "employeeNo", userId);
			if (employeeLogin != null) {
				employeeLogin.setLoginStatus(CodeCommon.NOT_LOGIN_STATUS);
				employeeLogin.setUpdTimestamp(getCurrTimeStamp());
				baseDaoImpl.update(employeeLogin);
			}
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return false;
		}
	}

}
