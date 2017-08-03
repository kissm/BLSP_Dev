<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
	<head>
	    <title>预览</title>
		<meta name="decorator" content="default"/>
	    <script type="text/javascript">
		    $(document).ready(function(){
		    	progress = $("#progress").clone();
		    	backdrop = $("#backdrop").clone();
		    	progress.removeClass('hide');
		    	backdrop.removeClass('hide');
		    	$('body').append(progress);
		    	$('body').append(backdrop);
		    	timer1 = setInterval("myInterval()",2000);
		    	myInterval();
		    });
		    var timer1;
		    var timer2;
		    var timer3;
		    var progress;
		    var backdrop;
		    var time = 5;
		    var viewUrl;
		    var pathUrl = '${pathUrl}';
		    function myInterval(){
		    	progressBar();
				$.ajax({
				  url: '${ctx}/sys/flag',
				  data: {pathUrl:pathUrl} ,
				  cache: false,
				  success: function(data){
					 if(data.obj=='true'){
						clearInterval(timer1);
						timer2 = setInterval("progressBar()",2000);
						$.ajax({
							url: '${ctx}/sys/convert',
							data: {pathUrl:pathUrl} ,
							cache: false,
							success: function(data){
								clearInterval(timer2);
								if(data.obj=='true'){
									objStateElement.backgroundSize(1,progress,backdrop);
									window.location.href='${ctx}/sys/preview?pathUrl='+pathUrl;
								}else if(data.obj=='false'){
									objStateElement.backgroundSize(1,progress,backdrop);
									window.location.href = pathUrl;
								}else{
									viewUrl = data.obj;
									timer3 = setInterval("flagConvert()",2000);
								}
							}
						});
					 }
				  }
				});
	        }
		    
		    function flagConvert(){
		    	progressBar();
				$.ajax({
					url: '${ctx}/sys/flag',
					data: {pathUrl:viewUrl} ,
					cache: false,
					success: function(data){
						if(data.obj=='true'){
							clearInterval(timer3);
							objStateElement.backgroundSize(1,progress,backdrop);
							window.location.href='${ctx}/sys/preview?pathUrl='+pathUrl;
						}
					}
				});
		    }
		    
		    function progressBar(){
		    	time+=1;
		    	if(time==50){
		    		time=49;
		    	}
		    	objStateElement.backgroundSize((time / 50),progress,backdrop);
		    }
		    
	    </script>
	</head>
	<body>
		<div id="progress" class="progress progress-striped hide" style="z-index: 10000;width: 30%;position: fixed;top: 50%;bottom: 50%;left: 35%;right: 35%;">
      		<div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100"></div>
		</div>
		<div id="backdrop" class="modal-backdrop fade in hide" style="width: 100%;height: 100%;z-index: 9999"></div>
		<form id="searchForm" action="${ctx}/sys/preview" method="post" >
			<input type="hidden" name="pathUrl" value="${pathUrl}">
		</form>
	</body>
</html>