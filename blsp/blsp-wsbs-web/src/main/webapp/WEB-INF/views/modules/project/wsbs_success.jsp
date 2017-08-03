<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta name="decorator" content="blank" />
	<title>提交成功提示页</title>
	<style type="text/css">
		.submits {
		    line-height: 30px;
		    padding-bottom: 85px;
		    padding-top: 52px;
		    text-align: left;
		    padding-left: 420px;
		    background: url(${ctxStaticResources}/img/success.gif) 320px 50px no-repeat;
		}

	</style>
	<script type="text/javascript">
		$(function (){
			var msg = "${msg}";
			if(msg == "1"){
				parent.blspObj.toPage("${fns:getConfig('wsbs.blsp.list.url')}/f/personal/blsp/list")
				<%--$("#skipConnect").attr("href","${fns:getConfig('wsbs.blsp.list.url')}/f/personal/blsp/list");--%>
				<%--&lt;%&ndash;$("#skipConnect").attr("href","${ctx}/project/list?token=tom");&ndash;%&gt;--%>
				<%--$("#skipConnect").text("返回并联审批列表");--%>
			}
		});
	</script>
	</head>
<body>
	<table id="editTable" class="editTable" style="width: 960px" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td colspan="4" style="height: 100px;border-top: none;border-left: none;border-right: none;text-align: center;font-size: 14px;font-weight: bolder;">
				<c:if test="${project.prjInstanceVo.prjType == '1'}">
					政府网厅项目信息
				</c:if>
				<c:if test="${project.prjInstanceVo.prjType == '2'}">
					企业网厅项目信息
				</c:if>
			</td>
		</tr>
		<tr>
			<td class="title" style="width: 20%;">
				项目名称
			</td>
			<td  style="width: 30%;">
				<input type="text" class="required" maxlength="200" id="prjName" readonly name="prjInstanceVo.prjName" value="${project.prjInstanceVo.prjName}"/>
			</td>
			<td class="title"  style="width: 20%;">
				建设单位
			</td>
			<td>
				<input type="text" class="required" maxlength="200" readonly name="prjInstanceVo.company" value="${project.prjInstanceVo.company}"/>
			</td>
		</tr>
		<tr>
			<td class="title" >
				受委托人 
			</td>
			<td>
				<input type="text" maxlength="50" readonly name="prjInstanceVo.agentName" value="${project.prjInstanceVo.agentName}"/>
			</td>
			<td class="title" >
				手机（受委托人）
			</td>
			<td>
				<input type="text" class="required mobile" maxlength="15" readonly name="prjInstanceVo.agentMphone" value="${project.prjInstanceVo.agentMphone}"/>
			</td>
		</tr>
	</table>
	<table width="1000" border="0" cellpadding="2" cellspacing="1" align="center" class="i-list" style="background: #fff; border-top: none;">
         <tbody><tr>
             <td class="submits">
              		<strong>恭喜！提交成功</strong><br>
                  	<a id="skipConnect" href="${ctx}/project/index?userName=${project.prjInstanceVo.wsbsUserName}">返回并联审批首页</a>
             </td>
         </tr>
     </tbody></table>
</body>
</html>