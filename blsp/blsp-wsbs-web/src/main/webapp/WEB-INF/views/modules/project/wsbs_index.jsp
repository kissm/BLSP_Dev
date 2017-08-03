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
			function acquirePrjInfo(){
				var txtSearchNo = $("#txtSearchNo").val();
				if(txtSearchNo != null && txtSearchNo != ''){
					window.location.href = "${ctx}/schedule/progress?prjCode=" + txtSearchNo;
				}else{
					alert("编号不能为空！");
				}
			}
			function openWindow(url){
				window.open(url,"_blank");
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
			
			<div class="t-b clear">
				<div class="t-i ico1"  onclick="openWindow('http://www.jinwaninvest.cn/sort.asp?lan=zh-cn&skin=2&id=41');">
					<div class="t-i-t">
						投资政策
					</div>
					投资金湾网上指引
				</div>
				<div class="t-i ico2"  onclick="openWindow('http://www.jinwaninvest.cn/sort.asp?lan=zh-cn&skin=1&id=20&open=0&num=100');">
					<div class="t-i-t">
						投资指南
					</div>
					投资类事项办事指南
				</div>
				<div class="t-i ico3"  onclick="openWindow('http://www.jinwaninvest.cn/sort.asp?lan=zh-cn&skin=7&id=7');">
					<div class="t-i-t">
						投资动态
					</div>
					金湾投资新闻、要闻
				</div>
				<div class="t-i ico4 last"  onclick="openWindow('http://ts.jinwan.gov.cn/?flag=4');">
					<div class="t-i-t">
						投资查询 
					</div>
					咨询解答
				</div>
			</div>
			
			<div class="t-c clear">
				<div class="t-c-l"></div>
				<div class="t-c-r">
					<div class="t-c-t">
						并联审批系统
					</div>
					<div class="t-c-c">
						按照中央、省、市关于全面深化改革的总体要求，进一步深化我区行政审批制度改革，改革我区投资建设并联审批机制。<br/><br/>
						以优化办理流程、缩短办理时限，方便群众办事为目标，通过“优化流程、综合受理、统一接件、信息共享、部门联动、全程督办”的方式，全面开展我区投资建设项目综合服务工作，强化责任和监督，提高效率，方便办事群众，服务全区经济社会发展。<br/>
					</div>
				</div>
			</div>
			
			<div class="t-c-b clear">
				<div class="t-b-l ico5" onclick="window.location.href='${ctx}/project/toZf'">
					<div class="t-b-t">
						政府投资项目申请 
					</div>
					政府投资类建设项目
				</div>
				<div class="t-b-r ico6" onclick="window.location.href='${ctx}/project/toQy'">
					<div class="t-b-t">
						企业投资项目申请 
					</div>
					企业投资类建设项目
				</div>
			</div>
		</div>
		
		<div class="chart-search clear">
            <div class="sea-left"></div>
            <div class="sea-center clear">
                <input type="text" id="txtSearchNo" placeholder="请输入项目编号" class="sea-txt">
                <input type="button" id="btnSearch" class="sea-btn" onclick="acquirePrjInfo()">
            </div>
            <div class="sea-right"></div>
            <div class="sea-tips">如已成功申办可在这里查询进度</div>
        </div>
		
	</body>
</html>