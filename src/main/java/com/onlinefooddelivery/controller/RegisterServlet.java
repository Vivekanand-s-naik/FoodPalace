package com.onlinefooddelivery.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.onlinefooddelivery.dao.UserDAO;
import com.onlinefooddelivery.dao.impl.UserDAOImpl;
import com.onlinefooddelivery.model.User;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Forward to register page
        request.getRequestDispatcher("/auth/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get form parameters
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String address = request.getParameter("address");
        String role = request.getParameter("role");
        String gender = request.getParameter("gender");
        String terms = request.getParameter("terms");

        // Validate required fields
        if (fullName == null || fullName.trim().isEmpty() ||
            email == null || email.trim().isEmpty() ||
            phone == null || phone.trim().isEmpty() ||
            password == null || password.trim().isEmpty() ||
            confirmPassword == null || confirmPassword.trim().isEmpty() ||
            address == null || address.trim().isEmpty()) {

            request.setAttribute("errorMessage", "All fields are required.");
            request.getRequestDispatcher("/auth/register.jsp").forward(request, response);
            return;
        }

        // Validate password match
        if (!password.equals(confirmPassword)) {
            request.setAttribute("errorMessage", "Passwords do not match.");
            request.getRequestDispatcher("/auth/register.jsp").forward(request, response);
            return;
        }

        // Validate password length
        if (password.length() < 6) {
            request.setAttribute("errorMessage", "Password must be at least 6 characters.");
            request.getRequestDispatcher("/auth/register.jsp").forward(request, response);
            return;
        }

        // Validate terms
        if (terms == null) {
            request.setAttribute("errorMessage", "Please agree to the Terms & Conditions.");
            request.getRequestDispatcher("/auth/register.jsp").forward(request, response);
            return;
        }

        try {
            // Check if email already exists
            User existingUser = userDAO.getUserByEmail(email.trim());
            if (existingUser != null) {
                request.setAttribute("errorMessage", "Email already registered. Please login.");
                request.getRequestDispatcher("/auth/register.jsp").forward(request, response);
                return;
            }

            // Create new user
            User user = new User();
            user.setFullName(fullName.trim());
            user.setEmail(email.trim());
            user.setPhone(phone.trim());
            user.setPassword(password);
            user.setRole(role != null && !role.isEmpty() ? role : "CUSTOMER");
//            user.setGender(gender);

            boolean success = userDAO.addUser(user);

            if (success) {
                // Optionally add address if address was provided
                // For now, we'll just redirect to login
                request.setAttribute("successMessage", "Registration successful! Please login.");
                request.getRequestDispatcher("/auth/login.jsp").forward(request, response);
            } else {
                request.setAttribute("errorMessage", "Registration failed. Please try again.");
                request.getRequestDispatcher("/auth/register.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred during registration. Please try again.");
            request.getRequestDispatcher("/auth/register.jsp").forward(request, response);
        }
    }
}