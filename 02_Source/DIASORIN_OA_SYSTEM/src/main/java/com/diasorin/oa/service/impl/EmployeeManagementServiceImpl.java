package com.diasorin.oa.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.diasorin.oa.common.CodeCommon;
import com.diasorin.oa.common.ErrCommon;
import com.diasorin.oa.common.QueryResult;
import com.diasorin.oa.dao.BaseDao;
import com.diasorin.oa.dao.EmployeeDao;
import com.diasorin.oa.dto.SeEmPeLsBean;
import com.diasorin.oa.model.EmployeeInfo;
import com.diasorin.oa.model.EmployeeLogin;
import com.diasorin.oa.service.EmployeeManagementService;

@Service("employeeManagementService")
public class EmployeeManagementServiceImpl extends BaseServiceImpl implements
	EmployeeManagementService {

	@Resource 
	BaseDao baseDaoImpl;
	
	@Resource 
	EmployeeDao employeeDaoImpl;
	
	@Override
	public QueryResult<EmployeeInfo> employeeListQuery(int start, int end, String orderBy, SeEmPeLsBean seEmPeLsBean) throws Exception {
		try{
			return employeeDaoImpl.getEmployeeInfoList(start, end, orderBy, seEmPeLsBean);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return null;
		}
	}

	@Override
	public boolean employeeAdd(EmployeeInfo employeeInfo) throws Exception {
		try{
			employeeInfo.setAddTimestamp(getCurrTimeStamp());
			employeeInfo.setDeleteFlg(CodeCommon.NOT_DELETE_FLG);
			baseDaoImpl.save(employeeInfo);
			
			EmployeeLogin employeeLogin = new EmployeeLogin();
			employeeLogin.setEmployeeNo(employeeInfo.getEmployeeNo());
			employeeLogin.setEmployeePassword("123456");//TODO
			employeeLogin.setLoginStatus(CodeCommon.NOT_LOGIN_STATUS);
			employeeLogin.setDeleteFlg(CodeCommon.NOT_DELETE_FLG);
			employeeLogin.setAddTimestamp(getCurrTimeStamp());
			employeeLogin.setAddUserKey(employeeInfo.getAddUserKey());
			baseDaoImpl.save(employeeLogin);
			// 保存用户的同时生成一条用户登录信息
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return false;
		}
	}

	@Override
	public boolean employeeInfoUpdate(EmployeeInfo employeeInfo) throws Exception {
		try{
			employeeInfo.setUpdTimestamp(getCurrTimeStamp());
			baseDaoImpl.update(employeeInfo);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return false;
		}
	}

	@Override
	public EmployeeInfo employeeInfoView(String employeeNo) throws Exception {
		try{
			return employeeDaoImpl.getEmployeeInfo(employeeNo);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return null;
		}
	}

	@Override
	public boolean employeePasswordChange(EmployeeLogin employeeLogin)
			throws Exception {
		try{
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
	public EmployeeLogin getEmployeeByNo(String employeeNo) throws Exception {
		try{
			return employeeDaoImpl.getEmployeeByNo(employeeNo);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return null;
		}
	}

	@Override
	public boolean employeeInfoDelete(EmployeeInfo employeeInfo)
			throws Exception {
		employeeInfo.setUpdTimestamp(getCurrTimeStamp());
		baseDaoImpl.update(employeeInfo);
		// 这里需要将登录表也更新掉
		EmployeeLogin employeeLogin = (EmployeeLogin) baseDaoImpl.getByIndex(EmployeeLogin.class, "employeeNo", employeeInfo.getEmployeeNo());
		employeeLogin.setDeleteFlg(CodeCommon.IS_DELETE_FLG);
		baseDaoImpl.update(employeeLogin);
		return false;
	}

	@Override
	public List<EmployeeInfo> employeeListQueryByCostCenter(String costCenter)
			throws Exception {
		return employeeDaoImpl.employeeListQueryByCostCenter(costCenter);
	}

}
