package com.onlinefooddelivery.model;

public class CartItem {

    private int cartItemId;
    private int cartId;
    private int itemId;
    private int quantity;
    private double price;
    private String imagePath;  
    private String description; 
    private String itemName;


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

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

    
    @Override
    public String toString() {
        return "CartItem{" +
        		"itemName=" + itemName + 
                "cartItemId=" + cartItemId +
                ", cartId=" + cartId +
                ", itemId=" + itemId +
                ", quantity=" + quantity +
                '}';
    }
}