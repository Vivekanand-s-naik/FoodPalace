package com.onlinefooddelivery.controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.onlinefooddelivery.dao.RestaurantDAO;
import com.onlinefooddelivery.dao.impl.RestaurantDAOImpl;
import com.onlinefooddelivery.model.Restaurant;

@WebServlet("/admin/restaurants")
public class AdminRestaurantServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private RestaurantDAO restaurantDAO;

    @Override
    public void init() throws ServletException {
        restaurantDAO = new RestaurantDAOImpl();
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
            // Handle delete
            if ("delete".equals(action)) {
                String idParam = request.getParameter("id");
                if (idParam != null) {
                    int id = Integer.parseInt(idParam);
                    restaurantDAO.deleteRestaurant(id);
                }
                response.sendRedirect(request.getContextPath() + "/admin/restaurants");
                return;
            }

            // Get all restaurants
            List<Restaurant> restaurants = restaurantDAO.getAllRestaurants();

            // Filter by search
            if (search != null && !search.trim().isEmpty()) {
                String searchTerm = search.trim().toLowerCase();
                restaurants = restaurants.stream()
                        .filter(r -> r.getName().toLowerCase().contains(searchTerm) ||
                                    (r.getCuisine() != null && r.getCuisine().toLowerCase().contains(searchTerm)))
                        .toList();
            }

            // Pagination
            int totalCount = restaurants != null ? restaurants.size() : 0;
            int totalPages = totalCount > 0 ? (int) Math.ceil((double) totalCount / pageSize) : 0;

            int startIndex = (page - 1) * pageSize;
            int endIndex = Math.min(startIndex + pageSize, totalCount);

            List<Restaurant> paginatedList = null;
            if (restaurants != null && !restaurants.isEmpty() && startIndex < totalCount) {
                paginatedList = restaurants.subList(startIndex, endIndex);
            }

            // Set attributes
            request.setAttribute("restaurants", paginatedList);
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("totalCount", totalCount);
            request.setAttribute("startIndex", totalCount > 0 ? startIndex + 1 : 0);
            request.setAttribute("endIndex", endIndex);
            request.setAttribute("search", search);

            request.getRequestDispatcher("/admin/restaurants.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Unable to load restaurants. Please try again.");
            request.getRequestDispatcher("/admin/restaurants.jsp").forward(request, response);
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
            if ("add".equals(action) || "edit".equals(action)) {
                String restaurantId = request.getParameter("restaurantId");
                String name = request.getParameter("name");
                String ownerName = request.getParameter("ownerName");
                String email = request.getParameter("email");
                String phone = request.getParameter("phone");
                String address = request.getParameter("address");
                String cuisine = request.getParameter("cuisine");
                String ratingStr = request.getParameter("rating");
                String active = request.getParameter("active");

                Restaurant restaurant = new Restaurant();
                restaurant.setName(name);
                restaurant.setOwnerName(ownerName);
                restaurant.setEmail(email);
                restaurant.setPhone(phone);
                restaurant.setAddress(address);
                restaurant.setCuisine(cuisine);
                
                if (ratingStr != null && !ratingStr.isEmpty()) {
                    restaurant.setRating(Double.parseDouble(ratingStr));
                }
                restaurant.setActive("on".equals(active));

                if ("edit".equals(action) && restaurantId != null && !restaurantId.isEmpty()) {
                    restaurant.setRestaurantId(Integer.parseInt(restaurantId));
                    restaurantDAO.updateRestaurant(restaurant);
                } else {
                    restaurantDAO.addRestaurant(restaurant);
                }

                response.sendRedirect(request.getContextPath() + "/admin/restaurants");
                return;
            }

            response.sendRedirect(request.getContextPath() + "/admin/restaurants");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Unable to save restaurant. Please try again.");
            request.getRequestDispatcher("/admin/restaurants.jsp").forward(request, response);
        }
    }
}