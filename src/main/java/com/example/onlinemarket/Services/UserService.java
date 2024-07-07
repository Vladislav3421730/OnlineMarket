package com.example.onlinemarket.Services;


import com.example.onlinemarket.Repositories.ImageRepository;
import com.example.onlinemarket.Repositories.UserRepository;
import com.example.onlinemarket.exceptions.DataNotFoundException;
import com.example.onlinemarket.exceptions.OperationForbiddenException;
import com.example.onlinemarket.models.Image;
import com.example.onlinemarket.models.Role;
import com.example.onlinemarket.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ImageRepository imageRepository;

    @Autowired
    public BCryptPasswordEncoder passwordEncoder;

    public boolean SaveUser(User user){
        if(userRepository.findUserByLogin(user.getLogin())!=null) return false;
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }
    public User getUserByPrincipal(Principal principal) {
        if (principal == null) return new User();
        return userRepository.findUserByLogin(principal.getName());
    }
    public void SetAvatar(MultipartFile avatar, Principal principal) throws IOException {
        Image FutureAvatar = new Image();
        if(avatar.getSize()!=0) {
            FutureAvatar  = ProductService.toImageEntity(avatar);
        }
        FutureAvatar.setUser(getUserByPrincipal(principal));
        imageRepository.save(FutureAvatar);
    }
    public User getUserById(Long id){
        return userRepository.findById(id).orElseThrow(()->
                new DataNotFoundException("user with id "+id+" not found"));
    }
    public List<User> GetAllUsers(){return userRepository.findAll();}

    public void DeleteUserById(Long id){ userRepository.deleteById(id); }

    public void MakeAdmin(Long id){
        User user=userRepository.findById(id).orElse(null);
        if(user==null){
            throw new DataNotFoundException("User doesn't exist");
        }
        if(user.getRoleSet().contains(Role.ROLE_ADMIN)){
            user.getRoleSet().remove(Role.ROLE_ADMIN);
        }
        else {
            user.getRoleSet().add(Role.ROLE_ADMIN);
        }
        userRepository.save(user);
    }
    public void BunUser(Long id,User Currentuser){
        User user=userRepository.findById(id).orElse(null);
        if(user==null){
            throw new DataNotFoundException("User doesn't exist");
        }
        if(!user.isIsBun()){
           if(!Currentuser.isAdmin() && user.isAdmin()){
               throw new OperationForbiddenException("Current user is not allowed to ban an admin user.");
           }
           else user.setIsBun(true);
        }
        else user.setIsBun(false);
        userRepository.save(user);
    }
}
