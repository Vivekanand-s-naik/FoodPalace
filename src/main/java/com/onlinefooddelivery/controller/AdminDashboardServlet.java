package com.onlinefooddelivery.controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.onlinefooddelivery.dao.OrderDAO;
import com.onlinefooddelivery.dao.RestaurantDAO;
import com.onlinefooddelivery.dao.UserDAO;
import com.onlinefooddelivery.dao.impl.OrderDAOImpl;
import com.onlinefooddelivery.dao.impl.RestaurantDAOImpl;
import com.onlinefooddelivery.dao.impl.UserDAOImpl;
import com.onlinefooddelivery.model.Order;
import com.onlinefooddelivery.model.Restaurant;
import com.onlinefooddelivery.model.User;

@WebServlet("/admin/dashboard")
public class AdminDashboardServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private OrderDAO orderDAO;
    private RestaurantDAO restaurantDAO;
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        orderDAO = new OrderDAOImpl();
        restaurantDAO = new RestaurantDAOImpl();
        userDAO = new UserDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Check admin session
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userRole") == null || 
            !"ADMIN".equals(session.getAttribute("userRole"))) {
            response.sendRedirect(request.getContextPath() + "/auth/login.jsp");
            return;
        }

        try {
            // Get statistics
            List<Order> allOrders = orderDAO.getAllOrders();
            List<Restaurant> allRestaurants = restaurantDAO.getAllRestaurants();
            List<User> allUsers = userDAO.getAllUsers();

            // Calculate totals
            int totalOrders = allOrders != null ? allOrders.size() : 0;
            double totalRevenue = 0;
            int activeRestaurants = 0;
            int totalUsers = allUsers != null ? allUsers.size() : 0;

            if (allOrders != null) {
                for (Order order : allOrders) {
                    if ("DELIVERED".equals(order.getStatus())) {
                        totalRevenue += order.getTotalAmount();
                    }
                }
            }

            if (allRestaurants != null) {
                for (Restaurant restaurant : allRestaurants) {
                    if (restaurant.isActive()) {
                        activeRestaurants++;
                    }
                }
            }

            // Get recent orders (last 5)
            List<Order> recentOrders = null;
            if (allOrders != null && allOrders.size() > 0) {
                int count = Math.min(5, allOrders.size());
                recentOrders = allOrders.subList(0, count);
            }

            // Get order trends (last 7 days) - simplified
            // In production, this would query the database with date filtering
            String[] trendLabels = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
            int[] trendData = {12, 18, 15, 22, 28, 35, 20};

            // Get top categories - simplified
            // In production, this would aggregate from order items
            String[][] categories = {
                {"Pizza", "25"},
                {"Biryani", "22"},
                {"Burgers", "18"},
                {"Chinese", "15"},
                {"Desserts", "10"}
            };

            // Set attributes
            request.setAttribute("totalOrders", totalOrders);
            request.setAttribute("totalRevenue", String.format("%.2f", totalRevenue));
            request.setAttribute("activeRestaurants", activeRestaurants);
            request.setAttribute("totalUsers", totalUsers);
            request.setAttribute("recentOrders", recentOrders);
            request.setAttribute("orderTrendLabels", "\"" + String.join("\",\"", trendLabels) + "\"");
            request.setAttribute("orderTrendData", "[12,18,15,22,28,35,20]");
            request.setAttribute("topCategories", categories);

            request.getRequestDispatcher("/admin/dashboard.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Unable to load dashboard. Please try again.");
            request.getRequestDispatcher("/admin/dashboard.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}