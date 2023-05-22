package com.task.serviceImpl;

import com.task.dto.ProductDTO;
import com.task.enums.ProductCategory;
import com.task.model.Product;
import com.task.repository.ProductRepository;
import com.task.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ProductServiceImplTest {
    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductServiceImpl productService;
    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveProduct() {
        ProductCategory productCategory = ProductCategory.GROCERIES;
        byte[] image = {(byte) 4231};
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setName("Bread");
        productDTO.setPrice(1000.0);
        productDTO.setProductCategory(productCategory);
        productDTO.setImage(image);
        productDTO.setQuantity(2);

        Product product = Product.builder()
                .id(productDTO.getId())
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .quantity(productDTO.getQuantity())
                .image(productDTO.getImage())
                .productCategory(productDTO.getProductCategory())
                .build();
        when(productRepository.save(product)).thenReturn(product);
        assertTrue(productService.saveProduct(productDTO));
    }

    @Test
    void findProductById() {
        ProductCategory productCategory = ProductCategory.GROCERIES;
        byte[] image = {(byte) 4231};
        Product product = Product.builder()
                .id(1L)
                .name("Bread")
                .price(1000.0)
                .quantity(2)
                .image(image)
                .productCategory(productCategory)
                .build();
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        ProductDTO productDTO = productService.findProductById(product.getId());

        assertNotNull(productDTO);
        assertEquals(product.getName(),productDTO.getName());
    }

    @Test
    void viewAllProducts() {
        ProductCategory productCategory = ProductCategory.GROCERIES;
        byte[] image = {(byte) 4231};
        Product product = Product.builder()
                .id(1L)
                .name("Bread")
                .price(1000.0)
                .quantity(2)
                .image(image)
                .productCategory(productCategory)
                .build();
        List<Product> productList = new ArrayList<>();
        productList.add(product);

        when(productRepository.findAll()).thenReturn(productList);
        List<ProductDTO> productDTOList = productService.viewAllProducts();
        assertNotNull(productDTOList);
        assertEquals(product.getName(),productDTOList.get(0).getName());
    }

    @Test
    void updateProductPrice() {
        Long product_id = 1L;
        double price = 100.0;
        int updated = 1;
        when(productRepository.updateProductPrice(price,product_id)).thenReturn(1);

        ProductCategory productCategory = ProductCategory.GROCERIES;
        byte[] image = {(byte) 4231};
        Product product = Product.builder()
                .id(product_id)
                .name("Bread")
                .price(price)
                .quantity(2)
                .image(image)
                .productCategory(productCategory)
                .build();
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        ProductDTO productDTO = productService.findProductById(product.getId());

        assertNotNull(productDTO);
        assertEquals(product.getPrice(),productDTO.getPrice());

    }

    @Test
    void updateProductQuantity() {
        Long product_id = 1L;
        int quantity = 1;
        int updated = 1;
        when(productRepository.updateProductQuantity(quantity,product_id)).thenReturn(1);

        ProductCategory productCategory = ProductCategory.GROCERIES;
        byte[] image = {(byte) 4231};
        Product product = Product.builder()
                .id(product_id)
                .name("Bread")
                .price(100.0)
                .quantity(quantity)
                .image(image)
                .productCategory(productCategory)
                .build();
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        ProductDTO productDTO = productService.findProductById(product.getId());

        assertNotNull(productDTO);
        assertEquals(product.getQuantity(),productDTO.getQuantity());
    }
}