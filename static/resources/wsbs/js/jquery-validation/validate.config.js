(function ($) {
	$.extend($.validator.defaults, {
		errorClass: "has-error",
		errorElement: "small",
		ignore: "",
		errorPlacement: function(label, element){
			element.attr('title',label.html());
			$(window).scrollTop(0);
		},
		highlight: function( element, errorClass, validClass ) {
			$(element).addClass(errorClass).removeClass(validClass);
		},
		unhighlight: function( element, errorClass, validClass ) {
			$(element).removeClass(errorClass).addClass(validClass);
		}
	});
}(jQuery));