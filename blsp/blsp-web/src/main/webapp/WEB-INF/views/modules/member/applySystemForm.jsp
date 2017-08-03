<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>接入系统管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/member/applySystem/">接入系统列表</a></li>
		<li class="active"><a href="${ctx}/member/applySystem/form?id=${applySystem.id}">接入系统<shiro:hasPermission name="extuser:applySystem:edit">${not empty applySystem.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="extuser:applySystem:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="applySystem" action="${ctx}/member/applySystem/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">系统名称：</label>
			<div class="controls">
				${applySystem.name}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">系统类型：</label>
			<div class="controls">
				${fns:getDictLabel(applySystem.sysType, 'sys_type', '')}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">状态：</label>
			<div class="controls">
				
				<c:if test="${applySystem.status eq 2 or applySystem.status eq 3 or applySystem.status eq 4}">
					 <form:radiobutton path="status" value="3"/>上线
					 <form:radiobutton path="status" value="4"/>下线
				</c:if>
				<c:if test="${applySystem.status eq 0}">待审批</c:if>
				<c:if test="${applySystem.status eq 1}">审批不通过</c:if>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">系统IP：</label>
			<div class="controls">
				<form:input path="ips" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">系统接口：</label>
			<div class="controls">
				<form:input path="intfList" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">系统描述：</label>
			<div class="controls">
				<form:textarea path="description" rows="5" maxlength="1500" cssStyle="width:500px"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">联系人：</label>
			<div class="controls">
				<form:input path="linkman" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">联系电话：</label>
			<div class="controls">
				<form:input path="phone" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">电子邮件：</label>
			<div class="controls">
				<form:input path="email" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
<!-- 
		<div class="control-group">
			<label class="control-label">服务URL：</label>
			<div class="controls">
				<form:input path="updateBy" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">FTP用户名：</label>
			<div class="controls">
				<form:input path="updateBy" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">FTP密码：</label>
			<div class="controls">
				<form:input path="createBy" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">FTP主目录：</label>
			<div class="controls">
				<form:input path="updateBy" htmlEscape="false" maxlength="200" class="input-xlarge "/>
			</div>
		</div>
		 -->
		<div class="form-actions">
			<shiro:hasPermission name="extuser:applySystem:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>