package com.task.service;

import com.task.dto.OrderDTO;

import java.util.List;

public interface OrderService {

    List<OrderDTO>viewUserOrder(Long id);
    List<OrderDTO> viewAllOrder();

    double getOrderTotal(Long user_id);
}
