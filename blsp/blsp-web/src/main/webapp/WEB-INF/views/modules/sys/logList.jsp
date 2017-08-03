<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>日志管理</title>
    <meta name="decorator" content="blank"/>
    <script type="text/javascript">
        function page(n, s) {
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }
    </script>
</head>
<body>
<!-- 	<ul class="nav nav-tabs"> -->
<%-- 		<li class="active"><a href="${ctx}/sys/log/">日志列表</a></li> --%>
<!-- 	</ul> -->
<%--<form:form id="searchForm" action="${ctx}/sys/log/" method="post" class="breadcrumb form-search">--%>
<%--<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>--%>
<%--<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>--%>
<%--<div>--%>
<%--<label>操作菜单：</label>--%>
<%--<input id="title" name="title" type="text" maxlength="50" class="input-mini" value="${log.title}"/>--%>
<%--<label>操作用户：</label><input id="createBy.name" name="createBy.name" type="text" maxlength="50" class="input-mini" value="${log.createBy.name}"/>--%>
<%--<label>日期范围：&nbsp;</label>--%>
<%--<input id="beginDate" name="beginDate" type="text" readonly="readonly" maxlength="20" class="input-mini Wdate"--%>
<%--value="<fmt:formatDate value="${log.beginDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>--%>
<%--<label>&nbsp;--&nbsp;&nbsp;&nbsp;</label><input id="endDate" name="endDate" type="text" readonly="readonly" maxlength="20" class="input-mini Wdate"--%>
<%--value="<fmt:formatDate value="${log.endDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>&nbsp;&nbsp;--%>
<%--&nbsp;<label for="exception"><input id="exception" name="exception" type="checkbox"${log.exception eq '1'?' checked':''} value="31"/>只查询异常信息</label>--%>
<%--&nbsp;&nbsp;&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>&nbsp;&nbsp;--%>
<%--</div>--%>
<%--</form:form>--%>
<%--<sys:message content="${message}"/>--%>


<div class="card">
    <div class="card-header">
        <h2>日志管理
            <small>您可通过本功能查询日志。</small>
        </h2>
        <ul class="actions">
            <li>
                <button data-toggle="tooltip" data-placement="bottom" type="button" title="刷新" class="btn btn-default btn-icon m-r-5" onclick="refresh();"><i class="md md-autorenew"></i></button>
            </li>
        </ul>
    </div>
    <form:form id="searchForm" action="${ctx}/sys/log/" method="post">
        <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
        <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>

        <div class="card-body card-padding">
            <div class="row">
                <div class="col-sm-4 form-group">
                    <div class="fg-line">
                        <label class="control-label">操作菜单：</label>
                        <input placeholder="操作菜单" type="text" id="title" name="title" maxlength="50"
                               value="${log.title}" class="form-control"/>
                    </div>
                </div>

                <div class="col-sm-4 form-group">
                    <label class="control-label">操作用户：</label>
                    <div class="fg-line">
                        <input placeholder="操作用户" type="text" id="createBy.name" name="createBy.name" maxlength="50"
                               value="${log.createBy.name}" class="form-control"/>
                    </div>
                </div>
                <div class="col-sm-4 form-group">
                    <label class="control-label" >&nbsp;</label>
                    <div class="checkbox">
                        <label class="checkbox checkbox-inline m-r-20">
                            <input type="checkbox" id="exception" name="exception"  ${log.exception eq '1'?' checked':''} value="1">
                            <i class="input-helper"></i>只查询异常信息
                        </label>
                    </div>
                </div>

                <div class="col-xs-4 form-group">
                    <label class="control-label">开始日期：</label>

                    <div class="input-group">
                        <span class="input-group-addon"><i class="md md-event"></i></span>

                        <div class="dtp-container dropdown fg-line">
                            <input type="text" id="beginDate" name="beginDate"
                                   value="<fmt:formatDate value="${log.beginDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                                   class="form-control date-time-picker" data-toggle="dropdown" placeholder="单击此处..."
                                   aria-expanded="false">
                        </div>
                    </div>
                </div>

                <div class="col-xs-4 form-group">
                    <label class="control-label">结束日期：</label>

                    <div class="input-group">
                        <span class="input-group-addon"><i class="md md-event"></i></span>

                        <div class="dtp-container dropdown fg-line">
                            <input type="text" id="endDate" name="endDate"
                                   value="<fmt:formatDate value="${log.endDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                                   class="form-control date-time-picker" data-toggle="dropdown" placeholder="单击此处..."
                                   aria-expanded="false">
                        </div>
                    </div>
                </div>
                <div class="col-sm-2 form-group">
                    <label class="control-label"></label>
                    <button id="btnSubmit" type="submit" class="btn btn-primary waves-effect form-control">查询</button>
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

                <th>操作菜单</th>
                <th>操作用户</th>
                <th>所在公司</th>
                <%--<th class="text-center">所在部门</th>--%>
                <th>操作者IP</th>
                <th>操作时间</th>
            </tr>
            </thead>
            <tbody><%
                request.setAttribute("strEnter", "\n");
                request.setAttribute("strTab", "\t");
            %>
            <c:forEach items="${page.list}" var="log">
                <tr>
                    <td>${log.title}</td>
                    <td>${log.createBy.name}</td>
                    <td>${log.createBy.company.name}</td>
                        <%--<td>${log.createBy.office.name}</td>--%>
                        <%--<td><strong>${log.requestUri}</strong></td>
                        <td>${log.method}</td>--%>
                    <td>${log.remoteAddr}</td>
                    <td><fmt:formatDate value="${log.createDate}" type="both"/></td>
                </tr>
                <c:if test="${not empty log.exception}">
                    <tr>
                        <td colspan="8" style="word-wrap:break-word;word-break:break-all;">
                            异常信息: <br/>
                                ${fn:replace(fn:replace(fns:escapeHtml(log.exception), strEnter, '<br/>'), strTab, '&nbsp; &nbsp; ')}
                        </td>
                    </tr>
                </c:if>
            </c:forEach>

            </tbody>
        </table>
    </div>
    ${page}
</div>
</body>
</html>