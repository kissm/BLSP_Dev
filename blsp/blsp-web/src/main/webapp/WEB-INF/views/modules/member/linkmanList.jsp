<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>会员管理</title>
	<meta name="decorator" content="default"/>
	<style>
		.form-horizontal .controls{margin-left:90px;}
		.form-horizontal .control-label{width:80px;}
	</style>
</head>
<body>
	<div class="tabbable">
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/member/form?id=${memberInfoDTO.mid}">会员基本信息</a></li>
		<li><a href="${ctx}/member/orderlist?mid=${memberInfoDTO.mid}">客户订单</a></li>
		<!--
		<li><a href="#favorate" data-toggle="tab">收藏夹</a></li>
		<li><a href="#coupons" data-toggle="tab">优惠券</a></li>
		-->
		<li class="active"><a href="#linkmans" data-toggle="tab">联系人</a></li>
		<li><a href="${ctx}/member/oftenpassenger?id=${linkmanDTO.mid}">常旅客</a></li>
		<!--
		<li><a href="#address" data-toggle="tab">邮寄地址</a></li>
		<li><a href="#logs" data-toggle="tab">客户日志</a></li>
		-->
		<li class="pull-right">
			<input onclick="return function(){location.href = '${ctx}/member/list';}();" id="btnCancel" class="btn btn-primary" type="button" value="返 回"/>
		</li>
	</ul>
		<form:form id="memberForm" modelAttribute="memberInfoDTO" class="form-horizontal row-fluid">
			<div class="row-fluid">
				<div class="control-group span4">
					<label class="control-label">帐号:</label>
					<div class="controls">
						<form:input path="code" readonly="true" htmlEscape="false" maxlength="50" class="required input-small"/>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
				</div>
				<div class="control-group span4">
					<label class="control-label">昵称:</label>
					<div class="controls">
						<form:input path="nickname" readonly="true"  htmlEscape="false" maxlength="50" class="required userName"/>
						<span class="help-inline"><font color="red">*</font></span>
					</div>
				</div>
				<div class="control-group span4">
					<label class="control-label">会员状态:</label>
					<div class="controls">
						<label class="lbl">
							<c:choose>
								<c:when test="${memberInfoDTO.status==1}"><span class="label label-success">激活</span></c:when>
								<c:otherwise><span class="label label-default">未激活</span></c:otherwise>
							</c:choose>
						</label>
					</div>
				</div>
			</div>
			<div class="row-fluid">
				<div class="control-group span4">
					<label class="control-label">手机:</label>
					<div class="controls">
						<form:input readonly="true" path="mobile" htmlEscape="false" class="input-small"/>
						<c:choose>
							<c:when test="${1==memberInfoDTO.mobileischeck}">
								<span class="help-inline"><span class="label label-success">已验证</span></span>
							</c:when>
							<c:otherwise><span class="help-inline"><span class="label label-default">未验证</span></span></c:otherwise>
						</c:choose>
					</div>
				</div>
				<div class="control-group span4">
					<label class="control-label">邮箱:</label>
					<div class="controls">
						<form:input  readonly="true" path="email" htmlEscape="false" maxlength="100" class="email"/>
						<c:choose>
							<c:when test="${1==memberInfoDTO.emailischeck}">
								<span class="help-inline"><span class="label label-success">已验证</span></span>
							</c:when>
							<c:otherwise><span class="help-inline"><span class="label label-default">未验证</span></span></c:otherwise>
						</c:choose>
					</div>
				</div>
			</div>
		</form:form>
	<div class="tab-content">
		<div id="member" class="tab-pane">
		</div>
		<div id="order" class="tab-pane"></div>
		<div id="favorate" class="tab-pane"></div>
		<div id="coupons" class="tab-pane"></div>
		<div id="linkmans" class="tab-pane active">
			<table id="contentTable" class="table table-striped table-bordered table-condensed">
				<thead><tr>
					<th>姓名</th>
					<th>性别</th>
					<th>出生日期</th>
					<th>手机</th>
					<th>邮箱</th>
					<th>状态</th>
					<%--<th>角色</th> --%>
				</tr></thead>
				<tbody>
				<c:forEach items="${page.list}" var="user">
					<tr>
						<td>${user.name}</td>
						<td>
							<c:choose>
								<c:when test="${1==user.sex}">男</c:when>
								<c:when test="${2==user.sex}">女</c:when>
								<c:otherwise>未知</c:otherwise>
							</c:choose>
						</td>
						<td>${user.birthdate}</td>
						<td>${user.mobile}</td>
						<td>${user.email}</td>
						<td>
							<c:choose>
								<c:when test="${0==user.status}">不用</c:when>
								<c:otherwise>正常</c:otherwise>
							</c:choose>
						</td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
			<div class="pagination">${page}</div>
		</div>
		<div id="oftenpass" class="tab-pane"></div>
		<div id="address" class="tab-pane"></div>
		<div id="logs" class="tab-pane"></div>
	</div>
	</div>
</body>
<script type="text/javascript">
	$(document).ready(function() {
		$("#no").focus();
		$("#inputForm").validate({
			rules: {
				loginName: {remote: "${ctx}/sys/user/checkLoginName?oldLoginName=" + encodeURIComponent('${user.loginName}')}
			},
			messages: {
				loginName: {remote: "用户登录名已存在"},
				confirmNewPassword: {equalTo: "输入与上面相同的密码"}
			},
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
</html>