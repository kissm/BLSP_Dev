<%@ taglib prefix="shiro" uri="/WEB-INF/tlds/shiros.tld" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fns" uri="/WEB-INF/tlds/fns.tld" %>
<%@ taglib prefix="fnc" uri="/WEB-INF/tlds/fnc.tld" %>
<%@ taglib prefix="sys" tagdir="/WEB-INF/tags/sys" %>
<%@ taglib prefix="cms" tagdir="/WEB-INF/tags/cms" %>
<c:set var="ctx" value="${pageContext.request.contextPath}${fns:getAdminPath()}"/>
<c:set var="centextPath" value="${pageContext.request.contextPath}" />
<c:set var="ckEditorAndFinder" value="${pageContext.request.contextPath}/static"/>
<c:set var="ctxStatic" value="http://${pageContext.request.serverName}:${pageContext.request.serverPort}/common_plugin"/><!-- 公用静态资源地址 -->
<c:set var="ctxStaticFront" value="http://${pageContext.request.serverName}:${pageContext.request.serverPort}/front_static"/><!-- 前台静态资源地址 -->
<c:set var="ctxStaticBack" value="http://${pageContext.request.serverName}:${pageContext.request.serverPort}/back_static"/><!-- 后台静态资源地址 -->
<%--<c:set var="ctxStaticModern" value="http://${pageContext.request.serverName}/resources"/>--%>
<%--<c:set var="ctxStaticModern" value="http://${pageContext.request.serverName}:${pageContext.request.serverPort}/resources"/>--%>
<c:set var="ctxStaticModern" value="http://${fns:getConfig('nginxIpAndPort')}/resources"/>