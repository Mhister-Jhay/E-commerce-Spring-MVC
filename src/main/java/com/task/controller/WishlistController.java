package com.task.controller;

import com.task.service.impl.ProductServiceImpl;
import com.task.service.impl.WishlistServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class WishlistController {
    private final WishlistServiceImpl wishlistServiceImpl;
    private final ProductServiceImpl productServiceImpl;

    @GetMapping("/wishlist")
    public ModelAndView viewWishlist(ModelAndView modelAndView, @RequestParam("session_id") Long id, @RequestParam("session_name") String name){
        modelAndView.addObject("page","wishlist");
        modelAndView.addObject("session_id",id);
        modelAndView.addObject("session_name",name);
        modelAndView.addObject("wishlistList", wishlistServiceImpl.viewWishlist(id));
        modelAndView.setViewName("wishlist");
        return  modelAndView;
    }
    @PostMapping("/addToWishlist")
    public String addToWishlist(Model model,
                                @RequestParam("product_id") Long product_id,
                                @RequestParam("prevPage") String route,
                                @RequestParam("session_id") Long session_id,
                                @RequestParam("session_name") String session_name){

        boolean isAddedToWishlist = wishlistServiceImpl.addToWishlist(session_id,product_id);
        if(isAddedToWishlist){
            model.addAttribute("wishlistStatus", "Product Added to your wishlist");
        }else{
            model.addAttribute("wishlistStatus","Product is already on your wishlist");
        }
        model.addAttribute("productList", productServiceImpl.viewAllProducts());
        model.addAttribute("session_id", session_id);
        model.addAttribute("session_name", session_name);
        return route;
    }
    @PostMapping("/deleteFromWishlist")
    public String deleteFromWishlist(Model model, @RequestParam("wishlist_id") Long id,
                                     @RequestParam("session_id") Long session_id,
                                     @RequestParam("session_name") String session_name){
        wishlistServiceImpl.deleteFromWishlist(id);
        model.addAttribute("wishlistStatus", "Product deleted from wishlist Successfully");
        model.addAttribute("wishlistList", wishlistServiceImpl.viewWishlist(session_id));
        model.addAttribute("session_id",session_id);
        model.addAttribute("session_name", session_name);
        return "wishlist";
    }

    @PostMapping("/addToCartFromWishlist")
    public String addToCartFromWishlist(Model model,
                                        @RequestParam("wishlist_id") Long id,
                                        @RequestParam("session_id") Long session_id,
                                        @RequestParam("session_name") String session_name){
        boolean isAddedToCart = wishlistServiceImpl.addToCart(id);
        if(isAddedToCart){
            model.addAttribute("wishlistStatus","Product added to Cart Successfully");
        }else{
            model.addAttribute("wishlistStatus", "Failure in adding product to cart");
        }
        model.addAttribute("wishlistList", wishlistServiceImpl.viewWishlist(session_id));
        model.addAttribute("session_id",session_id);
        model.addAttribute("session_name", session_name);
        return "wishlist";
    }
}
