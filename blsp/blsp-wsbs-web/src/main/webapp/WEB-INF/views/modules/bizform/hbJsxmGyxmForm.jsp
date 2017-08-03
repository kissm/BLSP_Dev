<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>建设项目环境影响登记表（工业项目）</title>
    <meta name="decorator" content="blank" />
    <style>
		.editTable input[type='text'].has-error {
			background: #F44336;
			color: #FFF;
		}
	</style>
    <script>
    	$(document).ready(function(){
	    	$("#hbJsxmGyxmForm").validate({
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
			//主要产品产量信息集合序列化
			var proList = [];
			$(".pro").each(function (){
				var pro = {};
				$(this).find('td').each(function (){
					pro[$(this).find('input').attr('name')] = $(this).find('input').val();
				});
				proList.push(pro);
			});
			var proJsonData = JSON.stringify(proList);
			$("#jsonDataProList").val(proJsonData);
			//主要材料用量信息集合序列化
			var metList = [];
			$(".met").each(function (){
				var met = {};
				$(this).find('td').each(function (){
					met[$(this).find('input').attr('name')] = $(this).find('input').val();
				});
				metList.push(met);
			});
			var metJsonData = JSON.stringify(metList);
			$("#jsonDataMetList").val(metJsonData);
			//主要设备或设施信息集合序列化
			var equList = [];
			$(".equ").each(function (){
				var equ = {};
				$(this).find('td').each(function (){
					equ[$(this).find('input').attr('name')] = $(this).find('input').val();
				});
				equList.push(equ);
			});
			var equJsonData = JSON.stringify(equList);
			$("#jsonDataEquList").val(equJsonData);
			$(".savebutton").attr("disabled",true);
			if($("#hbJsxmGyxmForm").valid()){
				$("#hbJsxmGyxmForm").submit();
			}else{
				$(".savebutton").attr("disabled",false);
			}
		}
		function refresh(){
			window.location.reload(true);
		}
		//添加主要产品产量行
		function addProRow(id){
			var tr = $('#'+id).clone();
			$(tr).removeAttr('id');
			$(tr).find('input').val('');
			$(tr).find('button').removeAttr('onclick').removeClass('btn-primary').addClass('btn-danger').bind("click", function(){
				var rows = $("#changeProRows").prop("rowspan");
				$("#changeProRows").attr("rowspan",rows-1);
				$(this).parents('tr').remove();
			});
			$(tr).find('button i.md').removeClass('add').addClass('minus');
			var rows = $("#changeProRows").prop("rowspan");
			$("#changeProRows").attr("rowspan",rows+1);
			$('.'+id+':last').after(tr);
		}
		//移除主要产品产量行
		function removeProRow(id){
			var rows = $("#changeProRows").prop("rowspan");
			$("#changeProRows").attr("rowspan",rows-1);
			$("#pro"+id).remove();
		}
		//添加材料用量行
		function addMetRow(id){
			var tr = $('#'+id).clone();
			$(tr).removeAttr('id');
			$(tr).find('input').val('');
			$(tr).find('button').removeAttr('onclick').removeClass('btn-primary').addClass('btn-danger').bind("click", function(){
				var rows = $("#changeMetRows").prop("rowspan");
				$("#changeMetRows").attr("rowspan",rows-1);
				$(this).parents('tr').remove();
			});
			$(tr).find('button i.md').removeClass('add').addClass('minus');
			var rows = $("#changeMetRows").prop("rowspan");
			$("#changeMetRows").attr("rowspan",rows+1);
			$('.'+id+':last').after(tr);
		}
		//移除材料用量行
		function removeMetRow(id){
			var rows = $("#changeMetRows").prop("rowspan");
			$("#changeMetRows").attr("rowspan",rows-1);
			$("#met"+id).remove();
		}
		//添加主要设备行
		function addEquRow(id){
			var tr = $('#'+id).clone();
			$(tr).removeAttr('id');
			$(tr).find('input').val('');
			$(tr).find('button').removeAttr('onclick').removeClass('btn-primary').addClass('btn-danger').bind("click", function(){
				var rows = $("#changeEquRows").prop("rowspan");
				$("#changeEquRows").attr("rowspan",rows-1);
				$(this).parents('tr').remove();
			});
			$(tr).find('button i.md').removeClass('add').addClass('minus');
			var rows = $("#changeEquRows").prop("rowspan");
			$("#changeEquRows").attr("rowspan",rows+1);
			$('.'+id+':last').after(tr);
		}
		//移除主要设备行
		function removeEquRow(id){
			var rows = $("#changeEquRows").prop("rowspan");
			$("#changeEquRows").attr("rowspan",rows-1);
			$("#equ"+id).remove();
		}
		$(function (){
			//初始化主要产品行
			var proList = '${formObject.proList}';
			if(proList.length > 0){
				$('tr[name="proTr"]').remove();
			}
			var flgp = 0;
			$(".pro").each(function (){
				flgp++;
			});
			var rows = $("#changeProRows").prop("rowspan");
			$("#changeProRows").attr("rowspan",rows+flgp-1);
			
			//初始化主要材料行
			var metList = '${formObject.metList}';
			if(metList.length > 0){
				$('tr[name="metTr"]').remove();
			}
			var flgm = 0;
			$(".met").each(function (){
				flgm++;
			});
			var rows = $("#changeMetRows").prop("rowspan");
			$("#changeMetRows").attr("rowspan",rows+flgm-1);
			
			//初始化主要设备行
			var equList = '${formObject.equList}';
			if(equList.length > 0){
				$('tr[name="equTr"]').remove();
			}
			var flge = 0;
			$(".equ").each(function (){
				flge++;
			});
			var rows = $("#changeEquRows").prop("rowspan");
			$("#changeEquRows").attr("rowspan",rows+flge-1);
		});
		//返回详情信息
		function backItem(){
			var prjId = "${formObject.prjId}";
			var taskId = "${formObject.taskId}";
			window.parent.location.href = "${ctx}/project/backItem?prjId="+prjId+"&taskId="+taskId;
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
	    <form id="hbJsxmGyxmForm" action="${ctx}/hbJsxmGyxm/save" method="post">
		    <div class="card-body card-padding">
	        	<div class="row">
		            <input type="hidden" name="id" class="base"
		                   value="${formObject.id}">
	                <input type="hidden" name="prjId" class="base"
	                 value="${formObject.prjId}">
	                <input type="hidden" name="taskId" class="base"
	                 value="${formObject.taskId}">
	                <table class="editTable" style="width: 960px;" border="0" cellspacing="0" cellpadding="0">
	                 	<tbody>
	                 		<tr>
		                		<td class="title" colspan="8" style="font-size: 16px;font-weight: bolder;">
	                 				建设项目环境影响登记表（工业项目）
	                 			</td>
		                	</tr>
	                 		<tr>
	                 			<td class="title" colspan="2" style="width: 20%;">
	                 				项目名称
	                 			</td>
	                 			<td colspan="2" style="width: 30%;">
	                 				<input maxlength="200" class="required base" type="text" value="${formObject.prjName}" name="prjName" />
	                 			</td>
	                 			<td class="title" colspan="2" style="width: 20%;">
	                 				建设单位（个人）盖章<br/>（签名）
	                 			</td>
	                 			<td colspan="2" style="width: 30%;">
	                 				<input maxlength="200" class=" base" type="text" name="company" value="${formObject.company}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
		            			<td class="title" colspan="2">
		            				项目地点
		            			</td>
		            			<td colspan="2">
		            				<input maxlength="50" class=" base" type="text" name="prjAddr" value="${formObject.prjAddr}" />
		            			</td>
		            			<td class="title" colspan="2">
		            				申请日期
		            			</td>
		            			<td colspan="2">
		            				<input class=" base" type="text" onClick="WdatePicker()" 
		                 				placeholder="点击填日期" 
		                 				value='<fmt:formatDate value="${formObject.applyDate}" 
		                 				pattern="yyyy-MM-dd" />' name="applyDate" />
		            			</td>
		            		</tr>
		            		<tr>
		            			<td class="title" colspan="2">
		            				申请人联系地址
		            			</td>
		            			<td colspan="2">
		            				<input maxlength="50" class=" base" type="text" name="appConAddr" value="${formObject.appConAddr}" />
		            			</td>
		            			<td class="title" colspan="2">
		            				申请人联系电话
		            			</td>
		            			<td colspan="2">
		            				<input maxlength="15" class=" base phone" type="text" name="applyConTel" value="${formObject.applyConTel}" />
		            			</td>
		            		</tr>
		            		<tr>
		            			<td class="title" colspan="2">
		            				随表附送资料
		            			</td>
		            			<td colspan="6" style="height: 120px;padding-left: 10px;">
		            				<label class="checkbox checkbox-inline m-r-10 m-l-10">
										<input class="base" type="checkbox" value="A" name="addMaterial" <c:if test="${formObject.addMaterial.contains('A')}"> checked </c:if> />
										<i class="input-helper"></i>    
										申请报告
									</label>
									<label class="checkbox checkbox-inline m-r-10 m-l-10">
										<input class="base" type="checkbox" value="B" name="addMaterial" <c:if test="${formObject.addMaterial.contains('B')}"> checked </c:if> />
										<i class="input-helper"></i>    
										工商登记资料
									</label>
									<label class="checkbox checkbox-inline m-r-10 m-l-10">
										<input class="base" type="checkbox" value="C" name="addMaterial" <c:if test="${formObject.addMaterial.contains('C')}"> checked </c:if> />
										<i class="input-helper"></i>    
										选址位置图
									</label><br/>
									<label class="checkbox checkbox-inline m-r-10 m-l-10">
										<input class="base" type="checkbox" value="D" name="addMaterial" <c:if test="${formObject.addMaterial.contains('D')}"> checked </c:if> />
										<i class="input-helper"></i>    
										项目建议书批准或备案准予复印件
									</label>
									<label class="checkbox checkbox-inline m-r-10 m-l-10">
										<input class="base" type="checkbox" value="E" name="addMaterial" <c:if test="${formObject.addMaterial.contains('E')}"> checked </c:if> />
										<i class="input-helper"></i>    
										租赁合同
									</label>
									<label class="checkbox checkbox-inline m-r-10 m-l-10">
										<input class="base" type="checkbox" value="F" name="addMaterial" <c:if test="${formObject.addMaterial.contains('F')}"> checked </c:if> />
										<i class="input-helper"></i>    
										房产证复印件
									</label><br/>
									<label class="checkbox checkbox-inline m-r-10 m-l-10">
										<input class="base" type="checkbox" value="G" name="addMaterial" <c:if test="${formObject.addMaterial.contains('G')}"> checked </c:if> />
										<i class="input-helper"></i>    
										规划选址意见复印件
									</label>
									<label class="checkbox checkbox-inline m-r-10 m-l-10">
										<input class="base" type="checkbox" value="H" name="addMaterial" <c:if test="${formObject.addMaterial.contains('H')}"> checked </c:if> />
										<i class="input-helper"></i>    
										土地预审意见复印件
									</label>
									<label class="checkbox checkbox-inline m-r-10 m-l-10">
										<input class="base" type="checkbox" value="I" name="addMaterial" <c:if test="${formObject.addMaterial.contains('I')}"> checked </c:if> />
										<i class="input-helper"></i>    
										生产工艺和设备简要说明
									</label><br/>
									<label class="checkbox checkbox-inline m-r-10 m-l-10">
										<input class="base" type="checkbox" value="J" name="addMaterial" <c:if test="${formObject.addMaterial.contains('J')}"> checked </c:if> />
										<i class="input-helper"></i>    
										总平面图或红线图
									</label>
									<label class="checkbox checkbox-inline m-r-10 m-l-10">
										<input class="base" type="checkbox" value="K" name="addMaterial" <c:if test="${formObject.addMaterial.contains('K')}"> checked </c:if> />
										<i class="input-helper"></i>    
										附法人身份证复印件、联系电话、联系地址
									</label><br/>
									<label class="checkbox checkbox-inline m-r-10 m-l-10">
										<input class="base" type="checkbox" value="L" name="addMaterial" <c:if test="${formObject.addMaterial.contains('L')}"> checked </c:if> />
										<i class="input-helper"></i>    
										其他
									</label>
									<input class="base" maxlength="250" type="text" name="addOther" value="${formObject.addOther}" style="height: auto;width: 200px;border-bottom: 1px solid #ccc;" />
		            			</td>
		            		</tr>
		            		<tr>
		            			<td class="title" colspan="2">
		            				项目负责人
		            			</td>
		            			<td colspan="2">
		            				<input maxlength="50" class=" base" type="text" name="prjPrincipal" value="${formObject.prjPrincipal}" />
		            			</td>
		            			<td class="title" colspan="2">
		            				批文文号
		            			</td>
		            			<td colspan="2">
		            				<input maxlength="50" class=" base" type="text" name="approvalNum" value="${formObject.approvalNum}" />
		            			</td>
		            		</tr>
		            		<tr>
		            			<td class="title" colspan="2">
		            				建设性质
		            			</td>
		            			<td colspan="2">
		            				<input maxlength="50" class=" base" type="text" name="buildNatrue" value="${formObject.buildNatrue}" />
		            			</td>
		            			<td class="title" colspan="2">
		            				占地面积
		            			</td>
		            			<td colspan="2">
		            				<input maxlength="50" class=" base" type="text" name="floorArea" value="${formObject.floorArea}" />
		            			</td>
		            		</tr>
		            		<tr>
		            			<td class="title" colspan="2" rowspan="2">
		            				总规模
		            			</td>
		            			<td colspan="2" rowspan="2">
		            				<input maxlength="50" class=" base" type="text" name="totalScale" value="${formObject.totalScale}" />
		            			</td>
		            			<td class="title" colspan="2">
		            				总投资
		            			</td>
		            			<td style="border-right: none;">
		            				<input maxlength="50" class=" base" type="text" name="totalInvest" value="${formObject.totalInvest}" placeholder="此处填写数据" />
		            			</td>
		            			<td class="content" style="border-left: none;width: 15%;">
		            				万元
		            			</td>
		            		</tr>
		            		<tr>
		            			<td class="title" colspan="2">
		            				环保投资
		            			</td>
		            			<td style="border-right: none;">
		            				<input maxlength="50" class=" base" type="text" name="envInvest" value="${formObject.envInvest}" placeholder="此处填写数据" />
		            			</td>
		            			<td class="content" style="border-left: none;">
		            				万元
		            			</td>
		            		</tr>
		            		<tr>
		            			<td class="title" rowspan="2" id="changeProRows">
		            				主<br/>要<br/>产<br/>品<br/>产<br/>量
		            			</td>
		            			<td class="title" colspan="3" style="width: 40%;">
		            				名称
		            			</td>
		            			<td class="title" colspan="4" style="width: 50%;">
		            				年产量（吨/年）
		            			</td>
		            		</tr>
		            		<tr id="pro" class="pro" name="proTr">
		            			<td style="border-right: none;" align="center">
	                 				<button type="button" onclick="addProRow('pro')" class="btn btn-primary">
										<i class="md add"></i>
									</button>
	                 			</td>
	                 			<td colspan="2" style="border-left: none;">
	                 				<input maxlength="50" type="text"  name="pName"></input>
	                 			</td>
	                 			<td colspan="4">
	                 				<input maxlength="50" type="text"  name="pYield"></input>
	                 			</td>
		            		</tr>
		            		<c:forEach items="${formObject.proList}" var="pro" varStatus="i">
		            			<c:if test="${i.index == 0}">
		            				<tr id="pro" class="pro">
		            					<td style="display: none;">
	                 						<input type="hidden" name="pId" value="${pro.pId}" />
	                 					</td>
		            					<td style="border-right: none;" align="center">
			                 				<button type="button" onclick="addProRow('pro')" class="btn btn-primary">
												<i class="md add"></i>
											</button>
											<input type="hidden" name="hId" value="${pro.hId}" />
			                 			</td>
			                 			<td colspan="2" style="border-left: none;">
			                 				<input maxlength="50" type="text"  name="pName" value="${pro.pName}"></input>
			                 			</td>
			                 			<td colspan="4">
			                 				<input maxlength="50" type="text"  name="pYield" value="${pro.pYield}"></input>
			                 			</td>
		            				</tr>
		            			</c:if>
		            			<c:if test="${i.index > 0}">
		            				<tr id="pro${pro.pId}" class="pro">
		            					<td style="display: none;">
	                 						<input type="hidden" name="pId" value="${pro.pId}" />
	                 					</td>
		            					<td style="border-right: none;" align="center">
			                 				<button type="button" onclick="removeProRow('${pro.pId}')" class="btn btn-danger">
												<i class="md minus"></i>
											</button>
											<input type="hidden" name="hId" value="${pro.hId}" />
			                 			</td>
			                 			<td colspan="2" style="border-left: none;">
			                 				<input maxlength="50" type="text"  name="pName" value="${pro.pName}"></input>
			                 			</td>
			                 			<td colspan="4">
			                 				<input maxlength="50" type="text"  name="pYield" value="${pro.pYield}"></input>
			                 			</td>
		            				</tr>
		            			</c:if>
		            		</c:forEach>
		            		<tr>
		            			<td class="title" rowspan="2" id="changeMetRows">
		            				主<br/>要<br/>原<br/>材<br/>料<br/>用<br/>量
		            			</td>
		            			<td class="title" colspan="3">
		            				名称
		            			</td>
		            			<td class="title" colspan="4">
		            				年用量（吨/年）
		            			</td>
		            		</tr>
		            		<tr id="met" class="met" name="metTr">
		            			<td style="border-right: none;" align="center">
	                 				<button type="button" onclick="addMetRow('met')" class="btn btn-primary">
										<i class="md add"></i>
									</button>
	                 			</td>
	                 			<td colspan="2" style="border-left: none;">
	                 				<input maxlength="50" type="text"  name="mName"></input>
	                 			</td>
	                 			<td colspan="4">
	                 				<input maxlength="50" type="text"  name="mUseLevel"></input>
	                 			</td>
		            		</tr>
		            		<c:forEach items="${formObject.metList}" var="met" varStatus="i">
		            			<c:if test="${i.index == 0}">
		            				<tr id="met" class="met">
		            					<td style="display: none;">
	                 						<input type="hidden" name="mId" value="${met.mId}" />
	                 					</td>
		            					<td style="border-right: none;" align="center">
			                 				<button type="button" onclick="addMetRow('met')" class="btn btn-primary">
												<i class="md add"></i>
											</button>
											<input type="hidden" name="hId" value="${met.hId}" />
			                 			</td>
			                 			<td colspan="2" style="border-left: none;">
			                 				<input maxlength="50" type="text"  name="mName" value="${met.mName}"></input>
			                 			</td>
			                 			<td colspan="4">
			                 				<input maxlength="50" type="text"  name="mUseLevel" value="${met.mUseLevel}"></input>
			                 			</td>
		            				</tr>
		            			</c:if>
		            			<c:if test="${i.index > 0}">
		            				<tr id="met${met.mId}" class="met">
		            					<td style="display: none;">
	                 						<input type="hidden" name="mId" value="${met.mId}" />
	                 					</td>
		            					<td style="border-right: none;" align="center">
			                 				<button type="button" onclick="removeMetRow('${met.mId}')" class="btn btn-danger">
												<i class="md minus"></i>
											</button>
											<input type="hidden" name="hId" value="${met.hId}" />
			                 			</td>
			                 			<td colspan="2" style="border-left: none;">
			                 				<input maxlength="50" type="text"  name="mName" value="${met.mName}"></input>
			                 			</td>
			                 			<td colspan="4">
			                 				<input maxlength="50" type="text"  name="mUseLevel" value="${met.mUseLevel}"></input>
			                 			</td>
		            				</tr>
		            			</c:if>
		            		</c:forEach>
		            		<tr>
		            			<td class="title" rowspan="2" id="changeEquRows" style="width: 10%;">
		            				主<br/>要<br/>设<br/>备<br/>或<br/>设<br/>施
		            			</td>
		            			<td class="title" colspan="2" style="width: 30%;">
		            				名称
		            			</td>
		            			<td class="title" colspan="2" style="width: 22.5%;">
		            				规格（型号）
		            			</td>
		            			<td class="title" colspan="2" style="width: 22.5%;">
		            				数量（单位）
		            			</td>
		            			<td class="title">
		            				备注
		            			</td>
		            		</tr>
		            		<tr id="equ" class="equ" name="equTr">
		            			<td style="border-right: none;" align="center">
	                 				<button type="button" onclick="addEquRow('equ')" class="btn btn-primary">
										<i class="md add"></i>
									</button>
	                 			</td>
	                 			<td style="border-left: none;">
	                 				<input maxlength="50" type="text"  name="eName"></input>
	                 			</td>
	                 			<td colspan="2">
	                 				<input maxlength="50" type="text"  name="eSpecificate"></input>
	                 			</td>
	                 			<td colspan="2">
	                 				<input maxlength="50" type="text"  name="eCount"></input>
	                 			</td>
	                 			<td>
	                 				<input maxlength="50" type="text"  name="eRemark"></input>
	                 			</td>
		            		</tr>
		            		<c:forEach items="${formObject.equList}" var="equ" varStatus="i">
		            			<c:if test="${i.index == 0}">
		            				<tr id="equ" class="equ">
		            					<td style="display: none;">
	                 						<input type="hidden" name="eId" value="${equ.eId}" />
	                 					</td>
		            					<td style="border-right: none;" align="center">
			                 				<button type="button" onclick="addEquRow('equ')" class="btn btn-primary">
												<i class="md add"></i>
											</button>
											<input type="hidden" name="hId" value="${equ.hId}" />
			                 			</td>
			                 			<td style="border-left: none;">
			                 				<input maxlength="50" type="text"  name="eName" value="${equ.eName}"></input>
			                 			</td>
			                 			<td colspan="2">
			                 				<input maxlength="50" type="text"  name="eSpecificate" value="${equ.eSpecificate}"></input>
			                 			</td>
			                 			<td colspan="2">
			                 				<input maxlength="50" type="text"  name="eCount" value="${equ.eCount}"></input>
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="eRemark" value="${equ.eRemark}"></input>
			                 			</td>
		            				</tr>
		            			</c:if>
		            			<c:if test="${i.index > 0}">
		            				<tr id="equ${equ.eId}" class="equ">
		            					<td style="display: none;">
	                 						<input type="hidden" name="eId" value="${equ.eId}" />
	                 					</td>
		            					<td style="border-right: none;" align="center">
			                 				<button type="button" onclick="removeEquRow('${equ.eId}')" class="btn btn-danger">
												<i class="md minus"></i>
											</button>
											<input type="hidden" name="hId" value="${equ.hId}" />
			                 			</td>
			                 			<td style="border-left: none;">
			                 				<input maxlength="50" type="text"  name="eName" value="${equ.eName}"></input>
			                 			</td>
			                 			<td colspan="2">
			                 				<input maxlength="50" type="text"  name="eSpecificate" value="${equ.eSpecificate}"></input>
			                 			</td>
			                 			<td colspan="2">
			                 				<input maxlength="50" type="text"  name="eCount" value="${equ.eCount}"></input>
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="eRemark" value="${equ.eRemark}"></input>
			                 			</td>
		            				</tr>
		            			</c:if>
		            		</c:forEach>
		            		<tr>
		            			<td class="title" colspan="2">
		            				经办人
		            			</td>
		            			<td colspan="2">
		            				<input maxlength="50" class=" base" type="text" name="operator" value="${formObject.operator}" />
		            			</td>
		            			<td class="title" colspan="2">
		            				经办人联系电话
		            			</td>
		            			<td colspan="2">
		            				<input maxlength="15" class=" base phone" type="text" name="operTel" value="${formObject.operTel}" />
		            			</td>
		            		</tr>
		            		<tr>
		            			<td colspan="8" style="border-left: none;border-right: none;height: 50px;"></td>
		            		</tr>
		            		<tr>
		            			<td colspan="3"></td>
		            			<td class="title" colspan="2">
		            				吨/日
		            			</td>
		            			<td class="title" colspan="3">
		            				年耗能情况
		            			</td>
		            		</tr>
		            		<tr>
		            			<td class="title" colspan="2" rowspan="2">
		            				总供水量
		            			</td>
		            			<td class="title">
		            				工业用水量
		            			</td>
		            			<td colspan="2">
		            				<input maxlength="50" class=" base" type="text" name="indWaterSupply" value="${formObject.indWaterSupply}" />
		            			</td>
		            			<td class="title" colspan="2">
		            				油（吨/年）
		            			</td>
		            			<td>
		            				<input maxlength="50" class=" base" type="text" name="oil" value="${formObject.oil}" />
		            			</td>
		            		</tr>
		            		<tr>
		            			<td class="title">
		            				生活用水量
		            			</td>
		            			<td colspan="2">
		            				<input maxlength="50" class=" base" type="text" name="lifeWaterSupply" value="${formObject.lifeWaterSupply}" />
		            			</td>
		            			<td class="title" colspan="2">
		            				煤（吨/年）
		            			</td>
		            			<td>
		            				<input maxlength="50" class=" base" type="text" name="coal" value="${formObject.coal}" />
		            			</td>
		            		</tr>
		            		<tr>
		            			<td class="title" colspan="2" rowspan="2">
		            				废水总排放量
		            			</td>
		            			<td class="title">
		            				工业废水排放量
		            			</td>
		            			<td colspan="2">
		            				<input maxlength="50" class=" base" type="text" name="indWasteWater" value="${formObject.indWasteWater}" />
		            			</td>
		            			<td class="title" colspan="2">
		            				电（度/年）
		            			</td>
		            			<td>
		            				<input maxlength="50" class=" base" type="text" name="electricity" value="${formObject.electricity}" />
		            			</td>
		            		</tr>
		            		<tr>
		            			<td class="title">
		            				生活废水排放量
		            			</td>
		            			<td colspan="2">
		            				<input maxlength="50" class=" base" type="text" name="lifeWasteWater" value="${formObject.lifeWasteWater}" />
		            			</td>
		            			<td class="title" colspan="2">
		            				液化气（公斤/年）
		            			</td>
		            			<td>
		            				<input maxlength="50" class=" base" type="text" name="liquefiedGas" value="${formObject.liquefiedGas}" />
		            			</td>
		            		</tr>
		            		<tr>
		            			<td class="title" colspan="2">
		            				循环水量
		            			</td>
		            			<td>
		            				<input maxlength="50" style="width: 100px;height: auto;" class=" base" type="text" name="circulatWater" value="${formObject.circulatWater}" placeholder="此处填写数据" />
		            				吨/日
		            			</td>
		            			<td class="title" colspan="2">
		            				排水去向
		            			</td>
		            			<td colspan="3">
		            				<input maxlength="50" class=" base" type="text" name="dewateringWhere" value="${formObject.dewateringWhere}" />
		            			</td>
		            		</tr>
		            		<tr>
		            			<td class="content" colspan="8" style="border-bottom: none;">
		            				生产工艺流程简要说明
		            			</td>
		            		</tr>
		            		<tr>
		            			<td colspan="8" style="border-top: none;padding: 0 10px;">	            			
									<textarea rows="15" style="border: none;resize:none;text-indent: 2em;width: 940px;" maxlength="1000" class="form-control auto-size  base" placeholder="请输入..." name="processExplain">${formObject.processExplain}</textarea>
		            			</td>
		            		</tr>
		            		<tr>
		            			<td class="content" colspan="8" style="border-bottom: none;">
		            				建设过程中、建成后对环境影响的分析及需要说明的问题
		            			</td>
		            		</tr>
		            		<tr>
		            			<td colspan="8" style="border-top: none;padding: 0 10px;">	            			
									<textarea rows="15" style="border: none;resize:none;text-indent: 2em;width: 940px;" maxlength="1000" class="form-control auto-size  base" placeholder="请输入..." name="needExplain">${formObject.needExplain}</textarea>
		            			</td>
		            		</tr>
	                 	</tbody>
	                </table>
		            <div class="btn-demo text-center col-xs-12">
		            	<input type="hidden" id="jsonDataObj" name="jsonDataObj" />
					    <input type="hidden" id="jsonDataProList" name="jsonDataProList" />
						<input type="hidden" id="jsonDataMetList" name="jsonDataMetList" />
						<input type="hidden" id="jsonDataEquList" name="jsonDataEquList" />
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
	    	</div>
	    </form>
	</div>
</body>