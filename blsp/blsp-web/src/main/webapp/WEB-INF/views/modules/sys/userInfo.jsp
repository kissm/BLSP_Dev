<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>用户管理</title>
    <meta name="decorator" content="blank"/>
    <script type="text/javascript">
        $(document).ready(function() {
            $("#inputForm").validate({
                rules: {
                    loginName: {remote: "${ctx}/sys/user/checkLoginName?oldLoginName=" + encodeURIComponent('${user.loginName}')}
                },
                messages: {
                    loginName: {remote: "用户登录名已存在"},
                    confirmNewPassword: {equalTo: "输入与上面相同的密码"},
                    roleIdList: "请选择角色"
                },
                submitHandler: function(form){
                    form.submit();
                }
            });
        });
        function saveOrUpdate() {
            $("#inputForm").attr("action", "${ctx}/sys/user/saveSelf");
            $("#inputForm").submit();
        }
    </script>
    <style>
        .form-search input, .form-inline input, .form-horizontal input, .form-search textarea, .form-inline textarea, .form-horizontal textarea, .form-search select, .form-inline select, .form-horizontal select, .form-search .help-inline, .form-inline .help-inline, .form-horizontal .help-inline, .form-search .uneditable-input, .form-inline .uneditable-input, .form-horizontal .uneditable-input, .form-search .input-prepend, .form-inline .input-prepend, .form-horizontal .input-prepend, .form-search .input-append, .form-inline .input-append, .form-horizontal .input-append
    </style>
</head>
<body>
<div class="card">
    <div class="card-header">
        <h2>个人资料信息
            <small>个人资料信息</small></h2>
        <ul class="actions">
            <li>
                <button data-toggle="tooltip" data-placement="bottom" title="返回" class="btn btn-success btn-icon m-r-5" onclick="javascrtpt:history.go(-1)"><i class="md md-arrow-back"></i></button>
            </li>
            <li>
                <button data-toggle="tooltip" data-placement="bottom" title="保存" class="btn btn-info btn-icon m-r-5" onclick="saveOrUpdate();"><i class="md md-save"></i></button>
            </li>
        </ul>
    </div>
    <form:form id="inputForm" modelAttribute="user" action="${ctx}/sys/user/saveSelf" method="post" class="form-horizontal">
        <form:hidden path="id"/>
        <input type="hidden" name="userInfo" value="userInfo"/>
        <div class="card-body card-padding">
            <sys:message content="${message}"/>
            <div class="form-group">
                <label class="col-sm-2 control-label">头像:</label>
                <div class="col-sm-10">
                    <c:if test="${!empty user.photo}">
                        <sys:file id="file" type="img" fileAddress="photo" downloadFileAddress="${user.photo}" ></sys:file>
                    </c:if>
                    <c:if test="${empty user.photo}">
                        <sys:file id="file" type="img" fileAddress="photo" downloadFileAddress="${ctxStaticModern}/img/userImg.png" ></sys:file>
                    </c:if>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">单位:</label>
                <div class="col-sm-10">
                    <label class="control-label">${user.company.name}</label>
                    <input type="hidden" name="company.id" value="${user.company.id}">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">部门:</label>
                <div class="col-sm-10">
                    <c:set var="officeNames">
                        <c:forEach items="${user.officeList}" var="officeName">${officeName.name},</c:forEach>
                    </c:set>
                    <label class="control-label">${officeNames.substring(0,officeNames.length()-1)}</label>
                    <input type="hidden" name="office.id" value="${user.office.id}">
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">工号:</label>
                <div class="col-sm-10">
                    <div class="fg-line">
                        <label class="control-label">${user.no}</label>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">姓名:</label>
                <div class="col-sm-10">
                    <div class="fg-line">
                        <label class="control-label">${user.name}</label>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label for="loginName" class="col-sm-2 control-label">登录名:</label>
                <div class="col-sm-10">
                    <div class="fg-line">
                        <input id="oldLoginName" name="oldLoginName" type="hidden" value="${user.loginName}">
                        <form:input path="loginName" htmlEscape="false" type="text" maxlength="50" class="form-control required" placeholder="登录名" />
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label for="email" class="col-sm-2 control-label">邮箱:</label>
                <div class="col-sm-10">
                    <div class="fg-line">
                        <form:input path="email" type="txt" htmlEscape="false" maxlength="50" class="form-control required email" placeholder="邮箱" />
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label for="phone" class="col-sm-2 control-label">电话:</label>
                <div class="col-sm-10">
                    <div class="fg-line">
                        <form:input path="phone" htmlEscape="false" maxlength="100" class="form-control auto-size required phone" minlength="6" placeholder="电话" />
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label for="mobile" class="col-sm-2 control-label">手机:</label>
                <div class="col-sm-10">
                    <div class="fg-line">
                        <form:input path="mobile" htmlEscape="false" maxlength="100" class="form-control auto-size required mobile" minlength="6" placeholder="手机" />
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label for="remarks" class="col-sm-2 control-label">备注:</label>
                <div class="col-sm-10">
                    <div class="fg-line">
                        <textarea class="form-control auto-size" placeholder="备注描述信息" id="remarks" name="remarks" maxlength="200" >${user.remarks}</textarea>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">用户角色:</label>
                <div class="col-sm-10">
                    <label class="control-label">${user.roleNames}</label>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">上次登录:</label>
                <div class="col-sm-10">
                    <label class="control-label">IP: ${user.oldLoginIp}&nbsp;&nbsp;&nbsp;&nbsp;时间：<fmt:formatDate value="${user.oldLoginDate}" type="both" dateStyle="full"/></label>
                </div>
            </div>
        </div>
    </form:form>
</div>
</body>
</html>



