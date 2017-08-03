<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page session="false"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<!DOCTYPE html>
<html>
<head>
	<title><sitemesh:title/></title>
	<%@include file="/WEB-INF/views/include/style.jsp" %>
	<%@include file="/WEB-INF/views/include/libraries.jsp" %>
	<script type="text/javascript">
		function page(n,s){
			$("#pageNo").val(n);
			if(s!=undefined && s !="")
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
		}
	</script>	
	<sitemesh:head/>
</head>
<body class="iframe-body">
	<sitemesh:body/>
</body>
</html>