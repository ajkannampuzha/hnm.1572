package hnm.dao.implementation;

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
import hnm.dao.DAO;
import hnm.dao.util.MySqlConnection;

public class DAOImplementation implements DAO{
	public static DAOImplementation daoObject;
	public static Logger logger=Logger.getLogger(DAOImplementation.class);
	public DAOImplementation() {
		daoObject=this;
	}
	public static DAOImplementation getInstance(){
		if(daoObject==null){
			new DAOImplementation();
		}
		return daoObject;
	}
	public static void dropInstance(){
		daoObject=null;
	}
	
	
	public boolean validate(User user) {
		
		try {
			Connection conn = MySqlConnection.getMySqlConnection().connect();
			String sql = "select a.role role,a.eId eId,b.level level from t_user a,t_role b where a.uId="
					+ user.getuId()
					+ " and a.pwd='"
					+ user.getPwd()
					+ "' and a.role=b.role";
			Statement stmt = conn.createStatement();
			boolean status = false;
			try {
				ResultSet result = stmt.executeQuery(sql);
				if (result.next()) {
					user.setRole(result.getString("role"));
					user.seteId(result.getInt("eId"));
					user.setLevel(result.getInt("level"));
					logger.info(user.getLevel());
					result = null;
					status = true;
				}
			} finally {
				MySqlConnection.getMySqlConnection().close();
			}
			conn = null;
			stmt = null;
			sql = null;
			return status;
		} catch (SQLException  | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<Request> getRequestsOfEmployee(int eId){
		
		try {
			Connection conn = MySqlConnection.getMySqlConnection().connect();
			String sql = "select * from t_request where not status='cancelled' and eId="
					+ eId;
			Statement stmt = conn.createStatement();
			List<Request> requests = new ArrayList<Request>();
			try {
				ResultSet result = stmt.executeQuery(sql);
				while (result.next()) {
					Request request = new Request();
					request.setAttenderId(result.getInt("attenderId"));
					request.setcId(result.getInt("cId"));
					request.setDescription(result.getString("description"));
					request.seteId(result.getInt("eId"));
					request.setExpiryTime(result.getTimestamp("expiryTime"));
					request.setName(result.getString("name"));
					request.setRemarks(result.getString("remarks"));
					request.setrId(result.getInt("rId"));
					request.setStatus(result.getString("status"));
					request.setSubmitTime(result.getTimestamp("submitTime"));

					requests.add(request);

				}
			} finally {
				MySqlConnection.getMySqlConnection().close();
			}
			conn = null;
			stmt = null;
			return requests;
		}  catch (SQLException  | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean cancelRequest(int rId){
		try {
			Connection conn = MySqlConnection.getMySqlConnection().connect();
			String sql = "update t_request set status='cancelled' where status='pending' and rId="
					+ rId;
			Statement stmt = conn.createStatement();
			boolean status = false;
			try {
				int result = stmt.executeUpdate(sql);
				if (result > 0) {
					status = true;
				}

			} finally {
				MySqlConnection.getMySqlConnection().close();
			}
			conn = null;
			stmt = null;
			return status;
		} catch (SQLException  | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return false;
		
		
	}

	public List<Category> getCategoryList(){
		try {
			Connection conn = MySqlConnection.getMySqlConnection().connect();
			String sql = "select * from t_category";
			Statement stmt = conn.createStatement();
			List<Category> categories = new ArrayList<Category>();
			try {
				ResultSet result = stmt.executeQuery(sql);
				while (result.next()) {
					Category category = new Category();
					category.setcId(result.getInt("cId"));
					category.setName(result.getString("name"));
					categories.add(category);
				}
			} finally {
				MySqlConnection.getMySqlConnection().close();
			}
			conn = null;
			stmt = null;
			return categories;
		}  catch (SQLException  | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean addRequest(Request request){
		try {
			Connection conn = MySqlConnection.getMySqlConnection().connect();
			String sql = "insert into t_request(eId,cId,name,description,submitTime,expiryTime,status,level) values("
					+ request.geteId()
					+ ","
					+ request.getcId()
					+ ",'"
					+ request.getName()
					+ "','"
					+ request.getDescription()
					+ "',curtime(),date_add(curtime(),interval (select eta from t_category where cId="
					+ request.getcId() + ") MINUTE),'pending',0)";
			Statement stmt = conn.createStatement();
			boolean status = false;
			try {
				int result = stmt.executeUpdate(sql);
				if (result > 0) {
					status = true;
				}

			} finally {
				MySqlConnection.getMySqlConnection().close();
			}
			conn = null;
			stmt = null;
			return status;
		} catch (SQLException  | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<Request> getCancelledRequestsOfEmployee(Integer eId){
		try {
			Connection conn = MySqlConnection.getMySqlConnection().connect();
			String sql = "select * from t_request where status='cancelled' and eId="
					+ eId;
			Statement stmt = conn.createStatement();
			List<Request> requests = new ArrayList<Request>();
			try {
				ResultSet result = stmt.executeQuery(sql);
				while (result.next()) {
					Request request = new Request();
					request.setAttenderId(result.getInt("attenderId"));
					request.setcId(result.getInt("cId"));
					request.setDescription(result.getString("description"));
					request.seteId(result.getInt("eId"));
					request.setExpiryTime(result.getTimestamp("expiryTime"));
					request.setName(result.getString("name"));
					request.setRemarks(result.getString("remarks"));
					request.setrId(result.getInt("rId"));
					request.setStatus(result.getString("status"));
					request.setSubmitTime(result.getTimestamp("submitTime"));

					requests.add(request);
				}
			} finally {
				MySqlConnection.getMySqlConnection().close();
			}
			conn = null;
			stmt = null;
			return requests;
		}  catch (SQLException  | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void executeScheduledUpdate(){
		try {
			Connection conn = MySqlConnection.getMySqlConnection().connect();
			String sql = "update t_request set status='escalated',level=level+1,expiryTime=date_add(curtime(),"
					+ "interval 5 MINUTE) where level<3 and expiryTime<curtime() and "
					+ "not status='processed' and not status='cancelled'";
			Statement stmt = conn.createStatement();
			logger.info("within scheduled task");
			try {

				int result = stmt.executeUpdate(sql);
				if (result > 0) {

					logger.info("rows effected:" + result);
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				MySqlConnection.getMySqlConnection().close();
			}
		} catch (SQLException  | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		
		
		
	}

	public List<Request> getAllRequests(){
		try {
			Connection conn = MySqlConnection.getMySqlConnection().connect();
			String sql = "select * from t_request where status='pending'";
			Statement stmt = conn.createStatement();
			List<Request> requests = new ArrayList<Request>();
			try {
				ResultSet result = stmt.executeQuery(sql);
				while (result.next()) {
					Request request = new Request();
					request.setAttenderId(result.getInt("attenderId"));
					request.setcId(result.getInt("cId"));
					request.setDescription(result.getString("description"));
					request.seteId(result.getInt("eId"));
					request.setExpiryTime(result.getTimestamp("expiryTime"));
					request.setName(result.getString("name"));
					request.setRemarks(result.getString("remarks"));
					request.setrId(result.getInt("rId"));
					request.setStatus(result.getString("status"));
					request.setSubmitTime(result.getTimestamp("submitTime"));

					requests.add(request);
				}
			} finally {
				MySqlConnection.getMySqlConnection().close();
			}
			conn = null;
			stmt = null;
			return requests;
		}  catch (SQLException  | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean updateRequest(int rId, int attenderId){
		try {
			Connection conn = MySqlConnection.getMySqlConnection().connect();
			String sql = "update t_request set status='processing',attenderId="
					+ attenderId + " where rId=" + rId;
			Statement stmt = conn.createStatement();
			boolean status = false;
			try {
				int result = stmt.executeUpdate(sql);
				if (result > 0) {
					status = true;
				}

			} finally {
				MySqlConnection.getMySqlConnection().close();
			}
			conn = null;
			stmt = null;
			return status;
		}  catch (SQLException  | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}

	public Request getRequest(int rId){
	try {
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
				request.setExpiryTime(result.getTimestamp("expiryTime"));
				request.setName(result.getString("name"));
				request.setRemarks(result.getString("remarks"));
				request.setrId(result.getInt("rId"));
				request.setStatus(result.getString("status"));
				request.setSubmitTime(result.getTimestamp("submitTime"));
			}
		} finally {
			MySqlConnection.getMySqlConnection().close();
		}
		conn=null;
		stmt=null;
		return request;
		}  catch (SQLException  | ClassNotFoundException e) {
			e.printStackTrace();
		}
	return null;
	}

	public boolean revert(int rId){
		try {
			Connection conn = MySqlConnection.getMySqlConnection().connect();
			String sql = "update t_request set level=0,status='pending',attenderId=null where rId="
					+ rId;
			Statement stmt = conn.createStatement();
			boolean status = false;
			try {
				int result = stmt.executeUpdate(sql);
				if (result > 0) {
					status = true;
				}

			} finally {
				MySqlConnection.getMySqlConnection().close();
			}
			conn = null;
			stmt = null;
			return status;
		} catch (SQLException  | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean resolve(int rId, String remarks){
		try {
			Connection conn = MySqlConnection.getMySqlConnection().connect();
			String sql = "update t_request set status='processed',remarks='"
					+ remarks + "' where rId=" + rId;
			Statement stmt = conn.createStatement();
			boolean status = false;
			try {
				int result = stmt.executeUpdate(sql);
				if (result > 0) {
					status = true;
				}

			} finally {
				MySqlConnection.getMySqlConnection().close();
			}
			conn = null;
			stmt = null;
			return status;
		}  catch (SQLException  | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean forward(int rId){
		try {
			Connection conn = MySqlConnection.getMySqlConnection().connect();
			String sql = "update t_request set status='escalated',level=level+1 where rId="
					+ rId;
			Statement stmt = conn.createStatement();
			boolean status = false;
			try {
				int result = stmt.executeUpdate(sql);
				if (result > 0) {
					status = true;
				}

			} finally {
				MySqlConnection.getMySqlConnection().close();
			}
			conn = null;
			stmt = null;
			return status;
		}  catch (SQLException  | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<Request> getRequestOfHR(int attenderId){
		try {
			Connection conn = MySqlConnection.getMySqlConnection().connect();
			String sql = "select * from t_request where status='processing' and attenderId="
					+ attenderId;
			Statement stmt = conn.createStatement();
			List<Request> requests = new ArrayList<Request>();
			try {
				ResultSet result = stmt.executeQuery(sql);
				while (result.next()) {
					Request request = new Request();
					request.setAttenderId(result.getInt("attenderId"));
					request.setcId(result.getInt("cId"));
					request.setDescription(result.getString("description"));
					request.seteId(result.getInt("eId"));
					request.setExpiryTime(result.getTimestamp("expiryTime"));
					request.setName(result.getString("name"));
					request.setRemarks(result.getString("remarks"));
					request.setrId(result.getInt("rId"));
					request.setStatus(result.getString("status"));
					request.setSubmitTime(result.getTimestamp("submitTime"));
					requests.add(request);
				}
			} finally {
				MySqlConnection.getMySqlConnection().close();
			}
			conn = null;
			stmt = null;
			return requests;
		} catch (SQLException  | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Request> getForwardedRequests(int level){
		try {
			Connection conn = MySqlConnection.getMySqlConnection().connect();
			String sql = "select * from t_request where status='escalated' and level="
					+ level;
			Statement stmt = conn.createStatement();
			List<Request> requests = new ArrayList<Request>();
			try {
				ResultSet result = stmt.executeQuery(sql);
				while (result.next()) {
					Request request = new Request();
					request.setAttenderId(result.getInt("attenderId"));
					request.setcId(result.getInt("cId"));
					request.setDescription(result.getString("description"));
					request.seteId(result.getInt("eId"));
					request.setExpiryTime(result.getTimestamp("expiryTime"));
					request.setName(result.getString("name"));
					request.setRemarks(result.getString("remarks"));
					request.setrId(result.getInt("rId"));
					request.setStatus(result.getString("status"));
					request.setSubmitTime(result.getTimestamp("submitTime"));
					requests.add(request);
				}
			} finally {
				MySqlConnection.getMySqlConnection().close();
			}
			conn = null;
			stmt = null;
			return requests;
		}  catch (SQLException  | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public int getEscalatedRequestCount(int level) {
		try {
			Connection conn = MySqlConnection.getMySqlConnection().connect();
			String sql = "select * from t_request where status='escalated' and level="
					+ level+"+1";
			Statement stmt = conn.createStatement();
			int count=0;
			try {
				ResultSet result = stmt.executeQuery(sql);
				while (result.next()) {
					count++;
				}
			} finally {
				MySqlConnection.getMySqlConnection().close();
			}
			conn = null;
			stmt = null;
			return count;
		}  catch (SQLException  | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return 0;
	}

}
