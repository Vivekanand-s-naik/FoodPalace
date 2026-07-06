package com.onlinefooddelivery.controller;

import java.io.IOException;
import java.util.List;

import com.onlinefooddelivery.dao.UserDAO;
import com.onlinefooddelivery.dao.impl.UserDAOImpl;
import com.onlinefooddelivery.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/admin/users")
public class AdminUserServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Check admin session
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userRole") == null
                || !"ADMIN".equals(session.getAttribute("userRole"))) {
            response.sendRedirect(request.getContextPath() + "/auth/login.jsp");
            return;
        }

        String action = request.getParameter("action");
        String search = request.getParameter("search");
        String roleFilter = request.getParameter("role");
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
            // Handle toggle user status
            if ("toggle".equals(action)) {
                String idParam = request.getParameter("id");
                if (idParam != null) {
                    int userId = Integer.parseInt(idParam);
                    User user = userDAO.getUserById(userId);
                    if (user != null) {
                        // Toggle active status
                        // Note: The schema has 'active' column. If not, we can use a custom field.
                        // For now, we'll just redirect
                        // user.setActive(!user.isActive());
                        // userDAO.updateUser(user);
                    }
                }
                response.sendRedirect(request.getContextPath() + "/admin/users");
                return;
            }

            // Get all users
            List<User> users = userDAO.getAllUsers();

            // Filter by role
            if (roleFilter != null && !roleFilter.isEmpty() && users != null) {
                users = users.stream()
                        .filter(u -> roleFilter.equals(u.getRole()))
                        .toList();
            }

            // Filter by search
            if (search != null && !search.trim().isEmpty() && users != null) {
                String searchTerm = search.trim().toLowerCase();
                users = users.stream()
                        .filter(u -> u.getFullName().toLowerCase().contains(searchTerm)
                        || u.getEmail().toLowerCase().contains(searchTerm)
                        || (u.getPhone() != null && u.getPhone().contains(searchTerm)))
                        .toList();
            }

            // Calculate active users
            int activeUsers = users != null ? (int) users.stream().filter(User::isActive).count() : 0;

            // Pagination
            int totalCount = users != null ? users.size() : 0;
            int totalPages = totalCount > 0 ? (int) Math.ceil((double) totalCount / pageSize) : 0;

            int startIndex = (page - 1) * pageSize;
            int endIndex = Math.min(startIndex + pageSize, totalCount);

            List<User> paginatedList = null;
            if (users != null && !users.isEmpty() && startIndex < totalCount) {
                paginatedList = users.subList(startIndex, endIndex);
            }

            // Set attributes
            request.setAttribute("users", paginatedList);
            request.setAttribute("totalUsers", totalCount);
            request.setAttribute("activeUsers", activeUsers);
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("totalCount", totalCount);
            request.setAttribute("startIndex", totalCount > 0 ? startIndex + 1 : 0);
            request.setAttribute("endIndex", endIndex);
            request.setAttribute("search", search);
            request.setAttribute("role", roleFilter);

            request.getRequestDispatcher("/admin/users.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Unable to load users. Please try again.");
            request.getRequestDispatcher("/admin/users.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
