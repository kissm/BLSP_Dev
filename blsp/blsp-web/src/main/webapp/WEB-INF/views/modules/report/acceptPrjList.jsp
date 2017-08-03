<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>受理项目详情列表</title>
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
<body><form id="searchForm" role="form" action="${ctx}/project/report/project"
			method="post">
			<input name="pageNo" id="pageNo" type="hidden" value="${page.pageNo}">
			<input name="pageSize" id="pageSize" type="hidden"
				value="${page.pageSize}"> <input
				name="type" type="hidden" value="${type}">
				<input
				name="dType" type="hidden" value="${dType}">
				<input
				name="status" type="hidden" value="${status}">
	<div class="p-20">
		<div class="table-responsive">
           	<table id="contentTable" class="table table-striped table-vmiddle bootgrid-table">
				<thead>
					<tr>
						<th class="col-xs-1">序号</th>
						<th class="col-xs-3">区项目编号</th>
						<th class="col-xs-3">项目名称</th>
						<th class="col-xs-3">申报日期</th>
						<c:if test="${type ne '1'}">
						<th class="col-xs-3">阶段</th>
						</c:if>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.list}" var="project" varStatus="c">
						<tr>
							<td>${page.pageSize*(page.pageNo-1)+c.index+1}</td>
							<td>${project.projectCode}</td>
							<td><a href="${ctx}/project/detail?id=${project.id}" target="_blank">${project.projectName}</a></td>
							<td><fmt:formatDate value="${project.applyDate}" pattern="yyyy-MM-dd" /></td>
							<c:if test="${type ne '1'}">
							<td>${project.stageName}</td>
							</c:if>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		${page}
	</div>
	</form>
</body>