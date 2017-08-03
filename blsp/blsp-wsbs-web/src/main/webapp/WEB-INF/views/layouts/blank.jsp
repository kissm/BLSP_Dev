<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<!DOCTYPE html>
	<head>
		<title><sitemesh:title/></title>
		<%@include file="/WEB-INF/views/include/wsbs_style.jsp" %>
		<%@include file="/WEB-INF/views/include/wsbs_libraries.jsp" %>
		<sitemesh:head/>
	</head>
	<body>
		<sitemesh:body/>
	</body>
</html>