package com.onlinefooddelivery.controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.onlinefooddelivery.dao.CartDAO;
import com.onlinefooddelivery.dao.CartItemDAO;
import com.onlinefooddelivery.dao.MenuItemDAO;
import com.onlinefooddelivery.dao.impl.CartDAOImpl;
import com.onlinefooddelivery.dao.impl.CartItemDAOImpl;
import com.onlinefooddelivery.dao.impl.MenuItemDAOImpl;
import com.onlinefooddelivery.model.Cart;
import com.onlinefooddelivery.model.CartItem;
import com.onlinefooddelivery.model.MenuItem;
import com.onlinefooddelivery.model.User;

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
            
         // Debug
            System.out.println("=== CART DEBUG ===");
            System.out.println("Cart ID: " + cart.getCartId());
            System.out.println("User ID: " + userId);
            System.out.println("Cart Items Count: " + (cartItems != null ? cartItems.size() : 0));
            if (cartItems != null) {
                for (CartItem item : cartItems) {
                    System.out.println("Item: " + item.getItemName() + 
                                      ", Price: " + item.getPrice() + 
                                      ", Qty: " + item.getQuantity() +
                                      ", Image: " + item.getImagePath());
                }
            }
            System.out.println("==================");
            
            
            // Calculate totals
            double subTotal = 0;
            if (cartItems != null) {
                for (CartItem item : cartItems) {
                    subTotal += item.getTotalPrice();
                }
            }
            
            double deliveryFee = subTotal > 0 ? 40.0 : 0; // Flat delivery fee
            double tax = subTotal * 0.05; // 5% GST
            double grandTotal = subTotal + deliveryFee + tax;

            // Set attributes
            request.setAttribute("cartItems", cartItems);
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
            System.out.println("Cart from DB: " + (cart != null ? cart.getCartId() : "null"));
            if (cart == null) {
            	System.out.println("Creating new cart for user: " + userId);
                cart = new Cart();
                cart.setUserId(userId);
                cartDAO.addCart(cart);
                cart = cartDAO.getCartByUserId(userId);
                System.out.println("New cart created: " + (cart != null ? cart.getCartId() : "failed"));
            }

            if ("add".equals(action)) {
                // Add item to cart
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

                // Check if item already exists in cart
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
                    // Update quantity
                    existingItem.setQuantity(existingItem.getQuantity() + quantity);
                    cartItemDAO.updateCartItem(existingItem);
                } else {
                    // Add new item
                    CartItem newItem = new CartItem();
                    newItem.setCartId(cart.getCartId());
                    newItem.setItemId(menuId);
                    newItem.setQuantity(quantity);
                    cartItemDAO.addCartItem(newItem);
                }
                System.out.println("Adding item - MenuId: " + menuId + ", Quantity: " + quantity);
                System.out.println("Cart ID: " + cart.getCartId());
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
}