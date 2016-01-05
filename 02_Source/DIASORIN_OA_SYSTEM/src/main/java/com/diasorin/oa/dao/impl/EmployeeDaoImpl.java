package com.diasorin.oa.dao.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.diasorin.oa.common.QueryResult;
import com.diasorin.oa.common.TypeConvertCommon;
import com.diasorin.oa.dao.EmployeeDao;
import com.diasorin.oa.dto.SeEmPeLsBean;
import com.diasorin.oa.model.EmployeeInfo;
import com.diasorin.oa.model.EmployeeLogin;

@Component
public class EmployeeDaoImpl extends BaseDaoImpl implements EmployeeDao {

	@Override
	public EmployeeInfo getEmployeeInfo(String employeeNo) throws Exception {
		try {
			String sql = " ";
			List<String> param = new ArrayList<String>();
			
			sql += " and o.employeeNo = ? ";
			param.add(employeeNo);
			
			QueryResult<EmployeeInfo> qs = getScrollData(EmployeeInfo.class, sql, param.toArray());
			if (qs.getResultlist().size() > 0) {
				return qs.getResultlist().get(0);
			}
			return null;
		} catch(Exception e) {
			throw e;
		}
	}

	@Override
	public QueryResult<EmployeeInfo> getEmployeeInfoList(int start, int end, String orderBy, SeEmPeLsBean seEmPeLsBean) throws Exception {
		try {
			String sql = " ";
			List<String> param = new ArrayList<String>();
			sql += " and o.deleteFlg = '0' ";	
			// 名字检索
			if (StringUtils.hasText(seEmPeLsBean.getName())){
				sql += " and (o.employeeNameCn like ? or o.employeeNameEn like ? ) ";
				param.add("%" + seEmPeLsBean.getName() + "%");
				param.add("%" + seEmPeLsBean.getName() + "%");
			}
			// 部门检索
			if (StringUtils.hasText(seEmPeLsBean.getDeptCode())){
				sql += " and o.deptCode = ? ";
				param.add(seEmPeLsBean.getDeptCode());
			}
			
			// 角色检索
			if (StringUtils.hasText(seEmPeLsBean.getRoleCode())){
				sql += " and o.roleCode = ? ";
				param.add(seEmPeLsBean.getRoleCode());
			}
			
			LinkedHashMap<String, String> orderByMap = new LinkedHashMap<String, String>(); 
			orderByMap.put(orderBy, "");
			QueryResult<EmployeeInfo> qs = getScrollData(EmployeeInfo.class, start, end, sql, param.toArray(), orderByMap);
			return qs;
		} catch(Exception e) {
			throw e;
		}
	}

	@Override
	public EmployeeLogin getEmployeeByNo(String employeeNo) throws Exception {
		try {
			String sql = " ";
			List<String> param = new ArrayList<String>();
			
			sql += " and o.employeeNo = ? ";
			param.add(employeeNo);
			
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
	public List<EmployeeInfo> employeeListQueryByCostCenter(String costCenter)
			throws Exception {
		try {
			String sql = " ";
			List<String> param = new ArrayList<String>();
			sql += " and o.deleteFlg = '0' ";	
			// 部门检索
			if (StringUtils.hasText(costCenter)){
				String[] arr = costCenter.split(",");
				sql += " and " + TypeConvertCommon.createWhereIn("o.deptCode = ?" , arr.length);
				for (String str : arr){
					param.add((str));
				}
			}

			QueryResult<EmployeeInfo> qs = getScrollData(EmployeeInfo.class, sql, param.toArray());
			return qs.getResultlist();
		} catch(Exception e) {
			throw e;
		}
	}

}
