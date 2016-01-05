package com.diasorin.oa.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Component;

import com.diasorin.oa.common.CodeCommon;
import com.diasorin.oa.common.QueryResult;
import com.diasorin.oa.dao.SystemManageDao;
import com.diasorin.oa.dto.SeSmPaMaEiBean;
import com.diasorin.oa.model.SysExpenses;

@Component
public class SystemManageDaoImpl extends BaseDaoImpl implements SystemManageDao {

	@SuppressWarnings("rawtypes")
	@Override
	public String getMaxEmployeeLevel() throws Exception {
		try {

			String sqlString = "select max(levelCode) as maxCode from t_sys_employee_level ";
					
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

	@SuppressWarnings("rawtypes")
	@Override
	public String getMaxExpensesItem() throws Exception {
		try {

			String sqlString = "select max(expenseCode) as maxCode from t_sys_expenses ";
					
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

	@SuppressWarnings("rawtypes")
	@Override
	public String getMaxCostCenter() throws Exception {
		try {

			String sqlString = "select max(costCenterCode) as maxCode from t_sys_cost_center ";
					
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
	public List<SeSmPaMaEiBean> getExpensesItemList() throws Exception {
		try {

			String sqlString = "select " + 
					"cast(a.no as char) as no, " + 
					"a.expenseCode, " + 
					"a.expenseName as expenseName, " + 
					"b.expenseName as fatherExpenseCode, " + 
					"a.financeNo as financeNo, " + 
					"cast(a.showOrderNo as char) as showOrderNo " + 
					"from t_sys_expenses  a " + 
					"left join t_sys_expenses b  " + 
					"on  a.fatherExpenseCode = b.expenseCode " + 
					"order by a.showOrderNo";
			Query query = em.createNativeQuery(sqlString);

			List list = query.getResultList();
			String[] fields = { "no", "expenseCode", "expenseName",
					"fatherExpenseCode", "financeNo", "showOrderNo"};
			List resultList = bindDataToDTO(list, new SeSmPaMaEiBean(), fields);
			return resultList;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<SysExpenses> getFirstFarItem() throws Exception {
		try {
			String sql = " ";
			List<String> param = new ArrayList<String>();
			
			sql += " and o.fatherExpenseCode = ? ";
			param.add(CodeCommon.EXPENSE_SUPERFARTHER);
			
			QueryResult<SysExpenses> qs = getScrollData(SysExpenses.class, sql, param.toArray());
			if (qs.getResultlist().size() > 0) {
				return qs.getResultlist();
			}
			return null;
		} catch(Exception e) {
			throw e;
		}
	}

	@Override
	public List<SysExpenses> getSubItem(String fartherItem) throws Exception {
		try {
			String sql = " ";
			List<String> param = new ArrayList<String>();
			
			sql += " and o.fatherExpenseCode = ? ";
			param.add(fartherItem);
			
			QueryResult<SysExpenses> qs = getScrollData(SysExpenses.class, sql, param.toArray());
			if (qs.getResultlist().size() > 0) {
				return qs.getResultlist();
			}
			return null;
		} catch(Exception e) {
			throw e;
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public SysExpenses getFartherExpenses(String expenseCode) throws Exception {
		try {
			List<String> param = new ArrayList<String>();
			String sqlString = "select " + 
					"a.expenseCode, " + 
					"a.expenseName, " + 
					"a.timeMethod, " +
					"a.fatherExpenseCode, " + 
					"a.financeNo, " + 
					"cast(a.showOrderNo as char ) as showOrderNo " + 
					"from t_sys_expenses  a " + 
					"inner join t_sys_expenses b  " + 
					"on  a.expenseCode = b.fatherExpenseCode " + 
					"where b.expenseCode =  ? " ;
			param.add(expenseCode);
			Query query = em.createNativeQuery(sqlString);
			setQueryParams(query, param.toArray());
			List list = query.getResultList();
			String[] fields = {"expenseCode", "expenseName", "timeMethod", 
					"fatherExpenseCode", "financeNo", "showOrderNo"};
			List resultList = bindDataToDTO(list, new SysExpenses(), fields);
			if (resultList.size() > 0) {
				return (SysExpenses) resultList.get(0);
			}
			return null;
		} catch (Exception e) {
			throw e;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<SysExpenses> getNotFartherItem() throws Exception {
		try {
			String sqlString = "select " + 
					"a.expenseCode, " + 
					"a.expenseName, " + 
					"a.timeMethod, " +
					"a.fatherExpenseCode, " + 
					"a.financeNo, " + 
					"cast(a.showOrderNo as char ) as showOrderNo " + 
					"from t_sys_expenses  a " + 
					"left join t_sys_expenses b  " + 
					"on  a.expenseCode = b.fatherExpenseCode " + 
					"where (b.expenseCode is not null and 1 <> 1 ) or b.expenseCode is null " ;
			Query query = em.createNativeQuery(sqlString);
			List list = query.getResultList();
			String[] fields = {"expenseCode", "expenseName", "timeMethod", 
					"fatherExpenseCode", "financeNo", "showOrderNo"};
			List resultList = bindDataToDTO(list, new SysExpenses(), fields);
			if (resultList.size() > 0) {
				return resultList;
			}
			return null;
		} catch (Exception e) {
			throw e;
		}
	}

}
