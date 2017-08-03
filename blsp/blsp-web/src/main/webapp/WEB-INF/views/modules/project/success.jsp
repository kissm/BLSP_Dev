<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<script>
	function tijiao() {
		var d = $("#date").val();
		if(typeof(d) == "undefined"){
			window.location.href = "${ctx}/project/print?id=${project.prjInstanceVo.id}&name=受理反馈通知书.doc";
		}else{
			window.location.href = "${ctx}/project/print?id=${project.prjInstanceVo.id}&name=受理反馈通知书.doc&date="+ d;
		}
	}
</script>
<c:set value="0" var="all" />
<div class="modal-header">
<c:if test="${not empty project.prjTaskVoList}">
<h4 class="modal-title">受理反馈通知书</h4>
</c:if>
<c:if test="${empty project.prjTaskVoList}">
<h4 class="modal-title">保存成功</h4>
</c:if>
</div>
<div class="modal-body  p-20 bgm-white text-muted o-auto"
	style="max-height: 450px;">
	<div class="row" id="success_">
		<c:if test="${not empty project.prjTaskVoList}">
			<div class="form-group col-sm-3 text-right">
				<label class="control-label">受理编号：</label>
			</div>
			<div class="form-group col-sm-3">
				<label class="control-label">${project.prjBusinessAcceptVo.acceptCode}&nbsp;</label>
			</div>
			<div class="form-group col-sm-3 text-right">
				<label class="control-label">受理状态：</label>
			</div>
			<div class="form-group col-sm-3">
				<label class="control-label">受理成功</label>
			</div>
		</c:if>
		<div class="form-group col-sm-3 text-right">
			<label class="control-label">区项目编号：</label>
		</div>
		<div class="form-group col-sm-3">
			<label class="control-label">${project.prjInstanceVo.prjCode}&nbsp;</label>
		</div>
		<div class="form-group col-sm-3 text-right">
			<label class="control-label">项目名称：</label>
		</div>
		<div class="form-group col-sm-3">
			<label class="control-label">${project.prjInstanceVo.prjName}&nbsp;</label>
		</div>
		<c:forEach items="${project.prjStageMaterialVoMap}" var="map"
			varStatus="c">
			<c:set value="0" var="isOver" />
			<div class="form-group col-sm-3 text-right">
					<label class="control-label">事项名称：</label>
			</div>
			<div class="form-group col-sm-9">
				<label class="control-label">${fns:getPrjTaskDefineVo(map.key).taskName}&nbsp;</label>
			</div>
			<div class="form-group col-sm-3 text-right">
				<label class="control-label">已提交材料：</label>
			</div>
			<div class="form-group col-sm-9">
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
					</c:forEach> &nbsp;
				</label>
			</div>
			<c:if test="${isOver eq '1'}">
				<c:set value="0" var="code" />
				<div class="form-group col-sm-3 text-right">
					<label class="control-label">未提交材料：</label>
				</div>
				<div class="form-group col-sm-9">
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
		</c:forEach>
		<c:if test="${all eq '1'}">
		<div class="form-group col-sm-3 text-right" >
					<label class="control-label" style="padding-top: 7px">请于：</label>
				</div>
				<div class="form-group col-xs-3">
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
				<div class="form-group col-xs-3">
					<label class="control-label" style="padding-top: 7px">前提交所需材料</label>
				</div>
		</c:if>
	</div>
</div>
<div class="modal-footer">
<c:if test="${project.prjTaskVoList ne null}">
	<button type="button" class="btn btn-link" onClick="tijiao()">打印</button>
</c:if>
<c:if test="${project.prjInstanceVo.prjType ne 2}">
	<button type="button" class="btn btn-link"
		onclick="javascrtpt:window.location.href='${ctx}/project/list'">关闭</button>
</c:if>
<c:if test="${project.prjInstanceVo.prjType eq 2}">
	<button type="button" class="btn btn-link"
		onclick="javascrtpt:window.location.href='${ctx}/enterprise/project/list'">关闭</button>
</c:if>
</div>
