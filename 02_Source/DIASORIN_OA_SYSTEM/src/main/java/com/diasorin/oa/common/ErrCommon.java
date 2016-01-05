package com.diasorin.oa.common;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class ErrCommon {
	private static String exceptionPath = "C:/ExpensesLogs/webAppLog";
	
	public static void errOut(Exception e) {
		try {
//			Calendar cal = Calendar.getInstance();
//			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			String time = formatter.format(cal.getTime());
//			
//			File dir = new File(exceptionPath);
//			if (!dir.exists()) {
//				dir.mkdirs();
//			}
//			
//			File f = new File(exceptionPath + "/"
//					+ time.substring(0,10)
//					+"Exception.log");
//			if(f.exists()) {
//				
//			}
//			else {
//				f.createNewFile();
//			}
//			
//			BufferedWriter output = new BufferedWriter(new FileWriter(f,true));
//
//			StringWriter sw = new StringWriter();
//			e.printStackTrace(new PrintWriter(sw, true));
//			String str = sw.toString();
//			output.write("===== "+time +" =====\r\n");
//			output.write(str);
//			output.close();
			e.printStackTrace();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
}
