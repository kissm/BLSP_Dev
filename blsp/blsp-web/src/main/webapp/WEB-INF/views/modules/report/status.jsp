<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<c:forEach items="${list}" var="status" varStatus="c">
<div>${status.name}<i class='m-r-counts bgm-orange'>${status.num}</i></div>
</c:forEach>
