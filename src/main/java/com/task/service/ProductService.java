package com.task.service;

import com.task.dto.ProductDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    boolean saveProduct(ProductDTO productDTO);
    ProductDTO findProductById(Long id);
    List<ProductDTO> viewAllProducts();
    ProductDTO updateProductPrice(Double price, Long id);
    ProductDTO updateProductQuantity(Integer quantity ,Long id);
    void deleteProduct(Long id);

}
