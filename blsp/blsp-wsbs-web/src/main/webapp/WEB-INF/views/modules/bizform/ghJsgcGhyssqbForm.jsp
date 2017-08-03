<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>珠海市建设工程（建筑类）规划条件核实申请表</title>
    <meta name="decorator" content="blank" />
    <style>
		.editTable input[type='text'].has-error {
			background: #F44336;
			color: #FFF;
		}
	</style>
    <script>
    	$(document).ready(function(){
	    	$("#ghJsgcGhyssqbForm").validate({
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
	    	$("#ghJsgcGhyssqbForm").validate({
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
			$(tr).find('button i.md').removeClass('add').addClass('minus');
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
			$(tr).find('button i.md').removeClass('add').addClass('minus');
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
	    <form id="ghJsgcGhyssqbForm" action="${ctx}/ghJsgcGhyssqb/save" method="post">
		    <div class="card-body card-padding">
	        	<div class="row">
		            <input type="hidden" name="id"
		                   value="${formObject.id}" class="base">
	                <input type="hidden" name="prjId"
	                 value="${formObject.prjId}" class="base">
	                <input type="hidden" name="taskId"
	                 value="${formObject.taskId}" class="base">
	                 <table class="editTable" style="width: 960px" border="0" cellspacing="0" cellpadding="0">
	                 	<tbody>
	                 		<tr>
	                 			<td class="title" colspan="13" style="font-size: 16px;font-weight: bolder;">
	                 				珠海市建设工程（建筑类）规划条件核实申请表
	                 			</td>
	                 		</tr>
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
	                 				<!-- 建设项目地址 -->
	                 				建设位置
	                 			</td>
	                 			<td colspan="10">
	                 				<input maxlength="50" class=" base" type="text" name="prjAddr" value="${formObject.prjAddr}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title" rowspan="3" width="40px">
	                 				申<br/>请<br/>人
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
	                 				<!-- 竣工<br/>项目 -->
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
	                 				基底面积<br/>(M<sup>2</sup>)
	                 			</td>
	                 			<td class="title" colspan="4">
	                 				建筑面积<br/>(M<sup>2</sup>)
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
	                 				<button type="button" onclick="addComRow('com')" class="btn btn-primary"><i class="md add"></i></button>
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
			                 				<button type="button" onclick="addComRow('com')" class="btn btn-primary"><i class="md add"></i></button>
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
			                 				<button type="button" onclick="removeComRow('com${com.cId}')" class="btn btn-danger"><i class="md minus"></i></button>
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
	                 				<!-- 所需<br/>材料 -->
	                 				申请<br/>资料
	                 			</td>
	                 			<td colspan="8" class="title">
	                 				<!-- 所&nbsp;需&nbsp;文&nbsp;件&nbsp;内&nbsp;容（须参照所需资料规定填写） -->
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
	                 				<button type="button" onclick="addMetRow('met')" class="btn btn-primary"><i class="md add"></i></button>
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
			                 				<button type="button" onclick="addMetRow('met')" class="btn btn-primary"><i class="md add"></i></button>
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
			                 				<button type="button" onclick="removeMetRow('met${met.mId}')" class="btn btn-danger"><i class="md minus"></i></button>
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
	                 				<input class=" base" type="text" 
		                 				onClick="WdatePicker()" placeholder="点击填日期" 
		                 				value='<fmt:formatDate value="${formObject.applyDate}" 
		                 				pattern="yyyy-MM-dd" />' name="applyDate" />
	                 			</td>
	                 			<td colspan="4" style="padding-left:200px;border-left: none;border-right: none;border-top: none;">
	                 				<input class=" base" type="text" 
		                 				onClick="WdatePicker()" placeholder="点击填日期" 
		                 				value='<fmt:formatDate value="${formObject.constructionDate}" 
		                 				pattern="yyyy-MM-dd" />' name="constructionDate" />
	                 			</td>
	                 			<td colspan="4" style="padding-left: 240px;border-left: none;border-top: none;">
	                 				<input class=" base" type="text" 
		                 				onClick="WdatePicker()" placeholder="点击填日期" 
		                 				value='<fmt:formatDate value="${formObject.designDate}" 
		                 				pattern="yyyy-MM-dd" />' name="designDate" />
	                 			</td>
	                 		</tr>
	                 	</tbody>
	                 </table>
	                 <input type="hidden" name="jsonDataObj" id="jsonDataObj"/>
	                 <input type="hidden" name="jsonDataComList" id="jsonDataComList"/>
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