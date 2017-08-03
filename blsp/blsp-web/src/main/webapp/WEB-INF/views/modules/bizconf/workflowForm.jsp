<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>流程时限${not empty workFlowDto.id?'修改':'添加'}</title>
    <meta name="decorator" content="blank"/>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#inputForm").validate({
                rules: {
                    name: {remote: "${ctx}/workflow/checkWorkflowName?oldWorkflowName=" + encodeURIComponent('${workFlowDto.name}')},
                    timeLimit: {max: 999, min: 1}
                },
                messages: {
                    name: {remote: "配置名称已存在"},
                },
                submitHandler: function(form){
                    form.submit();
                }
            });
        });

        function saveOrUpdate() {
            $("#inputForm").submit();
        }
    </script>
</head>
<body>
<div class="card">
    <div class="card-header">
        <h2>流程时限${not empty workFlowDto.id?'修改':'添加'}
            <small>您可通过本功能对总流程时长配置${not empty workFlowDto.id?'修改':'添加'}。</small>
        </h2>
        <ul class="actions">
            <li>
                <button title="返回" data-toggle="tooltip" data-placement="bottom" class="btn btn-success btn-icon m-r-5" onclick="javascrtpt:history.go(-1)"><i class="md md-arrow-back"></i></button>
            </li>
            <li>
                <button title="保存" data-toggle="tooltip" data-placement="bottom" class="btn btn-info btn-icon m-r-5" onclick="saveOrUpdate();"><i class="md md-save"></i></button>
            </li>
        </ul>
    </div>
    <div class="card-body card-padding">
        <form:form role="form" id="inputForm" modelAttribute="workFlowDto" action="${ctx}/workflow/modify" method="post">
            <form:input type="hidden" id="id" path="id"/>
            <form:input type="hidden" id="isDelete" path="isDelete" />
            <div class="row">
                <div class="card-body card-padding">
                    <div class="row">
                        <div class="col-xs-10 form-group">
                            <label for="name">配置名称：</label>
                            <div class="fg-line">
                                <%--<input type="hidden" name="oldName" value="${workFlowDto.name}">--%>
                                <form:input type="text" class="form-control required" id="name" path="name" maxlength="200"/>
                            </div>
                        </div>
                        <div class="col-sm-4">
                            <label class="control-label">类型：</label>
                            <div class="form-group">
                                <div class="fg-line">
                                    <div class="select">
                                        <form:select path="flowType" class="form-control selectpicker">
                                            <form:option value="1" >政府</form:option>
                                            <form:option value="2" >企业</form:option>
                                        </form:select>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-xs-5 form-group">
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
                            <label for="name">时间类型：</label>

                            <div class="fg-line">
                                <form:select path="timeType" class="form-control selectpicker">
                                    <form:options items="${fns:getDictList('dim_type')}" itemLabel="label" itemValue="value"/>
                                </form:select>
                            </div>
                        </div>
                        <div class="col-xs-5 form-group">
                            <label for="timeLimit">配置时长（天）：</label>

                            <div class="fg-line">
                                <form:input type="number" class="form-control required" id="timeLimit" path="timeLimit" />
                            </div>
                        </div>
                        <div class="col-xs-10 form-group">
                            <label for="description">描述：</label>

                            <div class="fg-line">
                                <form:textarea id="description" path="description" class="form-control auto-size" placeholder="描述信息" maxlength="200"></form:textarea>
                            </div>
                        </div>
                    </div>
                </div>
                <%--<div class="btn-demo text-center">--%>
                    <%--<button data-toggle="modal" onclick="submit()" class="btn btn-primary waves-effect">受理</button>--%>
                    <%--<button class="btn btn-default waves-effect">返回</button>--%>
                <%--</div>--%>
            </div>
        </form:form>
    </div>
</div>

</body>
</html>