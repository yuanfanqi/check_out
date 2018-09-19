package com.checkOut.common.model.commonModel;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "PermissionPage", description = "权限分页结果")
public class PageData<T> {

	/**
	 * 总页数
	 */
	@ApiModelProperty(value = "总页数")
	private Integer pages;
	/**
	 * 当前页
	 */
	@ApiModelProperty(value = "当前页")
	private Integer pageNum;
	/**
	 * 数据行总数
	 */
	@ApiModelProperty(value = "数据行总数")
	private Integer total;
	/**
	 * 数据
	 */
	@ApiModelProperty(value = "数据")
	private List<T> list;
	
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
	public Integer getPages() {
		return pages;
	}
	public void setPages(Integer pages) {
		this.pages = pages;
	}
	public Integer getPageNum() {
		return pageNum;
	}
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
	
}
