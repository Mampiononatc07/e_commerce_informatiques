package com.example.e_commerce_informatiques.service;

import org.springframework.stereotype.Service;
import com.example.e_commerce_informatiques.model.Orders;
import com.example.e_commerce_informatiques.repository.DatabaseConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrdersService {

    private final DatabaseConnection databaseConnection;

    public OrdersService(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public boolean createOrder(Orders order) {
        String sql = "INSERT INTO Orders (order_id, order_date, total_amount, order_status) VALUES (?, ?, ?, ?)";
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, order.getOrder_id());
            statement.setDate(2, new java.sql.Date(order.getOrder_date().getTime()));
            statement.setFloat(3, order.getTotal_amount());
            statement.setString(4, String.valueOf(order.getOrder_status()));
    
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    

    public Orders getOrderById(int order_Id) {
        String sql = "SELECT * FROM Orders WHERE order_id = ?";
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, order_Id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return extractOrderFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateOrder(int order_Id, Date order_date, float total_amount, char order_status) {
        String sql = "UPDATE Orders SET order_date = ?, total_amount = ?, order_status = ? WHERE order_id = ?";
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDate(1, new java.sql.Date(order_date.getTime()));
            statement.setFloat(2, total_amount);
            statement.setString(3, String.valueOf(order_status));
            statement.setInt(4, order_Id);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void deleteOrder(int order_Id) {
        String sql = "DELETE FROM Orders WHERE order_id = ?";
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, order_Id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Orders> getAllOrders() {
        List<Orders> ordersList = new ArrayList<>();
        String sql = "SELECT * FROM Orders";
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                Orders order = extractOrderFromResultSet(rs);
                ordersList.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ordersList;
    }

    private Orders extractOrderFromResultSet(ResultSet rs) throws SQLException {
        Orders order = new Orders(0, null, 0, (char) 0);
        order.setOrder_id(rs.getInt("order_id"));
        order.setOrder_date(rs.getDate("order_date"));
        order.setTotal_amount(rs.getFloat("total_amount"));
        order.setOrder_status(rs.getString("order_status").charAt(0));
        return order;
    }
}

