<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<div class="table-responsive">
	<table id="contentTable" class="table table-striped">
		<thead>
			<tr>
				<th>操作人</th>
				<th>开始时间</th>
				<th>结束时间</th>
				<th>操作类型</th>
				<th>操作说明</th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${not empty handle.obj}">
				<c:forEach items="${handle.obj}" var="dto">
					<tr>
						<td>
							${dto.userName}
						</td>
						<td>
							<fmt:formatDate value="${dto.startTime}" pattern="yyyy-MM-dd" />
						</td>
						<td>
						   <fmt:formatDate value="${dto.endTime}" pattern="yyyy-MM-dd" />
						</td>
						<td>
						<c:if test="${dto.handleType eq '1'}">审批</c:if>
						<c:if test="${dto.handleType eq '2'}">暂停</c:if>
						<c:if test="${dto.handleType eq '3'}">办结</c:if>
						</td>
						<td>
						 ${dto.desc}
						</td>
					</tr>
				</c:forEach>
			</c:if>
			<c:if test="${empty handle.obj}">
				<tr>
					<td colspan="5"><i>暂无审批记录</i></td>
				</tr>
			</c:if>
		</tbody>
	</table>
</div>