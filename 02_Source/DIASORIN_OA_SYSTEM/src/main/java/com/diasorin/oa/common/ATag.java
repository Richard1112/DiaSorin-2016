package com.diasorin.oa.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.lang.StringUtils;

import com.diasorin.oa.mvc.BaseController;

@SuppressWarnings("serial")
public class ATag extends BodyTagSupport {

	private String onclick;
	private String cssClass;
	private String id;
	private String value;
	private String innerImg;

	/**
	 * @return the innerImg
	 */
	public String getInnerImg() {
		return innerImg;
	}

	/**
	 * @param innerImg the innerImg to set
	 */
	public void setInnerImg(String innerImg) {
		this.innerImg = innerImg;
	}

	/**
	 * @return the onclick
	 */
	public String getOnclick() {
		return onclick;
	}

	/**
	 * @param onclick
	 *            the onclick to set
	 */
	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}

	/**
	 * @return the cssClass
	 */
	public String getCssClass() {
		return cssClass;
	}

	/**
	 * @param cssClass
	 *            the cssClass to set
	 */
	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int doStartTag() throws JspException {
		JspWriter out = pageContext.getOut();
		// 获取权限
		List<String> authority = (List<String>) pageContext.getSession().getAttribute("authority");
		String path = pageContext.getSession().getServletContext().getContextPath();
		//String valueStr = "";
		String cssClassStr = "";
		String aValue = "";
		if(StringUtils.isNotEmpty(this.value)){
			//valueStr = getPageContent(this.value);
		}
		if(StringUtils.isNotEmpty(this.cssClass)){
			cssClassStr = " class='"+this.cssClass +"' ";
		}
		if(StringUtils.isNotEmpty(this.innerImg)){
			aValue = "<img src='"+ path + getPageContent(this.innerImg) + "'/>";
		}
		if(StringUtils.isNotEmpty(this.value)){
			aValue = getPageContent(this.value);
		}
		try {
			if (null != authority && authority.contains(id)) {
				out.println("<a href=\"#\" " + cssClassStr + " onclick=\"" + this.onclick + "\" id='" + this.id + "'>"+
						aValue
						+"</a>");
			} else {
				out.println("");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return EVAL_PAGE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.jsp.tagext.TagSupport#doEndTag()
	 */
	@Override
	public int doEndTag() throws JspException {
		return SKIP_BODY;
	}
	
	public static String getPageContent(String key) {
		try {
			String language = "zh_CN";
			FileInputStream messageStream;
			String s = BaseController.class.getResource("/").getPath()
					.toString();
			Properties properties = new Properties();
			if ("zh_CN".equals(language)) {
				messageStream = new FileInputStream(s
						+ "/page_zh_CN.properties");
			} else if ("en_US".equals(language)) {
				messageStream = new FileInputStream(s
						+ "/page_en_US.properties");
			} else {
				messageStream = new FileInputStream(s
						+ "/page_zh_CN.properties");
			}
			properties.load(messageStream);
			if (properties.containsKey(key)) {
				String value = new String(properties.getProperty(key));
				return value;
			} else {
				return key;
			}
		} catch (FileNotFoundException ex) {
			return key;
		} catch (IOException ex) {
			return key;
		} catch (Exception e) {
			return "session超时处理";
		}
	}
}
