<%@ page contentType="text/html;charset=UTF-8" %>
<script type="text/javascript">
	//获取通知中消息数
	$(function (){
		var url = "${ctx}/prjTaskTodo/backlog";
		$.post(url,function(data){
				if(data != ""){
					var num = 0;
					var overTime = 0;
					for(i in data){
						num += 1;
						if(data[i].taskRemainTime < 0){
							overTime += 1;
						}
					}
					$("#totalMsg").append('<i class="tmn-counts">'+eval(num+overTime)+'</i>');
					$("#backlog").text(num);
					$("#backlog").css("color","red");
					if(overTime != 0){
						$("#overTime").text(overTime);
						$("#overTime").css("color","red");
					}else{
						$("#overTime").text('0');
						$("#overTime").css("color","blue");
					}
				}else{
					$("#totalMsg").append('');
					$("#backlog").text('0');
					$("#backlog").css("color","blue");
					$("#overTime").text('0');
					$("#overTime").css("color","blue");
				}
			},"json");
	});
</script>
<header id="header">
      <ul class="header-inner">
          <li id="menu-trigger" data-trigger="#sidebar" >
              <div class="line-wrap" title="显示隐藏菜单" data-toggle="tooltip" data-placement="bottom">
                  <div class="line top"></div>
                  <div class="line center"></div>
                  <div class="line bottom"></div>
              </div>
          </li>
      
          <li class="logo hidden-xs">
              <a href="${ctx}">${fns:getConfig('productName')}</a>
          </li>
          
          <li class="pull-right">
          <ul class="top-menu">
          	  <li class="dropdown" title data-toggle="tooltip" data-placement="bottom" data-original-title="通知">
                  <a data-toggle="dropdown" class="tm-notification" href="javaScript:;" id="totalMsg" aria-expanded="true"></a>
                  <div class="dropdown-menu pull-right">
                  	  <div class="listview" id="notifications">
                  	  	  <div id="lv-body" class="lv-body c-overflow" tabindex="0" style="overflow: hidden; outline: none;">
                  	  	  	  <a class="lv-item" href="${ctx}/prjTaskTodo/list">
                                  <div class="media">
                                      <div class="media-body">
                                          <div class="lv-title" style="float: left;">
                                          	    代办提醒
                                          </div>
                                          <small class="tmn-counts" id="backlog" style="display: block;float: right;"></small>
                                      </div>
                                  </div>
                              </a>
                              <a class="lv-item" href="${ctx}/prjTaskTodo/list">
                                  <div class="media">
                                      <div class="media-body">
                                          <div class="lv-title" style="float: left;">
                                          	    超时通知
                                          </div>
                                          <small class="tmn-counts" id="overTime" style="display: block;float: right;"></small>
                                      </div>
                                  </div>
                              </a>
                  	  	  </div>
                  	  </div>
                  </div>
              </li>
              <li id="toggle-width" title="是否隐藏菜单" data-toggle="tooltip" data-placement="bottom">
                  <div class="toggle-switch">
                      <input id="tw-switch" type="checkbox" hidden="hidden">
                      <label for="tw-switch" class="ts-helper"></label>
                  </div>
              </li>
              <li title="退出" data-toggle="tooltip" data-placement="bottom">
                  <a class="md-exit-to-app" href="${ctx}/logout"></a>
              </li>
              <li class="dropdown">
                  <a data-toggle="dropdown" class="tm-settings" href=""></a>
                  <ul class="dropdown-menu dm-icon pull-right">
						<li>
	                    	<a id="fullscreen" data-action="fullscreen" href=""><i class="md md-fullscreen"></i>切换全屏</a>
	                    </li>
	                    <script type="text/javascript">
		                    function IETester(userAgent){
		                        var UA =  userAgent || navigator.userAgent;
		                        if(/msie/i.test(UA)){
		                            return UA.match(/msie (\d+\.\d+)/i)[1];
		                        }else if(~UA.toLowerCase().indexOf('trident') && ~UA.indexOf('rv')){
		                            return UA.match(/rv:(\d+\.\d+)/)[1];
		                        }
		                        return false;
		                    }
		                    if (IETester()){
	                    	    $('#fullscreen').addClass('hide');
	                    	}
                  		</script>
		                <li>
		                    <a data-action="clear-localstorage" href=""><i class="md md-delete"></i>清除本地存储</a>
		                </li>
                  </ul>
              </li>
              </ul>
          </li>
      </ul>
  </header>