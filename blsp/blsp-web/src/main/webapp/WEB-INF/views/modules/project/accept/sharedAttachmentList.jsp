<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>共享材料库</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
function ok(){
	if($("input[name='Ids']:checked").length > 0){
		swal({
			title : "复用材料会覆盖现有材料，确定要复用吗？",
			type : "warning",
			showCancelButton : true,
			confirmButtonColor : "#DD6B55",
			cancelButtonText : '取消',
			confirmButtonText : "是的，确定！",
			closeOnConfirm : false
		}, function() {
			var data = [];
			$("input[name='Ids']:checked").each(function(){
				data.push(JSON.parse($(this).val()));
			});
			closeWindow(data);
		});
	}else{
		swal({   
            title: "您未选择材料，请选择材料后重试！",   
            text: "",
            type: "warning", 
            timer: 1000,   
            showConfirmButton: false
        });
	}
}
</script>
</head>
<body>
	<div class="panel panel-collapse">
		<div id="collapseThree2" class="collapse in" role="tabpanel" aria-labelledby="headingThree2">
			<div class="table-responsive">
		         <table id="contentTable" class="table table-striped table-vmiddle bootgrid-table">
					 <thead>
						<tr>
			             	<th class="col-xs-1" style="padding-left:18px;"><label>选择</label></th>
							<th class="col-xs-7"><label>材料名称</label></th>
							<th class="col-xs-4"><label>电子文件</label></th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${listMaterial}" var="materialDto">
							<tr>
								<input type="hidden" name="docId" value="${materialDto.id}" />
								<input type="hidden" name="docName" value="${materialDto.materName}" />
								<td class="text-center">
									<label class="checkbox checkbox-inline">
										<input name="Ids" type="checkbox" value='${fns:toJson(materialDto)}'>
										<i class="input-helper"></i>
									</label>
								</td>
								<td>${materialDto.materName}</td>
								<td>
									<sys:file id="file${materialDto.id}" uploadFlag="false" isPreview="true" fileName="fileName"
											  fileAddress="fileUrl" cssClass="btn-info" downloadFileName="${materialDto.materName}"
											downloadFileAddress="${materialDto.materialAddr}" >
									</sys:file>
								</td>
							</tr>
						</c:forEach>
					</tbody>
			       </table>
			</div>
		</div>
	</div>
</body>
</html>