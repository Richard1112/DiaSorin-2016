package com.diasorin.oa.mvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.diasorin.oa.common.CodeCommon;
import com.diasorin.oa.common.DateFormatCommon;
import com.diasorin.oa.common.ErrCommon;
import com.diasorin.oa.common.QueryResult;
import com.diasorin.oa.common.TypeConvertCommon;
import com.diasorin.oa.dto.AuthorityByListBean;
import com.diasorin.oa.dto.ClaimInfoBean;
import com.diasorin.oa.dto.SeAcApDeBean;
import com.diasorin.oa.dto.SeAcApLsBean;
import com.diasorin.oa.dto.SeAcApLsListBean;
import com.diasorin.oa.dto.SeEcEpDeBean;
import com.diasorin.oa.dto.SeEcEpDeListBean;
import com.diasorin.oa.model.EmployeeInfo;
import com.diasorin.oa.model.ExpensePurposeSum;
import com.diasorin.oa.model.ExpensesApplication;
import com.diasorin.oa.model.ExpensesDetails;
import com.diasorin.oa.service.BaseService;
import com.diasorin.oa.service.EmployeeManagementService;
import com.diasorin.oa.service.ExpensesManagementService;
import com.diasorin.oa.service.SystemManagementService;

@Controller
@RequestMapping("/SE_AC_AP_LS/*")
public class SeAcApLsController extends BaseController {

	@Resource
	BaseService baseService;
	
	@Resource
	SystemManagementService systemManagementService;
	
	@Resource
	ExpensesManagementService expensesManagementService;
	
	@Resource
	EmployeeManagementService employeeManagementService;
	
	/**
	 * 审批一览画面
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("init")
	public String init(Model model, HttpServletRequest request, HttpSession session) {
		try {
			SeAcApLsBean seAcApLsBean = new SeAcApLsBean();
			// 状态
			model.addAttribute("StatusSelect", baseService.getApproveSelect());
			// 获取成本中心下拉框
			model.addAttribute("costCenterList", systemManagementService.costCenterListQuery(-1, -1).getResultlist());
			model.addAttribute("listCount", "0");
			model.addAttribute("seAcApLsBean", seAcApLsBean);
			return "SE_AC_AP_LS";
		} catch (Exception e) {
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return CodeCommon.COMMON_ERROR_PAGE;
		}
		
	}
	
	/**
	 * 报销审批列表
	 * @param model
	 * @param request
	 * @param session
	 * @param seEmPeLsBean
	 * @return
	 */
	@RequestMapping("search")
	public String search(Model model, HttpServletRequest request, HttpSession session, @ModelAttribute SeAcApLsBean seAcApLsBean) {
		try {
			String userId = (String) session.getAttribute(CodeCommon.SESSION_USERID);
			// 状态
			model.addAttribute("StatusSelect", baseService.getApproveSelect());
			// 获取成本中心下拉框
			model.addAttribute("costCenterList", systemManagementService.costCenterListQuery(-1, -1).getResultlist());

			// 将现在的检索数据放入session 中
			session.setAttribute("seAcApLsBeanForSearch", seAcApLsBean);
			// 取得配置文件的页码最大显示数据量
			int maxresult = Integer.parseInt(getMessage("recordCount"));
			int totalPage = 1;
			// 报销列表
			QueryResult<SeAcApLsListBean> expenseApproveList = expensesManagementService.expensesApproveListQuery(0, maxresult, seAcApLsBean, userId);
			
			// 是否有记录
			if (expenseApproveList.getResultlist() != null && expenseApproveList.getTotalrecord() > 0) {
				model.addAttribute("listCount", expenseApproveList.getTotalrecord());
			}else{
				model.addAttribute("listCount", "0");
			}
			
			long resultCount = expenseApproveList.getTotalrecord();
			totalPage=(int) ((resultCount % maxresult ==0) ? resultCount/maxresult : resultCount/maxresult+1);
			// 将数据转化成画面bean	
			if (expenseApproveList.getTotalrecord() > 0) {
				int countNo = 0;
				for(SeAcApLsListBean bean : expenseApproveList.getResultlist()) {
					countNo++;
					bean.setNo(String.valueOf(countNo));
					bean.setClaimDate(DateFormatCommon.formatDate(bean.getClaimDate(), "3"));
					bean.setCostCenter(bean.getCostCenter());
					bean.setCostCenterName(systemManagementService.getCostCenterInfo(bean.getCostCenter()).getCostCenterName());
					bean.setTravelLocationType(baseService.getSelect(CodeCommon.COM004, bean.getTravelLocationType()));
					bean.setStatus(bean.getStatus());
					// 判断是不是已经进入审核流
					if (CodeCommon.CLAIM_APPLICATION.equals(bean.getStatus())
							|| CodeCommon.CLAIM_PENDING.equals(bean.getStatus())
							|| CodeCommon.CLAIM_APPROVED.equals(bean.getStatus())
							|| CodeCommon.CLAIM_FINISH.equals(bean.getStatus())) {
						bean.setCanBeApprove(CodeCommon.CAN_BEAPPROVE);
						// 如果当前的申请人是财务的话finished状态下的就不可以被操作
						EmployeeInfo employeeInfo = employeeManagementService.employeeInfoView(bean.getClaimNo());
						if (CodeCommon.FINANCIAL_PERSON.equals(employeeInfo.getRoleCode()) && CodeCommon.CLAIM_FINISH.equals(bean.getStatus())) {
							bean.setCanBeApprove(CodeCommon.CAN_NOT_BEAPPROVE);
						}
					} else {
						bean.setCanBeApprove(CodeCommon.CAN_NOT_BEAPPROVE);
					}
					// 判断当前审批节点是不是可以被当前用户审批
					if (CodeCommon.CAN_BEAPPROVE.equals(bean.getCanBeApprove())) {
						String curNodeId = expensesManagementService.getNowNodeIdByExpenseAppNo(bean.getExpenseNo());
						String hasNodeId = expensesManagementService.getNodeIdByCurrentUser(userId);
						if (!hasNodeId.contains(curNodeId)) {
							// 不拥有当前的NODEID,则不能审批。
							bean.setCanBeApprove(CodeCommon.CAN_NOT_BEAPPROVE);
						}
					}
					// 判断审核流是不是已经跑到当前用户
					if (CodeCommon.CAN_BEAPPROVE.equals(bean.getCanBeApprove())) {
						if(!expensesManagementService.judgeCanApprove(bean.getExpenseNo(), userId)){
							bean.setCanBeApprove(CodeCommon.CAN_NOT_BEAPPROVE);
						}
						
					}
					// 下一个可以申请的人(只有在pedding的时候显示)
					if (CodeCommon.CLAIM_PENDING.equals(bean.getStatus())) {
						bean.setNextAprrovePerson(expensesManagementService.getPerCanApprove(bean.getExpenseNo()));
					}
					bean.setStatusName(baseService.getSelect(CodeCommon.COM003, bean.getStatus()));
					bean.setTotal(TypeConvertCommon.convertToCurrencyFmt(new BigDecimal(bean.getTotal())));
				}
			}
			seAcApLsBean.setBeanList(expenseApproveList.getResultlist());
			
			model.addAttribute("curPage", 1);
			model.addAttribute("searched", "1");
			model.addAttribute("totalPage", totalPage);
			model.addAttribute("seAcApLsBean", seAcApLsBean);
			model.addAttribute("currentRole", session.getAttribute(CodeCommon.SESSION_ROLEID));
			return "SE_AC_AP_LS";
		} catch (Exception e) {
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return CodeCommon.COMMON_ERROR_PAGE;
		}
	}
	
	/**
	 * 报销审批列表(分页按钮按下)
	 * @param model
	 * @param request
	 * @param session
	 * @param seEmPeLsBean
	 * @return
	 */
	@RequestMapping("page")
	public String page(Model model, HttpServletRequest request, HttpSession session) {
		try {
			String userId = (String) session.getAttribute(CodeCommon.SESSION_USERID);
			// 状态
			model.addAttribute("StatusSelect", baseService.getApproveSelect());
			// 获取成本中心下拉框
			model.addAttribute("costCenterList", systemManagementService.costCenterListQuery(-1, -1).getResultlist());

			// 将session 中 的数据取出
			SeAcApLsBean seAcApLsBean = (SeAcApLsBean) session.getAttribute("seAcApLsBeanForSearch");
			if (seAcApLsBean == null) {
				seAcApLsBean = new SeAcApLsBean();
			}
			
			// 当前页
			int curPage = Integer.parseInt(request.getParameter("curPage"));
			// 取得配置文件的页码最大显示数据量
			int maxresult = Integer.parseInt(getMessage("recordCount"));
			int totalPage = 1;
			// 报销列表
			int startResult = (curPage - 1) * maxresult;
			// 报销列表
			QueryResult<SeAcApLsListBean> expenseApproveList = expensesManagementService.expensesApproveListQuery(startResult, maxresult, seAcApLsBean, userId);
			
			// 是否有记录
			if (expenseApproveList.getResultlist() != null && expenseApproveList.getTotalrecord() > 0) {
				model.addAttribute("listCount", expenseApproveList.getTotalrecord());
			}else{
				model.addAttribute("listCount", "0");
			}
			
			long resultCount = expenseApproveList.getTotalrecord();
			totalPage=(int) ((resultCount % maxresult ==0) ? resultCount/maxresult : resultCount/maxresult+1);
			// 将数据转化成画面bean	
			if (expenseApproveList.getTotalrecord() > 0) {
				int countNo = 0;
				for(SeAcApLsListBean bean : expenseApproveList.getResultlist()) {
					countNo++;
					bean.setNo(String.valueOf(countNo));
					bean.setClaimDate(DateFormatCommon.formatDate(bean.getClaimDate(), "3"));
					bean.setCostCenter(bean.getCostCenter());
					bean.setCostCenterName(systemManagementService.getCostCenterInfo(bean.getCostCenter()).getCostCenterName());
					bean.setTravelLocationType(baseService.getSelect(CodeCommon.COM004, bean.getTravelLocationType()));
					bean.setStatus(bean.getStatus());
					if (CodeCommon.CLAIM_APPLICATION.equals(bean.getStatus())
							|| CodeCommon.CLAIM_PENDING.equals(bean.getStatus())
							|| CodeCommon.CLAIM_APPROVED.equals(bean.getStatus())
							|| CodeCommon.CLAIM_FINISH.equals(bean.getStatus())) {
						bean.setCanBeApprove(CodeCommon.CAN_BEAPPROVE);
						// 如果当前的申请人是财务的话finished状态下的就不可以被操作
						EmployeeInfo employeeInfo = employeeManagementService.employeeInfoView(bean.getClaimNo());
						if (CodeCommon.FINANCIAL_PERSON.equals(employeeInfo.getRoleCode()) && CodeCommon.CLAIM_FINISH.equals(bean.getStatus())) {
							bean.setCanBeApprove(CodeCommon.CAN_NOT_BEAPPROVE);
						}
					} else {
						bean.setCanBeApprove(CodeCommon.CAN_NOT_BEAPPROVE);
					}
					// 判断当前审批节点是不是可以被当前用户审批
					if (CodeCommon.CAN_BEAPPROVE.equals(bean.getCanBeApprove())) {
						String curNodeId = expensesManagementService.getNowNodeIdByExpenseAppNo(bean.getExpenseNo());
						String hasNodeId = expensesManagementService.getNodeIdByCurrentUser(userId);
						if (!hasNodeId.contains(curNodeId)) {
							// 不拥有当前的NODEID,则不能审批。
							bean.setCanBeApprove(CodeCommon.CAN_NOT_BEAPPROVE);
						}
					}
					// 判断审核流是不是已经跑到当前用户
					if (CodeCommon.CAN_BEAPPROVE.equals(bean.getCanBeApprove())) {
						if(!expensesManagementService.judgeCanApprove(bean.getExpenseNo(), userId)){
							bean.setCanBeApprove(CodeCommon.CAN_NOT_BEAPPROVE);
						}
						
					}
					// 下一个可以申请的人(只有在pedding的时候显示)
					if (CodeCommon.CLAIM_PENDING.equals(bean.getStatus())) {
						bean.setNextAprrovePerson(expensesManagementService.getPerCanApprove(bean.getExpenseNo()));
					}
					bean.setStatusName(baseService.getSelect(CodeCommon.COM003, bean.getStatus()));
					bean.setTotal(TypeConvertCommon.convertToCurrencyFmt(new BigDecimal(bean.getTotal())));
				}
			}
			seAcApLsBean.setBeanList(expenseApproveList.getResultlist());
			
			model.addAttribute("curPage", curPage);
			model.addAttribute("searched", "1");
			model.addAttribute("totalPage", totalPage);
			model.addAttribute("seAcApLsBean", seAcApLsBean);
			model.addAttribute("currentRole", session.getAttribute(CodeCommon.SESSION_ROLEID));
			return "SE_AC_AP_LS";
		} catch (Exception e) {
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return CodeCommon.COMMON_ERROR_PAGE;
		}
	}
	
	/**
	 * 审批一览-->审批详细画面
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("toApprove")
	public String toApprove(Model model, HttpServletRequest request, HttpSession session) {
		try {
			String userId = (String) session.getAttribute(CodeCommon.SESSION_USERID);
			// 报销申请编号
			String expenseAppNo = (String)request.getParameter("expenseAppNo");
			
			SeAcApDeBean seAcApDeBean= new SeAcApDeBean();
			// 获取报销申请
			ExpensesApplication expensesApplication = expensesManagementService.expensesClaimQuery(expenseAppNo);
			
			// 获取报销申请同一目的项目
			QueryResult<ExpensePurposeSum> purposeDetails = expensesManagementService.expensesClaimPurposeListQuery(expenseAppNo);
			
			
			// 将所有的数据放入SESSION中
			ClaimInfoBean claimInfoBean = new ClaimInfoBean();
			claimInfoBean.setTravelLocalType(expensesApplication.getTravelLocalType());
			claimInfoBean.setTravelReason(expensesApplication.getTravelReason());
			claimInfoBean.setCostCenter(expensesApplication.getCostCenterCode());
			claimInfoBean.setClaimDateFrom(DateFormatCommon.formatDate(expensesApplication.getTravelDateStart(), "3"));
			claimInfoBean.setClaimDateTo(DateFormatCommon.formatDate(expensesApplication.getTravelDateEnd(), "3"));
			
			claimInfoBean.setExpenseAppNo(expenseAppNo);
			claimInfoBean.setAppStatus(expensesApplication.getStatus());
			// purpose List
			List<SeEcEpDeBean> purposeList = new ArrayList<SeEcEpDeBean>();
			if (purposeDetails != null && purposeDetails.getTotalrecord() > 0) {
				for (ExpensePurposeSum detail : purposeDetails.getResultlist()) {
					SeEcEpDeBean bean = new SeEcEpDeBean();
					bean.setPurposeNo(detail.getExpensesDetailsNo());
					bean.setPurpose(detail.getExpensesPurpose());
					bean.setAmount(detail.getExpensesAmount());
					// 这里再次检索同一目的
					QueryResult<ExpensesDetails> expenseDetailsList = expensesManagementService.expensesClaimDetailsListQuery(detail.getExpensesDetailsNo());
					List<SeEcEpDeListBean> contentDetailList = new ArrayList<SeEcEpDeListBean>();
					for (ExpensesDetails expensesDetails : expenseDetailsList.getResultlist()) {
						SeEcEpDeListBean listBean = new SeEcEpDeListBean();
						listBean.setNo(expensesDetails.getExpensesDetailsNo());
						listBean.setExpenseType(expensesDetails.getExpensesItem());
						listBean.setDayFrom(DateFormatCommon.formatDate(expensesDetails.getExpensesDate(), "3"));
						listBean.setDayTo(DateFormatCommon.formatDate(expensesDetails.getExpensesDateEnd(), "3"));
						listBean.setLocation(expensesDetails.getTravelLocation());
						listBean.setExpenseAmount(TypeConvertCommon.convertToCurrencyFmt(expensesDetails.getExpensesAmount()));
						listBean.setComments(expensesDetails.getExpensesComments());
						listBean.setTimeMethod(expensesDetails.getExpensesDateType());
						contentDetailList.add(listBean);
					}
					bean.setBeanList(contentDetailList);
					purposeList.add(bean);
				}
				claimInfoBean.setBeanList(purposeList);
			}
			
			// 将数据放入session中
			session.setAttribute(CodeCommon.SESSION_CLAIM_INFO, claimInfoBean);
			
			EmployeeInfo info = employeeManagementService.employeeInfoView(expensesApplication.getEmployeeNo());			

			// 头部数据
			if (!StringUtils.isEmpty(claimInfoBean.getCostCenter())) {
				seAcApDeBean.setCostCenterName(systemManagementService.getCostCenterInfo(expensesApplication.getCostCenterCode()).getCostCenterName());
				seAcApDeBean.setCostCenter(expensesApplication.getCostCenterCode());
			}
			if (!StringUtils.isEmpty(claimInfoBean.getTravelLocalType())) {
				seAcApDeBean.setTravelLocalType(baseService.getSelect(CodeCommon.COM004, expensesApplication.getTravelLocalType()));
			}
			seAcApDeBean.setTravelReason(expensesApplication.getTravelReason());
			seAcApDeBean.setEmployee(info.getEmployeeNameEn());
			seAcApDeBean.setAppStatus(expensesApplication.getStatus());
			seAcApDeBean.setClaimDateFrom(DateFormatCommon.formatDate(expensesApplication.getTravelDateStart(), "3"));
			seAcApDeBean.setClaimDateTo(DateFormatCommon.formatDate(expensesApplication.getTravelDateEnd(), "3"));
			// 出差发生目的
			List<SeEcEpDeBean> purposeToApproveList = new ArrayList<SeEcEpDeBean>();
			
			// 出差发生明细
			List<SeEcEpDeListBean> detailList = new ArrayList<SeEcEpDeListBean>();
			BigDecimal totalAmount = BigDecimal.ZERO;
			for (SeEcEpDeBean purposeBean : claimInfoBean.getBeanList()) {
				purposeToApproveList.add(purposeBean);
				totalAmount = totalAmount.add(purposeBean.getAmount());
				for (SeEcEpDeListBean detailBean : purposeBean.getBeanList()) {
					SeEcEpDeListBean detail = new SeEcEpDeListBean();
					BeanUtils.copyProperties(detail, detailBean);
					detail.setExpenseType(systemManagementService.getExpensesItemInfo(detailBean.getExpenseType()).getExpenseName());
					detailList.add(detail);
				}
			}
			
			seAcApDeBean.setPurposeList(purposeToApproveList);
			seAcApDeBean.setDetailList(detailList);
			seAcApDeBean.setTotalAmount(totalAmount.toString());
			// 判断当前记录的状态。是不是应该可以被审批
			if (CodeCommon.CLAIM_APPLICATION.equals(expensesApplication.getStatus())
					|| CodeCommon.CLAIM_PENDING.equals(expensesApplication.getStatus())
					|| CodeCommon.CLAIM_APPROVED.equals(expensesApplication.getStatus())
					|| CodeCommon.CLAIM_FINISH.equals(expensesApplication.getStatus())) {
				// 只有报销是申请的状态才可以审批
				seAcApDeBean.setMode(CodeCommon.FRAME_MODE_CANAPPROVE);
				// 如果当前的申请人是财务的话finished状态下的就不可以被操作
				EmployeeInfo employeeInfo = employeeManagementService.employeeInfoView(expensesApplication.getEmployeeNo());
				if (CodeCommon.FINANCIAL_PERSON.equals(employeeInfo.getRoleCode()) && CodeCommon.CLAIM_FINISH.equals(expensesApplication.getStatus())) {
					seAcApDeBean.setMode(CodeCommon.FRAME_MODE_CANNOTAPPROVE);
				}
			} else {
				// 审批画面过去的
				seAcApDeBean.setMode(CodeCommon.FRAME_MODE_CANNOTAPPROVE);
			}
			// 判断当前审批节点是不是可以被当前用户审批
			if (CodeCommon.FRAME_MODE_CANAPPROVE.equals(seAcApDeBean.getMode())) {
				String curNodeId = expensesManagementService.getNowNodeIdByExpenseAppNo(expenseAppNo);
				String hasNodeId = expensesManagementService.getNodeIdByCurrentUser(userId);
				if (!hasNodeId.contains(curNodeId)) {
					// 不拥有当前的NODEID,则不能审批。
					seAcApDeBean.setMode(CodeCommon.FRAME_MODE_CANNOTAPPROVE);
				}
			}
			// 判断审核流是不是已经跑到当前用户
			if (CodeCommon.FRAME_MODE_CANAPPROVE.equals(seAcApDeBean.getMode())) {
				if(!expensesManagementService.judgeCanApprove(expenseAppNo, userId)){
					seAcApDeBean.setMode(CodeCommon.FRAME_MODE_CANNOTAPPROVE);
				}
			}
			
			seAcApDeBean.setApplicationDate(DateFormatCommon.formatDate(expensesApplication.getApplicationDate(), "3"));
			seAcApDeBean.setExpenseAppNo(expenseAppNo);
			// 这里将所有审批过的人的信息显示在画面上面
			List<AuthorityByListBean> hisList = expensesManagementService.getAuthorityByHisList(expenseAppNo);
			for (AuthorityByListBean hisBean : hisList) {
				hisBean.setAuthorityDate(hisBean.getAuthorityDate().substring(0, 10).replaceAll("-", "/"));
				String hisStatus = hisBean.getStatusView();
				if (hisStatus.equals(CodeCommon.CLAIM_REJECT)) {
					hisBean.setStatusView(baseService.getSelect(CodeCommon.COM003, CodeCommon.CLAIM_REJECT));
				} else {
					hisBean.setStatusView(baseService.getSelect(CodeCommon.COM003, CodeCommon.CLAIM_APPROVED));
				}
			}
			model.addAttribute("authorityHisList", hisList);
			
			//这里判断APPROVE 是不是显示
			if (CodeCommon.FINANCIAL_PERSON.equals(session.getAttribute(CodeCommon.SESSION_ROLEID))) {
				// 当前记录的状态是APPROVE的时候
				if (CodeCommon.CLAIM_APPROVED.equals(expensesApplication.getStatus())) {
					seAcApDeBean.setApproveFlg("1");
				} else if (CodeCommon.CLAIM_FINISH.equals(expensesApplication.getStatus())) {
					seAcApDeBean.setApproveFlg("2");
				}
			}
			model.addAttribute("currentRole", session.getAttribute(CodeCommon.SESSION_ROLEID));
			model.addAttribute("seAcApDeBean", seAcApDeBean);
			return "SE_AC_AP_DE";
		} catch (Exception e) {
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return CodeCommon.COMMON_ERROR_PAGE;
		}
	}
	
	/**
	 * 多个审批
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("approve")
	public String approve(Model model, HttpServletRequest request, HttpSession session) {
		try {
			String expenseAppNo = request.getParameter("expenseAppNo");
			String curPage = request.getParameter("curPage");
			
			String undoFinish = request.getParameter("undoFinish");
			String userId = (String) session.getAttribute(CodeCommon.SESSION_USERID);
			if (StringUtils.isEmpty(undoFinish)) {
				expensesManagementService.expenseApprovedAll(expenseAppNo, userId, "SE_AC_AP_LS");
			} else {
				expensesManagementService.expenseUndoFinished(expenseAppNo);
			}
			
			return "redirect:/SE_AC_AP_LS/page?curPage="+curPage;
		} catch (Exception e) {
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return CodeCommon.COMMON_ERROR_PAGE;
		}
	}
	
	/**
	 * 多个驳回
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("reject")
	public String reject(Model model, HttpServletRequest request, HttpSession session, @ModelAttribute SeAcApLsBean seAcApLsBean) {
		try {
			String expenseAppNoAll = request.getParameter("expenseAppNo");
			String curPage = request.getParameter("curPage");
			String userId = (String) session.getAttribute(CodeCommon.SESSION_USERID);
			expensesManagementService.expenseRejectAll(expenseAppNoAll, userId, "SE_AC_AP_LS", seAcApLsBean);

			return "redirect:/SE_AC_AP_LS/page?curPage="+curPage;
		} catch (Exception e) {
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return CodeCommon.COMMON_ERROR_PAGE;
		}
	}
	
}
