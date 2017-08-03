<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>珠海市建设用地规划许可申请表</title>
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
	    	$("#ghJsydGhxkForm").validate({
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
	    	$("#ghJsydGhxkForm").validate({
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
			if($("#ghJsydGhxkForm").valid()){
				$("#ghJsydGhxkForm").submit();
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
        	<h2>珠海市建设用地规划许可申请表<small>您可通过本功能进行珠海市建设用地规划许可申请表申报</small></h2>
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
    
	    <form id="ghJsydGhxkForm" action="${ctx}/ghJsydGhxk/save" method="post">
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
	                 			<td colspan="2" rowspan="5" class="title">
	                 				用
	                 				<br/>
	                 				地
	                 				<br/>
	                 				单
	                 				<br/>
	                 				位
	                 			</td>
	                 			<td class="title" rowspan="2">
	                 				单位全称
	                 			</td>
	                 			<td colspan="2" rowspan="2">
	                 				<input maxlength="50"  type="text" name="landUnit" value="${formObject.landUnit}" />
	                 			</td>
	                 			<td class="title">
	                 				地址
	                 			</td>
	                 			<td colspan="5">
	                 				<input maxlength="50"  type="text" name="landAddr" value="${formObject.landAddr}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title">
	                 				电话
	                 			</td>
	                 			<td colspan="2">
	                 				<input maxlength="15" class=" phone" type="text" name="landTel" value="${formObject.landTel}" />
	                 			</td>
	                 			<td class="title">
	                 				传真
	                 			</td>
	                 			<td colspan="2">
	                 				<input maxlength="50"  type="text" name="landFax" value="${formObject.landFax}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td colspan="3" class="title">
	                 				组织机构代码或自然人身份证号码
	                 			</td>
	                 			<td colspan="6">
	                 				<input maxlength="50"  type="text" name="landOrgId" value="${formObject.landOrgId}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title">
	                 				法人代表
	                 			</td>
	                 			<td>
	                 				<input maxlength="50"  type="text" name="landLegal" value="${formObject.landLegal}" />
	                 			</td>
	                 			<td class="title">
	                 				联系电话
	                 			</td>
	                 			<td style="border-right: none;text-align: right;">
	                 				手机：
	                 			</td>
	                 			<td colspan="2" style="border-left: none;border-right: none;">
	                 				<input maxlength="15" class=" mobile" type="text" name="landLegalMobile" value="${formObject.landLegalMobile}" />
	                 			</td>
	                 			<td style="border-left: none;border-right: none;text-align: right;">
	                 				办公：
	                 			</td>
	                 			<td colspan="2" style="border-left: none;">
	                 				<input maxlength="15" class=" phone" type="text" name="landLegalPhone" value="${formObject.landLegalPhone}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title">
	                 				受委托人
	                 			</td>
	                 			<td>
	                 				<input maxlength="50"  type="text" name="landTrustee" value="${formObject.landTrustee}" />
	                 			</td>
	                 			<td class="title">
	                 				联系电话
	                 			</td>
	                 			<td style="border-right: none;text-align: right;">
	                 				手机：
	                 			</td>
	                 			<td colspan="2" style="border-left: none;border-right: none;">
	                 				<input maxlength="15" class=" mobile" type="text" name="landTrusteeMobile" value="${formObject.landTrusteeMobile}" />
	                 			</td>
	                 			<td style="border-left: none;border-right: none;text-align: right;">
	                 				办公：
	                 			</td>
	                 			<td colspan="2" style="border-left: none;">
	                 				<input maxlength="15" class=" phone" type="text" name="landTrusteePhone" value="${formObject.landTrusteePhone}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td style="width: 30px;border-right: none;" rowspan="5" class="title">
	                 				原用地单位
	                 			</td>
	                 			<td style="width: 30px;border-left: none;font-size: 10px;" rowspan="5" class="title">
	                 				︵
	                 				<br/>
	                 				仅更名时填写
	                 				<br/>
	                 				︶
	                 			</td>
	                 			<td class="title" rowspan="2">
	                 				单位全称
	                 			</td>
	                 			<td colspan="2" rowspan="2">
	                 				<input maxlength="50"  type="text" name="oriLandUnit" value="${formObject.oriLandUnit}" />
	                 			</td>
	                 			<td class="title">
	                 				地址
	                 			</td>
	                 			<td colspan="5">
	                 				<input maxlength="50"  type="text" name="oriLandAddr" value="${formObject.oriLandAddr}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title">
	                 				电话
	                 			</td>
	                 			<td colspan="2">
	                 				<input maxlength="15" class="phone" type="text" name="oriLandTel" value="${formObject.oriLandTel}" />
	                 			</td>
	                 			<td class="title">
	                 				传真
	                 			</td>
	                 			<td colspan="2">
	                 				<input maxlength="50"  type="text" name="oriLandFax" value="${formObject.oriLandFax}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td colspan="3" class="title">
	                 				组织机构代码或自然人身份证号码
	                 			</td>
	                 			<td colspan="6">
	                 				<input maxlength="50"  type="text" name="oriLandOrgId" value="${formObject.oriLandOrgId}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title">
	                 				法人代表
	                 			</td>
	                 			<td>
	                 				<input maxlength="50"  type="text" name="oriLandLegal" value="${formObject.oriLandLegal}" />
	                 			</td>
	                 			<td class="title">
	                 				联系电话
	                 			</td>
	                 			<td style="border-right: none;text-align: right;">
	                 				手机：
	                 			</td>
	                 			<td colspan="2" style="border-left: none;border-right: none;">
	                 				<input maxlength="15" class="mobile" type="text" name="oriLandLegalMobile" value="${formObject.oriLandLegalMobile}" />
	                 			</td>
	                 			<td style="border-left: none;border-right: none;text-align: right;">
	                 				办公：
	                 			</td>
	                 			<td colspan="2" style="border-left: none;">
	                 				<input maxlength="15" class="phone" type="text" name="oriLandLegalPhone" value="${formObject.oriLandLegalPhone}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title">
	                 				受委托人
	                 			</td>
	                 			<td>
	                 				<input maxlength="50"  type="text" name="oriLandTrustee" value="${formObject.oriLandTrustee}" />
	                 			</td>
	                 			<td class="title">
	                 				联系电话
	                 			</td>
	                 			<td style="border-right: none;text-align: right;">
	                 				手机：
	                 			</td>
	                 			<td colspan="2" style="border-left: none;border-right: none;">
	                 				<input maxlength="15" class="mobile" type="text" name="oriLandTrusteeMobile" value="${formObject.oriLandTrusteeMobile}" />
	                 			</td>
	                 			<td style="border-left: none;border-right: none;text-align: right;">
	                 				办公：
	                 			</td>
	                 			<td colspan="2" style="border-left: none;">
	                 				<input maxlength="15" class="phone" type="text" name="oriLandTrusteePhone" value="${formObject.oriLandTrusteePhone}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td style="width: 30px;border-right: none;" rowspan="4" class="title">
	                 				基
	                 				<br/><br/>
	                 				本
	                 				<br/><br/>
	                 				信
	                 				<br/><br/>
	                 				息
	                 			</td>
	                 			<td style="width: 30px;border-left: none;font-size: 10px;" rowspan="4" class="title">
	                 				︵
	                 				<br/>
	                 				首次申请仅填写确定内容
	                 				<br/>
	                 				︶
	                 			</td>
	                 			<td class="title" colspan="2">
	                 				原《建设用地规划许可证》号
	                 			</td>
	                 			<td colspan="3">
	                 				<input maxlength="50"  type="text" name="oriId" value="${formObject.oriId}" />
	                 			</td>
	                 			<td class="title" colspan="2">
	                 				用地项目名称
	                 			</td>
	                 			<td colspan="2">
	                 				<input maxlength="200"  type="text" name="landPrjName" value="${formObject.landPrjName}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title">
	                 				用地位置
	                 			</td>
	                 			<td>
	                 				<input maxlength="50"  type="text" name="landPosition" value="${formObject.landPosition}" />
	                 			</td>
	                 			<td class="title">
	                 				用地性质
	                 			</td>
	                 			<td colspan="2">
	                 				<input maxlength="50"  type="text" name="landNatrue" value="${formObject.landNatrue}" />
	                 			</td>
	                 			<td class="title" colspan="2">
	                 				用地面积
	                 			</td>
	                 			<td colspan="2">
	                 				<input maxlength="50"  type="text" name="landSpace" value="${formObject.landSpace}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title">
	                 				建设规模
	                 			</td>
	                 			<td>
	                 				<input maxlength="50"  type="text" name="buildScale" value="${formObject.buildScale}" />
	                 			</td>
	                 			<td class="title">
	                 				容积率
	                 			</td>
	                 			<td colspan="2">
	                 				<input maxlength="50"  type="text" name="plotRatio" value="${formObject.plotRatio}" />
	                 			</td>
	                 			<td class="title" colspan="2">
	                 				建筑密度
	                 			</td>
	                 			<td colspan="2">
	                 				<input maxlength="50"  type="text" name="buildingDensity" value="${formObject.buildingDensity}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title">
	                 				配套设施
	                 			</td>
	                 			<td colspan="8">
	                 				<input maxlength="250"  type="text" name="ancillaryFacility" value="${formObject.ancillaryFacility}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title" colspan="2" rowspan="3">
	                 				申请
	                 				<br/>
	                 				办理
	                 				<br/>
	                 				业务
	                 			</td>
	                 			<td style="height: 60px;padding-left: 10px;" colspan="8">
	                 				<label class="checkbox checkbox-inline m-r-10">
										<input type="checkbox" class="base" value="A" name="applyBusiness" <c:if test="${formObject.applyBusiness.contains('A')}"> checked </c:if> />
										<i class="input-helper"></i>    
										新征用地办理《建设用地规划许可证》
									</label>
									<label class="checkbox checkbox-inline m-r-10">
										<input type="checkbox" class="base" value="B" name="applyBusiness" <c:if test="${formObject.applyBusiness.contains('B')}"> checked </c:if> />
										<i class="input-helper"></i>    
										历史用地补办《建设用地规划许可证》
									</label>
									<br/>
									<label class="checkbox checkbox-inline m-r-10">
										<input type="checkbox" class="base" value="C" name="applyBusiness" <c:if test="${formObject.applyBusiness.contains('C')}"> checked </c:if> />
										<i class="input-helper"></i>    
										办理临时《建设用地规划许可证》
									</label>
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td style="height: 60px;padding-left: 10px;" colspan="8">
	                 				<label class="checkbox checkbox-inline m-r-10">
										<input type="checkbox" class="base" value="D" name="applyBusiness" <c:if test="${formObject.applyBusiness.contains('D')}"> checked </c:if> />
										<i class="input-helper"></i>    
										调整建设用地规划条件
									</label>
									<label class="checkbox checkbox-inline m-r-10">
										<input type="checkbox" class="base" value="E" name="applyBusiness" <c:if test="${formObject.applyBusiness.contains('E')}"> checked </c:if> />
										<i class="input-helper"></i>    
										调整建设用地红线
									</label>
									<br/>
									<label class="checkbox checkbox-inline m-r-10">
										<input type="checkbox" class="base" value="F" name="applyBusiness" <c:if test="${formObject.applyBusiness.contains('F')}"> checked </c:if> />
										<i class="input-helper"></i>    
										《建设用地规划许可证》及附件延期使用
									</label>
									<label class="checkbox checkbox-inline m-r-10">
										<input type="checkbox" class="base" value="G" name="applyBusiness" <c:if test="${formObject.applyBusiness.contains('G')}"> checked </c:if> />
										<i class="input-helper"></i>    
										《建设用地规划许可证》及附图、附件遗失补办
									</label>
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td style="padding-left: 10px;height: 30px;" colspan="8">
	                 				<label class="checkbox checkbox-inline m-r-10">
										<input type="checkbox" class="base" value="H" name="applyBusiness" <c:if test="${formObject.applyBusiness.contains('H')}"> checked </c:if> />
										<i class="input-helper"></i>    
										《建设用地规划许可证》更名
									</label>
									（
									<label class="checkbox checkbox-inline m-r-10">
										<input type="checkbox" class="base" value="I" name="applyBusiness" <c:if test="${formObject.applyBusiness.contains('I')}"> checked </c:if> />
										<i class="input-helper"></i>    
										全部
									</label>
									<label class="checkbox checkbox-inline m-r-10">
										<input type="checkbox" class="base" value="J" name="applyBusiness" <c:if test="${formObject.applyBusiness.contains('J')}"> checked </c:if> />
										<i class="input-helper"></i>    
										部分
									</label>
									）
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td colspan="2" class="title">
	                 				主要
	                 				<br/>
	                 				依据
	                 			</td>
	                 			<td style="height: 80px;padding: 0 10px;" colspan="8">
	                 				<textarea rows="3" style="border-bottom: none;resize:none;" maxlength="250" class="form-control auto-size " placeholder="请输入..." name="mainGist">${formObject.mainGist}</textarea>
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td colspan="2" class="title">
	                 				申
	                 				<br/>
	                 				请
	                 				<br/>
	                 				内
	                 				<br/>
	                 				容
	                 				<br/>
	                 				简
	                 				<br/>
	                 				述
	                 			</td>
	                 			<td style="height: 160px;padding:0 10px;" colspan="8">
	                 				<textarea rows="5" style="border-bottom: none;resize:none;" maxlength="500" class="form-control auto-size " placeholder="请输入..." name="applyContent">${formObject.applyContent}</textarea>
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title" rowspan="3" colspan="2">
	                 				备注
	                 			</td>
	                 			<td colspan="9" style="text-indent: 2em;height: 60px;border-bottom: none;padding-left: 20px;padding-right: 20px;">
	                 				       我单位（本人）已阅知有关备注说明，并承诺对申报资料的真实性及数据的准确性（含电子文件与
	                 				图纸的一致性）负责，若有任何虚报、瞒报、造假等不正当手段，审批机关可终止审理，我单位（本
	                 				人）自愿承担虚报、瞒报、造假等不正当手段而产生的一切法律责任。
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td colspan="2" style="border-right: none;border-top: none;border-bottom: none;height: 40px;"></td>
	                 			<td style="border:none;" colspan="2">
	                 				原用地单位盖章<span style="font-size: 10px;">（仅更名时加盖）</span>
	                 			</td>
	                 			<td colspan="2" style="border: none;"></td>
	                 			<td colspan="3" style="border-left: none;border-top: none;border-bottom: none;">
	                 				用地单位盖章
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td colspan="2" style="border-right: none;border-top: none;height: 40px;"></td>
	                 			<td colspan="2" style="border-left: none;border-right: none;border-top: none;">
	                 				<div class="dtp-container dropdown">
	                 					<input type="text" class="date-picker base" data-toggle="dropdown" aria-expanded="false" placeholder="点击填日期" value='<fmt:formatDate value="${formObject.oriLandDate}" pattern="yyyy-MM-dd" />' name="oriLandDate" >
	                 				</div>
	                 			</td>
	                 			<td colspan="2" style="border-left: none;border-right: none;border-top: none;"></td>
	                 			<td colspan="3" style="border-left: none;border-top: none;">
	                 				<div class="dtp-container dropdown">
	                 					<input type="text" class="date-picker  base" data-toggle="dropdown" aria-expanded="false" placeholder="点击填日期" value='<fmt:formatDate value="${formObject.landDate}" pattern="yyyy-MM-dd" />' name="landDate" >
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