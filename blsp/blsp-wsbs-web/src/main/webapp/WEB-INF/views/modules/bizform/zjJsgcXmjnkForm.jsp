<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>金湾区建设工程项目节能卡</title>
    <meta name="decorator" content="blank" />
    <style>
		.editTable input[type='text'].has-error {
			background: #F44336;
			color: #FFF;
		}
	</style>
    <script>
	    $(document).ready(function(){
	    	$("#zjJsgcXmjnkForm").validate({
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
			if($("#zjJsgcXmjnkForm").valid()){
				$("#zjJsgcXmjnkForm").submit();
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
	    <form id="zjJsgcXmjnkForm" action="${ctx}/zjJsgcXmjnk/save" method="post">
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
	                 				金湾区建设工程项目节能卡
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
		                			建设单位
		                		</td>
		                		<td style="width: 35%;">
		                			<input maxlength="200" class=" base" type="text" name="company" value="${formObject.company}" />
		                		</td>
		                	</tr>
		                	<tr>
		                		<td class="title">
		                			工程地点
		                		</td>
		                		<td>
		                			<input maxlength="50" class=" base" type="text" name="prjAddr" value="${formObject.prjAddr}" />
		                		</td>
		                		<td class="title">
		                			报建日期
		                		</td>
		                		<td>
		                			<input class=" base" type="text" onClick="WdatePicker()" 
			                			placeholder="点击填日期" 
			                			value='<fmt:formatDate value="${formObject.applicationDate}" 
			                			pattern="yyyy-MM-dd" />' name="applicationDate" />
		                		</td>
		                	</tr>
		                	<tr>
		                		<td class="title">
		                			报建面积
		                		</td>
		                		<td>
		                			<input maxlength="50" class=" base" type="text" name="applicationSpace" value="${formObject.applicationSpace}" />
		                		</td>
		                		<td class="title">
		                			设计墙材用量
		                		</td>
		                		<td>
		                			<input maxlength="50" class=" base" type="text" name="planWallDosage" value="${formObject.planWallDosage}" />
		                		</td>
		                	</tr>
		                	<tr>
		                		<td class="title">
		                			设计墙材名称
		                		</td>
		                		<td>
		                			<input maxlength="50" class=" base" type="text" name="planWallName" value="${formObject.planWallName}" />
		                		</td>
		                		<td class="title">
		                			设计水泥用量
		                		</td>
		                		<td>
		                			<input maxlength="50" class=" base" type="text" name="planCementDosage" value="${formObject.planCementDosage}" />
		                		</td>
		                	</tr>
		                	<tr>
		                		<td class="title">
		                			项目负责人
		                		</td>
		                		<td>
		                			<input maxlength="50" class=" base" type="text" name="prjPrincipal" value="${formObject.prjPrincipal}" />
		                		</td>
		                		<td class="title">
		                			联系电话
		                		</td>
		                		<td>
		                			<input maxlength="15" class=" base phone" type="text" name="priPhone" value="${formObject.priPhone}" />
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