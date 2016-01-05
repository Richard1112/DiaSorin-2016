package com.diasorin.oa.common;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateFormatCommon {

	public static int getDateSx(){
		Calendar cal = Calendar.getInstance();
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		if (hour < 12) {
			return 1;
		} else if (hour >= 12 && hour < 18) {
			return 2;
		} else {
			return 3;
		}
	}
	
	/**
	 * change timestamp to long
	 * 
	 * @author x-wang 2012/12/17
	 * @param Timestamp
	 *            timestamp
	 * @return long
	 */
	public static long timestamp2long(Timestamp timestamp) {
		if (timestamp == null) {
			return 0;
		}
		return timestamp.getTime();
	}

	/**
	 * change long to timestamp
	 * 
	 * @author x-wang 2012/12/17
	 * @param
	 * @return Timestamp
	 */
	public static Timestamp long2Timestamp(long time) {
		return new Timestamp(time);
	}

	/**
	 * change string to timestamp
	 * 
	 * @author x-wang 2012/12/17
	 * @param String
	 *            date
	 * @return Timestamp
	 */
	@SuppressWarnings("deprecation")
	public static Timestamp string2Timestamp(String date) {
		if (date == null || "".equals(date)) {
			return getSystemTimestamp();
		}
		date = date.replace("/", "-");
		int index = date.indexOf("-");
		int _day = 0;
		int _month = 0;
		int _year = 0;
		if (index == 2) {
			_day = Integer.parseInt(date.split("-")[0]);
			_month = Integer.parseInt(date.split("-")[1]);
			_year = Integer.parseInt(date.split("-")[2]);
		} else {
			_year = Integer.parseInt(date.split("-")[0]);
			_month = Integer.parseInt(date.split("-")[1]);
			_day = Integer.parseInt(date.split("-")[2]);
		}
		Timestamp time = new Timestamp(_year - 1900, _month - 1, _day, 0, 0, 0, 0);
		return time;
	}

	/**
	 * timestamp to String
	 * 
	 * @author x-wang 2012/12/14
	 * @param Timestamp
	 * @return String
	 */
	public static String timestamp2String(Timestamp timestamp) {
		if (timestamp == null) {
			return "";
		}
		String date = timestamp.toString().split(" ")[0];
		String time = timestamp.toString().split(" ")[1];
		try {
			date = formatDate(date, "2");
			time = time.substring(0, 8);
		} catch (Exception e) {

			return "";
		}
		return date + " " + time;
	}

	/**
	 * string to date with a input format
	 * 
	 * @author x-wang 2012/11/27
	 * @param string
	 *            ,String format(like:"yyyyDDmm")
	 * @return Date
	 */
	public static Date string2DateWithFormat(String date, String format) {
		if (date != null && !"".equals(date)) {
			SimpleDateFormat sdf1 = new SimpleDateFormat(format);
			Date d = null;
			try {
				d = sdf1.parse(date);
			} catch (ParseException e) {

			}
			return d;
		} else {
			return new Date();
		}
	}

	/**
	 * Date to String with a input format
	 * 
	 * @author x-wang 2012/12/03
	 * @param Date
	 *            ,String format(like:"yyyyDDmm")
	 * @return String
	 */
	public static String date2StringWithFormat(Date date, String format) {
		if (date != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.format(date);
		} else {
			return "";
		}
	}

	/**
	 * make a date with page input params
	 * 
	 * @author x-wang 2012/12/03
	 * @param String
	 *            year ,String month ,String day ,String split
	 * @return String
	 */
	public static String makeDate(String year, String month, String day, String split) {
		return year + split + month + split + day;
	}

	/**
	 * format date
	 * 
	 * @param strOpDivesion
	 *            :1.to DB; 2.to PG
	 * @throws Exception
	 */
	public static String formatDate(String date, String strOpDivesion) throws Exception {
		if (date == null) {
			return "";
		}
		if ("".equals(date)) {
			return date;
		}
		if (!"".equals(date.trim())) {
			String result = "";
			try {
				SimpleDateFormat sdf1 = null;
				SimpleDateFormat sdf2 = null;
				Date d = null;
				int intDevision = Integer.parseInt(strOpDivesion);
				int intFirst = date.lastIndexOf("/");
				date = date.replace("/", "");
				switch (intDevision) {
				case 1:
					if (date.length() == 6) {
						if (intFirst == 2) {
							sdf1 = new SimpleDateFormat("MMyyyy");
						} else if (intFirst == 4) {
							sdf1 = new SimpleDateFormat("yyyyMM");
						} else {
							sdf1 = new SimpleDateFormat("yyyyMM");
						}
						sdf2 = new SimpleDateFormat("yyyyMM");
						d = sdf1.parse(date);
						result = sdf2.format(d).toString();

					} else if (date.length() == 8) {
						if (intFirst == 5) {
							sdf1 = new SimpleDateFormat("ddMMyyyy");
						} else if (intFirst == 7) {
							sdf1 = new SimpleDateFormat("yyyyMMdd");
						} else {
							sdf1 = new SimpleDateFormat("yyyyMMdd");
						}
						sdf2 = new SimpleDateFormat("yyyyMMdd");
						d = sdf1.parse(date);
						result = sdf2.format(d).toString();
					}

					break;
				case 2:
					if (date.length() == 6) {
						if (intFirst == 2) {
							sdf1 = new SimpleDateFormat("MMyyyy");
						} else if (intFirst == 4) {
							sdf1 = new SimpleDateFormat("yyyyMM");
						} else {
							sdf1 = new SimpleDateFormat("yyyyMM");
						}
						sdf2 = new SimpleDateFormat("yyyy/MM");
						d = sdf1.parse(date);
						result = sdf2.format(d).toString();
					} else if (date.length() == 8) {
						if (intFirst == 5) {
							sdf1 = new SimpleDateFormat("ddMMyyyy");
						} else if (intFirst == 7) {
							sdf1 = new SimpleDateFormat("yyyyMMdd");
						} else {
							sdf1 = new SimpleDateFormat("yyyyMMdd");
						}
						sdf2 = new SimpleDateFormat("dd/MM/yyyy");
						d = sdf1.parse(date);
						result = sdf2.format(d).toString();

					}
					break;
				case 3:
					if (date.length() == 6) {
						sdf1 = new SimpleDateFormat("yyyyMM");
						sdf2 = new SimpleDateFormat("yyyy/MM");
						d = sdf1.parse(date);
						result = sdf2.format(d).toString();
					} else if (date.length() == 8) {
						sdf1 = new SimpleDateFormat("yyyyMMdd");
						sdf2 = new SimpleDateFormat("yyyy/MM/dd");
						d = sdf1.parse(date);
						result = sdf2.format(d).toString();
					}
					break;
				case 4:
					sdf1 = new SimpleDateFormat("yyyy-MM-dd HHmmss");
					sdf2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					d = sdf1.parse(date);
					result = sdf2.format(d).toString();
					
					break;
				}
			} catch (ParseException e) {

			}
			date = result;
			return date;
		} else {
			return date;
		}
	}

	/**
	 * get system time width format
	 */
	public static String getSystemTimeWidthFormat(String format) {
		if (format != null) {
			SimpleDateFormat simple = new SimpleDateFormat(format);
			return simple.format(new Date());
		} else {
			return new Date().toString();
		}
	}

	/**
	 * get system timestamp
	 */
	public static Timestamp getSystemTimestamp() {
		return new Timestamp(System.currentTimeMillis());
	}

	/**
	 * get year
	 */
	public static String getYear(int count) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.YEAR, count);
		return new SimpleDateFormat("yyyy").format(c.getTime()).toString();
	}

	/**
	 * get month
	 */
	public static String getMonth(int count) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, count);
		return new SimpleDateFormat("MM").format(c.getTime()).toString();
	}

	/**
	 * get day
	 */
	public static String getDay(int count) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, count);
		return new SimpleDateFormat("dd").format(c.getTime()).toString();
	}

	/**
	 * get a Specified date
	 * 
	 */
	@SuppressWarnings("deprecation")
	public static String getSpecifiedDate(String strDate, int intYPlus, int intMPlus, int intAppointDay) {
		if (!"".equals(strDate.trim())) {
			String strResult = "";
			int intIndesOfS = strDate.indexOf("/");
			try {
				strDate = strDate.replace("/", "-");
				int intIndex = strDate.indexOf("-");
				strDate = strDate.replace("-", "");
				SimpleDateFormat simpleDateFormat;
				int intDay = 0;
				Date d = null;
				if (strDate.length() == 6) {
					simpleDateFormat = new SimpleDateFormat("yyyyMM");
					d = simpleDateFormat.parse(strDate);
				} else if (strDate.length() == 8) {
					simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
					d = simpleDateFormat.parse(strDate);
				} else {

				}
				Calendar c = Calendar.getInstance();
				c.setTime(d);
				c.add(Calendar.YEAR, intYPlus);
				c.add(Calendar.MONTH, intMPlus);
				if (intAppointDay > 0) {
					intDay = intAppointDay - c.getTime().getDate();
					if (intDay > 0) {
						intDay = 0;
					}
					c.add(Calendar.DAY_OF_MONTH, intDay);
				} else if (intAppointDay == 0) {
					c.add(Calendar.DAY_OF_MONTH, 0);
				} else {
					c.add(Calendar.DAY_OF_MONTH, intAppointDay);
				}
				if (intIndex > 0) {
					SimpleDateFormat sdf;
					if (strDate.length() == 6) {
						sdf = new SimpleDateFormat("yyyy-MM");
						strResult = sdf.format(c.getTime());
					} else if (strDate.length() == 8) {
						sdf = new SimpleDateFormat("yyyy-MM-dd");
						strResult = sdf.format(c.getTime());
					} else {

					}
				} else {
					SimpleDateFormat sdf;
					if (strDate.length() == 6) {
						sdf = new SimpleDateFormat("yyyyMM");
						strResult = sdf.format(c.getTime());
					} else if (strDate.length() == 8) {
						sdf = new SimpleDateFormat("yyyyMMdd");
						strResult = sdf.format(c.getTime());
					} else {

					}
				}
			} catch (ParseException e) {
				return null;
			}
			strDate = strResult;
			if (intIndesOfS > 0) {
				strDate = strDate.replace("-", "/");
			}
			return strDate;
		} else {
			return strDate;
		}
	}

	public static String getSpecifiedDateAndAddDay(String strDate, int intYPlus, int intMPlus, int intMDay) {
		if (!"".equals(strDate.trim())) {
			String strResult = "";
			int intIndesOfS = strDate.indexOf("/");
			try {
				strDate = strDate.replace("/", "-");
				int intIndex = strDate.indexOf("-");
				strDate = strDate.replace("-", "");
				SimpleDateFormat simpleDateFormat;
				int intDay = 0;
				Date d = null;
				if (strDate.length() == 6) {
					simpleDateFormat = new SimpleDateFormat("yyyyMM");
					d = simpleDateFormat.parse(strDate);
				} else if (strDate.length() == 8) {
					simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
					SimpleDateFormat smdf = new SimpleDateFormat("dd");
					d = simpleDateFormat.parse(strDate);
					intDay = Integer.parseInt(smdf.format(d));
				} else {

				}
				Calendar c = Calendar.getInstance();
				c.setTime(d);
				c.add(Calendar.YEAR, intYPlus);
				c.add(Calendar.MONTH, intMPlus);
				if (intMDay > 0) {
					intDay = intMDay;
					c.add(Calendar.DAY_OF_MONTH, intDay);
				} else if (intMDay == 0) {
					c.add(Calendar.DAY_OF_MONTH, 0);
				} else {
					c.add(Calendar.DAY_OF_MONTH, intMDay);
				}
				if (intIndex > 0) {
					SimpleDateFormat sdf;
					if (strDate.length() == 6) {
						sdf = new SimpleDateFormat("yyyy-MM");
						strResult = sdf.format(c.getTime());
					} else if (strDate.length() == 8) {
						sdf = new SimpleDateFormat("yyyy-MM-dd");
						strResult = sdf.format(c.getTime());
					} else {

					}
				} else {
					SimpleDateFormat sdf;
					if (strDate.length() == 6) {
						sdf = new SimpleDateFormat("yyyyMM");
						strResult = sdf.format(c.getTime());
					} else if (strDate.length() == 8) {
						sdf = new SimpleDateFormat("yyyyMMdd");
						strResult = sdf.format(c.getTime());
					} else {

					}
				}
			} catch (ParseException e) {
				return null;
			}
			strDate = strResult;
			if (intIndesOfS > 0) {
				strDate = strDate.replace("-", "/");
			}
			return strDate;
		} else {
			return strDate;
		}
	}

	/**
	 * get the numer which between of these two days
	 */
	public static String getBetweenDays(String beforeDate, String afterDate) {
		if (beforeDate == null || afterDate == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		long time = 0;
		try {
			Date date1 = sdf.parse(beforeDate);
			Date date2 = sdf.parse(afterDate);
			time = date2.getTime() - date1.getTime();
			time = time / 1000 / 60 / 60 / 24;
			return String.valueOf(time);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 得到当前时间 yyyy-MM-dd
	 */
	public static String getNowTimeFormat() {
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(c.getTime());
	}

	/**
	 * 得到前一个月的时间
	 */
	public static String getBeforMonthFormat() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, -1);
		c.add(Calendar.DATE, 1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(c.getTime());
	}
	/**
	 * 得到当前月
	 */
	public static String getNowMonth(Date millis) {
		Calendar c = Calendar.getInstance();
		c.setTime(millis);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		return sdf.format(c.getTime());
	}
	/**
	 * 得到前一个月
	 */
	public static String getBeforMonth(Date millis) {
		Calendar c = Calendar.getInstance();
		c.setTime(millis);
		c.add(Calendar.MONTH, -1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		return sdf.format(c.getTime());
	}

	/**
	 * 得到前下个月个月的时间
	 * @throws ParseException 
	 */
	public static String getAfterMonthFormat(String applicationDate) throws ParseException {
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
			Date applyDate = df.parse(applicationDate);

			Calendar cal = Calendar.getInstance();
			cal.setTime(applyDate);
			cal.add(Calendar.MONTH, 1);
			return String.valueOf(df.format(cal.getTime()).substring(0, 7));
		} catch (ParseException e) {
			throw e;
		}
	}
	/**
	 * get the numer which between of these two days
	 */
	public static int getBetweenMonth(String beforeDate, String afterDate) {
		if (beforeDate == null || afterDate == null) {
			return 0;
		}
		String yearF = beforeDate.substring(0, 4);
		String monthF = beforeDate.substring(4, 6);

		String yearT = afterDate.substring(0, 4);
		String monthT = afterDate.substring(4, 6);

		return (Integer.parseInt(yearT) - Integer.parseInt(yearF)) * 12
				+ Integer.parseInt(monthT) - Integer.parseInt(monthF);
	}
	
	/**
	 * 对象中 BigDecimal属性为空的设置为0
	 * @param ob
	 */
	@SuppressWarnings({ "unused", "rawtypes" })
	public static void setBigDecimalNull2Zero(Object ob) {
		Class clazz = ob.getClass();
		Field[] fs = clazz.getDeclaredFields();
		try {
			for (int i = 0; i < fs.length; i++) {
				Field f = fs[i];
				f.setAccessible(true); // 设置些属性是可以访问的
				Object val = f.get(ob);// 得到此属性的值
				String type = f.getType().toString();// 得到此属性的类型
				if (type.endsWith("BigDecimal")) {
					if (f.get(ob) == null) {
						f.set(ob, BigDecimal.ZERO); // 给属性设值
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 例子：2015-01-01 --> 20150101 
	 * @param str
	 */
	public static String strToYYYYMMDDNoCa(String str) {
		if (str == null) return "";
		return str.replaceAll("/", "").replaceAll("-", "");
	}
	
	
	/**
	 * 例子：2015/03 --> 20150331
	 * @param str
	 * @return
	 */
	public static String getMonthLastDayToYYYYMMDD(String str) {
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
			String yearAndMon[] = str.split("/");
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, Integer.valueOf(yearAndMon[0]));
			cal.set(Calendar.MONTH, Integer.valueOf(yearAndMon[1]));
			cal.set(Calendar.DATE, 1);
			cal.add(Calendar.DATE, -1);
			return df.format(cal.getTime());
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * 得到这个月的第一天2015/03
	 * @throws ParseException 
	 */
	public static String getMonthFirstDay(String date) throws Exception {
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, Integer.valueOf(date.split("/")[0]));
			cal.set(Calendar.MONTH, Integer.valueOf(date.split("/")[1]) - 1);
			cal.set(Calendar.DATE, 1);
			return df.format(cal.getTime());
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * 得到这个月的最后一天2015/03
	 * @throws ParseException 
	 */
	public static String getMonthLastDay(String date) throws Exception {
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, Integer.valueOf(date.split("/")[0]));
			cal.set(Calendar.MONTH, Integer.valueOf(date.split("/")[1]));
			cal.set(Calendar.DATE, 1);
			cal.add(Calendar.DATE, -1);
			return df.format(cal.getTime());
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * yyyymmdd --> yyyy-mm-dd
	 * @throws ParseException 
	 */
	public static String convertToShow(String date) throws Exception {
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat dfShow = new SimpleDateFormat("yyyy-MM-dd");
			return dfShow.format(df.parse(date));
			
		} catch (Exception e) {
			throw e;
		}
	}
}
