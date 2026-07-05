package com.onlinefooddelivery.test;

import java.util.List;

import com.onlinefooddelivery.dao.MenuItemDAO;
import com.onlinefooddelivery.dao.impl.MenuItemDAOImpl;
import com.onlinefooddelivery.model.MenuItem;

public class TestMenuItemDAO {

    private static MenuItemDAO dao = new MenuItemDAOImpl();

    public static void main(String[] args) {

        System.out.println("===== MENU DAO TEST =====");

        testAddMenuItem();
        testGetAllMenuItems();
        testGetMenuItemById();
        testGetMenuByRestaurant();
        testGetAvailableItems();
        testUpdateMenuItem();
        testDeleteMenuItem();

    }

    public static void testAddMenuItem() {

        MenuItem item = new MenuItem();

        item.setRestaurantId(1);
        item.setItemName("Veg Pizza");
        item.setDescription("Cheesy Pizza");
        item.setPrice(249);
        item.setCategory("Pizza");
        item.setAvailable(true);
        item.setImagePath("pizza.jpg");

        boolean result = dao.addMenuItem(item);

        System.out.println("Added : " + result);
    }

    public static void testGetAllMenuItems() {

        System.out.println("\n===== ALL MENU ITEMS =====");

        List<MenuItem> items = dao.getAllMenuItems();

        for (MenuItem item : items) {
            System.out.println(item);
        }

    }

    public static void testGetMenuItemById() {

        System.out.println("\n===== GET MENU BY ID =====");

        System.out.println(dao.getMenuItemById(1));

    }

    public static void testGetMenuByRestaurant() {

        System.out.println("\n===== RESTAURANT MENU =====");

        List<MenuItem> items =
                dao.getMenuItemsByRestaurantId(1);

        for (MenuItem item : items) {
            System.out.println(item);
        }

    }

    public static void testGetAvailableItems() {

        System.out.println("\n===== AVAILABLE ITEMS =====");

        List<MenuItem> items =
                dao.getAvailableMenuItems();

        for (MenuItem item : items) {
            System.out.println(item);
        }

    }

    public static void testUpdateMenuItem() {

        System.out.println("\n===== UPDATE MENU =====");

        MenuItem item = dao.getMenuItemById(1);

        if (item != null) {

            item.setPrice(299);

            boolean result =
                    dao.updateMenuItem(item);

            System.out.println("Updated : " + result);

        }

    }

    public static void testDeleteMenuItem() {

        System.out.println("\n===== DELETE MENU =====");

        boolean result =
                dao.deleteMenuItem(11);

        System.out.println("Deleted : " + result);

    }

}