<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<c:forEach items="${list}" var="status" varStatus="c">
<li role="presentation"><a role="menuitem" tabindex="-1" href="javascript:task(${type},${dType},${taskid},${status.taskType})">${status.name}<i class="m-r-counts bgm-orange">${status.num}</i></a></li>
</c:forEach>
