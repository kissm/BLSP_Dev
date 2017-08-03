<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>事项办结</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        if ('${message}' == 'ok') {
            parent.iframeClose();
        }
        $(document).ready(function () {
            $('input[type=checkbox]').click(function () {
                if ($(this).is(':checked')) {
                    $('#' + $(this).val()).attr("disabled", false);
                } else {
                    $('#' + $(this).val()).attr("disabled", true);
                }
                // 刷新控件
                $('#' + $(this).val()).selectpicker('refresh');
            })

        });
        function processTask(){
            if ($(":checkbox:checked").size() < $("#countNum").val()) {
                swal("请为所有需要处理事项选择办结类型", "", "error");
            } else {
                swal({
                    title: "确定处理选中的未办事项吗？",
                    text: "",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "#DD6B55",
                    cancelButtonText: '取消',
                    confirmButtonText: "确认"
                }, function () {
                    var stageList = [];
                    $("input[name='stageId']").each(function(){
                        var stage = {};
                        var taskProcList = [];
                        var stageId = $(this).val();
                        $("select[name='finishType_"+stageId+"']").each(function(){
                            var task ={};
                            task["taskId"] = $(this).attr('id');
                            task["status"] = $(this).val();
                            taskProcList.push(task);
                        })
                        stage["stageId"] = stageId;
                        stage["taskProcList"] = taskProcList;
                        stageList.push(stage);
                    });
                    closeWindow(stageList);
                });
            }
        }
    </script>
</head>
<body>
<form id="offLineFinishForm" action="${ctx}/enterprise/project/offLine/finish/save" method="post">
    <div>
        <button type="button" style="top: 5px;left: 20px;" class="col-sm-2 btn bgm-lightgreen btn-sm" data-toggle="modal" onclick="processTask()">处理</button>&nbsp;&nbsp;
    </div>
    <div class="p-20">
        <c:set var="count" value="0"  ></c:set>
        <div class="table-responsive">
            <c:if test="${not empty stageList}">
                <c:forEach items="${stageList}" var="stage">
                    <div class="stageIdForEach">
                        <div class="card">
                            <div class="card-header">
                                <input type="hidden" name="stageId" id="${stage.id}" value="${stage.id}">
                                <h2>${stage.stageName}</h2>
                            </div>
                        </div>
                        <table id="contentTable" class="table table-striped  table-vmiddle">
                            <thead>
                            <tr>
                                <th></th>
                                <th class="col-xs-2">办结类型</th>
                                <th class="col-xs-6">事项名称</th>
                                <th class="col-xs-4">部门</th>
                            </tr>
                            </thead>
                            <tbody id="tbody_cert">
                            <c:forEach items="${stage.taskDefList}" var="task">
                                <c:set var="count" value="${count + 1 }"></c:set>
                                <tr>
                                    <td>
                                        <label class="checkbox checkbox-inline"><input type="checkbox" name="prjTaskInstIds" value="${task.id}"><i class="input-helper"></i></label>
                                    </td>

                                    <td>
                                            <%--<select name="finishType" id="type_${task.id}" disabled class="form-control selectpicker taskProcess" data-live-search="false">--%>
                                        <select name="finishType_${stage.id}" id="${task.id}" disabled class="form-control selectpicker taskProcess" data-live-search="false">
                                            <option value="7">已完成</option>
                                            <option value="8">免办</option>
                                        </select>
                                    </td>
                                    <td><font class="c-red">*</font>${task.taskName}</td>
                                    <td>${offices[task.deptId]}</td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:forEach>
            </c:if>
            <c:if test="${empty stageList}">
                <i>暂无未办事项</i>
            </c:if>
            <input type="hidden" name="countNum" id="countNum" value="${count}">
        </div>
        <div>
            <button type="button" style="top: 10px;"class="col-sm-2 btn bgm-lightgreen btn-sm" data-toggle="modal" onclick="processTask()">处理</button>&nbsp;&nbsp;
        </div>
    </div>

</form>
</body>
</html>