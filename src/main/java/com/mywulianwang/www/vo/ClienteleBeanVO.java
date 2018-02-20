package com.mywulianwang.www.vo;

import java.util.List;

/**
 * 顾客
 * 
 * @author stark
 *
 */
public class ClienteleBeanVO {
	private Long id;
	private String fileBeanId;
	private String name;
	private List<FileBeanVO> fileBeanVO;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFileBeanId() {
		return fileBeanId;
	}

	public void setFileBeanId(String fileBeanId) {
		this.fileBeanId = fileBeanId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<FileBeanVO> getFileBeanVO() {
		return fileBeanVO;
	}

	public void setFileBeanVO(List<FileBeanVO> fileBeanVO) {
		this.fileBeanVO = fileBeanVO;
	}

}
