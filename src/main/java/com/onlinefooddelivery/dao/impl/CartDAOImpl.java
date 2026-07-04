package com.onlinefooddelivery.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.onlinefooddelivery.dao.CartDAO;
import com.onlinefooddelivery.model.Cart;
import com.onlinefooddelivery.util.DBConnection;

public class CartDAOImpl implements CartDAO {

    private static final String INSERT =
            "INSERT INTO cart(user_id) VALUES(?)";

    private static final String UPDATE =
            "UPDATE cart SET user_id=? WHERE cart_id=?";

    private static final String DELETE =
            "DELETE FROM cart WHERE cart_id=?";

    private static final String GET_BY_ID =
            "SELECT * FROM cart WHERE cart_id=?";

    private static final String GET_BY_USER =
            "SELECT * FROM cart WHERE user_id=?";

    @Override
    public boolean addCart(Cart cart) {

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT)) {

            ps.setInt(1, cart.getUserId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean updateCart(Cart cart) {

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(UPDATE)) {

            ps.setInt(1, cart.getUserId());
            ps.setInt(2, cart.getCartId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean deleteCart(int cartId) {

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(DELETE)) {

            ps.setInt(1, cartId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public Cart getCartById(int cartId) {

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(GET_BY_ID)) {

            ps.setInt(1, cartId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return extractCart(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Cart getCartByUserId(int userId) {

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(GET_BY_USER)) {

            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return extractCart(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private Cart extractCart(ResultSet rs) throws SQLException {

        Cart cart = new Cart();

        cart.setCartId(rs.getInt("cart_id"));
        cart.setUserId(rs.getInt("user_id"));
        cart.setCreatedAt(rs.getTimestamp("created_at"));

        return cart;
    }
}