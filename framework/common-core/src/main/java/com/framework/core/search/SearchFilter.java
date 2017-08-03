package com.framework.core.search;

import java.io.Serializable;
import java.util.List;

public class SearchFilter implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4351204711168245573L;
	
	private String filterName;
	private String filterCode;
	private Integer displayOrder = 1;
	private List<FilterCount> values;
	
	
	
	
	public String getFilterName() {
		return filterName;
	}




	public void setFilterName(String filterName) {
		this.filterName = filterName;
	}




	public String getFilterCode() {
		return filterCode;
	}




	public void setFilterCode(String filterCode) {
		this.filterCode = filterCode;
	}




	public List<FilterCount> getValues() {
		return values;
	}




	public void setValues(List<FilterCount> values) {
		this.values = values;
	}
	
	




	public Integer getDisplayOrder() {
		return displayOrder;
	}




	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}






	public static class FilterCount{
		private String valueName;
		private String valueCode;
		private Long count;
		public String getValueName() {
			return valueName;
		}
		public void setValueName(String valueName) {
			this.valueName = valueName;
		}
		public String getValueCode() {
			return valueCode;
		}
		public void setValueCode(String valueCode) {
			this.valueCode = valueCode;
		}
		public Long getCount() {
			return count;
		}
		public void setCount(Long count) {
			this.count = count;
		}
		
		
		
	}

}
