package com.diasorin.oa.dto;

import java.util.ArrayList;
import java.util.List;

public class SeAmRoLsBean extends BaseBean{

	private List<SeAmRoLsListBean> beanList = new ArrayList<SeAmRoLsListBean>();

	/**
	 * @return the beanList
	 */
	public List<SeAmRoLsListBean> getBeanList() {
		return beanList;
	}

	/**
	 * @param beanList the beanList to set
	 */
	public void setBeanList(List<SeAmRoLsListBean> beanList) {
		this.beanList = beanList;
	}
	
	
}
