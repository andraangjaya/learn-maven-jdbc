import java.math.BigDecimal;

public class CustomerValidator {
    public static void validator(Customer customer) {

        if(customer.getCode() == null || customer.getCode().trim().isEmpty()) {
            throw new InvalidDataException("Customer Code");
        }


        if(customer.getCompanyName() == null || customer.getCompanyName().trim().isEmpty()) {
            throw new InvalidDataException("Customer Company Name");
        }


        if(customer.getFirstName() == null || customer.getFirstName().trim().isEmpty()) {
            throw new InvalidDataException("Customer First Name");
        }


        if(customer.getLastName() == null || customer.getLastName().trim().isEmpty()) {
            throw new InvalidDataException("Customer Last Name");
        }


        if(customer.getEmail() == null || customer.getEmail().trim().isEmpty()) {
            throw new InvalidDataException("Customer Email");
        }


        if(customer.getPhoneNo() == null || customer.getPhoneNo().trim().isEmpty()) {
            throw new InvalidDataException("Customer Phone No");
        }


        if(customer.getMobile() == null || customer.getMobile().trim().isEmpty()) {
            throw new InvalidDataException("Customer Mobile");
        }
    }
}
