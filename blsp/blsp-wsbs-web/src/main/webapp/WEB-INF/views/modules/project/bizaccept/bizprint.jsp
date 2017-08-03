<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>受理表单</title>
<meta name="decorator" content="blank" />
<script>
	function tijiao() {
		var d = $("#date").val();
		if(typeof(d) == "undefined"){
			window.location.href = "${ctx}/project/bizaccept/print?id=${project.prjInstanceVo.id}&name=受理反馈通知书.doc";
		}else{
			window.location.href = "${ctx}/project/bizaccept/print?id=${project.prjInstanceVo.id}&name=受理反馈通知书.doc&date="+ d;
		}
	}
</script>
</head>
<body>
	<div class="card">
		<div class="card-header">
			<h2>
				<c:if test="${not empty project.prjTaskVoList}">
					受理反馈通知书
				</c:if>
				<c:if test="${empty project.prjTaskVoList}">
					保存成功
				</c:if>
			</h2>
			<ul class="actions">
				<li>
					<button data-toggle="tooltip" data-placement="bottom" title="打印" class="btn btn-primary btn-icon m-r-5" onClick="tijiao()">
						<i class="md  md-file-download"></i>
					</button>
				</li>
				<li>
					<button data-toggle="tooltip" data-placement="bottom" title="返回项目列表" class="btn btn-warning btn-icon m-r-5" onclick="javascrtpt:window.location.href='${ctx}/project/bizaccept/list'">
						<i class="md md-arrow-back"></i>
					</button>
				</li>
			</ul>
		</div>
		<div class="card-body card-padding">
			<div class="row">
				<c:if test="${not empty project.prjTaskVoList}">
					<div class="p-15 col-sm-3 text-right">
						<label class="control-label">受理编号：</label>
					</div>
					<div class="p-15 col-sm-3">
						<label class="control-label">${project.prjBusinessAcceptVo.acceptCode}&nbsp;</label>
					</div>
					<div class="p-15 col-sm-3 text-right">
						<label class="control-label">受理状态：</label>
					</div>
					<div class="p-15 col-sm-3">
						<label class="control-label">受理成功</label>
					</div>
				</c:if>
				<div class="p-15 col-sm-3 text-right">
					<label class="control-label">项目编号：</label>
				</div>
				<div class="p-15 col-sm-3">
					<label class="control-label">${project.prjInstanceVo.prjCode}&nbsp;</label>
				</div>
				<div class="p-15 col-sm-3 text-right">
					<label class="control-label">项目名称：</label>
				</div>
				<div class="p-15 col-sm-3">
					<label class="control-label">${project.prjInstanceVo.prjName}&nbsp;</label>
				</div>
				<c:forEach items="${project.prjStageMaterialVoMap}" var="map" varStatus="c">
					<c:set value="0" var="isOver" />
					<div class="col-sm-12">
						<div class="p-15 col-sm-3 text-right">
							<label>事项名称：</label>
						</div>
						<div class="p-15 col-sm-9">
							<label>${fns:getPrjTaskDefineVo(map.key).taskName}&nbsp;</label>
						</div>
					</div>
					<div class="col-sm-12">
						<div class="col-sm-6">
							<div class="p-15 col-sm-6 text-right">
								<label class="control-label">已提交材料：</label>
							</div>
							<div class="p-15 col-sm-6">
								<label class="control-label"> <c:set value="0" var="xuhao" />
									<c:forEach items="${map.value}" var="matr"
										varStatus="c">
										<c:if test="${matr.isComplete eq '1'}">
											<c:set value="${xuhao+1}" var="xuhao" />
												${xuhao}.${matr.materName}</br>
										</c:if>
										<c:if test="${matr.isComplete ne '1'}">
											<c:set value="1" var="isOver" />
											<c:set value="1" var="all" />
										</c:if>
									</c:forEach>&nbsp;
								</label>
							</div>
						</div>
						<div class="col-sm-6">
							<c:if test="${isOver eq '1'}">
								<c:set value="0" var="code" />
								<div class="p-15 col-sm-6 text-right">
									<label class="control-label">未提交材料：</label>
								</div>
								<div class="p-15 col-sm-6">
									<label class="control-label"> <c:forEach
											items="${map.value}" var="matr"
											varStatus="c">
											<c:if test="${matr.isComplete ne '1'}">
												<c:set value="${code+1}" var="code" />
													${code}.${matr.materName}</br>
											</c:if>
										</c:forEach>
									</label>
								</div>
							</c:if>
						</div>
					</div>
				</c:forEach>
				<c:if test="${all eq '1'}">
					<div class="p-15 col-sm-2 text-right" >
						<label class="control-label" style="padding-top: 7px">请于：</label>
					</div>
					<div class="p-15 col-sm-2">
						<div class="input-group">
							<span class="input-group-addon"><i class="md md-event"></i></span>
							<div class="dtp-container dropdown fg-line">
								<input type="text" id="date"
									class="form-control date-picker date required"
									value='${date}'
									data-toggle="dropdown" name="formRfYdjsBjspVo.applyDate"
									placeholder="单击此处..." aria-expanded="false">
							</div>
						</div>
					</div>
					<div class="p-15 col-sm-2">
						<label class="control-label" style="padding-top: 7px">前提交所需材料</label>
					</div>
				</c:if>
			</div>
		</div>
		<div class="btn-demo text-center p-15">
			<button class="btn btn-primary" onClick="tijiao()">打印</button>
			<button class="btn btn-warning" onclick="javascrtpt:window.location.href='${ctx}/project/bizaccept/list'">返回项目列表</button>
		</div>
	</div>
</body>
