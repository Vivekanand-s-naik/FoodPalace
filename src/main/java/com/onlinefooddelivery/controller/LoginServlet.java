package com.onlinefooddelivery.controller;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.onlinefooddelivery.dao.UserDAO;
import com.onlinefooddelivery.dao.impl.UserDAOImpl;
import com.onlinefooddelivery.model.User;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Check if user is already logged in
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("loggedUser") != null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }
        // Forward to login page
        request.getRequestDispatcher("/auth/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String rememberMe = request.getParameter("rememberMe");

        // Validate input
        if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Email and Password are required.");
            request.getRequestDispatcher("/auth/login.jsp").forward(request, response);
            return;
        }

        try {
            // Authenticate user - using validateUser method from UserDAO
            User user = userDAO.validateUser(email.trim(), password.trim());

            if (user != null) {
                // Login successful - create session
                HttpSession session = request.getSession();
                session.setAttribute("loggedUser", user);
                session.setAttribute("userId", user.getUserId());
                session.setAttribute("userName", user.getFullName());
                session.setAttribute("userEmail", user.getEmail());
                session.setAttribute("userPhone", user.getPhone());
                session.setAttribute("userRole", user.getRole());

                // Remember me functionality
                if ("on".equals(rememberMe)) {
                    session.setMaxInactiveInterval(60 * 60 * 24 * 30); // 30 days
                }

                // Redirect based on role
                if ("ADMIN".equalsIgnoreCase(user.getRole())) {
                    response.sendRedirect(request.getContextPath() + "/admin/dashboard.jsp");
                } else {
                    response.sendRedirect(request.getContextPath() + "/index.jsp");
                }

            } else {
                // Login failed
                request.setAttribute("errorMessage", "Invalid email or password. Please try again.");
                request.getRequestDispatcher("/auth/login.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred during login. Please try again.");
            request.getRequestDispatcher("/auth/login.jsp").forward(request, response);
        }
    }
}