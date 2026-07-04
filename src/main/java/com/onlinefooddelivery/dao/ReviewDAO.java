package com.onlinefooddelivery.dao;

import java.util.List;

import com.onlinefooddelivery.model.Review;

public interface ReviewDAO {

    boolean addReview(Review review);

    boolean updateReview(Review review);

    boolean deleteReview(int reviewId);

    Review getReviewById(int reviewId);

    List<Review> getReviewsByRestaurantId(int restaurantId);

    List<Review> getReviewsByUserId(int userId);

}