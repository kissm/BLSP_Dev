<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>修改密码</title>
	<meta name="decorator" content="blank"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#inputForm").validate({
				rules: {
				},
				messages: {
					confirmNewPassword: {equalTo: "输入与上面相同的密码"}
				},
				submitHandler: function(form){
					form.submit();
				}
			});
		});
	</script>
</head>
<body>
<div class="card">
	<form role="form" id="inputForm" action="${ctx}/sys/user/modifyPwd" method="post" >
		<input type="hidden" value="${user.id}"/>
	    <div class="card-header">
	        <h2>修改密码 <small>账号的密码修改</small></h2>
	        <ul class="actions">
				<li>
					<button data-toggle="tooltip" data-placement="bottom" type="button" title="刷新" class="btn btn-default btn-icon m-r-5" onclick="refresh();"><i class="md md-autorenew"></i></button>
				</li>
			</ul>
	    </div>
	    <div class="card-body card-padding">
	    	<sys:message content="${message}"/>
	        <div class="form-group">
	            <label for="oldPassword">旧密码:</label>
				<div class=" fg-line">
					<input id="oldPassword" name="oldPassword" type="password" value="" class="form-control input-sm required" placeholder="旧密码">
	        	</div>
	        </div>
	        <div class="form-group">
	            <label for="newPassword">新密码:</label>
				<div class=" fg-line">
					<input id="newPassword" name="newPassword" type="password" value="" maxlength="18" minlength="6" class="form-control input-sm required" placeholder="新密码"/>
	        	</div>
	        </div>
	        <div class="form-group">
	            <label for="confirmNewPassword">确认新密码:</label>
	            <div class=" fg-line">
					<input id="confirmNewPassword" name="confirmNewPassword" type="password" value=""
						   equalTo="#newPassword" class="form-control input-sm required" placeholder="确认新密码" />
				</div>
	        </div>
	        <button id="btnSubmit" type="submit" class="btn btn-primary waves-effect m-t-10">保存</button>
	    </div>
	</form>
</div>
</body>
</html>