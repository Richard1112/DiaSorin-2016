package com.diasorin.oa.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Component;

import com.diasorin.oa.dao.MenuDao;
import com.diasorin.oa.model.SysModuleInfo;

@Component
public class MenuDaoImpl extends BaseDaoImpl implements MenuDao {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<SysModuleInfo> getModuleInfo(String userId) throws Exception {
		try {

			String sqlString = "SELECT C.* "
					+ "FROM t_employee_info A "
					+ "INNER JOIN t_sys_employee_authority B "
					+ "ON A.deptCode = B.deptCode AND A.roleCode = B.roleCode "
					+ "INNER JOIN t_sys_module_info C "
					+ "ON C.controlId = B.controlId "
					+ "WHERE A.employeeNo = ? ";
			List<String> paramsList = new ArrayList<String>();
			paramsList.add(userId);
			Query query = em.createNativeQuery(sqlString);
			setQueryParams(query, paramsList.toArray());
			List list = query.getResultList();
			String[] fields = { "controlId", "fatherControlId",
					"controlDivision", "controlName", "methodId" };
			List resultList = bindDataToDTO(list, new SysModuleInfo(), fields);
			return resultList;
		} catch (Exception e) {
			throw e;
		}
	}

}
