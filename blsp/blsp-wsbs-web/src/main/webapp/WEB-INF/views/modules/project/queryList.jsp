<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>项目查询</title>
<meta name="decorator" content="blank" />
<script type="text/javascript">
	function page(n, s) {
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
		return false;
	}
	
	function ajaxGetTask(prjInsId, stageInsId) {
		$('#modalContent').attr('src','${ctx}/enterprise/project/offLine/finish/form?prjInsId='+prjInsId+'&stageInsId='+stageInsId);
		$('.modal-title').text("事项线下办结页");
		$('#taskForm').modal('show');
	};

	function ajaxGetUndoTask(prjInsId, stageInsId) {
		$('#modalContent').attr('src','${ctx}/project/bizaccept/unpass/form?prjInsId='+prjInsId+'&stageInsId='+stageInsId);
		$('.modal-title').text("未通过事项激活页");
		$('#taskForm').modal('show');
	};

	function iframeClose(prjInsId,type) {
		$('#taskForm').modal('hide');
		if(type == 1){
			swal({
				title: "操作成功!",
				text: "是否跳转到项目事项的材料页面处理材料并推送此事项?",
				type: "warning",
				showCancelButton: true,
				confirmButtonColor: "#DD6B55",
				cancelButtonText: '稍后自行前往处理',
				confirmButtonText: "前往处理激活事项的材料"

			}, function(){
				$("#projectId").val(prjInsId);
				$("#toMaterial").submit();
			},function(){
				page(1);
			});
		}else{
			swal({
				title: "操作成功!",
				text: "",
				type: "success"

			}, function(){
				page(1);
			});
		}

	};
</script>
</head>
<body>
	<div class="card">
		<div class="card-header">
			<ul class="actions">
				<%--<li>--%>
					<%--<button data-toggle="tooltip" data-placement="bottom" title="新办政府项目受理" class="btn btn-success btn-icon m-r-5" onclick="javascrtpt:window.location.href='${ctx}/project/index/'"><i class="md md-add"></i></button>--%>
				<%--</li>--%>
				<li>
					<button data-toggle="tooltip" data-placement="bottom" type="button" title="刷新" class="btn btn-default btn-icon m-r-5" onclick="refresh();"><i class="md md-autorenew"></i></button>
				</li>
			</ul>
		</div>
		<form id="toMaterial" role="form" action="${ctx}/project/bizaccept/basic/" modelAttribute="project" method="post">
			<input id="projectId" name="projectId" type="hidden" >
			<input name="url" type="hidden" value="next">
			<input name="type" type="hidden" value="2">
			<input name="action" type="hidden" value="update">
		</form>
		<form id="searchForm" role="form" action="${ctx}/project/queryList/"
			method="post">
			<input name="pageNo" id="pageNo" type="hidden" value="${pageNo}">
			<input name="pageSize" id="pageSize" type="hidden"
				value="${pageSize}"> <input
				name="prjInstanceVo.prjType" type="hidden" value="3">
			<div class="card-body card-padding">
				<div class="row" id="clearQuery">
					<div class="col-sm-3 form-group">
						<div class="fg-line">
							<label>项目编号：</label> <input name="prjInstanceVo.prjCode"
								class="form-control" value="${project.prjInstanceVo.prjCode}"
								type="text" maxlength="50" placeholder="项目编号">
						</div>
					</div>
					<div class="col-sm-3 form-group">
						<div class="fg-line">
							<label>项目名称：</label> <input name="prjInstanceVo.prjName"
								class="form-control" value="${project.prjInstanceVo.prjName}"
								type="text" maxlength="50" placeholder="项目名称">
						</div>
					</div>
					<div class="col-sm-3 form-group">
						<div class="fg-line">
							<label>申请开始时间：</label> <input id="b" type="text" class="form-control date-picker"
															value='<fmt:formatDate value="${project.prjInstanceVo.startTime}" pattern="yyyy-MM-dd" />'
															data-toggle="dropdown" name="prjInstanceVo.startTime"
															placeholder="单击此处..." aria-expanded="false">
						</div>
					</div>
					<div class="col-sm-3 form-group">
						<div class="fg-line">
							<label>申请结束时间：</label><input id="e" type="text" class="form-control date-picker"
															value='<fmt:formatDate value="${project.prjInstanceVo.endTime}" pattern="yyyy-MM-dd" />'
															data-toggle="dropdown" name="prjInstanceVo.endTime"
															placeholder="单击此处..." aria-expanded="false">
						</div>
					</div>
					<div class="col-sm-2">
						<label></label>
						<button class="btn btn-primary waves-effect form-control" id="btnSubmit" type="submit" onclick="page(1)">查询</button>
					</div>
					<div class="col-sm-2">
						<label></label>
						<button type="button" class="btn btn-primary waves-effect form-control" onclick="clearQuery();">重置</button>
					</div>
				</div>
				<sys:message content="${message}">
				</sys:message>
			</div>
		</form>
		<div class="table-responsive">
			<table class="table table-striped table-vmiddle bootgrid-table"
				id="contentTable">
				<thead>
					<tr>
						<th class="text-center col-xs-2">项目编号</th>
						<th class="text-center col-xs-4">项目名称</th>
						<th class="text-center">阶段</th>
						<th class="text-center">申报日期</th>
						<th class="text-center">状态</th>
						<shiro:hasPermission name="task:offline:finish">
							<th class="text-center">操作</th>
						</shiro:hasPermission>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.list}" var="project">
						<tr>
							<td class="text-center">${project.prjInstanceVo.prjCode}</td>
							<td class="text-center"><a
								href="${ctx}/project/detail?id=${project.prjInstanceVo.id}">
									${project.prjInstanceVo.prjName} </a></td>
							<td class="text-center">${project.prjStageDefineVo.stageName}</td>
							<td class="text-center"><fmt:formatDate value="${project.prjInstanceVo.creatTime}" pattern="yyyy-MM-dd" /></td>
							<td class="text-center">${project.prjStageVo.stageStatus eq '0' ?"待资料补齐":(project.prjStageVo.stageStatus eq '4'?"办结":"审批中")}
							</td>
							
							<td class="text-center">
								<shiro:hasPermission name="task:offline:finish">
									<c:if test="${project.prjStageVo.stageStatus ne 4 && project.prjInstanceVo.prjType eq '2'}">
										<button class="btn btn-info waves-effect" data-toggle="modal" onclick="ajaxGetUndoTask(${project.prjInstanceVo.id},${project.prjStageVo.id});" >未通过事项</button>
									</c:if>
									<c:if test="${project.prjStageVo.stageStatus ne 4}">
										<button class="btn btn-info waves-effect" data-toggle="modal" onclick="ajaxGetTask(${project.prjInstanceVo.id},${project.prjStageVo.id});" >事项办结</button>
									</c:if>
								</shiro:hasPermission>
							</td>
							
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		${page}
	</div>
	
	
	<div class="modal fade" data-modal-color="cyan" id="taskForm" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">事项线下办结页</h4>
				</div>
				<div style="height:400px;">
					<iframe id="modalContent" width="100%" height="100%" frameborder="0"></iframe>
				</div>
				<div class="modal-footer">
					<button id="offFinish" type="button" class="btn btn-link">已完成</button>
					<button type="button" class="btn btn-link" data-dismiss="modal">取消</button>
				</div>
			</div>
		</div>
	</div>
	
</body>
</html>