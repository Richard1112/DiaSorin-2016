package com.diasorin.oa.dao;

import java.util.List;

import com.diasorin.oa.common.QueryResult;
import com.diasorin.oa.model.SysEmployeeAuthority;
import com.diasorin.oa.model.SysEmployeeRole;

/**
 * 角色管理用
 * @author liuan
 *
 */
public interface AuthorityManagementDao {

	/**
	 * 角色一览取得
	 * @param roleName
	 * @param first
	 * @param count
	 * @return
	 * @throws Exception
	 */
	public QueryResult<SysEmployeeRole> roleListQuery(String roleName, int first, int count) throws Exception;
	
	/**
	 * 角色
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	public SysEmployeeRole getRoleInfo(String roleId) throws Exception;
	
	/**
	 * 取得最大角色CODE
	 * @return
	 * @throws Exception
	 */
	public String getMaxRoleCode() throws Exception;
	
	/**
	 * 取得权限一览
	 * @param start
	 * @param result
	 * @return
	 */
	public List<SysEmployeeAuthority> getAuthorityList(int start, int result) throws Exception;
	
	/**
	 * 取得权限一览总数
	 * @param start
	 * @param result
	 * @return
	 */
	public Long getAuthorityListCount() throws Exception;
	
	/**
	 * 根据部门ID和角色ID取得权限一览
	 * @param deptId
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	public List<SysEmployeeAuthority> getAuthorityListByCondition(String deptId, String roleId) throws Exception;
	
}
