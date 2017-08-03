<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>受理详情</title>
<meta name="decorator" content="blank" />
<script>
	$(document).ready(function() {
		//init();
	});
	function downFile(url, name) {
		var u = "${ctx}/sys/download?pathUrl=";
		u = u + url + "&coi=" + encodeURI(name);
		window.location.href = u;
	}
	function init() {
		initMater();
	}
	function initMater() {
		$("input[data-type='taskId']:checkbox").each(function() {
			var tt = $(this);
			var key = tt.attr("data");
			var iscomplete = true;
			var isHasSelect = false;
			$("input[data-id='" + key + "']:checkbox").each(function() {
				var t = $(this);
				var isMust = t.attr("data-type");
				var isSelected = t.is(':checked');
				if (isMust == 'must' && !isSelected) {
					iscomplete = false;
					isHasSelect = false;
					return false;
				} else if (isSelected) {
					isHasSelect = true;
				}
			});
			if (isHasSelect) {
				if (iscomplete) {
					tt.prop("checked", true);
				} else {
					tt.prop("checked", false);
				}
			} else {
				tt.prop("checked", false);
			}
		});
	}
	function openTask(taskId,id){
		openWindow({
			id:'detailTable',
			title:'事项详情',
			url:'${ctx}/project/itemDetail',
			width:'1000px',
			height:'500px',
			params:{"taskId":taskId,"id":id,"view":"1"},
		});
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
	//判定是否是返回详情的数据，若是则自动选中某些元素信息
	$(function (){
		var taskId = <%=session.getAttribute("stageTaskId")%>;
		if(taskId){
			$("#declareItems").click();
			$("#item"+taskId).click();
		}
	});
</script>
</head>
<body>
	<form method="post" name="form1" id="form1">
		<input disabled type="hidden" name="prjInstanceVo.prjType" value="1">
		<input disabled type="hidden" id="key" name="prjInstanceVo.id"
			value="${project.prjInstanceVo.id}">
		<div class="card">
			<div class="card-header">
				<h2>
					政府项目受理详情<small>您可通过本功能查看政府项目受理详情</small>
				</h2>
				<ul class="actions">
					<li>
						<button data-toggle="tooltip" data-placement="bottom"
							type="button" title="返回" class="btn btn-success btn-icon m-r-5"
							<%--onclick="javascrtpt:window.location.href='${ctx}/project/index/'"--%>
								onclick="javascrtpt:history.go(-1)">
							<i class="md md-arrow-back"></i>
						</button>
					</li>
					<li>
						<button data-toggle="tooltip" data-placement="bottom"
							type="button" title="刷新" class="btn btn-default btn-icon m-r-5"
							onclick="refresh();">
							<i class="md md-autorenew"></i>
						</button>
					</li>
				</ul>
			</div>
			<div class="card-body card-padding">
				<div class="row">
					<div role="tabpanel" class="tab">
						<ul class="tab-nav" role="tablist" tabindex="3"
							style="overflow: hidden; outline: none;">
							<li class="active"><a href="#basic" aria-controls="basic"
								role="tab" data-toggle="tab">基本信息</a></li>
							<li role="presentation"><a href="#tasksDiv" id="declareItems"
								aria-controls="tasksDiv" role="tab" data-toggle="tab">申报事项</a></li>
						</ul>

						<div class="tab-content">
							<div role="tabpanel"
								class="tab-pane active animated fadeInRight in" id="basic">
								<div class="row">
									<div class="col-xs-6 form-group">
										<label class="control-label">区项目编号：</label>
										<div class="fg-line disabled">
											<input disabled type="text" name="prjInstanceVo.prjCode"
												value="${project.prjInstanceVo.prjCode}"
												class="form-control" id="projectCode" placeholder="区项目编号">
										</div>
									</div>
									<div class="col-xs-6 form-group">
										<label class="control-label">省项目编号：</label>
										<div class="fg-line disabled">
											<input disabled type="text" name="prjInstanceVo.prjCode"
												   value="${project.prjInstanceVo.shengPrjCode}"
												   class="form-control" id="shengProjectCode" placeholder="省项目编号">
										</div>
									</div>
									<div class="col-xs-4 form-group">
										<label class="control-label">项目类别：</label>
										<div class="fg-line disabled">
											<input disabled type="text" class="form-control"
												value="${project.prjInstanceVo.prjCat}" placeholder="项目类别"
												name="prjInstanceVo.prjCat">
										</div>
									</div>
									<div class="col-xs-4 form-group">
										<label class="control-label">电话：</label>
										<div class="fg-line disabled">
											<input disabled type="text" class="form-control"
												value="${project.prjInstanceVo.companyMphone}"
												name="prjInstanceVo.companyMphone" placeholder="电话">
										</div>
									</div>
									<div class="col-xs-4 form-group">
										<label class="control-label">传真：</label>
										<div class="fg-line disabled">
											<input disabled type="text" class="form-control"
												value="${project.prjInstanceVo.comapnyFax}"
												name="prjInstanceVo.comapnyFax" placeholder="电话">
										</div>
									</div>
									<div class="col-xs-4 form-group">
										<label class="control-label">建设单位：</label>
										<div class="fg-line disabled">
											<input name="prjInstanceVo.company" disabled="disabled"
												value="${project.prjInstanceVo.company}"
												class="form-control" placeholder="建设单位">
										</div>
									</div>
									<div class="col-xs-4 form-group">
										<label class="control-label">单位地址：</label>
										<div class="fg-line disabled">
											<input disabled type="text" class="form-control"
												value="${project.prjInstanceVo.companyAddr}"
												name="prjInstanceVo.companyAddr" placeholder="单位地址">
										</div>
									</div>
									<div class="col-xs-4 form-group">
										<label class="control-label">建设单位企业信用代码或组织机构代码：</label>
										<div class="fg-line disabled">
											<input disabled type="text" class="form-control"
												value="${project.prjInstanceVo.companyCode}"
												name="prjInstanceVo.companyCode"
												placeholder="建设单位企业信用代码或组织机构代码">
										</div>
									</div>
									<div class="col-xs-4 form-group">
										<label class="control-label">法人代表：</label>
										<div class="fg-line disabled">
											<input disabled type="text" class="form-control"
												value="${project.prjInstanceVo.legalEntity}"
												name="prjInstanceVo.legalEntity" placeholder="法人代表">
										</div>
									</div>
									<div class="col-xs-4 form-group">
										<label class="control-label">手机（法人代表）：</label>
										<div class="fg-line disabled">
											<input disabled type="text" class="form-control"
												value="${project.prjInstanceVo.entityMphone}"
												name="prjInstanceVo.entityMphone" placeholder="手机">
										</div>
									</div>
									<div class="col-xs-4 form-group">
										<label class="control-label">办公电话（法人代表）：</label>
										<div class="fg-line disabled">
											<input disabled type="text" class="form-control"
												value="${project.prjInstanceVo.entityPhone}"
												name="prjInstanceVo.entityPhone" placeholder="办公电话">
										</div>
									</div>
									<div class="col-xs-4 form-group">
										<label class="control-label">受委托人：</label>
										<div class="fg-line disabled">
											<input disabled type="text" class="form-control"
												value="${project.prjInstanceVo.agentName}"
												name="prjInstanceVo.agentName" placeholder="法人代表">
										</div>
									</div>
									<div class="col-xs-4 form-group">
										<label class="control-label">手机（受委托人）：</label>
										<div class="fg-line disabled">
											<input disabled type="text" class="form-control"
												value="${project.prjInstanceVo.agentMphone}"
												name="prjInstanceVo.agentMphone" placeholder="手机">
										</div>
									</div>
									<div class="col-xs-4 form-group">
										<label class="control-label">办公电话（受委托人）：</label>
										<div class="fg-line disabled">
											<input disabled type="text" class="form-control"
												value="${project.prjInstanceVo.agentPhone}"
												name="prjInstanceVo.agentPhone" placeholder="办公电话">
										</div>
									</div>
									<div class="col-xs-4 form-group">
										<label class="control-label">项目名称：</label>
										<div class="fg-line disabled">
											<input disabled type="text" class="form-control"
												value="${project.prjInstanceVo.prjName}"
												name="prjInstanceVo.prjName" placeholder="项目名称"
												id="projectName">
										</div>
									</div>
									<div class="col-xs-4 form-group">
										<label class="control-label">项目性质：</label>
										<div class="fg-line disabled">
											<input disabled type="text" class="form-control"
												value="${project.prjInstanceVo.prjNature}"
												name="prjInstanceVo.prjNature" placeholder="项目性质">
										</div>
									</div>
									<div class="col-xs-4 form-group">
										<label class="control-label">项目地址：</label>
										<div class="fg-line disabled">
											<input disabled type="text" class="form-control"
												value="${project.prjInstanceVo.prjAddr}"
												name="prjInstanceVo.prjAddr" placeholder="项目地址">
										</div>
									</div>
									<div class="col-xs-4 form-group">
										<label class="control-label">总建筑面积(m<sup>2</sup>)：
										</label>
										<div class="fg-line disabled">
											<input disabled type="text" class="form-control"
												value="${project.prjInstanceVo.prjFloorSpace}"
												name="prjInstanceVo.prjFloorSpace" placeholder="总建筑面积">
										</div>
									</div>
									<div class="col-xs-4 form-group">
										<label class="control-label">占地面积(m<sup>2</sup>)：
										</label>
										<div class="fg-line disabled">
											<input disabled type="text" class="form-control"
												value="${project.prjInstanceVo.prjRedlineSpace}"
												name="prjInstanceVo.prjRedlineSpace" placeholder="占地面积">
										</div>
									</div>
									<div class="col-xs-4 form-group">
										<label class="control-label">项目规模及内容：</label>
										<div class="fg-line disabled">
											<input disabled type="text" class="form-control"
												value="${project.prjInstanceVo.prjDescription}"
												name="prjInstanceVo.prjDescription" placeholder="项目规模及内容">
										</div>
									</div>
									<div class="col-xs-6 form-group">
										<label class="control-label">投资估算(万元)：</label>
										<div class="fg-line disabled">
											<input disabled type="text" class="form-control"
												value="${project.prjInstanceVo.investEstimate}"
												name="prjInstanceVo.investEstimate" placeholder="投资估算(万元)">
										</div>
									</div>
									<div class="col-xs-6 form-group">
										<label class="control-label">资金来源：</label>
										<div class="fg-line disabled">
											<input disabled type="text" class="form-control"
												value="${project.prjInstanceVo.fundSource}"
												name="prjInstanceVo.fundSource" placeholder="资金来源">
										</div>
									</div>
									<div class="col-xs-6 form-group">
										<label class="control-label">相关资料文件描述： </label>
										<div class="fg-line disabled">
											<input type="text" disabled class="form-control required"
												maxlength="5" value="${project.prjInstanceVo.preFilesDesc}"
												name="prjInstanceVo.preFilesDesc" placeholder="相关资料文件描述">
										</div>
									</div>
									<div class="col-xs-6 form-group">
										<label class="control-label">相关资料文件： </label>
										<div class="fg-line">
											<c:if test="${project.prjInstanceVo.preFilesAddr ne ''}">
												<button data-placement="bottom" type="button" title="下载"
													class="btn btn-icon btn-file bgm-lightblue"
													style="margin-left: 40px" type="button"
													onClick="downFile('${project.prjInstanceVo.preFilesAddr}','${project.prjInstanceVo.preFilesName}')">
													<i class="md md-file-download"></i>
												</button>
												<button data-placement="bottom" type="button" title="预览"
													onclick="window.open('${ctx}/sys/filePreview?pathUrl=${project.prjInstanceVo.preFilesAddr}')" 
													class="btn btn-success btn-icon btn-file">
													<i class="md md-visibility"></i>
												</button>
											</c:if>
											<c:if test="${project.prjInstanceVo.preFilesAddr eq ''}">
												<input type="text" name="no11" disabled class="form-control" placeholder="未上传">
											</c:if>
										</div>
									</div>
									
									<c:if test="${project.prjInstanceVo.isPrjComplete eq '9'}">
										<div class="col-xs-6 form-group">
											<label class="control-label">项目终止原因： </label>
											<div class="fg-line disabled">
												<input type="text" disabled class="form-control required"
													maxlength="5" value="${project.prjInstanceVo.stopReason}"
													name="prjInstanceVo.stopReason">
											</div>
										</div>
										<div class="col-xs-6 form-group">
											<label class="control-label">项目终止文件： </label>
											<div class="fg-line">
												<c:if test="${project.prjInstanceVo.stopFileAddr ne ''}">
													<button data-placement="bottom" type="button" title="下载"
														class="btn btn-icon btn-file bgm-lightblue"
														style="margin-left: 40px" type="button"
														onClick="downFile('${project.prjInstanceVo.stopFileAddr}','${project.prjInstanceVo.stopFileName}')">
														<i class="md md-file-download"></i>
													</button>
													<button data-placement="bottom" type="button" title="预览"
														onclick="window.open('${ctx}/sys/filePreview?pathUrl=${project.prjInstanceVo.stopFileAddr}')" 
														class="btn btn-success btn-icon btn-file">
														<i class="md md-visibility"></i>
													</button>
												</c:if>
											</div>
										</div>
									</c:if>
									
									<c:if test="${project.prjStageVo.stageStatus ne '0'}">
										<div class="col-xs-12 form-group">
											<img class="col-xs-offset-5 col-xs-2"
												src="${project.prjInstanceVo.lpcodeAddr}" alt="项目龙贝码">
										</div>
									</c:if>
								</div>
							</div>
							<div role="tabpanel" class="tab-pane animated fadeInRight" id="tasksDiv">
								<c:forEach items="${project.prjStageVoList}" var="stage"
										varStatus="c">
										<div role="tabpanel"
											class="tab-pane animated fadeInRight ${c.index eq 0?'active':''}"
											id="stage${c.index}">
											<div class="table-responsive">
												<table class="table table-striped">
													<thead>
														<tr>
															<th>事项名称</th>
															<th>事项状态</th>
															<th>申请信息</th>
														</tr>
													</thead>
													<tbody>
														<c:forEach
															items="${fns:getAllTaskByInstanceStage(stage.id,project.prjInstanceVo.id)}"
															var="task" varStatus="t">
															<tr>
																<td>
																	<c:choose>
																		<c:when test="${task.taskStatus eq 0 or task.taskStatus eq 5}">
																			${task.taskName}
																		</c:when>
																		<c:otherwise>
																			<a data-toggle="modal" href="javascript:void(0)" title="审批详情查看" onclick="ajaxHandle(${task.id})">${task.taskName}</a>
																		</c:otherwise>
																	</c:choose>
																
																</td>
																<td>
																	<c:if test="${project.prjInstanceVo.isPrjComplete eq '9'}">
																		终止(
																	</c:if>
																	<c:if test="${task.taskStatus eq 0}">暂存</c:if>
																	<c:if test="${task.taskStatus eq 1}">审批中</c:if> 
																	<c:if test="${task.taskStatus eq 2}">
																		暂停(${fns:getDictLabel(task.taskPauseType, 'task_pause_type', '')}
																			<c:if test="${task.taskPauseType == 99}">
																				其他
																			</c:if>
																		)<br/>
																		<fmt:formatDate value="${task.taskPauseStartTime}" pattern="yyyy-MM-dd" />
																	</c:if> 
																	<c:if test="${task.taskStatus eq 4}">已办结</c:if>
																	<c:if test="${task.taskStatus eq 5}">未启动</c:if>
																	<c:if test="${task.taskStatus eq 6}">不通过</c:if>
																	<c:if test="${task.taskStatus eq 7}">已完成</c:if>
																	<c:if test="${task.taskStatus eq 8}">免办</c:if>
																	<c:if test="${project.prjInstanceVo.isPrjComplete eq '9'}">
																		)
																	</c:if>
																</td>
																<td>
																	<a id="item${task.taskId}" data-toggle="modal" href="javascript:void(0)" title="事项详情查看" onclick="openTask(${task.taskId},${project.prjInstanceVo.id})">查看事项</a>
																</td>
															</tr>
														</c:forEach>
													</tbody>
												</table>
											</div>
										</div>
									</c:forEach>
							</div>
						</div>
					</div>
					<div class="btn-demo text-center">
						<%-- 判断是否是网上办事列表的查看详情，以便返回正确的列表 --%>
						<c:if test="${not empty isWsbs}">
							<button class="btn btn-default waves-effect" type="button"
								onclick="javascrtpt:window.location.href='${ctx}/project/wsbslist'">返回</button>
						</c:if>
						<c:if test="${empty isWsbs}">
							<button class="btn btn-default waves-effect" type="button"
								onclick="javascrtpt:window.location.href='${ctx}/project/list'">返回</button>
						</c:if>
					</div>
				</div>
			</div>
		</div>
	</form>
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
</body>
</html>