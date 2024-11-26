import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ProductManagementTest {

    @Test
    void insert() throws SQLException {
        Product product = new Product();
        product.setName("abcd");
        product.setCategory("test");
        product.setPrice(BigDecimal.valueOf(1234));
        product.setStatus("in stock");
        product.setStock(2);

        ProductManagement productManagement = new ProductManagement();
        Optional<Product> optProduct = productManagement.insert(product);

        Assertions.assertTrue(optProduct.isPresent());
        Product createdProduct = optProduct.get();
        Assertions.assertEquals(product.getName(), createdProduct.getName());
        Assertions.assertEquals(product.getCategory(), createdProduct.getCategory());
        Assertions.assertEquals(product.getPrice(), createdProduct.getPrice());
        Assertions.assertEquals(product.getStatus(), createdProduct.getStatus());
        Assertions.assertEquals(product.getStock(), createdProduct.getStock());
    }

    @Test
    void getAll() throws SQLException {
        ProductManagement productManagement = new ProductManagement();
        List<Product> result = productManagement.getAll();
        Assertions.assertNotNull(result);
    }

    @Test
    void update() throws SQLException {
        Product product = new Product();
        ProductManagement productManagement = new ProductManagement();

        //Insert new data
        product.setName("abcd");
        product.setCategory("test");
        product.setPrice(BigDecimal.valueOf(1234));
        product.setStatus("in stock");
        product.setStock(2);

        //Insert
        product = productManagement.insert(product).orElseThrow();

        //Update the existing data
        product.setName("abcd");
        product.setCategory("test");
        product.setPrice(BigDecimal.valueOf(23456));
        product.setStatus("in stock");
        product.setStock(10);

        //Update
        Product updatedProduct = productManagement.update(product);

        //Check if it works by comparing the first data and the updated data
        Assertions.assertEquals(product.getName(), updatedProduct.getName());
        Assertions.assertEquals(product.getCategory(), updatedProduct.getCategory());
        Assertions.assertEquals(product.getPrice(), updatedProduct.getPrice());
        Assertions.assertEquals(product.getStatus(), updatedProduct.getStatus());
        Assertions.assertEquals(product.getStock(), updatedProduct.getStock());
    }

    @Test
    void delete() throws SQLException {
        Product product = new Product();
        ProductManagement productManagement = new ProductManagement();

        //Insert new data
        product.setName("abcdefg");
        product.setCategory("test");
        product.setPrice(BigDecimal.valueOf(1234));
        product.setStatus("in stock");
        product.setStock(4);

        //Insert
        product = productManagement.insert(product).orElseThrow();

        //Delete
        productManagement.delete(product.getId());

        //Check by finding the deleted data
        Optional<Product> optProduct = productManagement.findById(product.getId());

        //Check If optProduct returns false then the test succeed
        Assertions.assertTrue(optProduct.isEmpty());
    }

    @Test
    void findById() throws SQLException {
        Product product = new Product();
        product.setName("abcd");
        product.setCategory("test");
        product.setPrice(BigDecimal.valueOf(1234));
        product.setStatus("in stock");
        product.setStock(2);

        ProductManagement productManagement = new ProductManagement();
        product = productManagement.insert(product).orElseThrow();

        Product foundProduct = productManagement.findById(product.getId()).orElseThrow();

        Assertions.assertEquals(product.getName(), foundProduct.getName());
        Assertions.assertEquals(product.getCategory(), foundProduct.getCategory());
        Assertions.assertEquals(product.getPrice(), foundProduct.getPrice());
        Assertions.assertEquals(product.getStatus(), foundProduct.getStatus());
        Assertions.assertEquals(product.getStock(), foundProduct.getStock());
    }
}