package com.onlinefooddelivery.dao;

import java.util.List;

import com.onlinefooddelivery.model.CartItem;

public interface CartItemDAO {

    boolean addCartItem(CartItem cartItem);

    boolean updateCartItem(CartItem cartItem);

    boolean deleteCartItem(int cartItemId);

    CartItem getCartItemById(int cartItemId);

    List<CartItem> getCartItemsByCartId(int cartId);

    boolean clearCart(int cartId);

}