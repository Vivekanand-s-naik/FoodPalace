package com.onlinefooddelivery.dao;

import java.util.List;

import com.onlinefooddelivery.model.Order;

public interface OrderDAO {

    boolean addOrder(Order order);

    boolean updateOrder(Order order);

    boolean deleteOrder(int orderId);

    Order getOrderById(int orderId);

    List<Order> getAllOrders();

    List<Order> getOrdersByUserId(int userId);

    List<Order> getOrdersByRestaurantId(int restaurantId);

    List<Order> getOrdersByStatus(String status);

}