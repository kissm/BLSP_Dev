<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>业务办理-成果领取</title>
	<meta name="decorator" content="blank"/>
	<script type="text/javascript">
		$(document).ready(function() {
		});	
			
		function ajaxGetCert(id) {
			$('#modalContent').attr('src','${ctx}/prjTask/cert/getCert?prjInsId='+id);
			$('#infoRegister').modal('show');
		};
		function iframeClose() {
			$('#infoRegister').modal('hide');
			swal({   
                title: "操作成功!",
                text: "",   
                type: "success"
            }, function(){
            	page(1);
            });
		};
	</script>
</head>
<body>
<div class="card">
	    <div class="card-header">
	        <h2>成果领取<small>您可通过本功能对成果领取信息进行登记。</small></h2>
	        <ul class="actions">
				<li>
					<button data-toggle="tooltip" data-placement="bottom" type="button" title="刷新" class="btn btn-default btn-icon m-r-5" onclick="refresh();"><i class="md md-autorenew"></i></button>
				</li>
			</ul>
	    </div>
		<form role="form" id="searchForm"  action="${ctx}/prjTask/cert/getList" method="post">
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
			<div class="card-body card-padding">
				<div class="row" id="clearQuery">
					<div class="col-sm-3 form-group">
						<div class="fg-line">
							<label class="control-label">区项目编号：</label>
							<input placeholder="区项目编号" name="prjCode" value="<c:out value="${param.prjCode}"/>" type="text" maxlength="50" class="form-control">
						</div>
					</div>
					<div class="col-sm-4 form-group">
						<div class="fg-line">
							<label class="control-label">项目名称：</label>
							<input placeholder="项目名称" name="prjName" value="<c:out value="${param.prjName}"/>" type="text"  maxlength="50" class="form-control">
						</div>
					</div>
					<div class="col-sm-4 form-group">
						<div class="fg-line">
							<label class="control-label">建设单位名称：</label>
							<input placeholder="建设单位名称" name="company" value="<c:out value="${param.company}"/>" type="text"  maxlength="50" class="form-control">
						</div>
					</div>
					<div class="col-sm-3 form-group">
						<div class="fg-line">
							<label class="control-label">领取状态：</label>
							<select name="prjFetchStatus" class="form-control selectpicker" data-live-search="true">
								<option value="0" <c:if test="${param.prjFetchStatus eq '0'}">selected</c:if>>未领取</option>
								<option value=""  <c:if test="${param.prjFetchStatus eq ''}">selected</c:if>>全部</option>
								<option value="1" <c:if test="${param.prjFetchStatus eq '1'}">selected</c:if>>已领取</option>
							</select>
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
					<th>区项目编号</th>
					<th>项目名称</th>
					<th>建设单位名称</th>
					<th>领取状态</th>
					<th>领证</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${page.list}" var="prjFetch">
					<tr>
						<td>${prjFetch.prjCode}</td>
						<td>${prjFetch.prjName}</td>
						<td>${prjFetch.company}</td>
						<td>
							<c:choose>
								<c:when test="${prjFetch.prjFetchStatus eq '1'}">
									已领取
								</c:when>
								<c:otherwise>
									未领取
								</c:otherwise>
							</c:choose>
						</td>
						<td>
							<button class="btn btn-icon btn-info" type="button"
									data-toggle="tooltip" data-placement="bottom"
									onclick="javascript:ajaxGetCert(${prjFetch.prjId})"
									title="<c:choose>
												<c:when test="${prjFetch.prjFetchStatus eq '1'}">
													查看
												</c:when>
												<c:otherwise>
													领取
												</c:otherwise>
											</c:choose>">
								<c:choose>
									<c:when test="${prjFetch.prjFetchStatus eq '1'}">
										<span class="md md-menu"></span>
									</c:when>
									<c:otherwise>
										<span class="md md-check"></span>
									</c:otherwise>
								</c:choose>
							</button>
						</td>
					</tr>
				</c:forEach>
			</tbody>
	       </table>
		</div>
		${page}
</div>

<div class="modal fade" data-modal-color="cyan" id="infoRegister" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">成果领取信息登记</h4>
				</div>
				<div style="height:400px;">
					<iframe id="modalContent" width="100%" height="100%" frameborder="0"></iframe>
				</div>
				<div class="modal-footer">
					<button id="t_confirm" type="button" class="btn btn-link">确认</button>
					<button type="button" class="btn btn-link" data-dismiss="modal" id="t_close">取消</button>
				</div>
			</div>
		</div>
	</div>

</body>
</html>