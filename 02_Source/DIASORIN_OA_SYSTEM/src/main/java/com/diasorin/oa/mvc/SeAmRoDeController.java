package com.diasorin.oa.mvc;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.diasorin.oa.common.CodeCommon;
import com.diasorin.oa.common.ErrCommon;
import com.diasorin.oa.dto.SeAmRoDeBean;
import com.diasorin.oa.model.SysEmployeeRole;
import com.diasorin.oa.service.AuthorityManagementService;

@Controller
@RequestMapping("/SE_AM_RO_DE/*")
public class SeAmRoDeController extends BaseController {
	
	@Resource
	AuthorityManagementService authorityManagementService;
	
	/**
	 * 角色的保存
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("save")
	public String save(Model model, HttpServletRequest request, HttpSession session, @ModelAttribute SeAmRoDeBean seAmRoDeBean) {
		try {
			String userId = (String) session.getAttribute(CodeCommon.SESSION_USERID);
			// 角色的保存
			SysEmployeeRole sysEmployeeRole = new SysEmployeeRole();
			
			sysEmployeeRole.setRoleCode(authorityManagementService.getMaxRoleCode());
			sysEmployeeRole.setRoleName(seAmRoDeBean.getRoleName());
			authorityManagementService.roleAdd(sysEmployeeRole, userId);
			
			model.addAttribute("seAmRoDeBean", seAmRoDeBean);
			model.addAttribute("saveDivision", "add");
			model.addAttribute("hasSaved","1");
			model.addAttribute("curPage", 1);
			return "SE_AM_RO_DE";
		} catch (Exception e) {
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return CodeCommon.COMMON_ERROR_PAGE;
		}
	}
	
	/**
	 * 角色的更新
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("update")
	public String update(Model model, HttpServletRequest request, HttpSession session, @ModelAttribute SeAmRoDeBean seAmRoDeBean) {
		try {
			String userId = (String) session.getAttribute(CodeCommon.SESSION_USERID);
			// 角色的更新
			SysEmployeeRole sysEmployeeRole = authorityManagementService.getRoleInfo(seAmRoDeBean.getRoleCode());
			sysEmployeeRole.setRoleName(seAmRoDeBean.getRoleName());
			authorityManagementService.roleUpdate(sysEmployeeRole, userId);

			model.addAttribute("seAmRoDeBean", seAmRoDeBean);
			model.addAttribute("saveDivision", "update");
			model.addAttribute("hasSaved","1");
			model.addAttribute("curPage", 1);
			return "SE_AM_RO_DE";
		} catch (Exception e) {
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return CodeCommon.COMMON_ERROR_PAGE;
		}
	}

	
	/**
	 * 验证角色ID是否存在
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/roleIdCheck")
	@ResponseBody
	public Map<String, Object> roleIdCheck(HttpServletRequest request, HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String roleId = (String)request.getParameter("roleId");
			// 存在
			SysEmployeeRole sysEmployeeRole = authorityManagementService.getRoleInfo(roleId);
			if (sysEmployeeRole != null) {
				map.put("roleIdCheck", true);
			}else {
				map.put("roleIdCheck", false);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
		}
		return map;
	}
}
