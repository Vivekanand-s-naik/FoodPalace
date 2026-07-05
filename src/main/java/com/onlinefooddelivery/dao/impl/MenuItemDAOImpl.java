package com.onlinefooddelivery.dao.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.onlinefooddelivery.dao.MenuItemDAO;
import com.onlinefooddelivery.model.MenuItem;
import com.onlinefooddelivery.util.DBConnection;

public class MenuItemDAOImpl implements MenuItemDAO {

    private static final String INSERT = 
        "INSERT INTO menu_items (restaurant_id, item_name, description, price, category, is_available, image) VALUES (?, ?, ?, ?, ?, ?, ?)";
    
    private static final String UPDATE = 
        "UPDATE menu_items SET restaurant_id=?, item_name=?, description=?, price=?, category=?, is_available=?, image=? WHERE item_id=?";
    
    private static final String DELETE = 
        "DELETE FROM menu_items WHERE item_id=?";
    
    private static final String GET_BY_ID = 
        "SELECT * FROM menu_items WHERE item_id=?";
    
    private static final String GET_BY_RESTAURANT = 
        "SELECT * FROM menu_items WHERE restaurant_id=?";
    
    private static final String GET_ALL = 
        "SELECT * FROM menu_items";
    
    private static final String GET_AVAILABLE = 
        "SELECT * FROM menu_items WHERE is_available=TRUE";

    @Override
    public boolean addMenuItem(MenuItem item) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT)) {
            
            ps.setInt(1, item.getRestaurantId());
            ps.setString(2, item.getItemName());
            ps.setString(3, item.getDescription());
            ps.setDouble(4, item.getPrice());
            ps.setString(5, item.getCategory());
            ps.setBoolean(6, item.isAvailable());
            ps.setString(7, item.getImagePath());
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateMenuItem(MenuItem item) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(UPDATE)) {
            
            ps.setInt(1, item.getRestaurantId());
            ps.setString(2, item.getItemName());
            ps.setString(3, item.getDescription());
            ps.setDouble(4, item.getPrice());
            ps.setString(5, item.getCategory());
            ps.setBoolean(6, item.isAvailable());
            ps.setString(7, item.getImagePath());
            ps.setInt(8, item.getItemId());
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteMenuItem(int itemId) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(DELETE)) {
            
            ps.setInt(1, itemId);
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public MenuItem getMenuItemById(int itemId) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(GET_BY_ID)) {
            
            ps.setInt(1, itemId);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return extractMenuItem(rs);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<MenuItem> getMenuItemsByRestaurantId(int restaurantId) {
        List<MenuItem> items = new ArrayList<>();
        
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(GET_BY_RESTAURANT)) {
            
            ps.setInt(1, restaurantId);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                items.add(extractMenuItem(rs));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    @Override
    public List<MenuItem> getAllMenuItems() {
        List<MenuItem> items = new ArrayList<>();
        
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(GET_ALL);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                items.add(extractMenuItem(rs));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    @Override
    public List<MenuItem> getAvailableMenuItems() {
        List<MenuItem> items = new ArrayList<>();
        
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(GET_AVAILABLE);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                items.add(extractMenuItem(rs));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    // Added method for category filtering
    @Override
    public List<MenuItem> getMenuItemsByCategory(int restaurantId, String category) {
        List<MenuItem> items = new ArrayList<>();
        String sql = "SELECT * FROM menu_items WHERE restaurant_id=? AND category=?";
        
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, restaurantId);
            ps.setString(2, category);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                items.add(extractMenuItem(rs));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    // Added method to get categories
    @Override
    public List<String> getCategoriesByRestaurantId(int restaurantId) {
        List<String> categories = new ArrayList<>();
        String sql = "SELECT DISTINCT category FROM menu_items WHERE restaurant_id=? AND category IS NOT NULL";
        
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, restaurantId);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                categories.add(rs.getString("category"));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    private MenuItem extractMenuItem(ResultSet rs) throws SQLException {
        MenuItem item = new MenuItem();
        item.setItemId(rs.getInt("item_id"));
        item.setRestaurantId(rs.getInt("restaurant_id"));
        item.setItemName(rs.getString("item_name"));
        item.setDescription(rs.getString("description"));
        item.setPrice(rs.getDouble("price"));
        item.setCategory(rs.getString("category"));
        item.setAvailable(rs.getBoolean("is_available"));
        item.setImagePath(rs.getString("image"));  // <-- KEY FIX: Map image column to imagePath
        return item;
    }
}