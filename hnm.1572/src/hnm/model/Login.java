package hnm.model;

import java.sql.SQLException;

import hnm.beans.User;
import hnm.dao.DAO;

public class Login {

	public static boolean validate(User user) {
		
		
			return DAO.validate(user);
		
	}

}
