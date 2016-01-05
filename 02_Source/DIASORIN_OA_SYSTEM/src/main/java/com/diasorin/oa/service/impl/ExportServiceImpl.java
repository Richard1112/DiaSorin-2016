package com.diasorin.oa.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.diasorin.oa.common.ErrCommon;
import com.diasorin.oa.common.QueryResult;
import com.diasorin.oa.dao.ExportDao;
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
import com.diasorin.oa.service.ExportService;

@Service("exportService")
public class ExportServiceImpl extends BaseServiceImpl implements ExportService {

	@Resource 
	ExportDao exportDaoImpl;
	
	@Override
	public List<SeRcReMoBean> getDateForSap(SeRcReMoSapBean seRcReMoSapBean) throws Exception {
		try {			
			return exportDaoImpl.getDateForSap(seRcReMoSapBean);
		} catch(Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return null;
		}
	}

	@Override
	public List<SeRcReLsHeadListBean> getPurposeList(int start, int count,
			SeRcReLsBean seRcReLsBean) throws Exception {
		try {			
			return exportDaoImpl.getPurposeList(start, count, seRcReLsBean);
		} catch(Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return null;
		}
	}
	
	@Override
	public List<SeRcReInHeadListBean> getPurposeList(int start, int count,
			SeRcReInBean seRcReInBean) throws Exception {
		try {			
			return exportDaoImpl.getPurposeList(start, count, seRcReInBean);
		} catch(Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return null;
		}
	}

	@Override
	public List<SeRcReLsExpenseListBean> getDetailList(int start, int count,
			SeRcReLsBean seRcReLsBean) throws Exception {
		try {			
			return exportDaoImpl.getDetailList(start, count, seRcReLsBean);
		} catch(Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return null;
		}
	}
	
	@Override
	public List<SeRcReInExpenseListBean> getDetailList(int start, int count,
			SeRcReInBean seRcReInBean) throws Exception {
		try {			
			return exportDaoImpl.getDetailList(start, count, seRcReInBean);
		} catch(Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return null;
		}
	}

	@Override
	public List<String> getDetailTitleList(SeRcReLsBean seRcReLsBean)
			throws Exception {
		try {			
			return exportDaoImpl.getDetailTitleList(seRcReLsBean);
		} catch(Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return null;
		}
	}
	
	@Override
	public List<String> getDetailTitleList(SeRcReInBean seRcReInBean)
			throws Exception {
		try {			
			return exportDaoImpl.getDetailTitleList(seRcReInBean);
		} catch(Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return null;
		}
	}

	@Override
	public List<SeEcEpDeListBean> getDetailData(String employeeNo,
			String travelLocation, SeRcReLsBean seRcReLsBean) throws Exception {
		try {			
			return exportDaoImpl.getDetailData(employeeNo, travelLocation, seRcReLsBean);
		} catch(Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return null;
		}
	}
	
	@Override
	public List<SeEcEpDeListBean> getDetailData(String employeeNo,
			String travelLocation, SeRcReInBean seRcReInBean) throws Exception {
		try {			
			return exportDaoImpl.getDetailData(employeeNo, travelLocation, seRcReInBean);
		} catch(Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			ErrCommon.errOut(e);
			return null;
		}
	}

	@Override
	public QueryResult<SeRcReMoBean> searchDateForSap(SeRcReMoSapBean seRcReMoSapBean, int start, int result)
			throws Exception {
		return exportDaoImpl.searchDateForSap(seRcReMoSapBean, start, result);
	}

	@Override
	public String sapTotalAmount(SeRcReMoSapBean seRcReMoSapBean)
			throws Exception {
		return exportDaoImpl.sapTotalAmount(seRcReMoSapBean);
	}

	@Override
	public String getAllDetailNo(SeRcReMoSapBean seRcReMoSapBean)
			throws Exception {
		return exportDaoImpl.getAllDetailNo(seRcReMoSapBean);
	}
	
	@Override
	public List<SeRcReInExBean> getExportData(SeRcReInBean seRcReInBean) throws Exception{
		return exportDaoImpl.getExportData(seRcReInBean);
	}
	
	@Override
	public List<SeRcReInExpenseListBean> getExportDataGroupByEm(SeRcReInBean seRcReInBean) throws Exception{
		return exportDaoImpl.getExportDataGroupByEm(seRcReInBean);
	}
}
