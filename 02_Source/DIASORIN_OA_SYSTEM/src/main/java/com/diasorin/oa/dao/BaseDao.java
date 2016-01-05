package com.diasorin.oa.dao;

import java.util.LinkedHashMap;
import java.util.List;

import com.diasorin.oa.common.QueryResult;
import com.diasorin.oa.model.SysCode;


public interface BaseDao {
	/** 
	 * 获取记录总数 
	 * @param entityClass 实体类 
	 * @return 
	 */
	public <T> long getCount(Class<T> entityClass) throws Exception;
	public <T> long getCount(Class<T> entityClass, String wherejpql, Object[] queryParams) throws Exception;
	/** 
	 * 清除一级缓存的数据 
	 */
	public void clear();
	/** 
	 * 关闭数据连接 
	 */
	public void close();
	/** 
	 * 保存实体 
	 * @param entity 实体id 
	 */
	public void save(Object entity) throws Exception;
	/**
	 * 更新实体 
	 * @param entity 实体id 
	 */  
	public void update(Object entity) throws Exception;
	/**
	 * 删除实体 
	 * @param entityClass 实体类 
	 * @param entityid 实体id 
	 */
	public <T> void delete(Class<T> entityClass, Object entityid) throws Exception;
	/**
	 * 删除实体 
	 * @param entityClass 实体类 
	 * @param entityids 实体id数组 
	 */
	public <T> void delete(Class<T> entityClass, Object[] entityids) throws Exception;
	/**
	 * 获取实体 
	 * @param <T> 
	 * @param entityClass 实体类 
	 * @param entityId 实体id 
	 * @return 
	 */
	public <T> T find(Class<T> entityClass, Object entityId) throws Exception;
	/**
	 * 获取最大id
	 * @param <T> 
	 * @param entityClass 实体类 
	 * @return 
	 */
	public <T> int findMaxId(Class<T> entityClass) throws Exception;
	/** 
	 * 获取分页数据 
	 * @param <T> 
	 * @param entityClass 实体类 
	 * @param firstindex 开始索引 
	 * @param maxresult 需要获取的记录数 
	 * @return 
	 */
	public <T> QueryResult<T> getScrollData(Class<T> entityClass, int firstindex, int maxresult
			, String wherejpql, Object[] queryParams,LinkedHashMap<String, String> orderby) throws Exception;
	
	public <T> QueryResult<T> getScrollData(Class<T> entityClass, int firstindex, int maxresult
			, String wherejpql, Object[] queryParams,LinkedHashMap<String, String> orderby, String groupBy) throws Exception;

	public <T> QueryResult<T> getScrollData(Class<T> entityClass, int firstindex, int maxresult
			, String wherejpql, Object[] queryParams) throws Exception;

	public <T> QueryResult<T> getScrollData(Class<T> entityClass, int firstindex, int maxresult
			, LinkedHashMap<String, String> orderby) throws Exception;

	public <T> QueryResult<T> getScrollData(Class<T> entityClass, int firstindex, int maxresult) throws Exception;

	public <T> QueryResult<T> getScrollData(Class<T> entityClass) throws Exception;
	public <T> QueryResult<T> getScrollData(Class<T> entityClass, String wherejpql, Object[] queryParams) throws Exception;
	
	//查询code表
	public List<SysCode> getSelect(String codeId) throws Exception;
	
	//查询code表
	public String getSelect(String codeId,String codeDetailId) throws Exception;
		
	//根据索引查找
	public <T> Object getByIndex(Class<T> entityClass, String index, String value) throws Exception;
	
	//根据索引查找
	public <T> Object getByIndexWithFlg(Class<T> entityClass, String index, String value) throws Exception;
	
	public <T> Object getBasicData(Class<T> entityClass) throws Exception;
	
	//获取权限
	public List<String> getAuthority(String roleKey) throws Exception;
	
}
