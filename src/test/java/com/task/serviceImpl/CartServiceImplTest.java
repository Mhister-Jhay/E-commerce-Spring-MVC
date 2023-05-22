package com.task.serviceImpl;


import com.task.dto.CartDTO;
import com.task.model.Cart;
import com.task.model.Order;
import com.task.model.Product;
import com.task.model.User;
import com.task.repository.CartRepository;
import com.task.repository.OrderRepository;
import com.task.repository.ProductRepository;
import com.task.repository.UserRepository;
import com.task.service.impl.CartServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


class CartServiceImplTest {
    @Mock
    private CartRepository cartRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private OrderRepository orderRepository;
    @InjectMocks
    private CartServiceImpl cartService;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void ViewAllCart() {
        Long user_id = 1L;
        User user = new User();
        user.setId(1L);
        when(userRepository.findStoreUsersById(user_id)).thenReturn(Optional.of(user));
        Long product_id = 1L;
        Product product = new Product();
        product.setId(product_id);
        when(productRepository.findById(product_id)).thenReturn(Optional.of(product));
        Long cart_id = 1L;
        Cart cart = new Cart();
        cart.setId(cart_id);
        cart.setQuantity(2);
        cart.setProduct(product);
        cart.setStoreUser(user);
        List<Cart> cartList = new ArrayList<>();
        cartList.add(cart);
        when(cartRepository.findAllByStoreUser(user_id)).thenReturn(cartList);
        List<CartDTO> cartDTOList = cartService.viewAllCart(user_id);

        assertNotNull(cartDTOList);
        assertEquals(cartDTOList.size(),1);
        CartDTO cartDTO = cartDTOList.get(0);
        assertEquals(cartDTO.getQuantity(),2);
        assertEquals(cartDTO.getProduct(),product);
        assertEquals(cartDTO.getUser(), user);
    }

    @Test
    void addToCart(){
        Long user_id = 1L;
        User user = new User();
        user.setId(1L);
        when(userRepository.findStoreUsersById(user_id)).thenReturn(Optional.of(user));
        Long product_id = 1L;
        Product product = new Product();
        product.setId(product_id);
        when(productRepository.findById(product_id)).thenReturn(Optional.of(product));
        when(cartRepository.findByStoreUserAndProduct(user_id,product_id)).thenReturn(Optional.empty());
        assertTrue(cartService.addToCart(user_id,product_id,2));
    }

    @Test
    void updateCartQuantity(){
        Long user_id = 1L;
        Long product_id = 1L;
        int quantity = 1;
        Long cart_id = 1L;
        int oldQuantity = 1;
        when(cartRepository.selectCartQuantity(product_id,user_id)).thenReturn(oldQuantity);
        when(cartRepository.updateCartQuantity(oldQuantity+quantity,product_id,user_id)).thenReturn(1);
        assertTrue(cartService.updateCartQuantity(user_id,product_id,quantity,cart_id));

    }

    @Test
    void checkOut(){
        Long user_id = 1L;
        User user = new User();
        user.setId(1L);
        when(userRepository.findStoreUsersById(user_id)).thenReturn(Optional.of(user));

        Long product_id = 1L;
        Product product = new Product();
        product.setId(product_id);
        product.setPrice(3000.0);
        when(productRepository.findById(product_id)).thenReturn(Optional.of(product));

        Cart cart = new Cart();
        cart.setQuantity(2);

        Order order = new Order();
        order.setId(1L);
        order.setAmount(product.getPrice()*cart.getQuantity());
        order.setOrderDate(LocalDate.now());
        order.setProduct(product);
        order.setQuantity(cart.getQuantity());
        order.setStoreUser(user);
        order.setPrice(product.getPrice());
        List<Order> orderList = new ArrayList<>();
        orderList.add(order);
        when(cartRepository.deleteAllByStoreUser(user_id)).thenReturn(1);

        assertTrue(cartService.checkOut(user_id));
        Order order1 = orderList.get(0);

        assertEquals(order1.getAmount(),6000.0);
        assertEquals(order1.getProduct(),product);
        assertEquals(order1.getStoreUser(), user);
    }

    @Test
    void getCartTotal() {
        Long user_id = 1L;
        User user = new User();
        user.setId(1L);
        when(userRepository.findStoreUsersById(user_id)).thenReturn(Optional.of(user));

        Long product_id = 1L;
        Product product = new Product();
        product.setId(product_id);
        product.setPrice(2000.0);
        when(productRepository.findById(product_id)).thenReturn(Optional.of(product));

        Long cart_id = 1L;
        Cart cart = new Cart();
        cart.setId(cart_id);
        cart.setQuantity(1);
        cart.setProduct(product);
        cart.setStoreUser(user);
        List<Cart> cartList = new ArrayList<>();
        cartList.add(cart);
        when(cartRepository.findAllByStoreUser(user_id)).thenReturn(cartList);

        double expectedCartTotal = 2000.0;
        double actualCartTotal = cartService.getCartTotal(user_id);
        assertEquals(expectedCartTotal,actualCartTotal);
    }
}