<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta name="decorator" content="blank" />
		<title>项目列表</title>
		<script type="text/javascript">
			$(document).ready(function() {
				$('#confirm').click(function() {
					$('#modalWider').modal('hide');
					ajaxSave();
					//$('#notice').modal('show');
				});
				$('#saveMater').click(function() {
					$('#material').modal('hide');
					domaterial();
					//$('#notice').modal('show');
				});
			});
			function ajaxAccept(id) {
				$("#prjId").val(id);
				$.ajax({
					type : 'post',
					url : '${ctx}/project/validate',
					data : {"prjInstanceVo.id":id},
					cache : false,
					dataType : 'html',
					success : function(data) {
						$("#temporary").html(data);
						$("#accept").html("");
						$("#success").html("");
						if ($("#accept_").length > 0) {
							$("#accept").html($("#temporary").html());
							$('#notice').modal('hide');
							$('.selectpicker').selectpicker();
							$('#modalWider').modal('show');
							return;
						}
						if ($("#success_").length > 0) {
							$("#success").html($("#temporary").html());
							$('#modalWider').modal('hide');
							$('#notice').modal('show');
							return;
						}
					}
				});
			}
			function mater(id) {
				$('#material').modal('show');
				$.ajax({
					type : 'post',
					url : '${ctx}/project/material',
					data : {"prjInstanceVo.id":id},
					cache : false,
					dataType : 'html',
					success : function(data) {
						$("#mater").html(data);
					}
				});
			}
			
			function ajaxSave(id) {
				var formParam = $("#form1").serialize();//序列化表格内容为字符串
				$.ajax({
					type : 'post',
					url :'${ctx}/project/task',
					data : formParam,
					cache : false,
					dataType : 'html',
					success : function(data) {
						$("#temporary").html(data);
						if($("#success_").length>0){
							$("#success").html($("#temporary").html());
							$('#notice').modal('show');
							return;
						}
					}
				});
			}
			function domaterial() {
				var formParam = $("#form1").serialize();//序列化表格内容为字符串
				$.ajax({
					type : 'post',
					url :'${ctx}/project/doMaterial',
					data : formParam,
					cache : false,
					dataType : 'html',
					success : function(data) {
						$("#temporary").html(data);
						if($("#success_").length>0){
							$("#success").html($("#temporary").html());
							$('#notice').modal('show');
							return;
						}
					}
				});
			}
			
			function page(n, s) {
				$("#pageNo").val(n);
				$("#pageSize").val(s);
				$("#wsbsUserName").val(parent.blspObj.token);
				$("#searchForm").submit();
				return false;
			}
			function goPage(){
				var pageIndex = $("#getPageIndex").val();
				var pageNo = '${pageNo}';
				var pageSize = '${pageSize}';
				var biggest = '<fmt:formatNumber type="number" value="${(page.count-page.count%page.pageSize)/page.pageSize+(page.count%page.pageSize == 0 ? 0 : 1)}"/>';
				if(pageIndex == pageNo){
					alert("已是当前页面！");
				}
				if(pageIndex >= 1 && eval(biggest) >= eval(pageIndex)){
					page(pageIndex, pageSize);
				}else{
					alert("错误页码！");
				}
			}
			function checkReason(pauseDesc){
				alert(pauseDesc);
			}
			function progress(prjCode){
				window.location.href = "${ctx}/schedule/progressTask?prjCode=" + prjCode;
			}
		</script>
	</head>
	<body>
		<div class="card">
			<form id="searchForm" role="form" action="${ctx}/project/list/" method="post">
				<input name="pageNo" id="pageNo" type="hidden" value="${pageNo}">
				<input name="pageSize" id="pageSize" type="hidden" value="${pageSize}">
				<input name="prjInstanceVo.wsbsUserName" id="wsbsUserName" type="hidden" />
				<input name="prjInstanceVo.prjType" type="hidden" />
				<div class="card-body card-padding">
					<sys:message content="${message}">
					</sys:message>
				</div>
			</form>
			<div class="table-responsive" >
				<table class="editTable" style="width: 960px" border="0" cellspacing="0" cellpadding="0">
					<thead>
						<tr>
							<th class="title" style="width: 20%;font-weight: bolder;height: 30px;">项目名称</th>
							<th class="title" style="width: 20%;font-weight: bolder;">项目编号</th>
							<th class="title" style="width: 20%;font-weight: bolder;">申报日期</th>
							<th class="title" style="width: 10%;font-weight: bolder;">阶段</th>
							<th class="title" style="width: 10%;font-weight: bolder;">状态</th>
							<th class="title" style="font-weight: bolder;">
								操作
							</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${page.list}" var="project">
							<tr>
								<td align="center" style="height: 30px;">
									<a href="${ctx}/project/wsbsview?id=${project.prjInstanceVo.id}" title="查看项目" style="font-size: 14px;">${project.prjInstanceVo.prjName}</a>
								</td>
								<td align="center">
									${project.prjInstanceVo.prjCode}
								</td>
								<td align="center">
									<fmt:formatDate value="${project.prjInstanceVo.creatTime}" pattern="yyyy-MM-dd" />
								</td>
								<td align="center">
									${project.prjStageDefineVo.stageName}
								</td>
								<td align="center">
									<c:if test="${project.prjInstanceVo.isStageComplete eq '1' && project.prjInstanceVo.isPrjComplete eq '1'}">
										完成
									</c:if>
									<c:if test="${project.prjInstanceVo.isStageComplete eq '1' && project.prjInstanceVo.isPrjComplete eq '0'}">
										受理中
									</c:if>
									<c:if test="${project.prjInstanceVo.isPrjComplete eq '9'}">
										终止
									</c:if>
									<c:if test="${project.prjInstanceVo.isStageComplete eq '0' && project.prjInstanceVo.isPrjComplete ne '9'}">
										<c:if test="${project.prjInstanceVo.applyState eq '0'}">
											草稿暂存
										</c:if>
										<c:if test="${project.prjInstanceVo.applyState eq '1'}">
											提交审核
										</c:if>
										<c:if test="${project.prjInstanceVo.applyState eq '2'}">
											受理中
										</c:if>
										<c:if test="${project.prjInstanceVo.applyState eq '3'}">
											审核驳回
										</c:if>
									</c:if>
								</td>
								<td align="center" style="padding: 0 15px;">
									<c:if test="${ project.prjInstanceVo.isPrjComplete ne '9'}">
										<c:if test="${project.prjInstanceVo.prjType == 1}">
											<c:if test="${project.prjInstanceVo.applyState eq '0'}">
												<div class="uploadify-button" onclick="javascript:window.location.href='${ctx}/project/accpet/basic?projectId=${project.prjInstanceVo.id}'" style="height: 30px; line-height: 30px; width: 80px; border: 0px; border-radius: 2px;background-image: none; background-color: rgb(61, 158, 233);">
													<span class="uploadify-button-text">编辑</span>
												</div>
											</c:if>
											<c:if test="${project.prjInstanceVo.applyState eq '1'}">
												
											</c:if>
											<c:if test="${project.prjInstanceVo.applyState eq '2'}">
												<div class="uploadify-button" onclick="progress('${project.prjInstanceVo.prjCode}')" style="height: 30px; line-height: 30px; width: 80px; border: 0px; border-radius: 2px;background-image: none; background-color: rgb(61, 158, 233);float: left;">
													<span class="uploadify-button-text">受理进度</span>
												</div>
												<div class="uploadify-button" onclick="window.location.href='${ctx}/polish/polishMaterial?id=${project.prjInstanceVo.id}'" style="height: 30px; line-height: 30px; width: 80px; border: 0px; border-radius: 2px;background-image: none; background-color: rgb(61, 158, 233);float: right;">
													<span class="uploadify-button-text">补齐材料</span>
												</div>
											</c:if>
											<c:if test="${project.prjInstanceVo.applyState eq '3'}">
												<div class="uploadify-button" onclick="checkReason('${project.prjInstanceVo.pauseDesc}')" style="height: 30px; line-height: 30px; width: 80px; border: 0px; border-radius: 2px;background-image: none; background-color: rgb(61, 158, 233);">
													<span class="uploadify-button-text">退回原因</span>
												</div>
											</c:if>
										</c:if>
										<c:if test="${project.prjInstanceVo.prjType != 1}">
											<c:if test="${project.prjInstanceVo.applyState eq '0'}">
												<div class="uploadify-button" onclick="javascript:window.location.href='${ctx}/project/bizaccept/basic?projectId=${project.prjInstanceVo.id}'" style="height: 30px; line-height: 30px; width: 80px; border: 0px; border-radius: 2px;background-image: none; background-color: rgb(61, 158, 233);">
													<span class="uploadify-button-text">编辑</span>
												</div>
											</c:if>
											<c:if test="${project.prjInstanceVo.applyState eq '1'}">
												
											</c:if>
											<c:if test="${project.prjInstanceVo.applyState eq '2'}">
												<div class="uploadify-button" onclick="progress('${project.prjInstanceVo.prjCode}')" style="height: 30px; line-height: 30px; width: 80px; border: 0px; border-radius: 2px;background-image: none; background-color: rgb(61, 158, 233);float: left;">
													<span class="uploadify-button-text">受理进度</span>
												</div>
												<div class="uploadify-button" onclick="window.location.href='${ctx}/polish/polishMaterial?id=${project.prjInstanceVo.id}'" style="height: 30px; line-height: 30px; width: 80px; border: 0px; border-radius: 2px;background-image: none; background-color: rgb(61, 158, 233);float: right;">
													<span class="uploadify-button-text">补齐材料</span>
												</div>
											</c:if>
											<c:if test="${project.prjInstanceVo.applyState eq '3'}">
												<div class="uploadify-button" onclick="checkReason('${project.prjInstanceVo.pauseDesc}')" style="height: 30px; line-height: 30px; width: 80px; border: 0px; border-radius: 2px;background-image: none; background-color: rgb(61, 158, 233);">
													<span class="uploadify-button-text">退回原因</span>
												</div>
											</c:if>
										</c:if>
									</c:if>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<table class="editTable" style="width: 960px" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td style="text-align: center;height: 30px;">
						第${page.pageNo}/<fmt:formatNumber type="number" value="${(page.count-page.count%page.pageSize)/page.pageSize+(page.count%page.pageSize == 0 ? 0 : 1)}"/>页
						<c:if test="${page.firstPage}">
							&nbsp;&nbsp;&nbsp;&nbsp;首页&nbsp;&nbsp;上一页
						</c:if>
						<c:if test="${!page.firstPage}">
							&nbsp;&nbsp;&nbsp;&nbsp;<a onclick="page(${page.first},10)" href="javascript:;">【首页】</a>&nbsp;&nbsp;<a onclick="page(${page.prev},10)" href="javascript:;">【上一页】</a>
						</c:if>
						<c:if test="${page.lastPage}">
							&nbsp;&nbsp;下一页&nbsp;&nbsp;尾页
						</c:if>
						<c:if test="${!page.lastPage}">
							&nbsp;&nbsp;<a onclick="page(${page.next},10)" href="javascript:;">【下一页】</a>&nbsp;&nbsp;<a onclick="page(${page.last},10)" href="javascript:;">【尾页】</a>
						</c:if>
						&nbsp;&nbsp;转到第<input type="text" style="width:20px;border-bottom: solid 1px #555555;border-top: 0px;border-left: 0px;border-right: 0px;" id="getPageIndex" />页&nbsp;&nbsp;
						<a onclick="goPage()" href="javascript:;">GO<a/>&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;共${page.count}条&nbsp;&nbsp;&nbsp;&nbsp;
					</td>
				</tr>
			</table>
		</div>
		<div id="temporary" style="display:none"></div>
	</body>
</html>