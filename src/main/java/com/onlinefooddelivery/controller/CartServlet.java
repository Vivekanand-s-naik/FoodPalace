package com.onlinefooddelivery.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.onlinefooddelivery.dao.CartDAO;
import com.onlinefooddelivery.dao.CartItemDAO;
import com.onlinefooddelivery.dao.MenuItemDAO;
import com.onlinefooddelivery.dao.impl.CartDAOImpl;
import com.onlinefooddelivery.dao.impl.CartItemDAOImpl;
import com.onlinefooddelivery.dao.impl.MenuItemDAOImpl;
import com.onlinefooddelivery.model.Cart;
import com.onlinefooddelivery.model.CartItem;
import com.onlinefooddelivery.model.MenuItem;
import com.onlinefooddelivery.model.RestaurantCartGroup;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private CartDAO cartDAO;
    private CartItemDAO cartItemDAO;
    private MenuItemDAO menuItemDAO;

    @Override
    public void init() throws ServletException {
        cartDAO = new CartDAOImpl();
        cartItemDAO = new CartItemDAOImpl();
        menuItemDAO = new MenuItemDAOImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/auth/login.jsp");
            return;
        }

        int userId = (int) session.getAttribute("userId");
        String action = request.getParameter("action");

        try {
            if ("remove".equals(action)) {
                String cartItemIdParam = request.getParameter("cartItemId");
                if (cartItemIdParam != null) {
                    int cartItemId = Integer.parseInt(cartItemIdParam);
                    cartItemDAO.deleteCartItem(cartItemId);
                }
                response.sendRedirect(request.getContextPath() + "/cart");
                return;
            }

            // Get or create cart
            Cart cart = cartDAO.getCartByUserId(userId);
            if (cart == null) {
                cart = new Cart();
                cart.setUserId(userId);
                cartDAO.addCart(cart);
                cart = cartDAO.getCartByUserId(userId);
            }

            // Get cart items with details
            List<CartItem> cartItems = cartItemDAO.getCartItemsByCartId(cart.getCartId());

            List<RestaurantCartGroup> restaurantGroups = groupItemsByRestaurant(cartItems);

            // Calculate totals
            double subTotal = 0;
            if (cartItems != null) {
                for (CartItem item : cartItems) {
                    subTotal += item.getTotalPrice();
                }
            }

            int restaurantCount = restaurantGroups.size();
            double deliveryFee = subTotal > 0 ? 40.0 * restaurantCount : 0;
            double tax = subTotal * 0.05;
            double grandTotal = subTotal + deliveryFee + tax;

            // Set attributes
            request.setAttribute("cartItems", cartItems);
            request.setAttribute("restaurantGroups", restaurantGroups);
            request.setAttribute("restaurantCount", restaurantGroups.size());
            request.setAttribute("cart", cart);
            request.setAttribute("subTotal", subTotal);
            request.setAttribute("deliveryFee", deliveryFee);
            request.setAttribute("tax", tax);
            request.setAttribute("grandTotal", grandTotal);

            // Get recommended items (optional)
            // request.setAttribute("recommendedItems", recommendedItems);
            request.getRequestDispatcher("/customer/cart.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Unable to load cart. Please try again.");
            request.setAttribute("restaurantGroups", new ArrayList<RestaurantCartGroup>());
            request.getRequestDispatcher("/customer/cart.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/auth/login.jsp");
            return;
        }

        int userId = (int) session.getAttribute("userId");
        String action = request.getParameter("action");

        try {
            // Get or create cart
            Cart cart = cartDAO.getCartByUserId(userId);
            if (cart == null) {
                cart = new Cart();
                cart.setUserId(userId);
                if (!cartDAO.addCart(cart)) {
                    response.sendRedirect(request.getContextPath() + "/cart?error=Unable to create cart");
                    return;
                }
                if (cart.getCartId() <= 0) {
                    cart = cartDAO.getCartByUserId(userId);
                }
            }

            if (cart == null || cart.getCartId() <= 0) {
                response.sendRedirect(request.getContextPath() + "/cart?error=Unable to load cart");
                return;
            }

            if ("add".equals(action)) {
                String menuIdParam = request.getParameter("menuId");
                String quantityParam = request.getParameter("quantity");
                String restaurantIdParam = request.getParameter("restaurantId");

                if (menuIdParam == null || menuIdParam.trim().isEmpty()) {
                    response.sendRedirect(request.getContextPath() + "/cart");
                    return;
                }

                int menuId = Integer.parseInt(menuIdParam);
                int quantity = 1;
                if (quantityParam != null && !quantityParam.trim().isEmpty()) {
                    quantity = Integer.parseInt(quantityParam);
                }

                int restaurantId = -1;
                if (restaurantIdParam != null && !restaurantIdParam.trim().isEmpty()) {
                    restaurantId = Integer.parseInt(restaurantIdParam);
                }

                MenuItem menuItem = menuItemDAO.getMenuItemById(menuId);
                if (menuItem == null || !menuItem.isAvailable()) {
                    response.sendRedirect(request.getContextPath() + "/cart?error=Item unavailable");
                    return;
                }

                List<CartItem> existingItems = cartItemDAO.getCartItemsByCartId(cart.getCartId());
                CartItem existingItem = null;

                if (existingItems != null) {
                    for (CartItem item : existingItems) {
                        if (item.getItemId() == menuId) {
                            existingItem = item;
                            break;
                        }
                    }
                }

                if (existingItem != null) {
                    existingItem.setQuantity(existingItem.getQuantity() + quantity);
                    if (menuItem != null) {
                        existingItem.setRestaurantId(menuItem.getRestaurantId());
                    } else if (restaurantId > 0) {
                        existingItem.setRestaurantId(restaurantId);
                    }
                    cartItemDAO.updateCartItem(existingItem);
                } else {
                    CartItem newItem = new CartItem();
                    newItem.setCartId(cart.getCartId());
                    newItem.setItemId(menuId);
                    newItem.setQuantity(quantity);
                    if (menuItem != null) {
                        newItem.setRestaurantId(menuItem.getRestaurantId());
                    } else if (restaurantId > 0) {
                        newItem.setRestaurantId(restaurantId);
                    }
                    cartItemDAO.addCartItem(newItem);
                }

                response.sendRedirect(request.getContextPath() + "/cart");

            } else if ("update".equals(action)) {
                // Update cart item quantity
                String cartItemIdParam = request.getParameter("cartItemId");
                String quantityParam = request.getParameter("quantity");

                if (cartItemIdParam != null && quantityParam != null) {
                    int cartItemId = Integer.parseInt(cartItemIdParam);
                    int quantity = Integer.parseInt(quantityParam);

                    CartItem item = cartItemDAO.getCartItemById(cartItemId);
                    if (item != null && item.getCartId() == cart.getCartId()) {
                        item.setQuantity(quantity);
                        cartItemDAO.updateCartItem(item);
                    }
                }
                response.sendRedirect(request.getContextPath() + "/cart");

            } else {
                response.sendRedirect(request.getContextPath() + "/cart");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/cart?error=Unable to update cart");
        }
    }

    private List<RestaurantCartGroup> groupItemsByRestaurant(List<CartItem> cartItems) {
        List<RestaurantCartGroup> groups = new ArrayList<>();
        if (cartItems == null || cartItems.isEmpty()) {
            return groups;
        }

        Map<Integer, RestaurantCartGroup> groupMap = new LinkedHashMap<>();
        for (CartItem item : cartItems) {
            int restaurantId = item.getRestaurantId();
            String restaurantName = item.getRestaurantName();
            if (restaurantName == null || restaurantName.isBlank()) {
                restaurantName = "Restaurant";
            }

            final String groupName = restaurantName;
            RestaurantCartGroup group = groupMap.computeIfAbsent(restaurantId, id -> {
                RestaurantCartGroup newGroup = new RestaurantCartGroup();
                newGroup.setRestaurantId(id);
                newGroup.setRestaurantName(groupName);
                newGroup.setItems(new ArrayList<>());
                return newGroup;
            });
            group.getItems().add(item);
            group.setSubtotal(group.getSubtotal() + item.getTotalPrice());
        }

        groups.addAll(groupMap.values());
        return groups;
    }
}
