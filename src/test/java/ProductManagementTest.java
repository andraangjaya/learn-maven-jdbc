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
        product.setSubCategory("testsssr");
        product.setSellPrice(BigDecimal.valueOf(1234));
        product.setStatus(ProductStatus.AVAILABLE);
        product.setStock(2);
        product.setBarcode("mvsnjee23");
        product.setPurchasePrice(BigDecimal.valueOf(6572090));

        ProductManagement productManagement = new ProductManagement();
        Optional<Product> optProduct = productManagement.insert(product);

        Assertions.assertTrue(optProduct.isPresent());
        Product createdProduct = optProduct.get();
        Assertions.assertEquals(product.getName(), createdProduct.getName());
        Assertions.assertEquals(product.getCategory(), createdProduct.getCategory());
        Assertions.assertEquals(product.getSubCategory(), createdProduct.getSubCategory());
        Assertions.assertEquals(product.getSellPrice(), createdProduct.getSellPrice());
        Assertions.assertEquals(product.getStatus(), createdProduct.getStatus());
        Assertions.assertEquals(product.getStock(), createdProduct.getStock());
        Assertions.assertEquals(product.getBarcode(), createdProduct.getBarcode());
        Assertions.assertEquals(product.getPurchasePrice(), createdProduct.getPurchasePrice());
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
        product.setSubCategory("testidk");
        product.setSellPrice(BigDecimal.valueOf(1234));
        product.setStatus(ProductStatus.AVAILABLE);
        product.setStock(2);
        product.setBarcode("123rabcdse");
        product.setPurchasePrice(BigDecimal.valueOf(124345423));

        //Insert
        product = productManagement.insert(product).orElseThrow();

        //Update the existing data
        product.setName("abcd");
        product.setCategory("test");
        product.setSubCategory("idk");
        product.setSellPrice(BigDecimal.valueOf(23456));
        product.setStatus(ProductStatus.DISCONTINUE);
        product.setStock(10);
        product.setBarcode("3543adfs");
        product.setPurchasePrice(BigDecimal.valueOf(5673));


        //Update
        Product updatedProduct = productManagement.update(product);

        //Check if it works by comparing the first data and the updated data
        Assertions.assertEquals(product.getName(), updatedProduct.getName());
        Assertions.assertEquals(product.getCategory(), updatedProduct.getCategory());
        Assertions.assertEquals(product.getSubCategory(), updatedProduct.getSubCategory());
        Assertions.assertEquals(product.getSellPrice(), updatedProduct.getSellPrice());
        Assertions.assertEquals(product.getStatus(), updatedProduct.getStatus());
        Assertions.assertEquals(product.getStock(), updatedProduct.getStock());
        Assertions.assertEquals(product.getBarcode(), updatedProduct.getBarcode());
        Assertions.assertEquals(product.getPurchasePrice(), updatedProduct.getPurchasePrice());
    }

    @Test
    void delete() throws SQLException {
        Product product = new Product();
        ProductManagement productManagement = new ProductManagement();

        //Insert new data
        product.setName("abcdefg");
        product.setCategory("test");
        product.setSubCategory("teststststststts");
        product.setSellPrice(BigDecimal.valueOf(1234));
        product.setStatus(ProductStatus.ARCHIVED);
        product.setStock(4);
        product.setBarcode("ksldherj79000");
        product.setPurchasePrice(BigDecimal.valueOf(89.0));

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
        product.setSubCategory("subtest");
        product.setSellPrice(BigDecimal.valueOf(1234));
        product.setStatus(ProductStatus.OUT_OF_STOCK);
        product.setStock(2);
        product.setBarcode("fgherttyu");
        product.setPurchasePrice(BigDecimal.valueOf(98.1));

        ProductManagement productManagement = new ProductManagement();
        product = productManagement.insert(product).orElseThrow();

        Product foundProduct = productManagement.findById(product.getId()).orElseThrow();

        Assertions.assertEquals(product.getName(), foundProduct.getName());
        Assertions.assertEquals(product.getCategory(), foundProduct.getCategory());
        Assertions.assertEquals(product.getSubCategory(), foundProduct.getSubCategory());
        Assertions.assertEquals(product.getSellPrice(), foundProduct.getSellPrice());
        Assertions.assertEquals(product.getStatus(), foundProduct.getStatus());
        Assertions.assertEquals(product.getStock(), foundProduct.getStock());
        Assertions.assertEquals(product.getBarcode(), foundProduct.getBarcode());
        Assertions.assertEquals(product.getPurchasePrice(), foundProduct.getPurchasePrice());
    }

    @Test
    void validateInsertName() {
        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
            Product product = new Product();
            new ProductManagement().insert(product);
        });

        assertEquals("Invalid data field: Product Name", exception.getMessage());
    }

    @Test
    void validateInsertPrice() {
        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
            Product product = new Product();
            product.setName("abcd");
            new ProductManagement().insert(product);
        });

        assertEquals("Invalid data field: Product Sell Price", exception.getMessage());
    }

    @Test
    void validateInsertCategory() {
        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
            Product product = new Product();
            product.setName("abcd");
            product.setSellPrice(BigDecimal.valueOf(1234));
            new ProductManagement().insert(product);
        });

        assertEquals("Invalid data field: Product Category", exception.getMessage());
    }

    @Test
    void validateInsertStatus() {
        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
            Product product = new Product();
            product.setName("abcd");
            product.setSellPrice(BigDecimal.valueOf(1234));
            product.setCategory("test");
            new ProductManagement().insert(product);
        });

        assertEquals("Invalid data field: Product Status", exception.getMessage());
    }

    @Test
    void validateInsertStock() {
        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
            Product product = new Product();
            product.setName("abcd");
            product.setSellPrice(BigDecimal.valueOf(1234));
            product.setCategory("test");
            product.setStatus(ProductStatus.OUT_OF_STOCK);
            new ProductManagement().insert(product);
        });

        assertEquals("Invalid data field: Product Stock", exception.getMessage());
    }

    @Test
    void validateInsertBarcode() {
        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
            Product product = new Product();
            product.setName("abcd");
            product.setSellPrice(BigDecimal.valueOf(1234));
            product.setCategory("test");
            product.setStatus(ProductStatus.OUT_OF_STOCK);
            product.setStock(10);
            new ProductManagement().insert(product);
        });

        assertEquals("Invalid data field: Product Barcode", exception.getMessage());
    }

    @Test
    void validateInsertSubCategory() {
        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
            Product product = new Product();
            product.setName("abcd");
            product.setSellPrice(BigDecimal.valueOf(1234));
            product.setCategory("test");
            product.setStatus(ProductStatus.OUT_OF_STOCK);
            product.setStock(10);
            product.setBarcode("1243ajdja");
            new ProductManagement().insert(product);
        });

        assertEquals("Invalid data field: Product Sub Category", exception.getMessage());
    }

    @Test
    void validateInsertPurchasePrice() {
        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> {
            Product product = new Product();
            product.setName("abcd");
            product.setSellPrice(BigDecimal.valueOf(1234));
            product.setCategory("test");
            product.setStatus(ProductStatus.OUT_OF_STOCK);
            product.setStock(10);
            product.setBarcode("1243ajdja");
            product.setSubCategory("subtest");
            new ProductManagement().insert(product);
        });

        assertEquals("Invalid data field: Product Purchase Price", exception.getMessage());
    }
}