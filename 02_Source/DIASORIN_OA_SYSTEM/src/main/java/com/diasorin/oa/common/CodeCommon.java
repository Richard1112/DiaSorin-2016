package com.diasorin.oa.common;

public final class CodeCommon {
	
	// 系统的错误页面
	public static final String COMMON_ERROR_PAGE = "404";
	
	// SESSION 中的用户ID
	public static final String SESSION_USERID = "userId";
	
	// SESSION 中的用户角色
	public static final String SESSION_ROLEID = "roleID";
	
	// 登录状态 -- 已经登录 
	public static final String HAS_LOGINED_STATUS = "1";
	
	// 登录状态 -- 未登录 
	public static final String NOT_LOGIN_STATUS = "0";
	
	// 未删除-标志
	public static final String NOT_DELETE_FLG = "0";
	
	// 删除-标志
	public static final String IS_DELETE_FLG = "1";
	
	// 性别类型
	public static final String COM001 = "COM001";
	
	// 出差地类型
	public static final String COM004 = "COM004";
	
	// 申请状态
	public static final String COM003 = "COM003";
	
	// SESSION 中的申请信息 。初期化时需要清空。
	public static final String SESSION_CLAIM_INFO = "claimInfo";
	
	// 计费方式 天
	public static final String TIME_METHOD_DAY = "1";
	
	// 计费方式 时间段
	public static final String TIME_METHOD_SEC = "2";
	
	// 计费方式 月
	public static final String TIME_METHOD_MON = "3";
	
	// 申请记录一时保存
	public static final String CLAIM_SAVE = "0";
	// 申请去审批
	public static final String CLAIM_APPLICATION = "1";
	// 正在审批中
	public static final String CLAIM_PENDING = "2";
	// 驳回
	public static final String CLAIM_REJECT = "3";
	// 审批通过
	public static final String CLAIM_APPROVED= "4";
	// 完成
	public static final String CLAIM_FINISH = "5";
	
	// 申请NODE_ID
	public static final String WFND01 = "WFND01";
	public static final String WFND02 = "WFND02";
	public static final String WFND03 = "WFND03";
	public static final String WFND04 = "WFND04";
	public static final String WFND05 = "WFND05";
	
	public static final String WFND06 = "WFND06"; //对于财务的经理审批
	public static final String WFND07 = "WFND07"; //对于地区经理的审批
	
	// 流程CATEGORY
	public static final String WFCG01 = "WFCG01";
	
	public static final String WFCG02 = "WFCG02"; //财务流程
	
	// 审批条件有无
	public static final String APPROVE_NO_CODITON = "0";
	
	// 报销申请项目的总项目
	public static final String EXPENSE_SUPERFARTHER = "S000";
	// 住宿费用
	public static final String ACCOMODATION_IN_HOTEL= "S005";
	// 每公里单价
	public static final String PRIVATE_CAR_FOR_BUSINESS= "S025";
	
	// 操作FLG
	public static final String OPERATEFLG_SAVE = "1";
	public static final String OPERATEFLG_UPDATE = "2";
	public static final String OPERATEFLG_DELETE = "3";
	
	// todo List 最多显示多少条记录
	public static final String TOLISTCOUNT = "toListCount";
	
	// downloadTempPath
	public static final String DOWNLOAD_TEMP_PATH = "downloadTempPath";
	
	// download for sap temp name key
	public static final String DOWMLOAD_FOR_SAP = "downloadForSapName";
	
	public static final String DOWMLOAD_FOR_FINANCE = "downloadForFinanceName";
	
	//EXCEL POSTINGKEY
	public static final String POSTING_KEY_DETAIL = "40";
	public static final String POSTING_KEY_TOTAL = "50";
	public static final String ACCOUNT_TOTAL = "212209";
	public static final String TAX_KEY = "J0";
	
	// 报表目的地
	public static final String REPORT_TRAVEL_LOCATION = "Travel Location";
	// 总金额
	public static final String REPORT_TOTAL_AMOUNT = "Total Amount";
	
	// 权限FLG
	public static final String HAS_AUTHORITY = "1";
	
	// 权限session
	public static final String SESSION_AUTHORITY_LIST = "authority";
	
	// 画面MODE
	public static final String FRAME_MODE_CLAIM = "0";
	public static final String FRAME_MODE_CANAPPROVE = "1";
	public static final String FRAME_MODE_CANNOTAPPROVE = "2";
	
	// 当前记录可不可以审批
	public static final String CAN_BEAPPROVE = "1";
	public static final String CAN_NOT_BEAPPROVE = "0";
	
	// 明细错误FLG
	public static final String DETAIL_ERROR_FLG = "1";
	public static final String DETAIL_NOMARL_FLG = "0";
	
	//判断此人是不是财务，因为只有财务可以FINISH和UNDOFINISH
	public static final String FINANCIAL_PERSON = "R005";
	
	// 当没有选择GROUP BY
	public static final String[] NO_SUBTOTAL = {"Submit Date", "Approval Date", 
		"Finished Date", "Cost Center", "Employee", "Application No.", 
		"Purpose No.", "Travel Reason", "Expense Type", "Day", 
		"Location", "Amount", "Comments"};
	public static final short[] NO_SUBTOTAL_WIDTH = {3000, 3000, 
		3000, 3000, 3000, 3500, 
		4000, 5000, 4000, 5000, 
		4000, 3000, 6000};
	
	public static final String[] EMPLOYEE_SUBTOTAL = {" ", "Employee", "Sum of Amount"};
	public static final short[] EMPLOYEE_SUBTOTAL_WIDTH = {3000, 4000, 4000};
	
	public static final String EXCEL_COSTCENTER = "Cost Center";
	public static final String EXCEL_EMPLOYEE = "Employee";
	public static final String EXCEL_EXPENSE_TYPE = "Expense Type";
	public static final String EXCEL_LOCATION = "Location";
	
}