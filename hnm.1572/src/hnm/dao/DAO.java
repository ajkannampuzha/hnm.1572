package hnm.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;





import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import hnm.beans.Category;
import hnm.beans.Request;
import hnm.beans.User;
import hnm.controller.HNMServlet;
import hnm.dao.util.MySqlConnection;

public class DAO {

	public static Logger logger=Logger.getLogger(DAO.class);
	public static boolean validate(User user) throws ClassNotFoundException, SQLException {
		Connection conn=MySqlConnection.getMySqlConnection().connect();
		String sql="select a.role role,a.eId eId,b.level level from t_user a,t_role b where a.uId="+user.getuId()+" and a.pwd='"+user.getPwd()+"' and a.role=b.role";
		Statement stmt=conn.createStatement();
		try {
			ResultSet result=stmt.executeQuery(sql);
			if(result.next()){
				user.setRole(result.getString("role"));
				user.seteId(result.getInt("eId"));
				user.setLevel(result.getInt("level"));
				logger.info(user.getLevel());
				return true;
			}
		} finally {
			MySqlConnection.getMySqlConnection().close();
		}
		return false;
	}

	public static List<Request> getRequestsOfEmployee(int eId) throws SQLException, ClassNotFoundException {
		
		Connection conn=MySqlConnection.getMySqlConnection().connect();
		String sql="select * from t_request where not status='cancelled' and eId="+eId;
		Statement stmt=conn.createStatement();
		List<Request> requests=new ArrayList<Request>();
		try {
			ResultSet result=stmt.executeQuery(sql);
			while(result.next()){
				Request request=new Request();
				request.setAttenderId(result.getInt("attenderId"));
				request.setcId(result.getInt("cId"));
				request.setDescription(result.getString("description"));
				request.seteId(result.getInt("eId"));
				request.setExpiryTime(result.getDate("expiryTime"));
				request.setName(result.getString("name"));
				request.setRemarks(result.getString("remarks"));
				request.setrId(result.getInt("rId"));
				request.setStatus(result.getString("status"));
				request.setSubmitTime(result.getDate("submitTime"));
				
				requests.add(request);
			}
		} finally {
			MySqlConnection.getMySqlConnection().close();
		}
		return requests;
	}

	public static boolean cancelRequest(int rId) throws SQLException, ClassNotFoundException {
		Connection conn=MySqlConnection.getMySqlConnection().connect();
		String sql="update t_request set status='cancelled' where status='pending' and rId="+rId;
		Statement stmt=conn.createStatement();
		boolean status=false;
		try {
			int result=stmt.executeUpdate(sql);
			if(result>0){
				status= true;
			}
			
		} finally {
			MySqlConnection.getMySqlConnection().close();
		}
		return status;
		
		
	}

	public static List<Category> getCategoryList() throws ClassNotFoundException, SQLException {
		Connection conn=MySqlConnection.getMySqlConnection().connect();
		String sql="select * from t_category";
		Statement stmt=conn.createStatement();
		List<Category> categories=new ArrayList<Category>();
		try {
			ResultSet result=stmt.executeQuery(sql);
			while(result.next()){
				Category category=new Category();
				category.setcId(result.getInt("cId"));
				category.setName(result.getString("name"));
				categories.add(category);
			}
		} finally {
			MySqlConnection.getMySqlConnection().close();
		}
		return categories;
	}

	public static boolean addRequest(Request request) throws ClassNotFoundException, SQLException {
		Connection conn=MySqlConnection.getMySqlConnection().connect();
		String sql="insert into t_request(eId,cId,name,description,submitTime,expiryTime,status,level) values("+request.geteId()+","+request.getcId()+",'"+request.getName()+"','"+request.getDescription()+"',curtime(),date_add(curtime(),interval (select eta from t_category where cId="+request.getcId()+") MINUTE),'pending',0)";
		Statement stmt=conn.createStatement();
		boolean status=false;
		try {
			int result=stmt.executeUpdate(sql);
			if(result>0){
				status= true;
			}
			
		} finally {
			MySqlConnection.getMySqlConnection().close();
		}
		return status;
	}

	public static List<Request> getCancelledRequestsOfEmployee(Integer eId) throws ClassNotFoundException, SQLException {
		Connection conn=MySqlConnection.getMySqlConnection().connect();
		String sql="select * from t_request where status='cancelled' and eId="+eId;
		Statement stmt=conn.createStatement();
		List<Request> requests=new ArrayList<Request>();
		try {
			ResultSet result=stmt.executeQuery(sql);
			while(result.next()){
				Request request=new Request();
				request.setAttenderId(result.getInt("attenderId"));
				request.setcId(result.getInt("cId"));
				request.setDescription(result.getString("description"));
				request.seteId(result.getInt("eId"));
				request.setExpiryTime(result.getDate("expiryTime"));
				request.setName(result.getString("name"));
				request.setRemarks(result.getString("remarks"));
				request.setrId(result.getInt("rId"));
				request.setStatus(result.getString("status"));
				request.setSubmitTime(result.getDate("submitTime"));
				
				requests.add(request);
			}
		} finally {
			MySqlConnection.getMySqlConnection().close();
		}
		return requests;
	}

	public static void executeScheduledUpdate()  {
		try{
			Connection conn=MySqlConnection.getMySqlConnection().connect();
			String sql="update t_request set status='escalated',level=level+1,expiryTime=date_add(curtime(),"
					+ "interval 5 MINUTE) where level<3 and expiryTime<curtime() and "
					+ "not status='processed' and not status='cancelled'";
			Statement stmt=conn.createStatement();
			boolean status=false;
			
			try {
				int result=stmt.executeUpdate(sql);
				if(result>0){
					
					status= true;
				}
				logger.info("rows effected:"+result);
				
			} finally {
				MySqlConnection.getMySqlConnection().close();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}

	public static List<Request> getAllRequests() throws ClassNotFoundException, SQLException {
		Connection conn=MySqlConnection.getMySqlConnection().connect();
		String sql="select * from t_request where status='pending'";
		Statement stmt=conn.createStatement();
		List<Request> requests=new ArrayList<Request>();
		try {
			ResultSet result=stmt.executeQuery(sql);
			while(result.next()){
				Request request=new Request();
				request.setAttenderId(result.getInt("attenderId"));
				request.setcId(result.getInt("cId"));
				request.setDescription(result.getString("description"));
				request.seteId(result.getInt("eId"));
				request.setExpiryTime(result.getDate("expiryTime"));
				request.setName(result.getString("name"));
				request.setRemarks(result.getString("remarks"));
				request.setrId(result.getInt("rId"));
				request.setStatus(result.getString("status"));
				request.setSubmitTime(result.getDate("submitTime"));
				
				requests.add(request);
			}
		} finally {
			MySqlConnection.getMySqlConnection().close();
		}
		return requests;
	}

	public static boolean updateRequest(int rId, int attenderId) throws SQLException, ClassNotFoundException {
		Connection conn=MySqlConnection.getMySqlConnection().connect();
		String sql="update t_request set status='processing',attenderId="+attenderId+" where rId="+rId;
		Statement stmt=conn.createStatement();
		boolean status=false;
		try {
			int result=stmt.executeUpdate(sql);
			if(result>0){
				status= true;
			}
			
		} finally {
			MySqlConnection.getMySqlConnection().close();
		}
		return status;
	}

	public static Request getRequest(int rId) throws ClassNotFoundException, SQLException {
		Connection conn=MySqlConnection.getMySqlConnection().connect();
		String sql="select * from t_request where rId="+rId;
		Statement stmt=conn.createStatement();
		Request request=new Request();
		try {
			ResultSet result=stmt.executeQuery(sql);
			if(result.next()){
				request.setAttenderId(result.getInt("attenderId"));
				request.setcId(result.getInt("cId"));
				request.setDescription(result.getString("description"));
				request.seteId(result.getInt("eId"));
				request.setExpiryTime(result.getDate("expiryTime"));
				request.setName(result.getString("name"));
				request.setRemarks(result.getString("remarks"));
				request.setrId(result.getInt("rId"));
				request.setStatus(result.getString("status"));
				request.setSubmitTime(result.getDate("submitTime"));
			}
		} finally {
			MySqlConnection.getMySqlConnection().close();
		}
		return request;
	}

	public static boolean revert(int rId) throws ClassNotFoundException, SQLException {
		Connection conn=MySqlConnection.getMySqlConnection().connect();
		String sql="update t_request set level=0,status='pending',attenderId=null where rId="+rId;
		Statement stmt=conn.createStatement();
		boolean status=false;
		try {
			int result=stmt.executeUpdate(sql);
			if(result>0){
				status= true;
			}
			
		} finally {
			MySqlConnection.getMySqlConnection().close();
		}
		return status;
	}

	public static boolean resolve(int rId, String remarks) throws ClassNotFoundException, SQLException {
		Connection conn=MySqlConnection.getMySqlConnection().connect();
		String sql="update t_request set status='processed',remarks='"+remarks+"' where rId="+rId;
		Statement stmt=conn.createStatement();
		boolean status=false;
		try {
			int result=stmt.executeUpdate(sql);
			if(result>0){
				status= true;
			}
			
		} finally {
			MySqlConnection.getMySqlConnection().close();
		}
		return status;
	}

	public static boolean forward(int rId) throws ClassNotFoundException, SQLException {
		Connection conn=MySqlConnection.getMySqlConnection().connect();
		String sql="update t_request set status='escalated',level=level+1 where rId="+rId;
		Statement stmt=conn.createStatement();
		boolean status=false;
		try {
			int result=stmt.executeUpdate(sql);
			if(result>0){
				status= true;
			}
			
		} finally {
			MySqlConnection.getMySqlConnection().close();
		}
		return status;
	}

	public static List<Request> getRequestOfHR(int attenderId) throws ClassNotFoundException, SQLException {
		Connection conn=MySqlConnection.getMySqlConnection().connect();
		String sql="select * from t_request where status='processing' and attenderId="+attenderId;
		Statement stmt=conn.createStatement();
		List<Request> requests=new ArrayList<Request>();
		try {
			ResultSet result=stmt.executeQuery(sql);
			while(result.next()){
				Request request=new Request();
				request.setAttenderId(result.getInt("attenderId"));
				request.setcId(result.getInt("cId"));
				request.setDescription(result.getString("description"));
				request.seteId(result.getInt("eId"));
				request.setExpiryTime(result.getDate("expiryTime"));
				request.setName(result.getString("name"));
				request.setRemarks(result.getString("remarks"));
				request.setrId(result.getInt("rId"));
				request.setStatus(result.getString("status"));
				request.setSubmitTime(result.getDate("submitTime"));
				requests.add(request);
			}
		} finally {
			MySqlConnection.getMySqlConnection().close();
		}
		return requests;
	}

	public static List<Request> getForwardedRequests(int level) throws ClassNotFoundException, SQLException {
		Connection conn=MySqlConnection.getMySqlConnection().connect();
		String sql="select * from t_request where status='escalated' and level="+level;
		Statement stmt=conn.createStatement();
		List<Request> requests=new ArrayList<Request>();
		try {
			ResultSet result=stmt.executeQuery(sql);
			while(result.next()){
				Request request=new Request();
				request.setAttenderId(result.getInt("attenderId"));
				request.setcId(result.getInt("cId"));
				request.setDescription(result.getString("description"));
				request.seteId(result.getInt("eId"));
				request.setExpiryTime(result.getDate("expiryTime"));
				request.setName(result.getString("name"));
				request.setRemarks(result.getString("remarks"));
				request.setrId(result.getInt("rId"));
				request.setStatus(result.getString("status"));
				request.setSubmitTime(result.getDate("submitTime"));
				requests.add(request);
			}
		} finally {
			MySqlConnection.getMySqlConnection().close();
		}
		return requests;
	}

}
