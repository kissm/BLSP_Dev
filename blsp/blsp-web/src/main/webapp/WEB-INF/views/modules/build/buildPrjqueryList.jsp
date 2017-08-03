<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>${project.prjInstanceVo.company}</title>
    <meta name="decorator" content="blank"/>
    <script type="text/javascript">
        function page(n, s) {
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }

        function ajaxGetPauseTask(prjInsId) {
            $('#modalContent').attr('src', '${ctx}/build/pauseTask/form?prjInsId=' + prjInsId);
            $('#taskForm').modal('show');
        }
        ;
        function iframeClose() {
            $('#taskForm').modal('hide');
            swal({
                title: "操作成功!",
                text: "",
                type: "success"
            }, function () {
                page(1);
            });
        }
        ;
        
    </script>
</head>
<body>
<div class="card">
    <div class="card-header">
        <h2>${project.prjInstanceVo.company}<small>该建设单位的所有项目</small></h2>
        <ul class="actions">
            <li>
                <button title="返回" data-toggle="tooltip" data-placement="bottom" class="btn btn-success btn-icon m-r-5" onclick="javascrtpt:history.go(-1)"><i class="md md-arrow-back"></i></button>
            </li>
            <li>
                <button data-toggle="tooltip" data-placement="bottom" type="button" title="刷新"
                        class="btn btn-default btn-icon m-r-5" onclick="refresh();"><i class="md md-autorenew"></i>
                </button>
            </li>
        </ul>
    </div>
    <form id="searchForm" role="form" action="${ctx}/build/buildPrjqueryList/"
          method="post">
        <input name="pageNo" id="pageNo" type="hidden" value="${pageNo}">
        <input name="pageSize" id="pageSize" type="hidden" value="${pageSize}">
        <input name="prjInstanceVo.prjType" type="hidden" value="3">
        <input name="prjInstanceVo.companyCode" type="hidden" value="${project.prjInstanceVo.companyCode}">
        <input name="prjInstanceVo.company" type="hidden" value="${project.prjInstanceVo.company}">

        <div class="card-body card-padding">
            <div class="row" id="clearQuery">
                <div class="col-sm-2 form-group">
                    <div class="fg-line">
                        <label>区项目编号：</label> <input name="prjInstanceVo.prjCode"
                                                    class="form-control" value="${project.prjInstanceVo.prjCode}"
                                                    type="text" maxlength="50" placeholder="区项目编号">
                    </div>
                </div>
                <div class="col-sm-2 form-group">
                    <div class="fg-line">
                        <label>项目名称：</label> <input name="prjInstanceVo.prjName"
                                                    class="form-control" value="${project.prjInstanceVo.prjName}"
                                                    type="text" maxlength="50" placeholder="项目名称">
                    </div>
                </div>
                <div class="col-sm-2 form-group">
                    <div class="fg-line">
                        <label>申请开始时间：</label> <input id="b" type="text" class="form-control date-picker"
                                                      value='<fmt:formatDate value="${project.prjInstanceVo.startTime}" pattern="yyyy-MM-dd" />'
                                                      data-toggle="dropdown" name="prjInstanceVo.startTime"
                                                      placeholder="单击此处..." aria-expanded="false">
                    </div>
                </div>
                <div class="col-sm-2 form-group">
                    <div class="fg-line">
                        <label>申请结束时间：</label><input id="e" type="text" class="form-control date-picker"
                                                     value='<fmt:formatDate value="${project.prjInstanceVo.endTime}" pattern="yyyy-MM-dd" />'
                                                     data-toggle="dropdown" name="prjInstanceVo.endTime"
                                                     placeholder="单击此处..." aria-expanded="false">
                    </div>
                </div>
                <div class="col-sm-2">
                    <label></label>
                    <button class="btn btn-primary waves-effect form-control" id="btnSubmit" type="submit">查询</button>
                </div>
                <div class="col-sm-2">
                    <label></label>
                    <button type="button" class="btn btn-primary waves-effect form-control" onclick="clearQuery();">重置
                    </button>
                </div>
            </div>
            <sys:message content="${message}">
            </sys:message>
        </div>
    </form>

    <div class="table-responsive">
        <table class="table table-striped table-vmiddle bootgrid-table"
               id="contentTable">
            <thead>
            <tr>
                <th class="text-center col-xs-2">区项目编号</th>
                <th class="text-center col-xs-4">项目名称</th>
                <th class="text-center col-xs-1">返回事项数</th>
                <th class="text-center">阶段</th>
                <th class="text-center">申报日期</th>
                <th class="text-center">状态</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${page.list}" var="project">
                <tr>
                    <td class="text-center">${project.prjInstanceVo.prjCode}</td>
                    <td class="text-center">
                        <c:if test="${project.prjInstanceVo.pauseTimesForMater > 0}" >
                            <a href="javascript:void(0)" onclick="ajaxGetPauseTask(${project.prjInstanceVo.id})" title="查看补材料事项列表">
                                ${project.prjInstanceVo.prjName}
                            </a>
                        </c:if>
                        <c:if test="${project.prjInstanceVo.pauseTimesForMater == 0}">
                                ${project.prjInstanceVo.prjName}
                        </c:if>
                    </td>
                    <td class="text-center">${project.prjInstanceVo.pauseTimesForMater}</td>
                    <td class="text-center">${project.prjStageDefineVo.stageName}</td>
                    <td class="text-center"><fmt:formatDate value="${project.prjInstanceVo.creatTime}" pattern="yyyy-MM-dd"/></td>
                    <td class="text-center">${project.prjStageVo.stageStatus eq '0' ?"待资料补齐":(project.prjStageVo.stageStatus eq '4'?"办结":"审批中")}
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    ${page}
</div>


<div class="modal fade" data-modal-color="cyan" id="taskForm" data-backdrop="static" data-keyboard="false" tabindex="-1"
     role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">补材料事项列表</h4>
            </div>
            <div style="height:400px;">
                <iframe id="modalContent" width="100%" height="100%" frameborder="0"></iframe>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-link" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>



</body>
</html>