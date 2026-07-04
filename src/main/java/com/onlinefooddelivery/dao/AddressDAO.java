package com.onlinefooddelivery.dao;

import java.util.List;

import com.onlinefooddelivery.model.Address;

public interface AddressDAO {

    boolean addAddress(Address address);

    boolean updateAddress(Address address);

    boolean deleteAddress(int addressId);

    Address getAddressById(int addressId);

    List<Address> getAddressesByUserId(int userId);

    List<Address> getAllAddresses();

}