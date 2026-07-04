package com.onlinefooddelivery.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.onlinefooddelivery.dao.RestaurantDAO;
import com.onlinefooddelivery.dao.impl.RestaurantDAOImpl;
import com.onlinefooddelivery.model.Restaurant;

@WebServlet("/restaurants")
public class RestaurantServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private RestaurantDAO restaurantDAO;

    @Override
    public void init() throws ServletException {
        restaurantDAO = new RestaurantDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String keyword = request.getParameter("keyword");
        String cuisine = request.getParameter("cuisine");
        String pageParam = request.getParameter("page");
        String sort = request.getParameter("sort");

        int page = 1;
        int pageSize = 6;

        if (pageParam != null && !pageParam.isEmpty()) {
            try {
                page = Integer.parseInt(pageParam);
            } catch (NumberFormatException e) {
                page = 1;
            }
        }

        try {
            List<Restaurant> restaurants;

            // Filter by keyword or cuisine
            if (keyword != null && !keyword.trim().isEmpty()) {
                // Search by keyword (name or cuisine)
                String searchTerm = keyword.trim().toLowerCase();
                List<Restaurant> allRestaurants = restaurantDAO.getAllRestaurants();
                if (allRestaurants != null) {
                    restaurants = allRestaurants.stream()
                            .filter(r -> r.getName().toLowerCase().contains(searchTerm) ||
                                        (r.getCuisine() != null && r.getCuisine().toLowerCase().contains(searchTerm)))
                            .filter(Restaurant::isActive)
                            .collect(Collectors.toList());
                } else {
                    restaurants = null;
                }
            } else if (cuisine != null && !cuisine.trim().isEmpty() && !"All Cuisines".equals(cuisine)) {
                restaurants = restaurantDAO.getRestaurantsByCuisine(cuisine.trim());
            } else {
                restaurants = restaurantDAO.getActiveRestaurants();
            }

            // Sorting
            if (restaurants != null) {
                if ("name".equals(sort)) {
                    restaurants.sort((r1, r2) -> r1.getName().compareToIgnoreCase(r2.getName()));
                } else if ("rating".equals(sort)) {
                    restaurants.sort((r1, r2) -> Double.compare(r2.getRating(), r1.getRating()));
                }
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
            request.setAttribute("restaurantList", paginatedList);
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("totalCount", totalCount);
            request.setAttribute("startIndex", totalCount > 0 ? startIndex + 1 : 0);
            request.setAttribute("endIndex", endIndex);
            request.setAttribute("keyword", keyword);
            request.setAttribute("cuisine", cuisine);
            request.setAttribute("sort", sort);

            request.getRequestDispatcher("/customer/restaurants.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Unable to load restaurants. Please try again.");
            request.getRequestDispatcher("/customer/restaurants.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}