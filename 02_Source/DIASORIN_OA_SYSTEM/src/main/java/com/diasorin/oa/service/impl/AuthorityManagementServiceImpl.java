package com.diasorin.oa.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.diasorin.oa.common.CodeCommon;
import com.diasorin.oa.common.ErrCommon;
import com.diasorin.oa.common.QueryResult;
import com.diasorin.oa.dao.AuthorityManagementDao;
import com.diasorin.oa.dao.BaseDao;
import com.diasorin.oa.dto.SeSyAmAUBean;
import com.diasorin.oa.dto.SeSyAmAUListBean;
import com.diasorin.oa.dto.SeSyAmAlListBean;
import com.diasorin.oa.model.SysEmployeeAuthority;
import com.diasorin.oa.model.SysEmployeeRole;
import com.diasorin.oa.model.SysEmployeeRoleHis;
import com.diasorin.oa.model.SysModuleInfo;
import com.diasorin.oa.service.AuthorityManagementService;
import com.diasorin.oa.service.SystemManagementService;

@Service("authorityManagementService")
public class AuthorityManagementServiceImpl extends BaseServiceImpl implements
		AuthorityManagementService {

	@Resource 
	AuthorityManagementDao authorityManagementDaoImpl;
	
	@Resource 
	SystemManagementService systemManagementService;
	
	@Resource 
	BaseDao baseDaoImpl;
	
	@Override
	public QueryResult<SysEmployeeRole> roleListQuery(String roleName, int first, int count) throws Exception {
		try {
			return authorityManagementDaoImpl.roleListQuery(roleName, first, count);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return null;
		}
	}

	@Override
	public boolean roleAdd(SysEmployeeRole sysEmployeeRole, String userId) throws Exception {
		try {
			baseDaoImpl.save(sysEmployeeRole);
			SysEmployeeRoleHis his = new SysEmployeeRoleHis();
			his.setRoleCode(sysEmployeeRole.getRoleCode());
			his.setRoleName(sysEmployeeRole.getRoleName());
			his.setOperateFlg(CodeCommon.OPERATEFLG_SAVE);
			his.setOperater(userId);
			his.setOperateTimestamp(getCurrTimeStamp());
			baseDaoImpl.save(his);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return false;
		}
	}

	@Override
	public boolean roleUpdate(SysEmployeeRole sysEmployeeRole, String userId) throws Exception {
		try {
			baseDaoImpl.update(sysEmployeeRole);
			SysEmployeeRoleHis his = new SysEmployeeRoleHis();
			his.setRoleCode(sysEmployeeRole.getRoleCode());
			his.setRoleName(sysEmployeeRole.getRoleName());
			his.setOperateFlg(CodeCommon.OPERATEFLG_UPDATE);
			his.setOperater(userId);
			his.setOperateTimestamp(getCurrTimeStamp());
			baseDaoImpl.save(his);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return false;
		}
	}

	@Override
	public SysEmployeeRole getRoleInfo(String roleId) throws Exception {
		try {
			return authorityManagementDaoImpl.getRoleInfo(roleId);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return null;
		}
	}

	@Override
	public boolean roleDelete(Long no, String userId) throws Exception {
		try {
			SysEmployeeRole sysEmployeeRole = baseDaoImpl.find(SysEmployeeRole.class, no);
			baseDaoImpl.delete(SysEmployeeRole.class, no);
			SysEmployeeRoleHis his = new SysEmployeeRoleHis();
			his.setRoleCode(sysEmployeeRole.getRoleCode());
			his.setRoleName(sysEmployeeRole.getRoleName());
			his.setOperateFlg(CodeCommon.OPERATEFLG_DELETE);
			his.setOperater(userId);
			his.setOperateTimestamp(getCurrTimeStamp());
			baseDaoImpl.save(his);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return false;
		}
	}

	@Override
	public String getMaxRoleCode() throws Exception {
		try {
			String maxRoleCode = authorityManagementDaoImpl.getMaxRoleCode();
			if (maxRoleCode == null || "".equals(maxRoleCode)) {
				return "R001";
			} else {
				return "R" + StringUtils.leftPad(String.valueOf(Integer.valueOf(maxRoleCode.substring(1)) + 1), 3, "0");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return null;
		}
	}

	@Override
	public QueryResult<SeSyAmAlListBean> getAuthorityListGet(int start,
			int result) {
		try {
			QueryResult<SeSyAmAlListBean> queryResult = new QueryResult<SeSyAmAlListBean>();
			
			List<SeSyAmAlListBean> beans = new ArrayList<SeSyAmAlListBean>(); 
			// 取得权限列表
			List<SysEmployeeAuthority> authorityList = authorityManagementDaoImpl.getAuthorityList(start, result);
			if (authorityList != null && authorityList.size() > 0) {
				for (int i = 0; i < authorityList.size(); i++) {
					SeSyAmAlListBean bean = new SeSyAmAlListBean();
					
					bean.setDeptId(authorityList.get(i).getDeptCode());
					// 部门名称
					bean.setDeptName(systemManagementService.getCostCenterInfo(authorityList.get(i).getDeptCode()).getCostCenterName());
					
					bean.setRoleId(authorityList.get(i).getRoleCode());
					// 角色名称
					bean.setRoleName(this.getRoleInfo(authorityList.get(i).getRoleCode()).getRoleName());
					beans.add(bean);
				}
			}
			queryResult.setResultlist(beans);
			// 取得权限所有数
			queryResult.setTotalrecord(authorityManagementDaoImpl.getAuthorityListCount());
			return queryResult;
		} catch (Exception e) {
			e.printStackTrace();
			ErrCommon.errOut(e);
			logger.error(e.getMessage());
			return null;
		}
	}

	@Override
	public SeSyAmAUBean authorityInfoGetFromModuleInfo() {
		try {
			SeSyAmAUBean seSyAmAUBean = new SeSyAmAUBean();
			List<SeSyAmAUListBean> beans = new ArrayList<SeSyAmAUListBean>(); 
			// 取得权限列表
			QueryResult<SysModuleInfo> tSysModuleInfo = baseDaoImpl.getScrollData(SysModuleInfo.class);
			if (tSysModuleInfo.getTotalrecord() > 0) {
				for (int i = 0; i < tSysModuleInfo.getTotalrecord(); i++) {
					SeSyAmAUListBean bean = new SeSyAmAUListBean();
					
					bean.setControlId(tSysModuleInfo.getResultlist().get(i).getControlId());
					bean.setControlName(tSysModuleInfo.getResultlist().get(i).getControlName());
					beans.add(bean);
				}
			}
			seSyAmAUBean.setBeanList(beans);
			return seSyAmAUBean;
		} catch (Exception e) {
			e.printStackTrace();
			ErrCommon.errOut(e);
			logger.error(e.getMessage());
			return null;
		}
	}

	@Override
	public SeSyAmAUBean authorityInfoGet(String deptId, String roleId) {
		try {
			SeSyAmAUBean seSyAmAUBean = new SeSyAmAUBean();
			seSyAmAUBean.setDeptId(deptId);
			// 部门名称
			seSyAmAUBean.setDeptName(systemManagementService.getCostCenterInfo(deptId).getCostCenterName());
			
			seSyAmAUBean.setRoleId(roleId);
			// 角色名称
			seSyAmAUBean.setRoleName(this.getRoleInfo(roleId).getRoleName());
			
			List<SeSyAmAUListBean> beans = new ArrayList<SeSyAmAUListBean>(); 
//			String sqlString = " and o.deptCode= ? and o.roleCode= ? ";
//			List<String> param = new ArrayList<String>();
//			param.add(deptId);
//			param.add(roleId);
			// 取得权限列表
			List<SysEmployeeAuthority> tSysAuthority = authorityManagementDaoImpl.getAuthorityListByCondition(deptId, roleId);
			//QueryResult<SysEmployeeAuthority> tSysAuthority = baseDaoImpl.getScrollData(SysEmployeeAuthority.class,sqlString,param.toArray());
			if (tSysAuthority != null && tSysAuthority.size() > 0) {
				for (int i = 0; i < tSysAuthority.size(); i++) {
					SeSyAmAUListBean bean = new SeSyAmAUListBean();
					
					bean.setDeptId(tSysAuthority.get(i).getDeptCode());
					
					bean.setDeptName(seSyAmAUBean.getDeptName());
					
					bean.setRoleId(tSysAuthority.get(i).getRoleCode());
					
					bean.setRoleName(seSyAmAUBean.getRoleName());
					
					bean.setControlId(tSysAuthority.get(i).getControlId());
					SysModuleInfo sysModuleInfo = (SysModuleInfo) baseDaoImpl.getByIndex(SysModuleInfo.class, "controlId", bean.getControlId());
					bean.setControlName(sysModuleInfo == null ? "" : sysModuleInfo.getControlName());
					
					bean.setAuthority(StringUtils.isEmpty(tSysAuthority.get(i).getAuthority()) ? "0" : tSysAuthority.get(i).getAuthority());
					beans.add(bean);
				}
				seSyAmAUBean.setBeanList(beans);
			}
			return seSyAmAUBean;
		} catch (Exception e) {
			e.printStackTrace();
			ErrCommon.errOut(e);
			logger.error(e.getMessage());
			return null;
		}
	}

	@Override
	public void authorityInfoCreate(SeSyAmAUBean seSyAmAUBean) {
		try {
			if (seSyAmAUBean.getBeanList().size() >0) {
				for (int i = 0; i < seSyAmAUBean.getBeanList().size(); i++) {
					SeSyAmAUListBean bean = seSyAmAUBean.getBeanList().get(i);
					String sqlString = " and o.deptCode= ? and o.roleCode= ? and controlId = ? ";
					List<String> param = new ArrayList<String>();
					param.add(bean.getDeptId());
					param.add(bean.getRoleId());
					param.add(bean.getControlId());
					QueryResult<SysEmployeeAuthority> tSysAuthority = baseDaoImpl.getScrollData(SysEmployeeAuthority.class, sqlString, param.toArray());
					if (tSysAuthority !=null && tSysAuthority.getResultlist().size() != 0) {
						tSysAuthority.getResultlist().get(0).setRoleCode(bean.getRoleId());
						tSysAuthority.getResultlist().get(0).setDeptCode(bean.getDeptId());
						tSysAuthority.getResultlist().get(0).setControlId(bean.getControlId());
						if (bean.getAuthority() == null) {
							tSysAuthority.getResultlist().get(0).setAuthority("0");
						} else {
							tSysAuthority.getResultlist().get(0).setAuthority("1");
						}
						// 修改
						baseDaoImpl.update(tSysAuthority.getResultlist().get(0));
					}else {
						SysEmployeeAuthority newAuthority = new SysEmployeeAuthority();
						newAuthority.setRoleCode(bean.getRoleId());
						newAuthority.setDeptCode(bean.getDeptId());
						newAuthority.setControlId(bean.getControlId());
						if (bean.getAuthority() == null) {
							newAuthority.setAuthority("0");
						} else {
							newAuthority.setAuthority("1");
						}
						// 新增
						baseDaoImpl.save(newAuthority);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
		}
	}

}
