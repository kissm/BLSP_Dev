<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>已有建设单位列表</title>
<meta name="decorator" content="default"/>
<script>
	$(document).ready(function() {

	});

	function selectCompany(companyCode,companyDto){
		if($("#"+companyCode).is(':checked')) {
			sessionStorage.setItem("compayMap", JSON.stringify(companyDto));
		}
	}

	function page(n, s) {
		$("#pageNo").val(n);
		$("#pageSize").val(s);
		$("#searchForm").submit();
		return false;
	}
	function sub(id) {
		$("#id").val(id);
		$("#form1").submit();
	}
</script>
</head>
<body>
<div class="card">
	<form id="form1" action="${ctx}/project/accpet/getProject" method="post">
		<input type="hidden" name="prjInstanceVo.prjType"
			value="${project.prjInstanceVo.prjType}"> <input type="hidden"
			name="prjInstanceVo.priceType" value="${project.prjInstanceVo.priceType}">
		<input type="hidden" name="prjInstanceVo.useageType"
			value="${project.prjInstanceVo.useageType}"> <input type="hidden" name="id" id="id">
		<input type="hidden" name="projectId" value="${project.prjInstanceVo.id}">
	</form>
	<form id="searchForm" role="form"
		action="${ctx}/project/accpet/project" method="post">
		<input name="pageNo" id="pageNo" type="hidden" value="${page.pageNo}">
		<input name="pageSize" id="pageSize" type="hidden"
			value="${page.pageSize}">
		<div class="p-20">
			<div class="card-body card-padding">
				<div class="row" id="clearQuery">
					<div class="col-sm-3 form-group">
						<div class="fg-line">
							<label>单位名称：</label> <input name="company" class="form-control"
								value="${company}" type="text" maxlength="50" placeholder="单位名称">
						</div>
					</div>
					<div class="col-sm-4 form-group">
						<div class="fg-line">
							<label>建设单位企业信用代码或组织机构代码：</label> <input name="companyCode"
								class="form-control" value="${companyCode}" type="text"
								maxlength="50" placeholder="建设单位企业信用代码或组织机构代码">
						</div>
					</div>
					<div class="col-sm-2">
						<label></label>
						<button class="btn btn-primary waves-effect form-control"
							id="btnSubmit" type="submit">查询</button>
					</div>
					<div class="col-sm-2">
						<label></label>
						<button type="button"
							class="btn btn-primary waves-effect form-control"
							onclick="clearQuery();">重置</button>
					</div>
				</div>
			</div>
			<div class="table-responsive">
				<table id="contentTable"
					class="table table-striped table-vmiddle bootgrid-table">
					<thead>
						<tr>
							<th class="col-xs-1"></th>
							<th class="col-xs-4">单位名称</th>
							<th class="col-xs-3">建设单位企业信用代码或组织机构代码</th>
							<th class="col-xs-2">法人代表</th>
							<th class="col-xs-2">电话（法人代表）</th>
							<th class="col-xs-2">手机（法人代表）</th>
							<%--<th class="col-xs-1">操作</th>--%>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.list}" var="project" varStatus="c">
							<tr>
								<td class="text-center">
									<label class="checkbox checkbox-inline" onclick='selectCompany("${project.companyCode}",${project})'>
										<input id="${project.companyCode}" type="radio" name="companyCode"
											   <c:if test="${prjCompanyCode eq project.companyCode}">checked</c:if> >
										<i class="input-helper"></i>
									</label>
								</td>
								<td>${project.company}</td>
								<td>${project.companyCode}</td>
								<td>${project.legalEntity}</td>
								<td>${project.entityPhone}</td>
								<td>${project.entityMphone}</td>
								<%--<td><button class="btn btn-icon btn-info m-r-5" data-toggle="tooltip" data-placement="bottom"--%>
											<%--type="button" onclick="sub(${project.id})" title="选择"/>--%>
									<%--<i class="md md-check"></i>--%>
								<%--</td>--%>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			${page}
		</div>
	</form>
	</div>
</body>