package com.mywulianwang.www.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mywulianwang.www.model.FileBean;
import com.mywulianwang.www.vo.FileBeanVO;

@Controller
public class FilesUploadController {

	private static final Logger log = Logger.getLogger(FilesUploadController.class);

	private static final String PATH = "fileup/";

	private static final String UpPath = "c:/images/";

	@RequestMapping(value = "/showup")
	public String addShow() {
		return PATH + "showup";
	}

	@ResponseBody
	@RequestMapping(value = "/up", method = RequestMethod.POST)
	public Object addFile(MultipartHttpServletRequest request, Model model) {
		Iterator<String> iterator = request.getFileNames();
		JSONArray jsonArray = new JSONArray();
		while (iterator.hasNext()) {
			String fileName = iterator.next();
			log.info("这是什么名称:》" + fileName);

			MultipartFile multipartFile = request.getFile(fileName);
			String originName = multipartFile.getOriginalFilename();

			log.info("上传的文件名称：》" + originName);

			FileBeanVO bean = new FileBeanVO();
			bean.getFileBean().setName(originName);

			File filepath = new File(UpPath, originName);
			if (!filepath.getParentFile().exists())
				filepath.getParentFile().mkdirs();

			bean.setFile(filepath);

			try {
				multipartFile.transferTo(new File(UpPath + File.separator + originName));
			} catch (IllegalStateException e) {
				log.info("异常啦", e);
				return "Please upload again";
			} catch (IOException e) {
				log.info("异常啦", e);
			}
			
			//构造json
            JSONObject fileJson = new JSONObject();
            fileJson.put("fileId", originName);
            jsonArray.add(fileJson);
		}
		
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("status", "1");
        map.put("message", "成功");
        map.put("data", jsonArray);
		return map;
	}

	/**
	 * GET 方式请求中文。需要在server.xml 中配置  URIEncoding="UTF-8"
	 * @param fileId
	 * @param model
	 * @return
	 */
	 @RequestMapping(value="/encoding")
	 public String deleteNewsFile(String fileId,Model model){
	 	log.info(fileId);
	 	model.addAttribute("name",fileId );
	 	return PATH + "show";
	 }

}
