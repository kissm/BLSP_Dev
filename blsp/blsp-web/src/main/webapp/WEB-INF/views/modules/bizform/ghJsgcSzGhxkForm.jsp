<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>珠海市建设工程（市政类）规划许可申请表</title>
    <meta name="decorator" content="blank" />
    <style>
		.editTable {
			width: 100%;
		}
		.editTable > tbody > tr > td {
			border: 1px solid #ccc;
		}
		.editTable td.title {
			padding: 6px;
			text-align: center;
			background: #F7F7F7;
		}
		.editTable td.content {
			padding: 6px;
		}
		.editTable input[type='text'] {
			width: 100%;
			height: 100%;
			border: none;
			padding: 0px 6px;
			resize: none;
		}
		.editTable input[type='text'].has-error {
			background: #F44336;
			color: #FFF;
		}
	</style>
    <script>
    	$(document).ready(function(){
	    	$("#ghJsgcSzGhxkForm").validate({
				rules: {
//					'entityIdcode':{card:true}
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
					element.attr('title',label.html()).tooltip();
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
//    				'entityIdcode':{card:true}
    			},
    			submitHandler: function(form){
  					form.submit();
    			}
    		});
	    	var view = "${view}";
	    	if(view){
	    		$("header").remove();
	    		$("input").attr("readonly",true);
	    		$("textarea").attr("disabled",true);
	    		$(".base").each(function (){
	    			if($(this).prop("type") == "checkbox"){
	    				$(this).attr("disabled",true);
	    			}
	    		});
	    		$("button").hide();
				$(".actions").append('<li>'+
						'<button type="button" class="btn btn-info btn-sm" data-toggle= "modal" onClick="createLocpde()">写码</button>'+
						'</li>&nbsp;&nbsp;');
				if(view != "3"){
					$(".actions").append('<li>'+
						'<button type="button"data-toggle= "modal" class="btn btn-success btn-sm" onclick="backItem()">返回</button>'+
						'</li>');
				}
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
			$(tr).find('button i.md').removeClass('md-add').addClass('md-clear');
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
		//返回详情信息
		function backItem(){
			var view = "${view}";
			var prjId = "${formObject.prjId}";
			var taskId = "${formObject.taskId}";
			window.parent.location.href = "${ctx}/project/backItem?prjId="+prjId+"&taskId="+taskId+"&view="+view;
		}
		//生成表单龙贝码
		function createLocpde(){
			var projectId = "${formObject.prjId}";
			var taskDefId = "${formObject.taskId}";
			var formCode = "${formCode}";
			window.open ('${ctx}/project/createFormLpcode?formCode='+formCode+'&taskDefId='+taskDefId+'&prjInstanceVo.id='+projectId,'_blank');
		}
    </script>
</head>
<body>
	<div class="card">
    	<div class="card-header">
        	<h2>珠海市建设工程（市政类）规划许可申请表<small>您可通过本功能进行珠海市建设工程（市政类）规划许可申请表申报</small></h2>
	        <ul class="actions">
	            <li>
	                <button data-toggle="tooltip" data-placement="bottom" type="button"
	                        title="返回" class="btn btn-success btn-icon m-r-5 savebutton"
	                        onclick="javascrtpt:history.go(-1)">
	                    <i class="md md-arrow-back"></i>
	                </button>
            	</li>
	            <li>
					<button title="保存" onclick="saveForm();" data-toggle="tooltip" data-placement="bottom" type="button" class="btn btn-info btn-icon m-r-5 savebutton" ><i class="md md-save"></i></button>
				</li>
	            <li>
	                <button data-toggle="tooltip" data-placement="bottom" type="button"
	                        title="刷新" class="btn btn-default btn-icon m-r-5 savebutton"
	                        onclick="refresh();">
	                    <i class="md md-autorenew"></i>
	                </button>
	            </li>
	        </ul>
    	</div>
    
	    <form id="ghJsgcSzGhxkForm" action="${ctx}/ghJsgcSzGhxk/save" method="post">
		    <div class="card-body card-padding">
	        	<div class="row">
		            <input type="hidden" name="id"
		                   value="${formObject.id}" class="base">
	                <input type="hidden" name="prjId"
	                 value="${formObject.prjId}" class="base">
	                <input type="hidden" name="taskId"
	                 value="${formObject.taskId}" class="base">
	                 <table class="editTable">
	                 	<tbody>
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
	                 			<td colspan="6" style="height: 90px;padding-left: 50px">
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
	                 			<td colspan="6" style="height: 50px;">
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
	                 				道桥
	                 				︵
	                 				<br/>
	                 				隧
	                 				<br/>
	                 				︶
	                 				工程
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
	                 				<button type="button" onclick="addRow('pip')" class="btn btn-primary"><i class="md md-add"></i></button>
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
			                 				<button type="button" onclick="addRow('pip')" class="btn btn-primary"><i class="md md-add"></i></button>
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
			                 				<button type="button" onclick="removeRow('${pip.pId}')" class="btn btn-danger"><i class="md md-clear"></i></button>
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
	                 				<div class="dtp-container dropdown">
	                 					<input type="text" class="date-picker  base" data-toggle="dropdown" aria-expanded="false" placeholder="点击填日期" value='<fmt:formatDate value="${formObject.applyDate}" pattern="yyyy-MM-dd" />' name="applyDate" >
	                 				</div>
	                 			</td>
	                 			<td style="border-left:none;border-right: none;border-top: none;"></td>
	                 			<td style="border-left:none;border-right: none;border-top: none;"></td>
	                 			<td style="border-left:none;border-right: none;border-top: none;"></td>
	                 			<td style="border-left: none;border-top: none;">
	                 				<div class="dtp-container dropdown">
	                 					<input type="text" class="date-picker  base" data-toggle="dropdown" aria-expanded="false" placeholder="点击填日期" value='<fmt:formatDate value="${formObject.designDate}" pattern="yyyy-MM-dd" />' name="designDate" >
	                 				</div>
	                 			</td>
	                 		</tr>
	                 	</tbody>
	                 </table>
	                 <br/><br/>
	                 <input type="hidden" name="jsonDataObj" id="jsonDataObj"/>
	                 <input type="hidden" name="jsonDataPipList" id="jsonDataPipList"/>
		            <div class="btn-demo text-center col-xs-12">
			            <button data-toggle="modal" onclick="saveForm();"
			                    class="btn btn-primary waves-effect" type="button savebutton">保存</button>
			            <button class="btn btn-default waves-effect" type="button savebutton"
			                    onclick="javascrtpt:history.go(-1)">返回</button>
			        </div>
	        	</div>
	    	</div>
	    </form>
	</div>
</body>