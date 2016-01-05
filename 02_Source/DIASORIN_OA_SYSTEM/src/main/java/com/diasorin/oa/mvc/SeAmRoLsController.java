package com.diasorin.oa.mvc;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.diasorin.oa.common.CodeCommon;
import com.diasorin.oa.common.ErrCommon;
import com.diasorin.oa.common.QueryResult;
import com.diasorin.oa.dto.SeAmRoDeBean;
import com.diasorin.oa.dto.SeAmRoLsBean;
import com.diasorin.oa.dto.SeAmRoLsListBean;
import com.diasorin.oa.model.SysEmployeeRole;
import com.diasorin.oa.service.AuthorityManagementService;

@Controller
@RequestMapping("/SE_AM_RO_LS/*")
public class SeAmRoLsController extends BaseController {
	
	@Resource
	AuthorityManagementService authorityManagementService;
	
	/**
	 * 角色一览
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("init")
	public String init(Model model, HttpServletRequest request, HttpSession session) {
		try {
			SeAmRoLsBean bean = new SeAmRoLsBean();
			
			List<SeAmRoLsListBean> roleList = new ArrayList<SeAmRoLsListBean>();
			
			// 取得配置文件的页码最大显示数据量
			int maxresult = Integer.parseInt(getMessage("recordCount"));
			int totalPage = 1;
			// 取得权限列表
			QueryResult<SysEmployeeRole> listBeans = authorityManagementService
					.roleListQuery(null, 0, maxresult);
			// 是否有记录
			if (listBeans.getResultlist() != null && listBeans.getResultlist().size() > 0) {
				model.addAttribute("listCount", listBeans.getResultlist().size());
			}else{
				model.addAttribute("listCount", "0");
			}
			
			long resultCount = listBeans.getTotalrecord();
			totalPage=(int) ((resultCount % maxresult ==0) ? resultCount/maxresult : resultCount/maxresult+1);
			// 将数据转化成画面bean
			for (SysEmployeeRole sysEmployeeRole : listBeans.getResultlist()) {
				SeAmRoLsListBean dtoBean = new SeAmRoLsListBean();
				dtoBean.setNo(sysEmployeeRole.getNo().toString());
				dtoBean.setRoleCode(sysEmployeeRole.getRoleCode());
				dtoBean.setRoleName(sysEmployeeRole.getRoleName());
				roleList.add(dtoBean);
			}
			bean.setBeanList(roleList);
			model.addAttribute("seAmRoLsBean",bean);
			
			model.addAttribute("curPage", 1);
			model.addAttribute("totalPage", totalPage);
			return "SE_AM_RO_LS";
		} catch(Exception e) {
			ErrCommon.errOut(e);
			return CodeCommon.COMMON_ERROR_PAGE;
		}
	}
	
	/**
	 * 迁移至角色新增
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("add")
	public String add(Model model, HttpServletRequest request, HttpSession session) {
		try {
			SeAmRoDeBean seAmRoDeBean = new SeAmRoDeBean();
			model.addAttribute("seAmRoDeBean", seAmRoDeBean);
			model.addAttribute("saveDivision", "add");
			return "SE_AM_RO_DE";
		} catch(Exception e) {
			ErrCommon.errOut(e);
			return CodeCommon.COMMON_ERROR_PAGE;
		}
	}
	
	/**
	 * 迁移至角色更新
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("update")
	public String update(Model model, HttpServletRequest request, HttpSession session) {
		try {
			String roleId = (String)request.getParameter("roleId");
			String curPage = (String)request.getParameter("curPage");
			SysEmployeeRole sysEmployeeRole = authorityManagementService.getRoleInfo(roleId);
			
			SeAmRoDeBean seAmRoDeBean = new SeAmRoDeBean();
			seAmRoDeBean.setRoleCode(sysEmployeeRole.getRoleCode());
			seAmRoDeBean.setRoleName(sysEmployeeRole.getRoleName());
			model.addAttribute("seAmRoDeBean", seAmRoDeBean);
			model.addAttribute("saveDivision", "update");
			model.addAttribute("curPage", curPage);
			
			return "SE_AM_RO_DE";
		} catch(Exception e) {
			ErrCommon.errOut(e);
			return CodeCommon.COMMON_ERROR_PAGE;
		}
	}
	
	/**
	 * 角色删除
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("delete")
	public String delete(Model model, HttpServletRequest request, HttpSession session) {
		try {
			String userId = (String) session.getAttribute(CodeCommon.SESSION_USERID);
			String roleId = (String)request.getParameter("roleId");
			SysEmployeeRole sysEmployeeRole = authorityManagementService.getRoleInfo(roleId);
			authorityManagementService.roleDelete(sysEmployeeRole.getNo(), userId);
			return "redirect:/SE_AM_RO_LS/init";
		} catch(Exception e) {
			ErrCommon.errOut(e);
			return CodeCommon.COMMON_ERROR_PAGE;
		}
	}
	
	/**
	 * 角色一览(分页)
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("page")
	public String page(Model model, HttpServletRequest request, HttpSession session) {
		try {
			SeAmRoLsBean bean = new SeAmRoLsBean();
			int curPage = Integer.parseInt(request.getParameter("curPage"));
			List<SeAmRoLsListBean> roleList = new ArrayList<SeAmRoLsListBean>();
			
			// 取得配置文件的页码最大显示数据量
			int maxresult = Integer.parseInt(getMessage("recordCount"));
			int totalPage = 1;
			// 取得权限列表
			int startResult = (curPage - 1) * maxresult;
			QueryResult<SysEmployeeRole> listBeans = authorityManagementService
					.roleListQuery(null, startResult, maxresult);
			// 是否有记录
			if (listBeans.getResultlist() != null && listBeans.getResultlist().size() > 0) {
				model.addAttribute("listCount", listBeans.getResultlist().size());
			}else{
				model.addAttribute("listCount", "0");
			}
			
			long resultCount = listBeans.getTotalrecord();
			totalPage=(int) ((resultCount % maxresult ==0) ? resultCount/maxresult : resultCount/maxresult+1);
			// 将数据转化成画面bean
			for (SysEmployeeRole sysEmployeeRole : listBeans.getResultlist()) {
				SeAmRoLsListBean dtoBean = new SeAmRoLsListBean();
				dtoBean.setNo(sysEmployeeRole.getNo().toString());
				dtoBean.setRoleCode(sysEmployeeRole.getRoleCode());
				dtoBean.setRoleName(sysEmployeeRole.getRoleName());
				roleList.add(dtoBean);
			}
			bean.setBeanList(roleList);
			model.addAttribute("seAmRoLsBean",bean);
			
			model.addAttribute("curPage", curPage);
			model.addAttribute("totalPage", totalPage);
			return "SE_AM_RO_LS";
		} catch(Exception e) {
			ErrCommon.errOut(e);
			return CodeCommon.COMMON_ERROR_PAGE;
		}
	}

}
