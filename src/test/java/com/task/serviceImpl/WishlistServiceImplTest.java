package com.task.serviceImpl;

import com.task.dto.WishlistDTO;
import com.task.model.Product;
import com.task.model.User;
import com.task.model.Wishlist;
import com.task.repository.ProductRepository;
import com.task.repository.UserRepository;
import com.task.repository.WishlistRepository;
import com.task.service.impl.CartServiceImpl;
import com.task.service.impl.WishlistServiceImpl;
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

class WishlistServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private WishlistRepository wishlistRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private CartServiceImpl cartServiceImpl;

    @InjectMocks
    private WishlistServiceImpl wishlistServiceImpl;
    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void addToWishlist() {
      Long user_id = 1L;
      Long product_id = 1L;
      User user = new User();
      user.setId(user_id);
      when(userRepository.findStoreUsersById(user_id)).thenReturn(Optional.of(user));
      Product product = new Product();
      product.setId(product_id);
      when(productRepository.findById(product_id)).thenReturn(Optional.of(product));
      when(wishlistRepository.findByStoreUserAndProduct(user_id,product_id)).thenReturn(Optional.empty());
      assertTrue(wishlistServiceImpl.addToWishlist(user_id,product_id));
    }

    @Test
    void viewWishlist() {
        Long user_id = 1L;
        User user = new User();
        user.setId(user_id);
        when(userRepository.findStoreUsersById(user_id)).thenReturn(Optional.of(user));

        Long product_id = 1L;
        Product product = new Product();
        product.setId(product_id);
        when(productRepository.findById(product_id)).thenReturn(Optional.of(product));

        Wishlist wishlist = new Wishlist();
        wishlist.setId(1L);
        wishlist.setProduct(product);
        wishlist.setStoreUser(user);

        List<Wishlist> wishlistList = new ArrayList<>();
        wishlistList.add(wishlist);

        when(wishlistRepository.findAllByStoreUser(user_id)).thenReturn(wishlistList);
        List<WishlistDTO> wishlistDTOList = wishlistServiceImpl.viewWishlist(user_id);
        assertNotNull(wishlistDTOList);
        assertEquals(product, wishlistDTOList.get(0).getProduct());
        assertEquals(user,wishlistDTOList.get(0).getUser());
        assertEquals(wishlist.getId(), wishlistDTOList.get(0).getId());
    }

    @Test
    void addToCart() {
        int quantity = 1;
        Long user_id = 1L;
        User user = new User();
        user.setId(user_id);
        when(userRepository.findStoreUsersById(user_id)).thenReturn(Optional.of(user));

        Long product_id = 1L;
        Product product = new Product();
        product.setId(product_id);
        when(productRepository.findById(product_id)).thenReturn(Optional.of(product));

        Wishlist wishlist = new Wishlist();
        wishlist.setId(1L);
        wishlist.setProduct(product);
        wishlist.setStoreUser(user);
        when(wishlistRepository.findById(wishlist.getId())).thenReturn(Optional.of(wishlist));
        when(cartServiceImpl.addToCart(user_id,product_id,quantity)).thenReturn(true);
        assertTrue(wishlistServiceImpl.addToCart(wishlist.getId()));
    }

    @Test
    void testAddToWishlist() {
    }

    @Test
    void testViewWishlist() {
    }

    @Test
    void testAddToCart() {
    }
}