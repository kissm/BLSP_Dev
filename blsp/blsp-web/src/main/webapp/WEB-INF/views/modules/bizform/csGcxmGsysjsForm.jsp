<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>
    	<c:choose>
    		<c:when test="${formObject.taskId eq 1}">工程项目概算审核申报表</c:when>
    		<c:when test="${formObject.taskId eq 2}">工程项目预算审核申报表</c:when>
    		<c:otherwise>工程项目结算审核申报表</c:otherwise>
    	</c:choose>
    </title>
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
	    	$("#csGcxmGsysjsForm").validate({
				rules: {
					'operLinkPhone':{phone:true},
					'sendLinkPhone':{phone:true}
				},
				messages: {
					'operLinkPhone':{phone:"格式为:固话为区号(3-4位)号码(7-8位),手机为:13,14,15,17,18号段"},
					'sendLinkPhone':{phone:"格式为:固话为区号(3-4位)号码(7-8位),手机为:13,14,15,17,18号段"}
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
	    	var view = "${view}";
	    	if(view){
	    		$("header").remove();
	    		$("input").attr("readonly",true);
	    		$("textarea").attr("disabled",true);
	    		$("button").hide();
	    		/* $(".actions").append('<li>'+
						'<button type="button" class="btn btn-info btn-sm" data-toggle= "modal" onClick="createLocpde()">写码</button>'+
    				     '</li>&nbsp;&nbsp;'); */
	    		if(view != "3"){
		    		$(".actions").append('<li>'+
						'<button type="button"data-toggle= "modal" class="btn btn-success btn-sm" onclick="backItem()">返回</button>'+
						'</li>');
	    		}
	    	}
		});
		function saveForm(){
			$(".savebutton").attr("disabled",true);
			if($("#csGcxmGsysjsForm").valid()){
				$("#csGcxmGsysjsForm").submit();
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
		
		//生成表单龙贝码
		function createLocpde(){
			var projectId = "${formObject.prjId}";
			var taskDefId = "${formObject.taskId}";
			var formCode = "${formCode}";
			window.open ('${ctx}/project/createFormLpcode?formCode='+formCode+'&taskDefId='+taskDefId+'&prjInstanceVo.id='+projectId,'_blank');
		}
    </script>
</head>
<body>
	<div class="card">
    	<div class="card-header">
        	<h2>
		        <c:choose>
		    		<c:when test="${formObject.taskId eq 1}">工程项目概算审核申报表</c:when>
		    		<c:when test="${formObject.taskId eq 2}">工程项目预算审核申报表</c:when>
		    		<c:otherwise>工程项目结算审核申报表</c:otherwise>
		    	</c:choose>
		    	<small>
		    		您可通过本功能进行<c:choose><c:when test="${formObject.taskId eq 1}">工程项目概算审核申报表</c:when><c:when test="${formObject.taskId eq 2}">工程项目预算审核申报表</c:when><c:otherwise>工程项目结算审核申报表</c:otherwise></c:choose>申报
		    	</small>
	    	</h2>
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
    
	    <form id="csGcxmGsysjsForm" action="${ctx}/csGcxmGsysjs/save" method="post">
		    <div class="card-body card-padding">
	        	<div class="row">
		            <input type="hidden" name="id" value="${formObject.id}">
	                <input type="hidden" name="prjId" value="${formObject.prjId}">
	                <input type="hidden" name="taskId" value="${formObject.taskId}">
		            <table class="editTable">
		            	<tbody>
		            		<tr>
		            			<td class="title" style="width: 12.5%;">
		            				项目名称
		            			</td>
		            			<td style="width: 37.5%;" colspan="3">
		            				<input maxlength="200" class="required" type="text" name="prjName" value="${formObject.prjName}" />
		            			</td>
		            			<td class="title" style="width: 12.5%;">
		            				申报金额<br/>（元）
		            			</td>
		            			<td style="width: 12.5%;">
		            				<input maxlength="50" class="required" type="text" name="declarAmount" value="${formObject.declarAmount}" />
		            			</td>
		            			<td class="title" style="width: 12.5%;">
		            				申报日期
		            			</td>
		            			<td style="width: 12.5%;">
		            				<div class="dtp-container dropdown">
	                 					<input type="text" class="date-picker required" data-toggle="dropdown" aria-expanded="false" placeholder="点击填日期" value='<fmt:formatDate value="${formObject.declarDate}" pattern="yyyy-MM-dd" />' name="declarDate" />
	                 				</div>
		            			</td>
		            		</tr>
		            		<tr>
		            			<td class="title">
		            				申报单位<br/>（建设单位）
		            			</td>
		            			<td colspan="3">
		            				<input maxlength="200" class="required" type="text" name="company" value="${formObject.company}" />
		            			</td>
		            			<td class="title">
		            				经办人
		            			</td>
		            			<td>
		            				<input maxlength="50" class="required" type="text" name="operator" value="${formObject.operator}" />
		            			</td>
		            			<td class="title">
		            				联系电话
		            			</td>
		            			<td>
		            				<input maxlength="20" class="required" type="text" name="operLinkPhone" value="${formObject.operLinkPhone}" />
		            			</td>
		            		</tr>
		            		<tr>
		            			<td class="title">
		            				区行政服务中心<br/>接收时间
		            			</td>
		            			<td colspan="3">
		            				<div class="dtp-container dropdown">
	                 					<input type="text" class="date-time-picker required" data-toggle="dropdown" aria-expanded="false" placeholder="点击填日期" value='<fmt:formatDate value="${formObject.receiveTime}" pattern="yyyy-MM-dd HH:mm:ss" />' name="receiveTime" />
	                 				</div>
		            			</td>
		            			<td class="title">
		            				接收人
		            			</td>
		            			<td>
		            				<input maxlength="50" class="required" type="text" name="sendee" value="${formObject.sendee}" />
		            			</td>
		            			<td class="title">
		            				联系电话
		            			</td>
		            			<td>
		            				<input maxlength="20" class="required" type="text" name="sendLinkPhone" value="${formObject.sendLinkPhone}" />
		            			</td>
		            		</tr>
		            		<tr>
		            			<td class="title" colspan="8">承&nbsp;&nbsp;诺&nbsp;&nbsp;书</td>
		            		</tr>
		            		<tr>
		            			<td colspan="8" style="text-indent: 2em;padding-top: 20px;padding-bottom: 20px;padding-left: 10px;padding-right: 10px;border-bottom: none;">
		            				我单位郑重承诺：所提交的文件、图纸等送审资料均为最终版本，在审核期间不会提出修改意见，如发现送审资料不实或有虚假、隐匿等违规行为，我单位愿承担全部责任。
		            			</td>
		            		</tr>
		            		<tr>
		            			<td colspan="6" style="padding-top: 10px;padding-bottom: 10px;border-top: none;border-right: none;border-bottom: none;" align="right">
		            				申报单位（建设单位）经办人签名：
		            			</td>
		            			<td colspan="2" style="border-top: none;border-bottom: none;border-left: none;">
		            				<input maxlength="50"  type="text" name="operAutograph" value="${formObject.operAutograph}" />
		            			</td>
		            		</tr>
		            		<tr>
		            			<td colspan="6" style="padding-top: 10px;padding-bottom: 10px;border-top: none;border-right: none;" align="right">
		            				日期：
		            			</td>
		            			<td colspan="2" style="border-top: none;border-left: none;">
		            				<div class="dtp-container dropdown">
	                 					<input type="text" class="date-picker" data-toggle="dropdown" aria-expanded="false" placeholder="点击填日期" value='<fmt:formatDate value="${formObject.operSignDate}" pattern="yyyy-MM-dd" />' name="operSignDate" />
	                 				</div>
		            			</td>
		            		</tr>
		            		<tr>
		            			<td class="title" colspan="8">区财审中心内部目录</td>
		            		</tr>
		            		<tr>
		            			<td class="title">
		            				预接收<br/>时间
		            			</td>
		            			<td>
		            				<div class="dtp-container dropdown">
	                 					<input type="text" class="date-time-picker" data-toggle="dropdown" aria-expanded="false" placeholder="点击填日期" value='<fmt:formatDate value="${formObject.estReceiveTime}" pattern="yyyy-MM-dd HH:mm:ss" />' name="estReceiveTime" />
	                 				</div>
		            			</td>
		            			<td class="title">
		            				接收人
		            			</td>
		            			<td>
		            				<input maxlength="50"  type="text" name="estSendee" value="${formObject.estSendee}" />
		            			</td>
		            			<td class="title">
		            				正式受理<br/>时间
		            			</td>
		            			<td>
		            				<div class="dtp-container dropdown">
	                 					<input type="text" class="date-time-picker" data-toggle="dropdown" aria-expanded="false" placeholder="点击填日期" value='<fmt:formatDate value="${formObject.formalAcceptTime}" pattern="yyyy-MM-dd HH:mm:ss" />' name="formalAcceptTime" />
	                 				</div>
		            			</td>
		            			<td class="title">
		            				主审人
		            			</td>
		            			<td>
		            				<input maxlength="50"  type="text" name="referee" value="${formObject.referee}" />
		            			</td>
		            		</tr>
		            		<tr>
		            			<td class="title">
		            				档案编号
		            			</td>
		            			<td>
		            				<input maxlength="100"  type="text" name="fileNo" value="${formObject.fileNo}" />
		            			</td>
		            			<td class="title">
		            				项目排号
		            			</td>
		            			<td>
		            				<input maxlength="100"  type="text" name="proNo" value="${formObject.proNo}" />
		            			</td>
		            			<td class="title">
		            				协审单位
		            			</td>
		            			<td colspan="3">
		            				<input maxlength="200"  type="text" name="coUnit" value="${formObject.coUnit}" />
		            			</td>
		            		</tr>
		            		<tr>
		            			<td class="title">
		            				备注
		            			</td>
		            			<td colspan="7" style="padding: 0 10px;">
		            				<textarea rows="10" style="border-bottom: none;resize:none;text-indent: 2em;" maxlength="1000" class="form-control auto-size " placeholder="请输入..." name="remarks">${formObject.remarks}</textarea>
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