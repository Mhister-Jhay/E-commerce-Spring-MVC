package com.task.service;

import com.task.dto.CartDTO;
import com.task.dto.ProductDTO;
import com.task.model.Cart;
import com.task.model.Order;


import java.util.List;

public interface CartService {

    List<CartDTO> viewAllCart(Long id);
//    List<ProductDTO> getProductList(Long id);

    boolean addToCart(Long user_id, Long product_id, int quantity);
    boolean updateCartQuantity(Long user_id, Long product_id, int quantity, Long cart_id);
    void deleteFromCart(Long cart_id);
    boolean checkOut(Long id);
    double getCartTotal(Long user_id);

}
