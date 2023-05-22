package com.task.repository;

import com.task.dto.ProductDTO;
import com.task.model.Product;
import jakarta.persistence.Table;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Modifying
    @Transactional
    @Query(
            value = "update products set price = ?1 where id = ?2",
            nativeQuery = true
    )
    int updateProductPrice(Double price, Long id);

    @Modifying
    @Transactional
    @Query(
            value = "update products set quantity = ?1 where id = ?2",
            nativeQuery = true
    )
    int updateProductQuantity(Integer quantity, Long id);


}
