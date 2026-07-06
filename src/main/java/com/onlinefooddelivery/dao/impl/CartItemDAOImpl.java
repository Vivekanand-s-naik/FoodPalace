package com.onlinefooddelivery.dao.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.onlinefooddelivery.dao.CartItemDAO;
import com.onlinefooddelivery.model.CartItem;
import com.onlinefooddelivery.util.DBConnection;

public class CartItemDAOImpl implements CartItemDAO {

    private static final String INSERT =
            "INSERT INTO cart_items(cart_id,item_id,quantity) VALUES(?,?,?)";

    private static final String UPDATE =
            "UPDATE cart_items SET cart_id=?, item_id=?, quantity=? WHERE cart_item_id=?";

    private static final String DELETE =
            "DELETE FROM cart_items WHERE cart_item_id=?";

    private static final String GET_BY_ID =
            "SELECT * FROM cart_items WHERE cart_item_id=?";

    // Updated: Join with menu_items to get item details
    private static final String GET_BY_CART =
            "SELECT ci.cart_item_id, ci.cart_id, ci.item_id, ci.quantity, " +
            "mi.item_name, mi.price, mi.image, mi.description, " +
            "mi.restaurant_id, COALESCE(r.name, 'Restaurant') AS restaurant_name " +
            "FROM cart_items ci " +
            "INNER JOIN menu_items mi ON ci.item_id = mi.item_id " +
            "LEFT JOIN restaurants r ON mi.restaurant_id = r.restaurant_id " +
            "WHERE ci.cart_id=? ORDER BY mi.restaurant_id, ci.cart_item_id";

    private static final String CLEAR_CART =
            "DELETE FROM cart_items WHERE cart_id=?";

    @Override
    public boolean addCartItem(CartItem cartItem) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT)) {

            ps.setInt(1, cartItem.getCartId());
            ps.setInt(2, cartItem.getItemId());
            ps.setInt(3, cartItem.getQuantity());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    
    @Override
    public boolean updateCartItem(CartItem cartItem) {

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(UPDATE)) {

            ps.setInt(1, cartItem.getCartId());
            ps.setInt(2, cartItem.getItemId());
            ps.setInt(3, cartItem.getQuantity());
            ps.setInt(4, cartItem.getCartItemId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean deleteCartItem(int cartItemId) {

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(DELETE)) {

            ps.setInt(1, cartItemId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public CartItem getCartItemById(int cartItemId) {

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(GET_BY_ID)) {

            ps.setInt(1, cartItemId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return extractCartItem(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<CartItem> getCartItemsByCartId(int cartId) {
        List<CartItem> cartItems = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(GET_BY_CART)) {
            ps.setInt(1, cartId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                cartItems.add(extractCartItemWithDetails(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cartItems;
    }

    
    @Override
    public boolean clearCart(int cartId) {

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(CLEAR_CART)) {

            ps.setInt(1, cartId);

            return ps.executeUpdate() >= 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Original extract method for basic cart item
    private CartItem extractCartItem(ResultSet rs) throws SQLException {

        CartItem cartItem = new CartItem();

        cartItem.setCartItemId(rs.getInt("cart_item_id"));
        cartItem.setCartId(rs.getInt("cart_id"));
        cartItem.setItemId(rs.getInt("item_id"));
        cartItem.setQuantity(rs.getInt("quantity"));

        return cartItem;
    }

    // New extract method with menu item details
    private CartItem extractCartItemWithDetails(ResultSet rs) throws SQLException {
        CartItem cartItem = new CartItem();
        cartItem.setCartItemId(rs.getInt("cart_item_id"));
        cartItem.setCartId(rs.getInt("cart_id"));
        cartItem.setItemId(rs.getInt("item_id"));
        cartItem.setQuantity(rs.getInt("quantity"));
        cartItem.setItemName(rs.getString("item_name"));
        cartItem.setPrice(rs.getDouble("price"));
        cartItem.setImagePath(rs.getString("image"));
        cartItem.setDescription(rs.getString("description"));
        cartItem.setRestaurantId(rs.getInt("restaurant_id"));
        cartItem.setRestaurantName(rs.getString("restaurant_name"));
        return cartItem;
    }
}