package com.onlinefooddelivery.test;

import java.util.List;

import com.onlinefooddelivery.dao.RestaurantDAO;
import com.onlinefooddelivery.dao.impl.RestaurantDAOImpl;
import com.onlinefooddelivery.model.Restaurant;

public class TestRestaurantDAO {

    private static RestaurantDAO dao = new RestaurantDAOImpl();

    public static void main(String[] args) {

        System.out.println("===== RESTAURANT DAO TEST =====");

        testAddRestaurant();
        testGetAllRestaurants();
        testGetRestaurantById();
        testGetRestaurantByCuisine();
        testGetActiveRestaurants();
        testUpdateRestaurant();
        testDeleteRestaurant();

    }

    public static void testAddRestaurant() {

        Restaurant restaurant = new Restaurant();

        restaurant.setName("Spice Garden");
        restaurant.setOwnerName("Rajesh Kumar");
        restaurant.setEmail("spicegarden@test.com");
        restaurant.setPhone("9876543210");
        restaurant.setAddress("MG Road, Bangalore");
        restaurant.setCuisine("Indian");
        restaurant.setRating(4.5);
        restaurant.setActive(true);

        boolean result = dao.addRestaurant(restaurant);

        System.out.println("Add Restaurant : " + result);
    }

    public static void testGetAllRestaurants() {

        System.out.println("\n===== ALL RESTAURANTS =====");

        List<Restaurant> restaurants = dao.getAllRestaurants();

        for (Restaurant restaurant : restaurants) {
            System.out.println(restaurant);
        }
    }

    public static void testGetRestaurantById() {

        System.out.println("\n===== GET RESTAURANT BY ID =====");

        Restaurant restaurant = dao.getRestaurantById(1);

        System.out.println(restaurant);
    }

    public static void testGetRestaurantByCuisine() {

        System.out.println("\n===== GET BY CUISINE =====");

        List<Restaurant> restaurants =
                dao.getRestaurantsByCuisine("Indian");

        for (Restaurant restaurant : restaurants) {
            System.out.println(restaurant);
        }
    }

    public static void testGetActiveRestaurants() {

        System.out.println("\n===== ACTIVE RESTAURANTS =====");

        List<Restaurant> restaurants =
                dao.getActiveRestaurants();

        for (Restaurant restaurant : restaurants) {
            System.out.println(restaurant);
        }
    }

    public static void testUpdateRestaurant() {

        System.out.println("\n===== UPDATE RESTAURANT =====");

        Restaurant restaurant = dao.getRestaurantById(1);

        if (restaurant != null) {

            restaurant.setRating(4.9);

            boolean result = dao.updateRestaurant(restaurant);

            System.out.println("Updated : " + result);
        }
    }

    public static void testDeleteRestaurant() {

        System.out.println("\n===== DELETE RESTAURANT =====");

        boolean result = dao.deleteRestaurant(11);

        System.out.println("Deleted : " + result);
    }

}