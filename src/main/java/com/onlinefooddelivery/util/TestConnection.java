package com.onlinefooddelivery.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TestConnection {
	public static void main(String[] args) {

		try (Connection connection = DBConnection.getConnection()) {

			if (connection != null) {
				System.out.println("================================");
				System.out.println("Database Connected Successfully!");
				System.out.println("================================");

				PreparedStatement ps = connection.prepareStatement("SELECT * FROM users");
				ResultSet rs = ps.executeQuery();

				while (rs.next()) {
					System.out.println(rs.getString("full_name"));
				}

			} else {
				System.out.println("Database Connection Failed!");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}