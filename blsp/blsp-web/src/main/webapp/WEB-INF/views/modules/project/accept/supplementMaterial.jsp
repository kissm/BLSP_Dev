<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>用户补交材料列表</title>
<meta name="decorator" content="default"/>
<script>
	$(document).ready(function() {

	});
	function subMaterial(id){
		swal({
			title: "你确定吗？",
			text: "接受这条材料信息",
			type: "warning",
			showCancelButton: true,
			confirmButtonColor: "#DD6B55",
			cancelButtonText: '取消',
			confirmButtonText: "是的，接受！",
			closeOnConfirm: false
		}, function(){
			$.ajax({
				url:"${ctx}/project/accpet/subMaterial?id="+id,
				dataType: "html",
				type: "POST",
				data:{id:id},
				success:function(data){
					if($("#tr"+id).length > 0){
						$("#tr"+id).remove();
					}
				}
			});
			swal("完成！", "接受成功！", "success");
		});
	}

	function cancelMaterial(id){
		swal({
			title: "你确定吗？",
			text: "拒绝这条材料信息",
			type: "warning",
			showCancelButton: true,
			confirmButtonColor: "#DD6B55",
			cancelButtonText: '取消',
			confirmButtonText: "是的，拒绝！",
			closeOnConfirm: false
		}, function(){
			$.ajax({
				url:"${ctx}/project/accpet/cancelMaterial?id="+id,
				dataType: "html",
				type: "POST",
				data:{id:id},
				success:function(data){
					if($("#tr"+id).length > 0){
						$("#tr"+id).remove();
					}
				}
			});
			swal("完成！", "拒绝成功！", "success");
		});
	}
</script>
</head>
<body>
	<div class="card">
		<div class="p-20">
			<div class="table-responsive">
				<table id="contentTable"
					class="table table-striped table-vmiddle bootgrid-table">
					<thead>
						<tr>
							<th class="col-xs-1">序号</th>
							<th class="col-xs-7">材料名称</th>
							<th class="col-xs-2">查看方式</th>
							<th class="col-xs-2">材料处理</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${suplementMaterList}" var="material" varStatus="c">
							<tr id="tr${material.id}">
								<input type="hidden" name="id" value="${material.id}"/>
								<td align="center">${c.index+1}</td>
								<td>${material.materialName}</td>
								<td>
									<c:if test="${!empty material.materialAddr}">
									<button data-toggle="tooltip" data-placement="bottom" type="button" title="下载" id="download${material.materialId}"
											onclick="window.open('${ctx}/sys/download?pathUrl=${material.materialAddr}&coi=${material.materialName}')"
											class="btn btn-icon btn-file bgm-lightblue">
										<i class="md md-file-download"></i>
									</button>
									<button data-toggle="tooltip" data-placement="bottom" type="button" title="预览" id="view${material.materialId}"
											onclick="window.open('${ctx}/sys/filePreview?pathUrl=${material.materialAddr}')"
											class="btn btn-success btn-icon btn-file">
										<i class="md md-visibility"></i>
									</button>
									</c:if>
								</td>
								<td>
									<button type="button" onclick="javascrtpt:subMaterial('${material.id}');" class="btn btn-info btn-sm">接受</button>
									<button type="button" onclick="javascrtpt:cancelMaterial('${material.id}');" class="btn bgm-deeporange btn-sm">拒绝</button>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</body>