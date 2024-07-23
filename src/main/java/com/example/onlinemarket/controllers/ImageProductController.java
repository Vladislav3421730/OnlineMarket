package com.example.onlinemarket.controllers;

import com.example.onlinemarket.Services.ProductService;
import com.example.onlinemarket.Services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.ui.Model;
import com.example.onlinemarket.models.Product;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.Principal;

@Controller
@AllArgsConstructor
public class ImageProductController {

    private final ProductService productService;
    private final UserService userService;

    @GetMapping("/product/add")
    public String GetPageForAddingProduct( Model model, Principal principal) {
        model.addAttribute("product", new Product());
        model.addAttribute("user",userService.getUserByPrincipal(principal));
        return "AddProducts";

    }
    @PostMapping("/product/delete/{id}")
    public String DeleteProductById(@PathVariable Long id,Principal principal,Model model){
        productService.DeleteProductById(id);
        return "redirect:/profile/"+userService.getUserByPrincipal(principal).getId().toString();
    }

    @PostMapping("/product/save")
    public String ViewInformationAboutProduct(@Validated Product product, BindingResult bindingResult, RedirectAttributes redirectAttributes,
                                              @RequestParam(name="file1",required = false) MultipartFile file1,
                                              @RequestParam(name="file2",required = false) MultipartFile file2,
                                              @RequestParam(name="file3",required = false) MultipartFile file3,
                                              Principal principal) throws  IOException {
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("product",product);
            return "redirect:/product/add";
        }
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
