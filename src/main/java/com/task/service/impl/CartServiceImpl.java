package com.task.service.impl;

import com.task.dto.CartDTO;
import com.task.model.Cart;
import com.task.model.Order;
import com.task.model.Product;
import com.task.model.User;
import com.task.repository.CartRepository;
import com.task.repository.OrderRepository;
import com.task.repository.ProductRepository;
import com.task.repository.UserRepository;
import com.task.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@RequiredArgsConstructor
@Service
public class CartServiceImpl implements CartService{

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    @Override
    public List<CartDTO> viewAllCart(Long id) {
        User user = userRepository.findStoreUsersById(id).orElse(null);
        if(user != null){
            List<Cart> cartList = cartRepository.findAllByStoreUser(id);
            List<CartDTO> cartDTOList = new ArrayList<>();
            for(Cart cart:cartList){
                Product product = productRepository.findById(cart.getProduct().getId()).orElse(null);
                cartDTOList.add(CartDTO.builder()
                                .id(cart.getId())
                                .product(product)
                                .user(user)
                                .quantity(cart.getQuantity())
                                .build());
            }
            return cartDTOList;
        }
        return null;
    }

    @Override
    public boolean addToCart(Long user_id, Long product_id, int quantity) {
        User user = userRepository.findStoreUsersById(user_id).orElse(null);
        Product product = productRepository.findById(product_id).orElse(null);
        if(user != null && product != null){
            Cart cart = cartRepository.findByStoreUserAndProduct(user_id,product_id).orElse(null);
            if(cart == null){
                cartRepository.save(Cart.builder()
                                .storeUser(user)
                                .product(product)
                                .quantity(1)
                                .build());
                return true;
            } else{
               int isUpdated = cartRepository.updateCartQuantity(cart.getQuantity()+quantity,product_id,user_id);
                return isUpdated > 0;
            }

        }
        return false;
    }

    @Override
    public boolean updateCartQuantity(Long user_id, Long product_id, int quantity, Long cart_id) {
        int oldQuantity = cartRepository.selectCartQuantity(product_id,user_id);
        if(oldQuantity+quantity <= 0){
            cartRepository.deleteById(cart_id);
            return false;
        }
        int isUpdated = cartRepository.updateCartQuantity(oldQuantity+quantity,product_id,user_id);
        return isUpdated > 0;
    }

    @Override
    public void deleteFromCart(Long cart_id) {
        cartRepository.deleteById(cart_id);
    }


    @Override
    public boolean checkOut(Long id) {
        List<CartDTO> cartDTOList = viewAllCart(id);
        User user = userRepository.findStoreUsersById(id).orElse(null);
        List<Order> orderList = orderRepository.findAllByStoreUser(id);
        for(CartDTO cart: cartDTOList){
            Product product = productRepository.findById(cart.getProduct().getId()).orElse(null);
            assert product != null;
            orderList.add(Order.builder()
                            .amount(product.getPrice()*cart.getQuantity())
                            .orderDate(LocalDate.now())
                            .price(product.getPrice())
                            .quantity(cart.getQuantity())
                            .product(product)
                            .storeUser(user)
                            .build());
        }
        int isCheckedOut = cartRepository.deleteAllByStoreUser(id);
        if(isCheckedOut > 0){
            orderRepository.saveAll(orderList);
            return true;
        }
        return false;
    }

    @Override
    public double getCartTotal(Long user_id) {
        List<CartDTO> cartDTOList = viewAllCart(user_id);
        double cartTotal = 0.0;
        for(CartDTO cartDTO : cartDTOList){
            cartTotal = cartTotal + (cartDTO.getQuantity()*cartDTO.getProduct().getPrice());
        }
        return cartTotal;
    }

}
