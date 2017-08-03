<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>接入系统管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<form:form id="searchForm" modelAttribute="coiApplySystemDTO" action="${ctx}/member/applySystem/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
		<ul class="ul-form">
			<li><label>系统名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="200" class="input-medium"/>
			</li>
			<li><label>系统类型：</label>
				<form:select path="sysType" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('sys_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label style="width: 60px; ">状态：</label>
				<form:select path="status" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('coi_system_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns" style="padding-left: 30px;"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>系统名称</th>
				<th>系统类型</th>
				<th>所属用户</th>
				<th>状态</th>
				<th>申请时间</th>
				<!--<th class="sort-column create_date">申请时间</th>
				 <th class="sort-column updateDate">审批时间</th> -->
				<th>系统登录名</th>
				<shiro:hasPermission name="extuser:applySystem:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="applySystem">
			<tr>
				<td>
					${applySystem.name}
				</td>
				<td>
					${fns:getDictLabel(applySystem.sysType, 'sys_type', '')}
				</td>
				<td>
					${applySystem.userName}
				</td>
				<td>
					${fns:getDictLabel(applySystem.status, 'coi_system_status', '')}
				</td>
				<td>
					<fmt:formatDate value="${applySystem.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
<%-- 					<c:if test="${applySystem.status ne 0}">
						<fmt:formatDate value="${applySystem.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</c:if> --%>
					${applySystem.sysLoginName}
				</td>
				<shiro:hasPermission name="extuser:applySystem:edit"><td>
				
    				<a href="${ctx}/member/applySystem/form?id=${applySystem.id}">修改</a>&nbsp;
	    			<a href="${ctx}/member/applySystem/audit?id=${applySystem.id}&&seeFlag=1">查看</a>&nbsp;
    				<c:if test="${applySystem.status eq 0}">
    					<a href="${ctx}/member/applySystem/audit?id=${applySystem.id}">审批</a>
    				</c:if>
    				
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>