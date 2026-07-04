package com.onlinefooddelivery.dao;

import com.onlinefooddelivery.model.Cart;

public interface CartDAO {

    boolean addCart(Cart cart);

    boolean updateCart(Cart cart);

    boolean deleteCart(int cartId);

    Cart getCartById(int cartId);

    Cart getCartByUserId(int userId);

}