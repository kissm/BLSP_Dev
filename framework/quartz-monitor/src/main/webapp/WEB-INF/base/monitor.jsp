<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
                                                           
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
        table
        {
            border-collapse: collapse;
            border: none;
            width: 200px;
        }
        td
        {
            border: solid #000 1px;
        }
    </style>
</head>
<body>
<h2>调度监控</h2>
	<table border="">
	<tr>
	<th>组名</th>
	<th>任务名</th>
	<th>cron表达式</th>
	</tr>
	 <c:forEach items="${list}" var="obj" varStatus="var">
	 <tr>
	 <td>
   ${obj.jobGroup}</td>
    <td>
   ${obj.jobName}</td>
   <td>
   ${obj.cronExpression}</td>
   	</tr>
  	</c:forEach>
	</table>
</body>
</html>
