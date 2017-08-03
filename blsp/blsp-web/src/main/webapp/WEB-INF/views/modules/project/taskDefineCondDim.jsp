<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<c:forEach items="${condDimList}" var="item">
	<div class="col-sm-6 form-group">
		<label class="control-label">${item.label}时限类型：</label>
		<select name="timeType${item.value}" class="form-control selectpicker_">
			<c:forEach items="${fns:getDictList('dim_type')}" var="dim">
				<option <c:if test="${item.timeType==dim.value}">selected="selected"</c:if> value="${dim.value}">${dim.label}</option>
			</c:forEach>
		</select>
	</div>
	<div class="col-xs-6 form-group">
		<label class="control-label" >${item.label}时限：</label>
		<div class="fg-line">
			<input type="number" min="0" max="999"  name="timeLimit${item.value}" value="${item.timeLimit}" class="form-control required" allowClear="true"/>
		</div>
	</div>
</c:forEach>