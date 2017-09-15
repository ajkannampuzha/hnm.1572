<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="hnm.beans.*" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>HR Home</title>
<script type="text/javascript">
	function checkRadio1(){
		if(document.getElementById("radio1").checked==true){
			return true;
		}else{
			alert("Select any element");
			return false;
		}
	 }
</script>
<script>
	function checkRadio2(){
		
		if(document.getElementById("radio2").checked==true){
			return true;
		}else{
			alert("Select any element");
			return false;
		}
	 }


</script>

</head>
<body style="background-color: lightblue">
	<center><h2 >HR Home Page</h2></center>
	<h4>EmpId:<%=request.getSession().getAttribute("userId") %></h4>
	<form action="hnms">
		<input type="hidden" name="action" value="logout">
		<h4 align="right"><input type="submit" value="Logout" ></h4>
	</form>
	<center>
		
			<form action="hnms">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="submit"  name="action" value="view all requests">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="submit"  name="action" value="View forwarded requests">	
			</form><br>
		
		
		<form action="hnms" id="form1" onsubmit="return checkRadio1();">
		<%if(request.getAttribute("jspAction")!=null&&((String)request.getAttribute("jspAction")).equals("view")){ %>
			<%List<Request> requests=(ArrayList<Request>)request.getAttribute("requests"); %>
			<%if(requests!=null && !(requests.isEmpty())){ %>
				<table border="1">
				<tr><td>Select</td><td>Ticket.No</td><td>Description</td><td>Status</td><td>Remarks</td></tr>
				<%for(Request r:requests){ %>
					<tr><td><input type="radio" id="radio1" name="requestId" value=<%=r.getrId() %>></td><td><%=r.getrId() %></td><td><%=r.getDescription() %></td><td><%=r.getStatus() %></td><td><%=r.getRemarks() %></td></tr>
				<%} %>
				</table><br>
				
				<input type="submit" name="action" value="View and Handle" >
				
			</form>
			<form action="hnms">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="submit" name="action" value="Show my Pending Requests" >
			</form>
			<%}%>
			
		<%} %>
		<%if(request.getAttribute("jspAction")!=null&&((String)request.getAttribute("jspAction")).equals("handle")){ %>
			<%Request req=(Request)request.getAttribute("request"); %>
			<form action="hnms" id="form2" onsubmit="return checkRadio2();">
		
			<%List<Request> requests=(ArrayList<Request>)request.getAttribute("requests"); %>
			<%if(requests!=null && !(requests.isEmpty())){ %>
				<table border="1">
				<tr><td>Select</td><td>Ticket.No</td><td>Requester ID</td><td>Title</td><td>Description</td><td>Submit Time</td><td>Expiry Time</td><td>Status</td><td>Remarks</td></tr>
				
				<%for(Request r:requests){ %>
					<tr><td><input type="radio" id="radio2" name="rId" value=<%=r.getrId() %>></td><td><%=r.getrId() %></td><td><%=r.geteId() %></td><td><%=r.getName() %></td><td><%=r.getDescription() %></td><td><%=r.getSubmitTime() %></td><td><%=r.getExpiryTime() %></td><td><%=r.getStatus() %></td><td><input type="text" id="remarkId" name="remarks"></td></tr>
				<%} %>
				</table><br>
			<input type="submit" name="action" value="Revert">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="submit" name="action" value="Resolve">
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="submit" name="action" value="Forward">
			</form>
			<%} %>
		<%} %>
	</center>

	<%if(request.getAttribute("error")!=null){ %>
		<h6 style="color: red;">Error:<%=request.getAttribute("error") %></h6>
	<%} %>
</body>
</html>