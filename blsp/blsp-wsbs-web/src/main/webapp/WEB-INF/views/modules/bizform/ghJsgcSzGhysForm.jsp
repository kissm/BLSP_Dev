<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>珠海市建设工程（市政类）规划条件核实申请表</title>
    <meta name="decorator" content="blank" />
    <style>
		.editTable input[type='text'].has-error {
			background: #F44336;
			color: #FFF;
		}
	</style>
    <script>
    	$(document).ready(function(){
	    	$("#ghJsgcSzGhysForm").validate({
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
	    	$("#ghJsgcSzGhysForm").validate({
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
			if($("#ghJsgcSzGhysForm").valid()){
				$("#ghJsgcSzGhysForm").submit();
			}else{
				$(".savebutton").attr("disabled",false);
			}
		}
		function refresh(){
			window.location.reload(true);
		}
		function addMetRow(id){
			var tr = $('#'+id).clone();
			$(tr).removeAttr('id');
			$(tr).find('input').val('');
			var content = $('.'+id+':last').find('td:eq(1)').text().trim();
			var num = content.substring(0,content.length-1);
			$(tr).find('td:eq(1)').text(eval(eval(num)+1)+"、");
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
		function removeMetRow(id){
			var rows = $("#changeMetRows").prop("rowspan");
			$("#changeMetRows").attr("rowspan",rows-1);
			$("#"+id).remove();
		}
		$(function (){
			var metList = "${formObject.metList}";
			if(metList.length > 0){
				$('tr[name="metTr"]').remove();
			}
			var flg = 0;
			$(".met").each(function (){
				flg++;
			});
			var rows = $("#changeMetRows").prop("rowspan");
			$("#changeMetRows").attr("rowspan",rows+flg-1);
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
	    <form id="ghJsgcSzGhysForm" action="${ctx}/ghJsgcSzGhys/save" method="post">
		    <div class="card-body card-padding">
	        	<div class="row">
		            <input type="hidden" name="id"
		                   value="${formObject.id}" class="base">
	                <input type="hidden" name="prjId"
	                 value="${formObject.prjId}" class="base">
	                <input type="hidden" name="taskId"
	                 value="${formObject.taskId}" class="base">
	                 <table class="editTable" style="width: 960px;" border="0" cellspacing="0" cellpadding="0">
	                 	<tbody>
	                 		<tr>
	                 			<td class="title" colspan="10" style="font-size: 16px;font-weight: bolder;">
	                 				珠海市建设工程（市政类）规划条件核实申请表
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title" colspan="3">
	                 				建设项目名称
	                 			</td>
	                 			<td colspan="7">
	                 				<input maxlength="200" class="required base" type="text" name="prjName" value="${formObject.prjName}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title" colspan="3">
	                 				<!-- 建设项目地址 -->
	                 				建设位置
	                 			</td>
	                 			<td colspan="7">
	                 				<input maxlength="50" class=" base" type="text" name="prjAddr" value="${formObject.prjAddr}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title" rowspan="3" width="40px">
	                 				申<br/>请<br/>人
	                 			</td>
	                 			<td rowspan="2" class="title" colspan="2">
	                 				名称<br/>（姓名）
	                 			</td>
	                 			<td rowspan="2" colspan="3">
	                 				<input maxlength="50" class=" base" type="text" name="proposerName" value="${formObject.proposerName}" />
	                 			</td>
	                 			<td class="title">
	                 				地址
	                 			</td>
	                 			<td colspan="3">
	                 				<input maxlength="50" class=" base" type="text" name="proposerAdd" value="${formObject.proposerAdd}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title">
	                 				电话
	                 			</td>
	                 			<td>
	                 				<input maxlength="15" class=" base phone" type="text" name="proposerTel" value="${formObject.proposerTel}" />
	                 			</td>
	                 			<td class="title">
	                 				邮政编码
	                 			</td>
	                 			<td>
	                 				<input maxlength="20" class=" base" type="text" name="proposerPostcode" value="${formObject.proposerPostcode}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td class="title" colspan="2">
	                 				受委托人
	                 			</td>
	                 			<td colspan="2">
	                 				<input maxlength="50" class=" base" type="text" name="trusteeName" value="${formObject.trusteeName}" />
	                 			</td>
	                 			<td class="title">
	                 				联系电话
	                 			</td>
	                 			<td style="text-align: right;border-right: none;">
	                 				办公：
	                 			</td>
	                 			<td style="border-left: none;border-right: none;">
	                 				<input maxlength="15" class=" base phone" type="text" name="trusteePhone" value="${formObject.trusteePhone}" placeholder="点此填写" />
	                 			</td>
	                 			<td style="text-align: right;border-left: none;border-right: none;">
	                 				手机：
	                 			</td>
	                 			<td style="border-left: none;">
	                 				<input maxlength="15" class=" base mobile" type="text" name="trusteeMobile" value="${formObject.trusteeMobile}" placeholder="点此填写" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td rowspan="5" class="title">
	                 				<!-- 竣工<br/>项目 -->
	                 				申请<br/>范围
	                 			</td>
	                 			<td colspan="5" style="padding-left: 10px;">
	                 				<label class="checkbox checkbox-inline m-r-10">
										<input type="checkbox" class="base" value="A" name="completeProject" <c:if test="${formObject.completeProject.contains('A')}"> checked </c:if> />
										<i class="input-helper"></i>    
										道路工程
									</label>
									<label class="checkbox checkbox-inline m-r-10">
										<input type="checkbox" class="base" value="B" name="completeProject" <c:if test="${formObject.completeProject.contains('B')}"> checked </c:if> />
										<i class="input-helper"></i>    
										桥梁工程
									</label>
									<label class="checkbox checkbox-inline m-r-10">
										<input type="checkbox" class="base" value="C" name="completeProject" <c:if test="${formObject.completeProject.contains('C')}"> checked </c:if> />
										<i class="input-helper"></i>    
										架空线路
									</label>
									<label class="checkbox checkbox-inline m-r-10">
										<input type="checkbox" class="base" value="D" name="completeProject" <c:if test="${formObject.completeProject.contains('D')}"> checked </c:if> />
										<i class="input-helper"></i>    
										埋地管线
									</label>
	                 			</td>
	                 			<td colspan="2" class="title">
	                 				原审批
	                 			</td>
	                 			<td colspan="2" class="title">
	                 				竣工测量结果
	                 			</td>
	                 		</tr>
	                 		<%-- 根据代填资料  15版无此项 --%>
	                 		<%-- <tr>
	                 			<td colspan="2" class="title">
	                 				工程内容
	                 			</td>
	                 			<td class="title">
	                 				工程内容
	                 			</td>
	                 			<td class="title">
	                 				工程内容
	                 			</td>
	                 			<td class="title">
	                 				工程内容
	                 			</td>
	                 			<td colspan="2">
	                 				<input maxlength="50" class=" base" type="text" name="approvalContent" value="${formObject.approvalContent}" />
	                 			</td>
	                 			<td colspan="2">
	                 				<input maxlength="50" class=" base" type="text" name="measureContent" value="${formObject.measureContent}" />
	                 			</td>
	                 		</tr> --%>
	                 		<tr>
	                 			<td colspan="2" class="title">
	                 				起止点
	                 			</td>
	                 			<td class="title">
	                 				跨越区域
	                 			</td>
	                 			<td class="title">
	                 				起止点
	                 			</td>
	                 			<td class="title">
	                 				起止点
	                 			</td>
	                 			<td colspan="2">
	                 				<input maxlength="50" class=" base" type="text" name="approvalStartStop" value="${formObject.approvalStartStop}" />
	                 			</td>
	                 			<td colspan="2">
	                 				<input maxlength="50" class=" base" type="text" name="measureStartStop" value="${formObject.measureStartStop}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td colspan="2" class="title">
	                 				断面形式
	                 			</td>
	                 			<td class="title">
	                 				桥面宽度
	                 			</td>
	                 			<td class="title">
	                 				线路规格
	                 			</td>
	                 			<td class="title">
	                 				管线规格
	                 			</td>
	                 			<td colspan="2">
	                 				<input maxlength="50" class=" base" type="text" name="approvalSpec" value="${formObject.approvalSpec}" />
	                 			</td>
	                 			<td colspan="2">
	                 				<input maxlength="50" class=" base" type="text" name="measureSpec" value="${formObject.measureSpec}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td colspan="2" class="title">
	                 				道路长度
	                 			</td>
	                 			<td class="title">
	                 				桥梁长度
	                 			</td>
	                 			<td class="title">
	                 				线路长度
	                 			</td>
	                 			<td class="title">
	                 				管线长度
	                 			</td>
	                 			<td colspan="2">
	                 				<input maxlength="50" class=" base" type="text" name="approvalLenght" value="${formObject.approvalLenght}" />
	                 			</td>
	                 			<td colspan="2">
	                 				<input maxlength="50" class=" base" type="text" name="measureLenght" value="${formObject.measureLenght}" />
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td colspan="2" class="title">
	                 				道路宽度
	                 			</td>
	                 			<td class="title">
	                 				净空高度
	                 			</td>
	                 			<td class="title">
	                 				走廊面积
	                 			</td>
	                 			<td class="title">
	                 				占地面积
	                 			</td>
	                 			<td colspan="2">
	                 				<input maxlength="50" class=" base" type="text" name="approvalSpace" value="${formObject.approvalSpace}" />
	                 			</td>
	                 			<td colspan="2">
	                 				<input maxlength="50" class=" base" type="text" name="measureSpace" value="${formObject.measureSpace}" />
	                 			</td>
	                 		</tr>
	                 		<%-- 根据代填资料  15版无此项 --%>
	                 		<%-- <tr>
	                 			<td colspan="2" class="title">
	                 				工程造价
	                 			</td>
	                 			<td class="title">
	                 				工程造价
	                 			</td>
	                 			<td class="title">
	                 				工程造价
	                 			</td>
	                 			<td class="title">
	                 				工程造价
	                 			</td>
	                 			<td colspan="2">
	                 				<input maxlength="50" class=" base" type="text" name="approvalPrjCost" value="${formObject.approvalPrjCost}" />
	                 			</td>
	                 			<td colspan="2">
	                 				<input maxlength="50" class=" base" type="text" name="measurePrjCost" value="${formObject.measurePrjCost}" />
	                 			</td>
	                 		</tr> --%>
	                 		<%-- 根据代填资料  15版无此项 --%>
	                 		<%-- <tr>
	                 			<td colspan="2" class="title">
	                 				其它
	                 			</td>
	                 			<td class="title">
	                 				其它
	                 			</td>
	                 			<td class="title">
	                 				其它
	                 			</td>
	                 			<td class="title">
	                 				其它
	                 			</td>
	                 			<td colspan="2">
	                 				<input maxlength="50" class=" base" type="text" name="approvalOther" value="${formObject.approvalOther}" />
	                 			</td>
	                 			<td colspan="2">
	                 				<input maxlength="50" class=" base" type="text" name="measureOther" value="${formObject.measureOther}" />
	                 			</td>
	                 		</tr> --%>
	                 		<tr>
	                 			<td rowspan="2" class="title" id="changeMetRows">
	                 				<!-- 所需<br/>资料 -->
	                 				申请<br/>资料
	                 			</td>
	                 			<td colspan="6" class="title">
	                 				<!-- 所&nbsp;需&nbsp;文&nbsp;件&nbsp;内&nbsp;容（须参照所需资料规定填写） -->
	                 				申请资料（须按照所需资料的规定填写）
	                 			</td>
	                 			<td colspan="2" class="title">
	                 				文&nbsp;件&nbsp;编&nbsp;号
	                 			</td>
	                 			<td class="title">
	                 				<!-- 张数 -->
	                 				页数
	                 			</td>
	                 		</tr>
	                 		<tr id="met" class="met" name="metTr">
	                 			<td>
	                 				<button type="button" onclick="addMetRow('met')" class="btn btn-primary"><i class="md add"></i></button>
	                 			</td>
	                 			<td style="border-right: none;">
	                 				1、
	                 			</td>
	                 			<td colspan="4" style="border-left: none;">
	                 				<input maxlength="200" type="text"  name="mContent"/>
	                 			</td>
	                 			<td colspan="2">
	                 				<input maxlength="50" type="text"  name="mCode"/>
	                 			</td>
	                 			<td>
	                 				<input maxlength="50" type="text"  name="mPageNum"/>
	                 			</td>
	                 		</tr>
	                 		<c:forEach items="${formObject.metList}" var="met" varStatus="i">
	                 			<c:if test="${i.index == 0}">
	                 				<tr id="met" class="met">
			                 			<td>
			                 				<button type="button" onclick="addMetRow('met')" class="btn btn-primary"><i class="md add"></i></button>
			                 				<input type="hidden" value="${met.mId}" name="mId" />
			                 			</td>
			                 			<td width="40px" style="border-right: none;">
			                 				<input type="hidden" value="${met.gId}" name="gId" />
			                 				1、
			                 			</td>
			                 			<td colspan="4" style="border-left: none;">
			                 				<input maxlength="200" type="text"  name="mContent" value="${met.mContent}" />
			                 			</td>
			                 			<td colspan="2">
			                 				<input maxlength="50" type="text"  name="mCode" value="${met.mCode}" />
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="mPageNum" value="${met.mPageNum}" />
			                 			</td>
			                 		</tr>
	                 			</c:if>
	                 			<c:if test="${i.index > 0}">
	                 				<tr id="${met.mId}" class="met">
			                 			<td>
			                 				<button type="button" onclick="removeMetRow('${met.mId}')" class="btn btn-danger"><i class="md minus"></i></button>
			                 				<input type="hidden" value="${met.mId}" name="mId" />
			                 			</td>
			                 			<td width="40px" style="border-right: none;">
			                 				<input type="hidden" value="${met.gId}" name="gId" />
			                 				${i.index+1}、
			                 			</td>
			                 			<td colspan="4" style="border-left: none;">
			                 				<input maxlength="200" type="text"  name="mContent" value="${met.mContent}" />
			                 			</td>
			                 			<td colspan="2">
			                 				<input maxlength="50" type="text"  name="mCode" value="${met.mCode}" />
			                 			</td>
			                 			<td>
			                 				<input maxlength="50" type="text"  name="mPageNum" value="${met.mPageNum}" />
			                 			</td>
			                 		</tr>
	                 			</c:if>
	                 		</c:forEach>
	                 		<tr>
	                 			<td class="title" rowspan="3">
	                 				<!-- 备注 -->
	                 				签章
	                 			</td>
	                 			<td colspan="9" style="text-indent: 2em;height: 60px;border-bottom: none;padding-left: 20px;padding-right: 20px;">
	                 				       我单位（本人）已阅知有关备注说明，并承诺对申报资料的真实性及数据的准确性（含
	                 				电子文件与图纸的一致性）负责，若有任何虚报、瞒报、造假等不正当手段，审批机关可终
	                 				止审理，我单位（本人）自愿承担虚报、瞒报、造假等不正当手段而产生的一切法律责任。
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td colspan="4" style="padding-left: 20px;border-right: none;border-top: none;border-bottom: none;height: 40px;">
	                 				（申请单位盖章/申请个人签名）
	                 			</td>
	                 			<td colspan="2" style="border:none;">
	                 				（工程施工单位盖章）
	                 			</td>
	                 			<td style="border: none;"></td>
	                 			<td colspan="2" style="border-left: none;border-top: none;border-bottom: none;">
	                 				（工程设计单位盖章）
	                 			</td>
	                 		</tr>
	                 		<tr>
	                 			<td colspan="4" style="padding-left: 40px;border-right: none;border-top: none;height: 40px;">
	                 				<input class=" base" type="text" 
		                 				onClick="WdatePicker()" placeholder="点击填日期" 
		                 				value='<fmt:formatDate value="${formObject.applyDate}" 
		                 				pattern="yyyy-MM-dd" />' name="applyDate" />
	                 			</td>
	                 			<td colspan="2" style="border-left: none;border-right: none;border-top: none;">
	                 				<input class=" base" type="text" 
		                 				onClick="WdatePicker()" placeholder="点击填日期" 
		                 				value='<fmt:formatDate value="${formObject.constructDate}" 
		                 				pattern="yyyy-MM-dd" />' name="constructDate" />
	                 			</td>
	                 			<td style="border-left: none;border-right: none;border-top: none;"></td>
	                 			<td colspan="2" style="border-left: none;border-top: none;">
	                 				<input class=" base" type="text" 
		                 				onClick="WdatePicker()" placeholder="点击填日期" 
		                 				value='<fmt:formatDate value="${formObject.designDate}" 
		                 				pattern="yyyy-MM-dd" />' name="designDate" />
	                 			</td>
	                 		</tr>
	                 	</tbody>
	                 </table>
	                 <input type="hidden" name="jsonDataObj" id="jsonDataObj"/>
	                 <input type="hidden" name="jsonDataMetList" id="jsonDataMetList"/>
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