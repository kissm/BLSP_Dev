<!DOCTYPE html>
    <!--[if IE 9 ]><html class="ie9"><![endif]-->
    <%
	    if (exception != null){
	    	org.slf4j.LoggerFactory.getLogger("500_ERROR").error(exception.getMessage(), exception);
	    }
    %>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
	<title>500 - 系统内部错误</title>
	<%@page import="com.framework.osp.common.web.Servlets"%>
	<%@page contentType="text/html;charset=UTF-8" isErrorPage="true"%>
	<%@include file="/WEB-INF/views/include/taglib.jsp"%>
	<%@include file="/WEB-INF/views/include/style.jsp" %>
</head>
<body class="four-zero-content">
	<div class="four-zero">
	    <h2>SORRY!</h2>
        <small>系统内部错误</small>
	    
	    <footer>
	        <a href="javascript:" onclick="history.go(-1);"><i class="md md-arrow-back"></i></a>
	        <a href="${ctx}"><i class="md md-home"></i></a>
	    </footer>
	</div>
	
<!-- Older IE warning message -->
<!--[if lt IE 9]>
<div class="ie-warning">
    <h1 class="c-white">对不起，不支持低版本IE!</h1>
    <p>您使用的是过时的版本的Internet Explorer,升级到以下任何web浏览器<br/>为了访问这个网站的最大功能。 </p>
    <ul class="iew-download">
        <li>
            <a href="http://www.google.com/chrome/">
                <img src="${ctxStaticModern}/img/browsers/chrome.png" alt="">
                <div>Chrome</div>
            </a>
        </li>
        <li>
            <a href="https://www.mozilla.org/en-US/firefox/new/">
                <img src="${ctxStaticModern}/img/browsers/firefox.png" alt="">
                <div>Firefox</div>
            </a>
        </li>
        <li>
            <a href="http://www.opera.com">
                <img src="${ctxStaticModern}/img/browsers/opera.png" alt="">
                <div>Opera</div>
            </a>
        </li>
        <li>
            <a href="https://www.apple.com/safari/">
                <img src="${ctxStaticModern}/img/browsers/safari.png" alt="">
                <div>Safari</div>
            </a>
        </li>
        <li>
            <a href="http://windows.microsoft.com/en-us/internet-explorer/download-ie">
                <img src="${ctxStaticModern}/img/browsers/ie.png" alt="">
                <div>IE (New)</div>
            </a>
        </li>
    </ul>
    <p>升级您的浏览器来体验更安全、更快的web。 <br/>谢谢你的耐心...</p>
</div>   
<![endif]-->
	
</body>
</html>