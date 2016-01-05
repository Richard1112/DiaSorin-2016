package com.diasorin.oa.mvc;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.diasorin.oa.common.CodeCommon;
import com.diasorin.oa.common.ErrCommon;
import com.diasorin.oa.dto.SeEmPeDeAdBean;
import com.diasorin.oa.model.EmployeeInfo;
import com.diasorin.oa.service.AuthorityManagementService;
import com.diasorin.oa.service.BaseService;
import com.diasorin.oa.service.EmployeeManagementService;
import com.diasorin.oa.service.SystemManagementService;

@Controller
@RequestMapping("/SE_EM_PE_AD/*")
public class SeEmPeAdController extends BaseController {
	
	
	@Resource
	EmployeeManagementService employeeManagementService;
	
	@Resource
	BaseService baseService;
	
	@Resource
	SystemManagementService systemManagementService;
	
	@Resource
	AuthorityManagementService authorityManagementService;
	
	/**
	 * 人员新增画面初期化
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("init")
	public String init(Model model, HttpServletRequest request, HttpSession session) {
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
			String curPage = request.getParameter("curPage");
			model.addAttribute("curPage", curPage);
			model.addAttribute("seEmPeDeAdBean", seEmPeDeAdBean);
			return "SE_EM_PE_AD";
		} catch (Exception e) {
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return CodeCommon.COMMON_ERROR_PAGE;
		}
		
	}
	
	/**
	 * 员工新增画面
	 * @param model
	 * @param request
	 * @param session
	 * @param seEmPeDeAdBean
	 * @return
	 */
	@RequestMapping("addEmployee")
	public String addEmployee(Model model, HttpServletRequest request, HttpSession session, @ModelAttribute SeEmPeDeAdBean seEmPeDeAdBean) {
		try {
			String curPage = request.getParameter("curPage");
			// 员工新增
			EmployeeInfo employeeInfo = new EmployeeInfo();
			BeanUtils.copyProperties(employeeInfo, seEmPeDeAdBean);
			employeeInfo.setAddUserKey((String)session.getAttribute(CodeCommon.SESSION_USERID));
			employeeManagementService.employeeAdd(employeeInfo);
			
			// 获取角色下拉框
			model.addAttribute("roleSelect", authorityManagementService.roleListQuery("", -1, -1).getResultlist());
			// 获取成本中心下拉框
			model.addAttribute("costCenterSelect", systemManagementService.costCenterListQuery(-1, -1).getResultlist());
			// 级别
			model.addAttribute("levelSelect", systemManagementService.employeeLevelListQuery(-1, -1).getResultlist());
			
			model.addAttribute("curPage", curPage);
			model.addAttribute("hasSaved","1");
			model.addAttribute("seEmPeDeAdBean", seEmPeDeAdBean);
			
			return "SE_EM_PE_AD";
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return CodeCommon.COMMON_ERROR_PAGE;
		}
		
	}
	
	/**
	 * 验证员工号是否存在
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/employeeNoCheck")
	@ResponseBody
	public Map<String, Object> employeeNoCheck(HttpServletRequest request, HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String employeeNo = (String)request.getParameter("employeeNo");
			// 存在
			EmployeeInfo employeeInfo = employeeManagementService.employeeInfoView(employeeNo);
			if (employeeInfo != null) {
				map.put("employeeNoCheck", true);
			}else {
				map.put("employeeNoCheck", false);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
		}
		return map;
	}
	
	
}
