package com.onlinefooddelivery.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
import com.onlinefooddelivery.model.RestaurantCartGroup;

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
            List<Address> addressList = addressDAO.getAddressesByUserId(userId);

            Cart cart = cartDAO.getCartByUserId(userId);
            if (cart == null) {
                response.sendRedirect(request.getContextPath() + "/cart");
                return;
            }

            List<CartItem> cartItems = cartItemDAO.getCartItemsByCartId(cart.getCartId());
            if (cartItems == null || cartItems.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/cart");
                return;
            }

            List<RestaurantCartGroup> restaurantGroups = groupItemsByRestaurant(cartItems);
            double subTotal = 0;
            for (CartItem item : cartItems) {
                subTotal += item.getTotalPrice();
            }

            int restaurantCount = restaurantGroups.size();
            double deliveryFee = 40.0 * restaurantCount;
            double tax = subTotal * 0.05;
            double grandTotal = subTotal + deliveryFee + tax;

            request.setAttribute("addressList", addressList);
            request.setAttribute("cartItems", cartItems);
            request.setAttribute("restaurantGroups", restaurantGroups);
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

        if (addressIdParam == null || addressIdParam.trim().isEmpty() ||
            paymentMethod == null || paymentMethod.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Please select address and payment method.");
            doGet(request, response);
            return;
        }

        try {
            Cart cart = cartDAO.getCartByUserId(userId);
            if (cart == null) {
                response.sendRedirect(request.getContextPath() + "/cart");
                return;
            }

            List<CartItem> cartItems = cartItemDAO.getCartItemsByCartId(cart.getCartId());
            if (cartItems == null || cartItems.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/cart");
                return;
            }

            Map<Integer, List<CartItem>> itemsByRestaurant = new LinkedHashMap<>();
            for (CartItem item : cartItems) {
                itemsByRestaurant.computeIfAbsent(item.getRestaurantId(), id -> new ArrayList<>()).add(item);
            }

            List<Integer> createdOrderIds = new ArrayList<>();

            for (Map.Entry<Integer, List<CartItem>> entry : itemsByRestaurant.entrySet()) {
                int restaurantId = entry.getKey();
                List<CartItem> restaurantItems = entry.getValue();

                double restaurantSubTotal = 0;
                for (CartItem item : restaurantItems) {
                    restaurantSubTotal += item.getTotalPrice();
                }

                double restaurantDeliveryFee = 40.0;
                double restaurantTax = restaurantSubTotal * 0.05;
                double restaurantTotal = restaurantSubTotal + restaurantDeliveryFee + restaurantTax;

                Order order = new Order();
                order.setUserId(userId);
                order.setRestaurantId(restaurantId);
                order.setTotalAmount(restaurantTotal);
                order.setStatus("PLACED");

                if (!orderDAO.addOrder(order) || order.getOrderId() <= 0) {
                    request.setAttribute("errorMessage", "Failed to place order. Please try again.");
                    doGet(request, response);
                    return;
                }

                for (CartItem cartItem : restaurantItems) {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setOrderId(order.getOrderId());
                    orderItem.setItemId(cartItem.getItemId());
                    orderItem.setQuantity(cartItem.getQuantity());
                    orderItem.setPrice(cartItem.getPrice());
                    orderItemDAO.addOrderItem(orderItem);
                }

                Payment payment = new Payment();
                payment.setOrderId(order.getOrderId());
                payment.setPaymentMethod(paymentMethod);
                payment.setPaymentStatus("PENDING");
                paymentDAO.addPayment(payment);

                createdOrderIds.add(order.getOrderId());
            }

            cartItemDAO.clearCart(cart.getCartId());

            if (createdOrderIds.size() == 1) {
                response.sendRedirect(request.getContextPath() + "/order-details?orderId=" + createdOrderIds.get(0));
            } else {
                response.sendRedirect(request.getContextPath() + "/orders");
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "An error occurred during checkout. Please try again.");
            doGet(request, response);
        }
    }

    private List<RestaurantCartGroup> groupItemsByRestaurant(List<CartItem> cartItems) {
        List<RestaurantCartGroup> groups = new ArrayList<>();
        Map<Integer, RestaurantCartGroup> groupMap = new LinkedHashMap<>();

        for (CartItem item : cartItems) {
            RestaurantCartGroup group = groupMap.computeIfAbsent(item.getRestaurantId(), id -> {
                RestaurantCartGroup newGroup = new RestaurantCartGroup();
                newGroup.setRestaurantId(id);
                newGroup.setRestaurantName(item.getRestaurantName());
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
