package com.diasorin.oa.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.diasorin.oa.common.QueryResult;
import com.diasorin.oa.dto.AuthorityByListBean;
import com.diasorin.oa.dto.ClaimInfoBean;
import com.diasorin.oa.dto.SeAcApDeBean;
import com.diasorin.oa.dto.SeAcApLsBean;
import com.diasorin.oa.dto.SeAcApLsListBean;
import com.diasorin.oa.dto.SeEcEpCaBean;
import com.diasorin.oa.dto.SeEcEpLsBean;
import com.diasorin.oa.dto.SeSmEpMaListBean;
import com.diasorin.oa.model.ExpensePurposeSum;
import com.diasorin.oa.model.ExpensesApplication;
import com.diasorin.oa.model.ExpensesApproveRules;
import com.diasorin.oa.model.ExpensesDetails;
import com.diasorin.oa.model.WorkflowNodeDefination;
import com.diasorin.oa.model.WorkflowProgress;

/**
 * 报销中心模块所调用的服务
 * @author linliuan
 *
 */
public interface ExpensesManagementService {

	/**
	 * 报销列表查询(这里申请列表应该是根据当前用户去抽取)
	 * @param employeeNo
	 * @return
	 * @throws Exception
	 */
	public QueryResult<ExpensesApplication> expensesClaimListQuery(String employeeNo, int start, int end, 
			String orderBy, SeEcEpLsBean seEcEpLsBean) throws Exception;
	
	
	/**
	 * 报销审批列表查询
	 * @param start
	 * @param end
	 * @param seAcApLsBean
	 * @return
	 * @throws Exception
	 */
	public QueryResult<SeAcApLsListBean> expensesApproveListQuery(int start, int end, SeAcApLsBean seAcApLsBean, String userId) throws Exception;
	
	
	/**
	 * 报销明细列表查询
	 * @param expensesNo
	 * @return
	 * @throws Exception
	 */
	public QueryResult<ExpensesDetails> expensesClaimDetailsListQuery(String expensesNo) throws Exception;
	
	/**
	 * 报销目的列表查询
	 * @param expensesNo
	 * @return
	 * @throws Exception
	 */
	public QueryResult<ExpensePurposeSum> expensesClaimPurposeListQuery(String aplicationNo) throws Exception;
	
	/**
	 * 报销HEAD查询
	 * @param expensesNo
	 * @return
	 * @throws Exception
	 */
	public ExpensesApplication expensesClaimQuery(String expensesNo) throws Exception;
	
	/**
	 * 取得当条PURPOSE记录
	 * @param expensesNo
	 * @return
	 * @throws Exception
	 */
	public ExpensePurposeSum expensesPurposeQuery(String expensesDetailsNo) throws Exception;
	
	/**
	 * 报销申请
	 * @return
	 * @throws Exception
	 */
	public boolean expensesClaim(ClaimInfoBean claimInfoBean, boolean isSubmit, SeEcEpCaBean seEcEpCaBean,  String userId) throws Exception;
	
	/**
	 * 报销参数列表查询
	 * @return
	 * @throws Exception
	 */
	public List<SeSmEpMaListBean> expensesParameterListQuery(String expenseCode) throws Exception;
	
	/**
	 * 报销参数设定
	 * @return
	 * @throws Exception
	 */
	public boolean expensesParameterSetting(String expenseCode, List<Map<String,Object>> list) throws Exception;
	
	/**
	 * 报销上限的取得
	 * @param travelType
	 * @param levelCode
	 * @param expenseCode
	 * @return
	 * @throws Exception
	 */
	public BigDecimal getExpenseUpAllow(String travelType, String levelCode, String expenseCode) throws Exception;
	
	/**
	 * 获取报销申请No
	 * @return
	 * @throws Exception
	 */
	public String getNextExpenseAppNo() throws Exception;
	
	/**
	 * 报销驳回
	 * @param expensesApplication
	 * @return
	 * @throws Exception
	 */
	public boolean expenseReject(SeAcApDeBean seAcApDeBean, String userId, String updKey) throws Exception;
	
	/**
	 * 报销驳回(多件)
	 * @param expensesApplication
	 * @return
	 * @throws Exception
	 */
	public boolean expenseRejectAll(String expenseAppNoAll, String userId, String updKey, SeAcApLsBean seAcApLsBean) throws Exception;
	
	/**
	 * 报销审批通过
	 * @param expensesApplication
	 * @return
	 * @throws Exception
	 */
	public boolean expenseApproved(String expenseAppNo, String userId, String updKey, WorkflowNodeDefination nextNode) throws Exception;
	
	/**
	 * 报销审批通过(多件)
	 * @param expensesApplication
	 * @return
	 * @throws Exception
	 */
	public boolean expenseApprovedAll(String expenseAppNo, String userId, String updKey) throws Exception;
	
	
	/**
	 * 审批规则的抽取
	 * @param nodeId
	 * @param roleCode
	 * @return
	 * @throws Exception
	 */
	public List<ExpensesApproveRules> getExpensesApproveRules(String nodeId) throws Exception;
	
	
	/**
	 * 取得下一个节点
	 * @param currentNodeId
	 * @return
	 * @throws Exception
	 */
	public WorkflowNodeDefination getNextDefination(String currentNodeId)  throws Exception;
	
	/**
	 * 获取审批流程
	 * @param expenseAppNo
	 * @return
	 * @throws Exception
	 */
	public WorkflowProgress getWorkflowProgressInfo(String expenseAppNo) throws Exception;
	
	/**
	 * 取得当前子项目
	 * @param fatherExpenseItem
	 * @return
	 * @throws Exception
	 */
	public String getSubExpenseItem(String fatherExpenseItem) throws Exception;
	
	/**
	 * 取得所有审批或者驳回者的信息
	 * @param expenseAppNo
	 * @return
	 * @throws Exception
	 */
	public String concatAuthority(String expenseAppNo) throws Exception;
	
	/**
	 * 获取所有审批或者驳回者的信息
	 * @param expenseAppNo
	 * @return
	 * @throws Exception
	 */
	public List<AuthorityByListBean> getAuthorityByHisList(String expenseAppNo) throws Exception;
	
	/**
	 * 获取当前所拥有的审批节点
	 * @param employeeNo
	 * @return
	 * @throws Exception
	 */
	public String getNodeIdByCurrentUser(String employeeNo) throws Exception;
	
	/**
	 * 取得当前报销所处于的哪一个节点
	 * @param expenseAppNo
	 * @return
	 * @throws Exception
	 */
	public String getNowNodeIdByExpenseAppNo(String expenseAppNo) throws Exception;
	
	/**
	 * 判断当前用户是否可以审批当前报销申请
	 * @param expenseAppNo
	 * @param employeeNo
	 * @return
	 * @throws Exception
	 */
	public boolean judgeCanApprove(String expenseAppNo, String employeeNo) throws Exception;
	
	/**
	 * 得到当前用户等待去审批的记录
	 * @param expenseAppNo
	 * @param employeeNo
	 * @return
	 * @throws Exception
	 */
	public List<SeAcApLsListBean> getApproveTodoList(String userId) throws Exception;
	
	/**
	 * 得到下一个审批节点
	 * @param current
	 * @param totalAmount
	 * @param detailAllList
	 * @return
	 * @throws Exception
	 */
	public WorkflowNodeDefination getNextNodeDeFinaton(
			WorkflowNodeDefination current, BigDecimal totalAmount,
			List<ExpensesDetails> detailAllList) throws Exception;
	
	/**
	 * 判断相同的报销项目有没有
	 * @param expenseItem
	 * @param day
	 * @return
	 * @throws Exception
	 */
	public boolean isExitSameExpenseByMonth(String expenseItem, String day, String applicationNo, String claimBy) throws Exception;
	
	/**
	 * 取得所有下一个审批者的申请名字
	 * @param businessId 申请编号
	 * @return
	 */
	public String getPerCanApprove(String businessId) throws Exception;
	
	/**
	 * 报销财务撤销完成操作
	 * @param expensesApplication
	 * @return
	 * @throws Exception
	 */
	public boolean expenseUndoFinished(String expenseAppNo) throws Exception;
}
