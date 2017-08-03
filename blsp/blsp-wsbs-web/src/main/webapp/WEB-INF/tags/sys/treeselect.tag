<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ attribute name="id" type="java.lang.String" required="true" description="编号"%>
<%@ attribute name="name" type="java.lang.String" required="true" description="隐藏域名称（ID）"%>
<%@ attribute name="value" type="java.lang.String" required="true" description="隐藏域值（ID）"%>
<%@ attribute name="labelName" type="java.lang.String" required="true" description="输入框名称（Name）"%>
<%@ attribute name="labelValue" type="java.lang.String" required="true" description="输入框值（Name）"%>
<%@ attribute name="title" type="java.lang.String" required="true" description="选择框标题"%>
<%@ attribute name="url" type="java.lang.String" required="true" description="树结构数据地址"%>
<%@ attribute name="checked" type="java.lang.Boolean" required="false" description="是否显示复选框，如果不需要返回父节点，请设置notAllowSelectParent为true"%>
<%@ attribute name="extId" type="java.lang.String" required="false" description="排除掉的编号（不能选择的编号）"%>
<%@ attribute name="isAll" type="java.lang.Boolean" required="false" description="是否列出全部数据，设置true则不进行数据权限过滤（目前仅对Office有效）"%>
<%@ attribute name="notAllowSelectRoot" type="java.lang.Boolean" required="false" description="不允许选择根节点"%>
<%@ attribute name="notAllowSelectParent" type="java.lang.Boolean" required="false" description="不允许选择父节点"%>
<%@ attribute name="allowClear" type="java.lang.Boolean" required="false" description="是否允许清除"%>
<%@ attribute name="allowInput" type="java.lang.Boolean" required="false" description="文本框可填写"%>
<%@ attribute name="cssClass" type="java.lang.String" required="false" description="css样式"%>
<%@ attribute name="disabled" type="java.lang.String" required="false" description="是否限制选择，如果限制，设置为disabled"%>
<div class="input-group">
    <span class="input-group-addon"><i class="md md-search"></i></span>
    <div class="dtp-container fg-line ${allowInput?'':'readonly'} ${disabled}">
    	<input id="${id}Id" name="${name}" type="hidden" value="${value}"/>
        <input id="${id}Name" name="${labelName}" ${allowInput?'':'readonly="readonly"'} ${disabled} value="${labelValue}" type="text" class="form-control ${cssClass}" placeholder="单击此处...">
    </div>
</div>
<div class="modal fade" data-modal-color="cyan" id="${id}Modal" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog" aria-hidden="true">
	<div class="modal-dialog modal-sm">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title">选择${title}</h4>
			</div>
			<div style="height:300px;">
				<iframe id="${id}ModalContent" width="100%" height="100%" frameborder="0"></iframe>
			</div>
			<div class="modal-footer">
				<button id="${id}Ok" type="button" class="btn btn-link">确定</button>
				<c:if test="${allowClear}">
					<button id="${id}Clear" type="button" class="btn btn-link">清除</button>
				</c:if>
				<button type="button" class="btn btn-link" data-dismiss="modal">关闭</button>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
$(document).ready(function(){
	$("#${id}Name").click(function(){
		var selectIds  = $("#${id}Id").val();
		$('#${id}ModalContent').attr('src','${ctx}/tag/treeselect?url='+encodeURIComponent('${url}')+'&treeselectId=${id}&selectIds='+selectIds+'&checked=${checked}&extId=${extId}&isAll=${isAll}');
		$('#${id}Modal').modal('show');
	});
	$('#${id}Ok').click(function(){
		${id}SelectNodes();
	});
	$('#${id}Clear').click(function(){
		$("#${id}Id").val("");
		$("#${id}Name").val("");
		$('#${id}Modal').modal('hide');
	});
});
function ${id}SelectNodes(){
	var tree = $('#${id}ModalContent')[0].contentWindow.tree;
	var ids = [], names = [], nodes = [];
	if ('${checked}' == 'true'){
		nodes = tree.getCheckedNodes(true);
	}else{
		nodes = tree.getSelectedNodes();
	}
	for(var i=0; i<nodes.length; i++) {
		if('${checked && notAllowSelectParent}' == 'true'){
			if (nodes[i].isParent){
				continue;
			}
		}
		if('${notAllowSelectRoot}' == 'true'){
			if (nodes[i].level == 0){
				swal("", "不能选择根节点（"+nodes[i].name+"）请重新选择。", "error")
				return false;
			}
		}
		if('${notAllowSelectParent}'=='true'){
			if (nodes[i].isParent){
				swal("", "不能选择根节点（"+nodes[i].name+"）请重新选择。", "error")
				return false;
			}
		}
		ids.push(nodes[i].id);
		names.push(nodes[i].name);
		if('${!checked}'=='true'){
			break;
		}
	}
	$("#${id}Id").val(ids.join(",").replace(/u_/ig,""));
	$("#${id}Name").val(names.join(","));
	$('#${id}Modal').modal('hide');
}
</script>