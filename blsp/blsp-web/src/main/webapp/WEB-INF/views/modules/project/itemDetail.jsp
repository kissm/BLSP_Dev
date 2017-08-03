<%@ page import="com.google.gson.Gson"%>
<%@ page import="com.lpcode.modules.service.project.dto.Project"%>
<%@ page import="com.lpcode.modules.service.project.dto.PrjTaskDTO"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title></title>
<meta name="decorator" content="default" />
<style type="text/css">
	.editTable {
		width: 100%;
	}
	.editTable > tbody > tr > td {
		border: 1px solid #ccc;
	}
	.editTable td.title {
		padding: 6px;
		text-align: center;
		background: #F7F7F7;
	}
	.editTable td.content {
		padding: 6px;
	}
	.editTable input[type='text'] {
		width: 100%;
		height: 100%;
		border: none;
		padding: 0px 6px;
		resize: none;
	}
	.zj_jsgc_zljdzcbTable tr {
		height: 50px;
	}
</style>
<script type="text/javascript">
	$(function (){
		<%
			Gson gson = new Gson();
		%>
		var formMap = <%=gson.toJson(((Project)request.getAttribute("project")).getFormMap())%>;
		if(formMap){
			var flag = 0;
			var map = eval(formMap);
			for( var i in map ){
				var object = map[i];
				if(object.id){
					flag++;
				}
			}
			if(flag == 0){
				$("#serInfoLi").remove();
			}
		}else{
			$("#serInfoLi").remove();
		}
		
		//判断是否存在已上传批文，若有则加载批文信息
		var prjTask = <%=gson.toJson((PrjTaskDTO)request.getAttribute("prjTask"))%>;
		if(prjTask.auditAttachAddr != '' && prjTask.auditAttachAddr != null){
			var id = prjTask.id;
			var pathUrl = prjTask.auditAttachAddr;
			var coi = "${fn:split(prjTask.auditAttachName,'.')[0]}";
			$.ajax({
				type : 'post',
				url : '${ctx}/prjTask/cert/uploadInput',
				data : {'prjTaskInstId':id},
				cache : false,
				async:false,
				dataType: 'json',
				success : function(data) {
					if(data.certDept != undefined)
						$('#certDeptDown').val(data.certDept);
					if(data.certDateStr != undefined)
						$('#certDateDown').val(data.certDateStr);
					if(data.certCode != undefined)
						$('#certCodeDown').val(data.certCode);
					if(data.certTitle != undefined)
						$('#certTitleDown').val(data.certTitle);
				}
			});
			$("#downLoadFile").click(function (){
				window.open("${ctx}/sys/download?pathUrl="+pathUrl+"&coi="+coi);
			});
		}
	});
	function lookForm(formCode){
		var view = "${view}";
		var taskId = '${taskId}';
		var id = '${project.prjInstanceVo.id}';
		window.location.href = "${ctx}/project/accpet/formDetailView?taskId="+taskId+"&formCode="+formCode+"&id="+id+"&view=" +view;
	}
	//判定是否是返回详情的数据，若是则自动选中业务信息
	$(function (){
		var taskId = <%=session.getAttribute("stageTaskId")%>;
		if(taskId){
			$("#serInfo").click();
			//清除session中存储的信息
			var url = "${ctx}/project/clearSessionInfo";
			$.post(url);
		}
		//判断是否是网厅提交项目查看详情，是则屏蔽材料
		var view = "${view}";
		if(view == "4" || view == "5"){
			$("#matLi").hide();
			$("#serInfo").click();
		}
	});
</script>
</head>
<body>
	<div role="tabpanel" class="tab-pane active animated fadeInRight in" id="detailTable">
			<div role="tabpanel">
				<ul class="tab-nav" role="tablist" data-tab-color="cyan">
					<li class="active" id="matLi" role="presentation"><a href="#materials1" aria-controls="materials1" role="tab" data-toggle="tab">申报材料</a></li>
					<li role="presentation" id="serInfoLi"><a href="#profileZero" id="serInfo" aria-controls="profileZero" role="tab" data-toggle="tab">业务信息</a></li>
					<c:if test="${prjTask.auditAttachAddr != '' && prjTask.auditAttachAddr != null}">
						<li role="presentation"><a href="#approvalDownLoad" aria-controls="materials" role="tab" data-toggle="tab">批文下载</a></li>
					</c:if>
				</ul>
				<div class="tab-content">
				
					<div role="tabpanel" class="tab-pane animated fadeIn in active" id="materials1">
						<div class="table-responsive">
							<table id="detailTable" class="table table-striped">
								<thead>
									<tr>
										<th class="text-center col-xs-2">事项名称</th>
										<th class="text-center">申请材料</th>
										<th class="text-center col-xs-1">原件</th>
										<th class="text-center col-xs-1">复印件</th>
										<th class="text-center col-xs-2">操作</th>
										<th class="text-center col-xs-1">是否提供</th>
									</tr>
								</thead>
								<tbody>
									<c:set var="i" value="0"></c:set>
									<c:forEach items="${project.prjStageMaterialVoMap}" var="map"
										varStatus="v">
										<c:forEach items="${map.value}" var="mater" varStatus="var">
											<c:set var="i" value="${i+1}"></c:set>
											<input disabled type="hidden" value="${mater.materialAddr}"
												name="prjStageMaterialVoList[${var.index}].materialAddr"
												id="fileUrl${var.index}" />
											<input disabled type="hidden" value="${mater.stageId}"
												name="prjStageMaterialVoList[${var.index}].stageId" />
											<input disabled type="hidden" value="${mater.materialId}"
												name="prjStageMaterialVoList[${var.index}].materialId" />
											<input disabled type="hidden" value="${mater.originalNum}"
												name="prjStageMaterialVoList[${var.index}].originalNum" />
											<input disabled type="hidden" value="${mater.copyNum}"
												name="prjStageMaterialVoList[${var.index}].copyNum" />
											<input disabled type="hidden" value="${mater.isMandatory}"
												name="prjStageMaterialVoList[${var.index}].isMandatory" />
											<input disabled type="hidden" value="${mater.materName}"
												name="prjStageMaterialVoList[${var.index}].materName" />
											<input disabled type="hidden" value="${mater.id}"
												name="prjStageMaterialVoList[${var.index}].id" />
											<tr>
											<c:if test="${var.index eq 0}">
													<c:set var="taskDefine" value="${fns:getPrjTaskDefineVo(map.key)}"></c:set>
													<c:set var="taskVo" value="${fns:getPrjTaskVo(map.key,project.prjInstanceVo.id,project.prjStageVo.id)}"></c:set>
														<td rowspan="${fn:length(map.value)}" class='${v.index%2==0?"odd":"even"}' style="vertical-align: middle;"><i class="input-helper"></i>${taskDefine.taskName}</td>
													</c:if>
												<td class='${v.index%2==0?"odd":"even"}'><c:if test="${mater.isMandatory eq '1'}">
														<span style="color: red">*</span>
													</c:if>${mater.materName}</td>
												<td class='${v.index%2==0?"odd":"even"} text-center'>
													${mater.originalNum}
												</td>
												<td class='${v.index%2==0?"odd":"even"} text-center'>
													${mater.copyNum}
												</td>
												<td class='${v.index%2==0?"odd":"even"} text-center'>
													<c:if test="${!empty mater.materialAddr}">
														<button data-toggle="tooltip" data-placement="bottom" type="button" title="下载" id="download${map.key}"
														 onclick="window.open('${centextPath}/download?pathUrl=${mater.materialAddr}&coi=${mater.materName}')"
														 class="btn btn-icon btn-file bgm-lightblue">
															<i class="md md-file-download"></i>
														</button>
														<button data-toggle="tooltip" data-placement="bottom" type="button" title="预览" id="view${map.key}" 
														onclick="window.open('${ctx}/sys/filePreview?pathUrl=${mater.materialAddr}')" 
														class="btn btn-success btn-icon btn-file">
															<i class="md md-visibility"></i>
														</button>
													</c:if>
												</td>
												<td class='text-center ${v.index%2==0?"odd":"even"}'><label
													class="checkbox checkbox-inline"> <input disabled
														type="checkbox" value="1"
														${mater.isComplete eq '1'?"checked":""}
														name="prjStageMaterialVoList[${var.index}].isComplete">
														<i class="input-helper"></i>
												</label></td>
											</tr>
										</c:forEach>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				
					<div role="tabpanel" class="tab-pane animated fadeIn" id="profileZero">
						<table class="table table-striped table-vmiddle bootgrid-table">
							<thead>
								<tr>
									<th>
									表单名称
									</th>
									<th class="text-center col-xs-2">
										操作
									</th>
								</tr>
							</thead>
							<c:forEach items="${project.formNameMap}" var="nameMap">
								<tr>
									<c:forEach items="${project.formMap}" var="formMap">
										<c:if test="${nameMap.key == formMap.key && formMap.value.id != null}">
											<td>
												<c:out value="${nameMap.value}" />
											</td>
											<td style="text-align:center;">
												<a href="javascript:;" onclick="lookForm('${formMap.key}')">查看表单</a>
											</td>
										</c:if>
									</c:forEach>
								</tr>
							</c:forEach>
						</table>
					</div>
				
					<div role="tabpanel" class="tab-pane animated fadeIn" id="approvalDownLoad">
						<div class="modal-body bgm-white text-muted p-20 form-horizontal">
							<div class="row">
								<div class="form-group">
									<label class="col-xs-3 control-label">发证机关：</label>
									<div class="col-xs-6">
										<div class="fg-line">
											<input id="certDeptDown" placeholder="发证机关" type="text" disabled maxlength="100" class="form-control required">
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-xs-3 control-label">发证时间：</label>
									<div class="col-xs-6">
										<div class="dtp-container dropdown fg-line">
											<input id="certDateDown" placeholder="发证时间" type="text" disabled data-toggle="dropdown" class="form-control date-picker required">
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-xs-3 control-label">文号：</label>
									<div class="col-xs-6">
										<div class="fg-line">
											<input id="certCodeDown" placeholder="文号" type="text" disabled maxlength="30" class="form-control required">
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-xs-3 control-label">标题：</label>
									<div class="col-xs-6">
										<div class="fg-line">
											<input id="certTitleDown" placeholder="标题" type="text" disabled maxlength="100" class="form-control required">
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-xs-3 control-label">附件下载：</label>
									<div class="col-xs-6">
										<div class="fg-line">
											<button class="btn btn-icon btn-file bgm-lightblue" id="downLoadFile" type="button" title="下载批文" data-toggle="tooltip" data-placement="bottom">
													<i class="md md-file-download"></i>
											</button>
										</div>
									</div>
								</div>
								
							</div>
						</div>
					</div>
					
				</div>
			</div>
		</div>
</body>