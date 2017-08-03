<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta name="decorator" content="blank" />
	<title>珠海市金湾区网上办事大厅</title>
	<script type="text/javascript">

	</script>
</head>
<body>
<form method="post" name="form" id="form" action="${ctx}/project/bizaccept/basic/">
	<input type="hidden" name="type" value="1">
	<input type="hidden" name="prjType" value="2">
	<input type="hidden" name="projectId" value="${project.prjInstanceVo.id}">
	<input type="hidden" name="userName" value="${userName}">
	<table class="editTable" style="width: 960px" border="0" cellspacing="0" cellpadding="0">
		<tbody>
		<tr>
			<td class="title" colspan="2">
				新建企业项目
			</td>
		</tr>
		<tr>
			<td class="title" style="width: 20%;">
				<font style="color:red;">*</font>选择阶段
			</td>
			<td class="content">
				<c:forEach items="${prjStageList}" var="stage" varStatus="i">
					<input type="radio" name="stageId" value="${stage.id}"  <c:if test="${i.index == 0 }">checked</c:if> />${stage.stageName}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</c:forEach>
			</td>
		</tr>

		<tr>
			<td class="content" colspan="2" align="center">
				<input type="submit" class="btnNoimg" value="我要申请" />
			</td>
		</tr>
	</table>
</form>
</body>
</html>