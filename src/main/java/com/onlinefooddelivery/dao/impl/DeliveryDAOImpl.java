package com.onlinefooddelivery.dao.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.onlinefooddelivery.dao.DeliveryDAO;
import com.onlinefooddelivery.model.Delivery;
import com.onlinefooddelivery.util.DBConnection;

public class DeliveryDAOImpl implements DeliveryDAO {

    private static final String INSERT =
            "INSERT INTO deliveries(order_id, delivery_partner, delivery_status) VALUES(?,?,?)";

    private static final String UPDATE =
            "UPDATE deliveries SET order_id=?, delivery_partner=?, delivery_status=? WHERE delivery_id=?";

    private static final String DELETE =
            "DELETE FROM deliveries WHERE delivery_id=?";

    private static final String GET_BY_ID =
            "SELECT * FROM deliveries WHERE delivery_id=?";

    private static final String GET_BY_ORDER =
            "SELECT * FROM deliveries WHERE order_id=?";

    private static final String GET_ALL =
            "SELECT * FROM deliveries";

    @Override
    public boolean addDelivery(Delivery delivery) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT)) {

            ps.setInt(1, delivery.getOrderId());
            ps.setString(2, delivery.getDeliveryPartner());
            ps.setString(3, delivery.getDeliveryStatus());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateDelivery(Delivery delivery) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(UPDATE)) {

            ps.setInt(1, delivery.getOrderId());
            ps.setString(2, delivery.getDeliveryPartner());
            ps.setString(3, delivery.getDeliveryStatus());
            ps.setInt(4, delivery.getDeliveryId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteDelivery(int deliveryId) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(DELETE)) {

            ps.setInt(1, deliveryId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Delivery getDeliveryById(int deliveryId) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(GET_BY_ID)) {

            ps.setInt(1, deliveryId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return extractDelivery(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Delivery getDeliveryByOrderId(int orderId) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(GET_BY_ORDER)) {

            ps.setInt(1, orderId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return extractDelivery(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Delivery> getAllDeliveries() {

        List<Delivery> deliveries = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(GET_ALL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                deliveries.add(extractDelivery(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return deliveries;
    }

    private Delivery extractDelivery(ResultSet rs) throws SQLException {

        Delivery delivery = new Delivery();

        delivery.setDeliveryId(rs.getInt("delivery_id"));
        delivery.setOrderId(rs.getInt("order_id"));
        delivery.setDeliveryPartner(rs.getString("delivery_partner"));
        delivery.setDeliveryStatus(rs.getString("delivery_status"));
        delivery.setDeliveredAt(rs.getTimestamp("delivered_at"));

        return delivery;
    }
}