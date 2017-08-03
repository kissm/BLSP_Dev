<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>防雷装置设计审核（授权书）</title>
    <meta name="decorator" content="blank" />
    <style>
		.editTable input[type='text'].has-error {
			background: #F44336;
			color: #FFF;
		}
	</style>
    <script>
	    $(document).ready(function(){
	    	$("#qxSqsForm").validate({
				rules: {
					'entrustedAgentId':{card:true}
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
			if($("#qxSqsForm").valid()){
				$("#qxSqsForm").submit();
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
	    <form id="qxSqsForm" action="${ctx}/qxSqs/save" method="post">
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
	                 				防雷装置设计审核（授权书）
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title" style="width: 15%;">
	                 				气象局
	                 			</td>
	                 			<td style="width: 35%;">
	                 				<input maxlength="50" class=" base" type="text" name="weatherBureau" value="${formObject.weatherBureau}" />
	                 			</td>
	                 			<td class="title" style="width: 15%;">
	                 				项目名称
	                 			</td>
	                 			<td style="width: 35%;">
	                 				<input maxlength="200" class="required base" type="text" name="prjName" value="${formObject.prjName}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title">
	                 				授权单位
	                 			</td>
	                 			<td>
	                 				<input maxlength="50" class=" base" type="text" name="warrantyUnit" value="${formObject.warrantyUnit}" />
	                 			</td>
	                 			<td class="title">
	                 				代理人
	                 			</td>
	                 			<td>
	                 				<input maxlength="50" class=" base" type="text" name="entrustedAgent" value="${formObject.entrustedAgent}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title">
	                 				性别
	                 			</td>
	                 			<td>
	                 				<input maxlength="2" class=" base" type="text" name="entrustedAgentSax" value="${formObject.entrustedAgentSax}" />
	                 			</td>
	                 			<td class="title">
	                 				年龄
	                 			</td>
	                 			<td>
	                 				<input maxlength="3" class=" base" type="text" name="entrustedAgentAge" value="${formObject.entrustedAgentAge}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title">
	                 				身份证号码
	                 			</td>
	                 			<td>
	                 				<input maxlength="18" class=" base" type="text" name="entrustedAgentId" value="${formObject.entrustedAgentId}" />
	                 			</td>
	                 			<td class="title">
	                 				固话
	                 			</td>
	                 			<td>
	                 				<input maxlength="15" class=" base simplePhone" type="text" name="entrustedAgentTel" value="${formObject.entrustedAgentTel}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title">
	                 				手机
	                 			</td>
	                 			<td>
	                 				<input maxlength="15" class=" base mobile" type="text" name="entrustedAgentMobile" value="${formObject.entrustedAgentMobile}" />
	                 			</td>
	                 			<td class="title">
	                 				法定代表人
	                 			</td>
	                 			<td>
	                 				<input maxlength="50" class=" base" type="text" name="warrantyLegal" value="${formObject.warrantyLegal}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title">
	                 				授权起始日期
	                 			</td>
	                 			<td>
	                 				<input class=" base" type="text" 
		                 				onClick="WdatePicker()" placeholder="点击填日期" 
		                 				value='<fmt:formatDate value="${formObject.warrantyStartTime}" 
		                 				pattern="yyyy-MM-dd" />' name="warrantyStartTime" />
	                 			</td>
	                 			<td class="title">
	                 				授权终止日期
	                 			</td>
	                 			<td>
	                 				<input class=" base" type="text" 
		                 				onClick="WdatePicker()" placeholder="点击填日期" 
		                 				value='<fmt:formatDate value="${formObject.warrantyEndTime}" 
		                 				pattern="yyyy-MM-dd" />' name="warrantyEndTime" />
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