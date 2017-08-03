<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>珠海市建设用地申请表</title>
    <meta name="decorator" content="blank" />
    <style>
		.editTable input[type='text'].has-error {
			background: #F44336;
			color: #FFF;
		}
	</style>
    <script>
    	$(document).ready(function(){
	    	$("#gtJsydSqbForm").validate({
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
	    	$("#gtJsydSqbForm").validate({
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
			var buiList = [];
			$(".bui").each(function (){
				var bui = {};
				$(this).find('td').each(function (){
					bui[$(this).find('input').attr('name')] = $(this).find('input').val();
				});
				buiList.push(bui);
			});
			var buiJsonData = JSON.stringify(buiList);
			$("#jsonDataBuiList").val(buiJsonData);
			
			var metList = [];
			$(".met").each(function (){
				var met = {};
				$(this).find('td').each(function (){
					met[$(this).find('input').attr('name')] = $(this).find('input').val();
				});
				metList.push(met);
			});
			var metJsonData = JSON.stringify(metList);
			$("#jsonDataMetList").val(metJsonData);
			
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
			if($("#gtJsydSqbForm").valid()){
				$("#gtJsydSqbForm").submit();
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
		function addMetRow(id){
			var tr = $('#'+id).clone();
			$(tr).removeAttr('id');
			$(tr).find('input').val('');
			//去掉只读类型
			$(tr).find('input').attr("readonly",false);
			var metList = "${formObject.metList}";
			if(metList){
				var content = $('.'+id+':last').find('td:eq(2)').text().trim();
				var num = content.substring(0,content.length-1);
				$(tr).find('td:eq(2)').text(eval(eval(num)+1)+"、");
			}else{
				var content = $('.'+id+':last').find('td:eq(1)').text().trim();
				var num = content.substring(0,content.length-1);
				$(tr).find('td:eq(1)').text(eval(eval(num)+1)+"、");
			}
			$(tr).find('button').removeAttr('onclick').removeClass('btn-primary').addClass('btn-danger').bind("click", function(){
				var rows = $("#changeMetRows").prop("rowspan");
				$("#changeMetRows").attr("rowspan",rows-1);
				$(this).parents('tr').remove();
			});
			$(tr).find('button i.md').removeClass('add').addClass('minus');
			var rows = $("#changeMetRows").prop("rowspan");
			$("#changeMetRows").attr("rowspan",rows+1);
			$('.'+id+':last').after(tr);
		}
		function removeBuiRow(id){
			var rows = $("#changeBuiRows").prop("rowspan");
			$("#changeBuiRows").attr("rowspan",rows-1);
			$("#"+id).remove();
		}
		function removeMetRow(id){
			var rows = $("#changeMetRows").prop("rowspan");
			$("#changeMetRows").attr("rowspan",rows-1);
			$("#"+id).remove();
		}
		$(function (){
			var buiList = "${formObject.buiList}";
			if(buiList.length > 0){
				$('tr[name="buiTr"]').remove();
			}
			var flag = 0;
			$(".bui").each(function (){
				flag++;
			});
			var rows = $("#changeBuiRows").prop("rowspan");
			$("#changeBuiRows").attr("rowspan",rows+flag-1);
			
			var metList = "${formObject.metList}";
			if(metList.length > 0){
				$('tr[name="metTr"]').remove();
			}
			var flg = 0;
			$(".met").each(function (){
				flg++;
			});
			var rows = $("#changeMetRows").prop("rowspan");
			$("#changeMetRows").attr("rowspan",rows+flg-1);
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
	    <form id="gtJsydSqbForm" action="${ctx}/gtJsydSqb/save" method="post">
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
	                 				珠海市建设用地申请表
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title" colspan="3">
	                 				建设项目名称
	                 			</td>
	                 			<td colspan="7">
	                 				<input maxlength="200" class="required base" type="text" name="prjName" value="${formObject.prjName}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title" colspan="3">
	                 				建设项目地址
	                 			</td>
	                 			<td colspan="7">
	                 				<input maxlength="50" class=" base" type="text" name="prjAddr" value="${formObject.prjAddr}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title" rowspan="4" width="40px">
	                 				申<br/>请<br/>人
	                 			</td>
	                 			<td rowspan="3" class="title" colspan="2">
	                 				名称
	                 			</td>
	                 			<td colspan="3" rowspan="2">
	                 				<input maxlength="50" class=" base" type="text" name="applyName" value="${formObject.applyName}" />
	                 			</td>
	                 			<td class="title">
	                 				地址
	                 			</td>
	                 			<td colspan="3">
	                 				<input maxlength="50" class=" base" type="text" name="applyAddr" value="${formObject.applyAddr}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title">
	                 				电话
	                 			</td>
	                 			<td>
	                 				<input maxlength="15" class=" base phone" type="text" name="applyTel" value="${formObject.applyTel}" />
	                 			</td>
	                 			<td class="title">
	                 				邮政编码
	                 			</td>
	                 			<td>
	                 				<input maxlength="20" class=" base" type="text" name="applyPostcode" value="${formObject.applyPostcode}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td colspan="3"  class="title">
	                 				组织机构代码或自然人身份证号码
	                 			</td>
	                 			<td colspan="4">
	                 				<input maxlength="50" class=" base" type="text" name="applyOrgId" value="${formObject.applyOrgId}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title" colspan="2">
	                 				受委托人
	                 			</td>
	                 			<td colspan="2">
	                 				<input maxlength="50" class=" base" type="text" name="trustee" value="${formObject.trustee}" />
	                 			</td>
	                 			<td class="title">
	                 				联系电话
	                 			</td>
	                 			<td style="text-align: right;border-right: none;">
	                 				办公：
	                 			</td>
	                 			<td style="border-left: none;border-right: none;">
	                 				<input maxlength="15" class=" base phone" type="text" name="trusteeOa" value="${formObject.trusteeOa}" />
	                 			</td>
	                 			<td style="text-align: right;border-left: none;border-right: none;">
	                 				手机：
	                 			</td>
	                 			<td style="border-left: none;">
	                 				<input maxlength="15" class=" base mobile" type="text" name="trusteeMobile" value="${formObject.trusteeMobile}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td rowspan="2" class="title">
	                 				业务类别
	                 			</td>
	                 			<td rowspan="2" class="title">
	                 				用地类
	                 			</td>
	                 			<td colspan="2" class="title">
	                 				业务性质
	                 			</td>
	                 			<td colspan="6" class="title">
	                 				申请建设用地
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td colspan="2" class="title">
	                 				业务内容
	                 			</td>
	                 			<td colspan="6" style="padding-left: 10px;height: 80px;">
	                 				<label class="checkbox checkbox-inline m-r-10 m-l-10">
										<input type="checkbox" class="base" value="A" name="businessContent" <c:if test="${formObject.businessContent.contains('A')}"> checked </c:if> />
										<i class="input-helper"></i>    
										新征用地办理《建设用地批准书》
									</label>
									<br/>
									<label class="checkbox checkbox-inline m-r-10 m-l-10">
										<input type="checkbox" class="base" value="B" name="businessContent" <c:if test="${formObject.businessContent.contains('B')}"> checked </c:if> />
										<i class="input-helper"></i>    
										换发《建设用地批准书》
									</label>
									<label class="checkbox checkbox-inline m-r-10 m-l-10">
										<input type="checkbox" class="base" value="C" name="businessContent" <c:if test="${formObject.businessContent.contains('C')}"> checked </c:if> />
										<i class="input-helper"></i>    
										历史用地补办《建设用地批准书》
									</label>
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title">
	                 				基本信息
	                 			</td>
	                 			<td colspan="3" class="title">
	                 				建设用地批准书证号
	                 			</td>
	                 			<td colspan="3">
	                 				<input maxlength="50" class=" base" type="text" name="landApproveId" value="${formObject.landApproveId}" />
	                 			</td>
	                 			<td class="title">
	                 				申请用地面积
	                 			</td>
	                 			<td colspan="2">
	                 				<input maxlength="50" class=" base" type="text" name="landSpace" value="${formObject.landSpace}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td rowspan="4" class="title" id="changeBuiRows">
	                 				用地建设规划
	                 			</td>
	                 			<td colspan="3" class="title">
	                 				现状土地类别
	                 			</td>
	                 			<td style="padding-left: 10px" colspan="6">
	                 				<label class="checkbox checkbox-inline m-r-10 m-l-10">
										<input type="checkbox" class="base" value="A" name="landTypes" <c:if test="${formObject.landTypes.contains('A')}"> checked </c:if> />
										<i class="input-helper"></i>    
										旧城区
									</label>
									<label class="checkbox checkbox-inline m-r-10 m-l-10">
										<input type="checkbox" class="base" value="B" name="landTypes" <c:if test="${formObject.landTypes.contains('B')}"> checked </c:if> />
										<i class="input-helper"></i>    
										村庄
									</label>
									<label class="checkbox checkbox-inline m-r-10 m-l-10">
										<input type="checkbox" class="base" value="C" name="landTypes" <c:if test="${formObject.landTypes.contains('C')}"> checked </c:if> />
										<i class="input-helper"></i>    
										荒地
									</label>
									<label class="checkbox checkbox-inline m-r-10 m-l-10">
										<input type="checkbox" class="base" value="D" name="landTypes" <c:if test="${formObject.landTypes.contains('D')}"> checked </c:if> />
										<i class="input-helper"></i>    
										工厂
									</label>
									<label class="checkbox checkbox-inline m-r-10 m-l-10">
										<input type="checkbox" class="base" value="E" name="landTypes" <c:if test="${formObject.landTypes.contains('E')}"> checked </c:if> />
										<i class="input-helper"></i>    
										耕地
									</label>
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td colspan="3" class="title">
	                 				计划批准机关
	                 			</td>
	                 			<td colspan="2">
	                 				<input maxlength="50" class=" base" type="text" name="palnApproveOrgan" value="${formObject.palnApproveOrgan}" />
	                 			</td>
	                 			<td class="title">
	                 				投资总额
	                 			</td>
	                 			<td colspan="3">
	                 				<input maxlength="50" class=" base" type="text" name="grossAssets" value="${formObject.grossAssets}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td colspan="2" class="title">
	                 				建设项目
	                 			</td>
	                 			<td class="title">
	                 				幢数
	                 			</td>
	                 			<td class="title">
	                 				层数
	                 			</td>
	                 			<td class="title">
	                 				建筑面积
	                 			</td>
	                 			<td class="title">
	                 				建设项目
	                 			</td>
	                 			<td class="title">
	                 				幢数
	                 			</td>
	                 			<td class="title">
	                 				层数
	                 			</td>
	                 			<td class="title">
	                 				建筑面积
	                 			</td>
	                 		</tr>
	                 		<tr id="bui" class="bui" name="buiTr">
	                 			<td>
	                 				<button type="button" onclick="addBuiRow('bui')" class="btn btn-primary"><i class="md add"></i></button>
	                 			</td>
	                 			<td>
	                 				<input maxlength="200" type="text"  name="bBuildPrj"/>
	                 			</td>
	                 			<td>
	                 				<input maxlength="50" type="text"  name="bUnm"/>
	                 			</td>
	                 			<td>
	                 				<input maxlength="50" type="text"  name="bFloors"/>
	                 			</td>
	                 			<td>
	                 				<input maxlength="50" type="text"  name="bSpace"/>
	                 			</td>
	                 			<td>
	                 				<input maxlength="200" type="text"  name="bBuildPrjs"/>
	                 			</td>
	                 			<td>
	                 				<input maxlength="50" type="text"  name="bUnms"/>
	                 			</td>
	                 			<td>
	                 				<input maxlength="50" type="text"  name="bFloorss"/>
	                 			</td>
	                 			<td>
	                 				<input maxlength="50" type="text"  name="bSpaces"/>
	                 			</td>
	                 		</tr>
	                 		<c:forEach items="${formObject.buiList}" var="bui" varStatus="i">
	                 			<c:if test="${i.index == 0}">
	                 				<tr id="bui" class="bui">
	                 					<td style="display: none;">
	                 						<input type="hidden" name="bId" value="${bui.bId}" />
	                 					</td>
			                 			<td>
			                 				<button type="button" onclick="addBuiRow('bui')" class="btn btn-primary"><i class="md add"></i></button>
			                 				<input type="hidden" name="gId" value="${bui.gId}" />
			                 			</td>
			                 			<td>
			                 				<input maxlength="200" type="text"  name="bBuildPrj" value="${bui.bBuildPrj}"/>
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="bUnm" value="${bui.bUnm}"/>
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="bFloors" value="${bui.bFloors}"/>
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="bSpace" value="${bui.bSpace}"/>
			                 			</td>
			                 			<td>
			                 				<input maxlength="200" type="text"  name="bBuildPrjs" value="${bui.bBuildPrjs}"/>
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="bUnms" value="${bui.bUnms}"/>
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="bFloorss" value="${bui.bFloorss}"/>
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="bSpaces" value="${bui.bSpaces}"/>
			                 			</td>
			                 		</tr>
	                 			</c:if>
	                 			<c:if test="${i.index > 0}">
	                 				<tr id="bui${bui.bId}" class="bui">
	                 					<td style="display: none;">
	                 						<input type="hidden" name="bId" value="${bui.bId}" />
	                 					</td>
			                 			<td>
			                 				<button type="button" onclick="removeBuiRow('bui${bui.bId}')" class="btn btn-danger"><i class="md minus"></i></button>
			                 				<input type="hidden" name="gId" value="${bui.gId}" />
			                 			</td>
			                 			<td>
			                 				<input maxlength="200" type="text"  name="bBuildPrj" value="${bui.bBuildPrj}"/>
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="bUnm" value="${bui.bUnm}"/>
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="bFloors" value="${bui.bFloors}"/>
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="bSpace" value="${bui.bSpace}"/>
			                 			</td>
			                 			<td>
			                 				<input maxlength="200" type="text"  name="bBuildPrjs" value="${bui.bBuildPrjs}"/>
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="bUnms" value="${bui.bUnms}"/>
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="bFloorss" value="${bui.bFloorss}"/>
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="bSpaces" value="${bui.bSpaces}"/>
			                 			</td>
			                 		</tr>
	                 			</c:if>
	                 		</c:forEach>
	                 		<tr>
	                 			<td rowspan="2" class="title" id="changeMetRows">
	                 				国土资源局历史审批及相关资料
	                 			</td>
	                 			<td colspan="6" class="title" style="height: 120px;">
	                 				所需文件内容（须参照说明事项所需料规定填写）
	                 			</td>
	                 			<td colspan="2" class="title">
	                 				文&nbsp;件&nbsp;编&nbsp;号
	                 			</td>
	                 			<td class="title">
	                 				张数
	                 			</td>
	                 		</tr>
	                 		<tr id="met" class="met" name="metTr">
	                 			<td>
	                 				<button type="button" onclick="addMetRow('met')" class="btn btn-primary"><i class="md add"></i></button>
	                 			</td>
	                 			<td style="text-align: right;border-right: none;">
	                 				1、
	                 			</td>
	                 			<td colspan="4" style="border-left: none;">
	                 				<input readonly type="text"  name="dContent" value="授权委托书（原件）" style="display: inline;" />
	                 			</td>
	                 			<td colspan="2">
	                 				<input maxlength="50" type="text"  name="dCode"/>
	                 			</td>
	                 			<td>
	                 				<input maxlength="50" type="text"  name="dNum"/>
	                 			</td>
	                 		</tr>
	                 		<c:forEach items="${formObject.metList}" var="met" varStatus="i">
	                 			<c:if test="${i.index == 0}">
	                 				<tr id="met" class="met">
			                 			<td>
			                 				<button type="button" onclick="addMetRow('met')" class="btn btn-primary"><i class="md add"></i></button>
			                 				<input type="hidden" value="${met.dId}" name="dId" />
			                 			</td>
			                 			<td style="display: none;">
			                 				<input type="hidden" value="${met.gId}" name="gId" />
			                 			</td>
			                 			<td style="text-align: right;border-right: none;">
			                 				1、
			                 			</td>
			                 			<td colspan="4" style="border-left: none;">
			                 				<input type="text"  name="dContent" value="${met.dContent}" />
			                 			</td>
			                 			<td colspan="2">
			                 				<input maxlength="50" type="text"  name="dCode" value="${met.dCode}"/>
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="dNum" value="${met.dNum}" />
			                 			</td>
			                 		</tr>
	                 			</c:if>
	                 			<c:if test="${i.index > 0}">
	                 				<tr id="met${met.dId}" class="met">
			                 			<td>
			                 				<button type="button" onclick="removeMetRow('met${met.dId}')" class="btn btn-danger"><i class="md minus"></i></button>
			                 				<input type="hidden" value="${met.dId}" name="dId" />
			                 			</td>
			                 			<td style="display: none;">
			                 				<input type="hidden" value="${met.gId}" name="gId" />
			                 			</td>
			                 			<td style="text-align: right;border-right: none;">
			                 				${i.index+1}、
			                 			</td>
			                 			<td colspan="4" style="border-left: none;">
			                 				<input type="text"  name="dContent" value="${met.dContent}" />
			                 			</td>
			                 			<td colspan="2">
			                 				<input maxlength="50" type="text"  name="dCode" value="${met.dCode}"/>
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="dNum" value="${met.dNum}" />
			                 			</td>
			                 		</tr>
	                 			</c:if>
	                 		</c:forEach>
	                 		<tr>
	                 			<td colspan="3" class="title">
	                 				文书接送方式
	                 			</td>
	                 			<td colspan="7" class="title">
	                 				请直接到窗口领取
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title" rowspan="3">
	                 				备<br/>注
	                 			</td>
	                 			<td colspan="5" style="text-indent: 2em;height: 80px;border-bottom: none;padding: 0 10px;">
	                 				       根据有关法律规定，申请人应如实提交有关
	                 				材料和反映真实情况，并对申请材料实质内容的
	                 				真实性负责。以虚报、瞒报、造假等不正当手
	                 				段取得行政许可的，将依法予以撤销。
	                 			</td>
	                 			<td colspan="4" style="text-indent: 2em;height: 80px;border-bottom: none;padding: 0 10px;">
	                 				       我单位已阅知有关备注说明，并承诺对申报资
	                 				料的真实性及数据的准确性（含电子文件与图纸的
	                 				一致性）负责，自愿承担虚报、瞒报、造假等不正
	                 				当而产生的一切法律责任。
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td colspan="5" style="height: 40px;border-top: none;border-bottom: none;"></td>
	                 			<td colspan="2" style="border-right: none;border-top: none;border-bottom: none;"></td>
	                 			<td colspan="2" style="border-left: none;border-top: none;border-bottom: none;">
	                 				（申请单位盖章）
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td colspan="5" style="height: 40px;border-top: none;"></td>
	                 			<td colspan="2" style="border-right: none;border-top: none;"></td>
	                 			<td colspan="2" style="border-left: none;border-top: none;">
	                 				<input class=" base" type="text" 
		                 				onClick="WdatePicker()" placeholder="点击填日期" 
		                 				value='<fmt:formatDate value="${formObject.applyDate}" 
		                 				pattern="yyyy-MM-dd" />' name="applyDate" />
	                 			</td>
	                 		</tr>
	                 	</tbody>
	                 </table>
	                 <input type="hidden" name="jsonDataObj" id="jsonDataObj"/>
	                 <input type="hidden" name="jsonDataBuiList" id="jsonDataBuiList"/>
	                 <input type="hidden" name="jsonDataMetList" id="jsonDataMetList"/>
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