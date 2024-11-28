package com.flowza.retail.customer;

import com.flowza.retail.exception.InvalidDataException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CustomerManagementTest {

    @Test
    void insert() throws SQLException {
        Customer customer = new Customer();
        customer.setCompanyName("testcompany");
        customer.setFirstName("testfirstname");
        customer.setLastName("testlastname");
        customer.setEmail("testemail");
        customer.setPhoneNo("testphone");
        customer.setMobile("testmobile");
        customer.setStore(StoreLocation.STORE_C);
        customer.setMemberType(MemberType.BRONZE);

        CustomerManagement cm = new CustomerManagement();
        Optional<Customer> optCustomer = cm.insert(customer);

        Customer insertedCustomer = optCustomer.get();

        Assertions.assertNotNull(customer.getCode(), insertedCustomer.getCode());
        Assertions.assertEquals(customer.getCompanyName(), insertedCustomer.getCompanyName());
        Assertions.assertEquals(customer.getFirstName(), insertedCustomer.getFirstName());
        Assertions.assertEquals(customer.getLastName(), insertedCustomer.getLastName());
        Assertions.assertEquals(customer.getEmail(), insertedCustomer.getEmail());
        Assertions.assertEquals(customer.getPhoneNo(), insertedCustomer.getPhoneNo());
        Assertions.assertEquals(customer.getMobile(), insertedCustomer.getMobile());
        Assertions.assertEquals(customer.getMemberType(), insertedCustomer.getMemberType());
        Assertions.assertEquals(customer.getStore(), insertedCustomer.getStore());
    }

    @Test
    void getAll() throws SQLException {
        CustomerManagement cm = new CustomerManagement();
        List<Customer> result = cm.getAll();
        Assertions.assertNotNull(result);
    }

    @Test
    void update() throws SQLException {
        Customer customer = new Customer();
        CustomerManagement cm = new CustomerManagement();

        //Insert new data
        customer.setCode("as25");
        customer.setCompanyName("testcompany");
        customer.setFirstName("testfirstname");
        customer.setLastName("testlastname");
        customer.setEmail("testemail");
        customer.setPhoneNo("testphone");
        customer.setMobile("testmobile");
        customer.setMemberType(MemberType.SILVER);
        customer.setStore(StoreLocation.STORE_B);

        //Insert
        customer = cm.insert(customer).orElseThrow();

        //Update the existing data
        customer.setCode("as28");
        customer.setCompanyName("testcompany1");
        customer.setFirstName("testfirstname1");
        customer.setLastName("testlastname1");
        customer.setEmail("testemail1");
        customer.setPhoneNo("testphone1");
        customer.setMobile("testmobile1");
        customer.setMemberType(MemberType.GOLD);
        customer.setStore(StoreLocation.STORE_C);


        //Update
        Customer updatedCustomer = cm.update(customer);

        //Check if it works by comparing the first data and the updated data
        Assertions.assertEquals(customer.getCode(), updatedCustomer.getCode());
        Assertions.assertEquals(customer.getCompanyName(), updatedCustomer.getCompanyName());
        Assertions.assertEquals(customer.getFirstName(), updatedCustomer.getFirstName());
        Assertions.assertEquals(customer.getLastName(), updatedCustomer.getLastName());
        Assertions.assertEquals(customer.getEmail(), updatedCustomer.getEmail());
        Assertions.assertEquals(customer.getPhoneNo(), updatedCustomer.getPhoneNo());
        Assertions.assertEquals(customer.getMobile(), updatedCustomer.getMobile());
        Assertions.assertEquals(customer.getMemberType(), updatedCustomer.getMemberType());
        Assertions.assertEquals(customer.getStore(), updatedCustomer.getStore());
    }

    @Test
    void delete() throws SQLException {
        Customer customer = new Customer();
        CustomerManagement cm = new CustomerManagement();

        //Insert new data
        customer.setCode("as29");
        customer.setCompanyName("testcompany");
        customer.setFirstName("testfirstname");
        customer.setLastName("testlastname");
        customer.setEmail("testemail");
        customer.setPhoneNo("testphone");
        customer.setMobile("testmobile");
        customer.setMemberType(MemberType.SILVER);
        customer.setStore(StoreLocation.STORE_B);

        //Insert
        customer = cm.insert(customer).orElseThrow();

        cm.delete(customer.getId());

        Optional<Customer> optCustomer = cm.findById(customer.getId());

        Assertions.assertTrue(optCustomer.isEmpty());
    }

    @Test
    void findById() throws SQLException {
        Customer customer = new Customer();
        customer.setCode("as30");
        customer.setCompanyName("testcompany");
        customer.setFirstName("testfirstname");
        customer.setLastName("testlastname");
        customer.setEmail("testemail");
        customer.setPhoneNo("testphone");
        customer.setMobile("testmobile");
        customer.setMemberType(MemberType.BRONZE);
        customer.setStore(StoreLocation.STORE_B);

        CustomerManagement cm = new CustomerManagement();
        customer = cm.insert(customer).orElseThrow();

        Customer foundCustomer = cm.findById(customer.getId()).orElseThrow();

        Assertions.assertEquals(customer.getCode(), foundCustomer.getCode());
        Assertions.assertEquals(customer.getCompanyName(), foundCustomer.getCompanyName());
        Assertions.assertEquals(customer.getFirstName(), foundCustomer.getFirstName());
        Assertions.assertEquals(customer.getLastName(), foundCustomer.getLastName());
        Assertions.assertEquals(customer.getEmail(), foundCustomer.getEmail());
        Assertions.assertEquals(customer.getPhoneNo(), foundCustomer.getPhoneNo());
        Assertions.assertEquals(customer.getMobile(), foundCustomer.getMobile());
        Assertions.assertEquals(customer.getMemberType(), foundCustomer.getMemberType());
        Assertions.assertEquals(customer.getStore(), foundCustomer.getStore());
    }


    @Test
    void validateInsertCompanyName() {
        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
            Customer customer = new Customer();
            customer.setCode("asdf312");
            new CustomerManagement().insert(customer);
        });

        assertEquals("Invalid data field: com.flowza.retail.customer.Customer Company Name", exception.getMessage());
    }

    @Test
    void validateInsertFirstName() {
        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
            Customer customer = new Customer();
            customer.setCode("asdf312");
            customer.setCompanyName("testcompany2");
            new CustomerManagement().insert(customer);
        });

        assertEquals("Invalid data field: com.flowza.retail.customer.Customer First Name", exception.getMessage());
    }

    @Test
    void validateInsertlastName() {
        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
            Customer customer = new Customer();
            customer.setCode("asdf312");
            customer.setCompanyName("testcompany2");
            customer.setFirstName("testfirst2");
            new CustomerManagement().insert(customer);
        });

        assertEquals("Invalid data field: com.flowza.retail.customer.Customer Last Name", exception.getMessage());
    }

    @Test
    void validateInsertEmail() {
        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
            Customer customer = new Customer();
            customer.setCode("asdf312");
            customer.setCompanyName("testcompany2");
            customer.setFirstName("testfirst2");
            customer.setLastName("testlast2");
            new CustomerManagement().insert(customer);
        });

        assertEquals("Invalid data field: com.flowza.retail.customer.Customer Email", exception.getMessage());
    }

    @Test
    void validateInsertPhoneNo() {
        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
            Customer customer = new Customer();
            customer.setCode("asdf312");
            customer.setCompanyName("testcompany2");
            customer.setFirstName("testfirst2");
            customer.setLastName("testlast2");
            customer.setEmail("testemail2");
            new CustomerManagement().insert(customer);
        });

        assertEquals("Invalid data field: com.flowza.retail.customer.Customer Phone No", exception.getMessage());
    }

    @Test
    void validateInsertMobile() {
        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
            Customer customer = new Customer();
            customer.setCode("asdf312");
            customer.setCompanyName("testcompany2");
            customer.setFirstName("testfirst2");
            customer.setLastName("testlast2");
            customer.setEmail("testemail2");
            customer.setPhoneNo("testphone2");
            new CustomerManagement().insert(customer);
        });

        assertEquals("Invalid data field: com.flowza.retail.customer.Customer Mobile", exception.getMessage());
    }

    @Test
    void validateInsertMemberType() {
        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
            Customer customer = new Customer();
            customer.setCode("asdf312");
            customer.setCompanyName("testcompany2");
            customer.setFirstName("testfirst2");
            customer.setLastName("testlast2");
            customer.setEmail("testemail2");
            customer.setPhoneNo("testphone2");
            customer.setMobile("testmobile2");
            new CustomerManagement().insert(customer);
        });

        assertEquals("Invalid data field: com.flowza.retail.customer.Customer Member Type", exception.getMessage());
    }

    @Test
    void validateInsertStore() {
        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
            Customer customer = new Customer();
            customer.setCode("asdf312");
            customer.setCompanyName("testcompany2");
            customer.setFirstName("testfirst2");
            customer.setLastName("testlast2");
            customer.setEmail("testemail2");
            customer.setPhoneNo("testphone2");
            customer.setMobile("testmobile2");
            customer.setMemberType(MemberType.GOLD);
            new CustomerManagement().insert(customer);
        });

        assertEquals("Invalid data field: com.flowza.retail.customer.Customer Store", exception.getMessage());
    }



}