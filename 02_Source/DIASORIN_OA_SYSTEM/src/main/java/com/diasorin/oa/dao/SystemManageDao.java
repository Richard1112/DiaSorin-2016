package com.diasorin.oa.dao;

import java.util.List;

import com.diasorin.oa.dto.SeSmPaMaEiBean;
import com.diasorin.oa.model.SysExpenses;


public interface SystemManageDao {

	public String getMaxEmployeeLevel() throws Exception;
	public String getMaxExpensesItem() throws Exception;
	public String getMaxCostCenter() throws Exception;
	
	public List<SeSmPaMaEiBean> getExpensesItemList() throws Exception;
	
	public List<SysExpenses> getFirstFarItem() throws Exception;
	public List<SysExpenses> getSubItem(String fartherItem) throws Exception;
	public SysExpenses getFartherExpenses(String expenseCode) throws Exception;
	
	public List<SysExpenses> getNotFartherItem() throws Exception;
}
