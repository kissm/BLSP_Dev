<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>珠海市建设工程（建筑类）规划条件核实申请表</title>
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
	    	$("#ghJsgcGhyssqbForm").validate({
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
	    	$("#ghJsgcGhyssqbForm").validate({
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
			var comList = [];
			$(".com").each(function (){
				var com = {};
				$(this).find('td').each(function (){
					com[$(this).find('input').attr('name')] = $(this).find('input').val();
				});
				comList.push(com);
			});
			var comJsonData = JSON.stringify(comList);
			$("#jsonDataComList").val(comJsonData);
			
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
			if($("#ghJsgcGhyssqbForm").valid()){
				$("#ghJsgcGhyssqbForm").submit();
			}else{
				$(".savebutton").attr("disabled",false);
			}
		}
		function refresh(){
			window.location.reload(true);
		}
		function addComRow(id){
			var tr = $('#'+id).clone();
			$(tr).removeAttr('id');
			$(tr).find('input').val('');
			$(tr).find('button').removeAttr('onclick').removeClass('btn-primary').addClass('btn-danger').bind("click", function(){
				var rows = $("#changeComRows").prop("rowspan");
				$("#changeComRows").attr("rowspan",rows-1);
				$(this).parents('tr').remove();
			});
			$(tr).find('button i.md').removeClass('md-add').addClass('md-clear');
			var rows = $("#changeComRows").prop("rowspan");
			$("#changeComRows").attr("rowspan",rows+1);
			$('.'+id+':last').after(tr);
		}
		function addMetRow(id){
			var tr = $('#'+id).clone();
			$(tr).removeAttr('id');
			$(tr).find('input').val('');
			var content = $('.'+id+':last').find('td:eq(2)').text().trim();
			var num = content.substring(0,content.length-1);
			$(tr).find('td:eq(2)').text(eval(eval(num)+1)+"、");
			$(tr).find('button').removeAttr('onclick').removeClass('btn-primary').addClass('btn-danger').bind("click", function(){
				var rows = $("#changeMetRows").prop("rowspan");
				$("#changeMetRows").attr("rowspan",rows-1);
				$(this).parents('tr').remove();
			});
			$(tr).find('button i.md').removeClass('md-add').addClass('md-clear');
			var rows = $("#changeMetRows").prop("rowspan");
			$("#changeMetRows").attr("rowspan",rows+1);
			$('.'+id+':last').after(tr);
		}
		function removeComRow(id){
			var rows = $("#changeComRows").prop("rowspan");
			$("#changeComRows").attr("rowspan",rows-1);
			$("#"+id).remove();
		}
		function removeMetRow(id){
			var rows = $("#changeMetRows").prop("rowspan");
			$("#changeMetRows").attr("rowspan",rows-1);
			$("#"+id).remove();
		}
		$(function (){
			var comList = "${formObject.comList}";
			if(comList.length > 0){
				$('tr[name="comTr"]').remove();
			}
			var flag = 0;
			$(".com").each(function (){
				flag++;
			});
			var rows = $("#changeComRows").prop("rowspan");
			$("#changeComRows").attr("rowspan",rows+flag-1);
			
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
    		<%-- 该表名称根据代填材料 已经从  《珠海市建设工程（建筑类）规划验收申请表》  修改为 《珠海市建设工程（建筑类）规划条件核实申请表》 后台为处理  --%>
        	<h2>珠海市建设工程（建筑类）规划条件核实申请表<small>您可通过本功能进行珠海市建设工程（建筑类）规划条件核实申请表申报</small></h2>
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
    
	    <form id="ghJsgcGhyssqbForm" action="${ctx}/ghJsgcGhyssqb/save" method="post">
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
	                 			<td class="title" colspan="3">
	                 				建设项目名称
	                 			</td>
	                 			<td colspan="10">
	                 				<input maxlength="200" class="required base" type="text" name="prjName" value="${formObject.prjName}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title" colspan="3">
	                 				<%-- 根据代填中改为新命名 --%>
	                 				<!-- 建设项目地址 -->
	                 				建设位置
	                 			</td>
	                 			<td colspan="10">
	                 				<input maxlength="50" class=" base" type="text" name="prjAddr" value="${formObject.prjAddr}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title" rowspan="3" width="40px">
	                 				申
	                 				<br/>
	                 				请
	                 				<br/>
	                 				人
	                 			</td>
	                 			<td rowspan="2" class="title" colspan="2">
	                 				名称<br/>（姓名）
	                 			</td>
	                 			<td rowspan="2" colspan="5">
	                 				<input maxlength="50" class=" base" type="text" name="proposerName" value="${formObject.proposerName}" />
	                 			</td>
	                 			<td class="title">
	                 				地址
	                 			</td>
	                 			<td colspan="4">
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
	                 			<td class="title" style="text-align: right;border-right: none;width: 50px;padding-right: 0;">
	                 				邮政
	                 			</td>
	                 			<td class="title" style="text-align: left;border-left: none;width: 50px;padding-left: 0;">
	                 				编码
	                 			</td>
	                 			<td>
	                 				<input maxlength="20" class=" base" type="text" name="proposerPostCode" value="${formObject.proposerPostCode}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title" colspan="2">
	                 				受委托人
	                 			</td>
	                 			<td colspan="3">
	                 				<input maxlength="50" class=" base" type="text" name="agentName" value="${formObject.agentName}" />
	                 			</td>
	                 			<td class="title" colspan="2">
	                 				联系电话
	                 			</td>
	                 			<td style="text-align: right;border-right: none;">
	                 				办公：
	                 			</td>
	                 			<td style="border-left: none;border-right: none;">
	                 				<input maxlength="15" class=" base phone" type="text" name="agentPhone" value="${formObject.agentPhone}" placeholder="点此填写" />
	                 			</td>
	                 			<td style="text-align: right;border-left: none;border-right: none;" colspan="2">
	                 				手机：
	                 			</td>
	                 			<td style="border-left: none;">
	                 				<input maxlength="15" class=" base mobile" type="text" name="agentMphone" value="${formObject.agentMphone}" placeholder="点此填写" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td rowspan="3" class="title" id="changeComRows">
	                 				<%-- 根据代填中改为新命名 --%>
	                 				<!-- 竣工
	                 				<br/>
	                 				项目 -->
	                 				申请<br/>范围
	                 			</td>
	                 			<td colspan="3" rowspan="2" class="title">
	                 				项目名称（用途）
	                 			</td>
	                 			<td rowspan="2" class="title">
	                 				栋数
	                 			</td>
	                 			<td class="title" colspan="2">
	                 				层数
	                 			</td>
	                 			<td class="title" colspan="2" rowspan="2">
	                 				基底面积
	                 				<br/>
	                 				(M<sup>2</sup>)
	                 			</td>
	                 			<td class="title" colspan="4">
	                 				建筑面积
	                 				(M<sup>2</sup>)
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title">
	                 				地上
	                 			</td>
	                 			<td class="title">
	                 				地下
	                 			</td>
	                 			<td class="title" colspan="2">
	                 				地上
	                 			</td>
	                 			<td class="title" colspan="2">
	                 				地下
	                 			</td>
	                 		</tr>
	                 		<tr id="com" class="com" name="comTr">
	                 			<td>
	                 				<button type="button" onclick="addComRow('com')" class="btn btn-primary"><i class="md md-add"></i></button>
	                 			</td>
	                 			<td colspan="2">
	                 				<input maxlength="200" type="text"  name="comPrjName"/>
	                 			</td>
	                 			<td>
	                 				<input maxlength="50" type="text"  name="comRidgepoleNum"/>
	                 			</td>
	                 			<td>
	                 				<input maxlength="50" type="text"  name="comFloorsOn"/>
	                 			</td>
	                 			<td>
	                 				<input maxlength="50" type="text"  name="comFloorsDown"/>
	                 			</td>
	                 			<td colspan="2">
	                 				<input maxlength="50" type="text"  name="comBaseArea"/>
	                 			</td>
	                 			<td colspan="2">
	                 				<input maxlength="50" type="text"  name="comCoveredAreaOn"/>
	                 			</td>
	                 			<td colspan="2">
	                 				<input maxlength="50" type="text"  name="comCoveredAreaDown"/>
	                 			</td>
	                 		</tr>
	                 		<c:forEach items="${formObject.comList}" var="com" varStatus="i">
	                 			<c:if test="${i.index == 0}">
	                 				<tr id="com" class="com">
	                 					<td style="display: none;">
	                 						<input type="hidden" name="cId" value="${com.cId}" />
	                 					</td>
			                 			<td>
			                 				<button type="button" onclick="addComRow('com')" class="btn btn-primary"><i class="md md-add"></i></button>
			                 				<input type="hidden" name="gId" value="${com.gId}" />
			                 			</td>
			                 			<td colspan="2">
			                 				<input maxlength="200" type="text"  name="comPrjName" value="${com.comPrjName}" />
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="comRidgepoleNum" value="${com.comRidgepoleNum}" />
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="comFloorsOn" value="${com.comFloorsOn}" />
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="comFloorsDown" value="${com.comFloorsDown}" />
			                 			</td>
			                 			<td colspan="2">
			                 				<input maxlength="50" type="text"  name="comBaseArea" value="${com.comBaseArea}" />
			                 			</td>
			                 			<td colspan="2">
			                 				<input maxlength="50" type="text"  name="comCoveredAreaOn" value="${com.comCoveredAreaOn}" />
			                 			</td>
			                 			<td colspan="2">
			                 				<input maxlength="50" type="text"  name="comCoveredAreaDown" value="${com.comCoveredAreaDown}" />
			                 			</td>
			                 		</tr>
	                 			</c:if>
	                 			<c:if test="${i.index > 0}">
	                 				<tr id="com${com.cId}" class="com">
	                 					<td style="display: none;">
	                 						<input type="hidden" name="cId" value="${com.cId}" />
	                 					</td>
			                 			<td>
			                 				<button type="button" onclick="removeComRow('com${com.cId}')" class="btn btn-danger"><i class="md md-clear"></i></button>
			                 				<input type="hidden" name="gId" value="${com.gId}" />
			                 			</td>
			                 			<td colspan="2">
			                 				<input maxlength="200" type="text"  name="comPrjName" value="${com.comPrjName}" />
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="comRidgepoleNum" value="${com.comRidgepoleNum}" />
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="comFloorsOn" value="${com.comFloorsOn}" />
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="comFloorsDown" value="${com.comFloorsDown}" />
			                 			</td>
			                 			<td colspan="2">
			                 				<input maxlength="50" type="text"  name="comBaseArea" value="${com.comBaseArea}" />
			                 			</td>
			                 			<td colspan="2">
			                 				<input maxlength="50" type="text"  name="comCoveredAreaOn" value="${com.comCoveredAreaOn}" />
			                 			</td>
			                 			<td colspan="2">
			                 				<input maxlength="50" type="text"  name="comCoveredAreaDown" value="${com.comCoveredAreaDown}" />
			                 			</td>
			                 		</tr>
	                 			</c:if>
	                 		</c:forEach>
	                 		<tr>
	                 			<td rowspan="2" class="title" id="changeMetRows">
	                 				<%-- 根据代填中改为新命名 --%>
	                 				<!-- 所需
	                 				<br/>
	                 				材料 -->
	                 				申请<br/>资料
	                 			</td>
	                 			<td colspan="8" class="title">
	                 				<%-- 根据代填中改为新命名 --%>
	                 				<!-- 所&nbsp;需&nbsp;文&nbsp;件&nbsp;内&nbsp;容 -->
	                 				申请资料（须参照所需资料规定填写）
	                 			</td>
	                 			<td colspan="3" class="title">
	                 				文&nbsp;件&nbsp;编&nbsp;号
	                 			</td>
	                 			<td class="title">
	                 				张数
	                 			</td>
	                 		</tr>
	                 		<tr id="met" class="met" name="metTr">
	                 			<td>
	                 				<button type="button" onclick="addMetRow('met')" class="btn btn-primary"><i class="md md-add"></i></button>
	                 			</td>
	                 			<td style="border-right: none;width: 40px;">
	                 				
	                 			</td>
	                 			<td width="40px" style="border-left: none;border-right: none;">
	                 				1、
	                 			</td>
	                 			<td colspan="5" style="border-left: none;">
	                 				<input maxlength="200" type="text"  name="metContent"/>
	                 			</td>
	                 			<td colspan="3">
	                 				<input maxlength="50" type="text"  name="metFileNum"/>
	                 			</td>
	                 			<td>
	                 				<input maxlength="50" type="text"  name="metSheetsNum"/>
	                 			</td>
	                 		</tr>
	                 		<c:forEach items="${formObject.metList}" var="met" varStatus="i">
	                 			<c:if test="${i.index == 0}">
	                 				<tr id="met" class="met">
			                 			<td>
			                 				<button type="button" onclick="addMetRow('met')" class="btn btn-primary"><i class="md md-add"></i></button>
			                 				<input type="hidden" value="${met.mId}" name="mId" />
			                 			</td>
			                 			<td style="border-right: none;width: 40px;">
			                 				<input type="hidden" value="${met.gId}" name="gId" />
			                 			</td>
			                 			<td width="40px" style="border-left: none;border-right: none;">
			                 				1、
			                 			</td>
			                 			<td colspan="5" style="border-left: none;">
			                 				<input maxlength="200" type="text"  name="metContent" value="${met.metContent}" />
			                 			</td>
			                 			<td colspan="3">
			                 				<input maxlength="50" type="text"  name="metFileNum" value="${met.metFileNum}" />
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="metSheetsNum" value="${met.metSheetsNum}" />
			                 			</td>
			                 		</tr>
	                 			</c:if>
	                 			<c:if test="${i.index > 0}">
	                 				<tr id="met${met.mId}" class="met">
			                 			<td>
			                 				<button type="button" onclick="removeMetRow('met${met.mId}')" class="btn btn-danger"><i class="md md-clear"></i></button>
			                 				<input type="hidden" value="${met.mId}" name="mId" />
			                 			</td>
			                 			<td style="border-right: none;width: 40px;">
			                 				<input type="hidden" value="${met.gId}" name="gId" />
			                 			</td>
			                 			<td width="40px" style="border-left: none;border-right: none;">
			                 				${i.index+1}、
			                 			</td>
			                 			<td colspan="5" style="border-left: none;">
			                 				<input maxlength="200" type="text"  name="metContent" value="${met.metContent}" />
			                 			</td>
			                 			<td colspan="3">
			                 				<input maxlength="50" type="text"  name="metFileNum" value="${met.metFileNum}" />
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="metSheetsNum" value="${met.metSheetsNum}" />
			                 			</td>
			                 		</tr>
	                 			</c:if>
	                 		</c:forEach>
	                 		<tr>
	                 			<td class="title" rowspan="3">
	                 				<%-- 根据代填中改为新命名 --%>
	                 				<!-- 备注 -->
	                 				签章
	                 			</td>
	                 			<td colspan="12" style="text-indent: 2em;height: 60px;border-bottom: none;padding-left: 20px;padding-right: 20px;">
	                 				       我单位（本人）已阅知有关备注说明，并承诺对申报资料的真实性及数据的准确性（含
	                 				电子文件与图纸的一致性）负责，若有任何虚报、瞒报、造假等不正当手段，审批机关可终
	                 				止审理，我单位（本人）自愿承担虚报、瞒报、造假等不正当手段而产生的一切法律责任。
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td colspan="4" style="padding-left: 20px;border-right: none;border-top: none;border-bottom: none;height: 40px;">
	                 				（申请单位盖章/申请个人签名）
	                 			</td>
	                 			<td colspan="4" style="text-align: center;padding-left:50px;border:none;">
	                 				（工程施工单位盖章）
	                 			</td>
	                 			<td colspan="4" style="text-align: right;padding-right: 20px;border-left: none;border-top: none;border-bottom: none;">
	                 				（工程设计单位盖章）
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td colspan="4" style="padding-left:60px;border-right: none;border-top: none;height: 40px;">
	                 				<div class="dtp-container dropdown">
	                 					<input type="text" class="date-picker  base" data-toggle="dropdown" aria-expanded="false" placeholder="点击填日期" value='<fmt:formatDate value="${formObject.applyDate}" pattern="yyyy-MM-dd" />' name="applyDate" >
	                 				</div>
	                 			</td>
	                 			<td colspan="4" style="padding-left:200px;border-left: none;border-right: none;border-top: none;">
	                 				<div class="dtp-container dropdown">
	                 					<input type="text" class="date-picker  base" data-toggle="dropdown" aria-expanded="false" placeholder="点击填日期" value='<fmt:formatDate value="${formObject.constructionDate}" pattern="yyyy-MM-dd" />' name="constructionDate" >
	                 				</div>
	                 			</td>
	                 			<td colspan="4" style="padding-left: 240px;border-left: none;border-top: none;">
	                 				<div class="dtp-container dropdown">
	                 					<input type="text" class="date-picker  base" data-toggle="dropdown" aria-expanded="false" placeholder="点击填日期" value='<fmt:formatDate value="${formObject.designDate}" pattern="yyyy-MM-dd" />' name="designDate" >
	                 				</div>
	                 			</td>
	                 		</tr>
	                 	</tbody>
	                 </table>
	                 <br/><br/>
	                 <input type="hidden" name="jsonDataObj" id="jsonDataObj"/>
	                 <input type="hidden" name="jsonDataComList" id="jsonDataComList"/>
	                 <input type="hidden" name="jsonDataMetList" id="jsonDataMetList"/>
		            <div class="btn-demo text-center col-xs-12">
			            <button data-toggle="modal" onclick="saveForm();"
			                    class="btn btn-primary waves-effect savebutton" type="button">保存</button>
			            <button class="btn btn-default waves-effect savebutton" type="button"
			                    onclick="javascrtpt:history.go(-1)">返回</button>
			        </div>
	        	</div>
	    	</div>
	    </form>
	</div>
</body>