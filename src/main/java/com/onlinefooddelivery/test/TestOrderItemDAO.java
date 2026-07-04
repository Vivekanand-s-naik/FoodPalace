package com.onlinefooddelivery.test;

import java.util.List;

import com.onlinefooddelivery.dao.OrderItemDAO;
import com.onlinefooddelivery.dao.impl.OrderItemDAOImpl;
import com.onlinefooddelivery.model.OrderItem;

public class TestOrderItemDAO {

    private static OrderItemDAO dao = new OrderItemDAOImpl();

    public static void main(String[] args) {

        System.out.println("===== ORDER ITEM DAO TEST =====");

        testAddOrderItem();
        testGetOrderItemById();
        testGetOrderItemsByOrderId();
        testUpdateOrderItem();
        testDeleteOrderItem();

    }

    public static void testAddOrderItem() {

        OrderItem item = new OrderItem();

        item.setOrderId(1);
        item.setItemId(1);
        item.setQuantity(2);
        item.setPrice(299.00);

        boolean result = dao.addOrderItem(item);

        System.out.println("Added : " + result);

    }

    public static void testGetOrderItemById() {

        System.out.println("\n===== GET ORDER ITEM =====");

        System.out.println(dao.getOrderItemById(1));

    }

    public static void testGetOrderItemsByOrderId() {

        System.out.println("\n===== ORDER ITEMS =====");

        List<OrderItem> items = dao.getOrderItemsByOrderId(1);

        for (OrderItem item : items) {
            System.out.println(item);
        }

    }

    public static void testUpdateOrderItem() {

        System.out.println("\n===== UPDATE ORDER ITEM =====");

        OrderItem item = dao.getOrderItemById(1);

        if (item != null) {

            item.setQuantity(5);

            boolean result = dao.updateOrderItem(item);

            System.out.println("Updated : " + result);

        }

    }

    public static void testDeleteOrderItem() {

        System.out.println("\n===== DELETE ORDER ITEM =====");

        boolean result = dao.deleteOrderItem(11);

        System.out.println("Deleted : " + result);

    }

}