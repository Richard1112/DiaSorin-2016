package com.diasorin.oa.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.diasorin.oa.common.CodeCommon;
import com.diasorin.oa.common.DateFormatCommon;
import com.diasorin.oa.common.ErrCommon;
import com.diasorin.oa.common.QueryResult;
import com.diasorin.oa.dao.BaseDao;
import com.diasorin.oa.dao.ExpenseDao;
import com.diasorin.oa.dto.AuthorityByListBean;
import com.diasorin.oa.dto.ClaimInfoBean;
import com.diasorin.oa.dto.SeAcApDeBean;
import com.diasorin.oa.dto.SeAcApLsBean;
import com.diasorin.oa.dto.SeAcApLsListBean;
import com.diasorin.oa.dto.SeEcEpCaBean;
import com.diasorin.oa.dto.SeEcEpDeBean;
import com.diasorin.oa.dto.SeEcEpDeListBean;
import com.diasorin.oa.dto.SeEcEpLsBean;
import com.diasorin.oa.dto.SeSmEpMaListBean;
import com.diasorin.oa.model.EmployeeInfo;
import com.diasorin.oa.model.ExpensePurposeSum;
import com.diasorin.oa.model.ExpensesApplication;
import com.diasorin.oa.model.ExpensesApproveRules;
import com.diasorin.oa.model.ExpensesDetails;
import com.diasorin.oa.model.ExpensesParameter;
import com.diasorin.oa.model.NoExpensesApp;
import com.diasorin.oa.model.SysExpenses;
import com.diasorin.oa.model.WorkflowNodeDefination;
import com.diasorin.oa.model.WorkflowProgress;
import com.diasorin.oa.service.EmployeeManagementService;
import com.diasorin.oa.service.ExpensesManagementService;
import com.diasorin.oa.service.SystemManagementService;

@Service("expensesManagementService")
public class ExpensesManagementServiceImpl extends BaseServiceImpl implements
	ExpensesManagementService {

	@Resource
	ExpenseDao expenseDaoImpl;
	
	@Resource
	BaseDao baseDaoImpl;
	
	@Resource
	EmployeeManagementService employeeManagementService;
	
	@Resource
	SystemManagementService systemManagementService;
	
	
	@Override
	public QueryResult<ExpensesApplication> expensesClaimListQuery(String employeeNo, int start, int end, 
				String orderBy, SeEcEpLsBean seEcEpLsBean) throws Exception{
		return expenseDaoImpl.expensesClaimListQuery(employeeNo, start, end, orderBy, seEcEpLsBean);
	}

	@Override
	public QueryResult<ExpensesDetails> expensesClaimDetailsListQuery(String expensesNo)
			throws Exception {
		return expenseDaoImpl.expenseClaimDetailQuery(expensesNo);
	}

	@Override
	public boolean expensesClaim(ClaimInfoBean claimInfoBean, boolean isSubmit, SeEcEpCaBean seEcEpCaBean, String userId) throws Exception {
		String expenseAppNo = seEcEpCaBean.getExpensesAppNo();
		//判断是不是新增数据
		List<ExpensesDetails> detailAllListToApprove = new ArrayList<ExpensesDetails>();
		if (StringUtils.isEmpty(expenseAppNo)) {
			// 纯粹的新规
			// 取得申请No
			expenseAppNo = this.getNextExpenseAppNo();
			int purposeCountNo = 0;
			BigDecimal totalAmount = BigDecimal.ZERO;
			for (SeEcEpDeBean seEcEpDeBean : claimInfoBean.getBeanList()) {
				// 将PURPOSE保存入数据库
				ExpensePurposeSum expensePurposeSum = new ExpensePurposeSum();
				String expensePurposeNo = expenseAppNo + "-" + this.getNextSubNo(purposeCountNo);
				purposeCountNo++;
				expensePurposeSum.setExpensesDetailsNo(expensePurposeNo);
				expensePurposeSum.setBelongExpensesAppNo(expenseAppNo);
				expensePurposeSum.setExpensesPurpose(seEcEpDeBean.getPurpose());
				expensePurposeSum.setExpensesAmount(seEcEpDeBean.getAmount());
				expensePurposeSum.setDeleteFlg("0");
				expensePurposeSum.setAddTimestamp(this.getCurrTimeStamp());
				expensePurposeSum.setAddUserKey(userId);
				baseDaoImpl.save(expensePurposeSum);
				totalAmount = totalAmount.add(seEcEpDeBean.getAmount());
				// 将明细项目放入数据库中
				int detailCountNo = 0;
				for (SeEcEpDeListBean seEcEpDeListBean : seEcEpDeBean.getBeanList()) {
					ExpensesDetails expensesDetails = new ExpensesDetails();
					expensesDetails.setExpensesDetailsNo(expensePurposeNo + "-" + this.getNextSubNo(detailCountNo));
					detailCountNo++;
					expensesDetails.setBelongExpensesAppNo(expensePurposeNo);
					expensesDetails.setExpensesDateType(seEcEpDeListBean.getTimeMethod());
					expensesDetails.setExpensesDate(DateFormatCommon.strToYYYYMMDDNoCa(seEcEpDeListBean.getDayFrom()));
					expensesDetails.setExpensesDateEnd(DateFormatCommon.strToYYYYMMDDNoCa(seEcEpDeListBean.getDayTo()));
					expensesDetails.setTravelLocation(seEcEpDeListBean.getLocation());
					expensesDetails.setExpensesItem(seEcEpDeListBean.getExpenseType());
					expensesDetails.setExpensesAmount(new BigDecimal(seEcEpDeListBean.getExpenseAmount().replaceAll(",", "")));
					if (CodeCommon.PRIVATE_CAR_FOR_BUSINESS.equals(expensesDetails.getExpensesItem())) {
						SysExpenses se = super.getS025Rate();
						String meth = se.getComputeMethod();
						BigDecimal rate = BigDecimal.ZERO;
						if (meth.endsWith("1")) {
							rate = new BigDecimal(se.getExtendsFieldCo1());
						} else if(meth.endsWith("2")) {
							rate = new BigDecimal(se.getExtendsFieldCo2());
						} else {
							rate = new BigDecimal(se.getExtendsFieldCo3());
						}
						expensesDetails.setKilometers(
								expensesDetails.getExpensesAmount().
								divide(rate, 2, BigDecimal.ROUND_UP));
					}
					expensesDetails.setExpensesComments(seEcEpDeListBean.getComments());
					expensesDetails.setDeleteFlg(CodeCommon.NOT_DELETE_FLG);
					expensesDetails.setAddTimestamp(this.getCurrTimeStamp());
					expensesDetails.setAddUserKey(userId);
					// 将所有数据保存在LIST中。判断需要下一个节点审批用
					detailAllListToApprove.add(expensesDetails);
					baseDaoImpl.save(expensesDetails);
				}
			}
			// 将申请总记录放入申请记录中
			ExpensesApplication expensesApplication = new ExpensesApplication();
			expensesApplication.setExpensesAppNo(expenseAppNo);
			expensesApplication.setEmployeeNo(userId);
			expensesApplication.setApplicationDate(this.getCurrDate());
			expensesApplication.setCostCenterCode(seEcEpCaBean.getCostCenter());
			expensesApplication.setTravelLocalType(seEcEpCaBean.getTravelLocalType());
			expensesApplication.setTravelReason(seEcEpCaBean.getTravelReason());
			expensesApplication.setTravelAppNo(StringUtils.EMPTY);
			expensesApplication.setExpenseSum(totalAmount);
			expensesApplication.setStatus(isSubmit ? CodeCommon.CLAIM_APPLICATION :CodeCommon.CLAIM_SAVE);
			expensesApplication.setDeleteFlg(CodeCommon.NOT_DELETE_FLG);
			expensesApplication.setAddTimestamp(this.getCurrTimeStamp());
			expensesApplication.setAddUserKey(userId);
			expensesApplication.setTravelDateStart(DateFormatCommon.strToYYYYMMDDNoCa(seEcEpCaBean.getClaimDateFrom()));
			expensesApplication.setTravelDateEnd(DateFormatCommon.strToYYYYMMDDNoCa(seEcEpCaBean.getClaimDateTo()));
			baseDaoImpl.save(expensesApplication);
			
			// 如果是提交申请
			if (isSubmit) {
				// 先抽取当前人员的信息
				EmployeeInfo employeeInfo = employeeManagementService.employeeInfoView(userId);
				// 这里先判断当前的角色是否是财务
				if (CodeCommon.FINANCIAL_PERSON.equals(employeeInfo.getRoleCode())) {
					// 是财务
					WorkflowProgress workflowProgress = new WorkflowProgress();
					workflowProgress.setNodeId(CodeCommon.WFND06);
					workflowProgress.setProgressStatus(CodeCommon.CLAIM_APPLICATION);
					workflowProgress.setOperTimestamp(this.getCurrTimeStamp());
					workflowProgress.setOperComments("");
					workflowProgress.setBusinessId(expenseAppNo);
					workflowProgress.setBusinessCategory(CodeCommon.WFCG02);
					workflowProgress.setDeleteFlg(CodeCommon.NOT_DELETE_FLG);
					workflowProgress.setAddTimestamp(this.getCurrTimeStamp());
					workflowProgress.setAddUserKey(userId);
					baseDaoImpl.save(workflowProgress);
				} else {
					// 不是财务
					// 抽取当前人员是不是拥有审批权限的人
					String maxNodeForSelf = expenseDaoImpl.getMaxNodeCurrentUser(employeeInfo.getRoleCode(), false);
					if (StringUtils.isEmpty(maxNodeForSelf)) {
						// 是空说明自己不是审批人员
						String nodeId = "";
						if (employeeInfo.getDeptCode().equals(seEcEpCaBean.getCostCenter())) {
							// 自己的部门
							nodeId = CodeCommon.WFND02;
						} else {
							// 别的部门经理审批
							nodeId = CodeCommon.WFND01;
						}
						WorkflowProgress workflowProgress = new WorkflowProgress();
						workflowProgress.setNodeId(nodeId);
						workflowProgress.setProgressStatus(CodeCommon.CLAIM_APPLICATION);
						workflowProgress.setOperTimestamp(this.getCurrTimeStamp());
						workflowProgress.setOperComments("");
						workflowProgress.setBusinessId(expenseAppNo);
						workflowProgress.setBusinessCategory(CodeCommon.WFCG01);
						workflowProgress.setDeleteFlg(CodeCommon.NOT_DELETE_FLG);
						workflowProgress.setAddTimestamp(this.getCurrTimeStamp());
						workflowProgress.setAddUserKey(userId);
						baseDaoImpl.save(workflowProgress);
					} else {
						// 自己是审批人员
						// 获取下一个节点
						WorkflowNodeDefination nextNode = this.getNextDefination(maxNodeForSelf);
						
						WorkflowNodeDefination nextNodeParam = null;
						if (nextNode != null) {
							if (CodeCommon.WFND04.equals(nextNode.getNodeId())) {
								// 如果下面是VP说明是CM去申请的那直接创建记录
								nextNodeParam = nextNode;
							} else {
								// 去判断是不是符合要求并返回
								nextNodeParam = this.getNextNodeDeFinaton(nextNode, expensesApplication.getExpenseSum(), detailAllListToApprove);
							}
						}
						WorkflowProgress workflowProgress = new WorkflowProgress();
						workflowProgress.setNodeId(nextNodeParam.getNodeId());
						workflowProgress.setProgressStatus(CodeCommon.CLAIM_APPLICATION);
						workflowProgress.setOperTimestamp(this.getCurrTimeStamp());
						workflowProgress.setOperComments("");
						workflowProgress.setBusinessId(expenseAppNo);
						workflowProgress.setBusinessCategory(CodeCommon.WFCG01);
						workflowProgress.setDeleteFlg(CodeCommon.NOT_DELETE_FLG);
						workflowProgress.setAddTimestamp(this.getCurrTimeStamp());
						workflowProgress.setAddUserKey(userId);
						baseDaoImpl.save(workflowProgress);
					}
				}

			}
			
		} else {
			// 更新操作
			// 先做删除操作
			// 检索当前数据库中原有的目的数据
			List<ExpensePurposeSum> beforPurposeList = expenseDaoImpl.expensesClaimPurposeListQuery(expenseAppNo).getResultlist();

			// 先将该删除的数据删除
			for (ExpensePurposeSum before : beforPurposeList) {
				boolean hasExit = false;
				for (SeEcEpDeBean seEcEpDeBean : claimInfoBean.getBeanList()) {
					if (before.getExpensesDetailsNo().equals(seEcEpDeBean.getPurposeNo())) {
						hasExit = true;
						break;
					}
				}
				if (!hasExit) {
					// 将当前的数据删除
					before.setDeleteFlg(CodeCommon.IS_DELETE_FLG);
					baseDaoImpl.update(before);
					// 将其明细项目的数据也删除
					List<ExpensesDetails> beforDetailList = expenseDaoImpl.expenseClaimDetailQuery(before.getExpensesDetailsNo()).getResultlist();
					for (ExpensesDetails beforeDetail : beforDetailList) {
						// 将当前的数据删除
						beforeDetail.setDeleteFlg(CodeCommon.IS_DELETE_FLG);
						baseDaoImpl.update(beforeDetail);
					}
				}
			}
			int purposeCountNo = expenseDaoImpl.getExpensePurposeMaxNo(expenseAppNo);
			BigDecimal totalAmount = BigDecimal.ZERO;
			
			// 删除完数据之后做保存和更新操作
			for (SeEcEpDeBean seEcEpDeBean : claimInfoBean.getBeanList()) {
				if (seEcEpDeBean.getPurposeNo().contains("-")) {
					// 说明数据是更新操作，将以前的数据抽出
					ExpensePurposeSum expensePurposeSum = expenseDaoImpl.expensesPurposeQuery(seEcEpDeBean.getPurposeNo());
					expensePurposeSum.setExpensesPurpose(seEcEpDeBean.getPurpose());
					expensePurposeSum.setExpensesAmount(seEcEpDeBean.getAmount());
					expensePurposeSum.setDeleteFlg("0");
					expensePurposeSum.setUpdTimestamp(this.getCurrTimeStamp());
					expensePurposeSum.setUpdUserKey(userId);
					expensePurposeSum.setUpdPgmId("SE_EC_EP_CA");
					baseDaoImpl.update(expensePurposeSum);
					totalAmount = totalAmount.add(seEcEpDeBean.getAmount());
					// 现在的明细数据
					List<SeEcEpDeListBean> nowdetailList = seEcEpDeBean.getBeanList();
					// 获取之前的明细数据
					List<ExpensesDetails> beforeDetailList = expenseDaoImpl.expenseClaimDetailQuery(seEcEpDeBean.getPurposeNo()).getResultlist();
					// 先将数据删除
					for (ExpensesDetails beforeDetail : beforeDetailList) {
						boolean hasExit = false;
						for (SeEcEpDeListBean nowDetail : nowdetailList) {
							if (beforeDetail.getExpensesDetailsNo().equals(nowDetail.getNo())) {
								hasExit = true;
								break;
							}
						}
						if (!hasExit) {
							// 将数据删除
							beforeDetail.setDeleteFlg(CodeCommon.IS_DELETE_FLG);
							baseDaoImpl.update(beforeDetail);
						}
					}
					
					int detailCountNo = expenseDaoImpl.getExpenseDetailMaxNo(seEcEpDeBean.getPurposeNo());
					// 开始做更新和保存操作
					for (SeEcEpDeListBean detail : nowdetailList) {
						if (detail.getNo().contains("-")) {
							// 更新操作
							ExpensesDetails expensesDetails = expenseDaoImpl.expensesDetailQuery(detail.getNo());
							expensesDetails.setExpensesDateType(detail.getTimeMethod());
							expensesDetails.setExpensesDate(DateFormatCommon.strToYYYYMMDDNoCa(detail.getDayFrom()));
							expensesDetails.setExpensesDateEnd(DateFormatCommon.strToYYYYMMDDNoCa(detail.getDayTo()));
							expensesDetails.setTravelLocation(detail.getLocation());
							expensesDetails.setExpensesItem(detail.getExpenseType());
							expensesDetails.setExpensesAmount(new BigDecimal(detail.getExpenseAmount().replaceAll(",", "")));
							if (CodeCommon.PRIVATE_CAR_FOR_BUSINESS.equals(expensesDetails.getExpensesItem())) {
								SysExpenses se = super.getS025Rate();
								String meth = se.getComputeMethod();
								BigDecimal rate = BigDecimal.ZERO;
								if (meth.endsWith("1")) {
									rate = new BigDecimal(se.getExtendsFieldCo1());
								} else if(meth.endsWith("2")) {
									rate = new BigDecimal(se.getExtendsFieldCo2());
								} else {
									rate = new BigDecimal(se.getExtendsFieldCo3());
								}
								expensesDetails.setKilometers(
										expensesDetails.getExpensesAmount().
										divide(rate, 2, BigDecimal.ROUND_UP));
							}
							expensesDetails.setExpensesComments(detail.getComments());
							expensesDetails.setDeleteFlg(CodeCommon.NOT_DELETE_FLG);
							expensesDetails.setUpdTimestamp(this.getCurrTimeStamp());
							expensesDetails.setUpdUserKey(userId);
							expensesDetails.setUpdPgmId("SE_EC_EP_CA");
							expensesDetails.setRejectErrorFlg(StringUtils.EMPTY);
							// 将所有数据保存在LIST中。判断需要下一个节点审批用
							detailAllListToApprove.add(expensesDetails);
							baseDaoImpl.update(expensesDetails);
						} else {
							// 新增操作
							ExpensesDetails expensesDetails = new ExpensesDetails();
							expensesDetails.setExpensesDetailsNo(seEcEpDeBean.getPurposeNo()+ "-" + this.getNextSubNo(detailCountNo));
							detailCountNo++;
							expensesDetails.setBelongExpensesAppNo(seEcEpDeBean.getPurposeNo());
							expensesDetails.setExpensesDateType(detail.getTimeMethod());
							expensesDetails.setExpensesDate(DateFormatCommon.strToYYYYMMDDNoCa(detail.getDayFrom()));
							expensesDetails.setExpensesDateEnd(DateFormatCommon.strToYYYYMMDDNoCa(detail.getDayTo()));
							expensesDetails.setTravelLocation(detail.getLocation());
							expensesDetails.setExpensesItem(detail.getExpenseType());
							expensesDetails.setExpensesAmount(new BigDecimal(detail.getExpenseAmount().replaceAll(",", "")));
							if (CodeCommon.PRIVATE_CAR_FOR_BUSINESS.equals(expensesDetails.getExpensesItem())) {
								SysExpenses se = super.getS025Rate();
								String meth = se.getComputeMethod();
								BigDecimal rate = BigDecimal.ZERO;
								if (meth.endsWith("1")) {
									rate = new BigDecimal(se.getExtendsFieldCo1());
								} else if(meth.endsWith("2")) {
									rate = new BigDecimal(se.getExtendsFieldCo2());
								} else {
									rate = new BigDecimal(se.getExtendsFieldCo3());
								}
								expensesDetails.setKilometers(
										expensesDetails.getExpensesAmount().
										divide(rate, 2, BigDecimal.ROUND_UP));
							}
							expensesDetails.setExpensesComments(detail.getComments());
							expensesDetails.setDeleteFlg(CodeCommon.NOT_DELETE_FLG);
							expensesDetails.setAddTimestamp(this.getCurrTimeStamp());
							expensesDetails.setAddUserKey(userId);
							// 将所有数据保存在LIST中。判断需要下一个节点审批用
							detailAllListToApprove.add(expensesDetails);
							baseDaoImpl.save(expensesDetails);
						}
					}
					
					
				} else {
					// 保存操作
					ExpensePurposeSum expensePurposeSum = new ExpensePurposeSum();
					String expensePurposeNo = expenseAppNo + "-" + this.getNextSubNo(purposeCountNo);
					purposeCountNo++;
					expensePurposeSum.setExpensesDetailsNo(expensePurposeNo);
					expensePurposeSum.setBelongExpensesAppNo(expenseAppNo);
					expensePurposeSum.setExpensesPurpose(seEcEpDeBean.getPurpose());
					expensePurposeSum.setExpensesAmount(seEcEpDeBean.getAmount());
					expensePurposeSum.setDeleteFlg("0");
					expensePurposeSum.setAddTimestamp(this.getCurrTimeStamp());
					expensePurposeSum.setAddUserKey(userId);
					baseDaoImpl.save(expensePurposeSum);
					totalAmount = totalAmount.add(seEcEpDeBean.getAmount());
					// 将明细项目放入数据库中
					int detailCountNo = 0;
					for (SeEcEpDeListBean seEcEpDeListBean : seEcEpDeBean.getBeanList()) {
						ExpensesDetails expensesDetails = new ExpensesDetails();
						expensesDetails.setExpensesDetailsNo(expensePurposeNo + "-" + this.getNextSubNo(detailCountNo));
						detailCountNo++;
						expensesDetails.setBelongExpensesAppNo(expensePurposeNo);
						expensesDetails.setExpensesDateType(seEcEpDeListBean.getTimeMethod());
						expensesDetails.setExpensesDate(DateFormatCommon.strToYYYYMMDDNoCa(seEcEpDeListBean.getDayFrom()));
						expensesDetails.setExpensesDateEnd(DateFormatCommon.strToYYYYMMDDNoCa(seEcEpDeListBean.getDayTo()));
						expensesDetails.setTravelLocation(seEcEpDeListBean.getLocation());
						expensesDetails.setExpensesItem(seEcEpDeListBean.getExpenseType());
						expensesDetails.setExpensesAmount(new BigDecimal(seEcEpDeListBean.getExpenseAmount().replaceAll(",", "")));
						if (CodeCommon.PRIVATE_CAR_FOR_BUSINESS.equals(expensesDetails.getExpensesItem())) {
							SysExpenses se = super.getS025Rate();
							String meth = se.getComputeMethod();
							BigDecimal rate = BigDecimal.ZERO;
							if (meth.endsWith("1")) {
								rate = new BigDecimal(se.getExtendsFieldCo1());
							} else if(meth.endsWith("2")) {
								rate = new BigDecimal(se.getExtendsFieldCo2());
							} else {
								rate = new BigDecimal(se.getExtendsFieldCo3());
							}
							expensesDetails.setKilometers(
									expensesDetails.getExpensesAmount().
									divide(rate, 2, BigDecimal.ROUND_UP));
						}
						expensesDetails.setExpensesComments(seEcEpDeListBean.getComments());
						expensesDetails.setDeleteFlg(CodeCommon.NOT_DELETE_FLG);
						expensesDetails.setAddTimestamp(this.getCurrTimeStamp());
						expensesDetails.setAddUserKey(userId);
						// 将所有数据保存在LIST中。判断需要下一个节点审批用
						detailAllListToApprove.add(expensesDetails);
						baseDaoImpl.save(expensesDetails);
					}
				}
			}
			
			ExpensesApplication expensesApplication = expenseDaoImpl.expenseClaimQuery(expenseAppNo);
			BigDecimal befAmount = expensesApplication.getExpenseSum();
			expensesApplication.setApplicationDate(this.getCurrDate());
			expensesApplication.setCostCenterCode(seEcEpCaBean.getCostCenter());
			expensesApplication.setTravelLocalType(seEcEpCaBean.getTravelLocalType());
			expensesApplication.setTravelReason(seEcEpCaBean.getTravelReason());
			expensesApplication.setTravelAppNo(StringUtils.EMPTY);
			expensesApplication.setExpenseSum(totalAmount);
			expensesApplication.setStatus(isSubmit ? CodeCommon.CLAIM_APPLICATION :CodeCommon.CLAIM_SAVE);
			if (claimInfoBean.getBeanList() == null || claimInfoBean.getBeanList().size() == 0 ) {
				expensesApplication.setDeleteFlg(CodeCommon.IS_DELETE_FLG);
			} else {
				expensesApplication.setDeleteFlg(CodeCommon.NOT_DELETE_FLG);
			}
			expensesApplication.setUpdTimestamp(this.getCurrTimeStamp());
			expensesApplication.setUpdUserKey(userId);
			expensesApplication.setUpdPgmId("SE_EC_EP_CA");
			expensesApplication.setTravelDateStart(DateFormatCommon.strToYYYYMMDDNoCa(seEcEpCaBean.getClaimDateFrom()));
			expensesApplication.setTravelDateEnd(DateFormatCommon.strToYYYYMMDDNoCa(seEcEpCaBean.getClaimDateTo()));
			baseDaoImpl.update(expensesApplication);
			
			// 如果是提交申请
			if (isSubmit) {

				//取得最新的申请流程节点
				WorkflowProgress wfProgress = expenseDaoImpl.getWorkflowProgressInfo(expenseAppNo);
				// 如果最新的是财务驳回的,并且金额小于或者等于以前的金额
				if (wfProgress != null && CodeCommon.WFND05.equals(wfProgress.getNodeId()) 
						&& CodeCommon.CLAIM_REJECT.equals(wfProgress.getProgressStatus())
						&& totalAmount.compareTo(befAmount) <= 0) {
					// 先将当前的申请记录显示为PENDING
					expensesApplication.setStatus(CodeCommon.CLAIM_PENDING);
					baseDaoImpl.update(expensesApplication);
					
					WorkflowProgress workflowProgress = new WorkflowProgress();
					workflowProgress.setNodeId(CodeCommon.WFND05);
					workflowProgress.setProgressStatus(CodeCommon.CLAIM_PENDING);
					workflowProgress.setOperTimestamp(this.getCurrTimeStamp());
					workflowProgress.setOperComments("");
					workflowProgress.setBusinessId(expenseAppNo);
					workflowProgress.setBusinessCategory(CodeCommon.WFCG02);
					workflowProgress.setDeleteFlg(CodeCommon.NOT_DELETE_FLG);
					workflowProgress.setAddTimestamp(this.getCurrTimeStamp());
					workflowProgress.setAddUserKey(userId);
					baseDaoImpl.save(workflowProgress);
				} else {
					// 先抽取当前人员的信息
					EmployeeInfo employeeInfo = employeeManagementService.employeeInfoView(userId);
					
					// 这里先判断当前的角色是否是财务
					if (CodeCommon.FINANCIAL_PERSON.equals(employeeInfo.getRoleCode())) {
						// 是财务
						WorkflowProgress workflowProgress = new WorkflowProgress();
						workflowProgress.setNodeId(CodeCommon.WFND06);
						workflowProgress.setProgressStatus(CodeCommon.CLAIM_APPLICATION);
						workflowProgress.setOperTimestamp(this.getCurrTimeStamp());
						workflowProgress.setOperComments("");
						workflowProgress.setBusinessId(expenseAppNo);
						workflowProgress.setBusinessCategory(CodeCommon.WFCG01);
						workflowProgress.setDeleteFlg(CodeCommon.NOT_DELETE_FLG);
						workflowProgress.setAddTimestamp(this.getCurrTimeStamp());
						workflowProgress.setAddUserKey(userId);
						baseDaoImpl.save(workflowProgress);
					} else {
						// 抽取当前人员是不是拥有审批权限的人
						String maxNodeForSelf = expenseDaoImpl.getMaxNodeCurrentUser(employeeInfo.getRoleCode(), false);
						if (StringUtils.isEmpty(maxNodeForSelf)) {
							// 是空说明自己不是审批人员
							String nodeId = "";
							if (employeeInfo.getDeptCode().equals(seEcEpCaBean.getCostCenter())) {
								// 自己的部门
								nodeId = CodeCommon.WFND02;
							} else {
								// 别的部门经理审批
								nodeId = CodeCommon.WFND01;
							}
							WorkflowProgress workflowProgress = new WorkflowProgress();
							workflowProgress.setNodeId(nodeId);
							workflowProgress.setProgressStatus(CodeCommon.CLAIM_APPLICATION);
							workflowProgress.setOperTimestamp(this.getCurrTimeStamp());
							workflowProgress.setOperComments("");
							workflowProgress.setBusinessId(expenseAppNo);
							workflowProgress.setBusinessCategory(CodeCommon.WFCG01);
							workflowProgress.setDeleteFlg(CodeCommon.NOT_DELETE_FLG);
							workflowProgress.setAddTimestamp(this.getCurrTimeStamp());
							workflowProgress.setAddUserKey(userId);
							baseDaoImpl.save(workflowProgress);
						} else {
							// 自己是审批人员
							// 获取下一个节点
							WorkflowNodeDefination nextNode = this.getNextDefination(maxNodeForSelf);
							WorkflowNodeDefination nextNodeParam = null;
							if (nextNode != null) {
								if (CodeCommon.WFND04.equals(nextNode.getNodeId())) {
									// 如果下面是VP说明是CM去申请的那直接创建记录
									nextNodeParam = nextNode;
								} else {
									// 去判断是不是符合要求并返回
									nextNodeParam = this.getNextNodeDeFinaton(nextNode, expensesApplication.getExpenseSum(), detailAllListToApprove);
								}
							}
							WorkflowProgress workflowProgress = new WorkflowProgress();
							workflowProgress.setNodeId(nextNodeParam.getNodeId());
							workflowProgress.setProgressStatus(CodeCommon.CLAIM_APPLICATION);
							workflowProgress.setOperTimestamp(this.getCurrTimeStamp());
							workflowProgress.setOperComments("");
							workflowProgress.setBusinessId(expenseAppNo);
							workflowProgress.setBusinessCategory(CodeCommon.WFCG01);
							workflowProgress.setDeleteFlg(CodeCommon.NOT_DELETE_FLG);
							workflowProgress.setAddTimestamp(this.getCurrTimeStamp());
							workflowProgress.setAddUserKey(userId);
							baseDaoImpl.save(workflowProgress);
						}
					}	
				}
			}
			
		}
		return true;
	}

	@Override
	public boolean expensesParameterSetting(String expenseCode, List<Map<String,Object>> list) throws Exception {
		// 将制定报销项目下地数据全部删去
		try{
			expenseDaoImpl.expensesParameterDeleteByExpenseCode(expenseCode);
			for (int i = 0;i < list.size(); i++) {
				Map<String,Object> map = list.get(i);
				if (map.get("expenseUp") == null || StringUtils.isEmpty(map.get("expenseUp").toString())) {
					continue;
				}
				ExpensesParameter ep = new ExpensesParameter();
				ep.setExpenseCode(expenseCode);
				ep.setTravelLocal(map.get("travelType").toString().trim());
				ep.setEmployeeLevelCode(map.get("levelCode").toString().trim());
				ep.setAllowExpensesUp(new BigDecimal(map.get("expenseUp").toString().trim()));
				baseDaoImpl.save(ep);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			throw e;
		}
	}

	@Override
	public List<SeSmEpMaListBean> expensesParameterListQuery(String expenseCode)
			throws Exception {
		try{
			return expenseDaoImpl.expensesParameterListQuery(expenseCode);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			throw e;
		}
	}

	@Override
	public ExpensesApplication expensesClaimQuery(String expensesNo)
			throws Exception {
		try{
			return expenseDaoImpl.expenseClaimQuery(expensesNo);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			throw e;
		}
	}

	@Override
	public QueryResult<SeAcApLsListBean> expensesApproveListQuery(int start,
			int end, SeAcApLsBean seAcApLsBean, String userId) throws Exception {
		try{
			return expenseDaoImpl.expensesApproveListQuery(start, end, seAcApLsBean, userId);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			throw e;
		}
	}

	@Override
	public QueryResult<ExpensePurposeSum> expensesClaimPurposeListQuery(
			String aplicationNo) throws Exception {
		return expenseDaoImpl.expensesClaimPurposeListQuery(aplicationNo);
	}

	@Override
	public ExpensePurposeSum expensesPurposeQuery(String expensesDetailsNo)
			throws Exception {
		try{
			return expenseDaoImpl.expensesPurposeQuery(expensesDetailsNo);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			throw e;
		}
	}

	@Override
	public BigDecimal getExpenseUpAllow(String travelType, String levelCode,
			String expenseCode) throws Exception {
		try{
			return expenseDaoImpl.getExpenseUpAllow(travelType, levelCode, expenseCode);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			throw e;
		}
	}

	@Override
	public String getNextExpenseAppNo() throws Exception {
		try{
			NoExpensesApp noExpensesApp = expenseDaoImpl.getNextExpenseAppNo();
			if (noExpensesApp != null) {
				noExpensesApp.setMaxAppNumber(noExpensesApp.getCurrentYear() + getNextNo(noExpensesApp.getMaxAppNumber().substring(4)));
				baseDaoImpl.update(noExpensesApp);
			} else {
				noExpensesApp = new NoExpensesApp();
				noExpensesApp.setCurrentYear(new SimpleDateFormat("yyyy").format(new java.util.Date()));
				noExpensesApp.setMaxAppNumber(noExpensesApp.getCurrentYear() + getNextNo("0"));
				baseDaoImpl.save(noExpensesApp);
			}
			return noExpensesApp.getMaxAppNumber();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			throw e;
		}
	}
	
	private String getNextNo(String no) {
		int intNo = Integer.valueOf(no);
		return StringUtils.leftPad(String.valueOf(intNo + 1), 6, "0");
	}
	
	private String getNextSubNo(int no) {
		return StringUtils.leftPad(String.valueOf(no + 1), 2, "0");
	}

	@Override
	public boolean expenseReject(SeAcApDeBean seAcApDeBean, String userId, String updKey)
			throws Exception {
		// 将申请记录变成驳回状态
		try{
			ExpensesApplication expensesApplication = this.expensesClaimQuery(seAcApDeBean.getExpenseAppNo());
			expensesApplication.setStatus(CodeCommon.CLAIM_REJECT);
			expensesApplication.setUpdTimestamp(getCurrTimeStamp());
			expensesApplication.setUpdUserKey(userId);
			expensesApplication.setUpdPgmId(updKey);
			baseDaoImpl.update(expensesApplication);
			// 申请的流也变成驳回状态
			WorkflowProgress workflowProgress = expenseDaoImpl.getWorkflowProgressInfo(seAcApDeBean.getExpenseAppNo());
			workflowProgress.setProgressStatus(CodeCommon.CLAIM_REJECT);
			workflowProgress.setEmployeeNo(userId);
			workflowProgress.setOperComments(seAcApDeBean.getRejectReason());
			workflowProgress.setUpdTimestamp(getCurrTimeStamp());
			workflowProgress.setUpdUserKey(userId);
			workflowProgress.setUpdPgmId(updKey);
			baseDaoImpl.update(workflowProgress);
			// 将标记错误的明细记录更新
			if (!StringUtils.isEmpty(seAcApDeBean.getErrorDetailNo())) {
				String[] errorDetailNoArr = seAcApDeBean.getErrorDetailNo().split(",");
				for (int i = 0; i< errorDetailNoArr.length ; i++) {
					if (!StringUtils.isEmpty(errorDetailNoArr[i])){
						ExpensesDetails detail = (ExpensesDetails) baseDaoImpl.getByIndex(ExpensesDetails.class, "expensesDetailsNo", errorDetailNoArr[i]);
						detail.setRejectErrorFlg(CodeCommon.DETAIL_ERROR_FLG);
						baseDaoImpl.update(detail);
					}
				}
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			throw e;
		}
	}

	@Override
	public List<ExpensesApproveRules> getExpensesApproveRules(String nodeId) throws Exception {
		try{
			return expenseDaoImpl.getExpensesApproveRules(nodeId);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return null;
		}
	}

	@Override
	public WorkflowProgress getWorkflowProgressInfo(String expenseAppNo) throws Exception {
		try{
			return expenseDaoImpl.getWorkflowProgressInfo(expenseAppNo);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return null;
		}
	}

	@Override
	public boolean expenseApproved(String expenseAppNo, String userId,
			String updKey, WorkflowNodeDefination nextNode) throws Exception {
		// 这一层人员审核通过
		try{
			// 获取申请信息
			ExpensesApplication expensesApplication = this.expensesClaimQuery(expenseAppNo);
			// 获取审批流程
			WorkflowProgress workflowProgress = this.getWorkflowProgressInfo(expenseAppNo);
			
			// 这里添加一个分支为财务提交的报销申请
			// 先抽取当前人员的信息
			EmployeeInfo claimPerson = employeeManagementService.employeeInfoView(expensesApplication.getEmployeeNo());
			// 这里先判断当前的角色是否是财务
			if (CodeCommon.FINANCIAL_PERSON.equals(claimPerson.getRoleCode())) {
				// 是财务
				if (nextNode == null) {
					// 说明审核流已经到了最后
					// 更新当前申请记录的状态
					expensesApplication.setStatus(CodeCommon.CLAIM_FINISH);
					expensesApplication.setUpdTimestamp(getCurrTimeStamp());
					expensesApplication.setUpdUserKey(userId);
					expensesApplication.setUpdPgmId(updKey);
					baseDaoImpl.update(expensesApplication);
					
					workflowProgress.setProgressStatus(CodeCommon.CLAIM_FINISH);
					workflowProgress.setEmployeeNo(userId);
					workflowProgress.setUpdTimestamp(getCurrTimeStamp());
					workflowProgress.setUpdUserKey(userId);
					workflowProgress.setUpdPgmId(updKey);
					baseDaoImpl.update(workflowProgress);

				} else {
					// 财务审核没有到最后一个流程
					// 更新当前申请记录的状态
					expensesApplication.setStatus(CodeCommon.CLAIM_PENDING);
					expensesApplication.setUpdTimestamp(getCurrTimeStamp());
					expensesApplication.setUpdUserKey(userId);
					expensesApplication.setUpdPgmId(updKey);
					baseDaoImpl.update(expensesApplication);
					
					workflowProgress.setProgressStatus(CodeCommon.CLAIM_PENDING);
					workflowProgress.setEmployeeNo(userId);
					workflowProgress.setUpdTimestamp(getCurrTimeStamp());
					workflowProgress.setUpdUserKey(userId);
					workflowProgress.setUpdPgmId(updKey);
					baseDaoImpl.update(workflowProgress);
					
					// 创建新的审批流程节点
					WorkflowProgress workflowProgressNew = new WorkflowProgress();
					workflowProgressNew.setNodeId(nextNode.getNodeId());
					workflowProgressNew.setProgressStatus(CodeCommon.CLAIM_PENDING);
					workflowProgressNew.setOperTimestamp(this.getCurrTimeStamp());
					workflowProgressNew.setOperComments("");
					workflowProgressNew.setBusinessId(expenseAppNo);
					workflowProgressNew.setBusinessCategory(CodeCommon.WFCG02);
					workflowProgressNew.setDeleteFlg(CodeCommon.NOT_DELETE_FLG);
					workflowProgressNew.setAddTimestamp(this.getCurrTimeStamp());
					workflowProgressNew.setAddUserKey(userId);
					baseDaoImpl.save(workflowProgressNew);
				}
					
			} else {

				
				if (CodeCommon.CLAIM_APPROVED.equals(expensesApplication.getStatus())) {
					// 财务的FINISH操作
					expensesApplication.setStatus(CodeCommon.CLAIM_FINISH);
					expensesApplication.setUpdTimestamp(getCurrTimeStamp());
					expensesApplication.setUpdUserKey(userId);
					expensesApplication.setUpdPgmId(updKey);
					baseDaoImpl.update(expensesApplication);
					// 这里先穿件一条信息为FINISH的记录
					// 申请的流也变成审核通过
					workflowProgress.setProgressStatus(CodeCommon.CLAIM_FINISH);
					workflowProgress.setEmployeeNo(userId);
					workflowProgress.setUpdTimestamp(getCurrTimeStamp());
					workflowProgress.setUpdUserKey(userId);
					workflowProgress.setUpdPgmId(updKey);
					baseDaoImpl.update(workflowProgress);
					
				} else {
					if (nextNode == null) {
						// 说明审核流已经到了最后
						expensesApplication.setStatus(CodeCommon.CLAIM_APPROVED);
						expensesApplication.setUpdTimestamp(getCurrTimeStamp());
						expensesApplication.setUpdUserKey(userId);
						expensesApplication.setUpdPgmId(updKey);
						baseDaoImpl.update(expensesApplication);
						
						// 申请的流也变成审核通过
						workflowProgress.setProgressStatus(CodeCommon.CLAIM_APPROVED);
						workflowProgress.setEmployeeNo(userId);
						workflowProgress.setUpdTimestamp(getCurrTimeStamp());
						workflowProgress.setUpdUserKey(userId);
						workflowProgress.setUpdPgmId(updKey);
						baseDaoImpl.update(workflowProgress);
						
						// 创建新的
						WorkflowProgress workflowProgressNew = new WorkflowProgress();
						workflowProgressNew.setNodeId(CodeCommon.WFND05);
						workflowProgressNew.setProgressStatus(CodeCommon.CLAIM_APPROVED);
						workflowProgressNew.setOperTimestamp(this.getCurrTimeStamp());
						workflowProgressNew.setOperComments("");
						workflowProgressNew.setBusinessId(expenseAppNo);
						workflowProgressNew.setBusinessCategory(CodeCommon.WFCG01);
						workflowProgressNew.setDeleteFlg(CodeCommon.NOT_DELETE_FLG);
						workflowProgressNew.setAddTimestamp(this.getCurrTimeStamp());
						workflowProgressNew.setAddUserKey(userId);
						baseDaoImpl.save(workflowProgressNew);
						
						// 不在有新的审核流
					} else {
						// 接着上一层的审核
						// 申请还是处于待审核状态
						// 当前的审核流通过
						String claimStatus = "";
						// 还没有到财务审批。
						claimStatus = CodeCommon.CLAIM_PENDING;
						//更新当前申请记录的状态
						expensesApplication.setStatus(claimStatus);
						expensesApplication.setUpdTimestamp(getCurrTimeStamp());
						expensesApplication.setUpdUserKey(userId);
						expensesApplication.setUpdPgmId(updKey);
						baseDaoImpl.update(expensesApplication);
						
						workflowProgress.setProgressStatus(claimStatus);
						workflowProgress.setEmployeeNo(userId);
						workflowProgress.setUpdTimestamp(getCurrTimeStamp());
						workflowProgress.setUpdUserKey(userId);
						workflowProgress.setUpdPgmId(updKey);
						baseDaoImpl.update(workflowProgress);
						
						// 创建新的
						WorkflowProgress workflowProgressNew = new WorkflowProgress();
						workflowProgressNew.setNodeId(nextNode.getNodeId());
						workflowProgressNew.setProgressStatus(claimStatus);
						workflowProgressNew.setOperTimestamp(this.getCurrTimeStamp());
						workflowProgressNew.setOperComments("");
						workflowProgressNew.setBusinessId(expenseAppNo);
						workflowProgressNew.setBusinessCategory(CodeCommon.WFCG01);
						workflowProgressNew.setDeleteFlg(CodeCommon.NOT_DELETE_FLG);
						workflowProgressNew.setAddTimestamp(this.getCurrTimeStamp());
						workflowProgressNew.setAddUserKey(userId);
						baseDaoImpl.save(workflowProgressNew);
					}
				}
				
			}
			

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			throw e;
		}
	}

	@Override
	public String getSubExpenseItem(String fatherExpenseItem) throws Exception {
		return expenseDaoImpl.getSubExpenseItem(fatherExpenseItem);
	}

	@Override
	public boolean expenseRejectAll(String expenseAppNo, String userId,
			String updKey, SeAcApLsBean seAcApLsBean) throws Exception {
		// 将申请记录变成驳回状态
		try{
			String[] appNoArr = expenseAppNo.split(",");
			for (String appNo : appNoArr) {
				ExpensesApplication expensesApplication = this.expensesClaimQuery(appNo);
				expensesApplication.setStatus(CodeCommon.CLAIM_REJECT);
				expensesApplication.setUpdTimestamp(getCurrTimeStamp());
				expensesApplication.setUpdUserKey(userId);
				expensesApplication.setUpdPgmId(updKey);
				baseDaoImpl.update(expensesApplication);
				// 申请的流也变成驳回状态
				WorkflowProgress workflowProgress = expenseDaoImpl.getWorkflowProgressInfo(appNo);
				workflowProgress.setProgressStatus(CodeCommon.CLAIM_REJECT);
				workflowProgress.setOperComments(seAcApLsBean.getRejectReason());
				workflowProgress.setEmployeeNo(userId);
				workflowProgress.setUpdTimestamp(getCurrTimeStamp());
				workflowProgress.setUpdUserKey(userId);
				workflowProgress.setUpdPgmId(updKey);
				baseDaoImpl.update(workflowProgress);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			throw e;
		}
	}

	@Override
	public boolean expenseApprovedAll(String expenseAppNoArr, String userId,
			String updKey) throws Exception {
		try{
			String[] appNoArr = expenseAppNoArr.split(",");
			for (String appNo : appNoArr) {
				// 获取申请信息
				ExpensesApplication expensesApplication = this.expensesClaimQuery(appNo);
				// 获取审批流程
				WorkflowProgress workflowProgress = this.getWorkflowProgressInfo(appNo);
				QueryResult<ExpensePurposeSum> purposeDetails = this.expensesClaimPurposeListQuery(appNo);
				List<ExpensesDetails>  detailAllList = new ArrayList<ExpensesDetails>();
				// purpose List
				if (purposeDetails != null && purposeDetails.getTotalrecord() > 0) {
					for (ExpensePurposeSum detail : purposeDetails.getResultlist()) {
						// 这里再次检索同一目的
						QueryResult<ExpensesDetails> expenseDetailsList = this.expensesClaimDetailsListQuery(detail.getExpensesDetailsNo());
						for (ExpensesDetails expensesDetails : expenseDetailsList.getResultlist()) {
							detailAllList.add(expensesDetails);
						}
					}
				}
				// 得到下一个节点
				WorkflowNodeDefination nextNode = this.getNextDefination(workflowProgress.getNodeId());
				WorkflowNodeDefination nextNodeParam = null;
				if (nextNode != null) {
					// 去判断是不是符合要求并返回
					nextNodeParam = this.getNextNodeDeFinaton(nextNode, expensesApplication.getExpenseSum(), detailAllList);
				}
				this.expenseApproved(appNo, userId, updKey, nextNodeParam);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			throw e;
		}
	}

	@Override
	public String concatAuthority(String expenseAppNo) throws Exception {
		return expenseDaoImpl.concatAuthority(expenseAppNo);
	}

	@Override
	public List<AuthorityByListBean> getAuthorityByHisList(String expenseAppNo)
			throws Exception {
		return expenseDaoImpl.getAuthorityByHisList(expenseAppNo);
	}

	@Override
	public String getNodeIdByCurrentUser(String employeeNo) throws Exception {
		return expenseDaoImpl.getNodeIdByCurrentUser(employeeNo);
	}

	@Override
	public String getNowNodeIdByExpenseAppNo(String expenseAppNo)
			throws Exception {
		return expenseDaoImpl.getNowNodeIdByExpenseAppNo(expenseAppNo);
	}

	@Override
	public boolean judgeCanApprove(String expenseAppNo, String employeeNo)
			throws Exception {
		return expenseDaoImpl.judgeCanApprove(expenseAppNo, employeeNo);
	}

	@Override
	public List<SeAcApLsListBean> getApproveTodoList(String userId)
			throws Exception {
		return expenseDaoImpl.getApproveTodoList(userId);
	}

	@Override
	public WorkflowNodeDefination getNextDefination(String currentNodeId)
			throws Exception {
		return (WorkflowNodeDefination) baseDaoImpl.getByIndex(WorkflowNodeDefination.class, "upNodeId", currentNodeId);
	}
	
	/**
	 * 取得下一个符合要求的节点
	 * @param currentNode
	 * @param totalAmount
	 * @param detailAllList
	 * @return
	 * @throws Exception
	 */
	@Override
	public WorkflowNodeDefination getNextNodeDeFinaton(WorkflowNodeDefination current, BigDecimal totalAmount, List<ExpensesDetails>  detailAllList) throws Exception {
		
		boolean isStepByStep = true;
		List<ExpensesApproveRules> expensesApproveRules = this.getExpensesApproveRules(current.getNodeId());
		
		// 先盘点是不是需要有比较
		boolean notCondition = true;
		for (ExpensesApproveRules approveRule : expensesApproveRules) {
			if (!CodeCommon.APPROVE_NO_CODITON.equals(approveRule.getApproveCondition())) {
				notCondition = false;
				break;
			}
		}
		if (!notCondition) {
			// 有条件去比较
			if (!judgeCurNodeNeedApprove(expensesApproveRules, totalAmount, detailAllList)) {
				isStepByStep = false;
			}
		}
		if (isStepByStep) {
			// 如果是一步一步下去
			return current;
		} else {
			// 如果不是
			WorkflowNodeDefination next = this.getNextDefination(current.getNodeId());
			if (next == null) {
				return null;
			} else {
				// 接着抽去判断是不是符合条件
				return getNextNodeDeFinaton(next, totalAmount, detailAllList);
			}
		}
	}
	
	/**
	 * 判断当前点是不是需要审批
	 * @param expensesApproveRules
	 * @param totalAmount
	 * @param detailAllList
	 * @return
	 * @throws Exception 
	 */
	private boolean judgeCurNodeNeedApprove(List<ExpensesApproveRules> expensesApproveRules, BigDecimal totalAmount, List<ExpensesDetails>  detailAllList) throws Exception {
		boolean needApprove = false;
		for (ExpensesApproveRules rule : expensesApproveRules) {
			if (CodeCommon.EXPENSE_SUPERFARTHER.equals(rule.getApproveExpenseType())) {
				// 如果是总项目
				if (totalAmount.compareTo(new BigDecimal(rule.getApproveAmount())) > 0) {
					needApprove = true;
				}
			} else {
				String subItem = this.getSubExpenseItem(rule.getApproveExpenseType());
				BigDecimal expenseAmount = BigDecimal.ZERO;
				for (ExpensesDetails detail: detailAllList) {
					// 判断其是不是父类项目
					if (subItem != null) {
						// 如果是父类项目的
						if (subItem.contains(detail.getExpensesDateType())) {
							expenseAmount = expenseAmount.add(detail.getExpensesAmount());
						}
					} else {
						// 不是父类项目
						if (rule.getApproveExpenseType().equals(detail.getExpensesItem())) {
							expenseAmount = expenseAmount.add(detail.getExpensesAmount());
						}
					}
				}
				if (expenseAmount.compareTo(new BigDecimal(rule.getApproveAmount())) > 0) {
					needApprove = true;
				}
			}
		}
		return needApprove;
	}

	@Override
	public boolean isExitSameExpenseByMonth(String expenseItem, String day,
			String applicationNo, String claimBy) throws Exception {
		return expenseDaoImpl.isExitSameExpenseByMonth(expenseItem, day, applicationNo, claimBy);
	}

	@Override
	public String getPerCanApprove(String businessId) throws Exception {
		return expenseDaoImpl.getPerCanApprove(businessId);
	}

	@Override
	public boolean expenseUndoFinished(String expenseAppNo) throws Exception {
		// 抽出申请数据将数据变成审批完，但为完成
		ExpensesApplication expensesApplication = this.expensesClaimQuery(expenseAppNo);
		expensesApplication.setStatus(CodeCommon.CLAIM_APPROVED);
		baseDaoImpl.update(expensesApplication);
		// 将审核流中的数据变成为完成前的状态
		WorkflowProgress progress = this.getWorkflowProgressInfo(expenseAppNo);
		progress.setEmployeeNo(null);
		progress.setUpdPgmId(null);
		progress.setUpdUserKey(null);
		baseDaoImpl.update(progress);
		return true;
	}

}
