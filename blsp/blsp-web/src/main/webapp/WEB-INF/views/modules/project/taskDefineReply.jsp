<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<div class="col-xs-12 form-group">
	<label class="control-label">依赖事项：</label>
	<select multiple="multiple" name="depTaskId" class="tag-select  form-control tag-select_1" data-live-search="true">
		<c:forEach items="${replyList}" var="item">
			<option <c:if test='${item.isValid=="1"}'>selected="selected"</c:if> value="${item.id}">${item.taskName}</option>
		</c:forEach>
	</select>
</div>