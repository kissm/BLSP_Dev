<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>防雷装置设计审核申报表</title>
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
	    	$("#qxSjshSbbForm").validate({
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
	    	$("#qxSjshSbbForm").validate({
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
			//将  建筑物信息补充说明   集合转为JSON格式存入隐藏框中
			var buiList = [];
			$(".bui").each(function (){
				var bui = {};
				$(this).find('td').each(function (){
					bui[$(this).find('input').attr('name')] = $(this).find('input').val();
				});
				buiList.push(bui);
			});
			var buiJsonData = JSON.stringify(buiList);
			$("#jsonDataBuiList").val(buiJsonData);
			//将所有基本信息取出转为json格式存入隐藏框
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
			if($("#qxSjshSbbForm").valid()){
				$("#qxSjshSbbForm").submit();
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
			var buiList = "${formObject.buiList}";
			if(buiList.length > 0){
				$('tr[name="buiTr"]').remove();
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
        	<h2>防雷装置设计审核申报表<small>您可通过本功能进行防雷装置设计审核申报表申报</small></h2>
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
    
	    <form id="qxSjshSbbForm" action="${ctx}/qxSjshSbb/save" method="post">
		    <div class="card-body card-padding">
	        	<div class="row">
		            <input type="hidden" name="id"
		                   value="${formObject.id}" class="base">
	                <input type="hidden" name="prjId"
	                 value="${formObject.prjId}" class="base">
	                <input type="hidden" name="taskId"
	                 value="${formObject.taskId}" class="base">
	                 <table class="editTable">
	                 	<tbody>
	                 		<tr>
	                 			<td colspan="2" class="title" rowspan="2">
	                 				申请单位
	                 				<br/>
	                 				名称（公章）
	                 			</td>
	                 			<td colspan="4" rowspan="2">
	                 				<input maxlength="50" class=" base" type="text" name="applyUnit" value="${formObject.applyUnit}" />
	                 			</td>
	                 			<td class="title">
	                 				联系电话
	                 			</td>
	                 			<td>
	                 				<input maxlength="15" class=" base phone" type="text" name="applyLinktel" value="${formObject.applyLinktel}" />
	                 			</td>
	                 			<td class="title">
	                 				传真
	                 			</td>
	                 			<td>
	                 				<input maxlength="50" class=" base" type="text" name="applyFax" value="${formObject.applyFax}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title">
	                 				联系人
	                 			</td>
	                 			<td>
	                 				<input maxlength="50" class=" base" type="text" name="applyLinkman" value="${formObject.applyLinkman}" />
	                 			</td>
	                 			<td class="title">
	                 				手机
	                 			</td>
	                 			<td>
	                 				<input maxlength="15" class=" base mobile" type="text" name="applyLinkMobile" value="${formObject.applyLinkMobile}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title" colspan="2">
	                 				项目名称
	                 			</td>
	                 			<td colspan="4">
	                 				<input maxlength="200" class="required base" type="text" name="prjName" value="${formObject.prjName}" />
	                 			</td>
	                 			<td class="title" colspan="2">
	                 				预计开工时间
	                 			</td>
	                 			<td colspan="2">
	                 				<div class="dtp-container dropdown">
										<input type="text" class="date-picker  base" data-toggle="dropdown" aria-expanded="false" value='<fmt:formatDate value="${formObject.predictStartTime}" pattern="yyyy-MM-dd" />' name="predictStartTime" />
									</div>
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title" colspan="2">
	                 				项目地址
	                 			</td>
	                 			<td colspan="4">
	                 				<input maxlength="50" class=" base" type="text" name="prjAddr" value="${formObject.prjAddr}" />
	                 			</td>
	                 			<td class="title" colspan="2">
	                 				预计竣工时间
	                 			</td>
	                 			<td colspan="2">
	                 				<div class="dtp-container dropdown">
										<input type="text" class="date-picker  base" data-toggle="dropdown" aria-expanded="false" value='<fmt:formatDate value="${formObject.predictEndTime}" pattern="yyyy-MM-dd" />' name="predictEndTime" />
									</div>
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title" colspan="2">
	                 				设计单位名称
	                 			</td>
	                 			<td colspan="3">
	                 				<input maxlength="50" class=" base" type="text" name="designUnit" value="${formObject.designUnit}" />
	                 			</td>
	                 			<td class="title">
	                 				联系人
	                 			</td>
	                 			<td>
	                 				<input maxlength="50" class=" base" type="text" name="designLinkman" value="${formObject.designLinkman}" />
	                 			</td>
	                 			<td class="title">
	                 				联系电话
	                 			</td>
	                 			<td colspan="2">
	                 				<input maxlength="15" class=" base phone" type="text" name="designLinktel" value="${formObject.designLinktel}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td colspan="2" class="title">
	                 				建筑物名称
	                 			</td>
	                 			<td class="title">
	                 				结构类型
	                 				<br/>
	                 				(见说明二)
	                 			</td>
	                 			<td class="title">
	                 				层数
	                 				<br/>
	                 				(层)
	                 			</td>
	                 			<td class="title">
	                 				高度
	                 				<br/>
	                 				(米)
	                 			</td>
	                 			<td class="title">
	                 				建筑面积
	                 				<br/>
	                 				(平方米)
	                 			</td>
	                 			<td class="title">
	                 				使用类别
	                 				<br/>
	                 				(见说明三)
	                 			</td>
	                 			<td class="title">
	                 				电源情况
	                 				<br/>
	                 				(见说明四)
	                 			</td>
	                 			<td class="title">
	                 				土壤情况
	                 				<br/>
	                 				(见说明五)
	                 			</td>
	                 			<td class="title">
	                 				防雷
	                 				<br/>
	                 				图号
	                 			</td>
	                 		</tr>
	                 		<tr id="bui" class="bui" name="buiTr">
	                 			<td>
	                 				<button type="button" onclick="addRow('bui')" class="btn btn-primary"><i class="md md-add"></i></button>
	                 			</td>
	                 			<td>
	                 				<input maxlength="50" type="text"  name="bName"/>
	                 			</td>
	                 			<td>
	                 				<input maxlength="50" type="text"  name="bStructureType"/>
	                 			</td>
	                 			<td>
	                 				<input maxlength="50" type="text"  name="bFloors"/>
	                 			</td>
	                 			<td>
	                 				<input maxlength="50" type="text"  name="bHeight"/>
	                 			</td>
	                 			<td>
	                 				<input maxlength="50" type="text"  name="bBuildingArea"/>
	                 			</td>
	                 			<td>
	                 				<input maxlength="50" type="text"  name="bUseType"/>
	                 			</td>
	                 			<td>
	                 				<input maxlength="50" type="text"  name="bPowerSource"/>
	                 			</td>
	                 			<td>
	                 				<input maxlength="50" type="text"  name="bSoilRegime"/>
	                 			</td>
	                 			<td>
	                 				<input maxlength="50" type="text"  name="bLightningFigNum"/>
	                 			</td>
	                 		</tr>
	                 		<c:forEach items="${formObject.buiList}" var="bui" varStatus="i">
	                 			<c:if test="${i.index == 0}">
	                 				<tr id="bui" class="bui">
			                 			<td>
			                 				<button type="button" onclick="addRow('bui')" class="btn btn-primary"><i class="md md-add"></i></button>
			                 				<input type="hidden" value="${bui.bId}" name="bId" />
			                 			</td>
			                 			<td style="display: none;">
			                 				<input type="hidden" value="${bui.qId}" name="qId" />
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="bName" value="${bui.bName}" />
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="bStructureType" value="${bui.bStructureType}" />
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="bFloors" value="${bui.bFloors}" />
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="bHeight" value="${bui.bHeight}" />
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="bBuildingArea" value="${bui.bBuildingArea}" />
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="bUseType" value="${bui.bUseType}" />
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="bPowerSource" value="${bui.bPowerSource}" />
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="bSoilRegime" value="${bui.bSoilRegime}" />
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="bLightningFigNum" value="${bui.bLightningFigNum}" />
			                 			</td>
			                 		</tr>
			                 	</c:if>
			                 	<c:if test="${i.index > 0}">
	                 				<tr id="${bui.bId}" class="bui">
			                 			<td>
			                 				<button type="button" onclick="removeRow('${bui.bId}')" class="btn btn-danger"><i class="md md-clear"></i></button>
			                 				<input type="hidden" value="${bui.bId}" name="bId" />
			                 			</td>
			                 			<td style="display: none;">
			                 				<input type="hidden" value="${bui.qId}" name="qId" />
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="bName" value="${bui.bName}" />
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="bStructureType" value="${bui.bStructureType}" />
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="bFloors" value="${bui.bFloors}" />
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="bHeight" value="${bui.bHeight}" />
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="bBuildingArea" value="${bui.bBuildingArea}" />
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="bUseType" value="${bui.bUseType}" />
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="bPowerSource" value="${bui.bPowerSource}" />
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="bSoilRegime" value="${bui.bSoilRegime}" />
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="bLightningFigNum" value="${bui.bLightningFigNum}" />
			                 			</td>
			                 		</tr>
			                 	</c:if>
			                 </c:forEach>
			                 <tr>
			                 	<td style="width: 30px;" class="title">
			                 		说
			                 		<br/><br/><br/><br/>
			                 		明
			                 	</td>
			                 	<td colspan="8" style="border-right: none;padding-left: 10px;">
			                 		一、送审资料：<br/>
			                 		1.《防雷装置设计审核申请书》；<br/>
			                 		2.设计单位和人员资质、资格证书的复印件（加盖设计单位公章）；<br/>
			                 		3.防雷装置施工图设计说明书、施工图设计图纸及其电子文档；<br/>
			                 		4.经规划部门批准的总平面图（原件或复印件，复印件需加盖建设单位公章）；<br/>
			                 		5.建筑施工图及其电子文档；<br/>
			                 		6.结构施工图（暂只提供电子版）；<br/>
			                 		7.其他与防雷建设有关的施工图（水、电、消防、煤气、金属构架大样、SPD安装等）；<br/>
			                 		8.工业建筑物应有生产工艺流程图、物料存储方式、危险品场所分布等资料；<br/>
			                 		9.储罐材质、壁厚、储存物形态、储存工作压力数据等资料；<br/>
			                 		10.防雷产品相关资料；<br/>
			                 		11.经过初步设计的，提交《防雷装置初步设计核准书》；<br/>
			                 		12.经当地气象主管机构认可的防雷专业技术机构出具的有关技术评价意见。<br/>
			                 		申请单位应将送审资料按统一规格装订成册，连同本表送气象主管机构审核。<br/>
			                 		二、结构类型填写：A、砖木；B、混合；C、钢筋混凝土；D、钢结构<br/>
			                 		三、使用类别填写：<br/>
			                 		A1.甲类厂房、仓库  B1.教育、医疗、科研、体育馆  C1.高级综合建筑  D1.一般综合建筑<br/>
			                 		A2.乙类厂房、仓库  B2.影剧院、会堂、俱乐部、旅游  C2.高层住宅    D2.住宅、公寓<br/>
			                 		A3.丙类仓库  B3.金融、商业、宾招、娱乐场所  C3.大型厂房、丙类厂房  D3.一般厂房、仓库<br/>
			                 		A4.油、气罐站（区）、锅炉房 B4.交通、通讯、供水、供电、供气 C4.特殊地形建筑物 D4.其他<br/>
			                 		四、电源情况填写：A.架空进线   B.自设变配电室   C.埋地进线<br/>
			                 		五、土壤情况填写：A.岩石       B.坚土           C.普通土       D.软土
			                 	</td>
			                 	<td style="border-left: none;text-align: right;padding-right: 10px;" valign="top">
			                 		<br/>
			                 		[共（1）份]<br/>
			                 		[共（1）份]<br/>
			                 		[共（2）份]<br/>
			                 		[共（2）份]<br/>
			                 		[共（2）份]<br/>
			                 		[共（1）份]<br/>
			                 		[共（2）份]<br/>
			                 		[共（1）份]<br/>
			                 		[共（1）份]<br/>
			                 		[共（1）份]<br/>
			                 		[共（   ）份]<br/>
			                 		[共（1）份]<br/>
			                 	</td>
			                 </tr>
			                 <tr>
			                 	<td colspan="5" style="border-right: none;height: 40px;padding-left: 10px;">
			                 		申请经办人签字
			                 		<input maxlength="50" class=" base" style="width:200px;height: auto;border-bottom: 1px solid #ccc;" type="text" value="${formObject.applyAgent}" name="applyAgent" />
			                 	</td>
			                 	<td colspan="5" style="border-left: none;">
			                 		联系电话
			                 		<input maxlength="15" class=" base phone" style="width:200px;height: auto;border-bottom: 1px solid #ccc;" type="text" value="${formObject.applyAgentLinktel}" name="applyAgentLinktel" />
			                 	</td>
			                 </tr>
			                 <tr>
			                 	<td colspan="10" style="border-top: none;border-bottom: none;height: 60px;"></td>
			                 </tr>
			                 <tr>
			                 	<td colspan="5" style="border-right: none;border-top: none;padding-left: 10px;height: 30px;">
			                 		申请单位（公章）
			                 	</td>
			                 	<td colspan="5" style="border-left: none;border-top: none;">
			                 		经办人：
			                 		<input maxlength="50" class=" base" style="width:100px;height: auto;border-bottom: 1px solid #ccc;" type="text" value="${formObject.applyUnitsAgent}" name="applyUnitsAgent" />
			                 	</td>
			                 </tr>
	                 	</tbody>
	                 </table>
	                 <br/><br/>
	                 <input type="hidden" name="jsonDataObj" id="jsonDataObj"/>
	                 <input type="hidden" name="jsonDataBuiList" id="jsonDataBuiList"/>
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