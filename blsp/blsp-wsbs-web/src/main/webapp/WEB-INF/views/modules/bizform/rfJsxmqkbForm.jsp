<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>建设项目情况表</title>
    <meta name="decorator" content="blank" />
    <style>
		.editTable input[type='text'].has-error {
			background: #F44336;
			color: #FFF;
		}
		.noBorder > tbody > tr > td {
			border: none;
		}
	</style>
    <script>
    	$(document).ready(function(){
	    	$("#rfJsxmqkbForm").validate({
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
			var conList = [];
			$(".con").each(function (){
				var con = {};
				$(this).find('td').each(function (){
					con[$(this).find('input').attr('name')] = $(this).find('input').val();
				});
				conList.push(con);
			});
			var conJsonData = JSON.stringify(conList);
			$("#jsonDataConList").val(conJsonData);
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
			if($("#rfJsxmqkbForm").valid()){
				$("#rfJsxmqkbForm").submit();
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
			var conList = "${formObject.conList}";
			if(conList.length > 0){
				$('tr[name="conTr"]').remove();
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
	    <form id="rfJsxmqkbForm" action="${ctx}/rfJsxmqkb/save" method="post">
		    <div class="card-body card-padding">
	        	<div class="row">
		            <input type="hidden" name="id"
		                   value="${formObject.id}" class="base">
	                <input type="hidden" name="prjId"
	                 value="${formObject.prjId}" class="base">
	                <input type="hidden" name="taskId"
	                 value="${formObject.taskId}" class="base">
	                 <table class="editTable noBorder" style="width: 960px;" border="0" cellspacing="0" cellpadding="0">
	                 	<tbody>
	                 		<tr>
	                 			<td class="title" colspan="2" style="font-size: 16px;font-weight: bolder;">
	                 				建设项目情况表
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td style="width: 70%;height: 40px;"></td>
	                 			<td style="font-size: 14px;">
	                 				建设单位公章：
	                 			</td>
	                 		</tr>
	                 	</tbody>
	                 </table>
	                 <table class="editTable" style="width: 960px;" border="0" cellspacing="0" cellpadding="0">
	                 	<tbody>
	                 		<tr>
	                 			<td class="title" colspan="2" style="width: 10%;">
	                 				序号
	                 			</td>
	                 			<td class="title">
	                 				单体建筑<br/>名&nbsp;&nbsp;&nbsp;&nbsp;称
	                 			</td>
	                 			<td class="title">
	                 				建筑<br/>层数
	                 			</td>
	                 			<td class="title">
	                 				单体建筑<br/>面积（m<sup>2</sup>）
	                 			</td>
	                 			<td class="title">
	                 				单体建筑<br/>地上总建筑<br/>面积（m<sup>2</sup>）
	                 			</td>
	                 			<td class="title">
	                 				单体建筑<br/>地下总建筑<br/>面积（m<sup>2</sup>）
	                 			</td>
	                 			<td class="title">
	                 				其中生产车间、<br/>仓库及配套<br/>设备房间<br/>建筑面积（m<sup>2</sup>）
	                 			</td>
	                 			<td class="title">
	                 				其&nbsp;&nbsp;他
	                 			</td>
	                 		</tr>
	                 		<tr id="con" class="con" name="conTr">
	                 			<td>
	                 				<button type="button" onclick="addRow('con')" class="btn btn-primary"><i class="md add"></i></button>
	                 			</td>
	                 			<td>
	                 				<input maxlength="50" type="text"  name="cNum"/>
	                 			</td>
	                 			<td>
	                 				<input maxlength="50" type="text"  name="cBuildName"/>
	                 			</td>
	                 			<td>
	                 				<input maxlength="50" type="text"  name="cFloorNum"/>
	                 			</td>
	                 			<td>
	                 				<input maxlength="50" type="text"  name="cBaseSpace"/>
	                 			</td>
	                 			<td>
	                 				<input maxlength="50" type="text"  name="cOnAllSpace"/>
	                 			</td>
	                 			<td>
	                 				<input maxlength="50" type="text"  name="cDownAllSpace"/>
	                 			</td>
	                 			<td>
	                 				<input maxlength="50" type="text"  name="cOtherSpace"/>
	                 			</td>
	                 			<td>
	                 				<input maxlength="50" type="text"  name="cOther"/>
	                 			</td>
	                 		</tr>
	                 		<c:forEach items="${formObject.conList}" var="con" varStatus="i">
	                 			<c:if test="${i.index == 0}">
	                 				<tr id="con" class="con">
	                 					<td style="display: none;">
	                 						<input type="hidden" name="cId" value="${con.cId}" />
	                 					</td>
			                 			<td>
			                 				<button type="button" onclick="addRow('con')" class="btn btn-primary"><i class="md add"></i></button>
			                 				<input type="hidden" name="rId" value="${con.rId}" />
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="cNum" value="${con.cNum}"/>
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="cBuildName" value="${con.cBuildName}"/>
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="cFloorNum" value="${con.cFloorNum}"/>
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="cBaseSpace" value="${con.cBaseSpace}"/>
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="cOnAllSpace" value="${con.cOnAllSpace}"/>
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="cDownAllSpace" value="${con.cDownAllSpace}"/>
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="cOtherSpace" value="${con.cOtherSpace}"/>
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="cOther" value="${con.cOther}"/>
			                 			</td>
			                 		</tr>
			                 	</c:if>
			                 	<c:if test="${i.index > 0}">
	                 				<tr id="${con.cId}" class="con">
	                 					<td style="display: none;">
	                 						<input type="hidden" name="cId" value="${con.cId}" />
	                 					</td>
			                 			<td>
			                 				<button type="button" onclick="removeRow('${con.cId}')" class="btn btn-danger"><i class="md minus"></i></button>
			                 				<input type="hidden" name="rId" value="${con.rId}" />
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="cNum" value="${con.cNum}"/>
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="cBuildName" value="${con.cBuildName}"/>
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="cFloorNum" value="${con.cFloorNum}"/>
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="cBaseSpace" value="${con.cBaseSpace}"/>
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="cOnAllSpace" value="${con.cOnAllSpace}"/>
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="cDownAllSpace" value="${con.cDownAllSpace}"/>
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="cOtherSpace" value="${con.cOtherSpace}"/>
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="cOther" value="${con.cOther}"/>
			                 			</td>
			                 		</tr>
			                 	</c:if>
			                 </c:forEach>
	                 	</tbody>
	                 </table>
	                 <input type="hidden" name="jsonDataObj" id="jsonDataObj"/>
	                 <input type="hidden" name="jsonDataConList" id="jsonDataConList"/>
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