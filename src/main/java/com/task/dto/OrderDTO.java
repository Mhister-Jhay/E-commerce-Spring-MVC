package com.task.dto;

import com.task.model.Product;
import com.task.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDTO {
    private Long id;
    private Product product;
    private Double price;
    private Integer quantity;
    private Double amount;
    private User user;
    private LocalDate date;

}
