<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>人防表单</title>
    <meta name="decorator" content="blank" />
    <script>
    	$(document).ready(function(){
    		$("#rfbjshForm").validate({
    			rules: {
//    				'entityIdcode':{card:true}
    			},
    			submitHandler: function(form){
  					form.submit();
    			}
    		});
    		var view = "${view}";
	    	if(view){
	    		$("header").remove();
	    		$("input").attr("readonly",true);
	    		$("textarea").attr("disabled",true);
	    		$(".base").each(function (){
	    			if($(this).prop("type") == "checkbox"){
	    				$(this).attr("disabled",true);
	    			}
	    		});
	    		$("button").hide();
	    		$(".actions").append('<li>'+
						'<button type="button" class="btn btn-info btn-sm" data-toggle= "modal" onClick="createLocpde()">写码</button>'+
     				     '</li>&nbsp;&nbsp;');
	    		if(view != "3"){
		    		$(".actions").append('<li>'+
						'<button type="button"data-toggle= "modal" class="btn btn-success btn-sm" onclick="backItem()">返回</button>'+
						'</li>');
	    		}
	    	}
    	});
    	function saveForm(){
			$(".savebutton").attr("disabled",true);
			if($("#rfbjshForm").valid()){
				$("#rfbjshForm").submit();
			}else{
				$(".savebutton").attr("disabled",false);
			}
    	}
    	function refresh(){
			window.location.reload(true);
		}
    	//返回详情信息
		function backItem(){
			var view = "${view}";
			var prjId = "${formObject.prjId}";
			var taskId = "${formObject.taskId}";
			window.parent.location.href = "${ctx}/project/backItem?prjId="+prjId+"&taskId="+taskId+"&view="+view;
		}
		//生成表单龙贝码
		function createLocpde(){
			var projectId = "${formObject.prjId}";
			var taskDefId = "${formObject.taskId}";
			var formCode = "${formCode}";
			window.open ('${ctx}/project/createFormLpcode?formCode='+formCode+'&taskDefId='+taskDefId+'&prjInstanceVo.id='+projectId,'_blank');
		}
    </script>
</head>
<body>


<div class="card">
    <div class="card-header">
        <h2>人防工程报建审核<small>您可通过本功能进行人防工程报建审核</small>
        </h2>
        <ul class="actions">
            <li>
                <button data-toggle="tooltip" data-placement="bottom" type="button"
                        title="返回" class="btn btn-success btn-icon m-r-5 savebutton"
                        onclick="javascrtpt:history.go(-1)">
                    <i class="md md-arrow-back"></i>
                </button>
            </li>
            <li>
				<button title="保存" onclick="saveForm();" data-toggle="tooltip" data-placement="bottom" type="button" class="btn btn-info btn-icon m-r-5 savebutton" ><i class="md md-save"></i></button>
			</li>
            <li>
                <button data-toggle="tooltip" data-placement="bottom" type="button"
                        title="刷新" class="btn btn-default btn-icon m-r-5 savebutton"
                        onclick="refresh();">
                    <i class="md md-autorenew"></i>
                </button>
            </li>
        </ul>
    </div>
	<form id="rfbjshForm" action="${ctx}/rfbjsh/save" method="post">
	    <div class="card-body card-padding">
	        <div class="row">
	             <input type="hidden" name="id"
	                    value="${formObject.id}"/>
                 <input type="hidden" name="prjId" value="${formObject.prjId}" />
                 <input type="hidden" name="taskId" value="${formObject.taskId}" />
                 <input type="hidden" name="code" value="${formObject.code}" />
	             <div class="col-xs-4 form-group">
	                 <label class="control-label">工程名称：</label>
	                 <div class="fg-line">
	                     <input type="text" class="form-control required"
	                            data-bind="prjName" maxlength="200"
	                            value="${formObject.prjName}"
	                            name="prjName" placeholder="工程名称">
	                 </div>
	             </div>
	             <div class="col-xs-4 form-group">
	                 <label class="control-label">单位地址：</label>
	                 <div class="fg-line">
	                     <input type="text" class="form-control "
	                            data-bind="companyAddr" maxlength="200"
	                            value="${formObject.companyAddr}"
	                            name="companyAddr" placeholder="单位地址">
	                 </div>
	             </div>
	             <div class="col-xs-4 form-group">
	                 <label class="control-label">单位电话：</label>
	                 <div class="fg-line">
	                     <input type="text" class="form-control phone "
	                            data-bind="agentPhone" maxlength="15"
	                            value="${formObject.companyMphone}"
	                            name="companyMphone" placeholder="单位电话">
	                 </div>
	             </div>
	             <div class="col-xs-4 form-group">
	                 <label class="control-label">联系人：</label>
	                 <div class="fg-line">
	                     <input type="text" class="form-control "
	                            data-bind="agentName" maxlength="50"
	                            value="${formObject.linkman}"
	                            name="linkman" placeholder="联系人">
	                 </div>
	             </div>
	             <div class="col-xs-4 form-group">
	                 <label class="control-label">联系电话：</label>
	                 <div class="fg-line">
	                     <input type="text" class="form-control phone "
	                            data-bind="agentMphone" maxlength="15"
	                            value="${formObject.linkmanPhone}"
	                                      name="linkmanPhone" placeholder="联系电话">
	                           </div>
	                       </div>
	                       <div class="col-xs-4 form-group">
	                           <label class="control-label">申请日期：</label>
	                           <div class="input-group">
	<span class="input-group-addon"><i class="md md-event"></i></span>
	                               <div class="dtp-container dropdown fg-line ">
	                                   <input type="text"
	                                          class="form-control date-picker "
	                                          data-toggle="dropdown" name="applyDate"
	                                          value='<fmt:formatDate value="${formObject.applyDate}" pattern="yyyy-MM-dd" />'
	                                placeholder="单击此处..." aria-expanded="false">
	                     </div>
	                 </div>
	             </div>
	             <div class="col-xs-4 form-group">
	                 <label class="control-label" class="control-label">工程地点：</label>
	                 <div class="fg-line">
	                     <input type="text" class="form-control "
	                            data-bind="prjAddr" maxlength="50"
	                            value="${formObject.prjAddress}"
	                            name="prjAddress" placeholder="工程地点">
	                 </div>
	             </div>
	             <div class="col-xs-4 form-group">
	                 <label class="control-label" class="control-label">建设单位：</label>
	                 <div class="fg-line">
	                     <input type="text" class="form-control "
	                            data-bind="company" maxlength="200"
	                            value="${formObject.company}"
	                            name="company" placeholder="建设单位">
	                 </div>
	             </div>
	             <div class="col-xs-4 form-group">
	                 <label class="control-label" class="control-label">设计单位：</label>
	                 <div class="fg-line">
	                     <input type="text" class="form-control "
	                            maxlength="50"
	                            value="${formObject.designCompany}"
	                            name="designCompany" placeholder="设计单位">
	                 </div>
	             </div>
	             <div class="col-xs-4 form-group">
	                 <label class="control-label" class="control-label">建设单位法人代表：</label>
	                 <div class="fg-line">
	                     <input type="text" class="form-control "
	                            data-bind="legalEntity" maxlength="50"
	                            value="${formObject.legalEntity}"
	                            name="legalEntity" placeholder="建设单位法人代表">
	                 </div>
	             </div>
	             <div class="col-xs-4 form-group">
	                 <label class="control-label" class="control-label">法人代表身份证号码：</label>
	                 <div class="fg-line">
	                     <input type="text" class="form-control required"
	                            maxlength="20"
	                            value="${formObject.entityIdcode}"
	                            name="entityIdcode"
	                            placeholder="法人代表身份证号码">
	                 </div>
	             </div>
	             <div class="col-xs-4 form-group">
	                 <label class="control-label" class="control-label">法人代表联系电话：</label>
	                 <div class="fg-line">
	                     <input type="text" class="form-control phone "
	                            data-bind="entityMphone" maxlength="15"
	                            value="${formObject.entityPhone}"
	                            name="entityPhone" placeholder="法人代表联系电话">
	                 </div>
	             </div>
	             <div class="col-xs-4 form-group">
	                 <label class="control-label">防空地下室主要出入口处城市坐标及高程：</label>
	                 <div class="fg-line">
	                     <label class="control-label"></label>
	                 </div>
	             </div>
	             <div class="col-xs-2 form-group">
	                 <label class="control-label">x坐标:</label>
	                 <div class="fg-line">
	                     <input type="text" class="form-control"
	                            maxlength="50"
	                            value="${formObject.basementXPoint}"
	                            name="basementXPoint" placeholder="x坐标">
	                 </div>
	             </div>
	             <div class="col-xs-2 form-group">
	                 <label class="control-label">y坐标:</label>
	                 <div class="fg-line">
	                     <input type="text" class="form-control"
	                            maxlength="50"
	                            value="${formObject.basementYPoint}"
	                            name="basementYPoint" placeholder="y坐标">
	                 </div>
	             </div>
	             <div class="col-xs-2 form-group">
	                 <label class="control-label">高程:</label>
	                 <div class="fg-line">
	                     <input type="text" class="form-control"
	                            maxlength="50"
	                            value="${formObject.basementGc}"
	                            name="basementGc" placeholder="高程">
	                 </div>
	             </div>
	            <div class="btn-demo text-center col-xs-12">
	                <button data-toggle="modal" onclick="saveForm();"
	                        class="btn btn-primary waves-effect savebutton" type="button">保存</button>
	                <button class="btn btn-default waves-effect savebutton" type="button"
	                        onclick="javascrtpt:history.go(-1)">返回</button>
	            </div>
	        </div>
        </form>
    </div>
</div>
</body>
</html>