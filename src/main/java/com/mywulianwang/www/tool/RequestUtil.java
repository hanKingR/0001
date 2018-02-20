package com.mywulianwang.www.tool;

import java.text.DecimalFormat;
import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

/**
 * RequestUtil 得到request中的整型参数。
 * 
 * @author Zhiwei Wang(topkaiser@gmail.com)
 * @since 2010-01-06
 */
public class RequestUtil {
	public static Integer getIntParam(HttpServletRequest request, String paramName) {
		Integer result = null;
		if (paramName != null) {
			String paramValue = request.getParameter(paramName);
			if (paramValue != null) {
				try {
					result = Integer.valueOf(paramValue);
				} catch (NumberFormatException e) {
					
				}
			}
		}
		return result;
	}
	
	/**
	 * 从cookie中取数据
	 * @param request
	 * @param key cookie的名字
	 * @return cookie的值
	 */
	public static String getCookie(HttpServletRequest request, String key) {
		String value = "";
		//从cookie中读取登录数据
		Cookie[] cookies = request.getCookies();

		if (null != cookies) {
			for (int i = 0; i < cookies.length; i++) {
				String name = cookies[i].getName();
				if (StringUtils.equals(name, key)) {
					value = cookies[i].getValue();
					return value;
				}
			}
		}
		return value;
	}
	
	/**
	 * 从session中得数据
	 * @param request
	 * @param name
	 * @return
	 */
	public static final Object getSession(HttpServletRequest request,String name) {
		return request.getSession(true).getAttribute(name);
	}
	
	/**
	 * 设置数据到session
	 * @param request
	 * @param name
	 * @param obj
	 */
	public static final void setSession(HttpServletRequest request,String name,Object obj) {
		request.getSession(true).setAttribute(name, obj);
	}

	/**
	 * 清除session中数据
	 * @param request
	 * @param name
	 */
	public static final void removeSession(HttpServletRequest request,String name) {
		request.getSession(true).removeAttribute(name);
	}
	
	/**
	 * 小数保留两位 四舍五入
	 */
	public static final Double getDoubleDecimal(Double number){
		DecimalFormat df = new DecimalFormat("#0.00");
		Double reDou = Double.valueOf(df.format(number));
		return reDou;
	}
	
	/**
	 * 对空值和Null值进行判断
	 */
	public static final boolean isNullOrBlank(Object obj){
		if(obj != null && !"".equals(obj))
			return true;
		return false;
	}
	
	/**
	 * 得到当前时间
	 * @param args
	 */
	public static final Date getNow(){
		return new Date();
	}
	
	public static void main(String[] args){
		System.out.println("=-------------->" + getDoubleDecimal(12.36 + 0.05));
	}

}