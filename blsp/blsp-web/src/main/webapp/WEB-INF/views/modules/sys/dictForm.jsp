<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%--<html>--%>
<%--<head>--%>
	<%--<title>字典管理</title>--%>
	<%--<meta name="decorator" content="default"/>--%>
	<%--<script type="text/javascript">--%>
		<%--$(document).ready(function() {--%>
			<%--$("#value").focus();--%>
			<%--$("#inputForm").validate({--%>
				<%--submitHandler: function(form){--%>
					<%--loading('正在提交，请稍等...');--%>
					<%--form.submit();--%>
				<%--},--%>
				<%--errorContainer: "#messageBox",--%>
				<%--errorPlacement: function(error, element) {--%>
					<%--$("#messageBox").text("输入有误，请先更正。");--%>
					<%--if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){--%>
						<%--error.appendTo(element.parent().parent());--%>
					<%--} else {--%>
						<%--error.insertAfter(element);--%>
					<%--}--%>
				<%--}--%>
			<%--});--%>
		<%--});--%>
	<%--</script>--%>
<%--</head>--%>
<%--<body>--%>
	<%--<ul class="nav nav-tabs">--%>
		<%--<li><a href="${ctx}/sys/dict/">字典列表</a></li>--%>
		<%--<li class="active"><a href="${ctx}/sys/dict/form?id=${dict.id}">字典<shiro:hasPermission name="sys:dict:edit">${not empty dict.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="sys:dict:edit">查看</shiro:lacksPermission></a></li>--%>
	<%--</ul><br/>--%>
	<%--<form:form id="inputForm" modelAttribute="dict" action="${ctx}/sys/dict/save" method="post" class="form-horizontal">--%>
		<%--&lt;%&ndash;<form:hidden path="id"/>&ndash;%&gt;--%>
		<%--<sys:message content="${message}"/>--%>


		<%--<div class="form-actions">--%>
			<%--<shiro:hasPermission name="sys:dict:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>--%>
			<%--<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>--%>
		<%--</div>--%>
	<%--</form:form>--%>
<%--</body>--%>
<%--</html>--%>


<html>
<head>
	<title>字典管理</title>
	<meta name="decorator" content="blank"/>
	<script type="text/javascript">
		$(document).ready(function () {
			$("#inputForm").validate({
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
			$("#inputForm").attr("action", "${ctx}/sys/dict/save");
			$("#inputForm").submit();
		}
	</script>
</head>
<body>
<div class="card">
	<div class="card-header">
		<h2>字典<shiro:hasPermission name="sys:dict:edit">${not empty office.id?'修改':'添加'}</shiro:hasPermission></h2>
		<ul class="actions">
			<li>
				<button title="返回" data-toggle="tooltip" data-placement="bottom" class="btn btn-success btn-icon m-r-5" onclick="javascrtpt:history.go(-1)"><i class="md md-arrow-back"></i></button>
			</li>
			<li>
				<shiro:hasPermission name="sys:dict:edit">
					<button title="保存" data-toggle="tooltip" data-placement="bottom" class="btn btn-info btn-icon m-r-5" onclick="saveOrUpdate();"><i class="md md-save"></i></button>
				</shiro:hasPermission>
			</li>
		</ul>
	</div>

	<div class="card-body card-padding">
		<sys:message content="${message}"/>
		<form:form id="inputForm" modelAttribute="dict" action="${ctx}/sys/dict/save" method="post" class="form-horizontal">
			<form:hidden path="id"/>
			<div class="form-group">
				<label class="col-sm-2 control-label" for="value">键值:</label>
				<div class="col-sm-8">
					<div class="fg-line">
						<form:input path="value" htmlEscape="false" maxlength="50" class="form-control required" placeholder="键值"/>
					</div>
				</div>
			</div>
			<div class="form-group" >
				<label for="label" class="col-sm-2 control-label">标签:</label>
				<div class="col-sm-8">
					<div class="fg-line">
						<form:input path="label" htmlEscape="false" maxlength="50" class="form-control required" placeholder="标签"/>
					</div>
				</div>
			</div>
			<div class="form-group" >
				<label for="type" class="col-sm-2 control-label">类型:</label>
				<div class="col-sm-8">
					<div class="fg-line">
						<form:input path="type" htmlEscape="false" maxlength="50" class="form-control required abc" placeholder="类型"/>
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
			</div>
			<div class="form-group" >
				<label for="description" class="col-sm-2 control-label">描述:</label>
				<div class="col-sm-8">
					<div class="fg-line">
						<form:input path="description" htmlEscape="false" maxlength="50" class="form-control required" placeholder="描述"/>
					</div>
				</div>
			</div>
			<div class="form-group" >
				<label for="remarks" class="col-sm-2 control-label">备注:</label>
				<div class="col-sm-8">
					<div class="fg-line">
						<form:input path="remarks" htmlEscape="false" maxlength="100" class="form-control" placeholder="备注"/>
					</div>
				</div>
			</div>



			<%--<div class="form-group">--%>
			<%--<label class="col-sm-2 control-label">机构类型:</label>--%>
			<%--<div class="col-sm-2 controls">--%>
				<%--<div class="select">--%>
					<%--<div class="fg-line">--%>
						<%--<form:select path="type" class="form-control selectpicker">--%>
							<%--<form:options items="${fns:getDictList('sys_office_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>--%>
						<%--</form:select>--%>
					<%--</div>--%>
				<%--</div>--%>
			<%--</div>--%>
			<%--<label class="col-sm-2 control-label">机构级别:</label>--%>
			<%--<div class="col-sm-2 controls">--%>
				<%--<div class="select">--%>
					<%--<div class="fg-line">--%>
						<%--<form:select path="grade" class="form-control selectpicker">--%>
							<%--<form:options items="${fns:getDictList('sys_office_grade')}" itemLabel="label" itemValue="value" htmlEscape="false"/>--%>
						<%--</form:select>--%>
					<%--</div>--%>
				<%--</div>--%>
			<%--</div>--%>
		<%--</div>--%>
			<%--<div class="form-group" >--%>
				<%--<label for="sort" class="col-sm-2 control-label">排序字段:</label>--%>
				<%--<div class="col-sm-2">--%>
					<%--<div class="fg-line">--%>
						<%--<form:input path="sort" type="number" htmlEscape="false" class="form-control required" placeholder="排序字段"/>--%>
					<%--</div>--%>
				<%--</div>--%>
				<%--<label class="col-sm-2 control-label">是否可用:</label>--%>
				<%--<div class="col-sm-2 controls">--%>
					<%--<div class="select">--%>
						<%--<div class="fg-line">--%>
							<%--<form:select path="useable" class="form-control selectpicker">--%>
								<%--<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>--%>
							<%--</form:select>--%>
						<%--</div>--%>
					<%--</div>--%>
				<%--</div>--%>
			<%--</div>--%>
			<%--<div class="form-group">--%>
				<%--<label class="col-sm-2 control-label">联系地址:</label>--%>
				<%--<div class="col-sm-8 controls">--%>
					<%--<form:input path="address" htmlEscape="false" maxlength="50" class="form-control" placeholder="联系地址"/>--%>
				<%--</div>--%>
			<%--</div>--%>
			<%--<div class="form-group">--%>
				<%--<label class="col-sm-2 control-label">邮政编码:</label>--%>
				<%--<div class="col-sm-8 controls">--%>
					<%--<div class="fg-line">--%>
						<%--<form:input path="zipCode" htmlEscape="false" maxlength="50" class="form-control" placeholder="邮政编码"/>--%>
					<%--</div>--%>
				<%--</div>--%>
			<%--</div>--%>
			<%--<div class="form-group">--%>
				<%--<label class="col-sm-2 control-label">负责人:</label>--%>
				<%--<div class="col-sm-8 controls">--%>
					<%--<div class="fg-line">--%>
						<%--<form:input path="master" htmlEscape="false" maxlength="50" class="form-control" placeholder="负责人"/>--%>
					<%--</div>--%>
				<%--</div>--%>
			<%--</div>--%>
			<%--<div class="form-group">--%>
				<%--<label class="col-sm-2 control-label">电话:</label>--%>
				<%--<div class="col-sm-8 controls">--%>
					<%--<div class="fg-line">--%>
						<%--<form:input path="phone" htmlEscape="false" maxlength="50" class="form-control" placeholder="电话"/>--%>
					<%--</div>--%>
				<%--</div>--%>
			<%--</div>--%>
			<%--<div class="form-group">--%>
				<%--<label class="col-sm-2 control-label">传真:</label>--%>
				<%--<div class="col-sm-8 controls">--%>
					<%--<div class="fg-line">--%>
						<%--<form:input path="fax" htmlEscape="false" maxlength="50" class="form-control" placeholder="传真"/>--%>
					<%--</div>--%>
				<%--</div>--%>
			<%--</div>--%>
			<%--<div class="form-group">--%>
				<%--<label class="col-sm-2 control-label">邮箱:</label>--%>
				<%--<div class="col-sm-8 controls">--%>
					<%--<div class="fg-line">--%>
						<%--<form:input path="email" htmlEscape="false" maxlength="50" class="form-control" placeholder="邮箱"/>--%>
					<%--</div>--%>
				<%--</div>--%>
			<%--</div>--%>
			<%--<div class="form-group">--%>
				<%--<label class="col-sm-2 control-label">备注:</label>--%>
				<%--<div class="col-sm-8 controls">--%>
					<%--<div class="fg-line">--%>
						<%--<form:input path="remarks" htmlEscape="false" maxlength="200" class="form-control auto-size" placeholder="备注"/>--%>
					<%--</div>--%>
				<%--</div>--%>
			<%--</div>--%>
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