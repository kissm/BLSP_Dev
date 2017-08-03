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
			
			var validator = $("#saveCertID").validate({
				rules: {
				},
				messages: {
				},
				submitHandler: function(form){
					form.submit();
				}
			});
			
			
			$('#t_confirm',parent.document).click(function(){
				if (validator.form()) {
					 if($(":checkbox:checked").size() == 0) {
						 swal("请至少选择领取一个证件", "", "error");
					 } else {
						$("#saveCertID").submit();
					 }
				}
			})
			
	});	

	</script>
</head>
<body>
<form id="saveCertID" action="${ctx}/prjTask/cert/saveCert" method="post">
	<input type="hidden" id="prjId" name="prjId" value="${prjId}"/>
	<div class="p-20">
		<div class="row">
			<div class="col-sm-3 form-group">
				<div class="fg-line">
					<label class="control-label">领证人：</label>
					<input name="fetchMan" placeholder="领证人" type="text" maxlength="10" class="form-control required">
				</div>
			</div>
			<div class="col-sm-3 form-group">
				<div class="fg-line">
					<label class="control-label">联系方式：</label>
					<input name="fetchContactInfo" placeholder="联系方式" type="text" maxlength="20" class="form-control required phone">
				</div>
			</div>
			<div class="col-sm-3 form-group">
				<div class="fg-line">
					<label class="control-label">领证时间：</label>
					<input name="fetchTimeStr" placeholder="领证时间" type="text" data-toggle="dropdown" class="form-control date-picker required">
				</div>
			</div>
			<div class="col-sm-3 form-group">
				<div class="fg-line">
					<label class="control-label">备注：</label>
					<input name="fetchDesc" placeholder="备注" type="text" maxlength="500" class="form-control">
				</div>
			</div>
		</div>
		<div class="table-responsive">
			<table id="contentTable" class="table table-striped  table-vmiddle">
				<thead>
					<tr>
						<th>本项目可领取批文名称</th>
						<th class="col-xs-1">是否已领取</th>
						<th class="col-xs-2">领证人</th>
						<th class="col-xs-2">联系方式</th>
						<th class="col-xs-2">领取时间</th>
						<th class="col-xs-2">下载</th>
						<th class="col-xs-1">本次领取</th>
					</tr>
				</thead>
				<tbody id="tbody_cert">
				<c:forEach  items="${list}" var="cert">
					<tr>
						<td>${cert.auditAttachName}</td>
						<td>
							<c:if test="${cert.isFetched eq 1}">是</c:if>
							<c:if test="${cert.isFetched ne 1}">否</c:if>
						</td>
						<td>${cert.fetchMan}</td>
						<td>${cert.fetchContactInfo}</td>
						<td>${cert.fetchTimeStr}</td>
						<td>
							<button class="btn btn-info btn-icon" data-toggle="tooltip" data-placement="bottom" type="button" title="下载" onClick="window.open('${ctx}/sys/download?pathUrl=${cert.auditAttachAddr}&coi=${fn:split(cert.auditAttachName,'.')[0]}')"><i class="md md-file-download"></i></button>
							<button class="btn btn-success btn-icon btn-file" data-toggle="tooltip" data-placement="bottom" type="button" title="预览" id="viewfile" onclick="window.open('${ctx}/sys/filePreview?pathUrl=${cert.auditAttachAddr}')"><i class="md md-visibility"></i></button>
						</td>
						<td>
							<c:if test="${cert.isFetched ne 1}">
								<label class="checkbox checkbox-inline"><input type="checkbox" name="prjTaskInstIds" value="${cert.id}"><i class="input-helper"></i></label>
							</c:if>
						</td>
				</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</form>
</body>
</html>