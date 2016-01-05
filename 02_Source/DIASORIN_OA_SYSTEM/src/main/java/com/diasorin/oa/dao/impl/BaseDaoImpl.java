package com.diasorin.oa.dao.impl;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.diasorin.oa.common.QueryResult;
import com.diasorin.oa.dao.BaseDao;
import com.diasorin.oa.model.SysCode;
@Component
@Transactional
public class BaseDaoImpl implements BaseDao {

	@PersistenceContext
	protected EntityManager em;
	
	public void clear(){
		em.clear();
	}
	
	public <T> void delete(Class<T> entityClass,Object entityid) throws Exception {
		try {
			delete(entityClass, new Object[]{entityid});
		}
		catch(Exception e) {
			throw e;
		}
	}
	
	public <T> void delete(Class<T> entityClass,Object[] entityids) throws Exception {
		try {
			for(Object id : entityids){
				em.remove(em.getReference(entityClass, id));
			}
		}
		catch(Exception e) {
			throw e;
		}
	}
	
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)  
	public <T> T find(Class<T> entityClass, Object entityId) throws Exception {
		try {
			return em.find(entityClass, entityId);
		}
		catch(Exception e) {
			throw e;
		}
	}
	
	public <T> int findMaxId(Class<T> entityClass) throws Exception {
		try {
			Object o = em.createQuery("select max(o.no) from " + getEntityName(entityClass) + " o").getSingleResult();
			return (o==null ? 0:(Integer)o);
		}
		catch(Exception e) {
			throw e;
		}
	}
	
	public void save(Object entity) throws Exception {
		try {
			em.persist(entity);
		}
		catch(Exception e) {
			throw e;
		}
	}
	
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)  
	public <T> long getCount(Class<T> entityClass) throws Exception {
		try {
			return (Long)em.createQuery("select count("+ getCountField(entityClass) +") from "+ getEntityName(entityClass)+ " o").getSingleResult();	
		}
		catch(Exception e) {
			throw e;
		}
	}
	
	public <T> long getCount(Class<T> entityClass, String wherejpql, Object[] queryParams) throws Exception {
		try {
			Query query = em.createQuery("select count("+ getCountField(entityClass) +") from "+ getEntityName(entityClass)+ " o " + (wherejpql==null? "": "where 1 = 1 "+ wherejpql));
			setQueryParams(query, queryParams);
			return (Long)query.getSingleResult();
		}
		catch(Exception e) {
			throw e;
		}
	}
	
	public void update(Object entity) throws Exception {
		try {
			em.merge(entity);
		}
		catch(Exception e) {
			throw e;
		}
	}
	
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)  
	public <T> QueryResult<T> getScrollData(Class<T> entityClass,  
			int firstindex, int maxresult, LinkedHashMap<String, String> orderby) throws Exception {
		try {
			return getScrollData(entityClass,firstindex,maxresult,null,null,orderby);	
		}
		catch(Exception e) {
			throw e;
		}
	}
	
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)  
	public <T> QueryResult<T> getScrollData(Class<T> entityClass,  
			int firstindex, int maxresult, String wherejpql, Object[] queryParams) throws Exception {
		try {
			return getScrollData(entityClass,firstindex,maxresult,wherejpql,queryParams,null);	
		}
		catch(Exception e) {
			throw e;
		}
	}
	
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)  
	public <T> QueryResult<T> getScrollData(Class<T> entityClass, int firstindex, int maxresult) throws Exception {
		try {
			return getScrollData(entityClass,firstindex,maxresult,null,null,null);	
		}
		catch(Exception e) {
			throw e;
		}
	}
	
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)  
	public <T> QueryResult<T> getScrollData(Class<T> entityClass) throws Exception {
		try {
			return getScrollData(entityClass, -1, -1);	
		}
		catch(Exception e) {
			throw e;
		}
	}
	
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)  
	public <T> QueryResult<T> getScrollData(Class<T> entityClass, String wherejpql, Object[] queryParams) throws Exception {
		try {
			return getScrollData(entityClass, -1, -1, wherejpql, queryParams,null);	
		}
		catch(Exception e) {
			throw e;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public <T> QueryResult<T> getScrollData(Class<T> entityClass, int firstindex, int maxresult  
			, String wherejpql, Object[] queryParams,LinkedHashMap<String, String> orderby, String groupBy) throws Exception{
		QueryResult<T> qr = new QueryResult<T>();
		String entityname = getEntityName(entityClass);
		Query query = em.createQuery("select o from "+ entityname+ " o "+(wherejpql==null? "": "where 1 = 1 "+ wherejpql) + (groupBy==null? "": " "+ groupBy) + buildOrderby(orderby));
		setQueryParams(query, queryParams);
		if(firstindex!=-1 && maxresult!=-1) query.setFirstResult(firstindex).setMaxResults(maxresult);
		try {
			qr.setResultlist(query.getResultList());
		}
		catch(Exception e) {
			throw e;
		}
		query = em.createQuery("select count("+ getCountField(entityClass)+ ") from "+ entityname+ " o "+(wherejpql==null? "": "where 1 = 1 "+ wherejpql));
		setQueryParams(query, queryParams);
		try {
			qr.setTotalrecord((Long)query.getSingleResult());	
		}
		catch(Exception e) {
			throw e;
		}
		return qr;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)  
	public <T> QueryResult<T> getScrollData(Class<T> entityClass, int firstindex, int maxresult  
			, String wherejpql, Object[] queryParams,LinkedHashMap<String, String> orderby) throws Exception {
		QueryResult<T> qr = new QueryResult<T>();
		String entityname = getEntityName(entityClass);
		Query query = em.createQuery("select o from "+ entityname+ " o "+(wherejpql==null? "": "where 1 = 1 "+ wherejpql)+ buildOrderby(orderby));
		setQueryParams(query, queryParams);
		if(firstindex!=-1 && maxresult!=-1) query.setFirstResult(firstindex).setMaxResults(maxresult);
		try {
			qr.setResultlist(query.getResultList());
		}
		catch(Exception e) {
			throw e;
		}
		query = em.createQuery("select count("+ getCountField(entityClass)+ ") from "+ entityname+ " o "+(wherejpql==null? "": "where 1 = 1 "+ wherejpql));
		setQueryParams(query, queryParams);
		try {
			
			qr.setTotalrecord(query.getSingleResult() == null ? 0 : (Long)query.getSingleResult());	
		}
		catch(Exception e) {
			throw e;
		}
		return qr;
	}
	
	protected void setQueryParams(Query query, Object[] queryParams) {
		if(queryParams!=null && queryParams.length>0){
			for(int i=0; i<queryParams.length; i++){
				query.setParameter(i+1, queryParams[i]);
			}
		}
	}
	
	protected String buildOrderby(LinkedHashMap<String, String> orderby) {
		StringBuffer orderbyql = new StringBuffer("");
		if(orderby!=null && orderby.size()>0){
			orderbyql.append(" order by ");
			for(String key : orderby.keySet()){
				orderbyql.append("o.").append(key).append(" ").append(orderby.get(key)).append(",");
			}
			orderbyql.deleteCharAt(orderbyql.length()-1);
		}
		return orderbyql.toString();
	}

	protected <T> String getEntityName(Class<T> entityClass) {
		String entityname = entityClass.getSimpleName();
		Entity entity = entityClass.getAnnotation(Entity.class);
		if(entity.name()!=null && !"".equals(entity.name())){
			entityname = entity.name();
		}
		return entityname;
	}
	
	protected <T> String getCountField(Class<T> clazz) throws Exception {
		String out = "o";
		try {
			PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(clazz).getPropertyDescriptors();
			for(PropertyDescriptor propertydesc : propertyDescriptors){
				Method method = propertydesc.getReadMethod();
				if(method!=null && method.isAnnotationPresent(EmbeddedId.class)){
					PropertyDescriptor[] ps = Introspector.getBeanInfo(propertydesc.getPropertyType()).getPropertyDescriptors();
					out = "o."+ propertydesc.getName()+ "." + (!ps[1].getName().equals("class")? ps[1].getName(): ps[0].getName());
					break;
				}
			}
		}
		catch(Exception e) {
			throw e;
		}
		return out;
	}

	
	@SuppressWarnings("unchecked")
	public List<SysCode> getSelect(String codeId) throws Exception {
		QueryResult<SysCode> qr = new QueryResult<SysCode>();
		String entityname = getEntityName(SysCode.class);
		String wherejpql = " where o.codeId = ? ";
		String orderBy = " order by o.codeDetailId ";
		Object[] queryParams = new Object[1];
		queryParams[0] = codeId;
		Query query = em.createQuery("select o from " + entityname+ " o " + wherejpql + orderBy);
		setQueryParams(query, queryParams);
		
		try {
			qr.setResultlist(query.getResultList());
		}
		catch(Exception e) {
			throw e;
		}

		return qr.getResultlist();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> Object getByIndex(Class<T> entityClass, String index, String value)
			throws Exception {
		QueryResult<T> qr = new QueryResult<T>();
		String entityname = getEntityName(entityClass);
		String wherejpql = " where o." + index + "= ? ";
		Object[] queryParams = new Object[1];
		queryParams[0] = value;
		Query query = em.createQuery("select o from " + entityname+ " o " + wherejpql);
		setQueryParams(query, queryParams);
		
		try {
			qr.setResultlist(query.getResultList());
		}
		catch(Exception e) {
			throw e;
		}
		if (qr.getResultlist() != null && qr.getResultlist().size() > 0) {
			return qr.getResultlist().get(0);
		} else {
			return null;
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> Object getBasicData(Class<T> entityClass) throws Exception {
		QueryResult<T> qr = new QueryResult<T>();
		String entityname = getEntityName(entityClass);

		String wherejpql = " where o.activeStatus = '0' and o.activeStartDate <= DATE_FORMAT(curdate(),'%Y%m%d')";
		
		Query query = em.createQuery("select o from " + entityname+ " o " + wherejpql);
		
		try {
			qr.setResultlist(query.getResultList());
		}
		catch(Exception e) {
			throw e;
		}
		if (qr.getResultlist() != null && qr.getResultlist().size() > 0) {
			return qr.getResultlist().get(0);
		} else {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> Object getByIndexWithFlg(Class<T> entityClass, String index, String value)
			throws Exception {
		QueryResult<T> qr = new QueryResult<T>();
		String entityname = getEntityName(entityClass);
		String wherejpql = " where o." + index + "= ? and o.deleteFlg = '0' ";
		Object[] queryParams = new Object[1];
		queryParams[0] = value;
		Query query = em.createQuery("select o from " + entityname+ " o " + wherejpql);
		setQueryParams(query, queryParams);
		
		try {
			qr.setResultlist(query.getResultList());
		}
		catch(Exception e) {
			throw e;
		}
		if (qr.getResultlist() != null && qr.getResultlist().size() > 0) {
			return qr.getResultlist().get(0);
		} else {
			return null;
		}
		
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public String getSelect(String codeId, String codeDetailId)
			throws Exception {
		QueryResult<SysCode> qr = new QueryResult<SysCode>();
		String entityname = getEntityName(SysCode.class);
		String wherejpql = " where o.codeId = ? and o.codeDetailId = ? ";
		Object[] queryParams = new Object[2];
		queryParams[0] = codeId;
		queryParams[1] = codeDetailId;
		Query query = em.createQuery("select o from " + entityname+ " o " + wherejpql);
		setQueryParams(query, queryParams);
		
		try {
			qr.setResultlist(query.getResultList());
		}
		catch(Exception e) {
			throw e;
		}

		return qr.getResultlist().get(0).getCodeDetailName();
	}

	@Override
	public void close() {
		em.close();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List bindDataToDTO(List list, Object ob, String[] fields) throws Exception {
		List resultList = new ArrayList();
		if (list != null && list.size() > 0) {
			for (int i=0;i<list.size();i++) {
				Object entity = ob.getClass().newInstance();
				Object[] o = (Object[]) list.get(i);
				
				encapsulateObject(entity,fields,o);
				
				resultList.add(entity);
			}
		}
		return resultList;
	}
	public static void encapsulateObject(Object object, String[] fields,
			Object[] params) {
		Class<?> cl = object.getClass();
		for (int i = 0; i < fields.length; i++) {
			StringBuffer buffer = new StringBuffer();
			try {
				Field field = cl.getDeclaredField(fields[i]);
				// 得到参数类型   
				Class<?> paramType = field.getType();
				buffer.append("set");  
				buffer.append(fields[i].substring(0, 1).toUpperCase());
				buffer.append(fields[i].substring(1));  
				Method method = cl.getDeclaredMethod(buffer.toString(),
						paramType);
				method.invoke(object, params[i]);
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAuthority(String roleKey) throws Exception {
		String sql = " ";
		List<String> param = new ArrayList<String>();
		
		if (null!=roleKey) {
			sql += " and sys023.roleId = ?";
			param.add(roleKey);
		}
		Query query_inf03 = em.createNativeQuery(
				" select sys023.controlId from tbl_sys00023 sys023 "
				+ " where 1 = 1 "
				+ sql
				+ " and sys023.authority = '1' ");
		setQueryParams(query_inf03, param.toArray());

		List<String> list = query_inf03.getResultList();
		
		return list;
	}
}
