package com.onlinefooddelivery.controller;

import java.io.IOException;
import java.io.PrintWriter;
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
import com.onlinefooddelivery.dao.RestaurantDAO;
import com.onlinefooddelivery.dao.UserDAO;
import com.onlinefooddelivery.dao.impl.OrderDAOImpl;
import com.onlinefooddelivery.dao.impl.OrderItemDAOImpl;
import com.onlinefooddelivery.dao.impl.PaymentDAOImpl;
import com.onlinefooddelivery.dao.impl.RestaurantDAOImpl;
import com.onlinefooddelivery.dao.impl.UserDAOImpl;
import com.onlinefooddelivery.model.Order;
import com.onlinefooddelivery.model.OrderItem;
import com.onlinefooddelivery.model.Payment;
import com.onlinefooddelivery.model.Restaurant;
import com.onlinefooddelivery.model.User;

@WebServlet("/invoice")
public class InvoiceServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private OrderDAO orderDAO;
    private OrderItemDAO orderItemDAO;
    private PaymentDAO paymentDAO;
    private RestaurantDAO restaurantDAO;
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        orderDAO = new OrderDAOImpl();
        orderItemDAO = new OrderItemDAOImpl();
        paymentDAO = new PaymentDAOImpl();
        restaurantDAO = new RestaurantDAOImpl();
        userDAO = new UserDAOImpl();
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

            // Get user
            User user = userDAO.getUserById(order.getUserId());
            
            // Get restaurant
            Restaurant restaurant = restaurantDAO.getRestaurantById(order.getRestaurantId());
            
            // Get order items
            List<OrderItem> orderItems = orderItemDAO.getOrderItemsByOrderId(orderId);
            
            // Get payment
            Payment payment = paymentDAO.getPaymentByOrderId(orderId);

            // Set response type
            response.setContentType("text/html");
            response.setHeader("Content-Disposition", "attachment; filename=invoice_" + orderId + ".html");

            PrintWriter out = response.getWriter();
            
            // Generate HTML invoice
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<meta charset='UTF-8'>");
            out.println("<title>Invoice #" + orderId + "</title>");
            out.println("<style>");
            out.println("body { font-family: Arial, sans-serif; max-width: 800px; margin: 0 auto; padding: 20px; }");
            out.println(".header { text-align: center; border-bottom: 2px solid #FFC107; padding-bottom: 10px; }");
            out.println(".header h1 { color: #1A1A2E; margin: 0; }");
            out.println(".header p { color: #6C757D; margin: 5px 0; }");
            out.println(".section { margin: 20px 0; }");
            out.println(".section h3 { color: #1A1A2E; border-bottom: 1px solid #E5E5E5; padding-bottom: 5px; }");
            out.println("table { width: 100%; border-collapse: collapse; }");
            out.println("table th { background-color: #F8F9FA; text-align: left; padding: 10px; }");
            out.println("table td { padding: 10px; border-bottom: 1px solid #E5E5E5; }");
            out.println(".total { text-align: right; font-size: 1.2em; }");
            out.println(".total strong { color: #FFC107; }");
            out.println(".footer { text-align: center; margin-top: 30px; color: #6C757D; font-size: 0.9em; }");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");

            // Header
            out.println("<div class='header'>");
            out.println("<h1>OnlineFoodDelivery</h1>");
            out.println("<p>Invoice #" + orderId + " | Date: " + order.getOrderDate() + "</p>");
            out.println("</div>");

            // Customer Info
            out.println("<div class='section'>");
            out.println("<h3>Customer Information</h3>");
            out.println("<p><strong>Name:</strong> " + (user != null ? user.getFullName() : "N/A") + "</p>");
            out.println("<p><strong>Email:</strong> " + (user != null ? user.getEmail() : "N/A") + "</p>");
            out.println("<p><strong>Phone:</strong> " + (user != null ? user.getPhone() : "N/A") + "</p>");
            out.println("</div>");

            // Restaurant Info
            out.println("<div class='section'>");
            out.println("<h3>Restaurant Details</h3>");
            out.println("<p><strong>Name:</strong> " + (restaurant != null ? restaurant.getName() : "N/A") + "</p>");
            out.println("<p><strong>Cuisine:</strong> " + (restaurant != null ? restaurant.getCuisine() : "N/A") + "</p>");
            out.println("</div>");

            // Order Items
            out.println("<div class='section'>");
            out.println("<h3>Order Items</h3>");
            out.println("<table>");
            out.println("<tr><th>Item</th><th>Quantity</th><th>Price</th><th>Total</th></tr>");
            
            double subtotal = 0;
            if (orderItems != null) {
                for (OrderItem item : orderItems) {
                    double total = item.getQuantity() * item.getPrice();
                    subtotal += total;
                    out.println("<tr>");
                    out.println("<td>" + item.getItemName() + "</td>");
                    out.println("<td>" + item.getQuantity() + "</td>");
                    out.println("<td>₹ " + String.format("%.2f", item.getPrice()) + "</td>");
                    out.println("<td>₹ " + String.format("%.2f", total) + "</td>");
                    out.println("</tr>");
                }
            }
            out.println("</table>");
            out.println("</div>");

            // Totals
            double deliveryFee = 40.0;
            double tax = subtotal * 0.05;
            double grandTotal = subtotal + deliveryFee + tax;

            out.println("<div class='section total'>");
            out.println("<p><strong>Subtotal:</strong> ₹ " + String.format("%.2f", subtotal) + "</p>");
            out.println("<p><strong>Delivery Fee:</strong> ₹ " + String.format("%.2f", deliveryFee) + "</p>");
            out.println("<p><strong>Tax (GST):</strong> ₹ " + String.format("%.2f", tax) + "</p>");
            out.println("<hr>");
            out.println("<h2><strong>Grand Total:</strong> ₹ " + String.format("%.2f", grandTotal) + "</h2>");
            out.println("</div>");

            // Payment Info
            out.println("<div class='section'>");
            out.println("<h3>Payment Details</h3>");
            out.println("<p><strong>Method:</strong> " + (payment != null ? payment.getPaymentMethod() : "N/A") + "</p>");
            out.println("<p><strong>Status:</strong> " + (payment != null ? payment.getPaymentStatus() : "N/A") + "</p>");
            out.println("</div>");

            // Footer
            out.println("<div class='footer'>");
            out.println("<p>Thank you for ordering with OnlineFoodDelivery!</p>");
            out.println("<p>This is a system-generated invoice.</p>");
            out.println("</div>");

            out.println("</body>");
            out.println("</html>");

            out.close();

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/orders");
        }
    }
}