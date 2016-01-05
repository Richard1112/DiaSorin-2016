package com.diasorin.oa.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.diasorin.oa.dao.MenuDao;
import com.diasorin.oa.model.EmployeeInfo;
import com.diasorin.oa.model.SysModuleInfo;
import com.diasorin.oa.service.MenuService;

@Service("menuService")
public class MenuServiceImpl extends BaseServiceImpl implements MenuService {

	@Resource
	MenuDao menuDaoImpl;
	
	@Override
	public List<SysModuleInfo> getModuleInfo(String userId) throws Exception {
		return menuDaoImpl.getModuleInfo(userId);
	}

	@Override
	public EmployeeInfo getEmployeeInfo(String userId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean updateEmployeeInfo(EmployeeInfo EmployeeInfo)
			throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

}
