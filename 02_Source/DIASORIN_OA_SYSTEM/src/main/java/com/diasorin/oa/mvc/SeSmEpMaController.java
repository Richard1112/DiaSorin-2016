package com.diasorin.oa.mvc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.diasorin.oa.common.CodeCommon;
import com.diasorin.oa.common.ErrCommon;
import com.diasorin.oa.dto.SeSmEpMaBean;
import com.diasorin.oa.dto.SeSmEpMaListBean;
import com.diasorin.oa.model.SysExpenses;
import com.diasorin.oa.service.BaseService;
import com.diasorin.oa.service.ExpensesManagementService;
import com.diasorin.oa.service.SystemManagementService;

@Controller
@RequestMapping("/SE_SM_EP_MA/*")
public class SeSmEpMaController extends BaseController {
	
	@Resource
	ExpensesManagementService expensesManagementService;
	
	@Resource
	SystemManagementService systemManagementService;
	
	@Resource
	BaseService baseService;
	/**
	 * 根据出差地类型、人员级别、报销项目对报销费用等相关参数进行设定维护
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("init")
	public String init(Model model, HttpServletRequest request, HttpSession session) {
		try {
			SeSmEpMaBean seSmEpMaBean = new SeSmEpMaBean();
			// 获取项目下拉框
			List<SysExpenses> notFarList = systemManagementService.getNotFartherItem();

			if (notFarList != null){
				String expenseCode = notFarList.get(0).getExpenseCode();
				// 获取报销费用
				List<SeSmEpMaListBean> beanList = expensesManagementService.expensesParameterListQuery(expenseCode);
				seSmEpMaBean.setBeanList(beanList);
				seSmEpMaBean.setExpenseCode(expenseCode);
			}
			model.addAttribute("expensesList", notFarList);
			model.addAttribute("seSmEpMaBean", seSmEpMaBean);
			return "SE_SM_EP_MA";
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return CodeCommon.COMMON_ERROR_PAGE;
		}
		
	}
	
	
	/**
	 *取得出差地类型的报销设定数据
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("/getList")
	@ResponseBody
	public Map<String, Object> getList(HttpServletRequest request, HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String expenseCode = (String)request.getParameter("expenseCode");
			// 获取报销费用
			List<SeSmEpMaListBean> beanList = expensesManagementService.expensesParameterListQuery(expenseCode);
			map.put("titleList", baseService.getSelect(CodeCommon.COM004));
			map.put("beanList", beanList);
		} catch (Exception e) {
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
		}
		return map;
	}
	
	/**
	 * 报销设定更新
	 * @param request
	 * @param session
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/updateExpense")
	@ResponseBody
	public Map<String, Object> updateExpense(HttpServletRequest request, HttpSession session , @RequestBody List list) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String expenseCode = (String)request.getParameter("expenseCode");
			
			// 获取报销费用
			boolean isSuccess = expensesManagementService.expensesParameterSetting(expenseCode ,list);
			if (isSuccess) {
				map.put("isException", false);
			} else {
				map.put("isException", true);
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			map.put("isException", true);
		}
		return map;
	}
	
	
}
