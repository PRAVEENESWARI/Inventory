package controller;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

public class ConnectionJdbc {

	private static final String url = "jdbc:mysql://localhost:3306/Inventory_Management";
	private static final String user = "praveen";
	private static final String password = "Eswari@1045";

	public Connection connect() throws SQLException {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (Exception e) {
			e.getMessage();
		}
		return DriverManager.getConnection(url, user, password);
	}
}
