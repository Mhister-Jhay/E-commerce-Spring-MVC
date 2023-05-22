package com.task.service.impl;

import com.task.dto.WishlistDTO;
import com.task.model.Product;
import com.task.model.User;
import com.task.model.Wishlist;
import com.task.repository.ProductRepository;
import com.task.repository.UserRepository;
import com.task.repository.WishlistRepository;
import com.task.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {
    private final UserRepository userRepository;
    private final WishlistRepository wishlistRepository;
    private  final ProductRepository productRepository;
    private final CartServiceImpl cartServiceImpl;

    @Override
    public boolean addToWishlist(Long user_id, Long product_id) {
        User user = userRepository.findStoreUsersById(user_id).orElse(null);
        Product product = productRepository.findById(product_id).orElse(null);
        if(user != null && product != null){
            Wishlist wishlist = wishlistRepository.findByStoreUserAndProduct(user_id,product_id).orElse(null);
            if(wishlist == null){
                wishlistRepository.save(Wishlist.builder()
                        .storeUser(user)
                        .product(product)
                        .build());
                return true;
            } else{
               return false;
            }
        }
        return false;
    }

    @Override
    public List<WishlistDTO> viewWishlist(Long user_id) {
        User user = userRepository.findStoreUsersById(user_id).orElse(null);
        if(user != null){
            List<Wishlist> wishlistList = wishlistRepository.findAllByStoreUser(user_id);
            List<WishlistDTO> wishlistDTOList = new ArrayList<>();
            for(Wishlist wishlist:wishlistList){
                Product product = productRepository.findById(wishlist.getProduct().getId()).orElse(null);
                wishlistDTOList.add(WishlistDTO.builder()
                        .id(wishlist.getId())
                        .product(product)
                        .user(user)
                        .build());
            }
            return wishlistDTOList;
        }
        return null;
    }

    @Override
    public void deleteFromWishlist(Long wishlist_id) {
        wishlistRepository.deleteById(wishlist_id);
    }

    @Override
    public boolean addToCart(Long wishlist_id) {
        Wishlist wishlist = wishlistRepository.findById(wishlist_id).orElse(null);
        boolean isAddedToCart = false;
        if(wishlist != null){
            Long product_id = wishlist.getProduct().getId();
            Long user_id = wishlist.getStoreUser().getId();
            int quantity = 1;
            isAddedToCart = cartServiceImpl.addToCart(user_id,product_id,quantity);

        }
        return isAddedToCart;
    }
}
