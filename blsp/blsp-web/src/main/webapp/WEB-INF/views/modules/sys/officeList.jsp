<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>机构管理</title>
    <meta name="decorator" content="blank"/>
    <%@include file="/WEB-INF/views/include/treetable.jsp" %>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#treeTable").treeTable({expandLevel : 3}).show();
            <%--var tpl = $("#treeTableTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g, "");--%>
            <%--var data = ${fns:toJson(list)}, rootId = "${not empty office.id ? office.id : '0'}";--%>
            <%--addRow("#treeTableList", tpl, data, rootId, true);--%>
//            $("#treeTable").treeTable({expandLevel: 3}).show();
            $("#listForm").validate({
                rules: {
                },
                messages: {
                },
                submitHandler: function(form){
                    form.submit();
                }
            });
        });
        <%--function addRow(list, tpl, data, pid, root) {--%>
            <%--for (var i = 0; i < data.length; i++) {--%>
                <%--var row = data[i];--%>
                <%--if ((${fns:jsGetVal('row.parentId')}) == pid) {--%>
                    <%--$(list).append(Mustache.render(tpl, {--%>
                        <%--dict: {--%>
                            <%--type: getDictLabel(${fns:toJson(fns:getDictList('sys_office_type'))}, row.type)--%>
                        <%--}, pid: (root ? 0 : pid), row: row--%>
                    <%--}));--%>
                    <%--addRow(list, tpl, data, row.id);--%>
                <%--}--%>
            <%--}--%>
        <%--}--%>
        function updateSort() {
            $("#listForm").attr("action", "${ctx}/sys/office/updateSort");
            $("#listForm").submit();
        }
        function deleteById(url,userNames){
            if(userNames == '' ){
                swal({
                    title: "你确定吗？",
                    text: "此机构将被删除",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "#DD6B55",
                    cancelButtonText: '取消',
                    confirmButtonText: "是的，删除！",
                    closeOnConfirm: false
                }, function(){
                    javascrtpt:window.location.href=url;
                });
            }else{
                swal("请先移除以下用户再进行删除",userNames)
            }
        }


    </script>
</head>
<body>
    <div class="card">
        <div class="card-header">
            <h2>机构列表</h2>
            <ul class="actions">
                <li>
                    <button title="新增" data-toggle="tooltip" data-placement="bottom" class="btn btn-success btn-icon m-r-5" onclick="javascrtpt:window.location.href='${ctx}/sys/office/form'"><i class="md md-add"></i></button>
                </li>
                <shiro:hasPermission name="sys:office:edit">
                    <li>
                        <button data-toggle="tooltip" data-placement="bottom" title="保存排序" class="btn btn-info btn-icon m-r-5" onclick="updateSort();"><i class="md md-save"></i></button>
                    </li>
                </shiro:hasPermission>
                <li>
                    <button title="刷新" data-toggle="tooltip" data-placement="bottom" class="btn btn-default btn-icon m-r-5" onclick="javascrtpt:window.location.href='${ctx}/sys/office/list?id=1'"><i class="md md-autorenew"></i></button>
                </li>
            </ul>
        </div>

        <div class="card-body card-padding">
            <sys:message content="${message}"/>
            <div class="table-responsive">
                <form id="listForm" method="post">
                <table id="treeTable" class="table table-striped table-vmiddle bootgrid-table">
                    <thead>
                        <tr>
                            <th class="col-sm-3">机构名称</th>
                            <%--<th class="col-sm-1">负责人</th>--%>
                            <th class="col-sm-3">机构编码</th>
                            <th class="text-center">机构类型</th>
                            <th class="text-center">机构级别</th>
                            <th class="text-center">排序字段</th>
                            <%--<th class="col-sm-2">备注</th>--%>
                            <shiro:hasPermission name="sys:office:edit">
                                <th class="text-center col-xs-1">操作</th>
                            </shiro:hasPermission></tr>
                        </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${list}" var="office">
                        <tr id="${office.id}" pId="${not empty office.parent.id ? office.parent.id : '0'}">
                            <td nowrap>
                                <%--<i class="md ${not empty office.icon?office.icon:'hide'}"></i>--%>
                                <a href="${ctx}/sys/office/form?id=${office.id}">${office.name}</a>
                            </td>
                            <%--<td>${office.master}</td>--%>
                            <td>${office.code}</td>
                            <td class="text-center col-sm-1">
                                <c:forEach items="${fns:getDictList('sys_office_type')}" var="officeType">
                                    <c:if test="${officeType.value eq office.type}" >${officeType.label}</c:if>
                                </c:forEach>
                            </td>
                            <td class="text-center col-sm-1">
                                <c:forEach items="${fns:getDictList('sys_office_grade')}" var="officeGrade">
                                    <c:if test="${officeGrade.value eq office.grade}" >${officeGrade.label}</c:if>
                                </c:forEach>
                            </td>
                            <td>
                                <shiro:hasPermission name="sys:office:edit">
                                    <input type="hidden" name="ids" value="${office.id}"/>
                                    <div class="fg-line">
                                        <input name="sorts" value="${office.sort}" type="number" class="form-control input-sm text-center" placeholder="排序">
                                    </div>
                                </shiro:hasPermission>
                                <shiro:lacksPermission name="sys:office:edit">
                                    ${office.sort}
                                </shiro:lacksPermission>
                            </td>

                            <%--<td>${office.remarks}</td>--%>
                            <shiro:hasPermission name="sys:office:edit">
                                <c:set var="userNames" >
                                    <c:forEach items="${office.userList}" var="userOfOffice">${userOfOffice.name},</c:forEach>
                                </c:set>
                                <td class="text-left text-nowrap">

                                    <button title="修改" class="btn bgm-orange btn-icon m-r-5" data-toggle="tooltip" data-placement="bottom" type="button"
                                            onclick="javascrtpt:window.location.href='${ctx}/sys/office/form?id=${office.id}'"><i class="md md-edit"></i></button>
                                    <button title="删除" class="btn btn-danger btn-icon m-r-5" data-toggle="tooltip" data-placement="bottom" type="button"
                                            onclick="deleteById('${ctx}/sys/office/delete?id=${office.id}','${userNames}')"><i class="md md-delete"></i></button>
                                    <button title="添加下级机构" class="btn bgm-lightgreen btn-icon" data-toggle="tooltip" data-placement="bottom" type="button"
                                            onclick="javascrtpt:window.location.href='${ctx}/sys/office/form?parent.id=${office.id}'"><i class="md md-menu"></i></button>

                                </td>
                            </shiro:hasPermission>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                </form>
            </div>
        </div>
    </div>

</body>
</html>