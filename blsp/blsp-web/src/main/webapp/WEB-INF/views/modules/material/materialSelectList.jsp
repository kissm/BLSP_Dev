<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>申报材料维护</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		var jeesiteMap = new JeesiteMap();
		$(document).ready(function() {
			//sessionStorage.setItem(key, value);
			var jeesiteMapJson = sessionStorage.getItem("jeesiteMap");
			if(jeesiteMapJson !=null && jeesiteMapJson !=""){
				var materials = JSON.parse(jeesiteMapJson);
				for(var index in materials){
					jeesiteMap.put(materials[index].id,materials[index].value);
					$("#"+materials[index].id).attr('checked',"checked");
				}
				if($(".result :checkbox").size() != 0 && $(".result :checkbox").size() == $(".result :checkbox[checked='checked']").size()){
					$("#selectallbtn").prop("checked",true);
				}
			}
		});
		
		
		var jeesiteCancelMap = new JeesiteMap();
		function addMaterial(id,name){
			if($("#"+id).is(':checked')){
				jeesiteMap.put(id,name);
				jeesiteCancelMap.removeById(id);
			}else{
				jeesiteMap.removeById(id);
				jeesiteCancelMap.put(id,name);
			}
			sessionStorage.setItem("jeesiteMap", JSON.stringify(jeesiteMap.toObject()));
			sessionStorage.setItem("jeesiteCancelMap", JSON.stringify(jeesiteCancelMap.toObject()));
		}
		function selectAll(obj){
			if($(obj).is(":checked")){
				$(".result :checkbox").each(function(){
					$(this).prop("checked",true);
				});
			}else{
				$(".result :checkbox").each(function(){
					$(this).removeAttr("checked");
				});
			}
			
			$(".result :checkbox").each(function(){
				addMaterial($(this).attr("id"),$(this).attr("name"));
			});
			
		}
	</script>
</head>
<body>
<div class="p-20">
    <form:form role="form" id="searchForm"  modelAttribute="materialDto" action="${ctx}/material/list?isValid=1&mode=select" method="post">
        <form:input id="pageNo" path="pageNo" type="hidden"/>
        <form:input id="pageSize" path="pageSize" type="hidden"/>
        <div class="card-body card-padding">
            <div class="row">
                <div class="col-sm-2 form-group">
					<div class="fg-line">
						<label class="control-label">材料ID：</label>
						<form:input placeholder="材料ID" type="text" path="id"  maxlength="50" class="form-control"/>
					</div>
                </div>
				<div class="col-sm-6 form-group">
					<div class="fg-line">
                        <label class="control-label">材料名称：</label>
                        <form:input placeholder="材料名称" type="text" path="name"  maxlength="50" class="form-control"/>
                    </div>
                </div>
                <div class="col-sm-2">
                    <label class="control-label"></label>
                    <button id="btnSubmit" type="submit" onclick="page(1)" class="btn btn-primary waves-effect form-control">查询</button>
                </div>
            </div>
            <sys:message content="${message}"/>
        </div>
    </form:form>
    <div class="table-responsive">
        <table id="contentTable" class="table table-striped table-vmiddle bootgrid-table">
            <thead>
            <tr>
             	<th class="text-center col-xs-1">
             		<label class="checkbox checkbox-inline" >
						<input id="selectallbtn" type="checkbox" onclick="selectAll(this);"/>
						<i class="input-helper"></i>
					</label>
             	</th>
                <th class="text-center">ID</th>
                <th class="text-center col-sm-4">材料名称</th>
                <th class="text-center">创建时间</th>
                <th class="text-center">更新时间</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${page.getList()}" var="material">
                <tr class="result">
                	<td class="text-center">
						<label class="checkbox checkbox-inline" onclick="addMaterial('${material.id}','${material.name}');">
							<input id="${material.id}" type="checkbox" name="${material.name}">
							<i class="input-helper"></i>
						</label>
					</td>
                    <td class="text-center">${material.id}</td>
                    <td>${material.name}</td>
                    <td class="text-center"><fmt:formatDate value="${material.creatTime}" type="both" pattern="yyyy-MM-dd"/></td>
                    <td class="text-center"><fmt:formatDate value="${material.updateTime}" type="both" pattern="yyyy-MM-dd"/></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    ${page}
</div>
</body>
</html>