jQuery(function() {
  jQuery('.error').hide();
  jQuery(".butcontact").click(function() {
		// validate and process form
		// first hide any error messages
    jQuery('.error').hide();
		
	  var name = jQuery("input#name").val();
		if (name == "") {
      jQuery("span#name_error").show();
      jQuery("input#name").focus();
      return false;
    }
	
		 var account = jQuery("input#account").val();
		  if (account == "") {
		  jQuery("span#account_error").show();
		  jQuery("input#account").focus();
		  return false;
	}
		  
	  var telno = jQuery("input#telno").val();
	  if (telno == "") {
	      jQuery("span#telno_error").show();
	      jQuery("input#telno").focus();
	      return false;
	  }
	
	 
	  
	var emailReg = /^1\d{10}$/;
	if(!emailReg.test(telno)) {
	jQuery("span#telno_error2").show();
    jQuery("input#telno").focus();
      return false;
	}else{
		  valiDateTelno(telno);
	 }
	
	
	
	});
});

function valiDateTelno(telno) {
	$.ajax( {
		type : "get",
		async:false,
		url : "CheckAgentTelno?telno="+telno,
		dataType:"html",
		success : function(result) {
			if(result=="1"){
				  jQuery("span#telno_error3").show();
			      jQuery("input#telno").focus();
			      return false;
			}else if(result=="0"){
				$("#sb_one").trigger('click');  
			}else{
				//未知错误
				$("span#account_unerror").show();
				return false;
			}
		}
	});
}
