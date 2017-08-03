$(document).ready(function() {            
            function getFrameElementId() {
			    var iframes= parent.document.getElementsByTagName('iframe');
			    for (var i= iframes.length; i-->0;) {
			        var iframe= iframes[i];
			        try {
			            var idoc= 'contentDocument' in iframe? iframe.contentDocument : iframe.contentWindow.document;
			        } catch (e) {
			            continue;
			        }
			        if (idoc==document){
			        	return iframe.id.split("_")[1];
			        }
			           
			    }
			    return "";
			}
			
			var frameId = getFrameElementId();
			var flag = 0;
			$(window.parent.document).find('#jerichotab_'+frameId).attr('needSave',"0");
			var valueChanged = function(){
				if(flag == 1) return ;
				flag = 1;
				$(window.parent.document).find('#jerichotab_'+getFrameElementId()).attr('needSave',"1");
			};
			$(window).bind("beforeunload",function(){
				if(flag==1){
				  return '您输入的内容尚未保存，确定离开此页面吗？';
				}
			});
			
			$("input").bind("change",valueChanged);
			$("select").bind("change",valueChanged);
			$("textarea").bind("change",valueChanged);

});
			
			