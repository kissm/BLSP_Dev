<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>珠海市建设工程项目安全生产文明施工目标管理责任承诺书</title>
    <meta name="decorator" content="blank" />
    <style>
		.editTable input[type='text'].has-error {
			background: #F44336;
			color: #FFF;
		}
		.noBorder > tbody > tr > td {
			border: none;
			height: 40px;
		}
		.editTable td.textAlignLeft {
			text-align: left;
			padding-left: 10px;
		}
		.editTable td.fontWeight {
			font-weight: bold;
		}
	</style>
    <script>
    	$(document).ready(function(){
	    	$("#zjAqscCnsForm").validate({
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
	    	$("#zjAqscCnsForm").validate({
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
			if($("#zjAqscCnsForm").valid()){
				$("#zjAqscCnsForm").submit();
			}else{
				$(".savebutton").attr("disabled",false);
			}
		}
		function refresh(){
			window.location.reload(true);
		}
		function getCivConSumo(civConRatioo){
			var ratioo = civConRatioo.value;
			if(ratioo != ''){
				var civConRatioo = civConRatioo.value*0.01;
				var sum = 0;
				$(".sum").each(function (){
					if($(this).val()){
						sum += eval($(this).val());
					}
				});
				var m = 0,sum = sum.toString(),civConRatioo = civConRatioo.toString();
				try{m += sum.split(".")[1].length}catch(e){}
				try{m += civConRatioo.split(".")[1].length}catch(e){}
				var civConSumo = Number(sum.replace(".",""))*Number(civConRatioo.replace(".",""))/Math.pow(10,m);
				$("#civConSumo").val(civConSumo);
			}else{
				$("#civConSumo").val('');
			}
		}
		$(function (){
			$(".sum").keyup(function (){
				getCivConSumo(document.getElementById("civConRatioo"));
			});
		});
		function checkCheckBox(checkbox){
			$('.checkOne[name="'+checkbox.name+'"]').each(function () {
				if (this != checkbox)
					$(this).attr("checked", false);
				else {
					if ($(this).prop("checked"))
						$(this).attr("checked", true);
					else
						$(this).attr("checked", false);
				}
			});
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
	    <form id="zjAqscCnsForm" action="${ctx}/zjAqscCns/save" method="post">
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
	                 			<td class="title" colspan="5" style="font-size: 16px;font-weight: bolder;">
	                 				珠海市建设工程项目安全生产文明施工目标管理责任承诺书
	                 			</td>
	                 		</tr>
		            		<tr>
		            			<td colspan="5" class="title fontWeight" style="font-size: 14px;height: 50px;">
		            				安全防护文明施工措施费支付计划
		            			</td>
		            		</tr>
		            		<tr>
		            			<td style="width: 10%;" class="title fontWeight">
		            				序<br/>号
		            			</td>
		            			<td class="title fontWeight">
		            				项&nbsp;&nbsp;&nbsp;&nbsp;目&nbsp;&nbsp;&nbsp;&nbsp;清&nbsp;&nbsp;&nbsp;&nbsp;单
		            			</td>
		            			<td style="width: 18%;" class="title fontWeight">
		            				措施费金额<br/>（元）
		            			</td>
		            			<td style="width: 18%;" class="title fontWeight">
		            				占安全、文明措<br/>施费比例
		            			</td>
		            			<td style="width: 18%;" class="title fontWeight">
		            				使用计划时间
		            			</td>
		            		</tr>
		            		<tr>
		            			<td colspan="5" class="title textAlignLeft fontWeight">
		            				一、安全防护项目清单
		            			</td>
		            		</tr>
		            		<tr>
		            			<td class="title textAlignLeft">
		            				1、
		            			</td>
		            			<td class="title textAlignLeft">
		            				五牌一图
		            			</td>
		            			<td>
		            				<input maxlength="50" class=" sum" type="text" name="safProSuma" value="${formObject.safProSuma}" />
		            			</td>
		            			<td>
		            				<input maxlength="50"  type="text" name="safProRatioa" value="${formObject.safProRatioa}" />
		            			</td>
		            			<td>
		            				<input maxlength="50"  type="text" name="safProTimea" value="${formObject.safProTimea}" />
		            			</td>
		            		</tr>
		            		<tr>
		            			<td class="title textAlignLeft">
		            				2、
		            			</td>
		            			<td class="title textAlignLeft">
		            				施工现场临设
		            			</td>
		            			<td>
		            				<input maxlength="50" class=" sum" type="text" name="safProSumb" value="${formObject.safProSumb}" />
		            			</td>
		            			<td>
		            				<input maxlength="50"  type="text" name="safProRatiob" value="${formObject.safProRatiob}" />
		            			</td>
		            			<td>
		            				<input maxlength="50"  type="text" name="safProTimeb" value="${formObject.safProTimeb}" />
		            			</td>
		            		</tr>
		            		<tr>
		            			<td class="title textAlignLeft">
		            				3、
		            			</td>
		            			<td class="title textAlignLeft">
		            				封闭施工围墙及装饰、大门、门卫室等
		            			</td>
		            			<td>
		            				<input maxlength="50" class=" sum" type="text" name="safProSumc" value="${formObject.safProSumc}" />
		            			</td>
		            			<td>
		            				<input maxlength="50"  type="text" name="safProRatioc" value="${formObject.safProRatioc}" />
		            			</td>
		            			<td>
		            				<input maxlength="50"  type="text" name="safProTimec" value="${formObject.safProTimec}" />
		            			</td>
		            		</tr>
		            		<tr>
		            			<td class="title textAlignLeft">
		            				4、
		            			</td>
		            			<td class="title textAlignLeft">
		            				现场污染源的控制等
		            			</td>
		            			<td>
		            				<input maxlength="50" class=" sum" type="text" name="safProSumd" value="${formObject.safProSumd}" />
		            			</td>
		            			<td>
		            				<input maxlength="50"  type="text" name="safProRatiod" value="${formObject.safProRatiod}" />
		            			</td>
		            			<td>
		            				<input maxlength="50"  type="text" name="safProTimed" value="${formObject.safProTimed}" />
		            			</td>
		            		</tr>
		            		<tr>
		            			<td class="title textAlignLeft">
		            				5、
		            			</td>
		            			<td class="title textAlignLeft">
		            				工地绿化、美化
		            			</td>
		            			<td>
		            				<input maxlength="50" class=" sum" type="text" name="safProSume" value="${formObject.safProSume}" />
		            			</td>
		            			<td>
		            				<input maxlength="50"  type="text" name="safProRatioe" value="${formObject.safProRatioe}" />
		            			</td>
		            			<td>
		            				<input maxlength="50"  type="text" name="safProTimee" value="${formObject.safProTimee}" />
		            			</td>
		            		</tr>
		            		<tr>
		            			<td class="title textAlignLeft">
		            				6、
		            			</td>
		            			<td class="title textAlignLeft">
		            				其它文明施工措施费用
		            			</td>
		            			<td>
		            				<input maxlength="50" class=" sum" type="text" name="safProSumf" value="${formObject.safProSumf}" />
		            			</td>
		            			<td>
		            				<input maxlength="50"  type="text" name="safProRatiof" value="${formObject.safProRatiof}" />
		            			</td>
		            			<td>
		            				<input maxlength="50"  type="text" name="safProTimef" value="${formObject.safProTimef}" />
		            			</td>
		            		</tr>
		            		<tr>
		            			<td colspan="5" class="title textAlignLeft fontWeight">
		            				二、文明施工措施项目清单
		            			</td>
		            		</tr>
		            		<tr>
		            			<td class="title textAlignLeft">
		            				1、
		            			</td>
		            			<td class="title textAlignLeft">
		            				安全资料、特殊作业专项方案的编制等
		            			</td>
		            			<td>
		            				<input maxlength="50" class=" sum" type="text" name="civConSuma" value="${formObject.civConSuma}" />
		            			</td>
		            			<td>
		            				<input maxlength="50"  type="text" name="civConRatioa" value="${formObject.civConRatioa}" />
		            			</td>
		            			<td>
		            				<input maxlength="50"  type="text" name="civConTimea" value="${formObject.civConTimea}" />
		            			</td>
		            		</tr>
		            		<tr>
		            			<td class="title textAlignLeft">
		            				2、
		            			</td>
		            			<td class="title textAlignLeft">
		            				安全培训及教育
		            			</td>
		            			<td>
		            				<input maxlength="50" class=" sum" type="text" name="civConSumb" value="${formObject.civConSumb}" />
		            			</td>
		            			<td>
		            				<input maxlength="50"  type="text" name="civConRatiob" value="${formObject.civConRatiob}" />
		            			</td>
		            			<td>
		            				<input maxlength="50"  type="text" name="civConTimeb" value="${formObject.civConTimeb}" />
		            			</td>
		            		</tr>
		            		<tr>
		            			<td class="title textAlignLeft">
		            				3、
		            			</td>
		            			<td class="title textAlignLeft">
		            				“三宝”、“四口”、“五临边”的防护
		            			</td>
		            			<td>
		            				<input maxlength="50" class=" sum" type="text" name="civConSumc" value="${formObject.civConSumc}" />
		            			</td>
		            			<td>
		            				<input maxlength="50"  type="text" name="civConRatioc" value="${formObject.civConRatioc}" />
		            			</td>
		            			<td>
		            				<input maxlength="50"  type="text" name="civConTimec" value="${formObject.civConTimec}" />
		            			</td>
		            		</tr>
		            		<tr>
		            			<td class="title textAlignLeft">
		            				4、
		            			</td>
		            			<td class="title textAlignLeft">
		            				施工用电等
		            			</td>
		            			<td>
		            				<input maxlength="50" class=" sum" type="text" name="civConSumd" value="${formObject.civConSumd}" />
		            			</td>
		            			<td>
		            				<input maxlength="50"  type="text" name="civConRatiod" value="${formObject.civConRatiod}" />
		            			</td>
		            			<td>
		            				<input maxlength="50"  type="text" name="civConTimed" value="${formObject.civConTimed}" />
		            			</td>
		            		</tr>
		            		<tr>
		            			<td class="title textAlignLeft">
		            				5、
		            			</td>
		            			<td class="title textAlignLeft">
		            				起重设备等安全防护措施
		            			</td>
		            			<td>
		            				<input maxlength="50" class=" sum" type="text" name="civConSume" value="${formObject.civConSume}" />
		            			</td>
		            			<td>
		            				<input maxlength="50"  type="text" name="civConRatioe" value="${formObject.civConRatioe}" />
		            			</td>
		            			<td>
		            				<input maxlength="50"  type="text" name="civConTimee" value="${formObject.civConTimee}" />
		            			</td>
		            		</tr>
		            		<tr>
		            			<td class="title textAlignLeft">
		            				6、
		            			</td>
		            			<td class="title textAlignLeft">
		            				施工机具防护棚及其围栏的安全设施
		            			</td>
		            			<td>
		            				<input maxlength="50" class=" sum" type="text" name="civConSumf" value="${formObject.civConSumf}" />
		            			</td>
		            			<td>
		            				<input maxlength="50"  type="text" name="civConRatiof" value="${formObject.civConRatiof}" />
		            			</td>
		            			<td>
		            				<input maxlength="50"  type="text" name="civConTimef" value="${formObject.civConTimef}" />
		            			</td>
		            		</tr>
		            		<tr>
		            			<td class="title textAlignLeft">
		            				7、
		            			</td>
		            			<td class="title textAlignLeft">
		            				隧道、人工挖孔桩、地下室等照明设施
		            			</td>
		            			<td>
		            				<input maxlength="50" class=" sum" type="text" name="civConSumg" value="${formObject.civConSumg}" />
		            			</td>
		            			<td>
		            				<input maxlength="50"  type="text" name="civConRatiog" value="${formObject.civConRatiog}" />
		            			</td>
		            			<td>
		            				<input maxlength="50"  type="text" name="civConTimeg" value="${formObject.civConTimeg}" />
		            			</td>
		            		</tr>
		            		<tr>
		            			<td class="title textAlignLeft">
		            				8、
		            			</td>
		            			<td class="title textAlignLeft">
		            				水上、水下作业的救生设备、器材等
		            			</td>
		            			<td>
		            				<input maxlength="50" class=" sum" type="text" name="civConSumh" value="${formObject.civConSumh}" />
		            			</td>
		            			<td>
		            				<input maxlength="50"  type="text" name="civConRatioh" value="${formObject.civConRatioh}" />
		            			</td>
		            			<td>
		            				<input maxlength="50"  type="text" name="civConTimeh" value="${formObject.civConTimeh}" />
		            			</td>
		            		</tr>
		            		<tr>
		            			<td class="title textAlignLeft">
		            				9、
		            			</td>
		            			<td class="title textAlignLeft">
		            				预防突发事故、预防自然灾害等抢险设备
		            			</td>
		            			<td>
		            				<input maxlength="50" class=" sum" type="text" name="civConSumi" value="${formObject.civConSumi}" />
		            			</td>
		            			<td>
		            				<input maxlength="50"  type="text" name="civConRatioi" value="${formObject.civConRatioi}" />
		            			</td>
		            			<td>
		            				<input maxlength="50"  type="text" name="civConTimei" value="${formObject.civConTimei}" />
		            			</td>
		            		</tr>
		            		<tr>
		            			<td class="title textAlignLeft">
		            				10、
		            			</td>
		            			<td class="title textAlignLeft">
		            				交通输导、警示设施
		            			</td>
		            			<td>
		            				<input maxlength="50" class=" sum" type="text" name="civConSumj" value="${formObject.civConSumj}" />
		            			</td>
		            			<td>
		            				<input maxlength="50"  type="text" name="civConRatioj" value="${formObject.civConRatioj}" />
		            			</td>
		            			<td>
		            				<input maxlength="50"  type="text" name="civConTimej" value="${formObject.civConTimej}" />
		            			</td>
		            		</tr>
		            		<tr>
		            			<td class="title textAlignLeft">
		            				11、
		            			</td>
		            			<td class="title textAlignLeft">
		            				基坑支护、检测
		            			</td>
		            			<td>
		            				<input maxlength="50" class=" sum" type="text" name="civConSumk" value="${formObject.civConSumk}" />
		            			</td>
		            			<td>
		            				<input maxlength="50"  type="text" name="civConRatiok" value="${formObject.civConRatiok}" />
		            			</td>
		            			<td>
		            				<input maxlength="50"  type="text" name="civConTimek" value="${formObject.civConTimek}" />
		            			</td>
		            		</tr>
		            		<tr>
		            			<td class="title textAlignLeft">
		            				12、
		            			</td>
		            			<td class="title textAlignLeft">
		            				提升架体围护用安全网购置
		            			</td>
		            			<td>
		            				<input maxlength="50" class=" sum" type="text" name="civConSuml" value="${formObject.civConSuml}" />
		            			</td>
		            			<td>
		            				<input maxlength="50"  type="text" name="civConRatiol" value="${formObject.civConRatiol}" />
		            			</td>
		            			<td>
		            				<input maxlength="50"  type="text" name="civConTimel" value="${formObject.civConTimel}" />
		            			</td>
		            		</tr>
		            		<tr>
		            			<td class="title textAlignLeft">
		            				13、
		            			</td>
		            			<td class="title textAlignLeft">
		            				消防设施及消防器材配置
		            			</td>
		            			<td>
		            				<input maxlength="50" class=" sum" type="text" name="civConSumm" value="${formObject.civConSumm}" />
		            			</td>
		            			<td>
		            				<input maxlength="50"  type="text" name="civConRatiom" value="${formObject.civConRatiom}" />
		            			</td>
		            			<td>
		            				<input maxlength="50"  type="text" name="civConTimem" value="${formObject.civConTimem}" />
		            			</td>
		            		</tr>
		            		<tr>
		            			<td class="title textAlignLeft">
		            				14、
		            			</td>
		            			<td class="title textAlignLeft">
		            				其他安全防护措施
		            			</td>
		            			<td>
		            				<input maxlength="50" class=" sum" type="text" name="civConSumn" value="${formObject.civConSumn}" />
		            			</td>
		            			<td>
		            				<input maxlength="50"  type="text" name="civConRation" value="${formObject.civConRation}" />
		            			</td>
		            			<td>
		            				<input maxlength="50"  type="text" name="civConTimen" value="${formObject.civConTimen}" />
		            			</td>
		            		</tr>
		            		<tr>
		            			<td class="title textAlignLeft">
		            				15、
		            			</td>
		            			<td class="title textAlignLeft">
		            				安全防护、文明施工措施费总额（合计）
		            			</td>
		            			<td colspan="3" style="padding-left: 10px;">
		            				总价×
		            				<input id="civConRatioo" maxlength="50" onkeyup="getCivConSumo(this)"  style="width:80px;height: auto;border-bottom: none;" type="text" placeholder="此处填写" value="${formObject.civConRatioo}" name="civConRatioo" />
		            				% =
		            				<input maxlength="50" style="width:300px;height: auto;border-bottom: none;" type="text" readonly value="${formObject.civConSumo}" name="civConSumo" id="civConSumo" />
		            			</td>
		            		</tr>
		            		<tr>
		            			<td colspan="6" style="height: 40px;padding-left: 10px;border-bottom: none;">
		            				建设单位支付费用方式：
		            			</td>
		            		</tr>
		            		<tr>
		            			<td colspan="6" style="border-top: none;height: 40px;padding:0 20px;">
		            				<input maxlength="50"  style="width: 600px;height: auto;border-bottom: none;" type="text" placeholder="此处填写" value="${formObject.buildPaymentMethod}" name="buildPaymentMethod" />
		            			</td>
		            		</tr>
		            	</tbody>
		            </table>
		            <br/>
		            <table class="editTable" style="width: 960px;" border="0" cellspacing="0" cellpadding="0">
		            	<tr>
	            			<td colspan="6" class="title fontWeight" style="font-size: 14px;height: 50px;">
	            				开工前安全生产条件
	            			</td>
	            		</tr>
	            		<tr>
	            			<td colspan="6" class="title fontWeight" style="height: 40px;">
	            				一、施工单位项目安全管理架构
	            			</td>
	            		</tr>
	            		<tr>
	            			<td style="width: 5%;" class="title">
	            				序<br/>号
	            			</td>
	            			<td class="title" colspan="2">
	            				类&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;别
	            			</td>
	            			<td style="width: 14%;" class="title">
	            				姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名
	            			</td>
	            			<td style="width: 20%;" class="title">
	            				安全生产<br/>考核合格证号
	            			</td>
	            			<td class="title">
	            				建设单位或建设单位委托的监理单位<br/>对安全生产条件审查、核查情况
	            			</td>
	            		</tr>
	            		<tr>
	            			<td class="title">
	            				1.1
	            			</td>
	            			<td rowspan="6" class="title" style="width: 5%;">
	            				安全生产考核合格证情况
	            			</td>
	            			<td class="title" style="width: 14%;">
	            				建造师<br/>(项目经<br/>理)
	            			</td>
	            			<td>
	            				<input maxlength="50"  type="text" name="constructorName" value="${formObject.constructorName}" />
	            			</td>
	            			<td>
	            				<input maxlength="50"  type="text" name="constructorSafProCer" value="${formObject.constructorSafProCer}" />
	            			</td>
	            			<td style="padding-left: 10px;">
	            				<label class="checkbox checkbox-inline m-l-25">
									<input type="checkbox" onclick="checkCheckBox(this)" class="checkOne" value="1" name="constructorSafProCon" <c:if test="${formObject.constructorSafProCon.contains('1')}"> checked </c:if> />
									<i class="input-helper"></i>
									符合
								</label>
								<label class="checkbox checkbox-inline m-l-25">
									<input type="checkbox" onclick="checkCheckBox(this)" class="checkOne" value="0" name="constructorSafProCon" <c:if test="${formObject.constructorSafProCon.contains('0')}"> checked </c:if> />
									<i class="input-helper"></i>
									不符合
								</label>
	            			</td>
	            		</tr>
	            		<tr style="height: 40px;">
	            			<td class="title" rowspan="5">
	            				1.2
	            			</td>
	            			<td class="title" rowspan="5">
	            				专职安全<br/>员配备（1<br/>万 M2配1<br/>个，1-5万<br/>M22个， 5<br/>万M2以上3<br/>个）
	            			</td>
	            			<td>
	            				<input maxlength="50" type="text" name="soNamea" value="${formObject.soNamea}" />
	            			</td>
	            			<td>
	            				<input maxlength="50" type="text" name="soSafProCera" value="${formObject.soSafProCera}" />
	            			</td>
	            			<td style="padding-left: 10px;">
	            				<label class="checkbox checkbox-inline m-l-25">
									<input type="checkbox" onclick="checkCheckBox(this)" class="checkOne" value="1" name="soSafProCona" <c:if test="${formObject.soSafProCona.contains('1')}"> checked </c:if> />
									<i class="input-helper"></i>
									符合
								</label>
								<label class="checkbox checkbox-inline m-l-25">
									<input type="checkbox" onclick="checkCheckBox(this)" class="checkOne" value="0" name="soSafProCona" <c:if test="${formObject.soSafProCona.contains('0')}"> checked </c:if> />
									<i class="input-helper"></i>
									不符合
								</label>
	            			</td>
	            		</tr>
	            		<tr style="height: 40px;">
	            			<td>
	            				<input maxlength="50" type="text" name="soNameb" value="${formObject.soNameb}" />
	            			</td>
	            			<td>
	            				<input maxlength="50" type="text" name="soSafProCerb" value="${formObject.soSafProCerb}" />
	            			</td>
	            			<td style="padding-left: 10px;">
	            				<label class="checkbox checkbox-inline m-l-25">
									<input type="checkbox" onclick="checkCheckBox(this)" class="checkOne" value="1" name="soSafProConb" <c:if test="${formObject.soSafProConb.contains('1')}"> checked </c:if> />
									<i class="input-helper"></i>
									符合
								</label>
								<label class="checkbox checkbox-inline m-l-25">
									<input type="checkbox" onclick="checkCheckBox(this)" class="checkOne" value="0" name="soSafProConb" <c:if test="${formObject.soSafProConb.contains('0')}"> checked </c:if> />
									<i class="input-helper"></i>
									不符合
								</label>
	            			</td>
	            		</tr>
	            		<tr style="height: 40px;">
	            			<td>
	            				<input maxlength="50" type="text" name="soNamec" value="${formObject.soNamec}" />
	            			</td>
	            			<td>
	            				<input maxlength="50" type="text" name="soSafProCerc" value="${formObject.soSafProCerc}" />
	            			</td>
	            			<td style="padding-left: 10px;">
	            				<label class="checkbox checkbox-inline m-l-25">
									<input type="checkbox" onclick="checkCheckBox(this)" class="checkOne" value="1" name="soSafProConc" <c:if test="${formObject.soSafProConc.contains('1')}"> checked </c:if> />
									<i class="input-helper"></i>
									符合
								</label>
								<label class="checkbox checkbox-inline m-l-25">
									<input type="checkbox" onclick="checkCheckBox(this)" class="checkOne" value="0" name="soSafProConc" <c:if test="${formObject.soSafProConc.contains('0')}"> checked </c:if> />
									<i class="input-helper"></i>
									不符合
								</label>
	            			</td>
	            		</tr>
	            		<tr style="height: 40px;">
	            			<td>
	            				<input maxlength="50" type="text" name="soNamed" value="${formObject.soNamed}" />
	            			</td>
	            			<td>
	            				<input maxlength="50" type="text" name="soSafProCerd" value="${formObject.soSafProCerd}" />
	            			</td>
	            			<td style="padding-left: 10px;">
	            				<label class="checkbox checkbox-inline m-l-25">
									<input type="checkbox" onclick="checkCheckBox(this)" class="checkOne" value="1" name="soSafProCond" <c:if test="${formObject.soSafProCond.contains('1')}"> checked </c:if> />
									<i class="input-helper"></i>
									符合
								</label>
								<label class="checkbox checkbox-inline m-l-25">
									<input type="checkbox" onclick="checkCheckBox(this)" class="checkOne" value="0" name="soSafProCond" <c:if test="${formObject.soSafProCond.contains('0')}"> checked </c:if> />
									<i class="input-helper"></i>
									不符合
								</label>
	            			</td>
	            		</tr>
	            		<tr style="height: 40px;">
	            			<td>
	            				<input maxlength="50" type="text" name="soNamee" value="${formObject.soNamee}" />
	            			</td>
	            			<td>
	            				<input maxlength="50" type="text" name="soSafProCere" value="${formObject.soSafProCere}" />
	            			</td>
	            			<td style="padding-left: 10px;">
	            				<label class="checkbox checkbox-inline m-l-25">
									<input type="checkbox" onclick="checkCheckBox(this)" class="checkOne" value="1" name="soSafProCone" <c:if test="${formObject.soSafProCone.contains('1')}"> checked </c:if> />
									<i class="input-helper"></i>
									符合
								</label>
								<label class="checkbox checkbox-inline m-l-25">
									<input type="checkbox" onclick="checkCheckBox(this)" class="checkOne" value="0" name="soSafProCone" <c:if test="${formObject.soSafProCone.contains('0')}"> checked </c:if> />
									<i class="input-helper"></i>
									不符合
								</label>
	            			</td>
	            		</tr>
	            		<tr>
	            			<td colspan="6" class="title fontWeight" style="font-size: 14px;height: 50px;">
	            				二、安&nbsp;全&nbsp;生&nbsp;产&nbsp;管&nbsp;理&nbsp;制&nbsp;度
	            			</td>
	            		</tr>
	            		<tr>
	            			<td class="title">
	            				序<br/>号
	            			</td>
	            			<td class="title" colspan="4">
	            				制&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;度&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;称
	            			</td>
	            			<td class="title">
	            				建设单位或建设单位委托的监理单位<br/>对安全生产条件审查、核查情况
	            			</td>
	            		</tr>
	            		<tr>
	            			<td class="title">
	            				2.1
	            			</td>
	            			<td rowspan="4" class="title">
	            				保<br/>证<br/>项<br/>目
	            			</td>
	            			<td class="title textAlignLeft" colspan="3">
	            				是否建立安全生产责任制
	            			</td>
	            			<td style="padding-left: 10px;">
	            				<label class="checkbox checkbox-inline m-l-25">
									<input type="checkbox" onclick="checkCheckBox(this)" class="checkOne" value="1" name="systemSafProCona" <c:if test="${formObject.systemSafProCona.contains('1')}"> checked </c:if> />
									<i class="input-helper"></i>
									是
								</label>
								<label class="checkbox checkbox-inline m-l-25">
									<input type="checkbox" onclick="checkCheckBox(this)" class="checkOne" value="0" name="systemSafProCona" <c:if test="${formObject.systemSafProCona.contains('0')}"> checked </c:if> />
									<i class="input-helper"></i>
									否
								</label>
	            			</td>
	            		</tr>
	            		<tr>
	            			<td class="title" rowspan="3">
	            				2.2
	            			</td>
	            			<td class="title textAlignLeft" colspan="3">
	            				是否建立安全生产教育、培训制度
	            			</td>
	            			<td style="padding-left: 10px;">
	            				<label class="checkbox checkbox-inline m-l-25">
									<input type="checkbox" onclick="checkCheckBox(this)" class="checkOne" value="1" name="systemSafProConb" <c:if test="${formObject.systemSafProConb.contains('1')}"> checked </c:if> />
									<i class="input-helper"></i>
									是
								</label>
								<label class="checkbox checkbox-inline m-l-25">
									<input type="checkbox" onclick="checkCheckBox(this)" class="checkOne" value="0" name="systemSafProConb" <c:if test="${formObject.systemSafProConb.contains('0')}"> checked </c:if> />
									<i class="input-helper"></i>
									否
								</label>
	            			</td>
	            		</tr>
	            		<tr>
	            			<td class="title textAlignLeft" colspan="3">
	            				是否建立安全检查制度
	            			</td>
	            			<td style="padding-left: 10px;">
	            				<label class="checkbox checkbox-inline m-l-25">
									<input type="checkbox" onclick="checkCheckBox(this)" class="checkOne" value="1" name="systemSafProConc" <c:if test="${formObject.systemSafProConc.contains('1')}"> checked </c:if> />
									<i class="input-helper"></i>
									是
								</label>
								<label class="checkbox checkbox-inline m-l-25">
									<input type="checkbox" onclick="checkCheckBox(this)" class="checkOne" value="0" name="systemSafProConc" <c:if test="${formObject.systemSafProConc.contains('0')}"> checked </c:if> />
									<i class="input-helper"></i>
									否
								</label>
	            			</td>
	            		</tr>
	            		<tr>
	            			<td class="title textAlignLeft" colspan="3">
	            				是否建立安全检查制度
	            			</td>
	            			<td style="padding-left: 10px;">
	            				<label class="checkbox checkbox-inline m-l-25">
									<input type="checkbox" onclick="checkCheckBox(this)" class="checkOne" value="1" name="systemSafProCond" <c:if test="${formObject.systemSafProCond.contains('1')}"> checked </c:if> />
									<i class="input-helper"></i>
									是
								</label>
								<label class="checkbox checkbox-inline m-l-25">
									<input type="checkbox" onclick="checkCheckBox(this)" class="checkOne" value="0" name="systemSafProCond" <c:if test="${formObject.systemSafProCond.contains('0')}"> checked </c:if> />
									<i class="input-helper"></i>
									否
								</label>
	            			</td>
	            		</tr>
	            		<tr>
	            			<td class="title" rowspan="4">
	            				2.3
	            			</td>
	            			<td rowspan="4" class="title">
	            				一<br/>般<br/>项<br/>目
	            			</td>
	            			<td class="title textAlignLeft" colspan="3">
	            				是否建立验收制度
	            			</td>
	            			<td style="padding-left: 10px;">
	            				<label class="checkbox checkbox-inline m-l-25">
									<input type="checkbox" onclick="checkCheckBox(this)" class="checkOne" value="1" name="systemSafProCone" <c:if test="${formObject.systemSafProCone.contains('1')}"> checked </c:if> />
									<i class="input-helper"></i>
									是
								</label>
								<label class="checkbox checkbox-inline m-l-25">
									<input type="checkbox" onclick="checkCheckBox(this)" class="checkOne" value="0" name="systemSafProCone" <c:if test="${formObject.systemSafProCone.contains('0')}"> checked </c:if> />
									<i class="input-helper"></i>
									否
								</label>
	            			</td>
	            		</tr>
	            		<tr>
	            			<td class="title textAlignLeft" colspan="3">
	            				是否建立班前安全活动制度
	            			</td>
	            			<td style="padding-left: 10px;">
	            				<label class="checkbox checkbox-inline m-l-25">
									<input type="checkbox" onclick="checkCheckBox(this)" class="checkOne" value="1" name="systemSafProConf" <c:if test="${formObject.systemSafProConf.contains('1')}"> checked </c:if> />
									<i class="input-helper"></i>
									是
								</label>
								<label class="checkbox checkbox-inline m-l-25">
									<input type="checkbox" onclick="checkCheckBox(this)" class="checkOne" value="0" name="systemSafProConf" <c:if test="${formObject.systemSafProConf.contains('0')}"> checked </c:if> />
									<i class="input-helper"></i>
									否
								</label>
	            			</td>
	            		</tr>
	            		<tr>
	            			<td class="title textAlignLeft" colspan="3">
	            				是否建立设备运行、维修、保养管理制度
	            			</td>
	            			<td style="padding-left: 10px;">
	            				<label class="checkbox checkbox-inline m-l-25">
									<input type="checkbox" onclick="checkCheckBox(this)" class="checkOne" value="1" name="systemSafProCong" <c:if test="${formObject.systemSafProCong.contains('1')}"> checked </c:if> />
									<i class="input-helper"></i>
									是
								</label>
								<label class="checkbox checkbox-inline m-l-25">
									<input type="checkbox" onclick="checkCheckBox(this)" class="checkOne" value="0" name="systemSafProCong" <c:if test="${formObject.systemSafProCong.contains('0')}"> checked </c:if> />
									<i class="input-helper"></i>
									否
								</label>
	            			</td>
	            		</tr>
	            		<tr>
	            			<td class="title textAlignLeft" colspan="3">
	            				是否建立安全生产操作规程
	            			</td>
	            			<td style="padding-left: 10px;">
	            				<label class="checkbox checkbox-inline m-l-25">
									<input type="checkbox" onclick="checkCheckBox(this)" class="checkOne" value="1" name="systemSafProConh" <c:if test="${formObject.systemSafProConh.contains('1')}"> checked </c:if> />
									<i class="input-helper"></i>
									是
								</label>
								<label class="checkbox checkbox-inline m-l-25">
									<input type="checkbox" onclick="checkCheckBox(this)" class="checkOne" value="0" name="systemSafProConh" <c:if test="${formObject.systemSafProConh.contains('0')}"> checked </c:if> />
									<i class="input-helper"></i>
									否
								</label>
	            			</td>
	            		</tr>
	            		<tr>
	            			<td colspan="6" class="title fontWeight" style="font-size: 14px;height: 50px;">
	            				三、安&nbsp;全&nbsp;专&nbsp;项&nbsp;措&nbsp;施
	            			</td>
	            		</tr>
	            		<tr>
	            			<td class="title">
	            				序<br/>号
	            			</td>
	            			<td class="title" colspan="4">
	            				方案名称
	            			</td>
	            			<td class="title">
	            				建设单位或建设单位委托的监理单位<br/>对安全生产条件审查、核查情况
	            			</td>
	            		</tr>
	            		<tr>
	            			<td class="title">
	            				3.1
	            			</td>
	            			<td class="title textAlignLeft" colspan="4">
	            				施工临时用电方案
	            			</td>
	            			<td style="padding-left: 10px;">
	            				<label class="checkbox checkbox-inline m-l-25">
									<input type="checkbox" onclick="checkCheckBox(this)" class="checkOne" value="1" name="programSafProCona" <c:if test="${formObject.programSafProCona.contains('1')}"> checked </c:if> />
									<i class="input-helper"></i>
									有
								</label>
								<label class="checkbox checkbox-inline m-l-25">
									<input type="checkbox" onclick="checkCheckBox(this)" class="checkOne" value="0" name="programSafProCona" <c:if test="${formObject.programSafProCona.contains('0')}"> checked </c:if> />
									<i class="input-helper"></i>
									无
								</label>
	            			</td>
	            		</tr>
	            		<tr>
	            			<td class="title">
	            				3.2
	            			</td>
	            			<td class="title textAlignLeft" colspan="4">
	            				人工挖孔桩专项施工方案
	            			</td>
	            			<td style="padding-left: 10px;">
	            				<label class="checkbox checkbox-inline m-l-25">
									<input type="checkbox" onclick="checkCheckBox(this)" class="checkOne" value="1" name="programSafProConb" <c:if test="${formObject.programSafProConb.contains('1')}"> checked </c:if> />
									<i class="input-helper"></i>
									有
								</label>
								<label class="checkbox checkbox-inline m-l-25">
									<input type="checkbox" onclick="checkCheckBox(this)" class="checkOne" value="0" name="programSafProConb" <c:if test="${formObject.programSafProConb.contains('0')}"> checked </c:if> />
									<i class="input-helper"></i>
									无
								</label>
								<label class="checkbox checkbox-inline m-l-25">
									<input type="checkbox" onclick="checkCheckBox(this)" class="checkOne" value="2" name="programSafProConb" <c:if test="${formObject.programSafProConb.contains('2')}"> checked </c:if> />
									<i class="input-helper"></i>
									无此项
								</label>
	            			</td>
	            		</tr>
	            		<tr>
	            			<td class="title">
	            				3.3
	            			</td>
	            			<td class="title textAlignLeft" colspan="4">
	            				基坑支护专项施工方案
	            			</td>
	            			<td style="padding-left: 10px;">
	            				<label class="checkbox checkbox-inline m-l-25">
									<input type="checkbox" onclick="checkCheckBox(this)" class="checkOne" value="1" name="programSafProConc" <c:if test="${formObject.programSafProConc.contains('1')}"> checked </c:if> />
									<i class="input-helper"></i>
									有
								</label>
								<label class="checkbox checkbox-inline m-l-25">
									<input type="checkbox" onclick="checkCheckBox(this)" class="checkOne" value="0" name="programSafProConc" <c:if test="${formObject.programSafProConc.contains('0')}"> checked </c:if> />
									<i class="input-helper"></i>
									无
								</label>
								<label class="checkbox checkbox-inline m-l-25">
									<input type="checkbox" onclick="checkCheckBox(this)" class="checkOne" value="2" name="programSafProConc" <c:if test="${formObject.programSafProConc.contains('2')}"> checked </c:if> />
									<i class="input-helper"></i>
									无此项
								</label>
	            			</td>
	            		</tr>
	            		<tr>
	            			<td class="title">
	            				3.4
	            			</td>
	            			<td class="title textAlignLeft" colspan="4">
	            				事故应急救援预案
	            			</td>
	            			<td style="padding-left: 10px;">
	            				<label class="checkbox checkbox-inline m-l-25">
									<input type="checkbox" onclick="checkCheckBox(this)" class="checkOne" value="1" name="programSafProCond" <c:if test="${formObject.programSafProCond.contains('1')}"> checked </c:if> />
									<i class="input-helper"></i>
									有
								</label>
								<label class="checkbox checkbox-inline m-l-25">
									<input type="checkbox" onclick="checkCheckBox(this)" class="checkOne" value="0" name="programSafProCond" <c:if test="${formObject.programSafProCond.contains('0')}"> checked </c:if> />
									<i class="input-helper"></i>
									无
								</label>
	            			</td>
	            		</tr>
	            		<tr>
	            			<td class="title">
	            				3.5
	            			</td>
	            			<td class="title textAlignLeft" colspan="4">
	            				其他危险性较大的分部分项专项施工方案
	            			</td>
	            			<td style="padding-left: 10px;">
	            				<label class="checkbox checkbox-inline m-l-25">
									<input type="checkbox" onclick="checkCheckBox(this)" class="checkOne" value="1" name="programSafProCone" <c:if test="${formObject.programSafProCone.contains('1')}"> checked </c:if> />
									<i class="input-helper"></i>
									有
								</label>
								<label class="checkbox checkbox-inline m-l-25">
									<input type="checkbox" onclick="checkCheckBox(this)" class="checkOne" value="0" name="programSafProCone" <c:if test="${formObject.programSafProCone.contains('0')}"> checked </c:if> />
									<i class="input-helper"></i>
									无
								</label>
								<label class="checkbox checkbox-inline m-l-25">
									<input type="checkbox" onclick="checkCheckBox(this)" class="checkOne" value="2" name="programSafProCone" <c:if test="${formObject.programSafProCone.contains('2')}"> checked </c:if> />
									<i class="input-helper"></i>
									无此项
								</label>
	            			</td>
	            		</tr>
	            		<tr>
	            			<td colspan="6" class="title fontWeight" style="font-size: 14px;height: 50px;">
	            				四、现&nbsp;场&nbsp;安&nbsp;全&nbsp;条&nbsp;件&nbsp;检&nbsp;查
	            			</td>
	            		</tr>
	            		<tr>
	            			<td class="title">
	            				序<br/>号
	            			</td>
	            			<td class="title" colspan="4">
	            				标志、设施
	            			</td>
	            			<td class="title">
	            				建设单位或建设单位委托的监理单位<br/>对安全生产条件审查、核查情况
	            			</td>
	            		</tr>
	            		<tr>
	            			<td class="title">
	            				4.1
	            			</td>
	            			<td class="title" rowspan="6">
	            				保<br/>证<br/>项<br/>目
	            			</td>
	            			<td class="title textAlignLeft" colspan="3">
	            				工地围档（围墙、围档板）
	            			</td>
	            			<td style="padding-left: 10px;">
	            				<label class="checkbox checkbox-inline m-l-25">
									<input type="checkbox" onclick="checkCheckBox(this)" class="checkOne" value="1" name="markSafProCona" <c:if test="${formObject.markSafProCona.contains('1')}"> checked </c:if> />
									<i class="input-helper"></i>
									符合
								</label>
								<label class="checkbox checkbox-inline m-l-25">
									<input type="checkbox" onclick="checkCheckBox(this)" class="checkOne" value="0" name="markSafProCona" <c:if test="${formObject.markSafProCona.contains('0')}"> checked </c:if> />
									<i class="input-helper"></i>
									不符合
								</label>
	            			</td>
	            		</tr>
	            		<tr>
	            			<td class="title">
	            				4.2
	            			</td>
	            			<td class="title textAlignLeft" colspan="3">
	            				工地门楼（包括企业标志）
	            			</td>
	            			<td style="padding-left: 10px;">
	            				<label class="checkbox checkbox-inline m-l-25">
									<input type="checkbox" onclick="checkCheckBox(this)" class="checkOne" value="1" name="markSafProConb" <c:if test="${formObject.markSafProConb.contains('1')}"> checked </c:if> />
									<i class="input-helper"></i>
									符合
								</label>
								<label class="checkbox checkbox-inline m-l-25">
									<input type="checkbox" onclick="checkCheckBox(this)" class="checkOne" value="0" name="markSafProConb" <c:if test="${formObject.markSafProConb.contains('0')}"> checked </c:if> />
									<i class="input-helper"></i>
									不符合
								</label>
	            			</td>
	            		</tr>
	            		<tr>
	            			<td class="title">
	            				4.3
	            			</td>
	            			<td class="title textAlignLeft" colspan="3">
	            				五牌一图的设置
	            			</td>
	            			<td style="padding-left: 10px;">
	            				<label class="checkbox checkbox-inline m-l-25">
									<input type="checkbox" onclick="checkCheckBox(this)" class="checkOne" value="1" name="markSafProConc" <c:if test="${formObject.markSafProConc.contains('1')}"> checked </c:if> />
									<i class="input-helper"></i>
									符合
								</label>
								<label class="checkbox checkbox-inline m-l-25">
									<input type="checkbox" onclick="checkCheckBox(this)" class="checkOne" value="0" name="markSafProConc" <c:if test="${formObject.markSafProConc.contains('0')}"> checked </c:if> />
									<i class="input-helper"></i>
									不符合
								</label>
	            			</td>
	            		</tr>
	            		<tr>
	            			<td class="title">
	            				4.4
	            			</td>
	            			<td class="title textAlignLeft" colspan="3">
	            				大门洗车槽
	            			</td>
	            			<td style="padding-left: 10px;">
	            				<label class="checkbox checkbox-inline m-l-25">
									<input type="checkbox" onclick="checkCheckBox(this)" class="checkOne" value="1" name="markSafProCond" <c:if test="${formObject.markSafProCond.contains('1')}"> checked </c:if> />
									<i class="input-helper"></i>
									符合
								</label>
								<label class="checkbox checkbox-inline m-l-25">
									<input type="checkbox" onclick="checkCheckBox(this)" class="checkOne" value="0" name="markSafProCond" <c:if test="${formObject.markSafProCond.contains('0')}"> checked </c:if> />
									<i class="input-helper"></i>
									不符合
								</label>
	            			</td>
	            		</tr>
	            		<tr>
	            			<td class="title">
	            				4.5
	            			</td>
	            			<td class="title textAlignLeft" colspan="3">
	            				临时设施
	            			</td>
	            			<td style="padding-left: 10px;">
	            				<label class="checkbox checkbox-inline m-l-25">
									<input type="checkbox" onclick="checkCheckBox(this)" class="checkOne" value="1" name="markSafProCone" <c:if test="${formObject.markSafProCone.contains('1')}"> checked </c:if> />
									<i class="input-helper"></i>
									符合
								</label>
								<label class="checkbox checkbox-inline m-l-25">
									<input type="checkbox" onclick="checkCheckBox(this)" class="checkOne" value="0" name="markSafProCone" <c:if test="${formObject.markSafProCone.contains('0')}"> checked </c:if> />
									<i class="input-helper"></i>
									不符合
								</label>
	            			</td>
	            		</tr>
	            		<tr>
	            			<td class="title">
	            				4.6
	            			</td>
	            			<td class="title textAlignLeft" colspan="3">
	            				其他防护或外电防护
	            			</td>
	            			<td style="padding-left: 10px;">
	            				<label class="checkbox checkbox-inline m-l-25">
									<input type="checkbox" onclick="checkCheckBox(this)" class="checkOne" value="1" name="markSafProConf" <c:if test="${formObject.markSafProConf.contains('1')}"> checked </c:if> />
									<i class="input-helper"></i>
									符合
								</label>
								<label class="checkbox checkbox-inline m-l-25">
									<input type="checkbox" onclick="checkCheckBox(this)" class="checkOne" value="0" name="markSafProConf" <c:if test="${formObject.markSafProConf.contains('0')}"> checked </c:if> />
									<i class="input-helper"></i>
									不符合
								</label>
	            			</td>
	            		</tr>
	            		<tr>
	            			<td class="title">
	            				4.7
	            			</td>
	            			<td class="title" rowspan="3">
	            				一<br/>般<br/>项<br/>目
	            			</td>
	            			<td class="title textAlignLeft" colspan="3">
	            				重大危险源公示牌
	            			</td>
	            			<td style="padding-left: 10px;">
	            				<label class="checkbox checkbox-inline m-l-25">
									<input type="checkbox" onclick="checkCheckBox(this)" class="checkOne" value="1" name="markSafProCong" <c:if test="${formObject.markSafProCong.contains('1')}"> checked </c:if> />
									<i class="input-helper"></i>
									符合
								</label>
								<label class="checkbox checkbox-inline m-l-25">
									<input type="checkbox" onclick="checkCheckBox(this)" class="checkOne" value="0" name="markSafProCong" <c:if test="${formObject.markSafProCong.contains('0')}"> checked </c:if> />
									<i class="input-helper"></i>
									不符合
								</label>
	            			</td>
	            		</tr>
	            		<tr>
	            			<td class="title">
	            				4.8
	            			</td>
	            			<td class="title textAlignLeft" colspan="3">
	            				场内硬化处理
	            			</td>
	            			<td style="padding-left: 10px;">
	            				<label class="checkbox checkbox-inline m-l-25">
									<input type="checkbox" onclick="checkCheckBox(this)" class="checkOne" value="1" name="markSafProConh" <c:if test="${formObject.markSafProConh.contains('1')}"> checked </c:if> />
									<i class="input-helper"></i>
									符合
								</label>
								<label class="checkbox checkbox-inline m-l-25">
									<input type="checkbox" onclick="checkCheckBox(this)" class="checkOne" value="0" name="markSafProConh" <c:if test="${formObject.markSafProConh.contains('0')}"> checked </c:if> />
									<i class="input-helper"></i>
									不符合
								</label>
	            			</td>
	            		</tr>
	            		<tr>
	            			<td class="title">
	            				4.9
	            			</td>
	            			<td class="title textAlignLeft" colspan="3">
	            				现场卫生及材料堆放
	            			</td>
	            			<td style="padding-left: 10px;">
	            				<label class="checkbox checkbox-inline m-l-25">
									<input type="checkbox" onclick="checkCheckBox(this)" class="checkOne" value="1" name="markSafProConi" <c:if test="${formObject.markSafProConi.contains('1')}"> checked </c:if> />
									<i class="input-helper"></i>
									符合
								</label>
								<label class="checkbox checkbox-inline m-l-25">
									<input type="checkbox" onclick="checkCheckBox(this)" class="checkOne" value="0" name="markSafProConi" <c:if test="${formObject.markSafProConi.contains('0')}"> checked </c:if> />
									<i class="input-helper"></i>
									不符合
								</label>
	            			</td>
	            		</tr>
		            </table>
	                <br/><br/>
	                <table class="editTable" style="width: 960px;" border="0" cellspacing="0" cellpadding="0">
	                	<tbody>
	                		<tr>
	                			<td class="title" style="width: 15%;">
	                				项目名称
	                			</td>
	                			<td style="width: 35%;">
	                				<input maxlength="200" class="required base" type="text" name="prjName" value="${formObject.prjName}" />
	                			</td>
	                			<td class="title" style="width: 15%;">
	                				项目地址
	                			</td>
	                			<td style="width: 35%;">
	                				<input maxlength="50" class=" base" type="text" name="prjAddr" value="${formObject.prjAddr}" />
	                			</td>
	                		</tr>
	                		<tr>
	                			<td class="title">
	                				建设单位法人
	                			</td>
	                			<td style="width: 35%;">
	                				<input maxlength="50" class=" base" type="text" name="buildLegal" value="${formObject.buildLegal}" />
	                			</td>
	                			<td class="title" style="width: 15%;">
	                				施工单位法人
	                			</td>
	                			<td style="width: 35%;">
	                				<input maxlength="50" class=" base" type="text" name="constuctionLegal" value="${formObject.constuctionLegal}" />
	                			</td>
	                		</tr>
	                		<tr>
	                			<td class="title">
	                				监理单位法人
	                			</td>
	                			<td style="width: 35%;">
	                				<input maxlength="50" class=" base" type="text" name="superiorLegal" value="${formObject.superiorLegal}" />
	                			</td>
	                			<td class="title" style="width: 15%;">
	                				施工单位建造师
	                			</td>
	                			<td style="width: 35%;">
	                				<input maxlength="50" class=" base" type="text" name="conConstructor" value="${formObject.conConstructor}" />
	                			</td>
	                		</tr>
	                		<tr>
	                			<td class="title">
	                				监理单位项目总监
	                			</td>
	                			<td style="width: 35%;">
	                				<input maxlength="50" class=" base" type="text" name="superiorProDirector" value="${formObject.superiorProDirector}" />
	                			</td>
	                			<td colspan="2"></td>
	                		</tr>
	                	</tbody>
	                </table>
		            <br/><br/>
		            <table class="editTable noBorder" style="width: 960px;" border="0" cellspacing="0" cellpadding="0">
		            	<tbody>
		            		<tr style="border: none;">
		            			<td style="width: 10%"></td>
		            			<td style="padding-left: 20px;" colspan="2">
		            				建设单位（公章）：
		            			</td>
		            		</tr>
		            		<tr style="border: none;">
		            			<td></td>
		            			<td style="padding-left: 20px;" colspan="2">
		            				法定代表人（签名）：
		            			</td>
		            		</tr>
		            		<tr style="border: none;">
		            			<td colspan="2"></td>
		            			<td style="width: 25%">
	                 				<input class=" base" type="text" 
		                 				onClick="WdatePicker()" placeholder="点击填日期" 
		                 				value='<fmt:formatDate value="${formObject.buildDate}" 
		                 				pattern="yyyy-MM-dd" />' name="buildDate" />
		            			</td>
		            		</tr>
		            		<tr style="border: none;">
		            			<td></td>
		            			<td style="padding-left: 20px;" colspan="2">
		            				施工单位（公章）：
		            			</td>
		            		</tr>
		            		<tr style="border: none;">
		            			<td></td>
		            			<td style="padding-left: 20px;" colspan="2">
		            				法定代表人（签名）：
		            			</td>
		            		</tr>
		            		<tr style="border: none;">
		            			<td></td>
		            			<td style="padding-left: 20px;" colspan="2">
		            				建造师（签名）：
		            			</td>
		            		</tr>
		            		<tr style="border: none;">
		            			<td colspan="2"></td>
		            			<td>
	                 				<input class=" base" type="text" 
		                 				onClick="WdatePicker()" placeholder="点击填日期" 
		                 				value='<fmt:formatDate value="${formObject.constuctionDate}" 
		                 				pattern="yyyy-MM-dd" />' name="constuctionDate" />
		            			</td>
		            		</tr>
		            		<tr style="border: none;">
		            			<td></td>
		            			<td style="padding-left: 20px;" colspan="2">
		            				监理单位（公章）：
		            			</td>
		            		</tr>
		            		<tr style="border: none;">
		            			<td></td>
		            			<td style="padding-left: 20px;" colspan="2">
		            				法定代表人（签名）：
		            			</td>
		            		</tr>
		            		<tr style="border: none;">
		            			<td></td>
		            			<td style="padding-left: 20px;" colspan="2">
		            				项目总监（签名、注册章）：
		            			</td>
		            		</tr>
		            		<tr style="border: none;">
		            			<td colspan="2"></td>
		            			<td>
	                 				<input class=" base" type="text" 
		                 				onClick="WdatePicker()" placeholder="点击填日期" 
		                 				value='<fmt:formatDate value="${formObject.superiorDate}" 
		                 				pattern="yyyy-MM-dd" />' name="superiorDate" />
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