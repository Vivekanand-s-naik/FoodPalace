package com.onlinefooddelivery.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.onlinefooddelivery.dao.ReviewDAO;
import com.onlinefooddelivery.dao.impl.ReviewDAOImpl;
import com.onlinefooddelivery.model.Review;

@WebServlet("/review")
public class ReviewServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private ReviewDAO reviewDAO;

    @Override
    public void init() throws ServletException {
        reviewDAO = new ReviewDAOImpl();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/auth/login.jsp");
            return;
        }

        int userId = (int) session.getAttribute("userId");

        String orderIdParam = request.getParameter("orderId");
        String restaurantIdParam = request.getParameter("restaurantId");
        String ratingParam = request.getParameter("rating");
        String comment = request.getParameter("comment");

        // Validate inputs
        if (orderIdParam == null || orderIdParam.trim().isEmpty() ||
            restaurantIdParam == null || restaurantIdParam.trim().isEmpty() ||
            ratingParam == null || ratingParam.trim().isEmpty()) {
            
            request.setAttribute("errorMessage", "All fields are required.");
            request.getRequestDispatcher("/customer/order-details.jsp").forward(request, response);
            return;
        }

        try {
            int restaurantId = Integer.parseInt(restaurantIdParam);
            int rating = Integer.parseInt(ratingParam);
            int orderId = Integer.parseInt(orderIdParam);

            // Create review
            Review review = new Review();
            review.setUserId(userId);
            review.setRestaurantId(restaurantId);
            review.setRating(rating);
            review.setComment(comment);

            boolean success = reviewDAO.addReview(review);

            if (success) {
                request.setAttribute("successMessage", "Thank you for your review!");
            } else {
                request.setAttribute("errorMessage", "Failed to submit review. Please try again.");
            }

            // Redirect back to order details
            response.sendRedirect(request.getContextPath() + "/order-details?orderId=" + orderId);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred. Please try again.");
            request.getRequestDispatcher("/customer/order-details.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/orders");
    }
}