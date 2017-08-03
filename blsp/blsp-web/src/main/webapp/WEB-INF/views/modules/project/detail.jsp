<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>项目追踪</title>
<meta name="decorator" content="blank" />
<script>
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
function downFile(url,name){
	var u="${ctx}/sys/download?pathUrl=";
	u=u+url+"&coi="+encodeURI(name);
	window.location.href=u;
}

function ajaxLoadMaterial(prjInsId,stageId) {
	$.ajax({
		type : 'post',
		url :'${ctx}/project/load/material',
		data : {"prjInsId":prjInsId,"stageId":stageId},
		cache : false,
		dataType : 'html',
		success : function(data) {
			$("#ajaxMaterial").html(data);
			
			// 只有第一次点击“申请信息”时加载数据
			if (stageId == '') {
				$("#sqxxId").removeAttr("onclick");
			}
		}
	});
}

	function ajaxDetail(taskId,id){
		openWindow({
			id:'detailTable',
			title:'申请信息',
			url:'${ctx}/project/itemDetail',
			width:'1000px',
			height:'500px',
			params:{"taskId":taskId,"id":id,"view":"2"},
		});
	}
	
	//判定是否是返回详情的数据，若是则自动选中某些元素信息
	$(function (){
		var taskId = <%=session.getAttribute("stageTaskId")%>;
		if(taskId){
			$("#item"+taskId).click();
			var stageId = <%=session.getAttribute("stageStageId")%>;
			if(stageId){
				$("#stage"+stageId).click();
			}
		}
	});

</script>
</head>
<body>
	<div class="card">
		<div class="card-header">
			<h2>
				项目信息<small>您可通过本功能对项目申请信息及审批详情进行查看。</small>
			</h2>
			<ul class="actions">
				<li>
					<button data-toggle="tooltip" data-placement="bottom" title="返回"
						class="btn btn-success btn-icon m-r-5"
					onclick="javascrtpt:window.location.href='${ctx}/project/queryList/'"/>
						<i class="md md-arrow-back"></i>
					</button>
				</li>
				<li>
					<button data-toggle="tooltip" data-placement="bottom" type="button"
						title="刷新" class="btn btn-default btn-icon m-r-5"
						onclick="refresh();">
						<i class="md md-autorenew"></i>
					</button>
				</li>
			</ul>
		</div>
		<div class="card-body card-padding">

			<div class="row">
				<div class="col-xs-6 form-group">
					<label class="control-label">区项目编号：${project.prjInstanceVo.prjCode}</label>
				</div>
				<div class="col-xs-6 form-group">
					<label class="control-label">项目名称：${project.prjInstanceVo.prjName}</label>
				</div>
				<div class="col-xs-6 form-group">
					<label class="control-label">建设单位：${project.prjInstanceVo.company}</label>
				</div>
				<div class="col-xs-6 form-group">
					<label class="control-label">电话：${project.prjInstanceVo.companyMphone}</label>
				</div>
			</div>

			<div class="panel-group" role="tablist" aria-multiselectable="true">
				<div class="panel panel-collapse">
					<div class="panel-heading" role="tab" id="headingOne">
						<h4 class="panel-title">
							<a class="collapsed" data-toggle="collapse"
								data-parent="#accordion" href="#collapseOne"
								aria-expanded="false" aria-controls="collapseOne"> 项目基本信息 </a>
						</h4>
					</div>
					<div id="collapseOne" class="collapse" role="tabpanel"
						aria-labelledby="headingOne">
						<div class="panel-body">
							<div class="row">
								<div class="col-xs-6 form-group">
									<label class="control-label">区项目编号：${project.prjInstanceVo.prjCode}</label>
								</div>
								<div class="col-xs-6 form-group">
									<label class="control-label">省项目编号：${project.prjInstanceVo.shengPrjCode}</label>
								</div>
								<div class="col-xs-4 form-group">
									<label class="control-label">项目类别：${project.prjInstanceVo.prjCat}</label>
								</div>
								<div class="col-xs-4 form-group">
									<label class="control-label">电话：${project.prjInstanceVo.companyMphone}</label>
								</div>
								<div class="col-xs-4 form-group">
									<label class="control-label">传真：${project.prjInstanceVo.comapnyFax}</label>
								</div>
								<div class="col-xs-4 form-group">
									<label class="control-label">建设单位：${project.prjInstanceVo.company}</label>
								</div>
								<div class="col-xs-4 form-group">
									<label class="control-label">单位地址：${project.prjInstanceVo.companyAddr}</label>
								</div>
								<div class="col-xs-4 form-group">
									<label class="control-label">建设单位企业信用代码或组织机构代码：${project.prjInstanceVo.companyCode}</label>
								</div>
								<div class="col-xs-4 form-group">
									<label class="control-label">法人代表：${project.prjInstanceVo.legalEntity}</label>
								</div>
								<div class="col-xs-4 form-group">
									<label class="control-label">手机（法人代表）：${project.prjInstanceVo.entityMphone}</label>
								</div>
								<div class="col-xs-4 form-group">
									<label class="control-label">办公（法人代表）：${project.prjInstanceVo.entityPhone}</label>
								</div>
								<div class="col-xs-4 form-group">
									<label class="control-label">受委托人：${project.prjInstanceVo.agentName}</label>
								</div>
								<div class="col-xs-4 form-group">
									<label class="control-label">手机（受委托人）：${project.prjInstanceVo.agentMphone}</label>
								</div>
								<div class="col-xs-4 form-group">
									<label class="control-label">办公（受委托人）：${project.prjInstanceVo.agentPhone}</label>
								</div>
								<div class="col-xs-4 form-group">
									<label class="control-label">项目名称：${project.prjInstanceVo.prjName}</label>
								</div>
								<div class="col-xs-4 form-group">
									<label class="control-label">项目性质：${project.prjInstanceVo.prjNature}</label>
								</div>
								<div class="col-xs-4 form-group">
									<label class="control-label">项目地址：${project.prjInstanceVo.prjAddr}</label>
								</div>
								<div class="col-xs-4 form-group">
									<label class="control-label">总建筑面积：${project.prjInstanceVo.prjFloorSpace}(m<sup>2</sup>)
									</label>
								</div>
								<div class="col-xs-4 form-group">
									<label class="control-label">占地面积：${project.prjInstanceVo.prjRedlineSpace}(m<sup>2</sup>)
									</label>
								</div>
								<div class="col-xs-4 form-group">
									<label class="control-label">项目规模及内容：${project.prjInstanceVo.prjDescription}</label>
								</div>
								<div class="col-xs-4 form-group">
									<label class="control-label">投资估算：${project.prjInstanceVo.investEstimate}</label>
								</div>
								<div class="col-xs-6 form-group">
									<label class="control-label">资金来源：${project.prjInstanceVo.fundSource}</label>
								</div>
								<div class="col-xs-4 form-group">
									<label class="control-label">相关资料文件描述：${project.prjInstanceVo.preFilesDesc}</label>
								</div>
								<div class="col-xs-6 form-group">
									<label class="control-label">相关资料文件：<c:if
											test="${project.prjInstanceVo.preFilesAddr ne ''}">
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
										</c:if></label>
								</div>
								
								<c:if test="${project.prjInstanceVo.isPrjComplete eq '9'}">
									<div class="col-xs-4 form-group">
										<label class="control-label">项目终止原因：${project.prjInstanceVo.stopReason}</label>
									</div>
									<div class="col-xs-6 form-group">
										<label class="control-label">项目终止文件：<c:if
												test="${project.prjInstanceVo.stopFileAddr ne ''}">
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
											</c:if></label>
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
					</div>
				</div>
				<div class="panel panel-collapse">
					<div class="panel-heading" role="tab" id="headingThree">
						<h4 class="panel-title">
							<a data-toggle="collapse" data-parent="#accordion"
								href="#collapseThree" aria-expanded="true"
								aria-controls="collapseThree"> 审批信息 </a>
						</h4>
					</div>
					<div id="collapseThree" class="collapse in" role="tabpanel"
						aria-labelledby="headingThree">
						<div class="panel-body">
							<div class="table-responsive">
								<table class="table table-striped">
									<thead>
										<tr>
											<th>阶段</th>
											<c:if test="${project.prjInstanceVo.prjType == 1}">
												<th>下达任务书到施工招标（时限）</th>
												<th>下任务至完成施工图审核用时</th>
												<th>下任务至完成施工许可证用时</th>
												<th>下任务至完成验收用时</th>
											</c:if>
											<c:if test="${project.prjInstanceVo.prjType == 2}">
												<th>企业的叫审批用时（工作日）</th>
												<th>实际用时</th>
											</c:if>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${project.prjStageVoList}" var="stage"
											varStatus="var">
											<tr>
												<td>${fns:getStageDefineById(stage.stageId).stageName}</td>
												<td>${fns:getStageDefineById(stage.stageId).stageTimeLimit}</td>
												<c:if test="${project.prjInstanceVo.prjType == 1}">
													<td>${xrwzwcsgtshys}</td>
													<td>${xrwzwcsgxkzys}</td>
													<td>${xrwzwcysys}</td>
												</c:if>
												<c:if test="${project.prjInstanceVo.prjType == 2}">
													<td>${stage.stageDuration}</td>
												</c:if>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
				<div class="panel panel-collapse">
					<div class="panel-heading" role="tab" id="headingFour">
						<h4 class="panel-title">
							<a data-toggle="collapse" data-parent="#accordion"
								href="#collapseFour" aria-expanded="true"
								aria-controls="collapseFour"> 各阶段事项信息 </a>
						</h4>
					</div>
					<div id="collapseFour" class="collapse in" role="tabpanel"
						aria-labelledby="headingFour">
						<div class="panel-body">
							<div role="tabpanel">
								<ul class="tab-nav" role="tablist" data-tab-color="teal">
									<c:forEach items="${project.prjStageVoList}" var="stage"
										varStatus="c">
										<li class="${c.index eq 0 ?'active':''}"><a id="stage${stage.stageId}"
											href="#stage${c.index}" aria-controls="#stage${c.index}"
											role="tab" data-toggle="tab">
												${fns:getStageDefineById(stage.stageId).stageName} </a></li>
									</c:forEach>
								</ul>
								<div class="tab-content">
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
															<th>部门</th>
															<th>初始归属人</th>
															<th>当前归属人</th>
															<th class="col-xs-1">事项状态</th>
															<th>计时开始日期</th>
															<th class="col-xs-1">审批时限</th>
															<th>实际用时</th>
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
																<td>${task.deptName}</td>
																<td>${task.initUserName}</td>
																<td>${task.currUserName}</td>
																<td>
																	<c:if test="${project.prjInstanceVo.isPrjComplete eq '9'}">
																		<c:if test="${task.taskStatus eq 0}">终止（暂存）</c:if>
																		<c:if test="${task.taskStatus eq 1}">终止（审批中）</c:if> 
																		<c:if test="${task.taskStatus eq 2}">
																			终止（暂停（${fns:getDictLabel(task.taskPauseType, 'task_pause_type', '')}
																				<c:if test="${task.taskPauseType == 99}">
																					其他
																				</c:if>
																			）<br/>
																			<fmt:formatDate value="${task.taskPauseStartTime}" pattern="yyyy-MM-dd" />）
																		</c:if> 
																		<c:if test="${task.taskStatus eq 4}">终止（已办结）</c:if>
																		<c:if test="${task.taskStatus eq 5}">终止（未启动）</c:if>
																		<c:if test="${task.taskStatus eq 6}">终止（不通过）</c:if>
																		<c:if test="${task.taskStatus eq 7}">终止（已完成）</c:if>
																		<c:if test="${task.taskStatus eq 8}">终止（免办）</c:if>
																	</c:if>
																	<c:if test="${project.prjInstanceVo.isPrjComplete ne '9'}">
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
																	</c:if>
																</td>
																<td><fmt:formatDate value="${task.taskStartTime}" pattern="yyyy-MM-dd" /></td>
																<td>${task.taskDueDuration}<c:if test="${task.taskDueDuration ne null}">${task.timeType eq '1'?"自然日":"工作日"}</c:if></td>
																<td>${task.taskDuration}</td>
																<td>
																	<a data-toggle="modal" id="item${task.taskId}" href="javascript:void(0)" title="事项详情查看" onclick="ajaxDetail(${task.taskId},${project.prjInstanceVo.id})">查看事项</a>
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
					</div>
				</div>
			</div>
		</div>
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
</body>
</html>