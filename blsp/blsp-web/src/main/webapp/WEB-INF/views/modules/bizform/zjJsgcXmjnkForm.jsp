<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>金湾区建设工程项目节能卡</title>
    <meta name="decorator" content="blank" />
    <script>
	    $(document).ready(function(){
	    	$("#zjJsgcXmjnkForm").validate({
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
			if($("#zjJsgcXmjnkForm").valid()){
				$("#zjJsgcXmjnkForm").submit();
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
        	<h2>金湾区建设工程项目节能卡<small>您可通过本功能进行金湾区建设工程项目节能卡申报</small></h2>
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
    
	    <form id="zjJsgcXmjnkForm" action="${ctx}/zjJsgcXmjnk/save" method="post">
		    <div class="card-body card-padding">
	        	<div class="row">
		            <input type="hidden" name="id"
		                   value="${formObject.id}">
	                <input type="hidden" name="prjId"
	                 value="${formObject.prjId}">
	                <input type="hidden" name="taskId"
	                 value="${formObject.taskId}">
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
		                <label class="control-label">建设单位：</label>
		                <div class="fg-line">
		                    <input type="text" class="form-control "
		                           data-bind="company" maxlength="200"
		                           value="${formObject.company}"
		                           name="company" placeholder="建设单位">
		                </div>
		            </div>
		            <div class="col-xs-4 form-group">
		                <label class="control-label">工程地点：</label>
		                <div class="fg-line">
		                    <input type="text" class="form-control "
		                           data-bind="prjAddr" maxlength="50"
		                           value="${formObject.prjAddr}"
		                           name="prjAddr" placeholder="工程地点">
		                </div>
		            </div>
		            <div class="col-xs-4 form-group">
						<label class="control-label">报建日期：</label>
						<div class="input-group">
							<span class="input-group-addon"><i
								class="md md-event"></i></span>
							<div class="dtp-container dropdown fg-line">
								<input type="text"
									class="form-control date-picker date "
									value='<fmt:formatDate value="${formObject.applicationDate}" pattern="yyyy-MM-dd" />'
									data-toggle="dropdown"
									name="applicationDate"
									placeholder="单击此处..." aria-expanded="false">
							</div>
						</div>
					</div>
		            <div class="col-xs-4 form-group">
		                <label class="control-label">报建面积：</label>
		                <div class="fg-line">
		                    <input type="text" class="form-control "
		                           data-bind="applicationSpace" maxlength="50"
		                           value="${formObject.applicationSpace}"
		                           name="applicationSpace" placeholder="报建面积">
		                </div>
		            </div>
		            <div class="col-xs-4 form-group">
		                <label class="control-label">设计墙材用量：</label>
		                <div class="fg-line">
		                    <input type="text" class="form-control "
		                           data-bind="planWallDosage" maxlength="50"
		                           value="${formObject.planWallDosage}"
		                           name="planWallDosage" placeholder="设计墙材用量">
		                </div>
		            </div>
		            <div class="col-xs-4 form-group">
		                <label class="control-label">设计墙材名称：</label>
		                <div class="fg-line">
		                    <input type="text" class="form-control "
		                           data-bind="planWallName" maxlength="50"
		                           value="${formObject.planWallName}"
		                           name="planWallName" placeholder="设计墙材名称">
		                </div>
		            </div>
		            <div class="col-xs-4 form-group">
		                <label class="control-label">设计水泥用量：</label>
		                <div class="fg-line">
		                    <input type="text" class="form-control "
		                           data-bind="planCementDosage" maxlength="50"
		                           value="${formObject.planCementDosage}"
		                           name="planCementDosage" placeholder="设计水泥用量">
		                </div>
		            </div>
		            <div class="col-xs-4 form-group">
		                <label class="control-label">项目负责人：</label>
		                <div class="fg-line">
		                    <input type="text" class="form-control "
		                           data-bind="prjPrincipal" maxlength="50"
		                           value="${formObject.prjPrincipal}"
		                           name="prjPrincipal" placeholder="项目负责人">
		                </div>
		            </div>
		            <div class="col-xs-4 form-group">
		                <label class="control-label">联系电话：</label>
		                <div class="fg-line">
		                    <input type="text" class="form-control  phone"
		                           data-bind="priPhone" maxlength="20"
		                           value="${formObject.priPhone}"
		                           name="priPhone" placeholder="联系电话">
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