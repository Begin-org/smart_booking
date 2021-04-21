package br.fatec.smartbooking.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
	public Connection getConnection() {
		try {
			String server = "localhost";
			String user = "root";
			String password = "";
			String database = "smart_booking";

			Class.forName("com.mysql.jdbc.Driver");
			String path = "jdbc:mysql://" + server + "/" + database;

			Connection conn = DriverManager.getConnection(path, user, password);
			return conn;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void closeConnection(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
