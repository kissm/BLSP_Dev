<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>流程时限管理</title>
    <meta name="decorator" content="blank"/>
    <script type="text/javascript">
        $(document).ready(function() {

        });
        function deleteById(id,name){
            url="${ctx}/workflow/deleteById";
            swal({
                title: "你确定吗？",
                text: name + " 将被删除",
                type: "warning",
                showCancelButton: true,
                confirmButtonColor: "#DD6B55",
                cancelButtonText: '取消',
                confirmButtonText: "是的，删除！",
                closeOnConfirm: false
            }, function(){
                post(url,{id:id},removeTr(id),null);
                swal("完成！", name+" 已被删除", "success");
            });
        }
        function post(url,data,fn,efn){
            var success = fn||function(){};
            var error = efn||function(){};
            $.ajax({
                url:url,
                type: "POST",
                contentType:'application/json; charset=utf-8',
                data:JSON.stringify(data),
                dataType: 'json',
                success:success,error:error
            });
        }
        function removeTr(id){
            $('tr.workflowId'+id).remove();
        }
    </script>
</head>
<body>
<div class="card">
    <div class="card-header">
        <h2>流程时限管理<small>您可通过本功能对总流程时长配置维护。</small></h2>
        <ul class="actions">
            <li>
                <button title="新增" data-toggle="tooltip" data-placement="bottom" class="btn btn-success btn-icon m-r-5" onclick="javascrtpt:window.location.href='${ctx}/workflow/add'"><i class="md md-add"></i></button>
            </li>
            <li>
                <button data-toggle="tooltip" data-placement="bottom" type="button" title="刷新" class="btn btn-default btn-icon m-r-5" onclick="refresh();"><i class="md md-autorenew"></i></button>
            </li>
        </ul>
    </div>
    <form:form role="form" id="searchForm"  modelAttribute="workFlowDto" action="${ctx}/workflow/list" method="post">
        <form:input id="pageNo" path="pageNo" type="hidden"/>
        <form:input id="pageSize" path="pageSize" type="hidden"/>
        <div class="card-body card-padding">
            <div class="row">
                <div class="col-sm-4 form-group">
                    <div class="fg-line">
                        <label class="control-label">配置名称：</label>
                        <form:input placeholder="配置名称" type="text" path="name"  maxlength="50" class="form-control"/>
                    </div>
                </div>
                <div class="col-sm-2">
                    <label class="control-label">类型：</label>
                    <div class="form-group">
                        <div class="fg-line">
                            <div class="select">
                                <form:select path="flowType" class="form-control selectpicker">
                                    <form:option value=""></form:option>
                                    <form:option value="1" >政府</form:option>
                                    <form:option value="2" >企业</form:option>
                                </form:select>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-sm-2">
                    <label class="control-label">状态：</label>
                    <div class="form-group">
                        <div class="fg-line">
                            <div class="select">
                                <form:select path="isValid" class="form-control selectpicker">
                                    <form:option value=""></form:option>
                                    <form:option value="1" >启用</form:option>
                                    <form:option value="0" >停用</form:option>
                                </form:select>
                            </div>
                        </div>
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
                <th class="text-center col-sm-4">配置名称</th>
                <th class="text-center">类型</th>
                <th class="text-center">配置时长</th>
                <th class="text-center">时间类型</th>
                <th class="text-center">状态</th>
                <%--<th class="text-center">创建时间</th>--%>
                <th class="text-center">更新时间</th>
                <%--<shiro:hasPermission name="zuh:zuhConsumerApp:edit">--%>
                <th class="text-center col-xs-1">操作</th>
                <%--</shiro:hasPermission>--%>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${page.getList()}" var="workFlow">
                <tr class="workflowId${workFlow.id}">
                    <td class="text-center">${workFlow.id}</td>
                    <td>${workFlow.name}</td>
                    <td class="text-center">
                        <c:if test="${workFlow.flowType == 1}">政府</c:if>
                        <c:if test="${workFlow.flowType == 2}">企业</c:if>
                    </td>
                    <td class="text-center">${workFlow.timeLimit} 天</td>
                    <td class="text-center">
                        <c:if test="${workFlow.timeType == 1}">工作日</c:if>
                        <c:if test="${workFlow.timeType == 2}">自然日</c:if>
                    </td>
                    <td class="text-center">
                        <c:if test="${workFlow.isValid == 1}">启用</c:if>
                        <c:if test="${workFlow.isValid == 0}">停用</c:if>
                    </td>
                    <td class="text-center"><fmt:formatDate value="${workFlow.updateTime}" type="both" pattern="yy-MM-dd"/></td>
                    <%--<shiro:hasPermission name="zuh:zuhConsumerApp:edit">--%>
                    <td class="text-left text-nowrap">
                        <button data-toggle="tooltip" data-placement="bottom" title="修改" onclick="javascrtpt:window.location.href='findById?id=${workFlow.id}'" 
                        		type="button" class="btn bgm-orange btn-icon m-r-5" ><span class="md md-edit"></span></button>
                        <button data-toggle="tooltip" data-placement="bottom" title="删除" onclick="deleteById('${workFlow.id}','${workFlow.name}')"
                                type="button" class="btn btn-danger btn-icon m-r-5" ><span class="md md-delete"></span></button>
                    </td>
                    <%--</shiro:hasPermission>--%>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    ${page}
</div>

</body>
</html>