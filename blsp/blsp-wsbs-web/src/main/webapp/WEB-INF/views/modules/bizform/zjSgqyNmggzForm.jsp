<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>珠海市金湾区施工企业农民工工资支付情况报表</title>
    <meta name="decorator" content="blank" />
    <style>
		.editTable input[type='text'].has-error {
			background: #F44336;
			color: #FFF;
		}
	</style>
    <script>
    	$(document).ready(function(){
	    	$("#zjSgqyNmggzForm").validate({
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
			if($("#zjSgqyNmggzForm").valid()){
				$("#zjSgqyNmggzForm").submit();
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
	    <form id="zjSgqyNmggzForm" action="${ctx}/zjSgqyNmggz/save" method="post">
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
	                 		<tr style="height: 50px">
	                 			<td class="title" colspan="4" style="font-size: 16px;font-weight: bolder;">
	                 				珠海市金湾区施工企业农民工工资支付情况报表
	                 			</td>
	                 		</tr>
	                 		<tr style="height: 50px">
	                 			<td class="title">
	                 				项目名称：
	                 			</td>
	                 			<td colspan="3">
	                 				<input maxlength="200" class="required" type="text" name="prjName" value="${formObject.prjName}" />
	                 			</td>
	                 		</tr>
	                 		<tr style="height: 50px">
	                 			<td class="title">
	                 				工程垫资情况
	                 			</td>
	                 			<td colspan="3" style="padding-left: 10px;">
	                 				<label class="checkbox checkbox-inline m-r-10 m-l-25">
										<input type="checkbox" class="base" value="A" name="payInCondition" <c:if test="${formObject.payInCondition.contains('A')}"> checked </c:if> />
										<i class="input-helper"></i>    
										不垫资
									</label>
									<label class="checkbox checkbox-inline m-r-10 m-l-25">
										<input type="checkbox" class="base" value="B" name="payInCondition" <c:if test="${formObject.payInCondition.contains('B')}"> checked </c:if> />
										<i class="input-helper"></i>    
										部分垫资
									</label>
									<label class="checkbox checkbox-inline m-r-10 m-l-25">
										<input type="checkbox" class="base" value="C" name="payInCondition" <c:if test="${formObject.payInCondition.contains('C')}"> checked </c:if> />
										<i class="input-helper"></i>    
										全垫资
									</label>
	                 			</td>
	                 		</tr>
	                 		<tr style="height: 80px">
	                 			<td class="title">
	                 				工程收款收取<br/>单位
	                 			<td colspan="3" style="padding-left: 10px;">
	                 				<label class="checkbox checkbox-inline m-r-10 m-l-25">
										<input type="checkbox" class="base" value="A" name="gatherUnit" <c:if test="${formObject.gatherUnit.contains('A')}"> checked </c:if> />
										<i class="input-helper"></i>    
										总公司
									</label>
									<label class="checkbox checkbox-inline m-r-10 m-l-25">
										<input type="checkbox" class="base" value="B" name="gatherUnit" <c:if test="${formObject.gatherUnit.contains('B')}"> checked </c:if> />
										<i class="input-helper"></i>    
										珠海分公司/项目部
									</label><br/><br/>
									其他：<input maxlength="200" style="width:400px;height: auto;border-bottom: 1px solid #ccc;" type="text" value="${formObject.gatherOther}" name="gatherOther" />
	                 			</td>
	                 		</tr>
	                 		<tr style="height: 80px">
	                 			<td class="title">
	                 				工资支付主体
	                 			<td colspan="3" style="padding-left: 10px;">
	                 				<label class="checkbox checkbox-inline m-r-10 m-l-25">
										<input type="checkbox" class="base" value="A" name="payTheme" <c:if test="${formObject.payTheme.contains('A')}"> checked </c:if> />
										<i class="input-helper"></i>    
										总公司
									</label>
									<label class="checkbox checkbox-inline m-r-10 m-l-25">
										<input type="checkbox" class="base" value="B" name="payTheme" <c:if test="${formObject.payTheme.contains('B')}"> checked </c:if> />
										<i class="input-helper"></i>    
										珠海分公司/项目部
									</label><br/><br/>
									其他：<input maxlength="200" style="width:400px;height: auto;border-bottom: 1px solid #ccc;" type="text" value="${formObject.payOther}" name="payOther" />
	                 			</td>
	                 		</tr>
	                 		<tr style="height: 80px">
	                 			<td class="title">
	                 				工资款来源
	                 			<td colspan="3" style="padding-left: 10px;">
	                 				<label class="checkbox checkbox-inline m-r-10 m-l-25">
										<input type="checkbox" class="base" value="A" name="salarySource" <c:if test="${formObject.salarySource.contains('A')}"> checked </c:if> />
										<i class="input-helper"></i>    
										甲方拨付的进度款
									</label>
									<label class="checkbox checkbox-inline m-r-10 m-l-25">
										<input type="checkbox" class="base" value="B" name="salarySource" <c:if test="${formObject.salarySource.contains('B')}"> checked </c:if> />
										<i class="input-helper"></i>    
										总公司自筹
									</label><br/><br/>
									其他：<input maxlength="200" style="width:400px;height: auto;border-bottom: 1px solid #ccc;" type="text" value="${formObject.salaryOther}" name="salaryOther" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title" rowspan="2">
	                 				本期工资支付<br/>情况
	                 			<td class="title">
	                 				本期完成工程量<br/>（万元）
	                 			</td>
	                 			<td class="title">
	                 				本期应付工资<br/>（万元）
	                 			</td>
	                 			<td class="title">
	                 				本期已付工资<br/>（万元）
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td height="40px">
	                 				<input maxlength="50"  type="text" name="completedAmount" value="${formObject.completedAmount}" />
	                 			</td>
	                 			<td height="40px">
	                 				<input maxlength="50"  type="text" name="wagesPayable" value="${formObject.wagesPayable}" />
	                 			</td>
	                 			<td height="40px">
	                 				<input maxlength="50"  type="text" name="hasBeenPaid" value="${formObject.hasBeenPaid}" />
	                 			</td>
	                 		</tr>
	                 		<tr style="height: 50px">
	                 			<td class="title">
	                 				拖欠工资情况<br/>及整改措施
	                 			</td>
	                 			<td colspan="3">
	                 				<input maxlength="200"  type="text" name="unpaidWages" value="${formObject.unpaidWages}" />
	                 			</td>
	                 		</tr>
	                 		<tr style="height: 50px;">
	                 			<td class="title">
	                 				填报单位（总公司）：
	                 			</td>
	                 			<td>
	                 				<input maxlength="50"  type="text" name="fillUnit" value="${formObject.fillUnit}" />
	                 			</td>
	                 			<td class="title">
	                 				核实单位（监理公司）：
	                 			</td>
	                 			<td>
	                 				<input maxlength="50"  type="text" name="checkUnit" value="${formObject.checkUnit}" />
	                 			</td>
	                 		</tr>
	                 		<tr style="height: 50px;">
	                 			<td class="title">
	                 				负责人：
	                 			</td>
	                 			<td>
	                 				<input maxlength="50"  type="text" name="principal" value="${formObject.principal}" />
	                 			</td>
	                 			<td class="title">
	                 				总监：
	                 			</td>
	                 			<td>
	                 				<input maxlength="50"  type="text" name="director" value="${formObject.director}" />
	                 			</td>
	                 		</tr>
	                 		<tr style="height: 50px;">
	                 			<td class="title">
	                 				日期：
	                 			</td>
	                 			<td>
	                 				<input class=" base" type="text" onClick="WdatePicker()" 
		                 				placeholder="点击填日期" 
		                 				value='<fmt:formatDate value="${formObject.priDate}" 
		                 				pattern="yyyy-MM-dd" />' name="priDate" />
	                 			</td>
	                 			<td class="title">
	                 				日期：
	                 			</td>
	                 			<td>
	                 				<input class=" base" type="text" onClick="WdatePicker()" 
		                 				placeholder="点击填日期" 
		                 				value='<fmt:formatDate value="${formObject.dirDate}" 
		                 				pattern="yyyy-MM-dd" />' name="dirDate" />
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