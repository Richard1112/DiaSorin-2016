package com.diasorin.oa.mvc;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.diasorin.oa.common.CodeCommon;
import com.diasorin.oa.common.ErrCommon;
import com.diasorin.oa.dto.SeEmPePcBean;
import com.diasorin.oa.model.EmployeeLogin;
import com.diasorin.oa.service.EmployeeManagementService;
import com.diasorin.oa.service.UserService;

@Controller
@RequestMapping("/SE_EM_PE_PC/*")
public class SeEmPePcController extends BaseController {
	
	
	@Resource
	EmployeeManagementService employeeManagementService;
	
	@Resource
	UserService userService;
	
	/**
	 * 密码更新画面初期化
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("init")
	public String init(Model model, HttpServletRequest request, HttpSession session) {
		try {
			
			SeEmPePcBean seEmPePcBean = new SeEmPePcBean();

			model.addAttribute("seEmPePcBean", seEmPePcBean);
			return "SE_EM_PE_PC";
		} catch (Exception e) {
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return CodeCommon.COMMON_ERROR_PAGE;
		}
	}
	
	/**
	 * 密码更新操作
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("updatePassword")
	public String updatePassword(Model model, HttpServletRequest request, HttpSession session, @ModelAttribute SeEmPePcBean seEmPePcBean) {
		try {
			
			// 输入的旧密码是否正确
			String userId = (String)session.getAttribute(CodeCommon.SESSION_USERID);
			String password = seEmPePcBean.getNowPassword(); //TODO 加密
			EmployeeLogin employeeLogin = userService.userLogin(userId, password);
			if (employeeLogin == null) {
				// 密码输入错误
				SeEmPePcBean seEmPePc = new SeEmPePcBean();
				seEmPePc.setNowPassword(seEmPePcBean.getNowPassword());
				model.addAttribute("seEmPePcBean", seEmPePc);
				// 返回画面
				return "SE_EM_PE_PC";
			}else{
				// 旧密码输入正确，更新密码
				employeeLogin.setEmployeePassword(seEmPePcBean.getNewPassword());//TODO 加密
				employeeManagementService.employeePasswordChange(employeeLogin);
			}
			return "SE_EM_PE_PC"; //TODO 应该迁移到哪一个画面
		} catch (Exception e) {
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return CodeCommon.COMMON_ERROR_PAGE;
		}
		
	}
	
}
