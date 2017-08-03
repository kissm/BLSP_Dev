<%@tag import="org.apache.commons.lang3.StringUtils"%>
<%@tag import="java.util.HashMap"%>
<%@tag import="java.util.Map"%>
<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ attribute name="id" type="java.lang.String" required="true" description="编号"%>
<%@ attribute name="fileName" type="java.lang.String" description="隐藏域：文件名字"%>
<%@ attribute name="fileNameValue" type="java.lang.String" description="隐藏域：文件名字的值"%>
<%@ attribute name="fileAddress" type="java.lang.String" description="隐藏域：文件地址"%>
<%@ attribute name="fileAddressValue" type="java.lang.String" description="隐藏域：文件地址的值"%>
<%@ attribute name="downloadFileName" type="java.lang.String" description="文件下载名字"%>
<%@ attribute name="downloadFileAddress" type="java.lang.String" description="文件下载地址"%>
<%@ attribute name="type" type="java.lang.String" description="文件类型：file（文件），img（图片）"%>
<%@ attribute name="accept" type="java.lang.String" description="文件类型限制：例如 	png,doc"%>
<%@ attribute name="isPreview" type="java.lang.String" description="文件是否可预览：true，false"%>
<%@ attribute name="uploadFlag" type="java.lang.String" description="是否可上传：true，false"%>
<%@ attribute name="cssClass" type="java.lang.String" required="false" description="按钮css样式"%>
<%@ attribute name="callFunction" type="java.lang.String" required="false" description="回调事件"%>
<input id="fileName${id}" name="${fileName}" value="${fileNameValue}" type="hidden">
<input id="fileAddress${id}" name="${fileAddress}" value="${fileAddressValue}" type="hidden">
<div id="${id}progress" class="progress progress-striped hide" style="z-index: 10000;width: 30%;position: fixed;top: 50%;bottom: 50%;left: 35%;right: 35%;">
	<div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100"></div>
</div>
<div id="${id}backdrop" class="modal-backdrop fade in hide" style="width: 100%;height: 100%;z-index: 9999"></div>
<c:if test="${empty type}">
	<c:set var="type" value="${empty type?'file':type}"/>
</c:if>
<%
	String acceptHtml = "";
%>
<c:if test="${!empty cssClass}">
	<c:if test="${empty uploadFlag || uploadFlag eq 'true'}">
		<div for="file${id}" data-toggle="tooltip" data-placement="bottom" type="button" title="上传" class="btn btn-icon btn-file m-r-5 ${cssClass}">
			<i class="md md-file-upload"></i>
			<%
				if(StringUtils.isNotBlank(accept)){
					Map<String,String> fileTypeMap = new HashMap<String,String>();
					fileTypeMap.put("png","image/png");
					fileTypeMap.put("jpg","image/jpeg");
					fileTypeMap.put("jpeg","image/jpeg");
					fileTypeMap.put("gif","image/gif");
					fileTypeMap.put("txt","text/plain");
					fileTypeMap.put("doc","application/msword");
					fileTypeMap.put("docx","application/vnd.openxmlformats-officedocument.wordprocessingml.document");
					fileTypeMap.put("pdf","application/pdf");
					fileTypeMap.put("xls","application/vnd.ms-excel");
					fileTypeMap.put("xlsx","application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
					String[] acceptArray = accept.split(",");
					for(String e:acceptArray){
						if(fileTypeMap.containsKey(e)){
							acceptHtml+=fileTypeMap.get(e)+",";
						}
					}
					acceptHtml = acceptHtml.substring(0, acceptHtml.length()-1);
				}
			%>
			<input id="file${id}" type="file" accept="<%=acceptHtml %>">
		</div>
	</c:if>
	<c:if test="${!empty downloadFileAddress}">
		<button data-toggle="tooltip" data-placement="bottom" type="button" title="下载" id="download${id}" onclick="window.open('${ctx}/sys/download?pathUrl=${downloadFileAddress}&coi=${downloadFileName}')" class="btn btn-icon btn-file bgm-lightblue m-r-5">
			<i class="md md-file-download"></i>
		</button>
		<c:if test="${isPreview eq 'true'}">
			<button data-toggle="tooltip" data-placement="bottom" type="button" title="预览" id="view${id}" onclick="window.open('${ctx}/sys/filePreview?pathUrl=${downloadFileAddress}')" class="btn btn-success btn-icon btn-file">
				<i class="md md-visibility"></i>
			</button>
		</c:if>
	</c:if>
	<c:if test="${empty downloadFileAddress}">
		<button data-toggle="tooltip" data-placement="bottom" type="button" title="下载" id="download${id}" class="btn btn-icon btn-file bgm-lightblue m-r-5 hide">
			<i class="md md-file-download"></i>
		</button>
		<c:if test="${isPreview eq 'true'}">
			<button data-toggle="tooltip" data-placement="bottom" type="button" title="预览" id="view${id}" class="btn btn-success btn-icon btn-file hide">
				<i class="md md-visibility"></i>
			</button>
		</c:if>
	</c:if>
</c:if>
<c:if test="${type eq 'file' && empty cssClass}">
	<div class="fileinput fileinput-new" data-provides="fileinput">
	    <span class="btn btn-primary btn-file m-r-10">
	        <span class="fileinput-new">选择文件</span>
	        <span class="fileinput-exists">重选文件</span>
	        <input id="file${id}" type="file">
	    </span>
		<span class="fileinput-filename"></span>
		<a id="close${id}" href="#" class="close fileinput-exists" data-dismiss="fileinput">&times;</a>
	</div>
</c:if>
<c:if test="${type eq 'img' && empty cssClass}">
	<div class="fileinput fileinput-new" data-provides="fileinput">
		<div class="fileinput-preview thumbnail" data-trigger="fileinput">
			<c:if test="${!empty downloadFileAddress}">
				<img alt="img" src="${downloadFileAddress}">
			</c:if>
		</div>
		<div>
	         <span class="btn btn-info btn-file">
	             <span class="fileinput-new">选择图片</span>
	             <span class="fileinput-exists">修改</span>
	              <input id="file${id}" type="file" accept="image/*" >
	         </span>
			<a id="close${id}" href="#" class="btn btn-danger fileinput-exists" data-dismiss="fileinput">清除</a>
		</div>
	</div>
</c:if>
<script type="text/javascript">
	$(document).ready(function() {
		var eleFile = getE("file${id}");
		eleFile.addEventListener("change", function(event) {
			var fileArray = event.target.files;
			if(fileArray.length>0){
				var maxUploadSize = '${fns:getConfig("web.maxUploadSize")}';
				if(fileArray[0].size>maxUploadSize){
					swal("上传失败！","请上传附件小于"+maxUploadSize/1048576+"M", "error");
					$('#close${id}').click();
					$('#file${id}').val('');
					return;
				}
				if('${!empty cssClass}'=='true'&&'${!empty downloadFileAddress}'=='true'){
					swal({
						title: "确定？",
						text: "再次上传将覆盖之前的文件",
						type: "warning",
						showCancelButton: true,
						confirmButtonColor: "#DD6B55",
						cancelButtonText: '取消',
						confirmButtonText: "是的！",
						closeOnConfirm: false,
						closeOnCancel: false
					}, function(isConfirm){
						if (isConfirm) {
							swal({
								title: "正在上传",
								text: "请稍后",
								timer: 100,
								showConfirmButton: false
							});
							uploadFile('${id}',fileArray[0],'${callFunction}','${downloadFileName}');
						} else {
							swal("你已取消上传", "", "info");
							$('#file${id}').val('');
							return;
						}
					});
				}else{
					uploadFile('${id}',fileArray[0],'${callFunction}','${downloadFileName}');
				}
			}
		});
		$('#close${id}').click( function () {
			$('#fileName${id}').val("");
			$('#fileAddress${id}').val("");
		});
	});
</script>