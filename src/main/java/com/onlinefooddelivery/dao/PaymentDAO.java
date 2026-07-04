package com.onlinefooddelivery.dao;

import java.util.List;

import com.onlinefooddelivery.model.Payment;

public interface PaymentDAO {

    boolean addPayment(Payment payment);

    boolean updatePayment(Payment payment);

    boolean deletePayment(int paymentId);

    Payment getPaymentById(int paymentId);

    Payment getPaymentByOrderId(int orderId);

    List<Payment> getAllPayments();

}