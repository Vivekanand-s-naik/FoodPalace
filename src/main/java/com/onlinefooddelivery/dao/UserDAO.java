package com.onlinefooddelivery.dao;

import java.util.List;

import com.onlinefooddelivery.model.User;

public interface UserDAO {

    boolean addUser(User user);

    boolean updateUser(User user);

    boolean deleteUser(int userId);

    User getUserById(int userId);

    User getUserByEmail(String email);

    List<User> getAllUsers();

    User validateUser(String email, String password);

}