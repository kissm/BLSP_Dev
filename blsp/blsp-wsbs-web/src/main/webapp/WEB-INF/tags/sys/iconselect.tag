<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ attribute name="id" type="java.lang.String" required="true" description="编号"%>
<%@ attribute name="name" type="java.lang.String" required="true" description="输入框名称"%>
<%@ attribute name="value" type="java.lang.String" required="true" description="输入框值"%>
<label class="control-label">
	<i id="${id}Icon" class="md ${not empty value?value:'hide'}"></i>
</label>
<label id="${id}IconLabel" class="control-label m-r-20">
	&nbsp;${not empty value?value:'无'}&nbsp;
</label>
<input id="${id}" name="${name}" type="hidden" value="${value}"/>
<button id="${id}Button" type="button" class="btn btn-primary btn-sm waves-effect">选择</button>

<div class="modal fade" data-modal-color="cyan" id="${id}Modal" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog" aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title">选择图标</h4>
			</div>
			<div style="height:400px;">
				<iframe id="${id}ModalContent" width="100%" height="100%" frameborder="0"></iframe>
			</div>
			<div class="modal-footer">
				<button id="${id}Ok" type="button" class="btn btn-link">确定</button>
				<button id="${id}Clear" type="button" class="btn btn-link">清除</button>
				<button type="button" class="btn btn-link" data-dismiss="modal">关闭</button>
			</div>
		</div>
	</div>
</div>


<script type="text/javascript">
	$(document).ready(function(){
		$("#${id}Button").click(function(){
			var value  = $("#${id}").val();
			$('#${id}ModalContent').attr('src','${ctx}/tag/iconselect?value='+value);
			$('#${id}Modal').modal('show');
		});
		$('#${id}Ok').click(function(){
			selectIcon();
		});
		$('#${id}Clear').click(function(){
			 $("#${id}Icon").attr("class", "md hide");
             $("#${id}IconLabel").text("无");
             $("#${id}").val("");
             $('#${id}Modal').modal('hide');
		});
	});
	function selectIcon(){
		var icon = $('#${id}ModalContent')[0].contentWindow.$("#icon").val();
    	$("#${id}Icon").attr("class",'md '+icon);
        $("#${id}IconLabel").text(icon);
        $("#${id}").val(icon);
        $('#${id}Modal').modal('hide');
	}
	
</script>