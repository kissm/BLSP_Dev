<%@ page contentType="text/html;charset=UTF-8" %>
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