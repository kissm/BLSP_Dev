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
	    	$("#rfydjsjgsbForm").validate({
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
			if($("#rfydjsjgsbForm").valid()){
				$("#rfydjsjgsbForm").submit();
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
    <form id="rfydjsjgsbForm" action="${ctx}/rfydjsjgsb/save" method="post">
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
                 				珠海市人防工程易地建设竣工申报
                 			</td>
                 		</tr>
                 		<tr>
                 			<td class="title" style="width: 15%;">
                 				报建编号
                 			</td>
                 			<td style="width: 35%;">
                 				<input maxlength="50" class=" base" type="text" name="bjCode" value="${formObject.bjCode}" />
                 			</td>
                 			<td class="title" style="width: 15%;">
                 				工程名称
                 			</td>
                 			<td style="width: 35%;">
                 				<input maxlength="200" class="required base" type="text" name="prjName" value="${formObject.prjName}" />
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
                 				建设单位联系人
                 			</td>
                 			<td>
                 				<input maxlength="50" class=" base" type="text" name="linkman" value="${formObject.linkman}" />
                 			</td>
                 			<td class="title">
                 				联系电话
                 			</td>
                 			<td>
                 				<input maxlength="15" class=" base phone" type="text" name="linkmanPhone" value="${formObject.linkmanPhone}" />
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
                 				施工单位
                 			</td>
                 			<td>
                 				<input maxlength="50" class=" base" type="text" name="constructCompany" value="${formObject.constructCompany}" />
                 			</td>
                 		</tr>
                 		<tr>
                 			<td class="title">
                 				监理单位
                 			</td>
                 			<td>
                 				<input maxlength="50" class=" base" type="text" name="inspectCompany" value="${formObject.inspectCompany}" />
                 			</td>
                 			<td class="title">
                 				报建地上总建筑面积
                 			</td>
                 			<td>
                 				<input maxlength="50" class=" base" type="text" name="bjdszjzmj" value="${formObject.bjdszjzmj}" />
                 			</td>
                 		</tr>
                 		<tr>
                 			<td class="title">
                 				报建地上建筑栋数
                 			</td>
                 			<td>
                 				<input maxlength="50" class=" base" type="text" name="bjdsjzds" value="${formObject.bjdsjzds}" />
                 			</td>
                 			<td class="title">
                 				报建地上建筑层数
                 			</td>
                 			<td>
                 				<input maxlength="50" class=" base" type="text" name="bjdsjzcs" value="${formObject.bjdsjzcs}" />
                 			</td>
                 		</tr>
                 		<tr>
                 			<td class="title">
                 				报建地下总建筑面积
                 			</td>
                 			<td>
                 				<input maxlength="50" class=" base" type="text" name="bjdxzjzmj" value="${formObject.bjdxzjzmj}" />
                 			</td>
                 			<td class="title">
                 				报建地下室个数
                 			</td>
                 			<td>
                 				<input maxlength="50" class=" base" type="text" name="bjdxsgs" value="${formObject.bjdxsgs}" />
                 			</td>
                 		</tr>
                 		<tr>
                 			<td class="title">
                 				报建地下室层数
                 			</td>
                 			<td>
                 				<input maxlength="50" class=" base" type="text" name="bjdxscs" value="${formObject.bjdxscs}" />
                 			</td>
                 			<td class="title">
                 				竣工地上总建筑面积
                 			</td>
                 			<td>
                 				<input maxlength="50" class=" base" type="text" name="jgdszjzmj" value="${formObject.jgdszjzmj}" />
                 			</td>
                 		</tr>
                 		<tr>
                 			<td class="title">
                 				竣工地上建筑栋数
                 			</td>
                 			<td>
                 				<input maxlength="50" class=" base" type="text" name="jgdsjzds" value="${formObject.jgdsjzds}" />
                 			</td>
                 			<td class="title">
                 				竣工地上建筑层数
                 			</td>
                 			<td>
                 				<input maxlength="50" class=" base" type="text" name="jgdsjzcs" value="${formObject.jgdsjzcs}" />
                 			</td>
                 		</tr>
                 		<tr>
                 			<td class="title">
                 				竣工地下总建筑面积
                 			</td>
                 			<td>
                 				<input maxlength="50" class=" base" type="text" name="jgdxzjzmj" value="${formObject.jgdxzjzmj}" />
                 			</td>
                 			<td class="title">
                 				竣工地下室个数
                 			</td>
                 			<td>
                 				<input maxlength="50" class=" base" type="text" name="jgdxsgs" value="${formObject.jgdxsgs}" />
                 			</td>
                 		</tr>
                 		<tr>
                 			<td class="title">
                 				竣工地下室个数层数
                 			</td>
                 			<td>
                 				<input maxlength="50" class=" base" type="text" name="jgdxscs" value="${formObject.jgdxscs}" />
                 			</td>
                 			<td class="title">
                 				应交易地建设费（元）
                 			</td>
                 			<td>
                 				<input maxlength="50" class=" base" type="text" name="yjydjsf" value="${formObject.yjydjsf}" />
                 			</td>
                 		</tr>
                 		<tr>
                 			<td class="title">
                 				已交易地建设费（元）
                 			</td>
                 			<td>
                 				<input maxlength="50" class=" base" type="text" name="yijiaoydjsf" value="${formObject.yijiaoydjsf}" />
                 			</td>
                 			<td class="title">
                 				补交易地建设费（元）
                 			</td>
                 			<td>
                 				<input maxlength="50" class=" base" type="text" name="bjydjsf" value="${formObject.bjydjsf}" />
                 			</td>
                 		</tr>
                 		<tr>
                 			<td class="title">
                 				建设单位验收意见
                 			</td>
                 			<td colspan="3">
                 				<input maxlength="200" class=" base" type="text" name="jsdwysyj" value="${formObject.jsdwysyj}" />
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