<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>业务办理-批文上传</title>
	<meta name="decorator" content="blank"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
			var validator = $("#taskFinishID").validate({
				rules: {
				},
				messages: {
				},
				submitHandler: function(form){
					form.submit();
				}
			});
			var b = false;
			$('#t_confirm').click(function(){
				if(validator.form()){
					if ($('input[name="auditAttachAddr"]').val() == '') {
						swal("请上传批文附件", "", "error");
					} else {
						b = true;
						$('#transferred').modal('hide');
						$("#taskFinishID").submit();
					}
				}
			});
			$('#transferred').on('hidden.bs.modal', function () {
				if(b){
					swal("操作成功!", "可在“查询”功能中进行项目审批情况查看。", "success");
					b=false;
				}
			});
			
			$('#t_close').click(function(){
				validator.resetForm();
				$('#t_confirm').removeAttr("disabled");
				$('#confirmCheck').attr("checked", false)
				$("#checkDiv").hide();
				$("#mateNoComplete").html('');
				$("#taskFinishID .has-error").each(function(){
					$(this).removeClass("has-error");
				});
			})
			
		});
		
		function certUpload(id) {
			$.ajax({
				type : 'post',
				url : '${ctx}/prjTask/cert/uploadInput',
				data : {'prjTaskInstId':id},
				cache : false,
				dataType: 'json',
				success : function(data) {
					if(data.certDept != undefined)
						$('#certDept').val(data.certDept);
					if(data.certDateStr != undefined)
						$('#certDate').val(data.certDateStr);
					if(data.certCode != undefined)
						$('#certCode').val(data.certCode);
					if(data.certTitle != undefined)
						$('#certTitle').val(data.certTitle);
					if(data.isAllMater == 1) {
						$('#t_confirm').attr({"disabled":"disabled"});
						
						var html = "<div class='form-group'><label class='col-xs-3 control-label'>未提交材料：</label><div class='col-xs-6'><div class='fg-line'>";
						$.each(data.prjTaskMaterialDTOs, function(n,value) {
							html += n+1 + ".&nbsp;";
							html += value.materialName+"<br>";
						});
						html += "</div></div></div>";
						$("#mateNoComplete").html(html);

						$('#confirmCheck').click(function(){
							if ($(this).is(':checked'))
								$('#t_confirm').removeAttr("disabled");
							else
								$('#t_confirm').attr({"disabled":"disabled"});
						});
						$("#checkDiv").show();
					}else{
						$("#checkDiv").remove();
					}
				}
			});
			
			$('#transferred').modal('show');
			$('#prjTaskInstIdInput').val(id);
		}
		
		function certDownLoad(pathUrl,coi,id){
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
			$('#transferredDown').modal('show');
		}

	</script>
</head>
<body>
<div class="card">
	    <div class="card-header">
	        <h2>批文上传 <small>您可通过本功能进行批文上传。</small></h2>
	        <ul class="actions">
				<li>
					<button data-toggle="tooltip" data-placement="bottom" type="button" title="刷新" class="btn btn-default btn-icon m-r-5" onclick="refresh();"><i class="md md-autorenew"></i></button>
				</li>
			</ul>
	    </div>
		<form role="form" id="searchForm"  modelAttribute="prjTaskDTO" action="${ctx}/prjTask/cert/list" method="post">
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
			<input id="typeId" name=passType type="hidden" value="3"/>
			<div class="card-body card-padding">
				<div class="row" id="clearQuery">
					<div class="col-sm-4 form-group">
						<div class="fg-line">
							<label class="control-label">区项目编号：</label>
							<input placeholder="区项目编号" name="prjCode" value="<c:out value="${param.prjCode}"/>" type="text" maxlength="50" class="form-control">
						</div>
					</div>
					<div class="col-sm-4 form-group">
						<div class="fg-line">
							<label class="control-label">项目名称：</label>
							<input placeholder="项目名称" name="prjName" value="<c:out value="${param.prjName}"/>" type="text"  maxlength="50" class="form-control">
						</div>
					</div>
					<div class="col-sm-2 form-group">
						<label class="control-label"></label>
						<button id="btnSubmit" type="submit" onclick="page(1);" class="btn btn-primary waves-effect form-control">查询</button>
					</div>
					<div class="col-sm-2 form-group">
						<label class="control-label"></label>
						<button id="btnReset" type="button" class="btn btn-primary waves-effect form-control" onclick="clearQuery();">重置</button>
					</div>
				</div>
				<sys:message content="${message}"/>
			</div>
		</form>
		<div class="table-responsive">
	         <table id="contentTable" class="table table-striped table-vmiddle bootgrid-table">
			 <thead>
				<tr>
					<th>区项目编号</th>
					<th>项目名称</th>
					<th>项目阶段</th>
					<th>事项名称</th>
					<th>状态</th>
					<th>事项办结时间</th>
					<th>批文处理</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${page.list}" var="task">
					<c:choose>
						<c:when test="${task.taskRemainTime le 0}">
							<tr>
								<td>${task.prjCode}</td>
								<td>${task.prjName}</td>
								<td>${task.taskStageName}</td>
								<td>${task.taskName}</td>
								<td>
									<c:if test="${task.auditAttachAddr == '' || task.auditAttachAddr == null}">
										未上传
									</c:if>
									<c:if test="${task.auditAttachAddr != '' && task.auditAttachAddr != null}">
										已上传
									</c:if>
								</td>
								<td><fmt:formatDate value="${task.taskRealEndTime}" pattern="yyyy-MM-dd"/></td>
								<td>
									<c:if test="${task.auditAttachAddr == '' || task.auditAttachAddr == null}">
										<button class="btn btn-icon btn-info " title="批文上传" data-toggle="tooltip" data-placement="bottom"
											onclick="javascrtpt:certUpload(${task.prjTaskInstId})"
											type="button">
											<span class="md md-file-upload"></span>
										</button>
									</c:if>
									<c:if test="${task.auditAttachAddr != '' && task.auditAttachAddr != null}">
										<button class="btn btn-icon btn-file bgm-green" type="button" title="下载批文" data-toggle="tooltip" data-placement="bottom" 
												onClick="certDownLoad('${task.auditAttachAddr}','${fn:split(task.auditAttachName,'.')[0]}','${task.prjTaskInstId}')">
												<i class="md md-file-download"></i>
										</button>
									</c:if>
								</td>
							</tr>
						</c:when>
					</c:choose>

				</c:forEach>
			</tbody>
	       </table>
		</div>
		${page}
</div>


	<div class="modal fade" data-modal-color="cyan" id="transferred" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog ">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">批文上传页</h4>
				</div>
				<form id="taskFinishID" action="${ctx}/prjTask/cert/upload" method="post">
					<input type="hidden" id="prjTaskInstIdInput" name="prjTaskInstId" value=""/>
					<div class="modal-body bgm-white text-muted p-20 form-horizontal">
						<div class="row">
							<div class="form-group">
								<label class="col-xs-3 control-label">发证机关：</label>
								<div class="col-xs-6">
									<div class="fg-line">
										<input name="certDept" id="certDept" placeholder="发证机关" type="text" maxlength="100" class="form-control required">
									</div>
								</div>
							</div>
							<div class="form-group">
								<label class="col-xs-3 control-label">发证时间：</label>
								<div class="col-xs-6">
									<div class="dtp-container dropdown fg-line">
										<input name="certDateStr" id="certDate" placeholder="发证时间" type="text" data-toggle="dropdown" class="form-control date-picker required">
									</div>
								</div>
							</div>
							<div class="form-group">
								<label class="col-xs-3 control-label">文号：</label>
								<div class="col-xs-6">
									<div class="fg-line">
										<input name="certCode" id="certCode" placeholder="文号" type="text" maxlength="30" class="form-control required">
									</div>
								</div>
							</div>
							<div class="form-group">
								<label class="col-xs-3 control-label">标题：</label>
								<div class="col-xs-6">
									<div class="fg-line">
										<input name="certTitle" id="certTitle" placeholder="标题" type="text" maxlength="100" class="form-control required">
									</div>
								</div>
							</div>
							<div class="form-group">
								<label class="col-xs-3 control-label">附件上传：</label>
								<div class="col-xs-6">
									<div class="fg-line">
										<sys:file id="attachFile" cssClass="btn-info" fileName="auditAttachName" fileAddress="auditAttachAddr" isPreview="true" ></sys:file>
									</div>
								</div>
							</div>
														
							
							<div id="mateNoComplete"></div>
							
							<div class="form-group" style="display:none;" id="checkDiv">
								<div class="col-xs-9 col-xs-offset-3">
									<div class="fg-line">
											<div class="checkbox">
												<label>
													<input type="checkbox" name="confirmCheck" id="confirmCheck" class='required' value="1">
													<i class="input-helper"></i>
													我已确认本事项所需材料已全部提供<br>
												</label>
											</div>
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

	<div class="modal fade" data-modal-color="cyan" id="transferredDown" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog ">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">批文下载页</h4>
				</div>
				<form id="taskFinishIDDown" method="post">
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
				</form>
				<div class="modal-footer">
					<button type="button" class="btn btn-link" data-dismiss="modal">关闭</button>
				</div>
			</div>
		</div>
	</div>
</body>
</html>