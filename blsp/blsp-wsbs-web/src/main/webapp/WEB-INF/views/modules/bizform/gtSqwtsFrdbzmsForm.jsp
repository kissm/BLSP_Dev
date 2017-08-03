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
	    	$("#gtSqwtsFrdbzmsForm").validate({
				rules: {
					'baiId':{card:true},
	    			'conId':{card:true},
	    			'leaId':{card:true}
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
			if($("#gtSqwtsFrdbzmsForm").valid()){
				$("#gtSqwtsFrdbzmsForm").submit();
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
	    <form id="gtSqwtsFrdbzmsForm" action="${ctx}/gtSqwtsFrdbzms/save" method="post">
		    <div class="card-body card-padding">
	        	<div class="row">
		            <input type="hidden" name="id"
		                   value="${formObject.id}">
	                <input type="hidden" name="prjId"
	                 value="${formObject.prjId}">
	                <input type="hidden" name="taskId"
	                 value="${formObject.taskId}">
	                <table class="editTable" style="width: 960px;" border="0" cellspacing="0" cellpadding="0">
	                	<tr>
	                		<td class="title" colspan="4" style="font-size: 16px;font-weight: bolder;">
                 				授权委托书、法人代表证明书
                 			</td>
	                	</tr>
	                	<tr>
	                		<td class="title" colspan="4" style="text-align: left;padding-left: 10px;">
                 				授权委托书
                 			</td>
	                	</tr>
	                	<tr>
	                		<td class="title" style="width: 15%;">
	                			委托代理人
	                		</td>
	                		<td style="width: 35%;">
	                			<input maxlength="50" class=" base" type="text" name="bailee" value="${formObject.bailee}" />
	                		</td>
	                		<td class="title" style="width: 15%;">
	                			委托代理人住所
	                		</td>
	                		<td style="width: 35%;">
	                			<input maxlength="200" class=" base" type="text" name="baiAdd" value="${formObject.baiAdd}" />
	                		</td>
	                	</tr>
	                	<tr>
	                		<td class="title">
	                			委托代理人身份证
	                		</td>
	                		<td>
	                			<input maxlength="18" class=" base" type="text" name="baiId" value="${formObject.baiId}" />
	                		</td>
	                		<td class="title">
	                			委托代理人电话
	                		</td>
	                		<td>
	                			<input maxlength="15" class=" base phone" type="text" name="baiTel" value="${formObject.baiTel}" />
	                		</td>
	                	</tr>
	                	<tr>
	                		<td class="title">
	                			委托人
	                		</td>
	                		<td>
	                			<input maxlength="50" class=" base" type="text" name="consignor" value="${formObject.consignor}" />
	                		</td>
	                		<td class="title">
	                			委托人身份证
	                		</td>
	                		<td>
	                			<input maxlength="18" class=" base" type="text" name="conId" value="${formObject.conId}" />
	                		</td>
	                	</tr>
	                	<tr>
	                		<td class="title">
	                			委托人电话
	                		</td>
	                		<td>
	                			<input maxlength="15" class=" base phone" type="text" name="conTel" value="${formObject.conTel}" />
	                		</td>
	                		<td class="title">
	                			委托事务
	                		</td>
	                		<td>
	                			<input maxlength="50" class=" base" type="text" name="taskname" value="${formObject.taskname}" />
	                		</td>
	                	</tr>
	                	<tr>
	                		<td class="title">
	                			委托限期开始日期
	                		</td>
	                		<td>
	                			<input class=" base" type="text" 
		                			onClick="WdatePicker()" placeholder="点击填日期" 
		                			value='<fmt:formatDate value="${formObject.startdate}" 
		                			pattern="yyyy-MM-dd" />' name="startdate" />
	                		</td>
	                		<td class="title">
	                			授权委托日期
	                		</td>
	                		<td>
	                			<input class=" base" type="text" 
		                			onClick="WdatePicker()" placeholder="点击填日期" 
		                			value='<fmt:formatDate value="${formObject.conDate}" 
		                			pattern="yyyy-MM-dd" />' name="conDate" />
	                		</td>
	                	</tr>
	                	<tr>
	                		<td class="title" colspan="4" style="text-align: left;padding-left: 10px;">
                 				法人代表证明书
                 			</td>
	                	</tr>
	                	<tr>
	                		<td class="title">
	                			法人名
	                		</td>
	                		<td>
	                			<input maxlength="50" class=" base" type="text" name="leagl" value="${formObject.leagl}" />
	                		</td>
	                		<td class="title">
	                			单位职务
	                		</td>
	                		<td>
	                			<input maxlength="50" class=" base" type="text" name="leaDuty" value="${formObject.leaDuty}" />
	                		</td>
	                	</tr>
	                	<tr>
	                		<td class="title">
	                			单位经济性质
	                		</td>
	                		<td>
	                			<input maxlength="50" class=" base" type="text" name="nature" value="${formObject.nature}" />
	                		</td>
	                		<td class="title">
	                			营业执照/代码证号码
	                		</td>
	                		<td>
	                			<input maxlength="50" class=" base" type="text" name="busLicId" value="${formObject.busLicId}" />
	                		</td>
	                	</tr>
	                	<tr>
	                		<td class="title">
	                			单位电话号码
	                		</td>
	                		<td>
	                			<input maxlength="15" class=" base phone" type="text" name="unitTel" value="${formObject.unitTel}" />
	                		</td>
	                		<td class="title">
	                			法人代表身份证号码
	                		</td>
	                		<td>
	                			<input maxlength="18" class=" base" type="text" name="leaId" value="${formObject.leaId}" />
	                		</td>
	                	</tr>
	                	<tr>
	                		<td class="title">
	                			签发日期
	                		</td>
	                		<td>
	                			<input class=" base" type="text" 
		                			onClick="WdatePicker()" placeholder="点击填日期" 
		                			value='<fmt:formatDate value="${formObject.signDate}" 
		                			pattern="yyyy-MM-dd" />' name="signDate" />
	                		</td>
	                		<td colspan="2"></td>
	                	</tr>
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