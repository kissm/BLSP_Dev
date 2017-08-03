package com.framework.core.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Selectors implements Serializable {
	private static final long serialVersionUID = 1L;
	private Set set;

	public Selectors() {
		set = new HashSet();
	}

	public Selectors(int capacity) {
		set = new HashSet(capacity);
	}

	public boolean add(String string) {
		return set.add(string);
	}

	public boolean remove(String string) {
		return set.remove(string);
	}

	public Iterator iterator() {
		return set.iterator();
	}

	@Override
	public String toString() {
		return set.toString();
	}

	/**
	 * 产生以property为根的新的Selectors
	 */
	public Selectors generateSubSelectors(String property) {
		property = property + ".";
		Selectors newSelector = new Selectors();
		Iterator it = iterator();
		while (it.hasNext()) {
			String item = it.next().toString();
			if (item.startsWith(property)) {
				String subItem = item.substring(property.length());
				newSelector.add(subItem);
			}
		}
		return newSelector;
	}

	/**
	 * property属性是否被定义在Seletors中了
	 */
	public boolean contains(String property) {
		Iterator it = iterator();
		while (it.hasNext()) {
			String item = it.next().toString();
			if (item.startsWith(property)) {
				return true;
			}
		}
		return false;
	}
}