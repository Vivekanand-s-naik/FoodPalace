package com.onlinefooddelivery.dao;

import java.util.List;

import com.onlinefooddelivery.model.Delivery;

public interface DeliveryDAO {

    boolean addDelivery(Delivery delivery);

    boolean updateDelivery(Delivery delivery);

    boolean deleteDelivery(int deliveryId);

    Delivery getDeliveryById(int deliveryId);

    Delivery getDeliveryByOrderId(int orderId);

    List<Delivery> getAllDeliveries();

}