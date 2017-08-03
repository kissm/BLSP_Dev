<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>并联审批信息选择设置页面</title>
<meta name="decorator" content="default" />
<style>
	.card {
	    position: relative;
	    background: #fff;
	    box-shadow: 0 0px 0px rgba(0, 0, 0, 0.15);
	    margin-bottom: 30px;
	}
/* 	.card .card-body.card-padding {
	    padding: 18px;
	    padding-left: 48px;
	}
	.checkbox-inline, .radio-inline {
	    vertical-align: top;
    	margin-top: 0;
    	margin-left:-30px;
    	padding-left: 25px;
	} */
</style>
<script type="text/javascript">
	function selectAll(obj){
		if($(obj).val()=="0"){
			$(obj).val("1");
			$(obj).html("取消全选");
			$("input:checkbox").prop("checked",true);
		}else{
			$(obj).val("0");
			$(obj).html("全选");
			$("input:checkbox").prop("checked",false);
		}
	}
	 function selectBlspTzjs() {
		 var backObjs = [];
	     $("input:checkbox:checked").each(function(){
	    	 var obj = {};
	    	 obj.name = $(this).attr("name");
	    	 obj.value = $(this).val();
	    	 backObjs.push(obj);
	     });
		closeWindow(backObjs);
	} 
 
</script>
</head>
<body>
<div class="card">
	<div class="card-header">
		<button type="button" class="col-sm-2 btn bgm-deeporange btn-sm" data-toggle="modal" style="margin-right: 10px;" onclick="selectAll(this);" value="0">全选</button>&nbsp;&nbsp;
		<button type="button" class="col-sm-2 btn btn-info btn-sm"       data-toggle="modal" style="margin-right: 10px;" onclick="javascrtpt:history.go(-1);">返回</button>&nbsp;&nbsp;
		<button type="button" class="col-sm-2 btn bgm-lightgreen btn-sm" data-toggle="modal" style="margin-right: 10px;" onclick="selectBlspTzjs();">确定</button>&nbsp;&nbsp;
	</div>
	<div class="card-body card-padding">
		<div class="col-xs-12">
			<div class="col-sm-4 form-group">
				<label class="checkbox checkbox-inline">
					<%--<input name="prjCode" type="checkbox" value="${blspPrjInstanc.prjCode}" checked />--%>
					<%--<i class="input-helper"></i>--%>
				</label>
				<label >区项目编号：</label>
				<div class="fg-line">
					${blspPrjInstanc.prjCode}
				</div>
			</div>
			<div class="col-sm-4 form-group">
				<label class="checkbox checkbox-inline">
					<input name="prjName" type="checkbox" value="${blspPrjInstanc.prjName }" />
					<i class="input-helper"></i>
				</label>
				<label >项目名称：</label>
				<div class="fg-line">
					${blspPrjInstanc.prjName}
				</div>
			</div>
			<div class="col-sm-4 form-group">
				<label class="checkbox checkbox-inline">
					<input name="prjAddr" type="checkbox" value="${blspPrjInstanc.prjAddr }" />
					<i class="input-helper"></i>
				</label>
				<label >项目地址：</label>
				<div class="fg-line">
					${blspPrjInstanc.prjAddr}
				</div>
			</div>
		</div>
		<div class="col-xs-12">
			<div class="col-sm-4 form-group">
				<label class="checkbox checkbox-inline">
					<input name="company" type="checkbox" value="${blspPrjInstanc.company }" />
					<i class="input-helper"></i>
				</label>
				<label >项目单位：</label>
				<div class="fg-line">
					${blspPrjInstanc.company}
				</div>
			</div>
			<div class="col-sm-4 form-group">
				<label class="checkbox checkbox-inline">
					<input name="companyAddr" type="checkbox" value="${blspPrjInstanc.companyAddr }" />
					<i class="input-helper"></i>
				</label>
				<label >单位地址：</label>
				<div class="fg-line">
					${blspPrjInstanc.companyAddr}
				</div>
			</div>
			<div class="col-sm-4 form-group">
				<label class="checkbox checkbox-inline">
					<input name="companyCode" type="checkbox" value="${blspPrjInstanc.companyCode }" />
					<i class="input-helper"></i>
				</label>
				<label >企业信用代码或组织机构代码：</label>
				<div class="fg-line">
					${blspPrjInstanc.companyCode}
				</div>
			</div>
		</div>
		<div class="col-xs-12">
			<div class="col-sm-4 form-group">
				<label class="checkbox checkbox-inline">
					<input name="prjNature" type="checkbox" value="${blspPrjInstanc.prjNature }" />
					<i class="input-helper"></i>
				</label>
				<label >项目性质：</label>
				<div class="fg-line">
					${blspPrjInstanc.prjNature}
				</div>
			</div>
			<div class="col-sm-4 form-group">
				<label class="checkbox checkbox-inline">
					<input name="prjCat" type="checkbox" value="${blspPrjInstanc.prjCat }" />
					<i class="input-helper"></i>
				</label>
				<label >项目类别：</label>
				<div class="fg-line">
					${blspPrjInstanc.prjCat}
				</div>
			</div>
			<div class="col-sm-4 form-group">
				<label class="checkbox checkbox-inline">
					<input name="prjDescription" type="checkbox" value="${blspPrjInstanc.prjDescription }" />
					<i class="input-helper"></i>
				</label>
				<label >项目规模及内容：</label>
				<div class="fg-line">
					${blspPrjInstanc.prjDescription}
				</div>
			</div>
		</div>
		<div class="col-xs-12">
			<div class="col-sm-4 form-group">
				<label class="checkbox checkbox-inline">
					<input name="fundSource" type="checkbox" value="${blspPrjInstanc.fundSource }" />
					<i class="input-helper"></i>
				</label>
				<label >资金来源：</label>
				<div class="fg-line">
					${blspPrjInstanc.fundSource}
				</div>
			</div>
			<div class="col-sm-4 form-group">
				<label class="checkbox checkbox-inline">
					<input name="investEstimate" type="checkbox" value="${blspPrjInstanc.investEstimate }" />
					<i class="input-helper"></i>
				</label>
				<label >投资估算(万元)：</label>
				<div class="fg-line">
					${blspPrjInstanc.investEstimate}
				</div>
			</div>
			<div class="col-xs-4 form-group">
				<label class="checkbox checkbox-inline">
					<input name="companyMphone" type="checkbox" value="${blspPrjInstanc.companyMphone }" />
					<i class="input-helper"></i>
				</label>
				<label >电话：</label>
				<div class="fg-line">
					${blspPrjInstanc.companyMphone}
				</div>
			</div>
		</div>
		<div class="col-xs-12">
			<div class="col-sm-4 form-group">
				<label class="checkbox checkbox-inline">
					<input name="prjFloorSpace" type="checkbox" value="${blspPrjInstanc.prjFloorSpace }" />
					<i class="input-helper"></i>
				</label>
				<label >总建筑面积(m<sup>2</sup>)：</label>
				<div class="fg-line">
					${blspPrjInstanc.prjFloorSpace}
				</div>
			</div>
			<div class="col-sm-4 form-group">
				<label class="checkbox checkbox-inline">
					<input name="prjRedlineSpace" type="checkbox" value="${blspPrjInstanc.prjRedlineSpace }" />
					<i class="input-helper"></i>
				</label>
				<label >占地面积(m<sup>2</sup>)：</label>
				<div class="fg-line">
					${blspPrjInstanc.prjRedlineSpace}
				</div>
			</div>
			<div class="col-sm-4 form-group">
				<label class="checkbox checkbox-inline">
					<input name="comapnyFax" type="checkbox" value="${blspPrjInstanc.comapnyFax }" />
					<i class="input-helper"></i>
				</label>
				<label >传真：</label>
				<div class="fg-line">
					${blspPrjInstanc.comapnyFax}
				</div>
			</div>
		</div>
		<div class="col-xs-12">
			<div class="col-sm-4 form-group">
				<label class="checkbox checkbox-inline">
					<input name="legalEntity" type="checkbox" value="${blspPrjInstanc.legalEntity }" />
					<i class="input-helper"></i>
				</label>
				<label >法人代表：</label>
				<div class="fg-line">
					${blspPrjInstanc.legalEntity}
				</div>
			</div>
			<div class="col-sm-4 form-group">
				<label class="checkbox checkbox-inline">
					<input name="entityMphone" type="checkbox" value="${blspPrjInstanc.entityMphone }" />
					<i class="input-helper"></i>
				</label>
				<label >手机（法人代表）：</label>
				<div class="fg-line">
					${blspPrjInstanc.entityMphone}
				</div>
			</div>
			<div class="col-sm-4 form-group">
				<label class="checkbox checkbox-inline">
					<input name="entityPhone" type="checkbox" value="${blspPrjInstanc.entityPhone }" />
					<i class="input-helper"></i>
				</label>
				<label >办公电话（法人代表）：</label>
				<div class="fg-line">
					${blspPrjInstanc.entityPhone}
				</div>
			</div>
		</div>
		<div class="col-xs-12">
			<div class="col-sm-4 form-group">
				<label class="checkbox checkbox-inline">
					<input name="agentName" type="checkbox" value="${blspPrjInstanc.agentName }" />
					<i class="input-helper"></i>
				</label>
				<label >受委托人：</label>
				<div class="fg-line">
					${blspPrjInstanc.agentName}
				</div>
			</div>
			<div class="col-sm-4 form-group">
				<label class="checkbox checkbox-inline">
					<input name="agentMphone" type="checkbox" value="${blspPrjInstanc.agentMphone }" />
					<i class="input-helper"></i>
				</label>
				<label >手机（受委托人）：</label>
				<div class="fg-line">
					${blspPrjInstanc.agentMphone}
				</div>
			</div>
			<div class="col-sm-4 form-group">
				<label class="checkbox checkbox-inline">
					<input name="agentPhone" type="checkbox" value="${blspPrjInstanc.agentPhone }" />
					<i class="input-helper"></i>
				</label>
				<label >办公电话（受委托人）：</label>
				<div class="fg-line">
					${blspPrjInstanc.agentPhone}
				</div>
			</div>
		</div>
	</div>
</div>
</body>
</html>