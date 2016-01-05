package com.diasorin.oa.mvc;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.diasorin.oa.common.CodeCommon;
import com.diasorin.oa.common.ErrCommon;
import com.diasorin.oa.common.QueryResult;
import com.diasorin.oa.dto.SeEmPeLsBean;
import com.diasorin.oa.dto.SeEmPeLsListBean;
import com.diasorin.oa.model.EmployeeInfo;
import com.diasorin.oa.model.EmployeeLogin;
import com.diasorin.oa.service.AuthorityManagementService;
import com.diasorin.oa.service.BaseService;
import com.diasorin.oa.service.EmployeeManagementService;
import com.diasorin.oa.service.SystemManagementService;

@Controller
@RequestMapping("/SE_EM_PE_LS/*")
public class SeEmPeLsController extends BaseController {
	
	
	@Resource
	EmployeeManagementService employeeManagementService;
	
	@Resource
	SystemManagementService systemManagementService;
	
	@Resource
	AuthorityManagementService authorityManagementService;
	
	@Resource
	BaseService baseService;
	
	/**
	 * 人员一览画面初期化
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("init")
	public String init(Model model, HttpServletRequest request, HttpSession session) {
		try {
			SeEmPeLsBean seEmPeLsBean = new SeEmPeLsBean();
			// 获取角色下拉框
			model.addAttribute("roleList", authorityManagementService.roleListQuery("", -1, -1).getResultlist());
			// 获取成本中心下拉框
			model.addAttribute("costCenterList", systemManagementService.costCenterListQuery(-1, -1).getResultlist());
			model.addAttribute("listCount", "0");
			model.addAttribute("seEmPeLsBean", seEmPeLsBean);
			return "SE_EM_PE_LS";
		} catch (Exception e) {
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return CodeCommon.COMMON_ERROR_PAGE;
		}
		
	}
	
	
	/**
	 * 员工删除操作
	 * @param model
	 * @param request
	 * @param session
	 * @param employeeNo
	 * @return
	 */
	@RequestMapping("deleteEmployee/{employeeNo}")
	public String deleteEmployee(Model model, HttpServletRequest request, HttpSession session, @PathVariable String employeeNo) {
		try {
			EmployeeInfo employeeInfo = employeeManagementService.employeeInfoView(employeeNo);
			employeeInfo.setDeleteFlg(CodeCommon.IS_DELETE_FLG);
			employeeManagementService.employeeInfoDelete(employeeInfo);
			return "redirect:/SE_EM_PE_LS/init";
		} catch (Exception e) {
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return CodeCommon.COMMON_ERROR_PAGE;
		}
	}
	
	
	/**
	 * 员工密码重置
	 * @param model
	 * @param request
	 * @param session
	 * @param employeeNo
	 * @return
	 */
	@RequestMapping("reflashPassword/{employeeNo}")
	public String reflashPassword(Model model, HttpServletRequest request, HttpSession session, @PathVariable String employeeNo) {
		try {
			String curPage = request.getParameter("curPage");
			EmployeeLogin employeeLogin = employeeManagementService.getEmployeeByNo(employeeNo);
			if (employeeLogin != null ){
				// 密码重置
				employeeLogin.setEmployeePassword("123456");
				employeeManagementService.employeePasswordChange(employeeLogin);
			} else {
			}
			if ("0".equals(curPage)) {
				return "redirect:/SE_EM_PE_LS/init";
			} else {
				return "redirect:/SE_EM_PE_LS/page?curPage="+curPage;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return CodeCommon.COMMON_ERROR_PAGE;
		}
	}
	
	/**
	 * 员工检索按钮按下
	 * @param model
	 * @param request
	 * @param session
	 * @param seEmPeLsBean
	 * @return
	 */
	@RequestMapping("searchEmployee")
	public String searchEmployee(Model model, HttpServletRequest request, HttpSession session, @ModelAttribute SeEmPeLsBean seEmPeLsBean) {
		try {
			// 获取角色下拉框
			model.addAttribute("roleList", authorityManagementService.roleListQuery("", -1, -1).getResultlist());
			// 获取成本中心下拉框
			model.addAttribute("costCenterList", systemManagementService.costCenterListQuery(-1, -1).getResultlist());
			
			List<SeEmPeLsListBean> beanList = new ArrayList<SeEmPeLsListBean>();
			// 将现在的检索数据放入session 中
			session.setAttribute("seEmPeLsBeanForSearch", seEmPeLsBean);
			// 取得配置文件的页码最大显示数据量
			int maxresult = Integer.parseInt(getMessage("recordCount"));
			int totalPage = 1;
			// 取得权限列表
			QueryResult<EmployeeInfo> employeeInfoList = employeeManagementService.employeeListQuery(0, maxresult, "employeeNameCn", seEmPeLsBean);
			
			// 是否有记录
			if (employeeInfoList.getResultlist() != null && employeeInfoList.getTotalrecord() > 0) {
				model.addAttribute("listCount", employeeInfoList.getTotalrecord());
			}else{
				model.addAttribute("listCount", "0");
			}
			
			long resultCount = employeeInfoList.getTotalrecord();
			totalPage=(int) ((resultCount % maxresult ==0) ? resultCount/maxresult : resultCount/maxresult+1);
			// 将数据转化成画面bean	
			if (employeeInfoList.getTotalrecord() > 0) {
				for(EmployeeInfo employeeInfo : employeeInfoList.getResultlist()) {
					SeEmPeLsListBean listBean = new SeEmPeLsListBean();
					listBean.setEmployeeNameCn(employeeInfo.getEmployeeNameCn());
					listBean.setEmployeeNameEn(employeeInfo.getEmployeeNameEn());
					listBean.setEmployeeNo(employeeInfo.getEmployeeNo());
					String ccName = systemManagementService.getCostCenterInfo(employeeInfo.getDeptCode()).getCostCenterName();
					listBean.setDeptName(ccName);
					listBean.setCostCenterCode(employeeInfo.getDeptCode());
					String rlName = authorityManagementService.getRoleInfo(employeeInfo.getRoleCode()).getRoleName();
					listBean.setRoleName(rlName);
					String leName = systemManagementService.getEmployeeLevelInfo(employeeInfo.getLevelCode()).getLevelName();
					listBean.setLevelName(leName);
					beanList.add(listBean);
				}
			}
			seEmPeLsBean.setBeanList(beanList);
			
			model.addAttribute("curPage", 1);
			model.addAttribute("totalPage", totalPage);
			model.addAttribute("seEmPeLsBean", seEmPeLsBean);
			return "SE_EM_PE_LS";
		} catch (Exception e) {
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return CodeCommon.COMMON_ERROR_PAGE;
		}
	}
	
	/**
	 * 员工分页一览任意按钮按下
	 * @param model
	 * @param request
	 * @param session
	 * @param seEmPeLsBean
	 * @return
	 */
	@RequestMapping("page")
	public String page(Model model, HttpServletRequest request, HttpSession session) {
		try {
			// 获取角色下拉框
			model.addAttribute("roleList", authorityManagementService.roleListQuery("", -1, -1).getResultlist());
			// 获取成本中心下拉框
			model.addAttribute("costCenterList", systemManagementService.costCenterListQuery(-1, -1).getResultlist());
			
			List<SeEmPeLsListBean> beanList = new ArrayList<SeEmPeLsListBean>();
			// 在SESSION 取得检索数据
			SeEmPeLsBean seEmPeLsBean = (SeEmPeLsBean) session.getAttribute("seEmPeLsBeanForSearch");
			int curPage = Integer.parseInt(request.getParameter("curPage"));
			// 取得配置文件的页码最大显示数据量
			int maxresult = Integer.parseInt(getMessage("recordCount"));
			int totalPage = 1;
			// 取得权限列表
			int startResult = (curPage - 1) * maxresult;
			QueryResult<EmployeeInfo> employeeInfoList = employeeManagementService.employeeListQuery(startResult, maxresult, "employeeNameCn", seEmPeLsBean);
			
			// 是否有记录
			if (employeeInfoList.getResultlist() != null && employeeInfoList.getTotalrecord() > 0) {
				model.addAttribute("listCount", employeeInfoList.getTotalrecord());
			}else{
				model.addAttribute("listCount", "0");
			}
			
			long resultCount = employeeInfoList.getTotalrecord();
			totalPage=(int) ((resultCount % maxresult ==0) ? resultCount/maxresult : resultCount/maxresult+1);
			// 将数据转化成画面bean	
			if (employeeInfoList.getTotalrecord() > 0) {
				for(EmployeeInfo employeeInfo : employeeInfoList.getResultlist()) {
					SeEmPeLsListBean listBean = new SeEmPeLsListBean();
					listBean.setEmployeeNameCn(employeeInfo.getEmployeeNameCn());
					listBean.setEmployeeNameEn(employeeInfo.getEmployeeNameEn());
					listBean.setEmployeeNo(employeeInfo.getEmployeeNo());
					String ccName = systemManagementService.getCostCenterInfo(employeeInfo.getDeptCode()).getCostCenterName();
					listBean.setDeptName(ccName);
					listBean.setCostCenterCode(employeeInfo.getDeptCode());
					String rlName = authorityManagementService.getRoleInfo(employeeInfo.getRoleCode()).getRoleName();
					listBean.setRoleName(rlName);
					String leName = systemManagementService.getEmployeeLevelInfo(employeeInfo.getLevelCode()).getLevelName();
					listBean.setLevelName(leName);
					beanList.add(listBean);
				}
			}
			seEmPeLsBean.setBeanList(beanList);
			
			model.addAttribute("curPage", curPage);
			model.addAttribute("totalPage", totalPage);
			model.addAttribute("seEmPeLsBean", seEmPeLsBean);
			return "SE_EM_PE_LS";
		} catch (Exception e) {
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return CodeCommon.COMMON_ERROR_PAGE;
		}
	}
	
}
