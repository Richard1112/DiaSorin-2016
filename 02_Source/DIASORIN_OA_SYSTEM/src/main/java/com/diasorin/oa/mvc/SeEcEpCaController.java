package com.diasorin.oa.mvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.diasorin.oa.common.CodeCommon;
import com.diasorin.oa.common.DateFormatCommon;
import com.diasorin.oa.common.ErrCommon;
import com.diasorin.oa.common.TypeConvertCommon;
import com.diasorin.oa.dto.AuthorityByListBean;
import com.diasorin.oa.dto.ClaimInfoBean;
import com.diasorin.oa.dto.SeAcApDeBean;
import com.diasorin.oa.dto.SeEcEpCaBean;
import com.diasorin.oa.dto.SeEcEpCaListBean;
import com.diasorin.oa.dto.SeEcEpDeBean;
import com.diasorin.oa.dto.SeEcEpDeListBean;
import com.diasorin.oa.model.EmployeeInfo;
import com.diasorin.oa.model.ExpensesApplication;
import com.diasorin.oa.model.SysCostCenter;
import com.diasorin.oa.model.SysExpenses;
import com.diasorin.oa.service.BaseService;
import com.diasorin.oa.service.EmployeeManagementService;
import com.diasorin.oa.service.ExpensesManagementService;
import com.diasorin.oa.service.SystemManagementService;

@Controller
@RequestMapping("/SE_EC_EP_CA/*")
public class SeEcEpCaController extends BaseController {
	
	@Resource
	ExpensesManagementService expensesManagementService;
	
	@Resource
	EmployeeManagementService employeeManagementService;
	
	@Resource
	BaseService baseService;
	
	@Resource
	SystemManagementService systemManagementService;
	
	/**
	 * 报销申请新增初期化
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("init")
	public String init(Model model, HttpServletRequest request, HttpSession session) {
		try {
			SeEcEpCaBean seEcEpCaBean = new SeEcEpCaBean();
			// 获取当前申请人
			String userId = (String) session.getAttribute(CodeCommon.SESSION_USERID);
			// 出差地类型
			model.addAttribute("TravelTypeSelect", baseService.getSelect(CodeCommon.COM004));
			// 状态
			model.addAttribute("StatusSelect", baseService.getSelect(CodeCommon.COM003));
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
			// 新增初期化的时候应该将明细的SESSION清空
			ClaimInfoBean claimInfoBean = new ClaimInfoBean();
			claimInfoBean.setAppStatus(CodeCommon.CLAIM_SAVE);
			session.setAttribute(CodeCommon.SESSION_CLAIM_INFO, claimInfoBean);
			model.addAttribute("costCenterList", costCenterListResult);
			model.addAttribute("listCount", "0");
			seEcEpCaBean.setAppStatus(CodeCommon.CLAIM_SAVE);
			model.addAttribute("seEcEpCaBean", seEcEpCaBean);
			return "SE_EC_EP_CA";
		} catch (Exception e) {
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return CodeCommon.COMMON_ERROR_PAGE;
		}
	}
	
	
	/**
	 * 报销申请详细画面
	 * @param model
	 * @param request
	 * @param session
	 * @param seEmPeDeAdBean
	 * @return
	 */
	@RequestMapping("toAddDetail")
	public String toAddDetail(Model model, HttpServletRequest request, HttpSession session, @ModelAttribute SeEcEpCaBean seEcEpCaBean) {
		try {
			SeEcEpDeBean seEcEpDeBean = new SeEcEpDeBean(); 
			
			ClaimInfoBean claimInfoBean = (ClaimInfoBean) session.getAttribute(CodeCommon.SESSION_CLAIM_INFO);
			
			if (claimInfoBean != null) {
				claimInfoBean.setCostCenter(seEcEpCaBean.getCostCenter());
				claimInfoBean.setTravelLocalType(seEcEpCaBean.getTravelLocalType());
				claimInfoBean.setTravelReason(seEcEpCaBean.getTravelReason());
				claimInfoBean.setClaimDateFrom(seEcEpCaBean.getClaimDateFrom());
				claimInfoBean.setClaimDateTo(seEcEpCaBean.getClaimDateTo());
			} else {
				claimInfoBean = new ClaimInfoBean();
				claimInfoBean.setCostCenter(seEcEpCaBean.getCostCenter());
				claimInfoBean.setTravelLocalType(seEcEpCaBean.getTravelLocalType());
				claimInfoBean.setTravelReason(seEcEpCaBean.getTravelReason());
				claimInfoBean.setClaimDateFrom(seEcEpCaBean.getClaimDateFrom());
				claimInfoBean.setClaimDateTo(seEcEpCaBean.getClaimDateTo());
			}
			
			session.setAttribute(CodeCommon.SESSION_CLAIM_INFO, claimInfoBean);
			//报销项目,这里值抽取最大父类报销项目
			model.addAttribute("expenseItemList", systemManagementService.getFirstFarItem());
			seEcEpDeBean.setAppStatus(seEcEpCaBean.getAppStatus());
			model.addAttribute("seEcEpDeBean", seEcEpDeBean);
			model.addAttribute("CARRATE", super.getCarRate().toString());
			return "SE_EC_EP_DE";
		} catch (Exception e) {
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return CodeCommon.COMMON_ERROR_PAGE;
		}
		
	}
	
	
	/**
	 * 报销申请详细画面
	 * @param model
	 * @param request
	 * @param session
	 * @param seEmPeDeAdBean
	 * @return
	 */
	@RequestMapping("toDetail")
	public String toDetail(Model model, HttpServletRequest request, HttpSession session, @ModelAttribute SeEcEpCaBean seEcEpCaBean) {
		try {
			SeEcEpDeBean seEcEpDeBean = new SeEcEpDeBean(); 
			String dataDetailNo = request.getParameter("detailNo");
			// 这里需要在session中取值
			ClaimInfoBean claimInfoBean = (ClaimInfoBean) session.getAttribute(CodeCommon.SESSION_CLAIM_INFO);
			if (CodeCommon.CLAIM_SAVE.equals(claimInfoBean.getAppStatus())
				|| CodeCommon.CLAIM_REJECT.equals(claimInfoBean.getAppStatus())) {
				claimInfoBean.setCostCenter(seEcEpCaBean.getCostCenter());
				claimInfoBean.setTravelLocalType(seEcEpCaBean.getTravelLocalType());
				claimInfoBean.setTravelReason(seEcEpCaBean.getTravelReason());
				claimInfoBean.setClaimDateFrom(seEcEpCaBean.getClaimDateFrom());
				claimInfoBean.setClaimDateTo(seEcEpCaBean.getClaimDateTo());
			}
			
			session.setAttribute(CodeCommon.SESSION_CLAIM_INFO, claimInfoBean);
			
			// 这里SESSION 一定是已经创建好的。
			SeEcEpDeBean deBean = claimInfoBean.getBeanList().get(Integer.valueOf(dataDetailNo));
			
			// 抽取申请明细
			List<SeEcEpDeListBean> detailList = deBean.getBeanList();
			for (SeEcEpDeListBean bean : detailList) {
				bean.setExpenseAmount(bean.getExpenseAmount().replaceAll(",", ""));
			}
			List<SeEcEpDeListBean> newBeanList = new ArrayList<SeEcEpDeListBean>();
			for (SeEcEpDeListBean bean : detailList) {
				SeEcEpDeListBean newBean = new SeEcEpDeListBean();
				BeanUtils.copyProperties(newBean, bean);
				if (bean.getExpenseType().equals(CodeCommon.PRIVATE_CAR_FOR_BUSINESS)) {
					// 如果是CAR RATE
					BigDecimal toAmount = new BigDecimal(bean.getExpenseAmount().replaceAll(",", ""));
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
					newBean.setExpenseAmount(toAmount.
							divide(rate, 2, BigDecimal.ROUND_UP).toString());
				} else {
					newBean.setExpenseAmount(bean.getExpenseAmount().replaceAll(",", ""));
				}
				
				newBeanList.add(newBean);
			}
			// Purpose
			seEcEpDeBean.setPurpose(deBean.getPurpose());
			seEcEpDeBean.setPurposeNo(deBean.getPurposeNo());
			seEcEpDeBean.setAppStatus(claimInfoBean.getAppStatus());
			seEcEpDeBean.setBeanList(newBeanList);
			
			
			//报销项目,这里值抽取最大父类报销项目
			model.addAttribute("expenseItemList", systemManagementService.getFirstFarItem());
			
			model.addAttribute("seEcEpDeBean", seEcEpDeBean);
			model.addAttribute("CARRATE", super.getCarRate().toString());
			return "SE_EC_EP_DE";
		} catch (Exception e) {
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return CodeCommon.COMMON_ERROR_PAGE;
		}
		
	}
	
	/**
	 * 申请画面删除行
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/deletePurpose")
	@ResponseBody
	public Map<String, Object> deletePurpose(HttpServletRequest request, HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 获取当前目的NO
			String purposeNo = request.getParameter("purposeNo");

			ClaimInfoBean claimInfo = (ClaimInfoBean) session.getAttribute(CodeCommon.SESSION_CLAIM_INFO);
			
			List<SeEcEpDeBean> seEcEpDeBeanList = new ArrayList<SeEcEpDeBean>();
			for (SeEcEpDeBean seEcEpDeBean : claimInfo.getBeanList()) {
				if (!purposeNo.equals(seEcEpDeBean.getPurposeNo())) {
					seEcEpDeBeanList.add(seEcEpDeBean);
				}
			}
			claimInfo.setBeanList(seEcEpDeBeanList);
			// 重置SESSION
			session.setAttribute(CodeCommon.SESSION_CLAIM_INFO, claimInfo);
			map.put("isException", false);
			return map;
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put("isException", true);
			ErrCommon.errOut(e);
			return null;
		}
	}
	
	/**
	 * 判断当前出差地类型下，申请出差项目是否已经超过了上线
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/checkDataIsOver")
	@ResponseBody
	public Map<String, Object> checkDataIsOver(HttpServletRequest request, HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// SESSION USERID
			String userId = (String) session.getAttribute(CodeCommon.SESSION_USERID);
			EmployeeInfo employeeInfo = employeeManagementService.employeeInfoView(userId);
			// 出差地类型
			String travelType = request.getParameter("travelLocalType");
			// 超过最高上线的项目
			String expenseName = "";
			ClaimInfoBean claimInfo = (ClaimInfoBean) session.getAttribute(CodeCommon.SESSION_CLAIM_INFO);
			List<SeEcEpDeListBean> allDeListBean = new ArrayList<SeEcEpDeListBean>();
			boolean isOverAllow = false;
			for (SeEcEpDeBean seEcEpDeBean : claimInfo.getBeanList()) {
				if (isOverAllow) break;
				for (SeEcEpDeListBean seEcEpDeListBean : seEcEpDeBean.getBeanList()) {
					allDeListBean.add(seEcEpDeListBean);
					BigDecimal allowUp = expensesManagementService.getExpenseUpAllow(travelType, employeeInfo.getLevelCode(), seEcEpDeListBean.getExpenseType());
					if (allowUp == null) continue;
					if (CodeCommon.ACCOMODATION_IN_HOTEL.equals(seEcEpDeListBean.getExpenseType())) {
						// 是住宿费用的
						BigDecimal betDay = new BigDecimal(DateFormatCommon.getBetweenDays(seEcEpDeListBean.getDayFrom().replaceAll("/", ""), 
								seEcEpDeListBean.getDayTo().replaceAll("/", "")));
						// 住宿费用 天数 - 1
						if (new BigDecimal(seEcEpDeListBean.getExpenseAmount().replaceAll(",", "")).
								compareTo(allowUp.multiply(betDay)) > 0) {
							// 超过上线
							isOverAllow = true;
							expenseName = systemManagementService.getExpensesItemInfo(seEcEpDeListBean.getExpenseType()).getExpenseName();
							break;
						}
						
					} else {
						if (new BigDecimal(seEcEpDeListBean.getExpenseAmount().replaceAll(",", "")).compareTo(allowUp) > 0) {
							// 超过上线
							isOverAllow = true;
							expenseName = systemManagementService.getExpensesItemInfo(seEcEpDeListBean.getExpenseType()).getExpenseName();
							break;
						}
					}
					
				}
			}
			
			// 以月来计费的项目不可以重复(单个申请单子)
			boolean hasSameExpense = false;
			for (int i = 0; i < allDeListBean.size() ; i++) {
				if (CodeCommon.TIME_METHOD_MON.equals(allDeListBean.get(i).getTimeMethod())) {
					for (int j = 0; j < allDeListBean.size() ; j++) {
						if (i != j 
							&& allDeListBean.get(i).getExpenseType().equals(allDeListBean.get(j).getExpenseType())
							&& allDeListBean.get(i).getDayTo().equals(allDeListBean.get(j).getDayTo())){
							hasSameExpense = true;
						}
					}
				}
				if (hasSameExpense) break;
			}
			
			// 以月来计费的项目不可以重复(不同的单子)
			if (!hasSameExpense) {
				for (int i = 0; i < allDeListBean.size() ; i++) {
					if (CodeCommon.TIME_METHOD_MON.equals(allDeListBean.get(i).getTimeMethod())) {
						hasSameExpense = expensesManagementService.isExitSameExpenseByMonth(allDeListBean.get(i).getExpenseType()
								, allDeListBean.get(i).getDayTo(), claimInfo.getExpenseAppNo(), userId);
					}
					if (hasSameExpense) break;
				}
			}
			
			
			// 将数据以申请项目排序
//			Collections.sort(allDeListBean, new Comparator<SeEcEpDeListBean>() {
//
//				@Override
//				public int compare(SeEcEpDeListBean o1, SeEcEpDeListBean o2) {
//					return o1.getExpenseType().compareTo(o2.getExpenseType());
//				}
//			});
			
			// 判断申请上线
			if (isOverAllow) {
				map.put("isOver", true);
				map.put("OverContent", expenseName);
			} else {
				map.put("isOver", false);
			}
			// 以月来计费的项目不可以重复
			if (hasSameExpense) {
				map.put("hasSameExpenseMonth", true);
			} else {
				map.put("hasSameExpenseMonth", false);
			}
			
			map.put("isException", false);
			return map;
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put("isException", true);
			ErrCommon.errOut(e);
			return null;
		}
	}

	/**
	 * 将数据一时保存
	 * @param model
	 * @param request
	 * @param session
	 * @param seEmPeDeAdBean
	 * @return
	 */
	@RequestMapping("claimContentSave")
	public String claimContentSave(Model model, HttpServletRequest request, HttpSession session, @ModelAttribute SeEcEpCaBean seEcEpCaBean) {
		try {
			// SESSION USERID
			String userId = (String) session.getAttribute(CodeCommon.SESSION_USERID);
			// 这里需要在session中取值
			ClaimInfoBean claimInfoBean = (ClaimInfoBean) session.getAttribute(CodeCommon.SESSION_CLAIM_INFO);
			claimInfoBean.setCostCenter(seEcEpCaBean.getCostCenter());
			claimInfoBean.setTravelLocalType(seEcEpCaBean.getTravelLocalType());
			claimInfoBean.setTravelReason(seEcEpCaBean.getTravelReason());
			claimInfoBean.setClaimDateFrom(seEcEpCaBean.getClaimDateFrom());
			claimInfoBean.setClaimDateTo(seEcEpCaBean.getClaimDateTo());
			expensesManagementService.expensesClaim(claimInfoBean, false, seEcEpCaBean, userId);
			
			model = createReturnDto(model, session);
			model.addAttribute("hasSaved", "1");
			
			return "SE_EC_EP_CA";
		} catch (Exception e) {
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return CodeCommon.COMMON_ERROR_PAGE;
		}
	}
	
	
	/**
	 * 将申请数据提交
	 * @param model
	 * @param request
	 * @param session
	 * @param seEmPeDeAdBean
	 * @return
	 */
	@RequestMapping("claimContentSubmit")
	public String claimContentSubmit(Model model, HttpServletRequest request, HttpSession session, @ModelAttribute SeEcEpCaBean seEcEpCaBean) {
		try {
			// SESSION USERID
			String userId = (String) session.getAttribute(CodeCommon.SESSION_USERID);
			// 这里需要在session中取值
			ClaimInfoBean claimInfoBean = (ClaimInfoBean) session.getAttribute(CodeCommon.SESSION_CLAIM_INFO);
			claimInfoBean.setCostCenter(seEcEpCaBean.getCostCenter());
			claimInfoBean.setTravelLocalType(seEcEpCaBean.getTravelLocalType());
			claimInfoBean.setTravelReason(seEcEpCaBean.getTravelReason());
			claimInfoBean.setClaimDateFrom(seEcEpCaBean.getClaimDateFrom());
			claimInfoBean.setClaimDateTo(seEcEpCaBean.getClaimDateTo());
			expensesManagementService.expensesClaim(claimInfoBean, true, seEcEpCaBean, userId);
			
			model = createReturnDto(model, session);
			model.addAttribute("hasSaved", "2");
			
			return "SE_EC_EP_CA";
		} catch (Exception e) {
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return CodeCommon.COMMON_ERROR_PAGE;
		}
		
	}
	
	
	/**
	 * review 画面显示
	 * @param model
	 * @param request
	 * @param session
	 * @param seEmPeDeAdBean
	 * @return
	 */
	@RequestMapping("review")
	public String review(Model model, HttpServletRequest request, HttpSession session, @ModelAttribute SeEcEpCaBean seEcEpCaBean) {
		try {
			String userId = (String)session.getAttribute(CodeCommon.SESSION_USERID);
			EmployeeInfo info = employeeManagementService.employeeInfoView(userId);			
			// 这里需要在session中取值
			ClaimInfoBean claimInfoBean = (ClaimInfoBean) session.getAttribute(CodeCommon.SESSION_CLAIM_INFO);
			SeAcApDeBean seAcApDeBean = new SeAcApDeBean();
			// 头部数据
			if (!StringUtils.isEmpty(claimInfoBean.getCostCenter())) {
				seAcApDeBean.setCostCenterName(systemManagementService.getCostCenterInfo(claimInfoBean.getCostCenter()).getCostCenterName());
				seAcApDeBean.setCostCenter(claimInfoBean.getCostCenter());
			}
			if (!StringUtils.isEmpty(claimInfoBean.getTravelLocalType())) {
				seAcApDeBean.setTravelLocalType(baseService.getSelect(CodeCommon.COM004, claimInfoBean.getTravelLocalType()));
			}
			seAcApDeBean.setTravelReason(claimInfoBean.getTravelReason());
			seAcApDeBean.setEmployee(info.getEmployeeNameEn());
			seAcApDeBean.setAppStatus(claimInfoBean.getAppStatus());
			seAcApDeBean.setClaimDateFrom(claimInfoBean.getClaimDateFrom());
			seAcApDeBean.setClaimDateTo(claimInfoBean.getClaimDateTo());
			// 出差发生目的
			List<SeEcEpDeBean> purposeList = new ArrayList<SeEcEpDeBean>();
			
			// 出差发生明细
			List<SeEcEpDeListBean> detailList = new ArrayList<SeEcEpDeListBean>();
			BigDecimal totalAmount = BigDecimal.ZERO;
			for (SeEcEpDeBean purposeBean : claimInfoBean.getBeanList()) {
				purposeList.add(purposeBean);
				totalAmount = totalAmount.add(purposeBean.getAmount());
				for (SeEcEpDeListBean detailBean : purposeBean.getBeanList()) {
					SeEcEpDeListBean detail = new SeEcEpDeListBean();
					BeanUtils.copyProperties(detail, detailBean);
					detail.setExpenseType(systemManagementService.getExpensesItemInfo(detailBean.getExpenseType()).getExpenseName());
					detailList.add(detail);
				}
			}
			seAcApDeBean.setPurposeList(purposeList);
			seAcApDeBean.setDetailList(detailList);
			seAcApDeBean.setTotalAmount(totalAmount.toString());
			if (!StringUtils.isEmpty(claimInfoBean.getExpenseAppNo())) {
				seAcApDeBean.setExpenseAppNo(expensesManagementService.concatAuthority(claimInfoBean.getExpenseAppNo()));
				// 这里将所有审批过的人的信息显示在画面上面
				List<AuthorityByListBean> hisList = expensesManagementService.getAuthorityByHisList(claimInfoBean.getExpenseAppNo());
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
				// 获取报销申请
				ExpensesApplication expensesApplication = expensesManagementService.expensesClaimQuery(claimInfoBean.getExpenseAppNo());
				seAcApDeBean.setApplicationDate(DateFormatCommon.formatDate(expensesApplication.getApplicationDate(), "3"));
				
			}
			
			seAcApDeBean.setMode("0");
			model.addAttribute("seAcApDeBean", seAcApDeBean);
			
			return "SE_AC_AP_DE";
		} catch (Exception e) {
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return CodeCommon.COMMON_ERROR_PAGE;
		}
		
	}
	
	/**
	 * 返回当前画面，重新抽一把数据
	 * @param model
	 * @param session
	 * @return
	 * @throws Exception 
	 */
	private Model createReturnDto(Model model, HttpSession session) throws Exception {

		SeEcEpCaBean seEcEpCaBean = new SeEcEpCaBean();
		// 获取当前申请人
		String userId = (String) session.getAttribute(CodeCommon.SESSION_USERID);
		// 出差地类型
		model.addAttribute("TravelTypeSelect", baseService.getSelect(CodeCommon.COM004));
		// 状态
		model.addAttribute("StatusSelect", baseService.getSelect(CodeCommon.COM003));
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

		
		ClaimInfoBean claimInfoBean = (ClaimInfoBean) session.getAttribute(CodeCommon.SESSION_CLAIM_INFO);
		if (claimInfoBean != null) {
			seEcEpCaBean.setCostCenter(claimInfoBean.getCostCenter());
			seEcEpCaBean.setTravelLocalType(claimInfoBean.getTravelLocalType());
			seEcEpCaBean.setTravelReason(claimInfoBean.getTravelReason());
			seEcEpCaBean.setExpensesAppNo(claimInfoBean.getExpenseAppNo());
			seEcEpCaBean.setAppStatus(claimInfoBean.getAppStatus());
			seEcEpCaBean.setClaimDateFrom(claimInfoBean.getClaimDateFrom());
			seEcEpCaBean.setClaimDateTo(claimInfoBean.getClaimDateTo());
			
			List<SeEcEpCaListBean> beanList = new ArrayList<SeEcEpCaListBean>();
			if (claimInfoBean.getBeanList() != null && claimInfoBean.getBeanList().size() > 0) {
				int count = 0;
				for (SeEcEpDeBean deBean : claimInfoBean.getBeanList()) {
					count++;
					SeEcEpCaListBean caListBean = new SeEcEpCaListBean();
					caListBean.setNo(String.valueOf(count));
					caListBean.setPurpose(deBean.getPurpose());
					caListBean.setTotal(TypeConvertCommon.convertToCurrencyFmt(deBean.getAmount()));
					caListBean.setDetailNo(deBean.getPurposeNo());
					caListBean.setExpensesAppNo(deBean.getExpensesAppNo());
					caListBean.setPurposeNo(deBean.getHasPurposeNo());
					boolean noError = true;
					List<SeEcEpDeListBean> expenseDetailsForCheckError = deBean.getBeanList();
					if (expenseDetailsForCheckError != null) {
						for (SeEcEpDeListBean detailError : expenseDetailsForCheckError) {
							if(CodeCommon.DETAIL_ERROR_FLG.equals(detailError.getRejectErrorFlg())) {
								noError = false;
								break;
							}
						}
					}
					if (!noError) {
						caListBean.setRejectErrorFlg(CodeCommon.DETAIL_ERROR_FLG);
					}
					beanList.add(caListBean);
				}
				seEcEpCaBean.setBeanList(beanList);
				model.addAttribute("listCount", count);
			} else {
				model.addAttribute("listCount", "0");
			}
			if (!StringUtils.isEmpty(claimInfoBean.getExpenseAppNo())) {
				// 这里将所有审批过的人的信息显示在画面上面
				List<AuthorityByListBean> hisList = expensesManagementService.getAuthorityByHisList(claimInfoBean.getExpenseAppNo());
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
			}
			
		} else {
			model.addAttribute("listCount", "0");
		}
		
		model.addAttribute("seEcEpCaBean", seEcEpCaBean);
		
		return model;
	}
}
