package com.flowza.retail.customer;

import com.flowza.retail.config.AppConstant;
import com.flowza.retail.product.Product;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerManagement {
    public Optional<Customer> insert(Customer customer) throws SQLException {
        CustomerValidator.validator(customer);
        customer.setCode(generateCustomerCode(customer.getStore()));
        String sql = "insert into customer (code, company_name, first_name, last_name, email, phone_no, mobile, member_type, store) values (?, ?, ?, ?, ?, ? ,?, ?, ?)";
        try (var connection = DriverManager.getConnection(AppConstant.URL, AppConstant.USER, AppConstant.PASSWORD)) {
            PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            mappingStatementToObj(customer, statement);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    customer.setId(generatedKeys.getLong(1));
                    return Optional.of(customer);
                } else {
                    throw new SQLException("Failed to retrieve customer ID");
                }
            } else {
                throw new SQLException("Failed to insert customer");
            }
        }
    }

    public List<Customer> getAll() throws SQLException {
        List<Customer> customers = new ArrayList<>();
        try (var connection = DriverManager.getConnection(AppConstant.URL, AppConstant.USER, AppConstant.PASSWORD);) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from customer");
            while (resultSet.next()) {
                Customer customer = mappingCustomerToObj(resultSet);
                customers.add(customer);
            }
        }
        return customers;
    }

    public Customer update(Customer customer) throws SQLException {
        CustomerValidator.validator(customer);
        String sql = "update customer set code = ?, company_name = ?, first_name = ?, last_name = ?, email = ?, phone_no = ?, mobile = ?, member_type = ?, store = ? where id = ?";
        try (var connection = DriverManager.getConnection(AppConstant.URL, AppConstant.USER, AppConstant.PASSWORD)) {
            PreparedStatement statement = connection.prepareStatement(sql);
            mappingStatementToObj(customer, statement);
            statement.setLong(10, customer.getId());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                return customer;
            } else {
                throw new SQLException("Failed to update customer");
            }
        }
    }

    public void delete(long id) throws SQLException {
        String sql = "delete from customer where id = ?";
        try (var connection = DriverManager.getConnection(AppConstant.URL, AppConstant.USER, AppConstant.PASSWORD)) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            statement.executeUpdate();
        }
    }

    public Optional<Customer> findById(long id) throws SQLException {
        String sql = "select * from customer where id = ?";
        try (var connection = DriverManager.getConnection(AppConstant.URL, AppConstant.USER, AppConstant.PASSWORD)) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Customer customer = mappingCustomerToObj(resultSet);
                return Optional.of(customer);
            }
        }
        return Optional.empty();
    }

    public String generateCustomerCode(StoreLocation storeLocation) throws SQLException {
        String storeCode = storeLocation.getValue();
        int year = LocalDate.now().getYear();
        String currentYear = String.format("%02d", year % 100);

        int num = retrieveId();
        String sequence = String.format("%05d", num);
        String result = currentYear + storeCode + sequence;
        return result;
    }

    public int retrieveId() throws SQLException{
        String sql = "select nextval('seq_customer_code')";
        try (var connection = DriverManager.getConnection(AppConstant.URL, AppConstant.USER, AppConstant.PASSWORD)) {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        }
        return 1;
    }

    private static Customer mappingCustomerToObj(ResultSet resultSet) throws SQLException {
        Customer customer = new Customer();
        customer.setId(resultSet.getLong("id"));
        customer.setCode(resultSet.getString("code"));
        customer.setCompanyName(resultSet.getString("company_name"));
        customer.setFirstName(resultSet.getString("first_name"));
        customer.setLastName(resultSet.getString("last_name"));
        customer.setEmail(resultSet.getString("email"));
        customer.setPhoneNo(resultSet.getString("phone_no"));
        customer.setMobile(resultSet.getString("mobile"));
        MemberType memberType = MemberType.valueOf(resultSet.getString("member_type"));
        customer.setMemberType(memberType);
        StoreLocation storeLocation = StoreLocation.valueOf(resultSet.getString("store"));
        customer.setStore(storeLocation);
        return customer;
    }

    private static void mappingStatementToObj(Customer customer, PreparedStatement statement) throws SQLException {
        statement.setString(1, customer.getCode());
        statement.setString(2, customer.getCompanyName());
        statement.setString(3, customer.getFirstName());
        statement.setString(4, customer.getLastName());
        statement.setString(5, customer.getEmail());
        statement.setString(6, customer.getPhoneNo());
        statement.setString(7, customer.getMobile());
        statement.setString(8, customer.getMemberType().name());
        statement.setString(9, customer.getStore().name());
    }

}
