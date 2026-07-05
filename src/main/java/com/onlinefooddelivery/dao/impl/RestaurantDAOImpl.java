package com.onlinefooddelivery.dao.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.onlinefooddelivery.dao.RestaurantDAO;
import com.onlinefooddelivery.model.Restaurant;
import com.onlinefooddelivery.util.DBConnection;

public class RestaurantDAOImpl implements RestaurantDAO {

    private static final String INSERT =
            "INSERT INTO restaurants(name, owner_name, email, phone, address, cuisine, rating, is_active) VALUES(?,?,?,?,?,?,?,?)";

    private static final String UPDATE =
            "UPDATE restaurants SET name=?, owner_name=?, email=?, phone=?, address=?, cuisine=?, rating=?, is_active=? WHERE restaurant_id=?";

    private static final String DELETE =
            "DELETE FROM restaurants WHERE restaurant_id=?";

    private static final String GET_BY_ID =
            "SELECT * FROM restaurants WHERE restaurant_id=?";

    private static final String GET_ALL =
            "SELECT * FROM restaurants";

    private static final String GET_BY_CUISINE =
            "SELECT * FROM restaurants WHERE cuisine=?";

    private static final String GET_ACTIVE =
            "SELECT * FROM restaurants WHERE is_active=true";

    @Override
    public boolean addRestaurant(Restaurant restaurant) {

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT)) {

            ps.setString(1, restaurant.getName());
            ps.setString(2, restaurant.getOwnerName());
            ps.setString(3, restaurant.getEmail());
            ps.setString(4, restaurant.getPhone());
            ps.setString(5, restaurant.getAddress());
            ps.setString(6, restaurant.getCuisine());
            ps.setDouble(7, restaurant.getRating());
            ps.setBoolean(8, restaurant.isActive());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean updateRestaurant(Restaurant restaurant) {

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(UPDATE)) {

            ps.setString(1, restaurant.getName());
            ps.setString(2, restaurant.getOwnerName());
            ps.setString(3, restaurant.getEmail());
            ps.setString(4, restaurant.getPhone());
            ps.setString(5, restaurant.getAddress());
            ps.setString(6, restaurant.getCuisine());
            ps.setDouble(7, restaurant.getRating());
            ps.setBoolean(8, restaurant.isActive());
            ps.setInt(9, restaurant.getRestaurantId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean deleteRestaurant(int restaurantId) {

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(DELETE)) {

            ps.setInt(1, restaurantId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public Restaurant getRestaurantById(int restaurantId) {

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(GET_BY_ID)) {

            ps.setInt(1, restaurantId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return extractRestaurant(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Restaurant> getAllRestaurants() {
        List<Restaurant> restaurants = new ArrayList<>();
        String sql = "SELECT * FROM restaurants";
        
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                restaurants.add(extractRestaurant(rs));
            }
            System.out.println("getAllRestaurants returned: " + restaurants.size() + " records");
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return restaurants;
    }

    @Override
    public List<Restaurant> getRestaurantsByCuisine(String cuisine) {

        List<Restaurant> restaurants = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(GET_BY_CUISINE)) {

            ps.setString(1, cuisine);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                restaurants.add(extractRestaurant(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return restaurants;
    }

    @Override
    public List<Restaurant> getActiveRestaurants() {

        List<Restaurant> restaurants = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(GET_ACTIVE);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                restaurants.add(extractRestaurant(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return restaurants;
    }

    private Restaurant extractRestaurant(ResultSet rs) throws SQLException {
        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurantId(rs.getInt("restaurant_id"));
        restaurant.setName(rs.getString("name"));
        restaurant.setOwnerName(rs.getString("owner_name"));
        restaurant.setEmail(rs.getString("email"));
        restaurant.setPhone(rs.getString("phone"));
        restaurant.setAddress(rs.getString("address"));
        restaurant.setCuisine(rs.getString("cuisine"));
        restaurant.setRating(rs.getDouble("rating"));
        restaurant.setActive(rs.getBoolean("is_active"));
        restaurant.setImagePath(rs.getString("image_path"));
        
        // deliveryTime might not be in the table yet
        try {
            restaurant.setDeliveryTime(rs.getInt("delivery_time"));
        } catch (SQLException e) {
            restaurant.setDeliveryTime(30); // Default value
        }
        
        return restaurant;
    }
}