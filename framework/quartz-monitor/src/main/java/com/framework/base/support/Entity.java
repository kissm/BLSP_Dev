/**
 * @Probject Name: quartz-monitor
 * @Path: com.framework.base.supportPageEntity.java
 * @Create By mogu
 * @Create In 2015年1月5日 下午4:07:59
 * TODO
 */
package com.framework.base.support;

/**
 * @Class Name PageEntity
 * @Author mogu
 * @Create In 2015年1月5日
 */
public class Entity {
    
    /**
     * 当前页
     */
    private int page;
    
    /**
     * 起始记录数-1
     */
    private int start;
    
    /**
     * 每页数
     */
    private int rows;
    

    
    /**
     * @Return the int start
     */
    public int getStart() {
        this.start=(page-1)*rows;
        return this.start;
    } 
    
    /**
     * @Param int start to set
     */
    public void setStart(int start) {
        this.start = start;
    }
  
    /**
     * @Return the int rows
     */
    public int getRows() {
        return rows;
    }
    /**
     * @Param int rows to set
     */
    public void setRows(int rows) {
        this.rows = rows;
    }

    /**
     * @Return the int page
     */
    public int getPage() {
        return page;
    }

    /**
     * @Param int page to set
     */
    public void setPage(int page) {
        this.page = page;
    }
    
}
