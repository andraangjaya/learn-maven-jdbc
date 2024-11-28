package com.flowza.retail.product;

import com.flowza.retail.config.AppConstant;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductManagement {
    public Optional<Product> insert(Product product) throws SQLException {
        ProductValidator.validator(product);
        String sql = "insert into product (name, sell_price, category, sub_category, status, stock, barcode, purchase_price) values (?, ?, ?, ?, ?, ? ,? ,?)";
        try (var connection = DriverManager.getConnection(AppConstant.URL, AppConstant.USER, AppConstant.PASSWORD)) {
            PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            mappingStatementToObj(product, statement);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    product.setId(generatedKeys.getLong(1));
                    return Optional.of(product);
                } else {
                    throw new SQLException("Failed to retrieve product ID");
                }
            } else {
                throw new SQLException("Failed to insert product");
            }
        }
    }

    public List<Product> getAll() throws SQLException {
        List<Product> products = new ArrayList<>();
        try (var connection = DriverManager.getConnection(AppConstant.URL, AppConstant.USER, AppConstant.PASSWORD);) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from product");
            while (resultSet.next()) {
                Product product = mappingProductToObj(resultSet);
                products.add(product);
            }
        }
        return products;
    }

    public Product update(Product product) throws SQLException {
        ProductValidator.validator(product);
        String sql = "update product set name = ?, sell_price = ?, category = ?, sub_category = ?, status = ?, stock = ?, barcode = ?, purchase_price = ? where id = ?";
        try (var connection = DriverManager.getConnection(AppConstant.URL, AppConstant.USER, AppConstant.PASSWORD)) {
            PreparedStatement statement = connection.prepareStatement(sql);
            mappingStatementToObj(product, statement);
            statement.setLong(9, product.getId());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                return product;
            } else {
                throw new SQLException("Failed to update product");
            }
        }
    }

    public void delete(long id) throws SQLException {
        String sql = "delete from product where id = ?";
        try (var connection = DriverManager.getConnection(AppConstant.URL, AppConstant.USER, AppConstant.PASSWORD)) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            statement.executeUpdate();
        }
    }

    public Optional<Product> findById(long id) throws SQLException {
        String sql = "select * from product where id = ?";
        try (var connection = DriverManager.getConnection(AppConstant.URL, AppConstant.USER, AppConstant.PASSWORD)) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Product product = mappingProductToObj(resultSet);
                return Optional.of(product);
            }
        }
        return Optional.empty();
    }

    private static void mappingStatementToObj(Product product, PreparedStatement statement) throws SQLException {
        statement.setString(1, product.getName());
        statement.setBigDecimal(2, product.getSellPrice());
        statement.setString(3, product.getCategory());
        statement.setString(4, product.getSubCategory());
        statement.setString(5, product.getStatus().name());
        statement.setInt(6, product.getStock());
        statement.setString(7, product.getBarcode());
        statement.setBigDecimal(8, product.getPurchasePrice());
    }

    private static Product mappingProductToObj(ResultSet resultSet) throws SQLException {
        Product product = new Product();
        product.setId(resultSet.getLong("id"));
        product.setName(resultSet.getString("name"));
        product.setSellPrice(resultSet.getBigDecimal("sell_price"));
        product.setCategory(resultSet.getString("category"));
        product.setSubCategory(resultSet.getString("sub_category"));
        ProductStatus status = ProductStatus.valueOf(resultSet.getString("status"));
        product.setStatus(status);
        product.setStock(resultSet.getInt("stock"));
        product.setBarcode(resultSet.getString("barcode"));
        product.setPurchasePrice(resultSet.getBigDecimal("purchase_price"));
        return product;
    }
}
