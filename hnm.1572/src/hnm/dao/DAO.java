package hnm.dao;

import java.util.List;

import hnm.beans.Category;
import hnm.beans.Request;
import hnm.beans.User;

public interface DAO {
	public boolean validate(User user);
	public List<Request> getRequestsOfEmployee(int eId);
	public boolean cancelRequest(int rId);
	public List<Category> getCategoryList();
	public boolean addRequest(Request request);
	public List<Request> getCancelledRequestsOfEmployee(Integer eId);
	public void executeScheduledUpdate();
	public List<Request> getAllRequests();
	public boolean updateRequest(int rId, int attenderId);
	public Request getRequest(int rId);
	public boolean revert(int rId);
	public boolean resolve(int rId, String remarks);
	public boolean forward(int rId);
	public List<Request> getRequestOfHR(int attenderId);
	public List<Request> getForwardedRequests(int level);
	public int getEscalatedRequestCount(int level);
	

}
