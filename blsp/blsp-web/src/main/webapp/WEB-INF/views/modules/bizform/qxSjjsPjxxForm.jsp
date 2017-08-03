<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>防雷装置设计技术评价信息表</title>
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
	</style>
    <script>
    	$(document).ready(function(){
	    	$("#qxSjjsPjxxForm").validate({
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
	    	$("#qxSjjsPjxxForm").validate({
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
        	<h2>防雷装置设计技术评价信息表<small>您可通过本功能进行防雷装置设计技术评价信息表申报</small></h2>
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
    
	    <form id="qxSjjsPjxxForm" action="${ctx}/qxSjjsPjxx/save" method="post">
		    <div class="card-body card-padding">
	        	<div class="row">
		            <input type="hidden" name="id"
		                   value="${formObject.id}">
	                <input type="hidden" name="prjId"
	                 value="${formObject.prjId}">
	                <input type="hidden" name="taskId"
	                 value="${formObject.taskId}">
	                 <table class="editTable">
	                 	<tbody>
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
	                 			<td colspan="3">
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
	                 <br/><br/>
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