<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>成果领取</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		if('${message}'=='ok'){
			parent.iframeClose();
		}
		$(document).ready(function() {
			
			var validator = $("#stopFormId").validate({
				rules: {
				},
				messages: {
				},
				submitHandler: function(form){
					form.submit();
				}
			});
			
			
			$('#offFinish',parent.document).click(function(){
				if (validator.form()) {
					if ($('input[name="stopFileAddr"]').val() == '') {
						swal("请上传附件", "", "error");
					} else {
						swal({   
		                    title: "确定终止此项目吗？",
		                    text: "",   
		                    type: "warning",   
		                    showCancelButton: true,   
		                    confirmButtonColor: "#DD6B55",
		                    cancelButtonText: '取消',
		                    confirmButtonText: "确认"
		                }, function(){
		                	$("#stopFormId").submit();
		                });
						
					}
					
				}
				
			})
			
	});	

	</script>
</head>
<body>
	<form id="stopFormId" action="${ctx}/project/stop/save" method="post">
		<input type="hidden" id="prjInstIdInput" name="prjInstId" value="${prjInsId}"/>
		<div class="modal-body bgm-white text-muted p-20 form-horizontal">
			<div class="row">
				<div class="form-group">
					<label class="col-xs-3 control-label">终止原因：</label>
					<div class="col-xs-6">
						<div class="fg-line">
							<input name="stopReason" id="stopReason" placeholder="终止原因" type="text" maxlength="100" class="form-control required">
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-3 control-label">附件上传：</label>
					<div class="col-xs-6">
						<div class="fg-line">
							<sys:file id="attachFile" fileName="stopFileName" fileAddress="stopFileAddr" isPreview="true" ></sys:file>
						</div>
					</div>
				</div>
			</div>
		</div>
	</form>
</body>
</html>