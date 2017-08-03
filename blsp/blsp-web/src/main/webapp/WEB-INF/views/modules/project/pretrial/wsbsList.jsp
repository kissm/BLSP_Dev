<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>在线预审项目列表</title>
<meta name="decorator" content="blank" />
<script type="text/javascript">
	$(document).ready(function() {
		$('#confirm').click(function() {
			$('#modalWider').modal('hide');
			ajaxSave();
			//$('#notice').modal('show');
		});
		$('#saveMater').click(function() {
			$('#material').modal('hide');
			domaterial();
			//$('#notice').modal('show');
		});
	});
	function ajaxAccept(id) {
		$("#prjId").val(id);
		$.ajax({
			type : 'post',
			url : '${ctx}/project/validate',
			data : {"prjInstanceVo.id":id},
			cache : false,
			dataType : 'html',
			success : function(data) {
				$("#temporary").html(data);
				$("#accept").html("");
				$("#success").html("");
				if ($("#accept_").length > 0) {
					$("#accept").html($("#temporary").html());
					$('#notice').modal('hide');
					$('.selectpicker').selectpicker();
					$('#modalWider').modal('show');
					return;
				}
				if ($("#success_").length > 0) {
					$("#success").html($("#temporary").html());
					$('#modalWider').modal('hide');
					$('#notice').modal('show');
					return;
				}
			}
		});
	}
	function mater(id) {
		$('#material').modal('show');
		$.ajax({
			type : 'post',
			url : '${ctx}/project/material',
			data : {"prjInstanceVo.id":id},
			cache : false,
			dataType : 'html',
			success : function(data) {
				$("#mater").html(data);
			}
		});
	}
	
	function ajaxSave(id) {
		var formParam = $("#form1").serialize();//序列化表格内容为字符串
		$.ajax({
			type : 'post',
			url :'${ctx}/project/task',
			data : formParam,
			cache : false,
			dataType : 'html',
			success : function(data) {
				$("#temporary").html(data);
				if($("#success_").length>0){
					$("#success").html($("#temporary").html());
					$('#notice').modal('show');
					return;
				}
			}
		});
	}
	function domaterial() {
		var formParam = $("#form1").serialize();//序列化表格内容为字符串
		$.ajax({
			type : 'post',
			url :'${ctx}/project/doMaterial',
			data : formParam,
			cache : false,
			dataType : 'html',
			success : function(data) {
				$("#temporary").html(data);
				if($("#success_").length>0){
					$("#success").html($("#temporary").html());
					$('#notice').modal('show');
					return;
				}
			}
		});
	}
	
	function page(n, s) {
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
		return false;
	}
</script>
</head>
<body>
	<div class="card">
		<div class="card-header">
			<h2>
				在线预审项目列表<small>您可通过本功能进行在线预审项目列表查询,并可以进行补充信息及审核</small>
			</h2>
			<ul class="actions">
	            <li>
					<button data-toggle="tooltip" data-placement="bottom" type="button" title="刷新" class="btn btn-default btn-icon m-r-5" onclick="refresh();"><i class="md md-autorenew"></i></button>
				</li>
            </ul>
		</div>
		<form id="searchForm" role="form" action="${ctx}/project/wsbslist/"
			method="post">
			<input name="pageNo" id="pageNo" type="hidden" value="${pageNo}">
			<input name="pageSize" id="pageSize" type="hidden"
				value="${pageSize}"> <input
				name="prjInstanceVo.prjType" type="hidden" value="1">
			<div class="card-body card-padding">
				<div class="row" id="clearQuery">
					<div class="col-sm-2 form-group">
						<div class="fg-line">
							<label>区项目编号：</label> <input name="prjInstanceVo.prjCode"
								class="form-control" value="${project.prjInstanceVo.prjCode}"
								type="text" maxlength="50" placeholder="区项目编号">
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
							<label>建设单位名称：</label> <input name="prjInstanceVo.company"
														  class="form-control" value="${project.prjInstanceVo.company}"
														  type="text" maxlength="50" placeholder="建设单位名称">
						</div>
					</div>
					<div class="col-sm-2">
						<label></label>
						<button class="btn btn-primary waves-effect form-control"
							id="btnSubmit" type="submit" onclick="page(1)">查询</button>
											</div>
											<div class="col-sm-2">
						<label></label>
											<button id="btnReset" type="button" class="btn btn-primary waves-effect form-control" onclick="clearQuery();">重置</button>
										</div>	
				</div>
				<sys:message content="${message}">
				</sys:message>
			</div>
		</form>
		<div class="table-responsive" >
			<table class="table table-striped table-vmiddle bootgrid-table"
				id="contentTable">
				<thead>
					<tr>
						<th>项目名称</th>
						<th class="col-xs-2">区项目编号</th>
						<th class="col-xs-2">建设单位名称</th>
						<th class="col-xs-2">申报日期</th>
						<th class="col-xs-1">阶段</th>
						<th class="col-xs-1">状态</th>
						<th class="text-center col-xs-2">操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.list}" var="project">
						<tr style="height:50px;">
							<td><a
								href="${ctx}/project/wsbsview?id=${project.prjInstanceVo.id}">
									${project.prjInstanceVo.prjName} </a></td>
							<td>${project.prjInstanceVo.prjCode}</td>
							<td>${project.prjInstanceVo.company}</td>
							<td><fmt:formatDate value="${project.prjInstanceVo.creatTime}" pattern="yyyy-MM-dd" /></td>
							<td>${project.prjStageDefineVo.stageName}</td>
							<td>
								<c:if test="${project.prjInstanceVo.applyState eq '0'}">
									草稿暂存
								</c:if>
								<c:if test="${project.prjInstanceVo.applyState eq '1'}">
									提交审核
								</c:if>
								<c:if test="${project.prjInstanceVo.applyState eq '2'}">
									审核通过
								</c:if>
								<c:if test="${project.prjInstanceVo.applyState eq '3'}">
									审核退回
								</c:if>
							</td>
							<td class="text-center text-nowrap">
								<%-- <c:if test="${project.prjStageVo.stageStatus eq '1'}">
								<button class="btn btn-icon btn-info m-r-5" title="资料补齐" data-toggle="tooltip" data-placement="bottom"
									onclick="javascript:window.location.href='${ctx}/project/accpet/basic?projectId=${project.prjInstanceVo.id}'"
									type="button">
									<i class="md md-file-upload"></i>
								</button>
								</c:if>
								<c:if test="${project.prjStageVo.stageStatus eq '0'}">
								<button class="btn btn-icon btn-info m-r-5" title="受理" data-toggle="tooltip" data-placement="bottom"
									onclick="javascript:window.location.href='${ctx}/project/accpet/basic?projectId=${project.prjInstanceVo.id}'"
									type="button">
									<i class="md md-check"></i>
								</button>
								<button class="btn btn-icon btn-warning" title="编辑" data-toggle="tooltip" data-placement="bottom"
									onclick="javascript:window.location.href='${ctx}/project/accpet/basic?projectId=${project.prjInstanceVo.id}'"
									type="button">
									<i class="md md-edit"></i>
								</button>
								</c:if> --%>
								<c:if test="${project.prjInstanceVo.applyState eq '1'}">
									<button class="btn btn-icon btn-info m-r-5" title="审核" data-toggle="tooltip" data-placement="bottom"
										onclick="javascript:window.location.href='${ctx}/project/wsbsview?id=${project.prjInstanceVo.id}&pretrial=1'"
										type="button">
										<i class="md md-check"></i>
									</button>
								</c:if>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		${page}
	</div>
	<form id="form1" method="post">
	<input type="hidden" value="" name="prjInstanceVo.id" id="prjId"/>
	<div class="modal fade" id="modalWider" data-modal-color="lightblue"
		data-backdrop="static" data-keyboard="false" tabindex="-1"
		role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">选择事项审批人</h4>
				</div>
				<div class="modal-body p-20 bgm-white text-muted o-auto"
					style="max-height: 400px;" id="accept">
				</div>
				<div class="modal-footer">
					<button id="confirm" type="button" class="btn btn-link">确认受理</button>
					<button type="button" class="btn btn-link" data-dismiss="modal">关闭</button>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="notice" data-modal-color="lightblue"
			tabindex="-1" role="dialog" aria-hidden="true" data-backdrop="static" data-keyboard="false">
			<div class="modal-dialog">
				<div class="modal-content" id="success">
				</div>
			</div>
		</div>
			<div class="modal fade" id="material" data-modal-color="lightblue"
			tabindex="-1" role="dialog" aria-hidden="true" data-backdrop="static" data-keyboard="false">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<h4 class="modal-title">审批中资料上传页</h4>
					</div>
					<div class="modal-body  p-20 bgm-white text-muted o-auto"
						style="max-height: 450px;" id="mater"></div>
					<div class="modal-footer">
						<button id="saveMater" type="button" class="btn btn-link">保存</button>
						<button type="button" class="btn btn-link" data-dismiss="modal">关闭</button>
					</div>
				</div>
			</div>
		</div>
		
	</form>
<div id="temporary" style="display:none"></div>
</body>
</html>