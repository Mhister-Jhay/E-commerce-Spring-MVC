package com.task.dto;

import com.task.model.Product;
import com.task.model.User;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartDTO {
    private Long id;
    private Product product;
    private User user;
    private int quantity;
}
