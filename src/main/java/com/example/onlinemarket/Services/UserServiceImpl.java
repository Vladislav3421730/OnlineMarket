package com.example.onlinemarket.Services;

import com.example.onlinemarket.Repositories.UserRepository;
import com.example.onlinemarket.models.User;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user =userRepository.findUserByLogin(username);
        if(user==null){
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }
}
