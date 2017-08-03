<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%
	String path = request.getContextPath();
	String rootPath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ "/";
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	request.setAttribute("basePath", basePath);
	request.setAttribute("rootPath", rootPath);
	pageContext.setAttribute("newLineChar", "\n");
%>
<title>job首页清单</title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>scripts/jquery-easyui-1.4.1/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>scripts/jquery-easyui-1.4.1/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>scripts/jquery-easyui-1.4.1/themes/icon.css">
<script type="text/javascript" src="<%=basePath%>scripts/jquery-easyui-1.4.1/jquery.min.js"></script>
<script type="text/javascript" src="<%=basePath%>scripts/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=basePath%>scripts/jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=basePath%>scripts/common.js"></script>
<script type="text/javascript" src="<%=basePath%>scripts/validatebox.js"></script>
<link type="text/css" rel="stylesheet" href="<%=basePath%>/style/common.css" />
<script type="text/javascript">
	var detailStatusJson=${detailStatusMap};
	var groupJson=${groupMap};
	var basePath="<%=basePath%>";
</script>
</head>
<body>
	<table id="dataGrid" class="easyui-datagrid" title="中台统一作业维护" style="width:100%;height:600px;"
			data-options="rownumbers:true,singleSelect:false,url:'<%=basePath%>task/querList.htm',method:'post',toolbar:'#tb',loadMsg:'正在查询中...',fitColumns:true" pagination="true">
		<thead>
			<tr>
				<th field="ck" checkbox="true"></th>
				<th data-options="field:'jobId',width:60">任务id</th>
				<th field="jobName" width="100" formatter="initJobName">任务名</th>
				<th field="jobGroup" width="50" formatter="initGroup">任务组</th>
				<th data-options="field:'description',width:80,align:'right'">描述</th>
				<th data-options="field:'cronExpression',width:100,align:'center'">cronExpression</th>
				<th field="jobStatus" width="20" align="right" formatter="initJobStatus">状态</th>
				<th field="isConcurrent" width="30" align="right" formatter="initConcurrent">是否并发</th>
				<th field="url" width="120" align="right" formatter="shardLine">url路径</th>
				<th field="paras" width="30" align="right" formatter="operation">操作</th>
			</tr>
		</thead>
	</table>
	<div id="tb" style="padding:5px;height:auto">
		<div style="margin-bottom:5px">
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="initAdd()">新增</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="loadJobInfo()">编辑</a>
			<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="bathDel()">删除</a>
		</div>
		<div>
			任务名称: <input id="jobName" class="easyui-textbox" style="width:180px;height:30px">
			任务组名: <select id="jobGroup" class="easyui-combobox" panelHeight="auto" style="width:100px" data-options="valueField: 'id', textField: 'text', url: '<%=basePath%>json/group.json'">
			</select>
			开启状态: 
			<select id="jobStatus" class="easyui-combobox" panelHeight="auto" style="width:100px">
				<option value="">全部</option>
				<option value="1">已开启</option>
				<option value="0">已停止</option>
			</select>
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="doSearch()">查询</a>
		</div>
	</div>
	<div id="win" class="easyui-window" title="添加任务" closed="true" style="width:500px;height:440px;padding:5px;">
   		<form id="form1" style="padding:10px 20px 10px 40px;" method="post">
   		<input name="jobId" type="hidden" />
		<p><span class="cfiled">任务名: </span><input name="jobName" class="easyui-validatebox" style="width:80px;height:20px" data-options="required:'true',validType:'NOCHS'" >
		<span class="cfiled">任务组:</span> 
		<select name="jobGroup" class="easyui-combobox" panelHeight="auto" style="width:100px" data-options="valueField: 'id', textField: 'text', url: '<%=basePath%>json/group.json'">
		</select>
		</p>
		<p>
			<span class="cfiled">状态:</span>  
			<select name="jobStatus" class="easyui-combobox" panelHeight="auto" style="width:100px">
				<option value="1">开启</option>
				<option value="0">关闭</option>
			</select>
			<span class="cfiled">是否并发: </span>
			<select name="isConcurrent" class="easyui-combobox" panelHeight="auto">
				<option value="1">是</option>
				<option value="0">否</option>
			</select>
		</p>
		<p><span class="cfiled">cronExpression: </span> 
		<input name="cronExpression" class="easyui-validatebox" style="width:180px;height:20px" required="true" ></p>
		<p><a href="http://www.bejson.com/cronCreator/" target="_blank">在线Cron表达式生成器</a></p>
		<p><span class="cfiled">url路径: </span> <input name="url"  class="easyui-validatebox" data-options="multiline:true,required:true" style="width:240px;height:60px"></p>
		<p>
			<span class="cfiled">描述:</span>
			<input name="description" class="easyui-textbox" style="width:200px;height:60px" data-options="multiline:true">
		</p>
		<div style="padding:5px;text-align:center;">
			<a href="javascript:void(0)" class="easyui-linkbutton" icon="icon-ok" onclick="submitForm()">保存</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" icon="icon-cancel" onclick="clearForm()">取消</a>
		</div>
	</form> 
  	</div>
  	<div id="detail" class="easyui-window" title="job执行明细(最近50条记录)" closed="true" style="width:1000px;height:440px;padding:5px;">
  		<table id="detailGrid"></table>
  	</div>
  	<script type="text/javascript" src="<%=basePath%>scripts/business/taskList.js"></script>
</body>
</html>