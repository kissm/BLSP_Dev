<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>防雷装置设计技术评价信息表</title>
    <meta name="decorator" content="blank" />
    <style>
		.editTable input[type='text'].has-error {
			background: #F44336;
			color: #FFF;
		}
	</style>
    <script>
    	$(document).ready(function(){
	    	$("#qxSjjsPjxxForm").validate({
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
	    	$("#qxSjjsPjxxForm").validate({
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
			$(".savebutton").attr("disabled",true);
			if($("#qxSjjsPjxxForm").valid()){
				$("#qxSjjsPjxxForm").submit();
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
	    <form id="qxSjjsPjxxForm" action="${ctx}/qxSjjsPjxx/save" method="post">
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
	                 			<td class="title" colspan="5" style="font-size: 16px;font-weight: bolder;">
	                 				防雷装置设计技术评价信息表
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td style="width: 35px;" class="title" rowspan="3">
	                 				建设单位
	                 			</td>
	                 			<td class="title">
	                 				名称（盖章）
	                 			</td>
	                 			<td colspan="3">
	                 				<input maxlength="50"  type="text" name="constructionName" value="${formObject.constructionName}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title">
	                 				地&nbsp;&nbsp;&nbsp;&nbsp;址
	                 			</td>
	                 			<td colspan="3">
	                 				<input maxlength="50"  type="text" name="constructionAddr" value="${formObject.constructionAddr}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title">
	                 				联系人
	                 			</td>
	                 			<td>
	                 				<input maxlength="50"  type="text" name="constructionLinkman" value="${formObject.constructionLinkman}" />
	                 			</td>
	                 			<td class="title">
	                 				联系电话
	                 			</td>
	                 			<td>
	                 				<input maxlength="15" class=" phone" type="text" name="constructionLinktel" value="${formObject.constructionLinktel}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title" rowspan="2">
	                 				设计单位
	                 			</td>
	                 			<td class="title">
	                 				名称
	                 			</td>
	                 			<td colspan="3">
	                 				<input maxlength="50"  type="text" name="designName" value="${formObject.designName}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title">
	                 				联系人
	                 			</td>
	                 			<td>
	                 				<input maxlength="50"  type="text" name="designLinkman" value="${formObject.designLinkman}" />
	                 			</td>
	                 			<td class="title">
	                 				联系电话
	                 			</td>
	                 			<td>
	                 				<input maxlength="15" class=" phone" type="text" name="designLinktel" value="${formObject.designLinktel}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title" rowspan="6">
	                 				项<br/>目<br/>概<br/>况
	                 			</td>
	                 			<td class="title">
	                 				项目名称
	                 			</td>
	                 			<td colspan="3">
	                 				<input maxlength="200" class="required" type="text" name="prjName" value="${formObject.prjName}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title">
	                 				项目地址
	                 			</td>
	                 			<td colspan="3">
	                 				<input maxlength="50"  type="text" name="prjAddr" value="${formObject.prjAddr}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title">
	                 				规划许可证号
	                 			</td>
	                 			<td colspan="3">
	                 				<input maxlength="50"  type="text" name="planLesence" value="${formObject.planLesence}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title">
	                 				建筑面积（m<sup>2</sup>）
	                 			</td>
	                 			<td colspan="3" style="padding-left: 10px;">
	                 				地上
	                 				<input maxlength="50"  style="width:50px;height: auto;border-bottom: 1px solid #ccc;" type="text" value="${formObject.onFloors}" name="onFloors" />
	                 				层，共
	                 				<input maxlength="50"  style="width:50px;height: auto;border-bottom: 1px solid #ccc;" type="text" value="${formObject.onFloorsSpace}" name="onFloorsSpace" />
									m<sup>2</sup>，地下
									<input maxlength="50"  style="width:50px;height: auto;border-bottom: 1px solid #ccc;" type="text" value="${formObject.downFloors}" name="downFloors" />
									层，共
									<input maxlength="50"  style="width:50px;height: auto;border-bottom: 1px solid #ccc;" type="text" value="${formObject.downFloorsSpace}" name="downFloorsSpace" />
	                 				m<sup>2</sup>
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title">
	                 				栋&nbsp;&nbsp;&nbsp;&nbsp;数
	                 			</td>
	                 			<td colspan="3">
	                 				<input maxlength="50"  type="text" name="ridgepoleNum" value="${formObject.ridgepoleNum}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title">
	                 				楼层数<br/>（地上/地下）
	                 			</td>
	                 			<td colspan="3">
	                 				<input maxlength="50"  type="text" name="floors" value="${formObject.floors}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title" colspan="2">
	                 				联系人
	                 			</td>
	                 			<td>
	                 				<input maxlength="50"  type="text" name="linkman" value="${formObject.linkman}" />
	                 			</td>
	                 			<td class="title">
	                 				联系电话
	                 			</td>
	                 			<td>
	                 				<input maxlength="15" class=" phone" type="text" name="linktel" value="${formObject.linktel}" />
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