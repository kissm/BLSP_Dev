<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>申报效能监控</title>
	<meta name="decorator" content="blank"/>
	<script type="text/javascript">
		$(document).ready(function() {
		});
	</script>
</head>
<body>
<div class="card">
    <div class="card-header">
        <h2>申报效能监控<small>申报效能监控列表。</small></h2>
        <ul class="actions">
            <li>
                <button data-toggle="tooltip" data-placement="bottom" class="btn btn-default btn-icon m-r-5" title="刷新"
                        onclick="refresh();"><i class="md md-autorenew"></i></button>
            </li>
        </ul>
    </div>
    <form:form role="form" id="searchForm"  modelAttribute="buildCompanyDto" action="${ctx}/build/list" method="post">
        <form:input id="pageNo" path="pageNo" type="hidden"/>
        <form:input id="pageSize" path="pageSize" type="hidden"/>
        <div class="card-body card-padding">
            <div class="row">
                <div class="col-sm-4 form-group">
                    <div class="fg-line">
                        <label class="control-label">单位名称：</label>
                        <form:input placeholder="单位名称" type="text" path="company"  maxlength="50" class="form-control"/>
                    </div>
                </div>
                <div class="col-sm-4 form-group">
                    <div class="fg-line">
                        <label class="control-label">建设单位企业信用代码或组织机构代码：</label>
                        <form:input placeholder="建设单位企业信用代码或组织机构代码" type="text" path="companyCode"  maxlength="50" class="form-control"/>
                    </div>
                </div>

                <div class="col-sm-2">
                    <label class="control-label"></label>
                    <button id="btnSubmit" type="submit" onclick="page(1)" class="btn btn-primary waves-effect form-control">查询</button>
                </div>
				<div class="col-sm-2">
					<label class="control-label"></label>
					<button id="btnReset" type="button" class="btn btn-primary waves-effect form-control" onclick="refresh();">重置</button>
				</div>                
            </div>
            <sys:message content="${message}"/>
        </div>
    </form:form>
    <div class="table-responsive">
        <table id="contentTable" class="table table-striped table-vmiddle bootgrid-table">
            <thead>
            <tr>
                <th class="text-center">ID</th>
                <th>单位名称</th>
                <th>建设单位企业信用代码或组织机构代码</th>
                <th class="text-center">法人代表</th>
                <th class="text-center">电话（法人代表）</th>
                <th class="text-center">手机（法人代表）</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${page.getList()}" var="buildCompany">
                <tr>
                    <td class="text-center">${buildCompany.id}</td>
                    <td>${buildCompany.company}</td>
                    <td><a href="${ctx}/build/buildPrjqueryList?prjInstanceVo.company=${buildCompany.company}&prjInstanceVo.companyCode=${buildCompany.companyCode}&prjInstanceVo.prjType=3">${buildCompany.companyCode}</a></td>
                    <td class="text-center">${buildCompany.legalEntity}</td>
                    <td class="text-center">${buildCompany.entityPhone}</td>
                    <td class="text-center">${buildCompany.entityMphone}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    ${page}
</div>

</body>
</html>