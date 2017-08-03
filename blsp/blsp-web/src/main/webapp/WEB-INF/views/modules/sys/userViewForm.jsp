<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户查看看板权限管理</title>
	<meta name="decorator" content="blank"/>
	<script type="text/javascript">
		$(document).ready(function() {
		});
		function saveOrUpdate() {
			$("#inputForm").attr("action", "${ctx}/sys/userview/saveUserView");
			$("#inputForm").submit();
		}
	</script>
	<style>
		.form-search input, .form-inline input, .form-horizontal input, .form-search textarea, .form-inline textarea, .form-horizontal textarea, .form-search select, .form-inline select, .form-horizontal select, .form-search .help-inline, .form-inline .help-inline, .form-horizontal .help-inline, .form-search .uneditable-input, .form-inline .uneditable-input, .form-horizontal .uneditable-input, .form-search .input-prepend, .form-inline .input-prepend, .form-horizontal .input-prepend, .form-search .input-append, .form-inline .input-append, .form-horizontal .input-append
	</style>
</head>
<body>
	<div class="card">
		<div class="card-header">
			<h2>用户看版查看权限${not empty user.id?'修改':'添加'}
				<small>${not empty user.id?'修改':'添加'}个人看版查看权限</small></h2>
			<ul class="actions">
				<li>
					<button data-toggle="tooltip" data-placement="bottom" title="返回" class="btn btn-success btn-icon m-r-5" onclick="javascrtpt:history.go(-1)"><i class="md md-arrow-back"></i></button>
				</li>
				<li>
					<button data-toggle="tooltip" data-placement="bottom" title="保存" class="btn btn-info btn-icon m-r-5" onclick="saveOrUpdate();"><i class="md md-save"></i></button>
				</li>
			</ul>
		</div>
		<form:form id="inputForm" modelAttribute="user" action="${ctx}/sys/userview/saveUserView" method="post" class="form-horizontal">
			<form:hidden path="id"/>
			<div class="card-body card-padding">
				<sys:message content="${message}"/>
				<div class="form-group">
					<label class="col-sm-2 control-label">用户可查看部门:</label>
					<div  class="col-sm-10 required">
						<c:forEach items="${allOfficeId}" var="userOfficeId">
							<div class="checkbox m-b-15">
								<label>
									<c:forEach items="${user.officeViewList}" var="officeViewId">
										<c:if test="${userOfficeId eq officeViewId }"><c:set var="isChecked" value="checked"/></c:if>
									</c:forEach>
									<input name="officeViewList" type="checkbox" value="${userOfficeId}" id="${userOfficeId}" ${isChecked} class="required"><i class="input-helper"></i>${fns:getOffice(userOfficeId).name}
								</label>
								<c:set var="isChecked" value=""/>
							</div>
						</c:forEach>
					</div>
				</div>

				<div class="form-group">
					<label class="col-sm-2 control-label">单位:</label>
					<div class="col-sm-10">
						<div class="fg-line">
							<label class="control-label">${user.company.name}</label>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">部门:</label>
					<div class="col-sm-10">
						<c:set var="officeNames">
							<c:forEach items="${user.officeList}" var="officeName">${officeName.name},</c:forEach>
						</c:set>
						<div class="fg-line">
							<label class="control-label">${officeNames}</label>
						</div>
						<%--<sys:treeselect id="office" name="office.id" value="${user.office.id}" labelName="office.name" labelValue="${officeNames}" title="部门" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="true" checked="true"/>--%>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">姓名:</label>
					<div class="col-sm-10">
						<div class="fg-line">
							<label class="control-label">${user.name}</label>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">登录名:</label>
					<div class="col-sm-10">
						<div class="fg-line">
							<label class="control-label">${user.loginName}</label>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">邮箱:</label>
					<div class="col-sm-10">
						<div class="fg-line">
							<label class="control-label">${user.email}</label>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">电话:</label>
					<div class="col-sm-10">
						<div class="fg-line">
							<label class="control-label">${user.phone}</label>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">手机:</label>
					<div class="col-sm-10">
						<div class="fg-line">
							<label class="control-label">${user.mobile}</label>
						</div>
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-10">
						<button id="btnSubmit" type="submit" class="btn btn-primary waves-effect">保 存</button>
					</div>
				</div>
			</div>
		</form:form>
	</div>

</body>
</html>