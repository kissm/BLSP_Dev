package com.framework.osp.common.utils;

import java.util.Comparator;

/**
 * @author Pengs
 * @package com.framework.osp.common.utils
 * @fileName MapKeyComparator
 * @date 16/2/24.
 */
//比较器类
public class MapKeyComparator implements Comparator<String> {
    public int compare(String str1, String str2) {
        return str1.compareTo(str2);
    }
}