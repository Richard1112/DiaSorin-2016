package com.diasorin.oa.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PassWordParseInMD5 {
    public static String Md5(String plainText) { 
        StringBuffer buf = new StringBuffer(""); 
        try { 
            MessageDigest md = MessageDigest.getInstance("MD5"); 
            md.update(plainText.getBytes()); 
            byte b[] = md.digest(); 
            int i; 
            for (int offset = 0; offset < b.length; offset++) { 
                i = b[offset]; 
                if(i<0) i+= 256; 
                if(i<16) 
                buf.append("0"); 
                buf.append(Integer.toHexString(i)); 
            }
        } catch (NoSuchAlgorithmException e) { 
            e.printStackTrace(); 
        }
        return buf.toString();
    } 
    
    @SuppressWarnings("static-access")
	public static void main(String[] args) {
    	PassWordParseInMD5 passWordParseInMD5 = new PassWordParseInMD5();
    	System.out.println(passWordParseInMD5.Md5("test"));
    }
}
