package com.diasorin.oa.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.diasorin.oa.common.CodeCommon;
import com.diasorin.oa.common.ErrCommon;
import com.diasorin.oa.common.QueryResult;
import com.diasorin.oa.dao.BaseDao;
import com.diasorin.oa.dao.SystemManageDao;
import com.diasorin.oa.dto.SeSmPaMaEiBean;
import com.diasorin.oa.model.SysCostCenter;
import com.diasorin.oa.model.SysCostCenterHis;
import com.diasorin.oa.model.SysEmployeeLevel;
import com.diasorin.oa.model.SysEmployeeLevelHis;
import com.diasorin.oa.model.SysExpenses;
import com.diasorin.oa.model.SysExpensesHis;
import com.diasorin.oa.model.SysTravelLocal;
import com.diasorin.oa.service.SystemManagementService;

@Service("systemManagementService")
public class SystemManagementServiceImpl extends BaseServiceImpl implements
		SystemManagementService {

	@Resource 
	BaseDao baseDaoImpl;
	
	@Resource 
	SystemManageDao systemManageDaoImpl;
	
	@Override
	public QueryResult<SysTravelLocal> travelLocalListQuery(int first, int count)
			throws Exception {
		try {			
			QueryResult<SysTravelLocal> qs = baseDaoImpl.getScrollData(
					SysTravelLocal.class, first, count);
			return qs;
		} catch(Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return null;
		}
	}

	@Override
	public boolean travelLocalAdd(SysTravelLocal sysTravelLocal)
			throws Exception {
		try {
			baseDaoImpl.save(sysTravelLocal);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return false;
		}
	}

	@Override
	public boolean travelLocalUpdate(SysTravelLocal sysTravelLocal)
			throws Exception {
		try {
			baseDaoImpl.update(sysTravelLocal);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return false;
		}
	}

	@Override
	public boolean travelLocalDelete(Long no) throws Exception {
		try {
			baseDaoImpl.delete(SysTravelLocal.class, no);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return false;
		}
	}

	@Override
	public SysTravelLocal getTravelLocalInfo(String travelCode)
			throws Exception {
		try {
			return (SysTravelLocal) baseDaoImpl.getByIndex(SysTravelLocal.class, "travelCode", travelCode);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return null;
		}
	}

	@Override
	public QueryResult<SysEmployeeLevel> employeeLevelListQuery(int first,
			int count) throws Exception {
		try {			
			QueryResult<SysEmployeeLevel> qs = baseDaoImpl.getScrollData(
					SysEmployeeLevel.class, first, count);
			return qs;
		} catch(Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return null;
		}
	}

	@Override
	public boolean employeeLevelAdd(SysEmployeeLevel sysEmployeeLevel, String userId)
			throws Exception {
		try {
			baseDaoImpl.save(sysEmployeeLevel);
			SysEmployeeLevelHis his = new SysEmployeeLevelHis();
			his.setLevelCode(sysEmployeeLevel.getLevelCode());
			his.setLevelName(sysEmployeeLevel.getLevelName());
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
	public boolean employeeLevelUpdate(SysEmployeeLevel sysEmployeeLevel, String userId)
			throws Exception {
		try {
			baseDaoImpl.update(sysEmployeeLevel);
			SysEmployeeLevelHis his = new SysEmployeeLevelHis();
			his.setLevelCode(sysEmployeeLevel.getLevelCode());
			his.setLevelName(sysEmployeeLevel.getLevelName());
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
	public boolean employeeLevelDelete(Long no, String userId) throws Exception {
		try {
			SysEmployeeLevel sysEmployeeLevel = baseDaoImpl.find(SysEmployeeLevel.class, no);
			baseDaoImpl.delete(SysEmployeeLevel.class, no);
			SysEmployeeLevelHis his = new SysEmployeeLevelHis();
			his.setLevelCode(sysEmployeeLevel.getLevelCode());
			his.setLevelName(sysEmployeeLevel.getLevelName());
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
	public SysEmployeeLevel getEmployeeLevelInfo(String levelCode)
			throws Exception {
		try {
			return (SysEmployeeLevel) baseDaoImpl.getByIndex(SysEmployeeLevel.class, "levelCode", levelCode);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return null;
		}
	}

	@Override
	public QueryResult<SysExpenses> expensesItemListQuery(int first, int count)
			throws Exception {
		try {			
			QueryResult<SysExpenses> qs = baseDaoImpl.getScrollData(
					SysExpenses.class, first, count);
			return qs;
		} catch(Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return null;
		}
	}

	@Override
	public boolean expensesItemAdd(SysExpenses sysExpenses, String userId) throws Exception {
		try {
			baseDaoImpl.save(sysExpenses);
			SysExpensesHis his = new SysExpensesHis();
			his.setExpenseCode(sysExpenses.getExpenseCode());
			his.setExpenseName(sysExpenses.getExpenseName());
			his.setFatherExpenseCode(sysExpenses.getFatherExpenseCode());
			his.setFinanceNo(sysExpenses.getFinanceNo());
			his.setShowOrderNo(sysExpenses.getShowOrderNo());
			his.setTimeMethod(sysExpenses.getTimeMethod());
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
	public boolean expensesItemUpdate(SysExpenses sysExpenses, String userId) throws Exception {
		try {
			baseDaoImpl.update(sysExpenses);
			SysExpensesHis his = new SysExpensesHis();
			his.setExpenseCode(sysExpenses.getExpenseCode());
			his.setExpenseName(sysExpenses.getExpenseName());
			his.setFatherExpenseCode(sysExpenses.getFatherExpenseCode());
			his.setFinanceNo(sysExpenses.getFinanceNo());
			his.setShowOrderNo(sysExpenses.getShowOrderNo());
			his.setTimeMethod(sysExpenses.getTimeMethod());
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
	public boolean expensesItemDelete(Long no, String userId) throws Exception {
		try {
			SysExpenses sysExpenses = baseDaoImpl.find(SysExpenses.class, no);
			baseDaoImpl.delete(SysExpenses.class, no);
			SysExpensesHis his = new SysExpensesHis();
			his.setExpenseCode(sysExpenses.getExpenseCode());
			his.setExpenseName(sysExpenses.getExpenseName());
			his.setFatherExpenseCode(sysExpenses.getFatherExpenseCode());
			his.setFinanceNo(sysExpenses.getFinanceNo());
			his.setShowOrderNo(sysExpenses.getShowOrderNo());
			his.setTimeMethod(sysExpenses.getTimeMethod());
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
	public SysExpenses getExpensesItemInfo(String expenseCode)
			throws Exception {
		try {
			return (SysExpenses) baseDaoImpl.getByIndex(SysExpenses.class, "expenseCode", expenseCode);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return null;
		}
	}

	@Override
	public QueryResult<SysCostCenter> costCenterListQuery(int first, int count)
			throws Exception {
		try {			
			QueryResult<SysCostCenter> qs = baseDaoImpl.getScrollData(
					SysCostCenter.class, first, count);
			return qs;
		} catch(Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return null;
		}
	}

	@Override
	public boolean costCenterAdd(SysCostCenter sysCostCenter, String userId) throws Exception {
		try {
			baseDaoImpl.save(sysCostCenter);
			SysCostCenterHis his = new SysCostCenterHis();
			his.setCostCenterCode(sysCostCenter.getCostCenterCode());
			his.setCostCenterName(sysCostCenter.getCostCenterName());
			his.setCostCenterDisplayName(sysCostCenter.getCostCenterDisplayName());
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
	public boolean costCenterUpdate(SysCostCenter sysCostCenter, String userId)
			throws Exception {
		try {
			baseDaoImpl.update(sysCostCenter);
			SysCostCenterHis his = new SysCostCenterHis();
			his.setCostCenterCode(sysCostCenter.getCostCenterCode());
			his.setCostCenterName(sysCostCenter.getCostCenterName());
			his.setCostCenterDisplayName(sysCostCenter.getCostCenterDisplayName());
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
	public boolean costCenterDelete(Long no, String userId) throws Exception {
		try {
			SysCostCenter sysCostCenter = baseDaoImpl.find(SysCostCenter.class, no);
			baseDaoImpl.delete(SysCostCenter.class, no);
			SysCostCenterHis his = new SysCostCenterHis();
			his.setCostCenterCode(sysCostCenter.getCostCenterCode());
			his.setCostCenterName(sysCostCenter.getCostCenterName());
			his.setCostCenterDisplayName(sysCostCenter.getCostCenterDisplayName());
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
	public SysCostCenter getCostCenterInfo(String costCenterCode)
			throws Exception {
		try {
			return (SysCostCenter) baseDaoImpl.getByIndex(SysCostCenter.class, "costCenterCode", costCenterCode);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return null;
		}
	}

	@Override
	public String getMaxEmployeeLevel() throws Exception {
		try {
			String maxLevelCode = systemManageDaoImpl.getMaxEmployeeLevel();
			if (maxLevelCode == null || "".equals(maxLevelCode)) {
				return "L001";
			} else {
				return "L" + StringUtils.leftPad(String.valueOf(Integer.valueOf(maxLevelCode.substring(1)) + 1), 3, "0");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return null;
		}
	}

	@Override
	public String getMaxExpensesItem() throws Exception {
		try {
			String maxExpensesCode = systemManageDaoImpl.getMaxExpensesItem();
			if (maxExpensesCode == null || "".equals(maxExpensesCode)) {
				return "S001";
			} else {
				return "S" + StringUtils.leftPad(String.valueOf(Integer.valueOf(maxExpensesCode.substring(1)) + 1), 3, "0");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return null;
		}
	}

	@Override
	public String getMaxCostCenter() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SeSmPaMaEiBean> getExpensesItemList() throws Exception {
		try {			
			return systemManageDaoImpl.getExpensesItemList();
		} catch(Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return null;
		}
	}

	@Override
	public List<SysExpenses> getFirstFarItem() throws Exception {
		try {			
			return systemManageDaoImpl.getFirstFarItem();
		} catch(Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return null;
		}
	}

	@Override
	public List<SysExpenses> getSubItem(String fartherItem) throws Exception {
		try {			
			return systemManageDaoImpl.getSubItem(fartherItem);
		} catch(Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return null;
		}
	}

	@Override
	public SysExpenses getFartherExpenses(String expenseCode) throws Exception {
		try {			
			return systemManageDaoImpl.getFartherExpenses(expenseCode);
		} catch(Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return null;
		}
	}

	@Override
	public List<SysExpenses> getNotFartherItem() throws Exception {
		try {			
			return systemManageDaoImpl.getNotFartherItem();
		} catch(Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return null;
		}
	}


}
