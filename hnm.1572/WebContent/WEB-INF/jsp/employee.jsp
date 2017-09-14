<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="hnm.beans.*" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title >Employee Home</title>
<script type="text/javascript">
	
</script>
</head>

<body>
	<center><h2 >Employee Home Page</h2></center>
	<h4>EmpId:<%=request.getSession().getAttribute("userId") %></h4>
	<form action="hnms">
		<input type="hidden" name="action" value="logout">
		<h4 align="right"><input type="submit" value="Logout" ></h4>
	</form>
	<center>
		
			<form action="hnms">
				<input type="submit"  name="action" value="new request" >
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="submit"  name="action" value="view requests">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="submit"  name="action" value="cancelled requests">	
			</form><br>
		
		
		<form action="hnms" >
		<%if(((String)request.getAttribute("jspAction")).equals("view")){ %>
			<%List<Request> requests=(ArrayList<Request>)request.getAttribute("requests"); %>
			<%if(requests!=null && !(requests.isEmpty())){ %>
				<table border="1">
				<tr><td>Select</td><td><a href="hnms?action=sort&sort=rId">Ticket.No</a></td><td>Description</td><td><a href="hnms?action=sort&sort=submit">Submit Time</a></td><td><a href="hnms?action=sort&sort=expiry">Expiry Time</a></td><td><a href="hnms?action=sort&sort=status">Status</a></td><td>Remarks</td></tr>
				<%for(Request r:requests){ %>
					<tr><td><input type="radio" name="requestId" value=<%=r.getrId() %>></td><td><%=r.getrId() %></td><td><%=r.getName()%></td><td><%=r.getDescription() %></td><td><%=r.getSubmitTime() %></td><td><%=r.getExpiryTime()%></td><td><%=r.getStatus() %></td><td><%=r.getRemarks() %></td></tr>
				<%} %>
				</table><br>
				<input type="hidden" name="action" value="cancel">
				<input type="submit" value="Cancel Request" >
			</form>
			<%}%>
			
		<%} %>
		<%if(((String)request.getAttribute("jspAction")).equals("new")){ %>
			<form action="hnms">
			<input type="hidden" name="action" value="add request">
			<table >
			<tr><td>Category</td><td><select id="categoryId" name="category">
				<%if(request.getSession().getAttribute("categories")!=null){ %>
					<%List<Category> categories=(ArrayList<Category>)request.getSession().getAttribute("categories"); %>
					<%for(Category c:categories){ %>
						<option value=<%=c.getcId() %>><%=c.getName() %></option>
					<%} %>
				
				<%} %>
			</select></td></tr>
			<tr><td>Title</td><td><input type="text" name="title"></td></tr>
			<tr><td>Description</td><td><textarea rows="5" cols="20" id="txtID" name="description">type here</textarea></td></tr>
			</table><br>
			<input type="submit" value="submit request">
			</form>
		<%} %>
	</center>

	<%if(request.getAttribute("error")!=null){ %>
		<h6 style="color: red;">Error:<%=request.getAttribute("error") %></h6>
	<%} %>
</body>
</html>