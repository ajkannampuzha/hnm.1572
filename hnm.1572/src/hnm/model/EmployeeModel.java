package hnm.model;

import hnm.beans.Category;
import hnm.beans.Request;
import hnm.dao.DAO;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EmployeeModel {

	public static List<Request> getRequests(int eId) {
		
		try {
			return DAO.getRequestsOfEmployee(eId);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static boolean cancelRequest(int rId) {
		try {
			return DAO.cancelRequest(rId);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
	}

	public static List<Category> getCategories() {
		
		try {
			return DAO.getCategoryList();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static boolean addRequest(Request request) {
		
		try {
			return DAO.addRequest(request);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public static List<Request> getCancelledRequests(Integer eId) {
		try {
			return DAO.getCancelledRequestsOfEmployee(eId);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static List<Request> sortRequests(List<Request> requests,
			String sortBasedOn) {
		switch (sortBasedOn){
		case "rId":{
			Collections.sort(requests, new Comparator<Request>() {
			    @Override
			    public int compare(Request o1, Request o2) {
			        return (new Integer(o1.getrId())).compareTo((new Integer(o2.getrId())));
			    }
			});
			break;
			}
		case "submit":{
			Collections.sort(requests, new Comparator<Request>() {
			    @Override
			    public int compare(Request o1, Request o2) {
			        return o1.getSubmitTime().compareTo(o2.getSubmitTime());
			    }
			});
			break;
			}
		case "expiry":{
			Collections.sort(requests, new Comparator<Request>() {
			    @Override
			    public int compare(Request o1, Request o2) {
			        return o1.getExpiryTime().compareTo(o2.getExpiryTime());
			    }
			});
			break;
			}
		
		}
			
		
		return requests;
	}

	

}
