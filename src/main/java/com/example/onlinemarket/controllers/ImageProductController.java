package com.example.onlinemarket.controllers;

import com.example.onlinemarket.Services.ProductService;
import com.example.onlinemarket.Services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.ui.Model;
import com.example.onlinemarket.models.Product;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@Controller
@AllArgsConstructor
public class ImageProductController {

    private final ProductService productService;
    private final UserService userService;

    @GetMapping("/product/add")
    public String GetIndexPage(Model model,Product product,Principal principal) {
        model.addAttribute("product",product);
        model.addAttribute("user",userService.getUserByPrincipal(principal));
        return "AddProducts";

    }
    @GetMapping("/product/edit/{id}")
    public String EditProduct(@PathVariable Long id,Model model,Principal principal){
        model.addAttribute("product",productService.FindProductById(id));
        model.addAttribute("user",userService.getUserByPrincipal(principal));
        return "AddProducts";
    }

    @PostMapping("/product/delete/{id}")
    public String DeleteProductById(@PathVariable Long id,Principal principal,Model model){
        productService.DeleteProductById(id);
        return "redirect:/profile/"+userService.getUserByPrincipal(principal).getId().toString();
    }

    @PostMapping("/product/save")
    public String ViewInformationAboutProduct(Product product, @RequestParam("file1") MultipartFile file1,
                                              @RequestParam("file2") MultipartFile file2,
                                              @RequestParam("file3") MultipartFile file3,Principal principal) throws  IOException {
        productService.saveProduct(product,principal,file1,file2,file3);
        return "redirect:/profile/"+userService.getUserByPrincipal(principal).getId().toString();
    }

    @GetMapping("/")
    public String AllProducts(Model model, @RequestParam(name = "sort",defaultValue = "none") String sort,
                              @RequestParam(name ="search",required = false ) String search, Principal principal) {
        model.addAttribute("products",productService.AllProducts(sort,search));
        model.addAttribute("search",search);
        model.addAttribute("user",userService.getUserByPrincipal(principal));
        return "AllProducts";
    }
    @GetMapping("/product/view/{id}")
    public String OneProduct(@PathVariable Long id,Model model,Principal principal){
        model.addAttribute("product",productService.FindProductById(id));
        model.addAttribute("user",userService.getUserByPrincipal(principal));
        return "OneProduct";
    }
}
