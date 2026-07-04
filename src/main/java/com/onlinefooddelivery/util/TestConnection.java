package com.onlinefooddelivery.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
public class TestConnection {

    public static void main(String[] args) {

        Connection connection = DBConnection.getConnection();

        if (connection != null) {
            System.out.println("================================");
            System.out.println("Database Connected Successfully!");
            System.out.println("================================");
            try {
            	PreparedStatement pstatement =  connection.prepareStatement("SELECT * FROM USERS");
            	ResultSet res = pstatement.executeQuery();
            	while (res.next())
            	System.out.println(res.getString("full_name"));
            	
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            DBConnection.closeConnection(connection);
        } else {
            System.out.println("================================");
            System.out.println("Database Connection Failed!");
            System.out.println("================================");
        }
    }
}