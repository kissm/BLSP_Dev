<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<select id="stageId" name="stageId" class="form-control selectpicker stageSelectpicker">
	<c:forEach items="${stageList}" var="item">
		<option value="${item.id}">${item.stageName}</option>
	</c:forEach>
</select>