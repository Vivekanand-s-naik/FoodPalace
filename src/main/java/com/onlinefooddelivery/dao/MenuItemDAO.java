package com.onlinefooddelivery.dao;

import java.util.List;

import com.onlinefooddelivery.model.MenuItem;

public interface MenuItemDAO {

    boolean addMenuItem(MenuItem menuItem);

    boolean updateMenuItem(MenuItem menuItem);

    boolean deleteMenuItem(int itemId);

    MenuItem getMenuItemById(int itemId);

    List<MenuItem> getAllMenuItems();

    List<MenuItem> getMenuItemsByRestaurantId(int restaurantId);

    List<MenuItem> getAvailableMenuItems();
    
    List<MenuItem> getMenuItemsByCategory(int restaurantId, String category);

    List<String> getCategoriesByRestaurantId(int restaurantId);

}