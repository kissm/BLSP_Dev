<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>建设工程消防设计审核申报表</title>
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
	    	$("#xfJsgcSjshsbbForm").validate({
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
			var unitList = [];
			$(".construction").each(function (){
				var unit = {};
				$(this).find('td').each(function (){
					unit[$(this).find('input').attr('name')] = $(this).find('input').val();
				});
				unitList.push(unit);
			});
			var unitJsonData = JSON.stringify(unitList);
			$("#jsonDataUnitList").val(unitJsonData);
			var buildList = [];
			$(".building").each(function (){
				var build = {};
				$(this).find('td').each(function (){
					build[$(this).find('input').attr('name')] = $(this).find('input').val();
				});
				buildList.push(build);
			});
			var buildJsonData = JSON.stringify(buildList);
			$("#jsonDataBuildList").val(buildJsonData);
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
			$("#xfJsgcSjshsbbForm").submit();
			$(".savebutton").attr("disabled",true);
			if($("#xfJsgcSjshsbbForm").valid()){
				$("#xfJsgcSjshsbbForm").submit();
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
			var unitType = $(tr).find('input[name="unitType"]').val();
			$(tr).find('input').val('');
			$(tr).find('input[name="unitType"]').val(unitType);
			$(tr).find('button').removeAttr('onclick').removeClass('btn-primary').addClass('btn-danger').bind("click", function(){
				$(this).parents('tr').remove();
			});
			$(tr).find('button i.md').removeClass('md-add').addClass('md-clear');
			
			$('.'+id+':last').after(tr);
		}
		function removeRow(id){
			console.info(id);
			$("#"+id).remove();
		}
		$(function (){
			var unitList = '${formObject.dwList}';
			if(unitList.length > 0){
				$('tr[name="unitTr"]').remove();
			}
			var buildList = '${formObject.jzList}';
			if(buildList.length > 0){
				$('tr[name="buildTr"]').remove();
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
        	<h2>建设工程消防设计审核申报表<small>您可通过本功能进行建设工程消防设计审核申报表申报</small></h2>
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
	                <button data-toggle="tooltip" data-placement="bottom" type="button savebutton"
	                        title="刷新" class="btn btn-default btn-icon m-r-5"
	                        onclick="refresh();">
	                    <i class="md md-autorenew"></i>
	                </button>
	            </li>
	        </ul>
    	</div>
		<form id="xfJsgcSjshsbbForm" action="${ctx}/xfJsgcSjshsbb/save" method="post">
    		<div class="card-body card-padding">
    			<table class="editTable">
					<tbody>
						<div id="baseOne">
							<tr>
								<td class="title" colspan="2">
									建设单位
								</td>
								<td>
									<input type="hidden" name="id" value="${formObject.id}" class="base">
		                			<input type="hidden" name="prjId" value="${formObject.prjId}"  class="base">
		                			<input type="hidden" name="taskId" value="${formObject.taskId}"  class="base">
									<input class=" base" maxlength="200" type="text" value="${formObject.company}" name="company"></input>
								</td>
								<td class="title">
									法定代表人/<br/>
									主要负责人
								</td>
								<td>
									<input class=" base" maxlength="50" type="text" value="${formObject.legalEntity}" name="legalEntity"></input>
								</td>
								<td class="title">
									联系电话
								</td>
								<td>
									<input class=" base phone" maxlength="15" type="text" value="${formObject.entityPhone}" name="entityPhone"></input>
								</td>	
							</tr>
							<tr>
								<td class="title" colspan="2">
									工程名称
								</td>
								<td>
									<input class="required base" maxlength="200" type="text" value="${formObject.prjName}" name="prjName"></input>
								</td>
								<td class="title">
									联系人
								</td>
								<td>
									<input class=" base" maxlength="50" type="text" value="${formObject.linkMan}" name="linkMan"></input>
								</td>
								<td class="title">
									联系电话
								</td>
								<td>
									<input class=" base phone" type="text" value="${formObject.linkPhone}" name="linkPhone"></input>
								</td>
							</tr>
							<tr>
								<td class="title" colspan="2">
									工程地址
								</td>
								<td>
									<input class=" base" maxlength="50" type="text" value="${formObject.prjAddr}" name="prjAddr"></input>
								</td>
								<td class="title">
									计划开工日期
								</td>
								<td>
									<div class="dtp-container dropdown">
										<input type="text" class="date-picker  base" data-toggle="dropdown" aria-expanded="false" value='<fmt:formatDate value="${formObject.planStartDate}" pattern="yyyy-MM-dd" />' name="planStartDate" />
									</div>
								</td>
								<td class="title">
									计划竣工日期
								</td>
								<td>
									<div class="dtp-container dropdown">
										<input type="text" class="date-picker  base" data-toggle="dropdown" aria-expanded="false" value='<fmt:formatDate value="${formObject.planEndDate}" pattern="yyyy-MM-dd" />' name="planEndDate" >
									</div>
								</td>	
							</tr>
							<tr>
								<td class="title" colspan="2">
									类&nbsp;&nbsp;别
								</td>
								<td class="text-center" colspan="5">
									<label class="checkbox checkbox-inline m-r-10">
										<input type="checkbox" class="base" value="A" name="type" <c:if test="${formObject.type.contains('A')}"> checked </c:if> />
										<i class="input-helper"></i>    
										新建
									</label>
									<label class="checkbox checkbox-inline m-r-10">
										<input type="checkbox" class="base" value="B" name="type" <c:if test="${formObject.type.contains('B')}"> checked </c:if> />
										<i class="input-helper"></i>    
										扩建
									</label>
									<label class="checkbox checkbox-inline m-r-10">
										<input type="checkbox" class="base" value="C" name="type" <c:if test="${formObject.type.contains('C')}"> checked </c:if> />
										<i class="input-helper"></i>    
										改建
									</label>
									（
									<label class="checkbox checkbox-inline m-r-10">
										<input type="checkbox" class="base" value="C1" name="type" <c:if test="${formObject.type.contains('C1')}"> checked </c:if> />
										<i class="input-helper"></i>    
										装修
									</label>
									<label class="checkbox checkbox-inline m-r-10">
										<input type="checkbox" class="base" value="C2" name="type" <c:if test="${formObject.type.contains('C2')}"> checked </c:if> />
										<i class="input-helper"></i>    
										建筑保温
									</label>
									<label class="checkbox checkbox-inline m-r-10">
										<input type="checkbox" class="base" value="C3" name="type" <c:if test="${formObject.type.contains('C3')}"> checked </c:if> />
										<i class="input-helper"></i>    
										改变用途
									</label>
									）
								</td>
							</tr>
							<tr>
								<td class="title" colspan="2">
									单位类别
								</td>
								<td class="title">
									单位名称
								</td>
								<td class="title">
									资质等级
								</td>
								<td class="title">
									法定代表人/<br/>
									主要负责人
								</td>
								<td class="title">
									联系人
								</td>
								<td class="title">
									联系电话
								</td>	
							</tr>
							<tr class="construction" name="unitTr">
								<td style="display: none;">
									<input type="hidden" name="unitId" value="${unit.unitId}" />
								</td>
								<td style="display: none;">
									<input type="hidden" name="unitType" value="设计单位" />
								</td>
								<td class="title" colspan="2">
									<input type="hidden" name="jId" value="${unit.jId}" />
									设计单位
								</td>
								<td>
									<input type="text"  maxlength="200" value="${unit.unitName}" name="unitName"></input>
								</td>
								<td>
									<input type="text"  maxlength="50" value="${unit.aptGra}" name="aptGra"></input>
								</td>
								<td>
									<input type="text"  maxlength="50" value="${unit.crincipal}" name="crincipal"></input>
								</td>
								<td>
									<input type="text"  maxlength="50" value="${unit.uLineMan}" name="uLineMan"></input>
								</td>
								<td>
									<input type="text" class=" phone" maxlength="15" value="${unit.uLinePhone}" name="uLinePhone"></input>
								</td>
							</tr>
							<tr class="construction" name="unitTr">
								<td style="display: none;">
									<input type="hidden" name="unitId" value="${unit.unitId}" />
								</td>
								<td style="display: none;">
									<input type="hidden" name="unitType" value="监理单位" />
								</td>
								<td class="title" colspan="2">
									<input type="hidden" name="jId" value="${unit.jId}" />
									监理单位
								</td>
								<td>
									<input type="text"  maxlength="200" value="${unit.unitName}" name="unitName"></input>
								</td>
								<td>
									<input type="text"  maxlength="50" value="${unit.aptGra}" name="aptGra"></input>
								</td>
								<td>
									<input type="text"  maxlength="50" value="${unit.crincipal}" name="crincipal"></input>
								</td>
								<td>
									<input type="text"  maxlength="50" value="${unit.uLineMan}" name="uLineMan"></input>
								</td>
								<td>
									<input type="text" class=" phone" maxlength="15" value="${unit.uLinePhone}" name="uLinePhone"></input>
								</td>
							</tr>
							<tr id="construction" class="construction" name="unitTr">
								<td style="display: none;">
									<input type="hidden" name="unitId" value="${unit.unitId}" />
								</td>
								<td style="display: none;">
									<input type="hidden" name="unitType" value="施工单位" />
								</td>
								<td class="title" colspan="2">
									<input type="hidden" name="jId" value="${unit.jId}" />
									施工单位
									<button type="button" onclick="addRow('construction')" class="btn btn-primary"><i class="md md-add"></i></button>
								</td>
								<td>
									<input type="text"  maxlength="200" value="${unit.unitName}" name="unitName"></input>
								</td>
								<td>
									<input type="text"  maxlength="50" value="${unit.aptGra}" name="aptGra"></input>
								</td>
								<td>
									<input type="text"  maxlength="50" value="${unit.crincipal}" name="crincipal"></input>
								</td>
								<td>
									<input type="text"  maxlength="50" value="${unit.uLineMan}" name="uLineMan"></input>
								</td>
								<td>
									<input type="text" class=" phone" maxlength="15" value="${unit.uLinePhone}" name="uLinePhone"></input>
								</td>
							</tr>
							<c:forEach items="${formObject.dwList}" var="unit" varStatus="i">
							<c:if test="${unit.unitType == '设计单位'}">
								<tr class="construction">
									<td style="display: none;">
										<input type="hidden" name="unitId" value="${unit.unitId}" />
									</td>
									<td style="display: none;">
										<input type="hidden" name="unitType" value="设计单位" />
									</td>
									<td class="title" colspan="2">
										<input type="hidden" name="jId" value="${unit.jId}" />
										设计单位
									</td>
									<td>
										<input type="text"  maxlength="200" value="${unit.unitName}" name="unitName"></input>
									</td>
									<td>
										<input type="text"  maxlength="50" value="${unit.aptGra}" name="aptGra"></input>
									</td>
									<td>
										<input type="text"  maxlength="50" value="${unit.crincipal}" name="crincipal"></input>
									</td>
									<td>
										<input type="text"  maxlength="50" value="${unit.uLineMan}" name="uLineMan"></input>
									</td>
									<td>
										<input type="text" class=" phone" maxlength="15" value="${unit.uLinePhone}" name="uLinePhone"></input>
									</td>
								</tr>
							</c:if>
							<c:if test="${unit.unitType == '监理单位'}">
								<tr class="construction">
									<td style="display: none;">
										<input type="hidden" name="unitId" value="${unit.unitId}" />
									</td>
									<td style="display: none;">
										<input type="hidden" name="unitType" value="监理单位" />
									</td>
									<td class="title" colspan="2">
										<input type="hidden" name="jId" value="${unit.jId}" />
										监理单位
									</td>
									<td>
										<input type="text"  maxlength="200" value="${unit.unitName}" name="unitName"></input>
									</td>
									<td>
										<input type="text"  maxlength="50" value="${unit.aptGra}" name="aptGra"></input>
									</td>
									<td>
										<input type="text"  maxlength="50" value="${unit.crincipal}" name="crincipal"></input>
									</td>
									<td>
										<input type="text"  maxlength="50" value="${unit.uLineMan}" name="uLineMan"></input>
									</td>
									<td>
										<input type="text" class=" phone" maxlength="15" value="${unit.uLinePhone}" name="uLinePhone"></input>
									</td>
								</tr>
							</c:if>
						<c:if test="${unit.unitType == '施工单位' && i.index == 2}">
						<tr id="construction" class="construction">
							<td style="display: none;">
								<input type="hidden" name="unitId" value="${unit.unitId}" />
							</td>
							<td style="display: none;">
								<input type="hidden" name="unitType" value="施工单位" />
							</td>
							<td class="title" colspan="2">
								<input type="hidden" name="jId" value="${unit.jId}" />
								施工单位
								<button type="button" onclick="addRow('construction')" class="btn btn-primary"><i class="md md-add"></i></button>
							</td>
							<td>
								<input type="text"  maxlength="200" value="${unit.unitName}" name="unitName"></input>
							</td>
							<td>
								<input type="text"  maxlength="50" value="${unit.aptGra}" name="aptGra"></input>
							</td>
							<td>
								<input type="text"  maxlength="50" value="${unit.crincipal}" name="crincipal"></input>
							</td>
							<td>
								<input type="text"  maxlength="50" value="${unit.uLineMan}" name="uLineMan"></input>
							</td>
							<td>
								<input type="text" class=" phone" maxlength="15" value="${unit.uLinePhone}" name="uLinePhone"></input>
							</td>
						</tr>
					</c:if>
			<c:if test="${unit.unitType == '施工单位' && i.index > 2}">
						<tr id="uni${unit.unitId}" class="construction">
							<td style="display: none;">
								<input type="hidden" name="unitId" value="${unit.unitId}" />
							</td>
							<td style="display: none;">
								<input type="hidden" name="unitType" value="施工单位" />
							</td>
							<td class="title" colspan="2">
								<input type="hidden" name="jId" value="${unit.jId}" />
								施工单位
								<button onclick="removeRow('uni${unit.unitId}')" class="btn btn-danger"><i class="md md-clear"></i></button>
							</td>
							<td>
								<input type="text"  maxlength="200" value="${unit.unitName}" name="unitName"></input>
							</td>
							<td>
								<input type="text"  maxlength="50" value="${unit.aptGra}" name="aptGra"></input>
							</td>
							<td>
								<input type="text"  maxlength="50" value="${unit.crincipal}" name="crincipal"></input>
							</td>
							<td>
								<input type="text"  maxlength="50" value="${unit.uLineMan}" name="uLineMan"></input>
							</td>
							<td>
								<input type="text" class=" phone" maxlength="15" value="${unit.uLinePhone}" name="uLinePhone"></input>
							</td>
						</tr>
					</c:if>
			</c:forEach>
						</div>
					<div id="baseTwo">
						<tr>
							<td class="title" style="width: 38px;padding: 6px 20px;" rowspan="3">
								使用性质
							</td>
							<td class="title" style="width: 38px;padding: 6px 20px;">
								大型的人员密集场所
							</td>
							<td class="content" colspan="5">
								<b>1．建筑总面积大于500㎡的</b>
								<br/>
								<label class="checkbox checkbox-inline m-r-10 m-l-25">
									<input type="checkbox" class="base" value="A1" name="useNature" <c:if test="${formObject.useNature.contains('A1')}"> checked </c:if> />
									<i class="input-helper"></i>    
									歌舞厅
								</label>
								<label class="checkbox checkbox-inline m-r-10">
									<input class="base" type="checkbox" value="A2" name="useNature" <c:if test="${formObject.useNature.contains('A2')}"> checked </c:if> />
									<i class="input-helper"></i>    
									录像厅
								</label>
								<label class="checkbox checkbox-inline m-r-10">
									<input class="base" type="checkbox" value="A3" name="useNature" <c:if test="${formObject.useNature.contains('A3')}"> checked </c:if> />
									<i class="input-helper"></i>    
									放映厅
								</label>
								<label class="checkbox checkbox-inline m-r-10">
									<input class="base" type="checkbox" value="A4" name="useNature" <c:if test="${formObject.useNature.contains('A4')}"> checked </c:if> />
									<i class="input-helper"></i>    
									卡拉OK厅
								</label>
								<br/>
								<label class="checkbox checkbox-inline m-r-10 m-l-25">
									<input class="base" type="checkbox" value="A5" name="useNature" <c:if test="${formObject.useNature.contains('A5')}"> checked </c:if> />
									<i class="input-helper"></i>    
									夜总会
								</label>
								<label class="checkbox checkbox-inline m-r-10">
									<input class="base" type="checkbox" value="A6" name="useNature" <c:if test="${formObject.useNature.contains('A6')}"> checked </c:if> />
									<i class="input-helper"></i>    
									游艺厅
								</label>
								<label class="checkbox checkbox-inline m-r-10">
									<input class="base" type="checkbox" value="A7" name="useNature" <c:if test="${formObject.useNature.contains('A7')}"> checked </c:if> />
									<i class="input-helper"></i>    
									桑拿浴室
								</label>
								<label class="checkbox checkbox-inline m-r-10">
									<input class="base" type="checkbox" value="A8" name="useNature" <c:if test="${formObject.useNature.contains('A8')}"> checked </c:if> />
									<i class="input-helper"></i>    
									网吧
								</label>
								<label class="checkbox checkbox-inline m-r-10">
									<input class="base" type="checkbox" value="A9" name="useNature" <c:if test="${formObject.useNature.contains('A9')}"> checked </c:if> />
									<i class="input-helper"></i>    
									酒吧
								</label>
								<br/>
								<label class="checkbox checkbox-inline m-r-10 m-l-25">
									<input class="base" type="checkbox" value="Aa1" name="useNature" <c:if test="${formObject.useNature.contains('Aa1')}"> checked </c:if> />
									<i class="input-helper"></i>    
									具有娱乐功能的餐馆
								</label>
								<label class="checkbox checkbox-inline m-r-10">
									<input class="base" type="checkbox" value="Aa2" name="useNature" <c:if test="${formObject.useNature.contains('Aa2')}"> checked </c:if> />
									<i class="input-helper"></i>    
									具有娱乐功能的茶馆
								</label>
								<label class="checkbox checkbox-inline m-r-10">
									<input class="base" type="checkbox" value="Aa3" name="useNature" <c:if test="${formObject.useNature.contains('Aa3')}"> checked </c:if> />
									<i class="input-helper"></i>    
									具有娱乐功能的咖啡厅
								</label>
								<br/>
								<br/>
								<b>2．建筑总面积大于1000㎡的</b>
								<br/>
								<label class="checkbox checkbox-inline m-r-10 m-l-25">
									<input class="base" type="checkbox" value="B1" name="useNature" <c:if test="${formObject.useNature.contains('B1')}"> checked </c:if> />
									<i class="input-helper"></i>    
									托儿所的儿童用房
								</label>
								<label class="checkbox checkbox-inline m-r-10">
									<input class="base" type="checkbox" value="B2" name="useNature" <c:if test="${formObject.useNature.contains('B2')}"> checked </c:if> />
									<i class="input-helper"></i>    
									幼儿园的儿童用房
								</label>
								<label class="checkbox checkbox-inline m-r-10">
									<input class="base" type="checkbox" value="B3" name="useNature" <c:if test="${formObject.useNature.contains('B3')}"> checked </c:if> />
									<i class="input-helper"></i>    
									儿童游乐厅 
								</label>
								<label class="checkbox checkbox-inline m-r-10">
									<input class="base" type="checkbox" value="B4" name="useNature" <c:if test="${formObject.useNature.contains('B4')}"> checked </c:if> />
									<i class="input-helper"></i>    
									其他室内儿童活动场所
								</label>
								<br/>
								<label class="checkbox checkbox-inline m-r-10 m-l-25">
									<input class="base" type="checkbox" value="B5" name="useNature" <c:if test="${formObject.useNature.contains('B5')}"> checked </c:if> />
									<i class="input-helper"></i>    
									养老院
								</label>
								<label class="checkbox checkbox-inline m-r-10">
									<input class="base" type="checkbox" value="B6" name="useNature" <c:if test="${formObject.useNature.contains('B6')}"> checked </c:if> />
									<i class="input-helper"></i>    
									福利院
								</label>
								<label class="checkbox checkbox-inline m-r-10">
									<input class="base" type="checkbox" value="B7" name="useNature" <c:if test="${formObject.useNature.contains('B7')}"> checked </c:if> />
									<i class="input-helper"></i>    
									医院的病房楼
								</label>
								<label class="checkbox checkbox-inline m-r-10">
									<input class="base" type="checkbox" value="B8" name="useNature" <c:if test="${formObject.useNature.contains('B8')}"> checked </c:if> />
									<i class="input-helper"></i>    
									疗养院的病房楼
								</label>
								<br/>
								<label class="checkbox checkbox-inline m-r-10 m-l-25">
									<input class="base" type="checkbox" value="B9" name="useNature" <c:if test="${formObject.useNature.contains('B9')}"> checked </c:if> />
									<i class="input-helper"></i>    
									中小学校的教学楼
								</label>
								<label class="checkbox checkbox-inline m-r-10">
									<input class="base" type="checkbox" value="Ba1" name="useNature" <c:if test="${formObject.useNature.contains('Ba1')}"> checked </c:if> />
									<i class="input-helper"></i>    
									中小学校的图书馆
								</label>
								<label class="checkbox checkbox-inline m-r-10">
									<input class="base" type="checkbox" value="Ba2" name="useNature" <c:if test="${formObject.useNature.contains('Ba2')}"> checked </c:if> />
									<i class="input-helper"></i>    
									中小学校的食堂
								</label>
								<label class="checkbox checkbox-inline m-r-10">
									<input class="base" type="checkbox" value="Ba3" name="useNature" <c:if test="${formObject.useNature.contains('Ba3')}"> checked </c:if> />
									<i class="input-helper"></i>    
									学校的集体宿舍
								</label>
								<br/>
								<label class="checkbox checkbox-inline m-r-10 m-l-25">
									<input class="base" type="checkbox" value="Ba4" name="useNature" <c:if test="${formObject.useNature.contains('Ba4')}"> checked </c:if> />
									<i class="input-helper"></i>    
									劳动密集型企业的员工集体宿舍
								</label>
								<br/>
								<br/>
								<b>3．建筑总面积大于2500㎡的</b>
								<br/>
								<label class="checkbox checkbox-inline m-r-10 m-l-25">
									<input class="base" type="checkbox" value="C1" name="useNature" <c:if test="${formObject.useNature.contains('C1')}"> checked </c:if> />
									<i class="input-helper"></i>    
									影剧院
								</label>
								<label class="checkbox checkbox-inline m-r-10">
									<input class="base" type="checkbox" value="C2" name="useNature" <c:if test="${formObject.useNature.contains('C2')}"> checked </c:if> />
									<i class="input-helper"></i>    
									公共图书馆的阅览室
								</label>
								<br/>
								<label class="checkbox checkbox-inline m-r-10 m-l-25">
									<input class="base" type="checkbox" value="C3" name="useNature" <c:if test="${formObject.useNature.contains('C3')}"> checked </c:if> />
									<i class="input-helper"></i>    
									营业性室内健身场馆
								</label>
								<label class="checkbox checkbox-inline m-r-10">
									<input class="base" type="checkbox" value="C4" name="useNature" <c:if test="${formObject.useNature.contains('C4')}"> checked </c:if> />
									<i class="input-helper"></i>    
									营业性室内休闲场馆
								</label>
								<br/>
								 <label class="checkbox checkbox-inline m-r-10 m-l-25">
									<input class="base" type="checkbox" value="C5" name="useNature" <c:if test="${formObject.useNature.contains('C5')}"> checked </c:if> />
									<i class="input-helper"></i>    
									医院的门诊楼
								</label>
								<label class="checkbox checkbox-inline m-r-10">
									<input class="base" type="checkbox" value="C6" name="useNature" <c:if test="${formObject.useNature.contains('C6')}"> checked </c:if> />
									<i class="input-helper"></i>    
									大学的教学楼
								</label>
								<label class="checkbox checkbox-inline m-r-10">
									<input class="base" type="checkbox" value="C7" name="useNature" <c:if test="${formObject.useNature.contains('C7')}"> checked </c:if> />
									<i class="input-helper"></i>    
									大学的图书馆
								</label>
								<label class="checkbox checkbox-inline m-r-10">
									<input class="base" type="checkbox" value="C8" name="useNature" <c:if test="${formObject.useNature.contains('C8')}"> checked </c:if> />
									<i class="input-helper"></i>    
									大学的食堂
								</label>
								<br/>
								<label class="checkbox checkbox-inline m-r-10 m-l-25">
									<input class="base" type="checkbox" value="C9" name="useNature" <c:if test="${formObject.useNature.contains('C9')}"> checked </c:if> />
									<i class="input-helper"></i>    
									劳动密集型企业的生产加工车间
								</label>
								<label class="checkbox checkbox-inline m-r-10">
									<input class="base" type="checkbox" value="Ca1" name="useNature" <c:if test="${formObject.useNature.contains('Ca1')}"> checked </c:if> />
									<i class="input-helper"></i>    
									寺庙
								</label>
								<label class="checkbox checkbox-inline m-r-10">
									<input class="base" type="checkbox" value="Ca2" name="useNature" <c:if test="${formObject.useNature.contains('Ca2')}"> checked </c:if> />
									<i class="input-helper"></i>    
									教堂
								</label>
								<br/>
								<br/>
								<b>4．建筑总面积大于10000㎡的</b>
								<br/>
								<label class="checkbox checkbox-inline m-r-10 m-l-25">
									<input class="base" type="checkbox" value="D1" name="useNature" <c:if test="${formObject.useNature.contains('D1')}"> checked </c:if> />
									<i class="input-helper"></i>    
									宾馆
								</label>
								<label class="checkbox checkbox-inline m-r-10">
									<input class="base" type="checkbox" value="D2" name="useNature" <c:if test="${formObject.useNature.contains('D2')}"> checked </c:if> />
									<i class="input-helper"></i>    
									饭店
								</label>
								<label class="checkbox checkbox-inline m-r-10">
									<input class="base" type="checkbox" value="D3" name="useNature" <c:if test="${formObject.useNature.contains('D3')}"> checked </c:if> />
									<i class="input-helper"></i>    
									商场
								</label>
								<label class="checkbox checkbox-inline m-r-10">
									<input class="base" type="checkbox" value="D4" name="useNature" <c:if test="${formObject.useNature.contains('D4')}"> checked </c:if> />
									<i class="input-helper"></i>    
									市场
								</label>
								<br/>
								<br/>
								<b>5．建筑总面积大于15000㎡的</b>
								<br/>
								<label class="checkbox checkbox-inline m-r-10 m-l-25">
									<input class="base" type="checkbox" value="E1" name="useNature" <c:if test="${formObject.useNature.contains('E1')}"> checked </c:if> />
									<i class="input-helper"></i>    
									民用机场航站楼
								</label>
								<label class="checkbox checkbox-inline m-r-10">
									<input class="base" type="checkbox" value="E2" name="useNature" <c:if test="${formObject.useNature.contains('E2')}"> checked </c:if> />
									<i class="input-helper"></i>    
									客运车站候车室
								</label>
								<label class="checkbox checkbox-inline m-r-10">
									<input class="base" type="checkbox" value="E3" name="useNature" <c:if test="${formObject.useNature.contains('E3')}"> checked </c:if> />
									<i class="input-helper"></i>    
									客运码头候船厅
								</label>
								<br/>
								<br/>
								<b>6．建筑总面积大于20000㎡的</b>
								<br/>
								<label class="checkbox checkbox-inline m-r-10 m-l-25">
									<input class="base" type="checkbox" value="F1" name="useNature" <c:if test="${formObject.useNature.contains('F1')}"> checked </c:if> />
									<i class="input-helper"></i>    
									体育场馆
								</label>
								<label class="checkbox checkbox-inline m-r-10">
									<input class="base" type="checkbox" value="F2" name="useNature" <c:if test="${formObject.useNature.contains('F2')}"> checked </c:if> />
									<i class="input-helper"></i>    
									会堂
								</label>
								<label class="checkbox checkbox-inline m-r-10">
									<input class="base" type="checkbox" value="F3" name="useNature" <c:if test="${formObject.useNature.contains('F3')}"> checked </c:if> />
									<i class="input-helper"></i>    
									公共展览馆的展示厅
								</label>
								<label class="checkbox checkbox-inline m-r-10">
									<input class="base" type="checkbox" value="F4" name="useNature" <c:if test="${formObject.useNature.contains('F4')}"> checked </c:if> />
									<i class="input-helper"></i>    
									博物馆的展示厅
								</label>
								
								
							</td>
						<tr>
						<tr>
							<td class="title" style="width: 38px;padding: 6px 20px;">
										其他特殊工程
									</td>
									<td class="content" colspan="5">
										<b>1．</b>
										<label class="checkbox checkbox-inline">
											<input class="base" type="checkbox" value="G1" name="useNature" <c:if test="${formObject.useNature.contains('G1')}"> checked </c:if> />
											<i class="input-helper"></i>    
											设有上栏所列大型的人员密集场所的建设工程
										</label>
										<br/>
										<br/>
										<b>2．</b>
										<label class="checkbox checkbox-inline m-r-10">
											<input class="base" type="checkbox" value="H1" name="useNature" <c:if test="${formObject.useNature.contains('H1')}"> checked </c:if> />
											<i class="input-helper"></i>    
											国家机关办公楼
										</label>
										<label class="checkbox checkbox-inline m-r-10">
											<input class="base" type="checkbox" value="H2" name="useNature" <c:if test="${formObject.useNature.contains('H2')}"> checked </c:if> />
											<i class="input-helper"></i>    
											电力调度楼
										</label>
										<label class="checkbox checkbox-inline m-r-10">
											<input class="base" type="checkbox" value="H3" name="useNature" <c:if test="${formObject.useNature.contains('H3')}"> checked </c:if> />
											<i class="input-helper"></i>    
											电信楼
										</label>
										<label class="checkbox checkbox-inline m-r-10">
											<input class="base" type="checkbox" value="H4" name="useNature" <c:if test="${formObject.useNature.contains('H4')}"> checked </c:if> />
											<i class="input-helper"></i>    
											邮政楼
										</label>
										<label class="checkbox checkbox-inline m-r-10">
											<input class="base" type="checkbox" value="H5" name="useNature" <c:if test="${formObject.useNature.contains('H5')}"> checked </c:if> />
											<i class="input-helper"></i>    
											防灾指挥调度楼
										</label>
										<label class="checkbox checkbox-inline m-r-10">
											<input class="base" type="checkbox" value="H6" name="useNature" <c:if test="${formObject.useNature.contains('H6')}"> checked </c:if> />
											<i class="input-helper"></i>    
											广播电视楼
										</label>
										<label class="checkbox checkbox-inline m-r-10">
											<input class="base" type="checkbox" value="H7" name="useNature" <c:if test="${formObject.useNature.contains('H7')}"> checked </c:if> />
											<i class="input-helper"></i>    
											档案楼
										</label>
										<br/>
										<br/>
										<b>3．除本栏第1项、第2项以外的</b>
										<label class="checkbox checkbox-inline m-r-10 m-l-15">
											<input class="base" type="checkbox" value="I1" name="useNature" <c:if test="${formObject.useNature.contains('I1')}"> checked </c:if> />
											<i class="input-helper"></i>    
											单体建筑面积大于40000㎡的公共建筑
										</label>
										<label class="checkbox checkbox-inline m-r-10">
											<input class="base" type="checkbox" value="I2" name="useNature" <c:if test="${formObject.useNature.contains('I2')}"> checked </c:if> />
											<i class="input-helper"></i>    
											建筑高度超过50m的公共建筑
										</label>
										<br/>
										<br/>
										<b>4．</b>
										<label class="checkbox checkbox-inline m-r-10">
											<input class="base" type="checkbox" value="J1" name="useNature" <c:if test="${formObject.useNature.contains('J1')}"> checked </c:if> />
											<i class="input-helper"></i>    
											国家标准规定的一类高层住宅建筑
										</label>
										<br/>
										<br/>
										<b>5．</b>
										<label class="checkbox checkbox-inline m-r-10">
											<input class="base" type="checkbox" value="K1" name="useNature" <c:if test="${formObject.useNature.contains('K1')}"> checked </c:if> />
											<i class="input-helper"></i>    
											城市轨道交通工程
										</label>
										<label class="checkbox checkbox-inline m-r-10">
											<input class="base" type="checkbox" value="K2" name="useNature" <c:if test="${formObject.useNature.contains('K2')}"> checked </c:if> />
											<i class="input-helper"></i>    
											城市隧道工程
										</label>
										<label class="checkbox checkbox-inline m-r-10">
											<input class="base" type="checkbox" value="K3" name="useNature" <c:if test="${formObject.useNature.contains('K3')}"> checked </c:if> />
											<i class="input-helper"></i>    
											大型发电工程
										</label>
										<label class="checkbox checkbox-inline m-r-10">
											<input class="base" type="checkbox" value="K4" name="useNature" <c:if test="${formObject.useNature.contains('K4')}"> checked </c:if> />
											<i class="input-helper"></i>    
											大型变配电工程
										</label>
										<br/>
										<br/>
										<b>6．</b><label>生产、储存易燃易爆危险物品的</label>
										<label class="checkbox checkbox-inline m-r-10 m-l-15">
											<input class="base" type="checkbox" value="L1" name="useNature" <c:if test="${formObject.useNature.contains('L1')}"> checked </c:if> />
											<i class="input-helper"></i>    
											工厂
										</label>
										<label class="checkbox checkbox-inline m-r-10">
											<input class="base" type="checkbox" value="L2" name="useNature" <c:if test="${formObject.useNature.contains('L2')}"> checked </c:if> />
											<i class="input-helper"></i>    
											仓库
										</label>
										<br/>
										<label class="m-l-20">装卸易燃易爆危险物品的</label>
										<label class="checkbox checkbox-inline m-r-10 m-l-15">
											<input class="base" type="checkbox" value="L3" name="useNature" <c:if test="${formObject.useNature.contains('L3')}"> checked </c:if> />
											<i class="input-helper"></i>    
											专用车站
										</label>
										<label class="checkbox checkbox-inline m-r-10">
											<input class="base" type="checkbox" value="L4" name="useNature" <c:if test="${formObject.useNature.contains('L4')}"> checked </c:if> />
											<i class="input-helper"></i>    
											专用码头
										</label>
										<br/>
										<label class="m-l-20">易燃易爆气体的</label>
										<label class="checkbox checkbox-inline m-r-10 m-l-15">
											<input class="base" type="checkbox" value="L5" name="useNature" <c:if test="${formObject.useNature.contains('L5')}"> checked </c:if> />
											<i class="input-helper"></i>    
											充装站
										</label>
										<label class="checkbox checkbox-inline m-r-10">
											<input class="base" type="checkbox" value="L6" name="useNature" <c:if test="${formObject.useNature.contains('L6')}"> checked </c:if> />
											<i class="input-helper"></i>    
											供应站
										</label>
										<label class="checkbox checkbox-inline m-r-10">
											<input class="base" type="checkbox" value="L7" name="useNature" <c:if test="${formObject.useNature.contains('L7')}"> checked </c:if> />
											<i class="input-helper"></i>    
											调压站
										</label>
										<br/>
										<label class="m-l-20">易燃易爆液体的</label>
										<label class="checkbox checkbox-inline m-r-10 m-l-15">
											<input class="base" type="checkbox" value="L8" name="useNature" <c:if test="${formObject.useNature.contains('L8')}"> checked </c:if> />
											<i class="input-helper"></i>    
											充装站
										</label>
										<label class="checkbox checkbox-inline m-r-10">
											<input class="base" type="checkbox" value="L9" name="useNature" <c:if test="${formObject.useNature.contains('L9')}"> checked </c:if> />
											<i class="input-helper"></i>    
											供应站
										</label>
										<label class="checkbox checkbox-inline m-r-10">
											<input class="base" type="checkbox" value="La1" name="useNature" <c:if test="${formObject.useNature.contains('La1')}"> checked </c:if> />
											<i class="input-helper"></i>    
											调压站
										</label>
									</td>
								<tr>
					</div>
				</tbody>
			</table>
    		</div>
    		<div class="card-body card-padding">
    			<table class="editTable">
					<tr>
						<td class="title" style="width: 200px;" colspan="2" rowspan="2">
					单体建筑名称
				</td>
				<td class="title" rowspan="2">
					结构类型
				</td>
				<td class="title" rowspan="2">
					耐火等级
				</td>
				<td class="title" colspan="2">
					层&nbsp;&nbsp;数
				</td>
				<td class="title" rowspan="2">
					建筑高度（m）
				</td>
				<td class="title" rowspan="2">
					占地面积（㎡）
				</td>
				<td class="title" colspan="2">
					建筑面积（㎡）
				</td>
			</tr>
			<tr>
				<td class="title">
					地上
				</td>
				<td class="title">
					地下
				</td>
				<td class="title">
					地上
				</td>
				<td class="title">
					地下
				</td>
			</tr>
			<tr id="building" class="building" name="buildTr" >
				<td style="display: none;">
					<input type="hidden" name="buildId" value="${build.buildId}" />
				</td>
				<td>
					<button type="button" onclick="addRow('building')" class="btn btn-primary">
						<i class="md md-add"></i>
					</button>
					<input type="hidden" name="jId" value="${build.jId}" />
				</td>
				<td>
					<input type="text" name="buildName" maxlength="200"  value="${build.buildName}" />
				</td>
				<td>
					<input type="text" name="strType" maxlength="50"  value="${build.strType}" />
				</td>
				<td>
					<input type="text" name="fireResRat" maxlength="50"  value="${build.fireResRat}" />
				</td>
				<td>
					<input type="text" name="onFloor" maxlength="50"  value="${build.onFloor}" />
				</td>
				<td>
					<input type="text" name="downFloor" maxlength="50"  value="${build.downFloor}" />
				</td>
				<td>
					<input type="text" name="buildHigh" maxlength="50"  value="${build.buildHigh}" />
				</td>
				<td>
					<input type="text" name="floorSpace" maxlength="50"  value="${build.floorSpace}" />
				</td>
				<td>
					<input type="text" name="onBuildSpace" maxlength="50"  value="${build.onBuildSpace}" />
				</td>
				<td>
					<input type="text" name="downBuildSpace" maxlength="50"  value="${build.downBuildSpace}" />
				</td>
			</tr>
			<div id="buildDiv">
				<c:forEach items="${formObject.jzList}" var="build" varStatus="i">
					<c:if test="${i.index == 0}">
						<tr id="building" class="building" >
							<td style="display: none;">
								<input type="hidden" name="buildId" value="${build.buildId}" />
							</td>
							<td>
								<button type="button" onclick="addRow('building')" class="btn btn-primary">
									<i class="md md-add"></i>
								</button>
								<input type="hidden" name="jId" value="${build.jId}" />
							</td>
							<td>
								<input type="text" name="buildName" maxlength="200"  value="${build.buildName}" />
							</td>
							<td>
								<input type="text" name="strType" maxlength="50"  value="${build.strType}" />
							</td>
							<td>
								<input type="text" name="fireResRat" maxlength="50"  value="${build.fireResRat}" />
							</td>
							<td>
								<input type="text" name="onFloor" maxlength="50"  value="${build.onFloor}" />
							</td>
							<td>
								<input type="text" name="downFloor" maxlength="50"  value="${build.downFloor}" />
							</td>
							<td>
								<input type="text" name="buildHigh" maxlength="50"  value="${build.buildHigh}" />
							</td>
							<td>
								<input type="text" name="floorSpace" maxlength="50"  value="${build.floorSpace}" />
							</td>
							<td>
								<input type="text" name="onBuildSpace" maxlength="50"  value="${build.onBuildSpace}" />
							</td>
							<td>
								<input type="text" name="downBuildSpace" maxlength="50"  value="${build.downBuildSpace}" />
							</td>
						</tr>
					</c:if>
					<c:if test="${i.index > 0}">
						<tr id="bui${build.buildId}" class="building" >
							<td style="display: none;">
								<input type="hidden" name="buildId" value="${build.buildId}" />
							</td>
							<td>
								<button onclick="removeRow('bui${build.buildId}')" class="btn btn-danger">
									<i class="md md-clear"></i>
								</button>
								<input type="hidden" name="jId" value="${build.jId}" />
							</td>
							<td>
								<input type="text" name="buildName" maxlength="200"  value="${build.buildName}" />
							</td>
							<td>
								<input type="text" name="strType" maxlength="50"  value="${build.strType}" />
							</td>
							<td>
								<input type="text" name="fireResRat" maxlength="50"  value="${build.fireResRat}" />
							</td>
							<td>
								<input type="text" name="onFloor" maxlength="50"  value="${build.onFloor}" />
							</td>
							<td>
								<input type="text" name="downFloor" maxlength="50"  value="${build.downFloor}" />
							</td>
							<td>
								<input type="text" name="buildHigh" maxlength="50"  value="${build.buildHigh}" />
							</td>
							<td>
								<input type="text" name="floorSpace" maxlength="50"  value="${build.floorSpace}" />
							</td>
							<td>
								<input type="text" name="onBuildSpace" maxlength="50"  value="${build.onBuildSpace}" />
							</td>
							<td>
								<input type="text" name="downBuildSpace" maxlength="50"  value="${build.downBuildSpace}" />
							</td>
						</tr>
					</c:if>
				</c:forEach>
			</div>
					<div id="baseThree">
						<tr>
				<td class="title" rowspan="3">
					储罐
				</td>
				<td class="title">
					设置位置
				</td>
				<td colspan="5">
					<input type="text" class=" base" maxlength="50" value="${formObject.setPosition}" name="setPosition"></input>
				</td>
				<td class="title" colspan="2">
					总容量（m³）
				</td>
				<td>
					<input type="text" class=" base" maxlength="50" value="${formObject.volumen}" name="volumen"></input>
				</td>
			</tr>
			<tr>
				<td class="title">
					设置型式
				</td>
				<td class="content" colspan="8">
					浮顶罐
					（
					<label class="checkbox checkbox-inline m-r-10">
						<input class="base" type="checkbox" value="A1" name="setFormat" <c:if test="${formObject.setFormat.contains('A1')}"> checked </c:if> />
						<i class="input-helper"></i>    
						外
					</label>
					<label class="checkbox checkbox-inline">
						<input class="base" type="checkbox" value="A2" name="setFormat" <c:if test="${formObject.setFormat.contains('A2')}"> checked </c:if> />
						<i class="input-helper"></i>    
						内
					</label>
					）
					<label class="checkbox checkbox-inline m-r-10">
						<input class="base" type="checkbox" value="B" name="setFormat" <c:if test="${formObject.setFormat.contains('B')}"> checked </c:if> />
						<i class="input-helper"></i>    
						固定顶罐
					</label>
					<label class="checkbox checkbox-inline m-r-10">
						<input class="base" type="checkbox" value="C" name="setFormat" <c:if test="${formObject.setFormat.contains('C')}"> checked </c:if> />
						<i class="input-helper"></i>    
						卧式罐
					</label>
					<br/>
					球形罐
					（
					<label class="checkbox checkbox-inline m-r-10">
						<input class="base" type="checkbox" value="D1" name="setFormat" <c:if test="${formObject.setFormat.contains('D1')}"> checked </c:if> />
						<i class="input-helper"></i>    
						液体
					</label>
					<label class="checkbox checkbox-inline">
						<input class="base" type="checkbox" value="D2" name="setFormat" <c:if test="${formObject.setFormat.contains('D2')}"> checked </c:if> />
						<i class="input-helper"></i>    
						气体
					</label>
					）
					可燃气体储罐
					（
					  
					<label class="checkbox checkbox-inline m-r-10">
						<input class="base" type="checkbox" value="E1" name="setFormat" <c:if test="${formObject.setFormat.contains('E1')}"> checked </c:if> />
						<i class="input-helper"></i>    
						干式
					</label>
					<label class="checkbox checkbox-inline">
						<input class="base" type="checkbox" value="E2" name="setFormat" <c:if test="${formObject.setFormat.contains('E2')}"> checked </c:if> />
						<i class="input-helper"></i>    
						湿式
					</label>
					）
					<label class="checkbox checkbox-inline m-r-10">
						<input class="base" type="checkbox" value="F" name="setFormat" <c:if test="${formObject.setFormat.contains('F')}"> checked </c:if> />
						<i class="input-helper"></i>    
						其他
					</label>
				</td>
			</tr>
			<tr>
				<td class="title">
					储存形式
				</td>
				<td class="content" colspan="3">
					<label class="checkbox checkbox-inline m-r-10">
						<input class="base" type="checkbox" value="A" name="storeFormat" <c:if test="${formObject.storeFormat.contains('A')}"> checked </c:if> />
						<i class="input-helper"></i>    
						地上
					</label>
					<label class="checkbox checkbox-inline m-r-10">
						<input class="base" type="checkbox" value="B" name="storeFormat" <c:if test="${formObject.storeFormat.contains('B')}"> checked </c:if> />
						<i class="input-helper"></i>    
						半地下
					</label>
					<label class="checkbox checkbox-inline m-r-10">
						<input class="base" type="checkbox" value="C" name="storeFormat" <c:if test="${formObject.storeFormat.contains('C')}"> checked </c:if> />
						<i class="input-helper"></i>    
						地下
					</label>
				</td>
				<td class="title" colspan="2">
					储存物质名称
				</td>
				<td colspan="3">
					<input type="text" maxlength="50" class=" base" value="${formObject.storeSubName}" name="storeSubName"></input>
				</td>
			</tr>
			<tr>
				<td class="title">
					堆场
				</td>
				<td class="title">
					储&nbsp;&nbsp;量
				</td>
				<td colspan="5">
					<input type="text" maxlength="50" class=" base" value="${formObject.proved}" name="proved" />
				</td>
				<td class="title" colspan="2">
					储存物质名称
				</td>
				<td>
					<input type="text" maxlength="50" class=" base" value="${formObject.storeSubNameYard}" name="storeSubNameYard"></input>
				</td>
			</tr>
			<tr>
				<td class="content text-center" colspan="2" rowspan="2">
					<label class="checkbox checkbox-inline">
						<input class="base" type="checkbox" value="1" name="buildInsu" <c:if test="${formObject.buildInsu.contains('1')}"> checked </c:if> />
						<i class="input-helper"></i>    
						建筑保温
					</label>
				</td>
				<td class="title">
					材料类别
				</td>
				<td class="content" colspan="4">
					<label class="checkbox checkbox-inline m-r-10">
						<input class="base" type="checkbox" value="A" name="insuMaterial" <c:if test="${formObject.insuMaterial.contains('A')}"> checked </c:if> />
						<i class="input-helper"></i>    
						A
					</label>
					<label class="checkbox checkbox-inline m-r-10">
						<input class="base" type="checkbox" value="B" name="insuMaterial" <c:if test="${formObject.insuMaterial.contains('B')}"> checked </c:if> />
						<i class="input-helper"></i>    
						B<sub>1</sub>
					</label>
					<label class="checkbox checkbox-inline m-r-10">
						<input class="base" type="checkbox" value="C" name="insuMaterial" <c:if test="${formObject.insuMaterial.contains('C')}"> checked </c:if> />
						<i class="input-helper"></i>    
						B<sub>2</sub>
					</label>
				</td>
				<td class="title">
					保温层数
				</td>
				<td colspan="2">
					<input type="text" maxlength="50" class=" base" value="${formObject.insuFloor}" name="insuFloor"></input>
				</td>
			</tr>
			<tr>
				<td class="title">
					使用性质
				</td>
				<td colspan="4">
					<input type="text" maxlength="50" class=" base" value="${formObject.insuUseNature}" name="insuUseNature" />
				</td>
				<td class="title">
					原有用途
				</td>
				<td colspan="2">
					<input type="text" maxlength="50" class=" base" value="${formObject.insuUriUse}" name="insuUriUse" />
				</td>
			</tr>
			<tr>
				<td class="content text-center" colspan="2" rowspan="3">
					<label class="checkbox checkbox-inline">
						<input class="base" type="checkbox" value="1" name="fitmentPrj" <c:if test="${formObject.fitmentPrj.contains('1')}"> checked </c:if> />
						<i class="input-helper"></i>    
						装修工程
					</label>
				</td>
				<td class="title">
					装修部位
				</td>
				<td class="content" colspan="7">
					<label class="checkbox checkbox-inline m-r-10">
						<input class="base" type="checkbox" value="A" name="fitmentSit" <c:if test="${formObject.fitmentSit.contains('A')}"> checked </c:if> />
						<i class="input-helper"></i>    
						顶棚
					</label>
					<label class="checkbox checkbox-inline m-r-10">
						<input class="base" type="checkbox" value="B" name="fitmentSit" <c:if test="${formObject.fitmentSit.contains('B')}"> checked </c:if> />
						<i class="input-helper"></i>    
						墙面
					</label>
					<label class="checkbox checkbox-inline m-r-10">
						<input class="base" type="checkbox" value="C" name="fitmentSit" <c:if test="${formObject.fitmentSit.contains('C')}"> checked </c:if> />
						<i class="input-helper"></i>    
						地面
					</label>
					<label class="checkbox checkbox-inline m-r-10">
						<input class="base" type="checkbox" value="D" name="fitmentSit" <c:if test="${formObject.fitmentSit.contains('D')}"> checked </c:if> />
						<i class="input-helper"></i>    
						隔断
					</label>
					<label class="checkbox checkbox-inline m-r-10">
						<input class="base" type="checkbox" value="E" name="fitmentSit" <c:if test="${formObject.fitmentSit.contains('E')}"> checked </c:if> />
						<i class="input-helper"></i>    
						固定家具
					</label>
					<label class="checkbox checkbox-inline m-r-10">
						<input class="base" type="checkbox" value="F" name="fitmentSit" <c:if test="${formObject.fitmentSit.contains('F')}"> checked </c:if> />
						<i class="input-helper"></i>    
						装饰织物
					</label>
					<label class="checkbox checkbox-inline m-r-10">
						<input class="base" type="checkbox" value="G" name="fitmentSit" <c:if test="${formObject.fitmentSit.contains('G')}"> checked </c:if> />
						<i class="input-helper"></i>    
						其他
					</label>
				</td>
			</tr>
			<tr>
				<td class="title">
					装修面积（㎡）
				</td>
				<td colspan="4">
					<input type="text" maxlength="50" class=" base" value="${formObject.fitmentSpace}" name="fitmentSpace" />
				</td>
				<td class="title">
					装修层数
				</td>
				<td colspan="2">
					<input type="text" maxlength="50" class=" base" value="${formObject.fitmentFloor}" name="fitmentFloor" />
				</td>
			</tr>
			<tr>
				<td class="title">
					使用性质
				</td>
				<td colspan="4">
					<input type="text" maxlength="50" class=" base" value="${formObject.fitmentUseNature}" name="fitmentUseNature" />
				</td>
				<td class="title">
					原有用途
				</td>
				<td colspan="2">
					<input type="text" maxlength="50" class=" base" value="${formObject.fitmentUriUse}" name="fitmentUriUse" />
				</td>
			</tr>
			<tr>
				<td class="title">
					消防<br/>设施
				</td>
				<td class="content" colspan="9">
					<label class="checkbox checkbox-inline m-r-10">
						<input class="base" type="checkbox" value="A" name="fireConFac" <c:if test="${formObject.fireConFac.contains('A')}"> checked </c:if> />
						<i class="input-helper"></i>    
						室内消火栓系统
					</label>
					<label class="checkbox checkbox-inline m-r-10">
						<input class="base" type="checkbox" value="B" name="fireConFac" <c:if test="${formObject.fireConFac.contains('B')}"> checked </c:if> />
						<i class="input-helper"></i>    
						室外消火栓系统
					</label>
					<label class="checkbox checkbox-inline m-r-10">
						<input class="base" type="checkbox" value="C" name="fireConFac" <c:if test="${formObject.fireConFac.contains('C')}"> checked </c:if> />
						<i class="input-helper"></i>    
						火灾自动报警系统
					</label>
					<label class="checkbox checkbox-inline m-r-10">
						<input class="base" type="checkbox" value="D" name="fireConFac" <c:if test="${formObject.fireConFac.contains('D')}"> checked </c:if> />
						<i class="input-helper"></i>    
						自动喷水灭火系统
					</label>
					<br/>
					<label class="checkbox checkbox-inline m-r-10">
						<input class="base" type="checkbox" value="E" name="fireConFac" <c:if test="${formObject.fireConFac.contains('E')}"> checked </c:if> />
						<i class="input-helper"></i>    
						气体灭火系统&nbsp;&nbsp;&nbsp;&nbsp;
					</label>
					<label class="checkbox checkbox-inline m-r-10">
						<input class="base" type="checkbox" value="F" name="fireConFac" <c:if test="${formObject.fireConFac.contains('F')}"> checked </c:if> />
						<i class="input-helper"></i>    
						泡沫灭火系统&nbsp;&nbsp;&nbsp;&nbsp;
					</label>
					<label class="checkbox checkbox-inline m-r-10">
						<input class="base" type="checkbox" value="G" name="fireConFac" <c:if test="${formObject.fireConFac.contains('G')}"> checked </c:if> />
						<i class="input-helper"></i>    
						其他灭火系统
					</label>
					<br/>
					<label class="checkbox checkbox-inline m-r-10">
						<input class="base" type="checkbox" value="H" name="fireConFac" <c:if test="${formObject.fireConFac.contains('H')}"> checked </c:if> />
						<i class="input-helper"></i>    
						疏散指示标志&nbsp;&nbsp;&nbsp;&nbsp;
					</label>
					<label class="checkbox checkbox-inline m-r-10">
						<input class="base" type="checkbox" value="I" name="fireConFac" <c:if test="${formObject.fireConFac.contains('I')}"> checked </c:if> />
						<i class="input-helper"></i>    
						消防应急照明&nbsp;&nbsp;&nbsp;&nbsp;
					</label>
					<label class="checkbox checkbox-inline m-r-10">
						<input class="base" type="checkbox" value="J" name="fireConFac" <c:if test="${formObject.fireConFac.contains('J')}"> checked </c:if> />
						<i class="input-helper"></i>    
						防烟排烟系统&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					</label>
					<label class="checkbox checkbox-inline m-r-10">
						<input class="base" type="checkbox" value="K" name="fireConFac" <c:if test="${formObject.fireConFac.contains('K')}"> checked </c:if> />
						<i class="input-helper"></i>    
						消防电梯
					</label> 
					<br/>
					<label class="checkbox checkbox-inline m-r-10">
						<input class="base" type="checkbox" value="L" name="fireConFac" <c:if test="${formObject.fireConFac.contains('L')}"> checked </c:if> />
						<i class="input-helper"></i>    
						灭火器&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					</label>
					<label class="checkbox checkbox-inline m-r-10">
						<input class="base" type="checkbox" value="M" name="fireConFac" <c:if test="${formObject.fireConFac.contains('M')}"> checked </c:if> />
						<i class="input-helper"></i>    
						其他：
					</label>
					<input class="base" name="otherStep" value="${formObject.otherStep}" style="width:100px;height: auto;border-bottom: 1px solid #ccc;" type="text"></input>
				</td>
			</tr>
			<tr>
				<td class="content" colspan="10">
					<b>同时提交的材料：</b>
					<br/>
					<br/>
					<label class="checkbox checkbox-inline m-l-25">
						<input class="base" type="checkbox" value="A" name="meanwhileSubMat" <c:if test="${formObject.meanwhileSubMat.contains('A')}"> checked </c:if> />
						<i class="input-helper"></i>
					</label> 
					1．建设单位的工商营业执照等合法身份证明文件；
					<br/>
					<label class="checkbox checkbox-inline m-l-25">
						<input class="base" type="checkbox" value="B" name="meanwhileSubMat" <c:if test="${formObject.meanwhileSubMat.contains('B')}"> checked </c:if> />
						<i class="input-helper"></i>
					</label> 
					2．设计单位资质证明文件；
					<br/>
					<label class="checkbox checkbox-inline m-l-25">
						<input class="base" type="checkbox" value="C" name="meanwhileSubMat" <c:if test="${formObject.meanwhileSubMat.contains('C')}"> checked </c:if> />
						<i class="input-helper"></i>
					</label> 
					3．消防设计文件，数量：<input maxlength="50" class="base" style="width:50px;height: auto;border-bottom: 1px solid #ccc;" type="text" value="${formObject.fireMaterial}" name="fireMaterial" />份（大写）；
					<br/>
					<label class="checkbox checkbox-inline m-l-25">
						<input class="base" type="checkbox" value="D" name="meanwhileSubMat" <c:if test="${formObject.meanwhileSubMat.contains('D')}"> checked </c:if> />
						<i class="input-helper"></i>
					</label> 
					4．专家评审申报材料（特殊消防设计文件，或者设计采用的国际标准、境外消防技术标准的中文文本，其他有关消防设计的应用实例、产品说明等技术资料），数量：<input maxlength="50" class="base" style="width:50px;height: auto;border-bottom: 1px solid #ccc;" type="text" value="${formObject.revMaterial}" name="revMaterial" />份（大写）；
								<br/>
								<label class="checkbox checkbox-inline m-l-25">
									<input class="base" type="checkbox" value="E" name="meanwhileSubMat" <c:if test="${formObject.meanwhileSubMat.contains('E')}"> checked </c:if> />
									<i class="input-helper"></i>
								</label> 
								5．建设工程规划许可证明文件；
								<br/>
								<label class="checkbox checkbox-inline m-l-25">
									<input class="base" type="checkbox" value="F" name="meanwhileSubMat" <c:if test="${formObject.meanwhileSubMat.contains('F')}"> checked </c:if> />
									<i class="input-helper"></i>
								</label> 
								6．城乡规划主管部门批准的临时性建筑证明文件；
								<br/>
								<label class="checkbox checkbox-inline m-l-25">
									<input class="base" type="checkbox" value="G" name="meanwhileSubMat" <c:if test="${formObject.meanwhileSubMat.contains('G')}"> checked </c:if> />
									<i class="input-helper"></i>
								</label> 
								7．法律、行政法规规定的其他材料；
								<br/>
							</td>
						</tr>
						<tr>
							<td class="content" colspan="10">
								其他需要说明的情况：<br/>
								<div class="fg-line">
									<textarea maxlength="500" class="form-control auto-size base" placeholder="请输入..." name="otherCondition">${formObject.otherCondition}</textarea>
								</div>
							</td>
						</tr>
					</div>	
				</table>
    		</div>
	    
		    <div class="card-body card-padding">
	        	<div class="row">
		            <%-- <div class="col-xs-4 form-group">
						<label class="control-label">填表日期：</label>
						<div class="input-group">
							<span class="input-group-addon"><i
								class="md md-event"></i></span>
							<div class="dtp-container dropdown fg-line">
								<input type="text"
									class="form-control date-picker date "
									value='<fmt:formatDate value="${formObject.fillFormDate}" pattern="yyyy-MM-dd" />'
									data-toggle="dropdown"
									name="fillFormDate"
									placeholder="单击此处..." aria-expanded="false">
							</div>
						</div>
					</div> --%>
		            <div class="btn-demo text-center col-xs-12">
			            <button data-toggle="modal" onclick="saveForm();"
			                    class="btn btn-primary waves-effect savebutton" type="button">保存</button>
			            <button class="btn btn-default waves-effect savebutton" type="button"
			                    onclick="javascrtpt:history.go(-1)">返回</button>
			        </div>
	        	</div>
	    	</div>
	    	<input type="hidden" id="jsonDataObj" name="jsonDataObj" />
		    <input type="hidden" id="jsonDataUnitList" name="jsonDataUnitList" />
			<input type="hidden" id="jsonDataBuildList" name="jsonDataBuildList" />
	    </form>
	</div>
</body>