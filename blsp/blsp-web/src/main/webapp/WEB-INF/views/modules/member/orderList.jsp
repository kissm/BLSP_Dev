<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
  <title>会员订单信息查询</title>
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
      $("#searchForm").attr("action","${ctx}/member/orderlist?mid=${orderDTO.mid}");
      $("#searchForm").submit();
      return false;
    }
  </script>
</head>
<body>
<div class="tabbable">
  <ul class="nav nav-tabs">
    <li><a href="${ctx}/member/form?id=${memberInfoDTO.mid}">会员基本信息</a></li>
    <li class="active"><a href="${ctx}/member/orderlist?mid=${memberInfoDTO.mid}">客户订单</a></li>
    <!--
    <li><a href="#favorate" data-toggle="tab">收藏夹</a></li>
    <li><a href="#coupons" data-toggle="tab">优惠券</a></li>-->
    <li><a href="${ctx}/member/linkman?id=${memberInfoDTO.mid}">联系人</a></li>
    <li><a href="${ctx}/member/oftenpassenger?id=${memberInfoDTO.mid}">常旅客</a></li>
    <!--<li><a href="#address" data-toggle="tab">邮寄地址</a></li>
    <li><a href="#logs" data-toggle="tab">客户日志</a></li>-->
    <li class="pull-right">
      <input onclick="return function(){location.href = '${ctx}/member/list';}();" id="btnCancel" class="btn btn-primary" type="button" value="返 回"/>
    </li>
  </ul><br/>
  <div class="tab-content">
    <div id="member" class="tab-pane">
    </div>
    <div id="order" class="tab-pane active">
      <form:form id="searchForm" modelAttribute="orderDTO" action="${ctx}/member/orderlist?mid=${orderDTO.mid}" method="post" class="breadcrumb form-search ">
        <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
        <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
        <sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
        <ul class="ul-form">
          <li><label>订单号：</label><form:input path="orderNo" htmlEscape="false" maxlength="50" class="input-medium"/></li>
          <li><label>产品编号：</label><form:input path="productCodeExt" htmlEscape="false" maxlength="50" class="input-medium"/></li>
          <li><label>状态：</label>
            <form:select path="orderStatus" htmlEscape="false" maxlength="50" class="input-medium">
              <form:option value="">--请选择--</form:option>
              <form:option value="0001">待支付</form:option>
              <form:option value="0005">已付款</form:option>
              <form:option value="0010">已确认</form:option>
              <form:option value="0015">发货中</form:option>
              <form:option value="0020">已收货</form:option>
              <form:option value="0100">已完成</form:option>
              <form:option value="0500">已关闭</form:option>
              <form:option value="0800">取消中</form:option>
            </form:select>
          </li>

          <li class="btns">
          <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();"/>
          <input id="btnClean" class="btn btn-primary" type="button" value="清空"/>
          </li>
          <li class="clearfix"></li>
        </ul>
      </form:form>
      <sys:message content="${message}"/>
      <table id="contentTable" class="table table-striped table-bordered table-condensed">
        <thead><tr>
          <th>订单号</th>
          <th>产品编号</th>
          <th>产品</th>
          <th>价格</th>
          <th>可否取消</th>
          <th>状态</th>
          <th>下单时间</th>
        </tr></thead>
        <tbody>
        <c:forEach items="${page.list}" var="order">
          <tr>
            <td>${order.orderNo}</td>
            <td>${order.productCodeExt}</td>
            <td>${order.productName}</td>
            <td>${order.price}</td>
            <td>
              <c:choose>
                <c:when test="${order.cancellable==1}"><span class="label label-success">可取消</span></c:when>
                <c:otherwise><span class="label label-default">不可取消</span></c:otherwise>
              </c:choose>
            </td>
            <td>
              <c:choose>
                <c:when test="${'0001'==order.orderStatus}"><span class="label label-warning">待支付</span></c:when>
                <c:when test="${'0005'==order.orderStatus}"><span class="label label-success">已付款</span></c:when>
                <c:when test="${'0010'==order.orderStatus}"><span class="label label-success">已确认</span></c:when>
                <c:when test="${'0015'==order.orderStatus}"><span class="label label-success">发货中</span></c:when>
                <c:when test="${'0020'==order.orderStatus}"><span class="label label-success">已收货</span></c:when>
                <c:when test="${'0100'==order.orderStatus}"><span class="label label-success">已完成</span></c:when>
                <c:when test="${'0500'==order.orderStatus}"><span class="label label-default">已关闭</span></c:when>
                <c:when test="${'0800'==order.orderStatus}"><span class="label label-warning">取消中</span></c:when>
              </c:choose>
            </td>
            <td>
                ${order.orderTime}
            </td>
          </tr>
        </c:forEach>
        </tbody>
      </table>
      <div class="pagination">${page}</div>
    </div>
    <div id="favorate" class="tab-pane"></div>
    <div id="coupons" class="tab-pane"></div>
    <div id="linkmans" class="tab-pane"></div>
    <div id="oftenpass" class="tab-pane"></div>
    <div id="address" class="tab-pane"></div>
    <div id="logs" class="tab-pane"></div>
  </div>
</div>
</body>
</html>