package com.task.serviceImpl;

import com.task.dto.OrderDTO;
import com.task.model.Order;
import com.task.model.Product;
import com.task.model.User;
import com.task.repository.OrderRepository;
import com.task.repository.ProductRepository;
import com.task.service.impl.OrderServiceImpl;
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

class OrderServiceImplTest {
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private OrderServiceImpl orderService;
    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void viewUserOrder() {
        Long user_id = 1L;
        User user = new User();
        user.setId(user_id);

        Long product_id = 1L;
        Product product = new Product();
        product.setId(product_id);
        product.setPrice(1000.0);
        when(productRepository.findById(product_id)).thenReturn(Optional.of(product));
        Order order = new Order();
        order.setId(1L);
        order.setStoreUser(user);
        order.setOrderDate(LocalDate.now());
        order.setProduct(product);
        order.setQuantity(2);
        order.setAmount(order.getQuantity()*product.getPrice());
        order.setPrice(product.getPrice());
        List<Order> orderList = new ArrayList<>();
        orderList.add(order);
        when(orderRepository.findAllByStoreUser(user_id)).thenReturn(orderList);

        List<OrderDTO> orderDTOList = orderService.viewUserOrder(user_id);
        assertNotNull(orderDTOList);
        assertEquals(orderList.size(),orderDTOList.size());
        assertEquals(orderList.get(0).getStoreUser(), user);
        assertEquals(orderList.get(0).getProduct(),product);

    }

    @Test
    void viewAllOrder() {
        User user = new User();
        user.setId(1L);

        Product product = new Product();
        product.setId(1L);
        product.setPrice(1000.0);

        Order order = new Order();
        order.setId(1L);
        order.setStoreUser(user);
        order.setOrderDate(LocalDate.now());
        order.setProduct(product);
        order.setQuantity(2);
        order.setAmount(order.getQuantity()*product.getPrice());
        order.setPrice(product.getPrice());
        List<Order> orderList = new ArrayList<>();
        orderList.add(order);
        when(orderRepository.findAll()).thenReturn(orderList);

        List<OrderDTO> orderDTOList = orderService.viewAllOrder();
        assertNotNull(orderDTOList);
        assertEquals(1, orderDTOList.size());
        assertEquals(product,orderDTOList.get(0).getProduct());
    }

    @Test
    void getOrderTotal() {
        Long user_id = 1L;
        User user = new User();
        user.setId(user_id);

        Long product_id = 1L;
        Product product = new Product();
        product.setId(product_id);
        product.setPrice(1000.0);
        when(productRepository.findById(product_id)).thenReturn(Optional.of(product));
        Order order = new Order();
        order.setId(1L);
        order.setStoreUser(user);
        order.setOrderDate(LocalDate.now());
        order.setProduct(product);
        order.setQuantity(2);
        order.setAmount(order.getQuantity()*product.getPrice());
        order.setPrice(product.getPrice());
        List<Order> orderList = new ArrayList<>();
        orderList.add(order);
        when(orderRepository.findAllByStoreUser(user_id)).thenReturn(orderList);

        double expectedTotal = 2000.0;
        double actualTotal = orderService.getOrderTotal(user_id);
        assertEquals(expectedTotal,actualTotal);
    }
}