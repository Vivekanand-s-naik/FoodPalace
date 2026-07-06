package com.onlinefooddelivery.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.onlinefooddelivery.dao.UserDAO;
import com.onlinefooddelivery.model.User;
import com.onlinefooddelivery.util.DBConnection;

public class UserDAOImpl implements UserDAO {

    private static final String INSERT_USER = "INSERT INTO users(full_name,email,phone,password,role) VALUES(?,?,?,?,?)";

    private static final String UPDATE_USER = "UPDATE users SET full_name=?, email=?, phone=?, password=?, role=? WHERE user_id=?";

    private static final String DELETE_USER = "DELETE FROM users WHERE user_id=?";

    private static final String GET_USER_BY_ID = "SELECT * FROM users WHERE user_id=?";

    private static final String GET_USER_BY_EMAIL = "SELECT * FROM users WHERE email=?";

    private static final String GET_ALL_USERS = "SELECT * FROM users";

    private static final String VALIDATE_USER = "SELECT * FROM users WHERE email=? AND password=?";

    @Override
    public boolean addUser(User user) {

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(INSERT_USER)) {

            ps.setString(1, user.getFullName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPhone());
            ps.setString(4, user.getPassword());
            ps.setString(5, user.getRole());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean updateUser(User user) {

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(UPDATE_USER)) {

            ps.setString(1, user.getFullName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPhone());
            ps.setString(4, user.getPassword());
            ps.setString(5, user.getRole());
            ps.setInt(6, user.getUserId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean deleteUser(int userId) {

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(DELETE_USER)) {

            ps.setInt(1, userId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public User getUserById(int userId) {

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(GET_USER_BY_ID)) {

            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return extractUser(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public User getUserByEmail(String email) {

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(GET_USER_BY_EMAIL)) {

            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return extractUser(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<User> getAllUsers() {

        List<User> users = new ArrayList<>();

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(GET_ALL_USERS); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                users.add(extractUser(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    @Override
    public User validateUser(String email, String password) {

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(VALIDATE_USER)) {

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            return extractUser(rs);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private User extractUser(ResultSet rs) throws SQLException {
        if (rs.next()) {
            User user = new User();

            user.setUserId(rs.getInt("user_id"));
            user.setFullName(rs.getString("full_name"));
            user.setEmail(rs.getString("email"));
            user.setPhone(rs.getString("phone"));
            user.setPassword(rs.getString("password"));
            user.setRole(rs.getString("role"));
            user.setCreatedAt(rs.getTimestamp("created_at"));

            try {
                user.setActive(rs.getBoolean("is_active"));
            } catch (SQLException e) {
                user.setActive(true);
            }

            return user;
        }
        return null;

    }
}
