package com.onlinefooddelivery.model;

public class Restaurant {

    private int restaurantId;
    private String name;
    private String ownerName;
    private String email;
    private String phone;
    private String address;
    private String cuisine;
    private double rating;
    private boolean active;

    public Restaurant() {
    }

    public Restaurant(int restaurantId, String name, String ownerName,
                      String email, String phone, String address,
                      String cuisine, double rating, boolean active) {
        this.restaurantId = restaurantId;
        this.name = name;
        this.ownerName = ownerName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.cuisine = cuisine;
        this.rating = rating;
        this.active = active;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "restaurantId=" + restaurantId +
                ", name='" + name + '\'' +
                ", ownerName='" + ownerName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", cuisine='" + cuisine + '\'' +
                ", rating=" + rating +
                ", active=" + active +
                '}';
    }
}