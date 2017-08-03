<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>珠海市金湾区网上办事大厅</title>
        <meta name="decorator" content="blank" />
        <script type="text/javascript">
        	$(function (){
        		var prjCode = "${prjCode}";
        		if(prjCode){
        			$("#backPage").show();
        		}else{
        			$("#backPage").hide();
        		}
        	});
        </script>
	</head>
<body>
	<table class="editTable" style="width: 947px" border="0" cellspacing="0" cellpadding="0">
		<thead>
			<tr>
				<th class="title" style="width: 10%">
					项目编号
				</th>
				<th class="title" style="width: 50%">
					项目名称
				</th>
				<th class="title" style="width: 20%">
					项目单位
				</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td align="center" class="content" id="prjCodeTd">${project.prjInstanceVo.prjCode}</td>
				<td align="center" class="content" id="prjNameTd">${project.prjInstanceVo.prjName}</td>
				<td align="center" class="content" id="companyTd">${project.prjInstanceVo.company}</td>
			</tr>
		</tbody>
	</table>
	<c:forEach items="${project.prjStageVoList}" var="stage" varStatus="c">
		<table class="editTable" style="width: 947px" border="0" cellspacing="0" cellpadding="0">
			<thead>
				<tr>
					<th class="title" >
						事项名称
					</th>
					<th class="title" style="width: 20%">
						事项状态
					</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${fns:getAllTaskByInstanceStage(stage.id,project.prjInstanceVo.id)}"
					var="task" varStatus="t">
					<tr>
						<td  class="content">
							${task.taskName}
						</td>
						<td align="center" class="content">
							<c:if test="${project.prjInstanceVo.isPrjComplete eq '9'}">
								终止(
							</c:if>
							<c:if test="${task.taskStatus eq 0}">暂存</c:if>
							<c:if test="${task.taskStatus eq 1}">审批中</c:if> 
							<c:if test="${task.taskStatus eq 2}">
								暂停(${fns:getDictLabel(task.taskPauseType, 'task_pause_type', '')}
									<c:if test="${task.taskPauseType == 99}">
										其他
									</c:if>
								)<br/>
								<fmt:formatDate value="${task.taskPauseStartTime}" pattern="yyyy-MM-dd" />
							</c:if> 
							<c:if test="${task.taskStatus eq 4}">已办结</c:if>
							<c:if test="${task.taskStatus eq 5}">未启动</c:if>
							<c:if test="${task.taskStatus eq 6}">不通过</c:if>
							<c:if test="${task.taskStatus eq 7}">已完成</c:if>
							<c:if test="${task.taskStatus eq 8}">免办</c:if>
							<c:if test="${project.prjInstanceVo.isPrjComplete eq '9'}">
								)
							</c:if>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:forEach>
	<table class="editTable" id="backPage" style="width: 947px" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td class="content" align="center">
				<input type="button" class="btnNoimg next" value="返回" onclick="window.history.back();" />
			</td>
		</tr>
	</table>
</body>
</html>