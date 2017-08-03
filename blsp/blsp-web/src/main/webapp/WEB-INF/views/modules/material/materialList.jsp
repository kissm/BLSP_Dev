<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>申报材料管理</title>
	<meta name="decorator" content="blank"/>
	<script type="text/javascript">
        $(document).ready(function() {
            $("#searchForm").validate({
                rules: {
                    id:{number:true},
                },
                messages: {
                    id:'请输入正确的数字ID'
                },
                submitHandler: function(form){
                    form.submit();
                }
            });
        });
        function deleteById(id,name){
            url="${ctx}/material/deleteById";
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
//                javascrtpt:window.location.href='findById?id='+id;
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
            $('tr.materialId'+id).remove();
        }
	</script>
</head>
<body>
<div class="card">
    <div class="card-header">
        <h2>申报材料管理<small>您可通过本功能对申报材料进行维护。</small></h2>
        <ul class="actions">
            <li>
                <button data-toggle="tooltip" data-placement="bottom" class="btn btn-success btn-icon m-r-5" title="新增"
                        onclick="javascrtpt:window.location.href='${ctx}/material/add'"><i class="md md-add"></i></button>
            </li>
            <li>
                <button data-toggle="tooltip" data-placement="bottom" class="btn btn-default btn-icon m-r-5" title="刷新"
                        onclick="refresh();"><i class="md md-autorenew"></i></button>
            </li>
        </ul>
    </div>
    <form:form role="form" id="searchForm"  modelAttribute="materialDto" action="${ctx}/material/list" method="post">
        <form:input id="pageNo" path="pageNo" type="hidden"/>
        <form:input id="pageSize" path="pageSize" type="hidden"/>
        <div class="card-body card-padding">
            <div class="row">
                <div class="col-sm-2 form-group">
                    <div class="fg-line">
                        <label class="control-label">材料ID：</label>
                        <form:input placeholder="材料ID" type="text" path="id" id="id" maxlength="8" class="form-control"/>
                    </div>
                </div>
                <div class="col-sm-4 form-group">
                    <div class="fg-line">
                        <label class="control-label">材料名称：</label>
                        <form:input placeholder="材料名称" type="text" path="name"  maxlength="50" class="form-control"/>
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
                <th class="text-center col-sm-4">材料名称</th>
                <th class="text-center">状态</th>
                <th class="text-center">创建时间</th>
                <th class="text-center">更新时间</th>
                <%--<shiro:hasPermission name="zuh:zuhConsumerApp:edit">--%>
                <th class="text-center col-xs-1">操作</th>
                <%--</shiro:hasPermission>--%>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${page.getList()}" var="material">
                <tr class="materialId${material.id}" >
                    <td class="text-center">${material.id}</td>
                    <td>${material.name}</td>
                    <td class="text-center">

                        <c:if test="${material.isValid == 1}">启用</c:if>
                        <c:if test="${material.isValid == 0}">停用</c:if>
                        <%--<c:otherwise>否</c:otherwise>--%>
                    </td>
                    <td class="text-center"><fmt:formatDate value="${material.creatTime}" type="both" pattern="yy-MM-dd"/></td>
                    <td class="text-center"><fmt:formatDate value="${material.updateTime}" type="both" pattern="yy-MM-dd"/></td>
                    <%--<shiro:hasPermission name="zuh:zuhConsumerApp:edit">--%>
                    <td class="text-left text-nowrap">
                        <button title="修改" onclick="window.location.href='${ctx}/material/findById?id=${material.id}'" data-toggle="tooltip" data-placement="bottom" type="button" class="btn bgm-orange btn-icon m-r-5" ><li class="md md-edit"></li></button>
                        <button title="删除" onclick="deleteById('${material.id}','${material.name}')" data-toggle="tooltip" data-placement="bottom" type="button" class="btn btn-danger btn-icon" ><li class="md md-delete"></li></button>
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