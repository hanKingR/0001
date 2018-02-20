package com.mywulianwang.www.tool;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

@SuppressWarnings("all")
public class DownLoad {
	public static void downLoadZZ(String fp)
	{
			try {
				HttpServletResponse response=ServletActionContext.getResponse();
				File f = new File(fp);
				BufferedInputStream br = new BufferedInputStream(new FileInputStream(f));
				byte[] buf = new byte[1024];
				int len = 0;
				response.reset(); 
				response.setContentType("application/x-msdownload"); 
				response.setHeader("Content-Disposition", "attachment; filename=" + f.getName()); 
				OutputStream out = response.getOutputStream();
				while((len = br.read(buf)) >0)
				out.write(buf,0,len);
				out.close();
				br.close();
			}  catch (IOException e) {
				e.printStackTrace();
			}
	} 
}
