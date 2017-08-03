<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>珠海市建设用地规划许可申请表</title>
    <meta name="decorator" content="blank" />
    <style>
		.editTable input[type='text'].has-error {
			background: #F44336;
			color: #FFF;
		}
	</style>
    <script>
    	$(document).ready(function(){
	    	$("#ghJsydGhxkForm").validate({
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
	    	$("#ghJsydGhxkForm").validate({
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
	    <form id="ghJsydGhxkForm" action="${ctx}/ghJsydGhxk/save" method="post">
		    <div class="card-body card-padding">
	        	<div class="row">
		            <input type="hidden" name="id"
		                   value="${formObject.id}">
	                <input type="hidden" name="prjId"
	                 value="${formObject.prjId}">
	                <input type="hidden" name="taskId"
	                 value="${formObject.taskId}">
	                 <table class="editTable" style="width: 960px;" border="0" cellspacing="0" cellpadding="0">
	                 	<tbody>
	                 		<tr>
	                 			<td class="title" colspan="11" style="font-size: 16px;font-weight: bolder;">
	                 				珠海市建设用地规划许可申请表
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td colspan="2" rowspan="5" class="title">
	                 				用<br/>地<br/>单<br/>位
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
	                 				基<br/><br/>本<br/><br/>信<br/><br/>息
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
	                 				申请<br/>办理<br/>业务
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
	                 				主要<br/>依据
	                 			</td>
	                 			<td style="height: 80px;padding: 0 10px;" colspan="8">
	                 				<textarea rows="4" style="resize:none;border: none;width:880px;line-height: auto;text-indent: 2em;" maxlength="250" class="form-control auto-size " placeholder="请输入..." name="mainGist">${formObject.mainGist}</textarea>
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td colspan="2" class="title">
	                 				申<br/>请<br/>内<br/>容<br/>简<br/>述
	                 			</td>
	                 			<td style="height: 160px;padding:0 10px;" colspan="8">
	                 				<textarea rows="8" style="resize:none;border: none;width:880px;line-height: auto;text-indent: 2em;" maxlength="500" class="form-control auto-size " placeholder="请输入..." name="applyContent">${formObject.applyContent}</textarea>
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
	                 				<input class=" base" type="text" 
		                 				onClick="WdatePicker()" placeholder="点击填日期" 
		                 				value='<fmt:formatDate value="${formObject.oriLandDate}" 
		                 				pattern="yyyy-MM-dd" />' name="oriLandDate" />
	                 			</td>
	                 			<td colspan="2" style="border-left: none;border-right: none;border-top: none;"></td>
	                 			<td colspan="3" style="border-left: none;border-top: none;">
	                 				<input class=" base" type="text" 
		                 				onClick="WdatePicker()" placeholder="点击填日期" 
		                 				value='<fmt:formatDate value="${formObject.landDate}" 
		                 				pattern="yyyy-MM-dd" />' name="landDate" />
	                 			</td>
	                 		</tr>
	                 	</tbody>
	                 </table>
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