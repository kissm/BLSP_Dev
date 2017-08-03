<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>珠海市房屋建筑和市政基础设施工程施工许可并联审批申请表</title>
    <meta name="decorator" content="blank" />
    <style>
		.editTable input[type='text'].has-error {
			background: #F44336;
			color: #FFF;
		}
		.noBorder > tbody > tr > td {
			border: none;
			height: 40px;
		}
	</style>
    <script>
    	$(document).ready(function(){
	    	$("#zjSgxkSqbForm").validate({
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
	    	$("#zjSgxkSqbForm").validate({
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
			//基础部分数据进行对象序列化
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
			console.info(objJsonData);
			$("#jsonDataObj").val(objJsonData);
			//建设单位送样见证人信息集合序列化
			var buiList = [];
			$(".bui").each(function (){
				var bui = {};
				$(this).find('td').each(function (){
					bui[$(this).find('input').attr('name')] = $(this).find('input').val();
				});
				buiList.push(bui);
			});
			var buiJsonData = JSON.stringify(buiList);
			console.info(buiJsonData);
			$("#jsonDataBuiList").val(buiJsonData);
			//施工单位组织机构数据集合序列化
			var conList = [];
			$(".con").each(function (){
				var con = {};
				$(this).find('td').each(function (){
					con[$(this).find('input').attr('name')] = $(this).find('input').val();
				});
				conList.push(con);
			});
			var conJsonData = JSON.stringify(conList);
			console.info(conJsonData);
			$("#jsonDataConList").val(conJsonData);
			//监理单位组织机构数据集合序列化
			var supList = [];
			$(".sup").each(function (){
				var sup = {};
				$(this).find('td').each(function (){
					sup[$(this).find('input').attr('name')] = $(this).find('input').val();
				});
				supList.push(sup);
			});
			var supJsonData = JSON.stringify(supList);
			console.info(supJsonData);
			$("#jsonDataSupList").val(supJsonData);
			$(".savebutton").attr("disabled",true);
			if($("#zjSgxkSqbForm").valid()){
				$("#zjSgxkSqbForm").submit();
			}else{
				$(".savebutton").attr("disabled",false);
			}
		}
		function refresh(){
			window.location.reload(true);
		}
		function addBuiRow(id){
			var tr = $('#'+id).clone();
			$(tr).removeAttr('id');
			$(tr).find('input').val('');
			$(tr).find('button').removeAttr('onclick').removeClass('btn-primary').addClass('btn-danger').bind("click", function(){
				var rows = $("#changeBuiRows").prop("rowspan");
				$("#changeBuiRows").attr("rowspan",rows-1);
				$(this).parents('tr').remove();
			});
			$(tr).find('button i.md').removeClass('add').addClass('minus');
			var rows = $("#changeBuiRows").prop("rowspan");
			$("#changeBuiRows").attr("rowspan",rows+1);
			$('.'+id+':last').after(tr);
		}
		function removeBuiRow(id){
			var rows = $("#changeBuiRows").prop("rowspan");
			$("#changeBuiRows").attr("rowspan",rows-1);
			$("#bui"+id).remove();
		}
		function addConRow(id){
			var tr = $('#'+id).clone();
			$(tr).removeAttr('id');
			$(tr).find('input').val('');
			$(tr).find('button').removeAttr('onclick').removeClass('btn-primary').addClass('btn-danger').bind("click", function(){
				var rows = $("#changeConRows").prop("rowspan");
				$("#changeConRows").attr("rowspan",rows-1);
				$(this).parents('tr').remove();
			});
			$(tr).find('button i.md').removeClass('add').addClass('minus');
			var rows = $("#changeConRows").prop("rowspan");
			$("#changeConRows").attr("rowspan",rows+1);
			$('.'+id+':last').after(tr);
		}
		function removeConRow(id){
			var rows = $("#changeConRows").prop("rowspan");
			$("#changeConRows").attr("rowspan",rows-1);
			$("#con"+id).remove();
		}
		function addSupRow(id){
			var tr = $('#'+id).clone();
			$(tr).removeAttr('id');
			$(tr).find('input').val('');
			$(tr).find('button').removeAttr('onclick').removeClass('btn-primary').addClass('btn-danger').bind("click", function(){
				var rows = $("#changeSupRows").prop("rowspan");
				$("#changeSupRows").attr("rowspan",rows-1);
				$(this).parents('tr').remove();
			});
			$(tr).find('button i.md').removeClass('add').addClass('minus');
			var rows = $("#changeSupRows").prop("rowspan");
			$("#changeSupRows").attr("rowspan",rows+1);
			$('.'+id+':last').after(tr);
		}
		function removeSupRow(id){
			var rows = $("#changeSupRows").prop("rowspan");
			$("#changeSupRows").attr("rowspan",rows-1);
			$("#sup"+id).remove();
		}
		$(function (){
			var buiList = '${formObject.buiList}';
			if(buiList.length > 0){
				$('tr[name="buiTr"]').remove();
			}
			var flgb = 0;
			$(".bui").each(function (){
				flgb++;
			});
			var rows = $("#changeBuiRows").prop("rowspan");
			$("#changeBuiRows").attr("rowspan",rows+flgb-1);
			
			var conList = '${formObject.conList}';
			if(conList.length > 0){
				$('tr[name="conTr"]').remove();
			}
			var flgc = 0;
			$(".con").each(function (){
				flgc++;
			});
			var rows = $("#changeConRows").prop("rowspan");
			$("#changeConRows").attr("rowspan",rows+flgc-1);
			
			var supList = '${formObject.supList}';
			console.info(supList);
			if(supList.length > 0){
				$('tr[name="supTr"]').remove();
			}
			var flgs = 0;
			$(".sup").each(function (){
				flgs++;
			});
			var rows = $("#changeSupRows").prop("rowspan");
			$("#changeSupRows").attr("rowspan",rows+flgs-1);
		});
		//同步两个建设单位名称（公章）的信息
		function writeBuildName(buildName){
			var value = buildName.value;
			$(".buildName").each(function (){
				if(this != buildName){
					$(this).val(value);
				}
			});
		}
		function blurBuildName(buildName){
			var value = buildName.value;
			$(".buildName").each(function (){
				if(this != buildName){
					$(this).val(value);
				}
			});
		}
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
	    <form id="zjSgxkSqbForm" action="${ctx}/zjSgxkSqb/save" method="post">
		    <div class="card-body card-padding">
	        	<div class="row">
		            <input type="hidden" name="id" class="base"
		                   value="${formObject.id}">
	                <input type="hidden" name="prjId" class="base"
	                 value="${formObject.prjId}">
	                <input type="hidden" name="taskId" class="base"
	                 value="${formObject.taskId}">
	                <table class="editTable" style="width: 960px;" border="0" cellspacing="0" cellpadding="0">
	                	<tbody>
	                		<tr>
		                		<td class="title" colspan="4" style="font-size: 16px;font-weight: bolder;">
	                 				珠海市房屋建筑和市政基础设施工程施工许可并联审批申请表
	                 			</td>
		                	</tr>
		                	<tr>
		                		<td class="title" style="width: 15%;">
		                			项目名称
		                		</td>
		                		<td style="width: 35%;">
		                			<input maxlength="200" class="required base" type="text" name="prjName" value="${formObject.prjName}" />
		                		</td>
		                		<td class="title" style="width: 15%;">
		                			项目地址
		                		</td>
		                		<td style="width: 35%;">
		                			<input maxlength="50" class=" base" type="text" name="prjAddr" value="${formObject.prjAddr}" />
		                		</td>
		                	</tr>
		                	<tr>
		                		<td class="title">
		                			建设单位（公章）
		                		</td>
		                		<td>
		                			<input maxlength="50" class=" base" id="buildNameA" type="text" name="buildName" value="${formObject.buildName}" onkeyup="writeBuildName(this)" onblur="blurBuildName(this)" />
		                		</td>
		                		<td class="title">
		                			单位地址
		                		</td>
		                		<td>
		                			<input maxlength="50" class=" base" type="text" name="buildAddr" value="${formObject.buildAddr}" />
		                		</td>
		                	</tr>
		                	<tr>
		                		<td class="title">
		                			经办人
		                		</td>
		                		<td>
		                			<input maxlength="50" class=" base" type="text" name="buildOperator" value="${formObject.buildOperator}" />
		                		</td>
		                		<td class="title">
		                			联系电话
		                		</td>
		                		<td>
		                			<input maxlength="15" class=" base phone" type="text" name="buildLinktel" value="${formObject.buildLinktel}" />
		                		</td>
		                	</tr>
		                </tbody>
	                </table>
		            <br/><br/>
		            <table class="editTable noBorder" style="border:1px solid #ccc;width: 960px;" cellspacing="0" cellpadding="0">
		            	<tbody>
		            		<tr>
		            			<td colspan="2"></td>
		            		</tr>
		            		<tr style="border: none;">
		            			<td style="padding-left: 30px;" colspan="2">
		            				申请单位（公章）：
		            			</td>
		            		</tr>
		            		<tr style="border: none;">
		            			<td style="padding-left: 30px;" colspan="2">
		            				法定代表人（签名）：
		            			</td>
		            		</tr>
		            		<tr style="border: none;">
		            			<td style="width: 25%;"></td>
		            			<td>
	                 				<input class=" base" type="text" onClick="WdatePicker()" 
		                 				placeholder="点击填日期" 
		                 				value='<fmt:formatDate value="${formObject.applyUnitDate}" 
		                 				pattern="yyyy-MM-dd" />' name="applyUnitDate" />
		            			</td>
		            		</tr>
		            	</tbody>
		            </table>
		            <br/><br/>
	                <table class="editTable" style="width: 960px;" border="0" cellspacing="0" cellpadding="0">
	                 	<tbody>
	                 		<tr>
	                 			<td colspan="8" class="title" style="font-size: 14px;font-weight: bolder;">
	                 				本次申请施工许可项目概况
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title">
	                 				建&nbsp;&nbsp;设&nbsp;&nbsp;形&nbsp;&nbsp;式
	                 			</td>
	                 			<td colspan="4" style="padding-left: 10px;">
	                 				<label class="checkbox checkbox-inline m-l-10">
										<input class="base" type="checkbox" value="A" name="buildTypes" <c:if test="${formObject.buildTypes.contains('A')}"> checked </c:if> />
										<i class="input-helper"></i>
										新建
									</label>
									<label class="checkbox checkbox-inline m-l-10">
										<input class="base" type="checkbox" value="B" name="buildTypes" <c:if test="${formObject.buildTypes.contains('B')}"> checked </c:if> />
										<i class="input-helper"></i>
										改建
									</label>
									<label class="checkbox checkbox-inline m-l-10">
										<input class="base" type="checkbox" value="C" name="buildTypes" <c:if test="${formObject.buildTypes.contains('C')}"> checked </c:if> />
										<i class="input-helper"></i>
										扩建
									</label>
									<label class="checkbox checkbox-inline m-l-10">
										<input class="base" type="checkbox" value="D" name="buildTypes" <c:if test="${formObject.buildTypes.contains('D')}"> checked </c:if> />
										<i class="input-helper"></i>
										续建
									</label>
	                 			</td>
	                 			<td class="title">
	                 				合同开工日期
	                 			</td>
	                 			<td colspan="2">
									<input class=" base" type="text" onClick="WdatePicker()" 
		                 				placeholder="点击填日期" 
		                 				value='<fmt:formatDate value="${formObject.contractStartWorkDate}" 
		                 				pattern="yyyy-MM-dd" />' name="contractStartWorkDate" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title" style="width: 18%">
	                 				合&nbsp;&nbsp;同&nbsp;&nbsp;造&nbsp;&nbsp;价
	                 			</td>
	                 			<td colspan="3" style="border-right: none;">
	                 				<input maxlength="50" class=" base" type="text" value="${formObject.contractCost}" name="contractCost" />
	                 			</td>
	                 			<td style="border-left: none;width: 7%;text-align: center;">
	                 				万元
	                 			</td>
	                 			<td class="title" style="width: 18%">
	                 				合同竣工日期
	                 			</td>
	                 			<td colspan="2" style="width: 23%;">
									<input class=" base" type="text" onClick="WdatePicker()" 
		                 				placeholder="点击填日期" 
		                 				value='<fmt:formatDate value="${formObject.contractEndWorkDate}" 
		                 				pattern="yyyy-MM-dd" />' name="contractEndWorkDate" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title" rowspan="2">
	                 				建&nbsp;&nbsp;设&nbsp;&nbsp;规&nbsp;&nbsp;模
	                 			</td>
	                 			<td colspan="4" style="border-right: none;height: 30px;border-bottom: none;padding-left: 10px;">
	                 				房屋建筑面积：
	                 				<input maxlength="50" class=" base" style="width:100px;height: auto;border-bottom: 1px solid #ccc;" type="text" value="${formObject.scaleSpace}" name="scaleSpace" />
	                 				m<sup>2</sup>
	                 			</td>
	                 			<td colspan="3" style="border-left: none;border-bottom: none;">
	                 				类别：
	                 				<label class="checkbox checkbox-inline m-l-10">
										<input class="base" type="checkbox" value="A" name="scaleTypes" <c:if test="${formObject.scaleTypes.contains('A')}"> checked </c:if> />
										<i class="input-helper"></i>
										住宅
									</label>
									<label class="checkbox checkbox-inline m-l-10">
										<input class="base" type="checkbox" value="B" name="scaleTypes" <c:if test="${formObject.scaleTypes.contains('B')}"> checked </c:if> />
										<i class="input-helper"></i>
										厂房
									</label>
									<label class="checkbox checkbox-inline m-l-10">
										<input class="base" type="checkbox" value="C" name="scaleTypes" <c:if test="${formObject.scaleTypes.contains('C')}"> checked </c:if> />
										<i class="input-helper"></i>
										公共建筑
									</label>
									<label class="checkbox checkbox-inline m-l-10">
										<input class="base" type="checkbox" value="D" name="scaleTypes" <c:if test="${formObject.scaleTypes.contains('D')}"> checked </c:if> />
										<i class="input-helper"></i>
										其他
									</label>
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td colspan="4" style="border-right: none;height: 30px;border-top: none;padding-left: 10px;">
	                 				市政工程：长
	                 				<input maxlength="50" class=" base" style="width:60px;height: auto;border-bottom: 1px solid #ccc;" type="text" value="${formObject.scaleLong}" name="scaleLong" />
	                 				米；宽
	                 				<input maxlength="50" class=" base" style="width:60px;height: auto;border-bottom: 1px solid #ccc;" type="text" value="${formObject.scaleWide}" name="scaleWide" />
	                 				米；
	                 			</td>
	                 			<td colspan="3" style="border-left: none;border-top: none;">
	                 				类别：
	                 				<label class="checkbox checkbox-inline m-l-10">
										<input class="base" type="checkbox" value="E" name="scaleTypes" <c:if test="${formObject.scaleTypes.contains('E')}"> checked </c:if> />
										<i class="input-helper"></i>
										道路
									</label>
									<label class="checkbox checkbox-inline m-l-10">
										<input class="base" type="checkbox" value="F" name="scaleTypes" <c:if test="${formObject.scaleTypes.contains('F')}"> checked </c:if> />
										<i class="input-helper"></i>
										管道
									</label>
									<label class="checkbox checkbox-inline m-l-10">
										<input class="base" type="checkbox" value="G" name="scaleTypes" <c:if test="${formObject.scaleTypes.contains('G')}"> checked </c:if> />
										<i class="input-helper"></i>
										其他
									</label>
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title" rowspan="4">
	                 				技&nbsp;&nbsp;术&nbsp;&nbsp;指&nbsp;&nbsp;标
	                 			</td>
	                 			<td colspan="2" class="title" style="width: 18%;">
	                 				建&nbsp;筑&nbsp;层&nbsp;数&nbsp;栋&nbsp;数
	                 			</td>
	                 			<td colspan="5" style="padding-left: 10px;">
	                 				<input maxlength="50" class=" base" style="width:40px;height: auto;border-bottom: 1px solid #ccc;" type="text" value="${formObject.buildingFloorsa}" name="buildingFloorsa" />
	                 				层/
	                 				<input maxlength="50" class=" base" style="width:40px;height: auto;border-bottom: 1px solid #ccc;" type="text" value="${formObject.buildingRida}" name="buildingRida" />
	                 				栋；
	                 				<input maxlength="50" class=" base" style="width:40px;height: auto;border-bottom: 1px solid #ccc;" type="text" value="${formObject.buildingFloorsb}" name="buildingFloorsb" />
	                 				层/
	                 				<input maxlength="50" class=" base" style="width:40px;height: auto;border-bottom: 1px solid #ccc;" type="text" value="${formObject.buildingRidb}" name="buildingRidb" />
	                 				栋；
	                 				<input maxlength="50" class=" base" style="width:40px;height: auto;border-bottom: 1px solid #ccc;" type="text" value="${formObject.buildingFloorsc}" name="buildingFloorsc" />
	                 				层/
	                 				<input maxlength="50" class=" base" style="width:40px;height: auto;border-bottom: 1px solid #ccc;" type="text" value="${formObject.buildingRidc}" name="buildingRidc" />
	                 				栋；
	                 				<input maxlength="50" class=" base" style="width:40px;height: auto;border-bottom: 1px solid #ccc;" type="text" value="${formObject.buildingFloorsd}" name="buildingFloorsd" />
	                 				层/
	                 				<input maxlength="50" class=" base" style="width:40px;height: auto;border-bottom: 1px solid #ccc;" type="text" value="${formObject.buildingRidd}" name="buildingRidd" />
	                 				栋
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td colspan="2" class="title">
	                 				最&nbsp;&nbsp;大&nbsp;&nbsp;高&nbsp;&nbsp;度
	                 			</td>
	                 			<td colspan="2" style="padding-left: 10px;">
	                 				<input maxlength="50" class=" base" style="width:40px;height: auto;border-bottom: 1px solid #ccc;" type="text" value="${formObject.maximumHeight}" name="maximumHeight" />
	                 				米/栋
	                 			</td>
	                 			<td class="title">
	                 				最&nbsp;&nbsp;大&nbsp;&nbsp;跨&nbsp;&nbsp;度
	                 			</td>
	                 			<td style="border-right: none;">
	                 				<input maxlength="50" class=" base" type="text" value="${formObject.maximumSpan}" name="maximumSpan" />
	                 			</td>
	                 			<td style="border-left: none;width:7%;text-align: right;padding-right: 10px;">
	                 				米
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td rowspan="2" class="title" style="width: 9%;">
	                 				结&nbsp;&nbsp;构<br/>类&nbsp;&nbsp;型
	                 			</td>
	                 			<td class="title" style="width: 9%;">
	                 				基&nbsp;&nbsp;础
	                 			</td>
	                 			<td colspan="2">
	                 				<input maxlength="50" class=" base" type="text" value="${formObject.conBase}" name="conBase" />
	                 			</td>
	                 			<td class="title">
	                 				基坑开挖深度
	                 			</td>
	                 			<td style="border-right: none;">
	                 				<input maxlength="50" class=" base" type="text" value="${formObject.cuttingDepth}" name="cuttingDepth" />
	                 			</td>
	                 			<td style="border-left: none;text-align: right;padding-right: 10px;">
	                 				米
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title">
	                 				上&nbsp;&nbsp;部
	                 			</td>
	                 			<td colspan="2">
	                 				<input maxlength="50" class=" base" type="text" value="${formObject.conUpsite}" name="conUpsite" />
	                 			</td>
	                 			<td class="title">
	                 				基坑开挖面积
	                 			</td>
	                 			<td style="border-right: none;">
	                 				<input maxlength="50" class=" base" type="text" value="${formObject.cuttingSpace}" name="cuttingSpace" />
	                 			</td>
	                 			<td style="border-left: none;text-align: right;padding-right: 10px;">
	                 				平方米
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td colspan="3" class="title">
	                 				其他与承包人资质证书许可的<br/>承包范围相关的技术指标
	                 			</td>
	                 			<td colspan="5">
	                 				<input maxlength="50" class=" base" type="text" value="${formObject.relSpecifications}" name="relSpecifications" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td colspan="2" class="title">
	                 				建设用地批准书证号
	                 			</td>
	                 			<td colspan="2">
	                 				<input maxlength="50" class=" base" type="text" value="${formObject.landCerId}" name="landCerId" />
	                 			</td>
	                 			<td colspan="2" class="title">
	                 				建设工程规划许可证号
	                 			</td>
	                 			<td colspan="2">
	                 				<input maxlength="50" class=" base" type="text" value="${formObject.planCerId}" name="planCerId" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td colspan="2" class="title">
	                 				施&nbsp;工&nbsp;图&nbsp;审&nbsp;查&nbsp;合&nbsp;格&nbsp;号
	                 			</td>
	                 			<td colspan="2">
	                 				<input maxlength="50" class=" base" type="text" value="${formObject.workingPlanQuaId}" name="workingPlanQuaId" />
	                 			</td>
	                 			<td colspan="2" class="title">
	                 				整&nbsp;顿&nbsp;办&nbsp;批&nbsp;准&nbsp;文&nbsp;件&nbsp;号
	                 			</td>
	                 			<td colspan="2">
	                 				<input maxlength="50" class=" base" type="text" value="${formObject.ratifyFileId}" name="ratifyFileId" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td colspan="2" class="title">
	                 				涉外安全审查意见表号
	                 			</td>
	                 			<td colspan="2">
	                 				<input maxlength="50" class=" base" type="text" value="${formObject.feedbackFormId}" name="feedbackFormId" />
	                 			</td>
	                 			<td colspan="2" class="title">
	                 				发&nbsp;&nbsp;包&nbsp;&nbsp;形&nbsp;&nbsp;式
	                 			</td>
	                 			<td colspan="2" style="padding-left: 10px;height: 50px;">
	                 				<label class="checkbox checkbox-inline m-l-15">
										<input class="base" type="checkbox" value="A" name="biddingTypes" <c:if test="${formObject.biddingTypes.contains('A')}"> checked </c:if> />
										<i class="input-helper"></i>
										直接发包
									</label>
									<label class="checkbox checkbox-inline m-l-15">
										<input class="base" type="checkbox" value="B" name="biddingTypes" <c:if test="${formObject.biddingTypes.contains('B')}"> checked </c:if> />
										<i class="input-helper"></i>
										邀请招标
									</label>
									<br/>
									<label class="checkbox checkbox-inline m-l-15">
										<input class="base" type="checkbox" value="C" name="biddingTypes" <c:if test="${formObject.biddingTypes.contains('C')}"> checked </c:if> />
										<i class="input-helper"></i>
										公开招标
									</label>
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td colspan="2" class="title">
	                 				保&nbsp;&nbsp;&nbsp;&nbsp;函
	                 			</td>
	                 			<td colspan="6">
	                 				<label class="checkbox checkbox-inline m-l-25">
										<input class="base" type="checkbox" value="A" name="backletter" <c:if test="${formObject.backletter.contains('A')}"> checked </c:if> />
										<i class="input-helper"></i>
										支付保函
									</label>
									<label class="checkbox checkbox-inline m-l-25">
										<input class="base" type="checkbox" value="B" name="backletter" <c:if test="${formObject.backletter.contains('B')}"> checked </c:if> />
										<i class="input-helper"></i>
										履约保函
									</label>
									<label class="checkbox checkbox-inline m-l-25">
										<input class="base" type="checkbox" value="C" name="backletter" <c:if test="${formObject.backletter.contains('C')}"> checked </c:if> />
										<i class="input-helper"></i>
										工资支付保函
									</label>
	                 			</td>
	                 		</tr>
	                 	</tbody>
	                </table>
	                <br/><br/>
	                <table class="editTable" style="width: 960px;" border="0" cellspacing="0" cellpadding="0">
	                	<tbody>
	                 		<tr>
	                 			<td colspan="7" class="title" style="font-size: 14px;font-weight: bolder;">
	                 				项目质量、安全管理机构
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td rowspan="7" id="changeBuiRows" class="title" style="width: 5%;">
	                 				建<br/>设<br/>单<br/>位
	                 			</td>
	                 			<td colspan="2" rowspan="2" class="title" style="width: 21%;" >
	                 				单位名称（公章）
	                 			</td>
	                 			<td colspan="2" rowspan="2" style="width: 32%;">
	                 				<input maxlength="50" id="buildNameB" class="buildName" onkeyup="writeBuildName(this)" onblur="blurBuildName(this)" type="text" value="${formObject.ratifyFileId}"/>
	                 			</td>
	                 			<td class="title" style="width: 16%;">
	                 				法&nbsp;定&nbsp;代&nbsp;表&nbsp;人
	                 			</td>
	                 			<td style="width: 26%;">
	                 				<input maxlength="50" class=" base" type="text" value="${formObject.buildLegal}" name="buildLegal" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title">
	                 				单位联系电话
	                 			</td>
	                 			<td>
	                 				<input maxlength="15" class=" base phone" type="text" value="${formObject.buildUnitLinktel}" name="buildUnitLinktel" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td colspan="2" rowspan="2" class="title">
	                 				项&nbsp;目&nbsp;负&nbsp;责&nbsp;人
	                 			</td>
	                 			<td class="title">
	                 				姓&nbsp;&nbsp;&nbsp;&nbsp;名
	                 			</td>
	                 			<td>
	                 				<input maxlength="50" class=" base" type="text" value="${formObject.buildPrincipal}" name="buildPrincipal" />
	                 			</td>
	                 			<td rowspan="2" class="title">
	                 				联&nbsp;系&nbsp;电&nbsp;话
	                 			</td>
	                 			<td rowspan="2">
	                 				<input maxlength="15" class=" base phone" type="text" value="${formObject.buildPriLinktel}" name="buildPriLinktel" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title" style="width: 16%;">
	                 				职&nbsp;&nbsp;&nbsp;&nbsp;务
	                 			</td>
	                 			<td style="width: 16%;">
	                 				<input maxlength="50" class=" base" type="text" value="${formObject.buildPriDuty}" name="buildPriDuty" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td colspan="6" class="title">
	                 				送样见证人<br/>（须是建设单位驻工地代表或建设单位委托的监理单位驻工地监理员）
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td colspan="2" class="title">
	                 				姓名
	                 			</td>
	                 			<td colspan="2" class="title">
	                 				见证员证编号
	                 			</td>
	                 			<td class="title">
	                 				职务
	                 			</td>
	                 			<td class="title">
	                 				联系电话
	                 			</td>
	                 		</tr>
	                 		<tr id="bui" class="bui" name="buiTr">
	                 			<td>
	                 				<button type="button" onclick="addBuiRow('bui')" class="btn btn-primary">
										<i class="md add"></i>
									</button>
	                 			</td>
	                 			<td>
	                 				<input maxlength="50" type="text"  name="bName"></input>
	                 			</td>
	                 			<td colspan="2">
	                 				<input maxlength="50" type="text"  name="bPapersId"></input>
	                 			</td>
	                 			<td>
	                 				<input maxlength="50" type="text"  name="bDuty"></input>
	                 			</td>
	                 			<td>
	                 				<input maxlength="15" type="text" class=" phone" name="bLinktel"></input>
	                 			</td>
	                 		</tr>
	                 		<c:forEach items="${formObject.buiList}" var="bui" varStatus="i">
	                 			<c:if test="${i.index == 0}">
	                 				<tr id="bui" class="bui">
	                 					<td style="display: none;">
	                 						<input type="hidden" name="bId" value="${bui.bId}" />
	                 					</td>
			                 			<td>
			                 				<button type="button" onclick="addBuiRow('bui')" class="btn btn-primary">
												<i class="md add"></i>
											</button>
											<input type="hidden" name="zId" value="${bui.zId}" />
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="bName" value="${bui.bName}"></input>
			                 			</td>
			                 			<td colspan="2">
			                 				<input maxlength="50" type="text"  name="bPapersId" value="${bui.bPapersId}"></input>
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="bDuty" value="${bui.bDuty}"></input>
			                 			</td>
			                 			<td>
			                 				<input maxlength="15" type="text" class=" phone" name="bLinktel" value="${bui.bLinktel}"></input>
			                 			</td>
			                 		</tr>
	                 			</c:if>
	                 			<c:if test="${i.index > 0}">
			    					<tr id="bui${bui.bId}" class="bui">
					    				<td style="display: none;">
											<input type="hidden" name="bId" value="${bui.bId}" />
										</td>
										<td style="border-right: none;">
											<button onclick="removeBuiRow('${bui.bId}')" class="btn btn-danger">
												<i class="md minus"></i>
											</button>
											<input type="hidden" name="zId" value="${bui.zId}" />
										</td>
										<td>
			                 				<input maxlength="50" type="text"  name="bName" value="${bui.bName}"></input>
			                 			</td>
			                 			<td colspan="2">
			                 				<input maxlength="50" type="text"  name="bPapersId" value="${bui.bPapersId}"></input>
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="bDuty" value="${bui.bDuty}"></input>
			                 			</td>
			                 			<td>
			                 				<input maxlength="15" type="text" class=" phone" name="bLinktel" value="${bui.bLinktel}"></input>
			                 			</td>
									</tr>
								</c:if>
	                 		</c:forEach>
	                 		<tr>
	                 			<td rowspan="5" class="title">
	                 				施<br/>工<br/>总<br/>承<br/>包<br/>单<br/>位
	                 			</td>
	                 			<td colspan="2" rowspan="2" class="title">
	                 				单位名称（公章）
	                 			</td>
	                 			<td colspan="2" rowspan="2">
	                 				<input maxlength="50" class=" base" type="text" value="${formObject.constructName}" name="constructName" />
	                 			</td>
	                 			<td class="title">
	                 				资质证书编号
	                 			</td>
	                 			<td>
	                 				<input maxlength="50" class=" base" type="text" value="${formObject.conLicenseId}" name="conLicenseId" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title">
	                 				单位联系电话
	                 			</td>
	                 			<td>
	                 				<input maxlength="15" class=" base phone" type="text" value="${formObject.conUnitLinktel}" name="conUnitLinktel" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td colspan="2" class="title">
	                 				安全生产许可证
	                 			</td>
	                 			<td class="title">
	                 				编&nbsp;&nbsp;&nbsp;&nbsp;号
	                 			</td>
	                 			<td>
		                 			<input maxlength="50" class=" base" type="text" value="${formObject.conSafetyQsId}" name="conSafetyQsId" />
		                 		</td>
		                 		<td class="title">
		                 			有&nbsp;效&nbsp;期&nbsp;限
		                 		</td>
		                 		<td style="padding-left: 10px;">
		                 			至：
	                 				<input class=" base" type="text" style="border:none;width: 150px;height: auto;" 
			                			onClick="WdatePicker()" placeholder="点击填日期" 
			                			value='<fmt:formatDate value="${formObject.conExpiryDate}" 
			                			pattern="yyyy-MM-dd" />' name="conExpiryDate" />
		                 		</td>
	                 		</tr>
	                 		<tr>
	                 			<td colspan="2" rowspan="2" class="title">
	                 				注&nbsp;册&nbsp;建&nbsp;造&nbsp;师<br/>（项目经理）
	                 			</td>
	                 			<td class="title">
	                 				姓&nbsp;&nbsp;&nbsp;&nbsp;名
	                 			</td>
	                 			<td>
		                 			<input maxlength="50" class=" base" type="text" value="${formObject.conConstructor}" name="conConstructor" />
		                 		</td>
		                 		<td class="title">
		                 			注册证书编号
		                 		</td>
		                 		<td>
		                 			<input maxlength="50" class=" base" type="text" value="${formObject.conConCerId}" name="conConCerId" />
		                 		</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title">
	                 				专&nbsp;&nbsp;&nbsp;&nbsp;业
	                 			</td>
	                 			<td>
		                 			<input maxlength="50" class=" base" type="text" value="${formObject.conConMajor}" name="conConMajor" />
		                 		</td>
		                 		<td class="title">
		                 			联&nbsp;系&nbsp;电&nbsp;话
		                 		</td>
		                 		<td>
		                 			<input maxlength="15" class=" base phone" type="text" value="${formObject.conConLinktel}" name="conConLinktel" />
		                 		</td>
	                 		</tr>
	                 		<tr>
	                 			<td rowspan="2" class="title" id="changeConRows">
	                 				施<br/>工<br/>专<br/>业<br/>分<br/>包<br/>单<br/>位
	                 			</td>
	                 			<td colspan="4" class="title">
	                 				单位名称
	                 			</td>
	                 			<td class="title">
	                 				分包专业
	                 			</td>
	                 			<td class="title">
	                 				造价
	                 			</td>
	                 		</tr>
	                 		<tr id="con" class="con" name="conTr">
	                 			<td>
	                 				<button type="button" onclick="addConRow('con')" class="btn btn-primary">
										<i class="md add"></i>
									</button>
	                 			</td>
	                 			<td colspan="3">
	                 				<input maxlength="50" type="text"  name="cUnitName"></input>
	                 			</td>
	                 			<td>
	                 				<input maxlength="50" type="text"  name="cSubMajor"></input>
	                 			</td>
	                 			<td>
	                 				<input maxlength="50" type="text"  name="cCost"></input>
	                 			</td>
	                 		</tr>
	                 		<c:forEach items="${formObject.conList}" var="con" varStatus="i">
	                 			<c:if test="${i.index == 0}">
	                 				<tr id="con" class="con">
	                 					<td style="display: none;">
	                 						<input type="hidden" name="cId" value="${con.cId}" />
	                 					</td>
			                 			<td>
			                 				<button type="button" onclick="addConRow('con')" class="btn btn-primary">
												<i class="md add"></i>
											</button>
											<input type="hidden" name="zId" value="${con.zId}" />
			                 			</td>
			                 			<td colspan="3">
			                 				<input maxlength="50" type="text"  name="cUnitName" value="${con.cUnitName}"></input>
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="cSubMajor" value="${con.cSubMajor}"></input>
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="cCost" value="${con.cCost}"></input>
			                 			</td>
			                 		</tr>
	                 			</c:if>
	                 			<c:if test="${i.index > 0}">
			    					<tr id="con${con.cId}" class="con">
					    				<td style="display: none;">
											<input type="hidden" name="cId" value="${con.cId}" />
										</td>
										<td style="border-right: none;">
											<button onclick="removeConRow('${con.cId}')" class="btn btn-danger">
												<i class="md minus"></i>
											</button>
											<input type="hidden" name="zId" value="${con.zId}" />
										</td>
										<td colspan="3">
			                 				<input maxlength="50" type="text"  name="cUnitName" value="${con.cUnitName}"></input>
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="cSubMajor" value="${con.cSubMajor}"></input>
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="cCost" value="${con.cCost}"></input>
			                 			</td>
									</tr>
								</c:if>
	                 		</c:forEach>
	                 		<tr>
	                 			<td rowspan="7" class="title" id="changeSupRows">
	                 				监<br/>理<br/>单<br/>位
	                 			</td>
	                 			<td colspan="2" rowspan="2" class="title">
	                 				单位名称（公章）
	                 			</td>
	                 			<td colspan="2" rowspan="2">
	                 				<input maxlength="50" class=" base" type="text" value="${formObject.supUnitName}" name="supUnitName" />
	                 			</td>
	                 			<td class="title">
	                 				资质证书编号
	                 			</td>
	                 			<td>
	                 				<input maxlength="50" class=" base" type="text" value="${formObject.supLicenseId}" name="supLicenseId" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title">
	                 				单位联系电话
	                 			</td>
	                 			<td>
	                 				<input maxlength="15" class=" base phone" type="text" value="${formObject.supUnitLinktel}" name="supUnitLinktel" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td colspan="2" rowspan="2" class="title">
	                 				项&nbsp;&nbsp;目&nbsp;&nbsp;总&nbsp;&nbsp;监<br/>（加盖总监章）
	                 			</td>
	                 			<td class="title">
	                 				姓&nbsp;&nbsp;&nbsp;&nbsp;名
	                 			</td>
	                 			<td>
		                 			<input maxlength="50" class=" base" type="text" value="${formObject.supDirector}" name="supDirector" />
		                 		</td>
		                 		<td class="title">
		                 			注册证书编号
		                 		</td>
		                 		<td>
		                 			<input maxlength="50" class=" base" type="text" value="${formObject.supDirCerId}" name="supDirCerId" />
		                 		</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title">
	                 				专&nbsp;&nbsp;&nbsp;&nbsp;业
	                 			</td>
	                 			<td>
		                 			<input maxlength="50" class=" base" type="text" value="${formObject.supDirMajor}" name="supDirMajor" />
		                 		</td>
		                 		<td class="title">
		                 			联&nbsp;系&nbsp;电&nbsp;话
		                 		</td>
		                 		<td>
		                 			<input maxlength="15" class=" base phone" type="text" value="${formObject.supDirLinktel}" name="supDirLinktel" />
		                 		</td>
	                 		</tr>
	                 		<tr>
	                 			<td colspan="6" class="title">
	                 				项目主要监理人员
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td colspan="2" class="title">
	                 				姓&nbsp;&nbsp;&nbsp;&nbsp;名
	                 			</td>
	                 			<td class="title">
	                 				专&nbsp;&nbsp;&nbsp;&nbsp;业
	                 			</td>
	                 			<td class="title">
	                 				职&nbsp;&nbsp;&nbsp;&nbsp;称
	                 			</td>
	                 			<td class="title">
	                 				编&nbsp;&nbsp;&nbsp;&nbsp;号
	                 			</td>
	                 			<td class="title">
	                 				联&nbsp;&nbsp;系&nbsp;&nbsp;电&nbsp;&nbsp;话
	                 			</td>
	                 		</tr>
	                 		<tr id="sup" class="sup" name="supTr">
	                 			<td>
	                 				<button type="button" onclick="addSupRow('sup')" class="btn btn-primary">
										<i class="md add"></i>
									</button>
	                 			</td>
	                 			<td>
	                 				<input maxlength="50" type="text"  name="sName"></input>
	                 			</td>
	                 			<td>
	                 				<input maxlength="50" type="text"  name="sMajor"></input>
	                 			</td>
	                 			<td>
	                 				<input maxlength="50" type="text"  name="sTitle"></input>
	                 			</td>
	                 			<td>
	                 				<input maxlength="50" type="text"  name="sNo"></input>
	                 			</td>
	                 			<td>
	                 				<input maxlength="15" type="text" class=" phone" name="sLinktel"></input>
	                 			</td>
	                 		</tr>
	                 		<c:forEach items="${formObject.supList}" var="sup" varStatus="i">
	                 			<c:if test="${i.index == 0}">
	                 				<tr id="sup" class="sup">
	                 					<td style="display: none;">
	                 						<input type="hidden" name="sId" value="${sup.sId}" />
	                 					</td>
			                 			<td>
			                 				<button type="button" onclick="addSupRow('sup')" class="btn btn-primary">
												<i class="md add"></i>
											</button>
											<input type="hidden" name="zId" value="${sup.zId}" />
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="sName" value="${sup.sName}" ></input>
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="sMajor" value="${sup.sMajor}"></input>
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="sTitle" value="${sup.sTitle}"></input>
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="sNo" value="${sup.sNo}"></input>
			                 			</td>
			                 			<td>
			                 				<input maxlength="15" type="text" class=" phone" name="sLinktel" value="${sup.sLinktel}"></input>
			                 			</td>
			                 		</tr>
	                 			</c:if>
	                 			<c:if test="${i.index > 0}">
			    					<tr id="sup${sup.sId}" class="sup">
					    				<td style="display: none;">
											<input type="hidden" name="sId" value="${sup.sId}" />
										</td>
										<td style="border-right: none;">
											<button onclick="removeSupRow('${sup.sId}')" class="btn btn-danger">
												<i class="md minus"></i>
											</button>
											<input type="hidden" name="zId" value="${sup.zId}" />
										</td>
										<td>
			                 				<input maxlength="50" type="text"  name="sName" value="${sup.sName}" ></input>
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="sMajor" value="${sup.sMajor}"></input>
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="sTitle" value="${sup.sTitle}"></input>
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="sNo" value="${sup.sNo}"></input>
			                 			</td>
			                 			<td>
			                 				<input maxlength="15" type="text" class=" phone" name="sLinktel" value="${sup.sLinktel}"></input>
			                 			</td>
									</tr>
								</c:if>
	                 		</c:forEach>
	                 		<tr>
	                 			<td rowspan="6" class="title">
	                 				设<br/>计<br/>单<br/>位
	                 			</td>
	                 			<td colspan="2" rowspan="2" class="title">
	                 				单&nbsp;&nbsp;位&nbsp;&nbsp;名&nbsp;&nbsp;称
	                 			</td>
	                 			<td colspan="2" rowspan="2">
	                 				<input maxlength="50" class=" base" type="text" value="${formObject.designUnitName}" name="designUnitName" />
	                 			</td>
	                 			<td class="title">
	                 				资质证书编号
	                 			</td>
	                 			<td>
	                 				<input maxlength="50" class=" base" type="text" value="${formObject.desLicenseId}" name="desLicenseId" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title">
	                 				单位联系电话
	                 			</td>
	                 			<td>
	                 				<input maxlength="15" class=" base phone" type="text" value="${formObject.desUnitLinktel}" name="desUnitLinktel" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td colspan="2" rowspan="2" class="title">
	                 				注&nbsp;册&nbsp;建&nbsp;筑&nbsp;师
	                 			</td>
	                 			<td class="title">
	                 				姓&nbsp;&nbsp;&nbsp;&nbsp;名
	                 			</td>
	                 			<td>
		                 			<input maxlength="50" class=" base" type="text" value="${formObject.desArchitect}" name="desArchitect" />
		                 		</td>
		                 		<td class="title">
		                 			注册证书编号
		                 		</td>
		                 		<td>
		                 			<input maxlength="50" class=" base" type="text" value="${formObject.desArcCerId}" name="desArcCerId" />
		                 		</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title">
	                 				专&nbsp;&nbsp;&nbsp;&nbsp;业
	                 			</td>
	                 			<td>
		                 			<input maxlength="50" class=" base" type="text" value="${formObject.desArcMajor}" name="desArcMajor" />
		                 		</td>
		                 		<td class="title">
		                 			联&nbsp;系&nbsp;电&nbsp;话
		                 		</td>
		                 		<td>
		                 			<input maxlength="15" class=" base phone" type="text" value="${formObject.desArcLinktel}" name="desArcLinktel" />
		                 		</td>
	                 		</tr>
	                 		<tr>
	                 			<td colspan="2" rowspan="2" class="title">
	                 				注&nbsp;册&nbsp;结&nbsp;构&nbsp;师
	                 			</td>
	                 			<td class="title">
	                 				姓&nbsp;&nbsp;&nbsp;&nbsp;名
	                 			</td>
	                 			<td>
		                 			<input maxlength="50" class=" base" type="text" value="${formObject.desEngineer}" name="desEngineer" />
		                 		</td>
		                 		<td class="title">
		                 			注册证书编号
		                 		</td>
		                 		<td>
		                 			<input maxlength="50" class=" base" type="text" value="${formObject.desEngCerId}" name="desEngCerId" />
		                 		</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title">
	                 				专&nbsp;&nbsp;&nbsp;&nbsp;业
	                 			</td>
	                 			<td>
		                 			<input maxlength="50" class=" base" type="text" value="${formObject.desEngMajor}" name="desEngMajor" />
		                 		</td>
		                 		<td class="title">
		                 			联&nbsp;系&nbsp;电&nbsp;话
		                 		</td>
		                 		<td>
		                 			<input maxlength="15" class=" base phone" type="text" value="${formObject.desEngLinktel}" name="desEngLinktel" />
		                 		</td>
	                 		</tr>
	                 		<tr>
	                 			<td rowspan="4" class="title">
	                 				勘<br/>察<br/>单<br/>位
	                 			</td>
	                 			<td colspan="2" rowspan="2" class="title">
	                 				单&nbsp;&nbsp;位&nbsp;&nbsp;名&nbsp;&nbsp;称
	                 			</td>
	                 			<td colspan="2" rowspan="2">
	                 				<input maxlength="50" class=" base" type="text" value="${formObject.exploreUnitName}" name="exploreUnitName" />
	                 			</td>
	                 			<td class="title">
	                 				资质证书编号
	                 			</td>
	                 			<td>
	                 				<input maxlength="50" class=" base" type="text" value="${formObject.expLicenseId}" name="expLicenseId" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title">
	                 				单位联系电话
	                 			</td>
	                 			<td>
	                 				<input maxlength="15" class=" base phone" type="text" value="${formObject.expUnitLinktel}" name="expUnitLinktel" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td colspan="2" rowspan="2" class="title">
	                 				注册岩石工程师
	                 			</td>
	                 			<td class="title">
	                 				姓&nbsp;&nbsp;&nbsp;&nbsp;名
	                 			</td>
	                 			<td>
		                 			<input maxlength="50" class=" base" type="text" value="${formObject.expEngineer}" name="expEngineer" />
		                 		</td>
		                 		<td class="title">
		                 			注册证书编号
		                 		</td>
		                 		<td>
		                 			<input maxlength="50" class=" base" type="text" value="${formObject.expEngCerId}" name="expEngCerId" />
		                 		</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title">
	                 				专&nbsp;&nbsp;&nbsp;&nbsp;业
	                 			</td>
	                 			<td>
		                 			<input maxlength="50" class=" base" type="text" value="${formObject.expEngMajor}" name="expEngMajor" />
		                 		</td>
		                 		<td class="title">
		                 			联&nbsp;系&nbsp;电&nbsp;话
		                 		</td>
		                 		<td>
		                 			<input maxlength="15" class=" base phone" type="text" value="${formObject.expEngLinktel}" name="expEngLinktel" />
		                 		</td>
	                 		</tr>
	                 	</tbody>
	                </table>
		            <div class="btn-demo text-center col-xs-12">
		            	<input type="hidden" id="jsonDataObj" name="jsonDataObj" />
					    <input type="hidden" id="jsonDataBuiList" name="jsonDataBuiList" />
						<input type="hidden" id="jsonDataConList" name="jsonDataConList" />
						<input type="hidden" id="jsonDataSupList" name="jsonDataSupList" />
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
	    	</div>
	    </form>
	</div>
</body>