package com.onlinefooddelivery.controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.onlinefooddelivery.dao.DeliveryDAO;
import com.onlinefooddelivery.dao.OrderDAO;
import com.onlinefooddelivery.dao.OrderItemDAO;
import com.onlinefooddelivery.dao.PaymentDAO;
import com.onlinefooddelivery.dao.RestaurantDAO;
import com.onlinefooddelivery.dao.ReviewDAO;
import com.onlinefooddelivery.dao.impl.DeliveryDAOImpl;
import com.onlinefooddelivery.dao.impl.OrderDAOImpl;
import com.onlinefooddelivery.dao.impl.OrderItemDAOImpl;
import com.onlinefooddelivery.dao.impl.PaymentDAOImpl;
import com.onlinefooddelivery.dao.impl.RestaurantDAOImpl;
import com.onlinefooddelivery.dao.impl.ReviewDAOImpl;
import com.onlinefooddelivery.model.Delivery;
import com.onlinefooddelivery.model.Order;
import com.onlinefooddelivery.model.OrderItem;
import com.onlinefooddelivery.model.Payment;
import com.onlinefooddelivery.model.Restaurant;
import com.onlinefooddelivery.model.Review;

@WebServlet("/order-details")
public class OrderDetailsServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private OrderDAO orderDAO;
    private OrderItemDAO orderItemDAO;
    private PaymentDAO paymentDAO;
    private DeliveryDAO deliveryDAO;
    private RestaurantDAO restaurantDAO;
    private ReviewDAO reviewDAO;

    @Override
    public void init() throws ServletException {
        orderDAO = new OrderDAOImpl();
        orderItemDAO = new OrderItemDAOImpl();
        paymentDAO = new PaymentDAOImpl();
        deliveryDAO = new DeliveryDAOImpl();
        restaurantDAO = new RestaurantDAOImpl();
        reviewDAO = new ReviewDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/auth/login.jsp");
            return;
        }

        String orderIdParam = request.getParameter("orderId");
        if (orderIdParam == null || orderIdParam.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/orders");
            return;
        }

        int orderId;
        try {
            orderId = Integer.parseInt(orderIdParam.trim());
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/orders");
            return;
        }

        try {
            // Get order details
            Order order = orderDAO.getOrderById(orderId);
            if (order == null) {
                response.sendRedirect(request.getContextPath() + "/orders");
                return;
            }

            // Get order items
            List<OrderItem> orderItems = orderItemDAO.getOrderItemsByOrderId(orderId);

            // Get payment details
            Payment payment = paymentDAO.getPaymentByOrderId(orderId);

            // Get delivery details
            Delivery delivery = deliveryDAO.getDeliveryByOrderId(orderId);

            // Get restaurant details
            Restaurant restaurant = restaurantDAO.getRestaurantById(order.getRestaurantId());

            // Check if user has already reviewed
            int userId = (int) session.getAttribute("userId");
            List<Review> userReviews = reviewDAO.getReviewsByUserId(userId);
            Review existingReview = null;
            if (userReviews != null) {
                for (Review review : userReviews) {
                    if (review.getRestaurantId() == order.getRestaurantId()) {
                        existingReview = review;
                        break;
                    }
                }
            }

            // Calculate order totals
            double subtotal = 0;
            if (orderItems != null) {
                for (OrderItem item : orderItems) {
                    subtotal += item.getTotalPrice();
                }
            }
            double deliveryFee = 40.0;
            double tax = subtotal * 0.05;

            // Set attributes
            request.setAttribute("order", order);
            request.setAttribute("orderItems", orderItems);
            request.setAttribute("payment", payment);
            request.setAttribute("delivery", delivery);
            request.setAttribute("restaurant", restaurant);
            request.setAttribute("existingReview", existingReview);
            request.setAttribute("subtotal", subtotal);
            request.setAttribute("deliveryFee", deliveryFee);
            request.setAttribute("tax", tax);

            // Format dates for timeline
            request.setAttribute("orderDate", order.getOrderDate());
            // These would be populated from actual data if available
            request.setAttribute("preparingTime", order.getOrderDate());
            request.setAttribute("outForDeliveryTime", order.getOrderDate());
            request.setAttribute("deliveredTime", order.getOrderDate());
            request.setAttribute("cancelledTime", order.getOrderDate());

            request.getRequestDispatcher("/customer/order-details.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Unable to load order details. Please try again.");
            request.getRequestDispatcher("/customer/order-details.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}