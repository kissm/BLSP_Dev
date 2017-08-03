<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<!DOCTYPE html>
 <!--[if IE 9 ]><html class="ie9"><![endif]-->
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
	<body>
		<%@include file="/WEB-INF/views/include/head.jsp" %>
		<section id="main">
			<%@include file="/WEB-INF/views/include/menu.jsp" %>
			<section id="content">
				<div class="container">
					<sitemesh:body/>
				</div>
			</section>
		</section>
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