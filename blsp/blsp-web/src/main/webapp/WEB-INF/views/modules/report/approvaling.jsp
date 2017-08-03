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

function ajaxHandle(id) {
	$.ajax({
		type : 'post',
		url :'${ctx}/project/handle',
		data : {"taskId":id},
		cache : false,
		dataType : 'html',
		success : function(data) {
			$("#handle").html(data);
			$('#approve').modal('show');
		}
	});
}
</script>
</head>
<body>
<form id="searchForm" role="form" action="${ctx}/project/report/task" method="post">
	<input name="pageNo" id="pageNo" type="hidden" value="${page.pageNo}">
	<input name="pageSize" id="pageSize" type="hidden" value="${page.pageSize}">
	<input name="type" type="hidden" value="${type}">
	<input name="dType" type="hidden" value="${dType}">
	<input name="taskid" type="hidden" value="${taskid}">
	<input name="taskType" type="hidden" value="${taskType}">
	<div class="p-20">
		<div class="table-responsive">
           	<table id="contentTable" class="table table-striped table-vmiddle bootgrid-table">
				<thead>
					<tr>
						<th class="col-xs-1">序号</th>
						<th class="col-xs-3">项目名称</th>
						<th class="col-xs-3">开始时间</th>
						<th class="col-xs-3">应办结时间</th>
						<th class="col-xs-2">状态</th>
						<th class="col-xs-2">当前处理人</th>
						<th class="col-xs-1">超时天数</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.list}" var="project" varStatus="c">
						<tr>
							<td>${page.pageSize*(page.pageNo-1)+c.index+1}</td>
							<td>
								<a href="javascript:void(0)" title="审批详情查看" onclick="ajaxHandle(${project.taskInstanceId})">${project.projectName}</a>
								<%--<a href="${ctx}/project/report/record?instId=${project.prjStageInstId}">${project.projectName}</a>--%>
							</td>
							<td>
								<fmt:formatDate value="${project.startTime}" pattern="yyyy-MM-dd" />
							</td>
							<td>
								<fmt:formatDate value="${project.needEndTime}" pattern="yyyy-MM-dd" />
							</td>
							<td>
								<c:if test="${project.status eq 0}">暂存</c:if>
								<c:if test="${project.status eq 1}">审批中</c:if> 
								<c:if test="${project.status eq 2}">
									暂停(${fns:getDictLabel(project.taskPauseType, 'task_pause_type', '')}
										<c:if test="${project.taskPauseType == 99}">
											其他
										</c:if>
									)<br/>
									<fmt:formatDate value="${project.taskPauseStartTime}" pattern="yyyy-MM-dd" />
								</c:if> 
								<c:if test="${project.status eq 4}">已办结</c:if>
								<c:if test="${project.status eq 5}">未启动</c:if>
								<c:if test="${project.status eq 6}">不通过</c:if>
								<c:if test="${project.status eq 7}">已完成</c:if>
							</td>
							<td>${project.userName}</td>
							<td>
								<c:if test="${project.overDate lt 0}">
									<span style="color: red;">${-project.overDate}</span>
								</c:if>
								<c:if test="${project.overDate gt 0}">
									<span style="color: blue;">${-project.overDate}</span>
								</c:if>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		${page}
	</div>
	<div class="modal fade" data-modal-color="cyan" id="approve"
		 data-backdrop="static" data-keyboard="false" tabindex="-1"
		 role="dialog" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">审批详情查看</h4>
				</div>
				<div class="modal-body bgm-white text-muted" id="handle"></div>
				<div class="modal-footer">
					<button type="button" class="btn btn-link" data-dismiss="modal">关闭</button>
				</div>
			</div>
		</div>
	</div>
	</form>
</body>