package com.onlinefooddelivery.test;

import java.util.List;

import com.onlinefooddelivery.dao.ReviewDAO;
import com.onlinefooddelivery.dao.impl.ReviewDAOImpl;
import com.onlinefooddelivery.model.Review;

public class TestReviewDAO {

    private static ReviewDAO dao = new ReviewDAOImpl();

    public static void main(String[] args) {

        System.out.println("===== REVIEW DAO TEST =====");

        testAddReview();
        testGetReviewById();
        testGetReviewsByRestaurantId();
        testGetReviewsByUserId();
        testUpdateReview();
        testDeleteReview();

    }

    public static void testAddReview() {

        Review review = new Review();

        review.setUserId(1);
        review.setRestaurantId(1);
        review.setRating(5);
        review.setComment("Excellent Food");

        boolean result = dao.addReview(review);

        System.out.println("Review Added : " + result);

    }

    public static void testGetReviewById() {

        System.out.println("\n===== GET REVIEW =====");

        System.out.println(dao.getReviewById(1));

    }

    public static void testGetReviewsByRestaurantId() {

        System.out.println("\n===== RESTAURANT REVIEWS =====");

        List<Review> reviews = dao.getReviewsByRestaurantId(1);

        for (Review review : reviews) {
            System.out.println(review);
        }

    }

    public static void testGetReviewsByUserId() {

        System.out.println("\n===== USER REVIEWS =====");

        List<Review> reviews = dao.getReviewsByUserId(1);

        for (Review review : reviews) {
            System.out.println(review);
        }

    }

    public static void testUpdateReview() {

        Review review = dao.getReviewById(1);

        if (review != null) {

            review.setComment("Awesome Food");

            boolean result = dao.updateReview(review);

            System.out.println("Updated : " + result);

        }

    }

    public static void testDeleteReview() {

        boolean result = dao.deleteReview(11);

        System.out.println("Deleted : " + result);

    }

}