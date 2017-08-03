package com.framework.core.result;

import com.framework.core.base.page.Page;

/**
 * Package: com.mogubang.dto
 * Created by 解建君.
 * Date: 15-5-25
 * Time: 下午4:51
 */
public class RequestDTOPage<T>   {
	private Page<T> page;
//  private int page = 0;
//  private int limit = 100;
//  String orderBy;
    T obj;
    

  public RequestDTOPage() {
  }
    
    

//  public RequestDTOPage(int page, int limit) {
//    this.page = page;
//    this.limit = limit;
//  }
//
//  public int getPage() {
//    return page;
//  }
//
//  public void setPage(int page) {
//    this.page = page;
//  }
//
//  public int getLimit() {
//    return limit;
//  }
//
//  public void setLimit(int limit) {
//    this.limit = limit;
//  }

  public Page<T> getPage() {
	return page;
}



public void setPage(Page<T> page) {
	this.page = page;
}



public T getObj() {
    return obj;
  }

  public void setObj(T obj) {
    this.obj = obj;
  }

//  public String getOrderBy() {
//    return orderBy;
//  }
//
//  public void setOrderBy(String orderBy) {
//    this.orderBy = orderBy;
//  }

//  @Override
//  public String toString() {
//    return "RequestDTOPage{" +
//           "page=" + page +
//           ", limit=" + limit +
//           ", orderBy='" + orderBy + '\'' +
//           ", obj=" + obj +
//           '}';
//  }
}
