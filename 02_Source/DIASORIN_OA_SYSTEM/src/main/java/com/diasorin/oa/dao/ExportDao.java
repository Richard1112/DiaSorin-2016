package com.diasorin.oa.dao;

import java.util.List;

import com.diasorin.oa.common.QueryResult;
import com.diasorin.oa.dto.SeEcEpDeListBean;
import com.diasorin.oa.dto.SeRcReInBean;
import com.diasorin.oa.dto.SeRcReInExBean;
import com.diasorin.oa.dto.SeRcReInExpenseListBean;
import com.diasorin.oa.dto.SeRcReInHeadListBean;
import com.diasorin.oa.dto.SeRcReLsBean;
import com.diasorin.oa.dto.SeRcReLsExpenseListBean;
import com.diasorin.oa.dto.SeRcReLsHeadListBean;
import com.diasorin.oa.dto.SeRcReMoBean;
import com.diasorin.oa.dto.SeRcReMoSapBean;

public interface ExportDao {
	
	public List<SeRcReMoBean> getDateForSap(SeRcReMoSapBean seRcReMoSapBean) throws Exception;
	
	public QueryResult<SeRcReMoBean> searchDateForSap(SeRcReMoSapBean seRcReMoSapBean, int start, int result) throws Exception;
	
	public String sapTotalAmount(SeRcReMoSapBean seRcReMoSapBean) throws Exception;
	
	// 获取所有的明细NO
	public String getAllDetailNo(SeRcReMoSapBean seRcReMoSapBean) throws Exception;
	
	public List<SeRcReLsHeadListBean> getPurposeList(int start, int count, SeRcReLsBean seRcReLsBean) throws Exception;
	public List<SeRcReInHeadListBean> getPurposeList(int start, int count, SeRcReInBean seRcReInBean) throws Exception;
	
	public List<SeRcReLsExpenseListBean> getDetailList(int start, int count, SeRcReLsBean seRcReLsBean) throws Exception;
	public List<SeRcReInExpenseListBean> getDetailList(int start, int count, SeRcReInBean seRcReInBean) throws Exception;
	
	public List<String> getDetailTitleList(SeRcReLsBean seRcReLsBean) throws Exception;
	public List<String> getDetailTitleList(SeRcReInBean seRcReInBean) throws Exception;
	
	public List<SeEcEpDeListBean> getDetailData(String employeeNo, String travelLocation, SeRcReLsBean seRcReLsBean) throws Exception;
	public List<SeEcEpDeListBean> getDetailData(String employeeNo, String travelLocation, SeRcReInBean seRcReInBean) throws Exception;
	
	public List<SeRcReInExBean> getExportData(SeRcReInBean seRcReInBean) throws Exception;
	public List<SeRcReInExpenseListBean> getExportDataGroupByEm(SeRcReInBean seRcReInBean) throws Exception;
}
