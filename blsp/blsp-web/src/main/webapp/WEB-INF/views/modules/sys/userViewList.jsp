<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户可视化看板权限管理</title>
	<meta name="decorator" content="blank"/>
	<script type="text/javascript">
		function page(n,s){
			if(n) $("#pageNo").val(n);
			if(s) $("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/sys/userview/listUserView");
			$("#searchForm").submit();
	    	return false;
	    }
	</script>
</head>
<body>
	<div class="card">
	    <div class="card-header">
	        <h2>用户统计数据查看权限分配列表</h2>
	        <ul class="actions">
				<%--<li>--%>
					<%--<button data-toggle="tooltip" data-placement="bottom" title="新增" class="btn btn-success btn-icon m-r-5" onclick="javascrtpt:window.location.href='${ctx}/sys/user/form'"><i class="md md-add"></i></button>--%>
				<%--</li>--%>
				<li>
					<button data-toggle="tooltip" data-placement="bottom" type="button" title="刷新" class="btn btn-default btn-icon m-r-5" onclick="refresh();"><i class="md md-autorenew"></i></button>
				</li>
			</ul>
	    </div>
		
	    <div class="card-body card-padding">
	    	<form id="searchForm" action="${ctx}/sys/userview/listUserView" method="post" >
	    		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
				<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
				<div class="row">
					<div class="col-xs-4 form-group">
						<div class="fg-line">
							<label class="control-label">登录名：</label>
							<input placeholder="登录名" type="text" name="loginName" value="${user.loginName}"  maxlength="50" class="form-control">
						</div>
					</div>
					<div class="col-xs-4 form-group">
						<div class="fg-line">
							<label class="control-label">姓名：</label>
							<input placeholder="姓名" type="text" name="name" value="${user.name}"  maxlength="50" class="form-control">
						</div>
					</div>
					<div class="col-xs-2">
						<label class="control-label"></label>
						<button id="btnSubmit" type="button" class="btn btn-primary waves-effect form-control" onclick="return page();">查询</button>
					</div>
					<div class="col-sm-2">
						<label class="control-label"></label>
						<button id="btnReset" type="button" class="btn btn-primary waves-effect form-control" onclick="refresh();">重置</button>
					</div>
				</div>
				<sys:message content="${message}"/>
	    	</form>
	    </div>
    	<div class="table-responsive">
           	<table id="contentTable" class="table table-striped table-vmiddle bootgrid-table">
				<thead>
					<tr>
						<th class="col-xs-3">所属机构</th>
						<th class="sort-column login_name"><div data-toggle="tooltip" data-placement="bottom" title="点击排序" >登录名</div></th>
						<th class="sort-column name"><div data-toggle="tooltip" data-placement="bottom" title="点击排序" >姓名</div></th>
						<th>工号</th>
						<th class="text-center col-xs-1">操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.list}" var="user">
						<tr>
							<td>${user.company.name}</td>
							<td><a href="${ctx}/sys/user/form?id=${user.id}">${user.loginName}</a></td>
							<td>${user.name}</td>
							<td>${user.no}</td>
							<td class="text-left text-nowrap">
								<button title="修改" data-toggle="tooltip" data-placement="bottom" class="btn bgm-orange btn-icon m-r-5" onclick="javascrtpt:window.location.href='${ctx}/sys/userview/viewForm?id=${user.id}'"><i class="md md-edit"></i></button>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		${page}
	    
	</div>
</body>
</html>