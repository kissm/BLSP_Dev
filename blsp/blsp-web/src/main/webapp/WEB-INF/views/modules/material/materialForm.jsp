<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>申报材料${not empty materialDto.id?'修改':'添加'}</title>
    <meta name="decorator" content="blank"/>
    <script type="text/javascript">
        $(document).ready(function() {
            $("#modifyForm").validate({
                rules: {
                    name: {remote: "${ctx}/material/checkMaterialName?oldMaterialName=" + encodeURIComponent('${materialDto.name}')}
                },
                messages: {
                    name: {remote: "该材料名称已存在"},
                },
                submitHandler: function(form){
                    form.submit();
                }
            });
        });

        function submitForm() {
            $("#modifyForm").submit();
        };
    </script>
</head>
<body>
<div class="card">
    <div class="card-header">
        <h2>申报材料${not empty materialDto.id?'修改':'添加'}
            <small>您可通过本功能对申报材料进行${not empty materialDto.id?'修改':'添加'}。</small>
        </h2>
        <ul class="actions">
            <li>
                <button title="返回" data-toggle="tooltip" data-placement="bottom" class="btn btn-success btn-icon m-r-5" onclick="javascrtpt:history.go(-1)"><i class="md md-arrow-back"></i></button>
            </li>
            <li>
                <%--<shiro:hasPermission name="sys:office:edit">--%>
                <button title="保存" data-toggle="tooltip" data-placement="bottom" class="btn btn-info btn-icon m-r-5" onclick="submitForm();"><i class="md md-save"></i></button>
                <%--</shiro:hasPermission>--%>
            </li>
        </ul>
    </div>
    <div class="card-body card-padding">
        <div class="row">
            <form:form role="form" id="modifyForm" modelAttribute="materialDto" action="${ctx}/material/modify"
                       method="post">
                <form:input type="hidden" id="id" path="id"/>
                <form:input type="hidden" id="isDelete" path="isDelete"/>
                <div class="card-body card-padding">
                    <div class="row">
                        <div class="col-xs-10 form-group">
                            <label for="name">材料名称：</label>
                            <div class="fg-line">
                                <%--<input type="hidden" id="oldName" name="oldName" value="${materialDto.name}"/>--%>
                                <form:input type="text" class="form-control required" maxlength="200" id="name" path="name"/>
                            </div>
                        </div>
                        <div class="col-xs-4 form-group">
                            <label for="isValid">状态：</label>

                            <div class="fg-line">
                                    <%--<input type="text" class="form-control" id="isValid" name="isValid" placeholder="状态">--%>
                                <div class="select">
                                    <form:select path="isValid" id="isValid" class="form-control selectpicker">
                                        <form:option value="1">启用</form:option>
                                        <form:option value="0">停用</form:option>
                                    </form:select>
                                </div>
                            </div>
                        </div>
                        <div class="col-xs-4 form-group">
                            <label for="isOriginalCumulation">原件份数是否唯一：</label>
                            <div class="fg-line">
                                <div class="select">
                                    <form:select path="isOriginalCumulation" id="isOriginalCumulation" class="form-control selectpicker">
                                        <form:option value="0">唯一</form:option>
                                        <form:option value="1">不唯一</form:option>
                                    </form:select>
                                </div>
                            </div>
                        </div>
                        <div class="col-xs-10 form-group">
                            <label for="description">描述：</label>

                            <div class="fg-line">
                                <form:textarea id="description" path="description" class="form-control auto-size"
                                               placeholder="描述信息" maxlength="200"></form:textarea>
                            </div>
                        </div>
                    </div>
                </div>
            </form:form>
            <%--<div class="btn-demo text-center">--%>
                <%--<button data-toggle="modal" onclick="submitForm()" class="btn btn-primary waves-effect">受理</button>--%>
                <%--<button class="btn btn-default waves-effect" onclick="javascrtpt:history.go(-1)">返回</button>--%>
            <%--</div>--%>
        </div>

    </div>
</div>

</body>
</html>