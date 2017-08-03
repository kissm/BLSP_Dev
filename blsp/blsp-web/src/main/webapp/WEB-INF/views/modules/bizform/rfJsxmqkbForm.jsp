<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>建设项目情况表</title>
    <meta name="decorator" content="blank" />
    <style>
		.editTable {
			width: 100%;
		}
		.editTable > tbody > tr > td {
			border: 1px solid #ccc;
		}
		.editTable td.title {
			padding: 6px;
			text-align: center;
			background: #F7F7F7;
		}
		.editTable td.content {
			padding: 6px;
		}
		.editTable input[type='text'] {
			width: 100%;
			height: 100%;
			border: none;
			padding: 0px 6px;
			resize: none;
		}
		.editTable input[type='text'].has-error {
			background: #F44336;
			color: #FFF;
		}
		.noBorder > tbody > tr > td {
			border: none;
			height: 40px;
		}
	</style>
    <script>
    	$(document).ready(function(){
	    	$("#rfJsxmqkbForm").validate({
				rules: {
//					'entityIdcode':{card:true}
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
					element.attr('title',label.html()).tooltip();
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
	    	$("#rfJsxmqkbForm").validate({
    			rules: {
//    				'entityIdcode':{card:true}
    			},
    			submitHandler: function(form){
  					form.submit();
    			}
    		});
	    	var view = "${view}";
	    	if(view){
	    		$("header").remove();
	    		$("input").attr("readonly",true);
	    		$("textarea").attr("disabled",true);
	    		$(".base").each(function (){
	    			if($(this).prop("type") == "checkbox"){
	    				$(this).attr("disabled",true);
	    			}
	    		});
	    		$("button").hide();
	    		if(view != "3"){
		    		$(".actions").append('<li>'+
		                		  '<button data-toggle="tooltip" data-placement="bottom" type="button"'+
		                          'title="返回" class="btn btn-success btn-icon m-r-5"'+
		                          'onclick="backItem()">'+
		                    	  '<i class="md md-arrow-back"></i>'+
		                		  '</button>'+
	            				  '</li>');
	    		}
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
			$(tr).find('button i.md').removeClass('md-add').addClass('md-clear');
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
		//返回详情信息
		function backItem(){
			var view = "${view}";
			var prjId = "${formObject.prjId}";
			var taskId = "${formObject.taskId}";
			window.parent.location.href = "${ctx}/project/backItem?prjId="+prjId+"&taskId="+taskId+"&view="+view;
		}
    </script>
</head>
<body>
	<div class="card">
    	<div class="card-header">
        	<h2>建设项目情况表<small>您可通过本功能进行建设项目情况表申报</small></h2>
	        <ul class="actions">
	            <li>
	                <button data-toggle="tooltip" data-placement="bottom" type="button"
	                        title="返回" class="btn btn-success btn-icon m-r-5 savebutton"
	                        onclick="javascrtpt:history.go(-1)">
	                    <i class="md md-arrow-back"></i>
	                </button>
            	</li>
	            <li>
					<button title="保存" onclick="saveForm();" data-toggle="tooltip" data-placement="bottom" type="button" class="btn btn-info btn-icon m-r-5 savebutton" ><i class="md md-save"></i></button>
				</li>
	            <li>
	                <button data-toggle="tooltip" data-placement="bottom" type="button"
	                        title="刷新" class="btn btn-default btn-icon m-r-5 savebutton"
	                        onclick="refresh();">
	                    <i class="md md-autorenew"></i>
	                </button>
	            </li>
	        </ul>
    	</div>
    
	    <form id="rfJsxmqkbForm" action="${ctx}/rfJsxmqkb/save" method="post">
		    <div class="card-body card-padding">
	        	<div class="row">
		            <input type="hidden" name="id"
		                   value="${formObject.id}" class="base">
	                <input type="hidden" name="prjId"
	                 value="${formObject.prjId}" class="base">
	                <input type="hidden" name="taskId"
	                 value="${formObject.taskId}" class="base">
	                 <table class="editTable noBorder">
	                 	<tbody>
	                 		<tr>
	                 			<td style="width: 70%;"></td>
	                 			<td style="font-size: 14px;">
	                 				建设单位公章：
	                 			</td>
	                 		</tr>
	                 	</tbody>
	                 </table>
	                 <table class="editTable">
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
	                 				基底建筑<br/>面积（m<sup>2</sup>）
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
	                 				<button type="button" onclick="addRow('con')" class="btn btn-primary"><i class="md md-add"></i></button>
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
			                 				<button type="button" onclick="addRow('con')" class="btn btn-primary"><i class="md md-add"></i></button>
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
			                 				<button type="button" onclick="removeRow('${con.cId}')" class="btn btn-danger"><i class="md md-clear"></i></button>
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
	                 <br/><br/>
	                 <input type="hidden" name="jsonDataObj" id="jsonDataObj"/>
	                 <input type="hidden" name="jsonDataConList" id="jsonDataConList"/>
		            <div class="btn-demo text-center col-xs-12">
			            <button data-toggle="modal" onclick="saveForm();"
			                    class="btn btn-primary waves-effect savebutton" type="button">保存</button>
			            <button class="btn btn-default waves-effect savebutton" type="button"
			                    onclick="javascrtpt:history.go(-1)">返回</button>
			        </div>
	        	</div>
	    	</div>
	    </form>
	</div>
</body>