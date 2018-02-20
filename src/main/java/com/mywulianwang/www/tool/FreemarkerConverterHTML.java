package com.mywulianwang.www.tool;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Locale;
import java.util.Map;

import com.yiqiao.util.commom.Constants;

import freemarker.template.Configuration;
import freemarker.template.Template;

@SuppressWarnings("all")
public class FreemarkerConverterHTML {

	/**
	 * 生成静态页面主方法
	 * 
	 * @param context ServletContext
	 * @param data 一个Map的数据结果集
	 * @param templatePath  ftl模版路径
	 * @param targetHtmlPath 生成静态页面的路径
	 * @throws Exception 
	 */
	public static void crateHTML(Map<String, Object> data, String templatePath, String targetHtmlPath) throws Exception {
		Configuration freemarkerCfg = new Configuration();
		// 加载模版
		freemarkerCfg.setServletContextForTemplateLoading(Constants.SERVLET_CONTEXT, "/WEB-INF/template");
		freemarkerCfg.setEncoding(Locale.getDefault(), "UTF-8");
		try {
			// 指定模版路径
			Template template = freemarkerCfg.getTemplate(templatePath, "UTF-8");
			template.setEncoding("UTF-8");
			// 静态页面路径
			String htmlPath = Constants.SERVLET_CONTEXT.getRealPath("/html") + "/"+ targetHtmlPath;
			File htmlFile = new File(htmlPath);
			Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(htmlFile), "UTF-8"));
			// 处理模版
			template.process(data, out);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}
