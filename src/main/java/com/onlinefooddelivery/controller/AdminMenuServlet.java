package com.onlinefooddelivery.controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.onlinefooddelivery.dao.MenuItemDAO;
import com.onlinefooddelivery.dao.RestaurantDAO;
import com.onlinefooddelivery.dao.impl.MenuItemDAOImpl;
import com.onlinefooddelivery.dao.impl.RestaurantDAOImpl;
import com.onlinefooddelivery.model.MenuItem;
import com.onlinefooddelivery.model.Restaurant;

@WebServlet("/admin/menu")
public class AdminMenuServlet extends HttpServlet {

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

        // Check admin session
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userRole") == null || 
            !"ADMIN".equals(session.getAttribute("userRole"))) {
            response.sendRedirect(request.getContextPath() + "/auth/login.jsp");
            return;
        }

        String action = request.getParameter("action");
        String search = request.getParameter("search");
        String restaurantIdParam = request.getParameter("restaurantId");
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
                    menuItemDAO.deleteMenuItem(id);
                }
                response.sendRedirect(request.getContextPath() + "/admin/menu");
                return;
            }

            // Get all menu items
            List<MenuItem> menuItems = menuItemDAO.getAllMenuItems();

            // Filter by restaurant
            if (restaurantIdParam != null && !restaurantIdParam.isEmpty()) {
                int restaurantId = Integer.parseInt(restaurantIdParam);
                menuItems = menuItemDAO.getMenuItemsByRestaurantId(restaurantId);
            }

            // Filter by search
            if (search != null && !search.trim().isEmpty()) {
                String searchTerm = search.trim().toLowerCase();
                if (menuItems != null) {
                    menuItems = menuItems.stream()
                            .filter(item -> item.getItemName().toLowerCase().contains(searchTerm) ||
                                           (item.getDescription() != null && item.getDescription().toLowerCase().contains(searchTerm)))
                            .toList();
                }
            }

            // Get all restaurants for filter dropdown
            List<Restaurant> restaurantList = restaurantDAO.getAllRestaurants();

            // Pagination
            int totalCount = menuItems != null ? menuItems.size() : 0;
            int totalPages = totalCount > 0 ? (int) Math.ceil((double) totalCount / pageSize) : 0;

            int startIndex = (page - 1) * pageSize;
            int endIndex = Math.min(startIndex + pageSize, totalCount);

            List<MenuItem> paginatedList = null;
            if (menuItems != null && !menuItems.isEmpty() && startIndex < totalCount) {
                paginatedList = menuItems.subList(startIndex, endIndex);
            }

            // Set attributes
            request.setAttribute("menuItems", paginatedList);
            request.setAttribute("restaurantList", restaurantList);
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("totalCount", totalCount);
            request.setAttribute("startIndex", totalCount > 0 ? startIndex + 1 : 0);
            request.setAttribute("endIndex", endIndex);
            request.setAttribute("search", search);
            request.setAttribute("restaurantId", restaurantIdParam);

            request.getRequestDispatcher("/admin/menu.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Unable to load menu items. Please try again.");
            request.getRequestDispatcher("/admin/menu.jsp").forward(request, response);
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
                String menuId = request.getParameter("menuId");
                String restaurantId = request.getParameter("restaurantId");
                String itemName = request.getParameter("itemName");
                String description = request.getParameter("description");
                String priceStr = request.getParameter("price");
                String category = request.getParameter("category");
                String available = request.getParameter("available");

                MenuItem menuItem = new MenuItem();
                menuItem.setRestaurantId(Integer.parseInt(restaurantId));
                menuItem.setItemName(itemName);
                menuItem.setDescription(description);
                menuItem.setPrice(Double.parseDouble(priceStr));
                menuItem.setCategory(category);
                menuItem.setAvailable("on".equals(available));

                if ("edit".equals(action) && menuId != null && !menuId.isEmpty()) {
                    menuItem.setItemId(Integer.parseInt(menuId));
                    menuItemDAO.updateMenuItem(menuItem);
                } else {
                    menuItemDAO.addMenuItem(menuItem);
                }

                response.sendRedirect(request.getContextPath() + "/admin/menu");
                return;
            }

            response.sendRedirect(request.getContextPath() + "/admin/menu");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Unable to save menu item. Please try again.");
            request.getRequestDispatcher("/admin/menu.jsp").forward(request, response);
        }
    }
}