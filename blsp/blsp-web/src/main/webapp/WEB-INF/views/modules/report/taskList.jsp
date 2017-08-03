<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>项目事项详情列表</title>
<meta name="decorator" content="default"/>
<script>
function page(n, s) {
	$("#pageNo").val(n);
	$("#pageSize").val(s);
	$("#searchForm").submit();
	return false;
}
</script>
</head>
<body>
<form id="searchForm" role="form" action="${ctx}/project/report/task"
			method="post">
			<input name="pageNo" id="pageNo" type="hidden" value="${page.pageNo}">
			<input name="pageSize" id="pageSize" type="hidden"
				value="${page.pageSize}"> <input
				name="type" type="hidden" value="${type}">
				<input
				name="dType" type="hidden" value="${dType}">
				<input
				name="taskid" type="hidden" value="${taskid}">
					<input
				name="taskType" type="hidden" value="${taskType}">
	<div class="p-20">
		<div class="table-responsive">
           	<table id="contentTable" class="table table-striped table-vmiddle bootgrid-table">
				<thead>
					<tr>
						<th class="col-xs-1">序号</th>
						<th class="col-xs-3">项目名称</th>
						<th class="col-xs-4">事项名称</th>
						<th class="col-xs-3">审批开始时间</th>
						<th class="col-xs-3">审批结束时间</th>
						<th class="col-xs-4">状态</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.list}" var="project" varStatus="c">
						<tr>
							<td>${page.pageSize*(page.pageNo-1)+c.index+1}</td>
							<td>${project.projectName}</td>
							<td>${project.taskName}</td>
							<td>${project.startTime}</td>
							<td>${project.endTime}</td>
							<td>${project.status}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		${page}
	</div>
	</form>
</body>