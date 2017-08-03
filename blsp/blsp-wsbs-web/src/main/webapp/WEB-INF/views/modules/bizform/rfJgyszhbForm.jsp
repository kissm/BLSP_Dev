<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>人防工程竣工验收综合表</title>
    <meta name="decorator" content="blank" />
    <style>
		.editTable input[type='text'].has-error {
			background: #F44336;
			color: #FFF;
		}
	</style>
    <script>
    	$(document).ready(function(){
    		$("#rfjgyszhbForm").validate({
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
			$(".savebutton").attr("disabled",true);
			if($("#rfjgyszhbForm").valid()){
				$("#rfjgyszhbForm").submit();
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
	<form id="rfjgyszhbForm" action="${ctx}/rfjgyszhb/save" method="post">
	    <div class="card-body card-padding">
	        <div class="row">
	            <input type="hidden" name="id"
	            	value="${formObject.id}"/>
                <input type="hidden" name="prjId" value="${formObject.prjId}" />
                <input type="hidden" name="taskId" value="${formObject.taskId}" />
	            <table class="editTable" style="width: 960px;" border="0" cellspacing="0" cellpadding="0">
                	<tbody>
                		<tr>
	                		<td class="title" colspan="4" style="font-size: 16px;font-weight: bolder;">
	               				人防工程专项竣工验收审核
	               			</td>
	                	</tr>
	                	<tr>
	                		<td class="title" style="width: 15%;">
	                			报建编号
	                		</td>
	                		<td style="width: 35%;">
	                			<input maxlength="50" class=" base" type="text" name="bjbh" value="${formObject.bjbh}" />
	                		</td>
	                		<td class="title" style="width: 15%;">
	                			工程名称
	                		</td>
	                		<td style="width: 35%;">
	                			<input maxlength="200" class="required base" type="text" name="prjName" value="${formObject.prjName}" />
	                		</td>
	                	</tr>
	                	<tr>
	                		<td class="title">
	                			工程地点
	                		</td>
	                		<td>
	                			<input maxlength="50" class=" base" type="text" name="prjAddr" value="${formObject.prjAddr}" />
	                		</td>
	                		<td class="title">
	                			建设单位
	                		</td>
	                		<td>
	                			<input maxlength="200" class=" base" type="text" name="company" value="${formObject.company}" />
	                		</td>
	                	</tr>
	                	<tr>
	                		<td class="title">
	                			建设单位联系人
	                		</td>
	                		<td>
	                			<input maxlength="50" class=" base" type="text" name="linkman" value="${formObject.linkman}" />
	                		</td>
	                		<td class="title">
	                			联系人电话办公电话
	                		</td>
	                		<td>
	                			<input maxlength="15" class=" base phone" type="text" name="linkmanOfficephone" value="${formObject.linkmanOfficephone}" />
	                		</td>
	                	</tr>
	                	<tr>
	                		<td class="title">
	                			联系人电话手机
	                		</td>
	                		<td>
	                			<input maxlength="15" class=" base mobile" type="text" name="linkmanMobilephone" value="${formObject.linkmanMobilephone}" />
	                		</td>
	                		<td class="title">
	                			地面建筑竣工总面积(m<sup>2</sup>)
	                		</td>
	                		<td>
	                			<input maxlength="50" class=" base" type="text" name="dmjzjgzmj" value="${formObject.dmjzjgzmj}" />
	                		</td>
	                	</tr>
	                	<tr>
	                		<td class="title">
	                			层数A
	                		</td>
	                		<td>
	                			<input maxlength="50" class=" base" type="text" name="pliesNumA" value="${formObject.pliesNumA}" />
	                		</td>
	                		<td class="title">
	                			栋数A
	                		</td>
	                		<td>
	                			<input maxlength="50" class=" base" type="text" name="ridgepoleNumA" value="${formObject.ridgepoleNumA}" />
	                		</td>
	                	</tr>
	                	<tr>
	                		<td class="title">
	                			层数B
	                		</td>
	                		<td>
	                			<input maxlength="50" class=" base" type="text" name="pliesNumB" value="${formObject.pliesNumB}" />
	                		</td>
	                		<td class="title">
	                			栋数B
	                		</td>
	                		<td>
	                			<input maxlength="50" class=" base" type="text" name="ridgepoleNumB" value="${formObject.ridgepoleNumB}" />
	                		</td>
	                	</tr>
	                	<tr>
	                		<td class="title">
	                			层数C
	                		</td>
	                		<td>
	                			<input maxlength="50" class=" base" type="text" name="pliesNumC" value="${formObject.pliesNumC}" />
	                		</td>
	                		<td class="title">
	                			栋数C
	                		</td>
	                		<td>
	                			<input maxlength="50" class=" base" type="text" name="ridgepoleNumC" value="${formObject.ridgepoleNumC}" />
	                		</td>
	                	</tr>
	                	<tr>
	                		<td class="title">
	                			地下建筑竣工总面积(m<sup>2</sup>)
	                		</td>
	                		<td>
	                			<input maxlength="50" class=" base" type="text" name="dxjzjgzmj" value="${formObject.dxjzjgzmj}" />
	                		</td>
	                		<td class="title">
	                			人防地下室竣工总面积(m<sup>2</sup>)
	                		</td>
	                		<td>
	                			<input maxlength="50" class=" base" type="text" name="rfdxsjgzmj" value="${formObject.rfdxsjgzmj}" />
	                		</td>
	                	</tr>
	                	<tr>
	                		<td class="title">
	                			人防地下室平时用途
	                		</td>
	                		<td>
	                			<input maxlength="50" class=" base" type="text" name="rfdxspsyt" value="${formObject.rfdxspsyt}" />
	                		</td>
	                		<td class="title">
	                			人防地下室报建面积(m<sup>2</sup>)
	                		</td>
	                		<td>
	                			<input maxlength="50" class=" base" type="text" name="rfdxsbjmj" value="${formObject.rfdxsbjmj}" />
	                		</td>
	                	</tr>
	                	<tr>
	                		<td class="title">
	                			防护单元数
	                		</td>
	                		<td>
	                			<input maxlength="50" class=" base" type="text" name="fhdys" value="${formObject.fhdys}" />
	                		</td>
	                		<td class="title">
	                			核级
	                		</td>
	                		<td>
	                			<input maxlength="50" class=" base" type="text" name="nucleus" value="${formObject.nucleus}" />
	                		</td>
	                	</tr>
	                	<tr>
	                		<td class="title">
	                			常级
	                		</td>
	                		<td>
	                			<input maxlength="50" class=" base" type="text" name="often" value="${formObject.often}" />
	                		</td>
	                		<td class="title">
	                			防护等级
	                		</td>
	                		<td>
	                			<input maxlength="50" class=" base" type="text" name="fence" value="${formObject.fence}" />
	                		</td>
	                	</tr>
	                	<tr>
	                		<td class="title">
	                			人防地下室战时用途
	                		</td>
	                		<td>
	                			<input maxlength="50" class=" base" type="text" name="rfdxszsyt" value="${formObject.rfdxszsyt}" />
	                		</td>
	                		<td class="title">
	                			二等人员面积
	                		</td>
	                		<td>
	                			<input maxlength="50" class=" base" type="text" name="edry" value="${formObject.edry}" />
	                		</td>
	                	</tr>
	                	<tr>
	                		<td class="title">
	                			物资库面积
	                		</td>
	                		<td>
	                			<input maxlength="50" class=" base" type="text" name="wzk" value="${formObject.wzk}" />
	                		</td>
	                		<td class="title">
	                			专业队面积
	                		</td>
	                		<td>
	                			<input maxlength="50" class=" base" type="text" name="zyd" value="${formObject.zyd}" />
	                		</td>
	                	</tr>
	                	<tr>
	                		<td class="title">
	                			平时出入口个数
	                		</td>
	                		<td>
	                			<input maxlength="50" class=" base" type="text" name="pscrkgs" value="${formObject.pscrkgs}" />
	                		</td>
	                		<td class="title">
	                			战时出入口个数
	                		</td>
	                		<td>
	                			<input maxlength="50" class=" base" type="text" name="zscrkgs" value="${formObject.zscrkgs}" />
	                		</td>
	                	</tr>
	                	<tr>
	                		<td class="title">
	                			平时通风口个数
	                		</td>
	                		<td>
	                			<input maxlength="50" class=" base" type="text" name="pstfkgs" value="${formObject.pstfkgs}" />
	                		</td>
	                		<td class="title">
	                			战时通风口个数
	                		</td>
	                		<td>
	                			<input maxlength="50" class=" base" type="text" name="zstfkgs" value="${formObject.zstfkgs}" />
	                		</td>
	                	</tr>
	                	<tr>
	                		<td class="title">
	                			战时封堵个数
	                		</td>
	                		<td>
	                			<input maxlength="50" class=" base" type="text" name="zsfdgs" value="${formObject.zsfdgs}" />
	                		</td>
	                		<td class="title">
	                			战时封堵面积
	                		</td>
	                		<td>
	                			<input maxlength="50" class=" base" type="text" name="zsfdmj" value="${formObject.zsfdmj}" />
	                		</td>
	                	</tr>
	                	<tr>
	                		<td class="title">
	                			防空地下室主要出入口处城市坐标(X轴)
	                		</td>
	                		<td>
	                			<input maxlength="50" class=" base" type="text" name="basementXPoint" value="${formObject.basementXPoint}" />
	                		</td>
	                		<td class="title">
	                			防空地下室主要出入口处城市坐标(Y轴)
	                		</td>
	                		<td>
	                			<input maxlength="50" class=" base" type="text" name="basementYPoint" value="${formObject.basementYPoint}" />
	                		</td>
	                	</tr>
	                	<tr>
	                		<td class="title">
	                			防空地下室主要出入口处城市高程
	                		</td>
	                		<td colspan="3">
	                			<input maxlength="50" class=" base" type="text" name="basementGc" value="${formObject.basementGc}" />
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
        </form>
	</div>
</body>
</html>