<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>受理表单</title>
<meta name="decorator" content="blank" />
<script>
	var arr = new Array();
	var obj = new Object();
	obj.taskCode = "10007200374172973914440404";
	obj.formid = "collapseTwo";
	var obj1 = new Object();
	obj1.taskCode = "10007200174172973914440404";
	obj1.formid = "collapseOne";
	arr[0] = obj;
	arr[1] = obj1;
	$(document).ready(function() {
		removeAll();
		init();
		bindChecBox();
		$("#form1").validate({
			submitHandler : function(form) {
			}
		});
		$("#form3").validate({
			rules: {
				'formRfYdjsBjspVo.entityIdcode':{card:true},
				'formRfBjshVo.entityIdcode':{card:true}
			},
			submitHandler : function(form) {
			}
		});
		$('#sa-success').click(function() {
			$("#sa-success").attr("disabled", true);
			$("#acceptButton").attr("disabled", true);
			ajaxDraft();
		});
		$('#mater').click(function() {
			$("#mater").attr("disabled", true);
			if (!$("#form1").valid()) {
				notify('没有通过验证!', 'danger');
				$("#mater").attr("disabled", false);
			}else if(!$("#form3").valid()){
				notify('没有通过验证!', 'danger');
				$("#mater").attr("disabled", false);
			}else{
				$("input[data-str='task']:checkbox").each(function(){
					var t = $(this);
					t.attr("disabled",false);
				});
				ajaxAccept();
			}
			$("#form1").submit();
			$("#form3").submit();
		});
		$('#acceptButton').click(function() {
			$("#acceptButton").attr("disabled", true);
			$("#sa-success").attr("disabled", true);
			if (!$("#form1").valid()&&$("#form3").valid()) {
				notify('没有通过验证!', 'danger');
				$("#acceptButton").attr("disabled", false);
				$("#sa-success").attr("disabled", false);
			}else{
				ajaxAccept();
			}
			$("#form1").submit();
			$("#form3").submit();
		});
		$('#confirm').click(function() {
			$('#modalWider').modal('hide');
			if (!$("#form1").valid()) {
				notify('没有通过验证!', 'danger');
			}else if(!$("#form3").valid()){
				notify('没有通过验证!', 'danger');
			}else{
				ajaxSave();
			}
			$("#form1").submit();
			$("#form3").submit();
		});
		$('#closeConfirm').click(function() {
			$('#modalWider').modal('hide');
			$("#acceptButton").attr("disabled", false);
			$("#sa-success").attr("disabled", false);
			$("#mater").attr("disabled", false);
			$("#accept").html("");
		});
		
		$("input[data-type='taskId']:checkbox").each(function() {
			var t = $(this);
			var isSelected = t.is(':checked');
			if(isSelected){
				var data=t.attr("data");
				$("input[data-id="+data+"]:checkbox").each(function() {
					var tt = $(this);
					var isSelected = tt.is(':checked');
					if(isSelected)tt.attr("disabled", true);
				});
			}
		});
	});
	function ajaxSave() {
		var formParam1 = $("#form1").serialize();//序列化表格内容为字符串
		var formParam2 = $("#form2").serialize();//序列化表格内容为字符串
		var formParam3 = $("#form3").serialize();//序列化表格内容为字符串
		var formParam4 = $("#form4").serialize();//序列化表格内容为字符串
		var formParam=formParam1+"&"+formParam2+"&"+formParam3+"&"+formParam4;
		$.ajax({
			type : 'post',
			url : '${ctx}/project/save',
			data : formParam,
			cache : false,
			dataType : 'html',
			success : function(data) {
				$("#temporary").html(data);
				if ($("#success_").length > 0) {
					$("#success").html($("#temporary").html());
					$('#notice').modal('show');
					var min = moment().format('YYYY-MM-DD');
					$('#date').datetimepicker({
						minDate: min,
					   	format: 'YYYY-MM-DD',
						widgetPositioning: {
							horizontal: 'left',
							vertical: 'top'
						}
			        });
					return;
				}
			}
		});
	}
	function ajaxAccept() {
		var formParam1 = $("#form1").serialize();//序列化表格内容为字符串
		var formParam2 = $("#form2").serialize();//序列化表格内容为字符串
		var formParam3 = $("#form3").serialize();//序列化表格内容为字符串
		var formParam4 = $("#form4").serialize();//序列化表格内容为字符串
		var formParam=formParam1+"&"+formParam2+"&"+formParam3+"&"+formParam4;
		var url;
		if (isCheck()) {
			url = '${ctx}/project/accept';
		} else {
			url = '${ctx}/project/success';
			/* swal("提示信息","您没有需要受理的事项，请选择！", "info");
			$("#mater").attr("disabled", false);
			return false;
 			*/		
 		}
		$.ajax({
			type : 'post',
			url : url,
			data : formParam,
			cache : false,
			dataType : 'html',
			success : function(data) {
				$("#temporary").html(data);
				if ($("#accept_").length > 0) {
					$("#accept").html($("#temporary").html());
					$('.selectpicker').selectpicker();
					$('#modalWider').modal('show');
					return;
				}
				if ($("#success_").length > 0) {
					$("#success").html($("#temporary").html());
					$('.selectpicker').selectpicker();
					$('#notice').modal('show');
					var min = moment().format('YYYY-MM-DD');
					$('#date').datetimepicker({
						minDate: min,
					   	format: 'YYYY-MM-DD',
						widgetPositioning: {
							horizontal: 'left',
							vertical: 'top'
						}
			        });
					return;
				}
			}
		});
	}
	function ajaxDraft() {//暂存
		if ($("#prjName").val() == "") {
			notify('请填写项目名!', 'danger');
			$("#prjName").focus();
			$("#sa-success").attr("disabled", false);
			$("#acceptButton").attr("disabled", false);
			return;
		}
		var formParam1 = $("#form1").serialize();//序列化表格内容为字符串
		var formParam2 = $("#form2").serialize();//序列化表格内容为字符串
		var formParam3 = $("#form3").serialize();//序列化表格内容为字符串
		var formParam4 = $("#form4").serialize();//序列化表格内容为字符串
		var formParam=formParam1+"&"+formParam2+"&"+formParam3+"&"+formParam4;
		$.ajax({
			type : 'post',
			url : '${ctx}/project/draft',
			data : formParam,
			cache : false,
			dataType : 'html',
			success : function(data) {
				swal("暂存成功!", "请稍后在受理列表中继续进行业务处理。", "success");
				setTimeout(function() {
					window.location.href = '${ctx}/project/list';
				}, 2000);
			}
		});
	}
	function isCheck() {
		var all = 0;
		$("input[data-type='taskId']:checked").each(function() {
			if(!$(this).attr("disabled")){
				all=all+1;
			}
		});
		if (all > 0) {
			return true;
		} else {
			return false;
		}
	}
	function init() {
		initMater();
	}
	function initMater() {
		$("input[data-type='taskId']:checkbox").bind('click',function() {
			var tt = $(this);
			var isChecked=tt.is(':checked');
			if(isChecked){
				addRequire(tt.attr("task-code"));
			}else{
				removeRequire(tt.attr("task-code"));
			}
		});
		initDraft();
	}
	
	function initDraft(){
		$("input[data-type='taskId']:checkbox").each(function(){
			var par=$(this);
			var key=par.attr("data");
			var iscomplete=true;
			var isHasSelect=false;
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
				if (par.attr("task-status") != '5')
					addRequire(par.attr("task-code"));
				par.prop("checked", true);
			} else {
				removeRequire(par.attr("task-code"));
				par.prop("checked", false);
			}
		} else {
			removeRequire(par.attr("task-code"));
			par.prop("checked", false);
		}
	});
}

	function addRequire(code) {
		for (var i = 0; i < arr.length; i++) {
			var o = arr[i];
			if (code == o.taskCode) {
				addRequired(o.formid);
			}
		}
	}
	function removeRequire(code) {
		for (var i = 0; i < arr.length; i++) {
			var o = arr[i];
			if (code == o.taskCode) {
				removeRequired(o.formid);
			}
		}
	}
	function removeAll() {
		for (var i = 0; i < arr.length; i++) {
			var o = arr[i];
			removeRequired(o.formid);
		}
	}
</script>
<script type="text/javascript">
	function selected(id) {
		$("#checkbox-" + id).prop("checked", true);
			taskClick("#checkbox-" + id);
	}
	function bindChecBox() {
		$("input[data-str='task']:checkbox").bind('click', function() {
			taskClick(this);
		});
	}
	function downFile(url, name) {
		var u = "${ctx}/sys/download?pathUrl=";
		u = u + url + "&coi=" + encodeURI(name);
		window.location.href = u;
	}
	function taskClick(obj){
		var tt=$(obj);
	    var data=tt.attr("data-id");
	    var par= $("input[data='" + data + "']:checkbox");
	    var iscomplete = true;
		var isHasSelect = false;
		$("input[data-id='" + data + "']:checkbox").each(function() {
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
// 		if(typeof(par.attr("disabled"))=='undefined'){
		if (isHasSelect) {
			if (iscomplete) {
				addRequire(par.attr("task-code"));
				par.prop("checked", true);
			} else {
				removeRequire(par.attr("task-code"));
				par.prop("checked", false);
			}
		} else {
			removeRequire(par.attr("task-code"));
			par.prop("checked", false);
		}
		}
// 	}
</script>
</head>
<body>


	<div class="card">
		<div class="card-header">
			<h2>
				新办项目受理<small>您可通过本功能进行新办项目受理</small>
			</h2>
			<ul class="actions">
				<li>
					<button data-toggle="tooltip" data-placement="bottom" type="button"
						title="返回" class="btn btn-success btn-icon m-r-5"
						<%--onclick="javascrtpt:window.location.href='${ctx}/project/index/'"--%>
							onclick="javascrtpt:history.go(-1)">
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
				<div role="tabpanel" class="tab">
					<ul class="tab-nav" role="tablist">
						<li class="active"><a href="#basic" aria-controls="basic"
							role="tab" data-toggle="tab">基本信息</a></li>
						<li role="presentation"><a href="#service"
							aria-controls="service" role="tab" data-toggle="tab">业务信息</a></li>
						<li role="presentation"><a href="#materials"
							aria-controls="materials" role="tab" data-toggle="tab">申报材料</a></li>
					</ul>
					<div class="tab-content">
						<div role="tabpanel"
							class="tab-pane active animated fadeInRight in" id="basic">

							<form method="post" name="form1" id="form1">
								<input type="hidden" name="prjInstanceVo.prjType" value="1">
								<input type="hidden" name="prjInstanceVo.priceType"
									value="${project.prjInstanceVo.priceType}"> <input
									type="hidden" name="prjInstanceVo.landType"
									value="${project.prjInstanceVo.landType}"> <input
									type="hidden" name="prjInstanceVo.useageType"
									value="${project.prjInstanceVo.useageType}"> <input
									type="hidden" name="prjCodeGeneratorVo.id"
									value="${project.prjCodeGeneratorVo.id}"> <input
									type="hidden" id="key" name="prjInstanceVo.id"
									value="${project.prjInstanceVo.id}"> <input
									type="hidden" id="key" name="prjInstanceVo.isNeedPreAudit"
									value="${project.prjInstanceVo.isNeedPreAudit}"> <input
									type="hidden" id="key" name="prjInstanceVo.isSpecialProject"
									value="${project.prjInstanceVo.isSpecialProject}"> <input
									type="hidden" id="key" name="prjInstanceVo.isWithBasePart"
									value="${project.prjInstanceVo.isWithBasePart}"> <input
									type="hidden" id="key" name="prjInstanceVo.isItType"
									value="${project.prjInstanceVo.isItType}"> <input
									type="hidden" id="key" name="prjInstanceVo.isGovType"
									value="${project.prjInstanceVo.isGovType}">
									<input
									type="hidden" id="key" name="prjInstanceVo.acceptId"
									value="${project.prjInstanceVo.acceptId}">

								<div class="row">
									<div class="col-xs-6 form-group">
										<label class="control-label">区项目编号：</label>
										<div class="fg-line readonly">
											<input type="text" name="prjInstanceVo.prjCode"
												maxlength="30" value="${project.prjInstanceVo.prjCode}"
												class="form-control required" id="projectCode" readonly
												placeholder="区项目编号">
										</div>
									</div>
									<div class="col-xs-6 form-group">
										<label class="control-label">省项目编号：</label>
										<div class="fg-line readonly">
											<input type="text" name="prjInstanceVo.prjCode"
												maxlength="30" value="${project.prjInstanceVo.shengPrjCode}"
												class="form-control required" id="shengPrjCode" readonly
												placeholder="省项目编号">
										</div>
									</div>
									<div class="col-xs-4 form-group">
										<label class="control-label">项目类别：</label>
										<div class="fg-line readonly">
											<input type="text" class="form-control required" readonly
												maxlength="50" value="${project.prjInstanceVo.prjCat}"
												placeholder="项目类别" name="prjInstanceVo.prjCat">
										</div>
									</div>
									<div class="col-xs-4 form-group">
										<label class="control-label">电话：</label>
										<div class="fg-line readonly">
											<input type="text" class="form-control required phone" readonly
												maxlength="15"
												value="${project.prjInstanceVo.companyMphone}"
												name="prjInstanceVo.companyMphone" placeholder="电话">
										</div>
									</div>
									<div class="col-xs-4 form-group">
										<label class="control-label">传真：</label>
										<div class="fg-line readonly">
											<input type="text" class="form-control required fax" readonly
												maxlength="15" value="${project.prjInstanceVo.comapnyFax}"
												name="prjInstanceVo.comapnyFax" placeholder="传真">
										</div>
									</div>
									<div class="col-xs-4 form-group">
										<label class="control-label">建设单位：</label>
										<div class="fg-line readonly">
											<input type="text" name="prjInstanceVo.company" readonly
												data-bind="company" maxlength="200"
												class="form-control required"
												value="${project.prjInstanceVo.company}" placeholder="建设单位">
										</div>
									</div>
									<div class="col-xs-4 form-group">
										<label class="control-label">单位地址：</label>
										<div class="fg-line readonly">
											<input type="text" class="form-control required" readonly
												data-bind="companyAddr" maxlength="200"
												value="${project.prjInstanceVo.companyAddr}"
												name="prjInstanceVo.companyAddr" placeholder="单位地址">
										</div>
									</div>

									<div class="col-xs-4 form-group">
										<label class="control-label">建设单位企业信用代码或组织机构代码：</label>
										<div class="fg-line readonly">
											<input type="text" class="form-control required" readonly
												maxlength="25" value="${project.prjInstanceVo.companyCode}"
												name="prjInstanceVo.companyCode"
												placeholder="建设单位企业信用代码或组织机构代码">
										</div>
									</div>
									<div class="col-xs-4 form-group">
										<label class="control-label">法人代表：</label>
										<div class="fg-line readonly">
											<input type="text" class="form-control required" readonly
												data-bind="legalEntity" maxlength="50"
												value="${project.prjInstanceVo.legalEntity}"
												name="prjInstanceVo.legalEntity" placeholder="法人代表">
										</div>
									</div>
									<div class="col-xs-4 form-group">
										<label class="control-label">手机（法人代表）：</label>
										<div class="fg-line readonly">
											<input type="text" class="form-control mobile" readonly
												data-bind="entityMphone" maxlength="15"
												value="${project.prjInstanceVo.entityMphone}"
												name="prjInstanceVo.entityMphone" placeholder="手机">
										</div>
									</div>
									<div class="col-xs-4 form-group">
										<label class="control-label">办公电话（法人代表）：</label>
										<div class="fg-line readonly">
											<input type="text" class="form-control required phone" readonly
												maxlength="15" value="${project.prjInstanceVo.entityPhone}"
												name="prjInstanceVo.entityPhone" placeholder="办公电话">
										</div>
									</div>
									<div class="col-xs-4 form-group">
										<label class="control-label">受委托人：</label>
										<div class="fg-line readonly">
											<input type="text" class="form-control required" readonly
												data-bind="agentName" maxlength="50"
												value="${project.prjInstanceVo.agentName}"
												name="prjInstanceVo.agentName" placeholder="受委托人">
										</div>
									</div>
									<div class="col-xs-4 form-group">
										<label class="control-label">手机（受委托人）：</label>
										<div class="fg-line readonly">
											<input type="text" class="form-control required mobile" readonly
												data-bind="agentMphone" maxlength="15"
												value="${project.prjInstanceVo.agentMphone}"
												name="prjInstanceVo.agentMphone" placeholder="手机">
										</div>
									</div>
									<div class="col-xs-4 form-group">
										<label class="control-label">办公电话（受委托人）：</label>
										<div class="fg-line readonly">
											<input type="text" class="form-control required phone" readonly
												data-bind="agentPhone" maxlength="15"
												value="${project.prjInstanceVo.agentPhone}"
												name="prjInstanceVo.agentPhone" placeholder="办公电话">
										</div>
									</div>
									<div class="col-xs-4 form-group">
										<label class="control-label">项目名称：</label>
										<div class="fg-line readonly">
											<input type="text" class="form-control required" id="prjName" readonly
												data-bind="prjName" maxlength="200"
												value="${project.prjInstanceVo.prjName}"
												name="prjInstanceVo.prjName" placeholder="项目名称"
												id="projectName">
										</div>
									</div>
									<div class="col-xs-4 form-group">
										<label class="control-label">项目性质：</label>
										<div class="fg-line readonly">
											<input type="text" class="form-control required" readonly
												maxlength="50" value="${project.prjInstanceVo.prjNature}"
												name="prjInstanceVo.prjNature" placeholder="项目性质">
										</div>
									</div>
									<div class="col-xs-4 form-group">
										<label class="control-label">项目地址：</label>
										<div class="fg-line readonly">
											<input type="text" class="form-control required" readonly
												data-bind="prjAddr" maxlength="50"
												value="${project.prjInstanceVo.prjAddr}"
												name="prjInstanceVo.prjAddr" placeholder="项目地址">
										</div>
									</div>
									<div class="col-xs-4 form-group">
										<label class="control-label">总建筑面积(m<sup>2</sup>)：
										</label>
										<div class="fg-line ">
											<input type="text" id="prjM"
												class="form-control required" maxlength="300" 
												value='${project.prjInstanceVo.prjFloorSpace}'
												name="prjInstanceVo.prjFloorSpace" placeholder="总建筑面积">
										</div>
									</div>
									<div class="col-xs-4 form-group">
										<label class="control-label">占地面积(m<sup>2</sup>)：
										</label>
										<div class="fg-line ">
											<input type="text" id="prjMM"
												class="form-control required" maxlength="300" 
												value='${project.prjInstanceVo.prjRedlineSpace}'
												name="prjInstanceVo.prjRedlineSpace" placeholder="占地面积">
										</div>
									</div>
									<div class="col-xs-4 form-group">
										<label class="control-label">项目规模及内容：</label>
										<div class="fg-line readonly">
											<input type="text" class="form-control required"
												maxlength="500"
												value="${project.prjInstanceVo.prjDescription}" readonly
												name="prjInstanceVo.prjDescription" placeholder="项目规模及内容">
										</div>
									</div>
									<div class="col-xs-6 form-group">
										<label class="control-label">投资估算： </label>
										<div class="fg-line ">
											<input type="text" class="form-control required" 
												maxlength="300"
												value='${project.prjInstanceVo.investEstimate}'
												name="prjInstanceVo.investEstimate" placeholder="投资估算">
										</div>
									</div>
									<div class="col-xs-6 form-group">
										<label class="control-label">资金来源：</label>
										<div class="fg-line readonly">
											<input type="text" class="form-control required" readonly
												maxlength="150" value="${project.prjInstanceVo.fundSource}"
												name="prjInstanceVo.fundSource" placeholder="资金来源">
										</div>
									</div>
									<div class="col-xs-6 form-group">
										<label class="control-label">相关资料文件描述： </label>
										<div class="fg-line ">
											<input type="text" class="form-control required" 
												maxlength="150"
												value="${project.prjInstanceVo.preFilesDesc}"
												name="prjInstanceVo.preFilesDesc" placeholder="相关资料文件描述">
										</div>
									</div>
									<div class="col-xs-6 form-group">
										<label class="control-label">相关资料文件：</label>
										<div class="fg-line">
											<sys:file id="file"
												downloadFileAddress="${project.prjInstanceVo.preFilesAddr}"
												downloadFileName="项目相关资料文件" cssClass="btn-info"
												fileName="prjInstanceVo.preFilesName"
												fileAddress="prjInstanceVo.preFilesAddr"></sys:file>
										</div>
									</div>
								</div>
							</form>
						</div>

						<c:if test="${1==1}">
							<div role="tabpanel" class="tab-pane animated fadeInRight"
								id="service">
								<form id="form3">
									<div class="panel-group" role="tablist"
										aria-multiselectable="true">
										<div class="panel panel-collapse">
											<div class="panel-heading" role="tab" id="headingOne">
												<h4 class="panel-title">
													<a data-toggle="collapse" data-parent="#accordion"
														href="#collapseOne" aria-expanded="true"
														aria-controls="collapseOne"> 人防工程易地建设报建审批 </a>
												</h4>
											</div>
											<div id="collapseOne" class="collapse in" role="tabpanel"
												aria-labelledby="headingOne">
												<div class="panel-body">
													<div class="row">
														<input type="hidden" name="formRfYdjsBjspVo.id"
															value="${project.formRfYdjsBjspVo.id}">
														<div class="col-xs-4 form-group">
															<label class="control-label">工程名称：</label>
															<div class="fg-line readonly">
																<input type="text" class="form-control required"
																	data-bind="prjName" readonly maxlength="200"
																	value="${project.formRfYdjsBjspVo.prjName}"
																	name="formRfYdjsBjspVo.prjName" placeholder="工程名称">
															</div>
														</div>
														<div class="col-xs-4 form-group">
															<label class="control-label">单位地址：</label>
															<div class="fg-line readonly">
																<input type="text" class="form-control required"
																	data-bind="companyAddr" readonly maxlength="250"
																	value="${project.formRfYdjsBjspVo.companyAddr}"
																	name="formRfYdjsBjspVo.companyAddr" placeholder="单位地址">
															</div>
														</div>
														<div class="col-xs-4 form-group">
															<label class="control-label">单位电话：</label>
															<div class="fg-line readonly">
																<input type="text" class="form-control phone required"
																	data-bind="agentPhone" readonly maxlength="15"
																	value="${project.formRfYdjsBjspVo.companyMphone}"
																	name="formRfYdjsBjspVo.companyMphone"
																	placeholder="单位电话">
															</div>
														</div>
														<div class="col-xs-4 form-group">
															<label class="control-label">联系人：</label>
															<div class="fg-line readonly">
																<input type="text" class="form-control required"
																	data-bind="agentName" readonly maxlength="50"
																	value="${project.formRfYdjsBjspVo.linkman}"
																	name="formRfYdjsBjspVo.linkman" placeholder="联系人">
															</div>
														</div>
														<div class="col-xs-4 form-group">
															<label class="control-label">联系电话：</label>
															<div class="fg-line readonly">
																<input type="text" class="form-control phone required"
																	maxlength="15"
																	value="${project.formRfYdjsBjspVo.linkmanPhone}"
																	data-bind="agentMphone" readonly
																	name="formRfYdjsBjspVo.linkmanPhone" placeholder="联系电话">
															</div>
														</div>
														<div class="col-xs-4 form-group">
															<label class="control-label">申请日期：</label>
															<div class="input-group">
																<span class="input-group-addon"><i
																	class="md md-event"></i></span>
																<div class="dtp-container dropdown fg-line">
																	<input type="text"
																		class="form-control date-picker date required"
																		value='<fmt:formatDate value="${project.formRfYdjsBjspVo.applyDate}" pattern="yyyy-MM-dd" />'
																		data-toggle="dropdown"
																		name="formRfYdjsBjspVo.applyDate"
																		placeholder="单击此处..." aria-expanded="false">
																</div>
															</div>
														</div>
														<div class="col-xs-4 form-group">
															<label class="control-label" class="control-label">工程地点：</label>
															<div class="fg-line readonly">
																<input type="text" class="form-control required"
																	data-bind="prjAddr" readonly maxlength="50"
																	value="${project.formRfYdjsBjspVo.prjAddress}"
																	name="formRfYdjsBjspVo.prjAddress" placeholder="工程地点">
															</div>
														</div>
														<div class="col-xs-4 form-group">
															<label class="control-label" class="control-label">建设单位：</label>
															<div class="fg-line readonly">
																<input type="text" class="form-control required"
																	readonly data-bind="company" maxlength="250"
																	value="${project.formRfYdjsBjspVo.company}"
																	name="formRfYdjsBjspVo.company" placeholder="建设单位">
															</div>
														</div>
														<div class="col-xs-4 form-group">
															<label class="control-label" class="control-label">设计单位：</label>
															<div class="fg-line">
																<input type="text" class="form-control required"
																	maxlength="50"
																	value="${project.formRfYdjsBjspVo.designCompany}"
																	name="formRfYdjsBjspVo.designCompany"
																	placeholder="设计单位">
															</div>
														</div>
														<div class="col-xs-4 form-group">
															<label class="control-label" class="control-label">建设单位法人代表：</label>
															<div class="fg-line readonly">
																<input type="text" class="form-control required"
																	data-bind="legalEntity" readonly maxlength="50"
																	value="${project.formRfYdjsBjspVo.legalEntity}"
																	name="formRfYdjsBjspVo.legalEntity"
																	placeholder="建设单位法人代表">
															</div>
														</div>
														<div class="col-xs-4 form-group">
															<label class="control-label" class="control-label">法人代表身份证号码：</label>
															<div class="fg-line">
																<input type="text" class="form-control"
																	maxlength="19"
																	value="${project.formRfYdjsBjspVo.entityIdcode}"
																	name="formRfYdjsBjspVo.entityIdcode"
																	placeholder="法人代表身份证号码">
															</div>
														</div>
														<div class="col-xs-4 form-group">
															<label class="control-label" class="control-label">法人代表联系电话：</label>
															<div class="fg-line readonly">
																<input type="text" class="form-control phone required"
																	data-bind="entityMphone" readonly maxlength="15"
																	value="${project.formRfYdjsBjspVo.entityPhone}"
																	name="formRfYdjsBjspVo.entityPhone"
																	placeholder="法人代表联系电话">
															</div>
														</div>
														<div class="col-xs-12 form-group">
															<label class="control-label">防空地下室易地建设方式（自选一项）：</label>
															<div class="fg-line">
																<label class="radio radio-inline m-r-20"> <input
																	type="radio" name="formRfYdjsBjspVo.basementType"
																	checked value="1"
																	${project.formRfYdjsBjspVo.basementType eq '1'?"checked":""}>
																	<i class="input-helper"></i> 1、缴易地建设费
																</label> <label class="radio radio-inline m-r-20"> <input
																	type="radio" name="formRfYdjsBjspVo.basementType"
																	value="2"
																	${project.formRfYdjsBjspVo.basementType eq '2'?"checked":""}>
																	<i class="input-helper"></i> 2、小区内自建
																</label> <label class="radio radio-inline m-r-20"> <input
																	type="radio" name="formRfYdjsBjspVo.basementType"
																	value="3"
																	${project.formRfYdjsBjspVo.basementType eq '3'?"checked":""}>
																	<i class="input-helper"></i> 3、易地自建
																</label>
															</div>
														</div>
													</div>
												</div>
											</div>
										</div>
										<div class="panel panel-collapse">
											<div class="panel-heading" role="tab" id="headingTwo">
												<h4 class="panel-title">
													<a data-toggle="collapse" data-parent="#accordion"
														href="#collapseTwo" aria-expanded="true"
														aria-controls="collapseTwo"> 人防工程报建审核 </a>
												</h4>
											</div>
											<div id="collapseTwo" class="collapse in" role="tabpanel"
												aria-labelledby="headingTwo">
												<div class="panel-body">
													<div class="row">
														<input type="hidden" name="formRfBjshVo.id"
															value="${project.formRfBjshVo.id}">
														<div class="col-xs-4 form-group">
															<label class="control-label">工程名称：</label>
															<div class="fg-line readonly">
																<input type="text" class="form-control required"
																	data-bind="prjName" readonly maxlength="250"
																	value="${project.formRfBjshVo.prjName}"
																	name="formRfBjshVo.prjName" placeholder="工程名称">
															</div>
														</div>
														<div class="col-xs-4 form-group">
															<label class="control-label">单位地址：</label>
															<div class="fg-line readonly">
																<input type="text" class="form-control required"
																	data-bind="companyAddr" readonly maxlength="250"
																	value="${project.formRfBjshVo.companyAddr}"
																	name="formRfBjshVo.companyAddr" placeholder="单位地址">
															</div>
														</div>
														<div class="col-xs-4 form-group">
															<label class="control-label">单位电话：</label>
															<div class="fg-line readonly">
																<input type="text" class="form-control phone required"
																	data-bind="agentPhone" readonly maxlength="15"
																	value="${project.formRfBjshVo.companyMphone}"
																	name="formRfBjshVo.companyMphone" placeholder="单位电话">
															</div>
														</div>
														<div class="col-xs-4 form-group">
															<label class="control-label">联系人：</label>
															<div class="fg-line readonly">
																<input type="text" class="form-control required"
																	data-bind="agentName" readonly maxlength="50"
																	value="${project.formRfBjshVo.linkman}"
																	name="formRfBjshVo.linkman" placeholder="联系人">
															</div>
														</div>
														<div class="col-xs-4 form-group">
															<label class="control-label">联系电话：</label>
															<div class="fg-line readonly">
																<input type="text" class="form-control phone required"
																	data-bind="agentMphone" readonly maxlength="15"
																	value="${project.formRfBjshVo.linkmanPhone}"
																	name="formRfBjshVo.linkmanPhone" placeholder="联系电话">
															</div>
														</div>
														<div class="col-xs-4 form-group">
															<label class="control-label">申请日期：</label>
															<div class="input-group">
																<span class="input-group-addon"><i
																	class="md md-event"></i></span>
																<div class="dtp-container dropdown fg-line ">
																	<input type="text"
																		class="form-control date-picker required"
																		data-toggle="dropdown" name="formRfBjshVo.applyDate"
																		value='<fmt:formatDate value="${project.formRfBjshVo.applyDate}" pattern="yyyy-MM-dd" />'
																		placeholder="单击此处..." aria-expanded="false">
																</div>
															</div>
														</div>
														<div class="col-xs-4 form-group">
															<label class="control-label" class="control-label">工程地点：</label>
															<div class="fg-line readonly">
																<input type="text" class="form-control required"
																	data-bind="prjAddr" readonly maxlength="50"
																	value="${project.formRfBjshVo.prjAddress}"
																	name="formRfBjshVo.prjAddress" placeholder="工程地点">
															</div>
														</div>
														<div class="col-xs-4 form-group">
															<label class="control-label" class="control-label">建设单位：</label>
															<div class="fg-line readonly">
																<input type="text" class="form-control required"
																	readonly data-bind="company" maxlength="250"
																	value="${project.formRfBjshVo.company}"
																	name="formRfBjshVo.company" placeholder="建设单位">
															</div>
														</div>
														<div class="col-xs-4 form-group">
															<label class="control-label" class="control-label">设计单位：</label>
															<div class="fg-line">
																<input type="text" class="form-control required"
																	maxlength="50"
																	value="${project.formRfBjshVo.designCompany}"
																	name="formRfBjshVo.designCompany" placeholder="设计单位">
															</div>
														</div>
														<div class="col-xs-4 form-group">
															<label class="control-label" class="control-label">建设单位法人代表：</label>
															<div class="fg-line readonly">
																<input type="text" class="form-control required"
																	data-bind="legalEntity" readonly maxlength="50"
																	value="${project.formRfBjshVo.legalEntity}"
																	name="formRfBjshVo.legalEntity" placeholder="建设单位法人代表">
															</div>
														</div>
														<div class="col-xs-4 form-group">
															<label class="control-label" class="control-label">法人代表身份证号码：</label>
															<div class="fg-line">
																<input type="text" class="form-control"
																	maxlength="20"
																	value="${project.formRfBjshVo.entityIdcode}"
																	name="formRfBjshVo.entityIdcode"
																	placeholder="法人代表身份证号码">
															</div>
														</div>
														<div class="col-xs-4 form-group">
															<label class="control-label" class="control-label">法人代表联系电话：</label>
															<div class="fg-line readonly">
																<input type="text" class="form-control phone required"
																	data-bind="entityMphone" readonly maxlength="15"
																	value="${project.formRfBjshVo.entityPhone}"
																	name="formRfBjshVo.entityPhone" placeholder="法人代表联系电话">
															</div>
														</div>
														<div class="col-xs-4 form-group">
															<label class="control-label">防空地下室主要出入口处城市坐标及高程：</label>
															<div class="fg-line">
																<label class="control-label"></label>
															</div>
														</div>
														<div class="col-xs-2 form-group">
															<label class="control-label">x坐标:</label>
															<div class="fg-line">
																<input type="text" class="form-control"
																	maxlength="50"
																	value="${project.formRfBjshVo.basementXPoint}"
																	name="formRfBjshVo.basementXPoint" placeholder="x坐标">
															</div>
														</div>
														<div class="col-xs-2 form-group">
															<label class="control-label">y坐标:</label>
															<div class="fg-line">
																<input type="text" class="form-control"
																	maxlength="50"
																	value="${project.formRfBjshVo.basementYPoint}"
																	name="formRfBjshVo.basementYPoint" placeholder="y坐标">
															</div>
														</div>
														<div class="col-xs-2 form-group">
															<label class="control-label">高程:</label>
															<div class="fg-line">
																<input type="text" class="form-control"
																	maxlength="50"
																	value="${project.formRfBjshVo.basementGc}"
																	name="formRfBjshVo.basementGc" placeholder="高程">
															</div>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
								</form>
							</div>
						</c:if>
						<div role="tabpanel" class="tab-pane animated fadeInRight"
							id="materials">
							<p>请勾选申请材料</p>
							<div class="table-responsive">
								<form id="form2" method="post" name="form2">
									<table class="table table-striped table-vmiddle bootgrid-table">
										<thead>
											<tr>
												<th class="text-center col-xs-2">可办理的事项名称</th>
												<th class="text-center">申请材料</th>
												<th class="text-center col-xs-1">原件</th>
												<th class="text-center col-xs-1">复印件</th>
												<th class="text-center col-xs-1">是否提供</th>
												<th class="text-center col-xs-1">操作</th>
											</tr>
										</thead>
										<tbody>
											<c:if test="${project.prjInstanceVo.id ne null}">
												<c:set var="i" value="-1"></c:set>
												<c:forEach items="${project.prjStageMaterialVoMap}"
													var="map" varStatus="v">
													<c:forEach items="${map.value}" var="mater" varStatus="var">
														<c:set var="i" value="${i+1}"></c:set>
														<input type="hidden" value="${mater.stageId}"
															name="prjStageMaterialVoList[${i}].stageId" />
														<input type="hidden" value="${mater.materialId}"
															name="prjStageMaterialVoList[${i}].materialId" />
														<input type="hidden" value="${mater.originalNum}"
															name="prjStageMaterialVoList[${i}].originalNum" />
														<input type="hidden" value="${mater.copyNum}"
															name="prjStageMaterialVoList[${i}].copyNum" />
														<input type="hidden" value="${mater.isMandatory}"
															name="prjStageMaterialVoList[${i}].isMandatory" />
														<input type="hidden" value="${mater.materName}"
															name="prjStageMaterialVoList[${i}].materName" />
														<input type="hidden" value="${mater.id}"
															name="prjStageMaterialVoList[${i}].id" />
														<input type="hidden" value="${map.key}"
															name="prjStageMaterialVoList[${i}].taskId" />
														<tr>
															<c:if test="${var.index eq 0}">
																<c:set var="taskDefine"
																	value="${fns:getPrjTaskDefineVo(map.key)}"></c:set>
																<c:set var="taskVo"
																	value="${fns:getPrjTaskVo(map.key,project.prjInstanceVo.id,project.prjStageVo.id)}"></c:set>
																<td rowspan="${fn:length(map.value)}"
																	class='${v.index%2==0?"odd":"even"}'><label
																	class="checkbox checkbox-inline"><input
																		type="checkbox" value="${map.key}" data-type="taskId" onclick="return false"
																		data="${map.key}" task-code="${taskDefine.taskCode}"
																		name="prjTaskDefineVoList[${v.index}].id"
																		${taskVo.taskStatus eq '0'?"":"disabled checked"}><i
																		class="input-helper"></i>${taskDefine.taskName}</label></td>
															</c:if>
															<td class='${v.index%2==0?"odd":"even"}' style="padding-left: 30px;"><c:if
																	test="${mater.isMandatory eq '1'}">
																	<span style="color: red">*</span>
																</c:if>${mater.materName}</td>
															<td class='text-center ${v.index%2==0?"odd":"even"}'>
																${mater.originalNum}
															</td>
															<td class='text-center ${v.index%2==0?"odd":"even"}'>
																${mater.copyNum}
															</td>
															<td class='text-center ${v.index%2==0?"odd":"even"}'><label
																class="checkbox checkbox-inline"> <input
																	id="checkbox-${i}" type="checkbox" value="1"
																	data-id="${map.key}" data-str="task"
																	data-type="${mater.isMandatory eq '1'?'must':'no'}"
																	 ${mater.isComplete eq '1'?"checked ":""}
																	name="prjStageMaterialVoList[${i}].isComplete">
																	<i class="input-helper"></i>
															</label></td>
															<td
																class='text-left text-nowrap ${v.index%2==0?"odd":"even"}'><c:if
																	test="${mater.isComplete eq '1'&&mater.materialAddr ne ''}">
																	<button data-placement="bottom" type="button"
																		title="下载" class="btn btn-icon btn-file bgm-lightblue"
																		style="margin-left: 40px" type="button"
																		title="${mater.materialName}"
																		onClick="downFile('${mater.materialAddr}','${mater.materialName}')">
																		<i class="md md-file-download"></i>
																	</button>
																</c:if> <c:if
																	test="${mater.isComplete eq '1'&&mater.materialAddr eq ''}">
																	<sys:file id="file${i}" callFunction='selected("${i}")'
																		downloadFileAddress="${mater.materialAddr}"
																		downloadFileName="${mater.materName}"
																		cssClass="btn-info"
																		fileName="prjStageMaterialVoList[${i}].materialName"
																		fileAddress="prjStageMaterialVoList[${i}].materialAddr"></sys:file>
																</c:if> <c:if test="${mater.isComplete ne '1'}">
																	<sys:file id="file${i}" callFunction='selected("${i}")'
																		downloadFileAddress="${mater.materialAddr}"
																		downloadFileName="${mater.materName}"
																		cssClass="btn-info"
																		fileName="prjStageMaterialVoList[${i}].materialName"
																		fileAddress="prjStageMaterialVoList[${i}].materialAddr"></sys:file>
																</c:if></td>
														</tr>
													</c:forEach>
												</c:forEach>
											</c:if>
										</tbody>
									</table>
								</form>
							</div>
						</div>
					</div>
				</div>
				<div class="btn-demo text-center">
					<c:if test="${project.prjStageVo.stageStatus eq '1'}">
						<button data-toggle="modal" id="mater"
								class="btn btn-primary waves-effect" type="button">资料补齐</button>
						</c:if>
					<button class="btn btn-default waves-effect" type="button"
						onclick="javascrtpt:window.location.href='${ctx}/project/list'">返回</button>
				</div>
			</div>
		</div>
	</div>
	<form id="form4">
	<div class="modal fade" id="modalWider"
							data-modal-color="lightblue" data-backdrop="static"
							data-keyboard="false" tabindex="-1" role="dialog"
							aria-hidden="true">
							<div class="modal-dialog modal-lg">
								<div class="modal-content">
									<div class="modal-header">
										<h4 class="modal-title">选择事项审批人</h4>
									</div>
									<div class="modal-body p-20 bgm-white text-muted o-auto"
										style="max-height: 400px;" id="accept"></div>
									<div class="modal-footer">
										<button id="confirm" type="button" class="btn btn-link">确认受理</button>
										<button type="button" id="closeConfirm" class="btn btn-link">关闭</button>
									</div>
								</div>
							</div>
						</div>
						<div class="modal fade" id="notice" data-modal-color="lightblue"
							tabindex="-1" role="dialog" data-backdrop="static"
							data-keyboard="false" aria-hidden="true">
							<div class="modal-dialog">
								<div class="modal-content" id="success"></div>
							</div>
						</div>
	</form>
	<div id="temporary" style="display: none"></div>
</body>
</html>