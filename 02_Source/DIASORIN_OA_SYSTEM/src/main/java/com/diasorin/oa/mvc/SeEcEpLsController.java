package com.diasorin.oa.mvc;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import com.diasorin.oa.dto.SeEcEpCaBean;
import com.diasorin.oa.dto.SeEcEpCaListBean;
import com.diasorin.oa.dto.SeEcEpDeBean;
import com.diasorin.oa.dto.SeEcEpDeListBean;
import com.diasorin.oa.dto.SeEcEpLsBean;
import com.diasorin.oa.dto.SeEcEpLsListBean;
import com.diasorin.oa.model.EmployeeInfo;
import com.diasorin.oa.model.ExpensePurposeSum;
import com.diasorin.oa.model.ExpensesApplication;
import com.diasorin.oa.model.ExpensesDetails;
import com.diasorin.oa.model.SysCostCenter;
import com.diasorin.oa.service.BaseService;
import com.diasorin.oa.service.EmployeeManagementService;
import com.diasorin.oa.service.ExpensesManagementService;
import com.diasorin.oa.service.SystemManagementService;

@Controller
@RequestMapping("/SE_EC_EP_LS/*")
public class SeEcEpLsController extends BaseController {
	
	@Resource
	ExpensesManagementService expensesManagementService;
	
	@Resource
	BaseService baseService;
	
	@Resource
	SystemManagementService systemManagementService;
	
	@Resource
	EmployeeManagementService employeeManagementService;
	
	/**
	 * 报销列表初期化
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("init")
	public String init(Model model, HttpServletRequest request, HttpSession session) {
		try {

			SeEcEpLsBean seEcEpLsBean = new SeEcEpLsBean();
			// 出差地类型
			model.addAttribute("TravelTypeSelect", baseService.getSelect(CodeCommon.COM004));
			// 状态
			model.addAttribute("StatusSelect", baseService.getSelect(CodeCommon.COM003));
			model.addAttribute("listCount", "0");
			model.addAttribute("seEcEpLsBean", seEcEpLsBean);
			return "SE_EC_EP_LS";
		} catch (Exception e) {
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return CodeCommon.COMMON_ERROR_PAGE;
		}
	}
	

	/**
	 * 报销列表
	 * @param model
	 * @param request
	 * @param session
	 * @param seEmPeLsBean
	 * @return
	 */
	@RequestMapping("search")
	public String search(Model model, HttpServletRequest request, HttpSession session, @ModelAttribute SeEcEpLsBean seEcEpLsBean) {
		try {
			// 出差地类型
			model.addAttribute("TravelTypeSelect", baseService.getSelect(CodeCommon.COM004));
			// 状态
			model.addAttribute("StatusSelect", baseService.getSelect(CodeCommon.COM003));
			
			// 获取
			String employeeNo = (String)session.getAttribute(CodeCommon.SESSION_USERID);

			List<SeEcEpLsListBean> beanList = new ArrayList<SeEcEpLsListBean>();
			// 将现在的检索数据放入session 中
			session.setAttribute("seEcEpLsBeanForSearch", seEcEpLsBean);
			// 取得配置文件的页码最大显示数据量
			int maxresult = Integer.parseInt(getMessage("recordCount"));
			int totalPage = 1;
			// 报销列表
			QueryResult<ExpensesApplication> expenseAppList = expensesManagementService.expensesClaimListQuery(employeeNo, 0, maxresult, "applicationDate DESC", seEcEpLsBean);
			
			// 是否有记录
			if (expenseAppList.getResultlist() != null && expenseAppList.getTotalrecord() > 0) {
				model.addAttribute("listCount", expenseAppList.getTotalrecord());
			}else{
				model.addAttribute("listCount", "0");
			}
			
			long resultCount = expenseAppList.getTotalrecord();
			totalPage=(int) ((resultCount % maxresult ==0) ? resultCount/maxresult : resultCount/maxresult+1);
			// 将数据转化成画面bean	
			if (expenseAppList.getTotalrecord() > 0) {
				int countNo = 0;
				for(ExpensesApplication expensesApp : expenseAppList.getResultlist()) {
					SeEcEpLsListBean listBean = new SeEcEpLsListBean();
					countNo++;
					listBean.setNo(String.valueOf(countNo));
					listBean.setApplicationDate(DateFormatCommon.formatDate(expensesApp.getApplicationDate(), "3"));
					listBean.setTavelLocationType(baseService.getSelect(CodeCommon.COM004, expensesApp.getTravelLocalType()));
					listBean.setExpenseSum(TypeConvertCommon.convertToCurrencyFmt(expensesApp.getExpenseSum()));
					listBean.setTravelReason(expensesApp.getTravelReason());
					listBean.setStatusContent(baseService.getSelect(CodeCommon.COM003, expensesApp.getStatus()));
					// 下一个可以申请的人(只有在pedding的时候显示)
					if (CodeCommon.CLAIM_PENDING.equals(expensesApp.getStatus())) {
						listBean.setNextAprrovePerson(expensesManagementService.getPerCanApprove(expensesApp.getExpensesAppNo()));
					}
					listBean.setExpensesAppNo(expensesApp.getExpensesAppNo());
					beanList.add(listBean);
				}
			}
			seEcEpLsBean.setBeanList(beanList);
			
			model.addAttribute("curPage", 1);
			model.addAttribute("searched", "1");
			model.addAttribute("totalPage", totalPage);
			model.addAttribute("seEcEpLsBean", seEcEpLsBean);
			return "SE_EC_EP_LS";
		} catch (Exception e) {
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return CodeCommon.COMMON_ERROR_PAGE;
		}
	}
	
	
	/**
	 * 报销一览任意按钮按下
	 * @param model
	 * @param request
	 * @param session
	 * @param seEmPeLsBean
	 * @return
	 */
	@RequestMapping("page")
	public String page(Model model, HttpServletRequest request, HttpSession session) {
		try {
			// 出差地类型
			model.addAttribute("TravelTypeSelect", baseService.getSelect(CodeCommon.COM004));
			// 状态
			model.addAttribute("StatusSelect", baseService.getSelect(CodeCommon.COM003));
			
			// 获取
			String employeeNo = (String)session.getAttribute(CodeCommon.SESSION_USERID);
			
			List<SeEcEpLsListBean> beanList = new ArrayList<SeEcEpLsListBean>();
			// 在SESSION 取得检索数据
			SeEcEpLsBean seEcEpLsBean = (SeEcEpLsBean) session.getAttribute("seEcEpLsBeanForSearch");
			int curPage = Integer.parseInt(request.getParameter("curPage"));
			// 取得配置文件的页码最大显示数据量
			int maxresult = Integer.parseInt(getMessage("recordCount"));
			int totalPage = 1;
			// 报销列表
			int startResult = (curPage - 1) * maxresult;
			QueryResult<ExpensesApplication> expenseAppList = expensesManagementService.expensesClaimListQuery(employeeNo, startResult, maxresult, "applicationDate DESC", seEcEpLsBean);
			
			// 是否有记录
			if (expenseAppList.getResultlist() != null && expenseAppList.getTotalrecord() > 0) {
				model.addAttribute("listCount", expenseAppList.getTotalrecord());
			}else{
				model.addAttribute("listCount", "0");
			}
			
			long resultCount = expenseAppList.getTotalrecord();
			totalPage=(int) ((resultCount % maxresult ==0) ? resultCount/maxresult : resultCount/maxresult+1);
			// 将数据转化成画面bean	
			if (expenseAppList.getTotalrecord() > 0) {
				int countNo = 0;
				for(ExpensesApplication expensesApp : expenseAppList.getResultlist()) {
					SeEcEpLsListBean listBean = new SeEcEpLsListBean();
					countNo++;
					listBean.setNo(String.valueOf(countNo));
					listBean.setApplicationDate(DateFormatCommon.formatDate(expensesApp.getApplicationDate(), "3"));
					listBean.setTavelLocationType(baseService.getSelect(CodeCommon.COM004, expensesApp.getTravelLocalType()));
					listBean.setExpenseSum(TypeConvertCommon.convertToCurrencyFmt(expensesApp.getExpenseSum()));
					listBean.setTravelReason(expensesApp.getTravelReason());
					listBean.setStatusContent(baseService.getSelect(CodeCommon.COM003, expensesApp.getStatus()));
					listBean.setExpensesAppNo(expensesApp.getExpensesAppNo());
					beanList.add(listBean);
				}
			}
			seEcEpLsBean.setBeanList(beanList);
			
			model.addAttribute("curPage", curPage);
			model.addAttribute("searched", "1");
			model.addAttribute("totalPage", totalPage);
			model.addAttribute("seEcEpLsBean", seEcEpLsBean);
			return "SE_EC_EP_LS";
		} catch (Exception e) {
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return CodeCommon.COMMON_ERROR_PAGE;
		}
	}
	
	/**
	 * 报销列表-->详细画面
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("toDetail")
	public String toDetail(Model model, HttpServletRequest request, HttpSession session) {
		try {
			// 先将session 清空
			session.setAttribute(CodeCommon.SESSION_CLAIM_INFO, null);
			// 获取当前申请人
			String userId = (String) session.getAttribute(CodeCommon.SESSION_USERID);
			// 报销申请编号
			String expenseAppNo = (String)request.getParameter("expenseAppNo");
			// 出差地类型
			model.addAttribute("TravelTypeSelect", baseService.getSelect(CodeCommon.COM004));

			// 获取成本中心下拉框
			List<SysCostCenter> costCenterListResult = new ArrayList<SysCostCenter>();
			List<SysCostCenter> costCenterList = systemManagementService.costCenterListQuery(-1, -1).getResultlist();
			EmployeeInfo employeeInfo = employeeManagementService.employeeInfoView(userId);
			String costCenter = employeeInfo.getDeptCode();
			String serviceCostCenter = employeeInfo.getServiceCostCenter();
			for (SysCostCenter cc : costCenterList) {
				if (costCenter.equals(cc.getCostCenterCode())){
					costCenterListResult.add(cc);
				}
				if (serviceCostCenter != null && serviceCostCenter.contains(cc.getCostCenterCode())){
					costCenterListResult.add(cc);
				}
			}
			model.addAttribute("costCenterList", costCenterListResult);
			// 获取报销申请
			ExpensesApplication expensesApplication = expensesManagementService.expensesClaimQuery(expenseAppNo);
			
			// 获取报销申请同一目的项目
			QueryResult<ExpensePurposeSum> purposeDetails = expensesManagementService.expensesClaimPurposeListQuery(expenseAppNo);
			
			SeEcEpCaBean seEcEpCaBean = new SeEcEpCaBean();
			seEcEpCaBean.setTravelLocalType(expensesApplication.getTravelLocalType());
			seEcEpCaBean.setTravelReason(expensesApplication.getTravelReason());
			seEcEpCaBean.setCostCenter(expensesApplication.getCostCenterCode());
			seEcEpCaBean.setExpensesAppNo(expenseAppNo);
			seEcEpCaBean.setAppStatus(expensesApplication.getStatus());
			seEcEpCaBean.setClaimDateFrom(DateFormatCommon.formatDate(expensesApplication.getTravelDateStart(), "3"));
			seEcEpCaBean.setClaimDateTo(DateFormatCommon.formatDate(expensesApplication.getTravelDateEnd(), "3"));
			// 明细项目
			List<SeEcEpCaListBean> beanList = new ArrayList<SeEcEpCaListBean>();
			if (purposeDetails != null && purposeDetails.getTotalrecord() > 0) {
				int count = 0;
				for (ExpensePurposeSum detail : purposeDetails.getResultlist()) {
					SeEcEpCaListBean bean = new SeEcEpCaListBean();
					//判断是否有错误
					QueryResult<ExpensesDetails> expenseDetailsForCheckError = expensesManagementService.expensesClaimDetailsListQuery(detail.getExpensesDetailsNo());
					boolean noError = true;
					if (expenseDetailsForCheckError != null) {
						for (ExpensesDetails detailError : expenseDetailsForCheckError.getResultlist()) {
							if(CodeCommon.DETAIL_ERROR_FLG.equals(detailError.getRejectErrorFlg())) {
								noError = false;
								break;
							}
						}
					}
					count++;
					bean.setNo(String.valueOf(count));
					bean.setDetailNo(detail.getExpensesDetailsNo());
					bean.setPurpose(detail.getExpensesPurpose());
					bean.setTotal(TypeConvertCommon.convertToCurrencyFmt(detail.getExpensesAmount()));
					bean.setExpensesAppNo(expenseAppNo);
					bean.setPurposeNo(detail.getExpensesDetailsNo());
					// 如果有错误
					if (!noError) {
						bean.setRejectErrorFlg("1");
					}
					beanList.add(bean);
				}
				seEcEpCaBean.setBeanList(beanList);
				model.addAttribute("listCount", purposeDetails.getTotalrecord());
			}
			model.addAttribute("seEcEpCaBean", seEcEpCaBean);
			
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
			
			
			// 将所有的数据放入SESSION中
			ClaimInfoBean claimInfoBean = new ClaimInfoBean();
			claimInfoBean.setTravelLocalType(expensesApplication.getTravelLocalType());
			claimInfoBean.setTravelReason(expensesApplication.getTravelReason());
			claimInfoBean.setCostCenter(expensesApplication.getCostCenterCode());
			claimInfoBean.setExpenseAppNo(expenseAppNo);
			claimInfoBean.setAppStatus(expensesApplication.getStatus());
			claimInfoBean.setClaimDateFrom(DateFormatCommon.formatDate(expensesApplication.getTravelDateStart(), "3"));
			claimInfoBean.setClaimDateTo(DateFormatCommon.formatDate(expensesApplication.getTravelDateEnd(), "3"));
			// purpose List
			List<SeEcEpDeBean> purposeList = new ArrayList<SeEcEpDeBean>();
			if (purposeDetails != null && purposeDetails.getTotalrecord() > 0) {
				for (ExpensePurposeSum detail : purposeDetails.getResultlist()) {
					SeEcEpDeBean bean = new SeEcEpDeBean();
					bean.setPurposeNo(detail.getExpensesDetailsNo());
					bean.setPurpose(detail.getExpensesPurpose());
					bean.setAmount(detail.getExpensesAmount());
					bean.setExpensesAppNo(expenseAppNo);
					bean.setHasPurposeNo(detail.getExpensesDetailsNo());
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
						listBean.setRejectErrorFlg(expensesDetails.getRejectErrorFlg());
						contentDetailList.add(listBean);
					}
					bean.setBeanList(contentDetailList);
					purposeList.add(bean);
				}
				claimInfoBean.setBeanList(purposeList);
				session.setAttribute(CodeCommon.SESSION_CLAIM_INFO, claimInfoBean);
			}
			
			return "SE_EC_EP_CA";
		} catch (Exception e) {
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return CodeCommon.COMMON_ERROR_PAGE;
		}
	}
	
}
