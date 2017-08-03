<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>政府新办项目受理</title>
<meta name="decorator" content="blank" />
<script>
	$(document).ready(function() {
		//新建项目时（重新编辑时不触发），组织机构代码变更时触发
        $("#companyCode").change(function(){
            if($("#projectId").val() == null || $("#projectId").val() == ''){
                authSharedInformationReuse();
			}
        });


        $("#form1").validate({
			rules: {
				'prjInstanceVo.prjName': {
					remote: {
						type:"POST",
						url:"${ctx}/project/accpet/checkProjectName?oldPrjName="+ encodeURIComponent('${project.prjInstanceVo.prjName}'),
						data:{
							prjName:function(){return $("#prjName").val();}
						}
					}
				},
				'prjInstanceVo.prjCode':{
					remote: {
						type:"POST",
						url:"${ctx}/project/accpet/checkProjectCode?oldPrjCode="+ encodeURIComponent('${project.prjInstanceVo.prjCode}'),
						data:{
							prjCode:function(){return $("#prjCode").val();}
						}
					}
				}
				<%--TODO prjInstanceVo.shengPrjCode 注掉就不验证项目编号--%>
				<c:if test="${fns:getDictValue('sheng_code_switch', 'sheng_code_switch', '0') eq '1'}">
				,
				'prjInstanceVo.shengPrjCode':{
					remote: {
						type:"POST",
						url:"${ctx}/shared/validateShengPrjCode",
						data:{
							shengPrjCode:function(){return $("#shengPrjCode").val();}
						}
					}
				}
				</c:if>
			},
			messages: {
				'prjInstanceVo.prjName': {remote: "该项目名称已存在"},
				'prjInstanceVo.prjCode': {remote: "该项目区项目编号已经存在"}
				<%--TODO prjInstanceVo.shengPrjCode 注掉就不验证项目编号--%>
				<c:if test="${fns:getDictValue('sheng_code_switch', 'sheng_code_switch', '0') eq '1'}">
				,'prjInstanceVo.shengPrjCode': {remote: "该项目省项目编号并不存在"}
				</c:if>
			},
			submitHandler : function(form) {
				form.submit();
			}
		});


		$("#pre").click(function() {
			$("#pre").attr("disabled",true);
			$("#next").attr("disabled",true);
			$("#url").val("pre");
			$("#form1").submit();
		});
		$("#next").click(function() {
			$("#next").attr("disabled",true);
			$("#pre").attr("disabled",true);
			if($("#form1").valid()){
				$("#url").val("next");
				$("#form1").submit();
			}
			$("#next").attr("disabled",false);
			$("#pre").attr("disabled",false);
		});
		$('#confirm').click(function(){
			var compayMap = sessionStorage.getItem("compayMap");
			if(compayMap  != null ){
				var compay = JSON.parse(compayMap);
				$("#companyMphone").val(compay.companyMphone);
				$("#comapnyFax").val(compay.comapnyFax);
				$("#company").val(compay.company); //建设单位名称;
				$("#companyAddr").val(compay.companyAddr);//单位地址
				$("#companyCode").val(compay.companyCode); //建设单位企业信用代码或组织机构代码
				$("#legalEntity").val(compay.legalEntity); //法人代表
				$("#entityMphone").val(compay.entityMphone); //手机（法人代表）
				$("#entityPhone").val(compay.entityPhone); //办公电话（法人代表）
				$("#prjCompanyCode").val(compay.companyCode);
			}
			$('#approve').modal('hide');
		});

		$('#approve').on('show.bs.modal', function () {
			var url = '${ctx}/project/accpet/project?prjCompanyCode=' + $("#prjCompanyCode").val();
			document.getElementById("buildIframe").src = url;
		});
	});

	function selectBlspTzjs(){
		openWindow({
			id:'blspTzjs',
			title:'并联审批项目信息',
			url:'${ctx}/project/plusadd/getBlspList?prjType=1',
			width:'900px',
			height:'560px',
			callBack : function(data){
				if(data != null && data.length >0){
					for(var index in data){
						$("#"+data[index].name).val(data[index].value);
					}
				}
			}
		});
	}

	function selectWsbsTzjs(){
		openWindow({
			id:'wsbsTzjs',
			title:'网厅项目信息',
			url:'${ctx}/project/plusadd/getWsbsList',
			width:'900px',
			height:'560px',
			callBack : function(data){
				if(data != null && data.length >0){
					for(var index in data){
						$("#"+data[index].name).val(data[index].value);
					}
				}
			}
		});
	}
	function isEmpty(data){
		return (data == null || data == undefined || data == "" || data == "null");
	}
	function sharedInformationReuse(){
		var companyCode = $("#companyCode").val();
		var prjCode = $("#prjCode").val();
		if(isEmpty(companyCode)){
			var msg = "请输入企业信用代码和组织机构代码后重试!";
			var validateKey = "companyCode";
			swal({
				title: msg,
				text: "",
				type: "warning",
				showCancelButton: false,
				confirmButtonColor: "#DD6B55",
				confirmButtonText: "确定",
				closeOnConfirm: false
			}, function(){
				swal({
					title: msg,
					text: "",
					type: "warning",
					timer: 10,
					showConfirmButton: false
				});
				$("#"+validateKey).focus();
			});
			return;
		}
		swal({
			title : "复用信息会覆盖现有数据，确定要复用吗？",
			type : "warning",
			showCancelButton : true,
			confirmButtonColor : "#DD6B55",
			cancelButtonText : '取消',
			confirmButtonText : "是的，确定！",
			closeOnConfirm : false
		}, function() {
			$.ajax({
				type:'post',
				url:'${ctx}/shared/information/reuse',
				dataType:'json',
				data:{companyCode:companyCode,prjCode:prjCode},
				success : function(data) {
					console.log(JSON.stringify(data));
					if(!isEmpty(data) && JSON.stringify(data) != "{}"){
						swal({
							title: "信息复用成功！",
							text: "",
							type: "success",
							timer: 1100,
							showConfirmButton: false
						});
						console.log(JSON.stringify(data));
						for(var key in data){
							if(!isEmpty(data[key])){
								$("#"+key).val(data[key]);
								if(!$("#"+key).is('input')){
									$("#"+key).selectpicker('refresh');
								}
							}
						}
					}else{
						swal("未能找到该可复用信息！","请确认组织机构代码是否正确？", "error");
					}
				}
			});
		});
	}

    function authSharedInformationReuse(){
        var companyCode = $("#companyCode").val();
        if(isEmpty(companyCode)){
            return;
        }
        $.ajax({
            type:'post',
            url:'${ctx}/shared/information/reuse',
            dataType:'json',
            data:{companyCode:companyCode,type:'企业'},
            success : function(data) {
                console.log(JSON.stringify(data));
                if(!isEmpty(data) && JSON.stringify(data) != "{}"){
                    swal({
                        title: "建设单位信息复用成功！",
                        text: "",
                        type: "success",
                        timer: 1100,
                        showConfirmButton: false
                    });
                    console.log(JSON.stringify(data));
                    for(var key in data){
                        if(!isEmpty(data[key])){
                            $("#"+key).val(data[key]);
                            if(!$("#"+key).is('input')){
                                $("#"+key).selectpicker('refresh');
                            }
                        }
                    }
                }
            }
        });
    }

	<%--function getWsbsPrjInstance(id){--%>
		<%--openWindow({--%>
			<%--id:'wsbsTzjs',--%>
			<%--title:'网厅项目信息',--%>
			<%--url:'${ctx}/project/plusadd/getWsbsPrjInstance',--%>
			<%--data:{'id':id},--%>
			<%--width:'900px',--%>
			<%--height:'560px',--%>
			<%--callBack : function(data){--%>
				<%--if(data != null && data.length >0){--%>
					<%--for(var index in data){--%>
						<%--$("#"+data[index].name).val(data[index].value);--%>
					<%--}--%>
				<%--}--%>
			<%--}--%>
		<%--});--%>
	<%--}--%>
	function clearInput(){
		$("#prjCode").val("");
	}

	function newPrjCode(){
		$.ajax({
			url:'${centextPath}/projectPort/newPrjCode.json',
			type: "POST",
			contentType:'application/json; charset=shengPrjCodeutf-8',
			data:'1',
			dataType: 'html',
			success:function(data){
				$("#prjCode").val(data);
			}
		});
	}

	function checkShengPrjCode(callback){
		var shengPrjCode = $.trim($("#shengPrjCode").val());
		if(shengPrjCode == ""){
			if(typeof callback == "function"){
				callback();
			}
		}else{
			var reqData = {};
			reqData.shengPrjCode = shengPrjCode;
			$.ajax({
				type:'post',
				data:reqData,
				url:'${ctx}/shared/checkShengPrjCode',
				dataType:'json',
				success : function(data) {
					var flag = true;
					if(data != null){
						if(data.resultCode == "PM0000"){
							$("#prjName").val(data.projectName);
							if(typeof callback == "function"){
								callback();
							}
						}else{
							swal((data.errmsg==null||data.errmsg=="")?"请输入正确的省项目编号":data.errmsg,"","error");
							$("#shengPriCode").focus();
							//$("#prjName").val("");
						}
					}else{
						swal("验证省项目编号失败，请重试！","", "error");
						//$("#prjName").val("");
					}
				}
			});
		}
	}

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
			<input type="hidden" name="prjInstanceVo.priceType" value="${project.prjInstanceVo.priceType}">
			<input type="hidden" name="prjInstanceVo.useageType" value="${project.prjInstanceVo.useageType}">
			<input type="hidden" name="projectId" id="projectId" value="${project.prjInstanceVo.id}">
			<input type="hidden" name="companyCode" value="${project.prjInstanceVo.companyCode}">
		</form>
		<div class="card-body card-padding">
			<div class="row">
				<div role="tabpanel" class="tab">
					<div class="tab-content">
						<div role="tabpanel" class="tab-pane active animated fadeInRight in" id="basic">
							<c:if test="${project.prjInstanceVo.id == null }">
								<div class="col-xs-12 form-group">
									<button type="button" class="btn bgm-green btn-info btn-sm"  onclick="selectWsbsTzjs();" style="width:140px;">调取网厅项目信息</button>&nbsp;&nbsp;
									<button type="button" class="btn bgm-deeporange btn-sm" onclick="selectBlspTzjs();" style="width:154px;">调取并联审批项目信息</button>&nbsp;&nbsp;
									<%--TODO sharedInformationReuse 注掉就不获取共享项目信息--%>
									<c:if test="${fns:getDictValue('data_share_switch', 'data_share_switch', '0') eq '1'}">
										<button type="button" class="btn bgm-blue btn-sm" onclick="sharedInformationReuse();" style="width:120px;">共享项目信息</button>
									</c:if>
								</div>
							</c:if>
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
									<div <c:if test="${project.prjInstanceVo.id == null}">class="col-xs-5 form-group" </c:if> class="col-xs-6 form-group">
										<label class="control-label">区项目编号：</label>
										<div class="fg-line readonly">
											<input type="text" name="prjInstanceVo.prjCode" id="prjCode"
												maxlength="30" value="${project.prjInstanceVo.prjCode}"
												class="form-control required" readonly
												placeholder="区项目编号">
										</div>
									</div>
									<c:if test="${project.prjInstanceVo.id == null}">
									<div class="col-xs-1 form-group">
										<label class="control-label"></label>
										<div class="fg-line readonly">
											<button type="button" style="width: 58px;" onclick="newPrjCode()" class="btn btn-info btn-xs waves-effect">重置</button>
										</div>
									</div>
									</c:if>

									<div class="col-xs-6 form-group">
										<label class="control-label">省项目编号：</label>
										<div class="fg-line">
											<%--TODO 上面是验证省项目编号,下面是不验证--%>
											<c:if test="${fns:getDictValue('sheng_code_switch', 'sheng_code_switch', '0') eq '1'}">
												<input type="text" class="form-control required" id="shengPrjCode" onblur="checkShengPrjCode();" maxlength="50" value="${project.prjInstanceVo.shengPrjCode}" placeholder="省项目编号" name="prjInstanceVo.shengPrjCode">
											</c:if>
											<c:if test="${fns:getDictValue('sheng_code_switch', 'sheng_code_switch', '0') eq '0'}">
												<input type="text" class="form-control" id="shengPrjCode" maxlength="50" value="${project.prjInstanceVo.shengPrjCode}" placeholder="省项目编号" name="prjInstanceVo.shengPrjCode">
											</c:if>
										</div>
									</div>

									<div class="col-xs-4 form-group">
										<label class="control-label">项目类别：</label>
										<div class="fg-line">
											<input type="text" class="form-control" id="prjCat"
												maxlength="50" value="${project.prjInstanceVo.prjCat}"
												placeholder="项目类别" name="prjInstanceVo.prjCat">
										</div>
									</div>
									<div class="col-xs-4 form-group">
										<label class="control-label">电话：</label>
										<div class="fg-line">
											<input type="text" class="form-control phone"
												maxlength="15" id="companyMphone"
												value="${project.prjInstanceVo.companyMphone}"
												name="prjInstanceVo.companyMphone" placeholder="电话">
										</div>
									</div>
									<div class="col-xs-4 form-group">
										<label class="control-label">传真：</label>
										<div class="fg-line">
											<input type="text" class="form-control fax" id = "comapnyFax"
												maxlength="15" value="${project.prjInstanceVo.comapnyFax}"
												name="prjInstanceVo.comapnyFax" placeholder="传真">
										</div>
									</div>
									<div class="col-xs-3 form-group">
										<label class="control-label">建设单位：</label>
										<div class="fg-line">
											<input type="text" name="prjInstanceVo.company" maxlength="200"
												class="form-control" id="company"
												value="${project.prjInstanceVo.company}" placeholder="建设单位">
										</div>
									</div>
									<div class="col-xs-1 form-group">
										<label class="control-label"></label>
										<div class="fg-line readonly">
											<button type="button" class="btn btn-info waves-effect" data-toggle="modal" href="#approve" title="加载已有建设单位">
												<i class="md md-add"></i>
											</button>
										</div>
									</div>
									<div class="col-xs-4 form-group">
										<label class="control-label">单位地址：</label>
										<div class="fg-line">
											<input type="text" class="form-control" maxlength="200"  id="companyAddr"
												value="${project.prjInstanceVo.companyAddr}"
												name="prjInstanceVo.companyAddr" placeholder="单位地址">
										</div>
									</div>

									<div class="col-xs-4 form-group">
										<label class="control-label">建设单位企业信用代码或组织机构代码：</label>
										<div class="fg-line">
											<input type="text" class="form-control" id="companyCode"
												maxlength="25" value="${project.prjInstanceVo.companyCode}"
												name="prjInstanceVo.companyCode"
												placeholder="建设单位企业信用代码或组织机构代码">
										</div>
									</div>
									<div class="col-xs-4 form-group">
										<label class="control-label">法人代表：</label>
										<div class="fg-line">
											<input type="text" class="form-control" maxlength="50" id="legalEntity"
												value="${project.prjInstanceVo.legalEntity}"
												name="prjInstanceVo.legalEntity" placeholder="法人代表">
										</div>
									</div>
									<div class="col-xs-4 form-group">
										<label class="control-label">手机（法人代表）：</label>
										<div class="fg-line">
											<input type="text" class="form-control" maxlength="15" id="entityMphone"
												value="${project.prjInstanceVo.entityMphone}"
												name="prjInstanceVo.entityMphone" placeholder="手机">
										</div>
									</div>
									<div class="col-xs-4 form-group">
										<label class="control-label">办公电话（法人代表）：</label>
										<div class="fg-line">
											<input type="text" class="form-control phone" id="entityPhone"
												maxlength="15" value="${project.prjInstanceVo.entityPhone}"
												name="prjInstanceVo.entityPhone" placeholder="办公电话">
										</div>
									</div>
									<div class="col-xs-4 form-group">
										<label class="control-label">受委托人：</label>
										<div class="fg-line">
											<input type="text" class="form-control  required"
												id="agentName" maxlength="50"
												value="${project.prjInstanceVo.agentName}"
												name="prjInstanceVo.agentName" placeholder="受委托人">
										</div>
									</div>
									<div class="col-xs-4 form-group">
										<label class="control-label">手机（受委托人）：</label>
										<div class="fg-line">
											<input type="text" class="form-control  required mobile"
												id="agentMphone" maxlength="15"
												value="${project.prjInstanceVo.agentMphone}"
												name="prjInstanceVo.agentMphone" placeholder="手机">
										</div>
									</div>
									<div class="col-xs-4 form-group">
										<label class="control-label">办公电话（受委托人）：</label>
										<div class="fg-line">
											<input type="text" class="form-control phone"
												id="agentPhone" maxlength="15"
												value="${project.prjInstanceVo.agentPhone}"
												name="prjInstanceVo.agentPhone" placeholder="办公电话">
										</div>
									</div>
									<div class="col-xs-4 form-group">
										<label class="control-label">项目名称：</label>
										<div class="fg-line">
											<input type="text" class="form-control required" id="prjName" maxlength="200"
												value="${project.prjInstanceVo.prjName}"
												name="prjInstanceVo.prjName" placeholder="项目名称" >
										</div>
									</div>
									<div class="col-xs-4 form-group">
										<label class="control-label">项目性质：</label>
										<div class="fg-line">
											<input type="text" class="form-control" maxlength="50" value="${project.prjInstanceVo.prjNature}"
												id="prjNature" name="prjInstanceVo.prjNature" placeholder="项目性质">
										</div>
									</div>
									<div class="col-xs-4 form-group">
										<label class="control-label">项目地址：</label>
										<div class="fg-line">
											<input type="text" class="form-control" id="prjAddr" maxlength="50" value="${project.prjInstanceVo.prjAddr}"
												name="prjInstanceVo.prjAddr" placeholder="项目地址">
										</div>
									</div>
									<div class="col-xs-4 form-group">
										<label class="control-label">总建筑面积(m<sup>2</sup>)：
										</label>
										<div class="fg-line">
											<input type="text" class="form-control" id="prjFloorSpace"
												maxlength="200" value='${project.prjInstanceVo.prjFloorSpace}'
												name="prjInstanceVo.prjFloorSpace" placeholder="总建筑面积">
										</div>
									</div>
									<div class="col-xs-4 form-group">
										<label class="control-label">占地面积(m<sup>2</sup>)：
										</label>
										<div class="fg-line">
											<input type="text" class="form-control" id="prjRedlineSpace"
												maxlength="200" value='${project.prjInstanceVo.prjRedlineSpace}'
												name="prjInstanceVo.prjRedlineSpace" placeholder="占地面积">
										</div>
									</div>
									<div class="col-xs-4 form-group">
										<label class="control-label">项目规模及内容：</label>
										<div class="fg-line">
											<input type="text" class="form-control" id="prjDescription"
												maxlength="500" value="${project.prjInstanceVo.prjDescription}"
												name="prjInstanceVo.prjDescription" placeholder="项目规模及内容">
										</div>
									</div>
									<div class="col-xs-6 form-group">
										<label class="control-label">投资估算： </label>
										<div class="fg-line">
											<input type="text" class="form-control" id="investEstimate"
												maxlength="200" value='${project.prjInstanceVo.investEstimate}'
												name="prjInstanceVo.investEstimate" placeholder="投资估算">
										</div>
									</div>
									<div class="col-xs-6 form-group">
										<label class="control-label">资金来源：</label>
										<div class="fg-line">
											<input type="text" class="form-control" id="fundSource"
												maxlength="150" value="${project.prjInstanceVo.fundSource}"
												name="prjInstanceVo.fundSource" placeholder="资金来源">
										</div>
									</div>
									<div class="col-xs-6 form-group">
										<label class="control-label">相关资料文件描述： </label>
										<div class="fg-line">
											<input type="text" class="form-control" maxlength="150" id="preFilesDesc"
												value="${project.prjInstanceVo.preFilesDesc}"
												name="prjInstanceVo.preFilesDesc" placeholder="相关资料文件描述">
										</div>
									</div>
									<div class="col-xs-6 form-group">
										<label class="control-label">相关资料文件：</label>
										<div class="fg-line">
											<sys:file id="file"
												downloadFileAddress="${project.prjInstanceVo.preFilesAddr}"
												downloadFileName="项目相关资料文件" cssClass="btn-info" isPreview="true"
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