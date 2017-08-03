<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>受理列表</title>
<meta name="decorator" content="blank" />
</head>
<body>
	<form method="post" name="form" id="form" action="${ctx}/project/form/">
	<div class="col-sm-8">
		<div class="card">
			<div class="card-header">
				<h2>新建政府项目</h2>
			</div>
			<div class="card-body card-padding">
				<div class="row">
					<div class="col-sm-12 form-group">
						<label class="control-label col-xs-3">项目金额：</label>
						<label class="radioBox">
							<input type="radio" name="priceType" value="1">
							小于一亿
						</label>
						<label class="radioBox active">
							<input type="radio" name="priceType" value="2" checked>
							大于一亿
						</label>
					</div>
					<div class="col-sm-12 form-group" style="display:none">
						<label class="control-label col-xs-3">用地类型：</label>
						<label class="radioBox">
							<input type="radio" name="landType" value="1">
							规划用地
						</label>
						<label class="radioBox active">
							<input type="radio" name="landType" value="2" checked>
							招拍挂用地
						</label>
					</div>
					<div class="col-sm-12 form-group">
						<label class="control-label col-xs-3">项目用途：</label>
						<label class="radioBox">
							<input type="radio" name="useageType" value="1">
							工业
						</label>
						<label class="radioBox active">
						<input type="radio"	name="useageType" value="2" checked>
							非工业
						</label>
					</div>
					<div class="col-sm-12 form-group" style="display:none">
						<label class="control-label col-xs-3">是否需要初步设计评审：</label>
						<label class="radioBox">
							<input type="radio" name="isNeedPreAudit" value="1">
							需要
						</label>
						<label class="radioBox active">
						<input type="radio"	name="isNeedPreAudit" value="2" checked>
							不需要
						</label>
					</div>
					<div class="col-sm-12 form-group" style="display:none">
						<label class="control-label col-xs-3">特殊专业工程：</label>
						<label class="radioBox">
							<input type="radio" name="isSpecialProject" value="1">
							是
						</label>
						<label class="radioBox active">
						<input type="radio"	name="isSpecialProject" value="2" checked>
							否
						</label>
					</div>
					<div class="col-sm-12 form-group" style="display:none">
						<label class="control-label col-xs-3">是否有基础部分工程或地基处理工程：</label>
						<label class="radioBox">
							<input type="radio" name="isWithBasePart" value="1">
							有
						</label>
						<label class="radioBox active">
						<input type="radio"	name="isWithBasePart" value="2" checked>
							无
						</label>
					</div>
					<div class="col-sm-12 form-group" style="display:none">
						<label class="control-label col-xs-3">信息化类项目：</label>
						<label class="radioBox">
							<input type="radio" name="isItType" value="1">
							是
						</label>
						<label class="radioBox active">
						<input type="radio"	name="isItType" value="2" checked>
							否
						</label>
					</div>
					<div class="col-sm-12 form-group" style="display:none">
						<label class="control-label col-xs-3">市政类项目：</label>
						<label class="radioBox">
							<input type="radio" name="isGovType" value="1">
							是
						</label>
						<label class="radioBox active">
						<input type="radio"	name="isGovType" value="2" checked>
							否
						</label>
					</div>
					<div class="col-sm-3 form-group">
						<button type="submit" class="btn btn-primary waves-effect form-control">新建项目</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	</form>
	<form method="post" name="form1" id="form1" action="${ctx}/project/list/">
	 <input name="prjInstanceVo.prjType" type="hidden" value="1">
		<div class="col-sm-4">
			<div class="card">
				<div class="card-header">
					<h2>查询已有项目</h2>
				</div>
				<div class="card-body card-padding">
					<div class="form-group fg-float">
						<div class="fg-line">
							<input type="text" name="prjInstanceVo.prjCode" maxlength="50" class="form-control">
						</div>
						<label class="fg-label">项目编号：</label>
					</div>
					<div class="form-group fg-float">
						<div class="fg-line">
							<input type="text" name="prjInstanceVo.prjName" maxlength="50" class="form-control">
						</div>
						<label class="fg-label">项目名称：</label>
					</div>
					<button type="submit" class="btn btn-primary waves-effect form-control">查询</button>
				</div>
			</div>
		</div>
	</form>
</body>