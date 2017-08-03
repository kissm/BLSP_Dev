<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>机构管理</title>
	<meta name="decorator" content="blank"/>
	<script type="text/javascript">
		$(document).ready(function () {
			$("#officeForm").validate({
				rules: {
					sort: {max: 999999, min: 1}
				},
				messages: {
				},
				submitHandler: function(form){
					form.submit();
				}
			});
		});
		function saveOrUpdate() {
			$("#officeForm").attr("action", "${ctx}/sys/office/save");
			$("#officeForm").submit();
		}
	</script>
</head>
<body>
	<div class="card">
		<div class="card-header">
			<h2>机构<shiro:hasPermission name="sys:office:edit">${not empty office.id?'修改':'添加'}</shiro:hasPermission></h2>
			<ul class="actions">
				<li>
					<button title="返回" data-toggle="tooltip" data-placement="bottom" class="btn btn-success btn-icon m-r-5" onclick="javascrtpt:history.go(-1)"><i class="md md-arrow-back"></i></button>
				</li>
				<li>
					<shiro:hasPermission name="sys:office:edit">
						<button title="保存" data-toggle="tooltip" data-placement="bottom" class="btn btn-info btn-icon m-r-5" onclick="saveOrUpdate();"><i class="md md-save"></i></button>
					</shiro:hasPermission>
				</li>
			</ul>
		</div>

		<div class="card-body card-padding">
			<sys:message content="${message}"/>
			<form:form id="officeForm" modelAttribute="office" action="${ctx}/sys/office/save" method="post" class="form-horizontal">
				<form:hidden path="id"/>
				<div class="form-group">
					<label class="col-sm-2 control-label">上级机构:</label>
					<div class="col-sm-8">
						<div class="fg-line">
						<sys:treeselect id="office" name="parent.id" value="${office.parent.id}" labelName="parent.name" labelValue="${office.parent.name}"
										title="机构" url="/sys/office/treeData" extId="${office.id}" cssClass="required" allowClear="${office.currentUser.admin}"/>
						</div>
					</div>
				</div>
				<form:input path="area.id" value="1" type="hidden"/>
				<div class="form-group">
					<label class="col-sm-2 control-label" for="name">机构名称:</label>
					<div class="col-sm-8">
						<div class="fg-line">
							<form:input path="name" htmlEscape="false" maxlength="50" class="form-control required" placeholder="机构名称"/>
						</div>
					</div>
				</div>
				<div class="form-group" >
					<label for="code" class="col-sm-2 control-label">机构编码:</label>
					<div class="col-sm-8">
						<div class="fg-line">
							<form:input path="code" htmlEscape="false" maxlength="50" class="form-control required" placeholder="机构编码"/>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">机构类型:</label>
					<div class="col-sm-2 controls">
						<div class="select">
							<div class="fg-line">
								<form:select path="type" class="form-control selectpicker">
									<form:options items="${fns:getDictList('sys_office_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
								</form:select>
							</div>
						</div>
					</div>
					<label class="col-sm-2 control-label">机构级别:</label>
					<div class="col-sm-2 controls">
						<div class="select">
							<div class="fg-line">
								<form:select path="grade" class="form-control selectpicker">
									<form:options items="${fns:getDictList('sys_office_grade')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
								</form:select>
							</div>
						</div>
					</div>
				</div>
				<div class="form-group" >
					<label for="sort" class="col-sm-2 control-label">排序字段:</label>
					<div class="col-sm-2">
						<div class="fg-line">
							<form:input path="sort" type="number" htmlEscape="false" class="form-control required" placeholder="排序字段"/>
						</div>
					</div>
					<label class="col-sm-2 control-label">是否可用:</label>
					<div class="col-sm-2 controls">
						<div class="select">
							<div class="fg-line">
								<form:select path="useable" class="form-control selectpicker">
									<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
								</form:select>
							</div>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">联系地址:</label>
					<div class="col-sm-8 controls">
						<form:input path="address" htmlEscape="false" maxlength="50" class="form-control" placeholder="联系地址"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">邮政编码:</label>
					<div class="col-sm-8 controls">
						<div class="fg-line">
							<form:input path="zipCode" htmlEscape="false" maxlength="50" class="form-control" placeholder="邮政编码"/>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">负责人:</label>
					<div class="col-sm-8 controls">
						<div class="fg-line">
							<form:input path="master" htmlEscape="false" maxlength="50" class="form-control" placeholder="负责人"/>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">电话:</label>
					<div class="col-sm-8 controls">
						<div class="fg-line">
							<form:input path="phone" htmlEscape="false" maxlength="50" class="form-control" placeholder="电话"/>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">传真:</label>
					<div class="col-sm-8 controls">
						<div class="fg-line">
							<form:input path="fax" htmlEscape="false" maxlength="50" class="form-control" placeholder="传真"/>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">邮箱:</label>
					<div class="col-sm-8 controls">
						<div class="fg-line">
							<form:input path="email" htmlEscape="false" maxlength="50" class="form-control" placeholder="邮箱"/>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">备注:</label>
					<div class="col-sm-8 controls">
						<div class="fg-line">
							<form:input path="remarks" htmlEscape="false" maxlength="200" class="form-control auto-size" placeholder="备注"/>
						</div>
					</div>
				</div>
				<%--<c:if test="${empty office.id}">--%>
					<%--<div class="form-group">--%>
						<%--<label class="col-sm-2 control-label">快速添加下级机构:</label>--%>
						<%--<div class="col-sm-8 controls checkbox">--%>
							<%--<form:checkboxes path="childDeptList" items="${fns:getDictList('sys_office_common')}" itemLabel="label" itemValue="value" htmlEscape="false"/>--%>
						<%--</div>--%>
					<%--</div>--%>
				<%--</c:if>--%>
			</form:form>
		</div>
	</div>
</body>
</html>