package com.diasorin.oa.dao;

import java.util.List;

import com.diasorin.oa.model.SysModuleInfo;

public interface MenuDao {

	//取得指定用户所权限下地模块
	public List<SysModuleInfo> getModuleInfo(String userId) throws Exception;
}
