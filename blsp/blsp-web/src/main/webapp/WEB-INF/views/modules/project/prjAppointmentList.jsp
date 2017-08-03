<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>预约列表</title>
	<meta name="decorator" content="blank"/>
	<script type="text/javascript">
		$(document).ready(function() {
		});	
			
	</script>
</head>
<body>
<div class="card">
	    <div class="card-header">
	        <h2>预约列表 <small>预约列表。</small></h2>
	        <ul class="actions">
				<li>
					<button data-toggle="tooltip" data-placement="bottom" type="button" title="刷新" class="btn btn-default btn-icon m-r-5" onclick="refresh();"><i class="md md-autorenew"></i></button>
				</li>
			</ul>
	    </div>
		<form role="form" id="searchForm"  action="${ctx}/prj/appointment/list" method="post">
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
			<div class="card-body card-padding">
				<div class="row" id="clearQuery">
					<div class="col-sm-2 form-group">
						<div class="fg-line">
							<label class="control-label">证件类型：</label>
							<select name="certType" class="form-control selectpicker" data-live-search="false">
								<option value="1" selected>身份证</option>
							</select>							
						</div>
					</div>
					<div class="col-sm-3 form-group">
						<div class="fg-line">
							<label class="control-label">证件号码：</label>
							<input placeholder="证件号码" name="certCode" value="${param.certCode}" type="text"  maxlength="50" class="form-control">
						</div>
					</div>
					<div class="col-sm-2 form-group">
						<label class="control-label"></label>
						<button id="btnSubmit" type="submit" onclick="page(1);" class="btn btn-primary waves-effect form-control">查询</button>
					</div>
					<div class="col-sm-2 form-group">
						<label class="control-label"></label>
						<button id="btnReset" type="button" class="btn btn-primary waves-effect form-control" onclick="clearQuery();">重置</button>
					</div>
				</div>
				<sys:message content="${message}"/>
			</div>
		</form>
		<div class="table-responsive">
	         <table id="contentTable" class="table table-striped table-vmiddle bootgrid-table">
			 <thead>
				<tr>
					<th>证件类型</th>
					<th>证件号码</th>
					<th>预约日期</th>
					<th>预约流水号</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${page.list}" var="app">
					<tr>
						<td>
							<c:if test="${app.certType eq 1}">身份证</c:if>
							<c:if test="${app.certType ne 1}">其他证件</c:if>
						</td>
						<td>${app.certCode}</td>
						<td><fmt:formatDate value="${app.appointDate}" pattern="yyyy-MM-dd"/></td>
						<td>${app.appintSeq}</td>
						<td>
							<button class="btn btn-icon btn-info" title="" data-toggle="tooltip" data-placement="bottom" type="button"
								onclick="javascript:void(0)">
								<span class="md md-check"></span>
							</button>
						</td>
					</tr>
				</c:forEach>
			</tbody>
	       </table>
		</div>
		${page}
</div>
</body>
</html>