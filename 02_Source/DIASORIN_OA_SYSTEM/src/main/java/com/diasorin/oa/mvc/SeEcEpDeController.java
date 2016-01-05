package com.diasorin.oa.mvc;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.diasorin.oa.common.CodeCommon;
import com.diasorin.oa.common.DateFormatCommon;
import com.diasorin.oa.common.ErrCommon;
import com.diasorin.oa.common.TypeConvertCommon;
import com.diasorin.oa.dto.AuthorityByListBean;
import com.diasorin.oa.dto.ClaimInfoBean;
import com.diasorin.oa.dto.SeEcEpCaBean;
import com.diasorin.oa.dto.SeEcEpCaListBean;
import com.diasorin.oa.dto.SeEcEpDeBean;
import com.diasorin.oa.dto.SeEcEpDeListBean;
import com.diasorin.oa.model.EmployeeInfo;
import com.diasorin.oa.model.SysCostCenter;
import com.diasorin.oa.model.SysExpenses;
import com.diasorin.oa.service.BaseService;
import com.diasorin.oa.service.EmployeeManagementService;
import com.diasorin.oa.service.ExpensesManagementService;
import com.diasorin.oa.service.SystemManagementService;

@Controller
@RequestMapping("/SE_EC_EP_DE/*")
public class SeEcEpDeController extends BaseController {
	
	@Resource
	ExpensesManagementService expensesManagementService;
	
	@Resource
	EmployeeManagementService employeeManagementService;
	
	@Resource
	BaseService baseService;
	
	@Resource
	SystemManagementService systemManagementService;
	
	/**
	 * 报销申请初期化
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("init")
	public String init(Model model, HttpServletRequest request, HttpSession session) {
		try {
			// 初期化session
			session.setAttribute(CodeCommon.SESSION_CLAIM_INFO, null);
			model.addAttribute("CARRATE", super.getCarRate().toString());
			return "SE_EC_EP_DE";
		} catch (Exception e) {
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return CodeCommon.COMMON_ERROR_PAGE;
		}
		
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/saveSession")
	@ResponseBody
	public Map<String, Object> saveSession(HttpServletRequest request, HttpSession session, @RequestBody List list) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 获取当前目的NO
			Map<String,String> purposeMap = (Map<String,String>) list.get(0);
			String purposeNo = purposeMap.get("purposeNo");
			ClaimInfoBean claimInfo = (ClaimInfoBean) session.getAttribute(CodeCommon.SESSION_CLAIM_INFO);
			if (claimInfo.getBeanList().size() > 0) {
				// 先前有数据
				if (StringUtils.isEmpty(purposeNo)) {
					// 新增迁移过来
					SeEcEpDeBean beanToAdd = JsonToBean(list);
					beanToAdd.setPurposeNo(createTempPurposeNo());
					claimInfo.getBeanList().add(beanToAdd);
					session.setAttribute(CodeCommon.SESSION_CLAIM_INFO, claimInfo);
				} else {
					// 明细项目过来
					SeEcEpDeBean beanToAdd = JsonToBean(list);
					for (SeEcEpDeBean seEcEpDeBean : claimInfo.getBeanList()) {
						if (purposeNo.equals(seEcEpDeBean.getPurposeNo())) {
							seEcEpDeBean.setAmount(beanToAdd.getAmount());
							seEcEpDeBean.setPurpose(beanToAdd.getPurpose());
							seEcEpDeBean.setBeanList(beanToAdd.getBeanList());
						}
					}
					session.setAttribute(CodeCommon.SESSION_CLAIM_INFO, claimInfo);
				}
			} else {
				// 先前没有数据,纯粹新增数据
				SeEcEpDeBean beanToAdd = JsonToBean(list);
				beanToAdd.setPurposeNo(createTempPurposeNo());
				claimInfo.getBeanList().add(beanToAdd);
				session.setAttribute(CodeCommon.SESSION_CLAIM_INFO, claimInfo);
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
	 * 将数据提交到申请画面
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("submitDetail")
	public String submitDetail(Model model, HttpServletRequest request, HttpSession session) {
		try {
			// 获取当前申请人
			String userId = (String) session.getAttribute(CodeCommon.SESSION_USERID);
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
			// 将SESSION 中得数据放入到画面上。
			ClaimInfoBean claimInfo = (ClaimInfoBean) session.getAttribute(CodeCommon.SESSION_CLAIM_INFO);
			//内容已经变更过
			claimInfo.setHasChanged("1");
			session.setAttribute(CodeCommon.SESSION_CLAIM_INFO, claimInfo);
			
			SeEcEpCaBean seEcEpCaBean = new SeEcEpCaBean();
			seEcEpCaBean.setCostCenter(claimInfo.getCostCenter());
			seEcEpCaBean.setTravelLocalType(claimInfo.getTravelLocalType());
			seEcEpCaBean.setTravelReason(claimInfo.getTravelReason());
			seEcEpCaBean.setExpensesAppNo(claimInfo.getExpenseAppNo());
			seEcEpCaBean.setAppStatus(claimInfo.getAppStatus());
			seEcEpCaBean.setClaimDateFrom(claimInfo.getClaimDateFrom());
			seEcEpCaBean.setClaimDateTo(claimInfo.getClaimDateTo());
			// 明细项目
			List<SeEcEpCaListBean> beanList = new ArrayList<SeEcEpCaListBean>();
			int count = 0;
			for (SeEcEpDeBean deBean : claimInfo.getBeanList()) {
				SeEcEpCaListBean bean = new SeEcEpCaListBean();
				count++;
				bean.setNo(String.valueOf(count));
				bean.setPurpose(deBean.getPurpose());
				bean.setTotal(TypeConvertCommon.convertToCurrencyFmt(deBean.getAmount()));
				bean.setDetailNo(deBean.getPurposeNo());
				bean.setExpensesAppNo(deBean.getExpensesAppNo());
				bean.setPurposeNo(deBean.getHasPurposeNo());
				beanList.add(bean);
			}
			seEcEpCaBean.setBeanList(beanList);
			//内容已经变更过
			seEcEpCaBean.setHasChanged("1");
			model.addAttribute("seEcEpCaBean", seEcEpCaBean);
			// 将编辑后的信息显示在画面上
			return "SE_EC_EP_CA";
		} catch (Exception e) {
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return CodeCommon.COMMON_ERROR_PAGE;
		}
		
	}
	
	
	/**
	 * 报销明细画面回到报销申请画面
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("detailBackToClaim")
	public String detailBackToClaim(Model model, HttpServletRequest request, HttpSession session) {
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
				seEcEpCaBean.setHasChanged(claimInfoBean.getHasChanged());
				
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
			return "SE_EC_EP_CA";
		} catch (Exception e) {
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return CodeCommon.COMMON_ERROR_PAGE;
		}
	}
	
	/**
	 * 将画面的JSON数据转成以PURPOSE为单位的一条数据
	 * @param list
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private SeEcEpDeBean JsonToBean(List list) throws Exception{
		SeEcEpDeBean deBean = new SeEcEpDeBean();
		BigDecimal totalAmount = BigDecimal.ZERO;
		List<SeEcEpDeListBean> detailList = new ArrayList<SeEcEpDeListBean>();
		for(int i = 0 ; i < list.size() ; i++) {
			Map<String,String> detailMap = (Map<String,String>) list.get(i);
			
			SeEcEpDeListBean deListBean = new SeEcEpDeListBean();
			// 报销项目
			String expenseItem = detailMap.get("expenseType").split(",")[0];
			// 项目计费方式
			String timeMethod = detailMap.get("expenseType").split(",")[1];
			if (CodeCommon.TIME_METHOD_DAY.equals(timeMethod)) {
				// 天计费
				deListBean.setDayTo(detailMap.get("dateTo"));
			} else if (CodeCommon.TIME_METHOD_SEC.equals(timeMethod)){
				// 区间计费
				deListBean.setDayFrom(detailMap.get("dateFrom"));
				deListBean.setDayTo(detailMap.get("dateTo"));
			} else {
				// 月计费
				deListBean.setDayTo(DateFormatCommon.getMonthLastDayToYYYYMMDD(detailMap.get("dateTo")));
			}
			deListBean.setNo(detailMap.get("detailNo"));
			deListBean.setExpenseType(expenseItem);
			deListBean.setLocation(detailMap.get("location"));
			if (expenseItem.equals(CodeCommon.PRIVATE_CAR_FOR_BUSINESS)) {
				// 是CAR RATE
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
				deListBean.setExpenseAmount(TypeConvertCommon.
						convertToCurrencyFmt(new BigDecimal(detailMap.get("amount").replaceAll(",", "")).multiply(rate)));
			} else {
				deListBean.setExpenseAmount(TypeConvertCommon.convertToCurrencyFmt(new BigDecimal(detailMap.get("amount").replaceAll(",", ""))));
			}
			totalAmount = totalAmount.add(new BigDecimal(deListBean.getExpenseAmount().replaceAll(",", "")));
			
			deListBean.setComments(detailMap.get("comment"));
			deListBean.setTimeMethod(timeMethod);
			deListBean.setRejectErrorFlg(detailMap.get("rejectErrorFlg"));
			detailList.add(deListBean);
		}
		deBean.setBeanList(detailList);
		deBean.setAmount(totalAmount);
		deBean.setPurpose(((Map)list.get(0)).get("purpose").toString());
		return deBean;
	}
	
	/**
	 * 创建临时的PURPOSE NO
	 * @return
	 */
	private String createTempPurposeNo(){
		SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
		return sdf.format(new Date());
	}
	
	/**
	 * 选择父类项目后得到子类项目
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/getSubItem")
	public Map<String, Object> getSubItem(HttpServletRequest request, HttpSession session) {
		Map<String, Object> mapReturn = new HashMap<String, Object>();
		try {
			String fartherItem = request.getParameter("expenseItem");
			boolean hasSub = false;
			List<SysExpenses> subExpenseList = systemManagementService.getSubItem(fartherItem);
			if (subExpenseList != null &&  subExpenseList.size() > 0) {
				hasSub = true;
			}
			mapReturn.put("subExpenseList", subExpenseList);
			mapReturn.put("hasSub", hasSub);
			mapReturn.put("isException", false);
			return mapReturn;
		} catch (Exception e) {
			logger.error(e.getMessage());
			mapReturn.put("isException", true);
			ErrCommon.errOut(e);
			return null;
		}
		
	}
	
	/**
	 * 得到当前子项目的下拉框
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/getCurrentItem")
	public Map<String, Object> getCurrentItem(HttpServletRequest request, HttpSession session) {
		Map<String, Object> mapReturn = new HashMap<String, Object>();
		try {
			String subItem = request.getParameter("expenseItem");
			// 得到当前的父费用
			
			SysExpenses farExpense = systemManagementService.getFartherExpenses(subItem);
			
			List<SysExpenses> currentExpenseList = systemManagementService.getSubItem(farExpense.getExpenseCode());

			mapReturn.put("currentExpenseList", currentExpenseList);
			mapReturn.put("farExpense", farExpense);
			mapReturn.put("isException", false);
			return mapReturn;
		} catch (Exception e) {
			logger.error(e.getMessage());
			mapReturn.put("isException", true);
			ErrCommon.errOut(e);
			return null;
		}
		
	}
	
}
