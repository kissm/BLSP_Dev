<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>法定代表人授权书</title>
    <meta name="decorator" content="blank" />
    <script>
	    $(document).ready(function(){
	    	$("#zjFddbrsqsForm").validate({
    			rules: {
    				'authId':{card:true}
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
			if($("#zjFddbrsqsForm").valid()){
				$("#zjFddbrsqsForm").submit();
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
        	<h2>法定代表人授权书<small>您可通过本功能进行法定代表人授权书申报</small></h2>
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
    
	    <form id="zjFddbrsqsForm" action="${ctx}/zjFddbrsqs/save" method="post">
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
		                <label class="control-label">授权单位：</label>
		                <div class="fg-line">
		                    <input type="text" class="form-control "
		                           data-bind="company" maxlength="200"
		                           value="${formObject.company}"
		                           name="company" placeholder="授权单位">
		                </div>
		            </div>
		            <div class="col-xs-4 form-group">
		                <label class="control-label">法人代表：</label>
		                <div class="fg-line">
		                    <input type="text" class="form-control "
		                           data-bind="legalEntity" maxlength="50"
		                           value="${formObject.legalEntity}"
		                           name="legalEntity" placeholder="法人代表">
		                </div>
		            </div>
		            <div class="col-xs-4 form-group">
		                <label class="control-label">被授权人姓名：</label>
		                <div class="fg-line">
		                    <input type="text" class="form-control "
		                           data-bind="authorizedPer" maxlength="50"
		                           value="${formObject.authorizedPer}"
		                           name="authorizedPer" placeholder="被授权人姓名">
		                </div>
		            </div>
		            <div class="col-xs-4 form-group">
		                <label class="control-label">身份证号：</label>
		                <div class="fg-line">
		                    <input type="text" class="form-control "
		                           data-bind="authId" maxlength="20"
		                           value="${formObject.authId}"
		                           name="authId" placeholder="身份证号">
		                </div>
		            </div>
		            <div class="col-xs-4 form-group">
		                <label class="control-label">注册执业资格：</label>
		                <div class="fg-line">
		                    <input type="text" class="form-control "
		                           data-bind="praReq" maxlength="50"
		                           value="${formObject.praReq}"
		                           name="praReq" placeholder="注册执业资格">
		                </div>
		            </div>
		            <div class="col-xs-4 form-group">
		                <label class="control-label">注册执业证号：</label>
		                <div class="fg-line">
		                    <input type="text" class="form-control "
		                           data-bind="praId" maxlength="50"
		                           value="${formObject.praId}"
		                           name="praId" placeholder="注册执业证号">
		                </div>
		            </div>
		            <div class="col-xs-4 form-group">
						<label class="control-label">授权日期：</label>
						<div class="input-group">
							<span class="input-group-addon"><i
								class="md md-event"></i></span>
							<div class="dtp-container dropdown fg-line">
								<input type="text"
									class="form-control date-picker date "
									value='<fmt:formatDate value="${formObject.authDate}" pattern="yyyy-MM-dd" />'
									data-toggle="dropdown"
									name="authDate"
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