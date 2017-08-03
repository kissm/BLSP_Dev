<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>珠海市金湾区网上办事大厅</title>
        <meta name="decorator" content="blank" />
        <script type="text/javascript">
        	$(function (){
        		$("#noInfoDiv").hide();
        		$("#infoDiv").hide();
        	});
        	//项目进度信息
        	function goProgress(){
        		var prjCode = $("#prjCode").val();
        		var company = $("#company").val();
        		if(company == null || company == ""){
        			alert("项目单位不能为空！");
        		}
        		if(prjCode == null || prjCode == ""){
        			alert("项目编号不能为空！");
        		}
				if(company != null && company != "" && prjCode != null && prjCode != ""){
					var testSearchNo = /^[A-Z]{4}[1-9]\d{3}(0[1-9]|1[012])(00[1-9]|[1-9]\d{2}|\d[1-9]\d)$/;
					if(!testSearchNo.test(prjCode)){
						alert("请输入正确格式的项目编号！");
					}else{
						var url = "${ctx}/schedule/progressInfo";
	            		$.post(url,{"prjCode":prjCode,"company":company},function (data){
	            			if(data == "0"){
	            				$("#noInfoDiv").show();
	            				$("#infoDiv").hide();
	            			}else{
	            				tasks(data);
	            				$("#infoDiv").show();
	            				$("#noInfoDiv").hide();
	            			}
	            		});
					}
        		}
        	}
        	function tasks(id){
        		var url = "${ctx}/schedule/progressTask";
        		$.post(url,{"id":id},function (data){
        			$("#infoDiv").html('');
        			$("#infoDiv").append(data);
        		},"html");
        	}
        </script>
	</head>
<body>
	
	<div class="chart-links">
			<div class="search">
				<div class="font">
					查询进度状态：在本栏目中,你可以查询到项目进度状态情况。查询时,需要提供项目编号和项目单位名称。
				</div>
				<div class="font">
					<table style="width: 100%">
						<tbody>
							<tr>
								<td style="text-align: right; width: 40%">
									项目编号<span style="color: Red">*</span>：
								</td>
								<td style="text-align: left;">
									<input style="width:200px" type="text" name="prjCode" id="prjCode" value="${prjIn.prjCode}" />
								</td>
							</tr>
							<tr>
								<td style="text-align: right">
									项目单位名称<span style="color: Red">*</span>：
								</td>
								<td style="text-align: left;">
									<input style="width:200px" type="text" name="company" id="company" value="" />
								</td>
							</tr>
						</tbody>
					</table>
					<div class="Lb01">
						<button class="btnTjCss" onclick="goProgress()"></button>
						<button class="btnReturnCss" onclick="window.history.back();"></button>
					</div>
					<div id="noInfoDiv">暂无查询到记录，请确认后再查询！</div>
				</div>
			</div>
		
			<div style="margin: 15px;" id="infoDiv"></div>
		</div>
		
</body>
</html>