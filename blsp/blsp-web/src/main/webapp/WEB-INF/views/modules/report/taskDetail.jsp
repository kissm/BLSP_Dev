<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>事项操作详情</title>
<meta name="decorator" content="default"/>
</head>
<body>
	<div class="card">
        <div class="card-header">
            <h2>操作信息</h2>  
        </div>
		<div class="card-body card-padding">
			<div class="row">
				<div class="table-responsive">
					<table id="contentTable" class="table table-striped">
						<thead>
							<tr>
								<th class="col-xs-2">操作人</th>
								<th class="col-xs-2">开始时间</th>
								<th class="col-xs-2">结束时间</th>
								<th class="col-xs-2">操作类型</th>
								<th>操作说明</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${list}" var="handle">
								<tr>
									<td>
										${handle.userName}
									</td>
									<td>
										<fmt:formatDate value="${handle.startTime}" pattern="yyyy-MM-dd"/>
									</td>
									<td>
										<fmt:formatDate value="${handle.endTime}" pattern="yyyy-MM-dd"/>
									</td>
									<td>
										<c:if test="${handle.handleType eq 1}">审批</c:if>
										<c:if test="${handle.handleType eq 2}">
											暂停(${fns:getDictLabel(handle.pauseType, 'task_pause_type', '')}
													<c:if test="${handle.pauseType == 99}">
														其他
													</c:if>
												)
										</c:if>
										<c:if test="${handle.handleType eq 3}">办结</c:if>
									</td>
									<td>
										<c:out value="${handle.desc}"/>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</body>
</html>