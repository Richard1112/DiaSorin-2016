package com.diasorin.oa.mvc;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Properties;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.diasorin.oa.common.CodeCommon;
import com.diasorin.oa.model.SysExpenses;
import com.diasorin.oa.service.SystemManagementService;

public class BaseController {

	protected static Log logger = null;
	
	protected static SysExpenses sysExpense = null;
	
	protected static BigDecimal rate = null;
	
	@Resource
	SystemManagementService systemManagementService;
	
	public BaseController() {
		logger = LogFactory.getLog(BaseController.class);
	}

	protected SysExpenses getS025Rate() throws Exception {
		if(sysExpense == null) {
			sysExpense = systemManagementService.getExpensesItemInfo(CodeCommon.PRIVATE_CAR_FOR_BUSINESS);
		} 
		return sysExpense;
	}
	
	protected BigDecimal getCarRate() throws Exception {
		if(sysExpense == null) {
			sysExpense = systemManagementService.getExpensesItemInfo(CodeCommon.PRIVATE_CAR_FOR_BUSINESS);
		} 
		String meth = sysExpense.getComputeMethod();
		BigDecimal rate = BigDecimal.ZERO;
		if (meth.endsWith("1")) {
			rate = new BigDecimal(sysExpense.getExtendsFieldCo1());
		} else if(meth.endsWith("2")) {
			rate = new BigDecimal(sysExpense.getExtendsFieldCo2());
		} else {
			rate = new BigDecimal(sysExpense.getExtendsFieldCo3());
		}
		return rate;
	}
	
	public String getMessage(String key) {
		try {
			// String language =
			// session.getAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME).toString();
			String language = "en_US";
			FileInputStream messageStream;
			String s = this.getClass().getClassLoader().getResource("/").getPath();
			s = java.net.URLDecoder.decode(s, "UTF-8");
			Properties properties = new Properties();
			if ("zh_CN".equals(language)) {
				messageStream = new FileInputStream(s
						+ "/message_zh_CN.properties");
			} else if ("en_US".equals(language)) {
				messageStream = new FileInputStream(s
						+ "/message_en_US.properties");
			} else {
				messageStream = new FileInputStream(s
						+ "/message_zh_CN.properties");
			}
			properties.load(messageStream);
			if (properties.containsKey(key)) {
				String value = new String(properties.getProperty(key));
				return value;
			} else {
				properties = new Properties();
				if ("zh_CN".equals(language)) {
					messageStream = new FileInputStream(s
							+ "/application_zh_CN.properties");
				} else if ("en_US".equals(language)) {
					messageStream = new FileInputStream(s
							+ "/application_en_US.properties");
				} else {
					messageStream = new FileInputStream(s
							+ "/application_zh_CN.properties");
				}
				properties.load(messageStream);
				if (properties.containsKey(key)) {
					String value = new String(properties.getProperty(key));
					return value;
				} else {
					properties = new Properties();
					if ("zh_CN".equals(language)) {
						messageStream = new FileInputStream(s
								+ "/whydl_Settings.properties");
					} else if ("en_US".equals(language)) {
						messageStream = new FileInputStream(s
								+ "/whydl_Settings.properties");
					} else {
						messageStream = new FileInputStream(s
								+ "/whydl_Settings.properties");
					}
					properties.load(messageStream);
					if (properties.containsKey(key)) {
						String value = new String(properties.getProperty(key));
						return value;
					} else {
						return key;
					}
				}
			}
		} catch (FileNotFoundException ex) {
			return key;
		} catch (IOException ex) {
			return key;
		} catch (Exception e) {
			return "session超时处理";
		}
	}

	public static String getApplicationMessage(String key) {
		try {
			// String language =
			// session.getAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME).toString();
			String language = "en_US";
			FileInputStream messageStream;
			String s = BaseController.class.getResource("/").getPath()
					.toString();
			s = java.net.URLDecoder.decode(s, "UTF-8");
			Properties properties = new Properties();
			if ("zh_CN".equals(language)) {
				messageStream = new FileInputStream(s
						+ "/application_zh_CN.properties");
			} else if ("en_US".equals(language)) {
				messageStream = new FileInputStream(s
						+ "/application_en_US.properties");
			} else {
				messageStream = new FileInputStream(s
						+ "/application_zh_CN.properties");
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

	public static String getPageContent(String key) {
		try {
			// String language =
			// session.getAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME).toString();
			String language = "en_US";
			FileInputStream messageStream;
			String s = BaseController.class.getResource("/").getPath()
					.toString();
			s = java.net.URLDecoder.decode(s, "UTF-8");
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

	private static Random randGen = null;
	private static char[] numbersAndLetters = null;

	public static final String randomString(int length) {
		if (length < 1) {
			return null;
		}
		if (randGen == null) {
			randGen = new Random();
			numbersAndLetters = ("0123456789abcdefghijklmnopqrstuvwxyz"
					+ "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
		}
		char[] randBuffer = new char[length];
		for (int i = 0; i < randBuffer.length; i++) {
			randBuffer[i] = numbersAndLetters[randGen.nextInt(71)];
		}
		return new String(randBuffer);
	}

	/**
	 * 获取系统时间
	 * 
	 * @return 系统时间
	 */
	public static Timestamp getSystem() {
		return new Timestamp(System.currentTimeMillis());
	}

	/**
	 * 下载
	 * 
	 * @author x-wang
	 * @date 2014-4-3
	 * @param request
	 * @param response
	 * @param fileName
	 * @throws Exception
	 */
	public static void download(HttpServletRequest request,
			HttpServletResponse response, String fileName, String ctxPath,
			String ctxPath1) throws Exception {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		String downLoadPath = ctxPath1 + ctxPath + fileName;

		long fileLength = new File(downLoadPath).length();

		response.setContentType("application/octet-stream");
		response.setHeader("Content-disposition", "attachment; filename="
				+ new String(fileName.getBytes("utf-8")));
		response.setHeader("Content-Length", String.valueOf(fileLength));

		bis = new BufferedInputStream(new FileInputStream(downLoadPath));
		bos = new BufferedOutputStream(response.getOutputStream());
		byte[] buff = new byte[2048];
		int bytesRead;
		while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
			bos.write(buff, 0, bytesRead);
		}

		bis.close();
		bos.close();
	}

}
