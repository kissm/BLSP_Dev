<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>审批效能监管</title>
<meta name="decorator" content="blank" />
<style>
@media ( min-width : 1200px) {
	.container {
		width: auto;
	}
}
</style>
<script>
localStorage.setItem('ma-layout-status', 0);
function dateSwitch(obj,type){
	var reportDate = $("#reportDateStr").val();
	this.location.href="${ctx}/project/report/report?type=1&dType="+type+"&reportDate="+reportDate;
}
function project(type,dType,status){
	var reportDate = $("#reportDateStr").val();
	$('#modalContent').attr('src','${ctx}/project/report/project?type='+type+'&dType='+dType+'&status='+status+'&reportDate='+reportDate);
	$('#modal').modal('show');
}
function task(type,dType,taskid,taskType){
	var reportDate = $("#reportDateStr").val();
	$('#modalContent').attr('src','${ctx}/project/report/task?type='+type+'&dType='+dType+'&taskid='+taskid+'&taskType='+taskType+'&reportDate='+reportDate);
	$('#modal').modal('show');
}

$(document).ready(function(){
	$('.dropdown-toggle').on('click.bs.dropdown', function () {
		$(this).next('ul').empty();
		var type=$(this).attr('type');
		var dType=$(this).attr('dType');
		var taskid=$(this).attr('taskid');
		var reportDate=$(this).attr('reportDate');
		var dropdown_toggle = this;
		$.ajax({
			type : 'post',
			url : '${ctx}/project/report/taskid',
			data : {"type":type,
				   "dType":dType,
				   "taskid":taskid,
				   "reportDate":reportDate
				  },
			cache : false,
			dataType : 'html',
			success : function(data) {
				$(dropdown_toggle).next('ul').append(data);
			}
		});
	});
});
</script>
</head>
<body>
	<div class="card">
		<div class="card-header p-b-15">
			<h2>
				审批效能监管<small>政府项目与企业项目可视化面板</small>
			</h2>
		</div>
		<div class="card-body card-padding p-t-0 p-b-0">
			<div class="row">
				<div role="tabpanel" class="tab">
					<ul class="tab-nav" role="tablist" tabindex="3"
						style="overflow: hidden; outline: none;">
						<li class="active"><a href="${ctx}/project/report/report?type=1&dType=${dType}">政府项目</a></li>
						<li ><a href="${ctx}/project/report/report?type=2&dType=${dType}">企业项目</a></li>
					</ul>

					<div class="m-t-10">
						<div class="col-xs-4">
							<div class="btn-group">
								<button type="button" class="btn btn-default" disabled>监控周期</button>
								<button type="button" class="btn ${dType==1?'btn-success':'btn-default'}"  onclick="dateSwitch(this,1)">本日</button>
								<button type="button" class="btn ${dType==2?'btn-success':'btn-default'}"  onclick="dateSwitch(this,2)">本周</button>
								<button type="button" class="btn ${dType==3?'btn-success':'btn-default'}"  onclick="dateSwitch(this,3)">本月</button>
								<button type="button" class="btn ${dType==4?'btn-success':'btn-default'}"  onclick="dateSwitch(this,4)">本季</button>
								<button type="button" class="btn ${dType==5?'btn-success':'btn-default'}"  onclick="dateSwitch(this,5)">本年</button>
							</div>
						</div>
						<div class="col-xs-2 input-group">
							<span class="input-group-addon"><i class="md md-event"></i></span>
							<div class="dtp-container dropdown fg-line">
								<input type="text" id="reportDateStr" name="reportDateStr" value="${reportDate}" class="form-control date-picker" data-toggle="dropdown" placeholder="监控日期" aria-expanded="false">
							</div>
						</div>
					</div>
					<div class="chart m-t-10 m-b-10 p-25">
						<div class="h4 text-center m-0">
							<a href="javascript:void(0);" onClick="project(${type},${dType},5)">下任务数:${fns:getAllPrjInTime(type,dType,reportDate)}</a>&nbsp;&nbsp;&nbsp;&nbsp;
							<span class="c-blue">办理项目数:${report.processNum + report.overNum + report.stopNum} </span>&nbsp;&nbsp;&nbsp;&nbsp;
							<a href="javascript:void(0);" onClick="project(${type},${dType},6)">审批中项目数:${report.processNum}</a>&nbsp;&nbsp;&nbsp;&nbsp;
							<a href="javascript:void(0);" onClick="project(${type},${dType},2)">完成项目数:${report.overNum}</a>&nbsp;&nbsp;&nbsp;&nbsp;
							<a href="javascript:void(0);" onClick="project(${type},${dType},4)">终止项目数:${report.stopNum}</a>
						</div>
						<div class="row">
							<div class="col-sm-2 r-l">
								<div class="max-round small bgm-deeporange" >
									<div class="d-round d-r-t bgm-deeporange" onClick="project(${type},${dType},1)">
										新项目<br />受理 <i class="m-r-counts bgm-orange" >${report.acceptNum}</i>
									</div>
									受理的数量
								</div>
							</div>
							<div class="col-sm-8">
								<div id="chart_id" class="col-sm-12">
									<div class="link clearfix">
										<c:forEach items="${report.projectStageVoList}" var="stage" varStatus="c">
											<c:forEach items="${stage.stageTaskVo.stageTask}" var="task" varStatus="t">
												<c:if test="${officeViewMap.get(task.key) != true }">
													<div class="l-b">
														<div class="l-b-i lock clearfix">
															<div class="l-b-i-l waves-effect">
																	${fns:getOffice(task.key).remarks}
															</div>
															<div class="l-b-i-r">
																<i class="md md-lock c-gray"></i>
															</div>
														</div>
													</div>
												</c:if>
												<c:if test="${officeViewMap.get(task.key)}">
													<div class="l-b">
														<div class="l-b-i clearfix">
															<div class="l-b-i-l waves-effect">
																	${fns:getOffice(task.key).remarks}
															</div>
															<div class="l-b-i-r">
																<div>
																	审批中<i class="m-r-counts bgm-blue">${fns:getTaskApprovalStatus(task.key,stage.stageid)}</i>
																</div>
																<div>
																	已暂停<i class="m-r-counts bgm-orange">${fns:getTaskPauseStatus(task.key,stage.stageid)}</i>
																</div>
																<div>
																	超时未办结<i class="m-r-counts bgm-red">${fns:getTaskOverTimeStatus(task.key,stage.stageid)}</i>
																</div>
																<div>
																	已办结<i class="m-r-counts bgm-green">${fns:getTaskAchieveStatus(task.key,stage.stageid,dType)}</i>
																</div>
															</div>
														</div>

														<div class="l-body">
															<c:forEach items="${task.value}" var="taskvo" varStatus="v">
																<div class="dropdown l-item">
																	<div class="dropdown-toggle" data-toggle="dropdown" type="${type}" dType="${dType}" taskid="${taskvo.taskid}">
																			${taskvo.taskName}
																		<c:if test="${fns:getTaskOverTimeStatusByTaskId(taskvo.taskid) ne 0}"><i class="m-r-counts bgm-red">${fns:getTaskOverTimeStatusByTaskId(taskvo.taskid)}</i></c:if>
																	</div>
																	<ul class="dropdown-menu pull-left l-item-menu"></ul>
																</div>
															</c:forEach>
														</div>
													</div>
												</c:if>
											</c:forEach>
										</c:forEach>
									</div>
								</div>
							</div>
							<div class="col-sm-2 r-r">
								<div class="max-round small m-r-l bgm-lightgreen">
									<div class="d-round d-r-t bgm-deeporange" onClick="project(${type},${dType},2)">
										完成<br />项目数 <i class="m-r-counts bgm-orange" >${report.overNum}</i>
									</div>
									<div class="d-round d-r-b bgm-deeporange" onClick="project(${type},${dType},4)">
										终止<br />项目数 <i class="m-r-counts bgm-orange" >${report.stopNum}</i>
									</div>
									结束的数量
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal fade" data-modal-color="cyan" id="modal" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">项目列表</h4>
				</div>
				<div style="height:400px;">
					<iframe id="modalContent" width="100%" height="100%" frameborder="0"></iframe>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-link" data-dismiss="modal">关闭</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>