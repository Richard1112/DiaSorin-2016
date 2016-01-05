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
import com.diasorin.oa.common.ErrCommon;
import com.diasorin.oa.dto.SeLgUlBean;
import com.diasorin.oa.dto.SeSyAmAUBean;
import com.diasorin.oa.dto.SeSyAmAUListBean;
import com.diasorin.oa.model.EmployeeInfo;
import com.diasorin.oa.model.EmployeeLogin;
import com.diasorin.oa.model.EmployeeLoginHistroy;
import com.diasorin.oa.service.AuthorityManagementService;
import com.diasorin.oa.service.EmployeeManagementService;
import com.diasorin.oa.service.UserService;

@Controller
@RequestMapping("/SE_LG_UL/*")
public class SeLgUlController extends BaseController {
	
	@Resource
	UserService userService;
	
	@Resource
	EmployeeManagementService employeeManagementService;
	
	@Resource
	AuthorityManagementService authorityManagementService;
	
	/**
	 * 登录画面初期化
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("init")
	public String init(Model model, HttpServletRequest request, HttpSession session) {
		try {
			// 进入登录画面
			SeLgUlBean seLgUlBean = new SeLgUlBean();
			model.addAttribute("seLgUlBean", seLgUlBean);
			model.addAttribute("cannotLogin", "0");
			return "SE_LG_UL";
		} catch (Exception e) {
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return CodeCommon.COMMON_ERROR_PAGE;
		}
		
	}
	
	/**
	 * 用户登录操作
	 * @param model
	 * @param request
	 * @param session
	 * @param seLgUlBean
	 * @return
	 */
	@RequestMapping("login")
	public String login(Model model, HttpServletRequest request, HttpSession session, @ModelAttribute SeLgUlBean seLgUlBean) {
		try {
			// 进入登录画面
			String userId = seLgUlBean.getLoginId();
			String password = seLgUlBean.getPassword();
			//String password = PassWordParseInMD5.Md5(seLgUlBean.getPassword());
			EmployeeLogin employeeLogin = userService.userLogin(userId, password);
			if (employeeLogin == null) {
				// 没有取到数据,清空密码
				seLgUlBean = new SeLgUlBean();
				seLgUlBean.setLoginId(userId);
				// 错误信息
				model.addAttribute("seLgUlBean", seLgUlBean);
				model.addAttribute("cannotLogin", "1");
				return "SE_LG_UL";
			}

			// 可以取到数据，将所用的数据放入session 中
			session.setAttribute(CodeCommon.SESSION_USERID, userId);
			
			// 登录成功插入历史记录
			if (CodeCommon.HAS_LOGINED_STATUS.equals(employeeLogin
					.getLoginStatus())) {
				// 用户已经登录着，这时不需要插入历史数据也不需要更新登录表
				
			} else {
				// 插入历史登录数据并且更新登录状态
				EmployeeLoginHistroy employeeLoginHistroy = new EmployeeLoginHistroy();
				employeeLoginHistroy.setEmployeeNo(userId);
				userService.insertLoginHisAndUpdateStatus(employeeLoginHistroy);
			}

			// 当前用户已经登录, 直接跳转到主菜单画面。
			// 这里当用户已经登录的时候，就不需要再插入历史记录了。
			
			// 这里取出当前用户的权限
			EmployeeInfo employeeInfo = employeeManagementService.employeeInfoView(employeeLogin.getEmployeeNo());
			SeSyAmAUBean seSyAmAUBean = authorityManagementService.authorityInfoGet(employeeInfo.getDeptCode(), employeeInfo.getRoleCode());
			List<String> authorityList = new ArrayList<String>();
			// 将所有有权限的放入SESSION中
			if (seSyAmAUBean != null && seSyAmAUBean.getBeanList() != null && seSyAmAUBean.getBeanList().size() > 0) {
				for (SeSyAmAUListBean listBean : seSyAmAUBean.getBeanList()) {
					if (CodeCommon.HAS_AUTHORITY.equals(listBean.getAuthority())) {
						authorityList.add(listBean.getControlId());
					}
				}
			}
			
			session.setAttribute(CodeCommon.SESSION_ROLEID, employeeInfo.getRoleCode());
			if (authorityList.size() > 0) {
				session.setAttribute(CodeCommon.SESSION_AUTHORITY_LIST, authorityList);
			}

			return "redirect:/SE_LG_MN/init";
		} catch (Exception e) {
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return CodeCommon.COMMON_ERROR_PAGE;
		}
		
	}
	
	
	/**
	 * 用户登出操作
	 * @param model
	 * @param request
	 * @param session
	 * @param seLgUlBean
	 * @return
	 */
	@RequestMapping("logout")
	public String logout(Model model, HttpServletRequest request, HttpSession session) {
		try {
			// 登出操作
			userService.loginOut((String)session.getAttribute(CodeCommon.SESSION_USERID));
			// 清空SESSION USERID
			session.setAttribute(CodeCommon.SESSION_USERID, "");
			// 废除当前SESSION
			session.invalidate();
			return "redirect:/SE_LG_UL/init";
		} catch (Exception e) {
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return CodeCommon.COMMON_ERROR_PAGE;
		}
		
	}
}
