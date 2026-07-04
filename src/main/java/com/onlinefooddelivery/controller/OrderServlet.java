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
import com.onlinefooddelivery.dao.OrderItemDAO;
import com.onlinefooddelivery.dao.PaymentDAO;
import com.onlinefooddelivery.dao.impl.OrderDAOImpl;
import com.onlinefooddelivery.dao.impl.OrderItemDAOImpl;
import com.onlinefooddelivery.dao.impl.PaymentDAOImpl;
import com.onlinefooddelivery.model.Order;
import com.onlinefooddelivery.model.OrderItem;
import com.onlinefooddelivery.model.Payment;

@WebServlet("/orders")
public class OrderServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private OrderDAO orderDAO;
    private OrderItemDAO orderItemDAO;
    private PaymentDAO paymentDAO;

    @Override
    public void init() throws ServletException {
        orderDAO = new OrderDAOImpl();
        orderItemDAO = new OrderItemDAOImpl();
        paymentDAO = new PaymentDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/auth/login.jsp");
            return;
        }

        int userId = (int) session.getAttribute("userId");
        String action = request.getParameter("action");
        String statusFilter = request.getParameter("status");
        String pageParam = request.getParameter("page");

        int page = 1;
        int pageSize = 5;

        if (pageParam != null && !pageParam.isEmpty()) {
            try {
                page = Integer.parseInt(pageParam);
            } catch (NumberFormatException e) {
                page = 1;
            }
        }

        try {
            // Handle cancel order
            if ("cancel".equals(action)) {
                String orderIdParam = request.getParameter("orderId");
                if (orderIdParam != null) {
                    int orderId = Integer.parseInt(orderIdParam);
                    Order order = orderDAO.getOrderById(orderId);
                    if (order != null && order.getUserId() == userId) {
                        order.setStatus("CANCELLED");
                        orderDAO.updateOrder(order);
                        // Update payment status
                        Payment payment = paymentDAO.getPaymentByOrderId(orderId);
                        if (payment != null) {
                            payment.setPaymentStatus("FAILED");
                            paymentDAO.updatePayment(payment);
                        }
                    }
                }
                response.sendRedirect(request.getContextPath() + "/orders");
                return;
            }

            // Handle reorder
            if ("reorder".equals(action)) {
                String orderIdParam = request.getParameter("orderId");
                if (orderIdParam != null) {
                    int orderId = Integer.parseInt(orderIdParam);
                    // Get order items and add to cart
                    List<OrderItem> orderItems = orderItemDAO.getOrderItemsByOrderId(orderId);
                    // Logic to add items to cart (reuse CartServlet logic)
                    // For simplicity, redirect to menu
                    response.sendRedirect(request.getContextPath() + "/customer/menu.jsp");
                    return;
                }
            }

            // Get all orders for user
            List<Order> orders = orderDAO.getOrdersByUserId(userId);
            
            // Filter by status
            if (statusFilter != null && !statusFilter.isEmpty() && orders != null) {
                orders = orders.stream()
                        .filter(o -> statusFilter.equals(o.getStatus()))
                        .toList();
            }

            // Calculate statistics
            int totalOrders = orders != null ? orders.size() : 0;
            int completedOrders = 0;
            int activeOrders = 0;
            double totalSpent = 0;

            if (orders != null) {
                for (Order order : orders) {
                    if ("DELIVERED".equals(order.getStatus())) {
                        completedOrders++;
                        totalSpent += order.getTotalAmount();
                    } else if (!"CANCELLED".equals(order.getStatus())) {
                        activeOrders++;
                    }
                }
            }

            // Pagination
            int totalCount = orders != null ? orders.size() : 0;
            int totalPages = totalCount > 0 ? (int) Math.ceil((double) totalCount / pageSize) : 0;

            int startIndex = (page - 1) * pageSize;
            int endIndex = Math.min(startIndex + pageSize, totalCount);

            List<Order> paginatedOrders = null;
            if (orders != null && !orders.isEmpty() && startIndex < totalCount) {
                paginatedOrders = orders.subList(startIndex, endIndex);
            }

            // Set attributes
            request.setAttribute("orderList", paginatedOrders);
            request.setAttribute("totalOrders", totalOrders);
            request.setAttribute("completedOrders", completedOrders);
            request.setAttribute("activeOrders", activeOrders);
            request.setAttribute("totalSpent", totalSpent);
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("totalCount", totalCount);
            request.setAttribute("startIndex", totalCount > 0 ? startIndex + 1 : 0);
            request.setAttribute("endIndex", endIndex);
            request.setAttribute("status", statusFilter);

            request.getRequestDispatcher("/customer/orders.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Unable to load orders. Please try again.");
            request.getRequestDispatcher("/customer/orders.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}