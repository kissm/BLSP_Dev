<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>授权委托书、法人代表证明书</title>
    <meta name="decorator" content="blank" />
    <style>
		.editTable input[type='text'].has-error {
			background: #F44336;
			color: #FFF;
		}
	</style>
    <script>
	    $(document).ready(function(){
	    	$("#ghSqwtsForm").validate({
				rules: {
					'bailorAgentId':{card:true}
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
			$(".savebutton").attr("disabled",true);
			if($("#ghSqwtsForm").valid()){
				$("#ghSqwtsForm").submit();
			}else{
				$(".savebutton").attr("disabled",false);
			}
		}
		function refresh(){
			window.location.reload(true);
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
	    <form id="ghSqwtsForm" action="${ctx}/ghSqwts/save" method="post">
		    <div class="card-body card-padding">
	        	<div class="row">
		            <input type="hidden" name="id"
		                   value="${formObject.id}">
	                <input type="hidden" name="prjId"
	                 value="${formObject.prjId}">
	                <input type="hidden" name="taskId"
	                 value="${formObject.taskId}">
	                 <table class="editTable" style="width: 960px;" border="0" cellspacing="0" cellpadding="0">
	                 	<tbody>
	                 		<tr>
	                 			<td class="title" colspan="4" style="font-size: 16px;font-weight: bolder;">
	                 				授权委托书、法人代表证明书
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title" style="text-align: left;padding-left: 10px;" colspan="4">
	                 				法定代表人身份证明书
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title" style="width: 15%;">
	                 				法人代表
	                 			</td>
	                 			<td style="width: 35%;">
	                 				<input maxlength="50" class=" base" type="text" name="legalName" value="${formObject.legalName}" />
	                 			</td>
	                 			<td class="title" style="width: 15%;">
	                 				任职职务
	                 			</td>
	                 			<td style="width: 35%;">
	                 				<input maxlength="50" class=" base" type="text" name="legalDuty" value="${formObject.legalDuty}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title">
	                 				单位名称
	                 			</td>
	                 			<td>
	                 				<input maxlength="50" class=" base" type="text" name="unitName" value="${formObject.unitName}" />
	                 			</td>
	                 			<td class="title">
	                 				单位地址
	                 			</td>
	                 			<td>
	                 				<input maxlength="50" class=" base" type="text" name="unitAddr" value="${formObject.unitAddr}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title">
	                 				邮政编码
	                 			</td>
	                 			<td>
	                 				<input maxlength="50" class=" base" type="text" name="unitPostcode" value="${formObject.unitPostcode}" />
	                 			</td>
	                 			<td class="title">
	                 				单位联系电话
	                 			</td>
	                 			<td>
	                 				<input maxlength="15" class=" base phone" type="text" name="unitTel" value="${formObject.unitTel}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title">
	                 				证明日期
	                 			</td>
	                 			<td>
	                 				<input class=" base" type="text" 
		                 				onClick="WdatePicker()" placeholder="点击填日期" 
		                 				value='<fmt:formatDate value="${formObject.unitDate}" 
		                 				pattern="yyyy-MM-dd" />' name="unitDate" />
	                 			</td>
	                 			<td colspan="2"></td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title" style="text-align: left;padding-left: 10px;" colspan="4">
	                 				授权委托书
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title">
	                 				委托人
	                 			</td>
	                 			<td>
	                 				<input maxlength="50" class=" base" type="text" name="bailor" value="${formObject.bailor}" />
	                 			</td>
	                 			<td class="title">
	                 				法定代表人
	                 			</td>
	                 			<td>
	                 				<input maxlength="50" class=" base" type="text" name="lawPerson" value="${formObject.lawPerson}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title">
	                 				委托办理地点
	                 			</td>
	                 			<td>
	                 				<input maxlength="50" class=" base" type="text" name="bailorAddr" value="${formObject.bailorAddr}" />
	                 			</td>
	                 			<td class="title">
	                 				委托办理项目
	                 			</td>
	                 			<td>
	                 				<input maxlength="200" class="required base" type="text" name="prjName" value="${formObject.prjName}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title">
	                 				委托办理手续
	                 			</td>
	                 			<td>
	                 				<input maxlength="50" class=" base" type="text" name="bailorProcedure" value="${formObject.bailorProcedure}" />
	                 			</td>
	                 			<td class="title">
	                 				委托代理人
	                 			</td>
	                 			<td>
	                 				<input maxlength="50" class=" base" type="text" name="bailorAgent" value="${formObject.bailorAgent}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title">
	                 				身份证号
	                 			</td>
	                 			<td>
	                 				<input maxlength="18" class=" base" type="text" name="bailorAgentId" value="${formObject.bailorAgentId}" />
	                 			</td>
	                 			<td class="title">
	                 				单位
	                 			</td>
	                 			<td>
	                 				<input maxlength="50" class=" base" type="text" name="bailorAgentUnit" value="${formObject.bailorAgentUnit}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title">
	                 				职务
	                 			</td>
	                 			<td>
	                 				<input maxlength="50" class=" base" type="text" name="bailorAgentDuty" value="${formObject.bailorAgentDuty}" />
	                 			</td>
	                 			<td class="title">
	                 				联系电话
	                 			</td>
	                 			<td>
	                 				<input maxlength="15" class=" base phone" type="text" name="bailorAgentTel" value="${formObject.bailorAgentTel}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title">
	                 				代理起始日期
	                 			</td>
	                 			<td>
	                 				<input class=" base" type="text" 
		                 				onClick="WdatePicker()" placeholder="点击填日期" 
		                 				value='<fmt:formatDate value="${formObject.agentStartTime}" 
		                 				pattern="yyyy-MM-dd" />' name="agentStartTime" />
	                 			</td>
	                 			<td class="title">
	                 				代理结束日期
	                 			</td>
	                 			<td>
	                 				<input class=" base" type="text" 
		                 				onClick="WdatePicker()" placeholder="点击填日期" 
		                 				value='<fmt:formatDate value="${formObject.agentEndTime}" 
		                 				pattern="yyyy-MM-dd" />' name="agentEndTime" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title">
	                 				单位或自然人
	                 			</td>
	                 			<td>
	                 				<input maxlength="50" class=" base" type="text" name="agentNameOrPerson" value="${formObject.agentNameOrPerson}" />
	                 			</td>
	                 			<td class="title">
	                 				委托日期
	                 			</td>
	                 			<td>
	                 				<input class=" base" type="text" 
		                 				onClick="WdatePicker()" placeholder="点击填日期" 
		                 				value='<fmt:formatDate value="${formObject.agentUnitDate}" 
		                 				pattern="yyyy-MM-dd" />' name="agentUnitDate" />
	                 			</td>
	                 		</tr>
	                 	</tbody>
	                 </table>
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