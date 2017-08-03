<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>预约信息</title>
	<meta name="decorator" content="blank"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
			$('#certCode').change(function() {
				var len = $('#certCode').val().length;
				if(len == 15 || len == 18) {
					$.ajax({
						type : 'post',
						url : '${ctx}/prj/appointment/form',
						data : {"certCode" : $('#certCode').val()},
						cache : false,
						dataType : 'html',
						success : function(data) {
							$("#basic").html(data);
						}
					});
				}
			});
			
			
		});	
		
			
	</script>
</head>
<body>
	<div class="card">
	    <div class="card-header">
	        <h2>预约信息 <small></small></h2>
	        <ul class="actions">
				<li>
					<button data-toggle="tooltip" data-placement="bottom" type="button" title="刷新" class="btn btn-default btn-icon m-r-5" onclick="refresh();"><i class="md md-autorenew"></i></button>
				</li>
			</ul>
	    </div>
		<div class="card-body card-padding">
			<form role="form" id="searchForm"  action="${ctx}/prj/appointment/detail" method="post">
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
				<div class="row" id="clearQuery">
				<%-- <div class="col-sm-2 form-group">
						<div class="fg-line">
							<label class="control-label">证件类型：</label>
							<select name="certType" class="form-control selectpicker" data-live-search="false">
								<option value="1" selected>身份证</option>
							</select>							
						</div>
					</div> --%>
					<div class="col-sm-3 form-group">
						<div class="fg-line">
							<label class="control-label">证件号码：</label>
							<input placeholder="证件号码" name="certCode" id="certCode" value="${param.certCode}" type="text"  maxlength="50" class="form-control">
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
			</form>
			
			<div class="tab-content">
				<div role="tabpanel" class="tab-pane active animated fadeInRight in" id="basic">
				 	<%@ include file="/WEB-INF/views/modules/project/prjAppointmentForm.jsp"%>
				</div>
			</div>
		</div>
	</div>
</body>
</html>