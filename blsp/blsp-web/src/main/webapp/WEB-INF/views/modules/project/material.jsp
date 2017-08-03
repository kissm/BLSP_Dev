<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<script type="text/javascript">
	function selected(id) {
		//$("[name='prjStageMaterialVoList["+id+"].isComplete']:checkbox").attr("checked", true);
		$("#checkbox-" + id).attr("checked", true);
	}
	function downFile(url, name) {
		var u = "${ctx}/sys/download?pathUrl=";
		u = u + url + "&coi=" + encodeURI(name);
		window.location.href = u;
	}
</script>
<div class="table-responsive">
	<table class="table table-striped table-vmiddle bootgrid-table">
		<thead>
			<tr>
				<th class="text-center">可办理的事项名称</th>
				<th class="text-center">申请材料</th>
				<th class="text-center">原件</th>
				<th class="text-center">复印件</th>
				<th class="text-center">收取</th>
				<th class="text-center col-xs-1">操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${project.prjStageMaterialVoMap}" var="map"
				varStatus="c">
				<c:forEach items="${map.value}" var="mater" varStatus="cc">
					<input type="hidden" value="${mater.id}"
						name="prjStageMaterialVoList[${c.index}].id" />
					<tr>
						<c:if test="${cc.index eq 0}">
							<input type="checkbox" value="${map.key}" data-type="taskId"
								name="prjTaskVoList[${v.index}].taskId">
							<td rowspan="${fn:length(map.value)}">${fns:getPrjTaskDefineVo(map.key).taskName}</td>
						</c:if>
						<td><c:if test="${mater.isMandatory eq '1'}">
								<span style="color: red">*</span>
							</c:if>${mater.materName}</td>
						<td>
							<div class="fg-line">
								<input type="text" class="form-control input-sm text-center"
									name="prjStageMaterialVoList[${c.index}].originalNum"
									placeholder="个数" disabled value="${mater.originalNum}">
							</div>
						</td>
						<td>
							<div class="fg-line">
								<input type="text" class="form-control input-sm text-center"
									name="prjStageMaterialVoList[${c.index}].copyNum"
									placeholder="个数" disabled value="${mater.copyNum}">
							</div>
						</td>
						<td class="text-center"><label
							class="checkbox checkbox-inline"> <input
								name="prjStageMaterialVoList[${c.index}].isComplete"
								id="checkbox-${c.index}" type="checkbox" value="1"
								${mater.isComplete eq '1'?"checked onclick='return false'":""}>
								<i class="input-helper"></i>
						</label></td>
						<td class="text-left text-nowrap"><c:if
								test="${mater.isComplete eq '1'&&mater.materialAddr ne ''}">
								<button class="btn btn-icon btn-file bgm-lightblue"
									type="button" title="${mater.materialName}"
									onClick="downFile('${mater.materialAddr}','${mater.materialName}')">
									<i class="md md-file-download"></i>
								</button>
							</c:if> <c:if
								test="${mater.isComplete eq '1'&&mater.materialAddr eq ''}">
								<sys:file id="file${c.index}" cssClass="btn-info"
									callFunction='selected("${c.index}")'
									downloadFileAddress="${mater.materialAddr}"
									downloadFileName="${mater.materName}"
									fileName="prjStageMaterialVoList[${c.index}].materialName"
									fileAddress="prjStageMaterialVoList[${c.index}].materialAddr"></sys:file>
							</c:if> <c:if test="${mater.isComplete ne '1'}">
								<sys:file id="file${c.index}" cssClass="btn-info"
									callFunction='selected("${c.index}")'
									downloadFileAddress="${mater.materialAddr}"
									downloadFileName="${mater.materName}"
									fileName="prjStageMaterialVoList[${c.index}].materialName"
									fileAddress="prjStageMaterialVoList[${c.index}].materialAddr"></sys:file>
							</c:if></td>
					</tr>
				</c:forEach>
			</c:forEach>
		</tbody>
	</table>
</div>
