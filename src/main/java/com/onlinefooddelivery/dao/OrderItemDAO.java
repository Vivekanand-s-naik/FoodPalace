package com.onlinefooddelivery.dao;

import java.util.List;

import com.onlinefooddelivery.model.OrderItem;

public interface OrderItemDAO {

    boolean addOrderItem(OrderItem orderItem);

    boolean updateOrderItem(OrderItem orderItem);

    boolean deleteOrderItem(int orderItemId);

    OrderItem getOrderItemById(int orderItemId);

    List<OrderItem> getOrderItemsByOrderId(int orderId);

}