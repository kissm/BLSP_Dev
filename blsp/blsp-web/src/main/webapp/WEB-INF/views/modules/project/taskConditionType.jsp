<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<select id="conditionTypeValue" name="conditionTypeValue" class="form-control selectpicker_3">
	<c:forEach items="${fns:getDictList(conditionType)}" var="item">
		<option <c:if test="${conditionTypeValue==item.value}">selected="selected"</c:if> value="${item.value}">${item.label}</option>
	</c:forEach>
</select>    