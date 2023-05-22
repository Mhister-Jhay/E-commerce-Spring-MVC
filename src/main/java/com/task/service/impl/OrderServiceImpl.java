package com.task.service.impl;

import com.task.dto.OrderDTO;
import com.task.model.Order;
import com.task.repository.OrderRepository;
import com.task.repository.ProductRepository;
import com.task.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Override
    public List<OrderDTO> viewUserOrder(Long id) {
        List<Order> orderList = orderRepository.findAllByStoreUser(id);
        if(orderList != null){
            List<OrderDTO> orderDTOList = new ArrayList<>();
            for(Order order: orderList){
                productRepository.findById(order.getProduct().getId()).ifPresent(product -> orderDTOList.add(OrderDTO.builder()
                        .id(order.getId())
                        .amount(order.getAmount())
                        .date(order.getOrderDate())
                        .price(order.getPrice())
                        .quantity(order.getQuantity())
                        .user(order.getStoreUser())
                        .product(product)
                        .build()));
            }
            return orderDTOList;
        }
        return null;
    }

    @Override
    public List<OrderDTO> viewAllOrder() {
        List<Order> orderList = orderRepository.findAll();
        List<OrderDTO> orderDTOList = new ArrayList<>();
        for(Order order: orderList){
            orderDTOList.add(OrderDTO.builder()
                    .id(order.getId())
                    .amount(order.getAmount())
                    .date(order.getOrderDate())
                    .price(order.getPrice())
                    .quantity(order.getQuantity())
                    .user(order.getStoreUser())
                    .product(order.getProduct())
                    .build());
        }
        return orderDTOList;
    }

    @Override
    public double getOrderTotal(Long user_id) {
        List<OrderDTO> orderDTOList = viewUserOrder(user_id);
        double orderTotal = 0.0;
        for(OrderDTO orderDTO : orderDTOList){
            orderTotal = orderTotal + (orderDTO.getQuantity()*orderDTO.getPrice());
        }
        return orderTotal;
    }

}
