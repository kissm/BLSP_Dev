<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>个人资料查看</title>
	<meta name="decorator" content="blank"/>
	<script type="text/javascript">
		$(document).ready(function() {

		});
	</script>
</head>
<body>
<div class="card">
	<form class="form-horizontal" role="form" id="inputForm" method="post" >
		<div class="card-header">
			<h2>个人资料修改 <small>修改个人资料信息</small></h2>
			<ul class="actions">
				<li>
					<button data-toggle="tooltip" data-placement="bottom" type="button" title="刷新" class="btn btn-default btn-icon m-r-5" onclick="refresh();"><i class="md md-autorenew"></i></button>
				</li>
			</ul>
		</div>
		<div class="card-body card-padding">
			<c:if test="${user.photo != ''}">
				<div class="form-group">
					<label class="col-sm-2 control-label">头像:</label>
					<div class="col-sm-10">
						<img style="height: 150px" src="${user.photo}"/>
					</div>
				</div>
			</c:if>
			<div class="form-group">
				<label class="col-sm-2 control-label">单位:</label>
				<div class="col-sm-10">
					<label class="control-label">${user.company.name}</label>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">部门:</label>
				<div class="col-sm-10">
					<c:set var="officeNames">
						<c:forEach items="${user.officeList}" var="officeName">${officeName.name},</c:forEach>
					</c:set>
					<label class="control-label">${officeNames}</label>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">姓名:</label>
				<div class="col-sm-10">
					<label class="control-label">
						${user.name}
					</label>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">邮箱:</label>
				<div class="col-sm-10">
					<label class="control-label">
						${user.email}
					</label>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">电话:</label>
				<div class="col-sm-10">
					<label class="control-label">
						${user.phone}
					</label>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">电话:</label>
				<div class="col-sm-10">
					<label class="control-label">
						${user.mobile}
					</label>
				</div>
			</div>
			<c:if test="${user.remarks != ''}">
			<div class="form-group">
				<label class="col-sm-2 control-label">备注:</label>
				<div class="col-sm-10">
					<label class="control-label">
						${user.remarks}
					</label>
				</div>
			</div>
			</c:if>
			<div class="form-group">
				<label class="col-sm-2 control-label">用户类型:</label>
				<div class="col-sm-10">
					<label class="control-label">${fns:getDictLabel(user.userType, 'sys_user_type', '无')}</label>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">用户角色:</label>
				<div class="col-sm-10">
					<label class="control-label">${user.roleNames}</label>
				</div>
			</div>
			<%--<div class="form-group">--%>
				<%--<label class="col-sm-2 control-label">上次登录:</label>--%>
				<%--<div class="col-sm-10">--%>
					<%--<label class="control-label">IP: ${user.oldLoginIp}&nbsp;&nbsp;&nbsp;&nbsp;时间：<fmt:formatDate value="${user.oldLoginDate}" type="both" dateStyle="full"/></label>--%>
				<%--</div>--%>
			<%--</div>--%>
		</div>
	</form>
</div>
</body>
</html>