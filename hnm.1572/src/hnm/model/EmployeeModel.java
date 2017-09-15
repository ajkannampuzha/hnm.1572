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
		
		
			return DAO.getRequestsOfEmployee(eId);
		
		
	}

	public static boolean cancelRequest(int rId) {
		
			return DAO.cancelRequest(rId);
		
		
	}

	public static List<Category> getCategories() {
		
	
			return DAO.getCategoryList();
		
	}

	public static boolean addRequest(Request request) {
		
	
			return DAO.addRequest(request);
		
	}

	public static List<Request> getCancelledRequests(Integer eId) {
		
			return DAO.getCancelledRequestsOfEmployee(eId);
		
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
			        return o2.getSubmitTime().compareTo(o1.getSubmitTime());
			    }
			});
			break;
			}
		case "expiry":{
			Collections.sort(requests, new Comparator<Request>() {
			    @Override
			    public int compare(Request o1, Request o2) {
			        return o2.getExpiryTime().compareTo(o1.getExpiryTime());
			    }
			});
			break;
			}
		
		}
			
		
		return requests;
	}

	

}
