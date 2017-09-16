package hnm.model;

import hnm.beans.Request;
import hnm.dao.implementation.DAOImplementation;

import java.sql.SQLException;
import java.util.List;

public class HRModel {
	
	public static HRModel hrModel;
	public HRModel() {
		hrModel=this;
	}
	public static HRModel getInstance(){
		if(hrModel==null){
			new HRModel();
		}
		return hrModel;
	}
	public static void dropInstance(){
		DAOImplementation.dropInstance();
		hrModel=null;
	}

	public List<Request> getRequests() {
		
	
			return DAOImplementation.getInstance().getAllRequests();
		
	}

	public boolean updateRequest(int rId, int attenderId) {
		
			return DAOImplementation.getInstance().updateRequest(rId,attenderId);
		
		
	}

	public Request getRequest(int rId) {
		
		
			return DAOImplementation.getInstance().getRequest(rId);
		
	}

	public boolean revert(int rId) {
		
			return DAOImplementation.getInstance().revert(rId);
		
	}

	public boolean resolve(int rId, String remarks) {
		
			return DAOImplementation.getInstance().resolve(rId,remarks);
		
	}

	public boolean forward(int rId) {
		
			return DAOImplementation.getInstance().forward(rId);
		
	}

	public List<Request> getRequestOfHR(int attenderId) {
		
			return DAOImplementation.getInstance().getRequestOfHR(attenderId);
		
	}

	public List<Request> getForwardedRequests(int level) {
		
			return DAOImplementation.getInstance().getForwardedRequests(level);
		
	}

	public int getEscalatedRequestCount(int level) {
		
		return DAOImplementation.getInstance().getEscalatedRequestCount(level);
	}
	

}
