<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>珠海市建设工程（建筑类）规划许可申请表</title>
    <meta name="decorator" content="blank" />
    <style>
		.editTable input[type='text'].has-error {
			background: #F44336;
			color: #FFF;
		}
	</style>
    <script>
    	$(document).ready(function(){
	    	$("#ghJsgcGhxksqbForm").validate({
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
	    	$("#ghJsgcGhxksqbForm").validate({
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
			var desList = [];
			$(".des").each(function (){
				var des = {};
				$(this).find('td').each(function (){
					des[$(this).find('input').attr('name')] = $(this).find('input').val();
				});
				desList.push(des);
			});
			var desJsonData = JSON.stringify(desList);
			$("#jsonDataDesList").val(desJsonData);
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
			if($("#ghJsgcGhxksqbForm").valid()){
				$("#ghJsgcGhxksqbForm").submit();
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
				$(this).parents('tr').remove();
			});
			$(tr).find('button i.md').removeClass('add').addClass('minus');
			var rows = $("#changRows").prop("rowspan");
			$("#changRows").attr("rowspan",rows+1);
			$('.'+id+':last').after(tr);
		}
		function removeRow(id){
			var rows = $("#changRows").prop("rowspan");
			$("#changRows").attr("rowspan",rows-1);
			$("#"+id).remove();
		}
		$(function (){
			var desList = "${formObject.desList}";
			if(desList.length > 0){
				$('tr[name="desTr"]').remove();
			}
			var flag = 0;
			$(".des").each(function (){
				flag++;
			});
			var rows = $("#changRows").prop("rowspan");
			$("#changRows").attr("rowspan",rows+flag-1);
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
	    <form id="ghJsgcGhxksqbForm" action="${ctx}/ghJsgcGhxksqb/save" method="post">
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
	                 				珠海市建设工程（建筑类）规划许可申请表
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td width="35px" rowspan="7" class="title">
	                 				建设单位填写
	                 			</td>
	                 			<td class="title" colspan="2">
	                 				建设项目名称
	                 			</td>
	                 			<td colspan="7">
	                 				<input maxlength="200" class="required base" type="text" name="prjName" value="${formObject.prjName}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title" colspan="2">
	                 				建设项目地址
	                 			</td>
	                 			<td colspan="7">
	                 				<input maxlength="50" class=" base" type="text" name="prjAddr" value="${formObject.prjAddr}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title" colspan="2">
	                 				建设单位名称
	                 			</td>
	                 			<td colspan="3">
	                 				<input maxlength="200" class=" base" type="text" name="company" value="${formObject.company}" />
	                 			</td>
	                 			<td class="title">
	                 				地址
	                 			</td>
	                 			<td colspan="3">
	                 				<input maxlength="200" class=" base" type="text" name="companyAddr" value="${formObject.companyAddr}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title" colspan="2">
	                 				原建设单位名称<br/>更名
	                 			</td>
	                 			<td colspan="3">
	                 				<input maxlength="50" class="base" type="text" name="oriComName" value="${formObject.oriComName}" />
	                 			</td>
	                 			<td class="title">
	                 				电话
	                 			</td>
	                 			<td>
	                 				<input maxlength="15" class=" base phone" type="text" name="companyMphone" value="${formObject.companyMphone}" />
	                 			</td>
	                 			<td class="title">
	                 				邮政编码
	                 			</td>
	                 			<td>
	                 				<input maxlength="20" class=" base" type="text" name="comPostcode" value="${formObject.comPostcode}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title" colspan="2">
	                 				受委托人
	                 			</td>
	                 			<td colspan="2">
	                 				<input maxlength="50" class=" base" type="text" name="agentName" value="${formObject.agentName}" />
	                 			</td>
	                 			<td class="title">
	                 				联系电话
	                 			</td>
	                 			<td style="border-right: none;">
	                 				办公：
	                 			</td>
	                 			<td style="border-left: none;border-right: none;">
	                 				<input maxlength="15" class=" base phone" type="text" name="agentPhone" value="${formObject.agentPhone}" placeholder="点此填写" />
	                 			</td>
	                 			<td style="border-left: none;border-right: none;">
	                 				手机：
	                 			</td>
	                 			<td style="border-left: none;">
	                 				<input maxlength="15" class=" base mobile" type="text" name="agentMphone" value="${formObject.agentMphone}" placeholder="点此填写" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td height="30px" colspan="2" style="padding-left: 10px;">
	                 				<label class="checkbox checkbox-inline m-r-10 m-l-10">
										<input type="checkbox" class="base" value="A" name="isHasHazArt" <c:if test="${formObject.isHasHazArt.contains('A')}"> checked </c:if> />
										<i class="input-helper"></i>    
										有
									</label>
									<label class="checkbox checkbox-inline m-r-10 m-l-10">
										<input type="checkbox" class="base" value="B" name="isHasHazArt" <c:if test="${formObject.isHasHazArt.contains('B')}"> checked </c:if> />
										<i class="input-helper"></i>    
										无
									</label>
	                 			</td>
	                 			<td colspan="7" style="padding-left: 15px">
	                 				污染源或易燃易爆等危险品
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title" colspan="2">
	                 				业务内容
	                 			</td>
	                 			<td colspan="4" style="border-right: none;height: 90px;">
	                 				<%-- 业务内容中的内容根据提供的  代填材料中提供的  信息进行了修改 --%>
	                 				<label class="checkbox checkbox-inline m-r-10 m-l-25">
										<input type="checkbox" class="base" value="A" name="businessContent" <c:if test="${formObject.businessContent.contains('A')}"> checked </c:if> />
										<i class="input-helper"></i>    
										申请《建设工程规划许可证（建筑类）》（新建）
									</label>
									<label class="checkbox checkbox-inline m-r-10 m-l-25">
										<input type="checkbox" class="base" value="C" name="businessContent" <c:if test="${formObject.businessContent.contains('C')}"> checked </c:if> />
										<i class="input-helper"></i>    
										申请《建设工程规划许可证（建筑类）》（临时）
									</label>
									<label class="checkbox checkbox-inline m-r-10 m-l-25">
										<input type="checkbox" class="base" value="E" name="businessContent" <c:if test="${formObject.businessContent.contains('E')}"> checked </c:if> />
										<i class="input-helper"></i>    
										□申请《建设工程规划许可证（建筑类）》（改建、扩建、变更）
									</label>
									<label class="checkbox checkbox-inline m-r-10 m-l-25">
										<input type="checkbox" class="base" value="G" name="businessContent" <c:if test="${formObject.businessContent.contains('G')}"> checked </c:if> />
										<i class="input-helper"></i>    
										申请《建设工程规划许可证（建筑类）》（建筑外立面装修）
									</label>
	                 			</td>
	                 			<td colspan="3" style="border-left: none;">
	                 				<label class="checkbox checkbox-inline m-r-10 m-l-25">
										<input type="checkbox" class="base" value="B" name="businessContent" <c:if test="${formObject.businessContent.contains('B')}"> checked </c:if> />
										<i class="input-helper"></i>    
										申请建筑设计方案审查
									</label>
									<label class="checkbox checkbox-inline m-r-10 m-l-25">
										<input type="checkbox" class="base" value="D" name="businessContent" <c:if test="${formObject.businessContent.contains('D')}"> checked </c:if> />
										<i class="input-helper"></i>    
										申请遗失补办《建设工程规划许可证》
									</label>
	                 				<%-- <label class="checkbox checkbox-inline m-r-10 m-l-25">
										<input type="checkbox" class="base" value="A" name="businessContent" <c:if test="${formObject.businessContent.contains('A')}"> checked </c:if> />
										<i class="input-helper"></i>    
										申请《建设工程规划许可证》（含临时）
									</label>
									<label class="checkbox checkbox-inline m-r-10 m-l-25">
										<input type="checkbox" class="base" value="C" name="businessContent" <c:if test="${formObject.businessContent.contains('C')}"> checked </c:if> />
										<i class="input-helper"></i>    
										申请《建设工程规划许可证》（含临时）续期 
									</label>
									<label class="checkbox checkbox-inline m-r-10 m-l-25">
										<input type="checkbox" class="base" value="E" name="businessContent" <c:if test="${formObject.businessContent.contains('E')}"> checked </c:if> />
										<i class="input-helper"></i>    
										已投入使用的建筑工程申请改建
									</label>
									<label class="checkbox checkbox-inline m-r-10 m-l-25">
										<input type="checkbox" class="base" value="G" name="businessContent" <c:if test="${formObject.businessContent.contains('G')}"> checked </c:if> />
										<i class="input-helper"></i>    
										改建建筑工程申请《建设工程规划许可证》 
									</label>
	                 			</td>
	                 			<td colspan="3" style="border-left: none;">
	                 				<label class="checkbox checkbox-inline m-r-10 m-l-25">
										<input type="checkbox" class="base" value="B" name="businessContent" <c:if test="${formObject.businessContent.contains('B')}"> checked </c:if> />
										<i class="input-helper"></i>    
										建筑设计方案审查
									</label>
									<label class="checkbox checkbox-inline m-r-10 m-l-25">
										<input type="checkbox" class="base" value="D" name="businessContent" <c:if test="${formObject.businessContent.contains('D')}"> checked </c:if> />
										<i class="input-helper"></i>    
										遗失补办《建设工程规划许可证》
									</label>
									<label class="checkbox checkbox-inline m-r-10 m-l-25">
										<input type="checkbox" class="base" value="F" name="businessContent" <c:if test="${formObject.businessContent.contains('F')}"> checked </c:if> />
										<i class="input-helper"></i>    
										《建设工程规划许可证》更名
									</label>
									<label class="checkbox checkbox-inline m-r-10 m-l-25">
										<input type="checkbox" class="base" value="H" name="businessContent" <c:if test="${formObject.businessContent.contains('H')}"> checked </c:if> />
										<i class="input-helper"></i>    
										调整《建设工程规划许可证》
									</label> --%>
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td rowspan="8" class="title" id="changRows">
	                 				设计单位填写
	                 			</td>
	                 			<td class="title" colspan="2">
	                 				单位名称
	                 			</td>
	                 			<td colspan="7">
	                 				<input maxlength="50" class=" base" type="text" name="designName" value="${formObject.designName}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title" colspan="2">
	                 				法人代表
	                 			</td>
	                 			<td colspan="2">
	                 				<input maxlength="50" class=" base" type="text" name="designLegalEntity" value="${formObject.designLegalEntity}" />
	                 			</td>
	                 			<td class="title">
	                 				电话
	                 			</td>
	                 			<td colspan="4">
	                 				<input maxlength="15" class=" base phone" type="text" name="legalTel" value="${formObject.legalTel}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title" colspan="2">
	                 				设计证书号
	                 			</td>
	                 			<td colspan="7">
	                 				<input maxlength="50" class=" base" type="text" name="legalCertId" value="${formObject.legalCertId}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title" colspan="2">
	                 				注册建筑师
	                 			</td>
	                 			<td colspan="2">
	                 				<input maxlength="50" class=" base" type="text" name="architect" value="${formObject.architect}" />
	                 			</td>
	                 			<td class="title">
	                 				电话
	                 			</td>
	                 			<td>
	                 				<input maxlength="15" class=" base phone" type="text" name="architectTel" value="${formObject.architectTel}" />
	                 			</td>
	                 			<td colspan="3" rowspan="2" style="text-align: center;">
	                 				注&nbsp;&nbsp;册&nbsp;&nbsp;证&nbsp;&nbsp;章
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title" colspan="2">
	                 				注册证号
	                 			</td>
	                 			<td colspan="4">
	                 				<input maxlength="50" class=" base" type="text" name="architectCertId" value="${formObject.architectCertId}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title" rowspan="2" colspan="2">
	                 				送审项目名称<br/>（用&nbsp;&nbsp;途）
	                 			</td>
	                 			<td class="title" rowspan="2">
	                 				栋数
	                 			</td>
	                 			<td class="title" colspan="2">
	                 				层&nbsp;&nbsp;数
	                 			</td>
	                 			<td class="title" rowspan="2" width="100px">
	                 				基底面积<br/>（平方米）
	                 			</td>
	                 			<td class="title" colspan="2">
	                 				建筑面积<br/>（平方米）
	                 			</td>
	                 			<td class="title" rowspan="2">
	                 				备注
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title" width="100px">
	                 				地上
	                 			</td>
	                 			<td class="title" width="100px">
	                 				地下
	                 			</td>
	                 			<td class="title">
	                 				地上
	                 			</td>
	                 			<td class="title">
	                 				地下
	                 			</td>
	                 		</tr>
	                 		<tr id="des" class="des" name="desTr">
	                 			<td>
	                 				<button type="button" onclick="addRow('des')" class="btn btn-primary"><i class="md add"></i></button>
	                 			</td>
	                 			<td width="85px">
	                 				<input maxlength="200" type="text"  name="goOverPrjName"/>
	                 			</td>
	                 			<td>
	                 				<input maxlength="50" type="text"  name="ridgepoleNum"/>
	                 			</td>
	                 			<td>
	                 				<input maxlength="50" type="text"  name="floorsOn"/>
	                 			</td>
	                 			<td>
	                 				<input maxlength="50" type="text"  name="floorsDown"/>
	                 			</td>
	                 			<td>
	                 				<input maxlength="50" type="text"  name="baseSpace"/>
	                 			</td>
	                 			<td>
	                 				<input maxlength="50" type="text"  name="floorAreaOn"/>
	                 			</td>
	                 			<td>
	                 				<input maxlength="50" type="text"  name="floorAreaDown"/>
	                 			</td>
	                 			<td>
	                 				<input maxlength="50" type="text"  name="remark"/>
	                 			</td>
	                 		</tr>
	                 		<c:forEach items="${formObject.desList}" var="des" varStatus="i">
	                 			<c:if test="${i.index == 0}">
	                 				<tr id="des" class="des">
	                 					<td style="display: none;">
	                 						<input type="hidden" name="dId" value="${des.dId}" />
	                 					</td>
			                 			<td>
			                 				<button type="button" onclick="addRow('des')" class="btn btn-primary"><i class="md add"></i></button>
			                 				<input type="hidden" name="gId" value="${des.gId}" />
			                 			</td>
			                 			<td width="85px">
			                 				<input maxlength="200" type="text"  name="goOverPrjName" value="${des.goOverPrjName}"/>
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="ridgepoleNum" value="${des.ridgepoleNum}"/>
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="floorsOn" value="${des.floorsOn}"/>
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="floorsDown" value="${des.floorsDown}"/>
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="baseSpace" value="${des.baseSpace}"/>
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="floorAreaOn" value="${des.floorAreaOn}"/>
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="floorAreaDown" value="${des.floorAreaDown}"/>
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="remark" value="${des.remark}"/>
			                 			</td>
			                 		</tr>
	                 			</c:if>
	                 			<c:if test="${i.index > 0}">
	                 				<tr id="${des.dId}" class="des">
	                 					<td style="display: none;">
	                 						<input type="hidden" name="dId" value="${des.dId}" />
	                 					</td>
			                 			<td>
			                 				<button type="button" onclick="removeRow('${des.dId}')" class="btn btn-danger"><i class="md minus"></i></button>
			                 				<input type="hidden" name="gId" value="${des.gId}" />
			                 			</td>
			                 			<td width="85px">
			                 				<input maxlength="200" type="text"  name="goOverPrjName" value="${des.goOverPrjName}"/>
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="ridgepoleNum" value="${des.ridgepoleNum}"/>
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="floorsOn" value="${des.floorsOn}"/>
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="floorsDown" value="${des.floorsDown}"/>
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="baseSpace" value="${des.baseSpace}"/>
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="floorAreaOn" value="${des.floorAreaOn}"/>
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="floorAreaDown" value="${des.floorAreaDown}"/>
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="remark" value="${des.remark}"/>
			                 			</td>
			                 		</tr>
	                 			</c:if>
	                 		</c:forEach>
	                 		<tr>
	                 			<td class="title" rowspan="3">
	                 				设计指标
	                 			</td>
	                 			<td colspan="2" class="title">
	                 				绿&nbsp;&nbsp;地&nbsp;&nbsp;率
	                 			</td>
	                 			<td colspan="2" style="border-right: none;">
	                 				<input maxlength="50" class=" base" type="text" name="greenbeltRatio" value="${formObject.greenbeltRatio}" />
	                 			</td>
	                 			<td colspan="2" style="border-left: none;">
	                 				%
	                 			</td>
	                 			<td class="title">
	                 				停车位
	                 			</td>
	                 			<td colspan="2">
	                 				<input maxlength="50" class=" base" type="text" name="parkingSpace" value="${formObject.parkingSpace}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td colspan="2" class="title">
	                 				建筑密度
	                 			</td>
	                 			<td colspan="2" style="border-right: none;">
	                 				<input maxlength="50" class=" base" type="text" name="siteCoverage" value="${formObject.siteCoverage}" />
	                 			</td>
	                 			<td colspan="2" style="border-left: none;">
	                 				%
	                 			</td>
	                 			<td class="title">
	                 				容积率
	                 			</td>
	                 			<td colspan="2">
	                 				<input maxlength="50" class=" base" type="text" name="plotRatio" value="${formObject.plotRatio}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td colspan="2" class="title">
	                 				总建筑面积
	                 			</td>
	                 			<td colspan="4" style="border-right: none;">
	                 				<input maxlength="50" class=" base" type="text" name="overallFloorage" value="${formObject.overallFloorage}" />
	                 			</td>
	                 			<td colspan="3" style="border-left: none;">
	                 				（平方米）
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title" rowspan="3">
	                 				备<br/><br/><br/>注
	                 			</td>
	                 			<td colspan="9" style="text-indent: 2em;font-size: 14px;padding-left: 10px;border-bottom: none;height: 80px;">
	                 				我单位（本人）已阅知有关备注说明，并承诺对申报资料的真实性及数据的准确性（含电子
	                 				文件与图纸的一致性）负责，若有任何虚报、瞒报、造假等不正当手段，审批机关可终止审理，
	                 				我单位（本人）自愿承担虚报、瞒报、造假等不正当手段而产生的一切法律责任。
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td colspan="4" style="border-right: none;text-align: right;padding-right: 50px;border-top: none;border-bottom: none;height: 40px;">
	                 				（申请单位盖章）
	                 			</td>
	                 			<td colspan="5" style="border-left: none;text-align: right;padding-right: 50px;border-top: none;border-bottom: none;">
	                 				（建筑设计单位盖章）
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td colspan="3" style="text-align: right;height: 30px;border-right: none;border-top: none;">
	                 				日期：
	                 			</td>
	                 			<td colspan="2" style="border-left: none;border-right: none;border-top: none;">
	                 				<input class=" base" type="text" 
	                 					onClick="WdatePicker()" placeholder="点击填日期" 
		                 				value='<fmt:formatDate value="${formObject.applyDate}" 
		                 				pattern="yyyy-MM-dd" />' name="applyDate" />
	                 			</td>
	                 			<td style="text-align: right;border-left: none;border-right: none;border-top: none;" colspan="2">
	                 				日期：
	                 			</td>
	                 			<td colspan="2" style="border-left: none;border-top: none;">
	                 				<input class=" base" type="text" 
		                 				onClick="WdatePicker()" placeholder="点击填日期" 
		                 				value='<fmt:formatDate value="${formObject.buildingDesignDate}" 
		                 				pattern="yyyy-MM-dd" />' name="buildingDesignDate" />
	                 			</td>
	                 		</tr>
	                 	</tbody>
	                 </table>
	                 <input type="hidden" name="jsonDataObj" id="jsonDataObj"/>
	                 <input type="hidden" name="jsonDataDesList" id="jsonDataDesList"/>
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