<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>授权委托书、法人代表证明书</title>
    <meta name="decorator" content="blank" />
    <script>
	    $(document).ready(function(){
	    	$("#gtSqwtsFrdbzmsForm").validate({
    			rules: {
    				'baiId':{card:true},
	    			'conId':{card:true},
	    			'leaId':{card:true}
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
			if($("#gtSqwtsFrdbzmsForm").valid()){
				$("#gtSqwtsFrdbzmsForm").submit();
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
    
	    <form id="gtSqwtsFrdbzmsForm" action="${ctx}/gtSqwtsFrdbzms/save" method="post">
		    <div class="card-body card-padding">
	        	<div class="row">
		            <input type="hidden" name="id"
		                   value="${formObject.id}">
	                <input type="hidden" name="prjId"
	                 value="${formObject.prjId}">
	                <input type="hidden" name="taskId"
	                 value="${formObject.taskId}">
	                 <div class="col-xs-12 form-group">
	                 	<label class="control-label" style="font-size: 16px">授权委托书</label>
	                 </div>
		            <div class="col-xs-4 form-group">
		                <label class="control-label">委托代理人：</label>
		                <div class="fg-line">
		                    <input type="text" class="form-control "
		                           data-bind="bailee" maxlength="50"
		                           value="${formObject.bailee}"
		                           name="bailee" placeholder="委托代理人">
		                </div>
		            </div>
		            <div class="col-xs-4 form-group">
		                <label class="control-label">委托代理人住所：</label>
		                <div class="fg-line">
		                    <input type="text" class="form-control "
		                           data-bind="baiAdd" maxlength="200"
		                           value="${formObject.baiAdd}"
		                           name="baiAdd" placeholder="委托代理人住所">
		                </div>
		            </div>
		            <div class="col-xs-4 form-group">
		                <label class="control-label">委托代理人身份证：</label>
		                <div class="fg-line">
		                    <input type="text" class="form-control "
		                           data-bind="baiId" maxlength="20"
		                           value="${formObject.baiId}"
		                           name="baiId" placeholder="委托代理人身份证">
		                </div>
		            </div>
		            <div class="col-xs-4 form-group">
		                <label class="control-label">委托代理人电话：</label>
		                <div class="fg-line">
		                    <input type="text" class="form-control  phone"
		                           data-bind="baiTel" maxlength="20"
		                           value="${formObject.baiTel}"
		                           name="baiTel" placeholder="委托代理人电话">
		                </div>
		            </div>
		            <div class="col-xs-4 form-group">
		                <label class="control-label">委托人：</label>
		                <div class="fg-line">
		                    <input type="text" class="form-control "
		                           data-bind="consignor" maxlength="50"
		                           value="${formObject.consignor}"
		                           name="consignor" placeholder="委托人">
		                </div>
		            </div>
		            <div class="col-xs-4 form-group">
		                <label class="control-label">委托人身份证：</label>
		                <div class="fg-line">
		                    <input type="text" class="form-control "
		                           data-bind="conId" maxlength="20"
		                           value="${formObject.conId}"
		                           name="conId" placeholder="委托人身份证">
		                </div>
		            </div>
		            <div class="col-xs-4 form-group">
		                <label class="control-label">委托人电话：</label>
		                <div class="fg-line">
		                    <input type="text" class="form-control  phone"
		                           data-bind="conTel" maxlength="20"
		                           value="${formObject.conTel}"
		                           name="conTel" placeholder="委托人电话">
		                </div>
		            </div>
		            <div class="col-xs-4 form-group">
		                <label class="control-label">委托事务：</label>
		                <div class="fg-line">
		                    <input type="text" class="form-control "
		                           data-bind="taskname" maxlength="50"
		                           value="${formObject.taskname}"
		                           name="taskname" placeholder="委托事务">
		                </div>
		            </div>
		            <div class="col-xs-4 form-group">
						<label class="control-label">委托限期开始日期：</label>
						<div class="input-group">
							<span class="input-group-addon"><i
								class="md md-event"></i></span>
							<div class="dtp-container dropdown fg-line">
								<input type="text"
									class="form-control date-picker date "
									value='<fmt:formatDate value="${formObject.startdate}" pattern="yyyy-MM-dd" />'
									data-toggle="dropdown"
									name="startdate"
									placeholder="单击此处..." aria-expanded="false">
							</div>
						</div>
					</div>
					<div class="col-xs-4 form-group">
						<label class="control-label">授权委托日期：</label>
						<div class="input-group">
							<span class="input-group-addon"><i
								class="md md-event"></i></span>
							<div class="dtp-container dropdown fg-line">
								<input type="text"
									class="form-control date-picker date "
									value='<fmt:formatDate value="${formObject.conDate}" pattern="yyyy-MM-dd" />'
									data-toggle="dropdown"
									name="conDate"
									placeholder="单击此处..." aria-expanded="false">
							</div>
						</div>
					</div>
					<div class="col-xs-12 form-group">
	                	<label class="control-label" style="font-size: 16px">法人代表证明书</label>
	                </div>
		            <div class="col-xs-4 form-group">
		                <label class="control-label">法人名：</label>
		                <div class="fg-line">
		                    <input type="text" class="form-control "
		                           data-bind="leagl" maxlength="50"
		                           value="${formObject.leagl}"
		                           name="leagl" placeholder="法人名">
		                </div>
		            </div>
		            <div class="col-xs-4 form-group">
		                <label class="control-label">单位职务：</label>
		                <div class="fg-line">
		                    <input type="text" class="form-control "
		                           data-bind="leaDuty" maxlength="50"
		                           value="${formObject.leaDuty}"
		                           name="leaDuty" placeholder="单位职务">
		                </div>
		            </div>
		            <div class="col-xs-4 form-group">
		                <label class="control-label">单位经济性质：</label>
		                <div class="fg-line">
		                    <input type="text" class="form-control "
		                           data-bind="nature" maxlength="50"
		                           value="${formObject.nature}"
		                           name="nature" placeholder="单位经济性质">
		                </div>
		            </div>
		            <div class="col-xs-4 form-group">
		                <label class="control-label">营业执照/代码证号码：</label>
		                <div class="fg-line">
		                    <input type="text" class="form-control "
		                           data-bind="busLicId" maxlength="50"
		                           value="${formObject.busLicId}"
		                           name="busLicId" placeholder="营业执照/代码证号码">
		                </div>
		            </div>
		            <div class="col-xs-4 form-group">
		                <label class="control-label">单位电话号码：</label>
		                <div class="fg-line">
		                    <input type="text" class="form-control  phone"
		                           data-bind="unitTel" maxlength="20"
		                           value="${formObject.unitTel}"
		                           name="unitTel" placeholder="单位电话号码">
		                </div>
		            </div>
		            <div class="col-xs-4 form-group">
		                <label class="control-label">法人代表身份证号码：</label>
		                <div class="fg-line">
		                    <input type="text" class="form-control "
		                           data-bind="leaId" maxlength="20"
		                           value="${formObject.leaId}"
		                           name="leaId" placeholder="法人代表身份证号码">
		                </div>
		            </div>
		            <div class="col-xs-4 form-group">
						<label class="control-label">签发日期：</label>
						<div class="input-group">
							<span class="input-group-addon"><i
								class="md md-event"></i></span>
							<div class="dtp-container dropdown fg-line">
								<input type="text"
									class="form-control date-picker date "
									value='<fmt:formatDate value="${formObject.signDate}" pattern="yyyy-MM-dd" />'
									data-toggle="dropdown"
									name="signDate"
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