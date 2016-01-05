package com.diasorin.oa.common;

import java.util.List;

public class QueryResult<T> {

	
	private long totalrecord;

	private List<T> resultlist;
	
	public void setResultlist(List<T> resultlist)
	{
		this.resultlist = resultlist;
	}

	public List<T> getResultlist() {
		return resultlist;
	}

	public long getTotalrecord() {
		return totalrecord;
	}

	public void setTotalrecord(long totalrecord) {
		this.totalrecord = totalrecord;
	}
	
}
