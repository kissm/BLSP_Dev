<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>在线预审受理</title>
<meta name="decorator" content="blank" />
<script>
	$(document).ready(function() {
		$("#form1").validate({
			rules: {
				'prjInstanceVo.prjName': {
					remote: {
						type:"POST",
						url:"${ctx}/project/accpet/checkProjectName?oldProjectName="+ encodeURIComponent('${project.prjInstanceVo.prjName}'),
						data:{
							prjName:function(){return $("#prjName").val();}
						}
					}
				}
			},
			messages: {
				'prjInstanceVo.prjName': {remote: "该项目名称已存在"},
			},
			submitHandler : function(form) {
				form.submit();
			}
		});
		var validator = $("#formReason").validate({
			rules: {
			},
			messages: {
			},
			submitHandler: function(form){
				form.submit();
			}
		});
		$('#confirm').click(function(){
			var compayMap = sessionStorage.getItem("compayMap");
			if(compayMap  != null ){
				var compay = JSON.parse(compayMap);
				console.log(compay);
				$("#prjInstanceVo_companyMphone").val(compay.companyMphone);
				$("#prjInstanceVo_comapnyFax").val(compay.comapnyFax);
				$("#prjInstanceVo_company").val(compay.company); //建设单位名称;
				$("#prjInstanceVo_companyAddr").val(compay.companyAddr);//单位地址
				$("#prjInstanceVo_companyCode").val(compay.companyCode); //建设单位企业信用代码或组织机构代码
				$("#prjInstanceVo_legalEntity").val(compay.legalEntity); //法人代表
				$("#prjInstanceVo_entityMphone").val(compay.entityMphone); //手机（法人代表）
				$("#prjInstanceVo_entityPhone").val(compay.entityPhone); //办公电话（法人代表）
				$("#prjCompanyCode").val(compay.companyCode);
			}
			$('#approve').modal('hide');
		});

		$('#approve').on('show.bs.modal', function () {
			var url = '${ctx}/project/accpet/project?prjCompanyCode=' + $("#prjCompanyCode").val();
			document.getElementById("buildIframe").src = url;
		});
		$("#t_confirm").click(function (){
			if(validator.form()){
				if ($('#pauseDesc').val() == '') {
					swal("请填写退回原因！", "", "error");
				} else {
					$('#falseReason').modal('hide');
					$("#prjPauseDesc").val($('#pauseDesc').val());
					$("#form1").submit();
				}
			}
		});
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
			params:{"taskId":taskId,"id":id,"view":"5"},
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
	function pretrial(n){
		if(n == 2){
			if($("#stageId").val() > 11){
				openWindow({
					id:'blspTzjs',
					title:'需要预处理的阶段事项',
					url:'${ctx}/project/showStageTask?stageId='+$("#stageId").val(),
					width:'900px',
					height:'560px',
					callBack : function(data){
						console.log(data);
						$("#taskProcess").val(JSON.stringify(data));
						$(".preDis").attr("disabled",true);
						if($("#form1").valid()){
							$("#applyState").val(n);
							$("#form1").submit();
						}else{
							$(".preDis").attr("disabled",false);
						}
					}
				});
//				return;
			}else{
				$(".preDis").attr("disabled",true);
				if($("#form1").valid()){
					$("#applyState").val(n);
					$("#form1").submit();
				}else{
					$(".preDis").attr("disabled",false);
				}
			}
		}else{
			$("#applyState").val(n);
			$("#falseReason").modal('show');
		}
	}
	//判定是否是返回详情的数据，若是则自动选中某些元素信息
	$(function (){
		var taskId = <%=session.getAttribute("stageTaskId")%>;
		if(taskId){
			$("#declareItems").click();
			$("#item"+taskId).click();
		}
	});

	function clearPrjCode(){
		$("#prjCode").val("");
	}

	function selectBlspTzjs(prjType,prjCode){
		openWindow({
			id:'blspTzjs',
			title:'并联审批项目信息',
			url:'${ctx}/project/plusadd/getOldBlspList?prjType='+prjType+'&prjCode='+prjCode,
			width:'900px',
			height:'560px',
			callBack : function(data){
				if(data != null && data.length >0){
					for(var index in data){
						$("#"+data[index].name).val(data[index].value);
					}
				}
			}
		});
	}

</script>
</head>
<body>
	<form method="post" name="form1" id="form1" action="${ctx}/project/pretrialWsbs">
		<div class="card">
			<div class="card-header">
				<h2>
					在线预审受理<small>您可通过本功能受理网上办事项目</small>
				</h2>
				<ul class="actions">
					<li>
						<button data-toggle="tooltip" data-placement="bottom"
							type="button" title="返回" class="btn btn-success btn-icon m-r-5"
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
							<li role="presentation"><a href="#materialDiv" id="materialItems"
								aria-controls="materialDiv" role="tab" data-toggle="tab">申报材料</a></li>
						</ul>
						<div class="tab-content">
							<div role="tabpanel" class="tab-pane active animated fadeInRight in" id="basic">
								<div class="col-xs-12 form-group">
									<button type="button" class="btn bgm-green btn-sm" onclick="selectBlspTzjs(${project.prjInstanceVo.prjType},'${project.prjInstanceVo.prjCode}');" style="width:122px;">并联审批项目信息</button>
								</div>

								<div class="row">
									<input type="hidden" name="prjInstanceVo.taskProcess" id="taskProcess" value="${project.prjInstanceVo.taskProcess}"/>
									<input type="hidden" name="prjInstanceVo.prjType" value="${project.prjInstanceVo.prjType}">
									<input type="hidden" name="prjInstanceVo.priceType" value="${project.prjInstanceVo.priceType}">
									<input type="hidden" name="prjInstanceVo.landType" value="${project.prjInstanceVo.landType}">
									<input type="hidden" name="prjInstanceVo.useageType" value="${project.prjInstanceVo.useageType}">
									<input type="hidden" name="prjCodeGeneratorVo.id" value="${project.prjCodeGeneratorVo.id}">
									<input type="hidden" name="prjCodeGeneratorVo.seqType" value="${project.prjCodeGeneratorVo.seqType}">
									<input type="hidden" name="prjInstanceVo.id" value="${project.prjInstanceVo.id}">
									<input type="hidden" name="prjInstanceVo.isNeedPreAudit" value="${project.prjInstanceVo.isNeedPreAudit}">
									<input type="hidden" name="prjInstanceVo.isSpecialProject" value="${project.prjInstanceVo.isSpecialProject}">
									<input type="hidden" name="prjInstanceVo.isWithBasePart" value="${project.prjInstanceVo.isWithBasePart}">
									<input type="hidden" name="prjInstanceVo.isItType" value="${project.prjInstanceVo.isItType}">
									<input type="hidden" name="prjInstanceVo.isGovType" value="${project.prjInstanceVo.isGovType}">
									<input type="hidden" name="prjInstanceVo.acceptId" value="${project.prjInstanceVo.acceptId}">
									<input type="hidden" name="prjInstanceVo.applyState" id="applyState" value="${project.prjInstanceVo.applyState}">
									<input type="hidden" name="prjInstanceVo.stageId" value="${project.prjInstanceVo.stageId}" id="stageId">
									<input type="hidden" name="prjInstanceVo.lpcodeAddr" value="${project.prjInstanceVo.lpcodeAddr}">
									<input type="hidden" name="prjInstanceVo.channel" value="${project.prjInstanceVo.channel}">
									<input type="hidden" name="prjInstanceVo.isWithCert" value="${project.prjInstanceVo.isWithCert}">
									<input type="hidden" name="prjInstanceVo.creatTime" value='<fmt:formatDate value="${project.prjInstanceVo.creatTime}" pattern="yyyy-MM-dd hh:mm:ss" />'>
									<input type="hidden" name="prjInstanceVo.creator" value="${project.prjInstanceVo.creator}">
									<input type="hidden" name="prjInstanceVo.isDelete" value="${project.prjInstanceVo.isDelete}">
									<input type="hidden" name="prjInstanceVo.isStageComplete" value="${project.prjInstanceVo.isStageComplete}">
									<input type="hidden" name="prjInstanceVo.isPrjComplete" value="${project.prjInstanceVo.isPrjComplete}">
									<input type="hidden" name="prjInstanceVo.pauseDesc" id="prjPauseDesc" value="${project.prjInstanceVo.pauseDesc}" />
									<input type="hidden" name="prjInstanceVo.wsbsUserName" value="${project.prjInstanceVo.wsbsUserName}">
									<div class="col-xs-2 form-group">
										<label class="control-label">区项目编号：</label>
										<div class="fg-line readonly">
											<input type="text" name="prjInstanceVo.prjCode"
												maxlength="30" value="${project.prjInstanceVo.prjCode}"
												class="form-control" id="prjCode" readonly
												placeholder="区项目编号">
										</div>
									</div>
									<div class="col-xs-1 form-group">
										<label class="control-label"></label>
										<div class="fg-line readonly">
											<button type="button" style="width: 58px;" onclick="clearPrjCode()" class="btn btn-info btn-xs waves-effect">清空</button>
										</div>
									</div>
									<div class="col-xs-3 form-group">
										<label class="control-label">项目类别：</label>
										<div class="fg-line">
											<input type="text" class="form-control"
												maxlength="50" value="${project.prjInstanceVo.prjCat}"
												placeholder="项目类别" name="prjInstanceVo.prjCat">
										</div>
									</div>
									<div class="col-xs-3 form-group">
										<label class="control-label">电话：</label>
										<div class="fg-line">
											<input type="text" class="form-control phone"
												maxlength="15" id="prjInstanceVo_companyMphone"
												value="${project.prjInstanceVo.companyMphone}"
												name="prjInstanceVo.companyMphone" placeholder="电话">
										</div>
									</div>
									<div class="col-xs-3 form-group">
										<label class="control-label">传真：</label>
										<div class="fg-line">
											<input type="text" class="form-control fax" id = "prjInstanceVo_comapnyFax"
												maxlength="15" value="${project.prjInstanceVo.comapnyFax}"
												name="prjInstanceVo.comapnyFax" placeholder="传真">
										</div>
									</div>
									<div class="col-xs-3 form-group">
										<label class="control-label">建设单位：</label>
										<div class="fg-line">
											<input type="text" name="prjInstanceVo.company"
												data-bind="company" maxlength="200"
												class="form-control required" id="prjInstanceVo_company"
												value="${project.prjInstanceVo.company}" placeholder="建设单位">
										</div>
									</div>
									<div class="col-xs-1 form-group">
										<button type="button" class="btn btn-info waves-effect" data-toggle="modal" href="#approve" title="加载已有建设单位">
											<i class="md md-add"></i>
										</button>
									</div>
									<div class="col-xs-4 form-group">
										<label class="control-label">单位地址：</label>
										<div class="fg-line">
											<input type="text" class="form-control"
												data-bind="companyAddr" maxlength="200"  id="prjInstanceVo_companyAddr"
												value="${project.prjInstanceVo.companyAddr}"
												name="prjInstanceVo.companyAddr" placeholder="单位地址">
										</div>
									</div>

									<div class="col-xs-4 form-group">
										<label class="control-label">建设单位企业信用代码或组织机构代码：</label>
										<div class="fg-line">
											<input type="text" class="form-control" id="prjInstanceVo_companyCode"
												maxlength="25" value="${project.prjInstanceVo.companyCode}"
												name="prjInstanceVo.companyCode"
												placeholder="建设单位企业信用代码或组织机构代码">
										</div>
									</div>
									<div class="col-xs-4 form-group">
										<label class="control-label">法人代表：</label>
										<div class="fg-line">
											<input type="text" class="form-control"
												data-bind="legalEntity" maxlength="50" id="prjInstanceVo_legalEntity"
												value="${project.prjInstanceVo.legalEntity}"
												name="prjInstanceVo.legalEntity" placeholder="法人代表">
										</div>
									</div>
									<div class="col-xs-4 form-group">
										<label class="control-label">手机（法人代表）：</label>
										<div class="fg-line">
											<input type="text" class="form-control"
												data-bind="entityMphone" maxlength="15" id="prjInstanceVo_entityMphone"
												value="${project.prjInstanceVo.entityMphone}"
												name="prjInstanceVo.entityMphone" placeholder="手机">
										</div>
									</div>
									<div class="col-xs-4 form-group">
										<label class="control-label">办公电话（法人代表）：</label>
										<div class="fg-line">
											<input type="text" class="form-control phone" id="prjInstanceVo_entityPhone"
												maxlength="15" value="${project.prjInstanceVo.entityPhone}"
												name="prjInstanceVo.entityPhone" placeholder="办公电话">
										</div>
									</div>
									<div class="col-xs-4 form-group">
										<label class="control-label">受委托人：</label>
										<div class="fg-line">
											<input type="text" class="form-control"
												data-bind="agentName" maxlength="50"
												value="${project.prjInstanceVo.agentName}"
												name="prjInstanceVo.agentName" placeholder="受委托人">
										</div>
									</div>
									<div class="col-xs-4 form-group">
										<label class="control-label">手机（受委托人）：</label>
										<div class="fg-line">
											<input type="text" class="form-control  required mobile"
												data-bind="agentMphone" maxlength="15"
												value="${project.prjInstanceVo.agentMphone}"
												name="prjInstanceVo.agentMphone" placeholder="手机">
										</div>
									</div>
									<div class="col-xs-4 form-group">
										<label class="control-label">办公电话（受委托人）：</label>
										<div class="fg-line">
											<input type="text" class="form-control phone"
												data-bind="agentPhone" maxlength="15"
												value="${project.prjInstanceVo.agentPhone}"
												name="prjInstanceVo.agentPhone" placeholder="办公电话">
										</div>
									</div>
									<div class="col-xs-4 form-group">
										<label class="control-label">项目名称：</label>
										<div class="fg-line">
											<input type="text" class="form-control required" id="prjName"
												data-bind="prjName" maxlength="200"
												value="${project.prjInstanceVo.prjName}"
												name="prjInstanceVo.prjName" placeholder="项目名称" >
										</div>
									</div>
									<div class="col-xs-4 form-group">
										<label class="control-label">项目性质：</label>
										<div class="fg-line">
											<input type="text" class="form-control"
												maxlength="50" value="${project.prjInstanceVo.prjNature}"
												name="prjInstanceVo.prjNature" placeholder="项目性质">
										</div>
									</div>
									<div class="col-xs-4 form-group">
										<label class="control-label">项目地址：</label>
										<div class="fg-line">
											<input type="text" class="form-control"
												data-bind="prjAddr" maxlength="50"
												value="${project.prjInstanceVo.prjAddr}"
												name="prjInstanceVo.prjAddr" placeholder="项目地址">
										</div>
									</div>
									<div class="col-xs-4 form-group">
										<label class="control-label">总建筑面积(m<sup>2</sup>)：
										</label>
										<div class="fg-line">
											<input type="text" id="prjM" class="form-control"
												maxlength="200"
												value='${project.prjInstanceVo.prjFloorSpace}'
												name="prjInstanceVo.prjFloorSpace" placeholder="总建筑面积">
										</div>
									</div>
									<div class="col-xs-4 form-group">
										<label class="control-label">占地面积(m<sup>2</sup>)：
										</label>
										<div class="fg-line">
											<input type="text" id="prjMM" class="form-control"
												maxlength="200"
												value='${project.prjInstanceVo.prjRedlineSpace}'
												name="prjInstanceVo.prjRedlineSpace" placeholder="占地面积">
										</div>
									</div>
									<div class="col-xs-4 form-group">
										<label class="control-label">项目规模及内容：</label>
										<div class="fg-line">
											<input type="text" class="form-control"
												maxlength="500"
												value="${project.prjInstanceVo.prjDescription}"
												name="prjInstanceVo.prjDescription" placeholder="项目规模及内容">
										</div>
									</div>
									<div class="col-xs-6 form-group">
										<label class="control-label">投资估算： </label>
										<div class="fg-line">
											<input type="text" class="form-control"
												maxlength="200"
												value='${project.prjInstanceVo.investEstimate}'
												name="prjInstanceVo.investEstimate" placeholder="投资估算">
										</div>
									</div>
									<div class="col-xs-6 form-group">
										<label class="control-label">资金来源：</label>
										<div class="fg-line">
											<input type="text" class="form-control"
												maxlength="150" value="${project.prjInstanceVo.fundSource}"
												name="prjInstanceVo.fundSource" placeholder="资金来源">
										</div>
									</div>
									<div class="col-xs-6 form-group">
										<label class="control-label">相关资料文件描述： </label>
										<div class="fg-line">
											<input type="text" class="form-control"
												maxlength="150"
												value="${project.prjInstanceVo.preFilesDesc}"
												name="prjInstanceVo.preFilesDesc" placeholder="相关资料文件描述">
										</div>
									</div>
									<div class="col-xs-6 form-group">
										<label class="control-label">相关资料文件：</label>
										<div class="fg-line">
											<sys:file id="file" isPreview="true"
												downloadFileAddress="${project.prjInstanceVo.preFilesAddr}"
												downloadFileName="项目相关资料文件" cssClass="btn-info"
												fileName="prjInstanceVo.preFilesName"
												fileNameValue ="${project.prjInstanceVo.preFilesName}"
											    fileAddress="prjInstanceVo.preFilesAddr"
											    fileAddressValue="${project.prjInstanceVo.preFilesAddr}"/>
										</div>
									</div>
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
															<th style="width: 18%;">表单状态</th>
															<th style="width: 8%;">表单信息</th>
														</tr>
													</thead>
													<tbody>
														<c:forEach
															items="${fns:getAllTaskByInstanceStageAndFormInfo(stage.id,project.prjInstanceVo.id)}"
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
																	<c:if test="${task.prjId != null && task.prjId != '' && task.prjId != 0}">
																		已填写
																	</c:if>
																	<c:if test="${task.prjId == 0}">
																		未填写
																	</c:if>
																	<c:if test="${task.prjId == null || task.prjId == '' && task.prjId != 0}">
																		无表单
																	</c:if>
																</td>
																<td>
																	<c:if test="${task.prjId != null && task.prjId != ''}">
																		<a id="item${task.taskId}" data-toggle="modal" href="javascript:void(0)" title="表单详情查看" onclick="openTask(${task.taskId},${project.prjInstanceVo.id})">查看表单</a>
																	</c:if>
																</td>
															</tr>
														</c:forEach>
													</tbody>
												</table>
											</div>
										</div>
									</c:forEach>
							</div>
							
							<div role="tabpanel" class="tab-pane animated fadeInRight" id="materialDiv">
								<table id="materList" class="table table-striped table-vmiddle bootgrid-table">
									<thead>
										<tr>
											<%-- <th class="text-center col-xs-1">是否收齐</th> --%>
											<th class="text-center col-xs-1">序号</th>
											<th class="text-center">申请材料</th>
											<!-- <th class="text-center col-xs-2">需求情况</th>
											<th class="text-center col-xs-3">收件情况</th> -->
											<th class="text-center col-xs-1">是否提供</th>
											<th class="text-center col-xs-2">操作</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${pro.materialDefMap}" var="mater" varStatus="status">
											<tr date-mater-name="${mater.value.name}"
											${(mater.value.originalNumReal!=0&&!empty mater.value.originalNumReal)||(mater.value.copyNumReal!=0&&!empty mater.value.copyNumReal)?'date-mater-isComplete="1"':''}>
												<%--<td class="text-center"></td> --%>
												<td>
													${status.index+1}
												</td>
												<td>
													${mater.value.name}
												</td>
												<%-- <td class="text-center">
													原件${mater.value.originalNum}份，复印件${mater.value.copyNum}份
												</td> --%>
												<%-- <td>
													<c:set var="materFlag" value="true"/>
													<c:set var="materOriginalFlag" value="false"/>
													<c:set var="taskFlag" value="false"/>
													<c:forEach items="${mater.value.taskList}" var="taskList" varStatus="taskListStatus">
														<c:if test="${mater.value.isMandatory=='0'||empty mater.value.isMandatory}">
															<c:if test="${taskList.taskStatus=='4'||taskList.taskStatus=='7'}">
																<c:set var="materOriginalFlag" value="true"/>
																<script type="text/javascript">
																	materialDefMap['${mater.key}'].materOriginalFlag = true;
																</script>
															</c:if>
														</c:if>
														<c:if test="${taskList.taskStatus!='4'&&taskList.taskStatus!='7'}">
															<c:set var="materFlag" value="false"/>
														</c:if>
														<c:if test="${taskList.taskStatus=='4'||taskList.taskStatus=='7'}">
															<c:set var="taskFlag" value="true"/>
														</c:if>
													</c:forEach>
													<label class="control-label" style="padding-top: 7px;">原件</label>
													<div class="fg-line ${materOriginalFlag||materFlag?'disabled':''}" style="width: 40px">
														<input ${materOriginalFlag||materFlag?'disabled':''} id="originalNum${mater.key}" onchange="originalNumChange('${mater.key}')" 
														value="${empty mater.value.originalNumReal?0:mater.value.originalNumReal}" type="text" class="form-control input-sm text-center" >
													</div>
													<label class="control-label" style="padding-top: 7px;">，复印件</label>
													<div class="fg-line ${materFlag?'disabled':''}" style="width: 40px">
														<input ${materFlag?'disabled':''} id="copyNum${mater.key}" onchange="copyNumChange('${mater.key}')" 
														 value="${empty mater.value.copyNumReal?0:mater.value.copyNumReal}" type="text" class="form-control input-sm text-center">
													</div>
													<c:if test="${fn:length(mater.value.taskList)>0}">
													<div class="fg-line actions" style="width: 30px">
														<a data-toggle="modal" onclick="showTaskList('${mater.key}');">
															<i class="md md-more-vert"></i>
														</a>
													</div>
					                                </c:if>
												</td> --%>
												<td class="text-center">
													<label class="checkbox checkbox-inline ${taskFlag?'disabled':''}">
														<input ${taskFlag?'disabled':''} id="checkbox${mater.key}" type="checkbox" disabled onchange="checkboxChange(this,'${mater.key}');"
														 ${(mater.value.originalNumReal!=0&&!empty mater.value.originalNumReal)||(mater.value.copyNumReal!=0&&!empty mater.value.copyNumReal)?'checked="checked"':''}  >
														<i class="input-helper"></i>
													</label>
												</td>
												<td class="text-nowrap" align="center">
													<input id="fileAddress${mater.key}" type="hidden">
													<%-- <div data-toggle="tooltip" data-placement="bottom" type="button" title="上传" type="button" class="btn btn-icon btn-file m-r-5 btn-info">
														<i class="md md-file-upload"></i>
														<input type="file" date-materId="${mater.key}" >
													</div> --%>
													<%-- <c:if test="${empty mater.value.materialAddr}">
														<button data-toggle="tooltip" data-placement="bottom" type="button" title="下载" id="download${mater.key}"
														class="btn btn-icon btn-file bgm-lightblue hide">
															<i class="md md-file-download"></i>
														</button>
														<button data-toggle="tooltip" data-placement="bottom" type="button" title="预览" id="view${mater.key}" 
														class="btn btn-success btn-icon btn-file hide">
															<i class="md md-visibility"></i>
														</button>
													</c:if> --%>
													<c:if test="${!empty mater.value.materialAddr}">
														<button data-toggle="tooltip" data-placement="bottom" type="button" title="下载" id="download${mater.key}"
														 onclick="window.open('${centextPath}/download?pathUrl=${mater.value.materialAddr}&coi=${mater.value.name}')"
														 class="btn btn-icon btn-file bgm-lightblue">
															<i class="md md-file-download"></i>
														</button>
														<button data-toggle="tooltip" data-placement="bottom" type="button" title="预览" id="view${mater.key}" 
														onclick="window.open('${ctx}/sys/filePreview?pathUrl=${mater.value.materialAddr}')" 
														class="btn btn-success btn-icon btn-file">
															<i class="md md-visibility"></i>
														</button>
													</c:if>
													
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
							
						</div>
					</div>
					<div class="btn-demo text-center">
						<button class="btn btn-default waves-effect preDis" type="button"
								onclick="javascrtpt:window.location.href='${ctx}/project/wsbslist'">返回</button>
						<button class="btn btn-default waves-effect preDis" type="button"
								onclick="pretrial(2)">通过</button>
						<button class="btn btn-default waves-effect preDis" type="button"
								onclick="pretrial(3)">退回</button>
					</div>
					<!-- 建设单位弹出页 -->
					<div class="modal fade" data-modal-color="cyan" id="approve" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog" aria-hidden="true">
						<div class="modal-dialog modal-lg">
							<div class="modal-content">
								<div class="modal-header">
									<h4 class="modal-title">建设单位选择</h4>
								</div>
								<div style="height:400px;">
									<iframe width="100%" height="100%" id="buildIframe" src=""></iframe>
								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-link" id="confirm">确认</button>
									<button type="button" class="btn btn-link" data-dismiss="modal">返回</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</form>


	<div class="modal fade" data-modal-color="cyan" id="falseReason" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog ">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">退回原因</h4>
				</div>
					<form id="formReason" action="javaScript:;">
						<div class="modal-body bgm-white text-muted" id="handle">
							<div class="row">
								<div class="form-group">
									<label class="control-label">原因：</label>
									<div>
										<div class="fg-line">
											<input name="pauseDesc" id="pauseDesc" placeholder="退回原因" type="text" maxlength="500" class="form-control required" />
										</div>
									</div>
								</div>
							</div>
						</div>
					</form>
				<div class="modal-footer">
					<button id="t_confirm" type="button" class="btn btn-link">确认</button>
					<button id="t_close" type="button" class="btn btn-link" data-dismiss="modal">取消</button>
				</div>
			</div>
		</div>
	</div>

	<div class="modal fade" data-modal-color="cyan" id="taskForm" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">未办事项页</h4>
				</div>
				<div id="iframeDIV" style="height:560px;">
					<iframe id="modalContent" width="100%" height="100%" frameborder="0"></iframe>
				</div>
				<div class="modal-footer">
					<button id="offFinish" type="button" class="btn btn-link">确认</button>
					<button type="button" class="btn btn-link" data-dismiss="modal">取消</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>