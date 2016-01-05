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

import org.apache.commons.lang3.StringUtils;
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
import com.diasorin.oa.dto.SeRcReInBean;
import com.diasorin.oa.dto.SeRcReInBodyListBean;
import com.diasorin.oa.dto.SeRcReInExBean;
import com.diasorin.oa.dto.SeRcReInExpenseListBean;
import com.diasorin.oa.dto.SeRcReInHeadListBean;
import com.diasorin.oa.model.EmployeeInfo;
import com.diasorin.oa.service.BaseService;
import com.diasorin.oa.service.EmployeeManagementService;
import com.diasorin.oa.service.ExportService;
import com.diasorin.oa.service.MenuService;
import com.diasorin.oa.service.SystemManagementService;

@Controller
@RequestMapping("/SE_RC_RE_IN/*")
public class SeRcReInController extends BaseController {
	
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
			SeRcReInBean seRcReInBean = new SeRcReInBean();
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
			seRcReInBean.setCostCenter(employeeInfo.getDeptCode());
			model.addAttribute("seRcReInBean", seRcReInBean);
			return "SE_RC_RE_IN";
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

			SeRcReInBean seRcReInBean = new SeRcReInBean();
			seRcReInBean.setFinance(map.get("finance"));
			seRcReInBean.setFinanceDateFrom(map.get("financeDateFrom"));
			seRcReInBean.setFinanceDateTo(map.get("financeDateTo"));
			seRcReInBean.setEmployee(map.get("employee"));
			seRcReInBean.setTravelFlg(map.get("travelFlg"));
			seRcReInBean.setExpenseType(map.get("expenseType"));
			seRcReInBean.setCostCenter(map.get("costCenter"));
			seRcReInBean.setCostCenterFlg(map.get("costCenterFlg"));
			seRcReInBean.setEmployeeFlg(map.get("employeeFlg"));
			seRcReInBean.setExpensesTypeFlg(map.get("expensesTypeFlg"));
			
			session.setAttribute("ExportSearchBean", seRcReInBean);

			// 取得明细项目的TITLE
			List<String> titleList = exportService.getDetailTitleList(seRcReInBean);
			// TITLE 明细
			List<String> titleNameList = new ArrayList<String>();
			
			for (String str : titleList){
				titleNameList.add(systemManagementService.getExpensesItemInfo(str).getExpenseName());
			}
			
			mapReturn = createMap(0, seRcReInBean, titleList, mapReturn);
			session.setAttribute("beanToExport", mapReturn.get("seRcReInBean"));
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
	 * @param seRcReInBean
	 * @return
	 * @throws Exception 
	 */
	private Map<String, Object> createMap(int start, SeRcReInBean seRcReInBean, List<String> titleList, Map<String, Object> mapReturn) throws Exception {

		// 取得目的数据一览
		List<SeRcReInHeadListBean> headList = exportService.getPurposeList(-1, -1, seRcReInBean);
		
		// 用来数据的比较
		List<String> titleComp = new ArrayList<String>();
		// 用来判断哪些expense被赋值了。
		List<Integer> indexList = new ArrayList<Integer>();
		
		for (String str : titleList){
			titleComp.add(str);
		}
		
		// 取得明细项目详细数据
		List<SeRcReInExpenseListBean> expenseList = exportService.getDetailList(-1, -1, seRcReInBean);
		
		List<SeRcReInBodyListBean> bodyList = new ArrayList<SeRcReInBodyListBean>();
		
		// 判断到底是以什么来集计的
		
		if ("1".equals(seRcReInBean.getTravelFlg())) {
			// 以员工和出差地来集计
			if (expenseList != null && expenseList.size() > 0) {
				SeRcReInBodyListBean bodyBean = new SeRcReInBodyListBean();
				
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
						bodyBean = new SeRcReInBodyListBean();
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
				SeRcReInBodyListBean bodyBean = new SeRcReInBodyListBean();

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
						bodyBean = new SeRcReInBodyListBean();
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
		
		seRcReInBean.setHeadList(headList);
		seRcReInBean.setBodyList(bodyList);
		mapReturn.put("seRcReInBean", seRcReInBean);
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
			
			SeRcReInBean seRcReInBean = (SeRcReInBean) session.getAttribute("ExportSearchBean");
			List<SeEcEpDeListBean> list = exportService.getDetailData(employeeNo, travelLocation, seRcReInBean);

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
			SeRcReInBean seRcReInBean = (SeRcReInBean) session.getAttribute("beanToExport");
			List<String> titleList = (List<String>) session.getAttribute("titleList");
			
			createExcelAndExport(request, response, seRcReInBean, titleList);
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
			SeRcReInBean seRcReInBean, List<String> titleList) throws Exception {
		String path = request.getSession().getServletContext().getRealPath("");
		String tempPath = path + getMessage(CodeCommon.DOWNLOAD_TEMP_PATH);
		String fileName = getMessage(CodeCommon.DOWMLOAD_FOR_FINANCE);
		
		List<SeRcReInHeadListBean> headList = seRcReInBean.getHeadList();
		
		//List<SeRcReInBodyListBean> bodyList = seRcReInBean.getBodyList();
		
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
//		if (titleList != null && titleList.size() > 0) {
//			HSSFRow row = sheet2.getRow(0);
//			HSSFCellStyle titleStyle = row.getCell(0).getCellStyle();
//			int index = 0;
//			if ("1".equals(seRcReInBean.getTravelFlg())) {
//				cell = row.createCell(1);
//				cell.setCellStyle(titleStyle);	
//				cell.setCellValue(CodeCommon.REPORT_TRAVEL_LOCATION);
//				index++;
//			}
//			for (int i = 0; i < titleList.size(); i++) {
//				cell = row.createCell(i + ("1".equals(seRcReInBean.getTravelFlg()) ? 2 : 1));
//				cell.setCellStyle(titleStyle);	
//				cell.setCellValue(titleList.get(i));
//				index++;
//			}
//			cell = row.createCell(index + 1);
//			cell.setCellStyle(titleStyle);	
//			cell.setCellValue(CodeCommon.REPORT_TOTAL_AMOUNT);
//		}
		// 第二个sheet全部的detail.

		// 明细项目全部抽出
		List<SeRcReInExBean> bodyList = exportService.getExportData(seRcReInBean);
		HSSFRow rowTitle = sheet2.getRow(0);
		HSSFCellStyle titleStyle = rowTitle.getCell(0).getCellStyle();
		for (int i = 0; i < CodeCommon.NO_SUBTOTAL.length; i++) {
			cell = rowTitle.createCell(i);
			cell.setCellStyle(titleStyle);	
			cell.setCellValue(CodeCommon.NO_SUBTOTAL[i]);
		}
		if (bodyList != null && bodyList.size() > 0) {
			for (int i = 0; i < bodyList.size(); i++) {
				HSSFRow row = sheet2.createRow(i + 1);

				cell = row.createCell(0);
				cell.setCellValue(DateFormatCommon.convertToShow(bodyList.get(i).getSubmitDate()));
				cell.setCellStyle(setBorder);
				
				cell = row.createCell(1);
				cell.setCellValue(StringUtils.isEmpty(bodyList.get(i).getApprovalDate()) ? "" : bodyList.get(i).getApprovalDate());
				cell.setCellStyle(setBorder);
				
				cell = row.createCell(2);
				cell.setCellValue(StringUtils.isEmpty(bodyList.get(i).getFinishedDate()) ? "" : bodyList.get(i).getFinishedDate());
				cell.setCellStyle(setBorder);
				
				cell = row.createCell(3);
				cell.setCellValue(bodyList.get(i).getCostCenter());
				cell.setCellStyle(setBorder);
				
				cell = row.createCell(4);
				cell.setCellValue(bodyList.get(i).getEmployee());
				cell.setCellStyle(setBorder);
				
				cell = row.createCell(5);
				cell.setCellValue(bodyList.get(i).getApplicationNo());
				cell.setCellStyle(setBorder);
				
				cell = row.createCell(6);
				cell.setCellValue(bodyList.get(i).getPurposeNo());
				cell.setCellStyle(setBorder);
				
				cell = row.createCell(7);
				cell.setCellValue(bodyList.get(i).getTravelReason());
				cell.setCellStyle(setBorder);
				
				cell = row.createCell(8);
				cell.setCellValue(bodyList.get(i).getExpenseType());
				cell.setCellStyle(setBorder);
				
				cell = row.createCell(9);
				cell.setCellValue(
						StringUtils.isEmpty(bodyList.get(i).getDayFrom()) ? 
								bodyList.get(i).getDayTo() : 
								bodyList.get(i).getDayFrom() + "~" + bodyList.get(i).getDayTo());
				cell.setCellStyle(setBorder);
				
				cell = row.createCell(10);
				cell.setCellValue(bodyList.get(i).getLocation());
				cell.setCellStyle(setBorder);
				
				cell = row.createCell(11);
				cell.setCellValue(bodyList.get(i).getAmount());
				cell.setCellStyle(setBorder);
				
				cell = row.createCell(12);
				cell.setCellValue(bodyList.get(i).getComments());
				cell.setCellStyle(setBorder);
				
			}
		}
		for (int i = 0; i < CodeCommon.NO_SUBTOTAL_WIDTH.length; i++) {
			sheet2.setColumnWidth(i, CodeCommon.NO_SUBTOTAL_WIDTH[i]);
		}
		
	
		if ("1".equals(seRcReInBean.getEmployeeFlg())) {
			// 第三个SHEET合计
			HSSFSheet sheet3 = wb.getSheetAt(2);
			// 员工集计
			List<SeRcReInExpenseListBean> bodyListThree = exportService.getExportDataGroupByEm(seRcReInBean);
			rowTitle = sheet3.getRow(0);
			titleStyle = rowTitle.getCell(0).getCellStyle();
			for (int i = 0; i < CodeCommon.EMPLOYEE_SUBTOTAL.length; i++) {
				cell = rowTitle.createCell(i);
				cell.setCellStyle(titleStyle);	
				cell.setCellValue(CodeCommon.EMPLOYEE_SUBTOTAL[i]);
			}
			if (bodyListThree != null && bodyListThree.size() > 0) {
				BigDecimal totalAmount = BigDecimal.ZERO;
				for (int i = 0; i < bodyListThree.size(); i++) {
					HSSFRow row = sheet3.createRow(i + 1);
					cell = row.createCell(0);
					cell.setCellValue(bodyListThree.get(i).getEmployeeNo());
					cell.setCellStyle(setBorder);
					
					cell = row.createCell(1);
					cell.setCellValue(bodyListThree.get(i).getEmployee());
					cell.setCellStyle(setBorder);
					
					cell = row.createCell(2);
					cell.setCellValue(bodyListThree.get(i).getExpensesAmount());
					cell.setCellStyle(setBorder);
					
					totalAmount = totalAmount.add(new BigDecimal(bodyListThree.get(i).getExpensesAmount()));
				}
				
				// 这里显示总金额
				HSSFRow row = sheet3.createRow(bodyListThree.size() + 1);
				cell = row.createCell(0);
				cell.setCellValue("");
				cell.setCellStyle(setBorder);
				
				cell = row.createCell(1);
				cell.setCellValue("Grand Total");
				cell.setCellStyle(setBorder);
				
				cell = row.createCell(2);
				cell.setCellValue(totalAmount.toString());
				cell.setCellStyle(setBorder);
			}
			
			for (int i = 0; i < CodeCommon.EMPLOYEE_SUBTOTAL_WIDTH.length; i++) {
				sheet3.setColumnWidth(i, CodeCommon.EMPLOYEE_SUBTOTAL_WIDTH[i]);
			}
		
		} else {
			//将第三个sheet删除
			wb.removeSheetAt(2);
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
