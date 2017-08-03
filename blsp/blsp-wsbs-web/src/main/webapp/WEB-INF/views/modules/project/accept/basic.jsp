<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>政府新办项目受理</title>
<meta name="decorator" content="blank" />
<script>
	$(document).ready(function() {
		$("#form1").validate({
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


		$("#pre").click(function() {
			$("#pre").attr("disabled",true);
			$("#url").val("pre");
			$("#form1").submit();
		});
		$("#next").click(function() {
			if($("#form1").valid()){
				$("#next").attr("disabled",true);
				$("#url").val("next");
				$("#form1").submit();
			}
		});
		$('#confirm').click(function(){
			var compayMap = sessionStorage.getItem("compayMap");
			if(compayMap  != null ){
				var compay = JSON.parse(compayMap);
				console.log(compay);
				$("#prjInstanceVo_companyMphone").val(compay.companyMphone);
				$("#prjInstanceVo_comapnyFax").val(compay.comapnyFax);
//				$("#prjInstanceVo_company").attr("value",compay.company); //建设单位名称;
				$("#prjInstanceVo_company").val(compay.company); //建设单位名称;
				$("#prjInstanceVo_companyAddr").val(compay.companyAddr);//单位地址
				$("#prjInstanceVo_companyCode").val(compay.companyCode); //建设单位企业信用代码或组织机构代码
				$("#prjInstanceVo_legalEntity").val(compay.legalEntity); //法人代表
				$("#prjInstanceVo_entityMphone").val(compay.entityMphone); //手机（法人代表）
				$("#prjInstanceVo_entityPhone").val(compay.entityPhone); //办公电话（法人代表）
				$("#prjCompanyCode").val(compay.companyCode);
			}
			$('#approve').modal('hide');
		});

		$('#approve').on('show.bs.modal', function () {
			var url = '${ctx}/project/accpet/project?prjCompanyCode=' + $("#prjCompanyCode").val();
			document.getElementById("buildIframe").src = url;
		});
	});



	function selectProject() {
		$("#form2").submit();
	}
</script>
</head>
<body>
	<div class="card">
		<div class="card-header">
			<h2>
				政府新办项目受理<small>您可通过本功能进行政府新办项目受理</small>
			</h2>
		</div>
		<form method="post" name="form2" id="form2" action="${ctx}/project/accpet/project/">
			<input type="hidden" name="prjInstanceVo.prjType" value="1">
			<input type="hidden" name="prjInstanceVo.priceType"
				value="${project.prjInstanceVo.priceType}"> <input
				type="hidden" name="prjInstanceVo.useageType"
				value="${project.prjInstanceVo.useageType}">
			<input type="hidden" name="projectId" value="${project.prjInstanceVo.id}">
			<input type="hidden" name="companyCode" value="${project.prjInstanceVo.companyCode}">
		</form>
		<div class="card-body card-padding">
			<div class="row">
				<div role="tabpanel" class="tab">
					<div class="tab-content">
						<div role="tabpanel"
							class="tab-pane active animated fadeInRight in" id="basic">

							<form method="post" name="form1" id="form1"
								action="${ctx}/project/accpet/basic/">
								<input type="hidden" name="prjCompanyCode" id="prjCompanyCode" value="${project.prjInstanceVo.companyCode}">
								<input type="hidden" name="prjInstanceVo.prjType" value="1">
								<input type="hidden" name="projectId" value="${project.prjInstanceVo.id}">
								<input type="hidden" name="prjInstanceVo.priceType" value="${project.prjInstanceVo.priceType}">
								<input type="hidden" name="prjInstanceVo.landType" value="${project.prjInstanceVo.landType}">
								<input type="hidden" name="prjInstanceVo.useageType" value="${project.prjInstanceVo.useageType}">
								<input type="hidden" name="prjCodeGeneratorVo.id" value="${project.prjCodeGeneratorVo.id}">
								<input type="hidden" name="prjInstanceVo.id" value="${project.prjInstanceVo.id}">
								<input type="hidden" name="prjInstanceVo.isNeedPreAudit" value="${project.prjInstanceVo.isNeedPreAudit}">
								<input type="hidden" name="prjInstanceVo.isSpecialProject" value="${project.prjInstanceVo.isSpecialProject}">
								<input type="hidden" name="prjInstanceVo.isWithBasePart" value="${project.prjInstanceVo.isWithBasePart}">
								<input type="hidden" name="prjInstanceVo.isItType" value="${project.prjInstanceVo.isItType}">
								<input type="hidden" name="prjInstanceVo.isGovType" value="${project.prjInstanceVo.isGovType}">
								<input type="hidden" name="prjInstanceVo.acceptId" value="${project.prjInstanceVo.acceptId}">
								<input type="hidden" name="action" value="${action}">
								<input type="hidden" name="type" value="1">
								<input type="hidden" name="url" id="url">
								<div class="row">
									<div class="col-xs-3 form-group">
										<label class="control-label">项目编号：</label>
										<div class="fg-line readonly">
											<input type="text" name="prjInstanceVo.prjCode"
												maxlength="30" value="${project.prjInstanceVo.prjCode}"
												class="form-control required" id="projectCode" readonly
												placeholder="项目编号">
										</div>
									</div>
									<div class="col-xs-3 form-group">
										<label class="control-label">项目类别：</label>
										<div class="fg-line">
											<input type="text" class="form-control"
												maxlength="50" value="${project.prjInstanceVo.prjCat}"
												placeholder="项目类别" name="prjInstanceVo.prjCat">
										</div>
									</div>
									<div class="col-xs-3 form-group">
										<label class="control-label">电话：</label>
										<div class="fg-line">
											<input type="text" class="form-control phone"
												maxlength="15" id="prjInstanceVo_companyMphone"
												value="${project.prjInstanceVo.companyMphone}"
												name="prjInstanceVo.companyMphone" placeholder="电话">
										</div>
									</div>
									<div class="col-xs-3 form-group">
										<label class="control-label">传真：</label>
										<div class="fg-line">
											<input type="text" class="form-control fax" id = "prjInstanceVo_comapnyFax"
												maxlength="15" value="${project.prjInstanceVo.comapnyFax}"
												name="prjInstanceVo.comapnyFax" placeholder="传真">
										</div>
									</div>
									<div class="col-xs-3 form-group">
										<label class="control-label">建设单位：</label>
										<div class="fg-line">
											<input type="text" name="prjInstanceVo.company"
												data-bind="company" maxlength="200"
												class="form-control" id="prjInstanceVo_company"
												value="${project.prjInstanceVo.company}" placeholder="建设单位">
										</div>
									</div>
									<div class="col-xs-1 form-group">
										<button type="button" class="btn btn-info waves-effect" data-toggle="modal" href="#approve" title="加载已有建设单位">
											<i class="md md-add"></i>
										</button>
										<%--<button data-toggle="tooltip" data-placement="bottom" title="加载已有建设单位" class="btn btn-success btn-icon m-r-5" onclick="selectProject()">--%>
											<%--<i class="md md-add"></i>--%>
										 <%--</button>--%>
									</div>
									<div class="col-xs-4 form-group">
										<label class="control-label">单位地址：</label>
										<div class="fg-line">
											<input type="text" class="form-control"
												data-bind="companyAddr" maxlength="200"  id="prjInstanceVo_companyAddr"
												value="${project.prjInstanceVo.companyAddr}"
												name="prjInstanceVo.companyAddr" placeholder="单位地址">
										</div>
									</div>

									<div class="col-xs-4 form-group">
										<label class="control-label">建设单位企业信用代码或组织机构代码：</label>
										<div class="fg-line">
											<input type="text" class="form-control" id="prjInstanceVo_companyCode"
												maxlength="25" value="${project.prjInstanceVo.companyCode}"
												name="prjInstanceVo.companyCode"
												placeholder="建设单位企业信用代码或组织机构代码">
										</div>
									</div>
									<div class="col-xs-4 form-group">
										<label class="control-label">法人代表：</label>
										<div class="fg-line">
											<input type="text" class="form-control"
												data-bind="legalEntity" maxlength="50" id="prjInstanceVo_legalEntity"
												value="${project.prjInstanceVo.legalEntity}"
												name="prjInstanceVo.legalEntity" placeholder="法人代表">
										</div>
									</div>
									<div class="col-xs-4 form-group">
										<label class="control-label">手机（法人代表）：</label>
										<div class="fg-line">
											<input type="text" class="form-control"
												data-bind="entityMphone" maxlength="15" id="prjInstanceVo_entityMphone"
												value="${project.prjInstanceVo.entityMphone}"
												name="prjInstanceVo.entityMphone" placeholder="手机">
										</div>
									</div>
									<div class="col-xs-4 form-group">
										<label class="control-label">办公电话（法人代表）：</label>
										<div class="fg-line">
											<input type="text" class="form-control phone" id="prjInstanceVo_entityPhone"
												maxlength="15" value="${project.prjInstanceVo.entityPhone}"
												name="prjInstanceVo.entityPhone" placeholder="办公电话">
										</div>
									</div>
									<div class="col-xs-4 form-group">
										<label class="control-label">受委托人：</label>
										<div class="fg-line">
											<input type="text" class="form-control  required"
												data-bind="agentName" maxlength="50"
												value="${project.prjInstanceVo.agentName}"
												name="prjInstanceVo.agentName" placeholder="受委托人">
										</div>
									</div>
									<div class="col-xs-4 form-group">
										<label class="control-label">手机（受委托人）：</label>
										<div class="fg-line">
											<input type="text" class="form-control  required mobile"
												data-bind="agentMphone" maxlength="15"
												value="${project.prjInstanceVo.agentMphone}"
												name="prjInstanceVo.agentMphone" placeholder="手机">
										</div>
									</div>
									<div class="col-xs-4 form-group">
										<label class="control-label">办公电话（受委托人）：</label>
										<div class="fg-line">
											<input type="text" class="form-control phone"
												data-bind="agentPhone" maxlength="15"
												value="${project.prjInstanceVo.agentPhone}"
												name="prjInstanceVo.agentPhone" placeholder="办公电话">
										</div>
									</div>
									<div class="col-xs-4 form-group">
										<label class="control-label">项目名称：</label>
										<div class="fg-line">
											<input type="text" class="form-control required" id="prjName"
												data-bind="prjName" maxlength="200"
												value="${project.prjInstanceVo.prjName}"
												name="prjInstanceVo.prjName" placeholder="项目名称" >
										</div>
									</div>
									<div class="col-xs-4 form-group">
										<label class="control-label">项目性质：</label>
										<div class="fg-line">
											<input type="text" class="form-control"
												maxlength="50" value="${project.prjInstanceVo.prjNature}"
												name="prjInstanceVo.prjNature" placeholder="项目性质">
										</div>
									</div>
									<div class="col-xs-4 form-group">
										<label class="control-label">项目地址：</label>
										<div class="fg-line">
											<input type="text" class="form-control"
												data-bind="prjAddr" maxlength="50"
												value="${project.prjInstanceVo.prjAddr}"
												name="prjInstanceVo.prjAddr" placeholder="项目地址">
										</div>
									</div>
									<div class="col-xs-4 form-group">
										<label class="control-label">总建筑面积(m<sup>2</sup>)：
										</label>
										<div class="fg-line">
											<input type="text" id="prjM" class="form-control"
												maxlength="200"
												value='${project.prjInstanceVo.prjFloorSpace}'
												name="prjInstanceVo.prjFloorSpace" placeholder="总建筑面积">
										</div>
									</div>
									<div class="col-xs-4 form-group">
										<label class="control-label">占地面积(m<sup>2</sup>)：
										</label>
										<div class="fg-line">
											<input type="text" id="prjMM" class="form-control"
												maxlength="200"
												value='${project.prjInstanceVo.prjRedlineSpace}'
												name="prjInstanceVo.prjRedlineSpace" placeholder="占地面积">
										</div>
									</div>
									<div class="col-xs-4 form-group">
										<label class="control-label">项目规模及内容：</label>
										<div class="fg-line">
											<input type="text" class="form-control"
												maxlength="500"
												value="${project.prjInstanceVo.prjDescription}"
												name="prjInstanceVo.prjDescription" placeholder="项目规模及内容">
										</div>
									</div>
									<div class="col-xs-6 form-group">
										<label class="control-label">投资估算： </label>
										<div class="fg-line">
											<input type="text" class="form-control"
												maxlength="200"
												value='${project.prjInstanceVo.investEstimate}'
												name="prjInstanceVo.investEstimate" placeholder="投资估算">
										</div>
									</div>
									<div class="col-xs-6 form-group">
										<label class="control-label">资金来源：</label>
										<div class="fg-line">
											<input type="text" class="form-control"
												maxlength="150" value="${project.prjInstanceVo.fundSource}"
												name="prjInstanceVo.fundSource" placeholder="资金来源">
										</div>
									</div>
									<div class="col-xs-6 form-group">
										<label class="control-label">相关资料文件描述： </label>
										<div class="fg-line">
											<input type="text" class="form-control"
												maxlength="150"
												value="${project.prjInstanceVo.preFilesDesc}"
												name="prjInstanceVo.preFilesDesc" placeholder="相关资料文件描述">
										</div>
									</div>
									<div class="col-xs-6 form-group">
										<label class="control-label">相关资料文件：</label>
										<div class="fg-line">
											<sys:file id="file"
												downloadFileAddress="${project.prjInstanceVo.preFilesAddr}"
												downloadFileName="项目相关资料文件" cssClass="btn-info"
												fileName="prjInstanceVo.preFilesName"
												fileNameValue ="${project.prjInstanceVo.preFilesName}"
											    fileAddress="prjInstanceVo.preFilesAddr"
											    fileAddressValue="${project.prjInstanceVo.preFilesAddr}"/>
										</div>
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
				<div class="btn-demo text-center">
					<button data-toggle="modal" id="pre"
						class="btn btn-primary waves-effect" type="button">上一步</button>
					<button id="next" class="btn btn-warning waves-effect"
						type="button">下一步</button>
				</div>

				<!-- 建设单位弹出页，设置保存和返回按钮，应设置上面的保存返回 -->
				<div class="modal fade" data-modal-color="cyan" id="approve" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog" aria-hidden="true">
					<div class="modal-dialog modal-lg">
						<div class="modal-content">
							<div class="modal-header">
								<h4 class="modal-title">建设单位选择</h4>
							</div>
							<div style="height:400px;">
								<iframe width="100%" height="100%" id="buildIframe" src=""></iframe>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-link" id="confirm">确认</button>
								<button type="button" class="btn btn-link" data-dismiss="modal">返回</button>
							</div>
						</div>
					</div>
				</div>
				<!-- 建设单位弹出页，设置保存和返回按钮，应设置上面的保存返回 -->
			</div>
		</div>
	</div>
</body>
</html>