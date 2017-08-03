package com.framework.core.dto;

public class EntityFieldModelInfo {
	public Object value;
	private String name;
	public Class<?> type;
	private String linkType;

	public EntityFieldModelInfo(Class<?> type, String name, Object value) {
		setName(name);
		this.value = value;
		this.type = type;
	}

	public boolean isLinkProperty() {
		return false;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public Class<?> getType() {
		return type;
	}

	public void setType(Class<?> type) {
		this.type = type;
	}

	public Object getLinkType() {
		return null;
	}

	public void setLinkType(String linkType) {
		this.linkType = linkType;
	}

}
