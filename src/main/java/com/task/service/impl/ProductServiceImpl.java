package com.task.service.impl;

import com.task.dto.ProductDTO;
import com.task.model.Product;
import com.task.repository.ProductRepository;
import com.task.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;


    @Override
    public boolean saveProduct(ProductDTO productDTO) {
        Product product = Product.builder()
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .quantity(productDTO.getQuantity())
                .image(productDTO.getImage())
                .productCategory(productDTO.getProductCategory())
                .build();
        productRepository.save(product);
        return true;
    }

    @Override
    public ProductDTO findProductById(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if(product != null){
            return ProductDTO.builder()
                    .id(product.getId())
                    .name(product.getName())
                    .price(product.getPrice())
                    .quantity(product.getQuantity())
                    .image(product.getImage())
                    .productCategory(product.getProductCategory())
                    .build();
        }

        return null;
    }

    @Override
    public List<ProductDTO> viewAllProducts() {
        List<ProductDTO> productDTOList = new ArrayList<>();
        List<Product> productList = productRepository.findAll();
        for(Product product: productList){
            productDTOList.add(ProductDTO.builder()
                            .id(product.getId())
                            .name(product.getName())
                            .image(product.getImage())
                            .productCategory(product.getProductCategory())
                            .price(product.getPrice())
                            .quantity(product.getQuantity())
                            .build());
        }
        return productDTOList;
    }

    @Override
    public ProductDTO updateProductPrice(Double price, Long id) {
        int updated = productRepository.updateProductPrice(price,id);
        if(updated > 0){
            return findProductById(id);
        }
       return null;
    }

    @Override
    public ProductDTO updateProductQuantity(Integer quantity, Long id) {
        int updated = productRepository.updateProductQuantity(quantity,id);
        if(updated > 0){
            return findProductById(id);
        }
        return null;
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

}
