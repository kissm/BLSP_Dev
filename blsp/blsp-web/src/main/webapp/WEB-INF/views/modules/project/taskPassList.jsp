<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>业务办理-我的已办业务</title>
	<meta name="decorator" content="blank"/>
	<script type="text/javascript">
		$(document).ready(function() {
		});

	</script>
</head>
<body>
<div class="card">
	    <div class="card-header">
	        <h2>我的已办业务 <small>您可通过本功能对本人已办事项进行查看。</small></h2>
	        <ul class="actions">
				<li>
					<button data-toggle="tooltip" data-placement="bottom" type="button" title="刷新" class="btn btn-default btn-icon m-r-5" onclick="refresh();"><i class="md md-autorenew"></i></button>
				</li>
			</ul>
	    </div>
		<form role="form" id="searchForm"  modelAttribute="prjTaskDTO" action="${ctx}/prjTask/pass/list" method="post">
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
			<input id="typeId" name="passType" type="hidden" value="1"/>
			<div class="card-body card-padding">
				<div class="row" id="clearQuery">
					<div class="col-sm-4 form-group">
						<div class="fg-line">
							<label class="control-label">区项目编号：</label>
							<input placeholder="区项目编号" name="prjCode" value="<c:out value="${param.prjCode}"/>" type="text" maxlength="50" class="form-control">
						</div>
					</div>
					<div class="col-sm-4 form-group">
						<div class="fg-line">
							<label class="control-label">项目名称：</label>
							<input placeholder="项目名称" name="prjName" value="<c:out value="${param.prjName}"/>" type="text"  maxlength="50" class="form-control">
						</div>
					</div>
					<div class="col-sm-4 form-group">
						<label class="control-label">事项名称：</label>
						<select name="taskName" class="form-control selectpicker" data-live-search="true">
							<option value="">全部</option>
							<c:forEach items="${taskName}" var="task">
								<option value="${task.id}" <c:if test="${param.taskName eq task.id}">selected</c:if>>${task.taskName}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-sm-2 form-group">
						<label class="control-label">事项状态：</label>
						<select name="taskStatus" class="form-control selectpicker" data-live-search="true">
							<option value="">全部</option>
							<option value="1" <c:if test="${param.taskStatus eq 1}">selected</c:if>>审批中</option>
							<option value="2" <c:if test="${param.taskStatus eq 2}">selected</c:if>>暂停</option>
						</select>
					</div>
					<div class="col-xs-3 form-group">
						<label class="control-label" >任务开始时间从：</label>
						<div class="input-group">
							<span class="input-group-addon"><i class="md md-event"></i></span>
							<div class="dtp-container dropdown fg-line">
								<input type="text" name="beginCreateDateStr" value="${param.beginCreateDateStr}" class="form-control date-picker" data-toggle="dropdown" placeholder="单击此处..." aria-expanded="false">
							</div>
						</div>
					</div>
					<div class="col-xs-3 form-group">
						<label class="control-label" >任务开始时间至：</label>
						<div class="input-group">
							<span class="input-group-addon"><i class="md md-event"></i></span>
							<div class="dtp-container dropdown fg-line">
								<input type="text" name="endCreateDateStr" value="${param.endCreateDateStr}" class="form-control date-picker" data-toggle="dropdown" placeholder="单击此处..." aria-expanded="false">
							</div>
						</div>
					</div>
					<div class="col-sm-2 form-group">
						<label class="control-label"></label>
						<button id="btnSubmit" type="submit" onclick="page(1);" class="btn btn-primary waves-effect form-control">查询</button>
					</div>
					<div class="col-sm-2 form-group">
						<label class="control-label"></label>
						<button id="btnReset" type="button" class="btn btn-primary waves-effect form-control" onclick="clearQuery();">重置</button>
					</div>
				</div>
				<sys:message content="${message}"/>
			</div>
		</form>
		<div class="table-responsive">
	         <table id="contentTable" class="table table-striped table-vmiddle bootgrid-table">
			 <thead>
				<tr>
					<th style="width:17%">区项目编号</th>
					<th style="width:22%">项目名称</th>
					<th>事项名称</th>
					<th style="width:18%">事项状态</th> 
					<th style="width:8%">主审人</th>
					<th style="width:8%">任务开始时间</th>
					<th style="width:8%">任务结束时间</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${page.list}" var="task">
					<tr>
						<td>${task.prjCode}</td>
						<td>${task.prjName}</td>
						<td>
							<a href="${ctx}/prjTaskTodo/detail?prjTaskInstId=${task.prjTaskInstId}&psta=2" title="查看详情">
								<u>${task.taskName}</u>
							</a>
						</td>								
						<td>
						 	<c:if test="${task.taskStatus eq 0}">暂存</c:if>
						 	<c:if test="${task.taskStatus eq 1}">审批中</c:if>
							<c:if test="${task.taskStatus eq 2}">
								暂停(${fns:getDictLabel(task.taskPauseType, 'task_pause_type', '')}
									<c:if test="${task.taskPauseType == 99}">
										其他
									</c:if>
								)
								<br/>
								<fmt:formatDate value="${task.taskPauseStartTime}" pattern="yyyy-MM-dd"/>
							</c:if>
							<c:if test="${task.taskStatus eq 4}">已办结</c:if>
							<c:if test="${task.taskStatus eq 5}">未启动</c:if>
							<c:if test="${task.taskStatus eq 6}">不通过</c:if>
							<c:if test="${task.taskStatus eq 7}">已完成</c:if>
							<c:if test="${task.taskStatus eq 8}">免办</c:if>
						</td> 
						<td>${task.userName}</td>
						<td><fmt:formatDate value="${task.taskStartTime}" pattern="yyyy-MM-dd"/></td>
						<td><fmt:formatDate value="${task.taskEndTime}" pattern="yyyy-MM-dd"/></td>
					</tr>
				</c:forEach>
			</tbody>
	       </table>
		</div>
		${page}
</div>
</body>
</html>