<%@ page import="com.google.gson.Gson"%>
<%@ page import="com.lpcode.modules.service.project.dto.Project"%>
<%@ page import="com.lpcode.modules.service.project.dto.PrjTaskDTO"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>业务办理-详情查看</title>
	<link href="${centextPath}/uploadify/uploadify.css" rel="stylesheet"/>
	<script src="${centextPath}/uploadify/jquery.uploadify.js"></script>
	<meta name="decorator" content="blank"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
			$.validator.setDefaults({
				 ignore: ""
				})
			
			var validatorPass = $("#taskPassID").validate({
				rules: {
				},
				messages: {
				},
				submitHandler: function(form){
					form.submit();
				}
			});
			
			var validatorFinish = $("#taskFinishID").validate({
				rules: {
				},
				messages: {
					confirmCheck: "",
				},
				submitHandler: function(form){
					form.submit();
				}
			});
			
			var validatorPause = $("#taskPauseID").validate({
				rules: {
				},
				messages: {
				},
				submitHandler: function(form){
					form.submit();
				}
			});
			
			
			 $('body').on('click', '#btn-color-targets > .btn', function(){
                 var color = $(this).data('target-color');
                 $('#modalColor').attr('data-modal-color', color);
             });
			 
			var a = false;
			$('#confirm').click(function(){
				a = true;
				$('#approve').modal('hide');
			});
			
			$('#approve').on('hidden.bs.modal', function () {
				if(a){
					swal("操作成功!", "可在“查询”功能中进行项目审批情况查看。", "success");
					a=false;
				}
			});
			var b = false;
			$('#t_confirm').click(function(){
				if(validatorFinish.form()){
					var c = false;
					if ($.trim($('input[name="auditAttachAddr"]').val())  == '') {
						c = true;
					} else if ($.trim($('input[name="auditAttachAddr"]').val()) != ''){
						if ($('input[name="certDept"]') != 'undefined' && $.trim($('input[name="certDept"]').val()) == '') {
							swal("请填写发证机关", "", "error");
						} else if ($.trim($('input[name="certDateStr"]').val()) == '') {
							swal("请填写发证时间", "", "error");
						} else if ($.trim($('input[name="certCode"]').val()) == '') {
							swal("请填写文号", "", "error");
						} else if ($.trim($('input[name="certTitle"]').val()) == '') {
							swal("请填写标题", "", "error");
						} else {
							c = true;
						}
					} 
					if (c) {
						b = true;
						//$("#taskFinishID").submit();
						var formParam = $("#taskFinishID").serialize();//序列化表格内容为字符串
						$.ajax({
							type : 'post',
							url : '${ctx}/prjTaskTodo/finish',
							data : formParam,
							cache : false,
							dataType : 'html',
							success : function(data) {
								$('#transferred').modal('hide');
							}
						});
					}
				}
			});
			
			$('#transferred').on('hidden.bs.modal', function () {
				if(b){
					swal({   
		                title: "操作成功!",
		                text: "可在“查询”功能中进行项目审批情况查看。",   
		                type: "success"
		            }, function(){
		            	window.location.href="${ctx}/prjTaskTodo/list";
		            });
					b=false;
				}
			});
			
			
			$("#pass_confirm").click(function() {
				if(validatorPass.form()){
					if($('#currUserId').val() == '') {
						swal("请选择下一审批人", "", "error");
					} else {
						$("#taskPassID").submit();
					}
				}
			})
			$("#pause_confirm").click(function() {
				if(validatorPause.form()){
					if($('#provideEndDateID').val() > $('#pauseDateStr').val()) {
						swal("提交材料截止日期不能晚于暂停恢复日期", "", "error");
					} else {
						a = true;
						$("#pause_confirm").attr("disabled",true);
						$('#approve').modal('hide');
						$("#taskPauseID").submit();
					}
				}
			})
			var flagTag = true;
			$("#taskResumeID").click(function() {
				var url = "${ctx}/prjTaskTodo/resume";
				 swal({   
	                    title: "你确定要恢复该事项吗？",
	                    text: "事项恢复后，办理时限将继续开始计算",   
	                    type: "warning",   
	                    showCancelButton: true,   
	                    confirmButtonColor: "#DD6B55",
	                    cancelButtonText: '取消',
	                    confirmButtonText: "是的，恢复！",
	                    closeOnConfirm: false 
	                }, function(){
					 	if(flagTag){
							flagTag = false;
							window.location.href="${ctx}/prjTaskTodo/resume?prjTaskInstId=${prjTaskInstId}";
						}
//					 	swal("完成！", "该事项已经恢复", "success");
	                });
			})
			
	
			$('#uploadify').uploadify({
				'swf': '${centextPath}/uploadify/uploadify.swf?_'+(new Date()).getTime(),
		        'uploader': '${ctx}/sys/uploadImage',
		        'auto': true,
		        'multi': false,
				'wmode' : 'transparent',
		        'buttonText': '请选择文件',
		        'fileTypeExts' : '*.doc;*.docx;*.pdf;*.jpg;*.bmp;*.png;*.tif;*.rar;*.zip;',
		        onSelect : function(file) {
				},
				onCancel : function(event, queueID, file, response, data) {
				},
				onUploadSuccess : function(file, data, response) {
					var result = eval("(" + data + ")");
					if(result.resCode == '00000000'){
						var path = (result.obj)[0];
						$('input[name="auditAttachAddr"]').val(path);
						$('input[name="auditAttachName"]').val(fileName);
						var fileDiv = $('#fileDiv');
						fileDiv.html("");
						var fileName = (result.obj)[1];
						fileDiv.html(fileDiv.html() + fileName +"<ul id='upImg' class='actions' style='display: inline-block;'><li><a class='icon-pop' href='javascript:void(0)' onclick='deleteImg()' title='删除图片'><i class='md md-close'></i></a></li></ul>");
					}
				},
				onUploadComplete : function(file) {
				},
				onUploadError : function(file, errorCode, errorMsg, errorString) {
				}
		    });
			
			
			$('input[name="sendMessage"]').click(function() {
				var t = $('input[name="provideEndDateStr"]');
				if ($(this).is(':checked')) {
					t.removeAttr("disabled");
					t.addClass('required');
					t.val('${materEndDate}');
				} else {
					t.val('');
					t.attr("disabled", true);
					t.removeClass('required');
					t.parent().parent().parent().removeClass('has-error');
				}
			})
			
	});
		
		function suspend() {
			$('#pauseTips').text('');
			$('#suspend').modal('show');
		}
		
		// 生成码图
		function createLocpde(type) {
			window.open ('${ctx}/prjTaskTodo/createLpcode?prjId=${prjTask.prjId}&type='+type,'_blank');
			// swal("下载码图成功!", "请继续进行业务处理。", "success")
		}
		
		function deleteImg() {
			 $('#fileDiv').html("");
			 $('input[name="auditAttachAddr"]').val('');
			 $('input[name="auditAttachName"]').val('');
		}
		
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
		}
		
		function downFile(url,name){
			var u="${ctx}/sys/download?pathUrl=";
			u=u+url+"&coi='"+encodeURI(name)+"'";
			window.location.href=u;
		}
		
		function showTransferred(id,prjTaskInstId){
			$.ajax({
				type : 'post',
				url : '${ctx}/prjTask/cert/uploadInput',
				data : {'prjTaskInstId':prjTaskInstId},
				cache : false,
				dataType: 'json',
				success : function(data) {
					if (data.certDept != undefined && data.certDept != '') {
						$('#certDept').val(data.certDept);
						// 已保存的信息不可编辑
						$('#certDept').attr("readonly", "readonly");
						$('#certDept').parent('.fg-line').addClass('readonly');
					}
				}
			});

			if (id == 2) {
				swal({   
                    title: "确定此事项不通过吗？",
                    text: "如果此事项材料未提交全，您可以先暂停此事项，待材料提交完成后再办理",   
                    type: "warning",   
                    showCancelButton: true,   
                    cancelButtonText: '取消',
                    confirmButtonText: "确认不通过！"
                }, function(){
    				$("#transTitle").text("审批不通过信息页");
    				$("#taskStatusID").val("6");
    				$('#transferred').modal('show');
                });
			} else {
				if ('${taskDefine.isAllMater}' == '1') {
					$('#finishTips').text('提示：该事项所需材料未提供齐全，无法上传批文，请等待材料全部提交完成后再进行批文上传');
					$("#transTitle").text("办结信息页");
					$("#taskStatusID").val("4");
					$('#transferred').modal('show');
				} else {
					$("#transTitle").text("办结信息页");
					$("#taskStatusID").val("4");
					$('#transferred').modal('show');
				}
			}
			
		}
		function transferred() {
			if ('${taskDefine.isAllMater}' == '1' && '${taskDefine.isConMater}' != '1') {
				$('#pauseTips').text('该事项所需材料未提供齐全，请先暂停该事项，待材料全部提交完成后再进行办结');
				$('#suspend').modal('show');
			} else {
				$('#transferred').modal('show');
			}
		}

		function post(url,data,fn,efn){
			var success = fn||function(){};
			var error = efn||function(){};
			$.ajax({
				url:url,
				type: "POST",
				contentType:'application/json; charset=utf-8',
				data:JSON.stringify(data),
				dataType: 'json',
				success:success,error:error
			});
		}
		$(function (){
			//判断是否有表单
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
		//表单弹窗
		function lookForm(formCode){
			var taskId = '${prjTask.taskId}';
			var id = '${project.prjInstanceVo.id}';
			openWindow({
				id:'detailTable',
				title:'表单详情',
				url:'${ctx}/project/accpet/formDetailView',
				width:'1000px',
				height:'500px',
				params:{"taskId":taskId,"id":id,"formCode":formCode,"view":"3"},
			});
		}
	</script>
</head>
<body>
<form id="locpdeForm" action="${ctx}/prjTaskTodo/createLpcode" method="post">
</form>
<div class="card">
	    <div class="card-header">
	       <h2>
	     	  详情查看
	     	   <small>
	     	   		您可通过本功能对事项申请信息及审批处理情况进行查看。
	     	   </small>
	       </h2>
<%-- 	        <c:if test="${taskStatus eq 1}">业务办理>>我的待办业务</c:if>
	        <c:if test="${taskStatus eq 2}">业务办理>>我的已办业务</c:if>
	        <c:if test="${taskStatus eq 4}">业务办理>>我的办结业务</c:if> --%>
	       <ul class="actions">
				<li>
					<button data-toggle="tooltip" data-placement="bottom" type="button" title="刷新" class="btn btn-default btn-icon m-r-5" onclick="refresh();"><i class="md md-autorenew"></i></button>
				</li>
			</ul>
	    </div>
		<div class="card-body card-padding">
			<div role="tabpanel">
				<ul class="tab-nav" role="tablist">
					<li class="active"><a href="#basic" aria-controls="basic" role="tab" data-toggle="tab">基本信息</a></li>
					<%-- <c:if test="${taskCodeFlag eq '1'}">
						<li role="presentation"><a href="#profile" aria-controls="profile" role="tab" data-toggle="tab">业务信息</a></li>
					</c:if> --%>
					<li role="presentation" id="serInfoLi"><a href="#profileZero" aria-controls="profileZero" role="tab" data-toggle="tab">业务信息</a></li>
					<c:if test="${not empty material}">
						<li role="presentation"><a href="#materials" aria-controls="materials" role="tab" data-toggle="tab">申报材料</a></li>
					</c:if>
					<c:if test="${prjTask.auditAttachAddr != '' && prjTask.auditAttachAddr != null}">
						<li role="presentation"><a href="#approvalDownLoad" aria-controls="materials" role="tab" data-toggle="tab">批文下载</a></li>
					</c:if>
				</ul>
				<div class="tab-content">
					<div role="tabpanel" class="tab-pane active animated fadeInRight in" id="basic">
						<div class="row">
							<div class="col-xs-6 form-group">
								<label class="control-label" >区项目编号：${project.prjInstanceVo.prjCode}</label>
							</div>
							<div class="col-xs-6 form-group">
								<label class="control-label" >省项目编号：${project.prjInstanceVo.shengPrjCode}</label>
							</div>
							<div class="col-xs-4 form-group">
								<label class="control-label" >项目类别：${project.prjInstanceVo.prjCat}</label>
							</div>
							<div class="col-xs-4 form-group">
								<label class="control-label" >电话：${project.prjInstanceVo.companyMphone}</label>
							</div>
							<div class="col-xs-4 form-group">
								<label class="control-label" >传真：${project.prjInstanceVo.comapnyFax}</label>
							</div>
							
							<div class="col-xs-4 form-group">
								<label class="control-label" >建设单位：${project.prjInstanceVo.company}</label>
							</div>
							<div class="col-xs-4 form-group">
								<label class="control-label" >单位地址：${project.prjInstanceVo.companyAddr}</label>
							</div>
							<div class="col-xs-4 form-group">
								<label class="control-label" >建设单位企业信用代码或组织机构代码：${project.prjInstanceVo.companyCode}</label>
							</div>
							
							<div class="col-xs-4 form-group">
								<label class="control-label" >法人代表：${project.prjInstanceVo.legalEntity}</label>
							</div>
							<div class="col-xs-4 form-group">
								<label class="control-label" >手机（法人代表）：${project.prjInstanceVo.entityMphone}</label>
							</div>
							<div class="col-xs-4 form-group">
								<label class="control-label" >办公电话（法人代表）：${project.prjInstanceVo.entityPhone}</label>
							</div>
							
							<div class="col-xs-4 form-group">
								<label class="control-label" >受委托人：${project.prjInstanceVo.agentName}</label>
							</div>
							<div class="col-xs-4 form-group">
								<label class="control-label" >手机（受委托人）：${project.prjInstanceVo.agentMphone}</label>
							</div>
							<div class="col-xs-4 form-group">
								<label class="control-label" >办公电话（受委托人）：${project.prjInstanceVo.agentPhone}</label>
							</div>
							
							<div class="col-xs-4 form-group">
								<label class="control-label" >项目名称：${project.prjInstanceVo.prjName}</label>
							</div>
							<div class="col-xs-4 form-group">
								<label class="control-label" >项目性质：${project.prjInstanceVo.prjNature}</label>
							</div>
							<div class="col-xs-4 form-group">
								<label class="control-label" >项目地址：${project.prjInstanceVo.prjAddr}</label>
							</div>
							
							<div class="col-xs-4 form-group">
								<label class="control-label" >总建筑面积(m<sup>2</sup>)：${project.prjInstanceVo.prjFloorSpace}</label>
							</div>
							<div class="col-xs-4 form-group">
								<label class="control-label" >占地面积(m<sup>2</sup>)：${project.prjInstanceVo.prjRedlineSpace}</label>
							</div>
							<div class="col-xs-4 form-group">
								<label class="control-label" >项目规模及内容：${project.prjInstanceVo.prjDescription}</label>
							</div>
							
							<div class="col-xs-4 form-group">
								<label class="control-label" >投资估算(万元)：${project.prjInstanceVo.investEstimate}</label>
							</div>
							<div class="col-xs-6 form-group">
								<label class="control-label" >资金来源：${project.prjInstanceVo.fundSource}</label>
							</div>
							
							<div class="col-xs-4 form-group">
								<label class="control-label" >相关资料文件描述：${project.prjInstanceVo.preFilesDesc}</label>
							</div>
							<div class="col-xs-8 form-group">
								<label class="control-label" >相关资料文件：${project.prjInstanceVo.preFilesName}
									<c:if test="${not empty project.prjInstanceVo.preFilesAddr}">
										<button data-toggle="tooltip" data-placement="bottom" type="button" title="下载" 
												class="btn btn-icon btn-file bgm-lightblue" style="margin-left: 8px" type="button"
												onClick="downFile('${project.prjInstanceVo.preFilesAddr}','${project.prjInstanceVo.preFilesName}')">
												<i class="md md-file-download"></i>
										</button>
										<button data-placement="bottom" type="button" title="预览"
												onclick="window.open('${ctx}/sys/filePreview?pathUrl=${project.prjInstanceVo.preFilesAddr}')"
												class="btn btn-success btn-icon btn-file">
											<i class="md md-visibility"></i>
										</button>
									</c:if>
								</label>
							</div>
							
							<div class="col-xs-12 form-group">
								<img class="col-xs-offset-5 col-xs-2" src="${project.prjInstanceVo.lpcodeAddr}" alt="项目龙贝码" >
							</div>
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
												<%--码图下载的地方--%>
												<%--<c:if test="${taskStatus ne 4}">--%>
													<%--<div class="card-header">--%>
														<%--<ul class="actions">--%>
															<%--<li>--%>
																<%--<button title="生成码图" data-toggle="tooltip" data-placement="bottom" class="btn btn-success btn-icon m-r-5" onclick="javascrtpt:createLocpde(1)"><i class="md md-file-download"></i></button>--%>
															<%--</li>--%>
														<%--</ul>--%>
													<%--</div>--%>
												<%--</c:if>--%>
											</td>
										</c:if>
									</c:forEach>
								</tr>
							</c:forEach>
						</table>
					</div>
				<c:if test="${not empty material}">
					<div role="tabpanel" class="tab-pane animated fadeInRight" id="materials">
						<div class="table-responsive">
							<table class="table table-striped table-vmiddle bootgrid-table">
								<thead>
									<tr>
										<th class="col-xs-1"></th>
										<th>材料名称</th>
										<th class="text-center col-xs-1">是否提供</th>
										<th class="col-xs-2">下载</th>
										<th class="col-xs-2">上传日期</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${material}" var="mat" varStatus="status">
										<tr>
											<td>
												${status.count}
											</td>
											<td>
												<c:if test="${mat.isMandatory eq '1'}">
													<span style="color: red">*</span>
												</c:if>
												${mat.materialName}
											</td>
											<td class="text-center">
												<c:if test="${mat.isComplete eq '1'}">
													<label class="checkbox checkbox-inline"> 
														<input disabled type="checkbox" checked name="checkboxDisabled"><i class="input-helper"></i>
													</label>
												</c:if>
											</td>
											<c:choose>
											<c:when test="${not empty mat.materialAddr}">
												<td>
													<button class="btn btn-info btn-icon" type="button" title="${mat.materialName}" onClick="window.open('${ctx}/sys/download?pathUrl=${mat.materialAddr}&coi=${mat.materialName}')"><i class="md md-file-download"></i></button>
													<button data-placement="bottom" type="button" title="预览"
															onclick="window.open('${ctx}/sys/filePreview?pathUrl=${mat.materialAddr}')"
															class="btn btn-success btn-icon btn-file">
														<i class="md md-visibility"></i>
													</button>
												</td>
												<td>
													<fmt:formatDate value="${mat.creatTime}" pattern="yyyy-MM-dd"/>
												</td>
											</c:when>
											<c:otherwise>
												<td colspan="2"><label style="color:red">未上传</label></td>
											</c:otherwise>
											</c:choose>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</c:if>
					
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
    </div>
    
    <div class="btn-demo text-center m-t-20 m-b-20">
		<c:if test="${prjTask.taskStatus eq 1}">
			<button class="btn btn-info waves-effect" data-toggle="modal" href="#approve" >通过并继续</button>
			<button class="btn btn-primary waves-effect" onclick="showTransferred(1,${prjTaskInstId})">通过并办结</button>
			<c:if test="${projectType eq 2}">
				<button class="btn btn-danger waves-effect" onclick="showTransferred(2,${prjTaskInstId})">不通过</button>
			</c:if>
			<button class="btn btn-warning waves-effect"  data-toggle="modal" onclick="suspend()">暂停</button>
		</c:if>
		<c:if test="${prjTask.taskStatus eq 2}">
		<button id="taskResumeID" class="btn btn-success waves-effect">恢复</button>
		</c:if>
		<c:if test="${prjTask.taskStatus ne -1}">
			<button class="btn btn-default waves-effect" onclick="location.href='${ctx}/prjTaskTodo/list'" >返回</button>
		</c:if>
		<c:if test="${prjTask.taskStatus eq -1}">
			<button class="btn btn-default waves-effect" onclick="javascrtpt:history.go(-1)">返回</button>
		</c:if>
	</div>
	
<c:if test="${projectType eq 2 and not empty depList}">
	<div class="card">
        <div class="card-header">
            <h2>依赖事项操作信息</h2>  
        </div>
		<div class="card-body card-padding">
			<div class="row">
				<div class="table-responsive">
					<table id="contentTable1" class="table table-striped">
						<thead>
							<tr>
								<th class="col-xs-2">部门</th>
								<th class="col-xs-2">事项名称</th>
								<th class="col-xs-1">办结人</th>
								<th class="col-xs-1">办结时间</th>
								<th>审批意见</th>
								<th class="col-xs-2">附件</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${depList}" var="dep">
								<tr>
									<td>
										${dep.deptName}
									</td>
									<td>
										${dep.taskName}
									</td>
									<td>
										${dep.finishedMan}
									</td>
									<td>
										<fmt:formatDate value="${dep.finishedTime}" pattern="yyyy-MM-dd"/>
									</td>
									<td>
										${dep.auditDesc}
									</td>
									<td>
										<c:if test="${not empty dep.auditAttachAddr}">
											<button class="btn btn-info btn-icon" type="button" title="下载" onClick="window.open('${ctx}/sys/download?pathUrl=${dep.auditAttachAddr}&coi=${dep.auditAttachName}')"><i class="md md-file-download"></i></button>
										</c:if>
										<c:if test="${empty dep.auditAttachAddr}">
											<label style="color:red">未上传</label>
										</c:if>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</c:if>	
	
	<c:if test="${not empty handleList}">
	<div class="card">
        <div class="card-header">
            <h2>操作信息</h2>  
        </div>
		<div class="card-body card-padding">
			<div class="row">
				<div class="table-responsive">
					<table id="contentTable" class="table table-striped">
						<thead>
							<tr>
								<th class="col-xs-2">操作人</th>
								<th class="col-xs-2">开始时间</th>
								<th class="col-xs-2">结束时间</th>
								<th class="col-xs-2">操作类型</th>
								<th>操作说明</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${handleList}" var="handle">
								<tr>
									<td>
										${handle.userName}
									</td>
									<td>
										<fmt:formatDate value="${handle.startTime}" pattern="yyyy-MM-dd"/>
									</td>
									<td>
										<fmt:formatDate value="${handle.endTime}" pattern="yyyy-MM-dd"/>
									</td>
									<td>
										<c:if test="${handle.handleType eq 1}">审批</c:if>
										<c:if test="${handle.handleType eq 2}">
											暂停(${fns:getDictLabel(handle.pauseType, 'task_pause_type', '')}
													<c:if test="${handle.pauseType == 99}">
														其他
													</c:if>
												)
										</c:if>
										<c:if test="${handle.handleType eq 3}">办结</c:if>
									</td>
									<td>
										<c:out value="${handle.desc}"/>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</c:if>

		

	
	<div class="modal fade" data-modal-color="cyan" id="approve" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog ">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">审批通过确认页</h4>
				</div>
				<form id="taskPassID" action="${ctx}/prjTaskTodo/pass" method="post">
				<input type="hidden" name="prjTaskInstId" value="${prjTaskInstId}"/>
					<div class="modal-body bgm-white text-muted p-20 form-horizontal">
						<div class="row">
							<div class="form-group">
								<label class="col-xs-3 control-label">下一审批人：</label>
								<div class="col-xs-6">
									<select name="currUser" id="currUserId" class="form-control selectpicker" data-live-search="true">
										<option value="">请选择</option>
					                    <c:forEach items="${fns:getUsersByOfficeId(taskDefine.deptId)}" var="itemValue">
					                        <option value="${itemValue.id}">${itemValue.name}</option>
					                    </c:forEach>
		                		    </select>
								</div>
							</div>
							<div class="form-group">
								<label class="col-xs-3 control-label">审批意见：</label>
								<div class="col-sm-6">
									<div class="fg-line">
										<textarea name="auditDesc" class="form-control auto-size required" maxlength="480" placeholder="请填写意见"></textarea>
									</div>
								</div>
							</div>
						</div>
					</div>
				</form>
				<div class="modal-footer">
					<button id="pass_confirm" type="button" class="btn btn-link">确认</button>
					<button type="button" class="btn btn-link" data-dismiss="modal">取消</button>
				</div>
			</div>
		</div>
	</div>
	
	
	<div class="modal fade" data-modal-color="cyan" id="transferred" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog ">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title" id="transTitle">1办结信息页</h4>
				</div>
				<form id="taskFinishID" action="${ctx}/prjTaskTodo/finish" method="post">
					<input type="hidden" name="prjTaskInstId" value="${prjTaskInstId}"/>
					<input type="hidden" name="taskStatus" id="taskStatusID"/>
					<div class="modal-body bgm-white text-muted p-20 form-horizontal">
						<div class="row">
							<div class="form-group">
								<label class="col-xs-3 control-label">审批意见：</label>
								<div class="col-sm-6">
									<div class="fg-line">
										<textarea name="auditDesc" class="form-control auto-size required" maxlength="480" placeholder="请填写意见"></textarea>
									</div>
								</div>
							</div>
							<c:if test="${prjTask.isWithCert eq 1 and taskDefine.isAllMater ne 1}">
								<div class="form-group">
									<label class="col-xs-3 control-label">发证机关：</label>
									<div class="col-xs-6">
										<div class="fg-line">
											<input id="certDept" name="certDept" placeholder="发证机关" type="text" maxlength="100" class="form-control">
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-xs-3 control-label">发证时间：</label>
									<div class="col-xs-6">
										<div class="dtp-container dropdown fg-line">
											<input name="certDateStr" placeholder="发证时间" type="text" data-toggle="dropdown" class="form-control date-picker">
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-xs-3 control-label">文号：</label>
									<div class="col-xs-6">
										<div class="fg-line">
											<input name="certCode" placeholder="文号" type="text" maxlength="30" class="form-control">
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-xs-3 control-label">标题：</label>
									<div class="col-xs-6">
										<div class="fg-line">
											<input name="certTitle" placeholder="标题" type="text" maxlength="100" class="form-control">
										</div>
									</div>
								</div>
								<div class="form-group">
									<label class="col-xs-3 control-label">附件上传：</label>
									<div class="col-xs-6">
										<div class="fg-line">
											<%-- <input name="auditAttachAddr" placeholder="附件上传" type="text" maxlength="50" class="form-control">
											<div id="fileDiv" class="form-control" style="border-bottom: 0px solid #e0e0e0;"></div>									
											<input type="file" name="file" id="uploadify">
											<input type="hidden" name="auditAttachAddr">	
											<input type="hidden" name="auditAttachName">	 --%>
											
											<sys:file id="attachFile" cssClass="btn-info" fileName="auditAttachName" fileAddress="auditAttachAddr" isPreview="true" ></sys:file>
										</div>
									</div>
								</div>
							</c:if>
							
						</div>
						<small class="c-red" style="" id="finishTips"></small>	
					</div>
				</form>
				<div class="modal-footer">
					<button id="t_confirm" type="button" class="btn btn-link">确认</button>
					<button type="button" class="btn btn-link" data-dismiss="modal">取消</button>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal fade" data-modal-color="cyan" id="suspend" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog ">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">暂停确认页</h4>
				</div>
				<form id="taskPauseID" action="${ctx}/prjTaskTodo/pause" method="post">
				<input type="hidden" name="prjTaskInstId" value="${prjTaskInstId}"/>
					<div class="modal-body bgm-white text-muted p-20 form-horizontal">
						<small class="c-red" id="pauseTips">
						</small>	
											
						<div class="row" style="margin-top: 10px;">
							<div class="form-group">
								<label class="col-xs-3 control-label">暂停类型：</label>
								<div class="col-xs-6">
									<div class="fg-line">
										<select name="pauseType" class="form-control selectpicker">
						                    <c:forEach items="${fns:getDictList('task_pause_type')}" var="itemValue">
						                        <option value="${itemValue.value}">${itemValue.label}</option>
						                    </c:forEach>											
										</select>
									</div>
								</div>
							</div>
							<div class="form-group">
								<label class="col-xs-3 control-label">恢复时间：</label>
								<div class="col-xs-6">
									<div class="fg-line">
										<input id="pauseDateStr" type="text" name="pauseDateStr" data-min="${minPauseDate}" class="form-control date-picker required" data-toggle="dropdown" placeholder="单击此处..." aria-expanded="false">
									</div>
								</div>
							</div>
							<div class="form-group">
								<label class="col-xs-3 control-label">暂停原因：</label>
								<div class="col-xs-6">
									<div class="fg-line">
										<textarea name="pauseDesc" class="form-control auto-size required" maxlength="450" placeholder="请填写意见"></textarea>
									</div>
								</div>
							</div>
							<div class="form-group">
								<div class="col-xs-5">
									<div class="checkbox control-label">
										<label>
											<input type="checkbox" name="sendMessage" value="1"><i class="input-helper"></i>短信通知申请人于
										</label>
									</div>
								</div>
								<div class="col-xs-3">
									<div class="fg-line">
										<input disabled type="text" name="provideEndDateStr" id="provideEndDateID" data-max-id="pauseDateStr" data-min="${minPauseDate}" class="form-control date-picker" data-toggle="dropdown" placeholder="单击此处..." aria-expanded="false" autoComplete="off">
									</div>
								</div>
								<div class="col-xs-3">
									<label class="control-label">前提交材料</label>
								</div>
							</div>
						</div>
						<small class="c-red">
							提示：请在允许的暂停的时间内完成事项办理，否则时间满限后将自动恢复正常审批，如提前办理完成可点击“恢复”进行继续审批处理。
						</small>
					</div>
				</form>
				<div class="modal-footer">
					<button id="pause_confirm" type="button" class="btn btn-link">确认</button>
					<button type="button" class="btn btn-link" data-dismiss="modal">取消</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>