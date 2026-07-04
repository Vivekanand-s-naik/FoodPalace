package com.onlinefooddelivery.test;

import com.onlinefooddelivery.dao.CartDAO;
import com.onlinefooddelivery.dao.impl.CartDAOImpl;
import com.onlinefooddelivery.model.Cart;

/**
 * 
 */
public class TestCartDAO {

    private static CartDAO dao = new CartDAOImpl();

    public static void main(String[] args) {

        System.out.println("===== CART DAO TEST =====");

        testAddCart();
        testGetCartById();
        testGetCartByUserId();
        testUpdateCart();
        testDeleteCart();

    }

    public static void testAddCart() {

        Cart cart = new Cart();

        cart.setUserId(1);

        boolean result = dao.addCart(cart);

        System.out.println("Cart Added : " + result);

    }

    public static void testGetCartById() {

        System.out.println("\n===== GET CART =====");

        System.out.println(dao.getCartById(1));

    }

    public static void testGetCartByUserId() {

        System.out.println("\n===== GET USER CART =====");

        System.out.println(dao.getCartByUserId(1));

    }

    public static void testUpdateCart() {

        System.out.println("\n===== UPDATE CART =====");

        Cart cart = dao.getCartById(1);

        if (cart != null) {

            cart.setUserId(2);

            boolean result = dao.updateCart(cart);

            System.out.println("Updated : " + result);

        }

    }

    public static void testDeleteCart() {

        System.out.println("\n===== DELETE CART =====");

        boolean result = dao.deleteCart(11);

        System.out.println("Deleted : " + result);

    }

}