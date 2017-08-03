<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
    <head>
        <title>并联审批项目列表</title>
        <meta name="decorator" content="default"/>
        <script type="text/javascript">
            function selectBlsp(prjCode) {
                var backObjs = [];
                var obj = {};
                obj.name = "prjCode";
                obj.value = prjCode;
                backObjs.push(obj);
                closeWindow(backObjs);
            }
        </script>
    </head>
    <body>
        <div class="p-20">
            <form:form role="form" id="searchForm" modelAttribute="blspPrjInstanc" action="${ctx}/project/plusadd/getBlspList?prjType=${prjType}" method="post">
                <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
                <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>

                <div class="card-body card-padding">
                    <div id="clearQuery" class="row">
                        <div class="col-sm-6 form-group">
                            <div class="fg-line">
                                <label class="control-label">企业信用代码或组织机构代码：</label>
                                <form:input path="companyCode" class="form-control"/>
                            </div>
                        </div>
                        <div class="col-sm-3 form-group">
                            <label class="control-label"></label>
                            <button id="btnSubmit" type="submit" onclick="page(1);"
                                    class="btn btn-primary waves-effect form-control">查询
                            </button>
                        </div>
                        <div class="col-sm-3 form-group">
                            <label class="control-label"></label>
                            <button id="btnReset" type="button" onclick="refresh();"
                                    class="btn btn-primary waves-effect form-control">重置
                            </button>
                        </div>
                    </div>
                    <sys:message content="${message}"/>
                </div>
            </form:form>
        </div>
        <div class="table-responsive">
            <table id="contentTable"
                   class="table table-striped table-vmiddle bootgrid-table">
                <thead>
                <tr>
                    <th class="col-xs-1">序号</th>
                    <th class="col-xs-3">区项目编号</th>
                    <th class="col-xs-3">项目名称</th>
                    <th class="col-xs-2">项目单位</th>
                    <th class="col-xs-3">企业信用代码或组织机构代码</th>
                    <th class="col-xs-1">操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${page.list}" var="blspPrj" varStatus="i">
                    <tr>
                        <td>${i.index + 1}</td>
                        <td>
                            <a href="${ctx}/project/plusadd/getBlspPrjInstance?id=${blspPrj.id}">${blspPrj.prjCode}</a>
                        </td>
                        <td>${blspPrj.prjName}</td>
                        <td>${blspPrj.company}</td>
                        <td class="text-center">${blspPrj.companyCode}</td>
                        <td class="text-left text-nowrap">
                            <%--<button style="width: 58px;" onclick="selectBlsp('${blspPrj.prjCode}');" class="btn btn-info btn-xs waves-effect">选取</button>--%>
                            <button style="width: 58px;" onclick="javascrtpt:window.location.href='${ctx}/project/plusadd/getBlspPrjInstance?id=${blspPrj.id}'" class="btn btn-info btn-xs waves-effect">选取</button>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        ${page}
    </body>
</html>