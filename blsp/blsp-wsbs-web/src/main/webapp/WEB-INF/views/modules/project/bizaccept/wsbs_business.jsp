<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta name="decorator" content="blank" />
		<title>珠海市金湾区网上办事大厅</title>
		<script type="text/javascript">
			//定义被展开的集合
			var list = [];
			$(function(){
				$('.fold div').click(function(){
					if($(this).is('.minus')){
						var foldId = $(this).parents('tr').attr('id');
						$('#editTable tr.'+foldId).hide();
						$(this).removeClass('minus');
						$(this).addClass('add');
						//去掉一个展开项
						list.splice(foldId,1);
					}else{
						var foldId = $(this).parents('tr').attr('id');
						$('#editTable tr.'+foldId).show();
						$(this).removeClass('add');
						$(this).addClass('minus');
						//增加一个展开项
						list.push(foldId);
					}
					
				});
				//获取展开项，初始化到被展开的集合
				var openList = <%=session.getAttribute("openList")%>;
				for ( i in openList ) {
					list.push(openList[i]);
				}
				removeOpenList();
				//遍历展开
				for(var i=0;i<openList.length;i++){
					$("#"+openList[i]+" div").click();
				}
			});
			function sub(formid,taskId) {
				$("#formCode").val(formid);
				$("#taskId").val(taskId);
				$("#form1").submit();
			}
			//提交审核信息
			function sumCheckInfo(){
				var url = "${ctx}/project/sumCheckInfo";
				var id = '${project.prjInstanceVo.id}';
				$.post(url,{"projectId":id},function (msg){
					if(msg){
						alert("保存成功！");
						window.location.href = "${ctx}/project/succeed?id=${project.prjInstanceVo.id}";
					}else{
						alert("保存失败！");
					}
				},"json");
			}
			//清除session信息
			function removeOpenList(){
				var url = "${ctx}/project/clearSessionInfo";
				$.post(url)
			}
		</script>
	</head>
	<body>
		<form id="form1" action="${ctx}/project/bizaccept/formDetail"
			method="post">
			<input type="hidden" name="formCode" id="formCode"> <input type="hidden"
				name="projectId" id="projectId" value="${project.prjInstanceVo.id}">
			<input type="hidden" name="taskId" id="taskId">
		</form>
		<table id="editTable" class="editTable" style="width: 960px" border="0" cellspacing="0" cellpadding="0">
			<c:forEach items="${tasks}" var="entry">
				<c:set value="${entry.key}" var="key" />
				<c:set value="${entry.value}" var="value" />
				<c:if test="${fn:length(value.formDefineList) eq 0}">
				<tr>
					<tr id="fold_${fns:getPrjTaskDefineVo(key).id}" class="fold" >
						<td colspan="2" class="content">
							${fns:getPrjTaskDefineVo(key).taskName}
						</td>
					</tr>
				</tr>
				</c:if>
				<c:if test="${fn:length(value.formDefineList) ne 0}">
					<tr>
						<tr id="fold_${fns:getPrjTaskDefineVo(key).id}" class="fold" >
							<td colspan="2" class="content">
								${fns:getPrjTaskDefineVo(key).taskName}
								<div class="add"></div>
							</td>
						</tr>
					</tr>
					<tr class="hidden fold_${fns:getPrjTaskDefineVo(key).id}">
						<td class="title" >
							表单名称
						</td>
						<td style="text-align: center;width: 25%;">
							操作
						</td>
					</tr>
					<c:forEach items="${value.formDefineList}" var="form"
						varStatus="status">
						<tr class="hidden fold_${fns:getPrjTaskDefineVo(key).id}">
							<td class="title" >
								${form.formName}
							</td>
							<td align="center">
								<c:if test="${form.prjectId eq null}">
									<div class="uploadify-button" onClick="sub('${form.formCode}','${value.taskDefId}')" style="height: 30px; line-height: 30px; width: 120px; border: 0px; border-radius: 2px;background-image: none; background-color: rgb(61, 158, 233);">
										<span class="uploadify-button-text">填写表单</span>
									</div>
								</c:if>
								<c:if test="${form.prjectId ne null}">
									<div class="uploadify-button" onClick="sub('${form.formCode}','${value.taskDefId}')" style="height: 30px; line-height: 30px; width: 120px; border: 0px; border-radius: 2px;background-image: none; background-color: rgb(61, 158, 233);">
										<span class="uploadify-button-text">更改表单</span>
									</div>
								</c:if>
							</td>
						</tr>
					</c:forEach>
				</c:if>
			</c:forEach>
		</table>
		<table class="editTable" style="width: 960px" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td class="content" colspan="2" align="center">
					<input type="button" class="btnNoimg" value="保存" onclick="sumCheckInfo()">
				</td>
			</tr>
		</table>
	</body>
</html>