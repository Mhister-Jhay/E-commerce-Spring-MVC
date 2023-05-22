package com.task.controller;

import com.task.service.impl.CartServiceImpl;
import com.task.service.impl.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class CartController {
    private final CartServiceImpl cartServiceImpl;
    private final ProductServiceImpl productServiceImpl;

    @GetMapping("/cart")
    public ModelAndView viewCart(ModelAndView modelAndView, @RequestParam("session_id") Long session_id,
                                 @RequestParam("session_name") String session_name
                                 ){
        modelAndView.setViewName("cart");
        modelAndView.addObject("cartList", cartServiceImpl.viewAllCart(session_id));
        modelAndView.addObject("cartTotal", cartServiceImpl.getCartTotal(session_id));
        modelAndView.addObject("session_id",session_id);
        modelAndView.addObject("session_name",session_name);
        return modelAndView;
    }

    @PostMapping("/addToCart")
    public String addToCart(Model model,@RequestParam("product_id") Long product_id,
                            @RequestParam("prevPage") String route,
                            @RequestParam("session_id") Long session_id,
                            @RequestParam("session_name") String session_name){
        int quantity = 1;
        boolean isAddedToCart = cartServiceImpl.addToCart(session_id,product_id,quantity);
        if(isAddedToCart){
            model.addAttribute("cartStatus", "Product Added to Cart Successfully");
        }else{
            model.addAttribute("cartStatus", "Adding to Cart was Interrupted");
        }
        model.addAttribute("productList", productServiceImpl.viewAllProducts());
        model.addAttribute("session_id", session_id);
        model.addAttribute("session_name", session_name);
        return route;
    }
    @PostMapping("/editCart")
    public String editOrDeleteCartItem(Model model, @RequestParam("session_id") Long user_id,
                                       @RequestParam("product_id") Long product_id,
                                       @RequestParam("cart_id") Long cart_id,
                                       @RequestParam("submit_type") String button,
                                       @RequestParam("session_name") String session_name){
        switch (button) {
            case "plus" -> {
                int quantity = 1;
                boolean isQuantityUpdated = cartServiceImpl.updateCartQuantity(user_id, product_id, quantity,cart_id);
                if (isQuantityUpdated) {
                    model.addAttribute("cartStatus", "Product Updated Successfully");
                }
            }
            case "minus" -> {
                int quantity = -1;
                boolean isQuantityUpdated = cartServiceImpl.updateCartQuantity(user_id, product_id, quantity, cart_id);
                if (isQuantityUpdated) {
                    model.addAttribute("cartStatus", "Product Updated Successfully");
                }
            }
            case "delete" -> {
                cartServiceImpl.deleteFromCart(cart_id);
                model.addAttribute("cartStatus", "Product Deleted Successfully");
            }
        }
        model.addAttribute("cartList", cartServiceImpl.viewAllCart(user_id));
        model.addAttribute("cartTotal", cartServiceImpl.getCartTotal(user_id));
        model.addAttribute("session_id",user_id);
        model.addAttribute("session_name",session_name);
        return "cart";
    }

    @PostMapping("/checkout")
    public String checkOut(Model model, @RequestParam("session_id") Long user_id,
                           @RequestParam("session_name") String session_name){
        boolean isOrderMade = cartServiceImpl.checkOut(user_id);
        if(isOrderMade){
            model.addAttribute("cartStatus", "Thanks for Ordering");
        }else{
            model.addAttribute("cartStatus", "Failed to place order");
        }
        model.addAttribute("cartList", cartServiceImpl.viewAllCart(user_id));
        model.addAttribute("cartTotal", cartServiceImpl.getCartTotal(user_id));
        model.addAttribute("session_id",user_id);
        model.addAttribute("session_name",session_name);
        return "cart";
    }
}
