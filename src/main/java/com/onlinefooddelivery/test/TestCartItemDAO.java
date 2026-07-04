package com.onlinefooddelivery.test;

import java.util.List;

import com.onlinefooddelivery.dao.CartItemDAO;
import com.onlinefooddelivery.dao.impl.CartItemDAOImpl;
import com.onlinefooddelivery.model.CartItem;

public class TestCartItemDAO {

    private static CartItemDAO dao = new CartItemDAOImpl();

    public static void main(String[] args) {

        System.out.println("===== CART ITEM DAO TEST =====");

        testAddCartItem();
        testGetCartItemById();
        testGetCartItemsByCartId();
        testUpdateCartItem();
        testClearCart();
        testDeleteCartItem();

    }

    public static void testAddCartItem() {

        CartItem item = new CartItem();

        item.setCartId(1);
        item.setItemId(1);
        item.setQuantity(2);

        boolean result = dao.addCartItem(item);

        System.out.println("Added : " + result);

    }

    public static void testGetCartItemById() {

        System.out.println("\n===== GET CART ITEM =====");

        System.out.println(dao.getCartItemById(1));

    }

    public static void testGetCartItemsByCartId() {

        System.out.println("\n===== CART ITEMS =====");

        List<CartItem> items = dao.getCartItemsByCartId(1);

        for (CartItem item : items) {

            System.out.println(item);

        }

    }

    public static void testUpdateCartItem() {

        System.out.println("\n===== UPDATE CART ITEM =====");

        CartItem item = dao.getCartItemById(1);

        if (item != null) {

            item.setQuantity(5);

            boolean result = dao.updateCartItem(item);

            System.out.println("Updated : " + result);

        }

    }

    public static void testClearCart() {

        System.out.println("\n===== CLEAR CART =====");

        boolean result = dao.clearCart(1);

        System.out.println("Cart Cleared : " + result);

    }

    public static void testDeleteCartItem() {

        System.out.println("\n===== DELETE CART ITEM =====");

        boolean result = dao.deleteCartItem(11);

        System.out.println("Deleted : " + result);

    }

}