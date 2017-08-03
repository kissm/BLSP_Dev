<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>角色管理</title>
	<meta name="decorator" content="blank"/>
	<script type="text/javascript">
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
	        <h2>角色列表</h2>
	        <ul class="actions">
				<shiro:hasPermission name="sys:role:edit">
					<c:if test="${fns:getUser().admin}">
						<li>
							<button data-toggle="tooltip" data-placement="bottom" type="button" title="新增" class="btn btn-success btn-icon m-r-5" onclick="javascrtpt:window.location.href='${ctx}/sys/role/form'"><i class="md md-add"></i></button>
						</li>
					</c:if>
				</shiro:hasPermission>
				<li>
					<button data-toggle="tooltip" data-placement="bottom" type="button" title="刷新" class="btn btn-default btn-icon m-r-5" onclick="javascrtpt:window.location.href='${ctx}/sys/role/'"><i class="md md-autorenew"></i></button>
				</li>
			</ul>
	    </div>
		 <div class="card-body card-padding">
		 	<sys:message content="${message}"/>
		 	<div class="table-responsive">
            	<table id="contentTable" class="table table-striped table-vmiddle bootgrid-table">
            		<thead>
			        	<tr>
			        		<th class="col-xs-4">角色名称</th>
			        		<th>英文名称</th>
			        		<th>归属机构</th>
			        		<th>数据范围</th>
			        		<shiro:hasPermission name="sys:role:edit">
			        			<th class="text-center col-xs-1">操作</th>
			        		</shiro:hasPermission>
			        	</tr>
		        	</thead>
		        	<tbody>
						<c:forEach items="${list}" var="role">
							<tr>
								<td><a href="${ctx}/sys/role/form?id=${role.id}&officeId=${role.office.id}">${role.name}</a></td>
								<td><a href="${ctx}/sys/role/form?id=${role.id}&officeId=${role.office.id}">${role.enname}</a></td>
								<td>${role.office.name}</td>
								<td>${fns:getDictLabel(role.dataScope, 'sys_data_scope', '无')}</td>
								<shiro:hasPermission name="sys:role:edit"><td class="text-left text-nowrap">
									<button onclick="javascrtpt:window.location.href='${ctx}/sys/role/assign?id=${role.id}&officeId=${role.office.id}'" data-toggle="tooltip" data-placement="bottom"
											title="分配" class="btn bgm-lime btn-icon m-r-5" ><i class="md md-apps"></i></button>
									<c:if test="${(role.sysData eq fns:getDictValue('是', 'yes_no', '1') && fns:getUser().admin)||!(role.sysData eq fns:getDictValue('是', 'yes_no', '1'))}">
										<button title="修改" class="btn bgm-orange btn-icon m-r-5" data-toggle="tooltip" data-placement="bottom"
												onclick="javascrtpt:window.location.href='${ctx}/sys/role/form?id=${role.id}&officeId=${role.office.id}'"><i class="md md-edit"></i></button>
										<button title="删除" class="btn btn-danger btn-icon" data-toggle="tooltip" data-placement="bottom"
												onclick="deleteById('${ctx}/sys/role/delete?id=${role.id}')"><i class="md md-delete"></i></button>
									</c:if>
								</td></shiro:hasPermission>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</body>
</html>