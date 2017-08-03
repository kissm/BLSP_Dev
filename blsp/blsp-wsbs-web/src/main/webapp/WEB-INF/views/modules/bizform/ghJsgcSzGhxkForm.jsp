<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>珠海市建设工程（市政类）规划许可申请表</title>
    <meta name="decorator" content="blank" />
    <style>
		.editTable input[type='text'].has-error {
			background: #F44336;
			color: #FFF;
		}
	</style>
    <script>
    	$(document).ready(function(){
	    	$("#ghJsgcSzGhxkForm").validate({
				rules: {
					'entityIdcode':{card:true}
				},
				submitHandler: function(form){
						form.submit();
				},
				errorPlacement: function(label, element){
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						label.addClass('m-l-20');
						label.appendTo(element.parent());
					} else {
						label.addClass('help-block');
						if(element.parents('.input-group').size()>0){
							label.insertAfter(element.parents('.input-group'));
						}else{
							label.insertAfter(element.parent('.fg-line'));
						}
					}
					element.attr('title',label.html());
				},
				highlight: function( element, errorClass, validClass ) {
					$(element).parents('.form-group').addClass(errorClass).removeClass(validClass);
					$(element).addClass(errorClass).removeClass(validClass);
				},
				unhighlight: function( element, errorClass, validClass ) {
					$(element).parents('.form-group').removeClass(errorClass).addClass(validClass);
					$(element).removeClass(errorClass).addClass(validClass);
				}
			});
		});
	    $(document).ready(function(){
	    	$("#ghJsgcSzGhxkForm").validate({
    			rules: {
    				'entityIdcode':{card:true}
    			},
    			submitHandler: function(form){
  					form.submit();
    			}
    		});
	    	if("${lookForm}"){
	    		$("input").attr("readonly",true);
	    		$("textarea").attr("disabled",true);
	    		$(".base").each(function (){
	    			if($(this).prop("type") == "checkbox"){
	    				$(this).attr("disabled",true);
	    			}
	    		});
	    	}
		});
		function saveForm(){
			var pipList = [];
			$(".pip").each(function (){
				var pip = {};
				$(this).find('td').each(function (){
					pip[$(this).find('input').attr('name')] = $(this).find('input').val();
				});
				pipList.push(pip);
			});
			var pipJsonData = JSON.stringify(pipList);
			$("#jsonDataPipList").val(pipJsonData);
			var base = {};
			$(".base").each(function (){
				if($(this).prop("type") == "checkbox"){
					if($(this).prop("checked")){
						if(base[$(this).attr('name')]){
							base[$(this).attr('name')] = base[$(this).attr('name')]+$(this).val();
						}else{
							base[$(this).attr('name')] = $(this).val();
						}
					}
				}else{
					base[$(this).attr('name')] = $(this).val();
				}
			});
			var objJsonData = JSON.stringify(base);
			$("#jsonDataObj").val(objJsonData);
			$(".savebutton").attr("disabled",true);
			if($("#ghJsgcSzGhxkForm").valid()){
				$("#ghJsgcSzGhxkForm").submit();
			}else{
				$(".savebutton").attr("disabled",false);
			}
		}
		function refresh(){
			window.location.reload(true);
		}
		function addRow(id){
			var tr = $('#'+id).clone();
			$(tr).removeAttr('id');
			$(tr).find('input').val('');
			$(tr).find('button').removeAttr('onclick').removeClass('btn-primary').addClass('btn-danger').bind("click", function(){
				var rows = $("#changRows").prop("rowspan");
				$("#changRows").attr("rowspan",rows-1);
				rows = $("#changRow").prop("rowspan");
				$("#changRow").attr("rowspan",rows-1);
				$(this).parents('tr').remove();
			});
			$(tr).find('button i.md').removeClass('add').addClass('minus');
			var rows = $("#changRows").prop("rowspan");
			$("#changRows").attr("rowspan",rows+1);
			rows = $("#changRow").prop("rowspan");
			$("#changRow").attr("rowspan",rows+1);
			$('.'+id+':last').after(tr);
		}
		function removeRow(id){
			var rows = $("#changRows").prop("rowspan");
			$("#changRows").attr("rowspan",rows-1);
			rows = $("#changRow").prop("rowspan");
			$("#changRow").attr("rowspan",rows-1);
			$("#"+id).remove();
		}
		$(function (){
			var pipList = "${formObject.pipList}";
			console.info(pipList);
			if(pipList.length > 0){
				$('tr[name="pipTr"]').remove();
			}
			var flag = 0;
			$(".pip").each(function (){
				flag++;
			});
			var rows = $("#changRows").prop("rowspan");
			$("#changRows").attr("rowspan",rows+flag-1);
			rows = $("#changRow").prop("rowspan");
			$("#changRow").attr("rowspan",rows+flag-1);
		});
		//打印
		function doPrint() {
			$("#footTable").hide();
	 		document.getElementById("print").style.display = "none";
			window.print();
			document.getElementById("print").style.display = "";
			$("#footTable").show();
		}
    </script>
</head>
<body>
	<div class="card" align="center">
	    <form id="ghJsgcSzGhxkForm" action="${ctx}/ghJsgcSzGhxk/save" method="post">
		    <div class="card-body card-padding">
	        	<div class="row">
		            <input type="hidden" name="id"
		                   value="${formObject.id}" class="base">
	                <input type="hidden" name="prjId"
	                 value="${formObject.prjId}" class="base">
	                <input type="hidden" name="taskId"
	                 value="${formObject.taskId}" class="base">
	                 <table class="editTable" style="width: 960px;" border="0" cellspacing="0" cellpadding="0">
	                 	<tbody>
	                 		<tr>
	                 			<td class="title" colspan="10" style="font-size: 16px;font-weight: bolder;">
	                 				珠海市建设工程（市政类）规划许可申请表
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td width="35px" rowspan="7" class="title">
	                 				申请单位填写
	                 			</td>
	                 			<td class="title" colspan="3">
	                 				建设项目名称
	                 			</td>
	                 			<td colspan="6">
	                 				<input maxlength="200" class="required base" type="text" name="prjName" value="${formObject.prjName}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title" colspan="3">
	                 				建设项目地址
	                 			</td>
	                 			<td colspan="6">
	                 				<input maxlength="50" class=" base" type="text" name="prjAddr" value="${formObject.prjAddr}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title" rowspan="5" style="width: 35px;" >
	                 				申请人
	                 			</td>
	                 			<td class="title" colspan="2" rowspan="2">
	                 				名称
	                 			</td>
	                 			<td colspan="2" rowspan="2">
	                 				<input maxlength="50" class=" base" type="text" name="proposerName" value="${formObject.proposerName}" />
	                 			</td>
	                 			<td class="title" style="width: 50px;">
	                 				地址
	                 			</td>
	                 			<td colspan="3">
	                 				<input maxlength="50" class=" base" type="text" name="proposerAddr" value="${formObject.proposerAddr}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title">
	                 				电话
	                 			</td>
	                 			<td>
	                 				<input maxlength="15" class=" base phone" type="text" name="proposerTel" value="${formObject.proposerTel}" />
	                 			</td>
	                 			<td class="title">
	                 				邮政编码
	                 			</td>
	                 			<td>
	                 				<input maxlength="20" class=" base" type="text" name="proposerPostCode" value="${formObject.proposerPostCode}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title" colspan="2">
	                 				受委托人
	                 			</td>
	                 			<td>
	                 				<input maxlength="50" class=" base" type="text" name="agentName" value="${formObject.agentName}" />
	                 			</td>
	                 			<td class="title">
	                 				联系电话
	                 			</td>
	                 			<td style="border-right: none;text-align: right;">
	                 				办公：
	                 			</td>
	                 			<td style="border-left: none;border-right: none;">
	                 				<input maxlength="15" class=" base phone" type="text" name="agentPhone" value="${formObject.agentPhone}" placeholder="点此填写" />
	                 			</td>
	                 			<td style="border-left: none;border-right: none;text-align: right;">
	                 				手机：
	                 			</td>
	                 			<td style="border-left: none;">
	                 				<input maxlength="15" class=" base mobile" type="text" name="agentMphone" value="${formObject.agentMphone}" placeholder="点此填写" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title" colspan="2">
	                 				业务内容
	                 			</td>
	                 			<td colspan="6" style="height: 90px;padding-left: 10px;">
	                 				<label class="checkbox checkbox-inline m-r-10 m-l-25">
										<input type="checkbox" class="base" value="A" name="businessContent" <c:if test="${formObject.businessContent.contains('A')}"> checked </c:if> />
										<i class="input-helper"></i>    
										申请道路、桥梁（交通）工程规划设计条件
									</label>
									<label class="checkbox checkbox-inline m-r-10 m-l-25">
										<input type="checkbox" class="base" value="B" name="businessContent" <c:if test="${formObject.businessContent.contains('B')}"> checked </c:if> />
										<i class="input-helper"></i>    
										临时
									</label>
									<br/>
									<label class="checkbox checkbox-inline m-r-10 m-l-25">
										<input type="checkbox" class="base" value="C" name="businessContent" <c:if test="${formObject.businessContent.contains('C')}"> checked </c:if> />
										<i class="input-helper"></i>    
										申请管线工程规划设计条件
									</label>
									<br/>
									<label class="checkbox checkbox-inline m-r-10 m-l-25">
										<input type="checkbox" class="base" value="D" name="businessContent" <c:if test="${formObject.businessContent.contains('D')}"> checked </c:if> />
										<i class="input-helper"></i>    
										申请道路、桥隧工程规划许可 
									</label>
									（
									<label class="checkbox checkbox-inline m-r-10 m-l-25">
										<input type="checkbox" class="base" value="D1" name="businessContent" <c:if test="${formObject.businessContent.contains('D1')}"> checked </c:if> />
										<i class="input-helper"></i>    
										方案阶段
									</label>
									<label class="checkbox checkbox-inline m-r-10 m-l-25">
										<input type="checkbox" class="base" value="D2" name="businessContent" <c:if test="${formObject.businessContent.contains('D2')}"> checked </c:if> />
										<i class="input-helper"></i>    
										施工图阶段
									</label>
									）
									<br/>
									<label class="checkbox checkbox-inline m-r-10 m-l-25">
										<input type="checkbox" class="base" value="E" name="businessContent" <c:if test="${formObject.businessContent.contains('E')}"> checked </c:if> />
										<i class="input-helper"></i>    
										申请管线工程规划许可
									</label>
									（
									<label class="checkbox checkbox-inline m-r-10 m-l-25">
										<input type="checkbox" class="base" value="E1" name="businessContent" <c:if test="${formObject.businessContent.contains('E1')}"> checked </c:if> />
										<i class="input-helper"></i>    
										方案阶段
									</label>
									<label class="checkbox checkbox-inline m-r-10 m-l-25">
										<input type="checkbox" class="base" value="E2" name="businessContent" <c:if test="${formObject.businessContent.contains('E2')}"> checked </c:if> />
										<i class="input-helper"></i>    
										施工图阶段
									</label>
									）
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td colspan="2" class="title">
	                 				管线类别
	                 			</td>
	                 			<td colspan="6" style="height: 50px;padding-left: 10px;">
	                 				<label class="checkbox checkbox-inline m-r-10 m-l-25">
										<input type="checkbox" class="base" value="A" name="pipelineType" <c:if test="${formObject.pipelineType.contains('A')}"> checked </c:if> />
										<i class="input-helper"></i>    
										道路、桥隧
									</label>
									<label class="checkbox checkbox-inline m-r-10 m-l-25">
										<input type="checkbox" class="base" value="B" name="pipelineType" <c:if test="${formObject.pipelineType.contains('B')}"> checked </c:if> />
										<i class="input-helper"></i>    
										电
									</label>
									<label class="checkbox checkbox-inline m-r-10 m-l-25">
										<input type="checkbox" class="base" value="C" name="pipelineType" <c:if test="${formObject.pipelineType.contains('C')}"> checked </c:if> />
										<i class="input-helper"></i>    
										信
									</label>
									<label class="checkbox checkbox-inline m-r-10 m-l-25">
										<input type="checkbox" class="base" value="D" name="pipelineType" <c:if test="${formObject.pipelineType.contains('D')}"> checked </c:if> />
										<i class="input-helper"></i>    
										给水
									</label>
									<label class="checkbox checkbox-inline m-r-10 m-l-25">
										<input type="checkbox" class="base" value="E" name="pipelineType" <c:if test="${formObject.pipelineType.contains('E')}"> checked </c:if> />
										<i class="input-helper"></i>    
										排水
									</label>
									<label class="checkbox checkbox-inline m-r-10 m-l-25">
										<input type="checkbox" class="base" value="F" name="pipelineType" <c:if test="${formObject.pipelineType.contains('F')}"> checked </c:if> />
										<i class="input-helper"></i>    
										防洪
									</label>
									<label class="checkbox checkbox-inline m-r-10 m-l-25">
										<input type="checkbox" class="base" value="G" name="pipelineType" <c:if test="${formObject.pipelineType.contains('G')}"> checked </c:if> />
										<i class="input-helper"></i>    
										油气
									</label>
									<label class="checkbox checkbox-inline m-r-10 m-l-25">
										<input type="checkbox" class="base" value="H" name="pipelineType" <c:if test="${formObject.pipelineType.contains('H')}"> checked </c:if> />
										<i class="input-helper"></i>    
										其他
									</label>
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td style="width: 35px;" class="title" id="changRows" rowspan="11">
	                 				工程设计单位填写
	                 			</td>
	                 			<td colspan="3" rowspan="3" class="title">
	                 				工程设计单位
	                 			</td>
	                 			<td class="title">
	                 				名称
	                 			</td>
	                 			<td colspan="5">
	                 				<input maxlength="50" class=" base" type="text" name="designName" value="${formObject.designName}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title">
	                 				地址
	                 			</td>
	                 			<td colspan="3">
	                 				<input maxlength="50" class=" base" type="text" name="designAddr" value="${formObject.designAddr}" />
	                 			</td>
	                 			<td class="title">
	                 				资质等级
	                 			</td>
	                 			<td>
	                 				<input maxlength="50" class=" base" type="text" name="aptitudesRank" value="${formObject.aptitudesRank}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title">
	                 				联系人
	                 			</td>
	                 			<td>
	                 				<input maxlength="50" class=" base" type="text" name="designLinkMan" value="${formObject.designLinkMan}" />
	                 			</td>
	                 			<td class="title">
	                 				电话
	                 			</td>
	                 			<td>
	                 				<input maxlength="15" class=" base phone" type="text" name="designLinkTel" value="${formObject.designLinkTel}" />
	                 			</td>
	                 			<td class="title">
	                 				资质证号
	                 			</td>
	                 			<td>
	                 				<input maxlength="50" class=" base" type="text" name="aptitudesRankCard" value="${formObject.aptitudesRankCard}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td colspan="9" class="title">
	                 				申报工程内容
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title" rowspan="5" style="width: 35px;">
	                 				道桥︵<br/>隧<br/>︶工程
	                 			</td>
	                 			<td class="title" colspan="2">
	                 				起止点
	                 			</td>
	                 			<td class="title" colspan="3">
	                 				断面形式
	                 			</td>
	                 			<td class="title">
	                 				道路长度
	                 			</td>
	                 			<td class="title">
	                 				道路宽度
	                 			</td>
	                 			<td class="title">
	                 				工程造价
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td colspan="2" style="height: 30px;">
	                 				<input maxlength="50" class=" base" type="text" name="initialPoint" value="${formObject.initialPoint}" />
	                 			</td>
	                 			<td colspan="3">
	                 				<input maxlength="50" class=" base" type="text" name="startSectionForm" value="${formObject.startSectionForm}" />
	                 			</td>
	                 			<td>
	                 				<input maxlength="50" class=" base" type="text" name="startRoadLength" value="${formObject.startRoadLength}" />
	                 			</td>
	                 			<td>
	                 				<input maxlength="50" class=" base" type="text" name="startRoadWidth" value="${formObject.startRoadWidth}" />
	                 			</td>
	                 			<td>
	                 				<input maxlength="50" class=" base" type="text" name="startPrjCost" value="${formObject.startPrjCost}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td colspan="2" style="height: 30px;">
	                 				<input maxlength="50" class=" base" type="text" name="stopPoint" value="${formObject.stopPoint}" />
	                 			</td>
	                 			<td colspan="3">
	                 				<input maxlength="50" class=" base" type="text" name="stopSectionForm" value="${formObject.stopSectionForm}" />
	                 			</td>
	                 			<td>
	                 				<input maxlength="50" class=" base" type="text" name="stopRoadLength" value="${formObject.stopRoadLength}" />
	                 			</td>
	                 			<td>
	                 				<input maxlength="50" class=" base" type="text" name="stopRoadWidth" value="${formObject.stopRoadWidth}" />
	                 			</td>
	                 			<td>
	                 				<input maxlength="50" class=" base" type="text" name="stopPrjCost" value="${formObject.stopPrjCost}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title" colspan="2">
	                 				跨越区域
	                 			</td>
	                 			<td class="title" colspan="3">
	                 				桥隧宽度
	                 			</td>
	                 			<td class="title">
	                 				桥隧长度
	                 			</td>
	                 			<td class="title">
	                 				净空高度
	                 			</td>
	                 			<td class="title">
	                 				工程造价
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td colspan="2" style="height: 30px;">
	                 				<input maxlength="50" class=" base" type="text" name="stepOverArea" value="${formObject.stepOverArea}" />
	                 			</td>
	                 			<td colspan="3">
	                 				<input maxlength="50" class=" base" type="text" name="bridgeWidth" value="${formObject.bridgeWidth}" />
	                 			</td>
	                 			<td>
	                 				<input maxlength="50" class=" base" type="text" name="bridgeLength" value="${formObject.bridgeLength}" />
	                 			</td>
	                 			<td>
	                 				<input maxlength="50" class=" base" type="text" name="headRoom" value="${formObject.headRoom}" />
	                 			</td>
	                 			<td>
	                 				<input maxlength="50" class=" base" type="text" name="prjectCost" value="${formObject.prjectCost}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title" rowspan="2" id="changRow">
	                 				管线工程
	                 			</td>
	                 			<td class="title" colspan="2">
	                 				工程内容
	                 			</td>
	                 			<td class="title">
	                 				起止点
	                 			</td>
	                 			<td class="title" colspan="2">
	                 				线路规格
	                 			</td>
	                 			<td class="title">
	                 				线路长度
	                 			</td>
	                 			<td class="title">
	                 				走廊面积
	                 			</td>
	                 			<td class="title">
	                 				工程造价
	                 			</td>
	                 		</tr>
	                 		<tr id="pip" class="pip" name="pipTr">
	                 			<td>
	                 				<button type="button" onclick="addRow('pip')" class="btn btn-primary"><i class="md add"></i></button>
	                 			</td>
	                 			<td>
	                 				<input maxlength="200"  type="text" name="pContent" value="${pip.pContent}" />
	                 			</td>
	                 			<td>
	                 				<input maxlength="50"  type="text" name="pEnthesis" value="${pip.pEnthesis}" />
	                 			</td>
	                 			<td colspan="2">
	                 				<input maxlength="50"  type="text" name="pCircuitSpe" value="${pip.pCircuitSpe}" />
	                 			</td>
	                 			<td>
	                 				<input maxlength="50"  type="text" name="pCircuitLength" value="${pip.pCircuitLength}" />
	                 			</td>
	                 			<td>
	                 				<input maxlength="50"  type="text" name="pAisleSpace" value="${pip.pAisleSpace}" />
	                 			</td>
	                 			<td>
	                 				<input maxlength="50"  type="text" name="pPrjCost" value="${pip.pPrjCost}" />
	                 			</td>
	                 		</tr>
	                 		<c:forEach items="${formObject.pipList}" var="pip" varStatus="i">
	                 			<c:if test="${i.index == 0}">
	                 				<tr id="pip" class="pip">
	                 					<td style="display: none;">
	                 						<input type="hidden" name="pId" value="${pip.pId}" />
	                 					</td>
	                 					<td>
			                 				<button type="button" onclick="addRow('pip')" class="btn btn-primary"><i class="md add"></i></button>
			                 				<input type="hidden" name="gId" value="${pip.gId}" />
			                 			</td>
			                 			<td>
			                 				<input maxlength="200"  type="text" name="pContent" value="${pip.pContent}" />
			                 			</td>
			                 			<td>
			                 				<input maxlength="50"  type="text" name="pEnthesis" value="${pip.pEnthesis}" />
			                 			</td>
			                 			<td colspan="2">
			                 				<input maxlength="50"  type="text" name="pCircuitSpe" value="${pip.pCircuitSpe}" />
			                 			</td>
			                 			<td>
			                 				<input maxlength="50"  type="text" name="pCircuitLength" value="${pip.pCircuitLength}" />
			                 			</td>
			                 			<td>
			                 				<input maxlength="50"  type="text" name="pAisleSpace" value="${pip.pAisleSpace}" />
			                 			</td>
			                 			<td>
			                 				<input maxlength="50"  type="text" name="pPrjCost" value="${pip.pPrjCost}" />
			                 			</td>
	                 				</tr>
	                 			</c:if>
	                 			<c:if test="${i.index > 0}">
	                 				<tr id="${pip.pId}" class="pip">
	                 					<td style="display: none;">
	                 						<input type="hidden" name="pId" value="${pip.pId}" />
	                 					</td>
	                 					<td>
			                 				<button type="button" onclick="removeRow('${pip.pId}')" class="btn btn-danger"><i class="md minus"></i></button>
			                 				<input type="hidden" name="gId" value="${pip.gId}" />
			                 			</td>
			                 			<td>
			                 				<input maxlength="200"  type="text" name="pContent" value="${pip.pContent}" />
			                 			</td>
			                 			<td>
			                 				<input maxlength="50"  type="text" name="pEnthesis" value="${pip.pEnthesis}" />
			                 			</td>
			                 			<td colspan="2">
			                 				<input maxlength="50"  type="text" name="pCircuitSpe" value="${pip.pCircuitSpe}" />
			                 			</td>
			                 			<td>
			                 				<input maxlength="50"  type="text" name="pCircuitLength" value="${pip.pCircuitLength}" />
			                 			</td>
			                 			<td>
			                 				<input maxlength="50"  type="text" name="pAisleSpace" value="${pip.pAisleSpace}" />
			                 			</td>
			                 			<td>
			                 				<input maxlength="50"  type="text" name="pPrjCost" value="${pip.pPrjCost}" />
			                 			</td>
	                 				</tr>
	                 			</c:if>
	                 		</c:forEach>
	                 		<tr>
	                 			<td rowspan="3" class="title">
	                 				备注
	                 			</td>
	                 			<td colspan="9" style="text-indent: 2em;font-size: 14px;padding-left: 10px;padding-right:10px;border-bottom: none;height: 80px;">
	                 				我单位（本人）已阅知有关备注说明，并承诺对申报资料的真实性及数据的准确性（含电子文件
	                 				与图纸的一致性）负责，若有任何虚报、瞒报、造假等不正当手段，审批机关可终止审理，我单位（本
	                 				人）自愿承担虚报、瞒报、造假等不正当手段而产生的一切法律责任。
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td style="border-right: none;border-top: none;border-bottom: none;height: 40px;"></td>
	                 			<td style="border: none;"></td>
	                 			<td style="border: none;"></td>
	                 			<td style="border: none;"></td>
	                 			<td style="border: none;">
	                 				（申请单位盖章）
	                 			</td>
	                 			<td style="border: none;"></td>
	                 			<td style="border: none;"></td>
	                 			<td style="border: none;"></td>
	                 			<td style="border-left: none;border-top: none;border-bottom: none;">
	                 				（设计单位盖章）
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td style="border-right: none;border-top: none;height: 40px;"></td>
	                 			<td style="border-left:none;border-right: none;border-top: none;"></td>
	                 			<td style="border-left:none;border-right: none;border-top: none;"></td>
	                 			<td style="border-left:none;border-right: none;border-top: none;"></td>
	                 			<td style="border-left:none;border-right: none;border-top: none;height: 40px;">
	                 				<input class=" base" type="text" 
		                 				onClick="WdatePicker()" placeholder="点击填日期" 
		                 				value='<fmt:formatDate value="${formObject.applyDate}" 
		                 				pattern="yyyy-MM-dd" />' name="applyDate" />
	                 			</td>
	                 			<td style="border-left:none;border-right: none;border-top: none;"></td>
	                 			<td style="border-left:none;border-right: none;border-top: none;"></td>
	                 			<td style="border-left:none;border-right: none;border-top: none;"></td>
	                 			<td style="border-left: none;border-top: none;">
	                 				<input class=" base" type="text" 
		                 				onClick="WdatePicker()" placeholder="点击填日期" 
		                 				value='<fmt:formatDate value="${formObject.designDate}" 
		                 				pattern="yyyy-MM-dd" />' name="designDate" />
	                 			</td>
	                 		</tr>
	                 	</tbody>
	                 </table>
	                 <input type="hidden" name="jsonDataObj" id="jsonDataObj"/>
	                 <input type="hidden" name="jsonDataPipList" id="jsonDataPipList"/>
		            <table class="editTable" id="footTable" style="width: 960px;" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td class="content" align="center">
								<c:if test="${empty lookForm}">
									<input type="button" class="btnNoimg savebutton" value="保存" onclick="saveForm();" />
								</c:if>
								<c:if test="${!empty lookForm}">
									<input id='print' type="button" value="打 印" onclick="doPrint();"class="btnNoimg" id="btnPrev">
								</c:if>
								<input type="button" class="btnNoimg savebutton" value="刷新" onclick="refresh();" />
								<input type="button" class="btnNoimg savebutton" value="返回" onclick="javascrtpt:history.go(-1)" />
							</td>
						</tr>
					</table>
	        	</div>
	    	</div>
	    </form>
	</div>
</body>