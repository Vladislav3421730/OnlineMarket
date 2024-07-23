package com.example.onlinemarket.controllers;


import com.example.onlinemarket.Repositories.ImageRepository;
import com.example.onlinemarket.Services.UserService;
import com.example.onlinemarket.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ImageRepository imageRepository;


    @GetMapping("/login")
    public String Login(){return "login";}


    @GetMapping("/registration")
    public String Registration(Model model)
    {
        model.addAttribute("user",new User());
        return "registration";
    }


    @PostMapping("/registration")
    public String registerUser(@Validated User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("user",user);
            return "registration";
        }
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            model.addAttribute("user",user);
            model.addAttribute("PasswordsAreNotTheSame", "Пароли не совпадают");
            return "registration";
        }
        if (!userService.SaveUser(user)) {
            model.addAttribute("user",user);
            model.addAttribute("UserAlsoInSystem", "Пользователь с таким лонгином уже есть в системе");
            return "registration";
        }
        return "redirect:/login";
    }
    @GetMapping("/profile/{id}")
    public String Profile(@PathVariable Long id, Model model, Principal principal ){
        model.addAttribute("user",userService.getUserById(id));
        model.addAttribute("CurrentUser",userService.getUserByPrincipal(principal));
        return "profile";
    }

    @PostMapping("/SetAvatar")
    public String ChangeAvatar(@RequestParam(name = "avatar") MultipartFile multipartFile,
                                Principal principal) throws IOException {
        userService.SetAvatar(multipartFile,principal);
        return "redirect:/profile/"+userService.getUserByPrincipal(principal).getId().toString();
    }
    @PostMapping("/ChangeAvatar/{id}")
    public String ChangeAvatar(@RequestParam(name = "avatar") MultipartFile multipartFile,
                                @PathVariable Long id,Principal principal) throws IOException {
        imageRepository.deleteById(id);
        userService.SetAvatar(multipartFile,principal);
        return "redirect:/profile/"+userService.getUserByPrincipal(principal).getId().toString();
    }
    @GetMapping("/users")
    public  String GetUsers(Principal principal,Model model){
        model.addAttribute("CurrentUser",userService.getUserByPrincipal(principal));
        model.addAttribute("UserList",userService.GetAllUsers());
        return "users";
    }

    @PostMapping("/user/bun/{id}")
    public String BunUser(@PathVariable Long id,Principal principal){
        userService.BunUser(id,userService.getUserByPrincipal(principal));
        return "redirect:/users";
    }

    @PostMapping("/user/delete/{id}")
    public String DeleteUser(@PathVariable Long id){
        userService.DeleteUserById(id);
        return "redirect:/users";
    }

    @PostMapping("/user/admin/{id}")
    public String MakeUserAdmin(@PathVariable Long id){
        userService.MakeAdmin(id);
        return "redirect:/users";
    }
}
