<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>事项配置</title>
	<meta name="decorator" content="blank"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnAdd").click(function(){
				window.location.href = "${ctx}/prjTaskDefine/edit";
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
		}
		
		function removeTask(id){
			swal({   
                title: "你确定吗？",
                text: "删除这条事项信息",   
                type: "warning",   
                showCancelButton: true,   
                confirmButtonColor: "#DD6B55",
                cancelButtonText: '取消',
                confirmButtonText: "是的，删除！",
                closeOnConfirm: false 
            }, function(){
            	window.location.href='${ctx}/prjTaskDefine/delete?id='+id;
            });
		}
	</script>
</head>
<body>
<div class="card">
	    <div class="card-header">
	         <h2>事项定义管理<small>您可通过本功能进行事项定义管理</small></h2>  
	        <ul class="actions">
				<li>
					<button data-toggle="tooltip" data-placement="bottom" title="新增事项定义" class="btn btn-success btn-icon m-r-5" id="btnAdd"><i class="md md-add"></i></button>
				</li>
				<li>
					<button data-toggle="tooltip" data-placement="bottom" type="button" title="刷新" class="btn btn-default btn-icon m-r-5" onclick="refresh();"><i class="md md-autorenew"></i></button>
				</li>
			</ul>
	    </div>
		<form:form role="form" id="searchForm"  modelAttribute="prjTaskDefineDTO" action="${ctx}/prjTaskDefine/list" method="post">
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
			<div class="card-body card-padding">
				<div id="clearQuery" class="row">
					<div class="col-sm-3 form-group">
						<div class="fg-line">
							<label class="control-label">事项编号：</label>
							<input placeholder="事项编号" name="taskCode" value="<c:out value="${param.taskCode}"/>" type="text" maxlength="50" class="form-control">
						</div>
					</div>
					<div class="col-sm-3 form-group">
						<div class="fg-line">
							<label class="control-label">事项名称：</label>
							<input placeholder="事项名称" name="taskName" value="<c:out value="${param.taskName}"/>" type="text"  maxlength="50" class="form-control">
						</div>
					</div>
					<div class="col-sm-6 form-group">
						<div class="col-sm-6">
							<div class="fg-line">
								<label class="control-label">阶段名称：</label>
								<select name="stageId" class="form-control selectpicker" data-live-search="true">
									<option value="">全部</option>
									<c:forEach var="item" items="${stages}">   
										<option <c:if test="${prjTaskDefineDTO.stageId == item.key}">selected="selected"</c:if> value="${item.key}">${item.value}</option>
									</c:forEach> 
								<select/>
							</div>
						</div>
					</div>
					<div class="col-sm-3 form-group">
						<label class="control-label">事项所属单位：</label>
							<form:select path="deptId" class="form-control selectpicker" data-live-search="true">
								<form:option value="">全部</form:option>
								<form:options items="${fns:findOfficeByParent('1')}" itemLabel="name" itemValue="id" htmlEscape="false"/>
							</form:select>
					</div>
					<div class="col-sm-3 form-group">
						<label class="control-label">事项状态：</label>
						<form:select path="isValid" class="form-control selectpicker" data-live-search="true">
							<form:option value="">全部</form:option>
							<form:option value="1">启用</form:option>
							<form:option value="0">停用</form:option>
						</form:select>
					</div>
					<div class="col-sm-2 form-group">
						<label class="control-label"></label>
						<button id="btnSubmit" type="submit" onclick="page(1);" class="btn btn-primary waves-effect form-control">查询</button>
					</div>
					<div class="col-sm-2 form-group">
						<label class="control-label"></label>
						<button id="btnReset" type="button" class="btn btn-primary waves-effect form-control" onclick="location.href='${ctx}/prjTaskDefine/list'">重置</button>
					</div> 
				</div>
				<sys:message content="${message}"/>
			</div>
		</form:form>
		<div class="table-responsive">
	         <table id="contentTable" class="table table-striped table-vmiddle bootgrid-table">
			 <thead>
				<tr>
					<th >事项编号</th>
					<th class="col-xs-3">事项名称</th>
					<th>事项所属单位</th>
					<th class="text-center">是否依赖其他事项</th>
					<th class="text-center">事项状态</th>
					<th>所属阶段</th>
					<th class="text-center">是否发证</th>
					<th class="text-center col-xs-1">操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${page.list}" var="task">
					<tr>
						<td>
							<a href="${ctx}/prjTaskDefine/edit?id=${task.id}">
								${task.taskCode}
							</a>
						</td>
						<td>${task.taskName}</td>
						<td>${offices[task.deptId]}</td>
						<td class="text-center">
							<c:if test="${task.isReplyon eq 0 || empty task.isReplyon }">否</c:if>
							<c:if test="${task.isReplyon eq 1}">是</c:if>
						</td>
						<td class="text-center">
							<c:if test="${task.isValid eq 0}">停用</c:if>
							<c:if test="${task.isValid eq 1}">启用</c:if>
						</td>
						<td>${stages[task.stageId]}</td>
						<td class="text-center">
							<c:if test="${task.isWithCert eq 0 || empty task.isWithCert }">否</c:if>
							<c:if test="${task.isWithCert eq 1}">是</c:if>
						</td>
						<td class="text-center text-nowrap">
							<button title="修改" data-toggle="tooltip" data-placement="bottom" class="btn bgm-orange btn-icon m-r-5" onclick="javascrtpt:window.location.href='${ctx}/prjTaskDefine/edit?id=${task.id}'" type="button"><span class="md md-edit"></span></button>
							<button title="删除" data-toggle="tooltip" data-placement="bottom" class="btn btn-danger btn-icon m-r-5" onclick='removeTask("${task.id}");' type="button"><span class="md md-delete"></span></button>
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