package com.onlinefooddelivery.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.onlinefooddelivery.dao.OrderDAO;
import com.onlinefooddelivery.model.Order;
import com.onlinefooddelivery.util.DBConnection;

public class OrderDAOImpl implements OrderDAO {

    private static final String INSERT
            = "INSERT INTO orders(user_id, restaurant_id, total_amount, status) VALUES(?,?,?,?)";

    private static final String UPDATE
            = "UPDATE orders SET user_id=?, restaurant_id=?, total_amount=?, status=? WHERE order_id=?";

    private static final String DELETE
            = "DELETE FROM orders WHERE order_id=?";

    private static final String GET_BY_ID
            = "SELECT * FROM orders WHERE order_id=?";

    private static final String GET_ALL
            = "SELECT * FROM orders";

    private static final String GET_BY_USER
            = "SELECT * FROM orders WHERE user_id=?";

    private static final String GET_BY_RESTAURANT
            = "SELECT * FROM orders WHERE restaurant_id=?";

    private static final String GET_BY_STATUS
            = "SELECT * FROM orders WHERE status=?";

    @Override
    public boolean addOrder(Order order) {

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, order.getUserId());
            ps.setInt(2, order.getRestaurantId());
            ps.setDouble(3, order.getTotalAmount());
            ps.setString(4, order.getStatus());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) {
                        order.setOrderId(keys.getInt(1));
                    }
                }
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean updateOrder(Order order) {

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(UPDATE)) {

            ps.setInt(1, order.getUserId());
            ps.setInt(2, order.getRestaurantId());
            ps.setDouble(3, order.getTotalAmount());
            ps.setString(4, order.getStatus());
            ps.setInt(5, order.getOrderId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean deleteOrder(int orderId) {

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(DELETE)) {

            ps.setInt(1, orderId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public Order getOrderById(int orderId) {

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(GET_BY_ID)) {

            ps.setInt(1, orderId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return extractOrder(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Order> getAllOrders() {

        List<Order> orders = new ArrayList<>();

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(GET_ALL); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                orders.add(extractOrder(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }

    @Override
    public List<Order> getOrdersByUserId(int userId) {

        List<Order> orders = new ArrayList<>();

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(GET_BY_USER)) {

            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                orders.add(extractOrder(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }

    @Override
    public List<Order> getOrdersByRestaurantId(int restaurantId) {

        List<Order> orders = new ArrayList<>();

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(GET_BY_RESTAURANT)) {

            ps.setInt(1, restaurantId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                orders.add(extractOrder(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }

    @Override
    public List<Order> getOrdersByStatus(String status) {

        List<Order> orders = new ArrayList<>();

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(GET_BY_STATUS)) {

            ps.setString(1, status);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                orders.add(extractOrder(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }

    private Order extractOrder(ResultSet rs) throws SQLException {

        Order order = new Order();

        order.setOrderId(rs.getInt("order_id"));
        order.setUserId(rs.getInt("user_id"));
        order.setRestaurantId(rs.getInt("restaurant_id"));
        order.setTotalAmount(rs.getDouble("total_amount"));
        order.setStatus(rs.getString("status"));
        order.setOrderDate(rs.getTimestamp("order_date"));

        try {
            order.setCustomerName(rs.getString("customer_name"));
        } catch (SQLException e) {
            order.setCustomerName(null);
        }

        try {
            order.setRestaurantName(rs.getString("restaurant_name"));
        } catch (SQLException e) {
            order.setRestaurantName(null);
        }

        if (order.getCustomerName() == null || order.getCustomerName().isBlank()) {
            order.setCustomerName("Customer");
        }
        if (order.getRestaurantName() == null || order.getRestaurantName().isBlank()) {
            order.setRestaurantName("Restaurant");
        }

        return order;
    }
}
