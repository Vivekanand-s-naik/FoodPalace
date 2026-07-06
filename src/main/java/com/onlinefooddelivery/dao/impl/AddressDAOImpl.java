package com.onlinefooddelivery.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.onlinefooddelivery.dao.AddressDAO;
import com.onlinefooddelivery.model.Address;
import com.onlinefooddelivery.util.DBConnection;

public class AddressDAOImpl implements AddressDAO {

    private static final String INSERT_WITH_EXTRA
            = "INSERT INTO addresses(user_id,house_no,street,city,state,pincode,address_type,default_address) VALUES(?,?,?,?,?,?,?,?)";

    private static final String INSERT_BASIC
            = "INSERT INTO addresses(user_id,house_no,street,city,state,pincode) VALUES(?,?,?,?,?,?)";

    private static final String UPDATE_WITH_EXTRA
            = "UPDATE addresses SET user_id=?, house_no=?, street=?, city=?, state=?, pincode=?, address_type=?, default_address=? WHERE address_id=?";

    private static final String UPDATE_BASIC
            = "UPDATE addresses SET user_id=?, house_no=?, street=?, city=?, state=?, pincode=? WHERE address_id=?";

    private static final String DELETE
            = "DELETE FROM addresses WHERE address_id=?";

    private static final String GET_BY_ID
            = "SELECT * FROM addresses WHERE address_id=?";

    private static final String GET_BY_USER
            = "SELECT * FROM addresses WHERE user_id=?";

    private static final String GET_ALL
            = "SELECT * FROM addresses";

    @Override
    public boolean addAddress(Address address) {

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(INSERT_WITH_EXTRA)) {

            ps.setInt(1, address.getUserId());
            ps.setString(2, address.getHouseNo());
            ps.setString(3, address.getStreet());
            ps.setString(4, address.getCity());
            ps.setString(5, address.getState());
            ps.setString(6, address.getPincode());
            ps.setString(7, address.getAddressType() != null ? address.getAddressType() : "Home");
            ps.setInt(8, address.isDefaultAddress() ? 1 : 0);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            if (isMissingColumnError(e)) {
                try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(INSERT_BASIC)) {

                    ps.setInt(1, address.getUserId());
                    ps.setString(2, address.getHouseNo());
                    ps.setString(3, address.getStreet());
                    ps.setString(4, address.getCity());
                    ps.setString(5, address.getState());
                    ps.setString(6, address.getPincode());

                    return ps.executeUpdate() > 0;
                } catch (SQLException inner) {
                    inner.printStackTrace();
                }
            } else {
                e.printStackTrace();
            }
        }

        return false;
    }

    @Override
    public boolean updateAddress(Address address) {

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(UPDATE_WITH_EXTRA)) {

            ps.setInt(1, address.getUserId());
            ps.setString(2, address.getHouseNo());
            ps.setString(3, address.getStreet());
            ps.setString(4, address.getCity());
            ps.setString(5, address.getState());
            ps.setString(6, address.getPincode());
            ps.setString(7, address.getAddressType() != null ? address.getAddressType() : "Home");
            ps.setInt(8, address.isDefaultAddress() ? 1 : 0);
            ps.setInt(9, address.getAddressId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            if (isMissingColumnError(e)) {
                try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(UPDATE_BASIC)) {

                    ps.setInt(1, address.getUserId());
                    ps.setString(2, address.getHouseNo());
                    ps.setString(3, address.getStreet());
                    ps.setString(4, address.getCity());
                    ps.setString(5, address.getState());
                    ps.setString(6, address.getPincode());
                    ps.setInt(7, address.getAddressId());

                    return ps.executeUpdate() > 0;
                } catch (SQLException inner) {
                    inner.printStackTrace();
                }
            } else {
                e.printStackTrace();
            }
        }

        return false;
    }

    @Override
    public boolean deleteAddress(int addressId) {

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(DELETE)) {

            ps.setInt(1, addressId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public Address getAddressById(int addressId) {

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(GET_BY_ID)) {

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

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(GET_BY_USER)) {

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

        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(GET_ALL); ResultSet rs = ps.executeQuery()) {

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
        address.setAddressType(getOptionalString(rs, "address_type", "Home"));
        address.setDefaultAddress(getOptionalBoolean(rs, "default_address", false));

        return address;
    }

    private boolean isMissingColumnError(SQLException e) {
        String message = e.getMessage();
        return message != null && (message.contains("Unknown column") || message.contains("doesn't exist") || message.contains("has no column"));
    }

    private String getOptionalString(ResultSet rs, String columnName, String defaultValue) {
        try {
            String value = rs.getString(columnName);
            return value != null ? value : defaultValue;
        } catch (SQLException e) {
            return defaultValue;
        }
    }

    private boolean getOptionalBoolean(ResultSet rs, String columnName, boolean defaultValue) {
        try {
            return rs.getBoolean(columnName);
        } catch (SQLException e) {
            return defaultValue;
        }
    }
}
