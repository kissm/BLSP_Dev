<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>节假日管理</title>
	<meta name="decorator" content="blank"/>
<style type="text/css">
td.content_column{
	width: auto;
}
</style>
<script type="text/javascript">
var yearHolidays = new Array();
var yearHolidaysCopy =  new Array();
$(document).ready(function(){
	var yearHolidaysStr = "<%=request.getAttribute("yearHolidays") %>";
	if(yearHolidaysStr !=null && yearHolidaysStr != "null" && yearHolidaysStr != ''){
		yearHolidays = yearHolidaysStr.split(",");
		yearHolidaysCopy = yearHolidays.slice(0);
	}
	getHtml();//加载本年数据
	$("#yearPeriod option[value='${yearNow}']").attr("selected","selected"); 
	$('#yearPeriod').change( function() {
	 	var date = $(this).val();
		//	$('#minDate').val(date+'-01-01');
	 	//$('#maxDate').val(date+'-12-31');
	 	//$('#yearHoliday').val('');
	 	var flag = true;
	 	if(isChangeArr()){
	 		flag = confirm("有未保存数据，是否放弃保存？");
	 	}
	 	if(flag){//刷新数据
	 		var url = '${ctx}/holidays/edit?yearNow='+date;
			window.location.href = url;
	 	}
	}); 
	//$('#yearHoliday').click(function(){
	//	WdatePicker({onpicked:add,minDate:'#F{$dp.$D(\'minDate\')}',maxDate:'#F{$dp.$D(\'maxDate\')}'});
	//});
	$("#saveButton").click(function(){
		save();
	});
	
	$("#addWeekends").click(function(){
		var year = $('#yearPeriod').val();
		$.ajax({
			url:"${ctx}/holidays/getWeekends",
			type:'POST',
			data:{"yearNow":year},
			dataType:"json",
			success: function(data){
				if(data!=null){
					for(var i=0;i<data.length;i++){
						addHoliday(data[i]);
					}
				}
			}
		});
	});
});	
function save(){
		var yearHolidaysStr = '';
		for(var i=0;i<yearHolidays.length;i++){
			if(i != 0){
				yearHolidaysStr +=',';
			}
			yearHolidaysStr += yearHolidays[i];
		}
		var date = $('#yearPeriod').val();
		$.ajax({
			url:"${ctx}/holidays/addCalendar",
			type:'POST',
			data:{"yearHolidays":yearHolidaysStr},
			dataType:"json",
			success: function(data){
				if(data.info == 'success') {
					alert("保存成功！");
					var url = '${ctx}/holidays/edit?yearNow='+date;
					window.location.href = url;
				}else{
					alert("保存失败，请重试！");
				}
			}
		});
}

function add(){
	var yearHoliday = $("#yearHoliday").val();//新增日期
	yearHoliday = yearHoliday.replace("-","").replace("-","");;
	if(!addHoliday(yearHoliday)){
		alert("日期已存在！");
	}
}
function getHtml(){
			for(var i=0;i<yearHolidays.length;i++){
				if(yearHolidays[i] != ''){
					var month = yearHolidays[i].substring(4,6);//判断日期属于哪一个月份添加到不同的框里面
					var html = $("#selectedData"+month).html()+"<span style='float:left;margin:3px;border:solid 1px #BEBEBE;padding-top: 1px;display:inline-block;width:110px;height:27px;font-size:16px;background-color:white;'>&nbsp;&nbsp;"
					+yearHolidays[i]+
					"<img src='${ctxStaticModern}/img/delete2.png'  style='cursor: pointer;margin-top: -3px;' onclick='del(this,"
					+'"'+yearHolidays[i]+'"'+
					");' width='20' height='20' align='absmiddle'/></span>";
					$("#selectedData"+month).html(html);
				}	
			}
}
function del(obj,val){
	$(obj).parent().remove();
	for(var i=0;i<yearHolidays.length;i++){
		if(val==yearHolidays[i]){
			yearHolidays.splice(i,1);
		}
	}
}
function isChangeArr(){
	if(yearHolidaysCopy.length != yearHolidays.length){
		return true;
	}else{
		for(var i=0;i<yearHolidays.length;i++){
			if(yearHolidaysCopy[i]!= yearHolidays[i]){
				return true;
				break;
			}
		}
	}
	return false;
}
//添加单个日期
function addHoliday(yearHoliday){
	var flag = true;
	if(yearHoliday==''){
		flag=false;
	};
	for(var i=0;i<yearHolidays.length;i++){
		if(yearHoliday==yearHolidays[i]){
			flag=false;
		}
	}
	if(flag){
		yearHolidays.push(yearHoliday);
		var month = yearHoliday.substring(4,6);//判断日期属于哪一个月份添加到不同的框里面
		if($("#selectedData"+month).html==''){
			$("#selectedData"+month).html("<span style='float:left;margin:3px;border:solid 1px #BEBEBE;padding-top: 1px;display:inline-block;width:110px;height:27px;font-size:16px;background-color:white;'>&nbsp;&nbsp;"
			+yearHoliday+
			"<img src='${ctxStaticModern}/img/delete2.png' style='cursor: pointer;margin-top: -3px;' onclick='del(this,"
			+'"'+yearHoliday+'"'+
			");' width='20' height='20' align='absmiddle'/></span>");
		}else{
			var html = $("#selectedData"+month).html()+"<span style='float:left;margin:3px;border:solid 1px #BEBEBE;padding-top: 1px;display:inline-block;width:110px;height:27px;font-size:16px;background-color:white;'>&nbsp;&nbsp;"
			+yearHoliday+
			"<img src='${ctxStaticModern}/img/delete2.png' style='cursor: pointer;margin-top: -3px;' onclick='del(this,"
			+'"'+yearHoliday+'"'+
			");' width='20' height='20' align='absmiddle'/></span>";
			$("#selectedData"+month).html(html);
		}
	}
	return flag;
}
</script>
</head>
<body>

<form  id="saveForm"  action="${ctx}/holidays/save" method="post">
	<div class="card">
	    <div class="card-header">
	        <h2>节假日管理<small>您可通过本功能进行节假日管理</small></h2>  
			<ul class="actions">
				<li>
					<button title="返回" data-toggle="tooltip" data-placement="bottom" type="button" class="btn btn-success btn-icon m-r-5" onclick="javascrtpt:history.go(-1)"><i class="md md-arrow-back"></i></button>
				</li>
				<li>
					<button title="保存"  id="saveButton" data-toggle="tooltip" data-placement="bottom" type="button" class="btn btn-info btn-icon m-r-5" ><i class="md md-save"></i></button>
				</li>
				<li>
					<button data-toggle="tooltip" data-placement="bottom" type="button" title="刷新" class="btn btn-default btn-icon m-r-5" onclick="refresh();"><i class="md md-autorenew"></i></button>
				</li>
			</ul>
	    </div>
		<div class="card-body card-padding">
				<div id="clearQuery" class="row">
					<div class="col-sm-3 form-group">
						<div class="fg-line">
							<label class="control-label">年度：</label>
							    <input type="hidden" id="minDate" value="${yearNow}0101" />
						        <input type="hidden" id="maxDate" value="${yearNow}1231"/>
								<select id="yearPeriod" name="yearPeriod" class="form-control selectpicker">
									<option value="2015">2015</option>
									<option value="2016">2016</option>
									<option value="2017">2017</option>
									<option value="2018">2018</option>
									<option value="2019">2019</option>
									<option value="2020">2020</option>
									<option value="2021">2021</option>
									<option value="2022">2022</option>
									<option value="2023">2023</option>
								</select>   
						</div>
					</div>
					<div class="col-sm-3 form-group">
						<div class="fg-line">
							<label class="control-label">添加节假日：</label>
							<input type="text" id="yearHoliday" name="yearHoliday"
                                   class="form-control date-picker" data-toggle="dropdown"  placeholder="单击此处..."
                                   aria-expanded="false" data-min="${yearNow}-01-01" data-max="${yearNow}-12-31" value="${yearNow==year?day:yearNow}" > 
						</div>
					</div>
					<div class="col-sm-3 form-group">
						<div class="fg-line">
							 <button type="button" class="btn btn-info waves-effect"  id="addWeekends">添加所有双休日</button>
							 <button type="button" class="btn btn-primary waves-effect" onclick="add();" >添加单个日期</button>
						</div>
					</div>
				</div>
		</div>
		<div class="card-body card-padding">
				<div class="row">
				    <table id="contentTable" class="table table-striped table-vmiddle bootgrid-table">
								<tr>
									<td class="title_column col-sm-1"  >一月</td>
									<td class="content_column" >
										<div  id="selectedData01" style='height:70px;overflow:auto;'></div> 
									</td>
								</tr>
								<tr>
									<td class="title_column col-sm-1">二月</td>
									<td class="content_column" >
										<div  id="selectedData02" style='height:70px;overflow:auto;'></div> 
									</td>
								</tr>
								<tr>
									<td class="title_column col-sm-1">三月</td>
									<td class="content_column" >
										<div  id="selectedData03" style='height:70px;overflow:auto;'></div> 
									</td>
								</tr>
								<tr>
									<td class="title_column col-sm-1">四月</td>
									<td class="content_column" >
										<div  id="selectedData04" style='height:70px;overflow:auto;'></div> 
									</td>
								</tr>
								<tr>
									<td class="title_column col-sm-1">五月</td>
									<td class="content_column" >
										<div  id="selectedData05" style='height:70px;overflow:auto;'></div> 
									</td>
								</tr>
								<tr>
									<td class="title_column col-sm-1">六月</td>
									<td class="content_column" >
										<div  id="selectedData06" style='height:70px;overflow:auto;'></div> 
									</td>
								</tr>
								<tr>
									<td class="title_column col-sm-1">七月</td>
									<td class="content_column" >
										<div  id="selectedData07" style='height:70px;overflow:auto;'></div> 
									</td>
								</tr>
								<tr>
									<td class="title_column col-sm-1">八月</td>
									<td class="content_column" >
										<div  id="selectedData08" style='height:70px;overflow:auto;'></div> 
									</td>
								</tr>
								<tr>
									<td class="title_column col-sm-1">九月</td>
									<td class="content_column" >
										<div  id="selectedData09" style='height:70px;overflow:auto;'></div> 
									</td>
								</tr>
								<tr>
									<td class="title_column col-sm-1">十月</td>
									<td class="content_column" >
										<div  id="selectedData10" style='height:70px;overflow:auto;'></div> 
									</td>
								</tr>
								<tr>
									<td class="title_column col-sm-1">十一月</td>
									<td class="content_column" >
										<div  id="selectedData11" style='height:70px;overflow:auto;'></div> 
									</td>
								</tr>
								<tr>
									<td class="title_column col-sm-1">十二月</td>
									<td class="content_column">
										<div  id="selectedData12" style='height:70px;overflow:auto;'></div> 
									</td>
								</tr>
					</table>
				</div>
			</div>
		</div>
	</form>
</body>
</html>