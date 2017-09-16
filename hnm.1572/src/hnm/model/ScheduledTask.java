package hnm.model;


import hnm.dao.implementation.DAOImplementation;

import java.sql.SQLException;
import java.util.TimerTask;

public class ScheduledTask extends TimerTask {
	

	
	public void run() {
		
	
			DAOImplementation.getInstance().executeScheduledUpdate();
		
		
	}

}
