<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>流程时限管理</title>
    <meta name="decorator" content="blank"/>
    <script type="text/javascript">
        $(document).ready(function() {

        });

    </script>
</head>
<body>
<div class="card">
    <div class="card-header">
        <h2>短信日志查看<small>您可通过本功能对总流程时长配置维护。</small></h2>
        <ul class="actions">
            <li>
                <button title="刷新" data-toggle="tooltip" data-placement="bottom" class="btn btn-default btn-icon m-r-5" onclick="refresh();"><i class="md md-autorenew"></i></button>
            </li>
        </ul>
    </div>
    <form:form role="form" id="searchForm"  modelAttribute="logSmsDto" action="${ctx}/workflow/smslist" method="post">
        <div class="card-body card-padding">
            <sys:message content="${message}"/>
        </div>
        <form:input id="pageNo" path="pageNo" type="hidden"/>
        <form:input id="pageSize" path="pageSize" type="hidden"/>
    </form:form>
    <div class="table-responsive">
        <table id="contentTable" class="table table-striped table-vmiddle bootgrid-table">
            <thead>
            <tr>
                <th class="text-center">发送时间</th>
                <th class="text-center ">模板Id</th>
                <th class="text-center">接收号码</th>
                <th class="text-center col-sm-4">发送内容</th>
                <th class="text-center">返回码</th>
                <th class="text-center">返回信息</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${page.getList()}" var="logSmsDto">
                <tr class="workflowId${logSmsDto.id}">
                    <td class="text-center"><fmt:formatDate value="${logSmsDto.sendtime}" type="both" pattern="yy-MM-dd HH:mm:ss"/></td>
                    <td class="text-center">${logSmsDto.tplid}</td>
                    <td class="text-center">${logSmsDto.receivers}</td>
                    <td >${logSmsDto.content}</td>
                    <td class="text-center">${logSmsDto.result}</td>
                    <td class="text-center">${logSmsDto.message}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    ${page}
</div>

</body>
</html>