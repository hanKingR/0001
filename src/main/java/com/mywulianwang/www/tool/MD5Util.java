package com.mywulianwang.www.tool;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
	
    public static String md5(String text,String charsetname) {
        MessageDigest msgDigest = null;
        String md5Str = "";
        try {
            msgDigest = MessageDigest.getInstance("MD5");
            if(!StringUtil.isEmpty(charsetname)){
            	msgDigest.update(text.getBytes(charsetname));
            }else{
            	msgDigest.update(text.getBytes());
            }
			md5Str = byte2hex(msgDigest.digest());
        }catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("System doesn't support MD5 algorithm.");
        }catch (UnsupportedEncodingException e) {
        	throw new IllegalStateException("System doesn't support charset.");
		}
        return md5Str;
    }
       
    public static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
		}
		return hs;
	}
    
    public static void main(String arg[]){
    	System.out.println(md5("ym2011-05-012011-11-1112345678912345678922",""));
    }

}
