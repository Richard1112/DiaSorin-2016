package com.diasorin.oa.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * 参数维护画面用bean
 * @author liuan
 *
 */
public class SeSmPaMaBean {

	// 出差地类型
	List<SeSmPaMaTrBean> trBeanList = new ArrayList<SeSmPaMaTrBean>();
	// 人员级别
	List<SeSmPaMaElBean> elBeanList = new ArrayList<SeSmPaMaElBean>();
	// 报销项目
	List<SeSmPaMaEiBean> eiBeanList = new ArrayList<SeSmPaMaEiBean>();
	// Cost Center
	List<SeSmPaMaCcBean> ccBeanList = new ArrayList<SeSmPaMaCcBean>();
	/**
	 * @return the trBeanList
	 */
	public List<SeSmPaMaTrBean> getTrBeanList() {
		return trBeanList;
	}
	/**
	 * @param trBeanList the trBeanList to set
	 */
	public void setTrBeanList(List<SeSmPaMaTrBean> trBeanList) {
		this.trBeanList = trBeanList;
	}
	/**
	 * @return the elBeanList
	 */
	public List<SeSmPaMaElBean> getElBeanList() {
		return elBeanList;
	}
	/**
	 * @param elBeanList the elBeanList to set
	 */
	public void setElBeanList(List<SeSmPaMaElBean> elBeanList) {
		this.elBeanList = elBeanList;
	}
	/**
	 * @return the eiBeanList
	 */
	public List<SeSmPaMaEiBean> getEiBeanList() {
		return eiBeanList;
	}
	/**
	 * @param eiBeanList the eiBeanList to set
	 */
	public void setEiBeanList(List<SeSmPaMaEiBean> eiBeanList) {
		this.eiBeanList = eiBeanList;
	}
	/**
	 * @return the ccBeanList
	 */
	public List<SeSmPaMaCcBean> getCcBeanList() {
		return ccBeanList;
	}
	/**
	 * @param ccBeanList the ccBeanList to set
	 */
	public void setCcBeanList(List<SeSmPaMaCcBean> ccBeanList) {
		this.ccBeanList = ccBeanList;
	}	
}
