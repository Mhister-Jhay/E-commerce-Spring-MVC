package com.task.repository;

import com.task.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {
    @Query(
            value = "select * from carts where user_id = ?1",
            nativeQuery = true
    )
    List<Cart> findAllByStoreUser(Long user_id);
    @Query(value = "select * from carts where user_id = ?1 and  product_id = ?2",
            nativeQuery = true
    )
    Optional<Cart> findByStoreUserAndProduct(Long user_id, Long product_id);
    @Modifying
    @Transactional
    @Query(
            value = "update carts set quantity = ?1 where product_id = ?2 and user_id = ?3",
            nativeQuery = true
    )
    int updateCartQuantity(Integer quantity, Long product_id, Long user_id);
    @Query(
            value = "select quantity from carts where product_id = ?1 and user_id = ?2",
            nativeQuery = true
    )
    int selectCartQuantity(Long product_id, Long user_id);
    @Modifying
    @Transactional
    @Query(
            value = "delete from carts where user_id = ?1",
            nativeQuery = true
    )
    int deleteAllByStoreUser(Long user_id);
}
