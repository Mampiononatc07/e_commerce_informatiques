package com.example.e_commerce_informatiques.service;

import org.springframework.stereotype.Service;
import com.example.e_commerce_informatiques.model.Products;
import com.example.e_commerce_informatiques.repository.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductsService {

    private final DatabaseConnection databaseConnection;

    public ProductsService(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public boolean createProduct(Products product) {
        String sql = "INSERT INTO Products (product_id, product_name, description, category, price) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, product.getProduct_id());
            statement.setString(2, product.getProduct_name());
            statement.setString(3, product.getDescription());
            statement.setString(4, product.getCategory());
            statement.setFloat(5, product.getPrice());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Products getProductById(int product_Id) {
        String sql = "SELECT * FROM Products WHERE product_id = ?";
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, product_Id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return extractProductFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateProduct(int product_Id, String product_name, String description,
                                 String category, Float price) {
        String sql = "UPDATE Products SET product_name = ?, description = ?, price = ?, category = ? WHERE product_id = ?";
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, product_name);
            statement.setString(2, description);
            statement.setFloat(3, price);
            statement.setString(4, category);
            statement.setInt(5, product_Id);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void deleteProduct(int product_Id) {
        String sql = "DELETE FROM Products WHERE product_id = ?";
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, product_Id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Products> getAllProducts() {
        List<Products> productsList = new ArrayList<>();
        String sql = "SELECT * FROM Products";
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                Products product = extractProductFromResultSet(rs);
                productsList.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productsList;
    }

    private Products extractProductFromResultSet(ResultSet rs) throws SQLException {
        Products product = new Products(0, null, null, null, null);
        product.setProduct_id(rs.getInt("product_id"));
        product.setProduct_name(rs.getString("product_name"));
        product.setDescription(rs.getString("description"));
        product.setPrice(rs.getFloat("price"));
        product.setCategory(rs.getString("category"));
        return product;
    }
}