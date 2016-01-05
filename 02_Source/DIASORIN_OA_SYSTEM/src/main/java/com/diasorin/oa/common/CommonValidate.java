package com.diasorin.oa.common;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonValidate {
	

	/**
	 * 汉字check
	 * @param str
	 * @return
	 */
	public static boolean hanZiCheck(String str) {
		if(str == null || "".equals(str)) {
			return false;
		}
		String regEx = "[\u4e00-\u9fa5]";
        Pattern p = Pattern.compile(regEx);
        int num = 0;//汉字长度
        
        for(int i=0;i<str.length();i++){
        	Matcher ma = p.matcher(str.substring(i, i + 1));
            if(ma.matches()){
                num++;
            }
        }
        if(num==str.length()){
        	return true;
        }else{
        	return false;
        }
	}

	/**
	 * 手机号check
	 * @param str 手机号
	 * @return 结果
	 * @author x-wang
	 */
	public static boolean phoneCheck(String str) {
		if(str == null || "".equals(str)) {
			return false;
		}
		String reg = "^[1][3-8]\\d{9}$";
		Pattern pa = Pattern.compile(reg);
		Matcher ma = pa.matcher(str);
		return ma.matches();
	}
	
	/**
	 * int check
	 * @author x-wang
	 * 2012/12/08
	 * @param string
	 * @return boolean
	 */
	public static boolean isInt(String str) {
		if(str == null || "".equals(str)) {
			return false;
		}
		String reg = "\\d{0,}";
		Pattern pa = Pattern.compile(reg);
		Matcher ma = pa.matcher(str);
		return ma.matches();
	}
	
	/**
	 * compare two date
	 * @author x-wang
	 * 2012/12/08
	 * @param 
	 * @return boolean
	 */
	public static boolean compareFromAndTo(String date_f ,String date_t) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date frome = sdf.parse(date_f);
			Date to = sdf.parse(date_t);
			if(frome.compareTo(to) > 0) {
				return false;
			}
			else {
				return true;
			}
		} catch (ParseException e) {
			return false;
		}
	}
	
	/**
	 * check year
	 * @author x-wang
	 * 2012/12/03
	 * @param String year
	 * @return boolean
	 */
	public static boolean isYearAvailable(String year) {
		int yr = Integer.parseInt(year);
		if(yr > 1900 && yr < 3000) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * check month
	 * @author x-wang
	 * 2012/12/03
	 * @param String month
	 * @return boolean
	 */
	public static boolean isMonthAvailable(String month) {
		int mon = Integer.parseInt(month);
		if(mon > 0 && mon < 13) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * 
	 * @param  String   Input String 
	 * @return boolean  (ture/false)
	 */
	public static boolean nullCheck(Object strName) {
		if(strName == null || "".equals(strName)) {
			return true;
		}
		else {
			if (strName instanceof String) {
				String s = strName.toString().replaceAll("^(\\s|\\u3000)*|(\\u3000|\\s)*$", "");
				if ((s == null || "".equals(s))) {
					return true;
				}
			}
			
			return false;
		}
	}

	/**
	 * 
	 * @param  String	Input String 
	 * @param  int	   Length
	 * @return boolean   (ture/false)
	 * @throws UnsupportedEncodingException 
	 */
	public static boolean lengthCheck(String strName,int strLen)
	{
		try {
			if(strName.getBytes("UTF-8").length > strLen) {
				return false;	
			}
			return true;
		}
		catch(Exception e) {
			return false;
		}
	}

	/**
	 * 
	 * @param  String	Input String 
	 * @param  int	   Length
	 * @return boolean   (ture/false)
	 */
	public static boolean minLengthCheck(String strName,int strLen)
	{
		try {
			if(strName.getBytes("Shift-JIS").length < strLen) {
				return false;	
			}
			return true;
		}
		catch(Exception e) {
			return false;
		}
	}

	/**
	 * 
	 * @param  String	Input String 
	 * @param  int	   Min Length
	 * @param  int	   Max Length
	 * @return boolean   (ture/false)
	 */
	public static boolean lenScopeCheck(String strName,int intMinLen,int intMaxLen)
	{
		try {
			if(strName.getBytes("Shift-JIS").length > intMaxLen) {
				return false;	
			}
			if(strName.getBytes("Shift-JIS").length < intMinLen) {
				return false;	
			}
			return true;
		}
		catch(Exception e) {
			return false;
		}
	}

	/**
	 * 
	 * @param  String	Input String 
	 * @param  int	   int Length
	 * @param  int	   Decimal Length
	 * @return boolean   (ture/false)
	 */
	public static boolean floatCheck(String strName,int intLen,int intDecimalLen)
	{
		if(nullCheck(strName)) {
			return true;
		}
		String reg = "\\d{0,"+String.valueOf(intLen)+"}";
		if(intDecimalLen>0) {
			reg += "(\\.\\d{0,"+String.valueOf(intDecimalLen)+"})?";
		}
		Pattern pa = Pattern.compile(reg);
		Matcher ma = pa.matcher(strName);
		return ma.matches();
	}

	/**
	 * 
	 * @param  String	Input String 
	 * @param  String	Link
	 * @return boolean   (ture/false)
	 */
	public static boolean dateCheck(String pDateObj,String strLink)
	{
		if(nullCheck(pDateObj)) {
			return true;
		}
		boolean ret = true;
		if(pDateObj.length()!=10) {
			ret = false;
		}
		if(!pDateObj.substring(4,5).equals(strLink) || !pDateObj.substring(7,8).equals(strLink)) {
			ret = false;
		}
		try {
			int year = new Integer(pDateObj.substring(0,4)).intValue();
			int month = new Integer(pDateObj.substring(5,7)).intValue();
			int day = new Integer(pDateObj.substring(8)).intValue();
			Calendar cal = Calendar.getInstance();
			cal.setLenient(false);
			cal.set(year,month-1,day);
			cal.getTime();
		}
		catch(Exception e) {
			ret = false;
		}
		return ret;
	}

	/**
	 * 英数字のみ入力可能
	 * @param  String	Input String 
	 * @return boolean   (ture/false)
	 */
	public static boolean halfWidthStringCheck(String strName)
	{
		if(nullCheck(strName)) {
			return true;
		}
		Pattern pa = Pattern.compile("([a-z0-9A-Z])*");
		Matcher ma = pa.matcher(strName);
		return ma.matches();
	}

	/**
	 * 英数字、スペースのみ入力可能
	 * @param  String	Input String 
	 * @return boolean   (ture/false)
	 */
	public static boolean halfWidthspaceStringCheck(String strName)
	{
		if(nullCheck(strName)) {
			return true;
		}
		Pattern pa = Pattern.compile("([a-z0-9A-Z ])*");
		Matcher ma = pa.matcher(strName);
		return ma.matches();
	}

	/**
	 * @param  String	Input String 
	 * @return boolean   (ture/false)
	 */
	public static boolean hfCheck(String strName)
	{
		if(nullCheck(strName)) {
			return true;
		}
		Pattern pa = Pattern.compile("([a-z0-9A-Z ,_-])*");
		Matcher ma = pa.matcher(strName);
		return ma.matches();
	}

	/**
	 * 英数字とアンダーバー（_）、ハイフン（‐）のみ入力可能
	 * @param  String	Input String 
	 * @return boolean   (ture/false)
	 */
	public static boolean hfEisujiCheck(String strName)
	{
		if(nullCheck(strName)) {
			return true;
		}
		Pattern pa = Pattern.compile("([a-z0-9A-Z_-])*");
		Matcher ma = pa.matcher(strName);
		return ma.matches();
	}

	/**
	 * 英数字とアンダーバー（_）、ハイフン（‐）のみ入力可能
	 * @param  String	Input String 
	 * @return boolean   (ture/false)
	 */
	public static boolean hfEisujiCheck1(String strName)
	{
		if(nullCheck(strName)) {
			return true;
		}
		Pattern pa = Pattern.compile("([0-9A-Z ])*");
		Matcher ma = pa.matcher(strName);
		return ma.matches();
	}

	/**
	 * 半角のみ入力可能
	 * @param  String	Input String 
	 * @return boolean   (ture/false)
	 */
	public static boolean halfCheck(String strName)
	{
		try {
			if(nullCheck(strName)) {
				return true;
			}
			if (strName.getBytes("Shift-JIS").length==strName.length()) {
				return true;
			}
			else {
				return false;
			}
		}
		catch(Exception e) {
			return false;
		}
	}

	/**
	 * 
	 * @param  String	BeginDate
	 * @param  String	EndDate
	 * @return boolean   (ture/false)
	 */
	public static boolean dateRangeCheck(String strBeginDate,String strEndDate)
	{
		if(strBeginDate.compareTo(strEndDate)>0) {
			return false;
		}
		else {
			return true;
		}
	}

	/**
	 * 正しメールアドレス入力可能
	 * @param  String	strMail
	 * @return boolean   (ture/false)
	 */
	public static boolean mailCheck(String strMail)
	{
		if(nullCheck(strMail)) {
			return true;
		}
		Pattern pa = Pattern.compile("^(?:[a-zA-Z\\d]+[_\\-\\+\\.])*[a-zA-Z\\d]+@(?:([a-zA-Z\\d]+\\-)*[a-zA-Z\\d]+\\.)+([a-zA-Z]{2,})+$");
		Matcher ma = pa.matcher(strMail);
		return ma.matches();
	}

	/**
	 * 数字のみ入力可能
	 * @param  String	strMail
	 * @return boolean   (ture/false)
	 */
	public static boolean intCheck(String strName, String flg )
	{
		if(nullCheck(strName)) {
			return true;
		}
		String reg = "\\d{0,}";
		if(flg.equals("Y"))
		{
			reg = "(-)?"+reg;
		}
		Pattern pa = Pattern.compile(reg);
		Matcher ma = pa.matcher(strName);
		return ma.matches();
	}

	/**
	 * コンマ入力不可
	 * @param  String	Input String 
	 * @return boolean   (ture/false)
	 */
	public static boolean commaCheck(String strName)
	{
		if(nullCheck(strName)) {
			return true;
		}
		if (strName.indexOf(",") != -1) {
			return false;
		}
		else {
			return true;
		}
	}	

	/**
	 * 全角カナチェック
	 * @param  String	Input String 
	 * @return boolean   (true/false)
	 */
	public static boolean zenkakuCheck(String strName)
	{
		if(nullCheck(strName)) {
			return true;
		}
		Pattern pa = Pattern.compile("^[ァ-ー　]+$");
		Matcher ma = pa.matcher(strName);
		return ma.matches();
	}

	/**  
	 * 功能：身份证的有效验证  
	 * @param IDStr 身份证号  
	 * @return 有效：返回"" 无效：返回String信息  
	 * @throws ParseException  
	 */  
	public static boolean IDCardValidate(String IDStr) throws ParseException {
		if(nullCheck(IDStr)) {
			return false;
		}
		if (IDStr.length() == 18) {
			return true;
		} else {
			return false;
		}
		
/*		String errorInfo = "";// 记录错误信息   
		String[] ValCodeArr = { "1", "0", "X", "9", "8", "7", "6", "5", "4",
				"3", "2" };
		String[] Wi = { "7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7",
				"9", "10", "5", "8", "4", "2" };
		String Ai = "";
		// ================ 号码的长度 15位或18位 ================
		if (IDStr.length() != 15 && IDStr.length() != 18) {
			errorInfo = "身份证号码长度应该为15位或18位。";
			//return false;
		}
		// =======================(end)========================

		// ================ 数字 除最后以为都为数字 ================
		if (IDStr.length() == 18) {
			Ai = IDStr.substring(0, 17);
		} else if (IDStr.length() == 15) {
			Ai = IDStr.substring(0, 6) + "19" + IDStr.substring(6, 15);
		} 
		if (isNumeric(Ai) == false) {
			errorInfo = "身份证15位号码都应为数字 ; 18位号码除最后一位外，都应为数字。";
			//return false;
		}
		// =======================(end)========================

		// ================ 出生年月是否有效 ================
		String strYear = Ai.substring(6, 10);// 年份   
		String strMonth = Ai.substring(10, 12);// 月份   
		String strDay = Ai.substring(12, 14);// 月份   
		if (isDataFormat(strYear + "-" + strMonth + "-" + strDay) == false) {
			errorInfo = "身份证生日无效。";
			//return false;
		} 
		GregorianCalendar gc = new GregorianCalendar();
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
		if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 150  
				|| (gc.getTime().getTime() - s.parse(   
						strYear + "-" + strMonth + "-" + strDay).getTime()) < 0) {
			errorInfo = "身份证生日不在有效范围。";
			//return false;
		} 
		if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {
			errorInfo = "身份证月份无效";
			//return false;
		} 
		if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {
			errorInfo = "身份证日期无效";
			//return false;
		}
		// =====================(end)=====================

		// ================ 地区码时候有效 ================
		Hashtable<String, String> h = GetAreaCode();
		if (h.get(Ai.substring(0, 2)) == null) {
			errorInfo = "身份证地区编码错误。";
			//return false;
		}
		// ==============================================
		// ================ 判断最后一位的值 ================
		int TotalmulAiWi = 0;
		for (int i = 0; i < 17; i++) {
			TotalmulAiWi = TotalmulAiWi
					+ Integer.parseInt(String.valueOf(Ai.charAt(i)))
					* Integer.parseInt(Wi[i]);
		}
		int modValue = TotalmulAiWi % 11;
		String strVerifyCode = ValCodeArr[modValue];
		Ai = Ai + strVerifyCode;

		if (IDStr.length() == 18) {
			if (Ai.equals(IDStr) == false) {
				errorInfo = "身份证无效，不是合法的身份证号码";
				//return false;
			}
		} else {
			return true;
		}
		if ("".equals(errorInfo)) {
			return true;
		} else {
			SendMailDto sendMailDto = new SendMailDto();
			sendMailDto.setTitle("身份证验证错误");
			sendMailDto.setContent("原身份证号："+IDStr+"-------计算后身份证号："+Ai+"-------错误信息："+errorInfo);
			List<String> to = new ArrayList<String>();
			to.add("x-wang@logictech.cn");
			sendMailDto.setTo(to);
			List<String> paramList = new ArrayList<String>();

			try {
				MailUtil.sendMail(sendMailDto, paramList);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;
		}*/
		// =====================(end)=====================
		//return true;
	}
	/**
	 * 功能：判断字符串是否为数字  
	 * @param str  
	 * @return  
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (isNum.matches()) {
			return true;
		} else {
			return false;
		}
	}
		
	/**
	 * 功能：设置地区编码  
	 * @return Hashtable 对象  
	 */
	public static Hashtable<String, String> GetAreaCode() {
		Hashtable<String, String> hashtable = new Hashtable<String, String>();
		hashtable.put("11", "北京");
		hashtable.put("12", "天津");
		hashtable.put("13", "河北");
		hashtable.put("14", "山西");
		hashtable.put("15", "内蒙古");
		hashtable.put("21", "辽宁");
		hashtable.put("22", "吉林");
		hashtable.put("23", "黑龙江");
		hashtable.put("31", "上海");
		hashtable.put("32", "江苏");
		hashtable.put("33", "浙江");
		hashtable.put("34", "安徽");
		hashtable.put("35", "福建");
		hashtable.put("36", "江西");
		hashtable.put("37", "山东");
		hashtable.put("41", "河南");
		hashtable.put("42", "湖北");
		hashtable.put("43", "湖南");
		hashtable.put("44", "广东");
		hashtable.put("45", "广西");
		hashtable.put("46", "海南");
		hashtable.put("50", "重庆");
		hashtable.put("51", "四川");
		hashtable.put("52", "贵州");
		hashtable.put("53", "云南");
		hashtable.put("54", "西藏");
		hashtable.put("61", "陕西");
		hashtable.put("62", "甘肃");
		hashtable.put("63", "青海");
		hashtable.put("64", "宁夏");
		hashtable.put("65", "新疆");
		hashtable.put("71", "台湾");
		hashtable.put("81", "香港");
		hashtable.put("82", "澳门");
		hashtable.put("91", "国外");
		return hashtable;
	}

	/**验证日期字符串是否是YYYY-MM-DD格式
	 * @param str
	 * @return
	 */
	public static boolean isDataFormat(String str){
		boolean flag=false;
		//String regxStr="[1-9][0-9]{3}-[0-1][0-2]-((0[1-9])|([12][0-9])|(3[01]))";
		String regxStr="^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$";
		Pattern pattern1=Pattern.compile(regxStr);
		Matcher isNo=pattern1.matcher(str);
		if(isNo.matches()){
			flag=true;
		}
		return flag;
	}

}
