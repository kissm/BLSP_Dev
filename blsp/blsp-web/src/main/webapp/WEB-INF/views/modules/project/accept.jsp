<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<div class="row">
	<div class="form-group col-sm-4">
		<label id="_projectCode">区项目编号:${project.prjInstanceVo.prjCode}</label>
	</div>
	<div class="form-group col-sm-4">
		<label id="_projectName">项目名称:${project.prjInstanceVo.prjName}</label>
	</div>
</div>
<div class="table-responsive">
	<table class="table table-striped table-vmiddle bootgrid-table"
		id="accept_">
		<thead>
			<tr>
				<th class="text-center">事项名称</th>
				<th class="text-center">单位</th>
				<th class="text-center">审批人</th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${project.prjTaskDefineVoList ne null}">
				<c:forEach items="${project.prjTaskDefineVoList}" var="dvo"
					varStatus="c">
					<input type="hidden" value="${dvo.id}"
						name="prjTaskVoList[${c.index}].taskId" />
					<input type="hidden" value="${dvo.id}"
						name="prjTaskVoList[${c.index}].id" />
					<input type="hidden" value="${dvo.isWithCert}"
						name="prjTaskVoList[${c.index}].isWithCert" />
					<input type="hidden" value="${dvo.deptId}"
						name="prjTaskVoList[${c.index}].deptId" />						
					<tr>
						<td>${dvo.taskName}</td>
						<td>${fns:getOffice(dvo.deptId).name}</td>
						<td><select class="form-control selectpicker"
							name="prjTaskVoList[${c.index}].initUser">
								<c:forEach items="${fns:getUsersByOfficeId(dvo.deptId)}"
									var="itemValue">
									<option value="${itemValue.id}" ${c.index eq 0?"selected":""}>${itemValue.name}</option>
								</c:forEach>
						</select></td>
					</tr>
				</c:forEach>
			</c:if>
			<c:if test="${project.prjTaskVoList ne null}">
				<c:forEach items="${project.prjTaskVoList}" var="dvo"
					varStatus="c">
					<input type="hidden" value="${dvo.taskId}"
						name="prjTaskVoList[${c.index}].taskId" />
					<input type="hidden" value="${dvo.id}"
						name="prjTaskVoList[${c.index}].id" />
					<input type="hidden" value="${dvo.isWithCert}"
						name="prjTaskVoList[${c.index}].isWithCert" />
					<tr>
						<td>${dvo.taskName}</td>
						<td>${fns:getOffice(dvo.deptId).name}</td>
						<td><select class="form-control selectpicker"
							name="prjTaskVoList[${c.index}].initUser">
								<c:forEach items="${fns:getUsersByOfficeId(dvo.deptId)}"
									var="itemValue">
									<option value="${itemValue.id}" ${c.index eq 0?"selected":""}>${itemValue.name}</option>
								</c:forEach>
						</select></td>
					</tr>
				</c:forEach>
			</c:if>
		</tbody>
	</table>
</div>