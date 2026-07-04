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

@WebServlet("/admin/orders")
public class AdminOrderServlet extends HttpServlet {

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

        // Check admin session
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userRole") == null || 
            !"ADMIN".equals(session.getAttribute("userRole"))) {
            response.sendRedirect(request.getContextPath() + "/auth/login.jsp");
            return;
        }

        String action = request.getParameter("action");
        String search = request.getParameter("search");
        String statusFilter = request.getParameter("status");
        String dateFilter = request.getParameter("date");
        String pageParam = request.getParameter("page");

        int page = 1;
        int pageSize = 10;

        if (pageParam != null && !pageParam.isEmpty()) {
            try {
                page = Integer.parseInt(pageParam);
            } catch (NumberFormatException e) {
                page = 1;
            }
        }

        try {
            // Get all orders
            List<Order> orders = orderDAO.getAllOrders();

            // Filter by status
            if (statusFilter != null && !statusFilter.isEmpty() && orders != null) {
                orders = orders.stream()
                        .filter(o -> statusFilter.equals(o.getStatus()))
                        .toList();
            }

            // Filter by date
            if (dateFilter != null && !dateFilter.isEmpty() && orders != null) {
                // In production, filter by date using database query
                // For simplicity, we'll keep all orders
            }

            // Filter by search (order ID or customer name)
            // Note: customer name not directly in Order model
            if (search != null && !search.trim().isEmpty() && orders != null) {
                String searchTerm = search.trim().toLowerCase();
                orders = orders.stream()
                        .filter(o -> String.valueOf(o.getOrderId()).contains(searchTerm))
                        .toList();
            }

            // Calculate pending orders count
            int pendingOrders = 0;
            if (orders != null) {
                for (Order order : orders) {
                    if (!"DELIVERED".equals(order.getStatus()) && !"CANCELLED".equals(order.getStatus())) {
                        pendingOrders++;
                    }
                }
            }

            // Pagination
            int totalCount = orders != null ? orders.size() : 0;
            int totalPages = totalCount > 0 ? (int) Math.ceil((double) totalCount / pageSize) : 0;

            int startIndex = (page - 1) * pageSize;
            int endIndex = Math.min(startIndex + pageSize, totalCount);

            List<Order> paginatedList = null;
            if (orders != null && !orders.isEmpty() && startIndex < totalCount) {
                paginatedList = orders.subList(startIndex, endIndex);
            }

            // Set attributes
            request.setAttribute("orders", paginatedList);
            request.setAttribute("totalOrders", totalCount);
            request.setAttribute("pendingOrders", pendingOrders);
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("totalCount", totalCount);
            request.setAttribute("startIndex", totalCount > 0 ? startIndex + 1 : 0);
            request.setAttribute("endIndex", endIndex);
            request.setAttribute("search", search);
            request.setAttribute("status", statusFilter);
            request.setAttribute("date", dateFilter);

            request.getRequestDispatcher("/admin/orders.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Unable to load orders. Please try again.");
            request.getRequestDispatcher("/admin/orders.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Check admin session
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userRole") == null || 
            !"ADMIN".equals(session.getAttribute("userRole"))) {
            response.sendRedirect(request.getContextPath() + "/auth/login.jsp");
            return;
        }

        String action = request.getParameter("action");

        try {
            if ("updateStatus".equals(action)) {
                String orderIdParam = request.getParameter("orderId");
                String status = request.getParameter("status");

                if (orderIdParam != null && !orderIdParam.isEmpty() && status != null && !status.isEmpty()) {
                    int orderId = Integer.parseInt(orderIdParam);
                    Order order = orderDAO.getOrderById(orderId);
                    if (order != null) {
                        // Update order status
                        order.setStatus(status);
                        orderDAO.updateOrder(order);

                        // If order is delivered or cancelled, update payment status
                        if ("DELIVERED".equals(status) || "CANCELLED".equals(status)) {
                            Payment payment = paymentDAO.getPaymentByOrderId(orderId);
                            if (payment != null) {
                                if ("DELIVERED".equals(status)) {
                                    payment.setPaymentStatus("SUCCESS");
                                } else if ("CANCELLED".equals(status)) {
                                    payment.setPaymentStatus("FAILED");
                                }
                                paymentDAO.updatePayment(payment);
                            }
                        }
                    }
                }

                response.sendRedirect(request.getContextPath() + "/admin/orders");
                return;
            }

            // If order ID is provided, show order details
            String orderIdParam = request.getParameter("orderId");
            if (orderIdParam != null && !orderIdParam.isEmpty()) {
                int orderId = Integer.parseInt(orderIdParam);
                Order order = orderDAO.getOrderById(orderId);
                List<OrderItem> orderItems = orderItemDAO.getOrderItemsByOrderId(orderId);
                
                // Get payment details
                Payment payment = paymentDAO.getPaymentByOrderId(orderId);
                
                // Set attributes for order details modal
                request.setAttribute("order", order);
                request.setAttribute("orderItems", orderItems);
                request.setAttribute("payment", payment);
                
                request.getRequestDispatcher("/admin/order-details.jsp").forward(request, response);
                return;
            }

            response.sendRedirect(request.getContextPath() + "/admin/orders");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Unable to update order. Please try again.");
            request.getRequestDispatcher("/admin/orders.jsp").forward(request, response);
        }
    }
}