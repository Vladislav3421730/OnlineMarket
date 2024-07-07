package com.example.onlinemarket.config;

import com.example.onlinemarket.Services.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig  {


    private final UserServiceImpl userService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/product/view/{id}","/","/css/**","/profile/{id}", "/registration","/images/{id}","/login").permitAll()
                        .requestMatchers("/product/**","/SetAvatar","/ChangeAvatar").authenticated()
                        .requestMatchers("/user**").hasAnyRole("ADMIN","MANAGER")
                        .requestMatchers("/user/delete/{id}","/user/admin/{id}").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin((form)->form
                        .loginPage("/login")
                        .defaultSuccessUrl("/")
                        .permitAll());
        return http.build();
    }


    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider()  {
        DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userService);
        return daoAuthenticationProvider;
    }


    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return  authenticationConfiguration.getAuthenticationManager();
    }

}
