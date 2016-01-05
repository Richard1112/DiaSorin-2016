package com.diasorin.oa.mvc;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.diasorin.oa.common.CodeCommon;
import com.diasorin.oa.common.ErrCommon;
import com.diasorin.oa.common.QueryResult;
import com.diasorin.oa.dto.SeAcApLsListBean;
import com.diasorin.oa.dto.SeEcEpLsBean;
import com.diasorin.oa.dto.SeEmPeDeAdBean;
import com.diasorin.oa.dto.SeLgMnIfBean;
import com.diasorin.oa.model.EmployeeInfo;
import com.diasorin.oa.model.ExpensesApplication;
import com.diasorin.oa.service.AuthorityManagementService;
import com.diasorin.oa.service.EmployeeManagementService;
import com.diasorin.oa.service.ExpensesManagementService;
import com.diasorin.oa.service.MenuService;
import com.diasorin.oa.service.SystemManagementService;

@Controller
@RequestMapping("/SE_LG_MN_IF/*")
public class SeLgMnIfController extends BaseController {
	
	@Resource
	MenuService menuService;
	
	@Resource
	EmployeeManagementService employeeManagementService;
	
	@Resource
	SystemManagementService systemManagementService;
	
	@Resource
	AuthorityManagementService authorityManagementService;
	
	@Resource
	ExpensesManagementService expensesManagementService;
	
	private final String UPDPRMID = "SE_LG_MN_IF";
	
	/**
	 * 待操作画面
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("init")
	public String init(Model model, HttpServletRequest request, HttpSession session) {
		try {
			SeLgMnIfBean seLgMnIfBean = new SeLgMnIfBean();
			// 待操作画面有登陆人员的信息
			String userId = (String) session.getAttribute(CodeCommon.SESSION_USERID);
			EmployeeInfo employeeInfo = employeeManagementService.employeeInfoView(userId);
			seLgMnIfBean.setEmployeeNo(employeeInfo.getEmployeeNo());
			seLgMnIfBean.setName(employeeInfo.getEmployeeNameEn());
			seLgMnIfBean.setCostCenter(systemManagementService.getCostCenterInfo(employeeInfo.getDeptCode()).getCostCenterName());
			seLgMnIfBean.setRoleName(authorityManagementService.getRoleInfo(employeeInfo.getRoleCode()).getRoleName());
			seLgMnIfBean.setLevelName(systemManagementService.getEmployeeLevelInfo(employeeInfo.getLevelCode()).getLevelName());
			seLgMnIfBean.setCostCenterCode(employeeInfo.getDeptCode());
			seLgMnIfBean.setHeadPic(employeeInfo.getHeadPic());
			// 检索申请记录
			int maxresult = Integer.parseInt(getMessage(CodeCommon.TOLISTCOUNT));
			SeEcEpLsBean seEcEpLsBean = new SeEcEpLsBean();
			// 一时保存
			seEcEpLsBean.setStatus(CodeCommon.CLAIM_SAVE);
			QueryResult<ExpensesApplication> expenseAppList = expensesManagementService.expensesClaimListQuery(userId, 0, maxresult, "applicationDate DESC", seEcEpLsBean);
			seLgMnIfBean.setApplicationBeanList(expenseAppList.getResultlist());
			// 被打回
			seEcEpLsBean.setStatus(CodeCommon.CLAIM_REJECT);
			QueryResult<ExpensesApplication> expenseRejectList = expensesManagementService.expensesClaimListQuery(userId, 0, maxresult, "applicationDate DESC", seEcEpLsBean);
			if (expenseRejectList != null && expenseRejectList.getResultlist() != null && expenseRejectList.getResultlist().size() > 0) {
				for (ExpensesApplication app : expenseRejectList.getResultlist()) {
					app.setUpdUserKey(employeeManagementService.employeeInfoView(app.getUpdUserKey()).getEmployeeNameEn());
				}
			}
			seLgMnIfBean.setRejectBeanList(expenseRejectList.getResultlist());
			// 检索审批记录
			List<SeAcApLsListBean> approveList = expensesManagementService.getApproveTodoList(userId);
			seLgMnIfBean.setApproveBeanList(approveList);
			model.addAttribute("seLgMnIfBean", seLgMnIfBean);
			return "SE_LG_MN_IF";
		} catch (Exception e) {
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return CodeCommon.COMMON_ERROR_PAGE;
		}
		
	}
	
	
	/**
	 * 人员更新
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("editSelf")
	public String editSelf(Model model, HttpServletRequest request, HttpSession session) {
		try {
			SeEmPeDeAdBean seEmPeDeAdBean = new SeEmPeDeAdBean();
			String userId = (String) session.getAttribute(CodeCommon.SESSION_USERID);
			// 获取角色下拉框
			model.addAttribute("roleSelect", authorityManagementService.roleListQuery("", -1, -1).getResultlist());
			// 获取成本中心下拉框
			model.addAttribute("costCenterSelect", systemManagementService.costCenterListQuery(-1, -1).getResultlist());
			// 级别
			model.addAttribute("levelSelect", systemManagementService.employeeLevelListQuery(-1, -1).getResultlist());
			
			EmployeeInfo employeeInfo = employeeManagementService.employeeInfoView(userId);
			if (employeeInfo != null) {
				BeanUtils.copyProperties(seEmPeDeAdBean, employeeInfo);
				seEmPeDeAdBean.setHiddenserviceCostCenter(employeeInfo.getServiceCostCenter());
			} 
			model.addAttribute("seEmPeDeAdBean", seEmPeDeAdBean);
			return "SE_MN_IF_DE";
		} catch (Exception e) {
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return CodeCommon.COMMON_ERROR_PAGE;
		}
		
	}
	
	/**
	 * 员工更新操作
	 * @param model
	 * @param request
	 * @param session
	 * @param seEmPeDeAdBean
	 * @return
	 */
	@RequestMapping("updateEmployee")
	public String updateEmployee(Model model, HttpServletRequest request, HttpSession session, @ModelAttribute SeEmPeDeAdBean seEmPeDeAdBean) {
		try {
			// 员工更新
			EmployeeInfo employeeInfo = employeeManagementService.employeeInfoView(seEmPeDeAdBean.getEmployeeNo());
			employeeInfo.setEmployeeNameCn(seEmPeDeAdBean.getEmployeeNameCn());
			employeeInfo.setEmployeeNameEn(seEmPeDeAdBean.getEmployeeNameEn());
			// 图片
			employeeInfo.setHeadPic(seEmPeDeAdBean.getHeadPic());
			employeeInfo.setContactAddress(seEmPeDeAdBean.getContactAddress());
			employeeInfo.setEmail(seEmPeDeAdBean.getEmail());
			employeeInfo.setLiveLocation(seEmPeDeAdBean.getLiveLocation());
			employeeInfo.setMobilePhone(seEmPeDeAdBean.getMobilePhone());
			employeeInfo.setUpdPgmId(UPDPRMID);
			employeeInfo.setUpdUserKey((String)session.getAttribute(CodeCommon.SESSION_USERID));
			employeeManagementService.employeeInfoUpdate(employeeInfo);

			return "redirect:/SE_LG_MN_IF/init";

		} catch (Exception e) {
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return CodeCommon.COMMON_ERROR_PAGE;
		}
		
	}
	
}
