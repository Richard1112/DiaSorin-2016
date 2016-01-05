package com.diasorin.oa.mvc;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.diasorin.oa.common.CodeCommon;
import com.diasorin.oa.common.ErrCommon;
import com.diasorin.oa.model.EmployeeInfo;
import com.diasorin.oa.service.EmployeeManagementService;
import com.diasorin.oa.service.MenuService;

@Controller
@RequestMapping("/SE_LG_MN/*")
public class SeLgMnController extends BaseController {
	
	@Resource
	MenuService menuService;
	
	@Resource
	EmployeeManagementService employeeManagementService;
	
	/**
	 * 菜单画面初期化
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("init")
	public String init(Model model, HttpServletRequest request, HttpSession session) {
		try {
			// 主菜单画面。根据当前登录用户抽取其权限下地功能块。
			String userId = (String)session.getAttribute(CodeCommon.SESSION_USERID);
			EmployeeInfo info = employeeManagementService.employeeInfoView(userId);			
			model.addAttribute("userId", info.getEmployeeNameEn());
			return "SE_LG_MN";
		} catch (Exception e) {
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return CodeCommon.COMMON_ERROR_PAGE;
		}
		
	}
	
}
