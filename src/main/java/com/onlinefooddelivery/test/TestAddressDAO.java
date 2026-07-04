package com.onlinefooddelivery.test;

import java.util.List;

import com.onlinefooddelivery.dao.AddressDAO;
import com.onlinefooddelivery.dao.impl.AddressDAOImpl;
import com.onlinefooddelivery.model.Address;

public class TestAddressDAO {

    private static AddressDAO dao = new AddressDAOImpl();

    public static void main(String[] args) {

        System.out.println("===== ADDRESS DAO TEST =====");

        testAddAddress();
        testGetAllAddresses();
        testGetAddressById();
        testGetAddressesByUserId();
        testUpdateAddress();
        testDeleteAddress();

    }

    public static void testAddAddress() {

        Address address = new Address();

        address.setUserId(1);
        address.setHouseNo("12A");
        address.setStreet("MG Road");
        address.setCity("Bangalore");
        address.setState("Karnataka");
        address.setPincode("560001");

        boolean result = dao.addAddress(address);

        System.out.println("Address Added : " + result);

    }

    public static void testGetAllAddresses() {

        System.out.println("\n===== ALL ADDRESSES =====");

        List<Address> addresses = dao.getAllAddresses();

        for (Address address : addresses) {
            System.out.println(address);
        }

    }

    public static void testGetAddressById() {

        System.out.println("\n===== GET ADDRESS =====");

        System.out.println(dao.getAddressById(1));

    }

    public static void testGetAddressesByUserId() {

        System.out.println("\n===== USER ADDRESSES =====");

        List<Address> addresses = dao.getAddressesByUserId(1);

        for (Address address : addresses) {
            System.out.println(address);
        }

    }

    public static void testUpdateAddress() {

        System.out.println("\n===== UPDATE ADDRESS =====");

        Address address = dao.getAddressById(1);

        if (address != null) {

            address.setCity("Mysore");

            boolean result = dao.updateAddress(address);

            System.out.println("Updated : " + result);

        }

    }

    /**
     * 
     */
    public static void testDeleteAddress() {

        System.out.println("\n===== DELETE ADDRESS =====");

        boolean result = dao.deleteAddress(11);

        System.out.println("Deleted : " + result);

    }

}