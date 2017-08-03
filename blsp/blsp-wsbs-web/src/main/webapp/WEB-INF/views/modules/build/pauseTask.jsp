<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>补材料事项列表</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});	
        function ajaxHandle(id) {
        	$.ajax({
        		type : 'post',
        		url :'${ctx}/project/handle',
        		data : {"taskId":id},
        		cache : false,
        		dataType : 'html',
        		success : function(data) {
        			$("#handle").html(data);
        			$('#approve').modal('show');
        		}
        	});
        }
	</script>
</head>
<body>
	<div class="p-20">
		<div class="table-responsive">
			<table id="contentTable" class="table table-striped  table-vmiddle">
				<thead>
					<tr>
						<th>事项名称</th>
						<th class="text-center col-xs-1">补材料次数</th>
						<th class="text-center col-xs-2">开始补材料时间</th>
						<th class="text-center col-xs-2">应补齐时间</th>
						<th class="text-center col-xs-2">实际补齐时间</th>
					</tr>
				</thead>
				<tbody id="tbody_cert">
					<c:if test="${not empty list}">
						<c:forEach  items="${list}" var="task">
							<tr>
								<td><a data-toggle="modal" href="javascript:void(0)" title="审批详情查看" onclick="ajaxHandle(${task.taskInstId})">${task.taskName}</a></td>
								<td class="text-center">${task.pauseNumMat}</td>
								<td class="text-center"><fmt:formatDate value="${task.pauseStartTime}" pattern="yyyy-MM-dd"/></td>
								<td class="text-center"><fmt:formatDate value="${task.provideEndTime}" pattern="yyyy-MM-dd"/></td>
								<td class="text-center"><fmt:formatDate value="${task.pauseEndTime}" pattern="yyyy-MM-dd"/></td>
						</c:forEach>
					</c:if>
				</tbody>
			</table>
		</div>
	</div>
	
	
		<div class="modal fade" data-modal-color="cyan" id="approve"
		data-backdrop="static" data-keyboard="false" tabindex="-1"
		role="dialog" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">审批详情查看</h4>
				</div>
				<div class="modal-body bgm-white text-muted" id="handle"></div>
				<div class="modal-footer">
					<button type="button" class="btn btn-link" data-dismiss="modal">关闭</button>
				</div>
			</div>
		</div>
</body>
</html>