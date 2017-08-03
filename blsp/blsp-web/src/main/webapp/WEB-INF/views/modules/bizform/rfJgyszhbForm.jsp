<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
    <title>人防工程竣工验收综合表</title>
    <meta name="decorator" content="blank" />
    <script>
    	$(document).ready(function(){
    		$("#rfjgyszhbForm").validate({
    			rules: {
//    				'entityIdcode':{card:true}
    			},
    			submitHandler: function(form){
  					form.submit();
    			}
    		});
    		var view = "${view}";
	    	if(view){
	    		$("header").remove();
	    		$("input").attr("readonly",true);
	    		$("textarea").attr("disabled",true);
	    		$(".base").each(function (){
	    			if($(this).prop("type") == "checkbox"){
	    				$(this).attr("disabled",true);
	    			}
	    		});
	    		$("button").hide();
	    		$(".actions").append('<li>'+
						 '<button type="button" class="btn btn-info btn-sm" data-toggle= "modal" onClick="createLocpde()">写码</button>'+
    				     '</li>&nbsp;&nbsp;');
	    		if(view != "3"){
		    		$(".actions").append('<li>'+
						'<button type="button"data-toggle= "modal" class="btn btn-success btn-sm" onclick="backItem()">返回</button>'+
						'</li>');
	    		}
	    	}
    	});
    	function saveForm(){
			$(".savebutton").attr("disabled",true);
			if($("#rfjgyszhbForm").valid()){
				$("#rfjgyszhbForm").submit();
			}else{
				$(".savebutton").attr("disabled",false);
			}
    	}
    	function refresh(){
			window.location.reload(true);
		}
    	//返回详情信息
		function backItem(){
			var view = "${view}";
			var prjId = "${formObject.prjId}";
			var taskId = "${formObject.taskId}";
			window.parent.location.href = "${ctx}/project/backItem?prjId="+prjId+"&taskId="+taskId+"&view="+view;
		}
		//生成表单龙贝码
		function createLocpde(){
			var projectId = "${formObject.prjId}";
			var taskDefId = "${formObject.taskId}";
			var formCode = "${formCode}";
			window.open ('${ctx}/project/createFormLpcode?formCode='+formCode+'&taskDefId='+taskDefId+'&prjInstanceVo.id='+projectId,'_blank');
		}
    </script>
</head>
<body>


<div class="card">
    <div class="card-header">
        <h2>人防工程专项竣工验收审核<small>您可通过本功能进行人防工程专项竣工验收审核</small>
        </h2>
        <ul class="actions">
            <li>
                <button data-toggle="tooltip" data-placement="bottom" type="button"
                        title="返回" class="btn btn-success btn-icon m-r-5 savebutton"
                        onclick="javascrtpt:history.go(-1)">
                    <i class="md md-arrow-back"></i>
                </button>
            </li>
            <li>
				<button title="保存" onclick="saveForm();" data-toggle="tooltip" data-placement="bottom" type="button" class="btn btn-info btn-icon m-r-5 savebutton" ><i class="md md-save"></i></button>
			</li>
            <li>
                <button data-toggle="tooltip" data-placement="bottom" type="button"
                        title="刷新" class="btn btn-default btn-icon m-r-5 savebutton"
                        onclick="refresh();">
                    <i class="md md-autorenew"></i>
                </button>
            </li>
        </ul>
    </div>
	<form id="rfjgyszhbForm" action="${ctx}/rfjgyszhb/save" method="post">
	    <div class="card-body card-padding">
	        <div class="row">
	             <input type="hidden" name="id"
	                    value="${formObject.id}"/>
                 <input type="hidden" name="prjId" value="${formObject.prjId}" />
                 <input type="hidden" name="taskId" value="${formObject.taskId}" />
	             <div class="col-xs-4 form-group">
	                 <label class="control-label">报建编号：</label>
	                 <div class="fg-line">
	                     <input type="text" class="form-control "
	                             maxlength="50"
	                            value="${formObject.bjbh}"
	                            name="bjbh" placeholder="报建编号">
	                 </div>
	             </div>
	             <div class="col-xs-4 form-group">
	                 <label class="control-label">工程名称</label>
	                 <div class="fg-line">
	                     <input type="text" class="form-control required"
	                            maxlength="200"
	                            value="${formObject.prjName}"
	                            name="prjName" placeholder="工程名称">
	                 </div>
	             </div>
	             <div class="col-xs-4 form-group">
	                 <label class="control-label">工程地点：</label>
	                 <div class="fg-line">
	                     <input type="text" class="form-control  "
	                            maxlength="50"
	                            value="${formObject.prjAddr}"
	                            name="prjAddr" placeholder="工程地点">
	                 </div>
	             </div>
	             <%-- <div class="col-xs-4 form-group">
	                 <label class="control-label">防空地下室主要出入口处城市坐标及高程：</label>
	                 <div class="fg-line ">
	                     <input type="text" class="form-control "
	                              maxlength="50"
	                            value="${formObject.zbjgc}"
	                            name="zbjgc" placeholder="防空地下室主要出入口处城市坐标及高程">
	                 </div>
	             </div> --%>
	             <%-- 根据代填表单材料在表单内加入新字段 --%>
	             <div class="col-xs-4 form-group">
	                 <label class="control-label">防空地下室主要出入口处城市坐标(X轴)：</label>
	                 <div class="fg-line ">
	                     <input type="text" class="form-control "
	                              maxlength="50"
	                            value="${formObject.basementXPoint}"
	                            name="basementXPoint" placeholder="防空地下室主要出入口处城市坐标(X轴)">
	                 </div>
	             </div>
	             <div class="col-xs-4 form-group">
	                 <label class="control-label">防空地下室主要出入口处城市坐标(Y轴)：</label>
	                 <div class="fg-line ">
	                     <input type="text" class="form-control "
	                              maxlength="50"
	                            value="${formObject.basementYPoint}"
	                            name="basementYPoint" placeholder="防空地下室主要出入口处城市坐标(Y轴)">
	                 </div>
	             </div>
	             <div class="col-xs-4 form-group">
	                 <label class="control-label">防空地下室主要出入口处城市高程：</label>
	                 <div class="fg-line ">
	                     <input type="text" class="form-control "
	                              maxlength="50"
	                            value="${formObject.basementGc}"
	                            name="basementGc" placeholder="防空地下室主要出入口处城市高程">
	                 </div>
	             </div>
	             <div class="col-xs-4 form-group">
	                 <label class="control-label">建设单位：</label>
	                 <div class="fg-line ">
	                     <input type="text" class="form-control "
	                              maxlength="200"
	                            value="${formObject.company}"
	                            name="company" placeholder="建设单位">
	                 </div>
	             </div>
	             
	             <div class="col-xs-4 form-group">
	                 <label class="control-label">建设单位联系人：</label>
	                 <div class="fg-line">
	                     <input type="text" class="form-control  "
	                            maxlength="50"
	                            value="${formObject.linkman}"
	                                      name="linkman" placeholder="建设单位联系人">
	                           </div>
	                       </div>
	                       
	             <div class="col-xs-4 form-group">
	                 <label class="control-label" class="control-label">联系人电话办公电话：</label>
	                 <div class="fg-line">
	                     <input type="text" class="form-control simplePhone "
	                              maxlength="15"
	                            value="${formObject.linkmanOfficephone}"
	                            name="linkmanOfficephone" placeholder="联系人电话办公电话">
	                 </div>
	             </div>
	             <div class="col-xs-4 form-group">
	                 <label class="control-label" class="control-label">联系人电话手机：</label>
	                 <div class="fg-line">
	                     <input type="text" class="form-control  mobile"
	                            maxlength="15"
	                            value="${formObject.linkmanMobilephone}"
	                            name="linkmanMobilephone" placeholder="联系人电话手机">
	                 </div>
	             </div>
	             <div class="col-xs-4 form-group">
	                 <label class="control-label" class="control-label">地面建筑竣工总面积（m2）：</label>
	                 <div class="fg-line">
	                     <input type="text" class="form-control "
	                            maxlength="50"
	                            value="${formObject.dmjzjgzmj}"
	                            name="dmjzjgzmj" placeholder="地面建筑竣工总面积（m2）">
	                 </div>
	             </div>
	             <%-- <div class="col-xs-4 form-group">
	                 <label class="control-label" class="control-label">层数：</label>
	                 <div class="fg-line">
	                     <input type="text" class="form-control "
	                              maxlength="50"
	                            value="${formObject.cs}"
	                            name="cs" placeholder="层数">
	                 </div>
	             </div>
	             <div class="col-xs-4 form-group">
	                 <label class="control-label" class="control-label">栋数：</label>
	                 <div class="fg-line">
	                     <input type="text" class="form-control " 
	                            maxlength="50"
	                            value="${formObject.ds}"
	                            name="ds"
	                            placeholder="栋数">
	                 </div>
	             </div> --%>
	             <%-- 根据代填表单材料在表单内加入新字段 --%>
	             <div class="col-xs-4 form-group">
	                 <label class="control-label" class="control-label">层数A：</label>
	                 <div class="fg-line">
	                     <input type="text" class="form-control "
	                              maxlength="50"
	                            value="${formObject.pliesNumA}"
	                            name="pliesNumA" placeholder="层数A">
	                 </div>
	             </div>
	             <div class="col-xs-4 form-group">
	                 <label class="control-label" class="control-label">栋数A：</label>
	                 <div class="fg-line">
	                     <input type="text" class="form-control "
	                              maxlength="50"
	                            value="${formObject.ridgepoleNumA}"
	                            name="ridgepoleNumA" placeholder="栋数A">
	                 </div>
	             </div>
	             <div class="col-xs-4 form-group">
	                 <label class="control-label" class="control-label">层数B：</label>
	                 <div class="fg-line">
	                     <input type="text" class="form-control "
	                              maxlength="50"
	                            value="${formObject.pliesNumB}"
	                            name="pliesNumB" placeholder="层数B">
	                 </div>
	             </div>
	             <div class="col-xs-4 form-group">
	                 <label class="control-label" class="control-label">栋数B：</label>
	                 <div class="fg-line">
	                     <input type="text" class="form-control "
	                              maxlength="50"
	                            value="${formObject.ridgepoleNumB}"
	                            name="ridgepoleNumB" placeholder="栋数B">
	                 </div>
	             </div>
	             <div class="col-xs-4 form-group">
	                 <label class="control-label" class="control-label">层数C：</label>
	                 <div class="fg-line">
	                     <input type="text" class="form-control "
	                              maxlength="50"
	                            value="${formObject.pliesNumC}"
	                            name="pliesNumC" placeholder="层数C">
	                 </div>
	             </div>
	             <div class="col-xs-4 form-group">
	                 <label class="control-label" class="control-label">栋数C：</label>
	                 <div class="fg-line">
	                     <input type="text" class="form-control "
	                              maxlength="50"
	                            value="${formObject.ridgepoleNumC}"
	                            name="ridgepoleNumC" placeholder="栋数C">
	                 </div>
	             </div>
	             <div class="col-xs-4 form-group">
	                 <label class="control-label" class="control-label">地下建筑竣工总面积（m2）：</label>
	                 <div class="fg-line ">
	                     <input type="text" class="form-control  "
	                              maxlength="50"
	                            value="${formObject.dxjzjgzmj}"
	                            name="dxjzjgzmj" placeholder="地下建筑竣工总面积（m2）">
	                 </div>
	             </div>
	             <div class="col-xs-4 form-group">
	                 <label class="control-label" class="control-label">人防地下室竣工总面积（m2）：</label>
	                 <div class="fg-line ">
	                     <input type="text" class="form-control "
	                              maxlength="50"
	                            value="${formObject.rfdxsjgzmj}"
	                            name="rfdxsjgzmj" placeholder="人防地下室竣工总面积（m2）">
	                 </div>
	             </div>
	             <div class="col-xs-4 form-group">
	                 <label class="control-label" class="control-label">人防地下室平时用途：</label>
	                 <div class="fg-line ">
	                     <input type="text" class="form-control "
	                              maxlength="50"
	                            value="${formObject.rfdxspsyt}"
	                            name="rfdxspsyt" placeholder="人防地下室平时用途">
	                 </div>
	             </div>
	             <div class="col-xs-4 form-group">
	                 <label class="control-label" class="control-label">人防地下室报建面积（m2）：</label>
	                 <div class="fg-line ">
	                     <input type="text" class="form-control "
	                              maxlength="50"
	                            value="${formObject.rfdxsbjmj}"
	                            name="rfdxsbjmj" placeholder="人防地下室报建面积（m2）">
	                 </div>
	             </div>
	             <div class="col-xs-4 form-group">
	                 <label class="control-label" class="control-label">防护单元数：</label>
	                 <div class="fg-line ">
	                     <input type="text" class="form-control "
	                              maxlength="50"
	                            value="${formObject.fhdys}"
	                            name="fhdys" placeholder="防护单元数">
	                 </div>
	             </div>
	             <%-- <div class="col-xs-4 form-group">
	                 <label class="control-label" class="control-label">防护等级：</label>
	                 <div class="fg-line ">
	                     <input type="text" class="form-control "
	                              maxlength="50"
	                            value="${formObject.fhdj}"
	                            name="fhdj" placeholder="防护等级">
	                 </div>
	             </div> --%>
	             <%-- 根据待办表单加入新字段 --%>
	             <div class="col-xs-4 form-group">
	                 <label class="control-label" class="control-label">核级：</label>
	                 <div class="fg-line ">
	                     <input type="text" class="form-control "
	                              maxlength="50"
	                            value="${formObject.nucleus}"
	                            name="nucleus" placeholder="核级">
	                 </div>
	             </div>
	             <div class="col-xs-4 form-group">
	                 <label class="control-label" class="control-label">常级：</label>
	                 <div class="fg-line ">
	                     <input type="text" class="form-control "
	                              maxlength="50"
	                            value="${formObject.often}"
	                            name="often" placeholder="常级">
	                 </div>
	             </div>
	             <div class="col-xs-4 form-group">
	                 <label class="control-label" class="control-label">防护等级：</label>
	                 <div class="fg-line ">
	                     <input type="text" class="form-control "
	                              maxlength="50"
	                            value="${formObject.fence}"
	                            name="fence" placeholder="防护等级">
	                 </div>
	             </div>
	             <div class="col-xs-4 form-group">
	                 <label class="control-label" class="control-label">人防地下室战时用途：</label>
	                 <div class="fg-line ">
	                     <input type="text" class="form-control "
	                              maxlength="50"
	                            value="${formObject.rfdxszsyt}"
	                            name="rfdxszsyt" placeholder="人防地下室战时用途">
	                 </div>
	             </div>
	             <div class="col-xs-4 form-group">
	                 <label class="control-label" class="control-label">二等人员面积：</label>
	                 <div class="fg-line ">
	                     <input type="text" class="form-control "
	                              maxlength="50"
	                            value="${formObject.edry}"
	                            name="edry" placeholder="二等人员面积">
	                 </div>
	             </div>
	             <div class="col-xs-4 form-group">
	                 <label class="control-label" class="control-label">物资库面积：</label>
	                 <div class="fg-line ">
	                     <input type="text" class="form-control "
	                              maxlength="50"
	                            value="${formObject.wzk}"
	                            name="wzk" placeholder="物资库面积">
	                 </div>
	             </div>
	             <div class="col-xs-4 form-group">
	                 <label class="control-label" class="control-label">专业队面积：</label>
	                 <div class="fg-line ">
	                     <input type="text" class="form-control "
	                              maxlength="50"
	                            value="${formObject.zyd}"
	                            name="zyd" placeholder="专业队面积">
	                 </div>
	             </div>
	             <div class="col-xs-4 form-group">
	                 <label class="control-label" class="control-label">平时出入口个数：</label>
	                 <div class="fg-line ">
	                     <input type="text" class="form-control "
	                              maxlength="50"
	                            value="${formObject.pscrkgs}"
	                            name="pscrkgs" placeholder="平时出入口个数">
	                 </div>
	             </div>
	             <div class="col-xs-4 form-group">
	                 <label class="control-label" class="control-label">战时出入口个数：</label>
	                 <div class="fg-line ">
	                     <input type="text" class="form-control "
	                              maxlength="50"
	                            value="${formObject.zscrkgs}"
	                            name="zscrkgs" placeholder="战时出入口个数">
	                 </div>
	             </div>
	             <div class="col-xs-4 form-group">
	                 <label class="control-label" class="control-label">平时通风口个数：</label>
	                 <div class="fg-line ">
	                     <input type="text" class="form-control "
	                              maxlength="50"
	                            value="${formObject.pstfkgs}"
	                            name="pstfkgs" placeholder="平时通风口个数">
	                 </div>
	             </div>
	             <div class="col-xs-4 form-group">
	                 <label class="control-label" class="control-label">战时通风口个数：</label>
	                 <div class="fg-line ">
	                     <input type="text" class="form-control "
	                              maxlength="50"
	                            value="${formObject.zstfkgs}"
	                            name="zstfkgs" placeholder="战时通风口个数">
	                 </div>
	             </div>
	             <div class="col-xs-4 form-group">
	                 <label class="control-label" class="control-label">战时封堵个数：</label>
	                 <div class="fg-line ">
	                     <input type="text" class="form-control "
	                              maxlength="50"
	                            value="${formObject.zsfdgs}"
	                            name="zsfdgs" placeholder="战时封堵个数">
	                 </div>
	             </div>
	             <div class="col-xs-4 form-group">
	                 <label class="control-label" class="control-label">战时封堵面积：</label>
	                 <div class="fg-line ">
	                     <input type="text" class="form-control "
	                              maxlength="50"
	                            value="${formObject.zsfdmj}"
	                            name="zsfdmj" placeholder="战时封堵面积">
	                 </div>
	             </div>
	             
	            <div class="btn-demo text-center col-xs-12">
	                <button data-toggle="modal" onclick="saveForm();"
	                        class="btn btn-primary waves-effect savebutton" type="button">保存</button>
	                <button class="btn btn-default waves-effect savebutton" type="button"
	                        onclick="javascrtpt:history.go(-1)">返回</button>
	            </div>
	        </div>
        </form>
    </div>
</div>
</body>
</html>