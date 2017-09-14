package hnm.dao.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlConnection {
	
	private final static String DRIVER="com.mysql.jdbc.Driver";
	private static MySqlConnection mySql;
	private Connection connection;
	private static final String URL="jdbc:mysql://localhost/hnm";
	private static final String USER="root";
	private static final String PASSWORD="admin";
	
	
	private MySqlConnection() {
		mySql=this;
	}
	
	public static MySqlConnection getMySqlConnection(){
		if(mySql==null){
			new MySqlConnection();
		}
		return mySql;
	}
	
	public Connection connect() throws ClassNotFoundException, SQLException   {
		
	
		
	
		try {
			connection=DriverManager.getConnection(URL,USER,PASSWORD);
			System.out.println("Connected");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
		
	}
	
	static {
		
			try {
				Class.forName(DRIVER);
				System.out.println("Driver Loaded");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	
	public void close() throws SQLException{
		if(connection!=null && !(connection.isClosed())){
			connection.close();
			System.out.println("Connection closed");
		}
	}

}
