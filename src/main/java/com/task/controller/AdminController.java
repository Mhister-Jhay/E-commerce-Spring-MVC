package com.task.controller;

import com.task.dto.ProductDTO;
import com.task.service.impl.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final ProductServiceImpl productServiceImpl;

    @GetMapping("/admin")
    public ModelAndView viewAdminHomePage(ModelAndView modelAndView){
        List<ProductDTO> productDTOList = productServiceImpl.viewAllProducts();
        modelAndView.setViewName("AdminHome");
        modelAndView.addObject("productForm",new ProductDTO());
        modelAndView.addObject("product_id", null);
        modelAndView.addObject("productList", productDTOList);
        return modelAndView;
    }

    @PostMapping("/searchProduct")
    public String searchProduct(@Validated Long id, Model model){
        System.out.println(id);
        ProductDTO productDTO = productServiceImpl.findProductById(id);
        if(productDTO != null){
            model.addAttribute("searchedProduct", productDTO);
        }else{
            model.addAttribute("searchedProduct", null);
            model.addAttribute("AdminStatus", "Product with "+id+" is not available");
        }
        List<ProductDTO> productDTOList = productServiceImpl.viewAllProducts();
        model.addAttribute("productList", productDTOList);
        model.addAttribute("productForm", new ProductDTO());
        model.addAttribute("product_id", null);
        return "AdminHome";
    }

    @GetMapping("/product/image/{id}")
    public ResponseEntity<byte[]> getProductImage(@PathVariable Long id) {
        // Find the product by id
        ProductDTO productDTO = productServiceImpl.findProductById(id);
        if (productDTO == null) {
            return ResponseEntity.notFound().build();
        }

        // Get the image data
        byte[] image = productDTO.getImage();
        if (image == null) {
            return ResponseEntity.notFound().build();
        }

        // Create the response entity with the image data and appropriate headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        headers.setContentLength(image.length);
        return new ResponseEntity<>(image, headers, HttpStatus.OK);
    }


    @PostMapping("/addProduct")
    public String addProduct(@ModelAttribute("productForm") ProductDTO productDTO, BindingResult bindingResult,
                             @RequestParam("image") MultipartFile image, Model model) {

        if (!Objects.requireNonNull(image.getContentType()).startsWith("image/")) {
            bindingResult.rejectValue("image", "file.type.invalid", "File must be an image");
        }
        // convert MultipartFile to byte array
        byte[] imageBytes = null;
        try {
            imageBytes = image.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        productDTO.setImage(imageBytes);
        boolean isProductSaved;
        isProductSaved = productServiceImpl.saveProduct(productDTO);
        if(isProductSaved){
            model.addAttribute("AdminStatus", "Product Saved Successfully");
        } else {
            model.addAttribute("AdminStatus","Product saving failed");
        }
        List<ProductDTO> productDTOList = productServiceImpl.viewAllProducts();
        model.addAttribute("productList", productDTOList);
        model.addAttribute("productForm", new ProductDTO());
        model.addAttribute("product_id", null);
        return "AdminHome";
    }

    @PostMapping("/updatePrice")
    public String updateProductPrice(@Validated ProductDTO productDTO, Model model){
        Double price = productDTO.getPrice();
        Long id = productDTO.getId();
        ProductDTO productDTO1 = productServiceImpl.updateProductPrice(price,id);
        if(productDTO1 != null){
            model.addAttribute("AdminStatus","Price Updated Successfully");
        }else{
            model.addAttribute("AdminStatus", "Price not updated successfully");
        }
        List<ProductDTO> productDTOList = productServiceImpl.viewAllProducts();
        model.addAttribute("productList", productDTOList);
        model.addAttribute("productForm", new ProductDTO());
        model.addAttribute("product_id", null);
        return "AdminHome";
    }

    @PostMapping("/updateQuantity")
    public String updateProductQuantity(@Validated ProductDTO productDTO, Model model){
        Integer quantity = productDTO.getQuantity();
        Long id = productDTO.getId();
        ProductDTO productDTO1 = productServiceImpl.updateProductQuantity(quantity,id);
        if(productDTO1 != null){
            model.addAttribute("AdminStatus","Quantity Updated Successfully");
        }else{
            model.addAttribute("AdminStatus", "Quantity not updated successfully");
        }
        List<ProductDTO> productDTOList = productServiceImpl.viewAllProducts();
        model.addAttribute("productList", productDTOList);
        model.addAttribute("productForm", new ProductDTO());
        model.addAttribute("product_id", null);
        return "AdminHome";
    }

    @PostMapping("/deleteProduct")
    public String deleteProduct(@Validated ProductDTO productDTO, Model model){
        Long id = productDTO.getId();
        productServiceImpl.deleteProduct(id);
        List<ProductDTO> productDTOList = productServiceImpl.viewAllProducts();
        model.addAttribute("productList", productDTOList);
        model.addAttribute("AdminStatus", "Product deleted Successfully");
        model.addAttribute("productForm", new ProductDTO());
        model.addAttribute("product_id", null);
        return "AdminHome";
    }

}
