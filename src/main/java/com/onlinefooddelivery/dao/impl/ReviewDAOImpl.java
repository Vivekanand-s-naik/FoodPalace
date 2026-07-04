package com.onlinefooddelivery.dao.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.onlinefooddelivery.dao.ReviewDAO;
import com.onlinefooddelivery.model.Review;
import com.onlinefooddelivery.util.DBConnection;

public class ReviewDAOImpl implements ReviewDAO {

    private static final String INSERT =
            "INSERT INTO reviews(user_id, restaurant_id, rating, comment) VALUES(?,?,?,?)";

    private static final String UPDATE =
            "UPDATE reviews SET user_id=?, restaurant_id=?, rating=?, comment=? WHERE review_id=?";

    private static final String DELETE =
            "DELETE FROM reviews WHERE review_id=?";

    private static final String GET_BY_ID =
            "SELECT * FROM reviews WHERE review_id=?";

    private static final String GET_BY_RESTAURANT =
            "SELECT * FROM reviews WHERE restaurant_id=?";

    private static final String GET_BY_USER =
            "SELECT * FROM reviews WHERE user_id=?";

    @Override
    public boolean addReview(Review review) {

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT)) {

            ps.setInt(1, review.getUserId());
            ps.setInt(2, review.getRestaurantId());
            ps.setInt(3, review.getRating());
            ps.setString(4, review.getComment());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean updateReview(Review review) {

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(UPDATE)) {

            ps.setInt(1, review.getUserId());
            ps.setInt(2, review.getRestaurantId());
            ps.setInt(3, review.getRating());
            ps.setString(4, review.getComment());
            ps.setInt(5, review.getReviewId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean deleteReview(int reviewId) {

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(DELETE)) {

            ps.setInt(1, reviewId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public Review getReviewById(int reviewId) {

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(GET_BY_ID)) {

            ps.setInt(1, reviewId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return extractReview(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Review> getReviewsByRestaurantId(int restaurantId) {

        List<Review> reviews = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(GET_BY_RESTAURANT)) {

            ps.setInt(1, restaurantId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                reviews.add(extractReview(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reviews;
    }

    @Override
    public List<Review> getReviewsByUserId(int userId) {

        List<Review> reviews = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(GET_BY_USER)) {

            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                reviews.add(extractReview(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reviews;
    }

    private Review extractReview(ResultSet rs) throws SQLException {

        Review review = new Review();

        review.setReviewId(rs.getInt("review_id"));
        review.setUserId(rs.getInt("user_id"));
        review.setRestaurantId(rs.getInt("restaurant_id"));
        review.setRating(rs.getInt("rating"));
        review.setComment(rs.getString("comment"));
        review.setReviewDate(rs.getTimestamp("review_date"));

        return review;
    }
}