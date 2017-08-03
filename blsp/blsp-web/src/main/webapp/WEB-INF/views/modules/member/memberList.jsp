<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
  <title>会员信息查询</title>
  <meta name="decorator" content="default"/>
  <script type="text/javascript">
    $(document).ready(function() {
      $("#btnExport").click(function(){
        top.$.jBox.confirm("确认要导出用户数据吗？","系统提示",function(v,h,f){
          if(v=="ok"){
            $("#searchForm").attr("action","${ctx}/sys/user/export");
            $("#searchForm").submit();
          }
        },{buttonsFocus:1});
        top.$('.jbox-body .jbox-icon').css('top','55px');
      });
      $("#btnClean").click(function(){
        $('#searchForm').find('select,input[type=text], input[type=password], input[type=number], input[type=email], textarea').val('');
        page();
      });
    });
    function page(n,s){
      if(n) $("#pageNo").val(n);
      if(s) $("#pageSize").val(s);
      $("#searchForm").attr("action","${ctx}/member/list");
      $("#searchForm").submit();
      return false;
    }
  </script>
</head>
<body>
<div id="importBox" class="hide">
  <form id="importForm" action="${ctx}/sys/user/import" method="post" enctype="multipart/form-data"
        class="form-search" style="padding-left:20px;text-align:center;" onsubmit="loading('正在导入，请稍等...');"><br/>
    <input id="uploadFile" name="file" type="file" style="width:330px"/><br/><br/>　　
    <input id="btnImportSubmit" class="btn btn-primary" type="submit" value="   导    入   "/>
    <a href="${ctx}/sys/user/import/template">下载模板</a>
  </form>
</div>
<form:form id="searchForm" modelAttribute="memberInfoDTO" action="${ctx}/member/list" method="post" class="breadcrumb form-search ">
  <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
  <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
  <sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
  <ul class="ul-form">
    <li><label>帐号：</label><form:input path="code" htmlEscape="false" maxlength="50" class="input-medium"/></li>
    <li><label>昵称：</label><form:input path="nickname" htmlEscape="false" maxlength="50" class="input-medium"/></li>
    <li><label>手机：</label><form:input path="mobile" htmlEscape="false" maxlength="50" class="input-medium"/></li>
    <li><label>邮箱：</label><form:input path="email" htmlEscape="false" maxlength="50" class="input-medium"/></li>
    <li class="clearfix"></li>
    <li><label>姓名：</label><form:input path="name" htmlEscape="false" maxlength="50" class="input-medium"/></li>
    <li><label>状态：</label>
      <form:select path="status" class="" placeholder="请选择..">
        <option value="" selected>请选择...</option>
        <form:option value="1">已激活</form:option>
        <form:option value="0">未激活</form:option>
      </form:select>
    </li>
    <li><label>注册渠道：</label><form:input path="regsource" htmlEscape="false" maxlength="50" class="input-medium"/></li>
    <li><label>注册日期：</label><form:input path="createtime" htmlEscape="false" maxlength="50" class="input-medium Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,startDate:'%y-%M-01',alwaysUseStartDate:true});"/></li>
    <li class="clearfix"></li>
    <li class="btns">
      <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();"/>
      <input id="btnClean" class="btn btn-primary" type="button" value="清空"/>
      <!--
      <input id="" class="btn btn-default" type="button" value="导出"/>
      -->
    </li>
  </ul>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
  <thead><tr>
    <th class="sort-column code">帐号</th>
    <th class="sort-column name">姓名</th>
    <th class="sort-column nickname">昵称</th>
    <th class="sort-column mobile">手机</th>
    <th class="sort-column email">邮箱</th>
    <th class="sort-column status">状态</th>
    <th class="sort-column regsource">注册渠道</th>
    <th class="sort-column createtime">注册时间</th>
    <shiro:hasPermission name="sys:user:edit"><th>操作</th></shiro:hasPermission>
  </tr></thead>
  <tbody>
  <c:forEach items="${page.list}" var="user">
    <tr>
      <td>${user.code}</td>
      <td>${user.name}</td>
      <td>${user.nickname}</td>
      <td>${user.mobile}</td>
      <td>${user.email}</td>
      <td>
        <c:choose>
          <c:when test="${user.status==1}"><span class="label label-success">已激活</span></c:when>
          <c:otherwise><span class="label label-default">未激活</span></c:otherwise>
        </c:choose>
      </td>
      <td>${user.regsource}</td>
      <td>
        <fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${user.createtime}" />
      </td>
      <shiro:hasPermission name="sys:user:edit">
        <td>
          <a href="${ctx}/member/form?id=${user.mid}">查看</a>
        </td>
      </shiro:hasPermission>
    </tr>
  </c:forEach>
  </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>