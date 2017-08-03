$(document).ready(function() {
			function getFrameElementId() {
			    var iframes= parent.parent.document.getElementsByTagName('iframe');
			    for (var i= iframes.length; i-->0;) {
			        var iframe= iframes[i];
			        try {
			            var idoc= 'contentDocument' in iframe? iframe.contentDocument : iframe.contentWindow.document;
			        } catch (e) {
			            continue;
			        }
			        if (idoc===parent.document){
			        	return iframe.id.split("_")[1];
			        }
			           
			    }
			    return "";
			}
			
			var frameId = getFrameElementId();
			$(window.parent.parent.document).find('#jerichotab_'+frameId).attr('needSave',"0");
});