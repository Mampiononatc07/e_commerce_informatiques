package com.example.e_commerce_informatiques.service;

import org.springframework.stereotype.Service;

import com.example.e_commerce_informatiques.model.Customers;
import com.example.e_commerce_informatiques.repository.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomersService {

    private final DatabaseConnection databaseConnection;

    public CustomersService(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }
    
    public boolean createCustomer(Customers customer) {
        String sql = "INSERT INTO Customers (customer_id, first_name, last_name, email, shipping_address, phone_number) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, customer.getCustomer_id());
            statement.setString(2, customer.getFirst_name());
            statement.setString(3, customer.getLast_name());
            statement.setString(4, customer.getEmail());
            statement.setString(5, customer.getShipping_address());
            statement.setString(6, customer.getPhone_number());
    
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Customers getCustomerById(int customer_Id) {
        String sql = "SELECT * FROM Customers WHERE customer_id = ?";
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, customer_Id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return extractCustomerFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateCustomer(int customer_Id, String first_name, String last_name,
                                  String email, String shipping_address, String phone_number) {
        String sql = "UPDATE Customers SET first_name = ?, last_name = ?, email = ?, " +
                     "shipping_address = ?, phone_number = ? WHERE customer_id = ?";
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, first_name);
            statement.setString(2, last_name);
            statement.setString(3, email);
            statement.setString(4, shipping_address);
            statement.setString(5, phone_number);
            statement.setInt(6, customer_Id);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void deleteCustomer(int customer_Id) {
        String sql = "DELETE FROM Customers WHERE customer_id = ?";
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, customer_Id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Customers> getAllCustomers() {
        List<Customers> customersList = new ArrayList<>();
        String sql = "SELECT * FROM Customers";
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                Customers customer = extractCustomerFromResultSet(rs);
                customersList.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customersList;
    }

    private Customers extractCustomerFromResultSet(ResultSet rs) throws SQLException {
        Customers customer = new Customers(0, null, null, null, null, null);
        customer.setCustomer_id(rs.getInt("customer_id"));
        customer.setFirst_name(rs.getString("first_name"));
        customer.setLast_name(rs.getString("last_name"));
        customer.setEmail(rs.getString("email"));
        customer.setShipping_address(rs.getString("shipping_address"));
        customer.setPhone_number(rs.getString("phone_number"));
        return customer;
    }
}