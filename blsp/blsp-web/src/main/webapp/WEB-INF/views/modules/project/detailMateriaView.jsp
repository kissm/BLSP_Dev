<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>申报材料</title>
	<script type="text/javascript">
		$(document).ready(function() {
		});	
	</script>
</head>
<body>
		<div role="tabpanel" class="tab-pane active animated fadeInRight in" id="tab1">
			<div role="tabpanel">
				<ul class="tab-nav" role="tablist" data-tab-color="cyan">
					<li class="active" role="presentation"><a href="#materials1" aria-controls="materials1" role="tab" data-toggle="tab">申报材料</a></li>
					<c:if test="${not empty project.formRfYdjsBjspVo or not empty project.formRfBjshVo}">
						<li role="presentation"><a href="#profile1" aria-controls="profile1" role="tab" data-toggle="tab">业务信息</a></li>
					</c:if>
				</ul>
				<div class="tab-content">
				
					<div role="tabpanel" class="tab-pane animated fadeIn in active" id="materials1">
						<div class="table-responsive">
							<table class="table table-striped table-vmiddle bootgrid-table">
								<thead>
									<tr>
										<th class="text-center col-xs-2">事项名称</th>
										<th>材料名称</th>
										<th class="text-center col-xs-1">是否提供</th>
										<th class="col-xs-1">下载</th>
										<th class="text-center col-xs-2">上传日期</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${project.prjStageMaterialVoMap}"
										var="map" varStatus="v">
										<c:forEach items="${map.value}" var="mater"
											varStatus="var">
											<tr>
												<c:if test="${var.index eq 0}"><td rowspan="${fn:length(map.value)}" class='${v.index%2==0?"odd":"even"}'>${fns:getPrjTaskDefineVo(map.key).taskName}</td></c:if>
												<td style="padding-left: 30px;">${mater.materName}</td>
												<td class="text-center">
													<c:if test="${mater.isComplete eq '1'}">
														<label class="checkbox checkbox-inline"> 
															<input disabled type="checkbox" checked name="checkboxDisabled"><i class="input-helper"></i>
														</label>
													</c:if>
												</td>
												<td><c:if test="${mater.materialAddr ne '' && !empty mater.materialAddr}">
														<button class="btn btn-info btn-icon" type="button"
															onClick="window.open('${ctx}/sys/download?pathUrl=${mater.materialAddr}&coi=${mater.materialName}')">
															<i class="md md-file-download"></i>
														</button>
														<button data-placement="bottom" type="button" title="预览"
															onclick="window.open('${ctx}/sys/filePreview?pathUrl=${mater.materialAddr}')"
															class="btn btn-success btn-icon btn-file">
															<i class="md md-visibility"></i>
														</button>
													</c:if>
													<c:if test="${mater.materialAddr eq '' || empty mater.materialAddr}">
														<font class="c-red">未上传</font>
													</c:if></td>
												<td class="text-center"><c:if test="${mater.materialAddr ne ''}">
														<fmt:formatDate value="${mater.creatTime}"
															pattern="yyyy-MM-dd" />
													</c:if></td>
											</tr>
										</c:forEach>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				
				
					<div role="tabpanel" class="tab-pane animated fadeIn" id="profile1">
					<c:if test="${not empty project.formRfYdjsBjspVo}">
						<p class="c-black f-500 m-b-25">人防工程易地建设报建审批</p>
						<div class="row">
							<div class="col-xs-4 form-group">
								<label class="control-label">工程名称：${project.formRfYdjsBjspVo.prjName}</label>
							</div>
							<div class="col-xs-4 form-group">
								<label class="control-label">单位地址：${project.formRfYdjsBjspVo.companyAddr}</label>
							</div>
							<div class="col-xs-4 form-group">
								<label class="control-label">单位电话：${project.formRfYdjsBjspVo.companyMphone}</label>
							</div>

							<div class="col-xs-4 form-group">
								<label class="control-label">联系人：${project.formRfYdjsBjspVo.linkman}</label>
							</div>
							<div class="col-xs-4 form-group">
								<label class="control-label">联系电话：${project.formRfYdjsBjspVo.linkmanPhone}</label>
							</div>
							<div class="col-xs-4 form-group">
								<label class="control-label">申请日期：<fmt:formatDate
										value="${project.formRfYdjsBjspVo.applyDate}"
										pattern="yyyy-MM-dd" /></label>
							</div>

							<div class="col-xs-4 form-group">
								<label class="control-label">工程地点：${project.formRfYdjsBjspVo.prjAddress}</label>
							</div>
							<div class="col-xs-4 form-group">
								<label class="control-label">建设单位：${project.formRfYdjsBjspVo.company}</label>
							</div>
							<div class="col-xs-4 form-group">
								<label class="control-label">设计单位：${project.formRfYdjsBjspVo.designCompany}</label>
							</div>

							<div class="col-xs-4 form-group">
								<label class="control-label">建设单位法人代表：${project.formRfYdjsBjspVo.legalEntity}</label>
							</div>
							<div class="col-xs-4 form-group">
								<label class="control-label">法人代表身份证号码：${project.formRfYdjsBjspVo.entityIdcode}</label>
							</div>
							<div class="col-xs-4 form-group">
								<label class="control-label">法人代表联系电话：${project.formRfYdjsBjspVo.entityPhone}</label>
							</div>

							<div class="col-xs-12 form-group">
								<label class="control-label">防空地下室易地建设方式：<c:if
										test="${project.formRfYdjsBjspVo.basementType eq '1'}">缴易地建设费</c:if>
									<c:if
										test="${project.formRfYdjsBjspVo.basementType eq '2'}">小区内自建</c:if>
									<c:if
										test="${project.formRfYdjsBjspVo.basementType eq '3'}">易地自建</c:if></label>
							</div>
						</div>
						</c:if>
						
						<c:if test="${not empty project.formRfBjshVo}">
						<p class="c-black f-500 m-b-25">人防工程报建审核</p>
						<div class="row">
							<div class="col-xs-4 form-group">
								<label class="control-label">工程名称：${project.formRfBjshVo.prjName}</label>
							</div>
							<div class="col-xs-4 form-group">
								<label class="control-label">单位地址：${project.formRfBjshVo.companyAddr}</label>
							</div>
							<div class="col-xs-4 form-group">
								<label class="control-label">单位电话：${project.formRfBjshVo.companyMphone}</label>
							</div>

							<div class="col-xs-4 form-group">
								<label class="control-label">联系人：${project.formRfBjshVo.linkman}</label>
							</div>
							<div class="col-xs-4 form-group">
								<label class="control-label">联系电话：${project.formRfBjshVo.linkmanPhone}</label>
							</div>
							<div class="col-xs-4 form-group">
								<label class="control-label">申请日期：<fmt:formatDate
										value="${project.formRfBjshVo.applyDate}"
										pattern="yyyy-MM-dd" /></label>
							</div>

							<div class="col-xs-4 form-group">
								<label class="control-label">工程地点：${project.formRfBjshVo.prjAddress}</label>
							</div>
							<div class="col-xs-4 form-group">
								<label class="control-label">建设单位：${project.formRfBjshVo.company}</label>
							</div>
							<div class="col-xs-4 form-group">
								<label class="control-label">设计单位：${project.formRfBjshVo.designCompany}</label>
							</div>

							<div class="col-xs-4 form-group">
								<label class="control-label">建设单位法人代表：${project.formRfBjshVo.legalEntity}</label>
							</div>
							<div class="col-xs-4 form-group">
								<label class="control-label">法人代表身份证号码：${project.formRfBjshVo.entityIdcode}</label>
							</div>
							<div class="col-xs-4 form-group">
								<label class="control-label">法人代表联系电话：${project.formRfBjshVo.entityPhone}</label>
							</div>
							<div class="col-xs-4 form-group">
								<label class="control-label">防空地下室主要出入口处城市坐标及高程：</label>
							</div>
							<div class="col-xs-2 form-group">
								<label class="control-label">x坐标：${project.formRfBjshVo.basementXPoint}</label>
							</div>
							<div class="col-xs-2 form-group">
								<label class="control-label">y坐标：${project.formRfBjshVo.basementYPoint}</label>
							</div>
							<div class="col-xs-2 form-group">
								<label class="control-label">高程：${project.formRfBjshVo.basementGc}</label>
							</div>
						</div>
						</c:if>
						
					</div>
					
					
				</div>
			</div>
		</div>
</body>
</html>