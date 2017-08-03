<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<c:forEach items="${materialList}" var="material">
	<tr id="tr${material.materialId}" class="material">
		<td>
			<input type="hidden" name="material" value="${material.materialId}">
			${material.materialId}
		</td>
		<td>
			<input type="hidden" name="materialName" value="${material.materialName}">
			${material.materialName}
		</td>
		<td>
			<div class="fg-line text-center">        
<%-- 				<input type="hidden" id="isOriginal${material.materialId}" name="isOriginal" value="${material.originalNum}" />                                                           
				<label class="checkbox checkbox-inline">
					<input <c:if test='${material.originalNum=="1"}'>checked="checked"</c:if> onclick="javascript:$('#isOriginal${material.materialId}').val($(this).is(':checked')?'1':'0');" type="checkbox" >                                              
					<i class="input-helper"></i>                                                    
				</label>   --%> 
				<input type="number"  min="0" max="999" name="originalNum" value="${empty material.originalNum ? 1 : material.originalNum}" class="form-control input-sm text-center col-xs-1" placeholder="个数">    
			</div>                                                                                  
		</td>                                                                                       
		<td>                                                                                        
			<div class="fg-line">                                                                   
				<input type="number"  min="0" max="999" name="copyNum" value="${material.copyNum}" class="form-control input-sm text-center col-xs-1" placeholder="个数">    
			</div>                                                                                  
		</td>
		<td class="text-center" >
			<input type="hidden" id="isMandatory${material.materialId}" name="isMandatory" value="${material.isMandatory}" /> 
			<input type="hidden" id="isByCondition${material.materialId}" name="isByCondition" value="0" />                                                                     
			<label class="checkbox checkbox-inline">                                                
				<input <c:if test='${material.isMandatory=="1"}'>checked="checked"</c:if> name="isMandatoryCheck" type="checkbox" onclick="javascript:$('#isMandatory${material.materialId}').val($(this).is(':checked')?'1':'0');" >
				<i class="input-helper"></i>                                                        
			</label>                                                                                
		</td>
		<td class="text-center" >    
			<input type="hidden" id="isResultMaterial${material.materialId}" name="isResultMaterial" value="${material.isResultMaterial}" />                                              
			<label class="checkbox checkbox-inline">                                                
				<input <c:if test='${material.isResultMaterial=="1"}'>checked="checked"</c:if> name="isResultMaterialCheck" type="checkbox" onclick="javascript:$('#isResultMaterial${material.materialId}').val($(this).is(':checked')?'1':'0');">
				<i class="input-helper"></i>                                                        
			</label>                                                                                
		</td>
		<td class="text-center">                                                                                        
			<div class="fg-line">  
				<select name="resultTaskId" class="form-control selectpicker selectpicker_4" data-live-search="true">
					<c:forEach items="${resultTaskList}" var="item">
						<option <c:if test="${material.resultTaskId ==item.id}">selected="selected"</c:if> value="${item.id}">${item.taskName}</option>
					</c:forEach>
				</select>                                                                 
			</div>                                                                                  
		</td>
<!-- 		<td class="text-center" >     -->
<%-- 			<input type="hidden" id="isByCondition${material.materialId}" name="isByCondition" value="${material.isByCondition}" />                                               --%>
<!-- 			<label class="checkbox checkbox-inline">                                                 -->
<%-- 				<input <c:if test='${material.isByCondition=="1"}'>checked="checked"</c:if> type="checkbox" onclick="javascript:$('#isByCondition${material.materialId}').val($(this).is(':checked')?'1':'0');">                                 --%>
<!-- 				<i class="input-helper"></i>                                                         -->
<!-- 			</label>                                                                                 -->
<!-- 		</td> -->
<!-- 		<td class="text-center">                                                                                         -->
<!-- 			<div class="fg-line">                                                                    -->
<%-- 				<select id="${material.materialId}" conditionTypeValue="${material.conditionTypeValue}" name="conditionType" class="form-control selectpicker_2" onchange="getConditionTypeValue('${material.materialId}','');"> --%>
<%-- 					<c:forEach items="${fns:getDictList('condition_type')}" var="item"> --%>
<%-- 						<option <c:if test='${material.conditionType==item.value}'>selected="selected"</c:if> value="${item.value}">${item.label}</option> --%>
<%-- 					</c:forEach> --%>
<!-- 				</select>     -->
<!-- 			</div>                                                                                   -->
<!-- 		</td> -->
<!-- 		<td class="text-center">                                                                                         -->
<%-- 			<div class="fg-line" id="conditionTypeValueDev${material.materialId}"> --%>
<!-- 				<select id="conditionTypeValue" name="conditionTypeValue" class="form-control selectpicker_3"> -->
<!-- 				</select>                                                                    -->
<!-- 			</div>                                                                                   -->
<!-- 		</td> -->
		<td class="text-center">                                                                                        
			<div class="fg-line">                                                                   
				<button class="btn btn-icon command-delete" onclick="javascrtpt:removeMaterial('${material.materialId}');" type="button"><span class="md md-delete"></span></button>
			</div>                                                                                  
		</td>                                                                               
	</tr>     
</c:forEach>