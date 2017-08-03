<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>珠海市金湾区网上办事大厅</title>
        <meta name="decorator" content="blank" />
		<script type="text/javascript">
			$(function() {
				$('.investTab').each(function(i){
					$(this).click(function(){
						$('.investTab').removeClass('sel');
						$(this).addClass('sel');
						$('.tzspDiv').hide();
						$($('.tzspDiv')[i]).show();
					});
				});
			});
			function goZf(){
				if(parent.blspObj.token == null || parent.blspObj.token ==""){
					parent.blspObj.callback("${ctx}/project/accpet/index");
				}else{
					window.location.href = "${ctx}/project/accpet/index?userName="+parent.blspObj.token;
				}
				<%--window.location.href = "${ctx}/project/accpet/index?userName=fengjiangong";--%>
			}
			function goAlreadyPrj(){
				parent.blspObj.toPage("${fns:getConfig('wsbs.blsp.list.url')}/f/personal/blsp/list");
			}

			$(function(){
				$('.fold div').click(function(){
					var trId = 	$(this).attr('id');
					if($(this).is('.minus')){
						$('#editTable tbody tr.'+trId).hide();
						$(this).removeClass('minus');
						$(this).addClass('add');
					}else{
						$('#editTable tbody tr.'+trId).show();
						$(this).removeClass('add');
						$(this).addClass('minus');
					}
				});
			});

			function xssj(){
				$("#xssj").toggle();
			}
		</script>
	</head>
	<body>
		<div class="chart-links">
			<div class="t-l">
				投资审批
				<div class="t-r">
					建设工程并联审批
				</div>
			</div>
			<div class="a-t">
				办事流程
			</div>
			<div class="a-c-w2"></div>
			<div class="a-t">
				申报资料
			</div>
			<div class="a-t-c clear">
				<button type="button" class="a-b btn1" onclick="xssj()"></button>
				<button type="button" class="a-b btn2" onclick="window.location.href='${ctx}/project/accpet/downLoadForm'"></button>
			</div>
			<div class="a-t-c clear" style="display: none" id="xssj">
				<table id="editTable" class="editTable" style="width: 960px" border="0" cellspacing="0" cellpadding="0">
					<thead>
						<tr>
							<th class="title" style="width: 10%">
								序号
							</th>
							<th class="title" style="width: 70%">
								材料名称
							</th>
							<th class="title" style="width: 20%">
								需求情况
							</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${TaskMaterialDefFormList}" var="taskMaterialDefForm" varStatus="status">
							<tr class="fold" >
								<td colspan="4" class="content">
										${taskMaterialDefForm.taskName}
									<div class="${status.index==0?'minus':'add'}"  id="tr${status.index}"></div>
								</td>
							</tr>
							<c:forEach items="${taskMaterialDefForm.materialList}" var="material" varStatus="c">
							<tr class="tr${status.index} ${status.index==0?'':'hidden'}">
								<td align="center" class="content">
										${c.index+1}
								</td>
								<td align="center">
										${material.materName}
								</td>
								<td align="center">
									原件${material.originalNum}份，复印件${material.copyNum}份
								</td>
							</tr>
							</c:forEach>
						</c:forEach>
					</tbody>
				</table>
			</div>
			
			<div class="a-t">
				项目申报
			</div>
			<div class="a-t-c clear">
				<div class="a-b btn3" onclick="goZf()">
					<span>
						新项目申报
					</span>
					申报新的建设项目
				</div>
				<div class="a-b btn3" onclick="goAlreadyPrj()">
					<span>
						已申报项目
					</span>
					已申报项目的处理
				</div>
			</div>
			
		</div>
	</body>
</html>