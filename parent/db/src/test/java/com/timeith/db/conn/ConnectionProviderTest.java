package com.timeith.db.conn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionProviderTest {

	public static void main(String[] args) {

		try {
			Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/newsdb", "nsr", "1");
			System.out.println("jdbc connected..");
			Statement statement = connection.createStatement();
			System.out.println("reading news records");
			ResultSet resultSet = statement.executeQuery("SELECT * FROM public.news");
			while (resultSet.next()) {
				System.out.println(resultSet.getString(1) + "\t" + resultSet.getString(2) + "\t" + resultSet.getString(3) + "\t" + resultSet.getString(4));
			}
		} catch (SQLException e) {
            System.out.println("connect failed..");
            e.printStackTrace();
		}
	}
}
