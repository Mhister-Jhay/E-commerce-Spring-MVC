package com.task.controller;

import com.task.service.impl.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final OrderServiceImpl orderServiceImpl;

    @GetMapping("/order")
    public ModelAndView viewOrder(ModelAndView modelAndView,  @RequestParam("session_id") Long session_id,
                                  @RequestParam("session_name") String session_name){
        modelAndView.setViewName("order");
        modelAndView.addObject("orderList",orderServiceImpl.viewUserOrder(session_id));
        System.out.println(orderServiceImpl.viewUserOrder(session_id).get(0).getProduct().getId());
        System.out.println(orderServiceImpl.viewUserOrder(session_id).get(0).getProduct().getName());
        System.out.println(orderServiceImpl.viewUserOrder(session_id).get(0).getProduct().getPrice());
        modelAndView.addObject("orderTotal",orderServiceImpl.getOrderTotal(session_id));
        modelAndView.addObject("session_id",session_id);
        modelAndView.addObject("session_name",session_name);
        return modelAndView;
    }
}
