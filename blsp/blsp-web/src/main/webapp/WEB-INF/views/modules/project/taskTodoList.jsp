<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>业务办理-我的待办业务</title>
	<meta name="decorator" content="blank"/>
	<script type="text/javascript">
		$(document).ready(function() {
		});

	</script>
</head>
<body>
<div class="card">
	<div class="card-header">
		<h2>我的待办业务 <small>您可通过本功能对本人需待办事项进行查看及处理</small></h2>
		<ul class="actions">
			<li>
				<button data-toggle="tooltip" data-placement="bottom" type="button" title="刷新" class="btn btn-default btn-icon m-r-5" onclick="refresh();">
					<i class="md md-autorenew"></i></button>
			</li>
		</ul>
	</div>
	<div class="card-body card-padding">
		<form role="form" id="searchForm" action="${ctx}/prjTaskTodo/list" method="post">
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
			<div class="card-body card-padding">
				<div class="row" id="clearQuery">
					<div class="col-sm-3 form-group">
						<div class="fg-line">
							<label class="control-label">区项目编号：</label>
							<input placeholder="区项目编号" name="prjCode" value="<c:out value="${param.prjCode}"/>" type="text" maxlength="50" class="form-control">
						</div>
					</div>
					<div class="col-sm-3 form-group">
						<div class="fg-line">
							<label class="control-label">项目名称：</label>
							<input placeholder="项目名称" name="prjName" value="<c:out value="${prjTaskDTO.prjName}"/>" type="text"  maxlength="50" class="form-control">
						</div>
					</div>
					<div class="col-sm-3 form-group">
						<label class="control-label">事项名称：</label>
						<select name="taskName" class="form-control selectpicker" data-live-search="true">
							<option value="">全部</option>
							<c:forEach items="${taskName}" var="task">
								<option value="${task.id}" <c:if test="${param.taskName eq task.id}">selected</c:if>>${task.taskName}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-sm-3 form-group">
						<label class="control-label">事项状态：</label>
						<select name="taskStatus" class="form-control selectpicker" data-live-search="false">
							<option value="">全部</option>
							<option value="1" <c:if test="${param.taskStatus eq 1}">selected</c:if>>审批中</option>
							<option value="2" <c:if test="${param.taskStatus eq 2}">selected</c:if>>暂停</option>
						</select>
					</div>
					<div class="col-xs-3 form-group">
						<label class="control-label" >审批开始时间从：</label>
						<div class="input-group">
							<span class="input-group-addon"><i class="md md-event"></i></span>
							<div class="dtp-container dropdown fg-line">
								<input type="text" name="beginCreateDateStr" value="${param.beginCreateDateStr}" class="form-control date-picker" data-toggle="dropdown" placeholder="单击此处..." aria-expanded="false">
							</div>
						</div>
					</div>
					<div class="col-xs-3 form-group">
						<label class="control-label" >审批开始时间至：</label>
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
	</div>
	<div class="table-responsive">
		 <table id="contentTable" class="table table-striped table-vmiddle bootgrid-table">
		 <thead>
			<tr>
				<th style="width:17%">区项目编号</th>
				<th style="width:20%">项目名称</th>
				<th>事项名称</th>
				<th style="width:18%">事项状态</th>
				<th style="width:8%">审批开始时间</th>
				<%--<th style="width:8%">剩余办结时间</th> --%>
				<th style="width:8%">办结期限时间</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${page.list}" var="task">
				<c:choose>
					<c:when test="${task.taskRemainTime lt 0}">
						<tr style="color:#F00">
							<td>${task.prjCode}</td>
							<td>${task.prjName}</td>
							<td>
								<a href="${ctx}/prjTaskTodo/detail?prjTaskInstId=${task.prjTaskInstId}&psta=1" title="查看详情">
									<u style="color:#F00">${task.taskName}</u>
								</a>
							</td>
							<td>
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
							</td>
							<td><fmt:formatDate value="${task.taskStartTime}" pattern="yyyy-MM-dd"/></td>
							<%--<td>超时${0-task.taskRemainTime}工作日</td> --%>
							<td><fmt:formatDate value="${task.taskEndTime}" pattern="yyyy-MM-dd"/></td>
						</tr>
					</c:when>
					<c:when test="${task.taskRemainTime eq 1 or task.taskRemainTime eq 0}">
						<tr style="color:#FF9933">
							<td>${task.prjCode}</td>
							<td>${task.prjName}</td>
							<td>
								<a href="${ctx}/prjTaskTodo/detail?prjTaskInstId=${task.prjTaskInstId}&psta=1" title="查看详情">
									<u style="color:#FF9933">${task.taskName}</u>
								</a>
							</td>
							<td>
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
							</td>
							<td><fmt:formatDate value="${task.taskStartTime}" pattern="yyyy-MM-dd"/></td>
							<%--<td>${task.taskRemainTime}工作日</td> --%>
							<td><fmt:formatDate value="${task.taskEndTime}" pattern="yyyy-MM-dd"/></td>
						</tr>
					</c:when>
					<c:otherwise>
						<tr>
							<td>${task.prjCode}</td>
							<td>${task.prjName}</td>
							<td>
								<a href="${ctx}/prjTaskTodo/detail?prjTaskInstId=${task.prjTaskInstId}&psta=1" title="查看详情">
									<u>${task.taskName}</u>
								</a>
							</td>
							<td>
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
							</td>
							<td><fmt:formatDate value="${task.taskStartTime}" pattern="yyyy-MM-dd"/></td>
							<%--<td>${task.taskRemainTime}工作日</td>--%>
							<td><fmt:formatDate value="${task.taskEndTime}" pattern="yyyy-MM-dd"/></td>
						</tr>
					</c:otherwise>
				</c:choose>

			</c:forEach>
		</tbody>
	   </table>
	</div>
	${page}
</div>
</body>
</html>