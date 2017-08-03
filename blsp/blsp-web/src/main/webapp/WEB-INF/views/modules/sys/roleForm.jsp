<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>角色管理</title>
    <meta name="decorator" content="blank"/>
    <%@include file="/WEB-INF/views/include/treeview.jsp" %>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#inputForm").validate({
                rules: {
                    name: {remote: "${ctx}/sys/role/checkName?oldName=" + encodeURIComponent("${role.name}")},
                    enname: {remote: "${ctx}/sys/role/checkEnname?oldEnname=" + encodeURIComponent("${role.enname}")}
                },
                messages: {
                    name: {remote: "角色名已存在"},
                    enname: {remote: "英文名已存在"}
                },
                submitHandler: function (form) {
                    var ids = [], nodes = tree.getCheckedNodes(true);
                    for (var i = 0; i < nodes.length; i++) {
                        ids.push(nodes[i].id);
                    }
                    $("#menuIds").val(ids);
                    var ids2 = [], nodes2 = tree2.getCheckedNodes(true);
                    for (var i = 0; i < nodes2.length; i++) {
                        ids2.push(nodes2[i].id);
                    }
                    $("#officeIds").val(ids2);
                    form.submit();
                }
            });


            var setting = {
                check: {enable: true, nocheckInherit: true}, view: {selectedMulti: false},
                data: {simpleData: {enable: true}}, callback: {
                    beforeClick: function (id, node) {
                        tree.checkNode(node, !node.checked, true, true);
                        return false;
                    }
                }
            };

            // 用户-菜单
            var zNodes = [
                    <c:forEach items="${menuList}" var="menu">{
                    id: "${menu.id}",
                    pId: "${not empty menu.parent.id?menu.parent.id:0}",
                    name: "${not empty menu.parent.id?menu.name:'权限列表'}"
                },
                </c:forEach>];
            // 初始化树结构
            var tree = $.fn.zTree.init($("#menuTree"), setting, zNodes);
            // 不选择父节点
            tree.setting.check.chkboxType = {"Y": "ps", "N": "s"};
            // 默认选择节点
            var ids = "${role.menuIds}".split(",");
            for (var i = 0; i < ids.length; i++) {
                var node = tree.getNodeByParam("id", ids[i]);
                try {
                    tree.checkNode(node, true, false);
                } catch (e) {
                }
            }
            // 默认展开全部节点
            tree.expandAll(true);

            // 用户-机构
            var zNodes2 = [
                    <c:forEach items="${officeList}" var="office">{
                    id: "${office.id}",
                    pId: "${not empty office.parent?office.parent.id:0}",
                    name: "${office.name}"
                },
                </c:forEach>];
            // 初始化树结构
            var tree2 = $.fn.zTree.init($("#officeTree"), setting, zNodes2);
            // 不选择父节点
            tree2.setting.check.chkboxType = {"Y": "ps", "N": "s"};
            // 默认选择节点
            var ids2 = "${role.officeIds}".split(",");
            for (var i = 0; i < ids2.length; i++) {
                var node = tree2.getNodeByParam("id", ids2[i]);
                try {
                    tree2.checkNode(node, true, false);
                } catch (e) {
                }
            }
            // 默认展开全部节点
            tree2.expandAll(true);
            // 刷新（显示/隐藏）机构
            refreshOfficeTree();
            $("#dataScope").change(function () {
                refreshOfficeTree();
            });
        });
        function refreshOfficeTree() {
            if ($("#dataScope").val() == 9) {
                $("#officeTree").show();
            } else {
                $("#officeTree").hide();
            }
        }
        function updateSort() {
            $("#inputForm").attr("action", "${ctx}/sys/role/save");
            $("#inputForm").submit();
        }
    </script>
</head>
    <body>
        <div class="card">
            <div class="card-header">
                <h2>角色<shiro:hasPermission name="sys:role:edit">${not empty role.id?'修改':'添加'}</shiro:hasPermission></h2>
                <ul class="actions">
                    <li>
                        <button data-toggle="tooltip" data-placement="bottom" title="返回" class="btn btn-success btn-icon m-r-5" onclick="javascrtpt:history.go(-1)"><i
                                class="md md-arrow-back"></i></button>
                    </li>
                    <c:if test="${(role.sysData eq fns:getDictValue('是', 'yes_no', '1') && fns:getUser().admin)||!(role.sysData eq fns:getDictValue('是', 'yes_no', '1'))}">
                        <shiro:hasPermission name="sys:role:edit">
                            <li>
                                <button data-toggle="tooltip" data-placement="bottom" title="保存" class="btn btn-info btn-icon m-r-5" onclick="updateSort();"><i
                                        class="md md-save"></i></button>
                            </li>
                        </shiro:hasPermission>
                    </c:if>
                    <li>
                        <button data-toggle="tooltip" data-placement="bottom" title="刷新" class="btn btn-default btn-icon m-r-5"
                                onclick="javascrtpt:window.location.href='${ctx}/sys/role/form?id=${role.id}&officeId=${role.office.id}'">
                            <i class="md md-autorenew"></i></button>
                    </li>
                </ul>
            </div>
            <div class="card-body card-padding">
                <sys:message content="${message}"/>
                <form:form id="inputForm" modelAttribute="role" action="${ctx}/sys/role/save" method="post"
                           class="form-horizontal">
                    <input type="hidden" name="id" value="${role.id}">

                    <div class="form-group">
                        <label class="col-sm-2 control-label">归属机构:</label>

                        <div class="col-sm-10">
                            <sys:treeselect id="office" name="office.id" value="${role.office.id}" labelName="office.name"
                                            labelValue="${role.office.name}"
                                            title="机构" url="/sys/office/treeData" cssClass="required"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">角色名称:</label>

                        <div class="col-sm-10">
                            <input id="oldName" name="oldName" type="hidden" value="${role.name}">

                            <div class="fg-line">
                                <input placeholder="角色名称" type="text" name="name" value="${role.name}" maxlength="50"
                                       class="form-control required">
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">英文名称:</label>

                        <div class="col-sm-10">
                            <input id="oldEnname" name="oldEnname" type="hidden" value="${role.enname}">

                            <div class="fg-line">
                                <input placeholder="英文名称" type="text" name="enname" value="${role.enname}" maxlength="50"
                                       class="form-control required">
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">角色类型:</label>

                        <div class="col-sm-10">
                            <form:select path="roleType" class="selectpicker form-control">
                                <form:options items="${fns:getDictList('role_type')}" itemLabel="label" itemValue="value"
                                              htmlEscape="false"/>
                            </form:select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">是否系统数据:</label>

                        <div class="col-sm-10">
                            <form:select path="sysData" class="selectpicker form-control">
                                <form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value"
                                              htmlEscape="false"/>
                            </form:select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">是否可用:</label>

                        <div class="col-sm-10">
                            <form:select path="useable" class="selectpicker form-control">
                                <form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value"
                                              htmlEscape="false"/>
                            </form:select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">数据范围:</label>

                        <div class="col-sm-10">
                            <form:select path="dataScope" class="selectpicker form-control">
                                <form:options items="${fns:getDictList('sys_data_scope')}" itemLabel="label" itemValue="value"
                                              htmlEscape="false"/>
                            </form:select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">角色授权:</label>

                        <div class="col-sm-10">
                            <div id="menuTree" class="ztree" style="margin-top:3px;float:left;"></div>
                            <form:hidden path="menuIds"/>
                            <div id="officeTree" class="ztree" style="margin-left:100px;margin-top:3px;float:left;"></div>
                            <form:hidden path="officeIds"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">默认访问路径:</label>

                        <div class="col-sm-10">
                            <div class="fg-line">
                                <form:textarea path="remarks" htmlEscape="false" rows="3" maxlength="200"
                                               class="form-control auto-size" placeholder="请填写备注"/>
                            </div>
                        </div>
                    </div>
                    <%--<form:input type="hidden" path="remarks" />--%>
                </form:form>
            </div>
        </div>
    </body>
</html>