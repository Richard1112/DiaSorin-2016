package com.diasorin.oa.mvc;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.diasorin.oa.common.CodeCommon;
import com.diasorin.oa.common.ErrCommon;
import com.diasorin.oa.common.QueryResult;
import com.diasorin.oa.dto.SeSyAmAUBean;
import com.diasorin.oa.dto.SeSyAmAlBean;
import com.diasorin.oa.dto.SeSyAmAlListBean;
import com.diasorin.oa.service.AuthorityManagementService;
import com.diasorin.oa.service.SystemManagementService;

@Controller
@RequestMapping("/SE_SY_AM_AL/*")
public class SeSyAmAlController extends BaseController {
	
	@Resource
	AuthorityManagementService authorityManagementService;
	
	@Resource
	SystemManagementService systemManagementService;
	/**
	 * 权限初期画面
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/init")
	public String init(Model model, HttpServletRequest request, HttpSession session) {
		try {
			SeSyAmAlBean bean = new SeSyAmAlBean();
			
			int maxresult = Integer.parseInt(getMessage("recordCount"));
			int totalPage = 1;
			
			QueryResult<SeSyAmAlListBean> listBeans = authorityManagementService.getAuthorityListGet(0, maxresult);
			// 是否有记录
			if (listBeans.getResultlist() != null && listBeans.getResultlist().size() > 0) {
				model.addAttribute("listCount", listBeans.getResultlist().size());
			}else{
				model.addAttribute("listCount", "0");
			}
			
			long resultCount = listBeans.getTotalrecord();
			totalPage=(int) ((resultCount % maxresult ==0) ? resultCount/maxresult : resultCount/maxresult+1);

			bean.setBeanList(listBeans.getResultlist());
			model.addAttribute("seSyAmAlBean",bean);
			
			model.addAttribute("curPage", 1);
			model.addAttribute("totalPage", totalPage);
			return "SE_SY_AM_AL";
		} catch(Exception e) {
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return CodeCommon.COMMON_ERROR_PAGE;
		}
		
	}
	
	/**
	 * 权限一览
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("page")
	public String page(Model model, HttpServletRequest request, HttpSession session) {
		try {
			SeSyAmAlBean bean = new SeSyAmAlBean();
			int curPage = Integer.parseInt(request.getParameter("curPage"));
			
			// 取得配置文件的页码最大显示数据量
			int maxresult = Integer.parseInt(getMessage("recordCount"));
			int totalPage = 1;
			// 取得权限列表
			int startResult = (curPage - 1) * maxresult;
			QueryResult<SeSyAmAlListBean> listBeans = authorityManagementService.getAuthorityListGet(startResult, maxresult);
			// 是否有记录
			if (listBeans.getResultlist() != null && listBeans.getResultlist().size() > 0) {
				model.addAttribute("listCount", listBeans.getResultlist().size());
			}else{
				model.addAttribute("listCount", "0");
			}
			
			long resultCount = listBeans.getTotalrecord();
			totalPage=(int) ((resultCount % maxresult ==0) ? resultCount/maxresult : resultCount/maxresult+1);


			bean.setBeanList(listBeans.getResultlist());
			model.addAttribute("seSyAmAlBean",bean);
			
			model.addAttribute("curPage", curPage);
			model.addAttribute("totalPage", totalPage);
			return "SE_SY_AM_AL";
		} catch(Exception e) {
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return CodeCommon.COMMON_ERROR_PAGE;
		}
	}
	
	/**
	 * 权限新增
	 * @param model
	 * @param request
	 * @param session
	 * @param deptId 部门ID
	 * @param roleId 角色ID
	 * @return 画面详细
	 */
	@RequestMapping("/add")
	public String add(Model model, HttpServletRequest request, HttpSession session) {
		try {
					
			SeSyAmAUBean seSyAmAUBean = new SeSyAmAUBean();
			
			model.addAttribute("saveDivision","add");
			
			seSyAmAUBean = authorityManagementService.authorityInfoGetFromModuleInfo();
			
			model.addAttribute("listCount",seSyAmAUBean.getBeanList().size());

			// 获取角色下拉框
			model.addAttribute("roleSelect", authorityManagementService.roleListQuery("", -1, -1).getResultlist());
			// 获取成本中心下拉框
			model.addAttribute("costCenterSelect", systemManagementService.costCenterListQuery(-1, -1).getResultlist());

			model.addAttribute("seSyAmAUBean",seSyAmAUBean);
			model.addAttribute("curPage", 1);
			return "SE_SY_AM_AU";
		} catch (Exception e) {
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return CodeCommon.COMMON_ERROR_PAGE;
		}
		
	}
	
	/**
	 * 权限更新
	 * @param model
	 * @param request
	 * @param session
	 * @param deptId 部门ID
	 * @param roleId 角色ID
	 * @return 画面详细
	 */
	@RequestMapping("/update")
	public String update(Model model, HttpServletRequest request, HttpSession session) {
		try {
			String deptId = request.getParameter("deptId");
			String roleId = request.getParameter("roleId");
			String curPage = request.getParameter("curPage");
					
			SeSyAmAUBean seSyAmAUBean = new SeSyAmAUBean();
			

			model.addAttribute("saveDivision","update");
			
			seSyAmAUBean = authorityManagementService.authorityInfoGet(deptId, roleId);
			
			model.addAttribute("listCount",seSyAmAUBean.getBeanList().size());


			// 获取角色下拉框
			model.addAttribute("roleSelect", authorityManagementService.roleListQuery("", -1, -1).getResultlist());
			// 获取成本中心下拉框
			model.addAttribute("costCenterSelect", systemManagementService.costCenterListQuery(-1, -1).getResultlist());

			model.addAttribute("seSyAmAUBean",seSyAmAUBean);
			model.addAttribute("curPage", curPage);
			return "SE_SY_AM_AU";
		} catch (Exception e) {
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return CodeCommon.COMMON_ERROR_PAGE;
		}
		
	}
	
}
