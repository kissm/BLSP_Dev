package com.framework.osp.common.utils;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author Pengs
 * @package com.framework.osp.modules.sys.web
 * @fileName MapSortDemo
 * @date 16/2/24.
 */
public class MapSort {
	// public static void main(String[] args) {
	// Map<String, String> map = new TreeMap<String, String>();
	// map.put("1", "窗口工作人员");
	// map.put("2", "wnba");
	// map.put("3", "nba");
	// map.put("4", "cba");
	// map.put("5", "xtgly");
	// Map<String, String> resultMap = sortMapByKey(map); //按Key进行排序
	// for (Map.Entry<String, String> entry : resultMap.entrySet()) {
	// System.out.println(entry.getKey() + " " + entry.getValue());
	// }
	// }

	/**
	 * 使用 Map按key进行排序
	 * 
	 * @param map
	 * @return
	 */
	public static Map<String, String> sortMapByKey(Map<String, String> map) {
		if (map == null || map.isEmpty()) {
			return null;
		}
		Map<String, String> sortMap = new TreeMap<String, String>(new MapKeyComparator());
		sortMap.putAll(map);
		return sortMap;
	}
}
