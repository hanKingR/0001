package com.mywulianwang.www.tool;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

@SuppressWarnings("all")
public class FileUtils {

	/**
     * 模板替换并返回
     * @param filename	读取的文件模版
     * @param replaceTbl 替换列表
     * @return String	模板替换出来的字符串
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static String repalceStr(String rstr, HashMap replaceTbl) throws FileNotFoundException,
            IOException {
        Set keys = replaceTbl.keySet();
        Iterator iter = keys.iterator();
        while (iter.hasNext()) {
            String key = (String) iter.next();
            String value = (String) replaceTbl.get(key);
            System.out.println("[" + key + "] = " + value);
            rstr = getReplaceString(rstr, key, value);
        }
        return rstr;
    }
    
    /**
     * 说明: 获得替换后的字符串
     * @param sourceString		原字符串
     * @param targetString		被替换的字符串
     * @param replaceString		替换字符串
     * @return String           替换后的字符串
     */
    public static String getReplaceString(String sourceString, String targetString,
                                          String replaceString) {
        String ssString = sourceString;
        String ttString = targetString;
        String rrString = replaceString;
        int lenOfsS = ssString.length();
        int lenOftS = ttString.length();
        int lenOfrS = rrString.length();

        if(lenOfsS <=0 || lenOftS <=0 || lenOfrS <=0 ) {
          return sourceString;
        }
        String llString = ssString;
        int len = llString.length();
        String tempLeft;
        String tempMid;
        String tempRight;
        boolean matches = false;
        char rS = ttString.charAt(0);
        int j = 0;
        for (int i = 0; i < len; i++) {
            if ( (rS == llString.charAt(i)) &&
                ( (i + lenOftS) <= len)) {
                matches = ttString.regionMatches(0, llString, i, lenOftS);
                if (matches) {
                    tempLeft = llString.substring(0, i);
                    tempMid = llString.substring(i, i + lenOftS);
                    tempMid = rrString;
                    tempRight = llString.substring(i + lenOftS, len);
                    llString = tempLeft + tempMid + tempRight;
                    i = i + lenOfrS - 1;
                    len = llString.length();
                }

            }
        }
        return llString;
    }
    
    public static String getIpAddr(javax.servlet.http.HttpServletRequest request) {
		String ipAddress = null;
		ipAddress = request.getRemoteAddr();
		ipAddress = request.getHeader("x-forwarded-for");
		if (ipAddress == null || ipAddress.length() == 0
				|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0
				|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0
				|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();
		}

		// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if (ipAddress != null && ipAddress.length() > 15) {
			if (ipAddress.indexOf(",") > 0) {
				ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
			}
		}
		return ipAddress;
	}
	
}
