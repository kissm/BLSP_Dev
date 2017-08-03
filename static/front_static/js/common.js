//�˳�
function doExit(){
   if (confirm('�Ƿ�ȷ���˳���'))
	{
		window.location.href = 'LoginQyWin.html';
	}
}
function grid(o,a,b,c,d){
	var t=document.getElementById(o).getElementsByTagName("tr");

	for(var i=1;i<t.length;i++){
		
		t[i].style.backgroundColor=(t[i].sectionRowIndex%2==0)?a:b;
		
		t[i].onclick=function(){
			if(this.x!="1"){
				this.x="1";
				this.style.backgroundColor=d;
			}else{
				this.x="0";
				this.style.backgroundColor=(this.sectionRowIndex%2==0)?a:b;
			}
		}

		t[i].onmouseover=function(){
			if(this.x!="1")this.style.backgroundColor=c;
		}
		
		t[i].onmouseout=function(){
			if(this.x!="1")this.style.backgroundColor=(this.sectionRowIndex%2==0)?a:b;
		}
	}
}
function closePopWindow(){
	$('#pop_background').hide();
	$('#popWindow').hide();
}
function popWindow(url){
	$('#pop_background').width('100%');
	$('#pop_background').height(window.document.documentElement.scrollHeight);
	$('#pop_background').show();
	var setTop = Math.round((window.document.documentElement.clientHeight / 2)-(parseInt($('#popWindow').height()) / 2))+'px';
	var setLeft = Math.round((window.document.documentElement.clientWidth / 2)-(parseInt($('#popWindow').width()) / 2))+'px';
	$('#popWindow').css('top',setTop);
	$('#popWindow').css('left',setLeft);
	if(url){
		$('#popWindow iframe').attr('src',url);	
	}
	$('#popWindow').show();	
}

function popWindowById(url,id){
	$('#pop_background').width('100%');
	$('#pop_background').height(window.document.documentElement.scrollHeight);
	$('#pop_background').show();
	var setTop = Math.round((window.document.documentElement.clientHeight / 2)-(parseInt($('#'+id).height()) / 2))+'px';
	var setLeft = Math.round((window.document.documentElement.clientWidth / 2)-(parseInt($('#'+id).width()) / 2))+'px';
	$('#'+id).css('top',setTop);
	$('#'+id).css('left',setLeft);
	if(url){
		$('#'+id+' iframe').attr('src',url);	
	}
	$('#'+id).show();	
}
function closePopWindowById(id){
	$('#pop_background').hide();
	$('#'+id).hide();
}




