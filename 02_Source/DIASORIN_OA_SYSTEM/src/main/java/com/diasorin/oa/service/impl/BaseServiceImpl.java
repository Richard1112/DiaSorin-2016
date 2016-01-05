package com.diasorin.oa.service.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.diasorin.oa.common.CodeCommon;
import com.diasorin.oa.common.MyMap;
import com.diasorin.oa.dao.BaseDao;
import com.diasorin.oa.model.SysCode;
import com.diasorin.oa.model.SysExpenses;
import com.diasorin.oa.service.BaseService;
import com.diasorin.oa.service.SystemManagementService;

@Service("baseService")
public class BaseServiceImpl implements BaseService{
	
	@Resource
	BaseDao baseDaoImpl;
	protected static Log logger = null;
	
	protected static SysExpenses sysExpense = null;
	
	@Resource
	SystemManagementService systemManagementService;
	
	protected SysExpenses getS025Rate() throws Exception {
		if(sysExpense == null) {
			sysExpense = systemManagementService.getExpensesItemInfo(CodeCommon.PRIVATE_CAR_FOR_BUSINESS);
		} 
		return sysExpense;
	}
	
	/**
	 * 取得当前时间
	 * 
	 */
	public Timestamp getCurrTimeStamp() {
		return new Timestamp(System.currentTimeMillis());
	}
	
	/**
	 * 取得当前YYYYMMDD
	 * 
	 */
	public String getCurrDate() {
		return new SimpleDateFormat("yyyyMMdd").format(new java.util.Date());
	}

	/**
	 * @param params
	 */
	public BaseServiceImpl() {
		logger = LogFactory.getLog(BaseServiceImpl.class);
	}

	@Override
	public List<MyMap> getSelect(String codeId) throws Exception {
		if (!StringUtils.hasLength(codeId)) {
			return null;
		}
		List<SysCode> list = baseDaoImpl.getSelect(codeId);

		List<MyMap> select = new ArrayList<MyMap>();
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				MyMap my = new MyMap();
				my.setKey(list.get(i).getCodeDetailId());
				my.setValue(list.get(i).getCodeDetailName());
				select.add(my);
			}
		}
		return select;
	}

	@Override
	public String getSelect(String codeId, String codeDetailId)
			throws Exception {
		if (!StringUtils.hasLength(codeId)) {
			return null;
		}
		if (!StringUtils.hasLength(codeDetailId)) {
			return null;
		}
		return baseDaoImpl.getSelect(codeId, codeDetailId);
	}

	@Override
	public List<MyMap> getFinanceSelect() throws Exception {
		List<SysCode> list = baseDaoImpl.getSelect(CodeCommon.COM003);

		List<MyMap> select = new ArrayList<MyMap>();
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				MyMap my = new MyMap();
				if (list.get(i).getCodeDetailId().equals(CodeCommon.CLAIM_APPROVED) 
						|| list.get(i).getCodeDetailId().equals(CodeCommon.CLAIM_FINISH)) {
					my.setKey(list.get(i).getCodeDetailId());
					my.setValue(list.get(i).getCodeDetailName());
					select.add(my);
				}
				
			}
		}
		return select;
	}

	@Override
	public List<MyMap> getApproveSelect() throws Exception {
		List<SysCode> list = baseDaoImpl.getSelect(CodeCommon.COM003);

		List<MyMap> select = new ArrayList<MyMap>();
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				MyMap my = new MyMap();
				if (list.get(i).getCodeDetailId().equals(CodeCommon.CLAIM_APPLICATION) 
						|| list.get(i).getCodeDetailId().equals(CodeCommon.CLAIM_PENDING)
						|| list.get(i).getCodeDetailId().equals(CodeCommon.CLAIM_REJECT)
						|| list.get(i).getCodeDetailId().equals(CodeCommon.CLAIM_APPROVED)
						|| list.get(i).getCodeDetailId().equals(CodeCommon.CLAIM_FINISH)) {
					my.setKey(list.get(i).getCodeDetailId());
					my.setValue(list.get(i).getCodeDetailName());
					select.add(my);
				}
				
			}
		}
		return select;
	}
	
	

}
