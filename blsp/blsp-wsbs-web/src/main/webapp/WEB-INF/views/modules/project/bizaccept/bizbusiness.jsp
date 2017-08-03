<%@ page import="com.google.gson.Gson"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>业务表单</title>
<meta name="decorator" content="blank" />
<script>
	function sub(formid,taskId) {
		$("#formCode").val(formid);
		$("#taskId").val(taskId);
		$("#form1").submit();
	}
	$(document).ready(function() {
		$("#pre").click(function() {
			$("#pre").attr("disabled",true);
			$("#url").val("pre");
			$("#form2").submit();
		});
		$("#next").click(function() {
			var flag = 0;
			var taskIds = [];
			$("input[name='checkOne']").each(function (){
				if($(this).prop("checked")){
					flag++;
					taskIds.push($(this).val());
				}
			});
			if(flag == 0){
				notify("至少选择一个事项！");
			}else{
				//判断是否有未填写的表单
				var bool = false;
				var flag = 0;
				<%
					Gson gson = new Gson();
				%>
				var map = <%=gson.toJson(request.getAttribute("tasks"))%>;
				for(i in map){
					var list = map[i].formDefineList;
					var taskId = map[i].taskDefId;
					if(list){
						for(n in taskIds){
							if(taskIds[n] == taskId){
								for(j in list){
									if(!list[j].prjectId){
										flag++;
									}
								}
								bool = true;
							}
						}
					}
				}
				if(bool){
					if(flag == 0){
						$("#taskIds").val(taskIds);
						$("#next").attr("disabled",true);
						$("#url").val("next");
						$("#form2").submit();
					}else{
						notify("有表单未填写！");
					}
				}else{
					$("#taskIds").val(taskIds);
					$("#next").attr("disabled",true);
					$("#url").val("next");
					$("#form2").submit();
				}
			}
		});
		if(("${tasks}".length) == 0){
			$("table thead").remove();
			$("table").append('<tr>'+
							  '<td align="center">暂无可受理事项</td>'+
							  '</tr>');
			$("#next").prop("disabled",true);
		}
	});
</script>
</head>
<body>
	<form id="form1" action="${ctx}/project/bizaccept/formDetail" method="post">
		<input type="hidden" name="formCode" id="formCode">
		<input type="hidden" name="projectId" id="projectId" value="${project.prjInstanceVo.id}">
		<input type="hidden" name="taskId" id="taskId">
	</form>
	<form id="form2" action="${ctx}/project/bizaccept/forms" method="post">
		<input type="hidden" name="taskIds" id="taskIds" >
		<input type="hidden" name="url" id="url">
		<input type="hidden" name="projectId" value="${project.prjInstanceVo.id}">
	</form>
	<div class="card">
		<div class="card-header">
			<h2>事项选择与表单填写</h2>
		</div>
		<div class="table-responsive">
			<table id="contentTable" class="table table-striped table-vmiddle bootgrid-table">
				<thead>
					<tr>
						<th class="text-center col-xs-1">选择事项</th>
						<th>事项名称</th>
						<th>表单名称</th>
						<th class="text-center col-xs-1">操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${tasks}" var="entry">
						<c:set value="${entry.key}" var="key" />
						<c:set value="${entry.value}" var="value" />
						<c:if test="${fn:length(value.formDefineList) eq 0}">
						<tr>
							<td class="text-center col-xs-1">
								<label class="checkbox checkbox-inline">
									<input id="checkbox${key}" value="${key}" name="checkOne" type="checkbox"/>
									<i class="input-helper"></i>
								</label>
							</td>
							<td>${fns:getPrjTaskDefineVo(key).taskName}</td>
							<td></td>
							<td></td>
						</tr>
						</c:if>
						<c:if test="${fn:length(value.formDefineList) ne 0}">
							<c:forEach items="${value.formDefineList}" var="form" varStatus="status">
								<tr>
									<c:if test="${status.index==0}">
										<td class="text-center col-xs-1" rowspan="${fn:length(value.formDefineList)}">
											<label class="checkbox checkbox-inline">
												<input id="checkbox${key}" value="${key}" name="checkOne" type="checkbox"/>
												<i class="input-helper"></i>
											</label>
										</td>
										<td rowspan="${fn:length(value.formDefineList)}">${value.taskDefName}</td>
									</c:if>
									<td >${form.formName}</td>
									<td class="text-left text-nowrap">
										<c:if test="${form.prjectId eq null}">
											<button data-toggle="modal" class="btn btn-primary waves-effect" onClick="sub('${form.formCode}','${value.taskDefId}')">填写表单</button>
										</c:if>
										<c:if test="${form.prjectId ne null}">
											<button data-toggle="modal" class="btn btn-primary waves-effect" onClick="sub('${form.formCode}','${value.taskDefId}')">更改表单</button>
										</c:if>
									</td>
								</tr>
							</c:forEach>
						</c:if>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div class="btn-demo text-center p-20">
			<button id="pre" class="btn btn-primary">上一步</button>
			<button id="next" class="btn btn-warning">下一步</button>
		</div>
	</div>
</body>
</html>