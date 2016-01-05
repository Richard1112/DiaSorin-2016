package com.diasorin.oa.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.diasorin.oa.common.CodeCommon;
import com.diasorin.oa.common.QueryResult;
import com.diasorin.oa.common.TypeConvertCommon;
import com.diasorin.oa.dao.ExportDao;
import com.diasorin.oa.dto.SeEcEpDeListBean;
import com.diasorin.oa.dto.SeRcReInBean;
import com.diasorin.oa.dto.SeRcReInExBean;
import com.diasorin.oa.dto.SeRcReInExpenseListBean;
import com.diasorin.oa.dto.SeRcReInHeadListBean;
import com.diasorin.oa.dto.SeRcReLsBean;
import com.diasorin.oa.dto.SeRcReLsExpenseListBean;
import com.diasorin.oa.dto.SeRcReLsHeadListBean;
import com.diasorin.oa.dto.SeRcReMoBean;
import com.diasorin.oa.dto.SeRcReMoSapBean;

@Component
public class ExportDaoImpl extends BaseDaoImpl implements ExportDao {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<SeRcReMoBean> getDateForSap(SeRcReMoSapBean seRcReMoSapBean) throws Exception {
		try {
			List<String> param = new ArrayList<String>();
			String sqlString = "select "+
					" left(A.costCenterCode, 6) as costCenter "+
					" ,E.employeeNo as internalOrderNumber "+
					" ,cast(C.expensesAmount as char) as amount "+
					" ,C.expensesComments as text"+
					" ,D.financeNo as account"+
					" from t_expenses_application A "+
					" inner join t_expenses_purpose_sum B "+
					" on A.expensesAppNo = B.belongExpensesAppNo "+
					" and A.deleteFlg = B.deleteFlg "+

					" inner join t_expenses_details C "+
					" on B.expensesDetailsNo = C.belongExpensesAppNo "+
					" and A.deleteFlg = C.deleteFlg "+

					" left join t_sys_expenses D "+
					" on C.expensesItem = D.expenseCode "+
					
					" left join t_employee_info E "+
					" on E.employeeNo = A.employeeNo "+
					" where A.deleteFlg = '0' ";
			
			if (seRcReMoSapBean != null) {
				// 必须是APPROVE的状态
				if (StringUtils.hasText(seRcReMoSapBean.getStatus())) {
					sqlString += " and A.status = ? ";
					param.add(seRcReMoSapBean.getStatus());
				}
				if (StringUtils.hasText(seRcReMoSapBean.getDateFrom())) {
					sqlString += " and left(cast(A.updTimestamp as char) , 10 ) >= ? ";
					param.add(seRcReMoSapBean.getDateFrom().replaceAll("/", "-"));
				}
				if (StringUtils.hasText(seRcReMoSapBean.getDateTo())) {
					sqlString += " and left(cast(A.updTimestamp as char) , 10 ) <= ? ";
					param.add(seRcReMoSapBean.getDateTo().replaceAll("/", "-"));
				}
				if ("0".equals(seRcReMoSapBean.getIsAllChecked())) {
					String allDetails = seRcReMoSapBean.getDetailId().substring(0, seRcReMoSapBean.getDetailId().length() - 1);
					String arr[] = allDetails.split(",");
					sqlString += " and " + TypeConvertCommon.createWhereIn(" C.expensesDetailsNo = ?" , arr.length);
					for (String str : arr){
						param.add((str));
					}
				}
				
			}
			
			Query query = em.createNativeQuery(sqlString);
			setQueryParams(query, param.toArray());
			List list = query.getResultList();
			String[] fields = { "costCenter", "internalOrderNumber", "amount",
					"text", "account" };
			List resultList = bindDataToDTO(list, new SeRcReMoBean(), fields);
			return resultList;
		} catch (Exception e) {
			throw e;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<SeRcReLsHeadListBean> getPurposeList(int start, int count, SeRcReLsBean seRcReLsBean)
			throws Exception {
		try {
			List<String> param = new ArrayList<String>();
			String sqlString = 
					" SELECT  "+
					" C.employeeNameEn as employee"+
					" ,A.expensesPurpose as purpose"+
					" ,cast(A.expensesAmount as char) as amount"+

					" FROM t_expenses_purpose_sum A "+
					" inner join t_expenses_application B "+
					" ON A.belongExpensesAppNo = B.expensesAppNo "+
					" AND A.deleteFlg = B.deleteFlg "+

					" inner join t_employee_info C "+
					" ON B.employeeNo = C.employeeNo "+
					" AND C.deleteFlg = A.deleteFlg "+

					" where A.deleteFlg = '0'  ";
			
			if (seRcReLsBean != null) {
				if (StringUtils.hasText(seRcReLsBean.getFinance())) {
					sqlString += " and B.status = ?  ";
					param.add(seRcReLsBean.getFinance());
				}
				if (StringUtils.hasText(seRcReLsBean.getFinanceDateFrom())) {
					sqlString += " and SUBSTRING(B.updTimestamp,1,10) >= ? ";
					param.add(seRcReLsBean.getFinanceDateFrom().replaceAll("/", "-"));
				}
				if (StringUtils.hasText(seRcReLsBean.getFinanceDateTo())) {
					sqlString += " and SUBSTRING(B.updTimestamp,1,10) <= ? ";
					param.add(seRcReLsBean.getFinanceDateTo().replaceAll("/", "-"));
				}
				if (StringUtils.hasText(seRcReLsBean.getEmployee())) {
					String arr[] = seRcReLsBean.getEmployee().split(",");
					sqlString += " and " + TypeConvertCommon.createWhereIn("B.employeeNo = ?" , arr.length);
					for (String str : arr){
						param.add((str));
					}
				}
				if (!StringUtils.hasText(seRcReLsBean.getOther())) {
					// 自己部门
					if (StringUtils.hasText(seRcReLsBean.getCostCenter())) {
						String[] arr = seRcReLsBean.getCostCenter().split(",");
						sqlString += " and " + TypeConvertCommon.createWhereIn("C.deptCode = ?" , arr.length);
						for (String str : arr){
							param.add((str));
						}
					}
					
				} else {
					// 其他部门申请到这边部门
					if (StringUtils.hasText(seRcReLsBean.getCostCenter())) {
						String[] arr = seRcReLsBean.getCostCenter().split(",");
						sqlString += " and " + TypeConvertCommon.createWhereIn("C.deptCode = ? or B.costCenterCode = ? " , arr.length);
						for (String str : arr){
							param.add((str));
							param.add((str));
						}
					}
				}
			}
			
			Query query = em.createNativeQuery(sqlString);
			setQueryParams(query, param.toArray());
			List list = query.getResultList();
			String[] fields = { "employee", "purpose", "amount",};
			List resultList = bindDataToDTO(list, new SeRcReLsHeadListBean(), fields);
			return resultList;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<SeRcReInHeadListBean> getPurposeList(int start, int count, SeRcReInBean seRcReInBean)
			throws Exception {
		try {
			List<String> param = new ArrayList<String>();
			String sqlString = 
					" SELECT  "+
					" C.employeeNameEn as employee"+
					" ,A.expensesPurpose as purpose"+
					" ,cast(A.expensesAmount as char) as amount"+

					" FROM t_expenses_purpose_sum A "+
					" inner join t_expenses_application B "+
					" ON A.belongExpensesAppNo = B.expensesAppNo "+
					" AND A.deleteFlg = B.deleteFlg "+

					" inner join t_employee_info C "+
					" ON B.employeeNo = C.employeeNo "+
					" AND C.deleteFlg = A.deleteFlg "+

					" where A.deleteFlg = '0'  ";
			
			if (seRcReInBean != null) {
				if (StringUtils.hasText(seRcReInBean.getFinance())) {
					sqlString += " and B.status = ?  ";
					param.add(seRcReInBean.getFinance());
				} else {
					sqlString += " and (B.status = '4' OR  B.status = '5') ";
				}
				if (StringUtils.hasText(seRcReInBean.getFinanceDateFrom())) {
					sqlString += " and SUBSTRING(B.updTimestamp,1,10) >= ? ";
					param.add(seRcReInBean.getFinanceDateFrom().replaceAll("/", "-"));
				}
				if (StringUtils.hasText(seRcReInBean.getFinanceDateTo())) {
					sqlString += " and SUBSTRING(B.updTimestamp,1,10) <= ? ";
					param.add(seRcReInBean.getFinanceDateTo().replaceAll("/", "-"));
				}
				if (StringUtils.hasText(seRcReInBean.getEmployee())) {
					String arr[] = seRcReInBean.getEmployee().split(",");
					sqlString += " and " + TypeConvertCommon.createWhereIn("B.employeeNo = ?" , arr.length);
					for (String str : arr){
						param.add((str));
					}
				}
				// 其他部门申请到这边部门
				if (StringUtils.hasText(seRcReInBean.getCostCenter())) {
					String[] arr = seRcReInBean.getCostCenter().split(",");
					sqlString += " and " + TypeConvertCommon.createWhereIn("C.deptCode = ? or B.costCenterCode = ? " , arr.length);
					for (String str : arr){
						param.add((str));
						param.add((str));
					}
				}
			}
			
			Query query = em.createNativeQuery(sqlString);
			setQueryParams(query, param.toArray());
			List list = query.getResultList();
			String[] fields = { "employee", "purpose", "amount",};
			List resultList = bindDataToDTO(list, new SeRcReInHeadListBean(), fields);
			return resultList;
		} catch (Exception e) {
			throw e;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<SeRcReLsExpenseListBean> getDetailList(int start, int count, SeRcReLsBean seRcReLsBean)
			throws Exception {
		try {
			List<String> param = new ArrayList<String>();
			String sqlString = 
					" SELECT   "+
					" C.employeeNameEn as employee "+
					" ,D.travelLocation as travelLocation"+
					" ,D.expensesItem as expensesItem"+
					" ,cast(SUM(D.expensesAmount) as char) as expensesAmount"+
					" ,B.employeeNo as employeeNo "+

					" FROM t_expenses_details D "+
					
					" inner join t_expenses_purpose_sum A  "+
					" ON D.belongExpensesAppNo = A.expensesDetailsNo "+
					" inner join t_expenses_application B  "+
					" ON A.belongExpensesAppNo = B.expensesAppNo "+ 
					" AND D.deleteFlg = B.deleteFlg  "+
					" inner join t_employee_info C  "+
					" ON B.employeeNo = C.employeeNo  "+
					" AND C.deleteFlg = D.deleteFlg  "+

					" where D.deleteFlg = '0'  ";

			if (seRcReLsBean != null) {

				if (StringUtils.hasText(seRcReLsBean.getOccurDateFrom())) {
					String occurDateFrom = seRcReLsBean.getOccurDateFrom().replaceAll("/", "");
					sqlString += " and ((D.expensesDateType = '1' and D.expensesDateEnd > ?) or "
							+ "(D.expensesDateType = '2' and D.expensesDate > ?) or "
							+ "(D.expensesDateType = '3' and D.expensesDateEnd > ?) ) ";
					param.add(occurDateFrom);
					param.add(occurDateFrom);
					param.add(occurDateFrom);
				}
				if (StringUtils.hasText(seRcReLsBean.getOccurDateTo())) {
					String occruDateTo = seRcReLsBean.getOccurDateTo().replaceAll("/", "");
					sqlString += " and ((D.expensesDateType = '1' and D.expensesDateEnd < ?) or "
							+ "(D.expensesDateType = '2' and D.expensesDateEnd < ?) or "
							+ "(D.expensesDateType = '3' and D.expensesDateEnd < ?) ) ";
					param.add(occruDateTo);
					param.add(occruDateTo);
					param.add(occruDateTo);
				}
				if (StringUtils.hasText(seRcReLsBean.getFinance())) {
					sqlString += " and B.status = ?  ";
					param.add(seRcReLsBean.getFinance());
				}
				if (StringUtils.hasText(seRcReLsBean.getFinanceDateFrom())) {
					sqlString += " and SUBSTRING(B.updTimestamp,1,10) >= ? ";
					param.add(seRcReLsBean.getFinanceDateFrom().replaceAll("/", "-"));
				}
				if (StringUtils.hasText(seRcReLsBean.getFinanceDateTo())) {
					sqlString += " and SUBSTRING(B.updTimestamp,1,10) <= ? ";
					param.add(seRcReLsBean.getFinanceDateTo().replaceAll("/", "-"));
				}
				if (StringUtils.hasText(seRcReLsBean.getEmployee())) {
					String arr[] = seRcReLsBean.getEmployee().split(",");
					sqlString += " and " + TypeConvertCommon.createWhereIn("B.employeeNo = ?" , arr.length);
					for (String str : arr){
						param.add((str));
					}
				}
				if (StringUtils.hasText(seRcReLsBean.getExpenseType())) {
					String arr[] = seRcReLsBean.getExpenseType().split(",");
					sqlString += " and " + TypeConvertCommon.createWhereIn("D.expensesItem = ?" , arr.length);
					for (String str : arr){
						param.add((str));
					}
				}
				if (!StringUtils.hasText(seRcReLsBean.getOther())) {
					// 自己部门
					if (StringUtils.hasText(seRcReLsBean.getCostCenter())) {
						String[] arr = seRcReLsBean.getCostCenter().split(",");
						sqlString += " and " + TypeConvertCommon.createWhereIn("C.deptCode = ?" , arr.length);
						for (String str : arr){
							param.add((str));
						}
					}
					
				} else {
					// 其他部门申请到这边部门
					if (StringUtils.hasText(seRcReLsBean.getCostCenter())) {
						String[] arr = seRcReLsBean.getCostCenter().split(",");
						sqlString += " and " + TypeConvertCommon.createWhereIn("C.deptCode = ? or B.costCenterCode = ? " , arr.length);
						for (String str : arr){
							param.add((str));
							param.add((str));
						}
					}
				}
			}
			sqlString += "group by B.employeeNo, D.expensesItem ";
			
			if (seRcReLsBean != null) {
				if (StringUtils.hasText(seRcReLsBean.getTravelFlg())) {
					sqlString += " , D.travelLocation ";
				}
			}
			
			sqlString += " order by C.employeeNameEn,D.travelLocation,D.expensesItem";
			
			Query query = em.createNativeQuery(sqlString);
			setQueryParams(query, param.toArray());
			List list = query.getResultList();
			String[] fields = { "employee", "travelLocation", "expensesItem", "expensesAmount", "employeeNo"};
			List resultList = bindDataToDTO(list, new SeRcReLsExpenseListBean(), fields);
			return resultList;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<SeRcReInExpenseListBean> getDetailList(int start, int count, SeRcReInBean seRcReInBean)
			throws Exception {
		try {
			List<String> param = new ArrayList<String>();
			String sqlString = 
					" SELECT   "+
					" C.employeeNameEn as employee "+
					" ,D.travelLocation as travelLocation"+
					" ,D.expensesItem as expensesItem"+
					" ,cast(SUM(D.expensesAmount) as char) as expensesAmount"+
					" ,B.employeeNo as employeeNo "+

					" FROM t_expenses_details D "+
					
					" inner join t_expenses_purpose_sum A  "+
					" ON D.belongExpensesAppNo = A.expensesDetailsNo "+
					" inner join t_expenses_application B  "+
					" ON A.belongExpensesAppNo = B.expensesAppNo "+ 
					" AND D.deleteFlg = B.deleteFlg  "+
					" inner join t_employee_info C  "+
					" ON B.employeeNo = C.employeeNo  "+
					" AND C.deleteFlg = D.deleteFlg  "+

					" where D.deleteFlg = '0'  ";

			if (seRcReInBean != null) {

				if (StringUtils.hasText(seRcReInBean.getFinance())) {
					sqlString += " and B.status = ?  ";
					param.add(seRcReInBean.getFinance());
				} else {
					sqlString += " and (B.status = '4' OR  B.status = '5') ";
				}
				if (StringUtils.hasText(seRcReInBean.getFinanceDateFrom())) {
					sqlString += " and SUBSTRING(B.updTimestamp,1,10) >= ? ";
					param.add(seRcReInBean.getFinanceDateFrom().replaceAll("/", "-"));
				}
				if (StringUtils.hasText(seRcReInBean.getFinanceDateTo())) {
					sqlString += " and SUBSTRING(B.updTimestamp,1,10) <= ? ";
					param.add(seRcReInBean.getFinanceDateTo().replaceAll("/", "-"));
				}
				if (StringUtils.hasText(seRcReInBean.getEmployee())) {
					String arr[] = seRcReInBean.getEmployee().split(",");
					sqlString += " and " + TypeConvertCommon.createWhereIn("B.employeeNo = ?" , arr.length);
					for (String str : arr){
						param.add((str));
					}
				}
				if (StringUtils.hasText(seRcReInBean.getExpenseType())) {
					String arr[] = seRcReInBean.getExpenseType().split(",");
					sqlString += " and " + TypeConvertCommon.createWhereIn("D.expensesItem = ?" , arr.length);
					for (String str : arr){
						param.add((str));
					}
				}
				// 其他部门申请到这边部门
				if (StringUtils.hasText(seRcReInBean.getCostCenter())) {
					String[] arr = seRcReInBean.getCostCenter().split(",");
					sqlString += " and " + TypeConvertCommon.createWhereIn("C.deptCode = ? or B.costCenterCode = ? " , arr.length);
					for (String str : arr){
						param.add((str));
						param.add((str));
					}
				}
			}
			sqlString += "group by B.employeeNo, D.expensesItem ";
			
			if (seRcReInBean != null) {
				if (StringUtils.hasText(seRcReInBean.getTravelFlg())) {
					sqlString += " , D.travelLocation ";
				}
			}
			
			sqlString += " order by C.employeeNameEn,D.travelLocation,D.expensesItem";
			
			Query query = em.createNativeQuery(sqlString);
			setQueryParams(query, param.toArray());
			List list = query.getResultList();
			String[] fields = { "employee", "travelLocation", "expensesItem", "expensesAmount", "employeeNo"};
			List resultList = bindDataToDTO(list, new SeRcReInExpenseListBean(), fields);
			return resultList;
		} catch (Exception e) {
			throw e;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<String> getDetailTitleList(SeRcReLsBean seRcReLsBean)
			throws Exception {
		try {
			List<String> param = new ArrayList<String>();
			String sqlString = 
					" SELECT   "+
					" D.expensesItem "+
					" FROM t_expenses_details D "+
					" inner join t_expenses_purpose_sum A  "+
					" ON D.belongExpensesAppNo = A.expensesDetailsNo "+
					" AND D.deleteFlg = A.deleteFlg  "+
					" inner join t_expenses_application B  "+
					" ON A.belongExpensesAppNo = B.expensesAppNo  "+
					" AND D.deleteFlg = B.deleteFlg  "+
					" inner join t_employee_info C  "+
					" ON B.employeeNo = C.employeeNo  "+
					" AND C.deleteFlg = D.deleteFlg  "+

					" where D.deleteFlg = '0'  ";

			if (seRcReLsBean != null) {
				if (StringUtils.hasText(seRcReLsBean.getOccurDateFrom())) {
					String occurDateFrom = seRcReLsBean.getOccurDateFrom().replaceAll("/", "");
					sqlString += " and ((D.expensesDateType = '1' and D.expensesDateEnd > ?) or "
							+ "(D.expensesDateType = '2' and D.expensesDate > ?) or "
							+ "(D.expensesDateType = '3' and D.expensesDateEnd > ?) ) ";
					param.add(occurDateFrom);
					param.add(occurDateFrom);
					param.add(occurDateFrom);
				}
				if (StringUtils.hasText(seRcReLsBean.getOccurDateTo())) {
					String occurDateTo = seRcReLsBean.getOccurDateTo().replaceAll("/", "");
					sqlString += " and ((D.expensesDateType = '1' and D.expensesDateEnd < ?) or "
							+ "(D.expensesDateType = '2' and D.expensesDateEnd < ?) or "
							+ "(D.expensesDateType = '3' and D.expensesDateEnd < ?) ) ";
					param.add(occurDateTo);
					param.add(occurDateTo);
					param.add(occurDateTo);
				}
				if (StringUtils.hasText(seRcReLsBean.getFinance())) {
					sqlString += " and B.status = ?  ";
					param.add(seRcReLsBean.getFinance());
				}
				if (StringUtils.hasText(seRcReLsBean.getFinanceDateFrom())) {
					sqlString += " and SUBSTRING(B.updTimestamp,1,10) >= ? ";
					param.add(seRcReLsBean.getFinanceDateFrom().replaceAll("/", "-"));
				}
				if (StringUtils.hasText(seRcReLsBean.getFinanceDateTo())) {
					sqlString += " and SUBSTRING(B.updTimestamp,1,10) <= ? ";
					param.add(seRcReLsBean.getFinanceDateTo().replaceAll("/", "-"));
				}
				if (StringUtils.hasText(seRcReLsBean.getEmployee())) {
					String arr[] = seRcReLsBean.getEmployee().split(",");
					sqlString += " and " + TypeConvertCommon.createWhereIn("B.employeeNo = ? " , arr.length);
					for (String str : arr){
						param.add((str));
					}
				}
				if (StringUtils.hasText(seRcReLsBean.getExpenseType())) {
					String arr[] = seRcReLsBean.getExpenseType().split(",");
					sqlString += " and " + TypeConvertCommon.createWhereIn("D.expensesItem = ? " , arr.length);
					for (String str : arr){
						param.add((str));
					}
				}
				if (!StringUtils.hasText(seRcReLsBean.getOther())) {
					// 自己部门
					if (StringUtils.hasText(seRcReLsBean.getCostCenter())) {
						String[] arr = seRcReLsBean.getCostCenter().split(",");
						sqlString += " and " + TypeConvertCommon.createWhereIn("C.deptCode = ?" , arr.length);
						for (String str : arr){
							param.add((str));
						}
					}
					
				} else {
					// 其他部门申请到这边部门
					if (StringUtils.hasText(seRcReLsBean.getCostCenter())) {
						String[] arr = seRcReLsBean.getCostCenter().split(",");
						sqlString += " and " + TypeConvertCommon.createWhereIn("C.deptCode = ? or B.costCenterCode = ? " , arr.length);
						for (String str : arr){
							param.add((str));
							param.add((str));
						}
					}
				}
			}
			sqlString += " GROUP BY D.expensesItem ";
			sqlString += " order by D.expensesItem ";
			
			Query query = em.createNativeQuery(sqlString);
			setQueryParams(query, param.toArray());
			List list = query.getResultList();
			return list;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<String> getDetailTitleList(SeRcReInBean seRcReInBean)
			throws Exception {
		try {
			List<String> param = new ArrayList<String>();
			String sqlString = 
					" SELECT   "+
					" D.expensesItem "+
					" FROM t_expenses_details D "+
					" inner join t_expenses_purpose_sum A  "+
					" ON D.belongExpensesAppNo = A.expensesDetailsNo "+
					" AND D.deleteFlg = A.deleteFlg  "+
					" inner join t_expenses_application B  "+
					" ON A.belongExpensesAppNo = B.expensesAppNo  "+
					" AND D.deleteFlg = B.deleteFlg  "+
					" inner join t_employee_info C  "+
					" ON B.employeeNo = C.employeeNo  "+
					" AND C.deleteFlg = D.deleteFlg  "+

					" where D.deleteFlg = '0'  ";

			if (seRcReInBean != null) {
				if (StringUtils.hasText(seRcReInBean.getFinance())) {
					sqlString += " and B.status = ?  ";
					param.add(seRcReInBean.getFinance());
				} else {
					sqlString += " and (B.status = '4' OR  B.status = '5') ";
				}
				if (StringUtils.hasText(seRcReInBean.getFinanceDateFrom())) {
					sqlString += " and SUBSTRING(B.updTimestamp,1,10) >= ? ";
					param.add(seRcReInBean.getFinanceDateFrom().replaceAll("/", "-"));
				}
				if (StringUtils.hasText(seRcReInBean.getFinanceDateTo())) {
					sqlString += " and SUBSTRING(B.updTimestamp,1,10) <= ? ";
					param.add(seRcReInBean.getFinanceDateTo().replaceAll("/", "-"));
				}
				if (StringUtils.hasText(seRcReInBean.getEmployee())) {
					String arr[] = seRcReInBean.getEmployee().split(",");
					sqlString += " and " + TypeConvertCommon.createWhereIn("B.employeeNo = ? " , arr.length);
					for (String str : arr){
						param.add((str));
					}
				}
				if (StringUtils.hasText(seRcReInBean.getExpenseType())) {
					String arr[] = seRcReInBean.getExpenseType().split(",");
					sqlString += " and " + TypeConvertCommon.createWhereIn("D.expensesItem = ? " , arr.length);
					for (String str : arr){
						param.add((str));
					}
				}
				// 其他部门申请到这边部门
				if (StringUtils.hasText(seRcReInBean.getCostCenter())) {
					String[] arr = seRcReInBean.getCostCenter().split(",");
					sqlString += " and " + TypeConvertCommon.createWhereIn("C.deptCode = ? or B.costCenterCode = ? " , arr.length);
					for (String str : arr){
						param.add((str));
						param.add((str));
					}
				}
			}
			sqlString += " GROUP BY D.expensesItem ";
			sqlString += " order by D.expensesItem ";
			
			Query query = em.createNativeQuery(sqlString);
			setQueryParams(query, param.toArray());
			List list = query.getResultList();
			return list;
		} catch (Exception e) {
			throw e;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<SeEcEpDeListBean> getDetailData(String employeeNo,
			String travelLocation, SeRcReLsBean seRcReLsBean) throws Exception {
		try {
			List<String> param = new ArrayList<String>();
			String sqlString = 
					" SELECT   "+
					"  B.expensesAppNo as no "+
					" ,D.expensesDate as dayFrom "+
					" ,D.expensesDateEnd as dayTo "+
					" ,C.expenseName as expenseType "+
					" ,C.timeMethod as timeMethod "+
					" ,cast(D.expensesAmount as char) as expenseAmount "+
					" ,D.expensesComments as comments "+
					" ,D.travelLocation as location "+
					" FROM t_expenses_details D "+
					" inner join t_expenses_purpose_sum A  "+
					" ON D.belongExpensesAppNo = A.expensesDetailsNo "+
					" AND D.deleteFlg = A.deleteFlg  "+
					" inner join t_expenses_application B  "+
					" ON A.belongExpensesAppNo = B.expensesAppNo  "+
					" AND D.deleteFlg = B.deleteFlg  "+
					" inner join t_sys_expenses C "+
					" ON C.expenseCode = D.expensesItem "+
					" inner join t_employee_info E  "+
					" ON B.employeeNo = E.employeeNo  "+
					" AND E.deleteFlg = D.deleteFlg  "+

					" where D.deleteFlg = '0'  ";

			if (seRcReLsBean != null) {
				if (StringUtils.hasText(seRcReLsBean.getOccurDateFrom())) {
					String occurDateFrom = seRcReLsBean.getOccurDateFrom().replaceAll("/", "");
					sqlString += " and ((D.expensesDateType = '1' and D.expensesDate > ?) or "
							+ "(D.expensesDateType = '2' and D.expensesDate > ?) or "
							+ "(D.expensesDateType = '3' and SUBSTRING(D.expensesDate,1,6) > ?) ) ";
					param.add(occurDateFrom);
					param.add(occurDateFrom);
					param.add(occurDateFrom);
				}
				if (StringUtils.hasText(seRcReLsBean.getOccurDateTo())) {
					String occurDateTo = seRcReLsBean.getOccurDateTo().replaceAll("/", "");
					sqlString += " and ((D.expensesDateType = '1' and D.expensesDate < ?) or "
							+ "(D.expensesDateType = '2' and D.expensesDateEnd < ?) or "
							+ "(D.expensesDateType = '3' and D.expensesDate < ?) ) ";
					param.add(occurDateTo);
					param.add(occurDateTo);
					param.add(occurDateTo);
				}
				if (StringUtils.hasText(seRcReLsBean.getFinance())) {
					sqlString += " and B.status = ?  ";
					param.add(seRcReLsBean.getFinance());
				} else {
					sqlString += " and (B.status = '4' OR  B.status = '5') ";
				}
				if (StringUtils.hasText(seRcReLsBean.getFinanceDateFrom())) {
					sqlString += " and SUBSTRING(B.updTimestamp,1,10) >= ? ";
					param.add(seRcReLsBean.getFinanceDateFrom().replaceAll("/", "-"));
				}
				if (StringUtils.hasText(seRcReLsBean.getFinanceDateTo())) {
					sqlString += " and SUBSTRING(B.updTimestamp,1,10) <= ? ";
					param.add(seRcReLsBean.getFinanceDateTo().replaceAll("/", "-"));
				}
				if (StringUtils.hasText(seRcReLsBean.getEmployee())) {
					String arr[] = seRcReLsBean.getEmployee().split(",");
					sqlString += " and " + TypeConvertCommon.createWhereIn("B.employeeNo = ?" , arr.length);
					for (String str : arr){
						param.add((str));
					}
				}
				if (StringUtils.hasText(seRcReLsBean.getExpenseType())) {
					String arr[] = seRcReLsBean.getExpenseType().split(",");
					sqlString += " and " + TypeConvertCommon.createWhereIn("D.expensesItem = ?" , arr.length);
					for (String str : arr){
						param.add((str));
					}
				}
				if (!StringUtils.hasText(seRcReLsBean.getOther())) {
					// 自己部门
					if (StringUtils.hasText(seRcReLsBean.getCostCenter())) {
						String[] arr = seRcReLsBean.getCostCenter().split(",");
						sqlString += " and " + TypeConvertCommon.createWhereIn("E.deptCode = ?" , arr.length);
						for (String str : arr){
							param.add((str));
						}
					}
					
				} else {
					// 其他部门申请到这边部门
					if (StringUtils.hasText(seRcReLsBean.getCostCenter())) {
						String[] arr = seRcReLsBean.getCostCenter().split(",");
						sqlString += " and " + TypeConvertCommon.createWhereIn("E.deptCode = ? or B.costCenterCode = ? " , arr.length);
						for (String str : arr){
							param.add((str));
							param.add((str));
						}
					}
				}
			}
			
			if (StringUtils.hasText(employeeNo)) {
				sqlString += " and B.employeeNo = ? ";
				param.add(employeeNo);
			}
			
			if (StringUtils.hasText(travelLocation)) {
				sqlString += " and D.travelLocation = ? ";
				param.add(travelLocation);
			}
			sqlString += " order by  D.expensesDateEnd ,D.expensesItem ";
			
			Query query = em.createNativeQuery(sqlString);
			setQueryParams(query, param.toArray());
			List list = query.getResultList();
			String[] fields = {"no", "dayFrom", "dayTo", "expenseType", "timeMethod", "expenseAmount", "comments", "location"};
			List resultList = bindDataToDTO(list, new SeEcEpDeListBean(), fields);
			return resultList;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<SeEcEpDeListBean> getDetailData(String employeeNo,
			String travelLocation, SeRcReInBean seRcReInBean) throws Exception {
		try {
			List<String> param = new ArrayList<String>();
			String sqlString = 
					" SELECT   "+
					"  B.expensesAppNo as no "+
					" ,D.expensesDate as dayFrom "+
					" ,D.expensesDateEnd as dayTo "+
					" ,C.expenseName as expenseType "+
					" ,C.timeMethod as timeMethod "+
					" ,cast(D.expensesAmount as char) as expenseAmount "+
					" ,D.expensesComments as comments "+
					" ,D.travelLocation as location "+
					" FROM t_expenses_details D "+
					" inner join t_expenses_purpose_sum A  "+
					" ON D.belongExpensesAppNo = A.expensesDetailsNo "+
					" AND D.deleteFlg = A.deleteFlg  "+
					" inner join t_expenses_application B  "+
					" ON A.belongExpensesAppNo = B.expensesAppNo  "+
					" AND D.deleteFlg = B.deleteFlg  "+
					" inner join t_sys_expenses C "+
					" ON C.expenseCode = D.expensesItem "+
					" inner join t_employee_info E  "+
					" ON B.employeeNo = E.employeeNo  "+
					" AND E.deleteFlg = D.deleteFlg  "+

					" where D.deleteFlg = '0'  ";

			if (seRcReInBean != null) {
				if (StringUtils.hasText(seRcReInBean.getFinance())) {
					sqlString += " and B.status = ?  ";
					param.add(seRcReInBean.getFinance());
				} else {
					sqlString += " and (B.status = '4' OR  B.status = '5') ";
				}
				if (StringUtils.hasText(seRcReInBean.getFinanceDateFrom())) {
					sqlString += " and SUBSTRING(B.updTimestamp,1,10) >= ? ";
					param.add(seRcReInBean.getFinanceDateFrom().replaceAll("/", "-"));
				}
				if (StringUtils.hasText(seRcReInBean.getFinanceDateTo())) {
					sqlString += " and SUBSTRING(B.updTimestamp,1,10) <= ? ";
					param.add(seRcReInBean.getFinanceDateTo().replaceAll("/", "-"));
				}
				if (StringUtils.hasText(seRcReInBean.getEmployee())) {
					String arr[] = seRcReInBean.getEmployee().split(",");
					sqlString += " and " + TypeConvertCommon.createWhereIn("B.employeeNo = ?" , arr.length);
					for (String str : arr){
						param.add((str));
					}
				}
				if (StringUtils.hasText(seRcReInBean.getExpenseType())) {
					String arr[] = seRcReInBean.getExpenseType().split(",");
					sqlString += " and " + TypeConvertCommon.createWhereIn("D.expensesItem = ?" , arr.length);
					for (String str : arr){
						param.add((str));
					}
				}
				// 其他部门申请到这边部门
				if (StringUtils.hasText(seRcReInBean.getCostCenter())) {
					String[] arr = seRcReInBean.getCostCenter().split(",");
					sqlString += " and " + TypeConvertCommon.createWhereIn("E.deptCode = ? or B.costCenterCode = ? " , arr.length);
					for (String str : arr){
						param.add((str));
						param.add((str));
					}
				}
			}
			
			if (StringUtils.hasText(employeeNo)) {
				sqlString += " and B.employeeNo = ? ";
				param.add(employeeNo);
			}
			
			if (StringUtils.hasText(travelLocation)) {
				sqlString += " and D.travelLocation = ? ";
				param.add(travelLocation);
			}
			sqlString += " order by  D.expensesDateEnd ,D.expensesItem ";
			
			Query query = em.createNativeQuery(sqlString);
			setQueryParams(query, param.toArray());
			List list = query.getResultList();
			String[] fields = {"no", "dayFrom", "dayTo", "expenseType", "timeMethod", "expenseAmount", "comments", "location"};
			List resultList = bindDataToDTO(list, new SeEcEpDeListBean(), fields);
			return resultList;
		} catch (Exception e) {
			throw e;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public QueryResult<SeRcReMoBean> searchDateForSap(SeRcReMoSapBean seRcReMoSapBean, int start, int result)
			throws Exception {
		try {
			List<String> param = new ArrayList<String>();
			String sqlString = "select "+
					" A.costCenterCode as costCenter "+
					" ,A.employeeNo as internalOrderNumber "+
					" ,cast(C.expensesAmount as char) as amount "+
					" ,C.expensesComments as text"+
					" ,D.financeNo as account"+
					" ,D.expenseName as expenseType"+
					" ,E.employeeNameEn as employeeName"+
					" ,F.costCenterName as costCenterName"+
					" ,C.expensesDetailsNo as expensesDetailsNo"+
					" from t_expenses_application A "+
					" inner join t_expenses_purpose_sum B "+
					" on A.expensesAppNo = B.belongExpensesAppNo "+
					" and A.deleteFlg = B.deleteFlg "+

					" inner join t_expenses_details C "+
					" on B.expensesDetailsNo = C.belongExpensesAppNo "+
					" and A.deleteFlg = C.deleteFlg "+

					" left join t_sys_expenses D "+
					" on C.expensesItem = D.expenseCode "+
			
					" left join t_employee_info E "+
					" on A.employeeNo = E.employeeNo "+
					
					" left join t_sys_cost_center F "+
					" on A.costCenterCode = F.costCenterCode "+
					
					" where A.deleteFlg = '0' ";

			if (seRcReMoSapBean != null) {
				// 必须是APPROVE的状态
				if (StringUtils.hasText(seRcReMoSapBean.getStatus())) {
					sqlString += " and A.status = ? ";
					param.add(seRcReMoSapBean.getStatus());
				}
				if (StringUtils.hasText(seRcReMoSapBean.getDateFrom())) {
					sqlString += " and left(cast(A.updTimestamp as char) , 10 ) >= ? ";
					param.add(seRcReMoSapBean.getDateFrom().replaceAll("/", "-"));
				}
				if (StringUtils.hasText(seRcReMoSapBean.getDateTo())) {
					sqlString += " and left(cast(A.updTimestamp as char) , 10 ) <= ? ";
					param.add(seRcReMoSapBean.getDateTo().replaceAll("/", "-"));
				}
				
			}
			Query query = em.createNativeQuery(sqlString);
			setQueryParams(query, param.toArray());
			
			QueryResult<SeRcReMoBean> qr = new QueryResult<SeRcReMoBean>();
			
			qr.setTotalrecord(query.getResultList().size());
			
			query.setFirstResult(start).setMaxResults(result);
			List list = query.getResultList();
			String[] fields = { "costCenter", "internalOrderNumber", "amount",
					"text", "account", "expenseType", "employeeName","costCenterName","expensesDetailsNo" };
			
			List resultList = bindDataToDTO(list, new SeRcReMoBean(), fields);
			qr.setResultlist(resultList);
			return qr;
		} catch (Exception e) {
			throw e;
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public String sapTotalAmount(SeRcReMoSapBean seRcReMoSapBean)
			throws Exception {
		try {
			List<String> param = new ArrayList<String>();
			String sqlString = "select "+
					" cast(SUM(C.expensesAmount) as char) as totalamount "+
					" from t_expenses_application A "+
					" inner join t_expenses_purpose_sum B "+
					" on A.expensesAppNo = B.belongExpensesAppNo "+
					" and A.deleteFlg = B.deleteFlg "+

					" inner join t_expenses_details C "+
					" on B.expensesDetailsNo = C.belongExpensesAppNo "+
					" and A.deleteFlg = C.deleteFlg "+
					
					" where A.deleteFlg = '0' ";

			if (seRcReMoSapBean != null) {
				// 必须是APPROVE的状态
				if (StringUtils.hasText(seRcReMoSapBean.getStatus())) {
					sqlString += " and A.status = ? ";
					param.add(seRcReMoSapBean.getStatus());
				}
				if (StringUtils.hasText(seRcReMoSapBean.getDateFrom())) {
					sqlString += " and left(cast(A.updTimestamp as char) , 10 ) >= ? ";
					param.add(seRcReMoSapBean.getDateFrom().replaceAll("/", "-"));
				}
				if (StringUtils.hasText(seRcReMoSapBean.getDateTo())) {
					sqlString += " and left(cast(A.updTimestamp as char) , 10 ) <= ? ";
					param.add(seRcReMoSapBean.getDateTo().replaceAll("/", "-"));
				}
				
			}
			Query query = em.createNativeQuery(sqlString);
			setQueryParams(query, param.toArray());
			
			List list = query.getResultList();
			if (list == null || list.size() == 0 || list.get(0) == null) return "";
			return list.get(0).toString();
		} catch (Exception e) {
			throw e;
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public String getAllDetailNo(SeRcReMoSapBean seRcReMoSapBean)
			throws Exception {
		try {
			List<String> param = new ArrayList<String>();
			String sqlString = "select "+
					" group_concat(C.expensesDetailsNo) as allDetailNo "+
					" from t_expenses_application A "+
					" inner join t_expenses_purpose_sum B "+
					" on A.expensesAppNo = B.belongExpensesAppNo "+
					" and A.deleteFlg = B.deleteFlg "+

					" inner join t_expenses_details C "+
					" on B.expensesDetailsNo = C.belongExpensesAppNo "+
					" and A.deleteFlg = C.deleteFlg "+
					
					" where A.deleteFlg = '0' ";

			if (seRcReMoSapBean != null) {
				// 必须是APPROVE的状态
				if (StringUtils.hasText(seRcReMoSapBean.getStatus())) {
					sqlString += " and A.status = ? ";
					param.add(seRcReMoSapBean.getStatus());
				}
				if (StringUtils.hasText(seRcReMoSapBean.getDateFrom())) {
					sqlString += " and left(cast(A.updTimestamp as char) , 10 ) >= ? ";
					param.add(seRcReMoSapBean.getDateFrom().replaceAll("/", "-"));
				}
				if (StringUtils.hasText(seRcReMoSapBean.getDateTo())) {
					sqlString += " and left(cast(A.updTimestamp as char) , 10 ) <= ? ";
					param.add(seRcReMoSapBean.getDateTo().replaceAll("/", "-"));
				}
				
			}
			Query query = em.createNativeQuery(sqlString);
			setQueryParams(query, param.toArray());
			
			List list = query.getResultList();
			if (list == null || list.size() == 0 || list.get(0) == null) return "";
			return list.get(0).toString();
		} catch (Exception e) {
			throw e;
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<SeRcReInExBean> getExportData(SeRcReInBean seRcReInBean)
			throws Exception {
		try {
			List<String> param = new ArrayList<String>();
			String sqlString = 
					" SELECT   "+
					"  B.applicationDate as submitDate "+
							
					" ,(select max(left(cast(operTimestamp as char) , 10 )) as approvalDate from t_workflow_progress "
					+ "where businessId = B.expensesAppNo and progressStatus = '4' and employeeNo is not null) as approvalDate "+ // 审批通过的日期
					
					" ,(select max(left(cast(operTimestamp as char) , 10 )) as finishedDate from t_workflow_progress "
					+ "where businessId = B.expensesAppNo and progressStatus = '5' and employeeNo is not null) as finishedDate "+ // 审批完成的日期
					" ,E.deptCode as costCenter "+
					
					" ,E.employeeNameEn as employee "+
					
					" ,B.expensesAppNo as applicationNo "+
					
					" ,A.expensesDetailsNo as purposeNo "+
					
					" ,B.travelReason as travelReason "+
					
					" ,C.expenseName as expenseType "+
					
					" ,D.expensesDate as dayFrom "+
					" ,D.expensesDateEnd as dayTo "+
					
					" ,D.travelLocation as location "+
					
					" ,cast(D.expensesAmount as char) as amount "+
					" ,D.expensesComments as comments "+
					
					" FROM t_expenses_details D "+
					" inner join t_expenses_purpose_sum A  "+
					" ON D.belongExpensesAppNo = A.expensesDetailsNo "+
					" AND D.deleteFlg = A.deleteFlg  "+
					" inner join t_expenses_application B  "+
					" ON A.belongExpensesAppNo = B.expensesAppNo  "+
					" AND D.deleteFlg = B.deleteFlg  "+
					" inner join t_sys_expenses C "+
					" ON C.expenseCode = D.expensesItem "+
					" inner join t_employee_info E  "+
					" ON B.employeeNo = E.employeeNo  "+
					" AND E.deleteFlg = D.deleteFlg  "+
					
					" where D.deleteFlg = '0'  ";

			if (seRcReInBean != null) {
				if (StringUtils.hasText(seRcReInBean.getFinance())) {
					sqlString += " and B.status = ?  ";
					param.add(seRcReInBean.getFinance());
					if (CodeCommon.CLAIM_APPROVED.equals(seRcReInBean.getFinance())) {
						// approve 的场合
						if (StringUtils.hasText(seRcReInBean.getFinanceDateFrom())) {
							sqlString += " and approvalDate >= ? ";
							param.add(seRcReInBean.getFinanceDateFrom().replaceAll("/", "-"));
						}
						if (StringUtils.hasText(seRcReInBean.getFinanceDateTo())) {
							sqlString += " and approvalDate <= ? ";
							param.add(seRcReInBean.getFinanceDateTo().replaceAll("/", "-"));
						}
					} else {
						// 完成的情况
						if (StringUtils.hasText(seRcReInBean.getFinanceDateFrom())) {
							sqlString += " and finishedDate >= ? ";
							param.add(seRcReInBean.getFinanceDateFrom().replaceAll("/", "-"));
						}
						if (StringUtils.hasText(seRcReInBean.getFinanceDateTo())) {
							sqlString += " and finishedDate <= ? ";
							param.add(seRcReInBean.getFinanceDateTo().replaceAll("/", "-"));
						}
					}
					
				} else {
					sqlString += " and (B.status = '4' OR  B.status = '5') ";
				}
				if (StringUtils.hasText(seRcReInBean.getEmployee())) {
					String arr[] = seRcReInBean.getEmployee().split(",");
					sqlString += " and " + TypeConvertCommon.createWhereIn("B.employeeNo = ?" , arr.length);
					for (String str : arr){
						param.add((str));
					}
				}
				if (StringUtils.hasText(seRcReInBean.getExpenseType())) {
					String arr[] = seRcReInBean.getExpenseType().split(",");
					sqlString += " and " + TypeConvertCommon.createWhereIn("D.expensesItem = ?" , arr.length);
					for (String str : arr){
						param.add((str));
					}
				}
				// 其他部门申请到这边部门
				if (StringUtils.hasText(seRcReInBean.getCostCenter())) {
					String[] arr = seRcReInBean.getCostCenter().split(",");
					sqlString += " and " + TypeConvertCommon.createWhereIn("E.deptCode = ? or B.costCenterCode = ? " , arr.length);
					for (String str : arr){
						param.add((str));
						param.add((str));
					}
				}
			}
			
			sqlString += " order by  submitDate ,approvalDate ";
			
			Query query = em.createNativeQuery(sqlString);
			setQueryParams(query, param.toArray());
			List list = query.getResultList();
			String[] fields = {"submitDate", "approvalDate", "finishedDate", "costCenter", "employee"
					, "applicationNo", "purposeNo", "travelReason", "expenseType", "dayFrom", "dayTo", "location"
					, "amount", "comments"};
			List resultList = bindDataToDTO(list, new SeRcReInExBean(), fields);
			return resultList;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<SeRcReInExpenseListBean> getExportDataGroupByEm(SeRcReInBean seRcReInBean) throws Exception{
		try {
			List<String> param = new ArrayList<String>();
			String sqlString = 
					" SELECT   "+

					"  E.employeeNo as employeeNo "+
					" ,E.employeeNameEn as employee "+
					
					" ,cast(sum(D.expensesAmount) as char) as expensesAmount "+
					
					" FROM t_expenses_details D "+
					" inner join t_expenses_purpose_sum A  "+
					" ON D.belongExpensesAppNo = A.expensesDetailsNo "+
					" AND D.deleteFlg = A.deleteFlg  "+
					" inner join t_expenses_application B  "+
					" ON A.belongExpensesAppNo = B.expensesAppNo  "+
					" AND D.deleteFlg = B.deleteFlg  "+
					" inner join t_sys_expenses C "+
					" ON C.expenseCode = D.expensesItem "+
					" inner join t_employee_info E  "+
					" ON B.employeeNo = E.employeeNo  "+
					" AND E.deleteFlg = D.deleteFlg  "+
					
					" where D.deleteFlg = '0'  ";

			if (seRcReInBean != null) {
				if (StringUtils.hasText(seRcReInBean.getFinance())) {
					sqlString += " and B.status = ?  ";
					param.add(seRcReInBean.getFinance());
					if (CodeCommon.CLAIM_APPROVED.equals(seRcReInBean.getFinance())) {
						// approve 的场合
						if (StringUtils.hasText(seRcReInBean.getFinanceDateFrom())) {
							sqlString += " and approvalDate >= ? ";
							param.add(seRcReInBean.getFinanceDateFrom().replaceAll("/", "-"));
						}
						if (StringUtils.hasText(seRcReInBean.getFinanceDateTo())) {
							sqlString += " and approvalDate <= ? ";
							param.add(seRcReInBean.getFinanceDateTo().replaceAll("/", "-"));
						}
					} else {
						// 完成的情况
						if (StringUtils.hasText(seRcReInBean.getFinanceDateFrom())) {
							sqlString += " and finishedDate >= ? ";
							param.add(seRcReInBean.getFinanceDateFrom().replaceAll("/", "-"));
						}
						if (StringUtils.hasText(seRcReInBean.getFinanceDateTo())) {
							sqlString += " and finishedDate <= ? ";
							param.add(seRcReInBean.getFinanceDateTo().replaceAll("/", "-"));
						}
					}
					
				} else {
					sqlString += " and (B.status = '4' OR  B.status = '5') ";
				}
				if (StringUtils.hasText(seRcReInBean.getEmployee())) {
					String arr[] = seRcReInBean.getEmployee().split(",");
					sqlString += " and " + TypeConvertCommon.createWhereIn("B.employeeNo = ?" , arr.length);
					for (String str : arr){
						param.add((str));
					}
				}
				if (StringUtils.hasText(seRcReInBean.getExpenseType())) {
					String arr[] = seRcReInBean.getExpenseType().split(",");
					sqlString += " and " + TypeConvertCommon.createWhereIn("D.expensesItem = ?" , arr.length);
					for (String str : arr){
						param.add((str));
					}
				}
				// 其他部门申请到这边部门
				if (StringUtils.hasText(seRcReInBean.getCostCenter())) {
					String[] arr = seRcReInBean.getCostCenter().split(",");
					sqlString += " and " + TypeConvertCommon.createWhereIn("E.deptCode = ? or B.costCenterCode = ? " , arr.length);
					for (String str : arr){
						param.add((str));
						param.add((str));
					}
				}
			}
			
			sqlString += " group by  E.employeeNo ,E.employeeNameEn  ";
			sqlString += " order by  E.employeeNo ";
			
			Query query = em.createNativeQuery(sqlString);
			setQueryParams(query, param.toArray());
			List list = query.getResultList();
			String[] fields = {"employeeNo", "employee", "expensesAmount"};
			List resultList = bindDataToDTO(list, new SeRcReInExpenseListBean(), fields);
			return resultList;
		} catch (Exception e) {
			throw e;
		}
	}

}
