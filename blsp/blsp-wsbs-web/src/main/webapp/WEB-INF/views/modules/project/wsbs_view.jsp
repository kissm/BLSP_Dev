<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
        <meta name="decorator" content="blank" />
		<title>珠海市金湾区网上办事大厅</title>
		<script type="text/javascript">
			$(function() {
				$('.fold div').click(function(){
					if($(this).is('.minus')){
						var foldId = $(this).parents('tr').attr('id');
						$('#editTable tr.'+foldId).hide();
						$(this).removeClass('minus');
						$(this).addClass('add');
					}else{
						var foldId = $(this).parents('tr').attr('id');
						$('#editTable tr.'+foldId).show();
						$(this).removeClass('add');
						$(this).addClass('minus');
					}
					
				});
				$("#showBasicDiv").css("background-color","white");
				$("#showBasicDiv").css("color","rgb(61, 158, 233)");
				$("#tasksDiv").hide();
				$("#materialDiv").hide();
			});
			function showBasic(){
				$("#showBasicDiv").css("background-color","white");
				$("#showBasicDiv").css("color","rgb(61, 158, 233)");
				$("#showTasksDiv").css("background-color","rgb(61, 158, 233)");
				$("#showTasksDiv").css("color","white");
				$("#showMaterialDiv").css("background-color","rgb(61, 158, 233)");
				$("#showMaterialDiv").css("color","white");
				$("#basic").show();
				$("#tasksDiv").hide();
				$("#materialDiv").hide();
				$("#printDiv").show();
			}
			function showTasksDiv(){
				$("#showBasicDiv").css("background-color","rgb(61, 158, 233)");
				$("#showBasicDiv").css("color","white");
				$("#showTasksDiv").css("background-color","white");
				$("#showTasksDiv").css("color","rgb(61, 158, 233)");
				$("#showMaterialDiv").css("background-color","rgb(61, 158, 233)");
				$("#showMaterialDiv").css("color","white");
				$("#basic").hide();
				$("#tasksDiv").show();
				$("#materialDiv").hide();
				$("#printDiv").hide();
			}
			function showMaterialDiv(){
				$("#showBasicDiv").css("background-color","rgb(61, 158, 233)");
				$("#showBasicDiv").css("color","white");
				$("#showTasksDiv").css("background-color","rgb(61, 158, 233)");
				$("#showTasksDiv").css("color","white");
				$("#showMaterialDiv").css("background-color","white");
				$("#showMaterialDiv").css("color","rgb(61, 158, 233)");
				$("#basic").hide();
				$("#tasksDiv").hide();
				$("#materialDiv").show();
				$("#printDiv").hide();
			}
			function openTask(taskId,id){
				openWindow({
					id:'detailTable',
					title:'表单详情',
					url:'${ctx}/project/itemDetail',
					width:'1000px',
					height:'500px',
					params:{"taskId":taskId,"id":id,"view":"4"},
				});
			}
			function sub(formid,taskId) {
				$("#formCode").val(formid);
				$("#taskId").val(taskId);
				$("#lookForm").val("1");
				$("#form1").submit();
			}
			function doPrint() {
				var url = "${ctx}/project/printInstance?id=${project.prjInstanceVo.id}";
				window.open(url,"_blank");
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
				<table class="editTable" id="backPage" style="width: 960px" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td class="content" align="center">
							<div class="uploadify-button" id="showBasicDiv" onclick="showBasic()" style="height: 30px; line-height: 30px; width: 80px; border: 0px; border-radius: 2px;background-image: none; background-color: rgb(61, 158, 233);float: left;">
								<span class="uploadify-button-text">基本信息</span>
							</div>
							<div class="uploadify-button" id="showTasksDiv" onclick="showTasksDiv()" style="height: 30px; line-height: 30px; width: 80px; border: 0px; border-radius: 2px;background-image: none; background-color: rgb(61, 158, 233);float: left;margin-left: 10px;">
								<span class="uploadify-button-text">申报事项</span>
							</div>
							<div class="uploadify-button" id="showMaterialDiv" onclick="showMaterialDiv()" style="height: 30px; line-height: 30px; width: 80px; border: 0px; border-radius: 2px;background-image: none; background-color: rgb(61, 158, 233);float: left;margin-left: 10px;">
								<span class="uploadify-button-text">申报材料</span>
							</div>
						</td>
					</tr>
				</table>
	
				<div class="tab-content">
					<div role="tabpanel"
						class="tab-pane active animated fadeInRight in" id="basic">
						<table class="editTable" style="width: 960px" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td class="title" >
									<font style="color:red;">*</font>项目名称
								</td>
								<td>
									<input type="text" readonly class="required" maxlength="200" id="prjName" name="prjInstanceVo.prjName" value="${project.prjInstanceVo.prjName}"></input>
								</td>
								<td class="title" >
									项目类别
								</td>
								<td>
									<input type="text" readonly maxlength="50" name="prjInstanceVo.prjCat" value="${project.prjInstanceVo.prjCat}"></input>
								</td>
								<td class="title" >
									电话
								</td>
								<td>
									<input type="text" readonly class="phone" maxlength="15" name="prjInstanceVo.companyMphone" value="${project.prjInstanceVo.companyMphone}"></input>
								</td>
							</tr>
							<tr>
								<td class="title" >
									传真
								</td>
								<td>
									<input type="text" readonly class="fax" maxlength="15" name="prjInstanceVo.comapnyFax" value="${project.prjInstanceVo.comapnyFax}"></input>
								</td>
								<td class="title" >
									<font style="color:red;">*</font>建设单位
								</td>
								<td>
									<input type="text" readonly class="required" maxlength="200" name="prjInstanceVo.company" value="${project.prjInstanceVo.company}"></input>
								</td>
								<td class="title" >
									单位地址
								</td>
								<td>
									<input type="text" readonly maxlength="200" name="prjInstanceVo.companyAddr" value="${project.prjInstanceVo.companyAddr}"></input>
								</td>
							</tr>
							<tr>
								<td class="title" colspan="2">
									建设单位企业信用代码或组织机构代码
								</td>
								<td colspan="4">
									<input type="text" readonly maxlength="25" name="prjInstanceVo.companyCode" value="${project.prjInstanceVo.companyCode}"></input>
								</td>
							</tr>
							<tr>
								<td class="title" >
									法人代表 
								</td>
								<td>
									<input type="text" readonly maxlength="50" name="prjInstanceVo.legalEntity" value="${project.prjInstanceVo.legalEntity}"></input>
								</td>
								<td class="title" >
									手机（法人代表）
								</td>
								<td>
									<input type="text" readonly class="mobile" maxlength="15" name="prjInstanceVo.entityMphone" value="${project.prjInstanceVo.entityMphone}"></input>
								</td>
								<td class="title" >
									办公电话（法人代表）
								</td>
								<td>
									<input type="text" readonly class="phone" maxlength="15" name="prjInstanceVo.entityPhone" value="${project.prjInstanceVo.entityPhone}"></input>
								</td>
							</tr>
							<tr>
								<td class="title" >
									<font style="color:red;">*</font>受委托人 
								</td>
								<td>
									<input type="text" readonly class="required" maxlength="50" name="prjInstanceVo.agentName" value="${project.prjInstanceVo.agentName}"></input>
								</td>
								<td class="title" >
									<font style="color:red;">*</font>手机（受委托人）
								</td>
								<td>
									<input type="text" readonly class="required mobile" maxlength="15" name="prjInstanceVo.agentMphone" value="${project.prjInstanceVo.agentMphone}"></input>
								</td>
								<td class="title" >
									办公电话（受委托人）
								</td>
								<td>
									<input type="text" readonly class="phone" maxlength="15" name="prjInstanceVo.agentPhone" value="${project.prjInstanceVo.agentPhone}"></input>
								</td>
							</tr>
							<tr>
								<td class="title" >
									项目性质
								</td>
								<td>
									<input type="text" readonly maxlength="50" name="prjInstanceVo.prjNature" value="${project.prjInstanceVo.prjNature}"></input>
								</td>
								<td class="title" >
									项目地址
								</td>
								<td colspan="3">
									<input type="text" readonly maxlength="50" name="prjInstanceVo.prjAddr" value="${project.prjInstanceVo.prjAddr}"></input>
								</td>
							</tr>
							<tr>
								<td class="title" >
									总建筑面积(m2)
								</td>
								<td>
									<input type="text" readonly maxlength="200" name="prjInstanceVo.prjFloorSpace" value="${project.prjInstanceVo.prjFloorSpace}"></input>
								</td>
								<td class="title" >
									占地面积(m2)
								</td>
								<td>
									<input type="text" readonly maxlength="200" name="prjInstanceVo.prjRedlineSpace" value="${project.prjInstanceVo.prjRedlineSpace}"></input>
								</td>
								<td class="title" >
									项目规模及内容
								</td>
								<td>
									<input type="text" readonly maxlength="500" name="prjInstanceVo.prjDescription" value="${project.prjInstanceVo.prjDescription}"></input>
								</td>
							</tr>
							<tr>
								<td class="title" >
									投资估算
								</td>
								<td>
									<input type="text" readonly maxlength="200" name="prjInstanceVo.investEstimate" value="${project.prjInstanceVo.investEstimate}"></input>
								</td>
								<td class="title" >
									资金来源
								</td>
								<td>
									<input type="text" readonly maxlength="150" name="prjInstanceVo.fundSource" value="${project.prjInstanceVo.fundSource}"></input>
								</td>
								<td class="title" >
									相关资料文件
								</td>
								<td align="center">
									<input type="hidden" id="fileUrlPrj${project.prjInstanceVo.id}" name="prjInstanceVo.preFilesAddr" value="${project.prjInstanceVo.preFilesAddr}"/>
									<input type="hidden" id="fileNamePrj${project.prjInstanceVo.id}" name="prjInstanceVo.preFilesName" value="${project.prjInstanceVo.preFilesName}"/>
									<div id="showFile_Prj${project.prjInstanceVo.id}" >
										<c:if test="${not empty project.prjInstanceVo.preFilesAddr}">
											<div class="UploadFileList"><a href="${centextPath}/download?pathUrl=${project.prjInstanceVo.preFilesAddr}&coi=项目相关资料文件">${project.prjInstanceVo.preFilesName}</a><span id="Prj${project.prjInstanceVo.id}"></span></div>
										</c:if>
									</div>
								</td>
							</tr>
							<tr>
								<td class="title" >
									相关资料文件描述
								</td>
								<td colspan="5">
									<input type="text" readonly maxlength="150" name="prjInstanceVo.preFilesDesc" value="${project.prjInstanceVo.preFilesDesc}"></input>
								</td>
							</tr>
						</table>
					</div>
					<div role="tabpanel" class="tab-pane animated fadeInRight" id="tasksDiv">
						<table id="editTable" class="editTable" style="width: 960px" border="0" cellspacing="0" cellpadding="0">
							<tbody>
								<c:forEach items="${tasks}" var="entry">
									<c:set value="${entry.key}" var="key" />
									<c:set value="${entry.value}" var="value" />
									<c:if test="${fn:length(value.formDefineList) eq 0}">
									<tr>
										<tr id="fold_${fns:getPrjTaskDefineVo(key).id}" class="fold" >
											<td colspan="3" class="content">
												${fns:getPrjTaskDefineVo(key).taskName}
											</td>
										</tr>
									</tr>
									</c:if>
									<c:if test="${fn:length(value.formDefineList) ne 0}">
										<tr>
											<tr id="fold_${fns:getPrjTaskDefineVo(key).id}" class="fold" >
												<td colspan="3" class="content">
													${fns:getPrjTaskDefineVo(key).taskName}
													<div class="add"></div>
												</td>
											</tr>
										</tr>
										<tr class="hidden fold_${fns:getPrjTaskDefineVo(key).id}">
											<td class="title" >
												表单名称
											</td>
											<td style="text-align: center;width: 15%;">
												状态
											</td>
											<td style="text-align: center;width: 15%;">
												操作
											</td>
										</tr>
										<c:forEach items="${value.formDefineList}" var="form"
											varStatus="status">
											<tr class="hidden fold_${fns:getPrjTaskDefineVo(key).id}">
												<td class="title" >
													${form.formName}
												</td>
												<td align="center">
													<c:if test="${form.prjectId eq null}">
														未填写
													</c:if>
													<c:if test="${form.prjectId ne null}">
														已填写
													</c:if>
												</td>
												<td align="center">
													<c:if test="${form.prjectId ne null}">
														<div class="uploadify-button" onClick="sub('${form.formCode}','${value.taskDefId}')" style="height: 30px; line-height: 30px; width: 120px; border: 0px; border-radius: 2px;background-image: none; background-color: rgb(61, 158, 233);">
															<span class="uploadify-button-text">查看</span>
														</div>
													</c:if>
												</td>
											</tr>
										</c:forEach>
									</c:if>
								</c:forEach>
							</tbody>
						</table>
					</div>
					
					<div class="tab-pane animated fadeInRight" id="materialDiv">
						<table id="editTable" class="editTable" style="width: 960px" border="0" cellspacing="0" cellpadding="0">
							<thead>
								<tr>
									<th class="title" style="width: 10%">
										序号
									</th>
									<th class="title" style="width: 50%">
										材料名称
									</th>
									<th class="title" style="width: 20%">
										需求情况
									</th>
									<th class="title" style="width: 20%">
										操作
									</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${pro.materialDefMap}" var="map" varStatus="status">
									<tr style="height: 40px;">
										<td align="center">
											${status.index+1}
										</td>
										<td align="center">
											${map.value.name}
										</td>
										<td align="center">
											原件${map.value.originalNum}份，复印件${map.value.copyNum}份
										</td>
										<td align="center" class="content">
											<div id="showFile_${map.value.materialId}" >
												<c:if test="${not empty map.value.materialAddr}">
													<div class="UploadFileList"><a href="${centextPath}/download?pathUrl=${map.value.materialAddr}&coi=${map.value.name}">${map.value.name}</a><span id="${map.value.materialId}"></span></div>
												</c:if>
											</div>
										</td>
									</tr>
								</c:forEach>
							</tbody>
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
						<input id="back" type="button" class="btnNoimg next" value="返回" onclick="window.history.back();" />
					</td>
				</tr>
			</table>
		</div>
	</div>
</body>
</html>