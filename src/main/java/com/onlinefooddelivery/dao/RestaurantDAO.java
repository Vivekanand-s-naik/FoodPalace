package com.onlinefooddelivery.dao;

import java.util.List;

import com.onlinefooddelivery.model.Restaurant;

public interface RestaurantDAO {

    boolean addRestaurant(Restaurant restaurant);

    boolean updateRestaurant(Restaurant restaurant);

    boolean deleteRestaurant(int restaurantId);

    Restaurant getRestaurantById(int restaurantId);

    List<Restaurant> getAllRestaurants();

    List<Restaurant> getRestaurantsByCuisine(String cuisine);

    List<Restaurant> getActiveRestaurants();

}