<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>防雷装置竣工验收申请书</title>
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
	    	$("#qxJgysSqsForm").validate({
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
	    	$("#qxJgysSqsForm").validate({
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
			if($("#qxJgysSqsForm").valid()){
				$("#qxJgysSqsForm").submit();
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
        	<h2>防雷装置竣工验收申请书<small>您可通过本功能进行防雷装置竣工验收申请书申报</small></h2>
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
    
	    <form id="qxJgysSqsForm" action="${ctx}/qxJgysSqs/save" method="post">
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
	                 			<td colspan="3" style="border-right: none;border-bottom: none;"></td>
	                 			<td colspan="3" style="border-left:none;border-bottom: none;height: 50px;font-size: 12px;">
	                 				申请单位（公章）：
	                 				<input maxlength="50"  style="width:200px;height: auto;border-bottom: 1px solid #ccc;" type="text" value="${formObject.applyUnit}" name="applyUnit" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td colspan="3" style="border-right: none;border-top: none;border-bottom: none;"></td>
	                 			<td colspan="3" style="border-left:none;border-top: none;border-bottom: none;height: 50px;font-size: 12px;">
	                 				申请项目：
	                 				<input maxlength="200"  style="width:250px;height: auto;border-bottom: 1px solid #ccc;" type="text" value="${formObject.applyPrjName}" name="applyPrjName" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td style="border-right: none;border-top: none;" colspan="3"></td>
	                 			<td colspan="3" style="border-left:none;border-top: none;height: 50px;font-size: 12px;">
	                 				申请时间：
	                 				<div class="dtp-container dropdown" style="display:inline;">
	                 					<input type="text" class="date-picker " data-toggle="dropdown" style="width:250px;height: auto;border-bottom: 1px solid #ccc;" aria-expanded="false" value='<fmt:formatDate value="${formObject.applyDate}" pattern="yyyy-MM-dd" />' name="applyDate" />
	                 				</div>
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title" colspan="2">
	                 				项目名称
	                 			</td>
	                 			<td colspan="4">
	                 				<input maxlength="200" class="required" type="text" name="prjName" value="${formObject.prjName}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title" colspan="2">
	                 				项目地址
	                 			</td>
	                 			<td colspan="4">
	                 				<input maxlength="50"  type="text" name="prjAddr" value="${formObject.prjAddr}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title" colspan="3">
	                 				《防雷装置设计核准意见书》编号
	                 			</td>
	                 			<td colspan="3">
	                 				<input maxlength="50"  type="text" name="opinionNum" value="${formObject.opinionNum}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title" colspan="3">
	                 				《防雷装置检测报告》编号
	                 			</td>
	                 			<td colspan="3">
	                 				<input maxlength="50"  type="text" name="testReportNum" value="${formObject.testReportNum}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title" colspan="2">
	                 				开工时间
	                 			</td>
	                 			<td style="width: 20%;">
	                 				<div class="dtp-container dropdown">
	                 					<input type="text" class="date-picker " data-toggle="dropdown" aria-expanded="false" value='<fmt:formatDate value="${formObject.startWorkDate}" pattern="yyyy-MM-dd" />' name="startWorkDate" />
	                 				</div>
	                 			</td>
	                 			<td class="title" style="width: 20%;">
	                 				竣工时间
	                 			</td>
	                 			<td colspan="2">
	                 				<div class="dtp-container dropdown">
	                 					<input type="text" class="date-picker " data-toggle="dropdown" aria-expanded="false" value='<fmt:formatDate value="${formObject.completionDate}" pattern="yyyy-MM-dd" />' name="completionDate" />
	                 				</div>
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title" rowspan="3" style="width: 35px;">
	                 				建设单位
	                 			</td>
	                 			<td class="title">
	                 				名称
	                 			</td>
	                 			<td colspan="4">
	                 				<input maxlength="50"  type="text" name="buildName" value="${formObject.buildName}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title">
	                 				地址
	                 			</td>
	                 			<td colspan="2">
	                 				<input maxlength="50"  type="text" name="buildAddr" value="${formObject.buildAddr}" />
	                 			</td>
	                 			<td class="title">
	                 				邮政编码
	                 			</td>
	                 			<td>
	                 				<input maxlength="15"  type="text" name="buildPostcode" value="${formObject.buildPostcode}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title">
	                 				联系人
	                 			</td>
	                 			<td colspan="2">
	                 				<input maxlength="50"  type="text" name="buildLinkman" value="${formObject.buildLinkman}" />
	                 			</td>
	                 			<td class="title">
	                 				联系电话
	                 			</td>
	                 			<td>
	                 				<input maxlength="15" class=" phone" type="text" name="buildLinktel" value="${formObject.buildLinktel}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title" rowspan="4">
	                 				设计单位
	                 			</td>
	                 			<td class="title">
	                 				名称
	                 			</td>
	                 			<td colspan="4">
	                 				<input maxlength="50"  type="text" name="designName" value="${formObject.designName}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title">
	                 				地址
	                 			</td>
	                 			<td colspan="2">
	                 				<input maxlength="50"  type="text" name="designAddr" value="${formObject.designAddr}" />
	                 			</td>
	                 			<td class="title">
	                 				邮政编码
	                 			</td>
	                 			<td>
	                 				<input maxlength="15"  type="text" name="designPostcode" value="${formObject.designPostcode}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title">
	                 				联系人
	                 			</td>
	                 			<td colspan="2">
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
	                 			<td class="title">
	                 				资质证编号
	                 			</td>
	                 			<td colspan="2">
	                 				<input maxlength="50"  type="text" name="designLicenseNum" value="${formObject.designLicenseNum}" />
	                 			</td>
	                 			<td class="title">
	                 				资质等级
	                 			</td>
	                 			<td>
	                 				<input maxlength="50"  type="text" name="designCredential" value="${formObject.designCredential}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title" rowspan="6">
	                 				施工单位
	                 			</td>
	                 			<td class="title">
	                 				名称
	                 			</td>
	                 			<td colspan="4">
	                 				<input maxlength="50"  type="text" name="constructionName" value="${formObject.constructionName}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title">
	                 				地址
	                 			</td>
	                 			<td colspan="2">
	                 				<input maxlength="50"  type="text" name="constructionAddr" value="${formObject.constructionAddr}" />
	                 			</td>
	                 			<td class="title">
	                 				邮政编码
	                 			</td>
	                 			<td>
	                 				<input maxlength="15"  type="text" name="constructionPostcode" value="${formObject.constructionPostcode}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title">
	                 				资质证编号
	                 			</td>
	                 			<td colspan="2">
	                 				<input maxlength="50"  type="text" name="constructionLicenseNum" value="${formObject.constructionLicenseNum}" />
	                 			</td>
	                 			<td class="title">
	                 				资质等级
	                 			</td>
	                 			<td>
	                 				<input maxlength="50"  type="text" name="constructionCredential" value="${formObject.constructionCredential}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title">
	                 				联系人
	                 			</td>
	                 			<td colspan="2">
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
	                 			<td class="title">
	                 				资格证编号
	                 			</td>
	                 			<td colspan="4">
	                 				<input maxlength="50"  type="text" name="constructionCgfnsNum" value="${formObject.constructionCgfnsNum}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title">
	                 				现场负责人
	                 			</td>
	                 			<td colspan="2">
	                 				<input maxlength="50"  type="text" name="constructionScenePrincipal" value="${formObject.constructionScenePrincipal}" />
	                 			</td>
	                 			<td class="title">
	                 				联系电话
	                 			</td>
	                 			<td>
	                 				<input maxlength="15" class=" phone" type="text" name="constructionPrincipalLinktel" value="${formObject.constructionPrincipalLinktel}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td></td>
	                 			<td class="title">
	                 				防雷类别
	                 			</td>
	                 			<td colspan="4">
	                 				<input maxlength="50"  type="text" name="lightningCategory" value="${formObject.lightningCategory}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td valign="top" class="title" rowspan="2" >
	                 				项目概况
	                 			</td>
	                 			<td style="border-bottom: none;height: 100px;padding:0 10px;" colspan="5">
	                 				<textarea maxlength="1000" class="form-control auto-size " placeholder="请输入..." name="prjProfile" rows="8" style="resize:none;text-indent:2em;border-bottom: none;">${formObject.prjProfile}</textarea>
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td style="border-top: none;height: 30px;padding-left: 30px;" colspan="5">
	                 				注：对建设工程而言，应有单体建筑名称、数量、总建筑面积等信息。
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td colspan="3" style="height: 40px;padding-left: 20px;border-right: none;border-bottom: none;">
	                 				建设单位（公章）：
	                 			</td>
	                 			<td style="border-left: none;border-bottom: none;"></td>
	                 			<td colspan="2" style="padding-left: 20px;border-bottom: none;">
	                 				施工单位（公章）：
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td colspan="4" style="height: 80px;border-bottom: none;border-top: none;"></td>
	                 			<td colspan="2" style="border-bottom: none;border-top: none;"></td>
	                 		</tr>
	                 		<tr>
	                 			<td colspan="3" style="height: 40px;border-right: none;border-bottom: none;border-top: none;padding-left: 30px;">
	                 				经办人：
	                 				<input maxlength="50"  style="width:200px;height: auto;" placeholder="点击此处填写" type="text" value="${formObject.buildOperator}" name="buildOperator" />
	                 			</td>
	                 			<td style="border-left: none;border-bottom: none;border-top: none;"></td>
	                 			<td colspan="2" style="padding-left: 30px;border-bottom: none;border-top: none;">
	                 				经办人：
	                 				<input maxlength="50"  style="width:200px;height: auto;" placeholder="点击此处填写" type="text" value="${formObject.constructionOperator}" name="constructionOperator" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td colspan="3" style="height: 40px;border-right: none;border-top: none;"></td>
	                 			<td style="border-left: none;border-top: none;">
	                 				<div class="dtp-container dropdown" style="float: right;">
	                 					<input type="text" class="date-picker  base" data-toggle="dropdown" aria-expanded="false" placeholder="点击填日期" value='<fmt:formatDate value="${formObject.buildDate}" pattern="yyyy-MM-dd" />' name="buildDate" >
	                 				</div>
	                 			</td>
	                 			<td style="border-top: none;border-right:none;"></td>
	                 			<td style="border-top: none;border-left: none;">
	                 				<div class="dtp-container dropdown" style="float: right;">
	                 					<input type="text" class="date-picker  base" data-toggle="dropdown" aria-expanded="false" placeholder="点击填日期" value='<fmt:formatDate value="${formObject.constructionDate}" pattern="yyyy-MM-dd" />' name="constructionDate" >
	                 				</div>
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