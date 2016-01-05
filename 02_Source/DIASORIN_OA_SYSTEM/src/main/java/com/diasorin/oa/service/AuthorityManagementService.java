package com.diasorin.oa.service;

import com.diasorin.oa.common.QueryResult;
import com.diasorin.oa.dto.SeSyAmAUBean;
import com.diasorin.oa.dto.SeSyAmAlListBean;
import com.diasorin.oa.model.SysEmployeeRole;

/**
 * 权限管理
 * @author liuan
 *
 */
public interface AuthorityManagementService {

	/**
	 * 角色一览查询
	 * @return
	 * @throws Exception
	 */
	public QueryResult<SysEmployeeRole> roleListQuery(String roleName, int first, int count) throws Exception;
	
	/**
	 * 角色新增
	 * @return
	 * @throws Exception
	 */
	public boolean roleAdd(SysEmployeeRole sysEmployeeRole, String userId) throws Exception;
	
	/**
	 * 角色更新
	 * @return
	 * @throws Exception
	 */
	public boolean roleUpdate(SysEmployeeRole sysEmployeeRole, String userId) throws Exception;
	
	/**
	 * 角色删除
	 * @return
	 * @throws Exception
	 */
	public boolean roleDelete(Long no, String userId) throws Exception;
	
	/**
	 * 获取角色信息
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
	 * 保存权限信息
	 * @param seSyAmAUBean 权限信息
	 */
	public void authorityInfoCreate(SeSyAmAUBean seSyAmAUBean);
	
	/**
	 * 权限一览的取得
	 * @param start
	 * @param result
	 * @return
	 */
	public QueryResult<SeSyAmAlListBean> getAuthorityListGet(int start, int result);
	
	/**
	 * 从模块信息表查询权限信息
	 * @param dbDivision
	 * @return
	 */
	public SeSyAmAUBean authorityInfoGetFromModuleInfo();
	
	/**
	 * 权限信息取得
	 * @param dbDivision
	 * @param deptId 部门ID
	 * @param roleId 角色ID
	 * @return
	 */
	public SeSyAmAUBean authorityInfoGet(String deptId, String roleId);
	
}
