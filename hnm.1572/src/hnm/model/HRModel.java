package hnm.model;

import hnm.beans.Request;
import hnm.dao.DAO;

import java.sql.SQLException;
import java.util.List;

public class HRModel {

	public static List<Request> getRequests() {
		
	
			return DAO.getAllRequests();
		
	}

	public static boolean updateRequest(int rId, int attenderId) {
		
			return DAO.updateRequest(rId,attenderId);
		
		
	}

	public static Request getRequest(int rId) {
		
		
			return DAO.getRequest(rId);
		
	}

	public static boolean revert(int rId) {
		
			return DAO.revert(rId);
		
	}

	public static boolean resolve(int rId, String remarks) {
		
			return DAO.resolve(rId,remarks);
		
	}

	public static boolean forward(int rId) {
		
			return DAO.forward(rId);
		
	}

	public static List<Request> getRequestOfHR(int attenderId) {
		
			return DAO.getRequestOfHR(attenderId);
		
	}

	public static List<Request> getForwardedRequests(int level) {
		
			return DAO.getForwardedRequests(level);
		
	}
	

}
