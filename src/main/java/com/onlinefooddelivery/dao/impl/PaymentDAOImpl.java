package com.onlinefooddelivery.dao.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.onlinefooddelivery.dao.PaymentDAO;
import com.onlinefooddelivery.model.Payment;
import com.onlinefooddelivery.util.DBConnection;

public class PaymentDAOImpl implements PaymentDAO {

    private static final String INSERT =
            "INSERT INTO payments(order_id, payment_method, payment_status) VALUES(?,?,?)";

    private static final String UPDATE =
            "UPDATE payments SET order_id=?, payment_method=?, payment_status=? WHERE payment_id=?";

    private static final String DELETE =
            "DELETE FROM payments WHERE payment_id=?";

    private static final String GET_BY_ID =
            "SELECT * FROM payments WHERE payment_id=?";

    private static final String GET_BY_ORDER =
            "SELECT * FROM payments WHERE order_id=?";

    private static final String GET_ALL =
            "SELECT * FROM payments";

    @Override
    public boolean addPayment(Payment payment) {

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT)) {

            ps.setInt(1, payment.getOrderId());
            ps.setString(2, payment.getPaymentMethod());
            ps.setString(3, payment.getPaymentStatus());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean updatePayment(Payment payment) {

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(UPDATE)) {

            ps.setInt(1, payment.getOrderId());
            ps.setString(2, payment.getPaymentMethod());
            ps.setString(3, payment.getPaymentStatus());
            ps.setInt(4, payment.getPaymentId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean deletePayment(int paymentId) {

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(DELETE)) {

            ps.setInt(1, paymentId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public Payment getPaymentById(int paymentId) {

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(GET_BY_ID)) {

            ps.setInt(1, paymentId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return extractPayment(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Payment getPaymentByOrderId(int orderId) {

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(GET_BY_ORDER)) {

            ps.setInt(1, orderId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return extractPayment(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Payment> getAllPayments() {

        List<Payment> payments = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(GET_ALL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                payments.add(extractPayment(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return payments;
    }

    private Payment extractPayment(ResultSet rs) throws SQLException {

        Payment payment = new Payment();

        payment.setPaymentId(rs.getInt("payment_id"));
        payment.setOrderId(rs.getInt("order_id"));
        payment.setPaymentMethod(rs.getString("payment_method"));
        payment.setPaymentStatus(rs.getString("payment_status"));
        payment.setPaymentDate(rs.getTimestamp("payment_date"));

        return payment;
    }
}