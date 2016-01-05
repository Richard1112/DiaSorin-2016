package com.diasorin.oa.service;

import java.util.List;

import com.diasorin.oa.common.QueryResult;
import com.diasorin.oa.dto.SeSmPaMaEiBean;
import com.diasorin.oa.model.SysCostCenter;
import com.diasorin.oa.model.SysEmployeeLevel;
import com.diasorin.oa.model.SysExpenses;
import com.diasorin.oa.model.SysTravelLocal;

/**
 * 系统管理
 * @author liuan
 *
 */
public interface SystemManagementService {

	/**
	 * 出差地列表查询
	 * @return
	 * @throws Exception
	 */
	public QueryResult<SysTravelLocal> travelLocalListQuery(int first, int count) throws Exception;
	
	// 出差地增删改，获取信息
	public boolean travelLocalAdd(SysTravelLocal sysTravelLocal) throws Exception;
	public boolean travelLocalUpdate(SysTravelLocal sysTravelLocal) throws Exception;
	public boolean travelLocalDelete(Long no) throws Exception;
	public SysTravelLocal getTravelLocalInfo(String travelCode) throws Exception;
	
	/**
	 * 人员级别列表查询
	 * @return
	 * @throws Exception
	 */
	public QueryResult<SysEmployeeLevel> employeeLevelListQuery(int first, int count) throws Exception;
	//  人员级别增删改，获取信息
	public boolean employeeLevelAdd(SysEmployeeLevel sysEmployeeLevel, String userId) throws Exception;
	public boolean employeeLevelUpdate(SysEmployeeLevel sysEmployeeLevel, String userId) throws Exception;
	public boolean employeeLevelDelete(Long no, String userId) throws Exception;
	public SysEmployeeLevel getEmployeeLevelInfo(String levelCode) throws Exception;

	
	/**
	 * 报销项目列表查询
	 * @return
	 * @throws Exception
	 */
	public QueryResult<SysExpenses> expensesItemListQuery(int first, int count) throws Exception;
	
	public List<SeSmPaMaEiBean> getExpensesItemList() throws Exception;
	//  报销项目增删改，获取信息
	public boolean expensesItemAdd(SysExpenses sysExpenses, String userId) throws Exception;
	public boolean expensesItemUpdate(SysExpenses sysExpenses, String userId) throws Exception;
	public boolean expensesItemDelete(Long no, String userId) throws Exception;
	public SysExpenses getExpensesItemInfo(String expenseCode) throws Exception;
	
	public List<SysExpenses> getFirstFarItem() throws Exception;
	public List<SysExpenses> getSubItem(String fartherItem) throws Exception;
	public SysExpenses getFartherExpenses(String expenseCode) throws Exception;
	
	public List<SysExpenses> getNotFartherItem() throws Exception;
	
	/**
	 * 成本中心列表查询
	 * @return
	 * @throws Exception
	 */
	public QueryResult<SysCostCenter> costCenterListQuery(int first, int count) throws Exception;
	//  成本中心增删改，获取信息
	public boolean costCenterAdd(SysCostCenter sysCostCenter, String userId) throws Exception;
	public boolean costCenterUpdate(SysCostCenter sysCostCenter, String userId) throws Exception;
	public boolean costCenterDelete(Long no, String userId) throws Exception;
	public SysCostCenter getCostCenterInfo(String costCenterCode) throws Exception;
	
	// 取得各个表的最大值
	public String getMaxEmployeeLevel() throws Exception;
	public String getMaxExpensesItem() throws Exception;
	public String getMaxCostCenter() throws Exception;
}
