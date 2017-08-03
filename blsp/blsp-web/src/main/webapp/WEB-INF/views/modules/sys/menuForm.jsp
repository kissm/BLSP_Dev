<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>菜单管理</title>
	<meta name="decorator" content="blank"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					form.submit();
				}
			});
		});
    	function updateSort() {
	    	$("#inputForm").attr("action", "${ctx}/sys/menu/save");
	    	$("#inputForm").submit();
    	}
	</script>
</head>
<body>
    <div class="card">
	    <div class="card-header">
	        <h2>菜单编辑</h2>
	        <ul class="actions">
				<li>
					<button data-toggle="tooltip" data-placement="bottom" title="返回" class="btn btn-success btn-icon m-r-5" onclick="javascrtpt:history.go(-1)"><i class="md md-arrow-back"></i></button>
				</li>
				<shiro:hasPermission name="sys:menu:edit">
					<li>
						<button data-toggle="tooltip" data-placement="bottom" title="保存菜单" class="btn btn-info btn-icon m-r-5" onclick="updateSort();"><i class="md md-save"></i></button>
					</li>
				</shiro:hasPermission>
				<li>
					<button data-toggle="tooltip" data-placement="bottom" title="刷新" class="btn btn-default btn-icon m-r-5" onclick="javascrtpt:window.location.href='${ctx}/sys/menu/form?id=${menu.id}'"><i class="md md-autorenew"></i></button>
				</li>
			</ul>
	    </div>
	    
	     <div class="card-body card-padding">
	     	<div class="row">
	     		<sys:message content="${message}"/>
		     	<form id="inputForm" action="${ctx}/sys/menu/save" method="post" class="form-horizontal">
		     		<input type="hidden" name="id" value="${menu.id}">
		     		<div class="form-group">
	                    <label class="col-sm-2 control-label">上级菜单:</label>
	                    <div class="col-sm-10">
	                       <sys:treeselect id="menu" name="parent.id" value="${menu.parent.id}" labelName="parent.name" labelValue="${menu.parent.name}"
									title="菜单" url="/sys/menu/treeData" extId="${menu.id}" cssClass="required" />
	                    </div>
	                </div>
	                <div class="form-group">
	                    <label class="col-sm-2 control-label">名称:</label>
	                    <div class="col-sm-10">
	                        <div class="fg-line">
	                            <input placeholder="名称" type="text" name="name" value="${menu.name}"  maxlength="50" class="form-control required">
	                        </div>
	                    </div>
	                </div>
	                <div class="form-group">
	                    <label class="col-sm-2 control-label">链接:</label>
	                    <div class="col-sm-10">
	                        <div class="fg-line">
	                            <input placeholder="点击菜单跳转的页面" type="text" name="href" value="${menu.href}"  maxlength="500" class="form-control">
	                        </div>
	                    </div>
	                </div>
	                <div class="form-group">
	                    <label class="col-sm-2 control-label">图标:</label>
	                    <div class="col-sm-10">
	                        <sys:iconselect id="icon" name="icon" value="${menu.icon}"/>
	                    </div>
	                </div>
	                <div class="form-group">
	                    <label class="col-sm-2 control-label">排序:</label>
	                    <div class="col-sm-10">
	                        <div class="fg-line">
	                            <input placeholder="排列顺序，升序。" type="text" name="sort" value="${menu.sort}"  maxlength="10" class="form-control required">
	                        </div>
	                    </div>
	                </div>
	                <div class="form-group">
	                    <label class="col-sm-2 control-label">可见:</label>
	                    <div class="col-sm-10">
	                    	<div class="radio radio-inline m-r-20">
	                            <label>
	                                <input id="isShow1" name="isShow" type="radio" value="1" ${empty menu.isShow?'checked':menu.isShow=='1'?'checked':''}>
		                        	<i class="input-helper"></i>显示
	                            </label>
	                        </div>
	                        <div class="radio radio-inline m-r-20">
	                            <label>
	                                <input id="isShow2" name="isShow" type="radio" value="0" ${menu.isShow=='0'?'checked':''}>
		                        	<i class="input-helper"></i>隐藏
	                            </label>
	                        </div>
		                     <label class="control-label"><i>该菜单或操作是否显示到系统菜单中</i></label>
	                    </div>
	                </div>
	                <div class="form-group">
	                    <label class="col-sm-2 control-label">权限标识:</label>
	                    <div class="col-sm-10">
	                        <div class="fg-line">
	                        	<input placeholder='控制器中定义的权限标识，如：@RequiresPermissions("权限标识")' type="text" name="permission" value="${menu.permission}"  maxlength="100" class="form-control">
	                        </div>
	                    </div>
	                </div>
	                <div class="form-group">
	                    <label class="col-sm-2 control-label">备注:</label>
	                    <div class="col-sm-10">
	                        <div class="fg-line">
	                        	<textarea name="remarks" class="form-control auto-size" maxlength="200" placeholder="请填写备注">${menu.remarks}</textarea>
	                        </div>
	                    </div>
	                </div>
		     	</form>
		     </div>
	     </div>
	</div>
</body>
</html>