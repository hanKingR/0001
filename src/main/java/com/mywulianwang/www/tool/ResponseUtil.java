package com.mywulianwang.www.tool;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONObject;


/**
 * ResponseUtil 通过response设置参数传递到页面。
 * 
 * @author Zhiwei Wang(topkaiser@gmail.com)
 * @since 2010-01-06
 */
public class ResponseUtil {

	private static final Logger logger = Logger.getLogger(ResponseUtil.class);
	
	/**
	 * 将数据写入cookie中
	 * @param response
	 * @param store cookie的生命周期
	 * @param key cookie的名字
	 * @param value cookie的值
	 */
	public static void setCookie(HttpServletResponse response, int store, String key, String value) {
		Cookie cookie = new Cookie(key, value);
		cookie.setMaxAge(store);
		cookie.setPath("/");
		response.addCookie(cookie);
	}

	/**
	 * 输出一个json对象，用UTF-8编码
	 * @param response
	 * @param json
	 * @throws IOException
	 */
	public static void writeJSON(HttpServletResponse response, JSONObject json) {
		response.setCharacterEncoding(Constants.ENCODING_UTF8);
		response.setContentType("application/json");
		try {
			response.getWriter().print(json);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 输出一段文本，用UTF-8编码
	 * @param response
	 * @param output
	 * @throws IOException
	 */
	public static void writeOutput(HttpServletResponse response, String output) {
		response.setCharacterEncoding(Constants.ENCODING_UTF8);
		try {
			response.getWriter().print(output);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 发送404错误代码给客户端
	 * @param response
	 */
	public static void send404(HttpServletResponse response) {
		try {
			response.sendError(404);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}
}
