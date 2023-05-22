package com.task.repository;

import com.task.model.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    @Query(
            value = "select * from wishlists where user_id = ?1",
            nativeQuery = true
    )
    List<Wishlist> findAllByStoreUser(Long user_id);
    @Query(value = "select * from wishlists where user_id = ?1 and  product_id = ?2",
            nativeQuery = true
    )
    Optional<Wishlist> findByStoreUserAndProduct(Long user_id, Long product_id);
}
