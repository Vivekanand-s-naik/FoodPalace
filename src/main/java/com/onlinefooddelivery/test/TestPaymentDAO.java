package com.onlinefooddelivery.test;

import java.util.List;

import com.onlinefooddelivery.dao.PaymentDAO;
import com.onlinefooddelivery.dao.impl.PaymentDAOImpl;
import com.onlinefooddelivery.model.Payment;

public class TestPaymentDAO {

    private static PaymentDAO dao = new PaymentDAOImpl();

    public static void main(String[] args) {

        System.out.println("===== PAYMENT DAO TEST =====");

        testAddPayment();
        testGetPaymentById();
        testGetPaymentByOrderId();
        testGetAllPayments();
        testUpdatePayment();
        testDeletePayment();

    }

    public static void testAddPayment() {

        Payment payment = new Payment();

        payment.setOrderId(1);
        payment.setPaymentMethod("UPI");
        payment.setPaymentStatus("SUCCESS");

        boolean result = dao.addPayment(payment);

        System.out.println("Payment Added : " + result);

    }

    public static void testGetPaymentById() {

        System.out.println("\n===== GET PAYMENT =====");

        System.out.println(dao.getPaymentById(1));

    }

    public static void testGetPaymentByOrderId() {

        System.out.println("\n===== GET PAYMENT BY ORDER =====");

        System.out.println(dao.getPaymentByOrderId(1));

    }

    public static void testGetAllPayments() {

        System.out.println("\n===== ALL PAYMENTS =====");

        List<Payment> payments = dao.getAllPayments();

        for (Payment payment : payments) {
            System.out.println(payment);
        }

    }

    public static void testUpdatePayment() {

        System.out.println("\n===== UPDATE PAYMENT =====");

        Payment payment = dao.getPaymentById(1);

        if (payment != null) {

            payment.setPaymentStatus("FAILED");

            boolean result = dao.updatePayment(payment);

            System.out.println("Updated : " + result);

        }

    }

    public static void testDeletePayment() {

        System.out.println("\n===== DELETE PAYMENT =====");

        boolean result = dao.deletePayment(11);

        System.out.println("Deleted : " + result);

    }

}