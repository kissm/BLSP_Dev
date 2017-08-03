<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>分配角色</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treeview.jsp" %>
	<script type="text/javascript">
	
		var officeTree;
		var selectedTree;//zTree已选择对象
		
		// 初始化
		$(document).ready(function(){
			officeTree = $.fn.zTree.init($("#officeTree"), setting, officeNodes);
			selectedTree = $.fn.zTree.init($("#selectedTree"), setting, selectedNodes);
		});

		var setting = {view: {selectedMulti:false,nameIsHTML:true,showTitle:false,dblClickExpand:false},
				data: {simpleData: {enable: true}},
				callback: {onClick: treeOnClick}};
		
		var officeNodes=[
	            <c:forEach items="${officeList}" var="office">
	            {id:"${office.id}",
	             pId:"${not empty office.parent?office.parent.id:0}", 
	             name:"${office.name}"},
	            </c:forEach>];
	
		var pre_selectedNodes =[
   		        <c:forEach items="${userList}" var="user">
   		        {id:"${user.id}",
   		         pId:"0",
   		         name:"<font color='red' style='font-weight:bold;'>${user.name}</font>"},
   		        </c:forEach>];
		
		var selectedNodes =[
		        <c:forEach items="${userList}" var="user">
		        {id:"${user.id}",
		         pId:"0",
		         name:"<font color='red' style='font-weight:bold;'>${user.name}</font>"},
		        </c:forEach>];
		var ids = [];
		var oldIds = "${selectIds}".split(",");
		//点击选择项回调
		function treeOnClick(event, treeId, treeNode, clickFlag){
			$.fn.zTree.getZTreeObj(treeId).expandNode(treeNode);
			if("officeTree"==treeId){
				$.get("${ctx}/sys/role/users?officeId=" + treeNode.id, function(userNodes){
					$.fn.zTree.init($("#userTree"), setting, userNodes);
				});
			}
			if("userTree"==treeId){
				if($.inArray(String(treeNode.id), ids)<0&&$.inArray(String(treeNode.id), oldIds)<0){
					selectedTree.addNodes(null, treeNode);
					ids.push(String(treeNode.id));
				}
			};
			if("selectedTree"==treeId){
				if($.inArray(String(treeNode.id), oldIds)<0){
					selectedTree.removeNode(treeNode);
					ids.splice($.inArray(String(treeNode.id), ids), 1);
				}else{
					top.window.swal("角色原有成员不能清除！", "", "error");
				}
			}
		};
		function clearAssign(){
			var tips = "";
			if(ids.length==0){
				tips = "未给角色【${role.name}】分配新成员！";
			}else{
				tips = "已选人员清除成功！";
			}
         	ids=oldIds.slice(0);
			selectedNodes=pre_selectedNodes;
			$.fn.zTree.init($("#selectedTree"), setting, selectedNodes);
			return tips;
		};
	</script>
</head>
<body>
	<div class="p-20">
		<div class="row">
		    <div class="col-xs-4 c-overflow" style="height:300px;">
		    	<p class="f-500 c-black">所在部门：</p>
		    	<div id="officeTree" class="ztree"></div>
		    </div>
		    <div class="col-xs-4 c-overflow" style="height:300px;">
		    	<p class="f-500 c-black">待选人员：</p>
		    	<div id="userTree" class="ztree"></div>
		    </div>
		    <div class="col-xs-4 c-overflow" style="height:300px;">
		    	<p class="f-500 c-black">已选人员：</p>
		    	<div id="selectedTree" class="ztree"></div>
		    </div>
	    </div>
	</div>
</body>
</html>
