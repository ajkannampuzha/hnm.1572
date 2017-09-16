package hnm.model;

import java.sql.SQLException;

import hnm.beans.User;
import hnm.dao.implementation.DAOImplementation;

public class Login {

	public static boolean validate(User user) {
		
		
			return DAOImplementation.getInstance().validate(user);
		
	}

}
