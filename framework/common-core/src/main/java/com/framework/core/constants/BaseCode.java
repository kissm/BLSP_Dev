package com.framework.core.constants;

import java.util.Random;

/**
 * @author Pengs
 * @package com.framework.core.constants
 * @fileName BaseCode
 * @date 15/12/3.
 */
public class BaseCode {
    /**
     * 删除标记（0：正常；1：删除；2：审核；）
     */
    public static final String DEL_FLAG_NORMAL = "0";
    public static final String DEL_FLAG_DELETE = "1";
    public static final String DEL_FLAG_AUDIT = "2";

    /**
     * 是否是导航按钮
     */
    public static final String NOT_MENU = "0";
    public static final String IS_MENU = "1";

    /**
     * 顶级栏目父ID
     */
    public static final String ROOT_PARENT_ID = "0";

    /**
     * 文章显示模式
     */
    public static final String SHOW_ARTICLE_DEFAULT = "0";
    public static final String SHOW_ARTICLE_LIST = "1";
    public static final String SHOW_ARTICLE_DETIL = "2";

    /**
     * 栏目类型
     */
    public static final String CATEGORY_MODULE_ARTICLE = "article";
    public static final String CATEGORY_MODULE_LINK = "link";

    /**
     * 货品转入 转出类型
     */
    public static final String TRANS_IN = "1";
    public static final String TRANS_OUT = "2";

    /**
     * 转让状态 转让中,转让完成
     */
    public static final String TRANSFERING = "0";
    public static final String TRANSFER_REJECT = "1";
    public static final String TRANSFER_COMPLETE = "2";

    /**
     * 是否需授权码
     */
    public static final String NOT_NEED_TRANS_CODE = "0";
    public static final String NEED_TRANS_CODE = "1";

    public static final String GOODS_VALID = "1";
    public static final String GOODS_CANCEL = "2";

    public static String createTransCode() {
        return getCharAndNumr(6);
    }

    private static String getCharAndNumr(int length) {
        String val = "";
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            // 输出字母还是数字
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            // 字符串
            if ("char".equalsIgnoreCase(charOrNum)) {
                // 取得大写字母还是小写字母
                int choice = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char) (choice + random.nextInt(26));
            } else if ("num".equalsIgnoreCase(charOrNum)) { // 数字
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val.toUpperCase();
    }

    /**
     * 部门机构根节点ID
     */
    public static final String ROOT_OFFICE_ID = "1";

    /**
     * 默认区域为中国
     */
    public static final String ROOT_AREA_ID = "1";


}
