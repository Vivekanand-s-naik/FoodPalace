package com.onlinefooddelivery.test;

import java.util.List;

import com.onlinefooddelivery.dao.UserDAO;
import com.onlinefooddelivery.dao.impl.UserDAOImpl;
import com.onlinefooddelivery.model.User;

public class TestUserDAO {

    public static void main(String[] args) {

        UserDAO dao = new UserDAOImpl();

        System.out.println("===== USER DAO TEST =====");

        // -------------------------------
        // 1. Add User
        // -------------------------------

        User user = new User();

        user.setFullName("Rahul Sharma");
        user.setEmail("rahul@test.com");
        user.setPhone("987654999");
        user.setPassword("123456");
        user.setRole("CUSTOMER");

        boolean inserted = dao.addUser(user);

        System.out.println("User Inserted : " + inserted);

        // -------------------------------
        // 2. Get All Users
        // -------------------------------

        System.out.println("\n===== ALL USERS =====");

        List<User> users = dao.getAllUsers();

        for (User u : users) {
            System.out.println(u);
        }

        // -------------------------------
        // 3. Get User By Email
        // -------------------------------

        System.out.println("\n===== GET USER BY EMAIL =====");

        User found = dao.getUserByEmail("rahul@test.com");

        if (found != null) {

            System.out.println(found);

            // -------------------------------
            // 4. Update User
            // -------------------------------

            found.setPhone("999911999");

            boolean updated = dao.updateUser(found);

            System.out.println("Updated : " + updated);

            // -------------------------------
            // 5. Validate User
            // -------------------------------

            User valid = dao.validateUser(
                    "rahul@test.com",
                    "123456"
            );

            System.out.println("Login Valid : " + valid);

            // -------------------------------
            // 6. Delete User
            // -------------------------------

            boolean deleted = dao.deleteUser(found.getUserId());

            System.out.println("Deleted : " + deleted);
        }
    }
}