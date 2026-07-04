package com.onlinefooddelivery.test;

import java.util.List;

import com.onlinefooddelivery.dao.DeliveryDAO;
import com.onlinefooddelivery.dao.impl.DeliveryDAOImpl;
import com.onlinefooddelivery.model.Delivery;

public class TestDeliveryDAO {

    private static DeliveryDAO dao = new DeliveryDAOImpl();

    public static void main(String[] args) {

        System.out.println("===== DELIVERY DAO TEST =====");

        testAddDelivery();
        testGetDeliveryById();
        testGetDeliveryByOrderId();
        testGetAllDeliveries();
        testUpdateDelivery();
        testDeleteDelivery();

    }

    public static void testAddDelivery() {

        Delivery delivery = new Delivery();

        delivery.setOrderId(1);
        delivery.setDeliveryPartner("Ramesh");
        delivery.setDeliveryStatus("ASSIGNED");

        boolean result = dao.addDelivery(delivery);

        System.out.println("Added : " + result);

    }

    public static void testGetDeliveryById() {

        System.out.println(dao.getDeliveryById(1));

    }

    public static void testGetDeliveryByOrderId() {

        System.out.println(dao.getDeliveryByOrderId(1));

    }

    public static void testGetAllDeliveries() {

        List<Delivery> deliveries = dao.getAllDeliveries();

        for (Delivery delivery : deliveries) {
            System.out.println(delivery);
        }

    }

    public static void testUpdateDelivery() {

        Delivery delivery = dao.getDeliveryById(1);

        if (delivery != null) {

            delivery.setDeliveryStatus("DELIVERED");

            boolean result = dao.updateDelivery(delivery);

            System.out.println("Updated : " + result);

        }

    }

    public static void testDeleteDelivery() {

        boolean result = dao.deleteDelivery(11);

        System.out.println("Deleted : " + result);

    }

}