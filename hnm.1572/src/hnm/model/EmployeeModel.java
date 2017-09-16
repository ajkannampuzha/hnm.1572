package hnm.model;

import hnm.beans.Category;
import hnm.beans.Request;
import hnm.dao.DAO;
import hnm.dao.implementation.DAOImplementation;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EmployeeModel {
	
	public static EmployeeModel employeeModel;
	public EmployeeModel() {
		employeeModel=this;
	}
	public static EmployeeModel getInstance(){
		if(employeeModel==null){
			new EmployeeModel();
		}
		return employeeModel;
	}
	public static void dropInstance(){
		DAOImplementation.dropInstance();
		employeeModel=null;
	}
	
	public List<Request> getRequests(int eId) {
		
		
			return DAOImplementation.getInstance().getRequestsOfEmployee(eId);
		
		
	}

	public boolean cancelRequest(int rId) {
		
			return DAOImplementation.getInstance().cancelRequest(rId);
		
		
	}

	public List<Category> getCategories() {
		
	
			return DAOImplementation.getInstance().getCategoryList();
		
	}

	public boolean addRequest(Request request) {
		
	
			return DAOImplementation.getInstance().addRequest(request);
		
	}

	public List<Request> getCancelledRequests(Integer eId) {
		
			return DAOImplementation.getInstance().getCancelledRequestsOfEmployee(eId);
		
	}

	public List<Request> sortRequests(List<Request> requests,
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
