package com.diasorin.oa.mvc;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.diasorin.oa.common.CodeCommon;
import com.diasorin.oa.common.DateFormatCommon;
import com.diasorin.oa.common.ErrCommon;
import com.diasorin.oa.common.TypeConvertCommon;
import com.diasorin.oa.dto.SeEcEpDeListBean;
import com.diasorin.oa.dto.SeEmPeLsBean;
import com.diasorin.oa.dto.SeRcReLsBean;
import com.diasorin.oa.dto.SeRcReLsBodyListBean;
import com.diasorin.oa.dto.SeRcReLsExpenseListBean;
import com.diasorin.oa.dto.SeRcReLsHeadListBean;
import com.diasorin.oa.model.EmployeeInfo;
import com.diasorin.oa.service.BaseService;
import com.diasorin.oa.service.EmployeeManagementService;
import com.diasorin.oa.service.ExportService;
import com.diasorin.oa.service.MenuService;
import com.diasorin.oa.service.SystemManagementService;

@Controller
@RequestMapping("/SE_RC_RE_LS/*")
public class SeRcReLsController extends BaseController {
	
	@Resource
	MenuService menuService;
	
	@Resource
	EmployeeManagementService employeeManagementService;
	
	@Resource
	SystemManagementService systemManagementService;
	
	@Resource
	BaseService baseService;
	
	@Resource
	ExportService exportService;
	
	/**
	 * 报表检索画面
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("init")
	public String init(Model model, HttpServletRequest request, HttpSession session) {
		try {
			String userId = (String) session.getAttribute(CodeCommon.SESSION_USERID);
			EmployeeInfo employeeInfo = employeeManagementService.employeeInfoView(userId);
			SeRcReLsBean seRcReLsBean = new SeRcReLsBean();
			// 员工
			SeEmPeLsBean seEmPeLsBean = new SeEmPeLsBean();
			seEmPeLsBean.setDeptCode(employeeInfo.getDeptCode());
			model.addAttribute("employeeSelect", employeeManagementService.employeeListQuery(-1, -1, "employeeNo", seEmPeLsBean).getResultlist());
			// 项目
			model.addAttribute("expensesSelect", systemManagementService.expensesItemListQuery(-1, -1).getResultlist());
			// 部门
			model.addAttribute("costCenterSelect", systemManagementService.costCenterListQuery(-1, -1).getResultlist());
			// 项目是审批了还是已经完成了。
			model.addAttribute("statusSelect", baseService.getFinanceSelect());
			seRcReLsBean.setCostCenter(employeeInfo.getDeptCode());
			model.addAttribute("seRcReLsBean", seRcReLsBean);
			return "SE_RC_RE_LS";
		} catch (Exception e) {
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return CodeCommon.COMMON_ERROR_PAGE;
		}
		
	}
	
	
	@RequestMapping(value = "/reloadEmployee")
	@ResponseBody
	public Map<String, Object> reloadEmployee(HttpServletRequest request, HttpSession session) {
		Map<String, Object> mapReturn = new HashMap<String, Object>();
		try {
			String costCenter = request.getParameter("costCenter");
			List<EmployeeInfo> emplList = employeeManagementService.employeeListQueryByCostCenter(costCenter);
			mapReturn.put("employeeList", emplList);
			mapReturn.put("isException", false);
			return mapReturn;
		} catch (Exception e) {
			logger.error(e.getMessage());
			mapReturn.put("isException", true);
			ErrCommon.errOut(e);
			return null;
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/search")
	@ResponseBody
	public Map<String, Object> search(Model model, HttpServletRequest request, HttpSession session, @RequestBody Map<String, String> map) {
		Map<String, Object> mapReturn = new HashMap<String, Object>();
		try {

			SeRcReLsBean seRcReLsBean = new SeRcReLsBean();
			seRcReLsBean.setOccurDateFrom(map.get("occurDateForm"));
			seRcReLsBean.setOccurDateTo(map.get("occurDateTo"));
			seRcReLsBean.setFinance(map.get("finance"));
			seRcReLsBean.setFinanceDateFrom(map.get("financeDateFrom"));
			seRcReLsBean.setFinanceDateTo(map.get("financeDateTo"));
			seRcReLsBean.setEmployee(map.get("employee"));
			seRcReLsBean.setTravelFlg(map.get("travelFlg"));
			seRcReLsBean.setExpenseType(map.get("expenseType"));
			seRcReLsBean.setCostCenter(map.get("costCenter"));
			seRcReLsBean.setOther(map.get("otherFlg"));
			
			session.setAttribute("ExportSearchBean", seRcReLsBean);

			// 取得明细项目的TITLE
			List<String> titleList = exportService.getDetailTitleList(seRcReLsBean);
			// TITLE 明细
			List<String> titleNameList = new ArrayList<String>();
			
			for (String str : titleList){
				titleNameList.add(systemManagementService.getExpensesItemInfo(str).getExpenseName());
			}
			
			mapReturn = createMap(0, seRcReLsBean, titleList, mapReturn);
			session.setAttribute("beanToExport", mapReturn.get("seRcReLsBean"));
			session.setAttribute("titleList", titleNameList);
			List<String> authorityLs = (List<String>) session.getAttribute("authority");
			String authorityString = "";
			for(int i = 0; i < authorityLs.size(); i ++) {
				authorityString = authorityString + "," +authorityLs.get(i);	
			}
			mapReturn.put("authority", authorityString);
			mapReturn.put("titleNameList", titleNameList);
			mapReturn.put("isException", false);
			return mapReturn;
		} catch (Exception e) {
			logger.error(e.getMessage());
			mapReturn.put("isException", true);
			ErrCommon.errOut(e);
			return null;
		}
		
	}
	
	/**
	 * 根据是否集计来生成数据
	 * @param start
	 * @param seRcReLsBean
	 * @return
	 * @throws Exception 
	 */
	private Map<String, Object> createMap(int start, SeRcReLsBean seRcReLsBean, List<String> titleList, Map<String, Object> mapReturn) throws Exception {

		// 取得目的数据一览
		List<SeRcReLsHeadListBean> headList = exportService.getPurposeList(-1, -1, seRcReLsBean);
		
		// 用来数据的比较
		List<String> titleComp = new ArrayList<String>();
		// 用来判断哪些expense被赋值了。
		List<Integer> indexList = new ArrayList<Integer>();
		
		for (String str : titleList){
			titleComp.add(str);
		}
		
		// 取得明细项目详细数据
		List<SeRcReLsExpenseListBean> expenseList = exportService.getDetailList(-1, -1, seRcReLsBean);
		
		List<SeRcReLsBodyListBean> bodyList = new ArrayList<SeRcReLsBodyListBean>();
		
		// 判断到底是以什么来集计的
		
		if ("1".equals(seRcReLsBean.getTravelFlg())) {
			// 以员工和出差地来集计
			if (expenseList != null && expenseList.size() > 0) {
				SeRcReLsBodyListBean bodyBean = new SeRcReLsBodyListBean();
				
				String employee = expenseList.get(0).getEmployee();
				String travelLocation = expenseList.get(0).getTravelLocation();
				
				bodyBean.setEmployee(employee);
				bodyBean.setTravelLocation(travelLocation);
				bodyBean.setEmployeeNo(expenseList.get(0).getEmployeeNo());
				
				BigDecimal totalAmount = BigDecimal.ZERO;
				for (int i = 0 ; i< expenseList.size() ; i++) {
					if (employee.equals(expenseList.get(i).getEmployee())
							&& travelLocation.equals(expenseList.get(i).getTravelLocation())) {
						
						for (int p = 0; p < titleComp.size(); p++) {
							if (titleComp.get(p).equals(expenseList.get(i).getExpensesItem())) {
								
								titleComp.set(p, expenseList.get(i).getExpensesAmount());
								indexList.add(p);
							}
						}
						totalAmount = totalAmount.add(new BigDecimal(expenseList.get(i).getExpensesAmount()));
						
					} else {
						// 将没有设上值得数据变成空
						for (int z = 0; z < titleComp.size(); z++) {
							boolean isIn = false;
							for (Integer in : indexList) {
								if (z == in) {
									isIn = true;
									break;
								}
							}
							if (!isIn) {
								titleComp.set(z, "");
							}
						}
						
						titleComp.add(totalAmount.toString());
						bodyBean.setAmountList(titleComp);
						bodyList.add(bodyBean);
						// 初始化数据
						bodyBean = new SeRcReLsBodyListBean();
						totalAmount = BigDecimal.ZERO;
						
						indexList = new ArrayList<Integer>();
						titleComp = initList(titleList);
						
						employee = expenseList.get(i).getEmployee();
						travelLocation = expenseList.get(i).getTravelLocation();
						
						for (int p = 0; p < titleComp.size(); p++) {
							if (titleComp.get(p).equals(expenseList.get(i).getExpensesItem())) {
								
								titleComp.set(p, expenseList.get(i).getExpensesAmount());
								indexList.add(p);
							}
						}

						totalAmount = totalAmount.add(new BigDecimal(expenseList.get(i).getExpensesAmount()));
						bodyBean.setEmployee(employee);
						bodyBean.setTravelLocation(travelLocation);
						bodyBean.setEmployeeNo(expenseList.get(i).getEmployeeNo());
					}
				}
				
				// 将剩余的数据输出
				for (int z = 0; z < titleComp.size(); z++) {
					boolean isIn = false;
					for (Integer in : indexList) {
						if (z == in) {
							isIn = true;
							break;
						}
					}
					if (!isIn) {
						titleComp.set(z, "");
					}
				}
				titleComp.add(totalAmount.toString());
				bodyBean.setAmountList(titleComp);
				bodyList.add(bodyBean);
			}
		} else {
			// 以员工来集计
			if (expenseList != null && expenseList.size() > 0) {
				SeRcReLsBodyListBean bodyBean = new SeRcReLsBodyListBean();

				String employee = expenseList.get(0).getEmployee();

				bodyBean.setEmployee(employee);
				bodyBean.setEmployeeNo(expenseList.get(0).getEmployeeNo());

				BigDecimal totalAmount = BigDecimal.ZERO;
				for (int i = 0; i < expenseList.size(); i++) {
					if (employee.equals(expenseList.get(i).getEmployee())) {

						for (int p = 0; p < titleComp.size(); p++) {
							if (titleComp.get(p).equals(
									expenseList.get(i).getExpensesItem())) {

								titleComp.set(p, expenseList.get(i)
										.getExpensesAmount());
								indexList.add(p);
							}
						}
						totalAmount = totalAmount.add(new BigDecimal(
								expenseList.get(i).getExpensesAmount()));

					} else {
						// 将没有设上值得数据变成空
						for (int z = 0; z < titleComp.size(); z++) {
							boolean isIn = false;
							for (Integer in : indexList) {
								if (z == in) {
									isIn = true;
									break;
								}
							}
							if (!isIn) {
								titleComp.set(z, "");
							}
						}

						titleComp.add(totalAmount.toString());
						bodyBean.setAmountList(titleComp);
						bodyList.add(bodyBean);
						// 初始化数据
						bodyBean = new SeRcReLsBodyListBean();
						totalAmount = BigDecimal.ZERO;

						indexList = new ArrayList<Integer>();
						titleComp = initList(titleList);

						employee = expenseList.get(i).getEmployee();

						for (int p = 0; p < titleComp.size(); p++) {
							if (titleComp.get(p).equals(
									expenseList.get(i).getExpensesItem())) {

								titleComp.set(p, expenseList.get(i)
										.getExpensesAmount());
								indexList.add(p);
							}
						}

						totalAmount = totalAmount.add(new BigDecimal(
								expenseList.get(i).getExpensesAmount()));
						bodyBean.setEmployee(employee);
						bodyBean.setEmployeeNo(expenseList.get(i).getEmployeeNo());
					}
				}

				// 将剩余的数据输出
				for (int z = 0; z < titleComp.size(); z++) {
					boolean isIn = false;
					for (Integer in : indexList) {
						if (z == in) {
							isIn = true;
							break;
						}
					}
					if (!isIn) {
						titleComp.set(z, "");
					}
				}
				titleComp.add(totalAmount.toString());
				bodyBean.setAmountList(titleComp);
				bodyList.add(bodyBean);
			}

		}
		
		seRcReLsBean.setHeadList(headList);
		seRcReLsBean.setBodyList(bodyList);
		mapReturn.put("seRcReLsBean", seRcReLsBean);
		return mapReturn;
	}
	
	private List<String> initList(List<String> titleList) {
		List<String> titleComp = new ArrayList<String>();
		for (String str : titleList){
			titleComp.add(str);
		}
		return titleComp;
	}
	
	/**
	 * 点击明细项目时抽取项目
	 * @param request
	 * @param session
	 * @param map
	 * @return
	 */
	@RequestMapping(value = "/showDetail")
	@ResponseBody
	public Map<String, Object> showDetail(HttpServletRequest request, HttpSession session, @RequestBody Map<String, String> map) {
		Map<String, Object> mapReturn = new HashMap<String, Object>();
		try {

			String employeeNo = map.get("employeeNo");
			String travelLocation = map.get("travelLocation");
			
			SeRcReLsBean seRcReLsBean = (SeRcReLsBean) session.getAttribute("ExportSearchBean");
			List<SeEcEpDeListBean> list = exportService.getDetailData(employeeNo, travelLocation, seRcReLsBean);

			for (SeEcEpDeListBean bean : list) {
				bean.setDayFrom(DateFormatCommon.formatDate(bean.getDayFrom(), "3"));
				bean.setDayTo(DateFormatCommon.formatDate(bean.getDayTo(), "3"));
				bean.setExpenseAmount(TypeConvertCommon.convertToCurrencyFmt(new BigDecimal(bean.getExpenseAmount())));
			}
			mapReturn.put("detailList", list);
			mapReturn.put("isException", false);
			return mapReturn;
		} catch (Exception e) {
			logger.error(e.getMessage());
			mapReturn.put("isException", true);
			ErrCommon.errOut(e);
			return null;
		}
		
	}
	
	/**
	 * 报表检索画面
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/Export")
	public void export(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		try {
			SeRcReLsBean seRcReLsBean = (SeRcReLsBean) session.getAttribute("beanToExport");
			List<String> titleList = (List<String>) session.getAttribute("titleList");
			
			createExcelAndExport(request, response, seRcReLsBean, titleList);
		} catch (Exception e) {
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
		}
		
	}
	
	/**
	 * 输出excel报表      
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	private void createExcelAndExport(HttpServletRequest request, HttpServletResponse response, 
			SeRcReLsBean seRcReLsBean, List<String> titleList) throws Exception {
		String path = request.getSession().getServletContext().getRealPath("");
		String tempPath = path + getMessage(CodeCommon.DOWNLOAD_TEMP_PATH);
		String fileName = getMessage(CodeCommon.DOWMLOAD_FOR_FINANCE);
		
		List<SeRcReLsHeadListBean> headList = seRcReLsBean.getHeadList();
		
		List<SeRcReLsBodyListBean> bodyList = seRcReLsBean.getBodyList();
		
		// 获取模板
		File file = new File(tempPath + fileName);
		POIFSFileSystem poifs = new POIFSFileSystem(new FileInputStream(file));
		
		// 读取模板
		HSSFWorkbook wb = new HSSFWorkbook(poifs);
		
		// 这里值操作一个SHEET
		HSSFSheet sheet1 = wb.getSheetAt(0);
		
		HSSFSheet sheet2 = wb.getSheetAt(1);
		
		HSSFCellStyle setBorder = wb.createCellStyle();
		// 画线
		setBorder.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
		setBorder.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
		setBorder.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
		setBorder.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
		HSSFCell cell = null;
		if (headList != null && headList.size() > 0) {
			for (int i = 0; i < headList.size(); i++) {
				HSSFRow row = sheet1.createRow(i + 1);
				
				cell = row.createCell(0);
				cell.setCellValue(headList.get(i).getEmployee());
				cell.setCellStyle(setBorder);
				
				cell = row.createCell(1);
				cell.setCellValue(headList.get(i).getPurpose());
				cell.setCellStyle(setBorder);
				
				cell = row.createCell(2);
				cell.setCellValue(TypeConvertCommon.convertToCurrencyFmt(new BigDecimal(headList.get(i).getAmount())));
				cell.setCellStyle(setBorder);	
			}	
		}
		cell = null;
		if (titleList != null && titleList.size() > 0) {
			HSSFRow row = sheet2.getRow(0);
			HSSFCellStyle titleStyle = row.getCell(0).getCellStyle();
			int index = 0;
			if ("1".equals(seRcReLsBean.getTravelFlg())) {
				cell = row.createCell(1);
				cell.setCellStyle(titleStyle);	
				cell.setCellValue(CodeCommon.REPORT_TRAVEL_LOCATION);
				index++;
			}
			for (int i = 0; i < titleList.size(); i++) {
				cell = row.createCell(i + ("1".equals(seRcReLsBean.getTravelFlg()) ? 2 : 1));
				cell.setCellStyle(titleStyle);	
				cell.setCellValue(titleList.get(i));
				index++;
			}
			cell = row.createCell(index + 1);
			cell.setCellStyle(titleStyle);	
			cell.setCellValue(CodeCommon.REPORT_TOTAL_AMOUNT);
		}
		if (bodyList != null && bodyList.size() > 0) {
			for (int i = 0; i < bodyList.size(); i++) {
				HSSFRow row = sheet2.createRow(i + 1);
				int index = 0;
				cell = row.createCell(0);
				cell.setCellValue(bodyList.get(i).getEmployee());
				cell.setCellStyle(setBorder);
				index++;
				
				if ("1".equals(seRcReLsBean.getTravelFlg())) {
					cell = row.createCell(1);
					cell.setCellValue(bodyList.get(i).getTravelLocation());
					cell.setCellStyle(setBorder);
					index++;
				}
				
				List<String> amount = bodyList.get(i).getAmountList();
				for (int j = 0; j < amount.size(); j++) {
					cell = row.createCell(j + index);
					cell.setCellValue(amount.get(j));
					cell.setCellStyle(setBorder);
				}
			}
		}
		
		String type = "application/x-msdownload";
		response.setContentType(type);
		String downFileName = "dataFile.xls";
		String inlineType = "attachment"; // 是否内联附件
		response.setHeader("Content-Disposition", inlineType + ";filename=\"" + downFileName + "\"");
   
		// 将数据输出到RESPONSE中
		wb.write(response.getOutputStream());
		response.getOutputStream().flush();
		response.getOutputStream().close();
		
	}
 	
}
