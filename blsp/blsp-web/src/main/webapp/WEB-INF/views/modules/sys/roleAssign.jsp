<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>分配角色</title>
	<meta name="decorator" content="blank"/>
	<script type="text/javascript">
	$(document).ready(function(){
		$('#assignButton').click(function(){
			$('#selectUserModal').modal('show');
		});
		$('#selectUserOk').click(function(){
			var oldIds = $('#selectUserModalContent')[0].contentWindow.oldIds;
			var ids = $('#selectUserModalContent')[0].contentWindow.ids;
			if(ids[0]==''){
				ids.shift();
				oldIds.shift();
			}
			if(ids.length==0){
				swal("完成！", "未给角色【${role.name}】分配新成员！", "success");
				return false;
			};
	    	var idsArr = "";
	    	for (var i = 0; i<ids.length; i++) {
	    		idsArr = (idsArr + ids[i]) + (((i + 1)== ids.length) ? '':',');
	    	}
	    	$('#idsArr').val(idsArr);
	    	$('#assignRoleForm').submit();
		});
		$('#selectUserClear').click(function(){
			swal({
                title: "你确定吗？",
                text: "清除角色【${role.name}】下的已选人员？",   
                type: "warning",   
                showCancelButton: true,   
                confirmButtonColor: "#DD6B55",
                cancelButtonText: '取消',
                confirmButtonText: "是的，清除！",
                closeOnConfirm: false,
                closeOnCancel: false
            }, function(isConfirm){
           	 if (isConfirm) {
           		var tips = $('#selectUserModalContent')[0].contentWindow.clearAssign();
                swal("完成！", tips, "success");
           	 }else{
           		swal("取消清除操作！","", "info");
           	 }
           });
		});
		
	});
	function outrole(userId,userName){
		swal({
            title: "你确定吗？",
            text: "要将用户【"+userName+"】从【${role.name}】角色中移除吗？", 
            type: "warning",   
            showCancelButton: true,   
            confirmButtonColor: "#DD6B55",
            cancelButtonText: '取消',
            confirmButtonText: "是的，移除！",
            closeOnConfirm: false 
        }, function(){
        	window.location.href="${ctx}/sys/role/outrole?userId="+userId+"&roleId=${role.id}&officeId=${role.office.id}";
	    });
	}
	</script>
	
</head>
<body>
    <div class="card">
		<div class="card-header">
	        <h2><shiro:hasPermission name="sys:role:edit">角色分配</shiro:hasPermission><shiro:lacksPermission name="sys:role:edit">人员列表</shiro:lacksPermission></h2>
	        <ul class="actions">
				<li>
					<button data-toggle="tooltip" data-placement="bottom" type="button" title="返回" class="btn btn-success btn-icon m-r-5" onclick="javascrtpt:window.location.href='${ctx}/sys/role/'"><i class="md md-arrow-back"></i></button>
				</li>
				<li>
					<button data-toggle="tooltip" data-placement="bottom" type="button" id="assignButton" title="分配角色" class="btn btn-info btn-icon m-r-5"><i class="md md-add"></i></button>
				</li>
				<li>
					<button data-toggle="tooltip" data-placement="bottom" type="button" title="刷新" class="btn btn-default btn-icon m-r-5" onclick="javascrtpt:window.location.href='${ctx}/sys/role/assign?id=${role.id}&officeId=${role.office.id}'"><i class="md md-autorenew"></i></button>
				</li>
			</ul>
	    </div>
	    <div class="card-body card-padding">
	    	<sys:message content="${message}"/>
	    	<div class="row">
		    	<div class="col-xs-4 form-group">
					<label class="control-label" >角色名称: <b>${role.name}</b></label>
				</div>
				<div class="col-xs-4 form-group">
					<label class="control-label" >归属机构: ${role.office.name}</label>
				</div>
		    	<div class="col-xs-4 form-group">
					<label class="control-label" >英文名称: ${role.enname}</label>
				</div>
				<div class="col-xs-4 form-group">
					<label class="control-label" >角色类型: ${fns:getDictLabel(role.roleType,'role_type','')}</label>
				</div>
				<div class="col-xs-4 form-group">
					<c:set var="dictvalue" value="${role.dataScope}" scope="page" />
					<label class="control-label" >数据范围: ${fns:getDictLabel(dictvalue, 'sys_data_scope', '')}</label>
				</div>
			</div>
			<form id="assignRoleForm" action="${ctx}/sys/role/assignrole" method="post">
				<input type="hidden" name="id" value="${role.id}"/>
				<input type="hidden" name="officeId" value="${role.office.id}">
				<input id="idsArr" type="hidden" name="idsArr" value=""/>
			</form>
			<div class="table-responsive">
            	<table id="contentTable" class="table table-striped table-vmiddle bootgrid-table">
            		<thead>
            			<tr>
            				<th>单位</th>
            				<th>部门</th>
            				<th>登录名</th>
            				<th>姓名</th>
            				<th>电话</th>
            				<th>手机</th>
            				<shiro:hasPermission name="sys:role:edit">
            					<th>操作</th>
            				</shiro:hasPermission>
            			</tr>
            		</thead>
					<tbody>
						<c:forEach items="${userList}" var="user">
							<tr>
								<td>${user.company.name}</td>
								<c:set var="officeNames">
									<c:forEach items="${user.officeList}" var="officeName">${officeName.name},</c:forEach>
								</c:set>
								<td>${officeNames}</td>
								<td><a href="${ctx}/sys/user/form?id=${user.id}">${user.loginName}</a></td>
								<td>${user.name}</td>
								<td>${user.phone}</td>
								<td>${user.mobile}</td>
								<shiro:hasPermission name="sys:role:edit">
									<td>
										<button class="btn btn-danger btn-icon" data-toggle="tooltip" data-placement="bottom" onclick="outrole('${user.id}','${user.name}');"><i class="md md-delete"></i></button>
									</td>
								</shiro:hasPermission>
							</tr>
						</c:forEach>
					</tbody>
            	</table>
            </div>
	    </div>
	</div>
	
	<div class="modal fade" data-modal-color="cyan" id="selectUserModal" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title">分配角色</h4>
			</div>
			<div style="height:340px;">
				<iframe id="selectUserModalContent" src="${ctx}/sys/role/usertorole?id=${role.id}" width="100%" height="100%" frameborder="0"></iframe>
			</div>
			<div class="modal-footer">
				<button id="selectUserOk" type="button" class="btn btn-link">确定</button>
				<button id="selectUserClear" type="button" class="btn btn-link">清除</button>
				<button type="button" class="btn btn-link" data-dismiss="modal">关闭</button>
			</div>
		</div>
	</div>
</div>
	
	
</body>
</html>
