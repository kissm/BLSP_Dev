<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>未通过事项</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		if('${message}'=='ok'){
			parent.iframeClose(${prjInsId},1);//激活后到事项对应的材料选择页
		}
		$(document).ready(function() {
			$('#offFinish',parent.document).click(function(){
				 if($(":checkbox:checked").size() == 0) {
					 swal("请至少选择一个事项", "", "error");
				 } else {
					 swal({   
		                    title: "确定激活此事项吗？",
		                    text: "",   
		                    type: "warning",   
		                    showCancelButton: true,   
		                    confirmButtonColor: "#DD6B55",
		                    cancelButtonText: '取消',
		                    confirmButtonText: "确认已完成"
		                }, function(){
							$("#taskRedo").submit();
		                });
				 }
			})
	});

	</script>
</head>
<body>
<form id="taskRedo" action="${ctx}/project/bizaccept/unpass/taskRedo" method="post">
	<input type="hidden" name="prjInsId" value="${prjInsId}"/>
	<div class="p-20">
		<div class="table-responsive">
			<table id="contentTable" class="table table-striped  table-vmiddle">
				<thead>
					<tr>
						<th></th>
						<th>事项名称</th>
						<th>部门</th>
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
					</c:forEach>
				</c:if>
				
				<c:if test="${empty list}">
					<tr><td colspan="3"><i>暂无未通过事项</i></td></tr>
				</c:if>
				
				</tbody>
			</table>
		</div>
	</div>
</form>
</body>
</html>