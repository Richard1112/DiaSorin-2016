package com.diasorin.oa.mvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.diasorin.oa.common.CodeCommon;
import com.diasorin.oa.common.ErrCommon;
import com.diasorin.oa.common.QueryResult;
import com.diasorin.oa.dto.SeSmPaMaBean;
import com.diasorin.oa.dto.SeSmPaMaCcBean;
import com.diasorin.oa.dto.SeSmPaMaEiBean;
import com.diasorin.oa.dto.SeSmPaMaElBean;
import com.diasorin.oa.dto.SeSmPaMaTrBean;
import com.diasorin.oa.dto.SeSmPaMaUpBean;
import com.diasorin.oa.model.SysCostCenter;
import com.diasorin.oa.model.SysEmployeeLevel;
import com.diasorin.oa.model.SysExpenses;
import com.diasorin.oa.model.SysTravelLocal;
import com.diasorin.oa.service.BaseService;
import com.diasorin.oa.service.MenuService;
import com.diasorin.oa.service.SystemManagementService;

@Controller
@RequestMapping("/SE_SM_PA_MA/*")
public class SeSmPaMaController extends BaseController {
	
	@Resource
	MenuService menuService;
	
	@Resource
	SystemManagementService systemManagementService;
	
	@Resource
	BaseService baseService;
	
	/**
	 * 参数维护画面初期化
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("init")
	public String init(Model model, HttpServletRequest request, HttpSession session) {
		try {
			// 对出差地类型、人员级别、报销项目、Cost Center等进行增删改查的维护
			String tabInfo = (String) request.getParameter("tabInfo");
			
			SeSmPaMaBean seSmPaMaBean = setSeSmPaMaBean();
			
			model.addAttribute("seSmPaMaBean", seSmPaMaBean);
			model.addAttribute("tabInfo", tabInfo);
			return "SE_SM_PA_MA";
		} catch (Exception e) {
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return CodeCommon.COMMON_ERROR_PAGE;
		}
	}
	
	/**
	 * 共通用BEAN生成
	 * @return
	 * @throws Exception
	 */
	private SeSmPaMaBean setSeSmPaMaBean() throws Exception {
		SeSmPaMaBean seSmPaMaBean = new SeSmPaMaBean();
		
		QueryResult<SysTravelLocal> trList = systemManagementService.travelLocalListQuery(-1, -1);
		List<SeSmPaMaTrBean> trBeanList = new ArrayList<SeSmPaMaTrBean>();
		// 将数据转化成画面bean
		for (SysTravelLocal sysTravelLocal : trList.getResultlist()) {
			SeSmPaMaTrBean trBean = new SeSmPaMaTrBean();
			trBean.setNo(sysTravelLocal.getNo());
			trBean.setTravelCode(sysTravelLocal.getTravelCode());
			String type = baseService.getSelect(CodeCommon.COM004, sysTravelLocal.getTravelLocalType());
			trBean.setTravelLocalType(type);
			trBean.setTravelName(sysTravelLocal.getTravelName());
			trBeanList.add(trBean);
		}
		seSmPaMaBean.setTrBeanList(trBeanList);	

		List<SeSmPaMaEiBean> eiList = systemManagementService.getExpensesItemList();
		// 将数据转化成画面bean
		
		seSmPaMaBean.setEiBeanList(eiList);
		
		QueryResult<SysEmployeeLevel> elList = systemManagementService.employeeLevelListQuery(-1, -1);
		List<SeSmPaMaElBean> elBeanList = new ArrayList<SeSmPaMaElBean>();
		// 将数据转化成画面bean
		for (SysEmployeeLevel sysEmployeeLevel : elList.getResultlist()) {
			SeSmPaMaElBean elBean = new SeSmPaMaElBean();
			elBean.setNo(sysEmployeeLevel.getNo());
			elBean.setLevelCode(sysEmployeeLevel.getLevelCode());
			elBean.setLevelName(sysEmployeeLevel.getLevelName());
			elBeanList.add(elBean);
		}
		seSmPaMaBean.setElBeanList(elBeanList);
		
		QueryResult<SysCostCenter> ccList = systemManagementService.costCenterListQuery(-1, -1);
		List<SeSmPaMaCcBean> ccBeanList = new ArrayList<SeSmPaMaCcBean>();
		// 将数据转化成画面bean
		for (SysCostCenter sysCostCenter : ccList.getResultlist()) {
			SeSmPaMaCcBean ccBean = new SeSmPaMaCcBean();
			ccBean.setNo(sysCostCenter.getNo());
			ccBean.setCostCenterCode(sysCostCenter.getCostCenterCode());
			ccBean.setCostCenterName(sysCostCenter.getCostCenterName());
			ccBeanList.add(ccBean);
		}
		seSmPaMaBean.setCcBeanList(ccBeanList);
		
		return seSmPaMaBean;
	}
	
	/**
	 * 迁移至出差地类型、人员级别、报销项目、Cost Center增加画面
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("toAdd")
	public String toAdd(Model model, HttpServletRequest request, HttpSession session) {
		try {
			String tabInfo = (String)request.getParameter("tabInfo");
			SeSmPaMaUpBean seSmPaMaUpBean = new SeSmPaMaUpBean();
			
			model.addAttribute("seSmPaMaUpBean", seSmPaMaUpBean);
			// 获取出差地类型下拉框
			model.addAttribute("TravelTypeSelect", baseService.getSelect(CodeCommon.COM004));
			// 获取所有的费用
			model.addAttribute("ExpenseItemSelect", systemManagementService.expensesItemListQuery(-1, -1).getResultlist());
			model.addAttribute("tabInfo", tabInfo);
			model.addAttribute("saveDivision", "add");
			return "SE_SM_PA_DE";
		} catch (Exception e) {
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return CodeCommon.COMMON_ERROR_PAGE;
		}
	}
	
	/**
	 * 迁移至出差地类型、人员级别、报销项目、Cost Center更新画面
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("toUpdate")
	public String toUpdate(Model model, HttpServletRequest request, HttpSession session) {
		try {
			String tabInfo = (String)request.getParameter("tabInfo");
			String code = (String)request.getParameter("code");
			SeSmPaMaUpBean seSmPaMaUpBean = new SeSmPaMaUpBean();
			// 获取所有的费用
			
			if ("1".equals(tabInfo)){
				SysTravelLocal sysTravelLocal = systemManagementService.getTravelLocalInfo(code);
				seSmPaMaUpBean.setTravelCode(sysTravelLocal.getTravelCode());
				seSmPaMaUpBean.setTravelName(sysTravelLocal.getTravelName());
				seSmPaMaUpBean.setTravelLocalType(sysTravelLocal.getTravelLocalType());
			} else if ("2".equals(tabInfo)){
				SysEmployeeLevel sysEmployeeLevel = systemManagementService.getEmployeeLevelInfo(code);
				seSmPaMaUpBean.setLevelCode(sysEmployeeLevel.getLevelCode());
				seSmPaMaUpBean.setLevelName(sysEmployeeLevel.getLevelName());
			} else if ("3".equals(tabInfo)){
				SysExpenses sysExpens = systemManagementService.getExpensesItemInfo(code);
				seSmPaMaUpBean.setExpenseCode(sysExpens.getExpenseCode());
				seSmPaMaUpBean.setExpenseName(sysExpens.getExpenseName());
				seSmPaMaUpBean.setTimeMethod(sysExpens.getTimeMethod());
				seSmPaMaUpBean.setFatherExpenseCode(sysExpens.getFatherExpenseCode());
				seSmPaMaUpBean.setExtendsFileCo(sysExpens.getExtendsFieldCo1());
				seSmPaMaUpBean.setFinanceNo(sysExpens.getFinanceNo());
				seSmPaMaUpBean.setShowOrderNo(sysExpens.getShowOrderNo());
				List<SysExpenses> enpensesList = systemManagementService.expensesItemListQuery(-1, -1).getResultlist();
				List<SysExpenses> exceptSelf = new ArrayList<SysExpenses>(); 
				for (SysExpenses ex : enpensesList) {
					if (!ex.getExpenseCode().equals(sysExpens.getExpenseCode())) {
						exceptSelf.add(ex);
					}
				}
				model.addAttribute("ExpenseItemSelect", exceptSelf);
				
			} else if ("4".equals(tabInfo)){
				SysCostCenter sysCostCenter = systemManagementService.getCostCenterInfo(code);
				seSmPaMaUpBean.setCostCenterCode(sysCostCenter.getCostCenterCode());
				seSmPaMaUpBean.setCostCenterName(sysCostCenter.getCostCenterName());
				seSmPaMaUpBean.setCostCenterDisplayName(sysCostCenter.getCostCenterDisplayName());
			}	
			model.addAttribute("TravelTypeSelect", baseService.getSelect(CodeCommon.COM004));
			model.addAttribute("seSmPaMaUpBean", seSmPaMaUpBean);
			model.addAttribute("tabInfo", tabInfo);
			model.addAttribute("saveDivision", "update");
			return "SE_SM_PA_DE";
		} catch (Exception e) {
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return CodeCommon.COMMON_ERROR_PAGE;
		}
	}
	
	
	/**
	 * 验证是否存在
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/codeCheck")
	@ResponseBody
	public Map<String, Object> codeCheck(HttpServletRequest request, HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String code = (String)request.getParameter("code");
			String tabInfo = (String)request.getParameter("tabInfo");
			// 存在
			Object o = null;
			if ("1".equals(tabInfo)){
				o = systemManagementService.getTravelLocalInfo(code);
			} else if ("2".equals(tabInfo)){
				o = systemManagementService.getEmployeeLevelInfo(code);
			} else if ("3".equals(tabInfo)){
				o = systemManagementService.getExpensesItemInfo(code);
			} else if ("4".equals(tabInfo)){
				o = systemManagementService.getCostCenterInfo(code);
			}

			if (o != null) {
				map.put("codeCheck", true);
			}else {
				map.put("codeCheck", false);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
		}
		return map;
	}
	
	/**
	 * 保存
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("save")
	public String save(Model model, HttpServletRequest request, HttpSession session, @ModelAttribute SeSmPaMaUpBean seSmPaMaUpBean) {
		try {
			String userId = (String) session.getAttribute(CodeCommon.SESSION_USERID);
			String tabInfo = (String)request.getParameter("tabInfo");
			// 保存
			if ("1".equals(tabInfo)){
				SysTravelLocal sysTravelLocal = new SysTravelLocal();
				sysTravelLocal.setTravelCode(seSmPaMaUpBean.getTravelCode());
				sysTravelLocal.setTravelLocalType(seSmPaMaUpBean.getTravelLocalType());
				sysTravelLocal.setTravelName(seSmPaMaUpBean.getTravelName());
				systemManagementService.travelLocalAdd(sysTravelLocal);
			} else if ("2".equals(tabInfo)){
				SysEmployeeLevel sysEmployeeLevel = new SysEmployeeLevel();
				sysEmployeeLevel.setLevelCode(systemManagementService.getMaxEmployeeLevel());
				sysEmployeeLevel.setLevelName(seSmPaMaUpBean.getLevelName());
				systemManagementService.employeeLevelAdd(sysEmployeeLevel, userId);
			} else if ("3".equals(tabInfo)){
				SysExpenses sysExpenses = new SysExpenses();
				sysExpenses.setExpenseCode(systemManagementService.getMaxExpensesItem());
				sysExpenses.setExpenseName(seSmPaMaUpBean.getExpenseName());
				sysExpenses
						.setFatherExpenseCode(StringUtils
								.isEmpty(seSmPaMaUpBean.getFatherExpenseCode()) ? CodeCommon.EXPENSE_SUPERFARTHER
								: seSmPaMaUpBean.getFatherExpenseCode());
				sysExpenses.setTimeMethod(seSmPaMaUpBean.getTimeMethod());
				if (!StringUtils.isEmpty(seSmPaMaUpBean.getExtendsFileCo())) {
					sysExpenses.setExtendsFieldNm1("Car rate");
					sysExpenses.setExtendsFieldCo1(seSmPaMaUpBean.getExtendsFileCo());
				}
				
				sysExpenses.setFinanceNo(seSmPaMaUpBean.getFinanceNo());
				sysExpenses.setShowOrderNo(seSmPaMaUpBean.getShowOrderNo());
				systemManagementService.expensesItemAdd(sysExpenses, userId);
			} else if ("4".equals(tabInfo)){
				SysCostCenter sysCostCenter = new SysCostCenter();
				sysCostCenter.setCostCenterCode(seSmPaMaUpBean.getCostCenterCode());
				sysCostCenter.setCostCenterName(seSmPaMaUpBean.getCostCenterName());
				sysCostCenter.setCostCenterDisplayName(seSmPaMaUpBean.getCostCenterDisplayName());
				systemManagementService.costCenterAdd(sysCostCenter, userId);
			}
			model.addAttribute("TravelTypeSelect", baseService.getSelect(CodeCommon.COM004));
			model.addAttribute("seSmPaMaUpBean", seSmPaMaUpBean);
			model.addAttribute("tabInfo", tabInfo);
			model.addAttribute("hdHasFinish", "1");
			model.addAttribute("saveDivision", "add");
			return "SE_SM_PA_DE";
		} catch (Exception e) {
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return CodeCommon.COMMON_ERROR_PAGE;
		}
	}
	
	/**
	 * 更新
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("update")
	public String update(Model model, HttpServletRequest request, HttpSession session, @ModelAttribute SeSmPaMaUpBean seSmPaMaUpBean) {
		try {
			String userId = (String) session.getAttribute(CodeCommon.SESSION_USERID);
			// 角色的更新
			String tabInfo = (String)request.getParameter("tabInfo");
			// 保存
			if ("1".equals(tabInfo)){
				SysTravelLocal sysTravelLocal = systemManagementService.getTravelLocalInfo(seSmPaMaUpBean.getTravelCode());
				sysTravelLocal.setTravelLocalType(seSmPaMaUpBean.getTravelLocalType());
				sysTravelLocal.setTravelName(seSmPaMaUpBean.getTravelName());
				systemManagementService.travelLocalUpdate(sysTravelLocal);
			} else if ("2".equals(tabInfo)){
				SysEmployeeLevel sysEmployeeLevel = systemManagementService.getEmployeeLevelInfo(seSmPaMaUpBean.getLevelCode());
				sysEmployeeLevel.setLevelName(seSmPaMaUpBean.getLevelName());
				systemManagementService.employeeLevelUpdate(sysEmployeeLevel, userId);
			} else if ("3".equals(tabInfo)){
				SysExpenses sysExpenses = systemManagementService.getExpensesItemInfo(seSmPaMaUpBean.getExpenseCode());
				sysExpenses.setExpenseName(seSmPaMaUpBean.getExpenseName());
				sysExpenses.setFatherExpenseCode(StringUtils
						.isEmpty(seSmPaMaUpBean.getFatherExpenseCode()) ? CodeCommon.EXPENSE_SUPERFARTHER
						: seSmPaMaUpBean.getFatherExpenseCode());
				sysExpenses.setTimeMethod(seSmPaMaUpBean.getTimeMethod());
				if (!StringUtils.isEmpty(seSmPaMaUpBean.getExtendsFileCo())) {
					sysExpenses.setExtendsFieldNm1("Car rate");
					sysExpenses.setExtendsFieldCo1(seSmPaMaUpBean.getExtendsFileCo());
				}
				sysExpenses.setFinanceNo(seSmPaMaUpBean.getFinanceNo());
				sysExpenses.setShowOrderNo(seSmPaMaUpBean.getShowOrderNo());
				systemManagementService.expensesItemUpdate(sysExpenses, userId);
			} else if ("4".equals(tabInfo)){
				SysCostCenter sysCostCenter = systemManagementService.getCostCenterInfo(seSmPaMaUpBean.getCostCenterCode());
				sysCostCenter.setCostCenterName(seSmPaMaUpBean.getCostCenterName());
				sysCostCenter.setCostCenterDisplayName(seSmPaMaUpBean.getCostCenterDisplayName());
				systemManagementService.costCenterUpdate(sysCostCenter, userId);
			}
			model.addAttribute("TravelTypeSelect", baseService.getSelect(CodeCommon.COM004));
			model.addAttribute("seSmPaMaUpBean", seSmPaMaUpBean);
			model.addAttribute("tabInfo", tabInfo);
			model.addAttribute("hdHasFinish", "1");
			model.addAttribute("saveDivision", "update");
			return "SE_SM_PA_DE";
		} catch (Exception e) {
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return CodeCommon.COMMON_ERROR_PAGE;
		}
	}
	
	/**
	 * 删除
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("toDelete")
	public String toDelete(Model model, HttpServletRequest request, HttpSession session) {
		try {
			String userId = (String) session.getAttribute(CodeCommon.SESSION_USERID);
			String tabInfo = (String)request.getParameter("tabInfo");
			String codeNo = (String)request.getParameter("codeNo");
			// 保存
			if ("1".equals(tabInfo)){
				systemManagementService.travelLocalDelete(Long.valueOf(codeNo));
			} else if ("2".equals(tabInfo)){
				systemManagementService.employeeLevelDelete(Long.valueOf(codeNo), userId);
			} else if ("3".equals(tabInfo)){
				systemManagementService.expensesItemDelete(Long.valueOf(codeNo), userId);
			} else if ("4".equals(tabInfo)){
				systemManagementService.costCenterDelete(Long.valueOf(codeNo), userId);
			}
			return "redirect:/SE_SM_PA_MA/init?tabInfo="+tabInfo;
		} catch (Exception e) {
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return CodeCommon.COMMON_ERROR_PAGE;
		}
	}
	
}
