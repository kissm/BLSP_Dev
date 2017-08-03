<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>受理表单</title>
    <meta name="decorator" content="blank" />
    <style>
		.editTable input[type='text'].has-error {
			background: #F44336;
			color: #FFF;
		}
	</style>
    <script>
	    $(document).ready(function(){
	    	$("#rfydjsForm").validate({
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
			if($("#rfydjsForm").valid()){
				$("#rfydjsForm").submit();
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
    <form id="rfydjsForm" action="${ctx}/rfydjs/save" method="post">
	    <div class="card-body card-padding">
	        <div class="row">
	            <input type="hidden" name="id"
	                   value="${formObject.id}">
                <input type="hidden" name="prjId"
                 value="${formObject.prjId}">
                <input type="hidden" name="taskId"
                 value="${formObject.taskId}">
                 <input type="hidden" name="prjCode"
                 value="${formObject.prjCode}">
                <table class="editTable" style="width: 960px;" border="0" cellspacing="0" cellpadding="0">
                 	<tbody>
                 		<tr>
                 			<td class="title" colspan="4" style="font-size: 16px;font-weight: bolder;">
                 				 人防工程易地建设报建审批
                 			</td>
                 		</tr>
                 		<tr>
                 			<td class="title" style="width: 15%;">
                 				工程名称
                 			</td>
                 			<td style="width: 35%;">
                 				<input maxlength="200" class="required base" type="text" name="prjName" value="${formObject.prjName}" />
                 			</td>
                 			<td class="title" style="width: 15%;">
                 				单位地址
                 			</td>
                 			<td style="width: 35%;">
                 				<input maxlength="200" class=" base" type="text" name="companyAddr" value="${formObject.companyAddr}" />
                 			</td>
                 		</tr>
                 		<tr>
                 			<td class="title">
                 				单位电话
                 			</td>
                 			<td>
                 				<input maxlength="15" class=" base phone" type="text" name="companyMphone" value="${formObject.companyMphone}" />
                 			</td>
                 			<td class="title">
                 				联系人
                 			</td>
                 			<td>
                 				<input maxlength="50" class=" base" type="text" name="linkman" value="${formObject.linkman}" />
                 			</td>
                 		</tr>
                 		<tr>
                 			<td class="title">
                 				联系电话
                 			</td>
                 			<td>
                 				<input maxlength="15" class=" base phone" type="text" name="linkmanPhone" value="${formObject.linkmanPhone}" />
                 			</td>
                 			<td class="title">
                 				申请日期
                 			</td>
                 			<td>
                 				<input class=" base" type="text" 
	                 				onClick="WdatePicker()" placeholder="点击填日期" 
	                 				value='<fmt:formatDate value="${formObject.applyDate}" 
	                 				pattern="yyyy-MM-dd" />' name="applyDate" />
                 			</td>
                 		</tr>
                 		<tr>
                 			<td class="title">
                 				工程地点
                 			</td>
                 			<td>
                 				<input maxlength="50" class=" base" type="text" name="prjAddress" value="${formObject.prjAddress}" />
                 			</td>
                 			<td class="title">
                 				建设单位
                 			</td>
                 			<td>
                 				<input maxlength="200" class=" base" type="text" name="company" value="${formObject.company}" />
                 			</td>
                 		</tr>
                 		<tr>
                 			<td class="title">
                 				设计单位
                 			</td>
                 			<td>
                 				<input maxlength="50" class=" base" type="text" name="designCompany" value="${formObject.designCompany}" />
                 			</td>
                 			<td class="title">
                 				建设单位法人代表
                 			</td>
                 			<td>
                 				<input maxlength="50" class=" base" type="text" name="legalEntity" value="${formObject.legalEntity}" />
                 			</td>
                 		</tr>
                 		<tr>
                 			<td class="title">
                 				法人代表身份证号码
                 			</td>
                 			<td>
                 				<input maxlength="18" class=" base" type="text" name="entityIdcode" value="${formObject.entityIdcode}" />
                 			</td>
                 			<td class="title">
                 				法人代表联系电话
                 			</td>
                 			<td>
                 				<input maxlength="15" class=" base phone" type="text" name="entityPhone" value="${formObject.entityPhone}" />
                 			</td>
                 		</tr>
                 		<tr>
                 			<td class="title" colspan="4" style="text-align: left;padding-left: 10px;">
                 				防空地下室易地建设方式（自选一项）：
                 			</td>
                 		</tr>
                 		<tr>
                 			<td colspan="4" style="padding-left: 10px;height: 35px;">
                 				<div class="fg-line">
				                    <label class="radio radio-inline m-r-20"> <input
				                            type="radio" name="basementType"
				                            checked value="1"
				                        ${formObject.basementType eq '1'?"checked":""}>
				                        <i class="input-helper"></i> 1、缴易地建设费
				                    </label> 
				                    <label class="radio radio-inline m-r-20"> <input
				                        type="radio" name="basementType"
				                        value="2"
					                    ${formObject.basementType eq '2'?"checked":""}>
					                    <i class="input-helper"></i> 2、小区内自建
					                </label> 
					                <label class="radio radio-inline m-r-20"> <input
				                        type="radio" name="basementType"
				                        value="3"
					                    ${formObject.basementType eq '3'?"checked":""}>
					                    <i class="input-helper"></i> 3、易地自建
					                </label>
				                </div>
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
</html>