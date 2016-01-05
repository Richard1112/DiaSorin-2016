package com.diasorin.oa.dao.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.diasorin.oa.common.CodeCommon;
import com.diasorin.oa.common.DateFormatCommon;
import com.diasorin.oa.common.QueryResult;
import com.diasorin.oa.common.TypeConvertCommon;
import com.diasorin.oa.dao.ExpenseDao;
import com.diasorin.oa.dto.AuthorityByListBean;
import com.diasorin.oa.dto.SeAcApLsBean;
import com.diasorin.oa.dto.SeAcApLsListBean;
import com.diasorin.oa.dto.SeEcEpLsBean;
import com.diasorin.oa.dto.SeSmEpMaListBean;
import com.diasorin.oa.model.ExpensePurposeSum;
import com.diasorin.oa.model.ExpensesApplication;
import com.diasorin.oa.model.ExpensesApproveRules;
import com.diasorin.oa.model.ExpensesDetails;
import com.diasorin.oa.model.ExpensesParameter;
import com.diasorin.oa.model.NoExpensesApp;
import com.diasorin.oa.model.WorkflowProgress;

@Component
public class ExpenseDaoImpl extends BaseDaoImpl implements ExpenseDao {

	@Override
	public QueryResult<ExpensesApplication> expensesClaimListQuery(String employeeNo, int start, int end, 
			String orderBy, SeEcEpLsBean seEcEpLsBean) throws Exception{
		try{
			String sql = " ";
			List<String> param = new ArrayList<String>();
			sql += " and o.employeeNo =  ? and o.deleteFlg = '0' ";
			param.add(employeeNo);
			// 出差地类型
			if (StringUtils.hasText(seEcEpLsBean.getTravelLocationType())){
				sql += " and o.travelLocalType = ?  ";
				param.add(seEcEpLsBean.getTravelLocationType());
			}
			
			// 申请日期FROM
			if (StringUtils.hasText(seEcEpLsBean.getChaimDateFrom())){
				sql += " and o.applicationDate >= ? ";
				param.add(DateFormatCommon.strToYYYYMMDDNoCa(seEcEpLsBean.getChaimDateFrom()));
			}
			
			// 申请日期TO
			if (StringUtils.hasText(seEcEpLsBean.getChaimDateTo())){
				sql += " and o.applicationDate <= ? ";
				param.add(DateFormatCommon.strToYYYYMMDDNoCa(seEcEpLsBean.getChaimDateTo()));
			}
			// 状态
			if (StringUtils.hasText(seEcEpLsBean.getStatus())){
				String arr[] = seEcEpLsBean.getStatus().split(",");
				sql += " and " + TypeConvertCommon.createWhereIn("o.status = ?" , arr.length);
				for (String str : arr){
					param.add((str));
				}	
			}
			
			
			LinkedHashMap<String, String> orderByMap = new LinkedHashMap<String, String>(); 
			orderByMap.put(orderBy, "");
			QueryResult<ExpensesApplication> qs = getScrollData(ExpensesApplication.class, 
					start, end, sql, param.toArray(), orderByMap);
			return qs;
		} catch(Exception e) {
			throw e;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<SeSmEpMaListBean> expensesParameterListQuery(String expenseCode)
			throws Exception {
		try {

			String sqlString = "select " + 
					"a.levelCode, " + 
					"a.levelName, " + 
					"b.codeDetailId as travelLocalTypeCode, " + 
					"b.codeDetailName as travelLocalType, " + 
					"IFNULL(c.allowExpensesUp,'') as allowExpensesUp " + 
					"from t_sys_employee_level  a " + 
					"inner join t_sys_code b  " + 
					"on b.codeId = '"+ CodeCommon.COM004 +"' " + 
					"left join t_expenses_parameter c "  + 
					"on  a.levelCode = c.employeeLevelCode " + 
					"and b.codeDetailId = c.travelLocal " + 
					"and c.expenseCode = ? " + 
					"order by levelCode,b.codeDetailId";
			List<String> paramsList = new ArrayList<String>();
			paramsList.add(expenseCode);
			Query query = em.createNativeQuery(sqlString);
			setQueryParams(query, paramsList.toArray());
			List list = query.getResultList();
			String[] fields = { "levelCode", "levelName", "travelLocalTypeCode",
					"travelLocalType", "allowExpensesUp" };
			List resultList = bindDataToDTO(list, new SeSmEpMaListBean(), fields);
			return resultList;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public boolean expensesParameterDeleteByExpenseCode(String expenseCode)
			throws Exception {
		try {
			String sqlString = "DELETE FROM t_expenses_parameter WHERE expenseCode = '" + expenseCode + "'" ;
			em.createNativeQuery(sqlString).executeUpdate();
			return true;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public ExpensesApplication expenseClaimQuery(String expenseNo)
			throws Exception {
		try {
			String sql = " ";
			List<String> param = new ArrayList<String>();
			
			sql += " and o.expensesAppNo = ? ";
			param.add(expenseNo);
			
			sql += " and o.deleteFlg = '0' ";
			
			QueryResult<ExpensesApplication> qs = getScrollData(ExpensesApplication.class, sql, param.toArray());
			if (qs.getResultlist().size() > 0) {
				return qs.getResultlist().get(0);
			}
			return null;
		} catch(Exception e) {
			throw e;
		}
	}

	@Override
	public QueryResult<ExpensesDetails> expenseClaimDetailQuery(String expenseNo)
			throws Exception {
		try {
			String sql = " ";
			List<String> param = new ArrayList<String>();
			
			sql += " and o.belongExpensesAppNo = ? ";
			param.add(expenseNo);
			
			sql += " and o.deleteFlg = '0' ";
			
			QueryResult<ExpensesDetails> qs = getScrollData(ExpensesDetails.class, sql, param.toArray());
			return qs;
		} catch(Exception e) {
			throw e;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public QueryResult<SeAcApLsListBean> expensesApproveListQuery(int start,
			int end, SeAcApLsBean seAcApLsBean, String userId) throws Exception {
		try{
			List<String> paramsList = new ArrayList<String>();
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT A.costCenterCode as costCenter ");
			sb.append("		 ,B.employeeNameEn as claimBy ");
			sb.append("		 ,B.employeeNo as claimNo ");
			sb.append("		 ,A.applicationDate as claimDate ");
			sb.append("		 ,A.travelLocalType as travelLocationType ");
			sb.append("		 ,CAST(A.expenseSum as char) as total ");
			sb.append("		 ,A.status as status ");
			sb.append("		 ,A.expensesAppNo as expenseNo ");
			sb.append(" FROM t_expenses_application A ");
			sb.append(" INNER JOIN t_employee_info B ");
			sb.append(" ON A.employeeNo = B.employeeNo AND B.deleteFlg = '0' ");
			
			sb.append(" INNER JOIN t_employee_info E ");//审批用户
			sb.append(" ON  E.deleteFlg = '0' AND E.employeeNo = ? ");
			paramsList.add(userId);
			
			//等待审批的记录
			sb.append(" INNER JOIN ");
			sb.append(" (select t2.nodeId AS CURNODEID, t2.businessId as BUSINESSID  from "
					+ "(SELECT MAX(no) as no from t_workflow_progress GROUP BY  businessId ) "
					+ "t1 "
					+ "INNER JOIN t_workflow_progress t2 "
					+ "ON t1.no = t2.no) "
					+ "C");
			sb.append(" ON C.BUSINESSID = A.expensesAppNo ");
			
			sb.append(" where 1= 1 AND A.status <> 0 ");
			
			// 已经审批过得记录
			sb.append(" AND ("
					+ "(A.expensesAppNo IN (SELECT businessId FROM t_workflow_progress WHERE employeeNo = ? GROUP BY businessId)) ");
			paramsList.add(userId);
			
			// 或者当前记录为当前用户申请的
			//sb.append(" OR ");
			//sb.append(" (A.employeeNo = ? ) ");
			//paramsList.add(userId);

			sb.append(" OR ");

			// 或者未审批的
			sb.append(" ( ");
			// 当前的节点在用户的权限下
			sb.append(" C.CURNODEID IN (SELECT nodeId FROM t_expenses_approve_rules WHERE approveRoleCode = E.roleCode) ");
			sb.append(" AND ");
			sb.append(" (( ");
			sb.append(" 	C.CURNODEID = 'WFND01' ");
			sb.append(" AND A.costCenterCode = E.deptCode AND A.costCenterCode <>  B.deptCode ");
			sb.append(" AND B.serviceCostCenter LIKE concat('%', E.deptCode ,'%') ");
			sb.append("  ) ");
			sb.append(" or  ");
			sb.append(" ( ");
			sb.append(" 	C.CURNODEID = 'WFND02' ");
			sb.append(" AND B.deptCode =  E.deptCode AND A.status = '2' ");
			sb.append("  ) ");
			sb.append(" or  ");
			sb.append(" ( ");
			sb.append(" 	C.CURNODEID = 'WFND02' ");
			sb.append(" AND A.costCenterCode = B.deptCode  AND A.costCenterCode =  E.deptCode AND A.status = '1' ");
			sb.append("  ) ");
			sb.append(" or  ");
			sb.append("  (C.CURNODEID <> 'WFND01' AND C.CURNODEID <> 'WFND02') ");
			sb.append(" )) ");
			
			sb.append(" ) ");
			
//			sb.append("SELECT E.deptCode as costCenter ");
//			sb.append("		 ,E.employeeNameEn as claimBy ");
//			sb.append("		 ,D.applicationDate as claimDate ");
//			sb.append("		 ,D.travelLocalType as travelLocationType ");
//			sb.append("		 ,CAST(D.expenseSum as char) as total ");
//			sb.append("		 ,D.status as status ");
//			sb.append("		 ,D.expensesAppNo as expenseNo ");
//			
//			sb.append(" FROM t_expenses_approve_rules A ");
//
//			sb.append(" inner JOIN t_workflow_progress C ");
//			sb.append(" ON A.nodeId = C.nodeId and C.deleteFlg = '0' ");
//
//			sb.append(" inner join t_expenses_application D ");
//			sb.append(" ON D.expensesAppNo = C.businessId and D.deleteFlg = '0'");
//			
//			sb.append(" INNER JOIN t_employee_info B ");
//			sb.append(" ON A.approveRoleCode = B.roleCode AND B.deleteFlg = '0' AND B.employeeNo = ? ");
//			
//			sb.append(" INNER JOIN t_employee_info E ");
//			sb.append(" ON D.employeeNo = E.employeeNo AND E.deleteFlg = '0' ");
//			
//			sb.append(" where 1= 1 ");
//			sb.append(" and( ");
//			sb.append(" ( ");
//			sb.append(" 	A.nodeId = 'WFND01' ");
//			sb.append(" AND B.deptCode <> (select deptCode from t_employee_info where employeeNo = ? ) ");
//			sb.append(" AND (select deptCode from t_employee_info where employeeNo = ? ) like concat('%', B.serviceCostCenter ,'%') ");
//			sb.append("  ) ");
//			sb.append(" or  ");
//			sb.append("  (A.nodeId <> 'WFND01') ");
//			sb.append(" ) ");
//			
//			paramsList.add(userId);
//			paramsList.add(userId);
//			paramsList.add(userId);
//	
//			// 成本中心
//			if (seAcApLsBean != null) {
//				if (StringUtils.hasText(seAcApLsBean.getCostCenter())){
//					sb.append(" AND B.deptCode = ? ");
//					paramsList.add(seAcApLsBean.getCostCenter());
//				}
//				
//				// 申请人
//				if (StringUtils.hasText(seAcApLsBean.getClaimBy())){
//					sb.append( " AND B.employeeNameEn like ? ");
//					paramsList.add("%" + seAcApLsBean.getClaimBy() + "%");
//				}
//				
//				// 申请日区间FROM
//				if (StringUtils.hasText(seAcApLsBean.getClaimDateFrom())){
//					sb.append( " AND D.applicationDate >= ? ");
//					paramsList.add(DateFormatCommon.strToYYYYMMDDNoCa(seAcApLsBean.getClaimDateFrom()));
//				}
//				
//				// 申请日区间TO
//				if (StringUtils.hasText(seAcApLsBean.getClaimDateTo())){
//					sb.append( " AND D.applicationDate <= ? ");
//					paramsList.add(DateFormatCommon.strToYYYYMMDDNoCa(seAcApLsBean.getClaimDateTo()));
//				}
//				
//				// 状态
//				if (StringUtils.hasText(seAcApLsBean.getStatus())){
//					sb.append( " AND D.status = ? ");
//					paramsList.add(seAcApLsBean.getStatus());
//					sb.append(" AND  C.progressStatus = ? " );
//					paramsList.add(seAcApLsBean.getStatus());
//				}
//			}
			// 成本中心
			if (seAcApLsBean != null) {
				if (StringUtils.hasText(seAcApLsBean.getCostCenter())){
					sb.append(" AND B.deptCode = ? ");
					paramsList.add(seAcApLsBean.getCostCenter());
				}
				
				// 申请人
				if (StringUtils.hasText(seAcApLsBean.getClaimBy())){
					sb.append( " AND B.employeeNameEn like ? ");
					paramsList.add("%" + seAcApLsBean.getClaimBy() + "%");
				}
				
				// 申请日区间FROM
				if (StringUtils.hasText(seAcApLsBean.getClaimDateFrom())){
					sb.append( " AND A.applicationDate >= ? ");
					paramsList.add(DateFormatCommon.strToYYYYMMDDNoCa(seAcApLsBean.getClaimDateFrom()));
				}
				
				// 申请日区间TO
				if (StringUtils.hasText(seAcApLsBean.getClaimDateTo())){
					sb.append( " AND A.applicationDate <= ? ");
					paramsList.add(DateFormatCommon.strToYYYYMMDDNoCa(seAcApLsBean.getClaimDateTo()));
				}
				
				// 状态
				if (StringUtils.hasText(seAcApLsBean.getStatus())){
					sb.append( " AND A.status = ? ");
					paramsList.add(seAcApLsBean.getStatus());
				}
			}
//			sb.append(" group by D.expensesAppNo ");
//			sb.append(" order by D.applicationDate desc ");
			
			Query query = em.createNativeQuery(sb.toString());
			setQueryParams(query, paramsList.toArray());
			
			QueryResult<SeAcApLsListBean> qr = new QueryResult<SeAcApLsListBean>();
			
			qr.setTotalrecord(query.getResultList().size());
			
			query.setFirstResult(start).setMaxResults(end);
			
			List list = query.getResultList();
			
			String[] fields = { "costCenter", "claimBy", "claimNo",
					"claimDate", "travelLocationType", "total", "status", "expenseNo" };
			List resultList = bindDataToDTO(list, new SeAcApLsListBean(), fields);
			qr.setResultlist(resultList);
			return qr;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public QueryResult<ExpensePurposeSum> expensesClaimPurposeListQuery(
			String aplicationNo) throws Exception {
		try {
			String sql = " ";
			List<String> param = new ArrayList<String>();
			
			sql += " and o.belongExpensesAppNo = ? ";
			param.add(aplicationNo);
			
			sql += " and o.deleteFlg = '0' ";
			
			QueryResult<ExpensePurposeSum> qs = getScrollData(ExpensePurposeSum.class, sql, param.toArray());
			return qs;
		} catch(Exception e) {
			throw e;
		}
	}

	@Override
	public ExpensePurposeSum expensesPurposeQuery(String expensesDetailsNo)
			throws Exception {
		try {
			String sql = " ";
			List<String> param = new ArrayList<String>();
			
			sql += " and o.expensesDetailsNo = ? ";
			param.add(expensesDetailsNo);
			
			sql += " and o.deleteFlg = '0' ";
			
			QueryResult<ExpensePurposeSum> qs = getScrollData(ExpensePurposeSum.class, sql, param.toArray());
			if (qs.getResultlist().size() > 0) {
				return qs.getResultlist().get(0);
			}
			return null;
		} catch(Exception e) {
			throw e;
		}
	}

	@Override
	public BigDecimal getExpenseUpAllow(String travelType, String levelCode,
			String expenseCode) throws Exception {
		try {
			String sql = " ";
			List<String> param = new ArrayList<String>();
			
			sql += " and o.travelLocal = ? ";
			param.add(travelType);
			
			sql += " and o.employeeLevelCode = ? ";
			param.add(levelCode);
			
			sql += " and o.expenseCode = ? ";
			param.add(expenseCode);
			
			
			QueryResult<ExpensesParameter> qs = getScrollData(ExpensesParameter.class, sql, param.toArray());
			if (qs.getResultlist().size() > 0) {
				return qs.getResultlist().get(0).getAllowExpensesUp();
			}
			return null;
		} catch(Exception e) {
			throw e;
		}
	}

	@Override
	public NoExpensesApp getNextExpenseAppNo() throws Exception {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
			String sql = " ";
			List<String> param = new ArrayList<String>();
			
			sql += " and o.currentYear = ? ";
			param.add(sdf.format(new java.util.Date()));
			
			QueryResult<NoExpensesApp> qs = getScrollData(NoExpensesApp.class, sql, param.toArray());
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
	public int getExpensePurposeMaxNo(String expenseAppNo) throws Exception {
		try {

			String sqlString = "select max(expensesDetailsNo) as maxNo from t_expenses_purpose_sum "
					+ "where belongExpensesAppNo = ? "; 
					
			List<String> paramsList = new ArrayList<String>();
			paramsList.add(expenseAppNo);
			Query query = em.createNativeQuery(sqlString);
			setQueryParams(query, paramsList.toArray());
			List list = query.getResultList();
			String maxNoStr = list.get(0).toString();
			int maxNo = Integer.valueOf(maxNoStr.substring(maxNoStr.lastIndexOf("-") + 1));
			return maxNo;
		} catch (Exception e) {
			throw e;
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public int getExpenseDetailMaxNo(String expensePurposeNo) throws Exception {
		try {

			String sqlString = "select max(expensesDetailsNo) as maxNo from t_expenses_details "
					+ "where belongExpensesAppNo = ? "; 
					
			List<String> paramsList = new ArrayList<String>();
			paramsList.add(expensePurposeNo);
			Query query = em.createNativeQuery(sqlString);
			setQueryParams(query, paramsList.toArray());
			List list = query.getResultList();
			String maxNoStr = list.get(0).toString();
			int maxNo = Integer.valueOf(maxNoStr.substring(maxNoStr.lastIndexOf("-") + 1));
			return maxNo;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public ExpensesDetails expensesDetailQuery(String expensesDetailsNo)
			throws Exception {
		try {
			String sql = " ";
			List<String> param = new ArrayList<String>();
			
			sql += " and o.expensesDetailsNo = ? ";
			param.add(expensesDetailsNo);
			
			sql += " and o.deleteFlg = '0' ";
			
			QueryResult<ExpensesDetails> qs = getScrollData(ExpensesDetails.class, sql, param.toArray());
			if (qs.getResultlist().size() > 0) {
				return qs.getResultlist().get(0);
			}
			return null;
		} catch(Exception e) {
			throw e;
		}
	}

	@Override
	public WorkflowProgress getWorkflowProgressInfo(String businessId)
			throws Exception {
		try {
			String sql = " ";
			List<String> param = new ArrayList<String>();
			
			sql += " and o.businessId = ? ";
			param.add(businessId);
			
			sql += " order by o.operTimestamp desc ";
			
			QueryResult<WorkflowProgress> qs = getScrollData(WorkflowProgress.class, sql, param.toArray());
			if (qs.getResultlist().size() > 0) {
				return qs.getResultlist().get(0);
			}
			return null;
		} catch(Exception e) {
			throw e;
		}
	}

	@Override
	public List<ExpensesApproveRules> getExpensesApproveRules(String nodeId) throws Exception {
		try {
			String sql = " ";
			List<String> param = new ArrayList<String>();
			
			sql += " and o.nodeId = ? ";
			param.add(nodeId);
			
			QueryResult<ExpensesApproveRules> qs = getScrollData(ExpensesApproveRules.class, sql, param.toArray());
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
	public String getSubExpenseItem(String fatherExpenseItem) throws Exception {
		try {

			String sqlString = "SELECT GROUP_CONCAT(expenseCode) FROM diasorin.t_sys_expenses where fatherExpenseCode = ? ";
					
			List<String> paramsList = new ArrayList<String>();
			paramsList.add(fatherExpenseItem);
			Query query = em.createNativeQuery(sqlString);
			setQueryParams(query, paramsList.toArray());
			List list = query.getResultList();
			
			if (list != null && list.size() > 0 && list.get(0) != null) {
				return list.get(0).toString();
			}
			return null;
			
		} catch (Exception e) {
			throw e;
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public String concatAuthority(String expenseAppNo) throws Exception {
		try {

			String sqlString = "SELECT group_concat(B.employeeNameEn) "+
					 " FROM  "+
					 " t_workflow_progress A "+
					 " left join t_employee_info B "+
					 " ON A.employeeNo = B.employeeNo "+
					 " where A.businessId = ? ";				
			List<String> paramsList = new ArrayList<String>();
			paramsList.add(expenseAppNo);
			Query query = em.createNativeQuery(sqlString);
			setQueryParams(query, paramsList.toArray());
			List list = query.getResultList();
			if (list == null || list.size() == 0 || list.get(0) == null) return "";
			return list.get(0).toString();
		} catch (Exception e) {
			throw e;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<AuthorityByListBean> getAuthorityByHisList(String expenseAppNo)
			throws Exception {
		try {

			String sqlString = "SELECT " + 
					 " B.employeeNameEn as authorityBy"+
					 " ,cast(A.operTimestamp as char) as authorityDate "+
					 " ,A.operComments as comment"+
					 " ,A.progressStatus as statusView"+
					 " FROM  "+
					 " t_workflow_progress A "+
					 " left join t_employee_info B "+
					 " ON A.employeeNo = B.employeeNo "+
					 
					 " where A.businessId = ? AND A.progressStatus <> '1' AND A.employeeNo IS NOT NULL ORDER BY A.operTimestamp";				
			List<String> paramsList = new ArrayList<String>();
			paramsList.add(expenseAppNo);
			Query query = em.createNativeQuery(sqlString);
			setQueryParams(query, paramsList.toArray());
			List list = query.getResultList();
			
			String[] fields = { "authorityBy", "authorityDate", "comment", "statusView" };
			List resultList = bindDataToDTO(list, new AuthorityByListBean(), fields);
			return resultList;

		} catch (Exception e) {
			throw e;
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public String getNodeIdByCurrentUser(String employeeNo) throws Exception {
		try {

			String sqlString = "SELECT group_concat(A.nodeId) "+
					 " FROM  "+
					 " t_expenses_approve_rules A "+
					 " left join t_employee_info B "+
					 " ON A.approveRoleCode = B.roleCode "+
					 " where B.employeeNo = ? ";				
			List<String> paramsList = new ArrayList<String>();
			paramsList.add(employeeNo);
			Query query = em.createNativeQuery(sqlString);
			setQueryParams(query, paramsList.toArray());
			List list = query.getResultList();
			if (list == null || list.size() == 0 || list.get(0) == null) return "";
			return list.get(0).toString();
		} catch (Exception e) {
			throw e;
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public String getNowNodeIdByExpenseAppNo(String expenseAppNo)
			throws Exception {
		try {

			String sqlString = "SELECT nodeId"+
					 " FROM  "+
					 " t_workflow_progress "+
					 " where businessId = ? " + 
					 " order by operTimestamp desc ";
			List<String> paramsList = new ArrayList<String>();
			paramsList.add(expenseAppNo);
			Query query = em.createNativeQuery(sqlString);
			setQueryParams(query, paramsList.toArray());
			List list = query.getResultList();
			if (list == null || list.size() == 0) return "";
			return list.get(0).toString();
		} catch (Exception e) {
			throw e;
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean judgeCanApprove(String expenseAppNo, String employeeNo)
			throws Exception {
		try{
			List<String> paramsList = new ArrayList<String>();
			StringBuffer sb = new StringBuffer();
			
			sb.append("SELECT C.CURNODEID ");
			
			sb.append(" FROM t_expenses_approve_rules A ");
			sb.append(" INNER JOIN t_employee_info B ");//审批用户
			sb.append(" ON A.approveRoleCode = B.roleCode AND B.deleteFlg = '0' AND B.employeeNo = ? ");
			paramsList.add(employeeNo);
			sb.append(" inner JOIN ");
			sb.append(" (select t2.no, t2.nodeId AS CURNODEID from "
					+ "(SELECT MAX(no) as no from t_workflow_progress WHERE businessId = ? ) "
					+ "t1 "
					+ "INNER JOIN t_workflow_progress t2 "
					+ "ON t1.no = t2.no) "
					+ "C");
			sb.append(" ON A.nodeId = C.CURNODEID ");
			paramsList.add(expenseAppNo);

			sb.append(" inner join t_expenses_application D ");//报销的申请
			sb.append(" ON D.expensesAppNo = ? and D.deleteFlg = '0'");
			paramsList.add(expenseAppNo);
			
			sb.append(" INNER JOIN t_employee_info E ");//申请用户
			sb.append(" ON D.employeeNo = E.employeeNo AND E.deleteFlg = '0' ");
			
			sb.append(" where 1= 1 ");
			sb.append(" and( ");
			sb.append(" ( ");
			sb.append(" 	C.CURNODEID = 'WFND01' ");
			sb.append(" AND D.costCenterCode = B.deptCode AND D.costCenterCode <>  E.deptCode ");
			sb.append(" AND E.serviceCostCenter LIKE concat('%', B.deptCode ,'%') ");
			sb.append("  ) ");
			sb.append(" or  ");
			sb.append(" ( ");
			sb.append(" 	C.CURNODEID = 'WFND02' ");
			sb.append(" AND B.deptCode =  E.deptCode AND D.status = '2' ");
			sb.append("  ) ");
			sb.append(" or  ");
			sb.append(" ( ");
			sb.append(" 	C.CURNODEID = 'WFND02' ");
			sb.append(" AND D.costCenterCode = B.deptCode  AND D.costCenterCode =  E.deptCode AND D.status = '1' ");
			sb.append("  ) ");
			sb.append(" or  ");
			sb.append("  (C.CURNODEID <> 'WFND01' AND C.CURNODEID <> 'WFND02') ");
			sb.append(" ) ");
			sb.append(" AND D.expensesAppNo = ? ");
			paramsList.add(expenseAppNo);
			

			Query query = em.createNativeQuery(sb.toString());
			setQueryParams(query, paramsList.toArray());
					
			List list = query.getResultList();
			
			if (list == null || list.size() == 0 || list.get(0) == null) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			throw e;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<SeAcApLsListBean> getApproveTodoList(String userId)
			throws Exception {
		try{
			List<String> paramsList = new ArrayList<String>();
			StringBuffer sb = new StringBuffer();
					
			sb.append(" select ");
			sb.append(" C.businessId as expenseNo, E.employeeNameEn as claimBy, D.status as status ");
			sb.append(" FROM t_expenses_approve_rules A ");
			sb.append(" INNER JOIN t_employee_info B ");
			sb.append(" ON A.approveRoleCode = B.roleCode AND B.deleteFlg = '0' AND B.employeeNo = ?  ");
			paramsList.add(userId);
			sb.append(" INNER JOIN ("
					+ "select t2.no, t2.nodeId AS CURNODEID, t2.businessId AS businessId from "
					+ "(SELECT MAX(no) as no from t_workflow_progress group by businessId) t1 "
					+ "INNER JOIN t_workflow_progress t2 "
					+ "ON t1.no = t2.no) "
					+ "C ");
			sb.append(" ON A.nodeId = C.CURNODEID ");

			sb.append(" INNER JOIN t_expenses_application D  ");
			sb.append(" ON D.expensesAppNo = C.businessId AND D.deleteFlg = '0' ");

			sb.append(" INNER JOIN t_employee_info E ");
			sb.append(" ON D.employeeNo = E.employeeNo AND E.deleteFlg = '0' ");

			sb.append(" WHERE 1 = 1  AND ( D.status <> 5 AND D.status <> 3 ) ");
			sb.append(" 	AND  ");
			sb.append(" 	( ");
			sb.append(" 	(A.nodeId = 'WFND01'  ");
			sb.append(" 	AND D.costCenterCode = B.deptCode AND D.costCenterCode <>  E.deptCode  ");
			sb.append(" 	AND E.serviceCostCenter LIKE concat('%', B.deptCode ,'%')) ");
			sb.append(" 	OR  ");
			sb.append(" 	(  ");
			sb.append(" 	A.nodeId = 'WFND02'  ");
			sb.append(" 	AND B.deptCode =  E.deptCode AND D.status = '2' ");
			sb.append(" 	)  ");
			sb.append(" 	or  ");
			sb.append(" 	(  ");
			sb.append(" 	A.nodeId = 'WFND02'  ");
			sb.append(" 	AND D.costCenterCode = B.deptCode  AND D.costCenterCode =  E.deptCode AND D.status = '1' ");
			sb.append(" 	)  ");
			sb.append(" 	or ");
			sb.append(" 	(  ");
			sb.append(" 	A.nodeId <> 'WFND01' AND A.nodeId <> 'WFND02' ");
			sb.append(" 	)  ");
			sb.append(" ) ");
			sb.append(" GROUP BY C.businessId  ");

			Query query = em.createNativeQuery(sb.toString());
			setQueryParams(query, paramsList.toArray());
			List list = query.getResultList();
			
			String[] fields = { "expenseNo", "claimBy", "status" };
			List resultList = bindDataToDTO(list, new SeAcApLsListBean(), fields);
			return resultList;
		} catch (Exception e) {
			throw e;
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public String getMaxNodeCurrentUser(String role, boolean isAccountant) throws Exception {
		try {

			String sqlString = "SELECT max(nodeId) "+
					 " FROM  "+
					 " t_expenses_approve_rules "+
					 " where approveRoleCode = ? ";
			if (isAccountant) {
				// 是财务
				sqlString += " and (nodeId = 'WFND06' or nodeId = 'WFND07') ";
			} else {
				// 不是财务
				sqlString += " and nodeId <> 'WFND06' and nodeId <> 'WFND07'";
			}
			List<String> paramsList = new ArrayList<String>();
			paramsList.add(role);
			Query query = em.createNativeQuery(sqlString);
			setQueryParams(query, paramsList.toArray());
			List list = query.getResultList();
			if (list == null || list.size() == 0 || list.get(0) == null) return "";
			return list.get(0).toString();
		} catch (Exception e) {
			throw e;
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean isExitSameExpenseByMonth(String expenseItem, String day,
			String applicationNo, String claimBy) throws Exception {
		try {
			String sql = "SELECT  belongExpensesAppNo FROM t_expenses_details o where 1 = 1 ";
			List<String> param = new ArrayList<String>();
			
			sql += " and o.expensesItem = ? ";
			param.add(expenseItem);
			
			sql += " and o.expensesDateEnd = ? ";
			param.add(day.replaceAll("/", ""));
			
			sql += " and o.deleteFlg = '0' ";
			
			if (StringUtils.hasText(applicationNo)) {
				sql += " and left(o.belongExpensesAppNo, 10) <> ? ";
				param.add(applicationNo);
			}
			
			sql += " and o.addUserKey = ? ";
			param.add(claimBy);
			
			Query query = em.createNativeQuery(sql);
			setQueryParams(query, param.toArray());
			List list = query.getResultList();
			
			if (list.size() > 0) {
				return true;
			}
			return false;
		} catch(Exception e) {
			throw e;
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public String getPerCanApprove(String businessId) throws Exception {
		try{
			List<String> paramsList = new ArrayList<String>();
			StringBuffer sb = new StringBuffer();
			sb.append("select ");
			sb.append(" group_concat(employeeNameEn) ");
			sb.append(" from ");
			sb.append("(");
			sb.append(" SELECT ");
			sb.append(" B.employeeNo,MAX(B.employeeNameEn) AS employeeNameEn ");
			sb.append(" FROM t_expenses_approve_rules A ");
			sb.append(" INNER JOIN t_employee_info B ");//审批用户
			sb.append(" ON A.approveRoleCode = B.roleCode AND B.deleteFlg = '0' ");
			sb.append(" inner JOIN ");
			sb.append(" (select t2.no, t2.nodeId AS CURNODEID from "
					+ "(SELECT MAX(no) as no from t_workflow_progress WHERE businessId = ? ) "
					+ "t1 "
					+ "INNER JOIN t_workflow_progress t2 "
					+ "ON t1.no = t2.no) "
					+ "C");
			sb.append(" ON A.nodeId = C.CURNODEID ");
			paramsList.add(businessId);

			sb.append(" inner join t_expenses_application D ");//报销的申请
			sb.append(" ON D.expensesAppNo = ? and D.deleteFlg = '0'");
			paramsList.add(businessId);
			
			sb.append(" INNER JOIN t_employee_info E ");//申请用户
			sb.append(" ON D.employeeNo = E.employeeNo AND E.deleteFlg = '0' ");
			
			sb.append(" where 1= 1 ");
			sb.append(" and( ");
			sb.append(" ( ");
			sb.append(" 	C.CURNODEID = 'WFND01' ");
			sb.append(" AND D.costCenterCode = B.deptCode AND D.costCenterCode <>  E.deptCode ");
			sb.append(" AND E.serviceCostCenter LIKE concat('%', B.deptCode ,'%') ");
			sb.append("  ) ");
			sb.append(" or  ");
			sb.append(" ( ");
			sb.append(" 	C.CURNODEID = 'WFND02' ");
			sb.append(" AND B.deptCode =  E.deptCode AND D.status = '2' ");
			sb.append("  ) ");
			sb.append(" or  ");
			sb.append(" ( ");
			sb.append(" 	C.CURNODEID = 'WFND02' ");
			sb.append(" AND D.costCenterCode = B.deptCode  AND D.costCenterCode =  E.deptCode AND D.status = '1' ");
			sb.append("  ) ");
			sb.append(" or  ");
			sb.append("  (C.CURNODEID <> 'WFND01' AND C.CURNODEID <> 'WFND02') ");
			sb.append(" ) ");
			sb.append(" AND D.expensesAppNo = ? ");
			
			sb.append(" group by B.employeeNo");
			sb.append(" ) T1");
			paramsList.add(businessId);
			
			Query query = em.createNativeQuery(sb.toString());
			setQueryParams(query, paramsList.toArray());
					
			List list = query.getResultList();
			if (list == null || list.size() == 0 || list.get(0) == null) return "";
			return list.get(0).toString();
		} catch (Exception e) {
			throw e;
		}
	}

}
