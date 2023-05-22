package com.task.service;

import com.task.dto.WishlistDTO;

import java.util.List;

public interface WishlistService {
    boolean addToWishlist(Long user_id, Long product_id);
    List<WishlistDTO> viewWishlist(Long user_id);

    void deleteFromWishlist(Long wishlist_id);
    boolean addToCart(Long wishlist_id);
}
