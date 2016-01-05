package com.diasorin.oa.mvc;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.diasorin.oa.common.CodeCommon;
import com.diasorin.oa.common.ErrCommon;
import com.diasorin.oa.dto.SeEmPeDeAdBean;
import com.diasorin.oa.model.EmployeeInfo;
import com.diasorin.oa.service.AuthorityManagementService;
import com.diasorin.oa.service.BaseService;
import com.diasorin.oa.service.EmployeeManagementService;
import com.diasorin.oa.service.SystemManagementService;

@Controller
@RequestMapping("/SE_EM_PE_DE/*")
public class SeEmPeDeController extends BaseController {
	
	private static final String UPDPRMID = "SE_EM_PE_DE";
	
	@Resource
	EmployeeManagementService employeeManagementService;
	
	@Resource
	BaseService baseService;
	
	@Resource
	SystemManagementService systemManagementService;
	
	@Resource
	AuthorityManagementService authorityManagementService;
	
	/**
	 * 人员更新画面初期化
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("init/{employeeNo}")
	public String init(Model model, HttpServletRequest request, HttpSession session, @PathVariable String employeeNo) {
		try {
			
			SeEmPeDeAdBean seEmPeDeAdBean = new SeEmPeDeAdBean();
			// 获取性别下拉框
			model.addAttribute("sexSelect", baseService.getSelect(CodeCommon.COM001));
			// 获取角色下拉框
			model.addAttribute("roleSelect", authorityManagementService.roleListQuery("", -1, -1).getResultlist());
			// 获取成本中心下拉框
			model.addAttribute("costCenterSelect", systemManagementService.costCenterListQuery(-1, -1).getResultlist());
			// 级别
			model.addAttribute("levelSelect", systemManagementService.employeeLevelListQuery(-1, -1).getResultlist());
			
			EmployeeInfo employeeInfo = employeeManagementService.employeeInfoView(employeeNo);
			String curPage = request.getParameter("curPage");
			if (employeeInfo != null) {
				BeanUtils.copyProperties(seEmPeDeAdBean, employeeInfo);
				seEmPeDeAdBean.setHiddenserviceCostCenter(employeeInfo.getServiceCostCenter());
			} 
			model.addAttribute("seEmPeDeAdBean", seEmPeDeAdBean);
			model.addAttribute("curPage", curPage);
			return "SE_EM_PE_DE";
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
			String curPage = request.getParameter("curPage");
			// 员工更新
			EmployeeInfo employeeInfo = employeeManagementService.employeeInfoView(seEmPeDeAdBean.getEmployeeNo());
			employeeInfo.setEmployeeNameCn(seEmPeDeAdBean.getEmployeeNameCn());
			employeeInfo.setEmployeeNameEn(seEmPeDeAdBean.getEmployeeNameEn());
			employeeInfo.setHeadPic(seEmPeDeAdBean.getHeadPic());
			employeeInfo.setLevelCode(seEmPeDeAdBean.getLevelCode());
			employeeInfo.setDeptCode(seEmPeDeAdBean.getDeptCode());
			employeeInfo.setRoleCode(seEmPeDeAdBean.getRoleCode());
			employeeInfo.setContactAddress(seEmPeDeAdBean.getContactAddress());
			employeeInfo.setServiceCostCenter(seEmPeDeAdBean.getServiceCostCenter());
			employeeInfo.setEmail(seEmPeDeAdBean.getEmail());
			employeeInfo.setLiveLocation(seEmPeDeAdBean.getLiveLocation());
			employeeInfo.setMobilePhone(seEmPeDeAdBean.getMobilePhone());
			employeeInfo.setUpdPgmId(UPDPRMID);
			employeeInfo.setUpdUserKey((String)session.getAttribute(CodeCommon.SESSION_USERID));
			employeeManagementService.employeeInfoUpdate(employeeInfo);
			
			// 获取角色下拉框
			model.addAttribute("roleSelect", authorityManagementService.roleListQuery("", -1, -1).getResultlist());
			// 获取成本中心下拉框
			model.addAttribute("costCenterSelect", systemManagementService.costCenterListQuery(-1, -1).getResultlist());
			// 级别
			model.addAttribute("levelSelect", systemManagementService.employeeLevelListQuery(-1, -1).getResultlist());
			
			model.addAttribute("curPage", curPage);
			model.addAttribute("hasSaved","1");
			model.addAttribute("seEmPeDeAdBean", seEmPeDeAdBean);
			
			return "SE_EM_PE_DE";
		} catch (Exception e) {
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return CodeCommon.COMMON_ERROR_PAGE;
		}
		
	}
	
	
}
