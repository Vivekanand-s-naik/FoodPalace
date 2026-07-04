package com.onlinefooddelivery.model;

public class CartItem {

    private int cartItemId;
    private int cartId;
    private int itemId;
    private int quantity;
    private double price;

    public CartItem() {
    }

    public CartItem(int cartItemId, int cartId, int itemId, int quantity) {
        this.cartItemId = cartItemId;
        this.cartId = cartId;
        this.itemId = itemId;
        this.quantity = quantity;
    }
    
    public double getTotalPrice() {
        return price * quantity;
    }
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    public int getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(int cartItemId) {
        this.cartItemId = cartItemId;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "cartItemId=" + cartItemId +
                ", cartId=" + cartId +
                ", itemId=" + itemId +
                ", quantity=" + quantity +
                '}';
    }
}