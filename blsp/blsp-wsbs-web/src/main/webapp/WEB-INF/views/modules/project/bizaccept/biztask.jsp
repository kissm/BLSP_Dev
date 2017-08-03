<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>受理表单</title>
<meta name="decorator" content="blank" />
<script>
	$(document).ready(function() {
		$("#pre").click(function() {
			$("#pre").attr("disabled",true);
			$("#url").val("pre");
			$("#form1").submit();
		});
		$("#next").click(function() {
			$("#next").attr("disabled",true);
			$("#url").val("next");
			$("#form1").submit();
		});
	});
</script>
</head>
<body>
	<div class="card">
		<div class="card-header">
			<h2>事项审批人选择</h2>
		</div>
		<form method="post" name="form1" id="form1" action="${ctx}/project/bizaccept/tasks">
			<input type="hidden" name="url" id="url">
			<input type="hidden" name="projectId" value="${project.prjInstanceVo.id}">
			<input type="hidden" name="action" value="${action}">
			<div class="table-responsive">
				<table class="table table-striped table-vmiddle bootgrid-table" id="accept_">
					<thead>
						<tr>
							<th class="text-center">事项名称</th>
							<th class="text-center">单位</th>
							<th class="text-center col-xs-3">审批人</th>
						</tr>
					</thead>
					<tbody>
						<c:if test="${project.prjTaskVoList ne null}">
							<c:forEach items="${project.prjTaskVoList}" var="dvo"
								varStatus="c">
								<input type="hidden" value="${dvo.taskId}"
									name="prjTaskVoList[${c.index}].taskId" />
								<input type="hidden" value="${dvo.id}"
									name="prjTaskVoList[${c.index}].id" />
								<input type="hidden" value="${dvo.isWithCert}"
									name="prjTaskVoList[${c.index}].isWithCert" />
								<tr>
									<td>${dvo.taskName}</td>
									<td>${fns:getOffice(dvo.deptId).name}</td>
									<td><select class="form-control selectpicker"
										name="prjTaskVoList[${c.index}].initUser">
											<c:forEach
												items="${fns:getUsersByOfficeId(dvo.deptId)}"
												var="itemValue">
												<option value="${itemValue.id}"
													${c.index eq 0?"selected":""}>${itemValue.name}</option>
											</c:forEach>
									</select></td>
								</tr>
							</c:forEach>
						</c:if>
					</tbody>
				</table>
			</div>
		</form>
		<div class="btn-demo text-center p-20">
			<button id="pre" class="btn btn-primary">上一步</button>
			<button id="next" class="btn btn-warning">下一步</button>
		</div>
	</div>
</body>
</html>