package com.onlinefooddelivery.test;

import java.util.List;

import com.onlinefooddelivery.dao.OrderDAO;
import com.onlinefooddelivery.dao.impl.OrderDAOImpl;
import com.onlinefooddelivery.model.Order;

public class TestOrderDAO {

    private static OrderDAO dao = new OrderDAOImpl();

    public static void main(String[] args) {

        System.out.println("===== ORDER DAO TEST =====");

        testAddOrder();
        testGetAllOrders();
        testGetOrderById();
        testGetOrdersByUserId();
        testGetOrdersByRestaurantId();
        testGetOrdersByStatus();
        testUpdateOrder();
        testDeleteOrder();

    }

    public static void testAddOrder() {

        Order order = new Order();

        order.setUserId(1);
        order.setRestaurantId(1);
        order.setTotalAmount(599.00);
        order.setStatus("PLACED");

        boolean result = dao.addOrder(order);

        System.out.println("Order Added : " + result);

    }

    public static void testGetAllOrders() {

        System.out.println("\n===== ALL ORDERS =====");

        List<Order> orders = dao.getAllOrders();

        for (Order order : orders) {
            System.out.println(order);
        }

    }

    public static void testGetOrderById() {

        System.out.println("\n===== GET ORDER =====");

        System.out.println(dao.getOrderById(1));

    }

    public static void testGetOrdersByUserId() {

        System.out.println("\n===== USER ORDERS =====");

        List<Order> orders = dao.getOrdersByUserId(1);

        for (Order order : orders) {
            System.out.println(order);
        }

    }

    public static void testGetOrdersByRestaurantId() {

        System.out.println("\n===== RESTAURANT ORDERS =====");

        List<Order> orders = dao.getOrdersByRestaurantId(1);

        for (Order order : orders) {
            System.out.println(order);
        }

    }

    public static void testGetOrdersByStatus() {

        System.out.println("\n===== STATUS ORDERS =====");

        List<Order> orders = dao.getOrdersByStatus("PLACED");

        for (Order order : orders) {
            System.out.println(order);
        }

    }

    public static void testUpdateOrder() {

        System.out.println("\n===== UPDATE ORDER =====");

        Order order = dao.getOrderById(1);

        if (order != null) {

            order.setStatus("DELIVERED");

            boolean result = dao.updateOrder(order);

            System.out.println("Updated : " + result);

        }

    }

    public static void testDeleteOrder() {

        System.out.println("\n===== DELETE ORDER =====");

        boolean result = dao.deleteOrder(11);

        System.out.println("Deleted : " + result);

    }

}