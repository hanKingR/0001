package com.mywulianwang.www.tool;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;


/**
 * String处理的一些方法。
 * 
 * @author Zhiwei Wang(topkaiser@gmail.com)
 * @since 2010-01-06
 */

@SuppressWarnings("all")
public class StringUtil {

	private static final Logger logger = Logger.getLogger(StringUtil.class);
	public static final Pattern BLANK_HTML_PATTERN = Pattern.compile("^\\s*(<br\\s?/?>\\s*)*$",
			Pattern.CASE_INSENSITIVE);
	public static final Pattern EMAIL_PATTERN = Pattern.compile("(\\w+)@(\\w+\\.)(\\w+)(\\.\\w+)*");

	/**
     * <p>Checks if a String is empty ("") or null.</p>
     *
     * <pre>
     * StringUtils.isEmpty(null)      = true
     * StringUtils.isEmpty("")        = true
     * StringUtils.isEmpty(" ")       = false
     * StringUtils.isEmpty("bob")     = false
     * StringUtils.isEmpty("  bob  ") = false
     * </pre>
     *
     * <p>NOTE: This method changed in Lang version 2.0.
     * It no longer trims the String.
     * That functionality is available in isBlank().</p>
     *
     * @param str  the String to check, may be null
     * @return <code>true</code> if the String is empty or null
     */
	public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

	
	/**
	 * 截字
	 * @param str 需要节字的字符串
	 * @param lower 起始位置
	 * @param upper 结束位置
	 * @param appendToEnd 结尾显示的字符
	 * @return 处理后的字符串
	 */
	public static String truncateNicely(String str, int lower, int upper,
			String appendToEnd) {
		if (strIsNull(str)) {
			return "";
		}
		str = str.trim();
		if (upper < lower){
			upper = lower;
		}
		if (str.length() > upper) {
			int loc = str.lastIndexOf(' ', upper);
			if (loc >= lower){
				str = str.substring(0, loc);
			}else{
				str = str.substring(lower, upper);
			}
			str = str + appendToEnd;
		}
		return str;
	}
	
	/**
	 * 判断字符串是否为空
	 * @param str 要判断的字符
	 * @return true:为空 	false:不为空
	 */
	public static boolean strIsNull(String str) {
		if (null == str) {
			return true;
		}
		if (str.trim().equals("")) {
			return true;
		}
		return false;
	}
	
	/**
	  * 用JAVA正则表达式来校验手机号码
	  * @param moblie
	  * @return
	  */
	 public static boolean checkMoblie(String moblie){
		if(StringUtil.isEmpty(moblie)){
			return false;
		}
		if(moblie.indexOf("@") != -1 || moblie.indexOf(".") != -1 || moblie.length() != 11){
			return false;
		}
		Pattern p1 = Pattern.compile("^((\\+{0,1}86){0,1})1[0-9]{10}");
		Matcher m1 = p1.matcher(moblie);
		if (m1.matches()) {
			return true;
		} else {
			return false;
		}
	}
		/**
		 *   用java正则 表达式来校验 固定电话的判断
		 * @param homePhone
		 * @return
		 */
	 public static boolean checkHomePhone(String homePhone){
		 if(StringUtil.isEmpty(homePhone)){
			 return false;
		 }
		if(homePhone.indexOf("@") != -1 || homePhone.indexOf(".") != -1 || homePhone.length() != 11){
				return false;
	    }
		 Pattern p1 = Pattern.compile("([0-9]{2})+-([0-9]{4})+-([0-9]{4})+");
		 Matcher m1 = p1.matcher(homePhone);
			if (m1.matches()) {
				return true;
			} else {
				return false;
			}
	 }
	 
	 
	/**
	 * 用JAVA正则表达式来校验Email
	 * @param email
	 * @return
	 */
	 public static boolean checkEmail(String email) {
		if(StringUtil.isEmpty(email)){
			return false;
		}
		if(email.indexOf("@") == -1 && email.indexOf(".") == -1){
			return false;
		}
		String check = "^([a-z0-9A-Z_-]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		Pattern regex = Pattern.compile(check);
		Matcher matcher = regex.matcher(email);
		boolean isMatched = matcher.matches();
		if (isMatched) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 对字符串作md5处理
	 * 
	 * @param input
	 * @return 32个字符的md5摘要
	 */
	public static String md5(String input) {
		com.twmacinta.util.MD5 md5 = new com.twmacinta.util.MD5();
		try {
			md5.Update(input, null);
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		}
		return md5.asHex();
	}

	/**
	 * 用utf-8方式编码字符串
	 * 
	 * @param input
	 * @return
	 */
	public static String encodeUTF8(String input) {
		String output = "";
		try {
			output = URLEncoder.encode(input, Constants.ENCODING_UTF8);
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		}
		return output;
	}

	/**
	 * 用utf-8方式解码字符串
	 * 
	 * @param input
	 * @return
	 */
	public static String decodeUTF8(String input) {
		String output = "";
		try {
			output = URLDecoder.decode(input, Constants.ENCODING_UTF8);
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
		}
		return output;
	}

	/**
	 * 将字符串中的尖括号替换掉，防止其以HTML形式展示或者扰乱页面布局
	 * 
	 * @param input
	 * @return
	 */
	public static String escapeHtml(String input) {
		return input.replace("<", "&lt;").replace(">", "&gt;");
	}

	/**
	 * 去掉html标签，截断
	 * 
	 * @param html
	 * @param limit
	 * @return
	 */
	public static String abbreviateHtml(String html, int limit) {
		int strLen;
		if (html == null || (strLen = html.length()) == 0) {
			return Constants.EMPTY_STRING;
		}
		StringBuilder sb = new StringBuilder(limit + 3);
		int i;
		int abbrevLen = 0;
		boolean tagOpen = false;
		for (i = 0; i < strLen && abbrevLen < limit; i++) {
			char c = html.charAt(i);
			if (tagOpen) {
				if (c == '>') {
					tagOpen = false;
				}
			} else {
				if (c == '<') {
					tagOpen = true;
				} else if (c != '>') {
					sb.append(c);
					abbrevLen += 1;
				}
			}
		}

		if (i < strLen) {
			sb.append("...");
		}

		return sb.toString();
	}

	/**
	 * 判断html是否为空（只有空格、<br />）
	 * 
	 * @param html
	 * @return
	 */
	public static boolean isHtmlBlank(String html) {
		Matcher m = BLANK_HTML_PATTERN.matcher(html);
		return m.matches();
	}
	
	/**
	 * 换行符替换为<br />
	 * @param input
	 * @return
	 */
	public static String convertNewLine(String input) {
		return input.replace("\r\n", "<br />").replace("\n", "<br />");
	}
	
	   /**
	 * 判断是否为整数
	 * 
	 * @param str传入的字符串
	 * @return 是整数返回true,否则返回false
	 */  
   public static boolean isInteger(String str) {  
     Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");  
     return pattern.matcher(str).matches();  
   }  
   
   /**
	 * 判断是否为浮点数，包括double和float
	 * 
	 * @param str传入的字符串
	 * @return 是浮点数返回true,否则返回false
	 */  
   public static boolean isDouble(String str) {  
     Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");  
     return pattern.matcher(str).matches();  
   }
   
   /**
	 * 判断输入的字符串是否符合Email样式.
	 * 
	 * @param str传入的字符串
	 * @return 是Email样式返回true,否则返回false
	 */  
   public static boolean isEmail(String str) {  
     Pattern pattern = Pattern.compile("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");  
     return pattern.matcher(str).matches();  
   }
   
   /**
	 * 判断输入的字符串是否为纯汉字
	 * 
	 * @param str传入的字符窜
	 * @return 如果是纯汉字返回true,否则返回false
	 */  
   public static boolean isChinese(String str) {  
     Pattern pattern = Pattern.compile("[\u0391-\uFFE5]+$");  
     return pattern.matcher(str).matches();  
   }
   
   /**
	 * 是否为空白,包括null和""
	 * 
	 * @param str
	 * @return
	 */
   public static boolean isBlank(String str) {  
     return str == null || str.trim().length() == 0;  
   }
   
   public static List reversalList(List srcList){
	   List goalList = new ArrayList();
	   int size = srcList.size();
	   if(size > 0){
		   for(int i = 0; i < size; i++){
			   goalList.add(srcList.get(size - 1 - i));
		   }
	   }
	   return goalList;
   }
   
   /**
	 * 地址栏escape加密
	 * @param src URL
	 * @return
	 */
	public static String escape(String src) {
		if(src == null || src.equals("")){
			return null;
		}
		int i;
		char j;
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length() * 6);
		for (i = 0; i < src.length(); i++) {
			j = src.charAt(i);
			if (Character.isDigit(j) || Character.isLowerCase(j)
					|| Character.isUpperCase(j))
				tmp.append(j);
			else if (j < 256) {
				tmp.append("%");
				if (j < 16)
					tmp.append("0");
				tmp.append(Integer.toString(j, 16));
			} else {
				tmp.append("%u");
				tmp.append(Integer.toString(j, 16));
			}
		}
		return tmp.toString();
	}

	/**
	 * 地址栏unescape解密
	 * @param src
	 * @return
	 */
	public static String unescape(String src) {
		if(src == null || src.equals("")){
			return null;
		}
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length());
		int lastPos = 0, pos = 0;
		char ch;
		while (lastPos < src.length()) {
			pos = src.indexOf("%", lastPos);
			if (pos == lastPos) {
				if (src.charAt(pos + 1) == 'u') {
					ch = (char) Integer.parseInt(src
							.substring(pos + 2, pos + 6), 16);
					tmp.append(ch);
					lastPos = pos + 6;
				} else {
					ch = (char) Integer.parseInt(src
							.substring(pos + 1, pos + 3), 16);
					tmp.append(ch);
					lastPos = pos + 3;
				}
			} else {
				if (pos == -1) {
					tmp.append(src.substring(lastPos));
					lastPos = src.length();
				} else {
					tmp.append(src.substring(lastPos, pos));
					lastPos = pos;
				}
			}
		}
		return tmp.toString();
	}
	public static String getPassword(int length) {
		String[] str = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		StringBuffer buffer = new StringBuffer("");
		Random r = new Random();
		for (int i = 0; i < length; i++) {
			buffer.append(str[r.nextInt(str.length)]);
		}
		return buffer.toString();
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

}
