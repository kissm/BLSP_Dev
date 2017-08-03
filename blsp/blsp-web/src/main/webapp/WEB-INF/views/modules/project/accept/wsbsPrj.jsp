<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>申请人设置</title>
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
					<input name="prjCode" type="checkbox" value="${wsbsProjInstanceDto.prjCode}" checked />
					<i class="input-helper"></i>
				</label>
				<label >区项目编号：</label>
				<div class="fg-line">
					${wsbsProjInstanceDto.prjCode}
				</div>
			</div>
			<div class="col-sm-4 form-group">
				<label class="checkbox checkbox-inline">
					<input name="prjName" type="checkbox" value="${wsbsProjInstanceDto.prjName }" />
					<i class="input-helper"></i>
				</label>
				<label >项目名称：</label>
				<div class="fg-line">
					${wsbsProjInstanceDto.prjName}
				</div>
			</div>
			<div class="col-sm-4 form-group">
				<label class="checkbox checkbox-inline">
					<input name="prjAddr" type="checkbox" value="${wsbsProjInstanceDto.prjAddr }" />
					<i class="input-helper"></i>
				</label>
				<label >项目地址：</label>
				<div class="fg-line">
					${wsbsProjInstanceDto.prjAddr}
				</div>
			</div>
		</div>
		<div class="col-xs-12">
			<div class="col-sm-4 form-group">
				<label class="checkbox checkbox-inline">
					<input name="company" type="checkbox" value="${wsbsProjInstanceDto.company }" />
					<i class="input-helper"></i>
				</label>
				<label >项目单位：</label>
				<div class="fg-line">
					${wsbsProjInstanceDto.company}
				</div>
			</div>
			<div class="col-sm-4 form-group">
				<label class="checkbox checkbox-inline">
					<input name="companyAddr" type="checkbox" value="${wsbsProjInstanceDto.companyAddr }" />
					<i class="input-helper"></i>
				</label>
				<label >单位地址：</label>
				<div class="fg-line">
					${wsbsProjInstanceDto.companyAddr}
				</div>
			</div>
			<div class="col-sm-4 form-group">
				<label class="checkbox checkbox-inline">
					<input name="companyCode" type="checkbox" value="${wsbsProjInstanceDto.companyCode }" />
					<i class="input-helper"></i>
				</label>
				<label >企业信用代码或组织机构代码：</label>
				<div class="fg-line">
					${wsbsProjInstanceDto.companyCode}
				</div>
			</div>
		</div>
		<div class="col-xs-12">
			<div class="col-sm-4 form-group">
				<label class="checkbox checkbox-inline">
					<input name="prjNature" type="checkbox" value="${wsbsProjInstanceDto.prjNature }" />
					<i class="input-helper"></i>
				</label>
				<label >项目性质：</label>
				<div class="fg-line">
					${wsbsProjInstanceDto.prjNature}
				</div>
			</div>
			<div class="col-sm-4 form-group">
				<label class="checkbox checkbox-inline">
					<input name="prjCat" type="checkbox" value="${wsbsProjInstanceDto.prjCat }" />
					<i class="input-helper"></i>
				</label>
				<label >项目类别：</label>
				<div class="fg-line">
					${wsbsProjInstanceDto.prjCat}
				</div>
			</div>
			<div class="col-sm-4 form-group">
				<label class="checkbox checkbox-inline">
					<input name="prjDescription" type="checkbox" value="${wsbsProjInstanceDto.prjDescription }" />
					<i class="input-helper"></i>
				</label>
				<label >项目规模及内容：</label>
				<div class="fg-line">
					${wsbsProjInstanceDto.prjDescription}
				</div>
			</div>
		</div>
		<div class="col-xs-12">
			<div class="col-sm-4 form-group">
				<label class="checkbox checkbox-inline">
					<input name="fundSource" type="checkbox" value="${wsbsProjInstanceDto.fundSource }" />
					<i class="input-helper"></i>
				</label>
				<label >资金来源：</label>
				<div class="fg-line">
					${wsbsProjInstanceDto.fundSource}
				</div>
			</div>
			<div class="col-sm-4 form-group">
				<label class="checkbox checkbox-inline">
					<input name="investEstimate" type="checkbox" value="${wsbsProjInstanceDto.investEstimate }" />
					<i class="input-helper"></i>
				</label>
				<label >投资估算(万元)：</label>
				<div class="fg-line">
					${wsbsProjInstanceDto.investEstimate}
				</div>
			</div>
			<div class="col-xs-4 form-group">
				<label class="checkbox checkbox-inline">
					<input name="companyMphone" type="checkbox" value="${wsbsProjInstanceDto.companyMphone }" />
					<i class="input-helper"></i>
				</label>
				<label >电话：</label>
				<div class="fg-line">
					${wsbsProjInstanceDto.companyMphone}
				</div>
			</div>
		</div>
		<div class="col-xs-12">
			<div class="col-sm-4 form-group">
				<label class="checkbox checkbox-inline">
					<input name="prjFloorSpace" type="checkbox" value="${wsbsProjInstanceDto.prjFloorSpace }" />
					<i class="input-helper"></i>
				</label>
				<label >总建筑面积(m<sup>2</sup>)：</label>
				<div class="fg-line">
					${wsbsProjInstanceDto.prjFloorSpace}
				</div>
			</div>
			<div class="col-sm-4 form-group">
				<label class="checkbox checkbox-inline">
					<input name="prjRedlineSpace" type="checkbox" value="${wsbsProjInstanceDto.prjRedlineSpace }" />
					<i class="input-helper"></i>
				</label>
				<label >占地面积(m<sup>2</sup>)：</label>
				<div class="fg-line">
					${wsbsProjInstanceDto.prjRedlineSpace}
				</div>
			</div>
			<div class="col-sm-4 form-group">
				<label class="checkbox checkbox-inline">
					<input name="comapnyFax" type="checkbox" value="${wsbsProjInstanceDto.comapnyFax }" />
					<i class="input-helper"></i>
				</label>
				<label >传真：</label>
				<div class="fg-line">
					${wsbsProjInstanceDto.comapnyFax}
				</div>
			</div>
		</div>
		<div class="col-xs-12">
			<div class="col-sm-4 form-group">
				<label class="checkbox checkbox-inline">
					<input name="legalEntity" type="checkbox" value="${wsbsProjInstanceDto.legalEntity }" />
					<i class="input-helper"></i>
				</label>
				<label >法人代表：</label>
				<div class="fg-line">
					${wsbsProjInstanceDto.legalEntity}
				</div>
			</div>
			<div class="col-sm-4 form-group">
				<label class="checkbox checkbox-inline">
					<input name="entityMphone" type="checkbox" value="${wsbsProjInstanceDto.entityMphone }" />
					<i class="input-helper"></i>
				</label>
				<label >手机（法人代表）：</label>
				<div class="fg-line">
					${wsbsProjInstanceDto.entityMphone}
				</div>
			</div>
			<div class="col-sm-4 form-group">
				<label class="checkbox checkbox-inline">
					<input name="entityPhone" type="checkbox" value="${wsbsProjInstanceDto.entityPhone }" />
					<i class="input-helper"></i>
				</label>
				<label >办公电话（法人代表）：</label>
				<div class="fg-line">
					${wsbsProjInstanceDto.entityPhone}
				</div>
			</div>
		</div>
		<div class="col-xs-12">
			<div class="col-sm-4 form-group">
				<label class="checkbox checkbox-inline">
					<input name="agentName" type="checkbox" value="${wsbsProjInstanceDto.agentName }" />
					<i class="input-helper"></i>
				</label>
				<label >受委托人：</label>
				<div class="fg-line">
					${wsbsProjInstanceDto.agentName}
				</div>
			</div>
			<div class="col-sm-4 form-group">
				<label class="checkbox checkbox-inline">
					<input name="agentMphone" type="checkbox" value="${wsbsProjInstanceDto.agentMphone }" />
					<i class="input-helper"></i>
				</label>
				<label >手机（受委托人）：</label>
				<div class="fg-line">
					${wsbsProjInstanceDto.agentMphone}
				</div>
			</div>
			<div class="col-sm-4 form-group">
				<label class="checkbox checkbox-inline">
					<input name="agentPhone" type="checkbox" value="${wsbsProjInstanceDto.agentPhone }" />
					<i class="input-helper"></i>
				</label>
				<label >办公电话（受委托人）：</label>
				<div class="fg-line">
					${wsbsProjInstanceDto.agentPhone}
				</div>
			</div>
		</div>
	</div>
</div>
</body>
</html>