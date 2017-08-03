<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>珠海市金湾区网上办事大厅</title>
        <meta name="decorator" content="blank" />
		<script type="text/javascript">
			$(document).ready(function(){
				$('.tab ul li').click(function(){
					$('.tab ul li.current').removeClass('current');
					var id = $(this).attr('id');
					$('.editTable').hide();
					$('.editTable.'+id).show();
					$(this).addClass('current');
				});
			});

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
			function goQy(){
				if(parent.blspObj.token == null || parent.blspObj.token ==""){
					parent.blspObj.callback("${ctx}/project/bizaccept/basic");
				}else{
					window.location.href = "${ctx}/project/bizaccept/basic?userName="+parent.blspObj.token;
				}
				<%--window.location.href = "${ctx}/project/bizaccept/index?userName=fengjiangong";--%>
			}
			function goAlreadyPrj(){
				parent.blspObj.toPage("${fns:getConfig('wsbs.blsp.list.url')}/f/personal/blsp/list")
			}
			$(function(){
				$('.fold div').click(function(){
					var trId = 	$(this).attr('id');
					if($(this).is('.minus')){
						$('.editTable tbody tr.'+trId).hide();
						$(this).removeClass('minus');
						$(this).addClass('add');
					}else{
						$('.editTable tbody tr.'+trId).show();
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
			<div class="a-c-w"></div>
			<div class="a-t tab">
				申报资料
				<ul>
					<li id="editTable1" class="current">
						立项阶段
					</li>
					<li id="editTable2">
						用地审批阶段
					</li>
					<li id="editTable3">
						规划报建阶段
					</li>
					<li id="editTable4">
						施工许可阶段
					</li>
					<li id="editTable5">
						竣工验收阶段
					</li>
				</ul>
			</div>
			<div class="a-t-c clear">
				<button type="button" class="a-b btn1" onclick="xssj()"></button>
				<button type="button" class="a-b btn2" onclick="window.location.href='${ctx}/project/accpet/downLoadForm'"></button>
			</div>
			<div class="a-t-c clear" style="display: none" id="xssj">
				<table  class="editTable editTable1" style="width: 960px" border="0" cellspacing="0" cellpadding="0">
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
					<c:forEach items="${TaskMaterialDefFormList1}" var="taskMaterialDefForm1" varStatus="status1">
						<tr class="fold" >
							<td colspan="4" class="content">
									${taskMaterialDefForm1.taskName}
								<div class="${status1.index==0?'minus':'add'}"  id="tr${taskMaterialDefForm1.id}"></div>
							</td>
						</tr>
						<c:forEach items="${taskMaterialDefForm1.materialList}" var="material1" varStatus="c1">
							<tr class="tr${taskMaterialDefForm1.id} ${status1.index==0?'':'hidden'}">
								<td align="center" class="content">
										${c1.index+1}
								</td>
								<td align="center">
										${material1.materName}
								</td>
								<td align="center">
									原件${material1.originalNum}份，复印件${material1.copyNum}份
								</td>
							</tr>
						</c:forEach>
					</c:forEach>
					</tbody>
				</table>
				<table  class="editTable editTable2" style="width: 960px;display: none" border="0" cellspacing="0" cellpadding="0">
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
				<c:forEach items="${TaskMaterialDefFormList2}" var="taskMaterialDefForm2" varStatus="status2">
					<tr class="fold" >
						<td colspan="4" class="content">
								${taskMaterialDefForm2.taskName}
							<div class="${status2.index==0?'minus':'add'}"  id="tr${taskMaterialDefForm2.id}"></div>
						</td>
					</tr>
					<c:forEach items="${taskMaterialDefForm2.materialList}" var="material2" varStatus="c2">
						<tr class="tr${taskMaterialDefForm2.id} ${status2.index==0?'':'hidden'}">
							<td align="center" class="content">
									${c2.index+1}
							</td>
							<td align="center">
									${material2.materName}
							</td>
							<td align="center">
								原件${material2.originalNum}份，复印件${material2.copyNum}份
							</td>
						</tr>
					</c:forEach>
				</c:forEach>
				</tbody>
			</table>
				<table  class="editTable editTable3" style="width: 960px;display: none" border="0" cellspacing="0" cellpadding="0">
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
				<c:forEach items="${TaskMaterialDefFormList3}" var="taskMaterialDefForm3" varStatus="status3">
					<tr class="fold" >
						<td colspan="4" class="content">
								${taskMaterialDefForm3.taskName}
							<div class="${status3.index==0?'minus':'add'}"  id="tr${taskMaterialDefForm3.id}"></div>
						</td>
					</tr>
					<c:forEach items="${taskMaterialDefForm3.materialList}" var="material3" varStatus="c3">
						<tr class="tr${taskMaterialDefForm3.id} ${status3.index==0?'':'hidden'}">
							<td align="center" class="content">
									${c3.index+1}
							</td>
							<td align="center">
									${material3.materName}
							</td>
							<td align="center">
								原件${material3.originalNum}份，复印件${material3.copyNum}份
							</td>
						</tr>
					</c:forEach>
				</c:forEach>
				</tbody>
			</table>
				<table  class="editTable editTable4" style="width: 960px;display: none" border="0" cellspacing="0" cellpadding="0">
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
				<c:forEach items="${TaskMaterialDefFormList4}" var="taskMaterialDefForm4" varStatus="status4">
					<tr class="fold" >
						<td colspan="4" class="content">
								${taskMaterialDefForm4.taskName}
							<div class="${status4.index==0?'minus':'add'}"  id="tr${taskMaterialDefForm4.id}"></div>
						</td>
					</tr>
					<c:forEach items="${taskMaterialDefForm4.materialList}" var="material4" varStatus="c4">
						<tr class="tr${taskMaterialDefForm4.id} ${status4.index==0?'':'hidden'}">
							<td align="center" class="content">
									${c4.index+1}
							</td>
							<td align="center">
									${material4.materName}
							</td>
							<td align="center">
								原件${material4.originalNum}份，复印件${material4.copyNum}份
							</td>
						</tr>
					</c:forEach>
				</c:forEach>
				</tbody>
			</table>
				<table  class="editTable editTable5" style="width: 960px;display: none" border="0" cellspacing="0" cellpadding="0">
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
					<c:forEach items="${TaskMaterialDefFormList5}" var="taskMaterialDefForm5" varStatus="status5">
						<tr class="fold" >
							<td colspan="5" class="content">
									${taskMaterialDefForm5.taskName}
								<div class="${status5.index==0?'minus':'add'}"  id="tr${taskMaterialDefForm5.id}"></div>
							</td>
						</tr>
						<c:forEach items="${taskMaterialDefForm5.materialList}" var="material5" varStatus="c5">
							<tr class="tr${taskMaterialDefForm5.id} ${status5.index==0?'':'hidden'}">
								<td align="center" class="content">
										${c5.index+1}
								</td>
								<td align="center">
										${material5.materName}
								</td>
								<td align="center">
									原件${material5.originalNum}份，复印件${material5.copyNum}份
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
				<div class="a-b btn3" onclick="goQy()">
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