package com.onlinefooddelivery.dao.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.onlinefooddelivery.dao.OrderItemDAO;
import com.onlinefooddelivery.model.OrderItem;
import com.onlinefooddelivery.util.DBConnection;

public class OrderItemDAOImpl implements OrderItemDAO {

    private static final String INSERT =
            "INSERT INTO order_items(order_id, item_id, quantity, price) VALUES(?,?,?,?)";

    private static final String UPDATE =
            "UPDATE order_items SET order_id=?, item_id=?, quantity=?, price=? WHERE order_item_id=?";

    private static final String DELETE =
            "DELETE FROM order_items WHERE order_item_id=?";

    private static final String GET_BY_ID =
            "SELECT * FROM order_items WHERE order_item_id=?";

    private static final String GET_BY_ORDER =
            "SELECT * FROM order_items WHERE order_id=?";

    @Override
    public boolean addOrderItem(OrderItem orderItem) {

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT)) {

            ps.setInt(1, orderItem.getOrderId());
            ps.setInt(2, orderItem.getItemId());
            ps.setInt(3, orderItem.getQuantity());
            ps.setDouble(4, orderItem.getPrice());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean updateOrderItem(OrderItem orderItem) {

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(UPDATE)) {

            ps.setInt(1, orderItem.getOrderId());
            ps.setInt(2, orderItem.getItemId());
            ps.setInt(3, orderItem.getQuantity());
            ps.setDouble(4, orderItem.getPrice());
            ps.setInt(5, orderItem.getOrderItemId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean deleteOrderItem(int orderItemId) {

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(DELETE)) {

            ps.setInt(1, orderItemId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public OrderItem getOrderItemById(int orderItemId) {

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(GET_BY_ID)) {

            ps.setInt(1, orderItemId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return extractOrderItem(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<OrderItem> getOrderItemsByOrderId(int orderId) {

        List<OrderItem> items = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(GET_BY_ORDER)) {

            ps.setInt(1, orderId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                items.add(extractOrderItem(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }

    private OrderItem extractOrderItem(ResultSet rs) throws SQLException {

        OrderItem item = new OrderItem();

        item.setOrderItemId(rs.getInt("order_item_id"));
        item.setOrderId(rs.getInt("order_id"));
        item.setItemId(rs.getInt("item_id"));
        item.setQuantity(rs.getInt("quantity"));
        item.setPrice(rs.getDouble("price"));

        return item;
    }
}