package com.mywulianwang.www.vo;

import java.io.File;

import com.mywulianwang.www.model.BaseEntity;
import com.mywulianwang.www.model.FileBean;

public class FileBeanVO extends BaseEntity {
	private FileBean fileBean;
	private String path;
	private File file;

	public FileBean getFileBean() {
		return fileBean;
	}

	public void setFileBean(FileBean fileBean) {
		this.fileBean = fileBean;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

}
