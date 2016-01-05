package com.diasorin.oa.mvc;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.diasorin.oa.common.CodeCommon;
import com.diasorin.oa.common.DateFormatCommon;
import com.diasorin.oa.common.ErrCommon;
import com.diasorin.oa.common.QueryResult;
import com.diasorin.oa.common.TypeConvertCommon;
import com.diasorin.oa.dto.AuthorityByListBean;
import com.diasorin.oa.dto.ClaimInfoBean;
import com.diasorin.oa.dto.SeAcApDeBean;
import com.diasorin.oa.dto.SeEcEpDeBean;
import com.diasorin.oa.dto.SeEcEpDeListBean;
import com.diasorin.oa.model.EmployeeInfo;
import com.diasorin.oa.model.ExpensePurposeSum;
import com.diasorin.oa.model.ExpensesApplication;
import com.diasorin.oa.model.ExpensesDetails;
import com.diasorin.oa.model.WorkflowNodeDefination;
import com.diasorin.oa.model.WorkflowProgress;
import com.diasorin.oa.service.BaseService;
import com.diasorin.oa.service.EmployeeManagementService;
import com.diasorin.oa.service.ExpensesManagementService;
import com.diasorin.oa.service.SystemManagementService;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;


@Controller
@RequestMapping("/SE_AC_AP_DE/*")
public class SeAcApDeController extends BaseController {
	
	private final static String UPDPRMID = "SE_AC_AP_DE";

	@Resource
	ExpensesManagementService expensesManagementService;
	
	@Resource
	EmployeeManagementService employeeManagementService;
	
	@Resource
	SystemManagementService systemManagementService;
	
	@Resource
	BaseService baseService;
	
	// 字体数据字体
	private final static Font font_g8_c = FontFactory.getFont(FontFactory.TIMES_ROMAN, 8f, Font.NORMAL, Color.BLACK);
	
	// 字体数据字体
	private final static Font font_g16_c = FontFactory.getFont(FontFactory.TIMES_ROMAN, 16f, Font.NORMAL, Color.BLACK);
	
	// 字体数据字体
	private final static Font font_g12_c = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12f, Font.NORMAL, Color.BLACK);
	
	// 字体数据字体
	private final static Font font_g10_c = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10f, Font.NORMAL, Color.BLACK);
	
	
	// 明细项目标题栏背景色
	private final static Color DETAIL_TITLE_COLOR = new Color(204, 204, 204);
	
	// 总金额
	private BigDecimal totalAmountDec = BigDecimal.ZERO;
	
	/**
	 * 驳回操作
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping("reject")
	@ResponseBody
	public Map<String, Object> init(Model model, HttpServletRequest request, HttpSession session, @RequestBody Map<String, String> mapRequest) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			
			String userId = (String)session.getAttribute(CodeCommon.SESSION_USERID);
			
			SeAcApDeBean seAcApDeBean = new SeAcApDeBean();
			// 申请编号
			seAcApDeBean.setExpenseAppNo(mapRequest.get("applicationNo"));
			// 驳回理由
			seAcApDeBean.setRejectReason(mapRequest.get("rejectReason"));
			// 驳回明细NO
			seAcApDeBean.setErrorDetailNo(mapRequest.get("errorDetailNo"));
			expensesManagementService.expenseReject(seAcApDeBean, userId, UPDPRMID);
					
			map.put("isException", false);
			return map;
		} catch (Exception e) {
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return null;
		}
		
	}
	
	/**
	 * 审批通过操作
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/approve")
	@ResponseBody
	public Map<String, Object> approve(HttpServletRequest request, HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String userId = (String)session.getAttribute(CodeCommon.SESSION_USERID);
			// 申请编号
			String expenseAppNo = (String)request.getParameter("expenseAppNo");
			// 获取申请信息
			ExpensesApplication expensesApplication = expensesManagementService.expensesClaimQuery(expenseAppNo);
			// 获取审批流程
			WorkflowProgress workflowProgress = expensesManagementService.getWorkflowProgressInfo(expenseAppNo);
			// 获取报销申请同一目的项目
			QueryResult<ExpensePurposeSum> purposeDetails = expensesManagementService.expensesClaimPurposeListQuery(expenseAppNo);
			List<ExpensesDetails>  detailAllList = new ArrayList<ExpensesDetails>();
			// purpose List
			if (purposeDetails != null && purposeDetails.getTotalrecord() > 0) {
				for (ExpensePurposeSum detail : purposeDetails.getResultlist()) {
					// 这里再次检索同一目的
					QueryResult<ExpensesDetails> expenseDetailsList = expensesManagementService.expensesClaimDetailsListQuery(detail.getExpensesDetailsNo());
					for (ExpensesDetails expensesDetails : expenseDetailsList.getResultlist()) {
						detailAllList.add(expensesDetails);
					}
				}
			}
			// 得到下一个节点
			WorkflowNodeDefination nextNode = expensesManagementService.getNextDefination(workflowProgress.getNodeId());
			WorkflowNodeDefination nextNodeParam = null;
			if (nextNode != null) {
				// 去判断是不是符合要求并返回
				nextNodeParam = expensesManagementService.getNextNodeDeFinaton(nextNode, expensesApplication.getExpenseSum(), detailAllList);
			}
			expensesManagementService.expenseApproved(expenseAppNo, userId, UPDPRMID, nextNodeParam);
			map.put("overAuthority", false);

			map.put("isException", false);
			return map;
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put("isException", true);
			ErrCommon.errOut(e);
			return null;
		}
	}
	
	/**
	 * 撤销完成操作
	 * @param request
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/undoFinished")
	@ResponseBody
	public Map<String, Object> undoFinished(HttpServletRequest request, HttpSession session) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 申请编号
			String expenseAppNo = (String)request.getParameter("expenseAppNo");
			
			expensesManagementService.expenseUndoFinished(expenseAppNo);

			map.put("overAuthority", false);

			map.put("isException", false);
			return map;
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put("isException", true);
			ErrCommon.errOut(e);
			return null;
		}
	}
	

	
	/**
	 * 将报销明细打印出画面
	 * @param model
	 * @param request
	 * @param session
	 */
	@RequestMapping("print")
	public void export(Model model, InputStream inputStream, HttpServletRequest request, HttpServletResponse response, 
			HttpSession session) {
		try {
			String path = request.getSession().getServletContext().getRealPath("");
			String tempPath = path + getMessage(CodeCommon.DOWNLOAD_TEMP_PATH);
			
			
			// 获取画面的数据
			ClaimInfoBean claimInfoBean = (ClaimInfoBean) session.getAttribute(CodeCommon.SESSION_CLAIM_INFO);
			
			String fileName = "Expense Note "+ (claimInfoBean.getExpenseAppNo() == null ? "" : claimInfoBean.getExpenseAppNo()) +".pdf";
			// 先将数据生成
			createPdf(path, tempPath + fileName, claimInfoBean, session);
			// 将文件输出
			InputStream inStream = new FileInputStream(tempPath + fileName);
			// 输出格式
			response.reset();
			response.setContentType("application/pdf");
			response.addHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
			// 循环取出流中的数据
			byte[] b = new byte[100];
			int len;
			try {
				while ((len = inStream.read(b)) > 0)
				response.getOutputStream().write(b, 0, len);
				inStream.close();
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
		}
	}
	
	/**
	 * 一览画面将PDF数据打出
	 * @param model
	 * @param request
	 * @param session
	 */
	@RequestMapping("toExportFromList")
	public void toExportFromList(Model model, InputStream inputStream, HttpServletRequest request, HttpServletResponse response, 
			HttpSession session) {
		try {
			String path = request.getSession().getServletContext().getRealPath("");
			String tempPath = path + getMessage(CodeCommon.DOWNLOAD_TEMP_PATH);
			
			
			// 获取画面的数据
			ClaimInfoBean claimInfoBean = getClaimDate((String)request.getParameter("expenseAppNo"));
			
			String fileName = "Expense Note "+ (claimInfoBean.getExpenseAppNo() == null ? "" : claimInfoBean.getExpenseAppNo()) +".pdf";
			// 先将数据生成
			createPdf(path, tempPath + fileName, claimInfoBean, session);
			// 将文件输出
			InputStream inStream = new FileInputStream(tempPath + fileName);
			// 输出格式
			response.reset();
			response.setContentType("application/pdf");
			response.addHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
			// 循环取出流中的数据
			byte[] b = new byte[100];
			int len;
			try {
				while ((len = inStream.read(b)) > 0)
				response.getOutputStream().write(b, 0, len);
				inStream.close();
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
		}
	}
	
	private ClaimInfoBean getClaimDate(String expenseAppNo) throws Exception {
			

		// 获取报销申请
		ExpensesApplication expensesApplication = expensesManagementService.expensesClaimQuery(expenseAppNo);
		
		// 获取报销申请同一目的项目
		QueryResult<ExpensePurposeSum> purposeDetails = expensesManagementService.expensesClaimPurposeListQuery(expenseAppNo);
	
		// 将所有的数据放入
		ClaimInfoBean claimInfoBean = new ClaimInfoBean();
		claimInfoBean.setTravelLocalType(expensesApplication.getTravelLocalType());
		claimInfoBean.setTravelReason(expensesApplication.getTravelReason());
		claimInfoBean.setCostCenter(expensesApplication.getCostCenterCode());
		claimInfoBean.setExpenseAppNo(expenseAppNo);
		claimInfoBean.setAppStatus(expensesApplication.getStatus());
		claimInfoBean.setClaimDateFrom(DateFormatCommon.formatDate(expensesApplication.getTravelDateStart(), "3"));
		claimInfoBean.setClaimDateTo(DateFormatCommon.formatDate(expensesApplication.getTravelDateEnd(), "3"));
		// purpose List
		List<SeEcEpDeBean> purposeList = new ArrayList<SeEcEpDeBean>();
		if (purposeDetails != null && purposeDetails.getTotalrecord() > 0) {
			for (ExpensePurposeSum detail : purposeDetails.getResultlist()) {
				SeEcEpDeBean bean = new SeEcEpDeBean();
				bean.setPurposeNo(detail.getExpensesDetailsNo());
				bean.setPurpose(detail.getExpensesPurpose());
				bean.setAmount(detail.getExpensesAmount());
				bean.setExpensesAppNo(expenseAppNo);
				bean.setHasPurposeNo(detail.getExpensesDetailsNo());
				// 这里再次检索同一目的
				QueryResult<ExpensesDetails> expenseDetailsList = expensesManagementService.expensesClaimDetailsListQuery(detail.getExpensesDetailsNo());
				List<SeEcEpDeListBean> contentDetailList = new ArrayList<SeEcEpDeListBean>();
				for (ExpensesDetails expensesDetails : expenseDetailsList.getResultlist()) {
					SeEcEpDeListBean listBean = new SeEcEpDeListBean();
					listBean.setNo(expensesDetails.getExpensesDetailsNo());
					listBean.setExpenseType(expensesDetails.getExpensesItem());
					listBean.setDayFrom(DateFormatCommon.formatDate(expensesDetails.getExpensesDate(), "3"));
					listBean.setDayTo(DateFormatCommon.formatDate(expensesDetails.getExpensesDateEnd(), "3"));
					listBean.setLocation(expensesDetails.getTravelLocation());
					listBean.setExpenseAmount(TypeConvertCommon.convertToCurrencyFmt(expensesDetails.getExpensesAmount()));
					listBean.setComments(expensesDetails.getExpensesComments());
					listBean.setTimeMethod(expensesDetails.getExpensesDateType());
					listBean.setRejectErrorFlg(expensesDetails.getRejectErrorFlg());
					contentDetailList.add(listBean);
				}
				bean.setBeanList(contentDetailList);
				purposeList.add(bean);
			}
			claimInfoBean.setBeanList(purposeList);
		}
		return claimInfoBean;
	}
	
	
	
	/**
	 * 手动创建PDF文件
	 * @throws Exception
	 */
	private void createPdf(String contentPath, String fileName, ClaimInfoBean claimInfoBean, HttpSession session) throws Exception{
		// create document object
		Document document = new Document(PageSize.A4,18, 18, 18, 18);
		try {
			// 部门名
			String costCenterCode = claimInfoBean.getCostCenter();
			String costCenterName = systemManagementService.getCostCenterInfo(claimInfoBean.getCostCenter()).getCostCenterName();
			String employeeId = "";
			// 申请日期
			String appDate = "";
			// 获取报销申请
			if (StringUtils.isEmpty(claimInfoBean.getExpenseAppNo())) {
				employeeId = (String) session.getAttribute(CodeCommon.SESSION_USERID);
			} else {
				ExpensesApplication expensesApplication = expensesManagementService.expensesClaimQuery(claimInfoBean.getExpenseAppNo());
				employeeId = expensesApplication.getEmployeeNo();
				appDate = DateFormatCommon.formatDate(expensesApplication.getApplicationDate(), "3");
			}
			// 员工名
			EmployeeInfo info = employeeManagementService.employeeInfoView(employeeId);
			String employeeName = info.getEmployeeNameEn();
			
			// 报销发生时间段
			String expenseOccur = DateFormatCommon.formatDate(claimInfoBean.getClaimDateFrom(), "3") + "~" +
									DateFormatCommon.formatDate(claimInfoBean.getClaimDateTo(), "3");
			// 出差地类型
			String travelLocalType = baseService.getSelect(CodeCommon.COM004, claimInfoBean.getTravelLocalType());
			
			// 出差理由
			String travelReasonData = claimInfoBean.getTravelReason();
			
			
			// out put file
			PdfWriter.getInstance(document,new FileOutputStream(fileName));
			/*Title*/
			document.addTitle("ExpenseDetail");
			/*Author*/
			document.addAuthor("");
			/*Subject*/
			document.addSubject("");
			/*Keywords*/
			document.addKeywords("");
			/*Creator*/
			document.addCreator("");
			
			HeaderFooter footer = new HeaderFooter(new Phrase("Page:",font_g10_c), true);
			footer.setBorderWidth(0f);
			footer.setAlignment(Element.ALIGN_RIGHT);
			document.setFooter(footer);

			document.open();
			
			//head table setting
			PdfPTable headTable = new PdfPTable(4);
			float hwsHead[] = { 176f, 200f, 250f, 350f};
			headTable.setWidths(hwsHead);
			headTable.setWidthPercentage(100);
			
			
			// 加载左侧LOGO
			Image imageLogo = Image.getInstance(contentPath+"/images/diasorin_logo.bmp");
			PdfPCell headLogo = new PdfPCell();
			headLogo.setBorderWidth(0.5f);
			headLogo.setPaddingTop(1f);
			headLogo.setHorizontalAlignment(Element.ALIGN_LEFT);
			headLogo.setImage(imageLogo);
			headLogo.setBorder(Rectangle.BOX);
			headTable.addCell(headLogo);
			
			// Expense Note
			PdfPCell headTitle = new PdfPCell(new Phrase("Expenses note       "+
			(claimInfoBean.getExpenseAppNo() == null ? "" : claimInfoBean.getExpenseAppNo()) +"", font_g16_c));
			headTitle.setBorderWidth(0.5f);
			headTitle.setPaddingTop(10f);
			headTitle.setHorizontalAlignment(Element.ALIGN_LEFT);
			headTitle.setBorder(Rectangle.BOX);
			headTable.addCell(headTitle);
			
			// 员工
			PdfPCell cellSb1Code = new PdfPCell(new Phrase("SB1 Code:" + employeeName, font_g10_c));
			cellSb1Code.setBorderWidth(0.5f);
			cellSb1Code.setPaddingTop(15f);
			cellSb1Code.setHorizontalAlignment(Element.ALIGN_LEFT);
			cellSb1Code.setBorder(Rectangle.BOX);
			headTable.addCell(cellSb1Code);
			
			// 部门
			PdfPCell cellCostCenter = new PdfPCell(new Phrase("Cost Center:" + costCenterCode.substring(0, 6) + "/" + costCenterName, font_g10_c));
			cellCostCenter.setBorderWidth(0.5f);
			cellCostCenter.setPaddingTop(15f);
			cellCostCenter.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cellCostCenter.setBorder(Rectangle.BOX);
			headTable.addCell(cellCostCenter);
			
			
			PdfPTable emptyRow1 = createEmptyRow();
			
			// 表头数据
			PdfPTable dataTable = new PdfPTable(4);
			float dataWidth[] = { 165f, 400f, 200f, 300f};

			dataTable.setWidths(dataWidth);
			dataTable.setWidthPercentage(100);
			
			// 项目发生时间段
			PdfPCell occurDateColumn = new PdfPCell(new Phrase("Date:", font_g8_c));
			occurDateColumn.setBorderWidth(0.5f);
			occurDateColumn.setPaddingTop(2.5f);
			occurDateColumn.setPaddingBottom(2.5f);
			occurDateColumn.setHorizontalAlignment(Element.ALIGN_LEFT);
			occurDateColumn.setBorder(Rectangle.BOX);
			occurDateColumn.setVerticalAlignment(Element.ALIGN_RIGHT);
			dataTable.addCell(occurDateColumn);
			
			// 项目发生时间段
			PdfPCell occurDate = new PdfPCell(new Phrase(expenseOccur, font_g8_c));
			occurDate.setBorderWidth(0.5f);
			occurDateColumn.setPaddingTop(2.5f);
			occurDateColumn.setPaddingBottom(2.5f);
			occurDate.setHorizontalAlignment(Element.ALIGN_LEFT);
			occurDate.setBorder(Rectangle.BOX);
			dataTable.addCell(occurDate);
			
			// 出差地类型
			PdfPCell travelTypeColumn = new PdfPCell(new Phrase("Travel Local Type:", font_g8_c));
			travelTypeColumn.setBorderWidth(0.5f);
			occurDateColumn.setPaddingTop(2.5f);
			occurDateColumn.setPaddingBottom(2.5f);
			travelTypeColumn.setHorizontalAlignment(Element.ALIGN_LEFT);
			travelTypeColumn.setBorder(Rectangle.BOX);
			travelTypeColumn.setVerticalAlignment(Element.ALIGN_RIGHT);
			dataTable.addCell(travelTypeColumn);
			
			// 出差地类型
			PdfPCell travelType = new PdfPCell(new Phrase(travelLocalType, font_g8_c));
			travelType.setBorderWidth(0.5f);
			occurDateColumn.setPaddingTop(2.5f);
			occurDateColumn.setPaddingBottom(2.5f);
			travelType.setHorizontalAlignment(Element.ALIGN_LEFT);
			travelType.setBorder(Rectangle.BOX);
			dataTable.addCell(travelType);
			
			// 出差理由
			PdfPCell travelReasonColumn = new PdfPCell(new Phrase("Travel Reason:", font_g8_c));
			travelReasonColumn.setBorderWidth(0.5f);
			occurDateColumn.setPaddingTop(2.5f);
			occurDateColumn.setPaddingBottom(2.5f);
			travelReasonColumn.setHorizontalAlignment(Element.ALIGN_LEFT);
			travelReasonColumn.setBorder(Rectangle.BOX);
			travelReasonColumn.setVerticalAlignment(Element.ALIGN_RIGHT);
			dataTable.addCell(travelReasonColumn);
			
			// 出差理由
			PdfPCell travelReason = new PdfPCell(new Phrase(travelReasonData, font_g8_c));
			travelReason.setBorderWidth(0.5f);
			travelReason.setPaddingTop(1f);
			travelReason.setColspan(3);
			travelReason.setHorizontalAlignment(Element.ALIGN_LEFT);
			travelReason.setBorder(Rectangle.BOX);
			dataTable.addCell(travelReason);
			
			// 首先summary
			PdfPTable summaryTable = new PdfPTable(1);
			float summaryWidth[] = { 165f};
			summaryTable.setSpacingBefore(10f);
			summaryTable.setWidths(summaryWidth);
			summaryTable.setWidthPercentage(100);
			
			PdfPCell summaryColumn = new PdfPCell(new Phrase("Summary", font_g12_c));
			summaryColumn.setBorderWidth(0.5f);
			summaryColumn.setPaddingTop(10f);
			summaryColumn.setPaddingBottom(10f);
			summaryColumn.setHorizontalAlignment(Element.ALIGN_LEFT);
			summaryColumn.setBorder(Rectangle.NO_BORDER);
			summaryTable.addCell(summaryColumn);
			
			// summary 明细项目标题
			PdfPTable summaryDetail = new PdfPTable(3);
			float summaryDetailWidth[] = { 5, 50, 15};
			summaryDetail.setSpacingBefore(10f);
			summaryDetail.setWidths(summaryDetailWidth);
			summaryDetail.setWidthPercentage(60);
			summaryDetail.setHorizontalAlignment(Element.ALIGN_LEFT);
			
			// No.
			PdfPCell noColumn = new PdfPCell(new Phrase("NO.", font_g8_c));
			noColumn.setBorderWidth(0.5f);
			noColumn.setHorizontalAlignment(Element.ALIGN_CENTER);
			noColumn.setBorder(Rectangle.BOX);
			noColumn.setBackgroundColor(DETAIL_TITLE_COLOR);
			summaryDetail.addCell(noColumn);
			
			// Purpose
			PdfPCell purposeColumn = new PdfPCell(new Phrase("Purpose", font_g8_c));
			purposeColumn.setBorderWidth(0.5f);
			purposeColumn.setHorizontalAlignment(Element.ALIGN_CENTER);
			purposeColumn.setBorder(Rectangle.BOX);
			purposeColumn.setBackgroundColor(DETAIL_TITLE_COLOR);
			summaryDetail.addCell(purposeColumn);
			
			// Total Amount
			PdfPCell totalAmountColumn = new PdfPCell(new Phrase("Total Amount", font_g8_c));
			totalAmountColumn.setBorderWidth(0.5f);
			totalAmountColumn.setHorizontalAlignment(Element.ALIGN_CENTER);
			totalAmountColumn.setBorder(Rectangle.BOX);
			totalAmountColumn.setBackgroundColor(DETAIL_TITLE_COLOR);
			summaryDetail.addCell(totalAmountColumn);
			
			// Summary
			summaryDetail = createSummaryDetail(summaryDetail, claimInfoBean);
			
			// 然后明细项目
			PdfPTable detailTable = new PdfPTable(1);
			float detailWidth[] = {165f};
			detailTable.setSpacingBefore(10f);
			detailTable.setWidths(detailWidth);
			detailTable.setWidthPercentage(100);
			
			PdfPCell detailColumn = new PdfPCell(new Phrase("Details", font_g12_c));
			detailColumn.setBorderWidth(0.5f);
			detailColumn.setPaddingTop(10f);
			detailColumn.setPaddingBottom(10f);
			detailColumn.setHorizontalAlignment(Element.ALIGN_LEFT);
			detailColumn.setBorder(Rectangle.NO_BORDER);
			detailTable.addCell(detailColumn);
			
			// Details明细项目标题
			PdfPTable detail = new PdfPTable(6);
			float detailTitleWidth[] = { 50f, 300f, 200f, 350f, 200f, 400f};
			detail.setSpacingBefore(10f);
			detail.setWidths(detailTitleWidth);
			detail.setWidthPercentage(100);
			
			// No.
			PdfPCell noDColumn = new PdfPCell(new Phrase("NO.", font_g8_c));
			noDColumn.setBorderWidth(0.5f);
			noDColumn.setHorizontalAlignment(Element.ALIGN_CENTER);
			noDColumn.setBorder(Rectangle.BOX);
			noDColumn.setBackgroundColor(DETAIL_TITLE_COLOR);
			detail.addCell(noDColumn);
			
			// Day
			PdfPCell dayDColumn = new PdfPCell(new Phrase("Day", font_g8_c));
			dayDColumn.setBorderWidth(0.5f);
			dayDColumn.setHorizontalAlignment(Element.ALIGN_CENTER);
			dayDColumn.setBorder(Rectangle.BOX);
			dayDColumn.setBackgroundColor(DETAIL_TITLE_COLOR);
			detail.addCell(dayDColumn);
			
			// Location
			PdfPCell locationDColumn = new PdfPCell(new Phrase("Location", font_g8_c));
			locationDColumn.setBorderWidth(0.5f);
			locationDColumn.setHorizontalAlignment(Element.ALIGN_CENTER);
			locationDColumn.setBorder(Rectangle.BOX);
			locationDColumn.setBackgroundColor(DETAIL_TITLE_COLOR);
			detail.addCell(locationDColumn);
			
			// Expense Type
			PdfPCell expenseTypeDColumn = new PdfPCell(new Phrase("Expense Type", font_g8_c));
			expenseTypeDColumn.setBorderWidth(0.5f);
			expenseTypeDColumn.setHorizontalAlignment(Element.ALIGN_CENTER);
			expenseTypeDColumn.setBorder(Rectangle.BOX);
			expenseTypeDColumn.setBackgroundColor(DETAIL_TITLE_COLOR);
			detail.addCell(expenseTypeDColumn);
			
			// Amount
			PdfPCell amountDColumn = new PdfPCell(new Phrase("Amount", font_g8_c));
			amountDColumn.setBorderWidth(0.5f);
			amountDColumn.setHorizontalAlignment(Element.ALIGN_CENTER);
			amountDColumn.setBorder(Rectangle.BOX);
			amountDColumn.setBackgroundColor(DETAIL_TITLE_COLOR);
			detail.addCell(amountDColumn);
			
			// Comments
			PdfPCell commentDColumn = new PdfPCell(new Phrase("Comments", font_g8_c));
			commentDColumn.setBorderWidth(0.5f);
			commentDColumn.setHorizontalAlignment(Element.ALIGN_CENTER);
			commentDColumn.setBorder(Rectangle.BOX);
			commentDColumn.setBackgroundColor(DETAIL_TITLE_COLOR);
			detail.addCell(commentDColumn);
			
			
			detail = createDetail(detail, claimInfoBean);
			
			// 总金额
			PdfPTable totalAmount = createTotalAmount();
			
			// review 的人和 审批的人
			//TODO
			
			// 申请人和申请时间
			PdfPTable claimEmAndDate = createClaimEmAndDate(employeeName, appDate);
			
			//add tables to document
			PdfPTable borderTable3 = new PdfPTable(1);
			borderTable3.setWidthPercentage(100);
			borderTable3.setSplitLate(false);
			borderTable3.setSplitRows(true);
			borderTable3.getDefaultCell().setBorder(Rectangle.NO_BORDER);
			borderTable3.getDefaultCell().setPadding(0);
			borderTable3.addCell(headTable); //页眉数据
			borderTable3.addCell(emptyRow1); //空行
			borderTable3.addCell(dataTable); //头部数据
			borderTable3.addCell(summaryTable);// 目的LABEL
			borderTable3.addCell(summaryDetail);// 目的数据
			borderTable3.addCell(new Paragraph("\n\n"));
			borderTable3.addCell(detailTable);// 明细LABEL
			borderTable3.addCell(detail);// 明细数据
			borderTable3.addCell(totalAmount);// 总金额
			// 这里添加几行空行
			borderTable3.addCell(this.createEmptyRow());
			borderTable3.addCell(this.createEmptyRow());
			borderTable3.addCell(this.createEmptyRow());
			borderTable3.addCell(this.createEmptyRow());
			
			PdfPTable borderTable = new PdfPTable(1);
			borderTable.setWidthPercentage(100);
			borderTable.setSplitLate(false);
			borderTable.setSplitRows(true);
			

			PdfPCell allCell = new PdfPCell(borderTable3);
			allCell.setBorderWidthBottom(0f);
			allCell.setBorderWidthLeft(0.5f);
			allCell.setBorderWidthRight(0.5f);
			allCell.setBorderWidthTop(0.5f);
			allCell.setPadding(5f);
			
			// 这里将所有审批过的人的信息显示在画面上面
			List<AuthorityByListBean> hisList = expensesManagementService.getAuthorityByHisList(claimInfoBean.getExpenseAppNo());
			for (AuthorityByListBean hisBean : hisList) {
				hisBean.setAuthorityDate(hisBean.getAuthorityDate().substring(0, 10).replaceAll("-", "/"));
				String hisStatus = hisBean.getStatusView();
				if (hisStatus.equals(CodeCommon.CLAIM_REJECT)) {
					hisBean.setStatusView(baseService.getSelect(CodeCommon.COM003, CodeCommon.CLAIM_REJECT));
				} else {
					hisBean.setStatusView(baseService.getSelect(CodeCommon.COM003, CodeCommon.CLAIM_APPROVED));
				}
			}
			
			float miniHei = 0;
			if (hisList != null && hisList.size() > 0) {
				miniHei = 735 - (hisList.size() + 1) * 20;
			} else {
				miniHei = 735;
			}
			
			allCell.setMinimumHeight(miniHei);
			
			borderTable.addCell(allCell);
			
			
			if (hisList != null && hisList.size() > 0) {
				// review 的人和 审批的人
				PdfPTable claimPerson = createClaimPerson(hisList);
				PdfPCell allCell3 = new PdfPCell(claimPerson);
				allCell3.setBorderWidthBottom(0f);
				allCell3.setBorderWidthLeft(0.5f);
				allCell3.setBorderWidthRight(0.5f);
				allCell3.setBorderWidthTop(0f);
				allCell3.setPadding(5f);
				borderTable.addCell(allCell3);// review 的人和 审批的人
			}

			
			PdfPCell allCell2 = new PdfPCell(claimEmAndDate);
			allCell2.setBorderWidthBottom(0.5f);
			allCell2.setBorderWidthLeft(0.5f);
			allCell2.setBorderWidthRight(0.5f);
			allCell2.setBorderWidthTop(0f);
			allCell2.setPadding(5f);
			borderTable.addCell(allCell2);// 申请人和申请日期
			
			document.add(borderTable);
			// close document
			document.close();
			
		}
		catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * 创建PURPOSE
	 * @return
	 * @throws Exception 
	 */
	private PdfPTable createSummaryDetail(PdfPTable summaryDetail, ClaimInfoBean claimInfoBean) throws Exception{
		
		List<SeEcEpDeBean> purposeList = claimInfoBean.getBeanList();
		
		if (purposeList == null || purposeList.size() == 0) return summaryDetail;
		
		int count = 1;
		for (SeEcEpDeBean purpose : purposeList) {
			// No.
			PdfPCell noColumn = new PdfPCell(new Phrase(String.valueOf(count), font_g8_c));
			noColumn.setBorderWidth(0.5f);
			noColumn.setHorizontalAlignment(Element.ALIGN_CENTER);
			noColumn.setBorder(Rectangle.BOX);
			summaryDetail.addCell(noColumn);
			
			// Purpose
			PdfPCell purposeColumn = new PdfPCell(new Phrase(purpose.getPurpose(), font_g8_c));
			purposeColumn.setBorderWidth(0.5f);
			purposeColumn.setHorizontalAlignment(Element.ALIGN_LEFT);
			purposeColumn.setBorder(Rectangle.BOX);
			summaryDetail.addCell(purposeColumn);
			
			// Total Amount
			PdfPCell totalAmountColumn = new PdfPCell(new Phrase(TypeConvertCommon.convertToCurrencyFmt(purpose.getAmount()), font_g8_c));
			totalAmountColumn.setBorderWidth(0.5f);
			totalAmountColumn.setHorizontalAlignment(Element.ALIGN_RIGHT);
			totalAmountColumn.setBorder(Rectangle.BOX);
			summaryDetail.addCell(totalAmountColumn);
			
			count++;
		}
		
		return summaryDetail;
	}
	
	/**
	 * 创建明细数据
	 * @param summaryDetail
	 * @return
	 * @throws Exception
	 */
	private PdfPTable createDetail(PdfPTable detail, ClaimInfoBean claimInfoBean) throws Exception{
		
		if (claimInfoBean == null || claimInfoBean.getBeanList() == null || claimInfoBean.getBeanList().size() == 0) return detail;
		// 出差发生明细
		List<SeEcEpDeListBean> detailList = new ArrayList<SeEcEpDeListBean>();

		for (SeEcEpDeBean purposeBean : claimInfoBean.getBeanList()) {

			for (SeEcEpDeListBean detailBean : purposeBean.getBeanList()) {
				SeEcEpDeListBean deDetail = new SeEcEpDeListBean();
				BeanUtils.copyProperties(deDetail, detailBean);
				deDetail.setExpenseType(systemManagementService.getExpensesItemInfo(detailBean.getExpenseType()).getExpenseName());
				detailList.add(deDetail);
			}
		}
		
		totalAmountDec = BigDecimal.ZERO;

		int count = 1;
		for (SeEcEpDeListBean detailBean : detailList) {
			// No.
			PdfPCell noDColumn = new PdfPCell(new Phrase(String.valueOf(count), font_g8_c));
			noDColumn.setBorderWidth(0.5f);
			noDColumn.setHorizontalAlignment(Element.ALIGN_CENTER);
			noDColumn.setBorder(Rectangle.BOX);
			detail.addCell(noDColumn);
			
			// Day
			String dayView = "";
			if ("1".equals(detailBean.getTimeMethod())) {
				dayView = detailBean.getDayTo();
			} else if ("2".equals(detailBean.getTimeMethod())) {
				dayView = detailBean.getDayFrom() +"~"+ detailBean.getDayTo();
			} else if ("3".equals(detailBean.getTimeMethod())) {
				dayView = detailBean.getDayTo().substring(0, 7);
			}
			PdfPCell dayDColumn = new PdfPCell(new Phrase(dayView, font_g8_c));
			dayDColumn.setBorderWidth(0.5f);
			dayDColumn.setHorizontalAlignment(Element.ALIGN_LEFT);
			dayDColumn.setBorder(Rectangle.BOX);
			detail.addCell(dayDColumn);
			
			// Location
			PdfPCell locationDColumn = new PdfPCell(new Phrase(detailBean.getLocation(), font_g8_c));
			locationDColumn.setBorderWidth(0.5f);
			locationDColumn.setHorizontalAlignment(Element.ALIGN_LEFT);
			locationDColumn.setBorder(Rectangle.BOX);
			detail.addCell(locationDColumn);
			
			// Expense Type
			PdfPCell expenseTypeDColumn = new PdfPCell(new Phrase(detailBean.getExpenseType(), font_g8_c));
			expenseTypeDColumn.setBorderWidth(0.5f);
			expenseTypeDColumn.setHorizontalAlignment(Element.ALIGN_CENTER);
			expenseTypeDColumn.setBorder(Rectangle.BOX);
			detail.addCell(expenseTypeDColumn);
			
			// Amount
			PdfPCell amountDColumn = new PdfPCell(new Phrase(detailBean.getExpenseAmount(), font_g8_c));
			amountDColumn.setBorderWidth(0.5f);
			amountDColumn.setHorizontalAlignment(Element.ALIGN_RIGHT);
			amountDColumn.setBorder(Rectangle.BOX);
			detail.addCell(amountDColumn);
			
			totalAmountDec = totalAmountDec.add(new BigDecimal(detailBean.getExpenseAmount().replaceAll(",", "")));
			
			// Comments
			PdfPCell commentDColumn = new PdfPCell(new Phrase(detailBean.getComments(), font_g8_c));
			commentDColumn.setBorderWidth(0.5f);
			commentDColumn.setHorizontalAlignment(Element.ALIGN_LEFT);
			commentDColumn.setBorder(Rectangle.BOX);
			detail.addCell(commentDColumn);
			
			count++;
		}
		
		return detail;
	}
	
	/**
	 * 总金额的显示
	 * @return
	 * @throws Exception 
	 */
	private PdfPTable createTotalAmount() throws Exception {
		
		PdfPTable totalAmountTable = new PdfPTable(3);
		float totalAmountWidth[] = {60, 10, 30};
		totalAmountTable.setWidths(totalAmountWidth);
		totalAmountTable.setWidthPercentage(100);
		
		// TOTAL
		PdfPCell totalColumn = new PdfPCell(new Phrase("Total:", font_g12_c));
		totalColumn.setPaddingTop(10f);
		totalColumn.setHorizontalAlignment(Element.ALIGN_RIGHT);
		totalColumn.setBorder(Rectangle.NO_BORDER);
		totalAmountTable.addCell(totalColumn);
		
		// AMOUNT
		PdfPCell amount = new PdfPCell(new Phrase(TypeConvertCommon.convertToCurrencyFmt(totalAmountDec), font_g12_c));
		amount.setPaddingTop(10f);
		amount.setHorizontalAlignment(Element.ALIGN_RIGHT);
		amount.setBorder(Rectangle.NO_BORDER);
		totalAmountTable.addCell(amount);
		
		// EMPTY
		PdfPCell empty = new PdfPCell(new Phrase("", font_g12_c));
		empty.setPaddingTop(10f);
		empty.setHorizontalAlignment(Element.ALIGN_RIGHT);
		empty.setBorder(Rectangle.NO_BORDER);
		totalAmountTable.addCell(empty);
		
		return totalAmountTable;
	}
	
	
	/**
	 * 申请人和申请时间
	 * @return
	 * @throws Exception 
	 */
	private PdfPTable createClaimEmAndDate(String employeeName, String claimDate) throws Exception {
		
		PdfPTable claimTable = new PdfPTable(4);
		float claimWidth[] = {65, 15, 7, 13};
		claimTable.setWidths(claimWidth);
		claimTable.setWidthPercentage(100);
		
		// Employee
		PdfPCell employeeColumn = new PdfPCell(new Phrase("Employee:", font_g12_c));
		employeeColumn.setPaddingBottom(1f);
		employeeColumn.setBorderWidth(0f);
		employeeColumn.setHorizontalAlignment(Element.ALIGN_RIGHT);
		employeeColumn.setBorder(Rectangle.BOX);
		claimTable.addCell(employeeColumn);
		
		// Employee
		PdfPCell employeeContent = new PdfPCell(new Phrase(employeeName, font_g12_c));
		employeeContent.setPaddingBottom(1f);
		employeeContent.setBorderWidth(0f);
		employeeContent.setHorizontalAlignment(Element.ALIGN_LEFT);
		employeeContent.setBorder(Rectangle.BOX);
		claimTable.addCell(employeeContent);
		
		// Date
		PdfPCell dateColumn = new PdfPCell(new Phrase("Date:", font_g12_c));
		dateColumn.setPaddingBottom(1f);
		dateColumn.setBorderWidth(0f);
		dateColumn.setHorizontalAlignment(Element.ALIGN_RIGHT);
		dateColumn.setBorder(Rectangle.BOX);
		claimTable.addCell(dateColumn);
		
		// Date
		PdfPCell dateContent = new PdfPCell(new Phrase(claimDate, font_g12_c));
		dateContent.setPaddingBottom(1f);
		dateContent.setBorderWidth(0f);
		dateContent.setHorizontalAlignment(Element.ALIGN_LEFT);
		dateContent.setBorder(Rectangle.BOX);
		claimTable.addCell(dateContent);
		
		return claimTable;
	}
	
	/**
	 * 创建申请人历史记录的信息
	 * @param hisList
	 * @return
	 * @throws Exception
	 */
	private PdfPTable createClaimPerson(List<AuthorityByListBean> hisList) throws Exception{
		PdfPTable claimPerson = new PdfPTable(5);
		float claimWidth[] = {15, 10, 10, 35, 35};
		claimPerson.setWidths(claimWidth);
		claimPerson.setWidthPercentage(100);
		
		// Authorized by
		PdfPCell employeeColumn = new PdfPCell(new Phrase("Authorized by", font_g8_c));
		employeeColumn.setBorderWidth(0.5f);
		employeeColumn.setHorizontalAlignment(Element.ALIGN_CENTER);
		employeeColumn.setBorder(Rectangle.BOX);
		employeeColumn.setBackgroundColor(DETAIL_TITLE_COLOR);
		claimPerson.addCell(employeeColumn);
		
		// Date
		PdfPCell dateContent = new PdfPCell(new Phrase("Date", font_g8_c));
		dateContent.setBorderWidth(0.5f);
		dateContent.setHorizontalAlignment(Element.ALIGN_CENTER);
		dateContent.setBorder(Rectangle.BOX);
		dateContent.setBackgroundColor(DETAIL_TITLE_COLOR);
		claimPerson.addCell(dateContent);
		
		// Status
		PdfPCell statusColumn = new PdfPCell(new Phrase("Status", font_g8_c));
		statusColumn.setBorderWidth(0.5f);
		statusColumn.setHorizontalAlignment(Element.ALIGN_CENTER);
		statusColumn.setBorder(Rectangle.BOX);
		statusColumn.setBackgroundColor(DETAIL_TITLE_COLOR);
		claimPerson.addCell(statusColumn);
		
		// Comments
		PdfPCell commentsContent = new PdfPCell(new Phrase("Comments", font_g8_c));
		commentsContent.setBorderWidth(0.5f);
		commentsContent.setHorizontalAlignment(Element.ALIGN_CENTER);
		commentsContent.setBorder(Rectangle.BOX);
		commentsContent.setBackgroundColor(DETAIL_TITLE_COLOR);
		claimPerson.addCell(commentsContent);
		
		// Empty
		PdfPCell emptyContent = new PdfPCell(new Phrase("", font_g8_c));
		emptyContent.setBorderWidth(0f);
		emptyContent.setHorizontalAlignment(Element.ALIGN_CENTER);
		emptyContent.setBorder(Rectangle.LEFT);
		claimPerson.addCell(emptyContent);
		for (AuthorityByListBean detailBean : hisList) {
			
			// Authorized by
			PdfPCell employeeDColumn = new PdfPCell(new Phrase(detailBean.getAuthorityBy(), font_g8_c));
			employeeDColumn.setBorderWidth(0.5f);
			employeeDColumn.setHorizontalAlignment(Element.ALIGN_CENTER);
			employeeDColumn.setBorder(Rectangle.BOX);
			claimPerson.addCell(employeeDColumn);
			
			// Date
			PdfPCell dateDContent = new PdfPCell(new Phrase(detailBean.getAuthorityDate(), font_g8_c));
			dateDContent.setPaddingBottom(1f);
			dateDContent.setBorderWidth(0.5f);
			dateDContent.setHorizontalAlignment(Element.ALIGN_CENTER);
			dateDContent.setBorder(Rectangle.BOX);
			claimPerson.addCell(dateDContent);
			
			// Status
			PdfPCell statusDColumn = new PdfPCell(new Phrase(detailBean.getStatusView(), font_g8_c));
			statusDColumn.setBorderWidth(0.5f);
			statusDColumn.setHorizontalAlignment(Element.ALIGN_CENTER);
			statusDColumn.setBorder(Rectangle.BOX);
			claimPerson.addCell(statusDColumn);
			
			// Comments
			PdfPCell commentsDContent = new PdfPCell(new Phrase(detailBean.getComment(), font_g8_c));
			commentsDContent.setBorderWidth(0.5f);
			commentsDContent.setHorizontalAlignment(Element.ALIGN_LEFT);
			commentsDContent.setBorder(Rectangle.BOX);
			claimPerson.addCell(commentsDContent);
			
			// Empty
			PdfPCell emptyDContent = new PdfPCell(new Phrase("", font_g8_c));
			emptyDContent.setBorderWidth(0f);
			emptyDContent.setHorizontalAlignment(Element.ALIGN_CENTER);
			emptyDContent.setBorder(Rectangle.LEFT);
			claimPerson.addCell(emptyDContent);	
		}
		return claimPerson;
	}
	
	/**
	 * 这里创建一个空行
	 * @return
	 * @throws Exception 
	 */
	private PdfPTable createEmptyRow() throws Exception{
		// 首先summary
		PdfPTable emptyTable = new PdfPTable(1);
		float summaryWidth[] = { 165f};
		emptyTable.setSpacingBefore(0f);
		emptyTable.setWidths(summaryWidth);
		emptyTable.setWidthPercentage(100);
		
		PdfPCell emptyColumn = new PdfPCell(new Phrase("", font_g12_c));
		emptyColumn.setBorderWidth(0f);
		emptyColumn.setPaddingTop(5f);
		emptyColumn.setPaddingBottom(5f);
		emptyColumn.setHorizontalAlignment(Element.ALIGN_LEFT);
		emptyColumn.setBorder(Rectangle.NO_BORDER);
		emptyTable.addCell(emptyColumn);
		
		return emptyTable;
	}
	
}
