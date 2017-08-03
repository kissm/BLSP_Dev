<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
        <meta name="decorator" content="blank" />
		<title>珠海市金湾区网上办事大厅</title>
		<style type="text/css">
			table {
				font-size: 12px;
			}
			table td {
	            border: 1px solid #999;
	        }
	        table td {
	            padding: 0 5px 0;
	            height: 35px;
	        }
	        .form-control {
	        	box-shadow: none;
	        	border: 0;
	        	border-bottom: 1px solid #e0e0e0;
	        	display: block;
	        	width: 100%;
	        	height: 35px;
	        	font-size: 13px;
	        	line-height: 1.42857143;
	        	color: #555555;
	        	background-color: #ffffff;
	        }
		</style>
		<script type="text/javascript">
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
	<form id="form1" action="${ctx}/project/formDetail"
		method="post">
		<input type="hidden" name="formCode" id="formCode"> <input type="hidden"
			name="prjInstanceVo.id" id="projectId" value="${project.prjInstanceVo.id}">
		<input type="hidden" name="taskId" id="taskId">
		<input type="hidden" name="lookForm" id="lookForm">
	</form>
	<div align="center">
		<div class="row">
			<div role="tabpanel" class="tab">
				<div class="tab-content">
					<div role="tabpanel"
						class="tab-pane active animated fadeInRight in" id="basic">
						<table style="width: 960px" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td colspan="2" align="center" style="font-size: 20px;height: 50px;border: none">
									项目基本信息
								</td>
							</tr>
							<tr>
								<td class="title" width="15%" >
									项目名称
								</td>
								<td>
									<input type="text" readonly class="form-control" maxlength="200" id="prjName" name="prjInstanceVo.prjName" value="${prjInstanceVo.prjName}"></input>
								</td>
							</tr>
							<tr>
								<td class="title" >
									项目类别
								</td>
								<td>
									<input type="text" class="form-control" readonly maxlength="50" name="prjInstanceVo.prjCat" value="${prjInstanceVo.prjCat}"></input>
								</td>
							</tr>
							<tr>
								<td class="title" >
									电话
								</td>
								<td>
									<input type="text" readonly class="form-control" maxlength="15" name="prjInstanceVo.companyMphone" value="${prjInstanceVo.companyMphone}"></input>
								</td>
							</tr>
							<tr>
								<td class="title" >
									传真
								</td>
								<td>
									<input type="text" readonly class="form-control" maxlength="15" name="prjInstanceVo.comapnyFax" value="${prjInstanceVo.comapnyFax}"></input>
								</td>
							</tr>
							<tr>
								<td class="title" >
									建设单位
								</td>
								<td>
									<input type="text" readonly class="form-control" maxlength="200" name="prjInstanceVo.company" value="${prjInstanceVo.company}"></input>
								</td>
							</tr>
							<tr>
								<td class="title" >
									单位地址
								</td>
								<td>
									<input type="text" readonly class="form-control" maxlength="200" name="prjInstanceVo.companyAddr" value="${prjInstanceVo.companyAddr}"></input>
								</td>
							</tr>
							<tr>
								<td class="title">
									建设单位企业信用代码或组织机构代码
								</td>
								<td>
									<input type="text" readonly class="form-control" maxlength="25" name="prjInstanceVo.companyCode" value="${prjInstanceVo.companyCode}"></input>
								</td>
							</tr>
							<tr>
								<td class="title" >
									法人代表 
								</td>
								<td>
									<input type="text" readonly class="form-control" maxlength="50" name="prjInstanceVo.legalEntity" value="${prjInstanceVo.legalEntity}"></input>
								</td>
							</tr>
							<tr>
								<td class="title" >
									手机（法人代表）
								</td>
								<td>
									<input type="text" readonly class="form-control" maxlength="15" name="prjInstanceVo.entityMphone" value="${prjInstanceVo.entityMphone}"></input>
								</td>
							</tr>
							<tr>
								<td class="title" >
									办公电话（法人代表）
								</td>
								<td>
									<input type="text" readonly class="form-control" maxlength="15" name="prjInstanceVo.entityPhone" value="${prjInstanceVo.entityPhone}"></input>
								</td>
							</tr>
							<tr>
								<td class="title" >
									受委托人 
								</td>
								<td>
									<input type="text" readonly class="form-control" maxlength="50" name="prjInstanceVo.agentName" value="${prjInstanceVo.agentName}"></input>
								</td>
							</tr>
							<tr>
								<td class="title" >
									手机（受委托人）
								</td>
								<td>
									<input type="text" readonly class="form-control" maxlength="15" name="prjInstanceVo.agentMphone" value="${prjInstanceVo.agentMphone}"></input>
								</td>
							</tr>
							<tr>
								<td class="title" >
									办公电话（受委托人）
								</td>
								<td>
									<input type="text" readonly class="form-control" maxlength="15" name="prjInstanceVo.agentPhone" value="${prjInstanceVo.agentPhone}"></input>
								</td>
							</tr>
							<tr>
								<td class="title" >
									项目性质
								</td>
								<td>
									<input type="text" readonly class="form-control" maxlength="50" name="prjInstanceVo.prjNature" value="${prjInstanceVo.prjNature}"></input>
								</td>
							</tr>
							<tr>
								<td class="title" >
									项目地址
								</td>
								<td colspan="3">
									<input type="text" readonly class="form-control" maxlength="50" name="prjInstanceVo.prjAddr" value="${prjInstanceVo.prjAddr}"></input>
								</td>
							</tr>
							<tr>
								<td class="title" >
									总建筑面积(m2)
								</td>
								<td>
									<input type="text" readonly class="form-control" maxlength="200" name="prjInstanceVo.prjFloorSpace" value="${prjInstanceVo.prjFloorSpace}"></input>
								</td>
							</tr>
							<tr>
								<td class="title" >
									占地面积(m2)
								</td>
								<td>
									<input type="text" readonly class="form-control" maxlength="200" name="prjInstanceVo.prjRedlineSpace" value="${prjInstanceVo.prjRedlineSpace}"></input>
								</td>
							</tr>
							<tr>
								<td class="title" >
									项目规模及内容
								</td>
								<td>
									<input type="text" readonly class="form-control" maxlength="500" name="prjInstanceVo.prjDescription" value="${prjInstanceVo.prjDescription}"></input>
								</td>
							</tr>
							<tr>
								<td class="title" >
									投资估算
								</td>
								<td>
									<input type="text" readonly class="form-control" maxlength="200" name="prjInstanceVo.investEstimate" value="${prjInstanceVo.investEstimate}"></input>
								</td>
							</tr>
							<tr>
								<td class="title" >
									资金来源
								</td>
								<td>
									<input type="text" readonly class="form-control" maxlength="150" name="prjInstanceVo.fundSource" value="${prjInstanceVo.fundSource}"></input>
								</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
			<table class="editTable" id="footTable" style="width: 960px" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="content" align="center">
						<div class="noprint" id="printDiv" style="display: inline;">
						    <input id='print' type="button" value="打 印" onclick="doPrint();"class="btnNoimg" id="btnPrev">
						</div>
						<input id="back" type="button" class="btnNoimg next" value="关闭" onclick="window.close();" />
					</td>
				</tr>
			</table>
		</div>
	</div>
</body>
</html>