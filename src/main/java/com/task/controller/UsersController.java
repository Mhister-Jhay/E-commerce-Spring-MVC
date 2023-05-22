package com.task.controller;

import com.task.dto.ProductDTO;
import com.task.dto.StoreUsersDTO;
import com.task.model.User;
import com.task.service.impl.ProductServiceImpl;
import com.task.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UsersController {

    private final UserServiceImpl storeUsersServiceImpl;
    private final ProductServiceImpl productServiceImpl;

    @GetMapping("/")
    public ModelAndView landingPage(ModelAndView modelAndView){
        List<ProductDTO> productDTOList = productServiceImpl.viewAllProducts();
        modelAndView.setViewName("index");
        modelAndView.addObject("page", "index");
        modelAndView.addObject("productList", productDTOList);
        modelAndView.addObject("session_id", null);
        modelAndView.addObject("session_name", null);
        return modelAndView;
    }
    @GetMapping("/index")
    public ModelAndView landingRequest(ModelAndView modelAndView, @RequestParam("session_id") Long id, @RequestParam("session_name") String name){
        modelAndView.setViewName("index");
        List<ProductDTO> productDTOList = productServiceImpl.viewAllProducts();
        modelAndView.addObject("productList", productDTOList);
        modelAndView.addObject("session_id", id);
        modelAndView.addObject("session_name", name);
        return modelAndView;
    }

    @GetMapping("/registration")
    public ModelAndView viewRegistrationPage(ModelAndView modelAndView){

        modelAndView.setViewName("registration");
        modelAndView.addObject("storeUserForm",new StoreUsersDTO());
        return modelAndView;
    }

    @PostMapping("/registerUser")
    public String getUserRegistered(@Validated StoreUsersDTO storeUsersDTO, Model model){
        if(storeUsersDTO.getPassword().equals(storeUsersDTO.getConfirmPassword())){
            boolean isRegistered = storeUsersServiceImpl.getUserRegistered(storeUsersDTO);
            if(isRegistered){
                model.addAttribute("userStatus", "User is Successfully Registered");
                model.addAttribute("loginForm", new StoreUsersDTO());
                return "login";
            }else{
                model.addAttribute("userStatus", "Email is already Registered");
                model.addAttribute("storeUserForm",new StoreUsersDTO());
                return "registration";
            }
        }else{
            model.addAttribute("userStatus", "Passwords do not match");
            model.addAttribute("storeUserForm",new StoreUsersDTO());
        }

        return "registration";
    }

    @GetMapping("/login")
    public ModelAndView viewLoginPage(ModelAndView modelAndView){
        modelAndView.setViewName("login");
        modelAndView.addObject("loginForm", new StoreUsersDTO());
        return modelAndView;
    }

    @PostMapping("/loginUser")
    public String loginUser(@Validated StoreUsersDTO storeUsersDTO, Model model, HttpServletRequest httpServletRequest){
        if(storeUsersServiceImpl.findUserByEmail(storeUsersDTO.getEmail()) != null){
            User user = storeUsersServiceImpl.findUserByEmail(storeUsersDTO.getEmail());
            boolean isUserLoggedIn = storeUsersServiceImpl.getUserLoggedIn(storeUsersDTO);
            if(isUserLoggedIn){
                HttpSession session = httpServletRequest.getSession(true);
                session.setAttribute("userName", user.getFirstName());
                session.setAttribute("user_id", user.getId());

                model.addAttribute("userStatus", "User successfully Logged in");

                model.addAttribute("session_id", session.getAttribute("user_id"));
                model.addAttribute("session_name", session.getAttribute("userName"));
                List<ProductDTO> productDTOList = productServiceImpl.viewAllProducts();
                model.addAttribute("productList", productDTOList);
                if(user.getEmail().equals("admin@gmail.com")){
                    model.addAttribute("productForm", new ProductDTO());
                    model.addAttribute("product_id", null);
                    return "AdminHome";
                }else{
                    return "index";
                }
            }else{
                model.addAttribute("userStatus", "Incorrect Password");
                model.addAttribute("loginForm", new StoreUsersDTO());
                return "login";
            }
        }else{
            model.addAttribute("userStatus", "Email not found, please Sign up");
            model.addAttribute("loginForm", new StoreUsersDTO());
        }
        return "login";
    }

    @GetMapping("/logout")
    public String logOutUser(HttpServletRequest httpServletRequest){
        HttpSession session = httpServletRequest.getSession();
        session.invalidate();
        return "redirect:/";
    }
}
