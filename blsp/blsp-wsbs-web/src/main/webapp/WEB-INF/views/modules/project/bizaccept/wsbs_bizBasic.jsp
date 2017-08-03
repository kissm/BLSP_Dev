<%@page import="com.google.gson.Gson"%>
<%@page import="com.lpcode.modules.service.project.dto.ProjectChangeForm"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
        <meta name="decorator" content="blank" />
		<title>珠海市金湾区网上办事大厅</title>
		<link href="${centextPath}/uploadify/uploadify.css" rel="stylesheet" />
		<script src="${centextPath}/uploadify/jquery.uploadify.js"></script>
		<script type="text/javascript">
			var taskMaterialList = [];
			$(function(){
				$('.fold div').click(function(){
					if($(this).is('.minus')){
						var fold = $(this).parents('tr');
						var trN = $('#editTable tbody tr.fold').map(function(e){
							if($(this).get(0)===fold.get(0)){
								return e;
							}
						}).get();
						$('#editTable tbody tr:not(.fold)').slice(trN*20,trN*20+20).hide();
						$(this).removeClass('minus');
						$(this).addClass('add');
					}else{
						var fold = $(this).parents('tr');
						var trN = $('#editTable tbody tr.fold').map(function(e){
							if($(this).get(0)===fold.get(0)){
								return e;
							}
						}).get();
						$('#editTable tbody tr:not(.fold)').slice(trN*20,trN*20+20).show();
						$(this).removeClass('add');
						$(this).addClass('minus');
					}
				});
				$(".closed").each(function (){
					var fold = $(this).parents('tr');
					var trN = $('#editTable tbody tr.fold').map(function(e){
						if($(this).get(0)===fold.get(0)){
							return e;
						}
					}).get();
					$('#editTable tbody tr:not(.fold)').slice(trN*20,trN*20+20).hide();
					$(this).removeClass('minus');
					$(this).addClass('add');
				});
				$("#form").validate({
					rules: {
						'prjInstanceVo.prjName': {
							remote: {
								type:"POST",
								url:"${ctx}/project/accpet/checkProjectName?oldProjectName="+ encodeURIComponent('${project.prjInstanceVo.prjName}'),
								data:{
									prjName:function(){return $("#prjName").val();}
								}
							}
						}
					},
					messages: {
						'prjInstanceVo.prjName': {remote: "该项目名称已存在"},
					},
					submitHandler : function(form) {
						form.submit();
					}
				});
				$(".next").click(function() {
					if($("#form").valid()){
						var handle = $(this).prop("id");
						$("#handle").val(handle);
						var applyState = $("#applyState").val();
						if(handle == "hold"){
							if(applyState == "0"){
								alert("项目已保存！");
							}else if(applyState == "1"){
								alert("项目已提交！");
							}else if(applyState == ""){
								$("#applyState").val("0");
								$(".next").attr("disabled",true);
								var jsonDate = JSON.stringify(taskMaterialList);
								$('#jsonData').val(jsonDate);
								$("#form").submit();
							}
						}else if(handle == "refer"){
							if(confirm("您确认提交审核？")){
								if(applyState == "1"){
									alert("项目已提交！");
								}else if(applyState == "" || applyState == "0"){
									$("#applyState").val("1");
									$(".next").attr("disabled",true);
									var jsonDate = JSON.stringify(taskMaterialList);
									$('#jsonData').val(jsonDate);
									$("#form").submit();
								}
							}
						}else{
							if(!applyState){
								$("#applyState").val("0");
							}
							$(".next").attr("disabled",true);
							var jsonDate = JSON.stringify(taskMaterialList);
							$('#jsonData').val(jsonDate);
							$("#form").submit();
						}
					}
				});
				handle();
			});
			$(function (){
				<%
					Gson gson = new Gson();
				%>
				//材料集合
				var map = <%=gson.toJson(((ProjectChangeForm)request.getAttribute("project")).getMaterialDefMap())%>;
				//首先获取已经存在关系的材料存入集合
				for(i in map){
					if(map[i].materialAddr){
						taskMaterialList.push(map[i]);
					}
				}
				$('input[type="file"]').each(function() {
					var id = $(this).attr('id');
					 $('#' + id).uploadify({
						'swf' : '${centextPath}/uploadify/uploadify.swf?_' + id,
						'uploader' : '${centextPath}/uploadImage',
						'auto' : true,
						'multi' : false,
						'wmode' : 'transparent',
						'buttonText' : '上传文件',
						'fileTypeExts' : '*.pdf;*.doc;*.docx;*.xls;*.xlsx;*.jpg;*.gif;*.bmp;*.png;*.rar;*.zip',
						onSelect : function(file) {},
						onCancel : function(event,queueID, file,response, data) {},
						onUploadSuccess : function(file, data, response) {
							var result = eval("(" + data + ")");
							if (result.resCode == '0') {
								var path = (result.obj)[0];
								var fileName = (result.obj)[1];
								var lastIndex = fileName.lastIndexOf('.');
								var loadName = fileName.substring(0,lastIndex);
								$('#fileUrl' + id).val(path);
								$('#fileName' + id).val(fileName);
								var htm = "<a href="+path+" target=_blank>"+fileName+"</a>";
								var htm = "<div class='UploadFileList'><a href='${centextPath}/download?pathUrl="+path+"&coi="+loadName+"'>"+fileName+"</a><span id='"+id+"'>[<a href='javascript:void(0)'>删除</a>]</span></div>";
								$('#showFile_' + id).html(htm);
								//判断是否是项目编号
								var testId = /^Prj\d+$/;
								if(testId.exec(id)){
									$('#fileUrl' + id).val(path);
									$('#fileName' + id).val("项目相关资料文件");
								}else{
									//对材料集合中已经存在的进行覆盖
									map[id].materialAddr = path;
									for(i in taskMaterialList){
										if(taskMaterialList[i].materialId == id) {
											taskMaterialList.splice(i,1);
										}
									}
									taskMaterialList.push(map[id]);
								}
							} else {
								alert('上传失败，请稍后再试');
							}
						},
						onUploadComplete : function(file) {},
						onUploadError : function(file,errorCode, errorMsg,errorString) {
							alert(errorString);
						}
					});
					$('#' + id + '-button').css('background-color','#3d9ee9').css('border', '0').css('background-image', 'none').css('border-radius', '2px');
					$('#' + id + '-queue').css('margin', '0');
					$('#' + id).css('margin', '0 auto');
					$('#' + id + " object").css('left', '0'); 
				});
				// 删除文件
				$("div [class='UploadFileList'] span a").live('click',function(){
					if(confirm('确定要删除该文件？')){
						var id = $(this).parent().attr('id');
						var testId = /^Prj\d+$/;
						if(testId.exec(id)){
							$('#fileUrl' + id).val('');
							$('#fileName' + id).val('');							
						}else{
							//对材料集合中已经存在的进行覆盖
							map[id].materialAddr = '';
							for(i in taskMaterialList){
								if(taskMaterialList[i].materialId == id) {
									taskMaterialList.splice(i,1);
								}
							}
							taskMaterialList.push(map[id]);
						}
						$(this).parent().parent().remove();
					}
				});
			});
			function handle(){
				var handle = '${handle}';
				if(handle == "hold"){
					alert("暂存成功！");
				}
			}
		</script>
	</head>
	<body>
		<div align="center">
			<form action="${ctx}/project/bizaccept/basic/" id="form" method="post">
				<input type="hidden" name="prjCompanyCode" id="prjCompanyCode" value="${project.prjInstanceVo.companyCode}">
				<input type="hidden" name="projectId" value="${project.prjInstanceVo.id}">
				<input type="hidden" name="type" value="2">
				<input type="hidden" name="url" value="next">
				<input type="hidden" name="prjInstanceVo.prjType" value="2">
				<input type="hidden" name="action" value="${action}">
				<input type="hidden" name="prjCodeGeneratorVo.seqType" value="2"> 
				<input type="hidden" name="prjCodeGeneratorVo.id" value="${project.prjCodeGeneratorVo.id}"> 
				<input type="hidden" id="key" name="prjInstanceVo.id" value="${project.prjInstanceVo.id}"> 
				<input type="hidden" id="key" name="prjInstanceVo.acceptId" value="${project.prjInstanceVo.acceptId}">
				<input type="hidden" name="prjInstanceVo.stageId" value="${project.prjInstanceVo.stageId}">
				<c:if test="${newStageFlag eq '1'}">
					<input type="hidden" name="stageId" value="${stageId}">
					<input type="hidden" name="prjInstanceVo.isStageComplete" value="0">
					<input type="hidden" name="prjStageVo.newStageFlag" value="1">
				</c:if>
				<%--<input type="hidden" name="prjInstanceVo.prjCode" value="${project.prjInstanceVo.prjCode}">--%>
				<input type="hidden" name="prjInstanceVo.channel" value="1">
				<input type="hidden" name="prjInstanceVo.applyState" id="applyState" value="${project.prjInstanceVo.applyState}">
				<input type="hidden" name="jsonData" id="jsonData">
				<%-- 网上办事用户名 --%>
				<input type="hidden" name="prjInstanceVo.wsbsUserName" value="${project.prjInstanceVo.wsbsUserName}">
				<input type="hidden" name="userName" value="${userName}">
				<%-- 确定提交方式 --%>
				<input type="hidden" name="handle" id="handle">
				<table class="editTable" style="width: 960px" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td class="title" >
							<font style="color:red;">*</font>项目名称
						</td>
						<td>
							<input type="text" class="required" maxlength="200" id="prjName" name="prjInstanceVo.prjName" value="${project.prjInstanceVo.prjName}"/>
						</td>
						<td class="title" >
							项目编号
						</td>
						<td>
							<input type="text" maxlength="20" name="prjInstanceVo.prjCode" value="${project.prjInstanceVo.prjCode}"/>
						</td>
						<td class="title" >
							项目类别
						</td>
						<td>
							<input type="text" maxlength="50" name="prjInstanceVo.prjCat" value="${project.prjInstanceVo.prjCat}"/>
						</td>
					</tr>
					<tr>
						<td class="title" >
							电话
						</td>
						<td>
							<input type="text" class="phone" maxlength="15" name="prjInstanceVo.companyMphone" value="${project.prjInstanceVo.companyMphone}"/>
						</td>
						<td class="title" >
							传真
						</td>
						<td>
							<input type="text" class="fax" maxlength="15" name="prjInstanceVo.comapnyFax" value="${project.prjInstanceVo.comapnyFax}"/>
						</td>
						<td class="title" >
							<font style="color:red;">*</font>建设单位
						</td>
						<td>
							<input type="text" class="required" maxlength="200" name="prjInstanceVo.company" value="${project.prjInstanceVo.company}"/>
						</td>
					</tr>
					<tr>
						<td class="title" >
							单位地址
						</td>
						<td>
							<input type="text" maxlength="200" name="prjInstanceVo.companyAddr" value="${project.prjInstanceVo.companyAddr}"/>
						</td>
						<td class="title" colspan="2">
							建设单位企业信用代码或组织机构代码
						</td>
						<td colspan="2">
							<input type="text" maxlength="25" name="prjInstanceVo.companyCode" value="${project.prjInstanceVo.companyCode}"/>
						</td>
					</tr>
					<tr>
						<td class="title" >
							法人代表 
						</td>
						<td>
							<input type="text" maxlength="50" name="prjInstanceVo.legalEntity" value="${project.prjInstanceVo.legalEntity}"/>
						</td>
						<td class="title" >
							手机（法人代表）
						</td>
						<td>
							<input type="text" class="mobile" maxlength="15" name="prjInstanceVo.entityMphone" value="${project.prjInstanceVo.entityMphone}"/>
						</td>
						<td class="title" >
							办公电话（法人代表）
						</td>
						<td>
							<input type="text" class="phone" maxlength="15" name="prjInstanceVo.entityPhone" value="${project.prjInstanceVo.entityPhone}"/>
						</td>
					</tr>
					<tr>
						<td class="title" >
							<font style="color:red;">*</font>受委托人 
						</td>
						<td>
							<input type="text" class="required" maxlength="50" name="prjInstanceVo.agentName" value="${project.prjInstanceVo.agentName}"/>
						</td>
						<td class="title" >
							<font style="color:red;">*</font>手机（受委托人）
						</td>
						<td>
							<input type="text" class="required mobile" maxlength="15" name="prjInstanceVo.agentMphone" value="${project.prjInstanceVo.agentMphone}"/>
						</td>
						<td class="title" >
							办公电话（受委托人）
						</td>
						<td>
							<input type="text" class="phone" maxlength="15" name="prjInstanceVo.agentPhone" value="${project.prjInstanceVo.agentPhone}"/>
						</td>
					</tr>
					<tr>
						<td class="title" >
							项目性质
						</td>
						<td>
							<input type="text" maxlength="50" name="prjInstanceVo.prjNature" value="${project.prjInstanceVo.prjNature}"/>
						</td>
						<td class="title" >
							项目地址
						</td>
						<td colspan="3">
							<input type="text" maxlength="50" name="prjInstanceVo.prjAddr" value="${project.prjInstanceVo.prjAddr}"/>
						</td>
					</tr>
					<tr>
						<td class="title" >
							总建筑面积(m2)
						</td>
						<td>
							<input type="text" maxlength="200" name="prjInstanceVo.prjFloorSpace" value="${project.prjInstanceVo.prjFloorSpace}"/>
						</td>
						<td class="title" >
							占地面积(m2)
						</td>
						<td>
							<input type="text" maxlength="200" name="prjInstanceVo.prjRedlineSpace" value="${project.prjInstanceVo.prjRedlineSpace}"/>
						</td>
						<td class="title" >
							项目规模及内容
						</td>
						<td>
							<input type="text" maxlength="500" name="prjInstanceVo.prjDescription" value="${project.prjInstanceVo.prjDescription}"/>
						</td>
					</tr>
					<tr>
						<td class="title" >
							投资估算
						</td>
						<td>
							<input type="text" maxlength="200" name="prjInstanceVo.investEstimate" value="${project.prjInstanceVo.investEstimate}"/>
						</td>
						<td class="title" >
							资金来源
						</td>
						<td>
							<input type="text" maxlength="150" name="prjInstanceVo.fundSource" value="${project.prjInstanceVo.fundSource}"/>
						</td>
						<td class="title" >
							相关资料文件
						</td>
						<td align="center">
							<input type="file" id="Prj${project.prjInstanceVo.id}" class="uploadify" />
							<input type="hidden" id="fileUrlPrj${project.prjInstanceVo.id}" name="prjInstanceVo.preFilesAddr" value="${project.prjInstanceVo.preFilesAddr}"/>
							<input type="hidden" id="fileNamePrj${project.prjInstanceVo.id}" name="prjInstanceVo.preFilesName" value="${project.prjInstanceVo.preFilesName}"/>
							<div id="showFile_Prj${project.prjInstanceVo.id}" >
								<c:if test="${not empty project.prjInstanceVo.preFilesAddr}">
									<div class="UploadFileList"><a href="${centextPath}/download?pathUrl=${project.prjInstanceVo.preFilesAddr}&coi=项目相关资料文件">${project.prjInstanceVo.preFilesName}</a><span id="Prj${project.prjInstanceVo.id}">[<a href="javascript:void(0)">删除</a>]</span></div>
								</c:if>
							</div>
						</td>
					</tr>
					<tr>
						<td class="title" >
							相关资料文件描述
						</td>
						<td colspan="5">
							<input type="text" maxlength="150" name="prjInstanceVo.preFilesDesc" value="${project.prjInstanceVo.preFilesDesc}"/>
						</td>
					</tr>
				</table>
				
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
						<c:forEach items="${project.materialDefMap}" var="map" varStatus="status">
							<c:if test="${status.index != 0 }">
								<tr>
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
										<input type="file" id="${map.value.materialId}" class="uploadify" />
										<input type="hidden" id="fileUrl${map.value.materialId}" value="${map.value.materialAddr}"/>
										<input type="hidden" id="fileName${map.value.materialId}" value="${map.value.name}"/>
										<div id="showFile_${map.value.materialId}" >
											<c:if test="${not empty map.value.materialAddr}">
												<div class="UploadFileList"><a href="${centextPath}/download?pathUrl=${map.value.materialAddr}&coi=${map.value.name}">${map.value.name}</a><span id="${map.value.materialId}">[<a href="javascript:void(0)">删除</a>]</span></div>
											</c:if>
										</div>
									</td>
								</tr>
							</c:if>
							<c:if test="${status.index == 0 }">
								<tr class="fold">
									<td colspan="4" class="content">
										${status.index+1}~${status.index+20}
										<div class="minus"></div>
									</td>
								</tr>
								<tr>
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
										<input type="file" id="${map.value.materialId}" class="uploadify" />
										<input type="hidden" id="fileUrl${map.value.materialId}" value="${map.value.materialAddr}"/>
										<input type="hidden" id="fileName${map.value.materialId}" value="${map.value.name}"/>
										<div id="showFile_${map.value.materialId}" >
											<c:if test="${not empty map.value.materialAddr}">
												<div class="UploadFileList"><a href="${centextPath}/download?pathUrl=${map.value.materialAddr}&coi=${map.value.name}">${map.value.name}</a><span id="${map.value.materialId}">[<a href="javascript:void(0)">删除</a>]</span></div>
											</c:if>
										</div>
									</td>
								</tr>
							</c:if>
							<c:if test="${(status.index+1)%20 == 0}">
								<tr class="fold">
									<td colspan="4" class="content">
										${status.index+2}~${status.index+21}
										<div class="minus closed"></div>
									</td>
								</tr>
							</c:if>
						</c:forEach>
					</tbody>
				</table>
				<table class="editTable" style="width: 960px" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td class="content" align="center">
							<input type="button" class="btnNoimg next" value="项目暂存" id="hold" />
							<input type="button" class="btnNoimg next" value="填写表单" id="next" />
							<input type="button" class="btnNoimg next" value="提交审核" id="refer" />
						</td>
					</tr>
				</table>
			</form>
		</div>
	</body>
</html>