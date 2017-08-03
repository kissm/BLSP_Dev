<%@page import="com.google.gson.Gson"%>
<%@page import="com.lpcode.modules.service.project.dto.ProjectChangeForm"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>企业项目材料列表</title>
	<meta name="decorator" content="blank" />
	<style type="text/css">
		#materList .btn-icon {
			width: 30px;
			height: 30px;
			line-height: 30px;
		}
		.fixedTool {
			bottom: 10px;
			right:24px;
			width: 60px;
			z-index: 1000;
		}
		.fixedTool button {
			float: left;
			margin: 2px 0px;
			font-size: 20px;
		}
		#statistical {
			margin: 10px 0px;
		}
		.fixedTool .tmn-counts {
			position: absolute;
		    width: 20px;
		    height: 20px;
		    line-height: 20px;
		    font-size: 10px;
		    text-align: center;
		    top: 90px;
		    right: 7px;
		    color: #FFF;
		    border-radius: 10px;
		}
		.fixedTool .tmn-counts.bgm-red {
			 top: 128px;
		}
	</style>
	<script type="text/javascript">
		//判断材料提交状态的标识(0:未提交材料列表;1:全部材料列表;2:已提交材料列表)
		var materIsChecked = 0;
		<%
			Gson gson = new Gson();
		%>
		var materialDefMap = <%=gson.toJson(((ProjectChangeForm)request.getAttribute("project")).getMaterialDefMap())%>;
		var taskDefMap = <%=gson.toJson(((ProjectChangeForm)request.getAttribute("project")).getTaskDefMap())%>;
		var prjTaskStatus = <%=gson.toJson(((ProjectChangeForm)request.getAttribute("project")).getPrjTaskStatus())%>;
		$(document).ready(function() {
            authSharedAttachmentReuse();

			$('#approve_1').on('show.bs.modal', function () {
				var url = '${ctx}/project/accpet/suplementMaterial?projectId='+ $("#projectId").val();
				document.getElementById("materialIframe").src = url;
			});
			$('#confirm_1').click(function(){
//				$('#approve_1').modal('hide');
				<%--var url = '${ctx}/project/accpet/toMaterial?projectId='+ $("#projectId").val();--%>
				<%--window.location.href = url;--%>
				parent.location.reload();
			});
			onLondCheckMaterialTaskFlag();
			checkTaskFlag();
			$('#top').click(function(){
				document.body.scrollTop=0;
			});
			$('#bottom').click(function(){
				document.body.scrollTop=$('html').height();
			});
			$('#pre').click(function(){
				$('#url').val('pre');
				var jsonDate = JSON.stringify(taskMaterialList);
				$('#jsonData').val(jsonDate);
				$('#searchForm').submit();
			});
			$('#next').click(function(){
				$('#url').val('next');
				var jsonDate = JSON.stringify(taskMaterialList);
				$('#jsonData').val(jsonDate);
				$('#searchForm').submit();
			});
			$('#statistical').click(function(){
				$('#statisticalList').modal('show');
			});
			$('#searchInput').keyup(function(){
				//console.log($(this).val());
				var value = $(this).val();
				value = value.replace("'",'');
				value = value.replace('"','');
				if(value!=''){
					$('#materList tbody tr:visible').hide();
					if(materIsChecked == 2){
						$("#materList tbody tr[date-mater-isComplete='1'][date-mater-name*='"+value+"']").show();
					}else if(materIsChecked == 1){
						$("#materList tbody tr[date-mater-name*='"+value+"']").show();
					}else{
						$("#materList tbody tr[date-mater-name*='"+value+"']").show();
						$("#materList tbody tr[date-mater-isComplete='1'][date-mater-name*='"+value+"']").hide();
					}
				}else{
					if(materIsChecked == 2){
						$("#materList tbody tr[date-mater-isComplete='1']").show();
					}else if(materIsChecked == 1){
						$('#materList tbody tr:hidden').show();
					}else{
						$('#materList tbody tr:hidden').show();
						$("#materList tbody tr[date-mater-isComplete='1']").hide();
					}
				}
				$('html').getNiceScroll().resize();
			});
			$('#materIsComplete').change( function() {
				var value = $('#searchInput').val();
				value = value.replace("'",'');
				value = value.replace('"','');
				var flag = $(this).val();
				if(flag == 0){
					materIsChecked = 0;
					if(value!=''){
						$("#materList tbody tr[date-mater-name*='"+value+"']").show();
						$("#materList tbody tr[date-mater-isComplete='1'][date-mater-name*='"+value+"']").hide();
					}else{
						$('#materList tbody tr:hidden').show();
						$("#materList tbody tr[date-mater-isComplete='1']").hide();
					}
				}else if(flag == 1){
					materIsChecked = 1;
					if(value!=''){
						$("#materList tbody tr[date-mater-name*='"+value+"']").show();
					}else{
						$('#materList tbody tr:hidden').show();
					}
				}else{
					materIsChecked = 2;
					$('#materList tbody tr:visible').hide();
					if(value!=''){
						$("#materList tbody tr[date-mater-isComplete='1'][date-mater-name*='"+value+"']").show();
					}else{
						$("#materList tbody tr[date-mater-isComplete='1']").show();
					}
				}
				$('html').getNiceScroll().resize();
			});
			$('#confirm').click(function(){
				var materialOriginalNumReal = 0;
				var materialCopyNumReal = 0;
				var materialId;
				$('#approve tbody tr').each(function(){
					var originalNumRealInput = $(this).find('input')[0];
					var copyNumRealInput = $(this).find('input')[1];
					var originalNumReal = parseInt($(originalNumRealInput).val());
					var copyNumReal = parseInt($(copyNumRealInput).val())
					var taskId = $(originalNumRealInput).attr('data-taskid');
					materialId = $(originalNumRealInput).attr('data-materialid');
					taskDefMap[taskId][materialId].originalNumReal = originalNumReal;
					taskDefMap[taskId][materialId].copyNumReal = copyNumReal;
					if(materialDefMap[materialId].isOriginalCumulation=='0'){
						if(originalNumReal>0){
							materialOriginalNumReal=originalNumReal;
						}
					}else{
						materialOriginalNumReal+=originalNumReal;
					}
					materialCopyNumReal+=copyNumReal;
					checkMaterialTaskFlag(taskId,materialId);
				});
				$('#originalNum'+materialId).val(materialOriginalNumReal);
				$('#copyNum'+materialId).val(materialCopyNumReal);
				materialDefMap[materialId].originalNumReal=materialOriginalNumReal;
				materialDefMap[materialId].copyNumReal=materialCopyNumReal;
				checkCheckbox(materialDefMap[materialId]);
				checkTaskFlag();
				$('#approve').modal('hide');
			});
			$('input[type="file"]').each(function(){
				$(this)[0].addEventListener("change", function(event) {
					var file = $(this);
					var materId = $(this).attr('date-materId')
					var material = materialDefMap[materId];
					var fileArray = event.target.files;
					if(fileArray.length>0){
						var maxUploadSize = '${fns:getConfig("web.maxUploadSize")}';
						if(fileArray[0].size>maxUploadSize){
							swal("上传失败！","请上传附件小于"+maxUploadSize/1048576+"M", "error");
							return;
						}
						if(material.materialAddr!=null){
							 swal({
				                    title: "确定？",
				                    text: "再次上传将覆盖之前的文件",   
				                    type: "warning",   
				                    showCancelButton: true,   
				                    confirmButtonColor: "#DD6B55",
				                    cancelButtonText: '取消',
				                    confirmButtonText: "是的！",
				                    closeOnConfirm: false,   
				                    closeOnCancel: false
				                }, function(isConfirm){
				                	if (isConfirm) {     
				                		swal({   
				                            title: "正在上传",   
				                            text: "请稍后",   
				                            timer: 100,   
				                            showConfirmButton: false
				                        });
				                		uploadFileMaterial(materId,fileArray[0]);
				                    } else {     
				                        swal("你已取消上传", "", "info");
				                        $(file).val('');
				                        return;
				                    } 
				                });
						}else{
							uploadFileMaterial(materId,fileArray[0]);
						}
					}
				});
			});
			initMaterialList();
			totalMaterialNum();
			upMaterialNum();
		});	
		function checkboxChange(checkbox,id){
			if($(checkbox).is(':checked')){
				var originalNum = materialDefMap[id].originalNum;
				var copyNum = materialDefMap[id].copyNum;
				$('#originalNum'+id).val(originalNum);
				$('#copyNum'+id).val(copyNum);
				materialDefMap[id].originalNumReal=originalNum;
				materialDefMap[id].copyNumReal=copyNum;
				$(checkbox).parent().parent().parent().attr('date-mater-isComplete','1');
			}else{
				$('#originalNum'+id).val(0);
				$('#copyNum'+id).val(0);
				materialDefMap[id].originalNumReal=0;
				materialDefMap[id].copyNumReal=0;
				$(checkbox).parent().parent().parent().attr('date-mater-isComplete','0');
			}
			//计算已提交材料数
			upMaterialNum();
			originalAutoWrite(materialDefMap[id]);
			copyAutoWrite(materialDefMap[id]);
		}
		function showTaskList(id){
			$('#materName').html(materialDefMap[id].name);
			$('#materNum').html('（原件'+materialDefMap[id].originalNum+'份，复印件'+materialDefMap[id].copyNum+'份）');
			$('#approve tbody').empty();
			var taskList = materialDefMap[id].taskList;
			var taskTr =
			'<tr data-toggle="tooltip" data-placement="top" title="$[description]">'+
				'<td>'+
					'$[taskName]$[isMandatory]'+
				'</td>'+
				'<td>'+
					'原件$[originalNum]份，复印件$[copyNum]份'+
				'</td>'+
				'<td>'+
					'<div class="fg-line $[originalDisabled]">'+
						'<input $[originalDisabled] name="original" data-materialid="$[materialId]" data-taskid=$[taskId] value="$[originalNumReal]" onchange="originalNumTaskChange(this,$[originalNum],$[isOriginalCumulation])" type="text" class="form-control input-sm text-center" >'+
					'</div>'+
				'</td>'+
				'<td>'+
					'<div class="fg-line $[disabled]">'+
						'<input $[disabled] data-materialid="$[materialId]" data-taskid=$[taskId] value="$[copyNumReal]" onchange="copyNumTaskChange(this,$[copyNum])" type="text" class="form-control input-sm text-center" >'+
					'</div>'+
				'</td>'+
			'</tr>';
			var taskTrHtml = '';
			for(var a=0;a<taskList.length;a++){
				if(!taskDefMap[taskList[a].id].taskName){
					taskDefMap[taskList[a].id].taskName=taskList[a].taskName;
				}
				var taskTrCopy = taskTr;
				if(materialDefMap[id].materOriginalFlag){
					taskTrCopy=taskTrCopy.replace('$[originalDisabled]','disabled');
					taskTrCopy=taskTrCopy.replace('$[originalDisabled]','disabled');
				}
				if(taskList[a].taskStatus=='4'||taskList[a].taskStatus=='7'){
					taskTrCopy=taskTrCopy.replace('$[originalDisabled]','disabled');
					taskTrCopy=taskTrCopy.replace('$[originalDisabled]','disabled');
					taskTrCopy=taskTrCopy.replace('$[disabled]','disabled');
					taskTrCopy=taskTrCopy.replace('$[disabled]','disabled');
				}else{
					taskTrCopy=taskTrCopy.replace('$[originalDisabled]','');
					taskTrCopy=taskTrCopy.replace('$[originalDisabled]','');
					taskTrCopy=taskTrCopy.replace('$[disabled]','');
					taskTrCopy=taskTrCopy.replace('$[disabled]','');
				}
				taskTrCopy=taskTrCopy.replace('$[taskName]',taskList[a].taskName);
				taskTrCopy=taskTrCopy.replace('$[materialId]',id);
				taskTrCopy=taskTrCopy.replace('$[materialId]',id);
				taskTrCopy=taskTrCopy.replace('$[taskId]',taskList[a].id);
				taskTrCopy=taskTrCopy.replace('$[taskId]',taskList[a].id);
				taskTrCopy=taskTrCopy.replace('$[isOriginalCumulation]',materialDefMap[id].isOriginalCumulation);
				var originalNumReal = taskDefMap[taskList[a].id][id].originalNumReal;
				var copyNumReal = taskDefMap[taskList[a].id][id].copyNumReal;
				if(!originalNumReal){
					originalNumReal=0;
				}
				if(!copyNumReal){
					copyNumReal=0;
				}
				if(taskDefMap[taskList[a].id][id].isMandatory=='1'){
					taskTrCopy=taskTrCopy.replace('$[isMandatory]','<font class="c-red">*</font>');
				}else{
					taskTrCopy=taskTrCopy.replace('$[isMandatory]','');
				}
				var description = taskDefMap[taskList[a].id][id].description;
				if(description){
					taskTrCopy=taskTrCopy.replace('$[description]',taskDefMap[taskList[a].id][id].description);
				}else{
					taskTrCopy=taskTrCopy.replace('$[description]','');
				}
				taskTrCopy=taskTrCopy.replace('$[originalNum]',taskDefMap[taskList[a].id][id].originalNum);
				taskTrCopy=taskTrCopy.replace('$[originalNum]',taskDefMap[taskList[a].id][id].originalNum);
				taskTrCopy=taskTrCopy.replace('$[copyNum]',taskDefMap[taskList[a].id][id].copyNum);
				taskTrCopy=taskTrCopy.replace('$[copyNum]',taskDefMap[taskList[a].id][id].copyNum);
				taskTrCopy=taskTrCopy.replace('$[originalNumReal]',originalNumReal);
				taskTrCopy=taskTrCopy.replace('$[copyNumReal]',copyNumReal);
				taskTrHtml+=taskTrCopy;
			}
			$('#approve tbody').append(taskTrHtml);
			if ($('[data-toggle="tooltip"]')[0]) {
		        $('[data-toggle="tooltip"]').tooltip();
		    }
			$('#approve').modal('show');
		}
		function originalNumTaskChange(input,originalNum,isOriginalCumulation){
			var originalNumReal = parseInt($(input).val());
			if(isNaN(originalNumReal)){
				originalNumReal=0;
				$(input).val(originalNumReal);
			}
			if(isOriginalCumulation==0){
				if(originalNumReal==0){
					$('#approve tbody input[name="original"]').val(0);
				}else{
					$('#approve tbody input[name="original"]').each(function(){
						$(this).val(taskDefMap[$(this).attr('data-taskid')][$(this).attr('data-materialid')].originalNum);
					});
				}
			}else{
				if(originalNumReal>originalNum){
					$(input).val(originalNum);
				}
			}		
		}
		function copyNumTaskChange(input,copyNum){
			var copyNumReal = parseInt($(input).val());
			if(isNaN(copyNumReal)){
				copyNumReal=0;
				$(input).val(copyNumReal);
			}
			if(copyNumReal>copyNum){
				$(input).val(copyNum);
			}
		}
		function getOriginalMixNum(id){
			var originalMixNum = 0;
			var taskList =  materialDefMap[id].taskList;
			for(var a=0;a<taskList.length;a++){
				if(taskList[a].taskStatus=='4'||taskList[a].taskStatus=='7'){
					var originalNumReal = taskDefMap[taskList[a].id][id].originalNumReal;
					if(!originalNumReal){
						originalNumReal=0;
					}
					originalMixNum+=originalNumReal;
				}
			}
			return originalMixNum;
		}
		function getCopyMixNum(id){
			var copyMixNum = 0;
			var taskList =  materialDefMap[id].taskList;
			for(var a=0;a<taskList.length;a++){
				if(taskList[a].taskStatus=='4'||taskList[a].taskStatus=='7'){
					var copyNumReal = taskDefMap[taskList[a].id][id].copyNumReal;
					if(!copyNumReal){
						copyNumReal=0;
					}
					copyMixNum+=copyNumReal;
				}
			}
			return copyMixNum;
		}
		function originalNumChange(id){
			var originalNum = materialDefMap[id].originalNum;
			var originalMixNum = getOriginalMixNum(id);
			var originalNumReal = parseInt($('#originalNum'+id).val());
			var re = /^[1-9]+[0-9]*]*$/;
		    if (!re.test(originalNumReal)){
		    	originalNumReal=0;
				$('#originalNum'+id).val(originalNumReal);
		    }
			if(originalNumReal>originalNum){
				$('#originalNum'+id).val(originalNum);
				materialDefMap[id].originalNumReal=originalNum;
			}else if(originalNumReal<originalMixNum){
				$('#originalNum'+id).val(originalMixNum);
				materialDefMap[id].originalNumReal=originalMixNum;
			}else{
				materialDefMap[id].originalNumReal=originalNumReal;
			}
			checkCheckbox(materialDefMap[id]);
			originalAutoWrite(materialDefMap[id]);
		}
		function copyNumChange(id){
			var copyNum = materialDefMap[id].copyNum;
			var copyMixNum = getCopyMixNum(id);
			var copyNumReal = parseInt($('#copyNum'+id).val());
			var re = /^[1-9]+[0-9]*]*$/;
		    if (!re.test(copyNumReal)){
		    	copyNumReal=0;
				$('#copyNum'+id).val(copyNumReal);
		    }
			if(copyNumReal>copyNum){
				$('#copyNum'+id).val(copyNum);
				materialDefMap[id].copyNumReal=copyNum;
			}else if(copyNumReal<copyMixNum){
				$('#copyNum'+id).val(copyMixNum);
				materialDefMap[id].copyNumReal=copyMixNum;
			}else{
				materialDefMap[id].copyNumReal=copyNumReal;
			}
			checkCheckbox(materialDefMap[id]);
			copyAutoWrite(materialDefMap[id]);
		}
		function checkCheckbox(material){
			if(!material.originalNumReal){
				material.originalNumReal=0
			}
			if(!material.copyNumReal){
				material.copyNumReal=0
			}
			if(material.originalNumReal==0&&material.copyNumReal==0){
				$('#checkbox'+material.materialId).prop("checked",false);
				$('#checkbox'+material.materialId).parent().parent().parent().attr('date-mater-isComplete','0');
			}else{
				$('#checkbox'+material.materialId).prop("checked",true);
				$('#checkbox'+material.materialId).parent().parent().parent().attr('date-mater-isComplete','1');
			}
			//计算已提交材料数
			upMaterialNum();
		}
		function  originalAutoWrite(material){
			var originalNumReal = material.originalNumReal;
			var originalMixNum = getOriginalMixNum(material.materialId);
			originalNumReal-=originalMixNum;
			var taskList = material.taskList;
			var isOriginalCumulation = material.isOriginalCumulation;
			for(var a=0;a<taskList.length;a++){
				if(!taskDefMap[taskList[a].id].taskName){
					taskDefMap[taskList[a].id].taskName=taskList[a].taskName;
				}
				var originalNum = 0;
				var taskOriginalNum = taskDefMap[taskList[a].id][material.materialId].originalNum;
				if(isOriginalCumulation=='0'){
					originalNum = taskOriginalNum;
				}else{
					if(taskList[a].taskStatus=='4'||taskList[a].taskStatus=='7'){
						originalNum = taskDefMap[taskList[a].id][material.materialId].originalNumReal;
					}else if(originalNumReal>=taskOriginalNum){
						originalNum = taskOriginalNum;
						originalNumReal-=taskOriginalNum;
					}else{
						originalNum = originalNumReal;
						originalNumReal = 0;
					}
				}
				taskDefMap[taskList[a].id][material.materialId].originalNumReal = originalNum;
				checkMaterialTaskFlag(taskList[a].id,material.materialId);
			}
			checkTaskFlag();
		}
		function  copyAutoWrite(material){
			var copyNumReal = material.copyNumReal;
			var copyMixNum = getCopyMixNum(material.materialId);
			copyNumReal-=copyMixNum;
			var taskList = material.taskList;
			for(var a=0;a<taskList.length;a++){
				if(!taskDefMap[taskList[a].id].taskName){
					taskDefMap[taskList[a].id].taskName=taskList[a].taskName;
				}
				var copyNum = 0;
				var taskCopyNum = taskDefMap[taskList[a].id][material.materialId].copyNum;
				if(taskList[a].taskStatus=='4'||taskList[a].taskStatus=='7'){
					copyNum = taskDefMap[taskList[a].id][material.materialId].copyNumReal;
				}else if(copyNumReal>=taskCopyNum){
					copyNum = taskCopyNum;
					copyNumReal-=taskCopyNum;
				}else{
					copyNum = copyNumReal;
				}
				taskDefMap[taskList[a].id][material.materialId].copyNumReal = copyNum;
				checkMaterialTaskFlag(taskList[a].id,material.materialId);
			}
			checkTaskFlag();
		}
		function checkMaterialTaskFlag(taskId,materialId){
			var taskMaterial = taskDefMap[taskId][materialId];
			var originalNum = taskMaterial.originalNum;
			var copyNum = taskMaterial.copyNum;
			var originalNumReal = taskMaterial.originalNumReal;
			var copyNumReal = taskMaterial.copyNumReal;
			if(originalNum == originalNumReal && copyNum == copyNumReal){
				taskMaterial.flag=true;
			}else if((originalNumReal!=0&&typeof(originalNumReal)!='undefined')||(copyNumReal!=0&&typeof(copyNumReal)!='undefined')){
				taskMaterial.flag=false;
			}else {
				taskMaterial.flag=null;
			}
		}
		var taskCollectList = [];
		var taskNotCollectList = [];
		var taskMaterialList = [];
		function checkTaskFlag(){
			taskCollectList.splice(0,taskCollectList.length);
			taskNotCollectList.splice(0,taskNotCollectList.length);
			taskMaterialList.splice(0,taskMaterialList.length);
			for(var key in taskDefMap){
				var flag = false;
				var mandatoryflag = true;
				var hasMandatoryflag = false;
				var hasFlag = false;
				for(var mKey in taskDefMap[key]){
					if(mKey!='taskName'&&mKey!='taskFlag'){
						if(!taskDefMap[key].taskFlag){
							if(taskDefMap[key][mKey].isMandatory=='1'){
								hasMandatoryflag = true;
								if(!taskDefMap[key][mKey].flag||taskDefMap[key][mKey].flag==null){
									mandatoryflag = false;
								}
							}else{
								if(taskDefMap[key][mKey].flag){
									flag = true;
								}
							}
							if(!materialDefMap[mKey].materialAddr){
								materialDefMap[mKey].materialAddr=null;
							}
							if(taskDefMap[key][mKey].flag!=null){
								hasFlag = true;
							}
						}
						if(taskDefMap[key][mKey].flag!=null||materialDefMap[mKey].materialAddr!=null){
							var originalNum = taskDefMap[key][mKey].originalNumReal;
							var copyNum = taskDefMap[key][mKey].copyNumReal;
							var newJson = $.extend({}, taskDefMap[key][mKey]);
							delete newJson.id;
							delete newJson.originalNumReal;
							delete newJson.copyNumReal;
							delete newJson.flag;
							newJson.originalNum = originalNum;
							newJson.copyNum = copyNum;
							newJson.materialAddr = materialDefMap[mKey].materialAddr;
							if(taskDefMap[key][mKey].flag){
								newJson.isComplete = '1';
							}else{
								newJson.isComplete = '0';
							}
							taskMaterialList.push(newJson);
						}
					}
				}
				
				if(prjTaskStatus!=null){
					if(prjTaskStatus[key]!='4'&&prjTaskStatus[key]!='7'&&prjTaskStatus[key]!='8'){
						if(hasMandatoryflag){
							if(mandatoryflag){
								taskCollectList.push(taskDefMap[key]);
							}else{
								if(hasFlag){
									taskNotCollectList.push(taskDefMap[key]);
								}
							}
						}else{
							if(flag){
								taskCollectList.push(taskDefMap[key]);
							}else{
								if(hasFlag){
									taskNotCollectList.push(taskDefMap[key]);
								}
							}
						}		
					}
				}else{
					if(hasMandatoryflag){
						if(mandatoryflag){
							taskCollectList.push(taskDefMap[key]);
						}else{
							if(hasFlag){
								taskNotCollectList.push(taskDefMap[key]);
							}
						}
					}else{
						if(flag){
							taskCollectList.push(taskDefMap[key]);
						}else{
							if(hasFlag){
								taskNotCollectList.push(taskDefMap[key]);
							}
						}
					}	
				}
			}
			var taskCollectHtml = '';
			for(var a=0;a<taskCollectList.length;a++){
				var html = '<li class="control-label col-xs-12">$[taskCollectName]</li>';
				taskCollectHtml+=html.replace('$[taskCollectName]',taskCollectList[a].taskName);
			}
			$('#taskCollect').html(taskCollectHtml);
			$('#taskCollectNum').html(taskCollectList.length);
			
			var taskNotCollectHtml = '';
			for(var a=0;a<taskNotCollectList.length;a++){
				var html = '<li class="control-label col-xs-12">$[taskNotCollectName]</li>';
				taskNotCollectHtml+=html.replace('$[taskNotCollectName]',taskNotCollectList[a].taskName);
			}
			$('#taskNotCollect').html(taskNotCollectHtml);
			$('#taskNotCollectNum').html(taskNotCollectList.length);
		}
		function onLondCheckMaterialTaskFlag(){
			for(var key in taskDefMap){
				for(var mKey in taskDefMap[key]){
					if(mKey!='taskName'){
						checkMaterialTaskFlag(key,mKey);
					}
				}
			}
			for(var materialId in materialDefMap){
				var taskList = materialDefMap[materialId].taskList;
				for(var a=0;a<taskList.length;a++){
					taskDefMap[taskList[a].id].taskName=taskList[a].taskName;
					if(taskList[a].taskStatus!='0'&&typeof(taskList[a].taskStatus)!='undefined'){
						taskDefMap[taskList[a].id].taskFlag = true;	
					}
				}
			}
		}
		function selectedFile(id){
			var materialAddr = $('#fileAddressfile'+id).val();
			materialDefMap[id].materialAddr=materialAddr;
		}
		function uploadFileMaterial(id,file){
			var progress = $("#progress").clone();
			var backdrop = $("#backdrop").clone();
			var data = new FormData();
			data.append("name", encodeURIComponent(file.name));
			data.append("file", file);
			var xhr = new XMLHttpRequest();
			xhr.open("post", _ctx_ + "/sys/uploadImage", true);
			xhr.setRequestHeader("X_Requested_With", "");
			xhr.timeout = 60000;
			xhr.ontimeout = function(){
			    swal("上传失败！","请求超时", "error");
			}
			xhr.upload.addEventListener("progress", function(e) {
				if(e.lengthComputable){
					objStateElement.backgroundSize((e.loaded / e.total),progress,backdrop);
				}
			}, false);
			xhr.onreadystatechange = function(e) {
				if (xhr.readyState == 4) {
					if (xhr.status == 200) {
						try {
							var json = JSON.parse(xhr.responseText);
							if(json.resCode=="00000000"||json.resCode=="0"){
								materialDefMap[id].materialAddr=json.obj[0];
								checkTaskFlag();
								var fileName = materialDefMap[id].name;
								$("#download"+id).attr('onClick',"window.open('"+_ctx_+"/sys/download?pathUrl="+json.obj[0]+"&coi="+fileName+"')");
								$("#download"+id).removeClass('hide');
								$('#view'+id).attr('onClick',"window.open('"+_ctx_+"/sys/filePreview?pathUrl="+json.obj[0]+"')");
								$('#view'+id).removeClass('hide');
							}else{
								objStateElement.backgroundSize(1,progress,backdrop);
								swal("上传失败！",json.msg, "error");
							}
						} catch (e) {
							objStateElement.backgroundSize(1,progress,backdrop);
							swal("上传失败！","", "error");
							return;
						} 
					}else{
						objStateElement.backgroundSize(1,progress,backdrop);
						swal("上传失败！","", "error");
					}
				}
			}
			xhr.send(data);
			$('#main',top.window.document).append(progress);
			$('#main',top.window.document).append(backdrop);
			progress.removeClass('hide');
			backdrop.removeClass('hide');
		}
		//初始化材料列表为未提交列表
		function initMaterialList(){
			$('#materList tbody tr:hidden').show();
			$("#materList tbody tr[date-mater-isComplete='1']").hide();
		}
		//总材料数
		function totalMaterialNum(){
			var num = 0;
			for(i in materialDefMap){
				num += 1;
			}
			$("#totalMaterialNum").val(num);
		}
		//已提交材料数
		function upMaterialNum(){
			var num = 0;
			$("#materList tbody tr[date-mater-isComplete='1']").each(function (){
				num += 1;
			});
			$("#upMaterialNum").val(num);
		}

		function isEmpty(data){
			return (data == null || data == undefined || data == "" || data == "null");
		}

		//从共享材料库中获取材料
		function sharedAttachmentReuse(){
			var data = {};
			var companyCode = $("#companyCode").val();
			var prjCode = $("#prjCode").val();
			var prjId = $("#projectId").val();
			var stageId = $("#stageId").val();
			data["companyCode"]=companyCode;
			data["prjCode"]=prjCode;
			data["prjId"]=prjId;
			data["stageId"]=stageId;
			if(isEmpty(companyCode) || isEmpty(prjCode)){
				var msg = "请输入项目编号和企业信用代码后重试!";
				var validateKey = "companyCode";
				if(isEmpty(companyCode)){
					msg = "请输入企业信用代码和组织机构代码后重试!";
				}
				swal({
					title: msg,
					text: "",
					type: "warning",
					showCancelButton: false,
					confirmButtonColor: "#DD6B55",
					confirmButtonText: "确定",
					closeOnConfirm: false
				}, function(){
					swal({
						title: msg,
						text: "",
						type: "warning",
						timer: 10,
						showConfirmButton: false
					});
					$("#"+validateKey).focus();
				});
				return;
			}
			openWindow({
				id:'sharedAttachmentReuse',
				title:'共享材料库',
				params:data,
				url:'${ctx}/shared/attachment/reuse/list',
				buttons: [{
					text:'确定',
					onclick:'ok()'
				}],
				width:'800px',
				height:'500px',
				callBack : function(data){
					for(var i=0; i<data.length; i++){
						var materId = data[i].id;
						var fileName = data[i].materName;
						var fileUrl = data[i].materialAddr;
						$('#checkbox'+materId).prop("checked",true);
						checkboxChange($('#checkbox'+materId),materId);
						$("#download"+materId).attr('onClick',"window.open('"+_ctx_+"/sys/download?pathUrl="+fileUrl+"&coi="+fileName+"')");
						$("#download"+materId).removeClass('hide');
						$('#view'+materId).attr('onClick',"window.open('"+_ctx_+"/sys/filePreview?pathUrl="+fileUrl+"')");
						$('#view'+materId).removeClass('hide');
						materialDefMap[materId].materialAddr=fileUrl;
						checkTaskFlag();
					}
				}
			});
		}

        //自动从共享材料库中获取材料
        function authSharedAttachmentReuse(){
            var companyCode = $("#companyCode").val();
            var prjCode = $("#prjCode").val();
            var prjId = $("#projectId").val();
            var stageId = $("#stageId").val();
            if(isEmpty(companyCode) || isEmpty(prjCode)){
                return;
            }else{
                $.ajax({
                    type:'post',
                    url:'${ctx}/shared/authattachment/reuse/list',
                    dataType:'json',
                    data:{"companyCode":companyCode,"prjCode":prjCode,"prjId":prjId,"stageId":stageId},
                    success : function(data) {
                        console.log(JSON.stringify(data));
                        if(!isEmpty(data) && JSON.stringify(data) != "{}"){
                            swal({
                                title: "材料信息自动复用成功！",
                                text: "",
                                type: "success",
                                timer: 2100,
                                showConfirmButton: false
                            });
                            console.log(JSON.stringify(data));
                            for(var i=0; i<data.length; i++){
                                var materId = data[i].id;
                                var fileName = data[i].materName;
                                var fileUrl = data[i].materialAddr;
                                $('#checkbox'+materId).prop("checked",true);
                                checkboxChange($('#checkbox'+materId),materId);
                                $("#download"+materId).attr('onClick',"window.open('"+_ctx_+"/sys/download?pathUrl="+fileUrl+"&coi="+fileName+"')");
                                $("#download"+materId).removeClass('hide');
                                $('#view'+materId).attr('onClick',"window.open('"+_ctx_+"/sys/filePreview?pathUrl="+fileUrl+"')");
                                $('#view'+materId).removeClass('hide');
                                materialDefMap[materId].materialAddr=fileUrl;
                                checkTaskFlag();
                            }
                        }
                    }
                });
            }
        }
	</script>
</head>
<body>

<form id="searchForm"  action="${ctx}/project/bizaccept/material" method="post" >
	<input type="hidden" name="action" value="${action}"/>
	<input type="hidden" name="projectId" id ="projectId" value="${project.prjInstanceVo.id}">
	<input type="hidden" id="companyCode" name="companyCode" value="${project.prjInstanceVo.companyCode}">
	<input type="hidden" id="prjCode" name="prjCode" value="${project.prjInstanceVo.prjCode}">
	<input type="hidden" id="stageId" name="stageId" value="${project.prjInstanceVo.stageId}">
	<input id="url" type="hidden" name="url" value=""/>
	<input id="jsonData" type="hidden" name="jsonData" value=""/>
</form>
	<div class="card">
		<div class="card-header">
			<h2>
				企业项目新办材料选择<small>您可通过本功能进行企业项目新办材料选择</small>
			</h2>
		</div>
		<div class="card-body card-padding">
			<div class="row">
				<div class="col-xs-3 form-group">
					<label class="control-label">材料名称：</label>
					<div class="fg-line readonly">
						<input id="searchInput" type="text" class="form-control" placeholder="请输入搜索">
					</div>
				</div>
				<!-- <div class="col-sm-4 form-group">
		            <label class="control-label">&nbsp;</label>
		            <div class="checkbox">
		                <label class="checkbox checkbox-inline m-r-20">
		                    <input type="checkbox" id="materIsComplete" value="1">
		                    <i class="input-helper"></i>只显示已提交材料列表
		                </label>
		            </div>
		        </div> -->
		        <div class="col-sm-4 form-group">
					<label class="control-label">状态：</label>
					<select id="materIsComplete" class="form-control selectpicker" data-live-search="true">
						<option value="0">未提交材料列表</option>
						<option value="1">全部材料列表</option>
						<option value="2">已提交材料列表</option>
					</select>
				</div>
				<div class="col-xs-2 form-group">
					<label class="control-label">全部材料数量：</label>
					<div class="fg-line">
						<input id="totalMaterialNum" class="form-control" type="text" disabled />
					</div>
				</div>
				<div class="col-xs-2 form-group">
					<label class="control-label">已提交材料数量：</label>
					<div class="fg-line">
						<input id="upMaterialNum" class="form-control" type="text" disabled />
					</div>
				</div>
			</div>
			<c:if test="${supplementMater}">
				<div class="btn-toolbar p-b-20">
					<button type="button" href="#approve_1" class="btn bgm-deeporange btn-sm" data-toggle="modal">用户补交材料列表</button>
				</div>
			</c:if>
			<%--TODO 共享材料暂时屏蔽待上线--%>
			<div class="panel-body">
				<div class="col-xs-12">
					<button type="button" class="btn bgm-lightblue" data-toggle="modal" onclick="sharedAttachmentReuse();" style="width:154px;">共享材料库</button>
				</div>
			</div>
			<div class="table-responsive">
				<table id="materList" class="table table-striped table-vmiddle bootgrid-table">
					<thead>
						<tr>
							<%-- <th class="text-center col-xs-1">是否收齐</th> --%>
							<th class="text-center">序号</th>
							<th class="text-center">申请材料</th>
							<th class="text-center col-xs-2">需求情况</th>
							<th class="text-center col-xs-3">收件情况</th>
							<th class="text-center col-xs-1">是否提供</th>
							<th class="text-center col-xs-1">操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${project.materialDefMap}" var="mater" varStatus="status">
							<tr date-mater-name="${mater.value.name}"
							${(mater.value.originalNumReal!=0&&!empty mater.value.originalNumReal)||(mater.value.copyNumReal!=0&&!empty mater.value.copyNumReal)?'date-mater-isComplete="1"':''}>
								<%--<td class="text-center"></td> --%>
								<td>
									${status.index+1}
								</td>
								<td>
									${mater.value.name}
								</td>
								<td class="text-center">
									原件${mater.value.originalNum}份，复印件${mater.value.copyNum}份
								</td>
								<td>
									<c:set var="materFlag" value="true"/>
									<c:set var="materOriginalFlag" value="false"/>
									<c:set var="taskFlag" value="false"/>
									<c:forEach items="${mater.value.taskList}" var="taskList" varStatus="taskListStatus">
										<c:if test="${mater.value.isMandatory=='0'||empty mater.value.isMandatory}">
											<c:if test="${taskList.taskStatus=='4'||taskList.taskStatus=='7'}">
												<c:set var="materOriginalFlag" value="true"/>
												<script type="text/javascript">
													materialDefMap['${mater.key}'].materOriginalFlag = true;
												</script>
											</c:if>
										</c:if>
										<c:if test="${taskList.taskStatus!='4'&&taskList.taskStatus!='7'}">
											<c:set var="materFlag" value="false"/>
										</c:if>
										<c:if test="${taskList.taskStatus=='4'||taskList.taskStatus=='7'}">
											<c:set var="taskFlag" value="true"/>
										</c:if>
									</c:forEach>
									<label class="control-label" style="padding-top: 7px;">原件</label>
									<div class="fg-line ${materOriginalFlag||materFlag?'disabled':''}" style="width: 40px">
										<input ${materOriginalFlag||materFlag?'disabled':''} id="originalNum${mater.key}" onchange="originalNumChange('${mater.key}')" 
										value="${empty mater.value.originalNumReal?0:mater.value.originalNumReal}" type="text" class="form-control input-sm text-center" >
									</div>
									<label class="control-label" style="padding-top: 7px;">，复印件</label>
									<div class="fg-line ${materFlag?'disabled':''}" style="width: 40px">
										<input ${materFlag?'disabled':''} id="copyNum${mater.key}" onchange="copyNumChange('${mater.key}')" 
										 value="${empty mater.value.copyNumReal?0:mater.value.copyNumReal}" type="text" class="form-control input-sm text-center">
									</div>
									<%--<c:if test="${fn:length(mater.value.taskList)>0}">--%>
									<div class="fg-line actions" style="width: 30px">
										<a data-toggle="modal" onclick="showTaskList('${mater.key}');">
											<i class="md md-more-vert"></i>
										</a>
									</div>
	                                <%--</c:if>--%>
								</td>
								<td class="text-center">
									<label class="checkbox checkbox-inline ${taskFlag?'disabled':''}">
										<input ${taskFlag?'disabled':''} id="checkbox${mater.key}" type="checkbox" onchange="checkboxChange(this,'${mater.key}');"
										 ${(mater.value.originalNumReal!=0&&!empty mater.value.originalNumReal)||(mater.value.copyNumReal!=0&&!empty mater.value.copyNumReal)?'checked="checked"':''}  >
										<i class="input-helper"></i>
									</label>
								</td>
								<td class="text-nowrap">
									<input id="fileAddress${mater.key}" type="hidden">
									<div data-toggle="tooltip" data-placement="bottom" type="button" title="上传" type="button" class="btn btn-icon btn-file m-r-5 btn-info">
										<i class="md md-file-upload"></i>
										<input type="file" date-materId="${mater.key}" >
									</div>
									<c:if test="${empty mater.value.materialAddr}">
										<button data-toggle="tooltip" data-placement="bottom" type="button" title="下载" id="download${mater.key}"
										class="btn btn-icon btn-file bgm-lightblue hide">
											<i class="md md-file-download"></i>
										</button>
										<button data-toggle="tooltip" data-placement="bottom" type="button" title="预览" id="view${mater.key}" 
										class="btn btn-success btn-icon btn-file hide">
											<i class="md md-visibility"></i>
										</button>
									</c:if>
									<c:if test="${!empty mater.value.materialAddr}">
										<button data-toggle="tooltip" data-placement="bottom" type="button" title="下载" id="download${mater.key}"
										 onclick="window.open('${ctx}/sys/download?pathUrl=${mater.value.materialAddr}&coi=${mater.value.name}')"
										 class="btn btn-icon btn-file bgm-lightblue">
											<i class="md md-file-download"></i>
										</button>
										<button data-toggle="tooltip" data-placement="bottom" type="button" title="预览" id="view${mater.key}" 
										onclick="window.open('${ctx}/sys/filePreview?pathUrl=${mater.value.materialAddr}')" 
										class="btn btn-success btn-icon btn-file">
											<i class="md md-visibility"></i>
										</button>
									</c:if>
									
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<div class="modal fade" data-modal-color="cyan" id="approve" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog ">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">材料分配事项</h4>
				</div>
				<div class="modal-body bgm-white text-muted p-20" >
					<p><label id="materName"></label><label id="materNum"></label></p>
				</div>
				<div class="table-responsive bgm-white text-muted o-auto" style="height:260px;">
					<table class="table table-striped table-vmiddle">
						<thead>
							<tr>
								<th class="text-center">事项名称</th>
								<th class="text-center col-xs-3">需求情况</th>
								<th class="text-center col-xs-1">原件</th>						
								<th class="text-center col-xs-1">复印件</th>
							</tr>
						</thead>
						<tbody>
							
						</tbody>
					</table>
				</div>
				<div class="modal-footer">
					<button id="confirm" type="button" class="btn btn-link">确认</button>
					<button type="button" class="btn btn-link" data-dismiss="modal">关闭</button>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" data-modal-color="cyan" id="statisticalList" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-sm">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">相关事项</h4>
				</div>
				<div class="modal-body bgm-white text-muted p-20 o-auto" style="max-height:300px;">
					<label class="col-xs-12 c-green">可启动审批</label>
					<div id="taskCollect" class="col-xs-12">
						
					</div>
					<label class="col-xs-12 c-red">材料不齐</label>
					<div id="taskNotCollect" class="col-xs-12">
						
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-link" data-dismiss="modal">关闭</button>
				</div>
			</div>
		</div>
	</div>
	<div class="p-fixed fixedTool">
		<button id="top" data-toggle="tooltip" data-placement="top" title="顶部" class="btn btn-default">
			<i class="md md-vertical-align-top"></i>
		</button>
		<button id="pre" data-toggle="tooltip" data-placement="top" title="上一步" class="btn btn-primary">
			<i class="md md-arrow-back"></i>
		</button>
		<button id="statistical" data-toggle="tooltip" data-placement="top" title="相关事项" class="btn bgm-pink">
			<i class="md md-add-shopping-cart"></i>
		</button>
		<i id="taskCollectNum" class="tmn-counts bgm-green">6</i>
		<i id="taskNotCollectNum" class="tmn-counts bgm-red">6</i>
		<button id="next" data-toggle="tooltip" data-placement="top" title="下一步" class="btn btn-warning">
			<i class="md md-arrow-forward"></i>
		</button>
		<button id="bottom" data-toggle="tooltip" data-placement="top" title="底部" class="btn btn-default">
			<i class="md md-vertical-align-bottom"></i>
		</button>
	</div>
	<div id="progress" class="progress progress-striped hide" style="z-index: 10000;width: 30%;position: fixed;top: 50%;bottom: 50%;left: 35%;right: 35%;">
	      <div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100"></div>
	</div>
	<div id="backdrop" class="modal-backdrop fade in hide" style="width: 100%;height: 100%;z-index: 9999"></div>

	<!-- 补材料弹出页，设置保存和返回按钮，应设置上面的保存返回 -->
	<div class="modal fade" data-modal-color="cyan" id="approve_1" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<h4 class="modal-title">补材料列表处理</h4>
				</div>
				<div style="height:400px;">
					<iframe width="100%" height="100%" id="materialIframe" src=""></iframe>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-link" id="confirm_1">确认</button>
				</div>
			</div>
		</div>
	</div>
	<!-- 补材料弹出页，设置保存和返回按钮，应设置上面的保存返回 -->
</body>
</html>