<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>防雷装置设计审核（授权书）</title>
    <meta name="decorator" content="blank" />
    <script>
	    $(document).ready(function(){
	    	$("#qxSqsForm").validate({
    			rules: {
    				'entrustedAgentId':{card:true},
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
			if($("#qxSqsForm").valid()){
				$("#qxSqsForm").submit();
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
        	<h2>防雷装置设计审核（授权书）<small>您可通过本功能进行防雷装置设计审核（授权书）申报</small></h2>
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
    
	    <form id="qxSqsForm" action="${ctx}/qxSqs/save" method="post">
		    <div class="card-body card-padding">
	        	<div class="row">
		            <input type="hidden" name="id"
		                   value="${formObject.id}">
	                <input type="hidden" name="prjId"
	                 value="${formObject.prjId}">
	                <input type="hidden" name="taskId"
	                 value="${formObject.taskId}">
		            <div class="col-xs-4 form-group">
		                <label class="control-label">气象局：</label>
		                <div class="fg-line">
		                    <input type="text" class="form-control "
		                           data-bind="weatherBureau" maxlength="50"
		                           value="${formObject.weatherBureau}"
		                           name="weatherBureau" placeholder="气象局">
		                </div>
		            </div>
		            <div class="col-xs-4 form-group">
		                <label class="control-label">项目名称：</label>
		                <div class="fg-line">
		                    <input type="text" class="form-control required"
		                           data-bind="prjName" maxlength="200"
		                           value="${formObject.prjName}"
		                           name="prjName" placeholder="项目名称">
		                </div>
		            </div>
		            <div class="col-xs-4 form-group">
		                <label class="control-label">授权单位：</label>
		                <div class="fg-line">
		                    <input type="text" class="form-control "
		                           data-bind="warrantyUnit" maxlength="50"
		                           value="${formObject.warrantyUnit}"
		                           name="warrantyUnit" placeholder="授权单位">
		                </div>
		            </div>
		            <div class="col-xs-4 form-group">
		                <label class="control-label">代理人：</label>
		                <div class="fg-line">
		                    <input type="text" class="form-control "
		                           data-bind="entrustedAgent" maxlength="50"
		                           value="${formObject.entrustedAgent}"
		                           name="entrustedAgent" placeholder="代理人">
		                </div>
		            </div>
		            <div class="col-xs-4 form-group">
		                <label class="control-label">性别：</label>
		                <div class="fg-line">
		                    <input type="text" class="form-control "
		                           data-bind="entrustedAgentSax" maxlength="2"
		                           value="${formObject.entrustedAgentSax}"
		                           name="entrustedAgentSax" placeholder="性别">
		                </div>
		            </div>
		            <div class="col-xs-4 form-group">
		                <label class="control-label">年龄：</label>
		                <div class="fg-line">
		                    <input type="text" class="form-control "
		                           data-bind="entrustedAgentAge" maxlength="3"
		                           value="${formObject.entrustedAgentAge}"
		                           name="entrustedAgentAge" placeholder="年龄">
		                </div>
		            </div>
		            <div class="col-xs-4 form-group">
		                <label class="control-label">身份证号码：</label>
		                <div class="fg-line">
		                    <input type="text" class="form-control "
		                           data-bind="entrustedAgentId" maxlength="20"
		                           value="${formObject.entrustedAgentId}"
		                           name="entrustedAgentId" placeholder="身份证号码">
		                </div>
		            </div>
		            <div class="col-xs-4 form-group">
		                <label class="control-label">固话：</label>
		                <div class="fg-line">
		                    <input type="text" class="form-control  simplePhone"
		                           data-bind="entrustedAgentTel" maxlength="15"
		                           value="${formObject.entrustedAgentTel}"
		                           name="entrustedAgentTel" placeholder="固话">
		                </div>
		            </div>
		            <div class="col-xs-4 form-group">
		                <label class="control-label">手机：</label>
		                <div class="fg-line">
		                    <input type="text" class="form-control  mobile"
		                           data-bind="entrustedAgentMobile" maxlength="15"
		                           value="${formObject.entrustedAgentMobile}"
		                           name="entrustedAgentMobile" placeholder="手机">
		                </div>
		            </div>
		            <div class="col-xs-4 form-group">
		                <label class="control-label">授权起始日期：</label>
		                <div class="input-group">
							<span class="input-group-addon"><i
								class="md md-event"></i></span>
							<div class="dtp-container dropdown fg-line">
								<input type="text"
									class="form-control date-picker date "
									value='<fmt:formatDate value="${formObject.warrantyStartTime}" pattern="yyyy-MM-dd" />'
									data-toggle="dropdown"
									name="warrantyStartTime"
									placeholder="单击此处..." aria-expanded="false">
							</div>
						</div>
		            </div>
		            <div class="col-xs-4 form-group">
		                <label class="control-label">授权终止日期：</label>
		                <div class="input-group">
							<span class="input-group-addon"><i
								class="md md-event"></i></span>
							<div class="dtp-container dropdown fg-line">
								<input type="text"
									class="form-control date-picker date "
									value='<fmt:formatDate value="${formObject.warrantyEndTime}" pattern="yyyy-MM-dd" />'
									data-toggle="dropdown"
									name="warrantyEndTime"
									placeholder="单击此处..." aria-expanded="false">
							</div>
						</div>
		            </div>
		            <div class="col-xs-4 form-group">
		                <label class="control-label">法定代表人：</label>
		                <div class="fg-line">
		                    <input type="text" class="form-control "
		                           data-bind="warrantyLegal" maxlength="50"
		                           value="${formObject.warrantyLegal}"
		                           name="warrantyLegal" placeholder="法定代表人">
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