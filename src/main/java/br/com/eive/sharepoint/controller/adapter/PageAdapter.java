package br.com.eive.sharepoint.controller.adapter;

import java.util.List;

import org.springframework.data.domain.Page;

public class PageAdapter<T> {
	private List<T> list;
	private Integer currentPage;
	private Long recordCount;
	private Integer pages;
	private Integer limit;

	public PageAdapter(Page<T> page) {
		this.list = page.getContent();
		this.currentPage = page.getPageable().getPageNumber()+1;
		this.recordCount = page.getTotalElements();
		this.pages = page.getTotalPages();
		this.limit = page.getPageable().getPageSize();
	}

	public List<T> getList() {
		return list;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public Long getRecordCount() {
		return recordCount;
	}

	public Integer getPages() {
		return pages;
	}

	public Integer getLimit() {
		return limit;
	}

}