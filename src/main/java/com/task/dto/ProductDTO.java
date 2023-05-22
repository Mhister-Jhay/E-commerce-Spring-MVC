package com.task.dto;

import com.task.enums.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {
    private Long id;
    private String name;
    private byte[] image;
    private Double price;
    private Integer quantity;
    private ProductCategory productCategory;

}
