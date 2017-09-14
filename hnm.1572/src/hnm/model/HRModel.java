package hnm.model;

import hnm.beans.Request;
import hnm.dao.DAO;

import java.sql.SQLException;
import java.util.List;

public class HRModel {

	public static List<Request> getRequests() {
		
		try {
			return DAO.getAllRequests();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static boolean updateRequest(int rId, int attenderId) {
		// TODO Auto-generated method stub
		try {
			return DAO.updateRequest(rId,attenderId);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
	}

	public static Request getRequest(int rId) {
		
		try {
			return DAO.getRequest(rId);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static boolean revert(int rId) {
		// TODO Auto-generated method stub
		try {
			return DAO.revert(rId);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public static boolean resolve(int rId, String remarks) {
		// TODO Auto-generated method stub
		try {
			return DAO.resolve(rId,remarks);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public static boolean forward(int rId) {
		// TODO Auto-generated method stub
		try {
			return DAO.forward(rId);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public static List<Request> getRequestOfHR(int attenderId) {
		// TODO Auto-generated method stub
		try {
			return DAO.getRequestOfHR(attenderId);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static List<Request> getForwardedRequests(int level) {
		// TODO Auto-generated method stub
		try {
			return DAO.getForwardedRequests(level);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	

}
