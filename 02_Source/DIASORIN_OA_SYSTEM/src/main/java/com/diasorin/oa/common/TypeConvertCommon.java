package com.diasorin.oa.common;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;

import javax.imageio.ImageIO;

public class TypeConvertCommon {

	public static String object2NotNullString(Object intStr) {
		if(intStr == null) {
			return "";
		}
		return intStr.toString();
	}
	public static Integer object2NotNullInteger(Object intStr) {
		if(intStr == null) {
			return 0;
		}
		return Integer.parseInt(intStr.toString());
	}
	public static String getImageBinary(File f){
		BufferedImage bi;
		try {
			bi = ImageIO.read(f);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(bi, "jpg", baos);
			byte[] bytes = baos.toByteArray();
			return new sun.misc.BASE64Encoder().encodeBuffer(bytes).trim();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String numberLengthFormat(Object o, int len) {
		String pattern="";
		for (int i=0;i<len;i++) {
			pattern += "0";
		}
		DecimalFormat df = new DecimalFormat(pattern);
		
		return df.format(o);
	}
	
	/**
	 * 金额格式转换
	 * @param  BigDecimal        Input
	 * @return String       	 OUTPUT
	 */
	public static String convertToCurrencyFmt(BigDecimal i) {
		if(i == null) {
			return "";
		}
		String str = i.toString();
		DecimalFormat fmt = new DecimalFormat("##,###,###,###,##0.00");
		String outStr = null;
		double d;
		try {
			d = Double.parseDouble(str);
			outStr = fmt.format(d);
		}
		catch(Exception e) {
		}
		return outStr;
	}
	/**
	 * 金额格式转换
	 * @param  BigDecimal        Input
	 * @return String       	 OUTPUT
	 */
	public static String convertToCurrencyWPFmt(BigDecimal i) {
		if(i == null) {
			return "";
		}
		String str = i.toString();
		DecimalFormat fmt = new DecimalFormat("##,###,###,###,##0");
		String outStr = null;
		double d;
		try {
			d = Double.parseDouble(str);
			outStr = fmt.format(d);
		}
		catch(Exception e) {
		}
		return outStr;
	}
	
	/**
	 * SQL 有IN得创建等于的SQL文
	 * @param str
	 * @return
	 */
	public static String createWhereIn(String str, int length) {
		StringBuffer sb = new StringBuffer();
		sb.append(" ( ");
		for (int i = 0; i< length ; i++) {
			if ( i == 0) {
				sb.append(str);
			} else {
				sb.append("or " + str);
			}
		}
		sb.append(" ) ");
		return sb.toString();
	}
	
	public static String toString(Object obj) {
		if (obj == null) {
			return "";
		} else {
			return String.valueOf(obj);
		}
	}
}
