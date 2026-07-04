package com.onlinefooddelivery.model;

import java.sql.Timestamp;

public class Delivery {

    private int deliveryId;
    private int orderId;
    private String deliveryPartner;
    private String deliveryStatus;
    private Timestamp deliveredAt;

    public Delivery() {
    }

    public Delivery(int deliveryId, int orderId,
                    String deliveryPartner,
                    String deliveryStatus,
                    Timestamp deliveredAt) {
        this.deliveryId = deliveryId;
        this.orderId = orderId;
        this.deliveryPartner = deliveryPartner;
        this.deliveryStatus = deliveryStatus;
        this.deliveredAt = deliveredAt;
    }

    public int getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(int deliveryId) {
        this.deliveryId = deliveryId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getDeliveryPartner() {
        return deliveryPartner;
    }

    public void setDeliveryPartner(String deliveryPartner) {
        this.deliveryPartner = deliveryPartner;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public Timestamp getDeliveredAt() {
        return deliveredAt;
    }

    public void setDeliveredAt(Timestamp deliveredAt) {
        this.deliveredAt = deliveredAt;
    }

    @Override
    public String toString() {
        return "Delivery{" +
                "deliveryId=" + deliveryId +
                ", orderId=" + orderId +
                ", deliveryPartner='" + deliveryPartner + '\'' +
                ", deliveryStatus='" + deliveryStatus + '\'' +
                ", deliveredAt=" + deliveredAt +
                '}';
    }
}