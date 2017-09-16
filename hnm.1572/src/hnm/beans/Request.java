package hnm.beans;

import java.io.Serializable;
import java.sql.Timestamp;;

public class Request implements Serializable {
	private int rId;
	private int eId;
	private int cId;
	private String name;
	private String description;
	private int attenderId;
	private Timestamp submitTime;
	private Timestamp expiryTime;
	private String status;
	private String remarks;
	public int getrId() {
		return rId;
	}
	public void setrId(int rId) {
		this.rId = rId;
	}
	public int geteId() {
		return eId;
	}
	public void seteId(int eId) {
		this.eId = eId;
	}
	public int getcId() {
		return cId;
	}
	public void setcId(int cId) {
		this.cId = cId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getAttenderId() {
		return attenderId;
	}
	public void setAttenderId(int attenderId) {
		this.attenderId = attenderId;
	}
	public Timestamp getSubmitTime() {
		return submitTime;
	}
	public void setSubmitTime(Timestamp submitTime) {
		this.submitTime = submitTime;
	}
	public Timestamp getExpiryTime() {
		return expiryTime;
	}
	public void setExpiryTime(Timestamp expiryTime) {
		this.expiryTime = expiryTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
	
	

}
