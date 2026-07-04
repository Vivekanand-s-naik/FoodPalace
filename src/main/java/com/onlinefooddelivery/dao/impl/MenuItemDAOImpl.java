package com.onlinefooddelivery.dao.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.onlinefooddelivery.dao.MenuItemDAO;
import com.onlinefooddelivery.model.MenuItem;
import com.onlinefooddelivery.util.DBConnection;

public class MenuItemDAOImpl implements MenuItemDAO {

    private static final String INSERT =
            "INSERT INTO menu_items(restaurant_id, item_name, description, price, category, is_available, image) VALUES(?,?,?,?,?,?,?)";

    private static final String UPDATE =
            "UPDATE menu_items SET restaurant_id=?, item_name=?, description=?, price=?, category=?, is_available=?, image=? WHERE item_id=?";

    private static final String DELETE =
            "DELETE FROM menu_items WHERE item_id=?";

    private static final String GET_BY_ID =
            "SELECT * FROM menu_items WHERE item_id=?";

    private static final String GET_ALL =
            "SELECT * FROM menu_items";

    private static final String GET_BY_RESTAURANT =
            "SELECT * FROM menu_items WHERE restaurant_id=?";

    private static final String GET_AVAILABLE =
            "SELECT * FROM menu_items WHERE is_available=true";

    @Override
    public boolean addMenuItem(MenuItem menuItem) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT)) {

            ps.setInt(1, menuItem.getRestaurantId());
            ps.setString(2, menuItem.getItemName());
            ps.setString(3, menuItem.getDescription());
            ps.setDouble(4, menuItem.getPrice());
            ps.setString(5, menuItem.getCategory());
            ps.setBoolean(6, menuItem.isAvailable());
            ps.setString(7, menuItem.getImage());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean updateMenuItem(MenuItem menuItem) {

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(UPDATE)) {

            ps.setInt(1, menuItem.getRestaurantId());
            ps.setString(2, menuItem.getItemName());
            ps.setString(3, menuItem.getDescription());
            ps.setDouble(4, menuItem.getPrice());
            ps.setString(5, menuItem.getCategory());
            ps.setBoolean(6, menuItem.isAvailable());
            ps.setString(7, menuItem.getImage());
            ps.setInt(8, menuItem.getItemId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean deleteMenuItem(int itemId) {

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(DELETE)) {

            ps.setInt(1, itemId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
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

    private MenuItem extractMenuItem(ResultSet rs) throws SQLException {

        MenuItem item = new MenuItem();

        item.setItemId(rs.getInt("item_id"));
        item.setRestaurantId(rs.getInt("restaurant_id"));
        item.setItemName(rs.getString("item_name"));
        item.setDescription(rs.getString("description"));
        item.setPrice(rs.getDouble("price"));
        item.setCategory(rs.getString("category"));
        item.setAvailable(rs.getBoolean("is_available"));
        item.setImage(rs.getString("image"));

        return item;
    }
}