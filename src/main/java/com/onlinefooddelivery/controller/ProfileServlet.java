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
import com.onlinefooddelivery.dao.UserDAO;
import com.onlinefooddelivery.dao.impl.AddressDAOImpl;
import com.onlinefooddelivery.dao.impl.UserDAOImpl;
import com.onlinefooddelivery.model.Address;
import com.onlinefooddelivery.model.User;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;
    private AddressDAO addressDAO;

    @Override
    public void init() throws ServletException {
        userDAO = new UserDAOImpl();
        addressDAO = new AddressDAOImpl();
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
            // Handle address actions
            if ("deleteAddress".equals(action)) {
                String addressIdParam = request.getParameter("addressId");
                if (addressIdParam != null) {
                    int addressId = Integer.parseInt(addressIdParam);
                    addressDAO.deleteAddress(addressId);
                }
                response.sendRedirect(request.getContextPath() + "/profile");
                return;
            }

            if ("setDefault".equals(action)) {
                String addressIdParam = request.getParameter("addressId");
                if (addressIdParam != null) {
                    int addressId = Integer.parseInt(addressIdParam);
                    // Set all addresses to non-default, then set selected as default
                    List<Address> addresses = addressDAO.getAddressesByUserId(userId);
                    if (addresses != null) {
                        for (Address addr : addresses) {
                            addr.setDefaultAddress(false);
                            addressDAO.updateAddress(addr);
                        }
                    }
                    Address address = addressDAO.getAddressById(addressId);
                    if (address != null) {
                        address.setDefaultAddress(true);
                        addressDAO.updateAddress(address);
                    }
                }
                response.sendRedirect(request.getContextPath() + "/profile");
                return;
            }

            // Get user details
            User user = userDAO.getUserById(userId);
            List<Address> addressList = addressDAO.getAddressesByUserId(userId);

            // Update session with latest user info
            if (user != null) {
                session.setAttribute("userName", user.getFullName());
                session.setAttribute("userEmail", user.getEmail());
                session.setAttribute("userPhone", user.getPhone());
            }

            request.setAttribute("user", user);
            request.setAttribute("addressList", addressList);

            request.getRequestDispatcher("/customer/profile.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Unable to load profile. Please try again.");
            request.getRequestDispatcher("/customer/profile.jsp").forward(request, response);
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
            if ("update".equals(action)) {
                // Update user profile
                String fullName = request.getParameter("fullName");
                String phone = request.getParameter("phone");
                String dob = request.getParameter("dob");
                String newPassword = request.getParameter("newPassword");

                User user = userDAO.getUserById(userId);
                if (user != null) {
                    user.setFullName(fullName);
                    user.setPhone(phone);
                    // user.setDob(dob); // if dob field exists
                    
                    if (newPassword != null && !newPassword.trim().isEmpty()) {
                        user.setPassword(newPassword);
                    }
                    
                    userDAO.updateUser(user);
                    
                    // Update session
                    session.setAttribute("userName", user.getFullName());
                    session.setAttribute("userPhone", user.getPhone());
                }
                
                request.setAttribute("successMessage", "Profile updated successfully!");
                doGet(request, response);
                return;
            }

            if ("addAddress".equals(action)) {
                // Add new address
                String addressType = request.getParameter("addressType");
                String houseNo = request.getParameter("houseNo");
                String street = request.getParameter("street");
                String city = request.getParameter("city");
                String state = request.getParameter("state");
                String pincode = request.getParameter("pincode");
                String defaultAddress = request.getParameter("defaultAddress");

                Address address = new Address();
                address.setUserId(userId);
                address.setAddressType(addressType);
                address.setHouseNo(houseNo);
                address.setStreet(street);
                address.setCity(city);
                address.setState(state);
                address.setPincode(pincode);
                address.setDefaultAddress("on".equals(defaultAddress));

                // If this is set as default, update all other addresses
                if (address.isDefaultAddress()) {
                    List<Address> existingAddresses = addressDAO.getAddressesByUserId(userId);
                    if (existingAddresses != null) {
                        for (Address addr : existingAddresses) {
                            addr.setDefaultAddress(false);
                            addressDAO.updateAddress(addr);
                        }
                    }
                }

                addressDAO.addAddress(address);
                request.setAttribute("successMessage", "Address added successfully!");
                doGet(request, response);
                return;
            }

            response.sendRedirect(request.getContextPath() + "/profile");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Unable to update profile. Please try again.");
            doGet(request, response);
        }
    }
}