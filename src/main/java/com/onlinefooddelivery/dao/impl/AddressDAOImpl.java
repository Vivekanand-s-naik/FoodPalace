package com.onlinefooddelivery.dao.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.onlinefooddelivery.dao.AddressDAO;
import com.onlinefooddelivery.model.Address;
import com.onlinefooddelivery.util.DBConnection;

public class AddressDAOImpl implements AddressDAO {

    private static final String INSERT =
            "INSERT INTO addresses(user_id,house_no,street,city,state,pincode) VALUES(?,?,?,?,?,?)";

    private static final String UPDATE =
            "UPDATE addresses SET user_id=?, house_no=?, street=?, city=?, state=?, pincode=? WHERE address_id=?";

    private static final String DELETE =
            "DELETE FROM addresses WHERE address_id=?";

    private static final String GET_BY_ID =
            "SELECT * FROM addresses WHERE address_id=?";

    private static final String GET_BY_USER =
            "SELECT * FROM addresses WHERE user_id=?";

    private static final String GET_ALL =
            "SELECT * FROM addresses";

    @Override
    public boolean addAddress(Address address) {

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT)) {

            ps.setInt(1, address.getUserId());
            ps.setString(2, address.getHouseNo());
            ps.setString(3, address.getStreet());
            ps.setString(4, address.getCity());
            ps.setString(5, address.getState());
            ps.setString(6, address.getPincode());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean updateAddress(Address address) {

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(UPDATE)) {

            ps.setInt(1, address.getUserId());
            ps.setString(2, address.getHouseNo());
            ps.setString(3, address.getStreet());
            ps.setString(4, address.getCity());
            ps.setString(5, address.getState());
            ps.setString(6, address.getPincode());
            ps.setInt(7, address.getAddressId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean deleteAddress(int addressId) {

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(DELETE)) {

            ps.setInt(1, addressId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public Address getAddressById(int addressId) {

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(GET_BY_ID)) {

            ps.setInt(1, addressId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return extractAddress(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Address> getAddressesByUserId(int userId) {

        List<Address> addresses = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(GET_BY_USER)) {

            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                addresses.add(extractAddress(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return addresses;
    }

    @Override
    public List<Address> getAllAddresses() {

        List<Address> addresses = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(GET_ALL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                addresses.add(extractAddress(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return addresses;
    }

    private Address extractAddress(ResultSet rs) throws SQLException {

        Address address = new Address();

        address.setAddressId(rs.getInt("address_id"));
        address.setUserId(rs.getInt("user_id"));
        address.setHouseNo(rs.getString("house_no"));
        address.setStreet(rs.getString("street"));
        address.setCity(rs.getString("city"));
        address.setState(rs.getString("state"));
        address.setPincode(rs.getString("pincode"));

        return address;
    }
}