<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>

<script type="text/javascript">
	function validate(){
		document.getElementById("p1").innerHTML="";
		document.getElementById("p2").innerHTML="";
		var id=document.getElementById("uId").value;
		var pass=document.getElementById("pId").value;
		//alert(id.length);
		if(id.length===0 || pass.length===0 ||isNaN(id)){
			
			if(id.length===0 ){
				document.getElementById("p1").innerHTML="Invalid User Id";
			}
			if(pass.length===0){
				document.getElementById("p2").innerHTML="password empty";
			}
			if(isNaN(id)){
				document.getElementById("p1").innerHTML="userId must be number";
			}
			return false;
		}else{
			return true;
		}
	}
	function changeColor(){
		var input=document.getElementById("uId");
		if(input.value.length===0){
			input.style.backgroundColor="white";
		}else{
			input.style.backgroundColor="gray";
	
		}
	}
</script>
</head>
<body style="background-color: gray;">
<div>
	<div>
		<div>
		<%if(request.getParameter("error")==null){ %>
		<h1 style="background-color: lightblue; ">Login</h1>
		</div>
		<div>
		<%}else{ %>
		<h1 style="background-color: yellow; ">Login Failed</h1>
		<%} %>
		</div>
	</div>
<form action="hnms" onsubmit="return validate();" method="post">

<p id="p1" style="color: red"></p>
UserId:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" id="uId" name="userId" onkeyup="changeColor();">
<p id="p2" style="color:red"></p>
Password:<input type="password" id="pId" name="password"><br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="submit" value="ok">

</form>
<div></div>
</body>
</html>