<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>建设项目环境影响登记表（第三产业）</title>
    <meta name="decorator" content="blank" />
    <style>
		.editTable input[type='text'].has-error {
			background: #F44336;
			color: #FFF;
		}
	</style>
    <script>
    	$(document).ready(function(){
	    	$("#hbJsxmDscyForm").validate({
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
			if($("#hbJsxmDscyForm").valid()){
				$("#hbJsxmDscyForm").submit();
			}else{
				$(".savebutton").attr("disabled",false);
			}
		}
		function refresh(){
			window.location.reload(true);
		}
		//返回详情信息
		function backItem(){
			var prjId = "${formObject.prjId}";
			var taskId = "${formObject.taskId}";
			window.parent.location.href = "${ctx}/project/backItem?prjId="+prjId+"&taskId="+taskId;
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
	    <form id="hbJsxmDscyForm" action="${ctx}/hbJsxmDscy/save" method="post">
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
		            			<td class="title" colspan="6" style="font-size: 16px;font-weight: bolder;">
		            				建设项目环境影响登记表（第三产业）
		            			</td>
		            		</tr>
		            		<tr>
		            			<td class="title" style="width: 20%;">
		            				项目名称
		            			</td>
		            			<td colspan="2" style="width: 30%;">
		            				<input maxlength="200" class="required" type="text" name="prjName" value="${formObject.prjName}" />
		            			</td>
		            			<td class="title" style="width: 20%;">
		            				建设单位（个人）盖章<br/>（签名）
		            			</td>
		            			<td colspan="2" style="width: 30%;">
		            				<input maxlength="200"  type="text" name="company" value="${formObject.company}" />
		            			</td>
		            		</tr>
		            		<tr>
		            			<td class="title">
		            				项目地点
		            			</td>
		            			<td colspan="2">
		            				<input maxlength="50"  type="text" name="prjAddr" value="${formObject.prjAddr}" />
		            			</td>
		            			<td class="title">
		            				申请日期
		            			</td>
		            			<td colspan="2">
	                 				<input class=" base" type="text" 
		                 				onClick="WdatePicker()" placeholder="点击填日期" 
		                 				value='<fmt:formatDate value="${formObject.applyDate}" 
		                 				pattern="yyyy-MM-dd" />' name="applyDate" />
		            			</td>
		            		</tr>
		            		<tr>
		            			<td class="title">
		            				申请人联系地址
		            			</td>
		            			<td colspan="2">
		            				<input maxlength="50"  type="text" name="appConAddr" value="${formObject.appConAddr}" />
		            			</td>
		            			<td class="title">
		            				申请人联系电话
		            			</td>
		            			<td colspan="2">
		            				<input maxlength="15" class=" phone" type="text" name="applyConTel" value="${formObject.applyConTel}" />
		            			</td>
		            		</tr>
		            		<tr>
		            			<td class="title">
		            				随表附送资料
		            			</td>
		            			<td colspan="5" style="height: 100px;padding-left: 10px;">
		            				<label class="checkbox checkbox-inline m-r-10 m-l-10">
										<input type="checkbox" value="A" name="addMaterial" <c:if test="${formObject.addMaterial.contains('A')}"> checked </c:if> />
										<i class="input-helper"></i>    
										申请报告
									</label>
									<label class="checkbox checkbox-inline m-r-10 m-l-10">
										<input type="checkbox" value="B" name="addMaterial" <c:if test="${formObject.addMaterial.contains('B')}"> checked </c:if> />
										<i class="input-helper"></i>    
										工商登记资料
									</label>
									<label class="checkbox checkbox-inline m-r-10 m-l-10">
										<input type="checkbox" value="C" name="addMaterial" <c:if test="${formObject.addMaterial.contains('C')}"> checked </c:if> />
										<i class="input-helper"></i>    
										选址位置图
									</label><br/>
									<label class="checkbox checkbox-inline m-r-10 m-l-10">
										<input type="checkbox" value="D" name="addMaterial" <c:if test="${formObject.addMaterial.contains('D')}"> checked </c:if> />
										<i class="input-helper"></i>    
										租赁合同
									</label>
									<label class="checkbox checkbox-inline m-r-10 m-l-10">
										<input type="checkbox" value="E" name="addMaterial" <c:if test="${formObject.addMaterial.contains('E')}"> checked </c:if> />
										<i class="input-helper"></i>    
										房产证复印件
									</label><br/>
									<label class="checkbox checkbox-inline m-r-10 m-l-10">
										<input type="checkbox" value="F" name="addMaterial" <c:if test="${formObject.addMaterial.contains('F')}"> checked </c:if> />
										<i class="input-helper"></i>    
										附法人身份证复印件、联系电话、联系地址
									</label><br/>
									<label class="checkbox checkbox-inline m-r-10 m-l-10">
										<input type="checkbox" value="G" name="addMaterial" <c:if test="${formObject.addMaterial.contains('G')}"> checked </c:if> />
										<i class="input-helper"></i>    
										其他
									</label>
									<input maxlength="250" type="text" name="addOther" value="${formObject.addOther}" style="height: auto;width: 200px;border-bottom: 1px solid #ccc;" />
		            			</td>
		            		</tr>
		            		<tr>
		            			<td class="title">
		            				总投资
		            			</td>
		            			<td colspan="2">
		            				<input maxlength="50"  type="text" name="allInvest" value="${formObject.allInvest}" />
		            			</td>
		            			<td class="title">
		            				批文文号
		            			</td>
		            			<td colspan="2">
		            				<input maxlength="50"  type="text" name="approvalNum" value="${formObject.approvalNum}" />
		            			</td>
		            		</tr>
		            		<tr>
		            			<td class="title">
		            				环保投资
		            			</td>
		            			<td colspan="2">
		            				<input maxlength="50"  type="text" name="envInvest" value="${formObject.envInvest}" />
		            			</td>
		            			<td class="title">
		            				项目负责人
		            			</td>
		            			<td colspan="2">
		            				<input maxlength="50"  type="text" name="prjPrincipal" value="${formObject.prjPrincipal}" />
		            			</td>
		            		</tr>
		            		<tr>
		            			<td class="title" rowspan="3">
		            				给排水情况<br/>（吨/日）
		            			</td>
		            			<td class="title" style="width: 15%;">
		            				总供水
		            			</td>
		            			<td style="width: 15%;">
		            				<input maxlength="50"  type="text" name="waterSupply" value="${formObject.waterSupply}" />
		            			</td>
		            			<td class="title" rowspan="3">
		            				年耗能情况
		            			</td>
		            			<td class="title" style="width: 15%;">
		            				电
		            			</td>
		            			<td style="width: 15%;">
		            				<input maxlength="50"  type="text" name="electricity" value="${formObject.electricity}" />
		            			</td>
		            		</tr>
		            		<tr>
		            			<td class="title">
		            				总排水量
		            			</td>
		            			<td>
		            				<input maxlength="50"  type="text" name="dewatering" value="${formObject.dewatering}" />
		            			</td>
		            			<td class="title">
		            				油
		            			</td>
		            			<td>
		            				<input maxlength="50"  type="text" name="oil" value="${formObject.oil}" />
		            			</td>
		            		</tr>
		            		<tr>
		            			<td class="title">
		            				排水去向
		            			</td>
		            			<td>
		            				<input maxlength="50"  type="text" name="dewateringWhere" value="${formObject.dewateringWhere}" />
		            			</td>
		            			<td class="title">
		            				气体燃料
		            			</td>
		            			<td>
		            				<input maxlength="50"  type="text" name="gasFuel" value="${formObject.gasFuel}" />
		            			</td>
		            		</tr>
		            		<tr>
		            			<td class="title">
		            				使用设备<br/>（台数、型号）
		            			</td>
		            			<td colspan="5" style="padding: 0 10px;height: 100px;">
		            				<textarea rows="5" style="border: none;resize:none;width: 740px;" maxlength="250" class="form-control auto-size " placeholder="请输入..." name="useDevice">${formObject.useDevice}</textarea>
		            			</td>
		            		</tr>
		            		<tr>
		            			<td class="title">
		            				经营范围
		            			</td>
		            			<td colspan="5" style="padding: 0 10px;height: 100px;">
		            				<textarea rows="5" style="border: none;resize:none;width: 740px;" maxlength="250" class="form-control auto-size " placeholder="请输入..." name="businessScope">${formObject.businessScope}</textarea>
		            			</td>
		            		</tr>
		            		<tr>
		            			<td class="title">
		            				污染物排<br/>放、处理<br/>情况说明
		            			</td>
		            			<td colspan="5" style="padding: 0 10px;height: 150px;">
		            				<textarea rows="8" style="border: none;resize:none;width: 740px;" maxlength="500" class="form-control auto-size " placeholder="请输入..." name="disposeExplain">${formObject.disposeExplain}</textarea>
		            			</td>
		            		</tr>
		            		<tr>
		            			<td class="title">
		            				经办人
		            			</td>
		            			<td colspan="2">
		            				<input maxlength="50"  type="text" name="operator" value="${formObject.operator}" />
		            			</td>
		            			<td class="title">
		            				经办人联系电话
		            			</td>
		            			<td colspan="2">
		            				<input maxlength="15" class=" phone" type="text" name="operTel" value="${formObject.operTel}" />
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