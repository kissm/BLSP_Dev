<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>文章管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		function viewComment(href){
			top.$.jBox.open('iframe:'+href,'查看评论',$(top.document).width()-220,$(top.document).height()-120,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
					$(".nav,.form-actions,[class=btn]", h.find("iframe").contents()).hide();
					$("body", h.find("iframe").contents()).css("margin","10px");
				}
			});
			return false;
		}
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<c:set var="noMobileCategoryFlag" value="${article.category.id ne '120' and article.category.id ne '12010' and article.category.id ne '12020' and article.category.id ne '13010' and not empty article.category.id}"/>
	<c:set var="oldCategoryId" value="${article.category.id}"/>
	<ul class="nav nav-tabs">
		<c:if test="${noMobileCategoryFlag}">
			<li class="active"><a href="${ctx}/cms/article/?category.id=${article.category.id}">文章列表</a></li>
			<shiro:hasPermission name="cms:article:edit"><li><a href="<c:url value='${fns:getAdminPath()}/cms/article/form?id=${article.id}&category.id=${article.category.id}'><c:param name='category.name' value='${article.category.name}'/></c:url>">文章添加</a></li></shiro:hasPermission>
		</c:if>
	</ul>
	<form:form id="searchForm" modelAttribute="article" action="${ctx}/cms/article/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>栏目：</label><sys:treeselect id="category" name="category.id" value="${article.category.id}" labelName="category.name" labelValue="${article.category.name}"
					title="栏目" url="/cms/category/treeData" module="article" notAllowSelectRoot="false" cssClass="input-small"/>
		<label>标题：</label><form:input path="title" htmlEscape="false" maxlength="50" class="input-small"/>&nbsp;
		<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>&nbsp;&nbsp;
		<label>状态：</label><form:radiobuttons onclick="$('#searchForm').submit();" path="delFlag" items="${fns:getDictList('cms_del_flag')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><tr><th>栏目</th><th>标题</th><th>权重</th><th>点击数</th><th>发布者</th><th>更新时间</th><th>操作</th></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="article">
			<tr>
				<td><a href="javascript:" onclick="$('#categoryId').val('${article.category.id}');$('#categoryName').val('${article.category.name}');$('#searchForm').submit();return false;">${article.category.name}</a></td>
				<td>
					<c:if test="${noMobileCategoryFlag}"><a href="${ctx}/cms/article/form?id=${article.id}" title="${article.title}">${fns:abbr(article.title,40)}</a></c:if>
					<c:if test="${not noMobileCategoryFlag}">${fns:abbr(article.title,40)}</c:if>
				</td>
				<td>${article.weight}</td>
				<td>${article.hits}</td>
				<td>${article.user.name}</td>
				<td><fmt:formatDate value="${article.updateDate}" type="both"/></td>
				<td>
					<a href="${ctxFront}?categoryId=${article.category.id}&articleId=${article.id}" target="_blank">访问</a>
					<shiro:hasPermission name="cms:article:edit">
						<%--<c:if test="${article.category.allowComment eq '1'}"><shiro:hasPermission name="cms:comment:view">--%>
							<%--<a href="${ctx}/cms/comment/?module=article&contentId=${article.id}&delFlag=2" onclick="return viewComment(this.href);">评论</a>--%>
						<%--</shiro:hasPermission></c:if>--%>
						<c:if test="${noMobileCategoryFlag}">
	    					<a href="${ctx}/cms/article/form?id=${article.id}">修改</a>
	    				</c:if>
						<c:if test="${noMobileCategoryFlag}">
							<a href="${ctx}/cms/article/delete?id=${article.id}${article.delFlag ne 0?'&isRe=true':''}&categoryId=${article.category.id}" onclick="return confirmx('确认要${article.delFlag ne 0?'':'删除'}该文章吗？', this.href)" >${article.delFlag ne 0?'':'删除'}</a>
						</c:if>
						<c:if test="${not noMobileCategoryFlag}">
							<a href="${ctx}/cms/article/deletePosid?id=${article.id}${article.delFlag ne 0?'&isRe=true':''}&categoryId=${oldCategoryId}" onclick="return confirmx('确认该文章不在此${article.delFlag ne 0?'':'展示'}吗（原栏目文章不会被删除）？', this.href)" >${article.delFlag ne 0?'':'删除'}</a>
						</c:if>
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>