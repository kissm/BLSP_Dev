<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>菜单管理</title>
	<meta name="decorator" content="blank"/>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#treeTable").treeTable({expandLevel : 3}).show();
		});
    	function updateSort() {
	    	$("#listForm").attr("action", "${ctx}/sys/menu/updateSort");
	    	$("#listForm").submit();
    	}

		function deleteById(url){
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
	        <h2>菜单列表</h2>
	        <ul class="actions">
				<li>
					<button data-toggle="tooltip" data-placement="bottom" title="新增" class="btn btn-success btn-icon m-r-5" onclick="javascrtpt:window.location.href='${ctx}/sys/menu/form'"><i class="md md-add"></i></button>
				</li>
				<shiro:hasPermission name="sys:menu:edit">
					<c:if test="${fns:getUser().admin}">
					<li>
						<button data-toggle="tooltip" data-placement="bottom" title="保存排序" class="btn btn-info btn-icon m-r-5" onclick="updateSort();"><i class="md md-save"></i></button>
					</li>
					</c:if>
				</shiro:hasPermission>
				<li>
					<button data-toggle="tooltip" data-placement="bottom" title="刷新" class="btn btn-default btn-icon m-r-5" onclick="javascrtpt:window.location.href='${ctx}/sys/menu'"><i class="md md-autorenew"></i></button>
				</li>
			</ul>
	    </div>
		
	    <div class="card-body card-padding">
	    	<sys:message content="${message}"/>
	    	<div class="table-responsive">
		    	<form id="listForm" method="post">
	            	<table id="treeTable" class="table table-striped table-vmiddle bootgrid-table">
						<thead>
							<tr>
								<th class="col-sm-3">名称</th>
								<th class="col-sm-3">链接</th>
								<th class="text-center">排序</th>
								<th>可见</th>
								<th>权限标识</th>
								<shiro:hasPermission name="sys:menu:edit">
									<th class="text-center col-xs-1">操作</th>
								</shiro:hasPermission>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${list}" var="menu">
								<tr id="${menu.id}" pId="${menu.parent.id ne '1'?menu.parent.id:'0'}">
									<td nowrap>
										<i class="md ${not empty menu.icon?menu.icon:'hide'}"></i>
										<a href="${ctx}/sys/menu/form?id=${menu.id}">${menu.name}</a>
									</td>
									<td title="${menu.href}">${fns:abbr(menu.href,50)}</td>
									<td style="text-align:center;">
										<shiro:hasPermission name="sys:menu:edit">
											<input type="hidden" name="ids" value="${menu.id}"/>
											<div class="fg-line">
												<input name="sorts" value="${menu.sort}" type="number" class="form-control input-sm text-center" placeholder="排序">
											</div>
										</shiro:hasPermission>
										<shiro:lacksPermission name="sys:menu:edit">
											${menu.sort}
										</shiro:lacksPermission>
									</td>
									<td>${menu.isShow eq '1'?'显示':'隐藏'}</td>
									<td title="${menu.permission}">${fns:abbr(menu.permission,50)}</td>
									<shiro:hasPermission name="sys:menu:edit">
									<td class="text-left text-nowrap">
										<button title="修改" class="btn bgm-orange btn-icon m-r-5" data-toggle="tooltip" data-placement="bottom" type="button"
												onclick="javascrtpt:window.location.href='${ctx}/sys/menu/form?id=${menu.id}'"><i class="md md-edit"></i></button>
										<c:if test="${fns:getUser().admin}">
										<button title="删除" class="btn btn-danger btn-icon m-r-5" data-toggle="tooltip" data-placement="bottom" type="button"
												onclick="deleteById('${ctx}/sys/menu/delete?id=${menu.id}')"><i class="md md-delete"></i></button>
										<button title="添加下级菜单" class="btn bgm-lightgreen btn-icon" data-toggle="tooltip" data-placement="bottom" type="button"
												onclick="javascrtpt:window.location.href='${ctx}/sys/menu/form?parent.id=${menu.id}'"><i class="md md-menu"></i></button>
										</c:if>
									</td>
									</shiro:hasPermission>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</form>
			</div>
	    </div>
	 </div>
</body>
</html>