package com.onlinefooddelivery.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/coupon")
public class CouponServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // Simple coupon codes for demo
    private static final String[][] COUPONS = {
        {"SAVE10", "10"},
        {"SAVE20", "20"},
        {"WELCOME", "15"},
        {"FESTIVAL", "25"},
        {"FLAT50", "50"}
    };

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/auth/login.jsp");
            return;
        }

        String couponCode = request.getParameter("couponCode");
        
        if (couponCode == null || couponCode.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Please enter a coupon code.");
            request.getRequestDispatcher("/customer/cart.jsp").forward(request, response);
            return;
        }

        // Validate coupon
        String discount = null;
        for (String[] coupon : COUPONS) {
            if (coupon[0].equalsIgnoreCase(couponCode.trim())) {
                discount = coupon[1];
                break;
            }
        }

        if (discount != null) {
            // Apply discount to session
            session.setAttribute("discount", Double.parseDouble(discount));
            session.setAttribute("couponCode", couponCode.toUpperCase());
            request.setAttribute("successMessage", "Coupon applied! You saved ₹" + discount);
        } else {
            session.removeAttribute("discount");
            session.removeAttribute("couponCode");
            request.setAttribute("errorMessage", "Invalid coupon code. Please try again.");
        }

        // Get current cart total from session
        Double cartTotal = (Double) session.getAttribute("cartTotal");
        if (cartTotal != null && discount != null) {
            double discountAmount = Double.parseDouble(discount);
            double newTotal = Math.max(0, cartTotal - discountAmount);
            session.setAttribute("cartTotal", newTotal);
        }

        request.getRequestDispatcher("/customer/cart.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Remove coupon
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute("discount");
            session.removeAttribute("couponCode");
        }
        response.sendRedirect(request.getContextPath() + "/cart");
    }
}