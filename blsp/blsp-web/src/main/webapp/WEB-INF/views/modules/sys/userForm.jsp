<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户管理</title>
	<meta name="decorator" content="blank"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#inputForm").validate({
				rules: {
					loginName: {remote: "${ctx}/sys/user/checkLoginName?oldLoginName=" + encodeURIComponent('${user.loginName}')}
				},
				messages: {
					loginName: {remote: "用户登录名已存在"},
					confirmNewPassword: {equalTo: "输入与上面相同的密码"},
					roleIdList: "请选择角色"
				},
				submitHandler: function(form){
					form.submit();
				}
			});
		});
		function saveOrUpdate() {

			$("#inputForm").attr("action", "${ctx}/sys/user/save");
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
			<h2>用户<shiro:hasPermission name="sys:user:edit">${not empty user.id?'修改':'添加'}</shiro:hasPermission>
				<small><shiro:hasPermission name="sys:user:edit">${not empty user.id?'修改':'添加'}</shiro:hasPermission>个人资料信息</small></h2>
			<ul class="actions">
				<li>
					<button data-toggle="tooltip" data-placement="bottom" title="返回" class="btn btn-success btn-icon m-r-5" onclick="javascrtpt:history.go(-1)"><i class="md md-arrow-back"></i></button>
				</li>
				<li>
					<shiro:hasPermission name="sys:user:edit">
						<button data-toggle="tooltip" data-placement="bottom" title="保存" class="btn btn-info btn-icon m-r-5" onclick="saveOrUpdate();"><i class="md md-save"></i></button>
					</shiro:hasPermission>
				</li>
			</ul>
		</div>
		<form:form id="inputForm" modelAttribute="user" action="${ctx}/sys/user/save" method="post" class="form-horizontal">
			<form:hidden path="id"/>
			<div class="card-body card-padding">
				<sys:message content="${message}"/>
				<div class="form-group">
					<label class="col-sm-2 control-label">头像:</label>
					<div class="col-sm-10">
						<sys:file id="file" type="img" fileAddress="photo" downloadFileAddress="${user.photo}" ></sys:file>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">单位:</label>
					<div class="col-sm-10">
						<sys:treeselect id="company" name="company.id" value="${user.company.id}" labelName="company.name" labelValue="${user.company.name}" title="公司" url="/sys/office/treeData?type=1" cssClass="required" allowClear="true"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">部门:</label>
					<div class="col-sm-10">
						<c:set var="officeNames">
							<c:forEach items="${user.officeList}" var="officeName">${officeName.name},</c:forEach>
						</c:set>
						<sys:treeselect id="office" name="office.id" value="${user.office.id}" labelName="office.name" labelValue="${officeNames}" title="部门" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="true" checked="true"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">工号:</label>
					<div class="col-sm-10">
						<div class="fg-line">
							<%--${user.no}--%>
							<%--<input name="no" value="${user.no}" type="hidden"/>--%>
							<form:input path="no" htmlEscape="false" maxlength="50" class="form-control required" placeholder="工号"/>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">姓名:</label>
					<div class="col-sm-10">
						<div class="fg-line">
							<%--${user.name}--%>
							<%--<input name="name" value="${user.name}" type="hidden"/>--%>
							<form:input path="name" htmlEscape="false" maxlength="50" type="text" class="form-control required" placeholder="姓名"/>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">登录名:</label>
					<div class="col-sm-10">
						<div class="fg-line">
							<%--${user.loginName}--%>
							<%--<input name="loginName" value="${user.loginName}" type="hidden"/>--%>
							<input id="oldLoginName" name="oldLoginName" type="hidden" value="${user.loginName}">
							<form:input path="loginName" htmlEscape="false" type="text" maxlength="50" class="form-control required" placeholder="登录名" />
						</div>
					</div>
				</div>
				<%--<div class="form-group">--%>
					<%--<label for="newPassword" class="col-sm-2 control-label">密码:</label>--%>
					<%--<div class="col-sm-10">--%>
						<%--<div class="fg-line">--%>
							<%--<form:input class="form-control ${empty user.id?'required':''}"  id="newPassword" path="newPassword" type="password"--%>
										<%--placeholder="${not empty user.id?'若不修改密码，请留空。':'密码'}" minlength="6" maxlength="18" />--%>
						<%--</div>--%>
					<%--</div>--%>
				<%--</div>--%>
				<%--<div class="form-group">--%>
					<%--<label for="confirmNewPassword" class="col-sm-2 control-label">确认密码:</label>--%>
					<%--<div class="col-sm-10">--%>
						<%--<div class="fg-line">--%>
							<%--<input id="confirmNewPassword" name="confirmNewPassword" type="password" class="form-control auto-size"--%>
										<%--value="" maxlength="18" minlength="6" equalTo="#newPassword"/>--%>
						<%--</div>--%>
					<%--</div>--%>
				<%--</div>--%>
				<div class="form-group">
					<label class="col-sm-2 control-label">邮箱:</label>
					<div class="col-sm-10">
						<div class="fg-line">
							<%--${user.email}--%>
							<%--<input name="email" value="${user.email}" type="hidden"/>--%>
							<form:input path="email" type="txt" htmlEscape="false" maxlength="50" class="form-control email" placeholder="邮箱" />
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">电话:</label>
					<div class="col-sm-10">
						<div class="fg-line">
							<%--${user.phone}--%>
							<%--<input name="phone" value="${user.phone}" type="hidden"/>--%>
							<form:input path="phone" htmlEscape="false" maxlength="100" class="form-control auto-size phone" minlength="6" placeholder="电话" />
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">手机:</label>
					<div class="col-sm-10">
						<div class="fg-line">
							<%--${user.mobile}--%>
							<%--<input name="mobile" value="${user.mobile}" type="hidden"/>--%>
							<form:input path="mobile" htmlEscape="false" maxlength="100" class="form-control auto-size mobile" minlength="6" placeholder="手机" />
						</div>
					</div>
				</div>
				<%--<div class="form-group">--%>
					<%--<label for="remarks" class="col-sm-2 control-label">备注:</label>--%>
					<%--<div class="col-sm-10">--%>
						<%--<div class="fg-line">--%>
							<%--<textarea class="form-control auto-size" placeholder="备注描述信息" id="remarks" name="remarks" maxlength="200" >${user.remarks}</textarea>--%>
						<%--</div>--%>
					<%--</div>--%>
				<%--</div>--%>
				<div class="form-group">
					<label class="col-sm-2 control-label">用户角色:</label>
					<div  class="col-sm-10 required">
						<c:forEach items="${allRoles}" var="userRole">
							<div class="checkbox m-b-15">
								<label>
									<c:forEach items="${user.roleList}" var="role">
										<c:if test="${userRole.id eq role.id }"><c:set var="isChecked" value="checked"/></c:if>
									</c:forEach>
									<input name="roleIdList" type="checkbox" value="${userRole.id}" id="${userRole.id}" ${isChecked} class="required"><i class="input-helper"></i>${userRole.name}
								</label>
								<c:set var="isChecked" value=""/>
							</div>
						</c:forEach>
					</div>
				</div>
				<%--<div class="form-group">--%>
					<%--<label class="col-sm-2 control-label">上次登录:</label>--%>
					<%--<div class="col-sm-10">--%>
						<%--<label class="control-label">IP: ${user.oldLoginIp}&nbsp;&nbsp;&nbsp;&nbsp;时间：<fmt:formatDate value="${user.oldLoginDate}" type="both" dateStyle="full"/></label>--%>
					<%--</div>--%>
				<%--</div>--%>
				<shiro:hasPermission name="sys:user:edit">
					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-10">
							<button id="btnSubmit" type="submit" class="btn btn-primary waves-effect">保 存</button>
						</div>
					</div>
				</shiro:hasPermission>
			</div>
		</form:form>
	</div>

</body>
</html>