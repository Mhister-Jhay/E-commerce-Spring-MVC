package com.task.controller;

import com.task.dto.ProductDTO;
import com.task.service.impl.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductServiceImpl productServiceImpl;

    @GetMapping("/products")
    public  ModelAndView viewAllProducts(ModelAndView modelAndView, @RequestParam("session_id") Long id, @RequestParam("session_name") String name){
        List<ProductDTO> productDTOList = productServiceImpl.viewAllProducts();
        modelAndView.setViewName("products");
        modelAndView.addObject("productList", productDTOList);
        modelAndView.addObject("session_id",id);
        modelAndView.addObject("session_name",name);
        return modelAndView;
    }
    @GetMapping("/product")
    public  ModelAndView viewAllProducts(ModelAndView modelAndView){
        List<ProductDTO> productDTOList = productServiceImpl.viewAllProducts();
        modelAndView.setViewName("products");
        modelAndView.addObject("productList", productDTOList);
        modelAndView.addObject("session_id",null);
        modelAndView.addObject("session_name",null);
        return modelAndView;
    }


}
