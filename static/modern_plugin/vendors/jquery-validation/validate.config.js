(function ($) {
	$.extend($.validator.defaults, {
		errorClass: "has-error",
		errorElement: "small",
		ignore: "",
		errorPlacement: function(label, element){
			if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
				label.addClass('m-l-20');
				label.appendTo(element.parent());
			} else {
				label.addClass('help-block');
				if(element.parents('.input-group').size()>0){
					label.insertAfter(element.parents('.input-group'));
				}else{
					label.insertAfter(element.parent('.fg-line'));
				}
			}
		},
		highlight: function( element, errorClass, validClass ) {
			$(element).parents('.form-group').addClass(errorClass).removeClass(validClass);
		},
		unhighlight: function( element, errorClass, validClass ) {
			$(element).parents('.form-group').removeClass(errorClass).addClass(validClass);
		}
	});
}(jQuery));