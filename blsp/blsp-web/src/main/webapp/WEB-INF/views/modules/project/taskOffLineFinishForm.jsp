<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>事项办结</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		if('${message}'=='ok'){
			parent.iframeClose();
		}
		$(document).ready(function() {
			
			$('#offFinish',parent.document).click(function(){
				 if($(":checkbox:checked").size() == 0) {
					 swal("请至少选择一个事项", "", "error");
				 } else {
					 swal({   
		                    title: "确定处理选中的未办事项吗？",
		                    text: "",   
		                    type: "warning",   
		                    showCancelButton: true,   
		                    confirmButtonColor: "#DD6B55",
		                    cancelButtonText: '取消',
		                    confirmButtonText: "确认"
		                }, function(){
							$("#offLineFinishForm").submit();
		                });
					 
					 
				 }
			})
			
			$('input[type=checkbox]').click(function() {
				 if($(this).is(':checked')){
					$('#type_'+$(this).val()).attr("disabled",false);
				 } else {
					$('#type_'+$(this).val()).attr("disabled",true);
				 }
				 // 刷新控件
				 $('#type_'+$(this).val()).selectpicker('refresh');
			})
			
	});	

	</script>
</head>
<body>
<form id="offLineFinishForm" action="${ctx}/enterprise/project/offLine/finish/save" method="post">
	<input type="hidden" name="prjInsId" value="${prjInsId}"/>
	<div class="p-20">
		<div class="table-responsive">
			<table id="contentTable" class="table table-striped  table-vmiddle">
				<thead>
					<tr>
						<th></th>
						<th class="col-xs-6">事项名称</th>
						<th class="col-xs-4">部门</th>
						<th class="text-center col-xs-2">办结类型</th>
					</tr>
				</thead>
				<tbody id="tbody_cert">
				<c:if test="${not empty list}">
					<c:forEach  items="${list}" var="task">
						<tr>
							<td>
								<label class="checkbox checkbox-inline"><input type="checkbox" name="prjTaskInstIds" value="${task.id}"><i class="input-helper"></i></label>
							</td>					
							<td>${task.taskName}</td>
							<td>${task.deptName}</td>
							<td>
								<select name="finishType" id="type_${task.id}" disabled class="form-control selectpicker" data-live-search="false">
									<option value="1">已完成</option>
									<option value="2">免办</option>
								</select>
							</td>
					</c:forEach>
				</c:if>
				
				<c:if test="${empty list}">
					<tr><td colspan="4"><i>暂无未办事项</i></td></tr>
				</c:if>
				
				</tbody>
			</table>
		</div>
	</div>
</form>
</body>
</html>