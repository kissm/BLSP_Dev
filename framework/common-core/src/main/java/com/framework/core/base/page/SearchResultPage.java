package com.framework.core.base.page;

import java.util.List;

import com.framework.core.search.SearchFilter;

public class SearchResultPage<T> extends Page<T> {
	private List<SearchFilter> filters;

	public List<SearchFilter> getFilters() {
		return filters;
	}

	public void setFilters(List<SearchFilter> filters) {
		this.filters = filters;
	}
	
	

}
