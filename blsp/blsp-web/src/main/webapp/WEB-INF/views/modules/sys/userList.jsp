<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户管理</title>
	<meta name="decorator" content="blank"/>
	<script type="text/javascript">
		function page(n,s){
			if(n) $("#pageNo").val(n);
			if(s) $("#pageSize").val(s);
			$("#searchForm").attr("action","${ctx}/sys/user/list");
			$("#searchForm").submit();
	    	return false;
	    }
		function deleteById(id){
			url="${ctx}/sys/user/delete?id="+id;
			swal({
				title: "你确定吗？",
				text: "此信息将被删除",
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
	        <h2>用户列表</h2>
	        <ul class="actions">
				<%--<shiro:hasPermission name="sys:user:edit">--%>
					<%--<li>--%>
						<%--<button data-toggle="tooltip" data-placement="bottom" title="新增" class="btn btn-success btn-icon m-r-5" onclick="javascrtpt:window.location.href='${ctx}/sys/user/form'"><i class="md md-add"></i></button>--%>
					<%--</li>--%>
				<%--</shiro:hasPermission>--%>
				<li>
					<button data-toggle="tooltip" data-placement="bottom" type="button" title="刷新" class="btn btn-default btn-icon m-r-5" onclick="refresh();"><i class="md md-autorenew"></i></button>
				</li>
			</ul>
	    </div>
		
	    <div class="card-body card-padding">
	    	<form id="searchForm" action="${ctx}/sys/user/list" method="post" >
	    		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
				<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
				<div class="row">
					<%--<div class="col-xs-3 form-group">--%>
						<%--<div class="fg-line">--%>
							<%--<label class="control-label">单位：</label>--%>
							<%--<sys:treeselect id="company" name="company.id" value="${user.company.id}" labelName="company.name" labelValue="${user.company.name}" title="公司" url="/sys/office/treeData?type=1" cssClass="input-small" allowClear="true"/>--%>
						<%--</div>--%>
					<%--</div>--%>
					<div class="col-xs-4 form-group">
						<div class="fg-line">
							<label class="control-label">登录名：</label>
							<input placeholder="登录名" type="text" name="loginName" value="${user.loginName}"  maxlength="50" class="form-control">
						</div>
					</div>
					<%--<div class="col-xs-4 form-group">--%>
						<%--<div class="fg-line">--%>
							<%--<label class="control-label">部门：</label>--%>
							<%--<sys:treeselect id="office" name="office.id" value="${user.office.id}" labelName="office.name" labelValue="${user.office.name}" title="部门" url="/sys/office/treeData?type=2" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>--%>
						<%--</div>--%>
					<%--</div>--%>
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
						<th class="col-xs-3">单位</th>
						<%--<th>部门</th>--%>
						<th class="sort-column login_name"><div data-toggle="tooltip" data-placement="bottom" title="点击排序" >登录名</div></th>
						<th class="sort-column name"><div data-toggle="tooltip" data-placement="bottom" title="点击排序" >姓名</div></th>
						<th>工号</th>
						<th class="col-xs-2">邮箱</th>
						<th class="col-xs-2">手机</th>
						<%--<th>角色</th> --%>
						<shiro:hasPermission name="sys:user:edit">
							<th class="text-center col-xs-1">操作</th>
						</shiro:hasPermission>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${page.list}" var="user">
						<tr>
							<td>${user.company.name}</td>
							<%--<c:set var="officeNames">--%>
								<%--<c:forEach items="${user.officeList}" var="officeName">${officeName.name},</c:forEach>--%>
							<%--</c:set>--%>
							<%--<td>${officeNames}</td>--%>
							<td><a href="${ctx}/sys/user/form?id=${user.id}">${user.loginName}</a></td>
							<td>${user.name}</td>
							<td>${user.no}</td>
							<td>${user.email}</td>
							<td>${user.mobile}</td>
							<%--<td>${user.roleNames}</td> --%>
							<shiro:hasPermission name="sys:user:edit">
								<td class="text-left text-nowrap">
									<button title="修改" data-toggle="tooltip" data-placement="bottom" class="btn bgm-orange btn-icon m-r-5" onclick="javascrtpt:window.location.href='${ctx}/sys/user/form?id=${user.id}'"><i class="md md-edit"></i></button>
									<%--<button title="删除" data-toggle="tooltip" data-placement="bottom" class="btn btn-danger btn-icon" onclick="deleteById('${user.id}')"><i class="md md-delete"></i></button>--%>
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