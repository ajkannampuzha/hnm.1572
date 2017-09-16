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
		var category=document.getElementById("categoryId").value;
		
		if(text.length ==0 || title.length==0 ||text=="type here"){
			alert("Fill all required fields");
			return false;
		}else{
			//uncomment the below fragment along with code in java for json impl
			/*var jObject={"name":title,"description":text,"cId":category};
			var str=JSON.stringify(jObject);
			alert(str);
			document.getElementById("hId").value=str;
			document.getElementById("titleId").value="nil";
			document.getElementById("txtID").value="nil";
			document.getElementById("categoryId").value="nil";*/
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