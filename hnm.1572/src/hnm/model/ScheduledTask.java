package hnm.model;


import hnm.dao.DAO;

import java.sql.SQLException;
import java.util.TimerTask;

public class ScheduledTask extends TimerTask {
	

	
	public void run() {
		
		
		try {
			DAO.executeScheduledUpdate();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
