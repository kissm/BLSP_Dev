<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<sys:message content="${message2}" type="warning"/>
		<div class="row">
			<div class="col-xs-4 form-group">
				<label class="control-label" >办事人：${dto.name}</label>
			</div>
			<div class="col-xs-6 form-group">
				<label class="control-label" >联系电话：${dto.phoneNum}</label>
			</div>

			<div class="col-xs-4 form-group">
				<label class="control-label" >预约类型：
					<c:if test="${dto.appintType eq 1}">新办</c:if>
					<c:if test="${dto.appintType eq 2}">咨询</c:if>
					<c:if test="${dto.appintType eq 3}">补交资料</c:if>
					<c:if test="${dto.appintType eq 4}">领证</c:if>
				
				</label>
			</div>								
			<div class="col-xs-6 form-group">
				<label class="control-label" >预约日期：<fmt:formatDate value="${dto.appointDate}" pattern="yyyy-MM-dd"/></label>
			</div>	
			
			<div class="col-xs-4 form-group">
				<label class="control-label" >证件类型：<c:if test="${not empty dto.certType}">身份证</c:if></label>
			</div>
			<div class="col-xs-6 form-group">
				<label class="control-label" >证件号码：${dto.certCode}</label>
			</div>
			
			<div class="col-xs-4 form-group">
				<label class="control-label" >预约流水号：${dto.appintSeq}</label>
			</div>
			
			<div class="col-xs-6 form-group">
				<label class="control-label" >申办流水号：${dto.applySeq}</label>
			</div>
			
			<div class="col-xs-4 form-group">
				<label class="control-label" >预约办事时间起：${dto.appointBegin}</label>
			</div>
			<div class="col-xs-6 form-group">
				<label class="control-label" >预约办事时间止：${dto.appintEnd}</label>
			</div>			
			
			<div class="col-xs-4 form-group">
				<label class="control-label" >部门名称：${dto.deptName}</label>
			</div>
			<div class="col-xs-6 form-group">
				<label class="control-label" >部门编码：${dto.deptCode}</label>
			</div>
			
			<div class="col-xs-4 form-group">
				<label class="control-label" >业务类型：
					<c:if test="${dto.businessType eq '1001'}">APP预约事项入口</c:if>
					<c:if test="${dto.businessType eq '1002'}">APP申办事项入口</c:if>
					<c:if test="${dto.businessType eq '1003'}">APP办事指南查询入口</c:if>
					<c:if test="${dto.businessType eq '2001'}">预约</c:if>
					<c:if test="${dto.businessType eq '2002'}">办事进度查询</c:if>
					<c:if test="${dto.businessType eq '3001'}">办事进度查询</c:if>
				</label>
			</div>			
			<div class="col-xs-6 form-group">
				<label class="control-label" >业务类型编码：${dto.businessCode}</label>
			</div>
			<%-- 
			<div class="col-xs-4 form-group">
				<label class="control-label" >区项目编号：${dto.prjCode}</label>
			</div>		
			<div class="col-xs-6 form-group">
				<label class="control-label" >码图编码类型：
					<c:if test="${dto.lpcodeType eq '1001'}">APP预约事项入口</c:if>
					<c:if test="${dto.lpcodeType eq '1002'}">APP申办事项入口</c:if>
					<c:if test="${dto.lpcodeType eq '1003'}">APP办事指南查询入口</c:if>
					<c:if test="${dto.lpcodeType eq '2001'}">预约</c:if>
					<c:if test="${dto.lpcodeType eq '2002'}">办事进度查询</c:if>
				</label>
			</div>				
				
			<div class="col-xs-4 form-group">
				<label class="control-label" >办事事项编码：${dto.itemCode}</label>
			</div> --%>
			<div class="col-xs-4 form-group">
				<label class="control-label" >叫号序列号：${dto.windowSeq}</label>
			</div>
			
			<div class="col-xs-6 form-group">
				<label class="control-label" >窗口号：${dto.windowCode}</label>
			</div>

		</div>
		<c:if test="${not empty dto.appintSeq}">
			<div class="btn-demo text-center m-t-20">
				<button class="btn btn-info waves-effect" data-toggle="modal" onclick="javascrtpt:window.location.href='${ctx}/project/index'">受理</button>
				<%-- <button class="btn btn-default waves-effect" onclick="javascrtpt:history.go(-1)">返回</button> --%>
			</div>
		</c:if>

