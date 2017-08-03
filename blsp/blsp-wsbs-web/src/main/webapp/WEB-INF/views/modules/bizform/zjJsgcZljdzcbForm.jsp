<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>建设工程质量监督注册表</title>
    <meta name="decorator" content="blank" />
    <style>
		.editTable tr {
			height: 50px;
		}
		.editTable input[type='text'].has-error {
			background: #F44336;
			color: #FFF;
		}
	</style>
    <script>
	    $(document).ready(function(){
	    	$("#zjJsgcZljdzcbForm").validate({
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
			//基础部分数据进行对象序列化
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
			console.info(objJsonData);
			$("#jsonDataObj").val(objJsonData);
			//建设单位组织机构数据集合序列化
			var buiList = [];
			$(".bui").each(function (){
				var bui = {};
				$(this).find('td').each(function (){
					bui[$(this).find('input').attr('name')] = $(this).find('input').val();
				});
				buiList.push(bui);
			});
			var buiJsonData = JSON.stringify(buiList);
			console.info(buiJsonData);
			$("#jsonDataBuiList").val(buiJsonData);
			//施工单位组织机构数据集合序列化
			var conList = [];
			$(".con").each(function (){
				var con = {};
				$(this).find('td').each(function (){
					con[$(this).find('input').attr('name')] = $(this).find('input').val();
				});
				conList.push(con);
			});
			var conJsonData = JSON.stringify(conList);
			console.info(conJsonData);
			$("#jsonDataConList").val(conJsonData);
			//监理单位组织机构数据集合序列化
			var supList = [];
			$(".sup").each(function (){
				var sup = {};
				$(this).find('td').each(function (){
					sup[$(this).find('input').attr('name')] = $(this).find('input').val();
				});
				supList.push(sup);
			});
			var supJsonData = JSON.stringify(supList);
			console.info(supJsonData);
			$("#jsonDataSupList").val(supJsonData);
			$(".savebutton").attr("disabled",true);
			if($("#zjJsgcZljdzcbForm").valid()){
				$("#zjJsgcZljdzcbForm").submit();
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
			$(tr).find('button i.md').removeClass('add').addClass('minus');
			
			$('.'+id+':last').after(tr);
		}
		function removeRow(id){
			$("#"+id).remove();
		}
		$(function (){
			var buiList = '${formObject.buiList}';
			if(buiList.length > 0){
				$('tr[name="buiTr"]').remove();
			}
			var conList = '${formObject.conList}';
			if(conList.length > 0){
				$('tr[name="conTr"]').remove();
			}
			var supList = '${formObject.supList}';
			console.info(supList);
			if(supList.length > 0){
				$('tr[name="supTr"]').remove();
			}
		});
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
    	<form id="zjJsgcZljdzcbForm" action="${ctx}/zjJsgcZljdzcb/save" method="post">
	    	<div class="card-body card-padding">
	    		<table class="editTable" style="width: 960px;" border="0" cellspacing="0" cellpadding="0">
	    			<tr>
	    				<td class="title" colspan="14" style="font-size: 16px;font-weight: bolder;">
	    					建设工程质量监督注册表
	    				</td>
	    			</tr>
	    			<tr height="50px">
	    				<td colspan="14" class="title" style="text-align: left;font-size: 14px;font-weight: 800;">
	    					一、工程概况
	    				</td>
	    			</tr>
	    			<tr height="50px">
	    				<td colspan="2" class="title">
	    					工程<br/>名称
	    				</td>
	    				<td colspan="5">
	    					<input type="hidden" name="id" value="${formObject.id}" class="base">
		                	<input type="hidden" name="prjId" value="${formObject.prjId}"  class="base">
		                	<input type="hidden" name="taskId" value="${formObject.taskId}"  class="base">
	    					<input class="required base" maxlength="200" type="text" value="${formObject.prjName}" name="prjName" />
	    				</td>
	    				<td class="title">
	    					工程<br/>类别
	    				</td>
	    				<td style="padding-left: 10px;" colspan="6">
	    					<label class="checkbox checkbox-inline m-r-10">
								<input class="base" type="checkbox" value="A" name="prjTypes" <c:if test="${formObject.prjTypes.contains('A')}"> checked </c:if> />
								<i class="input-helper"></i>    
								房屋建筑工程
							</label>
							<br/>
							<label class="checkbox checkbox-inline m-r-10">
								<input class="base" type="checkbox" value="B" name="prjTypes" <c:if test="${formObject.prjTypes.contains('B')}"> checked </c:if> />
								<i class="input-helper"></i>    
								市政基础设施工程
							</label>
	    				</td>
	    			</tr>
	    			<tr height="50px">
	    				<td colspan="2" class="title">
	    					工程<br/>地点
	    				</td>
	    				<td colspan="12">
	    					<input class=" base" maxlength="50" type="text" value="${formObject.prjAddr}" name="prjAddr" />
	    				</td>
	    			</tr>
	    			<tr height="50px">
	    				<td colspan="2" class="title">
	    					建筑<br/>面积
	    				</td>
	    				<td colspan="2" style="padding-left: 10px;">
	    					<input class=" base" maxlength="50" style="width:100px;height: auto;border-bottom: 1px solid #ccc;" type="text" value="${formObject.buildSpace}" name="buildSpace" />
	    					m<sup>2</sup>
	    				</td>
	    				<td class="title">
	    					合同<br/>总造价
	    				</td>
	    				<td colspan="2" style="padding-left: 10px;">
	    					<input class=" base" maxlength="50" style="width:100px;height: auto;border-bottom: 1px solid #ccc;" type="text" value="${formObject.contractCost}" name="contractCost" />
	    					万元
	    				</td>
	    				<td class="title">
	    					结构<br/>层数
	    				</td>
	    				<td colspan="6" style="padding-left: 10px;">
	    					地上：<input class=" base" maxlength="50" style="width:100px;height: auto;border-bottom: 1px solid #ccc;" type="text" value="${formObject.strLevNumOn}" name="strLevNumOn" /><br/>
	    					地下：<input class=" base" maxlength="50" style="width:100px;height: auto;border-bottom: 1px solid #ccc;" type="text" value="${formObject.strLevNumDown}" name="strLevNumDown" />
	    				</td>
	    			</tr>
	    			<tr height="50px">
	    				<td colspan="2" class="title">
	    					基础<br/>类型
	    				</td>
	    				<td colspan="2" style="padding-left: 10px;">
	    					<input class=" base" maxlength="50" type="text" value="${formObject.baseType}" name="baseType" />
	    				</td>
	    				<td class="title">
	    					结构<br/>类型
	    				</td>
	    				<td colspan="2">
	    					<input class=" base" maxlength="50" type="text" value="${formObject.structureType}" name="structureType" />
	    				</td>
	    				<td class="title">
	    					最大<br/>跨度
	    				</td>
	    				<td colspan="2" style="padding-left: 10px">
	    					<input class=" base" maxlength="50" style="width:100px;height: auto;border-bottom: 1px solid #ccc;" type="text" value="${formObject.maxSpan}" name="maxSpan" />
	    					m
	    				</td>
	    				<td class="title" colspan="2">
	    					总长度
	    				</td>
	    				<td colspan="2" style="padding-left: 10px">
	    					<input class=" base" maxlength="50" style="width:100px;height: auto;border-bottom: 1px solid #ccc;" type="text" value="${formObject.totalLength}" name="totalLength" />
	    					km
	    				</td>
	    			</tr>
	    			<tr height="80px">
	    				<td class="title" colspan="2">
	    					投资<br/>性质
	    				</td>
	    				<td colspan="5" style="padding-left: 10px">
	    					<label class="checkbox checkbox-inline m-r-10">
								<input class="base" type="checkbox" value="A" name="investNature" <c:if test="${formObject.investNature.contains('A')}"> checked </c:if> />
								<i class="input-helper"></i>    
								财政投入
							</label>
							<label class="checkbox checkbox-inline m-r-10">
								<input class="base" type="checkbox" value="B" name="investNature" <c:if test="${formObject.investNature.contains('B')}"> checked </c:if> />
								<i class="input-helper"></i>    
								国有资本
							</label><br/>
							<label class="checkbox checkbox-inline m-r-10">
								<input class="base" type="checkbox" value="C" name="investNature" <c:if test="${formObject.investNature.contains('C')}"> checked </c:if> />
								<i class="input-helper"></i>    
								集体资本
							</label>
							<label class="checkbox checkbox-inline m-r-10">
								<input class="base" type="checkbox" value="D" name="investNature" <c:if test="${formObject.investNature.contains('D')}"> checked </c:if> />
								<i class="input-helper"></i>    
								民营资本
							</label><br/>
							<label class="checkbox checkbox-inline m-r-10">
								<input class="base" type="checkbox" value="E" name="investNature" <c:if test="${formObject.investNature.contains('E')}"> checked </c:if> />
								<i class="input-helper"></i>    
								外商投资
							</label>
							<label class="checkbox checkbox-inline m-r-10">
								<input class="base" type="checkbox" value="F" name="investNature" <c:if test="${formObject.investNature.contains('F')}"> checked </c:if> />
								<i class="input-helper"></i>    
								混合经济
							</label>
	    				</td>
	    				<td class="title">
	    					计划<br/>工期
	    				</td>
	    				<td colspan="2" style="padding-left: 10px;border-right: none;">
							<input class=" base" type="text" 
								onClick="WdatePicker()" placeholder="点击填日期" 
								value='<fmt:formatDate value="${formObject.planDurationStart}" 
								pattern="yyyy-MM-dd" />' name="planDurationStart" />
	    				</td>
	    				<td colspan="2" style="border-left: none;border-right: none;">
	    				至
	    				</td>
	    				<td colspan="2" style="padding-left: 10px;border-left: none;">
							<input class=" base" type="text" 
								onClick="WdatePicker()" placeholder="点击填日期" 
								value='<fmt:formatDate value="${formObject.planDurationEnd}" 
								pattern="yyyy-MM-dd" />' name="planDurationEnd" />
	    				</td>
	    			</tr>
	    			<tr>
	    				<td rowspan="3" class="title" style="width: 38px">
	    					申报监督内容
	    				</td>
	    				<td  class="title" height="100px">
	    					房屋<br/>建筑<br/>工程
	    				</td>
	    				<td colspan="12" style="padding-left: 10px;">
	    					<label class="checkbox checkbox-inline m-r-10">
								<input class="base" type="checkbox" value="A1" name="declareSupervise" <c:if test="${formObject.declareSupervise.contains('A1')}"> checked </c:if> />
								<i class="input-helper"></i>    
								公共建筑
							</label>
							<label class="checkbox checkbox-inline m-r-10">
								<input class="base" type="checkbox" value="A2" name="declareSupervise" <c:if test="${formObject.declareSupervise.contains('A2')}"> checked </c:if> />
								<i class="input-helper"></i>    
								商品房
							</label>
							<label class="checkbox checkbox-inline m-r-10">
								<input class="base" type="checkbox" value="A3" name="declareSupervise" <c:if test="${formObject.declareSupervise.contains('A3')}"> checked </c:if> />
								<i class="input-helper"></i>    
								保障性住房
							</label>
							<label class="checkbox checkbox-inline m-r-10">
								<input class="base" type="checkbox" value="A4" name="declareSupervise" <c:if test="${formObject.declareSupervise.contains('A4')}"> checked </c:if> />
								<i class="input-helper"></i>    
								工业建筑
							</label>
							<label class="checkbox checkbox-inline m-r-10">
								<input class="base" type="checkbox" value="A5" name="declareSupervise" <c:if test="${formObject.declareSupervise.contains('A5')}"> checked </c:if> />
								<i class="input-helper"></i>    
								基坑工程
							</label><br/>
							<label class="checkbox checkbox-inline m-r-10">
								<input class="base" type="checkbox" value="A6" name="declareSupervise" <c:if test="${formObject.declareSupervise.contains('A6')}"> checked </c:if> />
								<i class="input-helper"></i>    
								地基基础
							</label>
							<label class="checkbox checkbox-inline m-r-10">
								<input class="base" type="checkbox" value="A7" name="declareSupervise" <c:if test="${formObject.declareSupervise.contains('A7')}"> checked </c:if> />
								<i class="input-helper"></i>    
								主体结构
							</label>
							<label class="checkbox checkbox-inline m-r-10">
								<input class="base" type="checkbox" value="A8" name="declareSupervise" <c:if test="${formObject.declareSupervise.contains('A8')}"> checked </c:if> />
								<i class="input-helper"></i>    
								装饰装修
							</label>
							<label class="checkbox checkbox-inline m-r-10">
								<input class="base" type="checkbox" value="A9" name="declareSupervise" <c:if test="${formObject.declareSupervise.contains('A9')}"> checked </c:if> />
								<i class="input-helper"></i>    
								建筑屋面
							</label>
							<label class="checkbox checkbox-inline m-r-10">
								<input class="base" type="checkbox" value="Aa1" name="declareSupervise" <c:if test="${formObject.declareSupervise.contains('Aa1')}"> checked </c:if> />
								<i class="input-helper"></i>    
								给水排水
							</label><br/>
							<label class="checkbox checkbox-inline m-r-10">
								<input class="base" type="checkbox" value="Aa2" name="declareSupervise" <c:if test="${formObject.declareSupervise.contains('Aa2')}"> checked </c:if> />
								<i class="input-helper"></i>    
								建筑电气
							</label>
							<label class="checkbox checkbox-inline m-r-10">
								<input class="base" type="checkbox" value="Aa3" name="declareSupervise" <c:if test="${formObject.declareSupervise.contains('Aa3')}"> checked </c:if> />
								<i class="input-helper"></i>    
								智能建筑
							</label>
							<label class="checkbox checkbox-inline m-r-10">
								<input class="base" type="checkbox" value="Aa4" name="declareSupervise" <c:if test="${formObject.declareSupervise.contains('Aa4')}"> checked </c:if> />
								<i class="input-helper"></i>    
								通风空调
							</label>
							<label class="checkbox checkbox-inline m-r-10">
								<input class="base" type="checkbox" value="Aa5" name="declareSupervise" <c:if test="${formObject.declareSupervise.contains('Aa5')}"> checked </c:if> />
								<i class="input-helper"></i>    
								建筑节能
							</label>
							<label class="checkbox checkbox-inline m-r-10">
								<input class="base" type="checkbox" value="Aa6" name="declareSupervise" <c:if test="${formObject.declareSupervise.contains('Aa6')}"> checked </c:if> />
								<i class="input-helper"></i>    
								室外建筑环境
							</label><br/>
							<label class="checkbox checkbox-inline m-r-10">
								<input class="base" type="checkbox" value="Aa7" name="declareSupervise" <c:if test="${formObject.declareSupervise.contains('Aa7')}"> checked </c:if> />
								<i class="input-helper"></i>    
								室外安装
							</label>
							<label class="checkbox checkbox-inline m-r-10">
								<input class="base" type="checkbox" value="Aa8" name="declareSupervise" <c:if test="${formObject.declareSupervise.contains('Aa8')}"> checked </c:if> />
								<i class="input-helper"></i>    
								其他
							</label>
							<input class="base" style="width:300px;height: auto;border-bottom: 1px solid #ccc;" type="text" value="${formObject.decBuildOther}" name="decBuildOther" />
	    				</td>
	    			</tr>
	    			<tr>
	    				<td  class="title" rowspan="2">
	    					市政<br/>基础<br/>设施<br/>工程
	    				</td>
	    				<td colspan="12" style="padding-left: 10px;" height="80px">
	    					<label class="checkbox checkbox-inline m-r-10">
								<input class="base" type="checkbox" value="B1" name="declareSupervise" <c:if test="${formObject.declareSupervise.contains('B1')}"> checked </c:if> />
								<i class="input-helper"></i>    
								基坑工程
							</label>
							<label class="checkbox checkbox-inline m-r-10">
								<input class="base" type="checkbox" value="B2" name="declareSupervise" <c:if test="${formObject.declareSupervise.contains('B2')}"> checked </c:if> />
								<i class="input-helper"></i>    
								城市道路
							</label>
							<label class="checkbox checkbox-inline m-r-10">
								<input class="base" type="checkbox" value="B3" name="declareSupervise" <c:if test="${formObject.declareSupervise.contains('B3')}"> checked </c:if> />
								<i class="input-helper"></i>    
								城市桥梁
							</label>
							<label class="checkbox checkbox-inline m-r-10">
								<input class="base" type="checkbox" value="B4" name="declareSupervise" <c:if test="${formObject.declareSupervise.contains('B4')}"> checked </c:if> />
								<i class="input-helper"></i>    
								隧道
							</label>
							<label class="checkbox checkbox-inline m-r-10">
								<input class="base" type="checkbox" value="B5" name="declareSupervise" <c:if test="${formObject.declareSupervise.contains('B5')}"> checked </c:if> />
								<i class="input-helper"></i>    
								城市给水
							</label>
							<label class="checkbox checkbox-inline m-r-10">
								<input class="base" type="checkbox" value="B6" name="declareSupervise" <c:if test="${formObject.declareSupervise.contains('B6')}"> checked </c:if> />
								<i class="input-helper"></i>    
								城市排水
							</label><br/>
							<label class="checkbox checkbox-inline m-r-10">
								<input class="base" type="checkbox" value="B7" name="declareSupervise" <c:if test="${formObject.declareSupervise.contains('B7')}"> checked </c:if> />
								<i class="input-helper"></i>    
								其它
							</label>
							<input maxlength="200" class="base" style="width:300px;height: auto;border-bottom: 1px solid #ccc;" type="text" value="${formObject.decMunRoadOther}" name="decMunRoadOther" /><br/>
							（道路的路基、路面、电气、交通设备安装等工程单独报监的，应注明）
	    				</td>
	    			</tr>
	    			<tr>
	    				<td colspan="12" style="padding-left: 10px;" height="100px">
	    					<label class="checkbox checkbox-inline m-r-10">
								<input class="base" type="checkbox" value="C1" name="declareSupervise" <c:if test="${formObject.declareSupervise.contains('C1')}"> checked </c:if> />
								<i class="input-helper"></i>    
								地铁工程
							</label>
							<label class="checkbox checkbox-inline m-r-10">
								<input class="base" type="checkbox" value="C2" name="declareSupervise" <c:if test="${formObject.declareSupervise.contains('C2')}"> checked </c:if> />
								<i class="input-helper"></i>    
								基坑工程
							</label>
							<label class="checkbox checkbox-inline m-r-10">
								<input class="base" type="checkbox" value="C3" name="declareSupervise" <c:if test="${formObject.declareSupervise.contains('C3')}"> checked </c:if> />
								<i class="input-helper"></i>    
								车站工程
							</label>
							<label class="checkbox checkbox-inline m-r-10">
								<input class="base" type="checkbox" value="C4" name="declareSupervise" <c:if test="${formObject.declareSupervise.contains('C4')}"> checked </c:if> />
								<i class="input-helper"></i>    
								区间
							</label><br/>
							<label class="checkbox checkbox-inline m-r-10">
								<input class="base" type="checkbox" value="C5" name="declareSupervise" <c:if test="${formObject.declareSupervise.contains('C5')}"> checked </c:if> />
								<i class="input-helper"></i>    
								配套房屋建筑
							</label>
							<label class="checkbox checkbox-inline m-r-10">
								<input class="base" type="checkbox" value="C6" name="declareSupervise" <c:if test="${formObject.declareSupervise.contains('C6')}"> checked </c:if> />
								<i class="input-helper"></i>    
								设备工程
							</label><br/>
							<label class="checkbox checkbox-inline m-r-10">
								<input class="base" type="checkbox" value="C7" name="declareSupervise" <c:if test="${formObject.declareSupervise.contains('C7')}"> checked </c:if> />
								<i class="input-helper"></i>    
								轨道工程
							</label>
							<label class="checkbox checkbox-inline m-r-10">
								<input class="base" type="checkbox" value="C8" name="declareSupervise" <c:if test="${formObject.declareSupervise.contains('C8')}"> checked </c:if> />
								<i class="input-helper"></i>    
								附属工程
							</label><br/>
							<label class="checkbox checkbox-inline m-r-10">
								<input class="base" type="checkbox" value="C9" name="declareSupervise" <c:if test="${formObject.declareSupervise.contains('C9')}"> checked </c:if> />
								<i class="input-helper"></i>    
								其他
							</label>
							<input maxlength="200" class="base" style="width:300px;height: auto;border-bottom: 1px solid #ccc;" type="text" value="${formObject.decMunBuildOther}" name="decMunBuildOther" /><br/>
							（地基处理、桩基、二次装修、电气设备安装工程等单独报监的，应注明）
	    				</td>
	    			</tr>
	    			<tr height="50px">
	    				<td colspan="2" class="title">
	    					附注
	    				</td>
	    				<td colspan="12" style="padding: 0 10px;">
	    					<textarea maxlength="200" class="form-control auto-size base" rows="2" style="resize:none;text-indent:2em;border: none;width:880px;line-height: auto;" placeholder="请输入..." name="notions">${formObject.notions}</textarea>
	    				</td>
	    			</tr>
	    		</table>
	    	</div>
	    	<%-- 建设单位组织架构 --%>
	   		<div class="card-body card-padding">
	    		<table class="editTable" style="width: 960px;" border="0" cellspacing="0" cellpadding="0">
	    			<tr>
	    				<td colspan="6" class="title" style="text-align: left;font-size: 14px;font-weight: 800;">
	    					二、建设单位工程项目质量管理组织架构
	    				</td>
	    			</tr>
	    			<tr>
	    				<td colspan="6" style="padding-left: 30px;">
	    					组织机构代码：
	    					<input maxlength="50" class=" base" style="width:auto;height: auto;border-bottom: 1px solid #ccc;" type="text" value="${formObject.buildUnitCode}" name="buildUnitCode" />
	    					&nbsp;&nbsp;&nbsp;&nbsp;
	    					法人代表：
	    					<input maxlength="50" class=" base" style="width:auto;height: auto;border-bottom: 1px solid #ccc;" type="text" value="${formObject.buildUnitLegal}" name="buildUnitLegal" />
	    					&nbsp;&nbsp;&nbsp;&nbsp;
	    					联系电话：
	    					<input maxlength="15" class=" base phone" style="width:auto;height: auto;border-bottom: 1px solid #ccc;" type="text" value="${formObject.buildUnitPhone}" name="buildUnitPhone" />
	    				</td>
	    			</tr>
	    			<tr>
	    				<td class="title" colspan="2">
	    					姓&nbsp;&nbsp;名
	    				</td>
	    				<td class="title" width="19%">
	    					职&nbsp;&nbsp;务
	    				</td>
	    				<td class="title" width="19%">
	    					职&nbsp;&nbsp;称
	    				</td>
	    				<td class="title" width="19%">
	    					专&nbsp;&nbsp;业
	    				</td>
	    				<td class="title" width="19%">
	    					手&nbsp;&nbsp;机&nbsp;&nbsp;号&nbsp;&nbsp;码
	    				</td>
	    			</tr>
	    			<tr class="bui" name="buiTr">
	    				<td style="display: none;">
							<input type="hidden" name="bId" />
						</td>
						<td style="display: none;">
							<input type="hidden" name="jId" />
						</td>
						<td colspan="2">
							<input maxlength="50" type="text"  name="bName"></input>
						</td>
						<td>
							<input maxlength="50" type="text"  name="bDuty" readonly value="项目负责人" />
						</td>
						<td>
							<input maxlength="50" type="text"  name="bTitle"></input>
						</td>
						<td>
							<input maxlength="50" type="text"  name="bMajor"></input>
						</td>
						<td>
							<input maxlength="15" type="text" class=" mobile" name="bPhone"></input>
						</td>
	    			</tr>
	    			<tr id="bui" class="bui" name="buiTr">
	    				<td style="display: none;">
							<input type="hidden" name="bId" />
						</td>
						<td style="border-right: none;">
							<button type="button" onclick="addRow('bui')" class="btn btn-primary">
								<i class="md add"></i>
							</button>
							<input type="hidden" name="jId" />
						</td>
						<td style="border-left: none;">
							<input maxlength="50" type="text"  name="bName"></input>
						</td>
						<td>
							<input maxlength="50" type="text"  name="bDuty" />
						</td>
						<td>
							<input maxlength="50" type="text"  name="bTitle"></input>
						</td>
						<td>
							<input maxlength="50" type="text"  name="bMajor"></input>
						</td>
						<td>
							<input maxlength="15" type="text" class=" mobile" name="bPhone"></input>
						</td>
	    			</tr>
	    			<c:forEach items="${formObject.buiList}" var="bui" varStatus="i">
	    				<c:if test="${i.index == 0}">
	    					<tr id="bui" class="bui">
			    				<td style="display: none;">
									<input type="hidden" name="bId" value="${bui.bId}" />
								</td>
								<td style="border-right: none;">
									<button type="button" onclick="addRow('bui')" class="btn btn-primary">
										<i class="md add"></i>
									</button>
									<input type="hidden" name="jId" value="${bui.jId}" />
								</td>
								<td style="border-left: none;">
									<input type="text" maxlength="50"  name="bName" value="${bui.bName}" />
								</td>
								<td>
									<input type="text" maxlength="50"  name="bDuty" value="${bui.bDuty}" />
								</td>
								<td>
									<input type="text" maxlength="50"  name="bTitle" value="${bui.bTitle}" />
								</td>
								<td>
									<input type="text" maxlength="50"  name="bMajor" value="${bui.bMajor}" />
								</td>
								<td>
									<input type="text" maxlength="15" class=" mobile" name="bPhone" value="${bui.bPhone}" />
								</td>
			    			</tr>
	    				</c:if>
	    				<c:if test="${i.index > 0}">
	    					<tr id="bui${bui.bId}" class="bui">
			    				<td style="display: none;">
									<input type="hidden" name="bId" value="${bui.bId}" />
								</td>
								<td style="border-right: none;">
									<button onclick="removeRow('bui${bui.bId}')" class="btn btn-danger">
										<i class="md minus"></i>
									</button>
									<input type="hidden" name="jId" value="${bui.jId}" />
								</td>
								<td style="border-left: none;">
									<input maxlength="50" type="text"  name="bName" value="${bui.bName}" />
								</td>
								<td>
									<input maxlength="50" type="text"  name="bDuty" value="${bui.bDuty}" />
								</td>
								<td>
									<input maxlength="50" type="text"  name="bTitle" value="${bui.bTitle}" />
								</td>
								<td>
									<input maxlength="50" type="text"  name="bMajor" value="${bui.bMajor}" />
								</td>
								<td>
									<input maxlength="15" type="text" class=" mobile" name="bPhone" value="${bui.bPhone}" />
								</td>
			    			</tr>
	    				</c:if>
	    			</c:forEach>
	    		</table>
	    	</div>
    		<div class="card-body card-padding">
    			<table class="editTable" style="width: 960px;" border="0" cellspacing="0" cellpadding="0">
    				<tr>
	    				<td colspan="11" class="title" style="text-align: left;font-size: 14px;font-weight: 800;">
	    					三、 其他质量责任主体机构资质、人员资格情况
	    				</td>
	    			</tr>
	    			<tr>
	    				<td colspan="3" class="title">
	    					施工单位名称
	    				</td>
	    				<td colspan="6">
	    					<input maxlength="50" class=" base" type="text" value="${formObject.construct}" name="construct" />
	    				</td>
	    				<td class="title">
	    					组织机<br/>构代码
	    				</td>
	    				<td>
	    					<input maxlength="50" class=" base" type="text" value="${formObject.conCode}" name="conCode" />
	    				</td>
	    			</tr>
	    			<tr>
	    				<td colspan="3" class="title">
	    					资质等级
	    				</td>
	    				<td colspan="4">
	    					<input maxlength="50" class=" base" type="text" value="${formObject.conAptRank}" name="conAptRank" />
	    				</td>
	    				<td colspan="2" class="title">
	    					资质证书号码
	    				</td>
	    				<td colspan="2">
	    					<input maxlength="50" class=" base" type="text" value="${formObject.conAptId}" name="conAptId" />
	    				</td>
	    			</tr>
	    			<tr style="height: 30px">
	    				<td colspan="3" rowspan="2" class="title">
	    					项目负责人姓名
	    				</td>
	    				<td colspan="4" rowspan="2">
	    					<input maxlength="50" class=" base" type="text" value="${formObject.conPri}" name="conPri" />
	    				</td>
	    				<td colspan="2" class="title">
	    					资质等级
	    				</td>
	    				<td colspan="2">
	    					<input maxlength="50" class=" base" type="text" value="${formObject.conPriAptRank}" name="conPriAptRank" />
	    				</td>
	    			</tr>
	    			<tr style="height: 30px;">
	    				<td colspan="2" class="title">
	    					证书号码
	    				</td>
	    				<td colspan="2">
	    					<input maxlength="50" class=" base" type="text" value="${formObject.conPriAptId}" name="conPriAptId" />
	    				</td>
	    			</tr>
	    			<tr style="height: 60px;">
	    				<td colspan="3" class="title">
	    					企业工商注册所在地
	    				</td>
	    				<td class="title">
	    					省内
	    				</td>
	    				<td colspan="3" style="padding-left: 10px">
	    					<label class="checkbox checkbox-inline m-r-10">
								<input class="base" type="checkbox" value="A" name="conBusRegLoc" <c:if test="${formObject.conBusRegLoc.contains('A')}"> checked </c:if> />
								<i class="input-helper"></i>    
								中央企业
							</label>
							<label class="checkbox checkbox-inline m-r-10">
								<input class="base" type="checkbox" value="B" name="conBusRegLoc" <c:if test="${formObject.conBusRegLoc.contains('B')}"> checked </c:if> />
								<i class="input-helper"></i>    
								省属企业
							</label><br/>
							<label class="checkbox checkbox-inline m-r-10">
								<input class="base" type="checkbox" value="C" name="conBusRegLoc" <c:if test="${formObject.conBusRegLoc.contains('C')}"> checked </c:if> />
								<i class="input-helper"></i>    
								市属企业
							</label>
							<label class="checkbox checkbox-inline m-r-10">
								<input class="base" type="checkbox" value="D" name="conBusRegLoc" <c:if test="${formObject.conBusRegLoc.contains('D')}"> checked </c:if> />
								<i class="input-helper"></i>    
								其它
							</label>
	    				</td>
	    				<td colspan="2" class="title">
	    					省外
	    				</td>
	    				<td colspan="2" style="padding-left: 10px">
	    					<label class="checkbox checkbox-inline m-r-10">
								<input class="base" type="checkbox" value="E" name="conBusRegLoc" <c:if test="${formObject.conBusRegLoc.contains('E')}"> checked </c:if> />
								<i class="input-helper"></i>    
								中央企业
							</label>
							<label class="checkbox checkbox-inline m-r-10">
								<input class="base" type="checkbox" value="F" name="conBusRegLoc" <c:if test="${formObject.conBusRegLoc.contains('F')}"> checked </c:if> />
								<i class="input-helper"></i>    
								地方企业
							</label>
							<br/>
							<label class="checkbox checkbox-inline m-r-10">
								<input class="base" type="checkbox" value="G" name="conBusRegLoc" <c:if test="${formObject.conBusRegLoc.contains('G')}"> checked </c:if> />
								<i class="input-helper"></i>    
								其他
							</label>
	    				</td>
	    			</tr>
	    			<tr>
	    				<td colspan="3" class="title">
	    					勘察单位名称
	    				</td>
	    				<td colspan="6">
	    					<input maxlength="50" class=" base" type="text" value="${formObject.prospect}" name="prospect" />
	    				</td>
	    				<td class="title">
	    					组织机<br/>构代码
	    				</td>
	    				<td>
	    					<input maxlength="50" class=" base" type="text" value="${formObject.proCode}" name="proCode" />
	    				</td>
	    			</tr>
	    			<tr>
	    				<td colspan="3" class="title">
	    					资质等级
	    				</td>
	    				<td colspan="4">
	    					<input maxlength="50" class=" base" type="text" value="${formObject.proAptRank}" name="proAptRank" />
	    				</td>
	    				<td colspan="2" class="title">
	    					资质证书号码
	    				</td>
	    				<td colspan="2">
	    					<input maxlength="50" class=" base" type="text" value="${formObject.proAptId}" name="proAptId" />
	    				</td>
	    			</tr>
	    			<tr>
	    				<td colspan="3" class="title">
	    					项目负责人姓名
	    				</td>
	    				<td colspan="4">
	    					<input maxlength="50" class=" base" type="text" value="${formObject.proPri}" name="proPri" />
	    				</td>
	    				<td colspan="2" class="title">
	    					证书号码
	    				</td>
	    				<td colspan="2">
	    					<input maxlength="50" class=" base" type="text" value="${formObject.proPriAptId}" name="proPriAptId" />
	    				</td>
	    			</tr>
	    			<tr style="height: 60px;">
	    				<td colspan="3" class="title">
	    					企业工商注册所在地
	    				</td>
	    				<td class="title">
	    					省内
	    				</td>
	    				<td colspan="3" style="padding-left: 10px">
	    					<label class="checkbox checkbox-inline m-r-10">
								<input class="base" type="checkbox" value="A" name="proBusRegLoc" <c:if test="${formObject.proBusRegLoc.contains('A')}"> checked </c:if> />
								<i class="input-helper"></i>    
								中央企业
							</label>
							<label class="checkbox checkbox-inline m-r-10">
								<input class="base" type="checkbox" value="B" name="proBusRegLoc" <c:if test="${formObject.proBusRegLoc.contains('B')}"> checked </c:if> />
								<i class="input-helper"></i>    
								省属企业
							</label><br/>
							<label class="checkbox checkbox-inline m-r-10">
								<input class="base" type="checkbox" value="C" name="proBusRegLoc" <c:if test="${formObject.proBusRegLoc.contains('C')}"> checked </c:if> />
								<i class="input-helper"></i>    
								市属企业
							</label>
							<label class="checkbox checkbox-inline m-r-10">
								<input class="base" type="checkbox" value="D" name="proBusRegLoc" <c:if test="${formObject.proBusRegLoc.contains('D')}"> checked </c:if> />
								<i class="input-helper"></i>    
								其它
							</label>
	    				</td>
	    				<td colspan="2" class="title">
	    					省外
	    				</td>
	    				<td colspan="2" style="padding-left: 10px">
	    					<label class="checkbox checkbox-inline m-r-10">
								<input class="base" type="checkbox" value="E" name="proBusRegLoc" <c:if test="${formObject.proBusRegLoc.contains('E')}"> checked </c:if> />
								<i class="input-helper"></i>    
								中央企业
							</label>
							<label class="checkbox checkbox-inline m-r-10">
								<input class="base" type="checkbox" value="F" name="proBusRegLoc" <c:if test="${formObject.proBusRegLoc.contains('F')}"> checked </c:if> />
								<i class="input-helper"></i>    
								地方企业
							</label>
							<br/>
							<label class="checkbox checkbox-inline m-r-10">
								<input class="base" type="checkbox" value="G" name="proBusRegLoc" <c:if test="${formObject.proBusRegLoc.contains('G')}"> checked </c:if> />
								<i class="input-helper"></i>    
								其他
							</label>
	    				</td>
	    			</tr>
	    			<tr>
	    				<td colspan="3" class="title">
	    					设计单位名称
	    				</td>
	    				<td colspan="6">
	    					<input maxlength="50" class=" base" type="text" value="${formObject.design}" name="design" />
	    				</td>
	    				<td class="title">
	    					组织机<br/>构代码
	    				</td>
	    				<td>
	    					<input maxlength="50" class=" base" type="text" value="${formObject.desCode}" name="desCode" />
	    				</td>
	    			</tr>
	    			<tr>
	    				<td colspan="3" class="title">
	    					资质等级
	    				</td>
	    				<td colspan="4">
	    					<input maxlength="50" class=" base" type="text" value="${formObject.desAptRank}" name="desAptRank" />
	    				</td>
	    				<td colspan="2" class="title">
	    					资质证书号码
	    				</td>
	    				<td colspan="2">
	    					<input maxlength="50" class=" base" type="text" value="${formObject.desAptId}" name="desAptId" />
	    				</td>
	    			</tr>
	    			<tr>
	    				<td colspan="3" class="title">
	    					项目负责人姓名
	    				</td>
	    				<td colspan="4">
	    					<input maxlength="50" class=" base" type="text" value="${formObject.desPri}" name="desPri" />
	    				</td>
	    				<td colspan="2" class="title">
	    					证书号码
	    				</td>
	    				<td colspan="2">
	    					<input maxlength="50" class=" base" type="text" value="${formObject.desPriAptId}" name="desPriAptId" />
	    				</td>
	    			</tr>
	    			<tr style="height: 60px;">
	    				<td colspan="3" class="title">
	    					企业工商注册所在地
	    				</td>
	    				<td class="title">
	    					省内
	    				</td>
	    				<td colspan="3" style="padding-left: 10px">
	    					<label class="checkbox checkbox-inline m-r-10">
								<input class="base" type="checkbox" value="A" name="desBusRegLoc" <c:if test="${formObject.desBusRegLoc.contains('A')}"> checked </c:if> />
								<i class="input-helper"></i>    
								中央企业
							</label>
							<label class="checkbox checkbox-inline m-r-10">
								<input class="base" type="checkbox" value="B" name="desBusRegLoc" <c:if test="${formObject.desBusRegLoc.contains('B')}"> checked </c:if> />
								<i class="input-helper"></i>    
								省属企业
							</label><br/>
							<label class="checkbox checkbox-inline m-r-10">
								<input class="base" type="checkbox" value="C" name="desBusRegLoc" <c:if test="${formObject.desBusRegLoc.contains('C')}"> checked </c:if> />
								<i class="input-helper"></i>    
								市属企业
							</label>
							<label class="checkbox checkbox-inline m-r-10">
								<input class="base" type="checkbox" value="D" name="desBusRegLoc" <c:if test="${formObject.desBusRegLoc.contains('D')}"> checked </c:if> />
								<i class="input-helper"></i>    
								其它
							</label>
	    				</td>
	    				<td colspan="2" class="title">
	    					省外
	    				</td>
	    				<td colspan="2" style="padding-left: 10px">
	    					<label class="checkbox checkbox-inline m-r-10">
								<input class="base" type="checkbox" value="E" name="desBusRegLoc" <c:if test="${formObject.desBusRegLoc.contains('E')}"> checked </c:if> />
								<i class="input-helper"></i>    
								中央企业
							</label>
							<label class="checkbox checkbox-inline m-r-10">
								<input class="base" type="checkbox" value="F" name="desBusRegLoc" <c:if test="${formObject.desBusRegLoc.contains('F')}"> checked </c:if> />
								<i class="input-helper"></i>    
								地方企业
							</label><br/>
							<label class="checkbox checkbox-inline m-r-10">
								<input class="base" type="checkbox" value="G" name="desBusRegLoc" <c:if test="${formObject.desBusRegLoc.contains('G')}"> checked </c:if> />
								<i class="input-helper"></i>    
								其他
							</label>
	    				</td>
	    			</tr>
	    			<tr>
	    				<td colspan="3" class="title">
	    					监理单位名称
	    				</td>
	    				<td colspan="6">
	    					<input maxlength="50" class=" base" type="text" value="${formObject.supervision}" name="supervision" />
	    				</td>
	    				<td class="title">
	    					组织机<br/>构代码
	    				</td>
	    				<td>
	    					<input maxlength="50" class=" base" type="text" value="${formObject.supCode}" name="supCode" />
	    				</td>
	    			</tr>
	    			<tr>
	    				<td colspan="3" class="title">
	    					资质等级
	    				</td>
	    				<td colspan="4">
	    					<input maxlength="50" class=" base" type="text" value="${formObject.supAptRank}" name="supAptRank" />
	    				</td>
	    				<td colspan="2" class="title">
	    					资质证书号码
	    				</td>
	    				<td colspan="2">
	    					<input maxlength="50" class=" base" type="text" value="${formObject.supAptId}" name="supAptId" />
	    				</td>
	    			</tr>
	    			<tr>
	    				<td colspan="3" class="title">
	    					总监理工程师姓名
	    				</td>
	    				<td colspan="4">
	    					<input maxlength="50" class=" base" type="text" value="${formObject.supPri}" name="supPri" />
	    				</td>
	    				<td colspan="2" class="title">
	    					注册证书号码
	    				</td>
	    				<td colspan="2">
	    					<input maxlength="50" class=" base" type="text" value="${formObject.supPriAptId}" name="supPriAptId" />
	    				</td>
	    			</tr>
	    			<tr style="height: 60px;">
	    				<td colspan="3" class="title">
	    					企业工商注册所在地
	    				</td>
	    				<td class="title">
	    					省内
	    				</td>
	    				<td colspan="3" style="padding-left: 10px">
	    					<label class="checkbox checkbox-inline m-r-10">
								<input class="base" type="checkbox" value="A" name="supBusRegLoc" <c:if test="${formObject.supBusRegLoc.contains('A')}"> checked </c:if> />
								<i class="input-helper"></i>    
								中央企业
							</label>
							<label class="checkbox checkbox-inline m-r-10">
								<input class="base" type="checkbox" value="B" name="supBusRegLoc" <c:if test="${formObject.supBusRegLoc.contains('B')}"> checked </c:if> />
								<i class="input-helper"></i>    
								省属企业
							</label><br/>
							<label class="checkbox checkbox-inline m-r-10">
								<input class="base" type="checkbox" value="C" name="supBusRegLoc" <c:if test="${formObject.supBusRegLoc.contains('C')}"> checked </c:if> />
								<i class="input-helper"></i>    
								市属企业
							</label>
							<label class="checkbox checkbox-inline m-r-10">
								<input class="base" type="checkbox" value="D" name="supBusRegLoc" <c:if test="${formObject.supBusRegLoc.contains('D')}"> checked </c:if> />
								<i class="input-helper"></i>    
								其它
							</label>
	    				</td>
	    				<td colspan="2" class="title">
	    					省外
	    				</td>
	    				<td colspan="2" style="padding-left: 10px">
	    					<label class="checkbox checkbox-inline m-r-10">
								<input class="base" type="checkbox" value="E" name="supBusRegLoc" <c:if test="${formObject.supBusRegLoc.contains('E')}"> checked </c:if> />
								<i class="input-helper"></i>    
								中央企业
							</label>
							<label class="checkbox checkbox-inline m-r-10">
								<input class="base" type="checkbox" value="F" name="supBusRegLoc" <c:if test="${formObject.supBusRegLoc.contains('F')}"> checked </c:if> />
								<i class="input-helper"></i>    
								地方企业
							</label><br/>
							<label class="checkbox checkbox-inline m-r-10">
								<input class="base" type="checkbox" value="G" name="supBusRegLoc" <c:if test="${formObject.supBusRegLoc.contains('G')}"> checked </c:if> />
								<i class="input-helper"></i>    
								其他
							</label>
	    				</td>
	    			</tr>
    			</table>
    		</div>
	    	<%-- 施工单位组织架构 --%>
	    	<div class="card-body card-padding">
	    		<table class="editTable" style="width: 960px;" border="0" cellspacing="0" cellpadding="0">
	    			<tr>
	    				<td colspan="6" class="title" style="text-align: left;font-size: 14px;font-weight: 800;">
	    					四、施工单位项目工程质量管理组织架构
	    				</td>
	    			</tr>
	   				<tr>
	    				<td colspan="2" style="padding-left: 10px;border-right: none;">
	    					<label class="checkbox checkbox-inline m-r-10">
								<input class="base" type="checkbox" value="A" name="contractType" <c:if test="${formObject.contractType.contains('A')}"> checked </c:if> />
								<i class="input-helper"></i>    
								总包
							</label>
							<label class="checkbox checkbox-inline m-r-10">
								<input class="base" type="checkbox" value="B" name="contractType" <c:if test="${formObject.contractType.contains('B')}"> checked </c:if> />
								<i class="input-helper"></i>    
								分包
							</label>
	    				</td>
	    				<td style="border: none;text-align: right;">
	    					法人代表：
	    				</td>
	    				<td style="border: none;">
	    					<input maxlength="50" class=" base" style="width:auto;height: auto;border-bottom: 1px solid #ccc;" type="text" value="${formObject.conUnitLegal}" name="conUnitLegal" />
	    				</td>
	    				<td style="border: none;text-align: right;">
	    					联系电话：
	    				</td>
	    				<td style="border-left: none;">
	    					<input maxlength="15" class=" base phone" style="width:auto;height: auto;border-bottom: 1px solid #ccc;" type="text" value="${formObject.conUnitPhone}" name="conUnitPhone" />
	    				</td>
	    			</tr>
	    			<tr>
	    				<td class="title" colspan="2">
	    					姓&nbsp;&nbsp;名
	    				</td>
	    				<td class="title" width="19%">
	    					职&nbsp;&nbsp;务
	    				</td>
	    				<td class="title" width="19%">
	    					职&nbsp;&nbsp;称
	    				</td>
	    				<td class="title" width="19%">
	    					专&nbsp;&nbsp;业
	    				</td>
	    				<td class="title" width="19%">
	    					手&nbsp;&nbsp;机&nbsp;&nbsp;号&nbsp;&nbsp;码
	    				</td>
	    			</tr>
	    			<tr class="con" name="conTr">
	    				<td style="display: none;">
							<input type="hidden" name="cId" />
						</td>
						<td style="display: none;">
							<input type="hidden" name="jId" />
						</td>
						<td colspan="2">
							<input maxlength="50" type="text"  name="cName"></input>
						</td>
						<td>
							<input maxlength="50" type="text"  name="cDuty" readonly value="项目负责人"></input>
						</td>
						<td>
							<input maxlength="50" type="text"  name="cTitle"></input>
						</td>
						<td>
							<input maxlength="50" type="text"  name="cMajor"></input>
						</td>
						<td>
							<input maxlength="15" type="text" class=" mobile" name="cPhone"></input>
						</td>
	    			</tr>
	    			<tr class="con" name="conTr">
	    				<td style="display: none;">
							<input type="hidden" name="cId" />
						</td>
						<td style="display: none;">
							<input type="hidden" name="jId" />
						</td>
						<td colspan="2">
							<input maxlength="50" type="text"  name="cName"></input>
						</td>
						<td>
							<input maxlength="50" type="text"  name="cDuty" readonly value="项目技术负责人"></input>
						</td>
						<td>
							<input maxlength="50" type="text"  name="cTitle"></input>
						</td>
						<td>
							<input maxlength="50" type="text"  name="cMajor"></input>
						</td>
						<td>
							<input maxlength="15" type="text" class=" mobile" name="cPhone"></input>
						</td>
	    			</tr>
	    			<tr class="con" name="conTr">
	    				<td style="display: none;">
							<input type="hidden" name="cId" />
						</td>
						<td style="display: none;">
							<input type="hidden" name="jId" />
						</td>
						<td colspan="2">
							<input maxlength="50" type="text"  name="cName"></input>
						</td>
						<td>
							<input maxlength="50" type="text"  name="cDuty" readonly value="专业质量检查员"></input>
						</td>
						<td>
							<input maxlength="50" type="text"  name="cTitle"></input>
						</td>
						<td>
							<input maxlength="50" type="text"  name="cMajor"></input>
						</td>
						<td>
							<input maxlength="15" type="text" class=" mobile" name="cPhone"></input>
						</td>
	    			</tr>
	    			<tr class="con" name="conTr">
	    				<td style="display: none;">
							<input type="hidden" name="cId" />
						</td>
						<td style="display: none;">
							<input type="hidden" name="jId" />
						</td>
						<td colspan="2">
							<input maxlength="50" type="text"  name="cName"></input>
						</td>
						<td>
							<input maxlength="50" type="text"  name="cDuty" readonly value="施工员"></input>
						</td>
						<td>
							<input maxlength="50" type="text"  name="cTitle"></input>
						</td>
						<td>
							<input maxlength="50" type="text"  name="cMajor"></input>
						</td>
						<td>
							<input maxlength="15" type="text" class=" mobile" name="cPhone"></input>
						</td>
	    			</tr>
	    			<tr class="con" name="conTr">
	    				<td style="display: none;">
							<input type="hidden" name="cId" />
						</td>
						<td style="display: none;">
							<input type="hidden" name="jId" />
						</td>
						<td colspan="2">
							<input maxlength="50" type="text"  name="cName"></input>
						</td>
						<td>
							<input maxlength="50" type="text"  name="cDuty" readonly value="资料员"></input>
						</td>
						<td>
							<input maxlength="50" type="text"  name="cTitle"></input>
						</td>
						<td>
							<input maxlength="50" type="text"  name="cMajor"></input>
						</td>
						<td>
							<input maxlength="15" type="text" class=" mobile" name="cPhone"></input>
						</td>
	    			</tr>
	    			<tr id="con" class="con" name="conTr">
	    				<td style="display: none;">
							<input type="hidden" name="cId" />
						</td>
						<td style="border-right: none;">
							<button type="button" onclick="addRow('con')" class="btn btn-primary">
								<i class="md add"></i>
							</button>
							<input type="hidden" name="jId" />
						</td>
						<td style="border-left: none;">
							<input maxlength="50" type="text"  name="cName"></input>
						</td>
						<td>
							<input maxlength="50" type="text"  name="cDuty"></input>
						</td>
						<td>
							<input maxlength="50" type="text"  name="cTitle"></input>
						</td>
						<td>
							<input maxlength="50" type="text"  name="cMajor"></input>
						</td>
						<td>
							<input maxlength="15" type="text" class=" mobile" name="cPhone"></input>
						</td>
	    			</tr>
	    			<c:forEach items="${formObject.conList}" var="con" varStatus="i">
	    				<c:if test="${i.index == 0}">
	    					<tr id="con" class="con">
			    				<td style="display: none;">
									<input type="hidden" name="cId" value="${con.cId}" />
								</td>
								<td style="border-right: none;">
									<button type="button" onclick="addRow('con')" class="btn btn-primary">
										<i class="md add"></i>
									</button>
									<input type="hidden" name="jId" value="${con.jId}" />
								</td>
								<td style="border-left: none;">
									<input maxlength="50" type="text"  name="cName" value="${con.cName}" />
								</td>
								<td>
									<input maxlength="50" type="text"  name="cDuty" value="${con.cDuty}" />
								</td>
								<td>
									<input maxlength="50" type="text"  name="cTitle" value="${con.cTitle}" />
								</td>
								<td>
									<input maxlength="50" type="text"  name="cMajor" value="${con.cMajor}" />
								</td>
								<td>
									<input maxlength="15" type="text" class=" mobile" name="cPhone" value="${con.cPhone}" />
								</td>
			    			</tr>
	    				</c:if>
	    				<c:if test="${i.index > 0}">
	    					<tr id="con${con.cId}" class="con">
			    				<td style="display: none;">
									<input type="hidden" name="cId" value="${con.cId}" />
								</td>
								<td style="border-right: none;">
									<button onclick="removeRow('con${con.cId}')" class="btn btn-danger">
										<i class="md minus"></i>
									</button>
									<input type="hidden" name="jId" value="${con.jId}" />
								</td>
								<td style="border-left: none;">
									<input maxlength="50" type="text"  name="cName" value="${con.cName}" />
								</td>
								<td>
									<input maxlength="50" type="text"  name="cDuty" value="${con.cDuty}" />
								</td>
								<td>
									<input maxlength="50" type="text"  name="cTitle" value="${con.cTitle}" />
								</td>
								<td>
									<input maxlength="50" type="text"  name="cMajor" value="${con.cMajor}" />
								</td>
								<td>
									<input maxlength="15" type="text" class=" mobile" name="cPhone" value="${con.cPhone}" />
								</td>
			    			</tr>
	    				</c:if>
	    			</c:forEach>
	    		</table>
	    		注：分包单位可在以后补报
	    	</div>
	    	<%-- 监理单位组织架构 --%>
	    	<div class="card-body card-padding">
	    		<table class="editTable" style="width: 960px;" border="0" cellspacing="0" cellpadding="0">
	    			<tr>
	    				<td colspan="6" class="title" style="text-align: left;font-size: 14px;font-weight: 800;">
	    					五、监理单位工程项目质量管理组织架构
	    				</td>
	    			</tr>
	   				<tr>
	    				<td colspan="2" style="border-right: none;text-align: right;">
	    					法人代表：
	    				</td>
	    				<td style="border: none;">
	    					<input maxlength="50" class=" base" style="width:auto;height: auto;border-bottom: 1px solid #ccc;" type="text" value="${formObject.supUnitLegal}" name="supUnitLegal" />
	    				</td>
	    				<td colspan="2" style="border: none;text-align: right;">
	    					联系电话：
	    				</td>
	    				<td style="border-left: none;">
	    					<input maxlength="15" class=" base phone" style="width:auto;height: auto;border-bottom: 1px solid #ccc;" type="text" value="${formObject.supUnitPhone}" name="supUnitPhone" />
	    				</td>
	    			</tr>
	    			<tr>
	    				<td class="title" colspan="2">
	    					姓&nbsp;&nbsp;名
	    				</td>
	    				<td class="title" width="19%">
	    					职&nbsp;&nbsp;务
	    				</td>
	    				<td class="title" width="19%">
	    					职&nbsp;&nbsp;称
	    				</td>
	    				<td class="title" width="19%">
	    					专&nbsp;&nbsp;业
	    				</td>
	    				<td class="title" width="19%">
	    					手&nbsp;&nbsp;机&nbsp;&nbsp;号&nbsp;&nbsp;码
	    				</td>
	    			</tr>
	    			<tr class="sup" name="supTr">
	    				<td style="display: none;">
							<input type="hidden" name="sId" />
						</td>
						<td style="display: none;">
							<input type="hidden" name="jId" />
						</td>
						<td colspan="2">
							<input maxlength="50" type="text"  name="sName"></input>
						</td>
						<td>
							<input maxlength="50" type="text"  name="sDuty" readonly value="项目总监理工程师"></input>
						</td>
						<td>
							<input maxlength="50" type="text"  name="sTitle"></input>
						</td>
						<td>
							<input maxlength="50" type="text"  name="sMajor"></input>
						</td>
						<td>
							<input maxlength="15" type="text" class=" mobile" name="sPhone"></input>
						</td>
	    			</tr>
	    			<tr class="sup" name="supTr">
	    				<td style="display: none;">
							<input type="hidden" name="sId" />
						</td>
						<td style="display: none;">
							<input type="hidden" name="jId" />
						</td>
						<td colspan="2">
							<input maxlength="50" type="text"  name="sName"></input>
						</td>
						<td>
							<input maxlength="50" type="text"  name="sDuty" readonly value="专业监理工程师"></input>
						</td>
						<td>
							<input maxlength="50" type="text"  name="sTitle"></input>
						</td>
						<td>
							<input maxlength="50" type="text"  name="sMajor"></input>
						</td>
						<td>
							<input maxlength="15" type="text" class=" mobile" name="sPhone"></input>
						</td>
	    			</tr>
	    			<tr class="sup" name="supTr">
	    				<td style="display: none;">
							<input type="hidden" name="sId" />
						</td>
						<td style="display: none;">
							<input type="hidden" name="jId" />
						</td>
						<td colspan="2">
							<input maxlength="50" type="text"  name="sName"></input>
						</td>
						<td>
							<input maxlength="50" type="text"  name="sDuty" readonly value="监理员"></input>
						</td>
						<td>
							<input maxlength="50" type="text"  name="sTitle"></input>
						</td>
						<td>
							<input maxlength="50" type="text"  name="sMajor"></input>
						</td>
						<td>
							<input maxlength="15" type="text" class=" mobile" name="sPhone"></input>
						</td>
	    			</tr>
	    			<tr id="sup" class="sup" name="supTr">
	    				<td style="display: none;">
							<input type="hidden" name="sId" />
						</td>
						<td style="border-right: none;">
							<button type="button" onclick="addRow('sup')" class="btn btn-primary">
								<i class="md add"></i>
							</button>
							<input type="hidden" name="jId" />
						</td>
						<td style="border-left: none;">
							<input maxlength="50" type="text"  name="sName"></input>
						</td>
						<td>
							<input maxlength="50" type="text"  name="sDuty"></input>
						</td>
						<td>
							<input maxlength="50" type="text"  name="sTitle"></input>
						</td>
						<td>
							<input maxlength="50" type="text"  name="sMajor"></input>
						</td>
						<td>
							<input maxlength="15" type="text" class=" mobile" name="sPhone"></input>
						</td>
	    			</tr>
	    			<c:forEach items="${formObject.supList}" var="sup" varStatus="i">
	    				<c:if test="${i.index == 0}">
	    					<tr id="sup" class="sup">
			    				<td style="display: none;">
									<input type="hidden" name="sId" value="${sup.sId}"/>
								</td>
								<td style="border-right: none;">
									<button type="button" onclick="addRow('sup')" class="btn btn-primary">
										<i class="md add"></i>
									</button>
									<input type="hidden" name="jId" value="${sup.jId}"/>
								</td>
								<td style="border-left: none;">
									<input maxlength="50" type="text"  name="sName" value="${sup.sName}"/>
								</td>
								<td>
									<input maxlength="50" type="text"  name="sDuty" value="${sup.sDuty}"/>
								</td>
								<td>
									<input maxlength="50" type="text"  name="sTitle" value="${sup.sTitle}"/>
								</td>
								<td>
									<input maxlength="50" type="text"  name="sMajor" value="${sup.sMajor}"/>
								</td>
								<td>
									<input maxlength="15" type="text" class=" mobile" name="sPhone" value="${sup.sPhone}"/>
								</td>
			    			</tr>
	    				</c:if>
	    				<c:if test="${i.index > 0}">
	    					<tr id="sup${sup.sId}" class="sup">
			    				<td style="display: none;">
									<input type="hidden" name="sId" value="${sup.sId}"/>
								</td>
								<td style="border-right: none;">
									<button onclick="removeRow('sup${sup.sId}')" class="btn btn-danger">
										<i class="md minus"></i>
									</button>
									<input type="hidden" name="jId" value="${sup.jId}"/>
								</td>
								<td style="border-left: none;">
									<input maxlength="50" type="text"  name="sName" value="${sup.sName}"/>
								</td>
								<td>
									<input maxlength="50" type="text"  name="sDuty" value="${sup.sDuty}"/>
								</td>
								<td>
									<input maxlength="50" type="text"  name="sTitle" value="${sup.sTitle}"/>
								</td>
								<td>
									<input maxlength="50" type="text"  name="sMajor" value="${sup.sMajor}"/>
								</td>
								<td>
									<input maxlength="15" type="text" class=" mobile" name="sPhone" value="${sup.sPhone}"/>
								</td>
			    			</tr>
	    				</c:if>
	    			</c:forEach>
	    		</table>
	    	</div>
	    	<br/><br/>
	    	<table class="editTable" style="width: 960px;" border="0" cellspacing="0" cellpadding="0">
	    		<tbody>
	    			<tr>
                		<td class="title" style="width: 15%;">
                			建设单位名称
                		</td>
                		<td style="width: 35%;">
                			<input maxlength="200" class=" base" type="text" name="company" value="${formObject.company}" />
                		</td>
                		<td class="title" style="width: 15%;">
                			建设单位日期
                		</td>
                		<td style="width: 35%;">
                			<input class=" base" type="text" 
                				onClick="WdatePicker()" placeholder="点击填日期" 
                 				value='<fmt:formatDate value="${formObject.buildUnitDate}" 
                 				pattern="yyyy-MM-dd" />' name="buildUnitDate" />
                		</td>
                	</tr>
                	<tr>
                		<td class="title">
                			监理单位日期
                		</td>
                		<td>
                			<input class=" base" type="text" onClick="WdatePicker()" 
                 				placeholder="点击填日期" 
                 				value='<fmt:formatDate value="${formObject.supUnitDate}" 
                 				pattern="yyyy-MM-dd" />' name="supUnitDate" />
                		</td>
                		<td class="title">
                			施工单位日期
                		</td>
                		<td>
                			<input class=" base" type="text" onClick="WdatePicker()" 
                 				placeholder="点击填日期" 
                 				value='<fmt:formatDate value="${formObject.conUnitDate}" 
                 				pattern="yyyy-MM-dd" />' name="conUnitDate" />
                		</td>
                	</tr>
	    		</tbody>
	    	</table>
         	<div class="row">
         		<input type="hidden" id="jsonDataObj" name="jsonDataObj" />
			    <input type="hidden" id="jsonDataBuiList" name="jsonDataBuiList" />
				<input type="hidden" id="jsonDataConList" name="jsonDataConList" />
				<input type="hidden" id="jsonDataSupList" name="jsonDataSupList" />
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
         </form>
   	</div>
</body>