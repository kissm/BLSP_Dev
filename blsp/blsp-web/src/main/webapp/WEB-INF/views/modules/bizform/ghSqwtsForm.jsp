<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>授权委托书、法人代表证明书</title>
    <meta name="decorator" content="blank" />
    <script>
	    $(document).ready(function(){
	    	$("#ghSqwtsForm").validate({
    			rules: {
    				'bailorAgentId':{card:true},
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
	    		if(view != "3"){
		    		$(".actions").append('<li>'+
		                		  '<button data-toggle="tooltip" data-placement="bottom" type="button"'+
		                          'title="返回" class="btn btn-success btn-icon m-r-5"'+
		                          'onclick="backItem()">'+
		                    	  '<i class="md md-arrow-back"></i>'+
		                		  '</button>'+
	            				  '</li>');
	    		}
	    	}
		});
		function saveForm(){
			$(".savebutton").attr("disabled",true);
			if($("#ghSqwtsForm").valid()){
				$("#ghSqwtsForm").submit();
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
    </script>
</head>
<body>
	<div class="card">
    	<div class="card-header">
        	<h2>授权委托书、法人代表证明书<small>您可通过本功能进行授权委托书、法人代表证明书申报</small></h2>
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
    
	    <form id="ghSqwtsForm" action="${ctx}/ghSqwts/save" method="post">
		    <div class="card-body card-padding">
	        	<div class="row">
		            <input type="hidden" name="id"
		                   value="${formObject.id}">
	                <input type="hidden" name="prjId"
	                 value="${formObject.prjId}">
	                <input type="hidden" name="taskId"
	                 value="${formObject.taskId}">
	                 <div class="col-xs-12 form-group">
	                 	<label class="control-label" style="font-size: 16px">法定代表人身份证明书</label>
	                 </div>
		            <div class="col-xs-4 form-group">
		                <label class="control-label">法人代表：</label>
		                <div class="fg-line">
		                    <input type="text" class="form-control "
		                           data-bind="legalName" maxlength="50"
		                           value="${formObject.legalName}"
		                           name="legalName" placeholder="法人代表">
		                </div>
		            </div>
		            <div class="col-xs-4 form-group">
		                <label class="control-label">任职职务：</label>
		                <div class="fg-line">
		                    <input type="text" class="form-control "
		                           data-bind="legalDuty" maxlength="50"
		                           value="${formObject.legalDuty}"
		                           name="legalDuty" placeholder="任职职务">
		                </div>
		            </div>
		            <div class="col-xs-4 form-group">
		                <label class="control-label">单位名称：</label>
		                <div class="fg-line">
		                    <input type="text" class="form-control "
		                           data-bind="unitName" maxlength="50"
		                           value="${formObject.unitName}"
		                           name="unitName" placeholder="单位名称">
		                </div>
		            </div>
		            <div class="col-xs-4 form-group">
		                <label class="control-label">单位地址：</label>
		                <div class="fg-line">
		                    <input type="text" class="form-control "
		                           data-bind="unitAddr" maxlength="50"
		                           value="${formObject.unitAddr}"
		                           name="unitAddr" placeholder="单位地址">
		                </div>
		            </div>
		            <div class="col-xs-4 form-group">
		                <label class="control-label">邮政编码：</label>
		                <div class="fg-line">
		                    <input type="text" class="form-control "
		                           data-bind="unitPostcode" maxlength="50"
		                           value="${formObject.unitPostcode}"
		                           name="unitPostcode" placeholder="邮政编码">
		                </div>
		            </div>
		            <div class="col-xs-4 form-group">
		                <label class="control-label">单位联系电话：</label>
		                <div class="fg-line">
		                    <input type="text" class="form-control  phone"
		                           data-bind="unitTel" maxlength="15"
		                           value="${formObject.unitTel}"
		                           name="unitTel" placeholder="单位联系电话">
		                </div>
		            </div>
		            <div class="col-xs-4 form-group">
		                <label class="control-label">证明日期：</label>
		                <div class="input-group">
							<span class="input-group-addon"><i
								class="md md-event"></i></span>
							<div class="dtp-container dropdown fg-line">
								<input type="text"
									class="form-control date-picker date "
									value='<fmt:formatDate value="${formObject.unitDate}" pattern="yyyy-MM-dd" />'
									data-toggle="dropdown"
									name="unitDate"
									placeholder="单击此处..." aria-expanded="false">
							</div>
						</div>
		            </div>
		            
					<div class="col-xs-12 form-group">
	                	<label class="control-label" style="font-size: 16px">授权委托书</label>
	                </div>
		            <div class="col-xs-4 form-group">
		                <label class="control-label">委托人：</label>
		                <div class="fg-line">
		                    <input type="text" class="form-control "
		                           data-bind="bailor" maxlength="50"
		                           value="${formObject.bailor}"
		                           name="bailor" placeholder="委托人">
		                </div>
		            </div>
		            <div class="col-xs-4 form-group">
		                <label class="control-label">法定代表人：</label>
		                <div class="fg-line">
		                    <input type="text" class="form-control "
		                           data-bind="lawPerson" maxlength="50"
		                           value="${formObject.lawPerson}"
		                           name="lawPerson" placeholder="法定代表人">
		                </div>
		            </div>
		            <div class="col-xs-4 form-group">
		                <label class="control-label">委托办理地点：</label>
		                <div class="fg-line">
		                    <input type="text" class="form-control "
		                           data-bind="bailorAddr" maxlength="50"
		                           value="${formObject.bailorAddr}"
		                           name="bailorAddr" placeholder="委托办理地点">
		                </div>
		            </div>
		            <div class="col-xs-4 form-group">
		                <label class="control-label">委托办理项目：</label>
		                <div class="fg-line">
		                    <input type="text" class="form-control required"
		                           data-bind="prjName" maxlength="200"
		                           value="${formObject.prjName}"
		                           name="prjName" placeholder="委托办理项目">
		                </div>
		            </div>
		            <div class="col-xs-4 form-group">
		                <label class="control-label">委托办理手续：</label>
		                <div class="fg-line">
		                    <input type="text" class="form-control "
		                           data-bind="bailorProcedure" maxlength="50"
		                           value="${formObject.bailorProcedure}"
		                           name="bailorProcedure" placeholder="委托办理手续">
		                </div>
		            </div>
		            <div class="col-xs-4 form-group">
		                <label class="control-label">委托代理人：</label>
		                <div class="fg-line">
		                    <input type="text" class="form-control "
		                           data-bind="bailorAgent" maxlength="50"
		                           value="${formObject.bailorAgent}"
		                           name="bailorAgent" placeholder="委托代理人">
		                </div>
		            </div>
		            <div class="col-xs-4 form-group">
		                <label class="control-label">身份证号：</label>
		                <div class="fg-line">
		                    <input type="text" class="form-control "
		                           data-bind="bailorAgentId" maxlength="18"
		                           value="${formObject.bailorAgentId}"
		                           name="bailorAgentId" placeholder="身份证号">
		                </div>
		            </div>
		            <div class="col-xs-4 form-group">
		                <label class="control-label">单位：</label>
		                <div class="fg-line">
		                    <input type="text" class="form-control "
		                           data-bind="bailorAgentUnit" maxlength="50"
		                           value="${formObject.bailorAgentUnit}"
		                           name="bailorAgentUnit" placeholder="单位">
		                </div>
		            </div>
		            <div class="col-xs-4 form-group">
		                <label class="control-label">职务：</label>
		                <div class="fg-line">
		                    <input type="text" class="form-control "
		                           data-bind="bailorAgentDuty" maxlength="50"
		                           value="${formObject.bailorAgentDuty}"
		                           name="bailorAgentDuty" placeholder="职务">
		                </div>
		            </div>
		            <div class="col-xs-4 form-group">
		                <label class="control-label">联系电话：</label>
		                <div class="fg-line">
		                    <input type="text" class="form-control  phone"
		                           data-bind="bailorAgentTel" maxlength="15"
		                           value="${formObject.bailorAgentTel}"
		                           name="bailorAgentTel" placeholder="联系电话">
		                </div>
		            </div>
		            <div class="col-xs-4 form-group">
						<label class="control-label">代理起始日期：</label>
						<div class="input-group">
							<span class="input-group-addon"><i
								class="md md-event"></i></span>
							<div class="dtp-container dropdown fg-line">
								<input type="text"
									class="form-control date-picker date "
									value='<fmt:formatDate value="${formObject.agentStartTime}" pattern="yyyy-MM-dd" />'
									data-toggle="dropdown"
									name="agentStartTime"
									placeholder="单击此处..." aria-expanded="false">
							</div>
						</div>
					</div>
					<div class="col-xs-4 form-group">
						<label class="control-label">代理结束日期：</label>
						<div class="input-group">
							<span class="input-group-addon"><i
								class="md md-event"></i></span>
							<div class="dtp-container dropdown fg-line">
								<input type="text"
									class="form-control date-picker date "
									value='<fmt:formatDate value="${formObject.agentEndTime}" pattern="yyyy-MM-dd" />'
									data-toggle="dropdown"
									name="agentEndTime"
									placeholder="单击此处..." aria-expanded="false">
							</div>
						</div>
					</div>
					<div class="col-xs-4 form-group">
		                <label class="control-label">单位或自然人：</label>
		                <div class="fg-line">
		                    <input type="text" class="form-control "
		                           data-bind="agentNameOrPerson" maxlength="50"
		                           value="${formObject.agentNameOrPerson}"
		                           name="agentNameOrPerson" placeholder="单位或自然人">
		                </div>
		            </div>
		            <div class="col-xs-4 form-group">
						<label class="control-label">委托日期：</label>
						<div class="input-group">
							<span class="input-group-addon"><i
								class="md md-event"></i></span>
							<div class="dtp-container dropdown fg-line">
								<input type="text"
									class="form-control date-picker date "
									value='<fmt:formatDate value="${formObject.agentUnitDate}" pattern="yyyy-MM-dd" />'
									data-toggle="dropdown"
									name="agentUnitDate"
									placeholder="单击此处..." aria-expanded="false">
							</div>
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