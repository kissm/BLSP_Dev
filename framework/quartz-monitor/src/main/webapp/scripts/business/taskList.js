function operation(val,row){
	return "<a href=\"javascript:;\" onclick=\"immediateExc("+row.jobId+")\">立刻执行</a>";
}		
function initJobStatus(val,row){
					if(val=="1"){
						return "<a href=\"javascript:;\" onclick=\"changeJobStatus('"+row.jobId+"','stop')\">停止</a>";
					}
					else{
						return "<a href=\"javascript:;\" onclick=\"changeJobStatus('"+row.jobId+"','start')\">开启</a>";
					}
				}
		function initConcurrent(val,row){
			if(val=="1"){
				return "是";
			}
			else{
				return "否";
			}
		}
		
		function shardLine(val,row){
			var str=val;
			var arr=str.split("");
			var temp="";
			for(var i=0;i<arr.length;i++){
				temp+=arr[i];
				if((i+1)%50==0){
				   temp+="<div></div>";
				}
			}
			return temp;
		}
		function initGroup(val,row){
				return groupJson[val];
		}
		function showtaskDetail(jobId){
			$('#detail').window("open");
			/**加载数据  */
		    $('#detailGrid').datagrid({  
		                width: '900px',  
		                height:'auto',               
		                striped: true,  
		                singleSelect : true,  
		                url:basePath+'task/taskDetailList.htm?jobId='+jobId,  
		                //queryParams:{},  
		                loadMsg:'数据加载中请稍后……',  
		                //pagination: true,  
		                rownumbers: true,     
		                columns:[[  
		                    {field:'id',title: '执行明细id',align: 'center'},  
		                    {field:'descCode',title: '响应code',align: 'center'},  
		                    {field:'descMsg',title: '响应信息',align: 'center'}, 
		                    {field:'receiptIp',title: '回执服务器ip',align: 'center'}, 
		                    {field:'receiptParas',title: '回执参数',align: 'center',formatter: function (value, row, index) {
		                    	 if(!domIsNotNull(value)){
		                    		 return '';
		                    	 }
		                    	 else{
		                    		 if(value.length>20){
		                    			 return "<a href=\"javascript:;\" onclick=\"$.messager.show({msg:'"+value+"'});\">"+value.substr(0,20)+"..."+"</a>";
		                    		 }
		                    		 else{
		                    			 return "<a href=\"javascript:;\" onclick=\"$.messager.show({msg:'"+value+"'});\">"+value+"</a>";
		                    		 }
		                    	 }
		                     }}, 
		                    {field:'status',title: '状态',align: 'center',formatter: function (value, row, index) {
		                         return detailStatusJson[value];
		                     }}, 
		                    {field:'createDateStr',title: '创建时间',align: 'center'},  
		                    {field:'updateDateStr',title: '更新时间',align: 'center'}                                                          
		                ]]  
		       });  
		}
		
		function initJobName(val,row){
			return "<a href=\"javascript:;\" onclick=\"showtaskDetail('"+row.jobId+"')\">"+val+"</a>";
		}
		function changeJobStatus(jobId,cmd){
			$.ajax({
				type : "POST",
				async : false,
				dataType : "JSON",
				cache : false,
				url : basePath+"task/changeJobStatus.htm",
				data : {
					jobId : jobId,
					cmd : cmd
				},
				success : function(data) {
					if (data.flag) {
						location.reload();
					} else {
						alert(data.msg);
					}
				}
			});
		}
		
		function immediateExc(jobId,cmd){
			$.ajax({
				type : "POST",
				async : false,
				dataType : "JSON",
				cache : false,
				url : basePath+"task/immediateExc.htm",
				data : {
					jobId : jobId
				},
				success : function(data) {
					if (data.flag) {
						alert("执行完毕");
					}
				}
			});
		}
		function doSearch(){
			//$('#searchForm').form('submit');
			$('#dataGrid').datagrid('load',{
				jobName: $('#jobName').val(),
				jobGroup: $('#jobGroup').combobox('getValue'),
				jobStatus:$('#jobStatus').combobox('getValue')
			});
		}
		
		function initAdd(){
			  $('#win').window("open");   
		}
	
		function submitForm(){
			$('#form1').form('submit', {  
                url:basePath+'task/saveOrUpdateTask.htm',  
                onSubmit: function(){  
                        if($("#form1").form("validate"))  
                            return true  
                        else  
                            return false;  
                    },  
				//注意ajax的url的后台action方法必须有返回值return "json"，而不是return null,否则下面的回调函数不起作用，sucess方法失效  
                success:function(data){  
                           //此处data={"Success":true}实际为字符串，而不是json对象，需要用如下代码处理  
                    var obj = jQuery.parseJSON(data);  
                    if(obj.flag){  
                        $.messager.alert('消息','保存成功！');  
                        $('#dataGrid').datagrid('reload');  
                        $("#form1").form("clear");  
                        location.reload();
                    }else{  
                        $.messager.alert('消息','保存失败！');  
                    }  
                }  
              });  	
		}
		
		function clearForm(){
			$('#form1').form('clear');
		}
		
		function loadJobInfo(){
			var jobIds=$.map($('#dataGrid').datagrid('getChecked'), function(obj){
				return obj["jobId"];
			});
			if(jobIds.length==1){
				$('#form1').form('load',basePath+'task/loadJobInfo.htm?jobId='+jobIds[0]);
				$('#win').window("open");
		    }
			else{
				$.messager.alert('提示','请选中一条记录！');  
			}
		}
		
		function bathDel(){
			var jobIds=$.map($('#dataGrid').datagrid('getChecked'), function(obj){
				return obj["jobId"];
			});
			if(jobIds.length>0){
				$.ajax({
					type : "POST",
					async : false,
					dataType : "JSON",
					cache : false,
					url : basePath+"task/batchDelJob.htm",
					 traditional:true,
					data : {
						"jobIds" :jobIds
					},
					success : function(data) {
						if (data.flag) {
							 $('#dataGrid').datagrid('reload');  
						} else {
							$.messager.alert('提示',data.msg); 
						}

					}//end-callback
				});//end-ajax
			}
			else{
				$.messager.alert('提示','请至少选中一条记录！');  
			}
		}
		
		//预加载
		$(function() {
			
		})