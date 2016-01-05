package com.diasorin.oa.service;

import java.util.List;

import com.diasorin.oa.common.MyMap;

public interface BaseService {

	public List<MyMap> getSelect(String codeId) throws Exception;
	
	public String getSelect(String codeId, String codeDetailId) throws Exception;
	
	public List<MyMap> getFinanceSelect() throws Exception;
	
	public List<MyMap> getApproveSelect() throws Exception;
	
}
