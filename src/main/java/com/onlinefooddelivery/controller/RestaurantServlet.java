package com.onlinefooddelivery.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.onlinefooddelivery.dao.RestaurantDAO;
import com.onlinefooddelivery.dao.impl.RestaurantDAOImpl;
import com.onlinefooddelivery.model.Restaurant;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet({"/restaurants", "/RestaurantServlet"})
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

        if (keyword != null) {
            keyword = keyword.trim();
        }
        if (cuisine != null) {
            cuisine = cuisine.trim();
        }
        if (sort == null || sort.trim().isEmpty()) {
            sort = "rating";
        } else {
            sort = sort.trim();
        }

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
            List<Restaurant> restaurants = restaurantDAO.getAllRestaurants();

            if (restaurants != null) {
                restaurants = restaurants.stream()
                        .filter(Restaurant::isActive)
                        .collect(Collectors.toList());

                if (keyword != null && !keyword.isEmpty()) {
                    String searchTerm = keyword.toLowerCase();
                    restaurants = restaurants.stream()
                            .filter(r -> (r.getName() != null && r.getName().toLowerCase().contains(searchTerm))
                            || (r.getCuisine() != null && r.getCuisine().toLowerCase().contains(searchTerm)))
                            .collect(Collectors.toList());
                }

                if (cuisine != null && !cuisine.isEmpty() && !"All Cuisines".equals(cuisine)) {
                    final String cuisineFilter = cuisine;
                    restaurants = restaurants.stream()
                            .filter(r -> cuisineFilter.equalsIgnoreCase(r.getCuisine()))
                            .collect(Collectors.toList());
                }

                if ("name".equals(sort)) {
                    restaurants.sort((r1, r2) -> r1.getName().compareToIgnoreCase(r2.getName()));
                } else {
                    restaurants.sort((r1, r2) -> Double.compare(r2.getRating(), r1.getRating()));
                }
            }

            int totalCount = restaurants != null ? restaurants.size() : 0;
            int totalPages = totalCount > 0 ? (int) Math.ceil((double) totalCount / pageSize) : 0;
            if (totalPages > 0 && page > totalPages) {
                page = totalPages;
            }
            if (page < 1) {
                page = 1;
            }

            int startIndex = (page - 1) * pageSize;
            int endIndex = Math.min(startIndex + pageSize, totalCount);

            List<Restaurant> paginatedList = null;
            if (restaurants != null && !restaurants.isEmpty() && startIndex < totalCount) {
                paginatedList = restaurants.subList(startIndex, endIndex);
            }

            // Set attributes - make sure the attribute name matches JSP
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
