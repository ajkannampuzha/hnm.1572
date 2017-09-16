package hnm.beans;

import java.io.Serializable;

public class Category implements Serializable {
	private int cId;
	private String name;
	private int eta;//in minutes
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
	public int getEta() {
		return eta;
	}
	public void setEta(int eta) {
		this.eta = eta;
	}
	
	
	
	
}
