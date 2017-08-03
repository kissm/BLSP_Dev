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
		<li class="active"><a href="#member" data-toggle="tab">会员基本信息</a></li>
		<li><a href="${ctx}/member/orderlist?mid=${memberInfoDTO.mid}">客户订单</a></li>
		<!--
		<li><a href="#favorate" data-toggle="tab">收藏夹</a></li>
		<li><a href="#coupons" data-toggle="tab">优惠券</a></li>-->
		<li><a href="${ctx}/member/linkman?id=${memberInfoDTO.mid}">联系人</a></li>
		<li><a href="${ctx}/member/oftenpassenger?id=${memberInfoDTO.mid}">常旅客</a></li>
		<!--<li><a href="#address" data-toggle="tab">邮寄地址</a></li>
		<li><a href="#logs" data-toggle="tab">客户日志</a></li>-->
		<li class="pull-right">
			<input onclick="return function(){location.href = '${ctx}/member/list';}();" id="btnCancel" class="btn btn-primary" type="button" value="返 回"/>
		</li>
	</ul><br/>
	<div class="tab-content">
	<div id="member" class="tab-pane active">
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
						<c:when test="${memberInfoDTO.status==1}"><span class="label label-success">已激活</span></c:when>
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
		<div class="control-group span6">
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
	<hr/>
	<form:form id="inputForm" modelAttribute="memberInfoDTO" action="" method="post" class="form-horizontal">
		<div class="row-fluid">
		<div class="span6">
			<form:hidden path="mid"/>
			<sys:message content="${message}"/>
			<div class="control-group">
				<label class="control-label">姓名:</label>
				<div class="controls">
					<form:input path="name" readonly="true" htmlEscape="false" maxlength="50" class="required"/>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">性别:</label>
				<div class="controls">
					<form:select path="sex" readonly="true" htmlEscape="false">
						<form:option value="9">未知</form:option>
						<form:option value="1">男</form:option>
						<form:option value="2">女</form:option>
					</form:select>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">生日:</label>
				<div class="controls">
					<form:input path="birthdate" readonly="true" htmlEscape="false" maxlength="50" class="required"/>
				</div>
			</div>
			<div class="control-group ">
				<label class="control-label">注册时间:</label>
				<div class="controls">
					<input readonly="true" name="createtime" type="text" value="<fmt:formatDate value="${memberInfoDTO.createtime}" type="both" dateStyle="full" />"/>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">注册渠道:</label>
				<div class="controls">
					<form:input  readonly="true" path="regsource" htmlEscape="false" maxlength="100"/>
				</div>
			</div>
		</div>
		<div class="span4">
			<div class="control-group">
				<label class="control-label">会员头像:</label>
				<div class="controls">
					<c:if test="${!empty memberInfoDTO.avatorurl}"><img src="${memberInfoDTO.avatorurl}"/></c:if>
				</div>
			</div>
		</div>
		</div>
		<c:if test="${not empty memberInfoDTO.mid}">
			<div class="control-group">
				<label class="control-label">最后登陆:</label>
				<div class="controls">
					<label class="lbl">IP: ${memberInfoDTO.loginIp}&nbsp;&nbsp;&nbsp;&nbsp;时间：<fmt:formatDate value="${memberInfoDTO.loginDate}" type="both" dateStyle="full"/></label>
				</div>
			</div>
		</c:if>
		<div class="form-actions">
		</div>
	</form:form>
	</div>
		<div id="order" class="tab-pane"></div>
		<div id="favorate" class="tab-pane"></div>
		<div id="coupons" class="tab-pane"></div>
		<div id="linkmans" class="tab-pane"></div>
		<div id="oftenpass" class="tab-pane"></div>
		<div id="address" class="tab-pane"></div>
		<div id="logs" class="tab-pane"></div>
	</div>
	</div>
</body>
</html>