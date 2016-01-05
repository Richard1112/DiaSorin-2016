package com.diasorin.oa.mvc;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.diasorin.oa.common.CodeCommon;
import com.diasorin.oa.common.ErrCommon;
import com.diasorin.oa.common.QueryResult;
import com.diasorin.oa.dto.SeRcReMoBean;
import com.diasorin.oa.dto.SeRcReMoSapBean;
import com.diasorin.oa.service.ExportService;

@Controller
@RequestMapping("/SE_RC_RE_MO/*")
public class SeRcReMoController extends BaseController {

	@Resource
	ExportService exportService;
	/**
	 * 报表出力画面(这个出力为SAP服务一个月一次导出)
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("init")
	public String init(Model model, HttpServletRequest request, HttpSession session) {
		try {
			SeRcReMoSapBean seRcReMoSapBean = new SeRcReMoSapBean();
			model.addAttribute("seRcReMoSapBean", seRcReMoSapBean);
			model.addAttribute("listCount", "0");
			return "SE_RC_RE_MO";
		} catch (Exception e) {
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return CodeCommon.COMMON_ERROR_PAGE;
		}
		
	}
	
	
	
	/**
	 * 检索数据
	 * @param model
	 * @param request
	 * @param session
	 */
	@RequestMapping("search")
	public String search(Model model, InputStream inputStream, HttpServletRequest request, HttpServletResponse response, 
			HttpSession session, @ModelAttribute SeRcReMoSapBean seRcReMoSapBean) {
		try {
			session.setAttribute("seRcReMoSapSearchBean", seRcReMoSapBean);
			int maxresult = Integer.parseInt(getMessage("recordCount"));
			int totalPage = 1;
			QueryResult<SeRcReMoBean> list = exportService.searchDateForSap(seRcReMoSapBean, 0 , maxresult);
			
			// 是否有记录
			if (list.getResultlist() != null && list.getTotalrecord() > 0) {
				model.addAttribute("listCount", list.getTotalrecord());
			}else{
				model.addAttribute("listCount", "0");
			}
			
			long resultCount = list.getTotalrecord();
			totalPage=(int) ((resultCount % maxresult ==0) ? resultCount/maxresult : resultCount/maxresult+1);
			
			model.addAttribute("curPage", 1);
			model.addAttribute("totalPage", totalPage);
			seRcReMoSapBean.setBeanList(list.getResultlist());
			// 默认选择全部
			seRcReMoSapBean.setIsAllChecked("1");
			seRcReMoSapBean.setTotalCount(String.valueOf(resultCount));
			seRcReMoSapBean.setSelectCount(String.valueOf(resultCount));
			// 取得总金额
			String totalAmount = exportService.sapTotalAmount(seRcReMoSapBean);
			seRcReMoSapBean.setTotalAmount(totalAmount);
			seRcReMoSapBean.setSelectAmount(totalAmount);
			// 取得总得DETAILNO
			String allDetail = exportService.getAllDetailNo(seRcReMoSapBean);
			seRcReMoSapBean.setDetailId(allDetail + ",");
			seRcReMoSapBean.setHiddenDetailId(allDetail + ",");
			model.addAttribute("searched", "1");
			model.addAttribute("seRcReMoSapBean", seRcReMoSapBean);
			return "SE_RC_RE_MO";
		} catch (Exception e) {
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return CodeCommon.COMMON_ERROR_PAGE;
		}
	}
	
	/**
	 * 分页检索数据
	 * @param model
	 * @param request
	 * @param session
	 */
	@RequestMapping("page")
	public String page(Model model, InputStream inputStream, HttpServletRequest request, HttpServletResponse response, 
			HttpSession session, @ModelAttribute SeRcReMoSapBean befSeRcReMoSapBean) {
		try {
			
			SeRcReMoSapBean seRcReMoSapBean = (SeRcReMoSapBean) session.getAttribute("seRcReMoSapSearchBean");

			int maxresult = Integer.parseInt(getMessage("recordCount"));
			int curPage = Integer.parseInt(request.getParameter("curPage"));
			int startResult = (curPage - 1) * maxresult;
			int totalPage = 1;
			QueryResult<SeRcReMoBean> list = exportService.searchDateForSap(seRcReMoSapBean, startResult , maxresult);
			
			// 是否有记录
			if (list.getResultlist() != null && list.getTotalrecord() > 0) {
				model.addAttribute("listCount", list.getTotalrecord());
			}else{
				model.addAttribute("listCount", "0");
			}
			
			long resultCount = list.getTotalrecord();
			totalPage=(int) ((resultCount % maxresult ==0) ? resultCount/maxresult : resultCount/maxresult+1);
			
			model.addAttribute("curPage", 1);
			model.addAttribute("totalPage", totalPage);
			seRcReMoSapBean.setBeanList(list.getResultlist());
			// 获取前一次操作的值
			seRcReMoSapBean.setIsAllChecked(befSeRcReMoSapBean.getIsAllChecked());
			seRcReMoSapBean.setTotalCount(befSeRcReMoSapBean.getTotalCount());
			seRcReMoSapBean.setSelectCount(befSeRcReMoSapBean.getSelectCount());

			seRcReMoSapBean.setTotalAmount(befSeRcReMoSapBean.getTotalAmount());
			seRcReMoSapBean.setSelectAmount(befSeRcReMoSapBean.getSelectAmount());

			seRcReMoSapBean.setDetailId(befSeRcReMoSapBean.getDetailId());
			seRcReMoSapBean.setHiddenDetailId(befSeRcReMoSapBean.getHiddenDetailId());
			model.addAttribute("searched", "1");
			model.addAttribute("seRcReMoSapBean", seRcReMoSapBean);
			return "SE_RC_RE_MO";
		} catch (Exception e) {
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return CodeCommon.COMMON_ERROR_PAGE;
		}
	}
	
	
	
	/**
	 * 导出数据FOR SAP
	 * @param model
	 * @param request
	 * @param session
	 */
	@RequestMapping("export")
	public void export(Model model, InputStream inputStream, HttpServletRequest request, HttpServletResponse response, 
			HttpSession session, @ModelAttribute SeRcReMoSapBean seRcReMoSapBean) {
		try {
			
			List<SeRcReMoBean> list = exportService.getDateForSap(seRcReMoSapBean);
			list = reCreate(list);
			// 数据重新整合
			createExcelAndExport(request, response, list);
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
	private void createExcelAndExport(HttpServletRequest request, HttpServletResponse response, List<SeRcReMoBean> list) throws Exception {
		String path = request.getSession().getServletContext().getRealPath("");
		String tempPath = path + getMessage(CodeCommon.DOWNLOAD_TEMP_PATH);
		String fileName = getMessage(CodeCommon.DOWMLOAD_FOR_SAP);
		
		// 获取模板
		File file = new File(tempPath + fileName);
		POIFSFileSystem poifs = new POIFSFileSystem(new FileInputStream(file));
		
		// 读取模板
		HSSFWorkbook wb = new HSSFWorkbook(poifs);
		
		// 这里值操作一个SHEET
		HSSFSheet sheet = wb.getSheetAt(0);
		
		HSSFCellStyle setBorder = wb.createCellStyle();
		// 画线
		setBorder.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
		setBorder.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
		setBorder.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
		setBorder.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
		HSSFCell cell = null;
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				HSSFRow row = sheet.createRow(i + 1);
				
				cell = row.createCell(0);
				cell.setCellValue(list.get(i).getCostCenter());
				cell.setCellStyle(setBorder);
				
				cell = row.createCell(1);
				cell.setCellValue(list.get(i).getAmount());
				cell.setCellStyle(setBorder);
				
				cell = row.createCell(2);
				cell.setCellValue(list.get(i).getPostingKey());
				cell.setCellStyle(setBorder);
				
				cell = row.createCell(3);
				cell.setCellValue(list.get(i).getAccount());
				cell.setCellStyle(setBorder);
				
				cell = row.createCell(4);
				cell.setCellValue(list.get(i).getText());
				cell.setCellStyle(setBorder);
				
				cell = row.createCell(5);
				cell.setCellValue(list.get(i).getTaxKey());
				cell.setCellStyle(setBorder);
				
				cell = row.createCell(6);
				cell.setCellValue(list.get(i).getAssignment());
				cell.setCellStyle(setBorder);
				
				cell = row.createCell(7);
				cell.setCellValue(list.get(i).getInternalOrderNumber());
				cell.setCellStyle(setBorder);
				
				cell = row.createCell(8);
				cell.setCellValue(list.get(i).getOrder());
				cell.setCellStyle(setBorder);
				
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
	
	private List<SeRcReMoBean> reCreate(List<SeRcReMoBean> list) {
		if (list == null || list.size() == 0 ) return null;
		
		// 成本中心
		String costCenter = list.get(0).getCostCenter();
		// 员工
		String employeeNo = list.get(0).getInternalOrderNumber();
		
		List<SeRcReMoBean> newList = new ArrayList<SeRcReMoBean>();
		BigDecimal totalAmount = BigDecimal.ZERO;
		for (SeRcReMoBean bean : list) {
			if (costCenter.equals(bean.getCostCenter()) && employeeNo.equals(bean.getInternalOrderNumber())) {
				SeRcReMoBean addBean = new SeRcReMoBean();
				addBean.setCostCenter(bean.getCostCenter());
				addBean.setAmount(bean.getAmount());
				addBean.setPostingKey(CodeCommon.POSTING_KEY_DETAIL);
				addBean.setAccount(bean.getAccount());
				addBean.setText(bean.getText());
				addBean.setTaxKey(CodeCommon.TAX_KEY);
				addBean.setInternalOrderNumber(bean.getInternalOrderNumber());
				newList.add(addBean);
				totalAmount = totalAmount.add(new BigDecimal(bean.getAmount()));
			} else {
				// 先产生合计记录
				SeRcReMoBean addBean = new SeRcReMoBean();
				addBean.setAmount(totalAmount.toString());
				addBean.setPostingKey(CodeCommon.POSTING_KEY_TOTAL);
				addBean.setAccount(CodeCommon.ACCOUNT_TOTAL);
				
				newList.add(addBean);
				
				// 清空金额
				totalAmount = BigDecimal.ZERO;
				costCenter = bean.getCostCenter();
				employeeNo = bean.getInternalOrderNumber();
				
				addBean = new SeRcReMoBean();
				addBean.setCostCenter(bean.getCostCenter());
				addBean.setAmount(bean.getAmount());
				addBean.setPostingKey(CodeCommon.POSTING_KEY_DETAIL);
				addBean.setAccount(bean.getAccount());
				addBean.setText(bean.getText());
				addBean.setTaxKey(CodeCommon.TAX_KEY);
				addBean.setInternalOrderNumber(bean.getInternalOrderNumber());
				newList.add(addBean);
				totalAmount = totalAmount.add(new BigDecimal(bean.getAmount()));

			}
		}
		
		// 最后在产生一条合计记录
		// 先产生合计记录
		SeRcReMoBean addBean = new SeRcReMoBean();
		addBean.setAmount(totalAmount.toString());
		addBean.setPostingKey(CodeCommon.POSTING_KEY_TOTAL);
		addBean.setAccount(CodeCommon.ACCOUNT_TOTAL);
		newList.add(addBean);
		
		return newList;
	}
	
}
