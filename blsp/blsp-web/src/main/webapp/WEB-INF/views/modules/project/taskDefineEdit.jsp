<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>事项配置</title>
	<meta name="decorator" content="blank"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
			$.validator.addMethod("isRequired", function(value, element) {
				if($("#timerCat").find("option:selected").val() == "2"){
					return true;
				}else{
					var defaultTimeLimitVal = $("#defaultTimeLimit").val();
					return !(defaultTimeLimitVal == null || defaultTimeLimitVal == "");
				}
				return false;       
			}, "必填信息");   
			
			
			$.validator.addMethod("illegalChar", function(value, element) {
				var patrn=/[`~!@#$%^&*()_+<>?:"{},.\/;'[\]]/im;  
				return !(patrn.test(value));
			}, "只能输入字母、数字和汉字，不能包含非法字符");
			
			
			$("#saveForm").validate({
				rules: {
					taskCode:{illegalChar:true,remote:"${ctx}/prjTaskDefine/checkTaskCode?id=${prjTaskDefineDTO.id}&taskType="+$("#taskType").val()},
					defaultTimeLimit:{isRequired:true}
				},
				messages: {
					taskCode:{remote:"事项编号已存在"}
				},
				submitHandler: function(form){
					if($(".material").size()==0){
						swal("还未设置事项所需材料！", "", "error");
					}else{
                        $("#saveButton").attr("disabled",true);
                        $("#saveTop").attr("disabled",true);
						form.submit();
					}
				}
			});
			
			sessionStorage.clear();
			sessionStorage.setItem("jeesiteMap",'${jeesiteMap}');
			
			$("#timerCat").change(function(){
				if($(this).find("option:selected").val() == "2"){
					$("#projectDefault").hide();
					$("#condContent").show();
					$("#condDimType").removeAttr("disabled");
					getCondDim();
				}else{
					$("#projectDefault").show();
					$("#condContent").hide();
					//$('#condDimType option:eq(0)').attr('selected','selected');
					$("#condDimType").attr("disabled","disabled");
				}
				$('#condDimType').selectpicker('refresh'); 
			});
			
			$("#condDimType").change(function(){
				getCondDim();
			});
			
			$("#isReplyonCheck").click(function(){
				$('#isReplyon').val($(this).is(':checked')?'1':'0');
				getDependency();
			});
			
			$("#taskType").change(function(){
				getTaskStage();
			});
			
			<c:if test='${prjTaskDefineDTO.timerCat!="2"}'>
				$("#condDimType").attr("disabled","disabled");
				$('#condDimType').selectpicker('refresh'); 
			</c:if>
			
			$('#confirm').click(function(){
				var jeesiteMap = sessionStorage.getItem("jeesiteMap");
				var jeesiteCancelMap = sessionStorage.getItem("jeesiteCancelMap");
				if(jeesiteCancelMap  != null ){
					var cancelMaterials = JSON.parse(jeesiteCancelMap);
					for(var index in cancelMaterials){
						if($("#tr"+cancelMaterials[index].id).length > 0){
							$("#tr"+cancelMaterials[index].id).remove();
						}
					}
				}
				
				if(jeesiteMap  != null ){
					var materials = JSON.parse(jeesiteMap);
					var newMaterials = [];
					for(var index in materials){
						if($("#tr"+materials[index].id).length == 0){
							newMaterials.push(materials[index]);
						}
					}
					getMaterialDef("",JSON.stringify(newMaterials));
				}else{
					$("#materialsBody").html("");
				}
				$('#approve').modal('hide');
			});
			$('#approve').on('show.bs.modal', function () {
				//alert('在调用 show 方法后触发。');
				 //$("#materialIframe").attr("src","${ctx}/material/list?mode=select");
				 document.getElementById("materialIframe").src="${ctx}/material/list?isValid=1&mode=select";
			})
			
			getDependency();
			getCondDim();
			getMaterialDef($("#id").val(),"");
			
		});
		
		function savaTask(){
			$("#saveForm").submit();
		}
		
		function getCondDim(){
			if($("#timerCat").find("option:selected").val() == "2"){
				$.ajax({
					url:"${ctx}/prjTaskDefine/getCondDim",
					dataType: "html",
			        type: "POST",
					data:{id:$("#id").val(),condDimType:$("#condDimType").find("option:selected").val()},
					success:function(data){
						if(data!=''){
							$("#condContent").html(data);
							$('.selectpicker_').selectpicker();
						}
					}
				});
			}
			
		}
		
		function getDependency(){
			if($("#isReplyonCheck").is(':checked')){
				$.ajax({
					url:"${ctx}/prjTaskDefine/getReplyTask",
					dataType: "html",
			        type: "POST",
					data:{id:$("#id").val()},
					success:function(data){
						if(data!=''){
							$("#replyContent").html(data);
							$('.tag-select_1').chosen({
							    width: '100%',
							    allow_single_deselect: true
							});
							//$('.tag-select_1').selectpicker();
						}
					}
				});
			}else{
				$("#replyContent").html("");
			}
		}
		
		function getMaterialDef(id,materials){
			$.ajax({
				url:"${ctx}/prjTaskDefine/getMaterialDef",
				dataType: "html",
		        type: "POST",
				data:{id:id,materials:materials},
				success:function(data){
					if(data!=''){
						$(data).appendTo("#materialsBody");
						$('.selectpicker_2').selectpicker();
						$('.selectpicker_4').selectpicker();
						$("select[name='conditionType']").each(function(){
							getConditionTypeValue($(this).attr("id"),$(this).attr("conditionTypeValue"));
						});
						
					}
				}
			});
			
		}
		
		function getTaskStage(){
			$.ajax({
				url:"${ctx}/prjTaskDefine/getTaskStage",
				dataType: "html",
		        type: "POST",
				data:{taskType:$("#taskType").find("option:selected").val()},
				success:function(data){
					if(data!=''){
						$("#stageSelectpicker").html(data);
						$('.stageSelectpicker').selectpicker();
					}
				}
			});
		}
		
		function removeMaterial(id){
			swal({   
                title: "你确定吗？",
                text: "删除这条材料信息（删除材料需保存才能生效！）",   
                type: "warning",   
                showCancelButton: true,   
                confirmButtonColor: "#DD6B55",
                cancelButtonText: '取消',
                confirmButtonText: "是的，删除！",
                closeOnConfirm: false 
            }, function(){
            	if($("#tr"+id).length > 0){
    				$("#tr"+id).remove();
    			}
    			var jeesiteMap = sessionStorage.getItem("jeesiteMap");
    			if(jeesiteMap  != null ){
    				var materials = JSON.parse(jeesiteMap);
    				var newMaterials = [];
    				for(var index in materials){
    					if($("#tr"+materials[index].id).length > 0){
    						newMaterials.push(materials[index]);
    					}
    				}
    				sessionStorage.setItem("jeesiteMap",JSON.stringify(newMaterials));
    			}else{
    				$("#materialsBody").html("");
    			}
                swal("完成！", "删除成功！", "success"); 
            });
		}
		
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
		}
		
		function back(){
			window.location = "${ctx}/prjTaskDefine/list";
		}
		
		function getConditionTypeValue(id,conditionTypeValue){
			$.ajax({
				url:"${ctx}/prjTaskDefine/getConditionType",
				dataType: "html",
		        type: "POST",
				data:{conditionType:$("#"+id).find("option:selected").val(),conditionTypeValue:conditionTypeValue},
				success:function(data){
					if(data != ''){
						$("#conditionTypeValueDev"+id).html(data);
						$('.selectpicker_3').selectpicker();
					}
				}
			});
		}
	</script>
</head>
<body>
<form:form role="form" id="saveForm"  modelAttribute="prjTaskDefineDTO" action="${ctx}/prjTaskDefine/save" method="post">
	<form:hidden path="id"/>
	<div class="card">
	    <div class="card-header">
	        <h2>事项定义${empty prjTaskDefineDTO.id?"添加":"修改"}<small>您可通过本功能进行事项定义${empty prjTaskDefineDTO.id?"添加":"修改"}</small></h2>  
			<ul class="actions">
				<li>
					<button title="返回" data-toggle="tooltip" data-placement="bottom" type="button" class="btn btn-success btn-icon m-r-5" onclick="javascrtpt:history.go(-1)"><i class="md md-arrow-back"></i></button>
				</li>
				<li>
					<button title="保存"  data-toggle="tooltip" id="saveTop" data-placement="bottom" type="button" class="btn btn-info btn-icon m-r-5" onclick="savaTask();"><i class="md md-save"></i></button>
				</li>
				<li>
					<button data-toggle="tooltip" data-placement="bottom" type="button" title="刷新" class="btn btn-default btn-icon m-r-5" onclick="refresh();"><i class="md md-autorenew"></i></button>
				</li>
			</ul>
	    </div>
		<div class="card-body card-padding">
				<div class="row">
					<div class="col-xs-6 form-group">
						<label class="control-label" >事项编号：</label>
						<div class="fg-line">
							<form:hidden path="id"/>
							<form:input path="taskCode" class="form-control required" allowClear="true" maxlength="30" />
						</div>
					</div>
					<div class="col-xs-6 form-group">
						<label class="control-label" >事项名称：</label>
						<div class="fg-line">
							<form:input path="taskName" class="form-control required" maxlength="200"/>
						</div>
					</div>
					<div class="col-sm-6 form-group">
						<label class="control-label">事项所属单位：</label>
						<form:select path="deptId" class="form-control selectpicker" data-live-search="true">
							<form:options items="${fns:findOfficeByParent('1')}" itemLabel="name" itemValue="id" htmlEscape="false"/>
						</form:select>
					</div>
					<div class="col-sm-6 form-group">
						<label class="control-label">事项类型：</label>
						<form:select path="taskType" class="form-control selectpicker">
							<form:options items="${fns:getDictList('task_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</div>
					<div class="col-sm-6 form-group">
						<label class="control-label">事项办事深度：</label>
						<form:select path="businessLevel" class="form-control selectpicker">
							<form:options items="${fns:getDictList('business_level')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</div>
					<div class="col-sm-6 form-group">
						<label class="control-label">所属阶段：</label>
						<div id="stageSelectpicker">
							<form:select path="stageId" class="form-control selectpicker">
								<form:options items="${fns:getStageList(prjTaskDefineDTO.taskType)}" itemLabel="stageName" itemValue="id" htmlEscape="false"/>
							</form:select>
						</div>
					</div>
					<div class="col-xs-12 form-group">
						<label class="control-label" >事项描述：</label>
						<div class="fg-line">
							<form:textarea path="description" class="form-control auto-size" maxlength="300"/>
						</div>
					</div>
					<div class="col-sm-6 form-group">
						<label class="control-label">事项时限类型：</label>
						<form:select path="timerCat" class="form-control selectpicker">
							<form:options items="${fns:getDictList('timer_cat')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</div>
					<div class="col-sm-6 form-group">
						<label class="control-label">条件类时限维度：</label>
						<form:select path="condDimType" class="form-control selectpicker" disabled="disabled">
							<form:options items="${fns:getDictList('cond_dim_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</div>
					
					<div id="projectDefault" <c:if test='${prjTaskDefineDTO.timerCat=="2"}'>style="display: none;"</c:if>>
						<div class="col-sm-6 form-group">
							<label class="control-label">一般时限类型：</label>
							<form:select path="defaultDimType" class="form-control selectpicker">
								<form:options items="${fns:getDictList('dim_type')}" itemLabel="label" itemValue="value"/>
							</form:select>
						</div>
						<div class="col-xs-6 form-group">
							<label class="control-label" >一般时限：</label>
							<div class="fg-line">
								<form:input type="number" min="0" max="999" path="defaultTimeLimit" class="form-control"/>
							</div>
						</div>
					</div>
					<div id="condContent">
					</div>
					<div class="col-xs-6 form-group">
						<label class="control-label" >排序号：</label>
						<div class="fg-line">
							<form:input path="orderNum"  min="0" max="999"  type="number" class="form-control required" maxlength="4"/>
						</div>
					</div>
					<div class="col-xs-6 form-group">
						<label class="control-label" >办理对象：</label>
						<div class="fg-line">
							<form:input path="businessObject" class="form-control" maxlength="200"/>
						</div>
					</div>
					
					<div class="col-sm-6 form-group">
						<label class="control-label">法定时限类型：</label>
						<form:select path="lgealTimeType" class="form-control selectpicker">
							<form:options items="${fns:getDictList('dim_type')}" itemLabel="label" itemValue="value"/>
						</form:select>
					</div>
					<div class="col-sm-6 form-group">
						<label class="control-label">承诺时限类型：</label>
						<form:select path="commitTimeType" class="form-control selectpicker">
							<form:options items="${fns:getDictList('dim_type')}" itemLabel="label" itemValue="value"/>
						</form:select>
					</div>
					<div class="col-xs-6 form-group">
						<label class="control-label" >法定时限：</label>
						<div class="fg-line">
						    <form:input path="lgealTimeLimit"  min="0" max="999"  type="number" class="form-control" maxlength="4" />
						</div>
					</div>
					<div class="col-xs-6 form-group">
						<label class="control-label" >承诺时限：</label>
						<div class="fg-line">
							<form:input path="commitTimeLimit"  min="0" max="999"  type="number" class="form-control" maxlength="4" />
						</div>
					</div>
					<div class="col-xs-6 form-group">
						<label class="control-label" >到现场次数：</label>
						<div class="fg-line">
							<form:input path="onsightTimes"  min="0" max="999"  type="number" class="form-control" maxlength="4" />
						</div>
					</div>
					<div class="col-sm-6 form-group">
						<label class="control-label">建筑类型（只有政府项目有区分）：</label>
						<div id="isGovTypeSelectpicker">
							<form:select path="isGovType" class="form-control selectpicker">
								<form:options items="${fns:getDictList('is_gov_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
							</form:select>
						</div>
					</div>
	                <div id="replyContent"></div>
						<div class="col-xs-6 toggle-switch form-group">
							   <form:hidden path="isWithCert" />
							   <label for="isWithCertCheck" class="ts-label control-label">是否发证：</label>
							   <input ${prjTaskDefineDTO.isWithCert=="1"?"checked='checked'":""} name="isWithCertCheck" id="isWithCertCheck" onclick="javascript:$('#isWithCert').val($(this).is(':checked')?'1':'0');" type="checkbox" value="1"  hidden="hidden">
							   <label for="isWithCertCheck" class="ts-helper"></label>
						</div>
					   <div class="col-xs-6 toggle-switch form-group">
					  	   <form:hidden path="isOnlineApply" />
	                       <label for="isOnlineApplyCheck" class="ts-label control-label">是否提供在线申报：</label>
	                       <input ${prjTaskDefineDTO.isOnlineApply=="1"?"checked='checked'":""} name="isOnlineApplyCheck" id="isOnlineApplyCheck" onclick="javascript:$('#isOnlineApply').val($(this).is(':checked')?'1':'0');" type="checkbox" value="1"  hidden="hidden">
	                       <label for="isOnlineApplyCheck" class="ts-helper"></label>
                   	   </div>
					   <div class="col-xs-6 toggle-switch form-group">
					   	   <form:hidden path="isCharge" />
	                       <label for="isChargeCheck" class="ts-label control-label">是否收费：</label>
	                       <input ${prjTaskDefineDTO.isCharge=="1"?"checked='checked'":""} name="isChargeCheck" id="isChargeCheck" onclick="javascript:$('#isCharge').val($(this).is(':checked')?'1':'0');" type="checkbox" value="1"  hidden="hidden">
	                       <label for="isChargeCheck" class="ts-helper"></label>
	                   </div>
	                   <div class="col-xs-6 toggle-switch form-group">
	                  	   <form:hidden path="isOnlineAll" />
	                       <label for="isOnlineAllCheck" class="ts-label control-label">是否网上全程办结：</label>
	                       <input ${prjTaskDefineDTO.isOnlineAll=="1"?"checked='checked'":""} name="isOnlineAllCheck" id="isOnlineAllCheck" onclick="javascript:$('#isOnlineAll').val($(this).is(':checked')?'1':'0');" type="checkbox" value="1"  hidden="hidden">
	                       <label for="isOnlineAllCheck" class="ts-helper"></label>
	                   </div>
	                   <div class="col-xs-6 toggle-switch form-group">
	                  	   <form:hidden path="isValid" value="${empty prjTaskDefineDTO.isValid?1:prjTaskDefineDTO.isValid}"/>
	                       <label for="isValidCheck" class="ts-label control-label">是否启用：</label>
                           <input ${prjTaskDefineDTO.isValid=="0"?"":"checked='checked'"} name="isValidCheck" id="isValidCheck" onclick="javascript:$('#isValid').val($(this).is(':checked')?'1':'0');" type="checkbox"  hidden="hidden">
	                       <label for="isValidCheck" class="ts-helper"></label>
	                   </div>
	                   <div class="col-xs-6 toggle-switch form-group">
	                  	   <form:hidden path="isReplyon" />
	                       <label for="isReplyonCheck" class="ts-label control-label">是否依赖其他事项：</label>
	                       <input ${prjTaskDefineDTO.isReplyon=="1"?"checked='checked'":""} name="isReplyonCheck" id="isReplyonCheck" type="checkbox" value="1"  hidden="hidden">
	                       <label for="isReplyonCheck" class="ts-helper"></label>
	                   </div>
				</div>
			</div>
		</div>
		<div id="materials" role="tabpanel" class="tab-pane animated fadeInRight" >
			<p class="m-l-15">请输入材料信息</p>
			<div class="table-responsive">
				<table class="table table-striped table-vmiddle bootgrid-table">
					<thead>
						<tr>
							<th class="">材料ID</th>
							<th class="">材料名称</th>
							<th class="text-center col-xs-1">原件</th>
							<th class="text-center col-xs-1">复印件</th>
							<th class="text-center">是否必须</th>
							<th class="text-center">是否结果材料</th>
							<th class="text-center col-xs-2">结果事项</th>
<!-- 							<th class="text-center">是否条件类型</th> -->
<!-- 							<th class="text-center col-xs-2">条件类型</th> -->
<!-- 							<th class="text-center col-xs-2">条件值</th> -->
							<th class="text-center">操作</th>
						</tr>
					</thead>
					<tbody id="materialsBody">
					</tbody>
				</table>
			</div>
		</div>
		<div class="btn-demo text-center m-t-20">
			<button type="button" class="btn btn-info waves-effect" id="matrialStep" data-toggle="modal" href="#approve" >材料设置</button>
			<button type="button" class="btn btn-primary waves-effect" id="saveButton" onclick="savaTask();">保存</button>
			<button type="button" class="btn btn-default waves-effect" id="back" onclick="back();">返回</button>
		</div>
	</form:form>
	<!-- 材料弹出页，设置保存和返回按钮，应设置上面的保存返回 -->
	<div class="modal fade" data-modal-color="cyan" id="approve" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">材料配置</h4>
				</div>
				<div style="height:400px;">
					<iframe width="100%" height="100%" id="materialIframe" src="${ctx}/material/list?isValid=1&mode=select"></iframe>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-link" id="confirm">确认</button>
					<button type="button" class="btn btn-link" data-dismiss="modal">返回</button>
				</div>
			</div>
		</div>
	</div>
	<!-- 材料弹出页，设置保存和返回按钮，应设置上面的保存返回 -->
</body>
</html>