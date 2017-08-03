<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>防雷装置设计审核申请书</title>
    <meta name="decorator" content="blank" />
    <style>
		.editTable input[type='text'].has-error {
			background: #F44336;
			color: #FFF;
		}
	</style>
    <script>
    	$(document).ready(function(){
	    	$("#qxSjshSqsForm").validate({
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
	    	$("#qxSjshSqsForm").validate({
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
			//将  易燃易爆品、化学危险品情况   集合转为JSON格式存入隐藏框中
			var flaList = [];
			$(".fla").each(function (){
				var fla = {};
				$(this).find('td').each(function (){
					fla[$(this).find('input').attr('name')] = $(this).find('input').val();
				});
				flaList.push(fla);
			});
			var flaJsonData = JSON.stringify(flaList);
			$("#jsonDataFlaList").val(flaJsonData);
			//将  电子信息系统情况  集合转为JSON格式存入隐藏框中
			var eleList = [];
			$(".ele").each(function (){
				var ele = {};
				$(this).find('td').each(function (){
					ele[$(this).find('input').attr('name')] = $(this).find('input').val();
				});
				eleList.push(ele);
			});
			var eleJsonData = JSON.stringify(eleList);
			$("#jsonDataEleList").val(eleJsonData);
			//将所有基本信息取出转为json格式存入隐藏框
			var base = {};
			$(".base").each(function (){
				if($(this).prop("type") == "checkbox"){
					if($(this).prop("checked")){
						if(base[$(this).attr('name')]){
							base[$(this).attr('name')] = base[$(this).attr('name')]+$(this).val();
						}else{
							base[$(this).attr('name')] = $(this).val();
						}
					}
				}else{
					base[$(this).attr('name')] = $(this).val();
				}
			});
			var objJsonData = JSON.stringify(base);
			$("#jsonDataObj").val(objJsonData);
			$(".savebutton").attr("disabled",true);
			if($("#qxSjshSqsForm").valid()){
				$("#qxSjshSqsForm").submit();
			}else{
				$(".savebutton").attr("disabled",false);
			}
		}
		function refresh(){
			window.location.reload(true);
		}
		function addRow(id){
			var tr = $('#'+id).clone();
			$(tr).removeAttr('id');
			$(tr).find('input').val('');
			$(tr).find('button').removeAttr('onclick').removeClass('btn-primary').addClass('btn-danger').bind("click", function(){
				$(this).parents('tr').remove();
			});
			$(tr).find('button i.md').removeClass('add').addClass('minus');
			$('.'+id+':last').after(tr);
		}
		function removeRow(id){
			$("#"+id).remove();
		}
		$(function (){
			var flaList = "${formObject.flaList}";
			if(flaList.length > 0){
				$('tr[name="flaTr"]').remove();
			}
			var eleList = "${formObject.eleList}";
			if(eleList.length > 0){
				$('tr[name="eleTr"]').remove();
			}
		});
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
	    <form id="qxSjshSqsForm" action="${ctx}/qxSjshSqs/save" method="post">
		    <div class="card-body card-padding">
	        	<div class="row">
		            <input type="hidden" name="id"
		                   value="${formObject.id}" class="base">
	                <input type="hidden" name="prjId"
	                 value="${formObject.prjId}" class="base">
	                <input type="hidden" name="taskId"
	                 value="${formObject.taskId}" class="base">
	                 <table class="editTable" style="width: 960px;" border="0" cellspacing="0" cellpadding="0">
	                 	<tbody>
	                 		<tr>
	                 			<td class="title" colspan="8" style="font-size: 16px;font-weight: bolder;">
	                 				防雷装置设计审核申请书
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td rowspan="5" class="title" width="30px">
	                 				项目情况
	                 			</td>
	                 			<td class="title">
	                 				名称
	                 			</td>
	                 			<td colspan="6">
	                 				<input maxlength="200" class="required base" type="text" name="prjName" value="${formObject.prjName}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title">
	                 				地址
	                 			</td>
	                 			<td colspan="6">
	                 				<input maxlength="50" class=" base" type="text" name="prjAddr" value="${formObject.prjAddr}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td rowspan="2" class="title">
	                 				建设规模
	                 			</td>
	                 			<td style="padding-left: 10px;border-bottom: none;height: 30px;" colspan="6">
	                 				建筑单体
	                 				<input maxlength="50" class=" base" style="width:50px;height: auto;border-bottom: 1px solid #ccc;" type="text" value="${formObject.ridgepoleNum}" name="ridgepoleNum" />
	                 				栋（座）；总建筑面积
	                 				<input maxlength="50" class=" base" style="width:50px;height: auto;border-bottom: 1px solid #ccc;" type="text" value="${formObject.overallFloorage}" name="overallFloorage" />
	                 				平方米；
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td style="padding-left: 10px;border-top: none;height: 30px;" colspan="6">
	                 				最高建筑高度
	                 				<input maxlength="50" class=" base" style="width:50px;height: auto;border-bottom: 1px solid #ccc;" type="text" value="${formObject.heightestHeight}" name="heightestHeight" />
	                 				米；总占地面积
	                 				<input maxlength="50" class=" base" style="width:50px;height: auto;border-bottom: 1px solid #ccc;" type="text" value="${formObject.totalArea}" name="totalArea" />
	                 				平方米。
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title">
	                 				使用性质
	                 			</td>
	                 			<td colspan="6">
	                 				<input maxlength="50" class=" base" type="text" name="useNature" value="${formObject.useNature}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title" rowspan="3">
	                 				建设单位
	                 			</td>
	                 			<td class="title">
	                 				名称
	                 			</td>
	                 			<td colspan="6">
	                 				<input maxlength="50" class=" base" type="text" name="constructUnit" value="${formObject.constructUnit}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title">
	                 				地址
	                 			</td>
	                 			<td colspan="3">
	                 				<input maxlength="50" class=" base" type="text" name="constructAddr" value="${formObject.constructAddr}" />
	                 			</td>
	                 			<td class="title">
	                 				邮政编码
	                 			</td>
	                 			<td colspan="2">
	                 				<input maxlength="20" class=" base" type="text" name="constructPostcode" value="${formObject.constructPostcode}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title">
	                 				联系人
	                 			</td>
	                 			<td colspan="3">
	                 				<input maxlength="50" class=" base" type="text" name="constructLinkman" value="${formObject.constructLinkman}" />
	                 			</td>
	                 			<td class="title">
	                 				联系电话
	                 			</td>
	                 			<td colspan="2">
	                 				<input maxlength="15" class=" base phone" type="text" name="constructLinktel" value="${formObject.constructLinktel}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title" rowspan="5">
	                 				设计单位
	                 			</td>
	                 			<td class="title">
	                 				名称
	                 			</td>
	                 			<td colspan="6">
	                 				<input maxlength="50" class=" base" type="text" name="designUnit" value="${formObject.designUnit}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title">
	                 				地址
	                 			</td>
	                 			<td colspan="3">
	                 				<input maxlength="50" class=" base" type="text" name="designAddr" value="${formObject.designAddr}" />
	                 			</td>
	                 			<td class="title">
	                 				邮政编码
	                 			</td>
	                 			<td colspan="2">
	                 				<input maxlength="20" class=" base" type="text" name="designPostcode" value="${formObject.designPostcode}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title">
	                 				资质证编号
	                 			</td>
	                 			<td colspan="3">
	                 				<input maxlength="50" class=" base" type="text" name="designLicenseId" value="${formObject.designLicenseId}" />
	                 			</td>
	                 			<td class="title">
	                 				资质等级
	                 			</td>
	                 			<td colspan="2">
	                 				<input maxlength="20" class=" base" type="text" name="designLicenseRank" value="${formObject.designLicenseRank}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title">
	                 				联系人
	                 			</td>
	                 			<td colspan="3">
	                 				<input maxlength="50" class=" base" type="text" name="designLinkman" value="${formObject.designLinkman}" />
	                 			</td>
	                 			<td class="title">
	                 				联系电话
	                 			</td>
	                 			<td colspan="2">
	                 				<input maxlength="15" class=" base phone" type="text" name="designLinktel" value="${formObject.designLinktel}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title">
	                 				资格证编号
	                 			</td>
	                 			<td colspan="6">
	                 				<input maxlength="50" class=" base" type="text" name="designCplmId" value="${formObject.designCplmId}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td colspan="8" class="title">
	                 				易燃易爆品、化学危险品情况
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td colspan="2" class="title" rowspan="2">
	                 				品&nbsp;&nbsp;名
	                 			</td>
	                 			<td colspan="6" class="title">
	                 				数量（吨/每年）
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title">
	                 				生产
	                 			</td>
	                 			<td class="title">
	                 				使用
	                 			</td>
	                 			<td class="title">
	                 				储存
	                 			</td>
	                 			<td class="title">
	                 				运输
	                 			</td>
	                 			<td class="title" colspan="2">
	                 				经营
	                 			</td>
	                 		</tr>
	                 		<tr id="fla" class="fla" name="flaTr">
	                 			<td>
	                 				<button type="button" onclick="addRow('fla')" class="btn btn-primary"><i class="md add"></i></button>
	                 			</td>
	                 			<td>
	                 				<input maxlength="50" type="text"  name="fTradeName"/>
	                 			</td>
	                 			<td>
	                 				<input maxlength="50" type="text"  name="fProduct"/>
	                 			</td>
	                 			<td>
	                 				<input maxlength="50" type="text"  name="fUse"/>
	                 			</td>
	                 			<td>
	                 				<input maxlength="50" type="text"  name="fStore"/>
	                 			</td>
	                 			<td>
	                 				<input maxlength="50" type="text"  name="fTransportation"/>
	                 			</td>
	                 			<td colspan="2">
	                 				<input maxlength="50" type="text"  name="fManage"/>
	                 			</td>
	                 		</tr>
	                 		<c:forEach items="${formObject.flaList}" var="fla" varStatus="i">
	                 			<c:if test="${i.index == 0}">
	                 				<tr id="fla" class="fla">
			                 			<td>
			                 				<button type="button" onclick="addRow('fla')" class="btn btn-primary"><i class="md add"></i></button>
			                 				<input type="hidden" value="${fla.fId}" name="fId" />
			                 			</td>
			                 			<td style="display: none;">
			                 				<input type="hidden" value="${fla.qId}" name="qId" />
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="fTradeName" value="${fla.fTradeName}" />
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="fProduct" value="${fla.fProduct}" />
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="fUse" value="${fla.fUse}" />
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="fStore" value="${fla.fStore}"/>
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="fTransportation" value="${fla.fTransportation}" />
			                 			</td>
			                 			<td colspan="2">
			                 				<input maxlength="50" type="text"  name="fManage" value="${fla.fManage}" />
			                 			</td>
			                 		</tr>
	                 			</c:if>
	                 			<c:if test="${i.index > 0}">
	                 				<tr id="fla${fla.fId}" class="fla">
			                 			<td>
			                 				<button type="button" onclick="removeRow('fla${fla.fId}')" class="btn btn-danger"><i class="md minus"></i></button>
			                 				<input type="hidden" value="${fla.fId}" name="fId" />
			                 			</td>
			                 			<td style="display: none;">
			                 				<input type="hidden" value="${fla.qId}" name="qId" />
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="fTradeName" value="${fla.fTradeName}" />
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="fProduct" value="${fla.fProduct}" />
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="fUse" value="${fla.fUse}" />
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="fStore" value="${fla.fStore}"/>
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="fTransportation" value="${fla.fTransportation}" />
			                 			</td>
			                 			<td colspan="2">
			                 				<input maxlength="50" type="text"  name="fManage" value="${fla.fManage}" />
			                 			</td>
			                 		</tr>
	                 			</c:if>
	                 		</c:forEach>
	                 		<tr>
	                 			<td colspan="8" class="title">
	                 				电子信息系统情况
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td colspan="2" class="title">
	                 				系统名称
	                 			</td>
	                 			<td colspan="6" class="title">
	                 				系统结构及设备配置
	                 			</td>
	                 		</tr>
	                 		<tr id="ele" class="ele" name="eleTr">
	                 			<td>
	                 				<button type="button" onclick="addRow('ele')" class="btn btn-primary"><i class="md add"></i></button>
	                 			</td>
	                 			<td>
	                 				<input maxlength="50" type="text"  name="eSysname"/>
	                 			</td>
	                 			<td colspan="6">
	                 				<input maxlength="250" type="text"  name="eSystemFlat"/>
	                 			</td>
	                 		</tr>
	                 		<c:forEach items="${formObject.eleList}" var="ele" varStatus="i">
	                 			<c:if test="${i.index == 0}">
	                 				<tr id="ele" class="ele">
			                 			<td>
			                 				<button type="button" onclick="addRow('ele')" class="btn btn-primary"><i class="md add"></i></button>
			                 				<input type="hidden" value="${ele.eId}" name="eId" />
			                 			</td>
			                 			<td style="display: none;">
			                 				<input type="hidden" value="${ele.qId}" name="qId" />
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="eSysname" value="${ele.eSysname}" />
			                 			</td>
			                 			<td colspan="6">
			                 				<input maxlength="250" type="text"  name="eSystemFlat" value="${ele.eSystemFlat}" />
			                 			</td>
			                 		</tr>
			                 	</c:if>
			                 	<c:if test="${i.index > 0}">
	                 				<tr id="ele${ele.eId}" class="ele">
			                 			<td>
			                 				<button type="button" onclick="removeRow('ele${ele.eId}')" class="btn btn-danger"><i class="md minus"></i></button>
			                 				<input type="hidden" value="${ele.eId}" name="fId" />
			                 			</td>
			                 			<td style="display: none;">
			                 				<input type="hidden" value="${ele.qId}" name="qId" />
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="eSysname" value="${ele.eSysname}" />
			                 			</td>
			                 			<td colspan="6">
			                 				<input maxlength="250" type="text"  name="eSystemFlat" value="${ele.eSystemFlat}" />
			                 			</td>
			                 		</tr>
			                 	</c:if>
			                </c:forEach>
	                 		<tr>
	                 			<td colspan="8" style="border-bottom: none;padding: 0 10px;padding-top: 10px;">
	                 				设计简介：
	                 				<textarea maxlength="1000" rows="10" class="form-control  auto-size base" style="border: none;resize:none;text-indent:2em;width:940px;line-height: auto;" placeholder="请输入..." name="planSynopsis">${formObject.planSynopsis}</textarea>
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td colspan="3" style="border-top: none;border-right: none;height: 40px;"></td>
	                 			<td style="border-top: none;border-left: none;border-right: none;text-align: right;">
	                 				经办人：
	                 			</td>
	                 			<td style="border-top: none;border-left: none;border-right: none;">
	                 				<input maxlength="50" type="text" class=" base" name="planAgent" value="${formObject.planAgent}" />
	                 			</td>
	                 			<td style="border-top: none;border-left: none;" colspan="3">
	                 				<input class=" base" type="text" 
		                 				onClick="WdatePicker()" placeholder="点击填日期" 
		                 				value='<fmt:formatDate value="${formObject.planDate}" 
		                 				pattern="yyyy-MM-dd" />' name="planDate" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td style="height: 40px;border-bottom: none;" colspan="8"></td>
	                 		</tr>
	                 		<tr>
	                 			<td style="border-top: none;border-right: none; padding-left: 10px;height: 40px;" colspan="2">
	                 				申请单位（公章）
	                 			</td>
	                 			<td style="border-top: none;border-left: none;border-right: none;"></td>
	                 			<td style="border-top: none;border-left: none;border-right: none;text-align: right;">
	                 				经办人：
	                 			</td>
	                 			<td style="border-top: none;border-left: none;border-right: none;">
	                 				<input maxlength="50" type="text" class=" base" name="applyAgent" value="${formObject.applyAgent}" />
	                 			</td>
	                 			<td style="border-top: none;border-left: none;" colspan="3">
	                 				<input class=" base" type="text" 
		                 				onClick="WdatePicker()" placeholder="点击填日期" 
		                 				value='<fmt:formatDate value="${formObject.applyDate}" 
		                 				pattern="yyyy-MM-dd" />' name="applyDate" />
	                 			</td>
	                 		</tr>
	                 	</tbody>
	                 </table>
	                 <input type="hidden" name="jsonDataObj" id="jsonDataObj"/>
	                 <input type="hidden" name="jsonDataFlaList" id="jsonDataFlaList"/>
	                 <input type="hidden" name="jsonDataEleList" id="jsonDataEleList"/>
		            <table class="editTable" id="footTable" style="width: 960px;" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td align="center" style="height: 50px;">
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