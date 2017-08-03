<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>受理表单</title>
    <meta name="decorator" content="blank" />
    <script>
	    $(document).ready(function(){
	    	$("#rfydjsjgsbForm").validate({
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
			if($("#rfydjsjgsbForm").valid()){
				$("#rfydjsjgsbForm").submit();
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
        <h2>珠海市人防工程易地建设竣工申报<small>您可通过本功能进行珠海市人防工程易地建设竣工申报</small></h2>
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
    
    <form id="rfydjsjgsbForm" action="${ctx}/rfydjsjgsb/save" method="post">
	    <div class="card-body card-padding">
	        <div class="row">
	            <input type="hidden" name="id"
	                   value="${formObject.id}">
                <input type="hidden" name="prjId"
                 value="${formObject.prjId}">
                <input type="hidden" name="taskId"
                 value="${formObject.taskId}">
<!--                  <input type="hidden" name="prjCode" -->
<%--                  value="${formObject.prjCode}"> --%>
                 
                 <div class="col-xs-4 form-group">
	                <label class="control-label" class="control-label">报建编号：</label>
	                <div class="fg-line">
	                    <input type="text" class="form-control "
	                           maxlength="50"
	                           value="${formObject.bjCode}"
	                           name="bjCode"
	                           placeholder="报建编号">
	                </div>
	            </div>
	            
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
	                <label class="control-label">工程地点：</label>
	                <div class="fg-line">
	                    <input type="text" class="form-control "
	                           data-bind="companyAddr" maxlength="50"
	                           value="${formObject.prjAddress}"
	                           name="prjAddress" placeholder="工程地点">
	                </div>
	            </div>
	            <div class="col-xs-4 form-group">
	                <label class="control-label">建设单位：</label>
	                <div class="fg-line">
	                    <input type="text" class="form-control "
	                           maxlength="200"
	                           value="${formObject.company}"
	                           name="company"
	                           placeholder="建设单位">
	                </div>
	            </div>
	            <div class="col-xs-4 form-group">
	                <label class="control-label">建设单位联系人：</label>
	                <div class="fg-line">
	                    <input type="text" class="form-control "
	                           maxlength="50"
	                           value="${formObject.linkman}"
	                           name="linkman" placeholder="建设单位联系人">
	                </div>
	            </div>
	            <div class="col-xs-4 form-group">
	                <label class="control-label">联系电话：</label>
	                <div class="fg-line">
	                    <input type="text" class="form-control phone "
	                           maxlength="15"
	                           value="${formObject.linkmanPhone}"
	                           data-bind="linkmanPhone"
	                           name="linkmanPhone" placeholder="联系电话">
	               </div>
	            </div>
	            
	            
	            
	            <div class="col-xs-4 form-group">
	                <label class="control-label" class="control-label">设计单位：</label>
	                <div class="fg-line">
	                    <input type="text" class="form-control "
	                           data-bind="designCompany"  maxlength="50"
	                           value="${formObject.designCompany}"
	                           name="designCompany" placeholder="设计单位">
	                </div>
	            </div>
	            <div class="col-xs-4 form-group">
	                <label class="control-label" class="control-label">施工单位：</label>
	                <div class="fg-line">
	                    <input type="text" class="form-control "
	                            data-bind="constructCompany" maxlength="50"
	                           value="${formObject.constructCompany}"
	                           name="constructCompany" placeholder="施工单位">
	                </div>
	            </div>
	            <div class="col-xs-4 form-group">
	                <label class="control-label" class="control-label">监理单位：</label>
	                <div class="fg-line">
	                    <input type="text" class="form-control "
	                           maxlength="50"
	                           value="${formObject.inspectCompany}"
	                           name="inspectCompany"
	                           placeholder="监理单位">
	                </div>
	            </div>
	            <div class="col-xs-4 form-group">
	                <label class="control-label" class="control-label">报建地上总建筑面积：</label>
	                <div class="fg-line">
	                    <input type="text" class="form-control "
	                           data-bind="legalEntity"  maxlength="50"
	                           value="${formObject.bjdszjzmj}"
	                           name="bjdszjzmj"
	                           placeholder="报建地上总建筑面积">
	                </div>
	            </div>
	            <div class="col-xs-4 form-group">
	                <label class="control-label" class="control-label">报建地上建筑栋数：</label>
	                <div class="fg-line">
	                    <input type="text" class="form-control "
	                           maxlength="50"
	                           value="${formObject.bjdsjzds}"
	                           name="bjdsjzds"
	                           placeholder="报建地上建筑栋数">
	                </div>
	            </div>
	            <div class="col-xs-4 form-group">
	                <label class="control-label" class="control-label">报建地上建筑层数：</label>
	                <div class="fg-line">
	                    <input type="text" class="form-control  "
	                           data-bind="entityMphone"  maxlength="50"
	                           value="${formObject.bjdsjzcs}"
	                           name="bjdsjzcs"
	                           placeholder="报建地上建筑层数">
	                </div>
	            </div>
	            <div class="col-xs-4 form-group">
	                <label class="control-label" class="control-label">报建地下总建筑面积：</label>
	                <div class="fg-line">
	                    <input type="text" class="form-control "
	                             maxlength="50"
	                           value="${formObject.bjdxzjzmj}"
	                           name="bjdxzjzmj"
	                           placeholder="报建地下总建筑面积">
	                </div>
	            </div>
	             <div class="col-xs-4 form-group">
	                <label class="control-label" class="control-label">报建地下室个数：</label>
	                <div class="fg-line">
	                    <input type="text" class="form-control "
	                             maxlength="50"
	                           value="${formObject.bjdxsgs}"
	                           name="bjdxsgs"
	                           placeholder="报建地下室个数">
	                </div>
	            </div>
	            <div class="col-xs-4 form-group">
	                <label class="control-label" class="control-label">报建地下室层数：</label>
	                <div class="fg-line">
	                    <input type="text" class="form-control "
	                             maxlength="50"
	                           value="${formObject.bjdxscs}"
	                           name="bjdxscs"
	                           placeholder="报建地下室层数">
	                </div>
	            </div>
	            <div class="col-xs-4 form-group">
	                <label class="control-label" class="control-label">竣工地上总建筑面积：</label>
	                <div class="fg-line">
	                    <input type="text" class="form-control "
	                             maxlength="50"
	                           value="${formObject.jgdszjzmj}"
	                           name="jgdszjzmj"
	                           placeholder="竣工地上总建筑面积">
	                </div>
	            </div>
	            <div class="col-xs-4 form-group">
	                <label class="control-label" class="control-label">竣工地上建筑栋数：</label>
	                <div class="fg-line">
	                    <input type="text" class="form-control "
	                             maxlength="50"
	                           value="${formObject.jgdsjzds}"
	                           name="jgdsjzds"
	                           placeholder="竣工地上建筑栋数">
	                </div>
	            </div>
	            <div class="col-xs-4 form-group">
	                <label class="control-label" class="control-label">竣工地上建筑层数：</label>
	                <div class="fg-line">
	                    <input type="text" class="form-control "
	                             maxlength="50"
	                           value="${formObject.jgdsjzcs}"
	                           name="jgdsjzcs"
	                           placeholder="竣工地上建筑层数">
	                </div>
	            </div>
	            
	            <div class="col-xs-4 form-group">
	                <label class="control-label" class="control-label">竣工地下总建筑面积：</label>
	                <div class="fg-line">
	                    <input type="text" class="form-control "
	                             maxlength="50"
	                           value="${formObject.jgdxzjzmj}"
	                           name="jgdxzjzmj"
	                           placeholder="竣工地下总建筑面积">
	                </div>
	            </div>
	            
	            <div class="col-xs-4 form-group">
	                <label class="control-label" class="control-label">竣工地下室个数：</label>
	                <div class="fg-line">
	                    <input type="text" class="form-control "
	                             maxlength="50"
	                           value="${formObject.jgdxsgs}"
	                           name="jgdxsgs"
	                           placeholder="竣工地下室个数">
	                </div>
	            </div>
	            
	            <div class="col-xs-4 form-group">
	                <label class="control-label" class="control-label">竣工地下室个数层数：</label>
	                <div class="fg-line">
	                    <input type="text" class="form-control "
	                             maxlength="50"
	                           value="${formObject.jgdxscs}"
	                           name="jgdxscs"
	                           placeholder="竣工地下室个数层数">
	                </div>
	            </div>
	            
	            <div class="col-xs-4 form-group">
	                <label class="control-label" class="control-label">应交易地建设费（元）：</label>
	                <div class="fg-line">
	                    <input type="text" class="form-control "
	                             maxlength="50"
	                           value="${formObject.yjydjsf}"
	                           name="yjydjsf"
	                           placeholder="应交易地建设费（元）">
	                </div>
	            </div>
	            
	            <div class="col-xs-4 form-group">
	                <label class="control-label" class="control-label">已交易地建设费（元）：</label>
	                <div class="fg-line">
	                    <input type="text" class="form-control "
	                             maxlength="50"
	                           value="${formObject.yijiaoydjsf}"
	                           name="yijiaoydjsf"
	                           placeholder="已交易地建设费（元）">
	                </div>
	            </div>
	            
	            <div class="col-xs-4 form-group">
	                <label class="control-label" class="control-label">补交易地建设费（元）：</label>
	                <div class="fg-line">
	                    <input type="text" class="form-control "
	                             maxlength="50"
	                           value="${formObject.bjydjsf}"
	                           name="bjydjsf"
	                           placeholder="补交易地建设费（元）">
	                </div>
	            </div>
	            
	            <div class="col-xs-4 form-group">
	                <label class="control-label" class="control-label">建设单位验收意见：</label>
	                <div class="fg-line">
	                    <input type="text" class="form-control "
	                             maxlength="200"
	                           value="${formObject.jsdwysyj}"
	                           name="jsdwysyj"
	                           placeholder="建设单位验收意见">
	                </div>
	            </div>
	            
	            <div class="btn-demo text-center col-xs-12">
	                <button data-toggle="modal" onclick="saveForm();"
	                        class="btn btn-primary waves-effect savebutton" type="button">保存</button>
	                <button class="btn btn-default waves-effect savebutton" type="button"
	                        onclick="javascrtpt:history.go(-1)">返回</button>
	            </div>
	        </div>
	    </div>
    </form>
</div>
</body>
</html>