package com.onlinefooddelivery.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // Database Configuration
    private static final String URL = "jdbc:mysql://localhost:3306/OnlineFoodDelivery";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    // Private constructor to prevent object creation
    private DBConnection() {
    }

    // Method to establish database connection
    public static Connection getConnection() {

        Connection connection = null;

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        } catch (ClassNotFoundException e) {

            System.out.println("MySQL JDBC Driver not found.");
            e.printStackTrace();

        } catch (SQLException e) {

            System.out.println("Failed to connect to database.");
            e.printStackTrace();

        }

        return connection;
    }

    // Method to close connection
    public static void closeConnection(Connection connection) {

        if (connection != null) {

            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }
}