<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>珠海市建设项目规划选址申请表</title>
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
	    	$("#ghJsxmGhxzForm").validate({
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
	    	$("#ghJsxmGhxzForm").validate({
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
			if($("#ghJsxmGhxzForm").valid()){
				$("#ghJsxmGhxzForm").submit();
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
        	<h2>珠海市建设项目规划选址申请表<small>您可通过本功能进行珠海市建设项目规划选址申请表申报</small></h2>
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
    
	    <form id="ghJsxmGhxzForm" action="${ctx}/ghJsxmGhxz/save" method="post">
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
	                 			<td class="title" colspan="2">
	                 				建设项目名称
	                 			</td>
	                 			<td colspan="6">
	                 				<input maxlength="200" class="required" type="text" name="prjName" value="${formObject.prjName}" />
	                 			</td>
	                 		</tr>
	                 		<tr style="height: 50px">
	                 			<td class="title" colspan="2">
	                 				项目地址
	                 			</td>
	                 			<td colspan="6">
	                 				<input maxlength="50"  type="text" name="prjAddr" value="${formObject.prjAddr}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td style="width: 40px;" rowspan="4" class="title">
	                 				申
	                 				<br/>
	                 				请
	                 				<br/>
	                 				人
	                 			</td>
	                 			<td class="title" colspan="2">
	                 				名&nbsp;&nbsp;&nbsp;&nbsp;称
	                 			</td>
	                 			<td colspan="5">
	                 				<input maxlength="50"  type="text" name="applyName" value="${formObject.applyName}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title" colspan="2">
	                 				地址/邮政编码
	                 			</td>
	                 			<td colspan="5">
	                 				<input maxlength="50"  type="text" name="applyAddrPostcode" value="${formObject.applyAddrPostcode}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title" colspan="2">
	                 				电话/传真
	                 			</td>
	                 			<td colspan="5">
	                 				<input maxlength="50"  type="text" name="applyTelFax" value="${formObject.applyTelFax}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title" colspan="2">
	                 				组织机构代码或
	                 				<br/>
	                 				自然人身份证号码
	                 			</td>
	                 			<td colspan="5">
	                 				<input maxlength="50"  type="text" name="applyOrgId" value="${formObject.applyOrgId}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title" colspan="3">
	                 				受&nbsp;委&nbsp;托&nbsp;人
	                 			</td>
	                 			<td colspan="2">
	                 				<input maxlength="50"  type="text" name="trustee" value="${formObject.trustee}" />
	                 			</td>
	                 			<td class="title">
	                 				联系电话
	                 			</td>
	                 			<td colspan="2">
	                 				<input maxlength="50" class=" phone" type="text" name="trusteeTel" value="${formObject.trusteeTel}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title">
	                 				业
	                 				<br/>
	                 				务
	                 				<br/>
	                 				类
	                 				<br/>
	                 				别
	                 			</td>
	                 			<td class="title">
	                 				用地类
	                 			</td>
	                 			<td class="title">
	                 				业务内容
	                 			</td>
	                 			<td colspan="5" style="padding-left: 10px;">
	                 				<label class="checkbox checkbox-inline m-r-10">
										<input type="checkbox" class="base" value="A" name="businessContent" <c:if test="${formObject.businessContent.contains('A')}"> checked </c:if> />
										<i class="input-helper"></i>    
										办理《建设项目选址意见书》
									</label>
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title" rowspan="2">
	                 				计
	                 				<br/>
	                 				划
	                 				<br/>
	                 				立
	                 				<br/>
	                 				项
	                 			</td>
	                 			<td class="title" colspan="3">
	                 				计划立项（前期）批文编号
	                 			</td>
	                 			<td colspan="2">
	                 				<input maxlength="50"  type="text" name="earlyNum" value="${formObject.earlyNum}" />
	                 			</td>
	                 			<td class="title" rowspan="2">
	                 				立项规模
	                 			</td>
	                 			<td rowspan="2">
	                 				<input maxlength="50"  type="text" name="approvalScale" value="${formObject.approvalScale}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title" colspan="3">
	                 				计划立项（固定资产投资）批文编号
	                 			</td>
	                 			<td colspan="2">
	                 				<input maxlength="50"  type="text" name="fixNum" value="${formObject.fixNum}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title">
	                 				申
	                 				<br/>
	                 				请
	                 				<br/>
	                 				规
	                 				<br/>
	                 				模
	                 			</td>
	                 			<td colspan="7">
	                 				用地面积：<input maxlength="50"  style="width:100px;height: auto;border-bottom: 1px solid #ccc;" type="text" value="${formObject.landSpace}" name="landSpace" />M<sup>2</sup>;
	                 				建设规模：<input maxlength="50"  style="width:100px;height: auto;border-bottom: 1px solid #ccc;" type="text" value="${formObject.buildScale}" name="buildScale" />M<sup>2</sup>
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title" style="height: 100px;">
	                 				申
	                 				<br/>
	                 				请
	                 				<br/>
	                 				用
	                 				<br/>
	                 				途
	                 			</td>
	                 			<td style="padding-left: 10px;height: 150px;" colspan="7">
	                 				<label class="checkbox checkbox-inline m-r-10">
										<input type="checkbox" class="base" value="A" name="applyUse" <c:if test="${formObject.applyUse.contains('A')}"> checked </c:if> />
										<i class="input-helper"></i>    
										机关事业单位办公用地
									</label>
									<label class="checkbox checkbox-inline m-r-10">
										<input type="checkbox" class="base" value="B" name="applyUse" <c:if test="${formObject.applyUse.contains('B')}"> checked </c:if> />
										<i class="input-helper"></i>    
										经济适用房用地
									</label>
									<label class="checkbox checkbox-inline m-r-10">
										<input type="checkbox" class="base" value="C" name="applyUse" <c:if test="${formObject.applyUse.contains('C')}"> checked </c:if> />
										<i class="input-helper"></i>    
										仓储用地
									</label>
									<br/>
									<label class="checkbox checkbox-inline m-r-10">
										<input type="checkbox" class="base" value="D" name="applyUse" <c:if test="${formObject.applyUse.contains('D')}"> checked </c:if> />
										<i class="input-helper"></i>    
										市政公用设施用地
									</label>
									<label class="checkbox checkbox-inline m-r-10">
										<input type="checkbox" class="base" value="E" name="applyUse" <c:if test="${formObject.applyUse.contains('E')}"> checked </c:if> />
										<i class="input-helper"></i>    
										对外交通用地
									</label>
									<label class="checkbox checkbox-inline m-r-10">
										<input type="checkbox" class="base" value="F" name="applyUse" <c:if test="${formObject.applyUse.contains('F')}"> checked </c:if> />
										<i class="input-helper"></i>    
										道路广场用地
									</label>
									<br/>
									<label class="checkbox checkbox-inline m-r-10">
										<input type="checkbox" class="base" value="G" name="applyUse" <c:if test="${formObject.applyUse.contains('G')}"> checked </c:if> />
										<i class="input-helper"></i>    
										公园绿地
									</label>
									<label class="checkbox checkbox-inline m-r-10">
										<input type="checkbox" class="base" value="H" name="applyUse" <c:if test="${formObject.applyUse.contains('H')}"> checked </c:if> />
										<i class="input-helper"></i>    
										口岸用地
									</label>
									<label class="checkbox checkbox-inline m-r-10">
										<input type="checkbox" class="base" value="I" name="applyUse" <c:if test="${formObject.applyUse.contains('I')}"> checked </c:if> />
										<i class="input-helper"></i>    
										宗教用地
									</label>
									<label class="checkbox checkbox-inline m-r-10">
										<input type="checkbox" class="base" value="J" name="applyUse" <c:if test="${formObject.applyUse.contains('J')}"> checked </c:if> />
										<i class="input-helper"></i>    
										福利用地
									</label>
									<br/>
									<label class="checkbox checkbox-inline m-r-10">
										<input type="checkbox" class="base" value="K" name="applyUse" <c:if test="${formObject.applyUse.contains('K')}"> checked </c:if> />
										<i class="input-helper"></i>    
										工业用地
									</label>
									(
									<label class="checkbox checkbox-inline m-r-10">
										<input type="checkbox" class="base" value="K1" name="applyUse" <c:if test="${formObject.applyUse.contains('K1')}"> checked </c:if> />
										<i class="input-helper"></i>    
										高科技项目工业用地
									</label>
									<label class="checkbox checkbox-inline m-r-10">
										<input type="checkbox" class="base" value="K2" name="applyUse" <c:if test="${formObject.applyUse.contains('K2')}"> checked </c:if> />
										<i class="input-helper"></i>    
										非高科技项目工业用地
									</label>
									)
									<br/>
									<label class="checkbox checkbox-inline m-r-10">
										<input type="checkbox" class="base" value="L" name="applyUse" <c:if test="${formObject.applyUse.contains('L')}"> checked </c:if> />
										<i class="input-helper"></i>    
										文教卫体用地
									</label>
									(
									<label class="checkbox checkbox-inline m-r-10">
										<input type="checkbox" class="base" value="L1" name="applyUse" <c:if test="${formObject.applyUse.contains('L1')}"> checked </c:if> />
										<i class="input-helper"></i>    
										文化
									</label>
									<label class="checkbox checkbox-inline m-r-10">
										<input type="checkbox" class="base" value="L2" name="applyUse" <c:if test="${formObject.applyUse.contains('L2')}"> checked </c:if> />
										<i class="input-helper"></i>    
										教育
									</label>
									<label class="checkbox checkbox-inline m-r-10">
										<input type="checkbox" class="base" value="L3" name="applyUse" <c:if test="${formObject.applyUse.contains('L3')}"> checked </c:if> />
										<i class="input-helper"></i>    
										科研
									</label>
									<label class="checkbox checkbox-inline m-r-10">
										<input type="checkbox" class="base" value="L4" name="applyUse" <c:if test="${formObject.applyUse.contains('L4')}"> checked </c:if> />
										<i class="input-helper"></i>    
										卫生
									</label>
									<label class="checkbox checkbox-inline m-r-10">
										<input type="checkbox" class="base" value="L5" name="applyUse" <c:if test="${formObject.applyUse.contains('L5')}"> checked </c:if> />
										<i class="input-helper"></i>    
										体育
									</label>
									)
									<br/>
									<label class="checkbox checkbox-inline m-r-10">
										<input type="checkbox" class="base" value="M" name="applyUse" <c:if test="${formObject.applyUse.contains('M')}"> checked </c:if> />
										<i class="input-helper"></i>    
										特殊用地
									</label>
									(
									<label class="checkbox checkbox-inline m-r-10">
										<input type="checkbox" class="base" value="M1" name="applyUse" <c:if test="${formObject.applyUse.contains('M1')}"> checked </c:if> />
										<i class="input-helper"></i>    
										军事
									</label>
									<label class="checkbox checkbox-inline m-r-10">
										<input type="checkbox" class="base" value="M2" name="applyUse" <c:if test="${formObject.applyUse.contains('M2')}"> checked </c:if> />
										<i class="input-helper"></i>    
										保安
									</label>
									<label class="checkbox checkbox-inline m-r-10">
										<input type="checkbox" class="base" value="M3" name="applyUse" <c:if test="${formObject.applyUse.contains('M3')}"> checked </c:if> />
										<i class="input-helper"></i>    
										水域
									</label>
									<label class="checkbox checkbox-inline m-r-10">
										<input type="checkbox" class="base" value="M4" name="applyUse" <c:if test="${formObject.applyUse.contains('M4')}"> checked </c:if> />
										<i class="input-helper"></i>    
										农业种养用地
									</label>
									)
									<br/>
									<label class="checkbox checkbox-inline m-r-10">
										<input type="checkbox" class="base" value="N" name="applyUse" <c:if test="${formObject.applyUse.contains('N')}"> checked </c:if> />
										<i class="input-helper"></i>    
										办公用地
									</label>
									<label class="checkbox checkbox-inline m-r-10">
										<input type="checkbox" class="base" value="O" name="applyUse" <c:if test="${formObject.applyUse.contains('O')}"> checked </c:if> />
										<i class="input-helper"></i>    
										住宅用地
									</label>
									<label class="checkbox checkbox-inline m-r-10">
										<input type="checkbox" class="base" value="P" name="applyUse" <c:if test="${formObject.applyUse.contains('P')}"> checked </c:if> />
										<i class="input-helper"></i>    
										商业用地
									</label>
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title" style="80px;">
	                 				选
	                 				<br/>
	                 				址
	                 				<br/>
	                 				意
	                 				<br/>
	                 				向
	                 			</td>
	                 			<td colspan="7" style="padding-left: 10px;">
	                 				<label class="checkbox checkbox-inline m-r-10">
										<input type="checkbox" class="base" value="A" name="sitePurpose" <c:if test="${formObject.sitePurpose.contains('A')}"> checked </c:if> />
										<i class="input-helper"></i>    
										待选址
									</label>
									<br/>
									<label class="checkbox checkbox-inline m-r-10">
										<input type="checkbox" class="base" value="B" name="sitePurpose" <c:if test="${formObject.sitePurpose.contains('B')}"> checked </c:if> />
										<i class="input-helper"></i>  
									</label>
									<input maxlength="200" class="base" style="width:500px;height: auto;border-bottom: 1px solid #ccc;" type="text" value="${formObject.siteTesting}" name="siteTesting" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title" rowspan="3">
	                 				备注
	                 			</td>
	                 			<td colspan="7" style="text-indent: 2em;height: 60px;border-bottom: none;padding-left: 20px;padding-right: 20px;">
	                 				        我单位（本人）已阅知有关备注说明，并承诺对申报资料的真实性及数据的准确性（含电子文
	                 				件与图纸的一致性）负责，若有任何虚报、瞒报、造假等不正当手段，审批机关可终止审理，我单
	                 				位（本人）自愿承担虚报、瞒报、造假等不正当手段而产生的一切法律责任。
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td colspan="5" style="border-right: none;border-top: none;border-bottom: none;height: 40px;"></td>
	                 			<td style="border:none;">
	                 				（申请单位盖章）
	                 			</td>
	                 			<td style="border-left: none;border-top: none;border-bottom: none;"></td>
	                 		</tr>
	                 		<tr>
	                 			<td colspan="5" style="border-right: none;border-top: none;height: 40px;"></td>
	                 			<td style="border-left: none;border-right: none;border-top: none;">
	                 				<div class="dtp-container dropdown">
	                 					<input type="text" class="date-picker  base" data-toggle="dropdown" aria-expanded="false" placeholder="点击填日期" value='<fmt:formatDate value="${formObject.applyDate}" pattern="yyyy-MM-dd" />' name="applyDate" >
	                 				</div>
	                 			</td>
	                 			<td style="border-left: none;border-top: none;"></td>
	                 		</tr>
	                 	</tbody>
	                 </table>
	                 <br/><br/>
		            <div class="btn-demo text-center col-xs-12">
			            <button data-toggle="modal" onclick="saveForm();"
			                    class="btn btn-primary waves-effect" type="button savebutton">保存</button>
			            <button class="btn btn-default waves-effect" type="button savebutton"
			                    onclick="javascrtpt:history.go(-1)">返回</button>
			        </div>
	        	</div>
	    	</div>
	    </form>
	</div>
</body>