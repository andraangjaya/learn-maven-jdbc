package com.flowza.retail.customer;

import com.flowza.retail.exception.InvalidDataException;

public class CustomerValidator {
    public static void validator(Customer customer) {

        if(customer.getCompanyName() == null || customer.getCompanyName().trim().isEmpty()) {
            throw new InvalidDataException("com.flowza.retail.customer.Customer Company Name");
        }


        if(customer.getFirstName() == null || customer.getFirstName().trim().isEmpty()) {
            throw new InvalidDataException("com.flowza.retail.customer.Customer First Name");
        }


        if(customer.getLastName() == null || customer.getLastName().trim().isEmpty()) {
            throw new InvalidDataException("com.flowza.retail.customer.Customer Last Name");
        }


        if(customer.getEmail() == null || customer.getEmail().trim().isEmpty()) {
            throw new InvalidDataException("com.flowza.retail.customer.Customer Email");
        }


        if(customer.getPhoneNo() == null || customer.getPhoneNo().trim().isEmpty()) {
            throw new InvalidDataException("com.flowza.retail.customer.Customer Phone No");
        }


        if(customer.getMobile() == null || customer.getMobile().trim().isEmpty()) {
            throw new InvalidDataException("com.flowza.retail.customer.Customer Mobile");
        }

        if(customer.getMemberType() == null){
            throw new InvalidDataException("com.flowza.retail.customer.Customer Member Type");
        }

        if(customer.getStore() == null){
            throw new InvalidDataException("com.flowza.retail.customer.Customer Store");
        }

    }
}
