package com.mywulianwang.www.tool;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.util.FileUtil;

import com.winenice.web.util.common.StringUtil;

/**
 * FormatHelper功能主要实现<br/>
 * 1、从String的数据到需要的类型JAVA数据对象的转换<br/>
 * 2、格式化JAVA数据对象为要求的String格式串<br/>
 */
@SuppressWarnings("all")
public class FormatHelper {
	/**
	 * 调试标志
	 */
	public static final boolean m_debug = false;

	/**
	 * 得到系统当前时间
	 */
	public static Timestamp getSystemCurrentTimestamp() {
		java.util.Date date = new java.util.Date();

		java.text.DateFormat df = java.text.DateFormat.getDateTimeInstance();

		debug("Current Datetime is:" + df.format(date));

		return Timestamp.valueOf(df.format(date));
	}

	/**
	 * 格式化时间戳<br/>
	 * 
	 * 日期时间格式：如，2002-02-02 00:00:00
	 * 
	 * @param time
	 *            时间戳
	 * @return 格式化时间字符串
	 */
	public static String formatTimestamp(Timestamp time) {
		try {
			return time.toString().substring(0, 19);
		} catch (Exception e) {
		}

		return null;
	}

	/**
	 * 格式化时间戳<br/>
	 * 
	 * 可返回两种格式，日期格式：如，2002-02-02；<br/>
	 * 日期时间格式：如，2002-02-02 00:00:00
	 * 
	 * @param time
	 *            时间戳
	 * @param bDate
	 *            返回日期格式标志。
	 * @return 格式化时间字符串
	 */
	public static String formatTimestamp(Timestamp time, boolean bDate) {
		try {
			int n = 16;
			if (bDate) {
				n = 10;
			}
			return time.toString().substring(0, n);
		} catch (Exception e) {
		}

		return null;
	}

	/**
	 * 根据时间戳字符串获取Tiestamp对象<br/>
	 * 
	 * 输入：2002-02-02 或者 2002-02-02 00:00:00
	 * 
	 * @param timestamp
	 *            时间格式或者日期格式
	 * @return Timestamp对象
	 */
	public static Timestamp parseTimestamp(String timestamp) {
		try {
			return Timestamp.valueOf(timestamp);
		} catch (Exception e) {
			try {
				return Timestamp.valueOf(timestamp.trim() + " 00:00:00");
			} catch (Exception ee) {
				try {
					return Timestamp.valueOf(timestamp.trim() + ":00");
				} catch (Exception eee) {
					try {
						return Timestamp.valueOf(timestamp.trim() + ":00:00");
					} catch (Exception eeee) {
					}
				}
			}
		}

		return null;
	}

	/**
	 * 获取Tiestamp<br/>
	 * 
	 * 输入：2002-02-02 或者 2002-02-02 00:00:00
	 * 
	 * @param timestamp
	 *            时间格式或者日期格式
	 */
	public static Timestamp parseTimestamp(String timestamp, boolean bStart) {
		if (bStart) {
			return parseTimestamp(timestamp);
		}

		try {
			return Timestamp.valueOf(timestamp);
		} catch (Exception e) {
			try {
				return Timestamp.valueOf(timestamp.trim() + " 23:59:59");
			} catch (Exception ee) {
			}
		}

		return null;
	}

	/**
	 * 使用中文的"是","否"作为bollean型的true和false
	 */
	public static final int BOOLEAN_CHINESE = 1;

	/**
	 * 使用"Yes" "No"作为bollean型的true和false
	 */
	public static final int BOOLEAN_YESNO = 2;

	/**
	 * 使用"Y", "N"作为bollean型的true和false
	 */
	public static final int BOOLEAN_YN = 3;

	/**
	 * 使用"true", "false"作为bollean型的true和false
	 */
	public static final int BOOLEAN_TRUEFALSE = 4;

	public static final int BOOLEAN_SEX = 5;

	/**
	 * 将中英文的字符串转换为boolean型
	 * 
	 * @param strBoolean
	 *            需要转换的字符串
	 * @param flag
	 *            转换类型, 转换类型--转换结果 <br/>
	 *            有四种 BOOLEAN_CHINESE--是/否,BOOLEAN_YESNO--YES/NO,<br/>
	 *            BOOLEAN_YN--Y/N, BOOLEAN_TRUEFALSE--true/false
	 * @return 转换完的结果
	 */
	public static boolean parseBoolean(String strBoolean, int flag) {
		boolean b = false;

		switch (flag) {
		case BOOLEAN_CHINESE:

			if (strBoolean.trim().equalsIgnoreCase("是")) {
				b = true;
			}
			break;
		case BOOLEAN_YESNO:
			if (strBoolean.trim().equalsIgnoreCase("YES")) {
				b = true;
			}
			break;
		case BOOLEAN_YN:
			if (strBoolean.trim().equalsIgnoreCase("Y")) {
				b = true;
			}
			break;
		case BOOLEAN_TRUEFALSE:
			if (strBoolean.trim().equalsIgnoreCase("true")) {
				b = true;
			}
			break;
		}

		return b;
	}

	/**
	 * 将boolean型转换为中英文的字符串
	 * 
	 * @param b
	 *            需要转换的boolean型
	 * @param flag
	 *            转换类型, 转换类型--转换结果 <br/>
	 *            有四种<br/>
	 *            BOOLEAN_CHINESE--是/否,BOOLEAN_YESNO--YES/NO,<br/>
	 *            BOOLEAN_YN--Y/N, BOOLEAN_TRUEFALSE--true/false
	 * @return 转换完的结果
	 */
	public static String formatBoolean(boolean b, int flag) {
		String strBoolean = null;

		switch (flag) {
		case BOOLEAN_CHINESE:
			if (b) {
				strBoolean = "是";
			} else {
				strBoolean = "否";
			}
			break;
		case BOOLEAN_YESNO:
			if (b) {
				strBoolean = "YES";
			} else {
				strBoolean = "NO";
			}
			break;
		case BOOLEAN_YN:
			if (b) {
				strBoolean = "Y";
			} else {
				strBoolean = "N";
			}
			break;
		case BOOLEAN_TRUEFALSE:
			if (b) {
				strBoolean = "true";
			} else {
				strBoolean = "false";
			}
			break;

		case BOOLEAN_SEX:
			if (b) {
				strBoolean = "男";
			} else {
				strBoolean = "女";
			}
			break;
		}

		return strBoolean;
	}

	/**
	 * 格式化BigDecimal
	 * 
	 * @param bigDecimal
	 * @return BigDecimal对象转换成的字符串
	 */
	public static String formatBigDecimal(BigDecimal decimal) {
		return decimal.toString();
	}

	/**
	 * 获取BigDecimal
	 * 
	 * @param bigDecimal
	 */
	public static BigDecimal parseBigDecimal(String bigDecimal) {
		try {
			return new BigDecimal(bigDecimal);
		} catch (Exception e) {
		}

		return null;
	}

	/**
	 * 打印调试信息
	 */
	public static void debug(String string) {
		if (m_debug) {
			System.out.println(string);
		}
	}

	/**
	 * 打印调试信息
	 */
	public static void debug(Object o) {
		if (m_debug) {
			System.out.println(o);
		}
	}

	/**
	 * 格式化打折率
	 * 
	 * @param name
	 *            String 打折率
	 * 
	 * @return String 打折标志
	 */
	public static String getDiscountFlag(String discount) {

		// 变汉语数字,例如"九",为阿拉伯数字9
		String qDiscount = qualifyDiscount(discount);
		// 删除非数字汉字,例如"折"
		StringBuffer discountSB = new StringBuffer(qDiscount);

		for (int i = 0; i < discountSB.length(); i++) {
			char c = discountSB.charAt(i);
			if (c < '1' || c > '9') {
				discountSB = discountSB.deleteCharAt(i);
				i--;
			}
		}

		return discountSB.toString();
	}

	/**
	 * 变汉语数字,例如"九",为阿拉伯数字9
	 * 
	 * @param name
	 *            String 打折
	 * 
	 * @return String 打折标志
	 */
	private static String qualifyDiscount(String discount) {

		if (discount.indexOf("九") != -1) {
			discount = discount.replace('九', '9');
		}
		if (discount.indexOf("八") != -1) {
			discount = discount.replace('八', '8');
		}
		if (discount.indexOf("七") != -1) {
			discount = discount.replace('七', '7');
		}
		if (discount.indexOf("六") != -1) {
			discount = discount.replace('六', '6');
		}
		if (discount.indexOf("五") != -1) {
			discount = discount.replace('五', '5');
		}
		if (discount.indexOf("四") != -1) {
			discount = discount.replace('四', '4');
		}
		if (discount.indexOf("三") != -1) {
			discount = discount.replace('三', '3');
		}
		if (discount.indexOf("二") != -1) {
			discount = discount.replace('二', '2');
		}
		if (discount.indexOf("一") != -1) {
			discount = discount.replace('一', '1');
		}
		return discount;
	}

	/**
	 * 查询操作所使用的关键字中的%、[、_进行转义操作
	 * 
	 * @param str
	 *            字符串多为查询的关键字
	 * @return 转义完的字符串
	 */
	// public static String sqlUnEscape(String str) {
	// if (StringUtil.isEmpty(str)) {
	// return str;
	// }
	//
	// str = StringReplace.getReplaceString(str, "!'", "'");
	// str = StringReplace.getReplaceString(str, "!%", "%");
	// str = StringReplace.getReplaceString(str, "!!", "!");
	// str = StringReplace.getReplaceString(str, "![", "[");
	// str = StringReplace.getReplaceString(str, "!_", "_");
	// str = StringReplace.getReplaceString(str, "!^", "^");
	// str = StringReplace.getReplaceString(str, "\r", "");
	// str = StringReplace.getReplaceString(str, "/r", "");
	// return str;
	// }
	/**
	 * 查询操作所使用的关键字中的%、[、_进行转义操作
	 * 
	 * @param str
	 *            字符串多为查询的关键字
	 * @return 转义完的字符串
	 */
	public static String sqlEscape(String str) {
		String escapeStr = "";

		if (str != null && str.trim().length() > 0) {
			for (;;) {
				int index = str.indexOf("!");
				if (index == -1) {
					escapeStr += str;
					break;
				}
				escapeStr += str.substring(0, index) + "!!";
				str = str.substring(index + 1, str.length());
			}
			str = escapeStr;
			escapeStr = "";

			for (;;) {
				int index = str.indexOf("%");
				if (index == -1) {
					escapeStr += str;
					break;
				}
				escapeStr += str.substring(0, index) + "!%";
				str = str.substring(index + 1, str.length());
			}

			str = escapeStr;
			escapeStr = "";
			for (;;) {
				int index = str.indexOf("[");
				if (index == -1) {
					escapeStr += str;
					break;
				}
				escapeStr += str.substring(0, index) + "![";
				str = str.substring(index + 1, str.length());
			}

			str = escapeStr;
			escapeStr = "";

			for (;;) {
				int index = str.indexOf("_");
				if (index == -1) {
					escapeStr += str;
					break;
				}
				escapeStr += str.substring(0, index) + "!_";
				str = str.substring(index + 1, str.length());
			}

			str = escapeStr;
			escapeStr = "";

			for (;;) {
				int index = str.indexOf("'");
				if (index == -1) {
					escapeStr += str;
					break;
				}
				escapeStr += str.substring(0, index) + "!'";
				str = str.substring(index + 1, str.length());
			}

			str = escapeStr;
			escapeStr = "";

			for (;;) {
				int index = str.indexOf("^");
				if (index == -1) {
					escapeStr += str;
					break;
				}
				escapeStr += str.substring(0, index) + "!^";
				str = str.substring(index + 1, str.length());
			}
		}

		return escapeStr;
	}

	public static double getDouble(double data, int scale) {
		return new BigDecimal(data).setScale(scale, BigDecimal.ROUND_HALF_UP)
				.doubleValue();
	}

	/**
	 * 获取完整的URL
	 * 
	 * @param request
	 * @return
	 */
	public static String getRequestURL(HttpServletRequest request) {
		if (request == null) {
			return "";
		}

		String url = request.getRequestURL().toString();

		String queryString = request.getQueryString();

		if (!StringUtil.isEmpty(queryString)) {
			url = url + "?" + queryString;
		}

		return url;
	}

	/*
	 * public static void main(String[] args) { Timestamp timestamp =
	 * Timestamp.valueOf("2002-2-12 00:00:00"); Timestamp t =
	 * FormatHelper.parseTimestamp(" 2002-2-12 "); FormatHelper.debug("Current
	 * Time:");FormatHelper.debug(FormatHelper.formatTimestamp(FormatHelper.
	 * getSystemCurrentTimestamp())); java.math.BigDecimal g = new
	 * BigDecimal("12.000000"); FormatHelper.debug(g); }
	 */

	/**
	 * 按照指定长度截取中英文字符串
	 * 
	 * @param str
	 *            需要截取的字符串
	 * @param size
	 *            指定长度，为字节的长度，按照中文2个英文数字1个计算
	 * @return
	 */
	public static String substring(String str, int size) {

		if (str == null || str.length() < 1 || size <= 2) {
			return str;
		}

		if (str.length() > 1 && size == 2) {
			int i = 0;
		}
		String returnStr = "";

		for (int i = 0, j = 0; j < size && j < str.getBytes().length;) {
			String ch = str.charAt(i++) + "";

			if (returnStr.getBytes().length == size - 1
					&& ch.getBytes().length > 1) {
				returnStr = returnStr + ".";
			} else {
				returnStr = returnStr + ch;
			}

			j = j + ch.getBytes().length;
		}

		return returnStr;
	}

	/**
	 * 按照指定长度截取中英文字符串
	 * 
	 * @param str
	 *            需要截取的字符串
	 * @param size
	 *            指定长度，为字节的长度，按照中文2个英文数字1个计算
	 * @return
	 */
	public static String substring2(String str, int size) {

		if (str == null || str.length() < 1 || size <= 2) {
			return str;
		}

		if (str.length() > 1 && size == 2) {
			int i = 0;
		}
		String returnStr = "";

		for (int i = 0, j = 0; j < size && j < str.getBytes().length;) {
			String ch = str.charAt(i++) + "";

			if (returnStr.getBytes().length == size - 1
					&& ch.getBytes().length > 1) {
				returnStr = returnStr + ".";
			} else {
				returnStr = returnStr + ch;
			}

			j = j + ch.getBytes().length;
		}

		return returnStr;
	}

	/**
	 * 按照指定长度截取中英文字符串
	 * 
	 * @param str
	 *            需要截取的字符串
	 * @param size
	 *            指定长度，为字节的长度，按照中文2个英文数字1个计算
	 * @return
	 */
	public static String substring(String str, int size, boolean isadd) {
		if (!isadd || (str != null && str.getBytes().length >= size)) {
			return substring(str, size);
		}

		int ensize = size - str.getBytes().length;

		for (int i = 0; i < ensize; i = i + 2) {
			str = str + "";
		}

		return str;
	}

	public static String filterHtml(String str) {

		if (StringUtil.isEmpty(str)) {
			return "";
		}

		Pattern pattern = Pattern
				.compile("<[^<|>]*>", Pattern.CASE_INSENSITIVE);
		Pattern pattern1 = Pattern.compile("&nbsp;", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(str);

		String returnStr = matcher.replaceAll("");

		Matcher matcher1 = pattern.matcher(returnStr);
		return returnStr;
	}

	public static List filterHtmlImgPath(String htmlStr) {
		if (StringUtil.isEmpty(htmlStr)) {
			return null;
		}
		String regx = "userfiles/product/img/[0-9]{8}/editor/\\w*\\.(jpg|jpeg|png|gif)";
		Pattern pattern = Pattern.compile(regx, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(htmlStr);
		List returnStr = new ArrayList();

		while (matcher.find()) {
			int start = matcher.start();
			int end = matcher.end();
			returnStr.add(htmlStr.substring(start, end));
		}
		return returnStr;
	}

	public static void main(String args[]) {
		System.out.println("ddddddddddddddddddddddddddddddd");
		List list = filterHtmlLink("<A  href='http://www.aebiz.net/b.gif '  >bb</A><img src=\"http://www.bjshop.net/b.gif \">");
		List list2 = filterHtmlImgPath("http://localhost:8000/newcomshop/userfiles/product/img/20070813/editor/11870388459844028801d1460fd3f011461075b2600020.gif");
		// if (list != null && list.size() > 0) {
		// for (int i = 0; i < list.size(); i++) {
		// String abc = (String) list.get(i);
		// System.out.println("path = " + abc);
		// }
		// }
		System.out.println("is in");
		System.out.println("is in");
		System.out.println("is in");
		System.out.println("is in");
		System.out.println("is in");
		System.out.println("is in");
		System.out.println("is in");
		if (list2 != null && list2.size() > 0) {
			for (int i = 0; i < list2.size(); i++) {
				String abc = (String) list2.get(i);
				System.out.println("path = " + abc);
			}
		}
		String src = "/testnewshop/userfiles/product/img/20071226/editor/1198658496718null0.jpg";
		List ll = filterHtmlImgPath(src);
		if (ll != null && ll.size() > 0) {
			System.out.println("src=" + ((String) ll.get(0)).toString());
		}

	}

	public static List filterHtmlImgPathStartWithHttp(String htmlStr) {
		if (StringUtil.isEmpty(htmlStr)) {
			return null;
		}

		String reg = "src=('|\"|.)http://";
		reg = reg + "(.*?)\\.(jpg|jpeg|gif|png|bmp)(.*?)('|\")";

		System.out.println(reg);
		Pattern pattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);

		Matcher matcher = pattern.matcher(htmlStr);
		List returnStr = new ArrayList();

		while (matcher.find()) {
			int start = matcher.start();
			int end = matcher.end();
			returnStr.add(htmlStr.substring(start, end));
		}
		return returnStr;
	}

	public static List filterHtmlLink(String htmlStr) {
		if (StringUtil.isEmpty(htmlStr)) {
			return null;
		}
		String regx = "<a (.*?)>(.*?)</a>";
		Pattern pattern = Pattern.compile(regx, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(htmlStr);

		List returnStr = new ArrayList();
		while (matcher.find()) {
			int start = matcher.start();
			int end = matcher.end();
			returnStr.add(htmlStr.substring(start, end));
		}

		return returnStr;
	}

	public static boolean filterHtmlIsExistedLink(String htmlStr,
			String serverName) {
		if (StringUtil.isEmpty(htmlStr)) {
			return false;
		}
		String regx = "href=(\"|')" + serverName + "[/*|/*#*|\\*|\\*#*]\"";
		Pattern pattern = Pattern.compile(regx, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(htmlStr);
		while (matcher.find()) {
			return true;
		}
		return false;
	}

	public static String escape(String src) {
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

	private final static byte[] val = { 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x00, 0x01,
			0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F,
			0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F, 0x3F };

	public static String unescape(String s) {
		StringBuffer sbuf = new StringBuffer();
		int i = 0;
		int len = s.length();
		while (i < len) {
			int ch = s.charAt(i);
			if ('A' <= ch && ch <= 'Z') {
				sbuf.append((char) ch);
			} else if ('a' <= ch && ch <= 'z') {
				sbuf.append((char) ch);
			} else if ('0' <= ch && ch <= '9') {
				sbuf.append((char) ch);
			} else if (ch == '-' || ch == '_' || ch == '.' || ch == '!'
					|| ch == '~' || ch == '*' || ch == '\'' || ch == '('
					|| ch == ')') {
				sbuf.append((char) ch);
			} else if (ch == '%') {
				int cint = 0;
				if ('u' != s.charAt(i + 1)) {
					cint = (cint << 4) | val[s.charAt(i + 1)];
					cint = (cint << 4) | val[s.charAt(i + 2)];
					i += 2;
				} else {
					cint = (cint << 4) | val[s.charAt(i + 2)];
					cint = (cint << 4) | val[s.charAt(i + 3)];
					cint = (cint << 4) | val[s.charAt(i + 4)];
					cint = (cint << 4) | val[s.charAt(i + 5)];
					i += 5;
				}
				sbuf.append((char) cint);
			} else {
				sbuf.append((char) ch);
			}
			i++;
		}
		return sbuf.toString();
	}

	// public static String trimParam(ActionRequest request, String paramName,
	// boolean isfieldNames) {
	// return trimParam(request, paramName, "", "", "", isfieldNames);
	// }

	// public static String trimParam(ActionRequest request, String paramName,
	// String paramName2, boolean isfieldNames) {
	// return trimParam(request, paramName, paramName2, "", "", isfieldNames);
	// }

	// public static String trimParam(ActionRequest request, String paramName,
	// String paramName2, String paramName3, String paramName4,
	// boolean isfieldNames) {
	//
	// // String requestPageUrl = "";
	//
	// java.util.Enumeration names = request.getRequest().getParameterNames();
	//
	// int i = 0;
	//
	// // boolean existed = false;
	// String fieldNamesValue = request.getParameter("fieldNames");
	//
	// String resultValues = "";
	// if (isfieldNames) {
	// if (!StringUtil.isEmpty(fieldNamesValue)) {
	// StringTokenizer keys = new StringTokenizer(fieldNamesValue, ".");
	//
	// int k = 0;
	// while (keys.hasMoreTokens()) {
	// String key = keys.nextToken();
	// if (!StringUtil.isEmpty(key)) {
	// String value = request.getParameter(key);
	//
	// if (!key.equals(paramName) || !key.equals(paramName2)
	// || !key.equals(paramName3)) {
	// if (StringUtil.isEmpty(resultValues)) {
	// resultValues = resultValues + key;
	// } else {
	// resultValues = resultValues + "." + key;
	// }
	// }
	// }
	//
	// k++;
	// }
	// }
	// }
	//
	// String url = "";
	// url = request.getRequest().getContextPath();
	// url = url + request.getRequest().getServletPath();
	// // url = request.getProductCategoryUrl("", request.getChannelId(),
	// request
	// // .getProductCategoryId(), 1);
	// //
	// // if (url.indexOf("pc") == -1) {
	// // url = request.getProductCategoryUrl("", "index", "303", 1);
	// // }
	// // if(!StringUtil.isEmpty(request.getProductCategoryId())){
	// // ProductCategory pc =
	// ProductHelper.getProductCategoryBySid(request.getProductCategoryId());
	// // if(pc != null && "1".equals(pc.getTemplateId())){
	// // url = request.getProductCategoryUrl("", "index", "303", 1);
	// // }
	// // }
	// if (names != null) {
	//
	// while (names.hasMoreElements()) {
	// String name = (String) names.nextElement();
	//
	// if (i == 0) {
	// url = url + "?";
	// } else {
	// url = url + "&";
	// }
	//
	// i++;
	//
	// String value = request.getParameter(name);
	// if (value == null) {
	// value = "";
	// }
	//
	// if (name.equals("fieldNames")) {
	// if (isfieldNames) {
	// if (!StringUtil.isEmpty(resultValues)) {
	// url = url + name + "=" + resultValues;
	// }
	// } else {
	// if (!StringUtil.isEmpty(fieldNamesValue)) {
	// url = url + name + "=" + fieldNamesValue;
	// }
	// }
	// } else if (paramName.equals(name) || paramName2.equals(name)
	// || paramName3.equals(name) || paramName4.equals(name)) {
	// // url = url + name + "=" + value;
	// } else if (paramName.equals("price")
	// && (name.equals("startPrice") || name
	// .equals("endPrice"))) {
	// } else {
	// url = url + name + "=" + value;
	// }
	// }
	// }
	//
	// return url;
	// }

	// public static String addParam(ActionRequest request, String paramName,
	// String paramValue, boolean isfieldNames) {
	//
	// String requestPageUrl = "";
	//
	// java.util.Enumeration names = request.getRequest().getParameterNames();
	//
	// int i = 0;
	//
	// boolean existed = false;
	// String fieldNamesValue = request.getParameter("fieldNames");
	//
	// if (isfieldNames) {
	//
	// if (!StringUtil.isEmpty(fieldNamesValue)) {
	// StringTokenizer keys = new StringTokenizer(fieldNamesValue, ".");
	// while (keys.hasMoreTokens()) {
	// String key = keys.nextToken();
	// if (!StringUtil.isEmpty(key)) {
	// String value = request.getParameter(key);
	//
	// if (key.equals(paramName)) {
	// existed = true;
	// }
	// }
	// }
	// }
	//
	// if (!existed) {
	// if (!StringUtil.isEmpty(fieldNamesValue)) {
	// fieldNamesValue = fieldNamesValue + "." + paramName;
	// } else {
	// fieldNamesValue = paramName;
	// }
	// }
	// }
	//
	// String url = "";
	// // url = request.getRequest().getRequestURI();
	//
	// url = request.getProductCategoryUrl("", request.getChannelId(), request
	// .getProductCategoryId(), 1);
	// if (url.indexOf("pc") == -1) {
	// url = request.getProductCategoryUrl("", request.getChannelId(),
	// "303", 1);
	// }
	// // if(!StringUtil.isEmpty(request.getProductCategoryId())){
	// // ProductCategory pc =
	// ProductHelper.getProductCategoryBySid(request.getProductCategoryId());
	// // if(pc != null && "1".equals(pc.getTemplateId())){
	// // url = request.getProductCategoryUrl("", request.getChannelId(),
	// // "303", 1);
	// // }
	// // }
	// boolean fieldNamesExisted = false;
	// boolean fieldValueExisted = false;
	// if (names != null) {
	//
	// while (names.hasMoreElements()) {
	// String name = (String) names.nextElement();
	//
	// if (i == 0) {
	// url = url + "?";
	// } else {
	// url = url + "&";
	// }
	//
	// i++;
	//
	// String value = request.getParameter(name);
	// if (value == null) {
	// value = "";
	// }
	//
	// if (name.equals("fieldNames")) {
	// fieldNamesExisted = true;
	// url = url + name + "=" + fieldNamesValue;
	// } else if (paramName.equals(name)) {
	// fieldValueExisted = true;
	// url = url + name + "=" + paramValue;
	// } else {
	// url = url + name + "=" + value;
	// }
	// }
	// }
	//
	// if (!fieldNamesExisted && !StringUtil.isEmpty(fieldNamesValue)) {
	// if (url.indexOf("?") == -1) {
	// url = url + "?";
	// } else {
	// url = url + "&";
	// }
	//
	// url = url + "fieldNames=" + fieldNamesValue;
	// }
	//
	// if (!fieldValueExisted) {
	// if (url.indexOf("?") == -1) {
	// url = url + "?";
	// } else {
	// url = url + "&";
	// }
	//
	// url = url + paramName + "=" + paramValue;
	// }
	//
	// return url;
	// }

	// public static String replaceParam(ActionRequest request, String
	// paramName,
	// String paramValue, String paramName2, String paramValue2) {
	//
	// String requestPageUrl = "";
	//
	// java.util.Enumeration names = request.getRequest().getParameterNames();
	//
	// int i = 0;
	//
	// String url = "";
	// // url = request.getRequest().getRequestURI();
	// url = request.getProductCategoryUrl("", request.getChannelId(), request
	// .getProductCategoryId(), 1);
	//		
	// if (url.indexOf("pc") == -1) {
	// url = request.getProductCategoryUrl("", "index", "303", 1);
	// }
	// // if(!StringUtil.isEmpty(request.getProductCategoryId())){
	// // ProductCategory pc =
	// ProductHelper.getProductCategoryBySid(request.getProductCategoryId());
	// // if(pc != null && "1".equals(pc.getTemplateId())){
	// // url = request.getProductCategoryUrl("", "index", "303", 1);
	// // }
	// // }
	// boolean fieldValueExisted = false;
	// boolean fieldValueExisted2 = false;
	// if (names != null) {
	//
	// while (names.hasMoreElements()) {
	// String name = (String) names.nextElement();
	//
	// if (i == 0) {
	// url = url + "?";
	// } else {
	// url = url + "&";
	// }
	//
	// i++;
	//
	// String value = request.getParameter(name);
	// if (value == null) {
	// value = "";
	// }
	//
	// if (paramName.equals(name)) {
	// fieldValueExisted = true;
	// url = url + name + "=" + paramValue;
	// } else if (paramName2.equals(name)) {
	// fieldValueExisted2 = true;
	// url = url + name + "=" + paramValue2;
	// } else {
	// url = url + name + "=" + value;
	// }
	// }
	// }
	//
	// if (!fieldValueExisted) {
	// if (url.indexOf("?") == -1) {
	// url = url + "?";
	// } else {
	// url = url + "&";
	// }
	//
	// url = url + paramName + "=" + paramValue;
	// }
	//
	// if (!fieldValueExisted2) {
	// if (url.indexOf("?") == -1) {
	// url = url + "?";
	// } else {
	// url = url + "&";
	// }
	//
	// url = url + paramName2 + "=" + paramValue2;
	// }
	//
	// return url;
	// }
	// public static String replaceImgPathRoot(String htmlStr) {
	// try {
	//
	// if (StringUtil.isEmpty(htmlStr)) {
	// return "";
	// }
	//
	// String reg = "src=('|\"|.)[^(http|HTTP)]*";
	// reg = reg
	// + "(.*?)userfiles/product/img/[0-9]{8}/editor/\\w*\\.(jpg|jpeg|png|gif)";
	//			
	// String reg1 = FileUtil.getImageWebRoot("");
	// String returnStr = "";
	//
	// List picurlPath = filterHtmlImgPath2(htmlStr);
	// if (picurlPath != null && picurlPath.size() > 0) {
	// for (int i = 0; i < picurlPath.size(); i++) {
	// reg = "src=('|\"|.)http://[^ ]*";
	// reg = reg + (String) picurlPath.get(i);
	// htmlStr = htmlStr.replaceAll(reg, "src=\"" + reg1
	// + (String) picurlPath.get(i));
	// }
	// }
	// if (!StringUtil.isEmpty(returnStr)) {
	// return returnStr;
	// } else {
	// return htmlStr;
	// }
	//
	// } catch (Exception e) {
	// e.printStackTrace();
	// // TODO: handle exception
	// }
	//
	// return htmlStr;
	// }
	public static List filterHtmlImgPath2(String htmlStr) {
		if (StringUtil.isEmpty(htmlStr)) {
			return null;
		}
		String regx = "/userfiles/product/img/[0-9]{8}/editor/\\w*\\.(jpg|jpeg|png|gif)";
		Pattern pattern = Pattern.compile(regx, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(htmlStr);
		List returnStr = new ArrayList();

		while (matcher.find()) {
			int start = matcher.start();
			int end = matcher.end();
			returnStr.add(htmlStr.substring(start, end));
		}
		return returnStr;
	}
	
	public static String replaceImgPathRoot(String htmlStr) {
		try {

			if (StringUtil.isEmpty(htmlStr)) {
				return "";
			}

			String reg = "src=('|\"|.)[^(http|HTTP)]*";
			reg = reg
					+ "(.*?)userfiles/product/img/[0-9]{8}/editor/\\w*\\.(jpg|jpeg|png|gif)";
			
			String returnStr = "";

			List picurlPath = filterHtmlImgPath2(htmlStr);
			if (picurlPath != null && picurlPath.size() > 0) {
				for (int i = 0; i < picurlPath.size(); i++) {
					reg = "src=('|\"|.)http://[^ ]*";
					reg = reg + (String) picurlPath.get(i);
					htmlStr = htmlStr.replaceAll(reg, "src=\"" + (String) picurlPath.get(i));
				}
			}
			if (!StringUtil.isEmpty(returnStr)) {
				return returnStr;
			} else {
				return htmlStr;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return htmlStr;
	}
}