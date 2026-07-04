package com.onlinefooddelivery.controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.onlinefooddelivery.dao.AddressDAO;
import com.onlinefooddelivery.dao.CartDAO;
import com.onlinefooddelivery.dao.CartItemDAO;
import com.onlinefooddelivery.dao.OrderDAO;
import com.onlinefooddelivery.dao.OrderItemDAO;
import com.onlinefooddelivery.dao.PaymentDAO;
import com.onlinefooddelivery.dao.impl.AddressDAOImpl;
import com.onlinefooddelivery.dao.impl.CartDAOImpl;
import com.onlinefooddelivery.dao.impl.CartItemDAOImpl;
import com.onlinefooddelivery.dao.impl.OrderDAOImpl;
import com.onlinefooddelivery.dao.impl.OrderItemDAOImpl;
import com.onlinefooddelivery.dao.impl.PaymentDAOImpl;
import com.onlinefooddelivery.model.Address;
import com.onlinefooddelivery.model.Cart;
import com.onlinefooddelivery.model.CartItem;
import com.onlinefooddelivery.model.Order;
import com.onlinefooddelivery.model.OrderItem;
import com.onlinefooddelivery.model.Payment;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private AddressDAO addressDAO;
    private CartDAO cartDAO;
    private CartItemDAO cartItemDAO;
    private OrderDAO orderDAO;
    private OrderItemDAO orderItemDAO;
    private PaymentDAO paymentDAO;

    @Override
    public void init() throws ServletException {
        addressDAO = new AddressDAOImpl();
        cartDAO = new CartDAOImpl();
        cartItemDAO = new CartItemDAOImpl();
        orderDAO = new OrderDAOImpl();
        orderItemDAO = new OrderItemDAOImpl();
        paymentDAO = new PaymentDAOImpl();
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

        try {
            // Get user addresses
            List<Address> addressList = addressDAO.getAddressesByUserId(userId);

            // Get cart
            Cart cart = cartDAO.getCartByUserId(userId);
            if (cart == null) {
                response.sendRedirect(request.getContextPath() + "/cart");
                return;
            }

            // Get cart items
            List<CartItem> cartItems = cartItemDAO.getCartItemsByCartId(cart.getCartId());
            if (cartItems == null || cartItems.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/cart");
                return;
            }

            // Calculate totals
            double subTotal = 0;
            for (CartItem item : cartItems) {
                subTotal += item.getTotalPrice();
            }
            
            double deliveryFee = 40.0;
            double tax = subTotal * 0.05;
            double grandTotal = subTotal + deliveryFee + tax;

            // Set attributes
            request.setAttribute("addressList", addressList);
            request.setAttribute("cartItems", cartItems);
            request.setAttribute("cart", cart);
            request.setAttribute("subTotal", subTotal);
            request.setAttribute("deliveryFee", deliveryFee);
            request.setAttribute("tax", tax);
            request.setAttribute("grandTotal", grandTotal);

            request.getRequestDispatcher("/customer/checkout.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Unable to load checkout. Please try again.");
            request.getRequestDispatcher("/customer/checkout.jsp").forward(request, response);
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

        String addressIdParam = request.getParameter("addressId");
        String paymentMethod = request.getParameter("paymentMethod");
        String instructions = request.getParameter("instructions");

        // Validate inputs
        if (addressIdParam == null || addressIdParam.trim().isEmpty() ||
            paymentMethod == null || paymentMethod.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Please select address and payment method.");
            doGet(request, response);
            return;
        }

        try {
            int addressId = Integer.parseInt(addressIdParam.trim());

            // Get cart
            Cart cart = cartDAO.getCartByUserId(userId);
            if (cart == null) {
                response.sendRedirect(request.getContextPath() + "/cart");
                return;
            }

            // Get cart items
            List<CartItem> cartItems = cartItemDAO.getCartItemsByCartId(cart.getCartId());
            if (cartItems == null || cartItems.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/cart");
                return;
            }

            // Calculate total
            double subTotal = 0;
            int restaurantId = 0;
            for (CartItem item : cartItems) {
                subTotal += item.getTotalPrice();
                // Get restaurantId from menu item
                // For simplicity, assume all items are from same restaurant
                // We'll use the first item's restaurant
                if (restaurantId == 0) {
                    // restaurantId = menuItemDAO.getMenuItemById(item.getItemId()).getRestaurantId();
                }
            }
            
            double deliveryFee = 40.0;
            double tax = subTotal * 0.05;
            double grandTotal = subTotal + deliveryFee + tax;

            // Create order
            Order order = new Order();
            order.setUserId(userId);
            order.setRestaurantId(restaurantId); // This needs to be set from menu items
            order.setTotalAmount(grandTotal);
            order.setStatus("PLACED");

            boolean orderSuccess = orderDAO.addOrder(order);
            if (!orderSuccess) {
                request.setAttribute("errorMessage", "Failed to place order. Please try again.");
                doGet(request, response);
                return;
            }

            // Get the generated order ID
            // Since we don't have a method to get last inserted ID, we'll get the latest order for user
            List<Order> userOrders = orderDAO.getOrdersByUserId(userId);
            Order latestOrder = userOrders != null && !userOrders.isEmpty() ? userOrders.get(0) : null;
            
            if (latestOrder == null) {
                request.setAttribute("errorMessage", "Failed to retrieve order. Please contact support.");
                doGet(request, response);
                return;
            }

            // Add order items
            for (CartItem cartItem : cartItems) {
                OrderItem orderItem = new OrderItem();
                orderItem.setOrderId(latestOrder.getOrderId());
                orderItem.setItemId(cartItem.getItemId());
                orderItem.setQuantity(cartItem.getQuantity());
                // Price needs to be fetched from menu item
                // For now, use a placeholder
                // orderItem.setPrice(price);
                orderItemDAO.addOrderItem(orderItem);
            }

            // Create payment
            Payment payment = new Payment();
            payment.setOrderId(latestOrder.getOrderId());
            payment.setPaymentMethod(paymentMethod);
            payment.setPaymentStatus("PENDING");
            paymentDAO.addPayment(payment);

            // Clear cart
            cartItemDAO.clearCart(cart.getCartId());

            // Redirect to order details
            response.sendRedirect(request.getContextPath() + "/order-details?orderId=" + latestOrder.getOrderId());

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred during checkout. Please try again.");
            doGet(request, response);
        }
    }
}