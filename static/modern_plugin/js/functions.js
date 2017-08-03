/*
 * Bootstrap Growl - Notifications popups
 */ 
function notify(message,type,delay){
	var def = 2500;
	if(typeof(delay)!='undefined'){
		def = delay;
	}
    $.growl({
        message: message
    },{
        type: type,
        allow_dismiss: true,
        label: 'Cancel',
        className: 'btn-xs btn-inverse',
        placement: {
            from: 'top',
            align: 'right'
        },
        delay: def,
        animate: {
                enter: 'animated bounceInRight',
                exit: 'animated bounceOutRight'
        },
        offset: {
            x: 20,
            y: 85
        }
    });
};
$(document).ready(function(){
	/*
	* chart
	*/
	if($('#chart_id')[0]) {
		var chart_h = $('#chart_id').height();
		var max_round_t = chart_h/2-100;
		$('.max-round').css('margin-top',(max_round_t>200?200:max_round_t)+'px');
		var l_h = 0;
		$('.link .l-body').each(function(i){
			if(i==0){
				l_h += $(this).outerHeight()/2+10;
				if(l_h>100){
					$('.link').css('margin-top',l_h+'px');
				}
				$('.l-body').css('top',(-l_h)+'px');
			}else if(i==($('.link .l-body').size()-1)){
				l_h += $(this).outerHeight()/2;
			}else{
				l_h += $(this).outerHeight()+10;
			}
			$(this).find('.mix-round').css('top',($(this).outerHeight()/2-33)+'px');
		});
		$('.mix-round').each(function(i){
			var mix_round = $(this).clone();
			mix_round.find('i').remove();
			var s_name = $.trim(mix_round.html());
			if(s_name.length<3){
				$(this).css('padding-top','13px');
			}
		});
		$('.link').height(l_h);
	}
	
	
	/*
	* Layout
	*/
	(function(){
	   //Get saved layout type from LocalStorage
	   var layoutStatus = localStorage.getItem('ma-layout-status');
	   if (layoutStatus == 1) {
	       $('body').addClass('sw-toggled');
	       $('#tw-switch').prop('checked', true);
	   }
	   
	   $('body').on('change', '#toggle-width input:checkbox', function(){
	       if ($(this).is(':checked')) {
		   $('body').addClass('toggled sw-toggled');
		   localStorage.setItem('ma-layout-status', 1);
	       }
	       else {
		   $('body').removeClass('toggled sw-toggled');
		   localStorage.setItem('ma-layout-status', 0);
	       }
	   });
	})();
	(function(){
		var selectMenu = localStorage.getItem('ma-select-menu');
		$('.main-menu > li').each(function(i){
			if(selectMenu==i){
				$(this).addClass('active');
				$(this).children('a').addClass('active');
				$(this).toggleClass('toggled');
				if(selectMenu<3){
					$('.profile-menu > a').each(function(){
						$(this).parent().toggleClass('toggled');
					    $(this).next().slideToggle(200);
					});
				}
			}
			$(this).click(function() {
				localStorage.setItem('ma-select-menu', i);
			});
		});
		var selectSonLi = localStorage.getItem('ma-select-li');
		$('.main-menu ul li').each(function(i){
			if(selectSonLi==i){
				$(this).children('a').addClass('active');
			}
			$(this).click(function() {
				localStorage.setItem('ma-select-li', i);
			});
		});
	})();
	
    /*
     * Top Search
     */
    (function(){
        $('body').on('click', '#top-search > a', function(e){
            e.preventDefault();
            
            $('#header').addClass('search-toggled');
        });
        
        $('body').on('click', '#top-search-close', function(e){
            e.preventDefault();
            
            $('#header').removeClass('search-toggled');
        });
    })();
    
    /*
     * Sidebar
     */
    (function(){
        //Toggle
        $('body').on('click', '#menu-trigger, #chat-trigger', function(e){            
            e.preventDefault();
            var x = $(this).data('trigger');
	    
            $(x).toggleClass('toggled');
            $(this).toggleClass('open');
	    
    	    //Close opened sub-menus
    	    $('.sub-menu.toggled').not('.active').each(function(){
        		$(this).removeClass('toggled');
        		$(this).find('ul').hide();
    	    });

	    $('.profile-menu .main-menu').hide();
            
            if (x == '#sidebar') {
                $elem = '#sidebar';
                $elem2 = '#menu-trigger';
                
                $('#chat-trigger').removeClass('open');
                
                if (!$('#chat').hasClass('toggled')) {
                    $('#header').toggleClass('sidebar-toggled');
                }
                else {
                    $('#chat').removeClass('toggled');
                }
            }
            
            if (x == '#chat') {
                $elem = '#chat';
                $elem2 = '#chat-trigger';
                
                $('#menu-trigger').removeClass('open');
                
                if (!$('#sidebar').hasClass('toggled')) {
                    $('#header').toggleClass('sidebar-toggled');
                }
                else {
                    $('#sidebar').removeClass('toggled');
                }
            }
            
            //When clicking outside
            if ($('#header').hasClass('sidebar-toggled')) {
                $(document).on('click', function (e) {
                    if (($(e.target).closest($elem).length === 0) && ($(e.target).closest($elem2).length === 0)) {
                        setTimeout(function(){
                            $($elem).removeClass('toggled');
                            $('#header').removeClass('sidebar-toggled');
                            $($elem2).removeClass('open');
                        });
                    }
                });
            }
        })
        
        //Submenu
        $('body').on('click', '.sub-menu > a', function(e){
            e.preventDefault();
            $(this).next().slideToggle(200);
            $(this).parent().toggleClass('toggled');
        });
    })();
    
    /*
     * Clear Notification
     */
    $('body').on('click', '[data-clear="notification"]', function(e){
	e.preventDefault();
    
	var x = $(this).closest('.listview');
	var y = x.find('.lv-item');
	var z = y.size();
	
	$(this).parent().fadeOut();
	
	x.find('.list-group').prepend('<i class="grid-loading hide-it"></i>');
	x.find('.grid-loading').fadeIn(1500);
	
        
	var w = 0;
	y.each(function(){
	    var z = $(this);
	    setTimeout(function(){
		z.addClass('animated fadeOutRightBig').delay(1000).queue(function(){
		    z.remove();
		});
	    }, w+=150);
	})
	
	//Popup empty message
	setTimeout(function(){
	    $('#notifications').addClass('empty');
	}, (z*150)+200);
    });
    
    /*
     * Dropdown Menu
     */
    if($('.dropdown')[0]) {
	//Propagate
	$('body').on('click', '.dropdown.open .dropdown-menu', function(e){
	    e.stopPropagation();
	});
	
	$('.dropdown').on('shown.bs.dropdown', function (e) {
	    if($(this).attr('data-animation')) {
		$animArray = [];
		$animation = $(this).data('animation');
		$animArray = $animation.split(',');
		$animationIn = 'animated '+$animArray[0];
		$animationOut = 'animated '+ $animArray[1];
		$animationDuration = ''
		if(!$animArray[2]) {
		    $animationDuration = 500; //if duration is not defined, default is set to 500ms
		}
		else {
		    $animationDuration = $animArray[2];
		}
		
		$(this).find('.dropdown-menu').removeClass($animationOut)
		$(this).find('.dropdown-menu').addClass($animationIn);
	    }
	});
	
	$('.dropdown').on('hide.bs.dropdown', function (e) {
	    if($(this).attr('data-animation')) {
    		e.preventDefault();
    		$this = $(this);
    		$dropdownMenu = $this.find('.dropdown-menu');
    	
    		$dropdownMenu.addClass($animationOut);
    		setTimeout(function(){
    		    $this.removeClass('open')
    		    
    		}, $animationDuration);
    	    }
    	});
    }
    
    /*
     * Calendar Widget
     */
    if($('#calendar-widget')[0]) {
        (function(){
            $('#calendar-widget').fullCalendar({
		        contentHeight: 'auto',
		        theme: true,
                header: {
                    right: '',
                    center: 'prev, title, next',
                    left: ''
                },
                defaultDate: '2014-06-12',
                editable: true,
                events: [
                    {
                        title: 'All Day',
                        start: '2014-06-01',
                        className: 'bgm-cyan'
                    },
                    {
                        title: 'Long Event',
                        start: '2014-06-07',
                        end: '2014-06-10',
                        className: 'bgm-orange'
                    },
                    {
                        id: 999,
                        title: 'Repeat',
                        start: '2014-06-09',
                        className: 'bgm-lightgreen'
                    },
                    {
                        id: 999,
                        title: 'Repeat',
                        start: '2014-06-16',
                        className: 'bgm-blue'
                    },
                    {
                        title: 'Meet',
                        start: '2014-06-12',
                        end: '2014-06-12',
                        className: 'bgm-teal'
                    },
                    {
                        title: 'Lunch',
                        start: '2014-06-12',
                        className: 'bgm-gray'
                    },
                    {
                        title: 'Birthday',
                        start: '2014-06-13',
                        className: 'bgm-pink'
                    },
                    {
                        title: 'Google',
                        url: 'http://google.com/',
                        start: '2014-06-28',
                        className: 'bgm-bluegray'
                    }
                ]
            });
        })();
    }
    
    /*
     * Weather Widget
     */
    if ($('#weather-widget')[0]) {
        $.simpleWeather({
            location: 'Austin, TX',
            woeid: '',
            unit: 'f',
            success: function(weather) {
                html = '<div class="weather-status">'+weather.temp+'&deg;'+weather.units.temp+'</div>';
                html += '<ul class="weather-info"><li>'+weather.city+', '+weather.region+'</li>';
                html += '<li class="currently">'+weather.currently+'</li></ul>';
                html += '<div class="weather-icon wi-'+weather.code+'"></div>';
                html += '<div class="dash-widget-footer"><div class="weather-list tomorrow">';
                html += '<span class="weather-list-icon wi-'+weather.forecast[2].code+'"></span><span>'+weather.forecast[1].high+'/'+weather.forecast[1].low+'</span><span>'+weather.forecast[1].text+'</span>';
                html += '</div>';
                html += '<div class="weather-list after-tomorrow">';
                html += '<span class="weather-list-icon wi-'+weather.forecast[2].code+'"></span><span>'+weather.forecast[2].high+'/'+weather.forecast[2].low+'</span><span>'+weather.forecast[2].text+'</span>';
                html += '</div></div>';
                $("#weather-widget").html(html);
            },
            error: function(error) {
                $("#weather-widget").html('<p>'+error+'</p>');
            }
        });
    }
    
    /*
     * Todo Add new item
     */
    if ($('#todo-lists')[0]) {
    	//Add Todo Item
    	$('body').on('click', '#add-tl-item .add-new-item', function(){
    	    $(this).parent().addClass('toggled'); 
    	});
            
            //Dismiss
            $('body').on('click', '.add-tl-actions > a', function(e){
                e.preventDefault();
                var x = $(this).closest('#add-tl-item');
                var y = $(this).data('tl-action');
                            
                if (y == "dismiss") {
                    x.find('textarea').val('');
                    x.removeClass('toggled'); 
                }
                
                if (y == "save") {
                    x.find('textarea').val('');
                    x.removeClass('toggled'); 
                }
    	});
    }
    
    /*
     * Auto Hight Textarea
     */
    if ($('.auto-size')[0]) {
	   $('.auto-size').autosize();
    }
    
    /*
     * Custom Scrollbars
     */
    function scrollbar(className, cursorWidth,touchbehavior) {
    	if(typeof(touchbehavior)=='undefined'){
    		touchbehavior = false;
    	}
        $(className).niceScroll({
        	touchbehavior: touchbehavior,
        	cursoropacitymin: 0.2,
        	cursoropacitymax: 0.6,
            cursorcolor: '#000',
            cursorborder: 0,
            cursorborderradius: 0,
            cursorwidth: cursorWidth,
            bouncescroll: true,
            mousescrollstep: 100,
            cursorborderradius: "3px"
        });
    }

    //Scrollbar for HTML but not for login page
    if (!$('.login-content')[0]) {
        scrollbar('html', '6px');
    }
    
    //Scrollbar Tables
    /*
    if ($('.table-responsive')[0]) {
        scrollbar('.table-responsive', 'rgba(0,0,0,0.5)', '5px');
    }
    */
    //Scrill bar for Chosen
    if ($('.chosen-results')[0]) {
        scrollbar('.chosen-results', '5px');
    }
    
    //Scroll bar for tabs
    if ($('.tab-nav')[0]) {
        scrollbar('.tab-nav', '2px');
    }

    //Scroll bar for dropdowm-menu
    if ($('.dropdown-menu .c-overflow')[0]) {
        scrollbar('.dropdown-menu .c-overflow', '0px');
    }

    //Scrollbar for rest
    if ($('.c-overflow')[0]) {
        scrollbar('.c-overflow', '5px');
    }
    
    if ($('.m-overflow')[0]) {
        scrollbar('.m-overflow', '5px',true);
    }
    
    /*
    * Profile Menu
    */
    $('body').on('click', '.profile-menu > a', function(e){
        e.preventDefault();
        $(this).parent().toggleClass('toggled');
	    $(this).next().slideToggle(200);
    });

    /*
     * Text Feild
     */
    
    //Add blue animated border and remove with condition when focus and blur
    if($('.fg-line')[0]) {
        $('body').on('focus', '.form-control', function(){
            $(this).closest('.fg-line').addClass('fg-toggled');
        })

        $('body').on('blur', '.form-control', function(){
            var p = $(this).closest('.form-group');
            var i = p.find('.form-control').val();
            
            if (p.hasClass('fg-float')) {
                if (i.length == 0) {
                    $(this).closest('.fg-line').removeClass('fg-toggled');
                }
            }
            else {
                $(this).closest('.fg-line').removeClass('fg-toggled');
            }
        });
    }
    
    //Add blue border for pre-valued fg-flot text feilds
    if($('.fg-float')[0]) {
        $('.fg-float .form-control').each(function(){
            var i = $(this).val();
            
            if (!i.length == 0) {
                $(this).closest('.fg-line').addClass('fg-toggled');
            }
            
        });
    }
    
    /*
     * Audio and Video
     */
    if($('audio, video')[0]) {
        $('video,audio').mediaelementplayer();
    }
    
    /*
     * Custom Select
     */
    if ($('.selectpickers')[0]) {
	 $('.selecstpicker').selectpicker();
    }
    
    /*
     * Tag Select
     */
    if($('.tag-select')[0]) {
	$('.tag-select').chosen({
	    width: '100%',
	    allow_single_deselect: true
	});
    }
    
    /*
     * Input Slider
     */ 
    //Basic
    if($('.input-slider')[0]) {
	$('.input-slider').each(function(){
	    var isStart = $(this).data('is-start');
	    
	    $(this).noUiSlider({
		start: isStart,
		range: {
			'min': 0,
			'max': 100,
		}
	    });
	});
	//$('.input-slider').Link('lower').to('-inline-<div class="is-tooltip"></div>');
    }
	
    //Range slider
    if($('.input-slider-range')[0]) {
	$('.input-slider-range').noUiSlider({
	    start: [30, 60],
	    range: {
		    'min': 0,
		    'max': 100
	    },
	    connect: true
	});
    }
    
    //Range slider with value
    if($('.input-slider-values')[0]) {
	$('.input-slider-values').noUiSlider({
	    start: [ 45, 80 ],
	    connect: true,
	    direction: 'rtl',
	    behaviour: 'tap-drag',
	    range: {
		    'min': 0,
		    'max': 100
	    }
	});

	$('.input-slider-values').Link('lower').to($('#value-lower'));
	$('.input-slider-values').Link('upper').to($('#value-upper'), 'html');
    }
    
    /*
     * Input Mask
     */
    // looking for inputs with data-mask attribute
    $('*[data-mask]').each(function() {
        var input = $(this),
            options = {};

        if (input.attr('data-mask-reverse') === 'true') {
            options.reverse = true;
        }

        if (input.attr('data-mask-maxlength') === 'false') {
            options.maxlength = false;
        }

        input.mask(input.attr('data-mask'), options);
    });
    
    /*
     * Color Picker
     */
    if ($('.color-picker')[0]) {
	$('.color-picker').each(function(){
	    $('.color-picker').each(function(){
		var colorOutput = $(this).closest('.cp-container').find('.cp-value');
		$(this).farbtastic(colorOutput);
	    });
	});
    }
    
    /*
     * HTML Editor
     */
    if ($('.html-editor')[0]) {
	$('.html-editor').summernote({
	    height: 150
	});
    }
    
    if($('.html-editor-click')[0]) {
	//Edit
	$('body').on('click', '.hec-button', function(){
	    $('.html-editor-click').summernote({
		focus: true
	    });
	    $('.hec-save').show();
	})
	
	//Save
	$('body').on('click', '.hec-save', function(){
	    $('.html-editor-click').code();
	    $('.html-editor-click').destroy();
	    $('.hec-save').hide();
	    notify('Content Saved Successfully!', 'success');
	});
    }
    
    //Air Mode
    if($('.html-editor-airmod')[0]) {
	$('.html-editor-airmod').summernote({
	    airMode: true
	});
    }
    
    /*
     * Date Time Picker
     */
    
    //Date Time Picker
    if ($('.date-time-picker')[0]) {
	   $('.date-time-picker').each(function(){
		   	bindDatetimepicker(this,'YYYY-MM-DD HH:mm:ss');
	   	});
    }
    
    //Time
    if ($('.time-picker')[0]) {
    	$('.time-picker').each(function(){
       		bindDatetimepicker(this,'HH:mm:ss');
    	});
    }
    
    //Date
    if ($('.date-picker')[0]) {
    	$('.date-picker').each(function(){
    		bindDatetimepicker(this,'YYYY-MM-DD');
    	});
    }
    //Year
    if ($('.year-picker')[0]) {
    	$('.year-picker').each(function(){
    		bindDatetimepicker(this,'YYYY');
    	});
    }
    //Month
    if ($('.month-picker')[0]) {
    	$('.month-picker').each(function(){
    		bindDatetimepicker(this,'YYYY-MM');
    	});
    }

    /*
     * Form Wizard
     */
    
    if ($('.form-wizard-basic')[0]) {
    	$('.form-wizard-basic').bootstrapWizard({
    	    tabClass: 'fw-nav',
    	});
    }
    
    //Welcome Message (not for login page)
    /*
    if (!$('.login-content')[0]) {
        notify('欢迎回来', 'inverse');
    }
	*/
    /*
     * Waves Animation
     */
    (function(){
        var wavesList = ['.btn'];

        for(var x = 0; x < wavesList.length; x++) {
            if($(wavesList[x])[0]) {
                if($(wavesList[x]).is('a')) {
                    $(wavesList[x]).not('.btn-icon, input').addClass('waves-effect waves-button');
                }
                else {
                    $(wavesList[x]).not('.btn-icon, input').addClass('waves-effect');
                }
            }
        }

        setTimeout(function(){
            if ($('.waves-effect')[0]) {
               Waves.displayEffect();
            }
        });
    })();
    
    /*
     * Lightbox
     */
    if ($('.lightbox')[0]) {
        $('.lightbox').lightGallery({
            enableTouch: true
        }); 
    }
    
    /*
     * Link prevent
     */
    $('body').on('click', '.a-prevent', function(e){
        e.preventDefault();
    });
    
    /*
     * Collaspe Fix
     */
    if ($('.collapse')[0]) {
        
        //Add active class for opened items
        $('.collapse').on('show.bs.collapse', function (e) {
            $(this).closest('.panel').find('.panel-heading').addClass('active');
        });
   
        $('.collapse').on('hide.bs.collapse', function (e) {
            $(this).closest('.panel').find('.panel-heading').removeClass('active');
        });
        
        //Add active class for pre opened items
        $('.collapse.in').each(function(){
            $(this).closest('.panel').find('.panel-heading').addClass('active');
        });
    }
    
    /*
     * Tooltips
     */
    if ($('[data-toggle="tooltip"]')[0]) {
        $('[data-toggle="tooltip"]').tooltip();
    }
    
    /*
     * Popover
     */
    if ($('[data-toggle="popover"]')[0]) {
        $('[data-toggle="popover"]').popover({html:true});
    }
    
    /*
     * Message
     */

    //Actions
    if ($('.on-select')[0]) {
        var checkboxes = '.lv-avatar-content input:checkbox';
        var actions = $('.on-select').closest('.lv-actions');
    
        $('body').on('click', checkboxes, function() {
            if ($(checkboxes+':checked')[0]) {
                actions.addClass('toggled');
            }
            else {
                actions.removeClass('toggled');
            }
        });
    }

    if($('#ms-menu-trigger')[0]) {
        $('body').on('click', '#ms-menu-trigger', function(e){            
            e.preventDefault();
            $(this).toggleClass('open');
            $('.ms-menu').toggleClass('toggled');
        });
    }
    
    /*
     * Login
     */
    if ($('.login-content')[0]) {
        //Add class to HTML. This is used to center align the logn box
        $('html').addClass('login-content');
        
        $('body').on('click', '.login-navigation > li', function(){
            var z = $(this).data('block');
            var t = $(this).closest('.lc-block');
            
            t.removeClass('toggled');
            
            setTimeout(function(){
                $(z).addClass('toggled');
            });
            
        })
    }
    
    /*
     * Fullscreen Browsing
     */
    if ($('[data-action="fullscreen"]')[0]) {
	var fs = $("[data-action='fullscreen']");
	fs.on('click', function(e) {
	    e.preventDefault();
	    	    
	    //Launch
	    function launchIntoFullscreen(element) {
		
		if(element.requestFullscreen) {
		    element.requestFullscreen();
		} else if(element.mozRequestFullScreen) {
		    element.mozRequestFullScreen();
		} else if(element.webkitRequestFullscreen) {
		    element.webkitRequestFullscreen();
		} else if(element.msRequestFullscreen) {
		    element.msRequestFullscreen();
		}
	    }
	    
	    //Exit
	    function exitFullscreen() {
		
		if(document.exitFullscreen) {
		    document.exitFullscreen();
		} else if(document.mozCancelFullScreen) {
		    document.mozCancelFullScreen();
		} else if(document.webkitExitFullscreen) {
		    document.webkitExitFullscreen();
		}
	    }
	    
	    launchIntoFullscreen(document.documentElement);
	    fs.closest('.dropdown').removeClass('open');
	});
    }
    
    /*
     * Clear Local Storage
     */
    if ($('[data-action="clear-localstorage"]')[0]) {
	var cls = $('[data-action="clear-localstorage"]');
	
	cls.on('click', function(e) {
	    e.preventDefault();
	    
                swal({   
                    title: "你确定吗？",
                    text: "所有本地存储将被删除",   
                    type: "warning",   
                    showCancelButton: true,   
                    confirmButtonColor: "#DD6B55",
                    cancelButtonText: '取消',
                    confirmButtonText: "是的，删除！",
                    closeOnConfirm: false 
                }, function(){
		    localStorage.clear();
                    swal("完成！", "本地存储已被删除", "success"); 
                });
	});
    }
    
    $('.radioBox input[type=radio]').change(function () {
		$('input[name="'+$(this).attr('name')+'"]').parent('.radioBox').removeClass('active');
		$(this).parent('.radioBox').addClass('active');
	} );
    
    if ($('[data-bind]')[0]) {
    	$('[data-bind]').each(function(){
    		if($(this).attr('readonly')!='readonly'){
    			$(this).bind("blur", function(){
					var value = $(this).val();
					$('[data-bind="'+$(this).attr('data-bind')+'"]').each(function(){
						if($(this).attr('readonly')=='readonly'){
							$(this).val(value);
						}
					});
    			});
    		}
    	});
    }
});







//获取URL地址参数
function getQueryString(name, url) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    if (!url || url == ""){
	    url = window.location.search;
    }else{	
    	url = url.substring(url.indexOf("?"));
    }
    r = url.substr(1).match(reg)
    if (r != null) return unescape(r[2]); return null;
}

//获取字典标签
function getDictLabel(data, value, defaultValue){
	for (var i=0; i<data.length; i++){
		var row = data[i];
		if (row.value == value){
			return row.label;
		}
	}
	return defaultValue;
}
//打开一个窗体
function windowOpen(url, name, width, height){
	var top=parseInt((window.screen.height-height)/2,10),left=parseInt((window.screen.width-width)/2,10),
		options="location=no,menubar=no,toolbar=no,dependent=yes,minimizable=no,modal=yes,alwaysRaised=yes,"+
		"resizable=yes,scrollbars=yes,"+"width="+width+",height="+height+",top="+top+",left="+left;
	window.open(url ,name , options);
}
//数值前补零
function pad(num, n) {
    var len = num.toString().length;
    while(len < n) {
        num = "0" + num;
        len++;
    }
    return num;
}

// 转换为日期
function strToDate(date){
	return new Date(date.replace(/-/g,"/"));
}

// 日期加减
function addDate(date, dadd){  
	date = date.valueOf();
	date = date + dadd * 24 * 60 * 60 * 1000;
	return new Date(date);  
}

//截取字符串，区别汉字和英文
function abbr(name, maxLength){  
 if(!maxLength){  
     maxLength = 20;  
 }  
 if(name==null||name.length<1){  
     return "";  
 }  
 var w = 0;//字符串长度，一个汉字长度为2   
 var s = 0;//汉字个数   
 var p = false;//判断字符串当前循环的前一个字符是否为汉字   
 var b = false;//判断字符串当前循环的字符是否为汉字   
 var nameSub;  
 for (var i=0; i<name.length; i++) {  
    if(i>1 && b==false){  
         p = false;  
    }  
    if(i>1 && b==true){  
         p = true;  
    }  
    var c = name.charCodeAt(i);  
    //单字节加1   
    if ((c >= 0x0001 && c <= 0x007e) || (0xff60<=c && c<=0xff9f)) {  
         w++;  
         b = false;  
    }else {  
         w+=2;  
         s++;  
         b = true;  
    }  
    if(w>maxLength && i<=name.length-1){  
         if(b==true && p==true){  
             nameSub = name.substring(0,i-2)+"...";  
         }  
         if(b==false && p==false){  
             nameSub = name.substring(0,i-3)+"...";  
         }  
         if(b==true && p==false){  
             nameSub = name.substring(0,i-2)+"...";  
         }  
         if(p==true){  
             nameSub = name.substring(0,i-2)+"...";  
         }  
         break;  
    }  
 }  
 if(w<=maxLength){  
     return name;  
 }  
 return nameSub;  
}

function getE(id) {
	return document.getElementById(id);
}

var objStateElement = (function() {
	return {
		backgroundSize: function(percent,progress,backdrop) {
			if(percent>0&&percent<1){
				progress.find(".progress-bar").attr("aria-valuenow",percent*100);
				progress.find(".progress-bar").width((percent*100)+"%");
			}else if(percent>=1){
				progress.find(".progress-bar").attr("aria-valuenow",100);
				progress.find(".progress-bar").width("100%");
				setTimeout(function(){
					progress.remove();
					backdrop.remove();
				},1000);
			}
		}
	}
})();

function uploadFile(id,file,callFunction){
	var progress = $("#"+id+"progress").clone();
	var backdrop = $("#"+id+"backdrop").clone();
	var data = new FormData();
	data.append("name", encodeURIComponent(file.name));
	data.append("file", file);
	var xhr = new XMLHttpRequest();
	xhr.open("post", _ctx_ + "/sys/uploadImage", true);
	xhr.setRequestHeader("X_Requested_With", "");
	xhr.timeout = 60000;
	xhr.ontimeout = function(){
	    swal("上传失败！","请求超时", "error");
	}
	xhr.upload.addEventListener("progress", function(e) {
		if(e.lengthComputable){
			objStateElement.backgroundSize((e.loaded / e.total),progress,backdrop);
		}
	}, false);
	xhr.onreadystatechange = function(e) {
		if (xhr.readyState == 4) {
			if (xhr.status == 200) {
				try {
					var json = JSON.parse(xhr.responseText);
					if(json.resCode=="00000000"||json.resCode=="0"){
						$('#fileAddress'+id).val(json.obj[0]);
						
						$('#fileSize'+id).val(file.size);
						$('#fileType'+id).val(file.type);
						
						var fileName = json.obj[1];
						$('#fileName'+id).val(fileName);
						
						$('#download'+id).attr('onClick',"window.open('"+_ctx_+"/sys/download?pathUrl="+json.obj[0]+"&coi="+fileName+"')");
						$('#download'+id).removeClass('hide');
						$('#view'+id).attr('onClick',"window.open('"+_ctx_+"/sys/filePreview?pathUrl="+json.obj[0]+"')");
						$('#view'+id).removeClass('hide');
						
						$('#fileinput-filename'+id).attr('title',fileName+'('+Math.round(file.size/10)/100+'KB)');
						$('#fileinput-filename'+id).attr('data-original-title',fileName+'('+Math.round(file.size/10)/100+'KB)');
						$('#fileinput-filename'+id).html(fileName+'('+Math.round(file.size/10)/100+'KB)');
						$('#close'+id).removeClass('hide');
						
						if(callFunction!=null&&callFunction!=''){
							eval(callFunction);
						}
					}else{
						objStateElement.backgroundSize(1,progress,backdrop);
						swal("上传失败！",json.msg, "error");
					}
				} catch (e) {
					objStateElement.backgroundSize(1,progress,backdrop);
					swal("上传失败！","", "error");
					return;
				} 
			}else{
				objStateElement.backgroundSize(1,progress,backdrop);
				swal("上传失败！","", "error");
			}
		}
	}
	xhr.send(data);
	$('#main',top.window.document).append(progress);
	$('#main',top.window.document).append(backdrop);
	progress.removeClass('hide');
	backdrop.removeClass('hide');
}


function refresh(){
	window.location.href=window.location.href;
}

function clearQuery(){
	$('#clearQuery input[type="text"],#clearQuery input[type="hidden"]').val('');
	$('#clearQuery select').each(function(i){
		$(this).val($(this).find("option:first").val());
	});
	$('#clearQuery select.selectpicker').each(function(i){
		$(this).selectpicker('val',$(this).find("option:first").val());
	});
	$('#clearQuery select.tag-select').each(function(i){
		$(this).trigger("chosen:updated");
	});
	$('#clearQuery :checkbox,#clearQuery :radio').each(function(i){
		$(this).removeAttr("checked");;
	});
}

function bindDateChange(minId,maxId,elem){
	if(minId!=''&&minId!=null){
		$('#'+minId).bind("blur", function() {
			var minVal =  $('#'+minId).val();
			if(minVal!=''&&minVal!=null){
				$(elem).data('DateTimePicker').minDate(minVal);
			}
		});
	}
	if(maxId!=''&&maxId!=null){
		$('#'+maxId).bind("blur", function() {
			var maxVal =  $('#'+maxId).val();
			if(maxVal!=''&&maxVal!=null){
				$(elem).data('DateTimePicker').maxDate(maxVal);
			}
		});
	}
}
function bindDatetimepicker(elem,format){
		var min = $(elem).attr('data-min');
		var max = $(elem).attr('data-max');
		if(min=='now'){
			min = moment().format(format);
		}
		if(max=='now'){
			max = moment().format(format);
		}
		if(min==''){
			min = undefined;
		}
		if(max==''){
			max = undefined;
		}
		$(elem).datetimepicker({
	   	    format: format,
	   	    minDate: min,
	   	    maxDate: max
		});
		var minId = $(elem).attr('data-min-id');
		var maxId = $(elem).attr('data-max-id');
		bindDateChange(minId,maxId,elem);
}
function removeRequired(id){
	$('#'+id +' input[class*="required"],#'+id +' textarea[class*="required"]').each(function(){
		$(this).removeClass('required');
		$(this).attr('data-required','required');
	});
}
function addRequired(id){
	$('#'+id +' input[data-required="required"],#'+id +' textarea[data-required="required"]').each(function(){
		$(this).addClass('required');
	});
}

var popWindow = {};
popWindow.html = 
'<div class="modal fade" data-modal-color="$[color]" id="$[id]" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog" aria-hidden="true">'+
	'<div class="modal-dialog" style="width:$[width];">'+
		'<div class="modal-content">'+
			'<div class="modal-header">'+
				'<h4 class="modal-title">$[title]</h4>'+
			'</div>'+
			'<div style="height:$[height];">'+
				'<iframe id="$[id]Iframe" name="$[id]Iframe" width="100%" height="100%" frameborder="0"></iframe>'+
			'</div>'+
			'<div class="modal-footer">'+
				'$[buttons]'+
				'<button type="button" class="btn btn-link" data-dismiss="modal">关闭</button>'+
			'</div>'+
		'</div>'+
	'</div>'+
'</div>'+
'<form id="$[id]Form" method="POST" action="$[src]" target="$[id]Iframe">'+
'$[input]'+
'</form>';
var newPopWindow = {};
popWindow.flag = false;
popWindow.create = function(option){
	newPopWindow = $.extend(true,{},popWindow,option);
	var html = newPopWindow.html;
	html = html.replace('$[color]',newPopWindow.color?newPopWindow.color:'cyan');
	html = html.replace('$[width]',newPopWindow.width?newPopWindow.width:'600px');
	html = html.replace('$[height]',newPopWindow.height?newPopWindow.height:'300px');
	html = html.replace('$[id]',newPopWindow.id);
	html = html.replace('$[id]',newPopWindow.id);
	html = html.replace('$[id]',newPopWindow.id);
	html = html.replace('$[id]',newPopWindow.id);
	html = html.replace('$[id]',newPopWindow.id);
	html = html.replace('$[title]',newPopWindow.title);
	html = html.replace('$[src]',newPopWindow.url);
	if(newPopWindow.buttons){
		if(newPopWindow.buttons.length>0){
			var buttonHtml = '';
			for(var i=0;i<newPopWindow.buttons.length;i++){
				var button = '<button type="button" class="btn btn-link" onclick="$[onclick]">$[text]</button>';
				button = button.replace('$[text]',newPopWindow.buttons[i].text);
				button = button.replace('$[onclick]','window.'+newPopWindow.id+'Iframe.'+newPopWindow.buttons[i].onclick);
				buttonHtml += button;
			}
			html = html.replace('$[buttons]',buttonHtml);
		}else{
			html = html.replace('$[buttons]','');
		}
	}else{
		html = html.replace('$[buttons]','');
	}
	var inputHtml="";
	for ( var key in newPopWindow.params) {
		inputHtml+='<input type="hidden" name="'+key+'" value="'+newPopWindow.params[key]+'">';
	}
	html = html.replace('$[input]',inputHtml);
	$(window.top.document.body).append(html);
	return newPopWindow;
}
popWindow.open = function(){
	$('#'+this.id+'Form',window.top.document).submit();
	$('#'+this.id,window.top.document).modal('show');
	$('#'+this.id,window.top.document).off('hidden.bs.modal');
	var thisPopWindow = this;
	$('#'+this.id,window.top.document).on('hidden.bs.modal', function () {
		$(this).remove();
		$('#'+thisPopWindow.id+'Form',window.top.document).remove();
		if(thisPopWindow.flag){
			thisPopWindow.flag = false;
			if(thisPopWindow.callBack){
				thisPopWindow.callBack(thisPopWindow.data);
			}
		}else{
			if(thisPopWindow.closeCallBack){
				thisPopWindow.closeCallBack(thisPopWindow.callBackData);
			}
		}
	});
}
popWindow.close = function(){
	this.flag = true;
	$('#'+this.id,window.top.document).modal('hide');
}
popWindowList = [];
function openWindow(option){
	window.top.popWindowList[window.top.popWindowList.length] = popWindow.create(option);
	window.top.popWindowList[window.top.popWindowList.length-1].open();
}
function closeWindow(data){
	window.top.popWindowList[window.top.popWindowList.length-1].data=data;
	window.top.popWindowList[window.top.popWindowList.length-1].close();
	window.top.popWindowList.splice(window.top.popWindowList.length-1,1);
}

function lpcode(sbSeq){
    var data = {};
    data.sbSeq = sbSeq;
    openWindow({
        id:'lpcode',
        title:'龙贝码',
        url:_ctx_+'/lpcode/view',
        params:data,
        width:'400px',
        height:'400px',
        callBack : function(data){}
    });
}

function lpcodeForYy(id){
    var data = {};
    data.id = id;
    openWindow({
        id:'lpcode',
        title:'龙贝码',
        url:_ctx_+'/lpcode/viewForYy',
        params:data,
        width:'400px',
        height:'400px',
        callBack : function(data){}
    });
}



