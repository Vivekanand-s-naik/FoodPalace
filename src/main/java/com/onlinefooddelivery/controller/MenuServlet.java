package com.onlinefooddelivery.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.onlinefooddelivery.dao.MenuItemDAO;
import com.onlinefooddelivery.dao.RestaurantDAO;
import com.onlinefooddelivery.dao.impl.MenuItemDAOImpl;
import com.onlinefooddelivery.dao.impl.RestaurantDAOImpl;
import com.onlinefooddelivery.model.MenuItem;
import com.onlinefooddelivery.model.Restaurant;

@WebServlet("/menu")
public class MenuServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private MenuItemDAO menuItemDAO;
    private RestaurantDAO restaurantDAO;

    @Override
    public void init() throws ServletException {
        menuItemDAO = new MenuItemDAOImpl();
        restaurantDAO = new RestaurantDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String restaurantIdParam = request.getParameter("restaurantId");
        String category = request.getParameter("category");
        String keyword = request.getParameter("keyword");

        // Validate restaurantId
        if (restaurantIdParam == null || restaurantIdParam.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/restaurants");
            return;
        }

        int restaurantId;
        try {
            restaurantId = Integer.parseInt(restaurantIdParam.trim());
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/restaurants");
            return;
        }

        try {
            // Get restaurant details
            Restaurant restaurant = restaurantDAO.getRestaurantById(restaurantId);
            if (restaurant == null) {
                response.sendRedirect(request.getContextPath() + "/restaurants");
                return;
            }

            // Get all menu items for the restaurant
            List<MenuItem> allItems = menuItemDAO.getMenuItemsByRestaurantId(restaurantId);

            List<MenuItem> menuItems = null;
            
            if (allItems != null) {
                // Filter by keyword
                if (keyword != null && !keyword.trim().isEmpty()) {
                    String searchTerm = keyword.trim().toLowerCase();
                    menuItems = allItems.stream()
                            .filter(item -> item.getItemName().toLowerCase().contains(searchTerm) ||
                                           (item.getDescription() != null && item.getDescription().toLowerCase().contains(searchTerm)))
                            .collect(Collectors.toList());
                } 
                // Filter by category
                else if (category != null && !category.trim().isEmpty() && !"All".equals(category)) {
                    menuItems = allItems.stream()
                            .filter(item -> category.equals(item.getCategory()))
                            .collect(Collectors.toList());
                } 
                // Show all items
                else {
                    menuItems = allItems;
                }
            }

            // Get unique categories for filter
            List<String> categories = null;
            if (allItems != null) {
                categories = allItems.stream()
                        .map(MenuItem::getCategory)
                        .filter(cat -> cat != null && !cat.isEmpty())
                        .distinct()
                        .collect(Collectors.toList());
            }

            // Set attributes
            request.setAttribute("restaurant", restaurant);
            request.setAttribute("menuList", menuItems);
            request.setAttribute("categories", categories);
            request.setAttribute("selectedCategory", category);
            request.setAttribute("keyword", keyword);

            request.getRequestDispatcher("/customer/menu.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Unable to load menu. Please try again.");
            request.getRequestDispatcher("/customer/menu.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}