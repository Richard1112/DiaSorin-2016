package com.diasorin.oa.dao;

import java.math.BigDecimal;
import java.util.List;

import com.diasorin.oa.common.QueryResult;
import com.diasorin.oa.dto.AuthorityByListBean;
import com.diasorin.oa.dto.SeAcApLsBean;
import com.diasorin.oa.dto.SeAcApLsListBean;
import com.diasorin.oa.dto.SeEcEpLsBean;
import com.diasorin.oa.dto.SeSmEpMaListBean;
import com.diasorin.oa.model.ExpensePurposeSum;
import com.diasorin.oa.model.ExpensesApplication;
import com.diasorin.oa.model.ExpensesApproveRules;
import com.diasorin.oa.model.ExpensesDetails;
import com.diasorin.oa.model.NoExpensesApp;
import com.diasorin.oa.model.WorkflowProgress;

public interface ExpenseDao {

	// 报销列表取得
	public QueryResult<ExpensesApplication> expensesClaimListQuery(String employeeNo, int start, int end, 
			String orderBy, SeEcEpLsBean seEcEpLsBean) throws Exception;
	
	// 报销审批列表查询
	public QueryResult<SeAcApLsListBean> expensesApproveListQuery(int start, int end, SeAcApLsBean seAcApLsBean, String userId) throws Exception;
	
	// 报销设定参数取得
	public List<SeSmEpMaListBean> expensesParameterListQuery(String expenseCode) throws Exception;
	
	// 将制定报销项目下地数据全部删去
	public boolean expensesParameterDeleteByExpenseCode(String expenseCode) throws Exception;
	
	// 报销申请项目的取得
	public ExpensesApplication expenseClaimQuery(String expenseNo) throws Exception;
	
	// 报销申请目的的取得
	public ExpensePurposeSum expensesPurposeQuery(String expensesDetailsNo) throws Exception;
	
	// 报销申请目的的取得
	public ExpensesDetails expensesDetailQuery(String expensesDetailsNo) throws Exception;
	
	// 报销明细项目取得
	public QueryResult<ExpensesDetails> expenseClaimDetailQuery(String expenseNo) throws Exception;
	
	// 报销目的查询
	public QueryResult<ExpensePurposeSum> expensesClaimPurposeListQuery(String aplicationNo) throws Exception;
	
	// 报销项目上限的取得
	public BigDecimal getExpenseUpAllow(String travelType, String levelCode,String expenseCode) throws Exception;
	
	public NoExpensesApp getNextExpenseAppNo() throws Exception;
	
	// 取得报销申请目的最大的COUNT
	public int getExpensePurposeMaxNo(String expenseAppNo) throws Exception;
	
	// 取得报销申请目的最大的COUNT
	public int getExpenseDetailMaxNo(String expensePurposeNo) throws Exception;
	
	// 取得流程过程信息
	public WorkflowProgress getWorkflowProgressInfo(String businessId) throws Exception;
	
	// 审批规则的抽取
	public List<ExpensesApproveRules> getExpensesApproveRules(String nodeId) throws Exception;
	
	// 取得当前子项目
	public String getSubExpenseItem(String fatherExpenseItem) throws Exception; 
	
	// 取得所有审批或者驳回者的信息
	public String concatAuthority(String expenseAppNo) throws Exception;
	
	// 取得所有审批或者驳回者的信息
	public List<AuthorityByListBean> getAuthorityByHisList(String expenseAppNo) throws Exception;
	
	// 获取当前所拥有的审批节点
	public String getNodeIdByCurrentUser(String employeeNo) throws Exception;
	
	// 取得当前报销所处于的哪一个节点
	public String getNowNodeIdByExpenseAppNo(String expenseAppNo) throws Exception;
	
	// 判断当前用户是否可以审批当前报销申请
	public boolean judgeCanApprove(String expenseAppNo, String employeeNo) throws Exception;
	
	// 得到当前用户等待去审批的记录
	public List<SeAcApLsListBean> getApproveTodoList(String userId) throws Exception;
	
	// 得到当前用户权限下最大的审批节点
	public String getMaxNodeCurrentUser(String role, boolean isAccountant)  throws Exception;
	
	// 判断相同的报销项目有没有
	public boolean isExitSameExpenseByMonth(String expenseItem, String day,String applicationNo, String claimBy) throws Exception;
	
	// 取得所有下一个审批者的申请名字
	public String getPerCanApprove(String businessId) throws Exception;
}
