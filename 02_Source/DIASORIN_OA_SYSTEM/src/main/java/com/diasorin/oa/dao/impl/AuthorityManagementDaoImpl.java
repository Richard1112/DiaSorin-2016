package com.diasorin.oa.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.diasorin.oa.common.QueryResult;
import com.diasorin.oa.dao.AuthorityManagementDao;
import com.diasorin.oa.model.SysEmployeeAuthority;
import com.diasorin.oa.model.SysEmployeeRole;

@Component
public class AuthorityManagementDaoImpl extends BaseDaoImpl implements
		AuthorityManagementDao {

	@Override
	public QueryResult<SysEmployeeRole> roleListQuery(String roleName, int first, int count)
			throws Exception {
		try {
			String sql = " ";
			List<String> param = new ArrayList<String>();
			
			if (StringUtils.hasText(roleName)) {
				sql += " and o.roleName like  ? ";
				param.add("%"+roleName+"%");
			}
			
			QueryResult<SysEmployeeRole> qs = getScrollData(
					SysEmployeeRole.class, first, count, sql, param.toArray());
			return qs;
		} catch(Exception e) {
			throw e;
		}
	}

	@Override
	public SysEmployeeRole getRoleInfo(String roleId) throws Exception {
		try {
			String sql = " ";
			List<String> param = new ArrayList<String>();
			
			sql += " and o.roleCode = ? ";
			param.add(roleId);
			
			QueryResult<SysEmployeeRole> qs = getScrollData(SysEmployeeRole.class, sql, param.toArray());
			if (qs.getResultlist().size() > 0) {
				return qs.getResultlist().get(0);
			}
			return null;
		} catch(Exception e) {
			throw e;
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public String getMaxRoleCode() throws Exception {
		try {

			String sqlString = "select max(roleCode) as maxCode from t_sys_employee_role ";
					
			Query query = em.createNativeQuery(sqlString);

			List list = query.getResultList();
			if (list == null || list.size() == 0) {
				return null;
			} else {
				return list.get(0).toString();
			}
		} catch (Exception e) {
			throw e;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<SysEmployeeAuthority> getAuthorityList(int start, int result) throws Exception {
		try {

			String sqlString = "select " + 
					"deptCode, " + 
					"roleCode " + 
					"from t_sys_employee_authority " + 
					"group by deptCode,roleCode ";

			Query query = em.createNativeQuery(sqlString);
			query.setFirstResult(start).setMaxResults(result);
			List list = query.getResultList();
			String[] fields = { "deptCode", "roleCode" };
			List resultList = bindDataToDTO(list, new SysEmployeeAuthority(), fields);
			return resultList;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Long getAuthorityListCount() throws Exception {
		try {

			String sqlString = "select count(*) from (select " + 
					"deptCode, " + 
					"roleCode " + 
					"from t_sys_employee_authority " + 
					"group by deptCode,roleCode) a ";

			Query query = em.createNativeQuery(sqlString);
			return Long.valueOf(query.getSingleResult().toString());
		} catch (Exception e) {
			throw e;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<SysEmployeeAuthority> getAuthorityListByCondition(
			String deptId, String roleId) throws Exception {
		try {
			List<String> param = new ArrayList<String>();
			String sqlString = "select " + 
					"? as deptCode, " + 
					"? as roleCode, " + 
					"b.controlId, " + 
					"IFNULL(a.authority, '') as authority " + 
					"from t_sys_module_info b " + 
					"left join t_sys_employee_authority a on a.controlId = b.controlId and a.deptCode = ? and a.roleCode = ?"
					+"";

			Query query = em.createNativeQuery(sqlString);
			param.add(deptId);
			param.add(roleId);
			param.add(deptId);
			param.add(roleId);
			setQueryParams(query, param.toArray());
			List list = query.getResultList();
			String[] fields = { "deptCode", "roleCode","controlId","authority" };
			List resultList = bindDataToDTO(list, new SysEmployeeAuthority(), fields);
			return resultList;
		} catch (Exception e) {
			throw e;
		}
	}

}
