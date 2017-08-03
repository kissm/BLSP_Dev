<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>建设项目环境影响登记表（基建项目）</title>
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
	    	$("#hbJsxmJjxmForm").validate({
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
	    		$(".actions").append('<li>'+
						'<button type="button" class="btn btn-info btn-sm" data-toggle= "modal" onClick="createLocpde()">写码</button>'+
    				     '</li>&nbsp;&nbsp;');
	    		if(view != "3"){
		    		$(".actions").append('<li>'+
						'<button type="button"data-toggle= "modal" class="btn btn-success btn-sm" onclick="backItem()">返回</button>'+
						'</li>');
	    		}
	    	}
		});
		function saveForm(){
			$(".savebutton").attr("disabled",true);
			if($("#hbJsxmJjxmForm").valid()){
				$("#hbJsxmJjxmForm").submit();
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
        	<h2>建设项目环境影响登记表（基建项目）<small>您可通过本功能进行建设项目环境影响登记表（基建项目）申报</small></h2>
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
    
	    <form id="hbJsxmJjxmForm" action="${ctx}/hbJsxmJjxm/save" method="post">
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
		            			<td class="title" style="width: 20%;">
		            				项目名称
		            			</td>
		            			<td style="width: 30%;">
		            				<input maxlength="200" class="required" type="text" name="prjName" value="${formObject.prjName}" />
		            			</td>
		            			<td class="title" style="width: 20%;">
		            				建设单位（个人）盖章<br/>（签名）
		            			</td>
		            			<td style="width: 30%;">
		            				<input maxlength="200"  type="text" name="company" value="${formObject.company}" />
		            			</td>
		            		</tr>
		            		<tr>
		            			<td class="title">
		            				项目地点
		            			</td>
		            			<td>
		            				<input maxlength="50"  type="text" name="prjAddr" value="${formObject.prjAddr}" />
		            			</td>
		            			<td class="title">
		            				申请日期
		            			</td>
		            			<td>
		            				<div class="dtp-container dropdown">
	                 					<input type="text" class="date-picker " data-toggle="dropdown" aria-expanded="false" placeholder="点击填日期" value='<fmt:formatDate value="${formObject.applyDate}" pattern="yyyy-MM-dd" />' name="applyDate" >
	                 				</div>
		            			</td>
		            		</tr>
		            		<tr>
		            			<td class="title">
		            				申请人联系地址
		            			</td>
		            			<td>
		            				<input maxlength="50"  type="text" name="appConAddr" value="${formObject.appConAddr}" />
		            			</td>
		            			<td class="title">
		            				申请人联系电话
		            			</td>
		            			<td>
		            				<input maxlength="15" class=" phone" type="text" name="applyConTel" value="${formObject.applyConTel}" />
		            			</td>
		            		</tr>
		            		<tr>
		            			<td class="title">
		            				随表附送资料
		            			</td>
		            			<td colspan="3" style="height: 100px;">
		            				<label class="checkbox checkbox-inline m-r-10 m-l-10">
										<input type="checkbox" value="A" name="addMaterial" <c:if test="${formObject.addMaterial.contains('A')}"> checked </c:if> />
										<i class="input-helper"></i>
										申请报告
									</label>
									<label class="checkbox checkbox-inline m-r-10 m-l-10">
										<input type="checkbox" value="B" name="addMaterial" <c:if test="${formObject.addMaterial.contains('B')}"> checked </c:if> />
										<i class="input-helper"></i>
										项目建议书批准或备案准予复印件
									</label>
									<label class="checkbox checkbox-inline m-r-10 m-l-10">
										<input type="checkbox" value="C" name="addMaterial" <c:if test="${formObject.addMaterial.contains('C')}"> checked </c:if> />
										<i class="input-helper"></i>
										规划选址意见复印件
									</label><br/>
									<label class="checkbox checkbox-inline m-r-10 m-l-10">
										<input type="checkbox" value="D" name="addMaterial" <c:if test="${formObject.addMaterial.contains('D')}"> checked </c:if> />
										<i class="input-helper"></i>
										土地预审意见复印件
									</label>
									<label class="checkbox checkbox-inline m-r-10 m-l-10">
										<input type="checkbox" value="E" name="addMaterial" <c:if test="${formObject.addMaterial.contains('E')}"> checked </c:if> />
										<i class="input-helper"></i>    
										红线图
									</label>
									<label class="checkbox checkbox-inline m-r-10 m-l-10">
										<input type="checkbox" value="F" name="addMaterial" <c:if test="${formObject.addMaterial.contains('F')}"> checked </c:if> />
										<i class="input-helper"></i>
										总平面图
									</label><br/>
									<label class="checkbox checkbox-inline m-r-10 m-l-10">
										<input type="checkbox" value="G" name="addMaterial" <c:if test="${formObject.addMaterial.contains('G')}"> checked </c:if> />
										<i class="input-helper"></i>
										附法人身份证复印件、联系电话、联系地址
									</label>
									<label class="checkbox checkbox-inline m-r-10 m-l-10">
										<input type="checkbox" value="H" name="addMaterial" <c:if test="${formObject.addMaterial.contains('H')}"> checked </c:if> />
										<i class="input-helper"></i>    
										其他
									</label>
									<input maxlength="250" type="text" name="addOther" value="${formObject.addOther}" style="height: auto;width: 200px;border-bottom: 1px solid #ccc;" />
		            			</td>
		            		</tr>
		            		<tr>
		            			<td class="title">
		            				项目负责人
		            			</td>
		            			<td>
		            				<input maxlength="50"  type="text" name="prjPrincipal" value="${formObject.prjPrincipal}" />
		            			</td>
		            			<td class="title">
		            				总投资
		            			</td>
		            			<td>
		            				<input maxlength="50"  type="text" name="totalInvest" value="${formObject.totalInvest}" />
		            			</td>
		            		</tr>
		            		<tr>
		            			<td class="title">
		            				建议书批准或备案准予<br/>批文文号
		            			</td>
		            			<td>
		            				<input maxlength="50"  type="text" name="approvalNum" value="${formObject.approvalNum}" />
		            			</td>
		            			<td class="title">
		            				规划许可文号
		            			</td>
		            			<td>
		            				<input maxlength="50"  type="text" name="planApprovalNum" value="${formObject.planApprovalNum}" />
		            			</td>
		            		</tr>
		            		<tr>
		            			<td class="title">
		            				占地面积
		            			</td>
		            			<td>
		            				<input maxlength="50"  type="text" name="floorArea" value="${formObject.floorArea}" />
		            			</td>
		            			<td class="title">
		            				层&nbsp;&nbsp;数
		            			</td>
		            			<td>
		            				<input maxlength="50"  type="text" name="pliesNum" value="${formObject.pliesNum}" />
		            			</td>
		            		</tr>
		            		<tr>
		            			<td class="title">
		            				总建筑面积
		            			</td>
		            			<td>
		            				<input maxlength="50"  type="text" name="totalBuildArea" value="${formObject.totalBuildArea}" />
		            			</td>
		            			<td class="title">
		            				使用功能
		            			</td>
		            			<td>
		            				<input maxlength="50"  type="text" name="useFunction" value="${formObject.useFunction}" />
		            			</td>
		            		</tr>
		            		<tr>
		            			<td class="content" colspan="4" style="border-bottom: none;">
		            				建设过程中、建成后对环境影响的分析及需要说明的问题
		            			</td>
		            		</tr>
		            		<tr>
		            			<td colspan="4" style="border-top:none;padding: 0 10px;">
		            				<textarea rows="10" style="border-bottom: none;resize:none;text-indent: 2em;" maxlength="1000" class="form-control auto-size " placeholder="请输入..." name="needExplain">${formObject.needExplain}</textarea>
		            			</td>
		            		</tr>
		            		<tr>
		            			<td class="title">
		            				经办人
		            			</td>
		            			<td>
		            				<input maxlength="50"  type="text" name="operator" value="${formObject.operator}" />
		            			</td>
		            			<td class="title">
		            				经办人联系电话
		            			</td>
		            			<td>
		            				<input maxlength="15" class=" phone" type="text" name="operTel" value="${formObject.operTel}" />
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