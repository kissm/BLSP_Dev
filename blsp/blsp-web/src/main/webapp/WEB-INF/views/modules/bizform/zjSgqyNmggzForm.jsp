<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>珠海市金湾区施工企业农民工工资支付情况报表</title>
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
	    	$("#zjSgqyNmggzForm").validate({
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
	    	$("#zjSgqyNmggzForm").validate({
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
			if($("#zjSgqyNmggzForm").valid()){
				$("#zjSgqyNmggzForm").submit();
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
        	<h2>珠海市金湾区施工企业农民工工资支付情况报表<small>您可通过本功能进行珠海市金湾区施工企业农民工工资支付情况报表申报</small></h2>
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
    
	    <form id="zjSgqyNmggzForm" action="${ctx}/zjSgqyNmggz/save" method="post">
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
	                 			<td colspan="3">
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
	                 				工程收款收取<br/>
	                 				单位
	                 			<td colspan="3">
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
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									其他：<input maxlength="200" style="width:400px;height: auto;border-bottom: 1px solid #ccc;" type="text" value="${formObject.gatherOther}" name="gatherOther" />
	                 			</td>
	                 		</tr>
	                 		<tr style="height: 80px">
	                 			<td class="title">
	                 				工资支付主体
	                 			<td colspan="3">
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
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									其他：<input maxlength="200" style="width:400px;height: auto;border-bottom: 1px solid #ccc;" type="text" value="${formObject.payOther}" name="payOther" />
	                 			</td>
	                 		</tr>
	                 		<tr style="height: 80px">
	                 			<td class="title">
	                 				工资款来源
	                 			<td colspan="3">
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
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									其他：<input maxlength="200" style="width:400px;height: auto;border-bottom: 1px solid #ccc;" type="text" value="${formObject.salaryOther}" name="salaryOther" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title" rowspan="2">
	                 				本期工资支付<br/>
	                 				情况
	                 			<td class="title">
	                 				本期完成工程量
	                 				<br/>（万元）
	                 			</td>
	                 			<td class="title">
	                 				本期应付工资
	                 				<br/>（万元）
	                 			</td>
	                 			<td class="title">
	                 				本期已付工资
	                 				<br/>（万元）
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
	                 				拖欠工资情况<br/>
	                 				及整改措施
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
	                 				<div class="dtp-container dropdown">
	                 					<input type="text" class="date-picker " data-toggle="dropdown" aria-expanded="false" value='<fmt:formatDate value="${formObject.priDate}" pattern="yyyy-MM-dd" />' name="priDate" />
	                 				</div>
	                 			</td>
	                 			<td class="title">
	                 				日期：
	                 			</td>
	                 			<td>
	                 				<div class="dtp-container dropdown">
	                 					<input type="text" class="date-picker " data-toggle="dropdown" aria-expanded="false" value='<fmt:formatDate value="${formObject.dirDate}" pattern="yyyy-MM-dd" />' name="dirDate" />
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