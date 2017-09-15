	function isOneChecked() {
		  
		  var chx = document.getElementsByTagName('input');
		  for (var i=0; i<chx.length; i++) {
		    
		    if (chx[i].type == 'radio' && chx[i].checked) {
		    	document.getElementById("form1").submit();
		    	return true;
		    } 
		  }
		  
		  alert("Please select any Request");
		  return false;
	}
	function validateForm(){
		var title=document.getElementById("titleId").value.trim();
		var text=document.getElementById("txtID").value.trim();
		
		if(text.length ==0 || title.length==0 ){
			alert("Fill all required fields");
			return false;
		}else{
			return true;
		}
	} 
	function isOneChecked() {
		  
		  var chx = document.getElementsByTagName('input');
		  for (var i=0; i<chx.length; i++) {
		    if (chx[i].type == 'radio' && chx[i].checked) {
		    	return true;
		    } 
		  }
		  
		  alert("Please select any Request");
		  return false;
		}
	function checkRadio() {
		  var remarks=document.getElementById("remarkId").value.trim();
		  if(remarks.length == 0){
			  alert("Enter your remarks");
			  return false;
		  }
		  var chx = document.getElementsByTagName('input');
		  for (var i=0; i<chx.length; i++) {
		   
		    if (chx[i].type == 'radio' && chx[i].checked) {
		    	return true;
		    } 
		  }
		 
		  alert("Please select any Request");
		  return false;
		}