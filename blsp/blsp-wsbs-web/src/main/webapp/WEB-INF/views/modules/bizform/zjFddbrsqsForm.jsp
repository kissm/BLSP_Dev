<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>法定代表人授权书</title>
    <meta name="decorator" content="blank" />
    <style>
		.editTable input[type='text'].has-error {
			background: #F44336;
			color: #FFF;
		}
	</style>
    <script>
	    $(document).ready(function(){
	    	$("#zjFddbrsqsForm").validate({
				rules: {
	    			'authId':{card:true}
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
			if($("#zjFddbrsqsForm").valid()){
				$("#zjFddbrsqsForm").submit();
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
	    <form id="zjFddbrsqsForm" action="${ctx}/zjFddbrsqs/save" method="post">
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
	                 				法定代表人授权书
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
		                			授权单位
		                		</td>
		                		<td style="width: 35%;">
		                			<input maxlength="200" class=" base" type="text" name="company" value="${formObject.company}" />
		                		</td>
		                	</tr>
		                	<tr>
		                		<td class="title">
		                			法人代表
		                		</td>
		                		<td>
		                			<input maxlength="50" class=" base" type="text" name="legalEntity" value="${formObject.legalEntity}" />
		                		</td>
		                		<td class="title">
		                			被授权人姓名
		                		</td>
		                		<td>
		                			<input maxlength="50" class=" base" type="text" name="authorizedPer" value="${formObject.authorizedPer}" />
		                		</td>
		                	</tr>
		                	<tr>
		                		<td class="title">
		                			身份证号
		                		</td>
		                		<td>
		                			<input maxlength="18" class=" base" type="text" name="authId" value="${formObject.authId}" />
		                		</td>
		                		<td class="title">
		                			注册执业资格
		                		</td>
		                		<td>
		                			<input maxlength="50" class=" base" type="text" name="praReq" value="${formObject.praReq}" />
		                		</td>
		                	</tr>
		                	<tr>
		                		<td class="title">
		                			注册执业证号
		                		</td>
		                		<td>
		                			<input maxlength="50" class=" base" type="text" name="praId" value="${formObject.praId}" />
		                		</td>
		                		<td class="title">
		                			授权日期
		                		</td>
		                		<td>
		                			<input class=" base" type="text" 
			                			onClick="WdatePicker()" placeholder="点击填日期" 
			                			value='<fmt:formatDate value="${formObject.authDate}" 
			                			pattern="yyyy-MM-dd" />' name="authDate" />
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