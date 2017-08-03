<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<%--<head>--%>
	<%--<title>字典管理</title>--%>
	<%--<meta name="decorator" content="blank"/>--%>
	<%--<script type="text/javascript">--%>
		<%--function page(n,s){--%>
			<%--$("#pageNo").val(n);--%>
			<%--$("#pageSize").val(s);--%>
			<%--$("#searchForm").submit();--%>
	    	<%--return false;--%>
	    <%--}--%>
	<%--</script>--%>
<%--</head>--%>
<%--<body>--%>
	<%--<ul class="nav nav-tabs">--%>
		<%--<li class="active"><a href="${ctx}/sys/dict/">字典列表</a></li>--%>
		<%--<shiro:hasPermission name="sys:dict:edit"><li><a href="${ctx}/sys/dict/form?sort=10">字典添加</a></li></shiro:hasPermission>--%>
	<%--</ul>--%>
	<%--<form:form id="searchForm" modelAttribute="dict" action="${ctx}/sys/dict/" method="post" class="breadcrumb form-search">--%>
		<%--<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>--%>
		<%--<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>--%>
		<%--<label>类型：</label>--%>
		<%--<form:select id="type" path="type" class="input-medium">--%>
			<%--<form:option value="" label=""/>--%>
			<%--<form:options items="${typeList}" htmlEscape="false"/>--%>
		<%--</form:select>--%>
		<%--&nbsp;&nbsp;<label>描述 ：</label>--%>
		<%--<form:input path="description" htmlEscape="false" maxlength="50" class="input-medium"/>--%>
		<%--&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>--%>
	<%--</form:form>--%>

	<%--<div class="pagination">${page}</div>--%>
<%--</body>--%>





<head>
	<title>字典管理</title>
	<meta name="decorator" content="blank"/>
	<script type="text/javascript">
		function page(n,s){
			if(n) $("#pageNo").val(n);
			if(s) $("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/sys/dict/list");
			$("#searchForm").submit();
			return false;
		}
		function deleteById(id,lableName){
			url="${ctx}/sys/dict/delete?id="+id;
			swal({
				title: "你确定吗？",
				text: "标签: "+lableName+" 将被删除",
				type: "warning",
				showCancelButton: true,
				confirmButtonColor: "#DD6B55",
				cancelButtonText: '取消',
				confirmButtonText: "是的，删除！",
				closeOnConfirm: false
			}, function(){
				javascrtpt:window.location.href=url;
			});
		}
	</script>
</head>
<body>
<div class="card">
	<div class="card-header">
		<h2>字典列表</h2>
		<ul class="actions">
			<shiro:hasPermission name="sys:user:edit">
				<li>
					<button data-toggle="tooltip" data-placement="bottom" title="新增" class="btn btn-success btn-icon m-r-5" onclick="javascrtpt:window.location.href='${ctx}/sys/dict/form'"><i class="md md-add"></i></button>
				</li>
			</shiro:hasPermission>
			<li>
				<button data-toggle="tooltip" data-placement="bottom" type="button" title="刷新" class="btn btn-default btn-icon m-r-5" onclick="refresh();"><i class="md md-autorenew"></i></button>
			</li>
		</ul>
	</div>

	<div class="card-body card-padding">
		<form:form id="searchForm" modelAttribute="dict" action="${ctx}/sys/dict/" method="post" class="breadcrumb form-search">
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
			<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
			<div class="row">
				<div class="col-xs-4 form-group">
					<div class="fg-line">
						<label class="control-label">类型：</label>
						<form:select id="type" path="type" class="form-control selectpicker">
							<form:option value="" label=""/>
							<form:options items="${typeList}" htmlEscape="false"/>
						</form:select>
					</div>
				</div>
				<div class="col-xs-4 form-group">
					<div class="fg-line">
						<label class="control-label">描述：</label>
						<form:input path="description" htmlEscape="false" maxlength="50" class="form-control"/>
					</div>
				</div>
				<div class="col-xs-2">
					<label class="control-label"></label>
					<button type="button" class="btn btn-primary waves-effect form-control" onclick="return page();">查询</button>
				</div>
				<div class="col-sm-2">
					<label class="control-label"></label>
					<button id="btnReset" type="button" class="btn btn-primary waves-effect form-control" onclick="refresh();">重置</button>
				</div>
			</div>
		</form:form>
	</div>
	<div class="table-responsive">
		<sys:message content="${message}"/>
		<table id="contentTable" class="table table-striped table-vmiddle bootgrid-table">
			<thead>
				<tr>
					<th>键值</th>
					<th>标签</th>
					<th>类型</th>
					<th>描述</th>
					<th>排序</th>
					<shiro:hasPermission name="sys:dict:edit">
						<th class="text-center col-xs-1">操作</th>
					</shiro:hasPermission>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${page.list}" var="dict">
				<tr>
					<td>${dict.value}</td>
					<td><a href="${ctx}/sys/dict/form?id=${dict.id}">${dict.label}</a></td>
					<td><a href="javascript:" onclick="$('#type').val('${dict.type}');$('#searchForm').submit();return false;">${dict.type}</a></td>
					<td>${dict.description}</td>
					<td>${dict.sort}</td>
					<shiro:hasPermission name="sys:dict:edit">
						<td class="text-left text-nowrap">
							<button title="修改" class="btn bgm-orange btn-icon m-r-5" data-toggle="tooltip" data-placement="bottom" type="button"
									onclick="javascrtpt:window.location.href='${ctx}/sys/dict/form?id=${dict.id}'"><i class="md md-edit"></i></button>
							<button title="删除" class="btn btn-danger btn-icon m-r-5" data-toggle="tooltip" data-placement="bottom" type="button"
									onclick="deleteById('${dict.id}','${dict.label}')"><i class="md md-delete"></i></button>
							<button title="添加键值" class="btn bgm-lightgreen btn-icon" data-toggle="tooltip" data-placement="bottom" type="button"
									onclick="javascrtpt:window.location.href='${ctx}/sys/dict/form?type=${dict.type}&sort=${dict.sort+10}&description=${dict.description}'"><i class="md md-menu"></i></button>

						</td>
					</shiro:hasPermission>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	</div>
	${page}

</div>
</body>



</html>