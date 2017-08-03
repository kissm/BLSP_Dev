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
		
		function reject() {
			$('#flag').val('2');
			if ($('#auditText').val() == '') {
				alert("请填写审批意见");
				return false;
			}
			$('#inputForm').submit();
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/member/applySystem/">接入系统列表</a></li>
		<li class="active"><a href="${ctx}/member/applySystem/form?id=${applySystem.id}">接入系统<shiro:hasPermission name="extuser:applySystem:edit">${not empty applySystem.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="extuser:applySystem:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="applySystem" action="${ctx}/member/applySystem/saveAudit" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<form:hidden id="flag" path="auditFlag"/>
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
				${fns:getDictLabel(applySystem.status, 'coi_system_status', '')}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">系统IP：</label>
			<div class="controls">
				${applySystem.ips}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">系统接口：</label>
			<div class="controls">
				${applySystem.intfList}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">系统描述：</label>
			<div class="controls">
				${applySystem.description}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">联系人：</label>
			<div class="controls">
				${applySystem.linkman}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">联系电话：</label>
			<div class="controls">
				${applySystem.phone}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">电子邮件：</label>
			<div class="controls">
				${applySystem.email}
			</div>
		</div>
		
		<c:if test="${applySystem.status eq 2 or applySystem.status eq 3}">
			  <div class="control-group" style="color: #1b6db8;">
			    <label class="control-label">登录账号：</label>
			    <div class="controls">
			    	${applySystem.sysLoginName}
			   	</div>
			 </div>
			  <div class="control-group" style="color: #1b6db8;">
			    <label class="control-label">登录密码：</label>
			    <div class="controls">
			    	${applySystem.password}
			   	</div>
			 </div>
		</c:if>

		<%-- <div class="control-group">
			<label class="control-label">服务URL：</label>
			<div class="controls">
				${applySystem.intfList}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">FTP用户名：</label>
			<div class="controls">
				${applySystem.intfList}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">FTP密码：</label>
			<div class="controls">
				${applySystem.intfList}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">FTP主目录：</label>
			<div class="controls">
				${applySystem.intfList}
			</div>
		</div> --%>
		<c:if test="${not empty approves && seeFlag eq 1}">
			<div class="control-group">
				<label class="control-label">审批时间：</label>
				<div class="controls">
					<fmt:formatDate value="${approves.createDate}" type="both"/>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">审批意见：</label>
				<div class="controls">
					${approves.message}
				</div>
			</div>
		</c:if>
		
		<c:if test="${applySystem.status eq 0 && seeFlag ne 1}">
		<div class="control-group">
			<label class="control-label">审批意见：</label>
			<div class="controls">
				<form:textarea path="auditText" rows="5" maxlength="1500" cssStyle="width:500px"/>
			</div>
		</div>
		</c:if>	
		<div class="form-actions">
			<shiro:hasPermission name="extuser:applySystem:edit">
				<c:if test="${applySystem.status eq 0 && seeFlag ne 1}">
					<input id="btnSubmit" class="btn btn-primary" type="submit" value="审批通过" onclick="$('#flag').val('1')"/>&nbsp;
					<input id="btnSubmit" class="btn btn-primary" type="button" value="退回" onclick="reject()"/>&nbsp;
				</c:if>
			</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>