package com.mywulianwang.www.tool;

import java.util.List;

/**
 * 分页所用的类
 * @author Zhiwei Wang(topkaiser@gmail.com)
 * @since 2010-01-06
 *
 */
@SuppressWarnings("unchecked")
public class PagerOld {
	public static final int NORMAL_SIZE = 10;
	public static final int LARGE_SIZE = 30;
	
	private int totalRecords; // 共有多少条记录
	private int pageSize; // 每页显示的记录数
	private int currentPage; // 当前页
	private int totalPages; // 共有多少页
	private int start;
	private List records;
	private int recordCount;
	private String requestData;
	private String paginationForFront;
	public PagerOld(int recordCount, int pageSize, int currentPage) {
		this.totalRecords = recordCount < 0 ? 0 : recordCount;
		this.pageSize = pageSize > 0 ? pageSize : NORMAL_SIZE;
		
		// 计算总页数
		this.totalPages = (recordCount - 1) / pageSize + 1;
		
		if (currentPage < 1) {
			this.currentPage = 1;
		} else if (currentPage > totalPages) {
			this.currentPage = totalPages;
		} else {
			this.currentPage = currentPage;
		}
		
		this.start = (this.currentPage - 1) * this.pageSize;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public int getStart() {
		return start;
	}

	public List getRecords() {
		return records;
	}

	public void setRecords(List records) {
		this.records = records;
		this.recordCount = records.size();
	}

	public int getRecordCount() {
		return recordCount;
	}

	public String getRequestData() {
		return requestData;
	}

	public void setRequestData(String requestData) {
		this.requestData = requestData;
	}

	public String getPaginationForFront() {
		if(!StringUtil.isEmpty(this.requestData)){
		StringBuffer paginationHtml = new StringBuffer("");
		String preData = this.requestData.split("\\|")[0];
		String prxData = this.requestData.split("\\|")[1];
		String totalCount = this.requestData.split("\\|")[2];
		if("0".equals(prxData.split("-")[1])){
			prxData = "?totalCount="+totalCount;
		}else{
			prxData = "?searchType="+prxData.split("-")[0]+"&searchKey="+prxData.split("-")[1]+"&totalCount="+totalCount;
		}
		if (this.currentPage==1){
			paginationHtml.append("<span class='g_up'>上一页</span>");
		}else{
			int prePage = currentPage-1;
			paginationHtml.append("<a href='"+preData+prePage+".shtml"+prxData+"' target='_parent' class='g_up'>上一页</a>");
		}
		if(this.currentPage<7||this.totalPages==7){
			int tmplPage = 7;
			if(this.totalPages<=7){
				tmplPage = this.totalPages;
			}
			for(int i=1;i<=tmplPage;i++){
				if(this.currentPage == i){
					paginationHtml.append("<a class='active' href='javascript:void(0);' target='_parent'>"+i+"</a>");
				}else{
					paginationHtml.append("<a href='"+preData+i+".shtml"+prxData+"'  target='_parent'>"+i+"</a>");
				}
			}
			if(this.totalPages>7){
				paginationHtml.append("<span class='ellipsis'>...</span>");
				int tmplPage2 = this.totalPages-1;
				if((this.totalPages-this.currentPage)>1){
					paginationHtml.append("<a href='"+preData+tmplPage2+".shtml"+prxData+"'  target='_parent'>"+tmplPage2+"</a>");
				}
				paginationHtml.append("<a href='"+preData+this.totalPages+".shtml"+prxData+"'  target='_parent'>"+this.totalPages+"</a>");
			}
		}else if((totalPages-this.currentPage)<6){
			paginationHtml.append("<a href='"+preData+"1.shtml"+prxData+"' target='_parent'>1</a>");
			paginationHtml.append("<a href='"+preData+"2.shtml"+prxData+"' target='_parent'>2</a>");
			paginationHtml.append("<span class='ellipsis'>...</span>");
			for(int i=this.totalPages-6;i<=this.totalPages;i++){
				if(this.currentPage == i){
					paginationHtml.append("<a class='active' href='javascript:void(0);' target='_parent'>"+i+"</a>");
				}else{
					paginationHtml.append("<a href='"+preData+i+".shtml"+prxData+"'  target='_parent'>"+i+"</a>");
				}
			}
		}else{
			paginationHtml.append("<a href='"+preData+"1.shtml"+prxData+"'  target='_parent'>1</a>");
			paginationHtml.append("<a href='"+preData+"2.shtml"+prxData+"'  target='_parent'>2</a>");
			paginationHtml.append("<span class='ellipsis'>...</span>");
			for(int i=this.currentPage-3;i<=this.currentPage+2;i++){
				if(this.currentPage == i){
					paginationHtml.append("<a class='active' href='javascript:void(0);' target='_parent'>"+i+"</a>");
				}else{
					paginationHtml.append("<a href='"+preData+i+".shtml"+prxData+"'  target='_parent'>"+i+"</a>");
				}
			}
			paginationHtml.append("<span class='ellipsis'>...</span>");
			int tmplPage = this.totalPages-1;
			paginationHtml.append("<a href='"+preData+tmplPage+".shtml"+prxData+"'  target='_parent'>"+tmplPage+"</a>");
			paginationHtml.append("<a href='"+preData+this.totalPages+".shtml"+prxData+"'  target='_parent'>"+this.totalPages+"</a>");
		}
		if(this.currentPage==this.totalPages){
			paginationHtml.append("<span class='g_next'>下一页</span>");
		}else{
			int nextPage = currentPage+1;
			paginationHtml.append("<a href='"+preData+nextPage+".shtml"+prxData+"'  target='_parent' class'g_next'>下一页</a>");
		}
		paginationForFront = paginationHtml.toString();
		
		}
		return paginationForFront;
	}

	public void setPaginationForFront(String paginationForFront) {
		this.paginationForFront = paginationForFront;
	}
	
}
