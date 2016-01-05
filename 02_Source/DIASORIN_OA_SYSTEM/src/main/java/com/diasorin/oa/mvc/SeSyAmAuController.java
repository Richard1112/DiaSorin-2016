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
import com.diasorin.oa.dto.SeSyAmAUBean;
import com.diasorin.oa.service.AuthorityManagementService;
import com.diasorin.oa.service.SystemManagementService;

@Controller
@RequestMapping("/SE_SY_AM_AU/*")
public class SeSyAmAuController extends BaseController {
	
	@Resource
	AuthorityManagementService authorityManagementService;
	
	@Resource
	SystemManagementService systemManagementService;
		
	/**
	 * 保存
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/save")
	public String save(Model model, HttpServletRequest request, HttpSession session, @ModelAttribute SeSyAmAUBean seSyAmAUBean) {
		try {

			int curPage = Integer.parseInt(request.getParameter("curPage"));
			// 保存权限信息
			authorityManagementService.authorityInfoCreate(seSyAmAUBean);
			
			model.addAttribute("curPage", curPage);

			model.addAttribute("saveDivision","update");
			
			seSyAmAUBean = authorityManagementService.authorityInfoGet(seSyAmAUBean.getDeptId(), seSyAmAUBean.getRoleId());
			
			model.addAttribute("listCount",seSyAmAUBean.getBeanList().size());

			// 获取角色下拉框
			model.addAttribute("roleSelect", authorityManagementService.roleListQuery("", -1, -1).getResultlist());
			// 获取成本中心下拉框
			model.addAttribute("costCenterSelect", systemManagementService.costCenterListQuery(-1, -1).getResultlist());

			model.addAttribute("seSyAmAUBean",seSyAmAUBean);
			
			model.addAttribute("hasSaved","1");

			return "SE_SY_AM_AU";
		} catch (Exception e) {
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return CodeCommon.COMMON_ERROR_PAGE;
		}
		
	}
	
}
