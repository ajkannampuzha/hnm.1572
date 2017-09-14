package hnm.controller;

import hnm.beans.User;
import hnm.model.EmployeeModel;
import hnm.model.HRModel;
import hnm.model.Login;
import hnm.model.ScheduledTask;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import java.util.*;

import hnm.beans.*;




public class HNMServlet extends HttpServlet {
	public static Logger logger=Logger.getLogger(HNMServlet.class);
	public static final long SECS=120000;//time set in millisecs
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("action")==null){
			response.sendRedirect("login.jsp");
		}else{
			logger.info(request.getParameter("action"));
			switch(request.getParameter("action")){
			
			case "new request":{//if employee opts for a new request
				if(request.getSession().getAttribute("categories")==null){
					logger.debug("inside new category");
					List<Category> categories=EmployeeModel.getCategories();
					logger.debug(categories.get(0));
					request.getSession().setAttribute("categories", categories);
				}
				request.setAttribute("jspAction", "new");
				request.getRequestDispatcher("/WEB-INF/jsp/employee.jsp").forward(request, response);
				break;
				}
			case "view requests":{//if employee demands to see his request history
				request.setAttribute("jspAction", "view");
				List<Request> requests=EmployeeModel.getRequests((Integer)request.getSession().getAttribute("userId"));
				if(requests!=null && requests.isEmpty()){
					request.setAttribute("error", "No records found");
				}
				request.setAttribute("requests", requests);
				request.getRequestDispatcher("/WEB-INF/jsp/employee.jsp").forward(request, response);
				logger.debug("reached at end of view");
				break;
				}
			case "cancel":{//if an employee cancel his request
				int rId=Integer.parseInt(request.getParameter("requestId"));
				boolean status=EmployeeModel.cancelRequest(rId);
				if(!status){
					request.setAttribute("error", "Sorry.This request cannot be cancelled.");
				}
				request.setAttribute("jspAction", "view");
				List<Request> requests=EmployeeModel.getRequests((Integer)request.getSession().getAttribute("userId"));
				request.setAttribute("requests", requests);
				request.getRequestDispatcher("/WEB-INF/jsp/employee.jsp").forward(request, response);
				break;
				}
			case "add request":{//Servlet called with new request attributes as parameter
				Request request1=new Request();
				logger.info(request.getParameter("category"));
				request1.setcId(Integer.parseInt(request.getParameter("category")));
				logger.info(request.getParameter("description"));
				request1.setDescription(request.getParameter("description"));
				logger.info(request.getSession().getAttribute("userId"));
				request1.seteId((Integer)request.getSession().getAttribute("userId"));
				request1.setName(request.getParameter("title"));
				boolean status=EmployeeModel.addRequest(request1);
				if(!status){
					request.setAttribute("error", "Something went wrong!!");
				}
				request.setAttribute("jspAction", "view");
				List<Request> requests=EmployeeModel.getRequests((Integer)request.getSession().getAttribute("userId"));
				if(requests!=null && requests.isEmpty()){
					request.setAttribute("error", "No records found");
				}
				request.setAttribute("requests", requests);
				request.getRequestDispatcher("/WEB-INF/jsp/employee.jsp").forward(request, response);
				break;
				}
			case "cancelled requests":{//if an employee opt to see all his cancelled requests
				request.setAttribute("jspAction", "view");
				List<Request> requests=EmployeeModel.getCancelledRequests((Integer)request.getSession().getAttribute("userId"));
				if(requests!=null && requests.isEmpty()){
					request.setAttribute("error", "No records found");
				}
				request.setAttribute("requests", requests);
				request.getRequestDispatcher("/WEB-INF/jsp/employee.jsp").forward(request, response);
				break;
				}
			case "view all requests":{
				request.setAttribute("jspAction", "view");
				List<Request> requests=HRModel.getRequests();
				if(requests!=null && requests.isEmpty()){
					request.setAttribute("error", "No records found");
				}
				request.setAttribute("requests", requests);
				request.getRequestDispatcher("/WEB-INF/jsp/hr.jsp").forward(request, response);
				break;
				}
			case "View forwarded requests":{
				User user=(User)request.getSession().getAttribute("user");
				int level=user.getLevel();
				logger.info(level);
				List<Request> requests=HRModel.getForwardedRequests(level);
				if(requests.isEmpty()){
					request.setAttribute("error", "No records found");
					request.getRequestDispatcher("/WEB-INF/jsp/hr.jsp").forward(request, response);
				}else{
					request.setAttribute("requests", requests);
					request.setAttribute("jspAction", "handle");
					request.getRequestDispatcher("/WEB-INF/jsp/hr.jsp").forward(request, response);
				}
				break;
				}
			case "View and Handle":{
				int attenderId=(Integer)request.getSession().getAttribute("userId");
				int rId=Integer.parseInt(request.getParameter("requestId"));
				boolean status=HRModel.updateRequest(rId,attenderId);
				if(status){
					Request request1=HRModel.getRequest(rId);
					List<Request> requests=new ArrayList<Request>();
					requests.add(request1);
					request.setAttribute("requests", requests);
					request.setAttribute("jspAction", "handle");
					request.getRequestDispatcher("/WEB-INF/jsp/hr.jsp").forward(request, response);
				}else{
					request.setAttribute("error", "Something went wrong!!");
					request.getRequestDispatcher("/WEB-INF/jsp/hr.jsp").forward(request, response);
				}
				
				break;
				}
			case "Show my Pending Requests":{
				int attenderId=(Integer)request.getSession().getAttribute("userId");
				List<Request> requests=HRModel.getRequestOfHR(attenderId);
				if(requests.isEmpty()){
					request.setAttribute("error", "no requests selected");
					request.getRequestDispatcher("/WEB-INF/jsp/hr.jsp").forward(request, response);
				}else{
					request.setAttribute("requests", requests);
					request.setAttribute("jspAction", "handle");
					request.getRequestDispatcher("/WEB-INF/jsp/hr.jsp").forward(request, response);
					
				}
				break;
				}
			case "Revert":{
				int rId=Integer.parseInt(request.getParameter("rId"));
				
				if(HRModel.revert(rId)){
					request.getRequestDispatcher("/WEB-INF/jsp/hr.jsp").forward(request, response);
				}else{
					request.setAttribute("error", "cannot revert");
				}
				break;
				}
			case "Resolve":{
				int rId=Integer.parseInt(request.getParameter("rId"));
				String remarks=request.getParameter("remarks");
				if(HRModel.resolve(rId,remarks)){
					request.getRequestDispatcher("/WEB-INF/jsp/hr.jsp").forward(request, response);
				}else{
					request.setAttribute("error", "cannot resolve");
				}
				break;
				}
			case "Forward":{
				int rId=Integer.parseInt(request.getParameter("rId"));
				if(HRModel.forward(rId)){
					request.getRequestDispatcher("/WEB-INF/jsp/hr.jsp").forward(request, response);
				}else{
					request.setAttribute("error", "cannot forward");
				}
				break;
				}
			case "logout":{//if someone logout
				logger.info("inside logout");
				request.getSession().invalidate();
				response.sendRedirect("login.jsp");
			}
				break;
			case "sort":{
				String sortBasedOn=request.getParameter("sort");
				List<Request> requests=EmployeeModel.getRequests((Integer)request.getSession().getAttribute("userId"));
				List<Request> sortedRequests=EmployeeModel.sortRequests(requests,sortBasedOn);
				request.setAttribute("jspAction", "view");
				request.setAttribute("requests", sortedRequests);
				request.getRequestDispatcher("/WEB-INF/jsp/employee.jsp").forward(request, response);
				break;
				}
			}
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.debug("inside dopost");
		//create a user object from input values
		User user=new User();
		user.setuId(Integer.parseInt(request.getParameter("userId")));
		user.setPwd(request.getParameter("password"));
		logger.debug(user.getPwd());
		//check for the user in database and return his object if present else null
		if(Login.validate(user)){
			logger.info(user.getRole());
			request.getSession().setAttribute("userId",user.getuId());
			request.getSession().setAttribute("user", user);
			switch(user.getRole()){
			case "employee":{
				request.setAttribute("jspAction", "view");
				List<Request> requests=EmployeeModel.getRequests(user.geteId());
				request.setAttribute("requests", requests);
				request.getRequestDispatcher("/WEB-INF/jsp/employee.jsp").forward(request, response);
				logger.debug("reached after employee");
				break;
				}
			case "hr":{
				
				request.setAttribute("role", "hr");
				request.getRequestDispatcher("/WEB-INF/jsp/hr.jsp").forward(request, response);
				break;
				}
			case "mgr":{
				request.setAttribute("role", "mgr");
				request.getRequestDispatcher("/WEB-INF/jsp/hr.jsp").forward(request, response);
				break;
				}
			case "ceo":{
				request.setAttribute("role", "ceo");
				request.getRequestDispatcher("/WEB-INF/jsp/hr.jsp").forward(request, response);
				break;
				}
			}
			
		}else{
			response.sendRedirect("login.jsp?error=login fail");
		}
	}
	@Override
	public void init() throws ServletException {
		logger.info("Initialised Successfully");
		Timer time = new Timer(); // Instantiate Timer Object
		ScheduledTask st = new ScheduledTask(); // Instantiate SheduledTask class
		time.schedule(st, 0, SECS); // Create Repetitively task for every 2 minutes(in millisecs)
	}
}
